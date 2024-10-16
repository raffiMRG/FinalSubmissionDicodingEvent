package com.example.coba1submission.ui.liked

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coba1submission.data.response.Event
import com.example.coba1submission.databinding.ItemRowBinding
import com.example.coba1submission.ui.adapter.Adapter
import com.example.coba1submission.ui.details.DetailsActivity

//class LikedAdapter : ListAdapter<Event, LikedAdapter.LikeViewHolder>(DIFF_CALLBACK) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
//        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return LikeViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
//        val review = getItem(position)
//        holder.bind(review)
//    }
//
//    class LikeViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(review: Event){
//            binding.eventTitle.text = "${review.name}"
//            Glide.with(binding.root)
//                .load("${review.mediaCover}")
//                .into(binding.recImage)
//            this.itemView.setOnClickListener{
//                val intentDetail = Intent(this.itemView.context, DetailsActivity::class.java)
//                intentDetail.putExtra("key_hero", review)
//                this.itemView.context.startActivity(intentDetail)
//            }
//        }
//    }
//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
//            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
//                return oldItem == newItem
//            }
//            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}

class LikedAdapter (private val events: List<Event>) : RecyclerView.Adapter<LikedAdapter.EventViewHolder>() {

    class EventViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.eventTitle.text = event.name
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.recImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount() = events.size
}