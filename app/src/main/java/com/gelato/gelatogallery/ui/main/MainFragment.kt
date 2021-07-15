package com.gelato.gelatogallery.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.gelato.gelatogallery.R
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.databinding.MainFragmentBinding
import com.gelato.gelatogallery.utils.ErrorType
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.no_data_layout.view.*
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(), ImagesAdapter.OnItemClickListener,
    LoadStatusAdapter.OnItemClickListener {

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
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {

            }
        }
        lifecycleScope.launch {
            viewModel.getImages().collect {
                Toast.makeText(requireContext(),"Got images",Toast.LENGTH_LONG).show()
                adapter.submitData(it)
                adapter.notifyDataSetChanged()
            }
        }


        /*lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { rv_list.scrollToPosition(0) }
        }*/

        binding.rvList.apply {
            //adapter = this@MainFragment.adapter
            adapter = this@MainFragment.adapter.withLoadStateHeaderAndFooter(
                header = LoadStatusAdapter(this@MainFragment),
                footer = LoadStatusAdapter(this@MainFragment)
            )
        }

        viewModel.mutableErrorType.observe(viewLifecycleOwner, { errorType ->
            when (errorType) {
                ErrorType.NETWORK -> {

                }
                ErrorType.TIMEOUT -> {

                }
                ErrorType.SESSION_EXPIRED -> {

                }
                ErrorType.UNKNOWN -> {

                }
                ErrorType.NO_INTERNET -> {

                }
            }
        })
    }

    override fun onItemClicked(item: ImageItem) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToFullscreenImageFragment(item))
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            //binding.emptyList.visibility = View.VISIBLE
            binding.noData.pbLoading.visibility = View.VISIBLE
            binding.noData.tvNoData.visibility = View.VISIBLE
            binding.noData.ivNoData.visibility = View.VISIBLE
            binding.rvList.visibility = View.GONE
        } else {
            binding.noData.pbLoading.visibility = View.GONE
            binding.noData.tvNoData.visibility = View.GONE
            binding.noData.ivNoData.visibility = View.GONE
            //binding.emptyList.visibility = View.GONE
            binding.rvList.visibility = View.VISIBLE
        }
    }

    override fun onItemClicked() {

    }

}