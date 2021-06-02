package com.project.sticksnow.presentation.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.sticksnow.LayoutUtils
import com.project.sticksnow.R
import com.project.sticksnow.StickSnowApplication
import com.project.sticksnow.databinding.FragmentEventsItemListBinding
import com.project.sticksnow.entities.EventInList
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class EventFragment : Fragment(R.layout.fragment_events_item_list) {

    private var _binding: FragmentEventsItemListBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventsAdapter: EventRecyclerViewAdapter
    private val eventViewModel: EventViewModel by viewModels {
        EventViewModel.EventViewModelFactory(
            ((requireActivity().application) as StickSnowApplication).getEventUseCase,
            ((requireActivity().application) as StickSnowApplication).eventUseCase,
            ((requireActivity().application) as StickSnowApplication).getRemoteEventUseCase,
            ((requireActivity().application) as StickSnowApplication).unEventUseCase,
            ((requireActivity().application) as StickSnowApplication).eventWithStatusMapper
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.plant(Timber.DebugTree());
        _binding = FragmentEventsItemListBinding.inflate(
            inflater, container, false
        )
        eventsAdapter = EventRecyclerViewAdapter(requireContext(), object : EventRecyclerViewAdapter.ActionClickListener {
            override fun event(event: EventInList) {
                eventViewModel.event(event)
            }
            override fun unEvent(event: EventInList) {
                eventViewModel.unEvent(event)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventViewModel.dataLoading.observe(viewLifecycleOwner, { loading ->
            binding.pbLoading.isVisible = loading
        })
        binding.list.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }
        eventViewModel.error.observe(viewLifecycleOwner, {
            binding.textViewError.text = it
            LayoutUtils.crossFade(binding.list, binding.textViewError)
        })
        eventViewModel.getEvents()
        eventViewModel.events.observe(viewLifecycleOwner, { list ->
            eventsAdapter.submitUpdate(list)
            list.forEach{
                println(it.toString())
            }
        })
    }

    companion object {
        val TAG: String = EventFragment::class.java.simpleName
        fun newInstance() = EventFragment()
    }
}