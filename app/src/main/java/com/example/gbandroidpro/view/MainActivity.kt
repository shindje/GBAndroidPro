package com.example.gbandroidpro.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbandroidpro.R
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.presenter.MainInteractor
import com.example.gbandroidpro.vm.MainViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    private var adapter: MainAdapter? = null // Адаптер для отображения списка вариантов перевода
    override val model: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    // Обработка нажатия элемента списка
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        data.meanings?.get(0)?.translation?.translation ?: "",
                        data.meanings!![0].imageUrl

                    )
                )
            }
        }

    // Переопределяем базовый метод
    override fun renderData(appState: AppState) {
        // В зависимости от состояния модели данных (загрузка, отображение,
        // ошибка) отображаем соответствующий экран
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter = MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                // Задел на будущее, если понадобится отображать прогресс
                // загрузки
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            model.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        main_activity_recyclerview.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        main_activity_recyclerview.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        main_activity_recyclerview.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}