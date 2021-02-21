package com.example.prof.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prof.model.AppState
import com.example.prof.viewmodel.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>


    abstract fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}
