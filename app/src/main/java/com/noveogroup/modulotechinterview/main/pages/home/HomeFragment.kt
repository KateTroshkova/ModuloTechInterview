package com.noveogroup.modulotechinterview.main.pages.home

import android.os.Bundle
import android.view.View
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
}