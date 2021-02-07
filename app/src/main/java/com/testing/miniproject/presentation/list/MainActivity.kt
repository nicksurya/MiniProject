package com.testing.miniproject.presentation.list

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.testing.miniproject.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel = listViewModel
            lifecycleOwner = this@MainActivity
        }

        rvList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        observeViewModel()


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
        listViewModel.currentData.observe(this, Observer {
            rvList.adapter = DataAdapter(it, object : DataAdapter.Listener {
                override fun onEditData(dataId: Long) {
                    startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply{
                        putExtra(DetailActivity.EXTRA_ID, dataId)
                    })
                }
            })
        })

        listViewModel.error.observe(this,
            Observer {error ->
                showError(error)
            }
        )

        listViewModel.loading.observe(this,
            Observer {
                showProgress(it)
            }
        )
    }

    private fun showProgress(show : Boolean) {
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
}