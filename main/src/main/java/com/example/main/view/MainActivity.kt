package com.example.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.view.BaseActivity
import com.example.description.DescriptionActivity
import com.example.main.R
import com.example.main.injectDependencies
import com.example.main.presenter.MainInteractor
import com.example.main.vm.MainViewModel
import com.example.model.AppState
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val HISTORY_ACTIVITY_PATH = "com.example.history.view.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "history"

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    private lateinit var splitInstallManager: SplitInstallManager
    private var adapter: MainAdapter? = null // Адаптер для отображения списка вариантов перевода
    override val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependencies()

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

        history_fab.setOnClickListener {
            splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
            val request = SplitInstallRequest.newBuilder()
                .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    startActivity(Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH))
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, "Couldn't download feature: " + it.message, Toast.LENGTH_LONG).show()
                }
        }

        favourites_fab.setOnClickListener {
            model.getFavourites()
        }

        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    // Обработка нажатия элемента списка
    private val onItemClickListener: MainAdapter.OnClickListener =
        object : MainAdapter.OnClickListener {
            override fun onItemClick(data: com.example.model.DataModel, position: Int) {
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

    // Обработка нажатия на иконку "Избранное" внутри элемента списка
    private val onFavouriteClickListener: MainAdapter.OnClickListener =
            object : MainAdapter.OnClickListener {
                override fun onItemClick(data: com.example.model.DataModel, position: Int) {
                    if (data.isFavorite != null && data.isFavorite!!) {
                        model.removeFavourite(data.text!!)
                        adapter?.getData()?.get(position)?.isFavorite = false
                    } else {
                        model.setFavourite(data.text!!, data.meanings?.get(0)?.translation?.translation
                                ?: "")
                        adapter?.getData()?.get(position)?.isFavorite = true
                    }
                    adapter?.notifyItemChanged(position)
                }
            }

    override fun setDataToAdapter(data: List<com.example.model.DataModel>) {
        if (adapter == null) {
            main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
            adapter = MainAdapter(onItemClickListener, onFavouriteClickListener, data)
            main_activity_recyclerview.adapter = adapter
        } else {
            adapter!!.setData(data)
        }
    }

    override fun showError(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            model.getData("hi", true)
        }
    }

    override fun showViewSuccess() {
        main_activity_recyclerview.visibility = VISIBLE
        findViewById<FrameLayout>(R.id.loading_frame_layout).visibility = GONE
        error_linear_layout.visibility = GONE
    }

    override fun showViewLoading() {
        main_activity_recyclerview.visibility = GONE
        findViewById<FrameLayout>(R.id.loading_frame_layout).visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        main_activity_recyclerview.visibility = GONE
        findViewById<FrameLayout>(R.id.loading_frame_layout).visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}