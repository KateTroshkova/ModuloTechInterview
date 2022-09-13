package com.noveogroup.modulotechinterview.main.pages.device_heater

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.android.ext.show
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.common.listener.SimpleSeekBarChangeListener
import com.noveogroup.modulotechinterview.databinding.FragmentHeaterBinding
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeaterFragment : BaseFragment() {
    override val viewModel: HeaterViewModel by viewModel()
    override val navigator: HeaterNavigator by lazy { HeaterNavigator(navigationController) }

    val args by navArgs<HeaterFragmentArgs>()

    private val binding by viewBinding(FragmentHeaterBinding::inflate)

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
            heaterLayout.onOffSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateMode(isChecked)
            }
            heaterLayout.heaterSeekBar.setOnSeekBarChangeListener(
                object :
                    SimpleSeekBarChangeListener() {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        viewModel.updateTemperature(progress.toFloat() / 2 + MIN_VALUE)
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

    private val stateObserver: Observer<HeaterState>
        get() = Observer {
            with(binding) {
                heaterLayout.onOffSwitch.isChecked = it.mode == DeviceMode.ON
                heaterLayout.heaterSeekBar.progress = ((it.temperature - 7) * 2).toInt()
                heaterLayout.progressView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        when {
                            it.temperature < 14 -> R.color.low
                            it.temperature in 14.0..21.0 -> R.color.normal
                            else -> R.color.high
                        }
                    )
                )
                heaterLayout.currentValueTextView.text = it.temperature.toString()
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

    companion object {
        private const val MIN_VALUE = 7
    }
}
