package com.noveogroup.modulotechinterview.common.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.noveogroup.modulotechinterview.common.android.delegate.KeyboardDelegate
import com.noveogroup.modulotechinterview.common.android.ext.setMarginExtra
import com.noveogroup.modulotechinterview.common.android.ext.setPaddingExtra
import com.noveogroup.modulotechinterview.common.architecture.binding.BindingFactory
import com.noveogroup.modulotechinterview.common.architecture.binding.NestedInflater
import com.noveogroup.modulotechinterview.common.architecture.binding.ViewBindingDelegate
import com.noveogroup.modulotechinterview.common.architecture.binding.terminalInflater
import com.noveogroup.modulotechinterview.common.navigation.NavigationProvider
import com.noveogroup.modulotechinterview.common.navigation.Navigator
import com.noveogroup.modulotechinterview.domain.ext.safeCast
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment : ScopeFragment(), NestedInflater {

    protected val baseActivity: BaseActivity get() = activity?.safeCast<BaseActivity>()!!

    /* MVVM */
    protected abstract val viewModel: MvvmViewModel

    /* NAVIGATION */
    protected val navigationController: NavController get() = findNavController()
    protected abstract val navigator: Navigator
    protected open val navigationProvider: NavigationProvider?
        get() = baseActivity.safeCast<NavigationProvider>()

    /* DELEGATES */
    protected val keyboardDelegate: KeyboardDelegate? get() = baseActivity.keyboardDelegate
    private val viewBindingDelegate by lazy { ViewBindingDelegate() }

    protected var statusBarHeight: Int = 0

    protected open fun observeLiveData() {}
    protected open fun removeLiveDataObservers() {}

    protected open fun activityNavigator(): Navigator? {
        return requireActivity().safeCast<NavigationProvider>()?.navigator
    }

    val contentView get() = requireView()

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
    open fun onApplyScreenInsets() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreated()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        view: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflateNested(
            view,
            viewBindingDelegate.terminalInflater(inflater)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (statusBarHeight.takeIf { it > 0 } ?: baseActivity.statusBarHeight.takeIf { it > 0 })
            ?.also {
                statusBarHeight = it
                onApplyScreenInsets()
            }
        view.setOnApplyWindowInsetsListener { _, insets ->
            val invokeInsetListener = statusBarHeight == 0
            statusBarHeight = baseActivity.statusBarHeight
            if (invokeInsetListener) onApplyScreenInsets()
            insets
        }
    }

    override fun onResume() {
        super.onResume()
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
        if (baseActivity.isFinishing) {
            onDestroyed()
            return
        }

        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            onDestroyed()
        }
    }

    /**
     * Method following [onCreate] event.
     *
     * This method needs to be implemented in child fragments, for instance to inject DI scopes.
     * Called once after in [onCreate] method.
     */
    protected open fun onCreated() {
    }

    /**
     * Method following [onDestroy] event.
     *
     * Called in [onDestroy] method.
     */
    protected open fun onDestroyed() {
    }

    open fun showErrorWithSnackbar(message: String, parent: View) {
        Snackbar.make(parent, message, Snackbar.LENGTH_LONG).show()
    }

    protected fun View.applyStatusBarInset() {
        setMarginExtra(topMargin = statusBarHeight)
    }

    protected fun View.applyStatusBarInsetWithPadding() {
        setPaddingExtra(topPadding = statusBarHeight)
    }
}
