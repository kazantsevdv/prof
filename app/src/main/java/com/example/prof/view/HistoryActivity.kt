package com.example.prof.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.util.viewById
import com.example.model.AppState
import com.example.model.DataModel
import com.example.prof.R
import com.example.prof.iteractor.HistoryInteractor
import com.example.prof.viewmodel.HistoryViewModel

import org.koin.android.scope.currentScope


class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private val history_activity_recyclerview by viewById<RecyclerView>(R.id.history_activity_recyclerview)

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
        //injectDependencies()

        if (history_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }

    // Инициализируем адаптер и передаем его в RecyclerView
    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}

