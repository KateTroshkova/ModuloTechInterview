package com.noveogroup.modulotechinterview.main.pages.device_shutter

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.android.ext.show
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.databinding.FragmentShuttersBinding
import com.noveogroup.modulotechinterview.main.AppBarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShutterFragment : BaseFragment() {

    private val appBarViewModel: AppBarViewModel by activityViewModels()

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
        appBarViewModel.hideMenu()
        with(binding) {
            titleTextView.text = args.device.deviceName
            shuttersLayout.seekBar.onProgressChangeListener = { viewModel.updatePosition(it) }
        }
        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.errorEvent.observe(viewLifecycleOwner, errorObserver)
        viewModel.loadingState.observe(viewLifecycleOwner, loadingObserver)
    }

    private val stateObserver: Observer<ShutterState>
        get() = Observer {
            with(binding) {
                shuttersLayout.seekBar.progress = it.position
                shuttersLayout.seekBar.barProgressColor = ContextCompat.getColor(
                    requireContext(),
                    when {
                        it.position < 33 -> R.color.low
                        it.position in 33..66 -> R.color.accent
                        else -> R.color.high
                    }
                )
                shuttersLayout.currentValueTextView.text = it.position.toString()
            }
        }

    private val errorObserver: Observer<Throwable>
        get() = Observer {
            Snackbar.make(
                binding.deviceLayout,
                it?.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

    private val loadingObserver: Observer<Boolean>
        get() = Observer {
            binding.progressBar.show(it)
        }
}