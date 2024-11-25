package com.ssafy.smartstore_jetpack.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ssafy.smartstore_jetpack.data.repository.alarm.AlarmRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.alarm.AlarmRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.attendance.AttendanceRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.attendance.AttendanceRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.comment.CommentRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.comment.CommentRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.coupon.CouponRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.coupon.CouponRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.datastore.DataStoreRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.event.EventRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.event.EventRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.order.OrderRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.order.OrderRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.product.ProductRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.product.ProductRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.shop.ShopRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.shop.ShopRepositoryImpl
import com.ssafy.smartstore_jetpack.data.repository.user.UserRemoteDataSource
import com.ssafy.smartstore_jetpack.data.repository.user.UserRepositoryImpl
import com.ssafy.smartstore_jetpack.domain.repository.AlarmRepository
import com.ssafy.smartstore_jetpack.domain.repository.AttendanceRepository
import com.ssafy.smartstore_jetpack.domain.repository.CommentRepository
import com.ssafy.smartstore_jetpack.domain.repository.CouponRepository
import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import com.ssafy.smartstore_jetpack.domain.repository.EventRepository
import com.ssafy.smartstore_jetpack.domain.repository.OrderRepository
import com.ssafy.smartstore_jetpack.domain.repository.ProductRepository
import com.ssafy.smartstore_jetpack.domain.repository.ShopRepository
import com.ssafy.smartstore_jetpack.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCommentRepository(commentRemoteDataSource: CommentRemoteDataSource): CommentRepository {
        return CommentRepositoryImpl(commentRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderRemoteDataSource: OrderRemoteDataSource): OrderRepository {
        return OrderRepositoryImpl(orderRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRemoteDataSource: ProductRemoteDataSource): ProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideShopRepository(shopRemoteDataSource: ShopRemoteDataSource): ShopRepository {
        return ShopRepositoryImpl(shopRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventRemoteDataSource: EventRemoteDataSource): EventRepository {
        return EventRepositoryImpl(eventRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCouponRepository(couponRemoteDataSource: CouponRemoteDataSource): CouponRepository {
        return CouponRepositoryImpl(couponRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmRemoteDataSource: AlarmRemoteDataSource): AlarmRepository {
        return AlarmRepositoryImpl(alarmRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(attendanceRemoteDataSource: AttendanceRemoteDataSource): AttendanceRepository {
        return AttendanceRepositoryImpl(attendanceRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }
}