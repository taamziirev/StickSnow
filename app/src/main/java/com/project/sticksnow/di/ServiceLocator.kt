package com.project.sticksnow.di

import android.content.Context
import com.project.data.api.NetworkModule
import com.project.data.db.AppDatabase
import com.project.data.mappers.category.CategoryApiResponseMapper
import com.project.data.mappers.category.CategoryEntityMapper
import com.project.data.mappers.event.EventApiResponseMapper
import com.project.data.mappers.event.EventEntityMapper
import com.project.data.repositories.category.CategoryLocalDataSource
import com.project.data.repositories.category.CategoryLocalDataSourceImpl
import com.project.data.repositories.category.CategoryRemoteDataSourceImpl
import com.project.data.repositories.category.CategoryRepositoryImpl
import com.project.data.repositories.event.EventLocalDataSource
import com.project.data.repositories.event.EventLocalDataSourceImpl
import com.project.data.repositories.event.EventRemoteDataSourceImpl
import com.project.data.repositories.event.EventRepositoryImpl
import kotlinx.coroutines.Dispatchers

object ServiceLocator {
    private var database: AppDatabase? = null
    private val networkModule by lazy {
        NetworkModule()
    }
    private val categoryEntityMapper by lazy {
        CategoryEntityMapper()
    }

    private val eventEntityMapper by lazy {
        EventEntityMapper()
    }

    @Volatile
    var categoryRepository: CategoryRepositoryImpl? = null

    @Volatile
    var eventRepository: EventRepositoryImpl? = null

    fun provideCategoryRepository(context: Context): CategoryRepositoryImpl {
        synchronized(this) {
            return categoryRepository ?: createCategoryRepository(context)
        }
    }

    private fun createCategoryRepository(context: Context): CategoryRepositoryImpl {
        val newRepo =
            CategoryRepositoryImpl(
                createCategoryLocalDataSource(context),
                CategoryRemoteDataSourceImpl(
                    networkModule.createCategoryApi("https://kudago.com/public-api/v1.4/event-categories/"),
                    CategoryApiResponseMapper()
                )
            )
        categoryRepository = newRepo
        return newRepo
    }

    private fun createCategoryLocalDataSource(context: Context): CategoryLocalDataSource {
        val database = database ?: createDataBase(context)
        return CategoryLocalDataSourceImpl(
            database.categoryDao(),
            Dispatchers.IO,
            categoryEntityMapper
        )
    }

    fun provideEventRepository(context: Context): EventRepositoryImpl {
        synchronized(this) {
            return eventRepository ?: createEventRepository(context)
        }
    }

    private fun createEventRepository(context: Context): EventRepositoryImpl {
        val newRepo =
            EventRepositoryImpl(
                createEventLocalDataSource(context),
                EventRemoteDataSourceImpl(
                    networkModule.createEventApi("https://kudago.com/public-api/v1.4/"),
                    EventApiResponseMapper()
                )
            )
        eventRepository = newRepo
        return newRepo
    }

    private fun createEventLocalDataSource(context: Context): EventLocalDataSource {
        val database = database ?: createDataBase(context)
        return EventLocalDataSourceImpl(
            database.eventDao(),
            Dispatchers.IO,
            eventEntityMapper
        )
    }
    private fun createDataBase(context: Context): AppDatabase {
        val result = AppDatabase.getDatabase(context)
        database = result
        return result
    }
}