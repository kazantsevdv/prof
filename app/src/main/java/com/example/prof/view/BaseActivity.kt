package com.example.prof.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.model.AppState
import com.example.model.Contract
import com.example.model.DataModel
import com.example.prof.R
import com.example.prof.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.load.*


abstract class BaseActivity<T : AppState, I : Contract.Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun setDataToAdapter(data: List<DataModel>)

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                //showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showErrorScreen(getString(R.string.empty_server_response_on_success))

                    } else {
                        showViewSuccess()
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
//                if (appState.progress != null) {
//                    progressBar.visibility = View.GONE
//                } else {
//                    progressBar.visibility = View.VISIBLE
//                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }


//    private fun showViewWorking() {
//        loading_frame_layout.visibility = View.GONE
//    }

    private fun showViewLoading() {
        success_linear_layout.visibility = View.GONE
        loading_frame_layout.visibility = View.VISIBLE
        error_linear_layout.visibility = View.GONE


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


    private fun showViewError() {
        success_linear_layout.visibility = View.GONE
        loading_frame_layout.visibility = View.GONE
        error_linear_layout.visibility = View.VISIBLE
    }
}
