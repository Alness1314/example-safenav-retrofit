package com.alness.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alness.myapplication.databinding.ItemUserBinding
import com.alness.myapplication.models.UserResponse

class UserAdapter( private val onClick: (UserResponse) -> Unit = {}): ListAdapter<UserResponse, UserAdapter.UserViewHolder>(DiffCallback()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserResponse) {
            binding.tvName.text = item.fullName
            binding.tvEmail.text = item.username
            binding.tvProfile.text = item.profile
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(old: UserResponse, new: UserResponse) = old.id == new.id
        override fun areContentsTheSame(old: UserResponse, new: UserResponse) = old == new
    }
}