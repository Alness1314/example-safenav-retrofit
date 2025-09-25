package com.alness.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alness.myapplication.R
import com.alness.myapplication.adapters.ModuleAdapter
import com.alness.myapplication.adapters.UserAdapter
import com.alness.myapplication.databinding.FragmentHomeBinding
import com.alness.myapplication.ui.notifications.NotificationsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // <- Hilt-aware ViewModel retrieval
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ModuleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ModuleAdapter { module ->
            when (module.path) {
                "receptionsFragment" -> findNavController().navigate(R.id.receptionsFragment)
                "deliveriesFragment"   -> findNavController().navigate(R.id.deliveriesFragment)
                "eventFragment"  -> findNavController().navigate(R.id.eventFragment)
                "opsVolumetricFragment"    -> findNavController().navigate(R.id.opsVolumetricFragment)
                else -> Snackbar.make(binding.root, "Ruta no configurada: ${module.path}", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.recyclerViewMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewMenu.adapter = adapter

        // Observadores
        homeViewModel.modules.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyView.isVisible = list.isEmpty()
        }

        homeViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeViewModel.UiState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is HomeViewModel.UiState.Success -> {
                    binding.progressBar.isVisible = false
                }
                is HomeViewModel.UiState.Error -> {
                    binding.progressBar.isVisible = false
                    Snackbar.make(binding.root, "Error: ${state.message}", Snackbar.LENGTH_LONG).show()
                }
                else -> Unit
            }
        }

        // Cargar datos
        homeViewModel.loadModules()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}