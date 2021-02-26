package com.example.gbandroidpro.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gbandroidpro.R
import com.example.gbandroidpro.model.DataModel
import com.example.gbandroidpro.presenter.HistoryInteractor
import com.example.gbandroidpro.vm.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_loading_frame_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException
import java.util.*

class HistoryActivity:BaseActivity<AppState, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        if (rv_history.adapter != null) {
            throw IllegalStateException("Error")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, { renderData(it) })
    }

    private fun initViews() {
        rv_history.adapter = adapter
    }

    override fun showError(error: String?) {
        Toast.makeText(this@HistoryActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showViewLoading() {
        rv_history.visibility = View.GONE
        loading_frame_layout.visibility = View.VISIBLE
    }

    override fun showViewSuccess() {
        rv_history.visibility = View.VISIBLE
        loading_frame_layout.visibility = View.GONE
    }
}