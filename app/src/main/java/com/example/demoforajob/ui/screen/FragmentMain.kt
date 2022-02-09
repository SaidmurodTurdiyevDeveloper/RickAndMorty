package com.example.demoforajob.ui.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.demoforajob.R
import com.example.demoforajob.data.repository.RepositoryMainImplament
import com.example.demoforajob.data.source.local.room.ApplicationDatabase
import com.example.demoforajob.data.source.remote.RemoteRetrofit
import com.example.demoforajob.databinding.MainLayoutBinding
import com.example.demoforajob.domen.impl.UseCaseMainImplament
import com.example.demoforajob.ui.adapter.AdapterItems
import com.example.demoforajob.viewModel.MyViewModelFactory
import com.example.demoforajob.viewModel.ViewModelMain
import com.example.demoforajob.viewModel.impl.ViewModelMainImplament
import com.example.demoforajob.zzz_utills.loadOnlyOneTimeObserver
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


class FragmentMain : Fragment(R.layout.main_layout) {
    private val binding: MainLayoutBinding by viewBinding()

    private lateinit var viewModel: ViewModelMain
    private lateinit var viewModelFactory: MyViewModelFactory
    private lateinit var adapter: AdapterItems
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = AdapterItems(requireContext())
        val database = ApplicationDatabase.init(requireContext())
        val api = RemoteRetrofit.api
        val repositoryMain = RepositoryMainImplament(api, database.getDao())
        val useCaseMain = UseCaseMainImplament(repositoryMain)
        viewModelFactory = MyViewModelFactory(useCaseMain)
        viewModel = ViewModelProvider(this, viewModelFactory)[ViewModelMainImplament::class.java]
        loadListeners()
        loadObservers()
        viewModel.loadItems()
    }

    private fun loadListeners() {
        binding.spLayout.setOnRefreshListener {
            viewModel.loadItems()
        }
        binding.igbBack.setOnClickListener {
            viewModel.back()
        }
        adapter.submitListenerItemTouch {
            viewModel.itemClick(it)
        }
        binding.list.layoutManager = LinearLayoutManager(requireActivity())
        binding.list.adapter = adapter
    }

    private fun loadObservers() {
        viewModel.backLiveData.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
        viewModel.loadItemsLiveData.observe(viewLifecycleOwner) {
            Timber.d("Load adapter")
            binding.spLayout.isRefreshing = false
            adapter.submitList(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            binding.spLayout.isRefreshing = false
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.snackBarLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                binding.spLayout.isRefreshing = false
                Snackbar.make(binding.defMainLayout, this, Snackbar.LENGTH_LONG).setAction("Refresh") {
                    event.block?.invoke("refresh")
                }.show()
            }
        }
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            binding.spLayout.isRefreshing = false
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        viewModel.openGoogle.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://rickandmortyapi.com/"))
                startActivity(browserIntent)
            }
        }
    }
}