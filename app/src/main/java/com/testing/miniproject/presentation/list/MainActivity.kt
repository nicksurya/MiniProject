@file:Suppress("DEPRECATION")

package com.testing.miniproject.presentation.list

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.testing.miniproject.R
import com.testing.miniproject.data.localdb.MyData
import com.testing.miniproject.databinding.ActivityMainBinding
import com.testing.miniproject.presentation.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val listViewModel by viewModel<ListViewModel>()


    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private var progressDialog : Dialog? = null
    private var adapter : DataAdapter? = null
    private var dataList : List<MyData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel = listViewModel
            lifecycleOwner = this@MainActivity
        }

        rvList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        adapter = DataAdapter(object : DataAdapter.Listener {
            override fun onEditData(dataId: Long) {
                startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, dataId)
                })
            }
        })

        rvList.adapter = adapter
        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handleSearch(newText)
                return false
            }
        })

        observeViewModel()

    }

    private fun handleSearch(key : String) {
        if (key.isNotEmpty()) {
            if (dataList.isNotEmpty()) {
                val filteredData = dataList.filter { it.name!!.contains(key, ignoreCase = true) || it.product!!.contains(key, ignoreCase = true) }
                adapter?.dataList = filteredData

            }
        } else {
            adapter?.dataList = dataList
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.fetchData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this@MainActivity, DetailActivity::class.java))
            }
            else -> {
                //do nothing
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel() {
        listViewModel.currentData.observe(this, {
            dataList = it
            adapter?.dataList = dataList
        })

        listViewModel.error.observe(this, { error ->
            showError(error)
        })

        listViewModel.loading.observe(this,{
            showProgress()
        })
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

    private fun showError(message: String?) {
        message?.let {
            AlertDialog.Builder(this)
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}
