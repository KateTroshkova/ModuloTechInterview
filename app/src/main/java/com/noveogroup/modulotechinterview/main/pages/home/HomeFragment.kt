package com.noveogroup.modulotechinterview.main.pages.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    override val viewModel: HomeViewModel by viewModel()
    override val navigator: HomeNavigator by lazy { HomeNavigator(navigationController) }

    private val binding by viewBinding(FragmentHomeBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.homeLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lightButton.setOnClickListener {
                navigator.openDevice()
            }
        }
        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.errorEvent.observe(viewLifecycleOwner, errorObserver)
    }

    private val errorObserver: Observer<Throwable>
        get() = Observer {
            Snackbar.make(
                binding.homeLayout,
                it?.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
}