package com.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.common.Constants
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.utils.NetworkHelper
import com.example.kutumbreadsms.data.source.db.RoomDataSource
import com.example.navigithubpr.data.source.TaskRepository
import com.example.navigithubpr.data.source.remote.ApiHelper
import com.example.navigithubpr.data.source.remote.ApiHelperImpl
import com.example.navigithubpr.data.source.remote.ApiService
import com.example.navigithubpr.data.source.remote.RemoteDataSource
import com.example.truecreditslist.db.PrLocalDb
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
e
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelperImpl(apiService);
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): PrLocalDb {
        val result = Room.databaseBuilder(
            context.applicationContext,
            PrLocalDb::class.java, "sms_local.db"
        ).build()
        return result
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(prLocalDb: PrLocalDb): TaskLocalDataSource {
        return RoomDataSource(prLocalDb.prDao());
    }

    @Provides
    @Singleton
    fun providePrRepository(prRemoteDataSource: TaskRemoteDataSource,
                            prLocalDataSource: TaskLocalDataSource,
                            networkHelper: NetworkHelper
    ): TaskRepository {
        return DefaultPreRepository(prRemoteDataSource, prLocalDataSource, networkHelper);
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiHelper: ApiHelper): TaskRemoteDataSource {
        return RemoteDataSource(apiHelper);
    }


}