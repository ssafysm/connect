package com.ssafy.smartstore_jetpack.data.di

import com.ssafy.smartstore_jetpack.data.api.CommentApi
import com.ssafy.smartstore_jetpack.data.api.OrderApi
import com.ssafy.smartstore_jetpack.data.api.ProductApi
import com.ssafy.smartstore_jetpack.data.api.UserApi
import com.ssafy.smartstore_jetpack.data.repository.comment.CommentRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.comment.CommentRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.order.OrderRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.order.OrderRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.product.ProductRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.product.ProductRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.user.UserRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.user.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideCommentRemoteDataSource(commentApi: CommentApi): CommentRemoteDataSource {
        return CommentRemoteDataSourceImpl(commentApi)
    }

    @Provides
    @Singleton
    fun provideOrderRemoteDataSource(orderApi: OrderApi): OrderRemoteDataSource {
        return OrderRemoteDataSourceImpl(orderApi)
    }

    @Provides
    @Singleton
    fun provideProductRemoteDataSource(productApi: ProductApi): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(productApi)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(userApi: UserApi): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userApi)
    }
}