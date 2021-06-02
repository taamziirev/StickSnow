package com.project.sticksnow

import android.app.Application
import com.project.data.repositories.EventRepository
import com.project.data.repositories.category.CategoryRepositoryImpl
import com.project.data.usecases.category.CategoryUseCase
import com.project.data.usecases.category.GetCategoryUseCase
import com.project.data.usecases.category.GetRemoteCategoryUseCase
import com.project.data.usecases.category.UnCategoryUseCase
import com.project.data.usecases.event.EventUseCase
import com.project.data.usecases.event.GetEventUseCase
import com.project.data.usecases.event.GetRemoteEventUseCase
import com.project.data.usecases.event.UnEventUseCase
import com.project.sticksnow.di.ServiceLocator
import com.project.sticksnow.mappers.CategoryWithStatusMapper
import com.project.sticksnow.mappers.EventInListMapper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StickSnowApplication : Application() {
    val getApplication: Application
        get() = this
    private val categoryRepository: CategoryRepositoryImpl
        get() = ServiceLocator.provideCategoryRepository(this)
    val getCategoryUseCase: GetCategoryUseCase
        get() = GetCategoryUseCase(categoryRepository)
    val getRemoteCategoryUseCase: GetRemoteCategoryUseCase
        get() = GetRemoteCategoryUseCase(categoryRepository)
    val categoryUseCase: CategoryUseCase
        get() = CategoryUseCase(categoryRepository)
    val unCategoryUseCase: UnCategoryUseCase
        get() = UnCategoryUseCase(categoryRepository)
    val categoryWithStatusMapper = CategoryWithStatusMapper()

    private val eventRepository: EventRepository
        get() = ServiceLocator.provideEventRepository(this)
    val getEventUseCase: GetEventUseCase
        get() = GetEventUseCase(eventRepository)
    val getRemoteEventUseCase: GetRemoteEventUseCase
        get() = GetRemoteEventUseCase(eventRepository)
    val eventUseCase: EventUseCase
        get() = EventUseCase(eventRepository)
    val unEventUseCase: UnEventUseCase
        get() = UnEventUseCase(eventRepository)
    val eventWithStatusMapper = EventInListMapper()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}