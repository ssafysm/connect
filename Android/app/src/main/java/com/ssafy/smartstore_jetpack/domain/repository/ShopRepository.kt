package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.Shop

interface ShopRepository {

    suspend fun getShops(): Result<List<Shop>>
}