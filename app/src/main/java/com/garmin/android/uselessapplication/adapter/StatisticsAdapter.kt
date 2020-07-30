package com.garmin.android.uselessapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garmin.android.uselessapplication.R
import com.garmin.android.uselessapplication.model.StatisticsDataFile

class StatisticsAdapter(private var dataList: List<StatisticsDataFile>) : RecyclerView.Adapter<StatisticsAdapter.StatisticsFileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsFileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StatisticsFileViewHolder(inflater, parent)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: StatisticsFileViewHolder, position: Int) = let { holder.bind(dataList[position]) }

    class StatisticsFileViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_file_stat, parent, false)) {
        private var mTitleView: TextView? = null
        private var mAverageView: TextView? = null
        private var mSumView: TextView? = null

        init {
            mTitleView = itemView.findViewById(R.id.tv_item_stat_title)
            mAverageView = itemView.findViewById(R.id.tv_item_stat_average)
            mSumView = itemView.findViewById(R.id.tv_item_stat_sum)
        }

        fun bind(statisticsDatafile: StatisticsDataFile) {
            mTitleView?.text = statisticsDatafile.name
            mAverageView?.text = statisticsDatafile.numbersAverage.toString()
            mSumView?.text = statisticsDatafile.numbersSum.toString()
        }
    }

    fun dataChanged(newDataList: List<StatisticsDataFile>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}