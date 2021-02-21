package com.example.prof.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prof.R
import com.example.prof.model.AppState
import com.example.prof.model.DataModel
import com.example.prof.view.Adapter.OnListItemClickListener
import com.example.prof.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.load.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState>() {
    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "123"
    }

    private var adapter: Adapter? = null


    override lateinit var model: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViewModel()

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)

                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }


    private fun iniViewModel() {
        //check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, { renderData(it) })
    }


    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)

    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = View.VISIBLE
        loading_frame_layout.visibility = View.GONE
        error_linear_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = View.GONE
        loading_frame_layout.visibility = View.VISIBLE
        error_linear_layout.visibility = View.GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = View.GONE
        loading_frame_layout.visibility = View.GONE
        error_linear_layout.visibility = View.VISIBLE
    }


    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter =
                            Adapter(object : OnListItemClickListener {
                                override fun onItemClick(data: DataModel) {

                                }
                            }, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }
}