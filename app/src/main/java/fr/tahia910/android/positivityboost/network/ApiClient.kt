package fr.tahia910.android.positivityboost.network

import fr.tahia910.android.positivityboost.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {

    fun createRetrofitBuilder(): Retrofit.Builder {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    fun createDogRetrofitBuilder(): Retrofit.Builder {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val header = Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("x-api-key", fr.tahia910.android.positivityboost.BuildConfig.DOG_API_KEY)
            chain.proceed(builder.build())
        }
        val clientBuilder = OkHttpClient().newBuilder().addInterceptor(header)

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }
}