package com.project.data.api

import com.project.data.api.category.CategoryApi
import com.project.data.api.event.EventApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    private fun getRetrofit(endpointURL: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpointURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun createCategoryApi(endpointURL: String): CategoryApi {
        val retrofit = getRetrofit(endpointURL)
        return retrofit.create(CategoryApi::class.java)
    }
    fun createEventApi(endpointURL: String): EventApi {
        val retrofit = getRetrofit(endpointURL)
        return retrofit.create(EventApi::class.java)
    }
}