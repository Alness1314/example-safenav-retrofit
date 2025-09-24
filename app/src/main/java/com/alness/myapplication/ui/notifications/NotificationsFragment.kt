package com.alness.myapplication.ui.notifications

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.alness.myapplication.adapters.UserAdapter
import com.alness.myapplication.databinding.FragmentNotificationsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    // <- Hilt-aware ViewModel retrieval
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter { user ->
            // Navegar con Safe Args: genera UsersFragmentDirections
            val action = NotificationsFragmentDirections
                .actionNotificationsFragmentToUserDetailFragment(user.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observadores
        notificationsViewModel.users.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyView.isVisible = list.isEmpty()
        }

        notificationsViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NotificationsViewModel.UiState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NotificationsViewModel.UiState.Success -> {
                    binding.progressBar.isVisible = false
                }
                is NotificationsViewModel.UiState.Error -> {
                    binding.progressBar.isVisible = false
                    Snackbar.make(binding.root, "Error: ${state.message}", Snackbar.LENGTH_LONG).show()
                }
                else -> Unit
            }
        }

        // Cargar datos
        notificationsViewModel.loadUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}