package com.testing.miniproject.presentation.detail.component

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.testing.miniproject.R
import com.testing.miniproject.helper.parseDateToString
import com.testing.miniproject.helper.parseStringToDate
import com.testing.miniproject.helper.parseTimeStringToDate
import com.testing.miniproject.helper.parseTimeToString
import com.testing.miniproject.presentation.detail.component.entities.BaseComponent
import com.testing.miniproject.presentation.detail.component.entities.TextComponent
import com.testing.miniproject.presentation.detail.component.entities.TextInputType
import kotlinx.android.synthetic.main.view_input_text.view.*
import java.util.*
import java.util.concurrent.TimeUnit


class TextInputComponentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : BaseComponentView(context, attrs, defStyleAttr){

    init {
        LayoutInflater.from(context).inflate(R.layout.view_input_text, this)
    }

    override fun setComponent(component : BaseComponent) {
        this.component = component
        setupDefaultComponent()
        setupChainTriggered(component.enableChain)

        if (component is TextComponent) {
            etInput.hint = component.hint
            setupInputType(component.inputType)

            setupDateTimePicker(component.inputType)
        }
    }

    override fun setValue(value: String?) {
        value?.let {
            etInput.setText(it)
            setEnabledField(false)
        }
    }

    override fun setEnabledField(enabled: Boolean) {
        etInput.isEnabled = enabled
        ivDatePicker.isEnabled = enabled
        tvLabel.isEnabled = enabled
    }

    @SuppressLint("CheckResult")
    override fun setupChainTriggered(enabled: Boolean) {
        RxTextView.textChanges(etInput)
            .debounce(1, TimeUnit.MILLISECONDS)
            .subscribe { textChanged ->
                resetError()
                listener?.onChainTriggered(this, textChanged.toString())
            }
    }

    private fun resetError() {
        (context as Activity).runOnUiThread {
            errorMessage = null
            hideError()
        }
    }

    private fun setupDateTimePicker(inputType: TextInputType) {
        when (inputType) {
            TextInputType.DATE, TextInputType.EVENT_DATE, TextInputType.BIRTH_DATE -> {
                etInput.setOnClickListener { openDatePicker() }
                ivDatePicker.setOnClickListener { openDatePicker() }
            }
            TextInputType.TIME -> {
                etInput.setOnClickListener { openTimePicker() }
            }
            else -> return
        }
    }

    private fun openDatePicker() {
        val parseDate = parseStringToDate(etInput.text.toString())
        val currentDate = Calendar.getInstance()
        parseDate?.let {
            currentDate.time = parseDate
        }

        val datePicker = DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener { _, year, month, date ->
                val dateString = parseDateToString(Calendar.getInstance().apply {
                    set(year, month, date)
                }.time)
                etInput.setText(dateString)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        when ((component as TextComponent).inputType) {
            TextInputType.BIRTH_DATE -> datePicker.datePicker.maxDate = System.currentTimeMillis()
            TextInputType.EVENT_DATE -> datePicker.datePicker.minDate = System.currentTimeMillis()
            else -> {}
        }
        datePicker.show()
    }

    private fun openTimePicker() {
        val parseDate = parseTimeStringToDate(etInput.text.toString())
        val currentDate = Calendar.getInstance()
        parseDate?.let {
            currentDate.time = parseDate
        }

        TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
                val timeString = parseTimeToString(Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hours)
                    set(Calendar.MINUTE, minutes)
                }.time)
                etInput.setText(timeString)
            },
            currentDate.get(Calendar.HOUR_OF_DAY),
            currentDate.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setupInputType(inputType: TextInputType) {
        when (inputType) {
            TextInputType.DATE, TextInputType.BIRTH_DATE, TextInputType.EVENT_DATE-> {
                etInput.inputType = InputType.TYPE_CLASS_NUMBER
                etInput.keyListener = DigitsKeyListener.getInstance("0123456789/")
                etInput.isFocusableInTouchMode = false
                ivDatePicker.visibility = View.VISIBLE
            }
            TextInputType.TIME -> {
                etInput.inputType = InputType.TYPE_CLASS_NUMBER
                etInput.keyListener = DigitsKeyListener.getInstance("0123456789/")
                etInput.isFocusableInTouchMode = false
            }
            TextInputType.NUMBER -> etInput.inputType = InputType.TYPE_CLASS_NUMBER
            TextInputType.PRICE-> {
                etInput.inputType = InputType.TYPE_CLASS_NUMBER
                etInput.keyListener = DigitsKeyListener.getInstance("0123456789")
            }
            TextInputType.TEXTAREA -> {
                etInput.isSingleLine = false
                etInput.maxLines = 5
                etInput.minLines = 3
                etInput.setLines(3)
                etInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                etInput.gravity = Gravity.TOP
            }
            TextInputType.PRODUCT_CODE -> {
                etInput.inputType = InputType.TYPE_CLASS_NUMBER
                etInput.keyListener = DigitsKeyListener.getInstance("0123456789-")
            }
            else -> {
                etInput.isSingleLine = true
                etInput.inputType = InputType.TYPE_CLASS_TEXT
            }
        }
    }

    override fun isValid() : Boolean {
        val value = etInput.text.toString()
        errorMessage = component!!.isValid(value)

        return errorMessage.isNullOrEmpty()
    }

    override fun getValue() : String? {
        return if (visibility == VISIBLE) {
            etInput.text.toString()
        } else {
            null
        }
    }

    override fun getLabelView(): TextView? = tvLabel

    override fun getErrorLabel(): TextView? = tvError

    override fun getInputView(): View? = etInput
}