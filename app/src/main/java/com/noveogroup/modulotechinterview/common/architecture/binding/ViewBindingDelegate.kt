package com.noveogroup.modulotechinterview.common.architecture.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingDelegate {

    private var handlers = mutableSetOf<ViewBindingHandler<*>>()

    private var primaryHandler: PrimaryViewBindingHandler<*>? = null

    /**
     * Create ViewBinding handler that is used when view is inflated. Each fragment or activity *must* define it
     */
    fun <Binding : ViewBinding> createPrimaryViewBindingHandler(
        factory: BindingFactory<Binding>,
    ): ReadOnlyProperty<Any, Binding> =
        PrimaryViewBindingHandler(factory).also { primaryHandler = it }

    /**
     * Create ViewBinding handler that does not inflate any views, but creates a ViewBinding from existing view
     */
    fun <Binding : ViewBinding> createSecondaryViewBindingHandler(
        binder: (View) -> Binding,
    ): ReadOnlyProperty<Any, Binding> =
        ViewBindingHandler(binder).also { handlers.add(it) }

    /**
     * Inflate primary ViewBinding and return its' rootView
     */
    fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): View =
        primaryHandler!!.inflateBinding(inflater, container, attachToParent)

    fun onCreateView(rootView: View) {
        handlers.forEach { it.bind(rootView) }
    }

    fun onDestroyView() {
        handlers.forEach { it.clear() }
        primaryHandler?.clear()
    }

    private class ViewBindingHandler<Binding : ViewBinding>(private val binder: (View) -> Binding) :
        ReadOnlyProperty<Any, Binding> {
        private var _binding: Binding? = null

        fun bind(view: View) {
            _binding = binder(view)
        }

        fun clear() {
            _binding = null
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): Binding = _binding!!
    }

    private class PrimaryViewBindingHandler<Binding : ViewBinding>(
        private val bindingFactory: BindingFactory<Binding>,
    ) :
        ReadOnlyProperty<Any, Binding> {
        private var _binding: Binding? = null

        fun inflateBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
            attachToParent: Boolean
        ): View =
            bindingFactory.inflate(inflater, container, attachToParent)
                .also { _binding = it }
                .root

        fun clear() {
            _binding = null
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): Binding = _binding!!
    }
}
