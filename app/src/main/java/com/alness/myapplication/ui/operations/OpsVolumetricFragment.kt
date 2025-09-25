package com.alness.myapplication.ui.operations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alness.myapplication.R
import com.alness.myapplication.databinding.FragmentHomeBinding
import com.alness.myapplication.databinding.FragmentOpsVolumetricBinding


class OpsVolumetricFragment : Fragment() {
    private var _binding: FragmentOpsVolumetricBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpsVolumetricBinding.inflate(inflater, container, false)
        return binding.root
    }


}