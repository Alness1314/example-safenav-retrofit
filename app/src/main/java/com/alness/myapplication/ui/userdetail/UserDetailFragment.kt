package com.alness.myapplication.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alness.myapplication.R
import com.alness.myapplication.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    // Obtener args (Safe Args)
    private val args: UserDetailFragmentArgs by navArgs()
    private val userId: String by lazy { args.userId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ahora binding ya no es null
        binding.tv1.text = userId

        // Aquí podrías pedir el detalle al ViewModel, ej:
        // viewModel.loadUser(userId)
        // viewModel.user.observe(viewLifecycleOwner) { user -> ... }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}