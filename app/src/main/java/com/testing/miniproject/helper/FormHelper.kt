package com.testing.miniproject.helper

import android.view.View
import android.view.ViewGroup
import com.testing.miniproject.presentation.detail.component.BaseComponentView
import com.testing.miniproject.presentation.detail.component.entities.*

val formComponent = arrayListOf(
     TextComponent(
        id = "tcvName",
        title = "Nama",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi nama")
        ),
        enabledChain = false,
        hint = "Masukkan nama",
        inputType = TextInputType.TEXT
    ),
    TextComponent(
        id = "tcvDoB",
        title = "Tanggal Lahir",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi tanggal lahir")
        ),
        enabledChain = true,
        hint = "Masukkan dengan format dd/MM/yyyy",
        inputType = TextInputType.BIRTH_DATE
    ),
    TextComponent(
        id = "tcvProductCode",
        title = "Kode Produk",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi kode produk")
        ),
        enabledChain = false,
        hint = "Masukkan kode produk",
        inputType = TextInputType.PRODUCT_CODE
    ),
    TextComponent(
        id = "tcvReason",
        title = "Alasan",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi alasan")
        ),
        enabledChain = false,
        hint = "Masukkan alasan",
        inputType = TextInputType.TEXT
    ),
    TextComponent(
        id = "tcvNotes",
        title = "Catatan",
        validations = arrayListOf(),
        enabledChain = false,
        hint = "Masukkan catatan disini",
        inputType = TextInputType.TEXTAREA
    ),
    SpinnerComponent(
        id = "scvProduct",
        title = "Produk",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap memilih produk")
        ),
        enabledChain = true,
        hint = "Pilih salah satu produk",
        dataList = arrayListOf("Produk A", "Produk B")
    ),
    SpinnerComponent(
        id = "scvActivity",
        title = "Aktivitas",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap memilih produk")
        ),
        enabledChain = true,
        hint = "Pilih salah satu aktivitas",
        dataList = arrayListOf("Meeting", "Call", "Email")
    ),
    TextComponent(
        id = "tcvActivityDate",
        title = "Tanggal Aktivitas",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi tanggal aktivitas")
        ),
        enabledChain = false,
        hint = "Masukkan tanggal aktivitas",
        inputType = TextInputType.EVENT_DATE
    ),
    TextComponent(
        id = "tcvPlanDate",
        title = "Tanggal Awal Mulai",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi tanggal awal rencana")
        ),
        enabledChain = false,
        hint = "Masukkan tanggal awal rencana",
        inputType = TextInputType.EVENT_DATE
    ),
    TextComponent(
        id = "tcvActivityStartTime",
        title = "Waktu Awal Aktivitas",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi waktu mulai aktifitas")
        ),
        enabledChain = false,
        hint = "Masukkan waktu mulai",
        inputType = TextInputType.TIME
    ),
    TextComponent(
        id = "tcvActivityEndTime",
        title = "",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi waktu akhir aktifitas")
        ),
        enabledChain = false,
        hint = "Masukkan waktu akhir",
        inputType = TextInputType.TIME
    ),
    TextComponent(
        id = "tcvPrice",
        title = "Harga",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi harga"),
            Validations(Rule.MIN_VALUE, "1000000", "Minimal 1.000.000"),
            Validations(Rule.MAX_VALUE, "100000000", "Maximal 100.000.000"),
        ),
        enabledChain = false,
        hint = "Masukkan harga",
        inputType = TextInputType.PRICE
    ),
    SpinnerComponent(
        id = "scvDuration",
        title = "Durasi",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap memilih durasi")
        ),
        enabledChain = true,
        hint = "Pilih salah satu durasi",
        dataList = arrayListOf("12 Bulan", "24 Bulan", "36 Bulan", "48 Bulan")
    ),
    TextComponent(
        id = "tcvPlace",
        title = "Lokasi",
        validations = arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap mengisi harga")
        ),
        enabledChain = false,
        hint = "Masukkan lokasi",
        inputType = TextInputType.TEXT
    ),
)

fun setupForm (container : ViewGroup, chainListener : BaseComponentView.ChainListener) : List<BaseComponentView> {
    var componentList = arrayListOf<BaseComponentView>()
    formComponent.forEach {
        val view = getViewByTag(it.id, container)
        view?.let { v ->
            (v as BaseComponentView).apply {
                setComponent(it)
                listener = chainListener

                componentList.add(v)
            }

        }
    }

    return componentList
}

private fun getViewByTag(tag : String, container : ViewGroup) : View? {
    return container.findViewWithTag<View>(tag)
}