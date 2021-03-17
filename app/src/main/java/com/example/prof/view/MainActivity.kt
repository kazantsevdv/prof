package com.example.prof.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.core.util.viewById
import com.example.model.AppState
import com.example.model.DataModel
import com.example.prof.R
import com.example.prof.iteractor.MainInteractor
import com.example.prof.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.scope.currentScope


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

    private val mainActivityRecyclerview by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
    private val searchHistFAB by viewById<FloatingActionButton>(R.id.search_hist_fab)

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

        mainActivityRecyclerview.adapter = adapter

        searchFAB.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)

                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        searchHistFAB.setOnClickListener {
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
        menu?.findItem(R.id.menu_screen_settings)?.isVisible =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.menu_screen_settings -> {
                startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 42)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun iniViewModel() {
//        injectDependencies()

        val viewModel: MainViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this@MainActivity, { renderData(it) })
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)

    }


}