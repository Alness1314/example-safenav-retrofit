package com.alness.myapplication.ui.detailreception

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alness.myapplication.databinding.FragmentDetailReceptionBinding

class DetailReceptionFragment : Fragment() {

    private var _binding: FragmentDetailReceptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailReceptionBinding.inflate(inflater, container, false)
        return binding.root
    }


}