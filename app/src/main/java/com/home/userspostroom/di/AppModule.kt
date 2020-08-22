package com.home.userspostroom.di

import androidx.room.Room
import co.com.ceiba.mobile.pruebadeingreso.data.database.UsersDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.network.APIService
import co.com.ceiba.mobile.pruebadeingreso.data.network.Endpoints.URL_BASE
import co.com.ceiba.mobile.pruebadeingreso.data.repository.Repository
import co.com.ceiba.mobile.pruebadeingreso.data.repository.RepositoryDB
import co.com.ceiba.mobile.pruebadeingreso.data.repository.RepositoryDBImpl
import co.com.ceiba.mobile.pruebadeingreso.data.repository.RepositoryImpl
import co.com.ceiba.mobile.pruebadeingreso.ui.userlist.UsersViewModel
import com.home.userspostroom.ui.userdetail.PostsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
        viewModel { UsersViewModel(get(), get()) }
        viewModel { PostsViewModel(get()) }
    }

    val apiServiceModule = module {
        single { createOkHttpClient() }
        single { createWebService<APIService>(get(), URL_BASE)
        }
    }

    val postUserDBModule = module {
        single<RepositoryDB> { RepositoryDBImpl(get()) }
        single<Repository> { RepositoryImpl(get()) }
        single { Room.databaseBuilder(get(), UsersDatabase::class.java, "postUserdb").build() }
        single{get<UsersDatabase>().userDao()}
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build()
    }

    inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(T::class.java)
    }

    val appModule = listOf(postUserDBModule, viewModelModule,
            apiServiceModule)

