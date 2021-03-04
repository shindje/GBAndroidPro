package com.example.gbandroidpro.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gbandroidpro.Interactor
import com.example.gbandroidpro.R
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.vm.BaseViewModel
import kotlinx.android.synthetic.main.include_loading_frame_layout.*

abstract class BaseActivity<T : AppState, V: Interactor<T>> : AppCompatActivity() {
    abstract val model: BaseViewModel<T>
    abstract fun setDataToAdapter(data: List<DataModel>)
    abstract fun showError(error: String?)
    abstract fun showViewLoading()
    abstract fun showViewSuccess()

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewSuccess()
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showError(getString(R.string.empty_server_response_on_success))
                } else {
                    setDataToAdapter(dataModel)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                // Задел на будущее, если понадобится отображать прогресс
                // загрузки
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = View.GONE
                    progress_bar_round.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showError(appState.error.message)
            }
        }
    }
}