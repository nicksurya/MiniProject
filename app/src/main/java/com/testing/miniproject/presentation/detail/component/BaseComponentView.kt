package com.testing.miniproject.presentation.detail.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.testing.miniproject.presentation.detail.component.entities.BaseComponent

abstract class BaseComponentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr){

    internal var component : BaseComponent? = null

    internal var listener : ChainListener? = null

    internal var errorMessage : String? = null

    abstract fun getLabelView(): TextView?

    abstract fun getErrorLabel() : TextView?

    abstract fun getInputView() : View?

    abstract fun isValid(): Boolean

    abstract fun setupChainTriggered(enabled : Boolean)

    abstract fun setComponent(component: BaseComponent)

    abstract fun getValue() : String?

    abstract fun setValue(value : String?)

    abstract fun setEnabledField(enabled : Boolean)

    fun setupDefaultComponent() {
        getLabelView()?.text = component?.title

    }

    fun showError() {
        getErrorLabel()?.visibility = View.VISIBLE
        getErrorLabel()?.maxLines = 2
        getErrorLabel()?.text = errorMessage

    }

    fun hideError() {
        getErrorLabel()?.visibility = View.GONE
    }

    interface ChainListener {
        fun onChainTriggered(view : BaseComponentView, value : String)
    }
}