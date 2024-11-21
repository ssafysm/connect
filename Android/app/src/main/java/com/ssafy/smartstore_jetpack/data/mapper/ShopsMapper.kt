package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.ShopEntity
import com.ssafy.smartstore_jetpack.domain.model.Shop

object ShopsMapper {

    operator fun invoke(shopEntities: List<ShopEntity>): List<Shop> {
        val newShops = mutableListOf<Shop>()

        shopEntities.forEach { shopEntity ->
            newShops.add(
                Shop(
                    id = shopEntity.id.toString(),
                    name = shopEntity.name ?: "",
                    image = shopEntity.image ?: "",
                    description = shopEntity.description ?: "",
                    time = shopEntity.time ?: "",
                    latitude = shopEntity.latitude ?: 0.0,
                    longitude = shopEntity.longitude ?: 0.0
                )
            )
        }

        return newShops.toList()
    }
}