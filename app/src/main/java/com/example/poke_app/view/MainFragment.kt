package com.example.poke_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poke_app.R
import com.example.poke_app.adapter.AdapterList
import com.example.poke_app.databinding.MainFragmentBinding
import com.example.poke_app.model.Pokemon
import com.example.poke_app.view_model.FiltersViewModel
import com.example.poke_app.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val adapter = AdapterList(mutableListOf())


    private val pokemonsObserver = Observer<List<Pokemon>> { newList ->
        adapter.refresh(newList)
    }
    private val errorObserver = Observer<String> { error ->
        Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
    }
    private val observerLoading = Observer<Boolean> { isLoading ->
        binding.progressBar.visibility = if (isLoading) VISIBLE else INVISIBLE
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.idContainer.layoutManager = LinearLayoutManager(requireContext())
        binding.idContainer.adapter = adapter

        viewModel.pokeResponse.observe(viewLifecycleOwner, pokemonsObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
        viewModel.fetchAllFromServer(requireContext())

        binding.idButtonFilters.setOnClickListener { showBottomSheetDialog() }
    }

    fun showBottomSheetDialog() {
        val bottomSheet = FiltersFragment.newInstance()
        bottomSheet.show(parentFragmentManager, "dialog_filters")
    }
}







