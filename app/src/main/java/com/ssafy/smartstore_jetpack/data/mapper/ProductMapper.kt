package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.ProductEntity
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma

object ProductMapper {

    operator fun invoke(productEntity: ProductEntity): Product {
        val newComments = mutableListOf<Comment>()

        productEntity.comments?.forEach { comment ->
            newComments.add(
                Comment(
                    id = comment.id,
                    userId = comment.userId,
                    productId = comment.productId,
                    rating = comment.rating,
                    comment = comment.comment,
                    userName = comment.userName,
                )
            )
        }

        return Product(
            id = productEntity.id,
            name = productEntity.name,
            type = productEntity.type,
            price = makeComma(productEntity.price),
            img = productEntity.img,
            comments = newComments,
            productCommentTotalCnt = productEntity.productCommentTotalCnt,
            productRatingAvg = productEntity.productRatingAvg.toString(),
            productTotalSellCnt = productEntity.productTotalSellCnt
        )
    }
}