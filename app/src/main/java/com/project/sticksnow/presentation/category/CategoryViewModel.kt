package com.project.sticksnow.presentation.category

import android.app.Application
import androidx.lifecycle.*
import com.project.data.common.Result
import com.project.data.entities.Category
import com.project.data.usecases.category.CategoryUseCase
import com.project.data.usecases.category.GetCategoryUseCase
import com.project.data.usecases.category.GetRemoteCategoryUseCase
import com.project.data.usecases.category.UnCategoryUseCase
import com.project.sticksnow.entities.CategoryWithStatus
import com.project.sticksnow.mappers.CategoryWithStatusMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class CategoryViewModel(
    application: Application,
    private val getCategoriesUseCase: GetCategoryUseCase,
    private val getRemoteCategoriesUseCase: GetRemoteCategoryUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val unCategoryUseCase: UnCategoryUseCase,
    private val mapper: CategoryWithStatusMapper
) : AndroidViewModel(application) {

    private val _dataLoading = MutableLiveData(true)
    var dataLoading: LiveData<Boolean> = _dataLoading

    private val _categories = MutableLiveData<List<CategoryWithStatus>>()
    val categories = _categories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var _remoteCategories = arrayListOf<Category>()

    fun getCategories() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val categoryResult = getRemoteCategoriesUseCase.invoke()) {
                is Result.Success -> {
                    _remoteCategories.clear()
                    _remoteCategories.addAll(categoryResult.data)

                    val categoriesFlow = getCategoriesUseCase.invoke()
                    categoriesFlow.collect { categorise ->
                        categories.value = mapper.fromCategoryToCategoryWithStatus(_remoteCategories, categorise)
                        _dataLoading.postValue(false)
                    }
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    categories.value = emptyList()
                    _error.postValue(categoryResult.exception.message)
                }
            }
        }
    }

    fun category(category: CategoryWithStatus) {
        viewModelScope.launch {
            categoryUseCase.invoke(mapper.fromCategoryWithStatusToCategory(category))
        }
    }

    fun unCategory(category: CategoryWithStatus) {
        viewModelScope.launch {
            unCategoryUseCase.invoke(mapper.fromCategoryWithStatusToCategory(category))
        }
    }

    class CategoryViewModelFactory(
        private val application: Application,
        private val getCategoriesUseCase: GetCategoryUseCase,
        private val categoryUseCase: CategoryUseCase,
        private val getRemoteCategoryUseCase: GetRemoteCategoryUseCase,
        private val unCategoryUseCase: UnCategoryUseCase,
        private val mapper: CategoryWithStatusMapper
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryViewModel(
                application,
                getCategoriesUseCase,
                getRemoteCategoryUseCase,
                categoryUseCase,
                unCategoryUseCase,
                mapper
            ) as T
        }
    }
}