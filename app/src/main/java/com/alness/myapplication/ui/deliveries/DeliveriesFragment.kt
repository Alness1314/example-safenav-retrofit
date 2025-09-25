package com.alness.myapplication.ui.deliveries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alness.myapplication.R
import com.alness.myapplication.databinding.FragmentDeliveriesBinding
import com.alness.myapplication.databinding.FragmentEventsBinding


class DeliveriesFragment : Fragment() {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveriesBinding.inflate(inflater, container, false)
        return binding.root
    }

}