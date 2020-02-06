package by.enolizard.newsaggregator.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.enolizard.newsaggregator.App
import by.enolizard.newsaggregator.base.PagingState
import by.enolizard.newsaggregator.base.State
import by.enolizard.newsaggregator.databinding.NewsFragmentBinding
import by.enolizard.newsaggregator.presentation.adapters.FeedsPagedAdapter
import by.enolizard.newsaggregator.presentation.showToast
import by.enolizard.newsaggregator.presentation.viewmodels.NewsViewModel
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: NewsFragmentBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!!.application as App).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsViewModel::class.java)
        setupListeners()
    }

    private fun setupListeners() {
        val feedListAdapter = FeedsPagedAdapter { viewModel.retry() }

        with(binding.rvFeeds) {
            adapter = feedListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.paginatedState.observe(viewLifecycleOwner, Observer {
            if (it is PagingState.Error)
                context?.showToast(it.e.message ?: "Internet error")
            feedListAdapter.updateState(it)
        })

        viewModel.feeds.observe(viewLifecycleOwner, Observer {
            feedListAdapter.submitList(it)
        })

    }
}
