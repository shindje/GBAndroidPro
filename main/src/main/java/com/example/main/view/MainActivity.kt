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
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val HISTORY_ACTIVITY_PATH = "com.example.history.view.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "history"
private const val UPDATE_REQUEST_CODE = 1010

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager
    private var adapter: MainAdapter? = null // Адаптер для отображения списка вариантов перевода
    override val model: MainViewModel by viewModel()

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.activity_main_layout),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    private val stateUpdatedListener: InstallStateUpdatedListener = object : InstallStateUpdatedListener {
        override fun onStateUpdate(state: InstallState) {
            // Переменная state позволяет следить за прогрессом установки
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependencies()

        checkForUpdates()

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

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener { intent ->
            if (intent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && intent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    intent,
                    IMMEDIATE,
                    this,
                    UPDATE_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(applicationContext, "Update flow failed! Result code: $resultCode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        IMMEDIATE,
                        this,
                        UPDATE_REQUEST_CODE
                    )
                }
            }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}