package com.alness.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alness.myapplication.R
import com.alness.myapplication.databinding.ItemCardBinding
import com.alness.myapplication.models.ModuleResponse

class ModuleAdapter(private val onClick: (ModuleResponse) -> Unit = {}): ListAdapter<ModuleResponse, ModuleAdapter.ViewHolder>(
    DiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ModuleResponse>() {
        override fun areItemsTheSame(old: ModuleResponse, new: ModuleResponse) = old.id == new.id
        override fun areContentsTheSame(old: ModuleResponse, new: ModuleResponse) = old == new
    }

    inner class ViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModuleResponse) {

            val resId = when (item.icon) {
                "ic_reception" -> R.drawable.ic_reception // Correcto
                "ic_delivery" -> R.drawable.ic_delivery     // Correcto
                "ic_ops_vol" -> R.drawable.ic_ops_vol     // Correcto
                "ic_events" -> R.drawable.ic_events       // Correcto
                else -> R.drawable.baseline_build_24                      // Icono por defecto
            }

            binding.textTitle.text = item.title
            binding.iconModule.setImageResource(resId)
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}