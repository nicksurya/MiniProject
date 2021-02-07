package com.testing.miniproject.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testing.miniproject.R
import com.testing.miniproject.data.localdb.MyData
import kotlinx.android.synthetic.main.item_mydata_view.view.*

class DataAdapter(var dataList :List<MyData>, var listener : Listener) : RecyclerView.Adapter<DataAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_mydata_view, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(dataList[position], listener)
    }


    class Holder(private val view : View) : RecyclerView.ViewHolder(view) {


        fun bind(data : MyData, listener: Listener) {
            when (data.activityType) {
                "Meeting" -> view.ivIllustrator.setImageResource(R.drawable.ic_event)
                "Call" -> view.ivIllustrator.setImageResource(R.drawable.ic_mail)
                else -> view.ivIllustrator.setImageResource(R.drawable.ic_phone)
            }

            view.tvName.text = data.name
            view.tvBirthday.text = data.dob
            view.tvProduct.text = data.product
            view.tvLastUpdate.text = "Last Update : ${data.lastUpdate}"

            view.ivEdit.setOnClickListener { listener.onEditData(data.id!!) }
        }
    }

    interface Listener {
        fun onEditData(dataId : Long)
    }
}