package com.example.description_feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.util.viewById

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso



class DescriptionActivity :AppCompatActivity() {
    private val description_header by viewById<TextView>(R.id.description_header)
    private val description_textview by viewById<TextView>(R.id.description_textview)
    private val description_imageview by viewById<ImageView>(R.id.description_imageview)
    private val description_screen_swipe_refresh_layout by viewById<SwipeRefreshLayout>(R.id.description_screen_swipe_refresh_layout)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)

        setActionbarHomeButtonAsUp()
        // Устанавливаем слушатель обновления экрана
        description_screen_swipe_refresh_layout.setOnRefreshListener { setData() }
        setData()
    }

    // Переопределяем нажатие на стрелку Назад, чтобы возвращаться по нему
    // на главный экран
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // Устанавливаем кнопку Назад в ActionBar
    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    // Достаём данные (слово, перевод и ссылку) из бандла и загружаем
    // изображение
    private fun setData() {
        val bundle = intent.extras
        description_header.text = bundle?.getString(WORD_EXTRA)
        description_textview.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(description_imageview, imageLink)

        }
    }


    // Метод, следящий за сокрытием спиннера загрузки при обновлении страницы
    private fun stopRefreshAnimationIfNeeded() {
        if (description_screen_swipe_refresh_layout.isRefreshing) {
            description_screen_swipe_refresh_layout.isRefreshing = false
        }
    }
    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.with(applicationContext).load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo_vector).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }



    companion object {



        private const val WORD_EXTRA = "WORD_EXTRA"
        private const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "URL_EXTRA"

        @JvmStatic  fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }


}