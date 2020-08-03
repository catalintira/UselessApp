package com.garmin.android.uselessapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.garmin.android.uselessapplication.R
import com.garmin.android.uselessapplication.viewmodel.StorageViewModel
import kotlinx.android.synthetic.main.fragment_insert_word.view.*
import kotlinx.android.synthetic.main.fragment_insert_word.view.spinner_destination_selector


class InsertWordFragment : Fragment() {
    private lateinit var mViewModel: StorageViewModel

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
        val view =  inflater.inflate(R.layout.fragment_insert_word, container, false)
        val spinnerItems = resources.getStringArray(R.array.spinner_options)
        view.spinner_destination_selector.adapter =
            ArrayAdapter(activity, android.R.layout.simple_spinner_item, spinnerItems)
                .apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
        view.bt_add_text.setOnClickListener {
            insertWord(view.spinner_destination_selector.selectedItemId,
                view.tv_word_to_insert.text.toString())
        }
        return view
    }

    private fun insertWord(
        selectedItem: Long,
        textToInsert: String
    ) {
        if (textToInsert.isBlank() || textToInsert.isEmpty())
            return
        when (selectedItem) {
            INTERNAL_STORAGE_ID ->
                mViewModel.addTextInternal(textToInsert)
            EXTERNAL_STORAGE_ID ->
                mViewModel.addTextExternal(textToInsert)
            SHARED_PREF_STORAGE_ID ->
                mViewModel.addTextSharedPreferences(textToInsert)
        }
    }

    companion object {
        private const val INTERNAL_STORAGE_ID = 0L
        private const val EXTERNAL_STORAGE_ID = 1L
        private const val SHARED_PREF_STORAGE_ID = 2L

        fun newInstance() =
            InsertWordFragment()
    }
}
