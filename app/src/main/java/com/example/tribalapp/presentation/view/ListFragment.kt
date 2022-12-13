package com.example.tribalapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tribalapp.R
import com.example.tribalapp.databinding.FragmentListBinding
import com.example.tribalapp.presentation.adapter.DetailsAdapter
import com.example.tribalapp.presentation.adapter.listAdapter
import com.example.tribalapp.presentation.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels ()
    private var listAdapter = listAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter.setOnItemClickListener { item, adapterPosition ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(item))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()

        viewModel.categoriesState.observe(viewLifecycleOwner) { state ->
            binding.swipeRefreshLayout.isRefreshing = false
            when (state) {
                is ListViewModel.UiState.IsLoading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is ListViewModel.UiState.IsError -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(context, R.string.error_api, Toast.LENGTH_SHORT).show()
                }
                is ListViewModel.UiState.Data -> {
                    binding.loading.visibility = View.GONE
                    //Toast.makeText(context, R.string.success_api, Toast.LENGTH_SHORT).show()
                    state.data?.let {
                        listAdapter.submitList(it)
                        binding.rvCategory.scheduleLayoutAnimation()
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.purple_200)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.getCategories()
        }
    }

    fun setupRecyclerView() {
        binding.rvCategory.adapter = listAdapter
        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}