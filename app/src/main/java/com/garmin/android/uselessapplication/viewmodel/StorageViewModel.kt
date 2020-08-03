package com.garmin.android.uselessapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.garmin.android.uselessapplication.model.DataFile
import com.garmin.android.uselessapplication.model.StatisticsDataFile
import com.garmin.android.uselessapplication.repository.InternalStorageRepository
import com.garmin.android.uselessapplication.repository.SharedPreferencesRepository
import com.garmin.android.uselessapplication.repository.SharedStorageRepository

class StorageViewModel(app: Application) : AndroidViewModel(app) {

    var mFileList: MutableLiveData<List<DataFile>> = MutableLiveData()

    private val mInternalStorageRepository = InternalStorageRepository()
    private val mSharedStorageRepository = SharedStorageRepository()
    private val mSharedPreferencesRepository = SharedPreferencesRepository()

    init {
        mInternalStorageRepository.setContext(app)
        mInternalStorageRepository.initializeData()
        mSharedPreferencesRepository.setContext(app)
        mSharedPreferencesRepository.initializeData()
        mSharedStorageRepository.setContext(app)
        mSharedStorageRepository.initializeData()
    }

    fun getInternalFiles() {
        val listOfFiles = ArrayList<DataFile>()
        listOfFiles.add(mInternalStorageRepository.genInternalFile())
        listOfFiles.add(mSharedStorageRepository.getSharedStorageFile())
        listOfFiles.add(mSharedPreferencesRepository.getSharedPrefFile())
        mFileList.postValue(listOfFiles)
    }

    fun getStatisticalFiles() =
        mFileList.value?.map {
            StatisticsDataFile.fromDataFile(it)
        }

    fun addTextInternal(text: String) =
        mInternalStorageRepository.insertText(text)


    fun addTextExternal(text: String) {
        mSharedStorageRepository.insertText(text)
    }

    fun addTextSharedPreferences(text: String) {
        mSharedPreferencesRepository.insertText(text)
    }
}