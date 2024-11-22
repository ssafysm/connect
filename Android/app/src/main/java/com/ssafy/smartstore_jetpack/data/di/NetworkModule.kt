package com.ssafy.smartstore_jetpack.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ssafy.smartstore_jetpack.data.api.AlarmApi
import com.ssafy.smartstore_jetpack.data.base.AddCookiesInterceptor
import com.ssafy.smartstore_jetpack.data.base.ReceivedCookiesInterceptor
import com.ssafy.smartstore_jetpack.data.api.CommentApi
import com.ssafy.smartstore_jetpack.data.api.FcmApi
import com.ssafy.smartstore_jetpack.data.api.CouponApi
import com.ssafy.smartstore_jetpack.data.api.EventApi
import com.ssafy.smartstore_jetpack.data.api.OrderApi
import com.ssafy.smartstore_jetpack.data.api.ProductApi
import com.ssafy.smartstore_jetpack.data.api.ShopApi
import com.ssafy.smartstore_jetpack.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

//    private const val BASE_URL = "http://mobile-pjt.sample.ssafy.io/"

    // JW 서버 주소
    // private const val BASE_URL = "http://192.168.33.129:9987/"

    // SM 서버 주소
    // private const val BASE_URL = "http://192.168.33.130:9987/"

    // JW 핫스팟 서버 주소
    // private const val BASE_URL = "http://192.168.43.161:9987/"

    // JW 자취방 와이파이 주소
    private const val BASE_URL = "http://192.168.0.200:9987/"

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        addCookiesInterceptor: AddCookiesInterceptor,
        receivedCookiesInterceptor: ReceivedCookiesInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(receivedCookiesInterceptor).build()
    }

    @Provides
    @Singleton
    @Named("SSAFY")
    fun provideSSAFYRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(provideMoshiConverterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideCommentApiService(@Named("SSAFY") retrofit: Retrofit): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderApiService(@Named("SSAFY") retrofit: Retrofit): OrderApi {
        return retrofit.create(OrderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApiService(@Named("SSAFY") retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(@Named("SSAFY") retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShopApiService(@Named("SSAFY") retrofit: Retrofit): ShopApi {
        return retrofit.create(ShopApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventApiService(@Named("SSAFY") retrofit: Retrofit): EventApi {
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCouponApiService(@Named("SSAFY") retrofit: Retrofit): CouponApi {
        return retrofit.create(CouponApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAlarmApiService(@Named("SSAFY") retrofit: Retrofit): AlarmApi {
        return retrofit.create(AlarmApi::class.java)
    }

    // FcmApi 추가
    @Provides
    @Singleton
    fun provideFcmApiService(@Named("SSAFY") retrofit: Retrofit): FcmApi {
        return retrofit.create(FcmApi::class.java)
    }
}