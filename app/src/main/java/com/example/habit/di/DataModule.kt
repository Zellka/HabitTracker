package com.example.habit.di

import android.content.Context
import androidx.room.Room
import com.example.domain.repository.HabitsRepository
import com.example.habit.data.api.ApiService
import com.example.habit.data.db.AppDatabase
import com.example.habit.data.db.HabitDao
import com.example.habit.data.repository.HabitsRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
    fun provideHabitsRepository(api: ApiService, dao: HabitDao): HabitsRepository {
        return HabitsRepositoryImpl(api, dao)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideHabitDao(context: Context): HabitDao {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "db-test"
        ).fallbackToDestructiveMigration().build().habitDao()
    }

    private fun proceed(chain: Interceptor.Chain, request: Request): Response {
        return try {
            chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()
            proceed(chain, request)
        }
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "token")
                .build()
            proceed(chain, request)
        }

        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(headerInterceptor).build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}