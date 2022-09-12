package com.noveogroup.modulotechinterview.main.pages.profile

import android.os.Bundle
import android.view.View
import com.noveogroup.modulotechinterview.common.architecture.BaseFragment
import com.noveogroup.modulotechinterview.common.listener.SimpleTextWatcher
import com.noveogroup.modulotechinterview.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {
    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: ProfileNavigator by lazy { ProfileNavigator(navigationController) }

    private val binding by viewBinding(FragmentProfileBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.profileLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

            }
        }
        observeLiveData()
    }
}
