package com.example.prof.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.prof.R
import com.example.prof.iteractor.HistoryInteractor
import com.example.prof.model.AppState
import com.example.prof.model.DataModel
import com.example.prof.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (history_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }
    // Инициализируем адаптер и передаем его в RecyclerView
    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}
