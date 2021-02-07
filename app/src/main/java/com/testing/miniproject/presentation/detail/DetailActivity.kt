@file:Suppress("DEPRECATION")

package com.testing.miniproject.presentation.detail

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.testing.miniproject.R
import com.testing.miniproject.data.localdb.MyData
import com.testing.miniproject.databinding.ActivityDetailBinding
import com.testing.miniproject.helper.*
import com.testing.miniproject.presentation.detail.component.BaseComponentView
import com.testing.miniproject.presentation.detail.component.SpinnerComponentView
import com.testing.miniproject.presentation.detail.component.TextInputComponentView
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailActivity : AppCompatActivity(), BaseComponentView.ChainListener{

    companion object {
        const val EXTRA_ID = "data_id"
    }

    private val dataViewModel by viewModel<DetailViewModel>()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
    }

    private var progressDialog : Dialog? = null

    private var fieldViewList : List<BaseComponentView>  = arrayListOf()

    private var currentData : MyData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel = dataViewModel
            lifecycleOwner = this@DetailActivity
        }

        observeViewModel()
        fieldViewList = setupForm(llFormContainer, this)

        if (intent.hasExtra(EXTRA_ID)) {
            val id = intent.getLongExtra(EXTRA_ID, 0)
            dataViewModel.findDataById(id)
        }

        btnSubmit.setOnClickListener {
            handleSubmit()
        }
    }

    private fun observeViewModel() {
        dataViewModel.currentData.observe(this, Observer {
            currentData?.let {
                showError("Success")
            }

            currentData = it
            populateData()

        })

        dataViewModel.error.observe(this,
            Observer {error ->
                showError(error)
            }
        )

        dataViewModel.loading.observe(this,
            Observer {
                showProgress()
            }
        )
    }

    @Suppress("DEPRECATION")
    private fun showProgress() {
        progressDialog?.let {
            it.dismiss()
            progressDialog = null
        }?: run {
            progressDialog = ProgressDialog(this).apply {
                setMessage("Loading...")
                show()
            }
        }
    }

    private fun showError(message : String?) {
        message?.let {
            AlertDialog.Builder(this)
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onChainTriggered(view: BaseComponentView, value: String) {
        when (view) {
            is TextInputComponentView ->{
                if ("tcvDoB".equals(view.tag.toString(), ignoreCase = true)) {
                    runOnUiThread { handleAge(value) }
                } else if ("tcvActivityStartTime".equals(view.tag.toString(), ignoreCase = true) || "tcvActivityEndTime".equals(view.tag.toString(), ignoreCase = true)) {
                    runOnUiThread { checkTimeIsValid() }
                }
            }
            is SpinnerComponentView -> {
                if ("scvProduct".equals(view.tag.toString(), ignoreCase = true)) {
                    handleProduct(value)
                } else if ("scvActivity".equals(view.tag.toString(), ignoreCase = true)) {
                    hanldeActivity(value)
                }
            }
        }
    }

    private fun checkTimeIsValid() : Boolean {
        if (!timeIntervalIsValid(tcvActivityStartTime.getValue(), tcvActivityEndTime.getValue())) {
            tcvActivityEndTime.errorMessage = "Waktu tidak valid"
            tcvActivityEndTime.showError()
            return false
        }
        return true
    }

    private fun handleAge(birthDate :String) {
        when(getAge(parseStringToDate(birthDate))) {
            in 18..23 -> {
                llProductContainer.visibility = View.VISIBLE
                scvProduct.setupSpinnerContent(arrayListOf("Product A"))
            }
            in 25..30 -> {
                llProductContainer.visibility = View.VISIBLE
                scvProduct.setupSpinnerContent(arrayListOf("Product A", "Product B"))
            }
            else -> {
                llProductContainer.visibility = View.GONE
                llInfoContainer.visibility = View.GONE

                tcvDoB.errorMessage = "Diluar Range Usia"
                tcvDoB.showError()
            }
        }
    }

    private fun handleProduct(product : String) {
        when {
            product.equals("Product A", true) -> {
                llInfoContainer.visibility = View.VISIBLE
                tcvProductCode.visibility = View.VISIBLE
                tcvPlanDate.visibility = View.VISIBLE
                tcvReason.visibility = View.VISIBLE

                tcvPrice.visibility = View.GONE
                scvDuration.visibility = View.GONE
            }
            product.equals("Product B", true) -> {
                llInfoContainer.visibility = View.VISIBLE

                tcvProductCode.visibility = View.GONE
                tcvPlanDate.visibility = View.GONE
                tcvReason.visibility = View.GONE

                tcvPrice.visibility = View.VISIBLE
                scvDuration.visibility = View.VISIBLE

            }
            else -> {
                llInfoContainer.visibility = View.GONE

                tcvProductCode.visibility = View.GONE
                tcvPlanDate.visibility = View.GONE
                tcvReason.visibility = View.GONE

                tcvPrice.visibility = View.GONE
                scvDuration.visibility = View.GONE
            }
        }
    }

    private fun hanldeActivity(activity : String) {
        when {
            activity.equals("meeting", ignoreCase = true) ->
                tcvPlace.visibility = View.VISIBLE
            else ->
                tcvPlace.visibility = View.GONE
        }
    }

    private fun handleSubmit() {
        var isValid = checkTimeIsValid()

        fieldViewList.forEach {
            if (it.visibility == View.VISIBLE) {
                if (!it.isValid()) {
                    isValid = false
                    it.showError()
                }
            }
        }

        if (isValid) {
            val id = currentData?.id ?: System.currentTimeMillis()

            val data = MyData(
                id = id,
                name = tcvName.getValue(),
                dob = tcvDoB.getValue(),
                product = scvProduct.getValue(),
                productCode = tcvProductCode.getValue(),
                activityType = scvActivity.getValue(),
                activityLocation = tcvPlace.getValue(),
                activityDate = tcvActivityDate.getValue(),
                activityTimeStart = tcvActivityStartTime.getValue(),
                activityTimeEnd = tcvActivityEndTime.getValue(),
                planStart = tcvPlanDate.getValue(),
                reason = tcvReason.getValue(),
                price = tcvPrice.getValue()?.toDouble()?:0.0,
                period = scvDuration.getValue()?.replace(" Bulan", "", true)?.toInt()?:0,
                notes = tcvNotes.getValue(),
                lastUpdate = parseDateToString(Calendar.getInstance().time))

            dataViewModel.updatedData(data)
        }
    }

    private fun populateData() {
        currentData?.let {
            tcvName.setValue(it.name)
            tcvDoB.setValue(it.dob)
            scvProduct.setValue(it.product)
            tcvProductCode.setValue(it.productCode)
            scvActivity.setValue(it.activityType)
            tcvPlace.setValue(it.activityLocation)
            tcvActivityDate.setValue(it.activityDate)
            tcvActivityStartTime.setValue(it.activityTimeStart)
            tcvActivityEndTime.setValue(it.activityTimeEnd)
            tcvPlanDate.setValue(it.planStart)
            tcvReason.setValue(it.reason)
            tcvPrice.setValue(if (it.price!! <= 0) "" else it.price!!.toInt().toString())
            scvDuration.setValue(if (it.period == 0)  "" else "${it.period} Bulan")

            //only allow edit notes
            tcvNotes.setValue(it.notes)
            tcvNotes.setEnabledField(true)
        }
    }
}