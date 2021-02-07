package com.testing.miniproject.presentation.detail.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.testing.miniproject.R
import com.testing.miniproject.presentation.detail.component.entities.BaseComponent
import com.testing.miniproject.presentation.detail.component.entities.SpinnerComponent
import kotlinx.android.synthetic.main.view_input_text.view.*
import kotlinx.android.synthetic.main.view_input_text.view.tvError
import kotlinx.android.synthetic.main.view_input_text.view.tvLabel
import kotlinx.android.synthetic.main.view_spinner.view.*
import java.util.*

class SpinnerComponentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : BaseComponentView(context, attrs, defStyleAttr){

    private var selectedValue : String?= null

    private var contents : List<String> = arrayListOf()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_spinner, this)
    }

    override fun setComponent(component : BaseComponent) {
        this.component = component
        setupDefaultComponent()
        setupChainTriggered(component.enableChain)

        if (component is SpinnerComponent) {
            if (component.dataList.isNotEmpty()) {
                setupSpinnerContent(component.dataList)
            }
        }
    }

    override fun setValue(newValue: String?) {
        newValue?.let { value ->
            selectedValue = value
            setEnabledField(false)

            contents.isNotEmpty().let {
                spInput.setSelection(contents.indexOf(value))
            }
        }
    }

    override fun setEnabledField(enabled: Boolean) {
        spInput.isEnabled = enabled
        tvLabel.isEnabled = enabled
    }

    fun setupSpinnerContent(contents : List<String>) {
        this.contents = contents
        spInput.adapter = ArrayAdapter(
            Objects.requireNonNull(context), R.layout.spinner_simple_item,
            contents)

        selectedValue?.let {
            setValue(selectedValue)
        }
    }

    override fun isValid() : Boolean {
        val value = spInput.selectedItem.toString()
        errorMessage = component!!.isValid(value)

        return errorMessage.isNullOrEmpty()
    }

    override fun setupChainTriggered(enabled : Boolean) {
        spInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                errorMessage = null
                hideError()
                if (enabled) {
                    listener?.onChainTriggered( this@SpinnerComponentView, spInput.selectedItem.toString())
                }
            }
        }

    }

    override fun getValue(): String? {
        return if (visibility == VISIBLE) {
            spInput.selectedItem.toString()
        } else {
            null
        }
    }

    override fun getLabelView(): TextView? = tvLabel

    override fun getErrorLabel(): TextView? = tvError

    override fun getInputView(): View? = etInput
}