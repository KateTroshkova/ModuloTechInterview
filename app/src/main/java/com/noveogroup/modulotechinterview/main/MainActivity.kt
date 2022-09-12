package com.noveogroup.modulotechinterview.main

import android.content.ContextWrapper
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.architecture.BaseActivity
import com.noveogroup.modulotechinterview.common.navigation.NavigationProvider
import com.noveogroup.modulotechinterview.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavigationProvider {

    override val viewModel: MainViewModel by viewModel()

    override val navigationController: NavController get() = findNavController(R.id.mainRootContainer)
    override val navigator: MainNavigator by lazy { MainNavigator(navigationController) }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override val containerId: Int get() = binding.content.mainRootContainer.id
    override val context: ContextWrapper get() = ContextWrapper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
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
}
