package com.example.prof.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.prof.R
import com.example.prof.iteractor.MainInteractor
import com.example.model.AppState
import com.example.model.DataModel
import com.example.prof.viewmodel.MainViewModel
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.load.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<AppState, MainInteractor>() {
    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "123"
        private const val DESC_ACTIVITY_PATH = "com.example.description_feature.DescriptionActivity"
        private const val DESC_ACTIVITY_FEATURE_NAME = "description_feature"
        private const val WORD_EXTRA = "WORD_EXTRA"
        private const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "URL_EXTRA"
    }

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private lateinit var splitInstallManager: SplitInstallManager

    override lateinit var model: MainViewModel

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {

// Создаём менеджер
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
// Создаём запрос на создание экрана
                val request = SplitInstallRequest
                    .newBuilder()
                    .addModule(DESC_ACTIVITY_FEATURE_NAME)
                    .build()

                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        // Открываем экран
                        val intent = Intent().setClassName(packageName, DESC_ACTIVITY_PATH)
                         val descActivity = Class.forName(DESC_ACTIVITY_PATH).kotlin
                        intent.putExtra(WORD_EXTRA, data.text!!)
                        intent.putExtra(DESCRIPTION_EXTRA, data.meanings!!.joinToString(","))
                        intent.putExtra(URL_EXTRA, data.meanings!![0].imageUrl)
                        startActivity(intent)
                    }
                    // Добавляем слушатель в случае, если что-то пошло не так
                    .addOnFailureListener {
                        // Обрабатываем ошибку
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }


            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViewModel()

        main_activity_recyclerview.adapter = adapter

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
        search_hist_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, false)

                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun iniViewModel() {

        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, { renderData(it) })
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)

    }


}