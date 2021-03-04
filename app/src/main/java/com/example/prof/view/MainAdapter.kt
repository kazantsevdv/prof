package com.example.prof.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prof.R
import com.example.prof.model.DataModel
import kotlinx.android.synthetic.main.activity_main_rv_item.view.*
import okhttp3.internal.notify


class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {
    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_rv_item, parent, false) as View
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
                itemView.description_textview_recycler_item.text =
                    data.meanings?.get(0)?.translation?.translation

                itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }


    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}
