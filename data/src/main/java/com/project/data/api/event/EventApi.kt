package com.project.data.api.event

import retrofit2.Response
import retrofit2.http.GET

interface EventApi {
    // id,publication_date,dates,title,short_title,slug,place,description,body_text,location,categories,tagline,age_restriction,price,is_free,images,favorites_count,comments_count,site_url,tags,participants&page_size=1&expand=id,publication_date,dates,title,short_title,slug,place,description,body_text,location,categories,tagline,age_restriction,price,is_free,images,favorites_count,comments_count,site_url,tags,participants
    @GET("events/?fields=description,name,id,title")
    suspend fun getEvents(): Response<EventApiResponse>
}