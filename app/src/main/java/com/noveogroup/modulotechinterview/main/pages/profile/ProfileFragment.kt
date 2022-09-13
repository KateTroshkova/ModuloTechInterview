package com.noveogroup.modulotechinterview.main.pages.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.android.ext.show
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.common.listener.SimpleTextWatcher
import com.noveogroup.modulotechinterview.databinding.FragmentProfileBinding
import com.noveogroup.modulotechinterview.main.AppBarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private val appBarViewModel: AppBarViewModel by activityViewModels()

    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: ProfileNavigator by lazy { ProfileNavigator(navigationController) }

    private val binding by viewBinding(FragmentProfileBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.profileLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appBarViewModel.hideMenu()
        with(binding) {
            firstNameEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateFirstName(text)
                }
            }
            )
            lastNameEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateLastName(text)
                }
            }
            )
            birthdateEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateBirthDate(text)
                }
            }
            )
            cityEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateCity(text)
                }
            }
            )
            postalCodeEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updatePostalCode(text)
                }
            }
            )
            streetEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateStreet(text)
                }
            }
            )
            streetCodeEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateStreetCode(text)
                }
            }
            )
            countryEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    viewModel.updateCountry(text)
                }
            }
            )
            saveButton.setOnClickListener {
                viewModel.saveChanges()
            }
            saveButton.isSelected = true
        }
        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.errorEvent.observe(viewLifecycleOwner, errorObserver)
        viewModel.loadingState.observe(viewLifecycleOwner, loadingObserver)
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.informationEvent.observe(viewLifecycleOwner, informationObserver)
    }

    private val stateObserver: Observer<ProfileState>
        get() = Observer {
            with(binding) {
                firstNameLayout.error =
                    if (it.firstNameError) getString(R.string.format_error) else ""
                firstNameEditText.setText(it.firstName)

                lastNameLayout.error =
                    if (it.lastNameError) getString(R.string.format_error) else ""
                lastNameEditText.setText(it.lastName)

                birthdateLayout.error =
                    if (it.birthdateError) getString(R.string.format_error) else ""
                birthdateEditText.setText(it.birthDate)

                cityLayout.error = if (it.cityError) getString(R.string.format_error) else ""
                cityEditText.setText(it.city)

                postalCodeLayout.error =
                    if (it.postalCodeError) getString(R.string.format_error) else ""
                postalCodeEditText.setText(it.postalCode)

                streetLayout.error = if (it.streetError) getString(R.string.format_error) else ""
                streetEditText.setText(it.street)

                streetCodeLayout.error =
                    if (it.streetCodeError) getString(R.string.format_error) else ""
                streetCodeEditText.setText(it.streetCode)

                countryLayout.error = if (it.countryError) getString(R.string.format_error) else ""
                countryEditText.setText(it.country)
            }
        }

    private val errorObserver: Observer<Throwable>
        get() = Observer {
            Snackbar.make(
                binding.profileLayout,
                it?.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

    private val informationObserver: Observer<Int>
        get() = Observer {
            Snackbar.make(
                binding.profileLayout,
                getString(it),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

    private val loadingObserver: Observer<Boolean>
        get() = Observer {
            binding.progressBar.show(it)
        }

}
