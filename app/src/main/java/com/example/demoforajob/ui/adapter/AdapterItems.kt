package com.example.demoforajob.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoforajob.R
import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.databinding.ItemListBinding
import com.example.demoforajob.zzz_utills.MyDiffUtils
import com.example.demoforajob.zzz_utills.sendOneParametreBlock

class AdapterItems constructor(private var context: Context) :
    RecyclerView.Adapter<AdapterItems.ViewHolder>() {
    private var differ = emptyList<ItemData>()
    private var listenerItem: sendOneParametreBlock<ItemData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ[position])
    }

    fun submitList(ls: List<ItemData>) {
        val diffUtil = MyDiffUtils(differ, ls)
        val difResult = DiffUtil.calculateDiff(diffUtil)
        differ = ls
        difResult.dispatchUpdatesTo(this)
    }

    fun submitListenerItemTouch(block: sendOneParametreBlock<ItemData>) {
        listenerItem = block
    }


    inner class ViewHolder(private var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listenerItem?.invoke(differ[bindingAdapterPosition])
            }
        }

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(data: ItemData) {
            binding.apply {
                Glide.with(context).load(data.imageUri).centerCrop().placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.wrong).into(binding.ivItem)
                binding.tvFirstSeen.text = data.firstSeen
                binding.tvLoaction.text = data.loacation
                binding.tvName.text = data.name
                if (data.status.equals("Alive")) binding.ivAliveDot.background.setTint(Color.GREEN) else if (data.status.equals("Dead")) binding.ivAliveDot.background.setTint(Color.RED) else binding.ivAliveDot.background.setTint(
                    Color.GRAY
                )
                binding.tvAliveAndType.text = data.status + " - " + data.spacies
            }
        }
    }
}