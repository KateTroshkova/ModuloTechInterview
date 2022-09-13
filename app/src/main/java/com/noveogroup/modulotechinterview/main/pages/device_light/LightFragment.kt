package com.noveogroup.modulotechinterview.main.pages.device_light

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.android.ext.show
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.common.listener.SimpleSeekBarChangeListener
import com.noveogroup.modulotechinterview.databinding.FragmentLightBinding
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.main.AppBarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LightFragment : BaseFragment() {

    private val appBarViewModel: AppBarViewModel by activityViewModels()

    override val viewModel: LightViewModel by viewModel()
    override val navigator: LightNavigator by lazy { LightNavigator(navigationController) }

    val args by navArgs<LightFragmentArgs>()

    private val binding by viewBinding(FragmentLightBinding::inflate)

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
            lightLayout.onOffSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateMode(isChecked)
            }
            lightLayout.lightSeekBar.setOnSeekBarChangeListener(
                object :
                    SimpleSeekBarChangeListener() {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        viewModel.updateIntensity(progress)
                    }
                }
            )
        }
        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.errorEvent.observe(viewLifecycleOwner, errorObserver)
        viewModel.loadingState.observe(viewLifecycleOwner, loadingObserver)
    }

    private val stateObserver: Observer<LightState>
        get() = Observer {
            with(binding) {
                lightLayout.onOffSwitch.isChecked = it.mode == DeviceMode.ON
                lightLayout.lightSeekBar.progress = it.intensity
                lightLayout.progressView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        when {
                            it.intensity < 33 -> R.color.low
                            it.intensity in 33..66 -> R.color.accent
                            else -> R.color.high
                        }
                    )
                )
                lightLayout.currentValueTextView.text = it.intensity.toString()
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