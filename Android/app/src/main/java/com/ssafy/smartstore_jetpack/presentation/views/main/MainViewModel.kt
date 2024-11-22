package com.ssafy.smartstore_jetpack.presentation.views.main

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Coupon
import com.ssafy.smartstore_jetpack.domain.model.Event
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.OrderDetail
import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.domain.model.Shop
import com.ssafy.smartstore_jetpack.domain.model.ShoppingCart
import com.ssafy.smartstore_jetpack.domain.model.Stamp
import com.ssafy.smartstore_jetpack.domain.model.Status
import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.model.UserInfo
import com.ssafy.smartstore_jetpack.domain.usecase.AddFcmTokenUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCommentUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCouponUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetEventUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetNoticesUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetOrderUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetProductUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetShopUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserIdUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetNoticesUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetUserIdUseCase
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.deleteComma
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeCommaWon
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.validateId
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.validatePassword
import com.ssafy.smartstore_jetpack.presentation.util.DuplicateState
import com.ssafy.smartstore_jetpack.presentation.util.EmptyState
import com.ssafy.smartstore_jetpack.presentation.util.InputValidState
import com.ssafy.smartstore_jetpack.presentation.util.PasswordState
import com.ssafy.smartstore_jetpack.presentation.util.SelectState
import com.ssafy.smartstore_jetpack.presentation.util.ShopSelectValidState
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShopSelectUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.coupon.CouponClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.coupon.CouponUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.coupon.CouponUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail.CouponDetailClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail.CouponDetailUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.history.HistoryUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.home.HomeClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.home.HomeUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.information.InformationClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.information.InformationUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.join.JoinClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.join.JoinUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.join.JoinUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.login.LoginClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.login.LoginUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.login.LoginUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.menudetail.CommentClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.menudetail.MenuDetailUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.my.GradeState
import com.ssafy.smartstore_jetpack.presentation.views.main.my.MyPageClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.my.MyPageUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.my.MyPageUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.notice.NoticeClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.notice.NoticeUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.notice.NoticeUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.order.OrderUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.order.ProductClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.password.PasswordClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.password.PasswordUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.password.PasswordUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.setting.SettingClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.setting.SettingUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCommentUseCase: GetCommentUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getShopUseCase: GetShopUseCase,
    private val getEventUseCase: GetEventUseCase,
    private val getCouponUseCase: GetCouponUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,
    private val getCookieUseCase: GetCookieUseCase,
    private val setCookieUseCase: SetCookieUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
    private val getNoticesUseCase: GetNoticesUseCase,
    private val setNoticesUseCase: SetNoticesUseCase,
    // 추가된 UseCase
    private val addFcmTokenUseCase: AddFcmTokenUseCase

) : ViewModel(), HomeClickListener, LoginClickListener, JoinClickListener, MyPageClickListener,
    ProductClickListener, CommentClickListener, ShoppingListClickListener, SettingClickListener,
    InformationClickListener, PasswordClickListener, CouponClickListener,
    CouponDetailClickListener, NoticeClickListener {

    /****** Edit Text ******/
    /*** Login ***/
    private val _userId = MutableStateFlow<String>("")
    val userId = _userId

    private val _userPass = MutableStateFlow<String>("")
    val userPass = _userPass

    /*** Join ***/
    private val _joinId = MutableStateFlow<String>("")
    val joinId = _joinId

    private val _joinPass = MutableStateFlow<String>("")
    val joinPass = _joinPass

    private val _joinPassConfirm = MutableStateFlow<String>("")
    val joinPassConfirm = _joinPassConfirm

    private val _joinName = MutableStateFlow<String>("")
    val joinName = _joinName

    /*** Comment ***/
    private val _nowComment = MutableStateFlow<String>("")
    val nowComment = _nowComment

    /*** Password Update ***/
    private val _newPassword = MutableStateFlow<String>("")
    val newPassword = _newPassword

    private val _newPasswordConfirm = MutableStateFlow<String>("")
    val newPasswordConfirm = _newPasswordConfirm

    /*** Shop Search ***/
    private val _shopSearchKeyword = MutableStateFlow<String>("")
    val shopSearchKeyword = _shopSearchKeyword

    /****** Data ******/
    /*** Init ***/
    private val _user = MutableStateFlow<UserInfo?>(null)
    val user = _user.asStateFlow()

    private val _products = MutableStateFlow<List<List<Product>>>(emptyList())
    val products = _products.asStateFlow()

    private val _ordersMonth = MutableStateFlow<List<Order>>(emptyList())
    val ordersMonth = _ordersMonth.asStateFlow()

    private val _orders6Months = MutableStateFlow<List<Order>>(emptyList())
    val orders6Months = _orders6Months.asStateFlow()

    private val _shops = MutableStateFlow<List<Shop>>(emptyList())
    val shops = _shops.asStateFlow()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    private val _coupons = MutableStateFlow<List<Coupon>>(emptyList())
    val coupons = _coupons.asStateFlow()

    private val _notices = MutableStateFlow<List<String>>(emptyList())
    val notices = _notices.asStateFlow()

    /*** Coupon ***/
    private val _selectedCoupon = MutableStateFlow<Coupon?>(null)
    val selectedCoupon = _selectedCoupon.asStateFlow()

    private val _selectedCouponMenu = MutableStateFlow<ShoppingCart?>(null)
    val selectedCouponMenu = _selectedCouponMenu.asStateFlow()

    /*** Cart ***/
    private val _shoppingList = MutableStateFlow<List<ShoppingCart>>(emptyList())
    val shoppingList = _shoppingList.asStateFlow()

    private val _totalOrder = MutableStateFlow<String>("0")
    val totalOrder = _totalOrder.asStateFlow()

    private val _totalPrice = MutableStateFlow<String>("0원")
    val totalPrice = _totalPrice.asStateFlow()

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder = _selectedOrder.asStateFlow()

    private val _selectedOrderDetails = MutableStateFlow<List<OrderDetail>>(emptyList())
    val selectedOrderDetails = _selectedOrderDetails.asStateFlow()

    /*** Product Detail ***/
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _selectedProductPrice = MutableStateFlow<String>("0")
    val selectedProductPrice = _selectedProductPrice.asStateFlow()

    private val _selectProductCount = MutableStateFlow<String>("1")
    val selectProductCount = _selectProductCount.asStateFlow()

    private val _selectProductRating = MutableStateFlow<Float>(5F)
    val selectProductRating = _selectProductRating.asStateFlow()

    /*** Shop ***/
    private val _searchedShops = MutableStateFlow<List<Shop>>(emptyList())
    val searchedShops = _searchedShops.asStateFlow()

    private val _selectShop = MutableStateFlow<Shop?>(null)
    val selectShop = _selectShop.asStateFlow()

    /****** Ui State ******/
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _joinUiState = MutableStateFlow<JoinUiState>(JoinUiState())
    val joinUiState = _joinUiState.asStateFlow()

    private val _noticeUiState = MutableStateFlow<NoticeUiState>(NoticeUiState())
    val noticeUiState = _noticeUiState.asStateFlow()

    private val _shoppingListUiState = MutableStateFlow<ShoppingListUiState>(ShoppingListUiState())
    val shoppingListUiState = _shoppingListUiState.asStateFlow()

    private val _myPageUiState = MutableStateFlow<MyPageUiState>(MyPageUiState())
    val myPageUiState = _myPageUiState.asStateFlow()

    private val _couponUiState = MutableStateFlow<CouponUiState>(CouponUiState())
    val couponUiState = _couponUiState.asStateFlow()

    private val _passwordUiState = MutableStateFlow<PasswordUiState>(PasswordUiState())
    val passwordUiState = _passwordUiState.asStateFlow()

    private val _shopSelectUiState = MutableStateFlow<ShopSelectUiState>(ShopSelectUiState())
    val shopSelectUiState = _shopSelectUiState.asStateFlow()

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

    private val _isMapMode = MutableStateFlow<Boolean>(false)
    val isMapMode = _isMapMode.asStateFlow()

    private val _nfcMode = MutableStateFlow<Boolean>(true)
    val nfcMode = _nfcMode.asStateFlow()

    private val _tableNumber = MutableStateFlow<Int>(0)
    val tableNumber = _tableNumber.asStateFlow()

    /****** Ui Event ******/
    private val _homeUiEvent = MutableSharedFlow<HomeUiEvent>()
    val homeUiEvent = _homeUiEvent.asSharedFlow()

    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent>()
    val loginUiEvent = _loginUiEvent.asSharedFlow()

    private val _joinUiEvent = MutableSharedFlow<JoinUiEvent>()
    val joinUiEvent = _joinUiEvent.asSharedFlow()

    private val _noticeUiEvent = MutableSharedFlow<NoticeUiEvent>()
    val noticeUiEvent = _noticeUiEvent.asSharedFlow()

    private val _orderUiEvent = MutableSharedFlow<OrderUiEvent>()
    val orderUiEvent = _orderUiEvent.asSharedFlow()

    private val _menuDetailUiEvent = MutableSharedFlow<MenuDetailUiEvent>()
    val menuDetailUiEvent = _menuDetailUiEvent.asSharedFlow()

    private val _shoppingUiEvent = MutableSharedFlow<ShoppingListUiEvent>()
    val shoppingUiEvent = _shoppingUiEvent.asSharedFlow()

    private val _myPageUiEvent = MutableSharedFlow<MyPageUiEvent>()
    val myPageUiEvent = _myPageUiEvent.asSharedFlow()

    private val _settingUiEvent = MutableSharedFlow<SettingUiEvent>()
    val settingUiEvent = _settingUiEvent.asSharedFlow()

    private val _passwordUiEvent = MutableSharedFlow<PasswordUiEvent>()
    val passwordUiEvent = _passwordUiEvent.asSharedFlow()

    private val _historyUiEvent = MutableSharedFlow<HistoryUiEvent>()
    val historyUiEvent = _historyUiEvent.asSharedFlow()

    private val _informationUiEvent = MutableSharedFlow<InformationUiEvent>()
    val informationUiEvent = _informationUiEvent.asSharedFlow()

    private val _couponUiEvent = MutableSharedFlow<CouponUiEvent>()
    val couponUiEvent = _couponUiEvent.asSharedFlow()

    private val _couponDetailUiEvent = MutableSharedFlow<CouponDetailUiEvent>()
    val couponDetailUiEvent = _couponDetailUiEvent.asSharedFlow()

    init {
        setEvents()
    }

    /*** Home Click ***/
    override fun onClickHomeSignUp() {
        viewModelScope.launch {
            _homeUiEvent.emit(HomeUiEvent.GoToJoin)
        }
    }

    override fun onClickHomeLogin() {
        viewModelScope.launch {
            _homeUiEvent.emit(HomeUiEvent.GoToLogin)
        }
    }

    override fun onClickNotice() {
        viewModelScope.launch {
            _notices.value = getNotices().first()
            _homeUiEvent.emit(HomeUiEvent.GoToNotice)
        }
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
                            _totalOrder.value =
                                (_totalOrder.value.toInt() + detail.quantity).toString()
                            _totalPrice.value = makeComma(
                                (deleteComma(_totalPrice.value) + (detail.quantity * deleteComma(
                                    detail.unitPrice
                                )))
                            )
                            add(
                                ShoppingCart(
                                    menuId = detail.productId,
                                    menuImg = detail.img,
                                    menuName = detail.productName,
                                    menuCnt = detail.quantity.toString(),
                                    menuPrice = detail.unitPrice,
                                    totalPrice = (deleteComma(detail.unitPrice) * detail.quantity).toString(),
                                    type = ""
                                )
                            )
                        }

                        else -> {
                            val newCount = this[index].menuCnt.toInt() + detail.quantity
                            _totalOrder.value =
                                (_totalOrder.value.toInt() + detail.quantity).toString()
                            _totalPrice.value = makeComma(
                                (deleteComma(_totalPrice.value) + (detail.quantity * deleteComma(
                                    detail.unitPrice
                                )))
                            )
                            this[index] = ShoppingCart(
                                menuId = detail.productId,
                                menuImg = detail.img,
                                menuName = detail.productName,
                                menuCnt = newCount.toString(),
                                menuPrice = detail.unitPrice,
                                totalPrice = (newCount * deleteComma(detail.unitPrice)).toString(),
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

    /*** Login Click ***/
    override fun onClickLogin() {
        login()
    }

    override fun onClickJoin() {
        viewModelScope.launch {
            _loginUiEvent.emit(LoginUiEvent.GoToJoin)
        }
    }

    /*** Join Click ***/
    override fun onClickCheckId() {
        validateJoinIdDuplicate()
    }

    override fun onClickGoToJoin() {
        join()
    }

    /*** Notice Click ***/
    override fun onClickNoticeDelete(position: Int) {
        viewModelScope.launch {
            val newNotices = _notices.value.toMutableList()
            newNotices.removeAt(position)
            _notices.value = newNotices.toList()
            _noticeUiEvent.emit(NoticeUiEvent.DeleteNotice)
        }
    }

    /*** Order Click ***/
    override fun onClickProduct(product: Product) {
        _selectedProduct.value = null
        _selectedProductPrice.value = ""
        viewModelScope.launch {
            val response = getProductUseCase.getProductWithComment(product.id)
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        _selectedProduct.value = it
                        _selectedProductPrice.value = it.price
                    }
                }

                else -> {}
            }
        }
    }

    override fun onClickToCart() {
        viewModelScope.launch {
            _orderUiEvent.emit(OrderUiEvent.GoToCart)
        }
    }

    /*** Menu Detail Click ***/
    override fun onClickProductCountUp() {
        if (_selectProductCount.value.toInt() < 99) {
            _selectProductCount.value = (_selectProductCount.value.toInt() + 1).toString()
            _selectedProduct.value?.let {
                _selectedProductPrice.value =
                    makeComma(_selectProductCount.value.toInt() * deleteComma(it.price))
            }
        }
    }

    override fun onClickProductCountDown() {
        if (_selectProductCount.value.toInt() > 1) {
            _selectProductCount.value = (_selectProductCount.value.toInt() - 1).toString()
            _selectedProduct.value?.let {
                _selectedProductPrice.value =
                    makeComma(_selectProductCount.value.toInt() * deleteComma(it.price))
            }
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
                            _totalOrder.value =
                                (_totalOrder.value.toInt() + _selectProductCount.value.toInt()).toString()
                            _totalPrice.value = makeCommaWon(
                                (deleteComma(_totalPrice.value) + (deleteComma(_selectProductCount.value) * deleteComma(
                                    it.price
                                )))
                            )
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
                            val newCount =
                                this[index].menuCnt.toInt() + _selectProductCount.value.toInt()
                            _totalOrder.value =
                                (_totalOrder.value.toInt() + _selectProductCount.value.toInt()).toString()
                            _totalPrice.value = makeComma(
                                (deleteComma(_totalPrice.value) + (deleteComma(_selectProductCount.value) * deleteComma(
                                    it.price
                                )))
                            )
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

    /*** Cart Click ***/
    override fun onClickProductAdd(position: Int) {
        val newShoppingList = mutableListOf<ShoppingCart>()

        _shoppingList.value.forEachIndexed { index, shoppingCart ->
            when (position == index) {
                true -> {
                    newShoppingList.add(
                        ShoppingCart(
                            menuId = shoppingCart.menuId,
                            menuImg = shoppingCart.menuImg,
                            menuName = shoppingCart.menuName,
                            menuCnt = (shoppingCart.menuCnt.toInt() + 1).toString(),
                            menuPrice = shoppingCart.menuPrice,
                            totalPrice = (deleteComma(shoppingCart.menuPrice) * (shoppingCart.menuCnt.toInt() + 1)).toString(),
                            type = shoppingCart.type
                        )
                    )
                }

                else -> {
                    newShoppingList.add(shoppingCart)
                }
            }
        }

        _shoppingList.value = newShoppingList.toList()
        _totalOrder.value = (_shoppingList.value.sumOf { it.menuCnt.toInt() }).toString()
        _totalPrice.value = makeComma((_shoppingList.value.sumOf { deleteComma(it.totalPrice) }))
    }

    override fun onClickProductRemove(position: Int) {
        val newShoppingList = mutableListOf<ShoppingCart>()

        _shoppingList.value.forEachIndexed { index, shoppingCart ->
            when (position == index) {
                true -> {
                    newShoppingList.add(
                        ShoppingCart(
                            menuId = shoppingCart.menuId,
                            menuImg = shoppingCart.menuImg,
                            menuName = shoppingCart.menuName,
                            menuCnt = (shoppingCart.menuCnt.toInt() - 1).toString(),
                            menuPrice = shoppingCart.menuPrice,
                            totalPrice = (deleteComma(shoppingCart.menuPrice) * (shoppingCart.menuCnt.toInt() - 1)).toString(),
                            type = shoppingCart.type
                        )
                    )
                }

                else -> {
                    newShoppingList.add(shoppingCart)
                }
            }
        }

        _shoppingList.value = newShoppingList.toList()
        _totalOrder.value = (_shoppingList.value.sumOf { it.menuCnt.toInt() }).toString()
        _totalPrice.value = makeComma((_shoppingList.value.sumOf { deleteComma(it.totalPrice) }))
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

    override fun onClickShop() {
        viewModelScope.launch {
            _shoppingUiEvent.emit(ShoppingListUiEvent.ShopOrder)
        }
    }

    override fun onClickTakeout() {
        viewModelScope.launch {
            _searchedShops.value = _shops.value
            _shopSearchKeyword.value = ""
            _isMapMode.value = false
            _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.SEARCH) }
            _shoppingUiEvent.emit(ShoppingListUiEvent.TakeOutOrder)
            _couponDetailUiEvent.emit(CouponDetailUiEvent.CouponTakeOut)
        }
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

    override fun onClickShopClose() {
        viewModelScope.launch {
            _selectShop.value = null
            _shoppingListUiState.update { it.copy(shopSelectState = SelectState.NONE) }
            _shoppingUiEvent.emit(ShoppingListUiEvent.OrderFail)
            _couponDetailUiEvent.emit(CouponDetailUiEvent.CouponOrderFail)
        }
    }

    override fun onClickShopSelect(shop: Shop) {
        viewModelScope.launch {
            _selectShop.value = shop
            _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.SEARCHSELECT) }
        }
    }

    override fun onClickShopSelectInMap(shop: Shop) {
        viewModelScope.launch {
            _selectShop.value = shop
            _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.MAPSELECT) }
        }
    }

    override fun onClickShopSelectCancel() {
        viewModelScope.launch {
            _selectShop.value = null
            when (_shopSelectUiState.value.selectValidState) {
                ShopSelectValidState.MAPSELECT -> {
                    _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.MAP) }
                }

                ShopSelectValidState.SEARCHSELECT -> {
                    _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.SEARCH) }
                }

                else -> {}
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
                    val orderId = response.data ?: 0
                    _shoppingUiEvent.emit(ShoppingListUiEvent.FinishOrder(orderId))
                    _couponDetailUiEvent.emit(CouponDetailUiEvent.FinishCouponOrder(orderId))
                    _totalOrder.value = "0"
                    _totalPrice.value = "￦0"
                    getUser()
                    getLastMonthOrders()
                    getLast6MonthsOrders()
                    getProducts()
                    validateShoppingList()
                    deleteCoupon()
                }

                else -> {
                    _shoppingUiEvent.emit(ShoppingListUiEvent.OrderFail)
                    _couponDetailUiEvent.emit(CouponDetailUiEvent.CouponOrderFail)
                }
            }
        }
    }

    private fun deleteCoupon() {
        viewModelScope.launch {
            _selectedCoupon.value?.let { coupon ->
                val response = getCouponUseCase.deleteCoupon(coupon.id)

                when (response.status) {
                    Status.SUCCESS -> {
                        when (response.data) {
                            true -> {
                                getCouponUseCase.getCoupons(getUserId().first())
                                // Coupon List 검증 로직 추가
                            }

                            else -> {

                            }
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }

    /*** My Page Click ***/
    override fun onClickSettings() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToSettings)
        }
    }

    override fun onClickMyPageSignUp() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToJoin)
        }
    }

    override fun onClickMyPageLogin() {
        viewModelScope.launch {
            _myPageUiEvent.emit(MyPageUiEvent.GoToLogin)
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

    /*** Setting Click ***/
    override fun onClickLogout() {
        viewModelScope.launch {
            _user.value = null
            setUserIdUseCase.setUserId("")
            setCookieUseCase.deleteLoginCookie()
            initStates()
            _settingUiEvent.emit(SettingUiEvent.DoLogout)
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

    /*** History Click ***/
    override fun onClickOrder(order: Order) {
        viewModelScope.launch {
            _selectedOrder.value = order
            _selectedOrderDetails.value = order.details
            _historyUiEvent.emit(HistoryUiEvent.GoToOrderDetail)
        }
    }

    /*** Information Click ***/
    override fun onClickPassword() {
        viewModelScope.launch {
            _newPassword.value = ""
            _newPasswordConfirm.value = ""
            _informationUiEvent.emit(InformationUiEvent.GoToPassword)
        }
    }

    override fun onClickPasswordUpdate() {
        viewModelScope.launch {
            _user.value?.let { user ->
                val newUser = User(
                    id = user.user.id,
                    name = "",
                    pass = _newPassword.value,
                    stamps = "",
                    stampList = emptyList()
                )
                val response = getUserUseCase.updatePassword(newUser)

                when (response.status) {
                    Status.SUCCESS -> {
                        response.data?.let { it ->
                            when (it) {
                                true -> {
                                    Timber.d("비번 변경 성공")
                                    _passwordUiEvent.emit(PasswordUiEvent.PasswordUpdateSuccess)
                                    onClickLogout()
                                }

                                else -> {
                                    Timber.d("비번 변경 실패인데 통신은 됨")
                                    _passwordUiEvent.emit(PasswordUiEvent.PasswordUpdateFailed)
                                }
                            }
                        }
                    }

                    else -> {
                        Timber.d("비번 변경하는 통신도 안 됨 ㅅㅂ")
                        _passwordUiEvent.emit(PasswordUiEvent.PasswordUpdateFailed)
                    }
                }
            }
        }
    }

    /*** Coupon Click ***/
    override fun onClickCoupon(coupon: Coupon) {
        viewModelScope.launch {
            _selectedCoupon.value = null
            _selectedCoupon.value = coupon
            _selectedCouponMenu.value = null
            val response = getProductUseCase.getProductWithComment(coupon.menuCount.toInt())

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { product ->
                        _selectedCouponMenu.value = ShoppingCart(
                            menuId = product.id,
                            menuImg = product.img,
                            menuName = product.name,
                            menuCnt = coupon.menuCount,
                            menuPrice = product.price,
                            totalPrice = (deleteComma(coupon.menuCount) * deleteComma(product.price)).toString(),
                            type = ""
                        )
                    }
                }

                else -> {
                    Timber.d("뭐해?")
                }
            }

            _couponUiEvent.emit(CouponUiEvent.GoToCouponDetail)
        }
    }

    /*** Coupon Detail Click ***/
    override fun onClickTakeoutCoupon() {
        viewModelScope.launch {
            val selectedCouponMenu = _selectedCouponMenu.value
            if (selectedCouponMenu != null) {
                _shoppingList.value = listOf(selectedCouponMenu)
            }
            onClickTakeout()
        }
    }

    /*** Init Function ***/
    private fun setTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            val appTheme = getAppTheme().first()
            _appThemeName.value = appTheme
        }
    }

    private fun setNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            val newNotices = getNotices().first()
            _notices.value = newNotices.toList()
            validateNoticeState()
        }
    }

    private fun setEvents() {
        viewModelScope.launch {
            val response = getEventUseCase.getEvents()

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { events ->
                        Timber.d("Events: $events")
                        _events.value = listOf(events.last()) + events + listOf(events.first())
                    }
                }

                else -> {
                    Timber.d("뭐해?")
                }
            }
        }
    }

    private fun getShop() {
        viewModelScope.launch {
            val response = getShopUseCase.getShops()

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { shops ->
                        _shops.value = shops
                    }
                }

                else -> {}
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val response =
                getUserUseCase.getUserInfo(getUserId().first())
            when (response.status) {
                Status.SUCCESS -> {
                    _user.value = response.data
                    validateGrade()
                    val newUser = _user.value
                    if (newUser != null) {
                        getCoupons(newUser.user.id)
                    }
                    Timber.d("User: ${_user.value}")
                }

                else -> {
                    Timber.d("${response.data}")
                }
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
                        val newBeverageProducts = mutableListOf<Product>()
                        val newFoodProducts = mutableListOf<Product>()

                        products.forEach { product ->
                            when (product.type) {
                                "beverage" -> {
                                    Timber.d("Product: $product")
                                    newBeverageProducts.add(product)
                                }

                                else -> {
                                    newFoodProducts.add(product)
                                }
                            }
                        }

                        newProducts.add(newBeverageProducts.toList())
                        newProducts.add(newFoodProducts.toList())
                        _products.value = newProducts.toList()
                    }
                }

                else -> {}
            }
        }
    }

    private fun getCoupons(userId: String) {
        viewModelScope.launch {
            val response =
                getCouponUseCase.getCoupons(userId)
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { coupons ->
                        _coupons.value = coupons
                        validateCouponState()
                    }
                }

                else -> {
                    Timber.d("${response.data}")
                }
            }
        }
    }

    /*** Get Dto ***/
    private fun getUserBody(
        id: String,
        name: String,
        password: String,
        stamps: String,
        stampList: List<Stamp>
    ): User = User(id, name, password, stamps, stampList)

    /*** Use Case ***/
    private fun login() {
        viewModelScope.launch {
            val response = getUserUseCase.postUserForLogin(
                getUserBody(
                    _userId.value,
                    "",
                    _userPass.value,
                    "0",
                    emptyList()
                )
            )

            when (response.status) {
                Status.SUCCESS -> {
                    val cookies = getCookies().first()
                    cookies.let {
                        if (it.isNotEmpty()) {
                            setUserIdUseCase.setUserId(_userId.value)
                            setCookieUseCase.setLoginCookie(it.toHashSet())
                            _userId.value = ""
                            _userPass.value = ""
                            initStatesWithLogin()
                            validateLoginUiState()
                            _loginUiEvent.emit(LoginUiEvent.GoToLogin)
                            _joinUiEvent.emit(JoinUiEvent.GoToLogin)
                        }
                    }
                }

                else -> {
                    _loginUiEvent.emit(LoginUiEvent.LoginFail)
                }
            }
        }
    }

    private fun join() {
        viewModelScope.launch {
            val response = getUserUseCase.postUser(
                getUserBody(
                    _joinId.value,
                    _joinName.value,
                    _joinPass.value,
                    "0",
                    emptyList()
                )
            )

            when (response.status) {
                Status.SUCCESS -> {
                    when (response.data) {
                        true -> {
                            _userId.value = _joinId.value
                            _joinId.value = ""
                            _joinPass.value = ""
                            _joinPassConfirm.value = ""
                            _joinName.value = ""
                            _joinUiState.update {
                                it.copy(
                                    joinIdValidState = InputValidState.NONE,
                                    joinPassValidState = PasswordState.INIT,
                                    joinPassConfirmValidState = PasswordState.INIT,
                                    joinNameValidState = InputValidState.NONE,
                                    joinIdDuplicateState = DuplicateState.DUPLICATE
                                )
                            }
                            _joinUiEvent.emit(JoinUiEvent.GoToLogin)
                        }

                        else -> {
                            _joinUiEvent.emit(JoinUiEvent.JoinFail)
                        }
                    }
                }

                else -> {
                    _joinUiEvent.emit(JoinUiEvent.JoinFail)
                }
            }
        }
    }

    /*** Validate Ui State ***/
    private fun validateLoginUiState() {
        when (_userId.value.isBlank()) {
            true -> _loginUiState.update { it.copy(userIdValidState = InputValidState.NONE) }

            else -> _loginUiState.update { it.copy(userIdValidState = InputValidState.VALID) }
        }
        when (_userPass.value.isBlank()) {
            true -> _loginUiState.update { it.copy(userPassValidState = InputValidState.NONE) }

            else -> _loginUiState.update { it.copy(userPassValidState = InputValidState.VALID) }
        }
    }

    private fun validateJoinIdDuplicate() {
        viewModelScope.launch {
            when (_joinId.value.isBlank()) {
                true -> {
                    _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.DUPLICATE) }
                    _joinUiEvent.emit(JoinUiEvent.CheckId(true))
                }

                else -> {
                    val response = getUserUseCase.getIsUsedId(_joinId.value)
                    when (response.status) {
                        Status.SUCCESS -> {
                            when (response.data) {
                                false -> {
                                    _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.NONE) }
                                    _joinUiEvent.emit(JoinUiEvent.CheckId(response.data))
                                }

                                else -> {
                                    _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.DUPLICATE) }
                                    _joinUiEvent.emit(JoinUiEvent.CheckId(true))
                                }
                            }
                        }

                        else -> {
                            _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.DUPLICATE) }
                            _joinUiEvent.emit(JoinUiEvent.CheckId(true))
                        }
                    }
                }
            }
        }
    }

    private fun validateGrade() {
        when (_user.value?.grade?.title) {
            "PLATINUM" -> {
                _myPageUiState.update { it.copy(gradeState = GradeState.TREE) }
            }

            else -> {
                _myPageUiState.update { it.copy(gradeState = GradeState.NONE) }
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

    private fun validateCouponState() {
        when (_coupons.value.isNotEmpty()) {
            true -> _couponUiState.update { it.copy(couponsValidState = EmptyState.NONE) }

            else -> _couponUiState.update { it.copy(couponsValidState = EmptyState.EMPTY) }
        }
    }

    private fun validateNoticeState() {
        when (_notices.value.isNotEmpty()) {
            true -> _noticeUiState.update { it.copy(noticesState = EmptyState.NONE) }

            else -> _noticeUiState.update { it.copy(noticesState = EmptyState.EMPTY) }
        }
    }

    /*** Data Store ***/
    private suspend fun getUserId(): Flow<String> = flow {
        val userId = getUserIdUseCase.getUserId().first()
        emit(userId)
    }

    private suspend fun getAppTheme(): Flow<String> = flow {
        val appTheme = getAppThemeUseCase.getAppTheme().first()
        emit(appTheme)
    }

    private suspend fun getCookies(): Flow<Set<String>> = flow {
        val cookies = getCookieUseCase.getLoginCookie().firstOrNull() ?: emptySet()
        emit(cookies)
    }

    private suspend fun getNotices(): Flow<List<String>> = flow {
        val notices = getNoticesUseCase.getNotices().firstOrNull() ?: emptyList()
        emit(notices)
    }

    /*** Public Methods ***/
    fun initStatesWithLogin() {
        setTheme()
        setNotices()
        getShop()
        getUser()
        getLastMonthOrders()
        getLast6MonthsOrders()
        getProducts()
    }

    fun initStates() {
        setTheme()
        getProducts()
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

    fun setMapMode(isMapMode: Boolean) {
        viewModelScope.launch {
            _isMapMode.value = isMapMode
            when (_isMapMode.value) {
                true -> {
                    _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.MAP) }
                }

                else -> {
                    _shopSelectUiState.update { it.copy(selectValidState = ShopSelectValidState.SEARCH) }
                }
            }
        }
    }

    fun sortSearchedShop(myPosition: Location) {
        _searchedShops.value = _searchedShops.value.sortedBy { shop ->
            val shopLocation = Location("").apply {
                latitude = shop.latitude
                longitude = shop.longitude
            }

            abs(shopLocation.distanceTo(myPosition))
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
                    val orderId = response.data ?: 0
                    _shoppingUiEvent.emit(ShoppingListUiEvent.FinishOrder(orderId))
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

    fun getNewNotice(notice: String) {
        viewModelScope.launch {
            _notices.value = _notices.value.toMutableList().apply {
                add(notice)
            }
            validateNoticeState()
            setNoticesUseCase.setNotices(_notices.value)
        }
    }

    fun validateUserId(id: CharSequence) {
        when (id.isNotBlank()) {
            true -> _loginUiState.update { it.copy(userIdValidState = InputValidState.VALID) }

            else -> _loginUiState.update { it.copy(userIdValidState = InputValidState.NONE) }
        }
    }

    fun validateUserPass(pass: CharSequence) {
        when (pass.isNotBlank()) {
            true -> _loginUiState.update { it.copy(userPassValidState = InputValidState.VALID) }

            else -> _loginUiState.update { it.copy(userPassValidState = InputValidState.NONE) }
        }
    }

    fun validateJoinId(id: CharSequence) {
        if (id.isBlank()) {
            _joinUiState.update { it.copy(joinIdValidState = InputValidState.NONE) }
            return
        }

        when (validateId(id)) {
            true -> {
                _joinUiState.update { it.copy(joinIdValidState = InputValidState.VALID) }
            }

            else -> {
                _joinUiState.update { it.copy(joinIdValidState = InputValidState.NONE) }
            }
        }
    }

    fun validateJoinPass(pass: CharSequence) {
        if (pass.isBlank()) {
            _joinUiState.update { it.copy(joinPassValidState = PasswordState.INIT) }
            return
        }

        when (validatePassword(pass)) {
            true -> {
                _joinUiState.update { it.copy(joinPassValidState = PasswordState.VALID) }
            }

            else -> {
                _joinUiState.update { it.copy(joinPassValidState = PasswordState.NONE) }
            }
        }
    }

    fun validateJoinPassConfirm(passConfirm: CharSequence) {
        if (passConfirm.isBlank()) {
            _joinUiState.update { it.copy(joinPassConfirmValidState = PasswordState.INIT) }
            return
        }

        when (passConfirm.toString() == _joinPass.value) {
            true -> {
                _joinUiState.update { it.copy(joinPassConfirmValidState = PasswordState.VALID) }
            }

            else -> {
                _joinUiState.update { it.copy(joinPassConfirmValidState = PasswordState.NONE) }
            }
        }
    }

    fun validateJoinName(name: CharSequence) {
        when (name.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinNameValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinNameValidState = InputValidState.NONE) }
        }
    }

    fun validateNewPassword(password: CharSequence) {
        if (password.isBlank()) {
            _passwordUiState.update { it.copy(newPasswordValidState = PasswordState.INIT) }
            return
        }

        when (validatePassword(password)) {
            true -> {
                _passwordUiState.update { it.copy(newPasswordValidState = PasswordState.VALID) }
            }

            else -> {
                _passwordUiState.update { it.copy(newPasswordValidState = PasswordState.NONE) }
            }
        }
    }

    fun validateNewPasswordConfirm(passwordConfirm: CharSequence) {
        if (passwordConfirm.isBlank()) {
            _passwordUiState.update { it.copy(newPasswordConfirmValidState = PasswordState.INIT) }
            return
        }

        when (passwordConfirm.toString() == _newPassword.value) {
            true -> {
                _passwordUiState.update { it.copy(newPasswordConfirmValidState = PasswordState.VALID) }
            }

            else -> {
                _passwordUiState.update { it.copy(newPasswordConfirmValidState = PasswordState.NONE) }
            }
        }
    }

    fun validateShopSearchKeyword(shopSearchKeyword: CharSequence) {
        if (shopSearchKeyword.isBlank()) {
            _searchedShops.value = _shops.value
            return
        }

        val newSearchedShops = mutableListOf<Shop>()

        _shops.value.forEach { shop ->
            if (shop.name.contains(shopSearchKeyword)) {
                newSearchedShops.add(shop)
            }
        }

        _searchedShops.value = newSearchedShops.toList()
    }

    // FCM 토큰 업로드 메서드 추가
    fun uploadFcmToken(token: String) {
        viewModelScope.launch {
            val userId = getUserIdUseCase.getUserId().first()
            if (userId.isNotEmpty()) {
                val result = addFcmTokenUseCase.addFcmToken(userId, token)
                when (result.status) {
                    Status.SUCCESS -> {
                        if (result.data == true) {
                            Timber.d("FCM 토큰 업로드 성공")
                        } else {
                            Timber.e("FCM 토큰 업로드 실패: 서버 응답이 false입니다.")
                        }
                    }

                    else -> {
                        Timber.e("FCM 토큰 업로드 실패: ${result.message}")
                    }
                }
            } else {
                Timber.w("사용자 ID가 비어있습니다. FCM 토큰을 업로드할 수 없습니다.")
            }
        }
    }
}