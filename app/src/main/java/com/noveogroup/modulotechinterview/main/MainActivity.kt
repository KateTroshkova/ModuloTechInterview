package com.noveogroup.modulotechinterview.main

import android.content.ContextWrapper
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.architecture.BaseActivity
import com.noveogroup.modulotechinterview.common.navigation.NavigationProvider
import com.noveogroup.modulotechinterview.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavigationProvider {

    private val appBarViewModel: AppBarViewModel by viewModel()
    private var menu: Menu? = null

    override val viewModel: MainViewModel by viewModel()

    override val navigationController: NavController get() = findNavController(R.id.mainRootContainer)
    override val navigator: MainNavigator by lazy { MainNavigator(navigationController) }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override val containerId: Int get() = binding.content.mainRootContainer.id
    override val context: ContextWrapper get() = ContextWrapper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
    }

    override fun onResume() {
        super.onResume()
        NavigationUI.setupWithNavController(binding.toolbar, navigationController)
        setupActionBarWithNavController(
            navigationController,
            AppBarConfiguration.Builder(navigationController.graph).build()
        )
    }

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.mainLayout.applyStatusBarInset()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                navigator.openProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigator.back()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        appBarViewModel.showMenu.observe(this, menuObserver)
    }

    private val menuObserver: Observer<Boolean>
        get() = Observer { isVisible ->
            menu?.children?.forEach {
                when (it.itemId) {
                    R.id.profile -> {
                        it.isVisible = isVisible
                    }
                }
            }
        }
}
