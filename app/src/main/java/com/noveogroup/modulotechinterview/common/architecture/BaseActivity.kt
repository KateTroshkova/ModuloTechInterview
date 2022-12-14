package com.noveogroup.modulotechinterview.common.architecture

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.noveogroup.modulotechinterview.common.android.delegate.KeyboardDelegate
import com.noveogroup.modulotechinterview.common.android.ext.applyNoStatusBar
import com.noveogroup.modulotechinterview.common.android.ext.setMarginExtra
import com.noveogroup.modulotechinterview.common.architecture.binding.BindingFactory
import com.noveogroup.modulotechinterview.common.architecture.binding.NestedInflater
import com.noveogroup.modulotechinterview.common.architecture.binding.ViewBindingDelegate
import com.noveogroup.modulotechinterview.common.architecture.binding.terminalInflater
import com.noveogroup.modulotechinterview.common.navigation.Navigator
import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity : ScopeActivity(), NestedInflater {

    /* MVVM */
    protected abstract val viewModel: MvvmViewModel

    /* NAVIGATION */
    protected abstract val navigationController: NavController
    protected abstract val navigator: Navigator

    /* DELEGATES */
    private val viewBindingDelegate by lazy { ViewBindingDelegate() }
    var keyboardDelegate: KeyboardDelegate? = null

    var statusBarHeight: Int = 0

    private var onViewCreatedCalled = false
    private val contentView: View get() = window.decorView.rootView

    protected open fun observeLiveData() {}
    protected open fun removeLiveDataObservers() {}

    fun <Binding : ViewBinding> viewBinding(factory: BindingFactory<Binding>) =
        viewBindingDelegate.createPrimaryViewBindingHandler(factory)

    fun <Binding : ViewBinding> viewBinding(@IdRes viewId: Int, binder: (View) -> Binding) =
        viewBindingDelegate.createSecondaryViewBindingHandler {
            binder(it.findViewById(viewId))
        }

    /**
     * Method to apply screen insets: bottomBar offset or statusBar offset.
     * It is invoked only once, when [statusBarHeight] is found.
     */
    open fun onApplyScreenInsets() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* UI */
        applyTheme()
        val view = inflateNested(
            null,
            viewBindingDelegate.terminalInflater(layoutInflater)
        )
        setContentView(view)

        keyboardDelegate = KeyboardDelegate(this)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        view?.let { onSetContentView(it) }
    }

    private fun onSetContentView(view: View) {
        statusBarHeight.takeIf { it > 0 }?.also { onApplyScreenInsets() }
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val invokeInsetListener = statusBarHeight == 0
            statusBarHeight = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            if (invokeInsetListener) onApplyScreenInsets()
            windowInsets
        }
    }

    override fun onStart() {
        super.onStart()
        if (!onViewCreatedCalled) {
            onViewCreatedCalled = true
            viewBindingDelegate.onCreateView(contentView)
        }
    }

    override fun onResume() {
        super.onResume()
        observeLiveData()
        viewModel.attach()
    }

    override fun onPause() {
        super.onPause()
        keyboardDelegate?.hideKeyboard()
        viewModel.detach()
        removeLiveDataObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBindingDelegate.onDestroyView()
    }

    open fun applyTheme() {
        // setup Activity theme if needed
        window.applyNoStatusBar()
    }

    protected fun View.applyStatusBarInset() {
        setMarginExtra(topMargin = statusBarHeight)
    }
}
