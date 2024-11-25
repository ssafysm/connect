package com.ssafy.smartstore_jetpack.data.di

import com.ssafy.smartstore_jetpack.data.api.AlarmApi
import com.ssafy.smartstore_jetpack.data.api.AttendanceApi
import com.ssafy.smartstore_jetpack.data.api.CommentApi
import com.ssafy.smartstore_jetpack.data.api.CouponApi
import com.ssafy.smartstore_jetpack.data.api.EventApi
import com.ssafy.smartstore_jetpack.data.api.OrderApi
import com.ssafy.smartstore_jetpack.data.api.ProductApi
import com.ssafy.smartstore_jetpack.data.api.ShopApi
import com.ssafy.smartstore_jetpack.data.api.UserApi
import com.ssafy.smartstore_jetpack.data.repository.alarm.AlarmRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.alarm.AlarmRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.attendance.AttendanceRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.attendance.AttendanceRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.comment.CommentRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.comment.CommentRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.coupon.CouponRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.coupon.CouponRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.event.EventRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.event.EventRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.order.OrderRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.order.OrderRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.product.ProductRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.product.ProductRemoteDataSourceImpl
import com.ssafy.smartstore_jetpack.data.repository.shop.ShopRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.shop.ShopRemoteDataSourceImpl
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

    @Provides
    @Singleton
    fun provideShopRemoteDataSource(shopApi: ShopApi): ShopRemoteDataSource {
        return ShopRemoteDataSourceImpl(shopApi)
    }

    @Provides
    @Singleton
    fun provideEventRemoteDataSource(eventApi: EventApi): EventRemoteDataSource {
        return EventRemoteDataSourceImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideCouponRemoteDataSource(couponApi: CouponApi): CouponRemoteDataSource {
        return CouponRemoteDataSourceImpl(couponApi)
    }

    @Provides
    @Singleton
    fun provideAlarmRemoteDataSource(couponApi: AlarmApi): AlarmRemoteDataSource {
        return AlarmRemoteDataSourceImpl(couponApi)
    }

    @Provides
    @Singleton
    fun provideAttendanceRemoteDataSource(attendanceApi: AttendanceApi): AttendanceRemoteDataSource {
        return AttendanceRemoteDataSourceImpl(attendanceApi)
    }
}