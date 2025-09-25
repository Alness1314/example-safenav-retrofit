package com.alness.myapplication.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alness.myapplication.R
import com.alness.myapplication.databinding.FragmentEventsBinding
import com.alness.myapplication.databinding.FragmentHomeBinding


class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }


}