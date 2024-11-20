package com.ssafy.smartstore_jetpack.presentation.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.OrderDetail
import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.domain.model.Shop
import com.ssafy.smartstore_jetpack.domain.model.ShoppingCart
import com.ssafy.smartstore_jetpack.domain.model.Status
import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.model.UserInfo
import com.ssafy.smartstore_jetpack.domain.usecase.GetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCommentUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetOrderUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetProductUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserIdUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetUserIdUseCase
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.deleteComma
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeCommaWon
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.validatePassword
import com.ssafy.smartstore_jetpack.presentation.util.DuplicateState
import com.ssafy.smartstore_jetpack.presentation.util.EmptyState
import com.ssafy.smartstore_jetpack.presentation.util.InputValidState
import com.ssafy.smartstore_jetpack.presentation.util.PasswordState
import com.ssafy.smartstore_jetpack.presentation.util.SelectState
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.cart.ShoppingListUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.coupon.CouponUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.history.HistoryUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.home.EventUiState
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
import com.ssafy.smartstore_jetpack.presentation.views.main.notice.NoticeUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.order.OrderUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.main.order.ProductClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.password.PasswordUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.setting.SettingClickListener
import com.ssafy.smartstore_jetpack.presentation.views.main.setting.SettingUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCommentUseCase: GetCommentUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,
    private val getCookieUseCase: GetCookieUseCase,
    private val setCookieUseCase: SetCookieUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase
) : ViewModel(), HomeClickListener, LoginClickListener, JoinClickListener, MyPageClickListener,
    ProductClickListener, CommentClickListener, ShoppingListClickListener, SettingClickListener,
    InformationClickListener {

    /****** Data ******/
    private val _userId = MutableStateFlow<String>("")
    val userId = _userId

    private val _userPass = MutableStateFlow<String>("")
    val userPass = _userPass

    private val _joinId = MutableStateFlow<String>("")
    val joinId = _joinId

    private val _joinPass = MutableStateFlow<String>("")
    val joinPass = _joinPass

    private val _joinName = MutableStateFlow<String>("")
    val joinName = _joinName

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

    private val _shops = MutableStateFlow<List<Shop>>(emptyList())
    val shops = _shops.asStateFlow()

    private val _selectShop = MutableStateFlow<Shop?>(null)
    val selectShop = _selectShop.asStateFlow()

    private val _newPassword = MutableStateFlow<String>("")
    val newPassword = _newPassword

    private val _newPasswordConfirm = MutableStateFlow<String>("")
    val newPasswordConfirm = _newPasswordConfirm

    private val _notices = MutableStateFlow<List<String>>(emptyList())
    val notice = _notices.asStateFlow()

    /****** Ui State ******/
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _joinUiState = MutableStateFlow<JoinUiState>(JoinUiState())
    val joinUiState = _joinUiState.asStateFlow()

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

    private val _passwordUiState = MutableStateFlow<PasswordUiState>(PasswordUiState())
    val passwordUiState = _passwordUiState.asStateFlow()

    private val _noticeUiState = MutableStateFlow<NoticeUiState>(NoticeUiState())
    val noticeUiState = _noticeUiState.asStateFlow()

    /****** Ui Event ******/
    private val _homeUiEvent = MutableSharedFlow<HomeUiEvent>()
    val homeUiEvent = _homeUiEvent.asSharedFlow()

    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent>()
    val loginUiEvent = _loginUiEvent.asSharedFlow()

    private val _joinUiEvent = MutableSharedFlow<JoinUiEvent>()
    val joinUiEvent = _joinUiEvent.asSharedFlow()

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

    override fun onClickLogin() {
        viewModelScope.launch {
            val response = getUserUseCase.postUserForLogin(
                User(
                    id = _userId.value,
                    name = "",
                    pass = _userPass.value,
                    stamps = "0",
                    stampList = emptyList()
                )
            )

            when (response.status) {
                Status.SUCCESS -> {
                    val cookies = getCookies().first()
                    cookies.let {
                        if (it.isNotEmpty()) {
                            Timber.d("Cookie: $cookies")
                            setUserIdUseCase.setUserId(_userId.value)
                            initStatesWithLogin()
                            setCookieUseCase.setLoginCookie(it.toHashSet())
                            _userId.value = ""
                            _userPass.value = ""
                            _loginUiState.update { uiState ->
                                uiState.copy(
                                    userIdValidState = InputValidState.NONE,
                                    userPassValidState = InputValidState.NONE
                                )
                            }
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

    override fun onClickJoin() {
        viewModelScope.launch {
            _loginUiEvent.emit(LoginUiEvent.GoToJoin)
        }
    }

    override fun onClickCheckId() {
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

    override fun onClickGoToJoin() {
        viewModelScope.launch {
            val response = getUserUseCase.postUser(
                User(
                    id = _joinId.value,
                    name = _joinName.value,
                    pass = _joinPass.value,
                    stamps = "0",
                    stampList = emptyList()
                )
            )

            when (response.status) {
                Status.SUCCESS -> {
                    when (response.data) {
                        true -> {
                            _userId.value = _joinId.value
                            _joinId.value = ""
                            _joinPass.value = ""
                            _joinName.value = ""
                            _joinUiState.update {
                                it.copy(
                                    joinIdValidState = InputValidState.NONE,
                                    joinPassValidState = InputValidState.NONE,
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

    override fun onClickOrder(order: Order) {
        viewModelScope.launch {
            _selectedOrder.value = order
            _selectedOrderDetails.value = order.details
            _historyUiEvent.emit(HistoryUiEvent.GoToOrderDetail)
        }
    }

    override fun onClickNotice() {
        viewModelScope.launch {
            _homeUiEvent.emit(HomeUiEvent.GoToNotice)
        }
    }

    override fun onClickLogout() {
        viewModelScope.launch {
            _user.value = null
            setUserIdUseCase.setUserId("")
            setCookieUseCase.deleteLoginCookie()
            _settingUiEvent.emit(SettingUiEvent.DoLogout)
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
        Timber.d("Total Price: ${_totalPrice.value}")
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
            _shoppingUiEvent.emit(ShoppingListUiEvent.TakeOutOrder)
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
        }
    }

    override fun onClickShopSelect(shop: Shop) {
        viewModelScope.launch {
            _selectShop.value = shop
            _shoppingListUiState.update { it.copy(shopSelectState = SelectState.SELECT) }
        }
    }

    override fun onClickShopSelectCancel() {
        viewModelScope.launch {
            _selectShop.value = null
            _shoppingListUiState.update { it.copy(shopSelectState = SelectState.NONE) }
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
            _newPassword.value = ""
            _newPasswordConfirm.value = ""
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

    private fun setShop() {
        val newShops = mutableListOf<Shop>()

        newShops.add(
            Shop(
                id = "1",
                name = "구미인동",
                image = "https://mblogthumb-phinf.pstatic.net/MjAyNDAxMThfMjUz/MDAxNzA1NTY2Mzg0Mzk2.gVwa4ygCav1gbmwGq2tWEtDvHU5ufrJVjJs-JZBIrM0g.QwyYd_P-C2LCjsTh3fEHJfAQl91scMSVaYR2gjown3Ag.JPEG.yosulpp/SE-b60fa36a-b42d-11ee-9a89-976840ec37c2.jpg?type=w800",
                description = "경상북도 구미시 인동가산로9-3, 노블레스타워 1층(황상동)",
                time = "평일 06:00 ~ 23:00\n주말 07:00 ~ 23:00"
            )
        )
        newShops.add(
            Shop(
                id = "2",
                name = "구미인의DT",
                image = "https://mblogthumb-phinf.pstatic.net/MjAyNDAxMDVfODIg/MDAxNzA0NDU3ODE1NjE2.3eBnacAfnkYIPrf0m1X5KrLaLfkmPak_na1ei7bZnMEg.kLKFbCAieqLlg1v80b0BWxBHfYWFCZtWA_y4oJJbrBUg.JPEG.m_4862/output_2270109392.jpg?type=w800",
                description = "경상북도 구미시 인동북길 149(인의동)",
                time = "평일 06:00 ~ 23:00\n주말 07:00 ~ 23:00"
            )
        )
        newShops.add(
            Shop(
                id = "3",
                name = "구미공단",
                image = "https://naverbooking-phinf.pstatic.net/20240611_120/1718104723924coCrQ_JPEG/image.jpg?type=f750_420_60_sharpen",
                description = "경상북도 구미시 1공단로212, HALLA SIGMA VALLEY 104...",
                time = "평일 06:00 ~ 23:00\n주말 07:00 ~ 23:00"
            )
        )
        newShops.add(
            Shop(
                id = "4",
                name = "구미옥계",
                image = "https://mblogthumb-phinf.pstatic.net/MjAyMTA3MTRfMTEg/MDAxNjI2MjYyNzE4NDI5.AMdA_uB_i8FJNyhVzFx4pkGRyKqgzTkRRPUwbTEqflcg.wAI4J4M6MfW0_k3LKPyTGRpcii_cw-Alju6rgTGu0gog.JPEG.kilrboy89/SE-01f7a5c6-9709-4736-8f30-9f3c6d81df8a.jpg?type=w800",
                description = "경상북도 구미시 옥계북로20(양포동)",
                time = "평일 06:00 ~ 23:00\n주말 07:00 ~ 23:00"
            )
        )
        newShops.add(
            Shop(
                id = "5",
                name = "구미광평DT",
                image = "https://naverbooking-phinf.pstatic.net/20240611_257/1718104467893XeEyG_JPEG/image.jpg?type=f750_420_60_sharpen",
                description = "경상북도 구미시 구미대로 188(광평동)",
                time = "평일 06:00 ~ 23:00\n주말 07:00 ~ 23:00"
            )
        )

        _shops.value = newShops
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
        val appTheme = getAppThemeUseCase.getAppTheme().first()
        emit(appTheme)
    }

    private suspend fun getCookies(): Flow<Set<String>> = flow {
        val cookies = getCookieUseCase.getLoginCookie().firstOrNull() ?: emptySet()
        emit(cookies)
    }

    fun initStatesWithLogin() {
        setEvents()
        setTheme()
        setShop()
        getUser()
        getLastMonthOrders()
        getLast6MonthsOrders()
        getProducts()
    }

    fun initStates() {
        setEvents()
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
        when (id.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinIdValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinIdValidState = InputValidState.NONE) }
        }
    }

    fun validateJoinPass(pass: CharSequence) {
        when (pass.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinPassValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinPassValidState = InputValidState.NONE) }
        }
    }

    fun validateJoinName(name: CharSequence) {
        when (name.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinNameValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinNameValidState = InputValidState.NONE) }
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
}