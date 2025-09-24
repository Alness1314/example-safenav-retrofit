package com.alness.myapplication.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    private val viewModel: UserDetailViewModel by viewModels() // Hilt VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observa el LiveData de detalle
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.textId.text = user.id
                binding.textFullName.text = user.fullName
                binding.textLabelId.text = "id"
                binding.textLabelEmail.text = "Correo"
                binding.textEmail.text = user.username
                binding.textLabelProfile.text = "Perfil"
                binding.textProfile.text = user.profile
                binding.textLabelFechayhora.text = "Alta"
                binding.textFechayhora.text = user.createdAt
                binding.textLabelEnabled.text = "Habilitado"
                binding.textEnabled.text = if (!user.erased) "Si" else "No"
            } else {
                //binding.text_fullName.text = "No encontrado"
            }
        }

        // carga el detalle
        viewModel.loadUser(userId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}