package com.garmin.android.uselessapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.garmin.android.uselessapplication.R
import com.garmin.android.uselessapplication.adapter.StatisticsAdapter
import com.garmin.android.uselessapplication.model.StatisticsDataFile
import com.garmin.android.uselessapplication.viewmodel.StorageViewModel
import kotlinx.android.synthetic.main.fragment_statistics.view.*


class StatisticsFragment : Fragment() {
    private lateinit var mViewModel: StorageViewModel
    private lateinit var mAdapter: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(requireActivity())
            .get(StorageViewModel::class.java)
            .apply { getInternalFiles() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        view.recycler_view_statistics.apply {
            layoutManager = LinearLayoutManager(activity)
            mAdapter = StatisticsAdapter(mViewModel.getStatisticalFiles() ?: listOf())
            mViewModel.mFileList.observe(this@StatisticsFragment, Observer {
                mAdapter.dataChanged(mViewModel.getStatisticalFiles() ?: listOf())
            })
            adapter = mAdapter
        }
        return view
    }

    companion object {
        fun newInstance() =
            StatisticsFragment()
    }
}