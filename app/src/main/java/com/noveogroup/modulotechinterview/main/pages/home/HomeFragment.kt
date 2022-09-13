package com.noveogroup.modulotechinterview.main.pages.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.common.android.ext.show
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    override val viewModel: HomeViewModel by viewModel()
    override val navigator: HomeNavigator by lazy { HomeNavigator(navigationController) }

    private val binding by viewBinding(FragmentHomeBinding::inflate)

    private val adapter: DeviceAdapter by lazy {
        DeviceAdapter(
            navigator::openDevice,
            viewModel::deleteDevice
        )
    }

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.homeLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lightButton.setOnClickListener {
                viewModel.enableLightFilter()
            }
            heaterButton.setOnClickListener {
                viewModel.enableHeaterFilter()
            }
            shuttersButton.setOnClickListener {
                viewModel.enableShutterFilter()
            }
            deviceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            deviceRecyclerView.adapter = adapter
        }
        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.errorEvent.observe(viewLifecycleOwner, errorObserver)
        viewModel.loadingState.observe(viewLifecycleOwner, loadingObserver)
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
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

    private val loadingObserver: Observer<Boolean>
        get() = Observer {
            binding.progressBar.show(it)
        }

    private val stateObserver: Observer<HomeState>
        get() = Observer {
            with(binding) {
                lightButton.isSelected = it.isLightSelected
                heaterButton.isSelected = it.isHeaterSelected
                shuttersButton.isSelected = it.isShutterSelected
                adapter.setItems(it.filteredDevices)
            }
        }
}
