package com.ssafy.smartstore_jetpack.presentation.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.OrderDetail
import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.domain.model.ShoppingCart
import com.ssafy.smartstore_jetpack.domain.model.Status
import com.ssafy.smartstore_jetpack.domain.model.UserInfo
import com.ssafy.smartstore_jetpack.domain.usecase.GetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCommentUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetOrderUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetProductUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserIdUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetUserIdUseCase
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.deleteComma
import com.ssafy.smartstore_jetpack.presentation.views.main.home.HomeClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.home.HomeUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.my.GradeState
import com.ssafy.smartstore_jetpack.presentation.views.main.my.MyPageClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.my.MyPageUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.my.MyPageUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.menudetail.CommentClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.EmptyState
import com.ssafy.smartstore_jetpack.presentation.views.main.menudetail.MenuDetailUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.order.OrderUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.order.ProductClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListUiState
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.deleteCommaWon
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeCommaWon
import com.ssafy.smartstore_jetpack.presentation.views.main.coupon.CouponUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.history.HistoryUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.home.EventUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.information.InformationClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.information.InformationUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.setting.SettingClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.setting.SettingUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCommentUseCase: GetCommentUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,
    private val setCookieUseCase: SetCookieUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase
) : ViewModel(), HomeClickListener, MyPageClickListener, ProductClickListener, CommentClickListener,
    ShoppingListClickListener, SettingClickListener, InformationClickListener {

    /****** Data ******/
    private val _events = MutableStateFlow<List<EventUiState>>(emptyList())
    val events = _events.asStateFlow()

    private val _user = MutableStateFlow<UserInfo?>(null)
    val user = _user.asStateFlow()

    private val _ordersMonth = MutableStateFlow<List<Order>>(emptyList())
    val ordersMonth = _ordersMonth.asStateFlow()

    private val _orders6Months = MutableStateFlow<List<Order>>(emptyList())
    val orders6Months = _orders6Months.asStateFlow()

    private val _shoppingList = MutableStateFlow<List<ShoppingCart>>(emptyList())
    val shoppingList = _shoppingList.asStateFlow()

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder = _selectedOrder.asStateFlow()

    private val _selectedOrderDetails = MutableStateFlow<List<OrderDetail>>(emptyList())
    val selectedOrderDetails = _selectedOrderDetails.asStateFlow()

    private val _products = MutableStateFlow<List<List<Product>>>(emptyList())
    val products = _products.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _selectedProductPrice = MutableStateFlow<String>("0")
    val selectedProductPrice = _selectedProductPrice.asStateFlow()

    private val _selectProductCount = MutableStateFlow<String>("1")
    val selectProductCount = _selectProductCount.asStateFlow()

    private val _selectProductRating = MutableStateFlow<Float>(5F)
    val selectProductRating = _selectProductRating.asStateFlow()

    private val _nowComment = MutableStateFlow<String>("")
    val nowComment = _nowComment

    private val _nfcMode = MutableStateFlow<Boolean>(true)
    val nfcMode = _nfcMode.asStateFlow()

    private val _tableNumber = MutableStateFlow<Int>(0)
    val tableNumber = _tableNumber.asStateFlow()

    private val _totalOrder = MutableStateFlow<String>("0")
    val totalOrder = _totalOrder.asStateFlow()

    private val _totalPrice = MutableStateFlow<String>("0원")
    val totalPrice = _totalPrice.asStateFlow()

    /****** Ui State ******/
    private val _bnvState = MutableStateFlow<Boolean>(true)
    val bnvState = _bnvState.asStateFlow()

    private val _fabState = MutableStateFlow<Boolean>(false)
    val fabState = _fabState.asStateFlow()

    private val _appThemeName = MutableStateFlow<String>("기본")
    val appThemeName = _appThemeName.asStateFlow()

    private val _nowEventIndex = MutableStateFlow<Int>(1)
    val nowEventIndex = _nowEventIndex.asStateFlow()

    private val _isPushReceiving = MutableStateFlow<Boolean>(false)
    val isPushReceiving = _isPushReceiving.asStateFlow()

    private val _shoppingListUiState = MutableStateFlow<ShoppingListUiState>(ShoppingListUiState())
    val shoppingListUiState = _shoppingListUiState.asStateFlow()

    private val _myPageUiState = MutableStateFlow<MyPageUiState>(MyPageUiState())
    val myPageUiState = _myPageUiState.asStateFlow()

    private val _couponUiState = MutableStateFlow<CouponUiState>(CouponUiState())
    val couponUiState = _couponUiState.asStateFlow()

    /****** Ui Event ******/
    private val _homeUiEvent = MutableSharedFlow<HomeUiEvent>()
    val homeUiEvent = _homeUiEvent.asSharedFlow()

    private val _myPageUiEvent = MutableSharedFlow<MyPageUiEvent>()
    val myPageUiEvent = _myPageUiEvent.asSharedFlow()

    private val _orderUiEvent = MutableSharedFlow<OrderUiEvent>()
    val orderUiEvent = _orderUiEvent.asSharedFlow()

    private val _menuDetailUiEvent = MutableSharedFlow<MenuDetailUiEvent>()
    val menuDetailUiEvent = _menuDetailUiEvent.asSharedFlow()

    private val _shoppingUiEvent = MutableSharedFlow<ShoppingListUiEvent>()
    val shoppingUiEvent = _shoppingUiEvent.asSharedFlow()

    private val _settingUiEvent = MutableSharedFlow<SettingUiEvent>()
    val settingUiEvent = _settingUiEvent.asSharedFlow()

    private val _historyUiEvent = MutableSharedFlow<HistoryUiEvent>()
    val historyUiEvent = _historyUiEvent.asSharedFlow()

    private val _informationUiEvent = MutableSharedFlow<InformationUiEvent>()
    val informationUiEvent = _informationUiEvent.asSharedFlow()

    init {
        setEvents()
        setTheme()
        getUser()
        getLastMonthOrders()
        getLast6MonthsOrders()
        getProducts()
    }

    override fun onClickOrderDetail(order: Order) {
        viewModelScope.launch {
            _shoppingList.value = _shoppingList.value.toMutableList().apply {
                order.details.forEach { detail ->
                    var index = -1
                    _shoppingList.value.forEachIndexed { i, cart ->
                        if (cart.menuId == detail.productId) {
                            index = i
                        }
                    }

                    when (index) {
                        -1 -> {
                            _totalOrder.value = (_totalOrder.value.toInt() + detail.quantity).toString()
                            _totalPrice.value = makeComma((deleteCommaWon(_totalPrice.value) + (detail.quantity * detail.unitPrice.toInt())))
                            add(
                                ShoppingCart(
                                    menuId = detail.productId,
                                    menuImg = detail.img,
                                    menuName = detail.productName,
                                    menuCnt = detail.quantity.toString(),
                                    menuPrice = detail.unitPrice,
                                    totalPrice = (detail.unitPrice.toInt() * detail.quantity).toString(),
                                    type = ""
                                )
                            )
                        }

                        else -> {
                            val newCount = this[index].menuCnt.toInt() + detail.quantity
                            _totalOrder.value = (_totalOrder.value.toInt() + detail.quantity).toString()
                            _totalPrice.value = makeComma((deleteCommaWon(_totalPrice.value) + (detail.quantity * detail.unitPrice.toInt())))
                            this[index] = ShoppingCart(
                                menuId = detail.productId,
                                menuImg = detail.img,
                                menuName = detail.productName,
                                menuCnt = newCount.toString(),
                                menuPrice = detail.unitPrice,
                                totalPrice = (newCount * detail.unitPrice.toInt()).toString(),
                                type = ""
                            )
                        }
                    }
                }
            }.toList()
            validateShoppingList()
            _homeUiEvent.emit(HomeUiEvent.GoToOrderDetail)
        }
    }

    override fun onClickOrder(order: Order) {
        viewModelScope.launch {
            _selectedOrder.value = order
            _selectedOrderDetails.value = order.details
            _historyUiEvent.emit(HistoryUiEvent.GoToOrderDetail)
        }
    }

    override fun onClickLogout() {
        viewModelScope.launch {
            setUserIdUseCase.setUserId("")
            setCookieUseCase.deleteLoginCookie()
            _myPageUiEvent.emit(MyPageUiEvent.DoLogout)
        }
    }

    override fun onClickProduct(product: Product) {
        _selectedProduct.value = null
        viewModelScope.launch {
            val response = getProductUseCase.getProductWithComment(product.id)
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        _selectedProduct.value = it
                    }
                }

                else -> {}
            }
        }
    }

    override fun onClickProductToCart() {
        viewModelScope.launch {
            _shoppingList.value = _shoppingList.value.toMutableList().apply {
                _selectedProduct.value?.let {
                    var index = -1
                    _shoppingList.value.forEachIndexed { i, cart ->
                        if (cart.menuId == it.id) {
                            index = i
                        }
                    }
                    when (index) {
                        -1 -> {
                            _totalOrder.value = (_totalOrder.value.toInt() + _selectProductCount.value.toInt()).toString()
                            _totalPrice.value = makeCommaWon((deleteComma(_totalPrice.value) + (deleteComma(_selectProductCount.value) * deleteComma(it.price))))
                            add(
                                ShoppingCart(
                                    menuId = it.id,
                                    menuImg = it.img,
                                    menuName = it.name,
                                    menuCnt = _selectProductCount.value,
                                    menuPrice = it.price,
                                    totalPrice = (_selectProductCount.value.toInt() * deleteComma(it.price)).toString(),
                                    type = it.type
                                )
                            )
                        }

                        else -> {
                            val newCount = this[index].menuCnt.toInt() + _selectProductCount.value.toInt()
                            _totalOrder.value = (_totalOrder.value.toInt() + _selectProductCount.value.toInt()).toString()
                            _totalPrice.value = makeComma((deleteComma(_totalPrice.value) + (deleteComma(_selectProductCount.value) * deleteComma(it.price))))
                            this[index] = ShoppingCart(
                                menuId = it.id,
                                menuImg = it.img,
                                menuName = it.name,
                                menuCnt = newCount.toString(),
                                menuPrice = it.price,
                                totalPrice = (newCount * deleteComma(it.price)).toString(),
                                type = it.type
                            )
                        }
                    }
                }
            }.toList()
            validateShoppingList()
            _selectProductCount.value = "1"
            _menuDetailUiEvent.emit(MenuDetailUiEvent.SelectProduct)
        }
    }

    override fun onClickProductCountUp() {
        if (_selectProductCount.value.toInt() < 99) {
            _selectProductCount.value = (_selectProductCount.value.toInt() + 1).toString()
        }
    }

    override fun onClickProductCountDown() {
        if (_selectProductCount.value.toInt() > 1) {
            _selectProductCount.value = (_selectProductCount.value.toInt() - 1).toString()
        }
    }

    override fun onClickSelectRating() {
        viewModelScope.launch {
            _menuDetailUiEvent.emit(MenuDetailUiEvent.SelectRating)
        }
    }

    override fun onClickInsertComment() {
        viewModelScope.launch {
            _selectedProduct.value?.let { product ->
                _user.value?.let {
                    getCommentUseCase.postComment(
                        Comment(
                            id = -1,
                            userId = it.user.id,
                            productId = product.id,
                            rating = _selectProductRating.value,
                            comment = _nowComment.value,
                            userName = it.user.name
                        )
                    )
                    _selectProductRating.value = 5F
                    _nowComment.value = ""
                    val response = getProductUseCase.getProductWithComment(product.id)
                    when (response.status) {
                        Status.SUCCESS -> {
                            _selectedProduct.value = response.data
                        }

                        else -> {}
                    }
                    _menuDetailUiEvent.emit(MenuDetailUiEvent.SubmitComment)
                }
            }
        }
    }

    override fun onClickCommentCancel() {
        viewModelScope.launch {
            _menuDetailUiEvent.emit(MenuDetailUiEvent.SubmitComment)
        }
    }

    override fun onClickUpdateComment(comment: Comment) {
        viewModelScope.launch {
            val response = getCommentUseCase.putComment(comment)

            when (response.status) {
                Status.SUCCESS -> {
                    val response2 = getProductUseCase.getProductWithComment(comment.productId)
                    when (response2.status) {
                        Status.SUCCESS -> {
                            _selectedProduct.value = response2.data
                            _menuDetailUiEvent.emit(MenuDetailUiEvent.UpdateComment)
                        }

                        else -> {
                            _menuDetailUiEvent.emit(MenuDetailUiEvent.UpdateCommentFail)
                        }
                    }
                }

                else -> {
                    _menuDetailUiEvent.emit(MenuDetailUiEvent.UpdateCommentFail)
                }
            }
        }
    }

    override fun onClickDeleteComment(comment: Comment) {
        viewModelScope.launch {
            val response = getCommentUseCase.deleteComment(comment.id)

            when (response.status) {
                Status.SUCCESS -> {
                    val response2 = getProductUseCase.getProductWithComment(comment.productId)
                    when (response2.status) {
                        Status.SUCCESS -> {
                            _selectedProduct.value = response2.data
                            _menuDetailUiEvent.emit(MenuDetailUiEvent.DeleteComment)
                        }

                        else -> {
                            _menuDetailUiEvent.emit(MenuDetailUiEvent.DeleteCommentFail)
                        }
                    }
                }

                else -> {
                    _menuDetailUiEvent.emit(MenuDetailUiEvent.DeleteCommentFail)
                }
            }
        }
    }

    override fun onClickToCart() {
        viewModelScope.launch {
            _orderUiEvent.emit(OrderUiEvent.GoToCart)
        }
    }

    override fun onClickProductDelete(position: Int) {
        _shoppingList.value = _shoppingList.value.toMutableList().apply {
            removeAt(position)
        }.toList()
        validateShoppingList()

        _totalOrder.value = (_shoppingList.value.sumOf { it.menuCnt.toInt() }).toString()
        _totalPrice.value = makeComma((_shoppingList.value.sumOf { it.totalPrice.toInt() }))
    }

    override fun onClickNFCTrue() {
        _nfcMode.value = true
    }

    override fun onClickNFCFalse() {
        _nfcMode.value = false
    }

    override fun onClickOrder() {
        viewModelScope.launch {
            when (_nfcMode.value) {
                true -> {
                    _shoppingUiEvent.emit(ShoppingListUiEvent.ShopOrder)
                }

                else -> {
                    _shoppingUiEvent.emit(ShoppingListUiEvent.TakeOutOrder)
                }
            }
        }
    }

    override fun onClickTakeOutFinish() {
        viewModelScope.launch {
            val newDetails = mutableListOf<OrderDetail>()
            _shoppingList.value.forEach { product ->
                newDetails.add(
                    OrderDetail(
                        id = 0,
                        orderId = 0,
                        productId = product.menuId,
                        quantity = product.menuCnt.toInt(),
                        unitPrice = "",
                        img = "",
                        productName = ""
                    )
                )
            }

            val newOrder = Order(
                id = 0,
                userId = getUserId().first(),
                orderTable = _tableNumber.value.toString(),
                orderTime = "",
                completed = "N",
                details = newDetails.toList()
            )

            val response = getOrderUseCase.makeOrder(newOrder)

            when (response.status) {
                Status.SUCCESS -> {
                    _shoppingList.value = emptyList()
                    validateShoppingList()
                    _shoppingUiEvent.emit(ShoppingListUiEvent.FinishOrder)
                    _totalOrder.value = "0"
                    _totalPrice.value = "0원"
                    getUser()
                    getLastMonthOrders()
                    getLast6MonthsOrders()
                    getProducts()
                }

                else -> {
                    _shoppingUiEvent.emit(ShoppingListUiEvent.OrderFail)
                }
            }
        }
    }

    override fun onClickSettings() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToSettings)
        }
    }

    override fun onClickHistory() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToHistory)
        }
    }

    override fun onClickInformation() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToInformation)
        }
    }

    override fun onClickCoupon() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToCoupon)
        }
    }

    override fun onClickPay() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToPay)
        }
    }

    override fun onClickPassword() {
        viewModelScope.launch {
            _informationUiEvent.emit(InformationUiEvent.GoToPassword)
        }
    }

    override fun onClickTheme() {
        viewModelScope.launch {
            _settingUiEvent.emit(SettingUiEvent.SelectAppTheme)
        }
    }

    override fun onClickPrimaryTheme() {
        viewModelScope.launch {
            _appThemeName.value = "기본"
            setAppThemeUseCase.setAppTheme("기본")
            _settingUiEvent.emit(SettingUiEvent.SubmitAppTheme("기본"))
        }
    }

    override fun onClickSpringTheme() {
        viewModelScope.launch {
            _appThemeName.value = "봄"
            setAppThemeUseCase.setAppTheme("봄")
            _settingUiEvent.emit(SettingUiEvent.SubmitAppTheme("봄"))
        }
    }

    override fun onClickSummerTheme() {
        viewModelScope.launch {
            _appThemeName.value = "여름"
            setAppThemeUseCase.setAppTheme("여름")
            _settingUiEvent.emit(SettingUiEvent.SubmitAppTheme("여름"))
        }
    }

    override fun onClickAutumnTheme() {
        viewModelScope.launch {
            _appThemeName.value = "가을"
            setAppThemeUseCase.setAppTheme("가을")
            _settingUiEvent.emit(SettingUiEvent.SubmitAppTheme("가을"))
        }
    }

    override fun onClickWinterTheme() {
        viewModelScope.launch {
            _appThemeName.value = "겨울"
            setAppThemeUseCase.setAppTheme("겨울")
            _settingUiEvent.emit(SettingUiEvent.SubmitAppTheme("겨울"))
        }
    }

    override fun onClickCloseTheme() {
        viewModelScope.launch {
            _settingUiEvent.emit(SettingUiEvent.CloseAppTheme)
        }
    }

    private fun setTheme() {
        viewModelScope.launch {
            val appTheme = getAppTheme().first()
            _appThemeName.value = appTheme
            Timber.d("App Theme: ${_appThemeName.value}")
        }
    }

    private fun setEvents() {
        val newEvents = mutableListOf<EventUiState>()
        newEvents.add(EventUiState.EventItem("https://appservice-img.s3.amazonaws.com/apps/0zdpAngaKBFnlCcCqpCU4A/ko/list/image?1621844405"))
        newEvents.add(EventUiState.EventItem("https://dcenter-img.cafe24.com/d/product/2022/03/10/306c857f338c411e1bdbd90728d4fce5.png"))
        newEvents.add(EventUiState.EventItem("https://appservice-img.s3.amazonaws.com/apps/jsISJfide0GLiUyXsznbgP/ko/list/image"))
        newEvents.add(EventUiState.EventItem("https://appservice-img.s3.amazonaws.com/apps/0zdpAngaKBFnlCcCqpCU4A/ko/list/image?1621844405"))
        newEvents.add(EventUiState.EventItem("https://dcenter-img.cafe24.com/d/product/2022/03/10/306c857f338c411e1bdbd90728d4fce5.png"))
        _events.value = newEvents.toList()
    }

    private fun getUser() {
        viewModelScope.launch {
            val response =
                getUserUseCase.getUserInfo(getUserId().first())
            when (response.status) {
                Status.SUCCESS -> {
                    _user.value = response.data
                    validateGrade()
                    Timber.d("User: ${_user.value}")
                }

                else -> {
                    Timber.d("${response.data}")
                }
            }
        }
    }

    private fun validateGrade() {
        when (_user.value?.grade?.title) {
            "커피나무" -> {
                _myPageUiState.update { it.copy(gradeState = GradeState.TREE) }
            }

            else -> {
                _myPageUiState.update { it.copy(gradeState = GradeState.NONE) }
            }
        }
    }

    private fun getLastMonthOrders() {
        viewModelScope.launch {
            val response = getOrderUseCase.getLastMonthOrders(getUserId().first())

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { orders ->
                        _ordersMonth.value = orders
                        Timber.d("Orders: ${_ordersMonth.value}")
                    }
                }

                else -> {
                    Timber.d("Order Fail")
                }
            }
        }
    }

    private fun getLast6MonthsOrders() {
        viewModelScope.launch {
            val response = getOrderUseCase.getLast6MonthOrders(getUserId().first())

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { orders ->
                        _orders6Months.value = orders
                    }
                }

                else -> {}
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            val response = getProductUseCase.getProducts()

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { products ->
                        val newProducts = mutableListOf<List<Product>>()
                        newProducts.add(products)
                        newProducts.add(emptyList())
                        _products.value = newProducts.toList()
                    }
                }

                else -> {}
            }
        }
    }

    private fun validateShoppingList() {
        when (_shoppingList.value.isEmpty()) {
            true -> {
                _shoppingListUiState.update { it.copy(shoppingListState = EmptyState.EMPTY) }
            }

            else -> {
                _shoppingListUiState.update { it.copy(shoppingListState = EmptyState.NONE) }
            }
        }
    }

    private suspend fun getUserId(): Flow<String> = flow {
        val userId = getUserIdUseCase.getUserId().first()
        emit(userId)
    }

    private suspend fun getAppTheme(): Flow<String> = flow {
        val appTheme =  getAppThemeUseCase.getAppTheme().first()
        emit(appTheme)
    }

    fun setFabState(flag: Boolean) {
        _fabState.value = flag
        Timber.d("$flag")
    }

    fun setBnvState(flag: Boolean) {
        _bnvState.value = flag
    }

    fun setEventsIndex(index: Int) {
        _nowEventIndex.value = index
    }

    fun setPushReceiving(isPushReceiving: Boolean) {
        viewModelScope.launch {
            _isPushReceiving.value = isPushReceiving
        }
    }

    fun setTableNumber(number: Int) {
        viewModelScope.launch {
            _tableNumber.value = number
            _shoppingUiEvent.emit(ShoppingListUiEvent.Tagged)
            val newDetails = mutableListOf<OrderDetail>()
            _shoppingList.value.forEach { product ->
                newDetails.add(
                    OrderDetail(
                        id = 0,
                        orderId = 0,
                        productId = product.menuId,
                        quantity = product.menuCnt.toInt(),
                        unitPrice = "",
                        img = "",
                        productName = ""
                    )
                )
            }

            val newOrder = Order(
                id = 0,
                userId = getUserId().first(),
                orderTable = _tableNumber.value.toString(),
                orderTime = "",
                completed = "N",
                details = newDetails.toList()
            )

            val response = getOrderUseCase.makeOrder(newOrder)

            when (response.status) {
                Status.SUCCESS -> {
                    _shoppingList.value = emptyList()
                    validateShoppingList()
                    _shoppingUiEvent.emit(ShoppingListUiEvent.FinishOrder)
                    _totalOrder.value = "0"
                    _totalPrice.value = "0원"
                    getUser()
                    getLastMonthOrders()
                    getLast6MonthsOrders()
                    getProducts()
                }

                else -> {
                    _shoppingUiEvent.emit(ShoppingListUiEvent.OrderFail)
                }
            }
        }
    }
}