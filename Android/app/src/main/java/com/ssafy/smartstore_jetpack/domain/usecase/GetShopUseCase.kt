package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.Shop
import com.ssafy.smartstore_jetpack.domain.repository.ShopRepository
import javax.inject.Inject

class GetShopUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {

    suspend fun getShops(): Result<List<Shop>> = shopRepository.getShops()
}