package com.project.sticksnow.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.sticksnow.LayoutUtils
import com.project.sticksnow.R
import com.project.sticksnow.StickSnowApplication
import com.project.sticksnow.databinding.FragmentRecyclerCategoriesSelectedBinding
import com.project.sticksnow.entities.CategoryWithStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecyclerCategoriesSelected : Fragment(R.layout.fragment_recycler_categories_selected) {

    private var _binding: FragmentRecyclerCategoriesSelectedBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoriesAdapter: CategoryAdapter
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModel.CategoryViewModelFactory(
            ((requireActivity().application) as StickSnowApplication).getApplication,
            ((requireActivity().application) as StickSnowApplication).getCategoryUseCase,
            ((requireActivity().application) as StickSnowApplication).categoryUseCase,
            ((requireActivity().application) as StickSnowApplication).getRemoteCategoryUseCase,
            ((requireActivity().application) as StickSnowApplication).unCategoryUseCase,
            ((requireActivity().application) as StickSnowApplication).categoryWithStatusMapper
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerCategoriesSelectedBinding.inflate(
                inflater, container, false
        )
        categoriesAdapter = CategoryAdapter(requireContext(), object : CategoryAdapter.ActionClickListener {
            override fun category(category: CategoryWithStatus) {
                categoryViewModel.category(category)
            }
            override fun unCategory(category: CategoryWithStatus) {
                categoryViewModel.unCategory(category)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel.dataLoading.observe(viewLifecycleOwner, { loading ->
            binding.pbLoading.isVisible = loading
        })
        binding.recyclerViewSelected.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = categoriesAdapter
        }
        categoryViewModel.error.observe(viewLifecycleOwner, {
            binding.textViewError.text = it
            LayoutUtils.crossFade(binding.recyclerViewSelected, binding.textViewError)
        })
        categoryViewModel.getCategories()
        categoryViewModel.categories.observe(viewLifecycleOwner, {
            categoriesAdapter.submitUpdate(it)
        })
    }

    companion object {
        val TAG: String = RecyclerCategoriesSelected::class.java.simpleName
        fun newInstance() = RecyclerCategoriesSelected()
    }
}



















