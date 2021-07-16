package com.gelato.gelatogallery.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.gelato.gelatogallery.R
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.databinding.MainFragmentBinding
import com.gelato.gelatogallery.utils.ErrorType
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collectLatest

class MainFragment : Fragment(), ImagesAdapter.OnItemClickListener{

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModel<MainViewModel>()
    private var adapter = ImagesAdapter()
    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setOnItemClickListener(this)
        adapter.addLoadStateListener { loadState ->
            // show empty list
            showHideList()

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {

            }
        }
        binding.rvList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStatusAdapter { adapter.retry() },
            footer = LoadStatusAdapter { adapter.retry() }
        )

        viewModel.mutableErrorType.observe(viewLifecycleOwner, { errorType ->
            when (errorType) {
                ErrorType.NETWORK -> {
                    showHideList()
                }
                ErrorType.TIMEOUT -> {
                    showHideList()
                }
                ErrorType.SESSION_EXPIRED -> {
                    showHideList()
                }
                ErrorType.UNKNOWN -> {
                    showHideList()
                }
                ErrorType.NO_INTERNET -> {
                    showHideList()
                }
            }
        })

        binding.noData.tvNoData.setOnClickListener {
            binding.noData.pbLoading.visibility = View.VISIBLE
            binding.noData.tvNoData.visibility = View.GONE
            binding.noData.ivNoData.visibility = View.GONE
            loadImages()
        }
    }

    override fun onResume() {
        super.onResume()
        if (adapter.itemCount==0)
            loadImages()
    }

    private fun loadImages(){
        lifecycleScope.launch {
            viewModel.getImages().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onItemClicked(item: ImageItem) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToImageFragment(item))
    }

    private fun showHideList() {
        if (adapter.itemCount == 0){
            binding.noData.pbLoading.visibility = View.GONE
            binding.noData.tvNoData.visibility = View.VISIBLE
            binding.noData.ivNoData.visibility = View.VISIBLE
            binding.rvList.visibility = View.GONE
        } else {
            binding.noData.pbLoading.visibility = View.GONE
            binding.noData.tvNoData.visibility = View.GONE
            binding.noData.ivNoData.visibility = View.GONE
            binding.rvList.visibility = View.VISIBLE
        }
    }
}