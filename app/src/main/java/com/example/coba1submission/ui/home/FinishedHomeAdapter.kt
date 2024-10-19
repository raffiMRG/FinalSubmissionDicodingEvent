package com.example.coba1submission.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.ItemRowBinding
import com.example.coba1submission.ui.details.DetailsActivity

class FinishedHomeAdapter : ListAdapter<ListEventsItem, FinishedHomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ListEventsItem){
            binding.eventTitle.text = review.name
            Glide.with(binding.root)
                .load(review.imageLogo)
                .into(binding.recImage)
            this.itemView.setOnClickListener{
                val intentDetail = Intent(this.itemView.context, DetailsActivity::class.java)
                intentDetail.putExtra("key_hero", review)
                this.itemView.context.startActivity(intentDetail)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}