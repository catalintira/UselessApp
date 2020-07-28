package com.garmin.android.uselessapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garmin.android.uselessapplication.R
import com.garmin.android.uselessapplication.model.DataFile

class FilesAdapter(private var dataList: List<DataFile>) : RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FileViewHolder(inflater, parent)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) = let { holder.bind(dataList[position]) }

    fun dataChanged(newDataList: List<DataFile>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    class FileViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.file_item, parent, false)) {
        private var mTitleView: TextView? = null
        private var mYearView: TextView? = null


        init {
            mTitleView = itemView.findViewById(R.id.tv_item_title)
            mYearView = itemView.findViewById(R.id.tv_item_text)
        }

        fun bind(datafile: DataFile) {
            mTitleView?.text = datafile.name
            mYearView?.text = datafile.text
        }
    }

}