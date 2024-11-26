package com.ssafy.smartstore_jetpack.presentation.views.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.app.SharedPreferencesUtil
import com.ssafy.smartstore_jetpack.databinding.ActivityMainBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseActivity
import com.ssafy.smartstore_jetpack.presentation.util.PermissionChecker
import com.ssafy.smartstore_jetpack.presentation.views.main.attendance.AttendanceUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.home.StoreEventDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var beaconManager: BeaconManager
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var isScanning = false
    private val handler = Handler(Looper.getMainLooper())

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
    }

    private val region = Region(
        "estimote",
        listOf(
            Identifier.parse(BEACON_UUID),
            Identifier.parse(BEACON_MAJOR),
            Identifier.parse(BEACON_MINOR)
        ),
        null
    )

    private val checker = PermissionChecker(this)
    private val runtimePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var preferencesUtil: SharedPreferencesUtil

    private val scanRunnable = object : Runnable {
        override fun run() {
            if (isScanning) {
                // 현재 스캔 중이면 중지하고 5초 후에 다시 시작
                stopScan()
                handler.postDelayed({
                    startScan()
                }, 5000) // 5초 휴식
            }
            // 다음 주기를 15초 후에 실행 (10초 스캔 + 5초 휴식)
            handler.postDelayed(this, 15000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        when (intent.getBooleanExtra("login_success", false)) {
            true -> viewModel.initStatesWithLogin()

            else -> viewModel.initStates()
        }

        preferencesUtil = SharedPreferencesUtil(this)

        lifecycleScope.launch {
            viewModel.appThemeName.collectLatest { themeName ->
                applyTheme(themeName)
            }
        }

        lifecycleScope.launch {
            viewModel.attendanceUiEvent.collectLatest {
                if (it == AttendanceUiEvent.getBeacon) {
                    showStoreEventDialog()
                }
            }
        }

        setBottomNavigationBar()
        setNdef()
        setBeacon()
        createNotificationChannel("ssafy_channel", "ssafy")
        checkPermissions()

        handleIntent(intent)  // NFC 인텐트 처리
        initFCM()
    }

    override fun onResume() {
        super.onResume()
        // NFC 인텐트 수신 설정
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED))
        val techLists = arrayOf(arrayOf(Ndef::class.java.name))

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, techLists)
    }

    override fun onPause() {
        super.onPause()

        // NFC 인텐트 수신 해제
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        isScanning = false
        stopScan()
        handler.removeCallbacks(scanRunnable)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun setNdef() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                val msgs = rawMsgs.map { it as NdefMessage }
                for (msg in msgs) {
                    for (record in msg.records) {
                        val payload = record.payload
                        val textData = String(payload, 3, payload.size - 3) // 첫 3바이트는 메타데이터
                        val tableNumber = textData.split(":")[1].toInt()
                        viewModel.setTableNumber(tableNumber)
                        Toast.makeText(
                            this,
                            "${tableNumber}번 테이블 번호가 등록되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setBeacon() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.clear()
        beaconManager.beaconParsers.add(
            BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        )
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener {
                startScan()
            }
            checker.requestPermissionLauncher.launch(runtimePermissions)
        } else {
            startScan()
        }
    }

    private fun requestEnableBLE() {
        val callBLEEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestBLEActivity.launch(callBLEEnableIntent)
    }

    private val requestBLEActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (bluetoothAdapter.isEnabled) {
            startScan()
        }
    }

    private fun startScan() {
        if (!bluetoothAdapter.isEnabled) {
            requestEnableBLE()
            return
        }

        isScanning = true
        beaconManager.startMonitoring(region)
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.startRangingBeacons(region)

        // 첫 번째 주기 시작
        handler.postDelayed(scanRunnable, 10000) // 10초 후에 첫 스캔 중지
    }

    private val rangeNotifier = RangeNotifier { beacons, _ ->
        beacons?.forEach { beacon ->
            if (beacon.bluetoothAddress == BLUETOOTH_ADDRESS) {  // 특정 비콘만 감지
                if (beacon.distance <= BEACON_DISTANCE && shouldShowNotification()) {
                    runOnUiThread {
                        showStoreEventDialog()
                        Toast.makeText(
                            applicationContext,
                            "지정된 비콘 감지! 거리: ${String.format("%.2f", beacon.distance)}m",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    updateLastNotificationTime()
                    return@forEach
                }
            }
        }
    }

    private fun shouldShowNotification(): Boolean {
        val lastShownTime = preferencesUtil.getLastPopupShownTime()
        val currentTime = System.currentTimeMillis()

        viewModel.getAttendancesForBeacon()

        return (currentTime - lastShownTime) >= 24 * 60 * 60 * 1000 // 24시간
        // return (currentTime - lastShownTime) >= 30 * 1000 // 30초 (테스트용)
    }

    private fun updateLastNotificationTime() {
        preferencesUtil.setLastPopupShownTime(System.currentTimeMillis())
    }

    private fun showStoreEventDialog() {
        val dialog = StoreEventDialogFragment()
        dialog.show(supportFragmentManager, "StoreEventDialog")
    }

    private fun stopScan() {
        beaconManager.stopMonitoring(region)
        beaconManager.stopRangingBeacons(region)
        isScanning = false
    }

    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)

        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun checkPermissions() {
        // 권한 요청 관련 코드 추가
    }

    private fun setBottomNavigationBar() {
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph_main)
        with(binding.bnvMain) {
            setupWithNavController(navController)
            background = null
            menu.getItem(1).isEnabled = false
        }
        binding.fabOrder.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.fragment_order)
            binding.bnvMain.menu.findItem(R.id.fragment_home).isChecked = false
            binding.bnvMain.menu.findItem(R.id.fragment_my_page).isChecked = false
        }
    }

    @SuppressLint("ResourceAsColor")
    fun applyTheme(themeName: String) {
        when (themeName) {
            "봄" -> window.statusBarColor = resources.getColor(R.color.spring_secondary, theme)

            "여름" -> window.statusBarColor = resources.getColor(R.color.summer_secondary, theme)

            "가을" -> window.statusBarColor = resources.getColor(R.color.autumn_secondary, theme)

            "겨울" -> window.statusBarColor = resources.getColor(R.color.winter_secondary, theme)

            else -> window.statusBarColor = resources.getColor(R.color.main_end, theme)
        }
    }

    fun initFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w(task.exception, "FCM 등록 토큰 가져오기 실패")
                return@addOnCompleteListener
            }
            // 새로운 FCM 등록 토큰 획득
            val token = task.result
            Timber.d("FCM 토큰: $token")
            // 토큰을 Toast로 표시
            // Toast.makeText(this, "FCM 토큰: $token", Toast.LENGTH_LONG).show()
            // 토큰을 서버로 업로드
            if (token != null) {
                // 서버로 토큰을 업로드하는 로직을 여기에 추가하세요.
                // 예: uploadTokenToServer(token)
                viewModel.uploadFcmToken(token)
            }
        }
        // 알림 채널 생성
        createNotificationChannel("ssafy_channel", "ssafy")
    }

    companion object {

        private const val BEACON_UUID = "fda50693-a4e2-4fb1-afcf-c6eb07647825"
        private const val BEACON_MAJOR = "10004"
        private const val BEACON_MINOR = "54480"
        private const val BEACON_DISTANCE = 5.0
        private const val BLUETOOTH_ADDRESS = "00:81:F9:E2:49:E1"
    }
}
