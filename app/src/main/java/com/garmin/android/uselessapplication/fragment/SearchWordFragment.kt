package com.garmin.android.uselessapplication.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.garmin.android.uselessapplication.R
import com.garmin.android.uselessapplication.adapter.FilesAdapter
import com.garmin.android.uselessapplication.viewmodel.StorageViewModel
import kotlinx.android.synthetic.main.fragment_search_word.view.*


class SearchWordFragment : Fragment(), TextWatcher {
    private lateinit var mViewModel: StorageViewModel
    private lateinit var mAdapter: FilesAdapter

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
        val view = inflater.inflate(R.layout.fragment_search_word, container, false)
        view.recycler_view_search_word.apply {
            layoutManager = LinearLayoutManager(activity)
            mAdapter = FilesAdapter(mViewModel.mFileList.value ?: listOf())
            mViewModel.mFileList.observe(this@SearchWordFragment, Observer {
                mAdapter.dataChanged(it)
            })
            adapter = mAdapter
        }
        view.edit_text_search_word.addTextChangedListener(this)
        return view
    }

    // Nothing to do after the text was changed
    override fun afterTextChanged(s: Editable?) { }

    // Nothing to do before the text was changed
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.toString().isEmpty()) {
            mAdapter.dataChanged(mViewModel.mFileList.value ?: listOf())
        } else {
            mAdapter.dataChanged(mViewModel.mFileList.value?.map {
                it.copy()
                it.mapToCount(s.toString())
            } ?: listOf())
        }
    }

    companion object {
        fun newInstance() =
            SearchWordFragment()
    }
}
