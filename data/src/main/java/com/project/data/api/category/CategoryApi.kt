package com.project.data.api.category

import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {
    @GET("?lang=ru")
    suspend fun getCategories() : Response<List<CategoryApiResponse>>
}