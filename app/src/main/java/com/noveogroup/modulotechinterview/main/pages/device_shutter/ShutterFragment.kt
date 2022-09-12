package com.noveogroup.modulotechinterview.main.pages.device_shutter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.databinding.FragmentShuttersBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShutterFragment : BaseFragment() {
    override val viewModel: ShutterViewModel by viewModel()
    override val navigator: ShutterNavigator by lazy { ShutterNavigator(navigationController) }

    val args by navArgs<ShutterFragmentArgs>()

    private val binding by viewBinding(FragmentShuttersBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.deviceLayout.applyStatusBarInsetWithPadding()
    }

    override fun onResume() {
        super.onResume()
        viewModel.applyState(args.device)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            titleTextView.text = args.device.deviceName
            shuttersLayout.seekBar.onProgressChangeListener = { viewModel.updatePosition(it) }
        }
        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
    }

    private val stateObserver: Observer<ShutterState>
        get() = Observer {
            with(binding) {
                shuttersLayout.seekBar.progress = it.position
            }
        }
}