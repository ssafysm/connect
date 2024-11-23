package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.ProductEntity
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Product

object ProductsMapper {

    operator fun invoke(productEntities: List<ProductEntity>): List<List<Product>> {
        val newProducts = mutableListOf<List<Product>>()
        val newBeverages = mutableListOf<Product>()
        val newFoods = mutableListOf<Product>()

        productEntities.forEach { productEntity ->
            val newComments = mutableListOf<Comment>()

            productEntity.comments?.forEach { comment ->
                newComments.add(
                    Comment(
                        id = comment.id,
                        userId = comment.userId,
                        productId = comment.productId,
                        rating = comment.rating,
                        comment = comment.comment,
                        userName = comment.userName
                    )
                )
            }

            when (productEntity.type) {
                "beverage" -> {
                    newBeverages.add(
                        Product(
                            id = productEntity.id,
                            name = productEntity.name,
                            type = productEntity.type,
                            price = productEntity.price.toString(),
                            img = productEntity.img,
                            description = productEntity.description ?: productEntity.name,
                            mode = productEntity.mode ?: "ICED",
                            comments = newComments.reversed(),
                            productCommentTotalCnt = productEntity.productCommentTotalCnt,
                            productRatingAvg = productEntity.productRatingAvg.toString(),
                            productTotalSellCnt = productEntity.productTotalSellCnt
                        )
                    )
                }

                else -> {
                    newFoods.add(
                        Product(
                            id = productEntity.id,
                            name = productEntity.name,
                            type = productEntity.type,
                            price = productEntity.price.toString(),
                            img = productEntity.img,
                            description = productEntity.description ?: productEntity.name,
                            mode = productEntity.mode ?: "ICED",
                            comments = newComments,
                            productCommentTotalCnt = productEntity.productCommentTotalCnt,
                            productRatingAvg = productEntity.productRatingAvg.toString(),
                            productTotalSellCnt = productEntity.productTotalSellCnt
                        )
                    )
                }
            }
        }
        newProducts.add(newBeverages)
        newProducts.add(newFoods)

        return newProducts.toList()
    }
}