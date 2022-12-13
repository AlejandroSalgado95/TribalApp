package com.example.tribalapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tribalapp.R
import com.example.tribalapp.databinding.FragmentDetailsBinding
import com.example.tribalapp.databinding.FragmentListBinding
import com.example.tribalapp.presentation.adapter.DetailsAdapter
import com.example.tribalapp.presentation.viewmodel.DetailsViewModel
import com.example.tribalapp.presentation.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels ()
    @Inject
    lateinit var detailsAdapter: DetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()

        activity?.actionBar?.title = viewModel.category

        viewModel.jokeLocalList.observe(viewLifecycleOwner) { jokeLocalList ->
            val myJokeLocalList = jokeLocalList ?: listOf()
            detailsAdapter.submitList(myJokeLocalList)
        }

        viewModel.jokeState.observe(viewLifecycleOwner) { state ->
            binding.swipeRefreshLayout.isRefreshing = false
            when (state) {
                is DetailsViewModel.UiState.IsLoading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is DetailsViewModel.UiState.IsError -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(context, R.string.error_api, Toast.LENGTH_SHORT).show()
                }
                is DetailsViewModel.UiState.Data -> {
                    binding.loading.visibility = View.GONE
                    //Toast.makeText(context, R.string.success_api, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.purple_200)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.getRandomJokeOfCategory()
        }
    }

    fun setupRecyclerView() {
        binding.rvJoke.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJoke.adapter = detailsAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}