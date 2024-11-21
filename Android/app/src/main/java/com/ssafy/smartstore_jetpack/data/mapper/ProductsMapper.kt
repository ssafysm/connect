package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.ProductEntity
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Product

object ProductsMapper {

    operator fun invoke(productEntities: List<ProductEntity>): List<Product> {
        val newProducts = mutableListOf<Product>()

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

            newProducts.add(
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

        return newProducts.toList()
    }
}