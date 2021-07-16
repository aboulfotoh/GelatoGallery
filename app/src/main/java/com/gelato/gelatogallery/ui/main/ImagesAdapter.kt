package com.gelato.gelatogallery.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.databinding.ItemImageBinding

class ImagesAdapter :
    PagingDataAdapter<ImageItem, ImagesAdapter.ViewHolder>(ImagesDiff()) {

    private var mListener: OnItemClickListener? = null

    class ImagesDiff : DiffUtil.ItemCallback<ImageItem>() {

        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.form(
            parent
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, mListener!!)
    }

    class ViewHolder
    constructor(
        val
        binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageItem, mListener: OnItemClickListener) {
            binding.image = item
            binding.executePendingBindings()
            binding.ivImage.setOnClickListener {
                mListener.onItemClicked(item)
            }
        }

        companion object {
            fun form(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(inflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: ImageItem)
    }

    fun setOnItemClickListener(mListener: OnItemClickListener) {
        this.mListener = mListener
    }
}
