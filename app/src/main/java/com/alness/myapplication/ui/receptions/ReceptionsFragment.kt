package com.alness.myapplication.ui.receptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alness.myapplication.R
import com.alness.myapplication.databinding.FragmentEventsBinding
import com.alness.myapplication.databinding.FragmentReceptionsBinding


class ReceptionsFragment : Fragment() {
    private var _binding: FragmentReceptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

}