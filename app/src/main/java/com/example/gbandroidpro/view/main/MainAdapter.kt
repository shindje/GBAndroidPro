package com.example.gbandroidpro.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbandroidpro.R
import com.example.gbandroidpro.model.DataModel
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*

class MainAdapter(private var onItemClickListener: OnClickListener, private var onFavouriteClickListener: OnClickListener, private var data: List<DataModel>) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData() = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text = data.meanings?.get(0)?.translation?.translation

                itemView.setOnClickListener { onItemClickListener.onItemClick(data, layoutPosition) }
                itemView.iv_favourite.setOnClickListener { onFavouriteClickListener.onItemClick(data, layoutPosition) }
                if (data.isFavorite != null && data.isFavorite!!)
                    itemView.iv_favourite.setImageResource(R.drawable.ic_baseline_star_yellow_24)
                else
                    itemView.iv_favourite.setImageResource(R.drawable.ic_baseline_star_outline_24)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(data: DataModel, position: Int)
    }
}
