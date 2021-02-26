package com.example.gbandroidpro.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.gbandroidpro.R
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity: AppCompatActivity() {

    companion object {
        private const val WORD_EXTRA = "_word"
        private const val DESCRIPTION_EXTRA = "_description"
        private const val URL_EXTRA = "_url"

        fun getIntent(context: Context, word: String, description: String, url: String?): Intent =
            Intent(context, DescriptionActivity::class.java).apply {
                putExtra(WORD_EXTRA, word)
                putExtra(DESCRIPTION_EXTRA, description)
                putExtra(URL_EXTRA, url)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        setActionBarHomeButtonAsUp()
        layout_description.setOnRefreshListener { setData() }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {
        var bundle = intent.extras
        tv_description_header.text = bundle?.getString(WORD_EXTRA)
        tv_description.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageUrl = bundle?.getString(URL_EXTRA)
        if (imageUrl.isNullOrBlank())
            stopRefreshAnimation()
        else
            loadImage(imageUrl)
    }

    private fun stopRefreshAnimation() { layout_description.isRefreshing = false }

    private fun loadImage(url: String) {
        Glide.with(iv_descriprion_image)
            .load("https:$url")
            .listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimation()
                    iv_descriprion_image.setImageResource(R.drawable.ic_baseline_load_error_24)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimation()
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_downloading_24)
                    .centerCrop()
            )
            .into(iv_descriprion_image)
    }
}