package com.gelato.gelatogallery.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gelato.gelatogallery.databinding.LoadStateFooterViewItemBinding


class LoadStatusAdapter(private val mListener: OnItemClickListener) :
    LoadStateAdapter<LoadStatusAdapter.ViewHolder>() {


    class ViewHolder
    constructor(
        val
        binding: LoadStateFooterViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun form(parent: ViewGroup, mListener: OnItemClickListener): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = LoadStateFooterViewItemBinding.inflate(inflater, parent, false)
                binding.retryButton.setOnClickListener {
                    mListener.onItemClicked()
                }
                return ViewHolder(
                    binding
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked()
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder.form(parent,mListener!!)
    }
}
