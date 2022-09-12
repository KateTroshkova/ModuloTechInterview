package com.noveogroup.modulotechinterview.main.pages.device

import android.os.Bundle
import android.view.View
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.databinding.FragmentDeviceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceFragment : BaseFragment() {
    override val viewModel: DeviceViewModel by viewModel()
    override val navigator: DeviceNavigator by lazy { DeviceNavigator(navigationController) }

    private val binding by viewBinding(FragmentDeviceBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.deviceLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }
}