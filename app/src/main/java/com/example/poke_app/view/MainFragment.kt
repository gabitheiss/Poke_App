package com.example.poke_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
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


    private val productsObserver = Observer<List<Pokemon>> { newList ->
        adapter.refresh(newList)
    }
    private val errorObserver = Observer<String> { error ->
        Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.idContainer)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        binding = MainFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.pokeResponse.observe(viewLifecycleOwner, productsObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)

        viewModel.fetchAllFromDatabase(requireContext())


        //funcao para buscar os caracteres digitados no input - antes, durante e depois da digitacao
        //pegamos os valores durante a digitacao
        binding.idPlaceholder.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.length > 2) {
                        viewModel.fetchFilteredFromDataBase(requireContext(), it.toString())
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    if (it.isEmpty()) {
                        viewModel.fetchAllFromDatabase(requireContext())
                    }
                }
            }
        })
        //para chamar o bottom sheet, click no botão do menu superior
        binding.idButtonFilters.setOnClickListener { showBottomSheetDialog() }
    }

    fun showBottomSheetDialog() {
        val bottomSheet = FiltersFragment.newInstance()
        bottomSheet.show(parentFragmentManager, "dialog_filters")
    }
}