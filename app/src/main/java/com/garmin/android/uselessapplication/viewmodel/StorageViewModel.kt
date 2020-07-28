package com.garmin.android.uselessapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.garmin.android.uselessapplication.model.DataFile
import com.garmin.android.uselessapplication.repository.InternalStorageRepository
import com.garmin.android.uselessapplication.repository.SharedPreferencesRepository
import com.garmin.android.uselessapplication.repository.SharedStorageRepository

class StorageViewModel(private val app: Application) : AndroidViewModel(app) {

    var mFileList: MutableLiveData<List<DataFile>> = MutableLiveData()

    private val internalStorageRepository = InternalStorageRepository()
    private val sharedStorageRepository = SharedStorageRepository()
    private val sharedPreferencesRepository = SharedPreferencesRepository()

    fun setUp() {
        internalStorageRepository.setContext(app)
        internalStorageRepository.initializeData()
        sharedPreferencesRepository.setContext(app)
        sharedPreferencesRepository.initializeData()
        sharedStorageRepository.setContext(app)
        sharedStorageRepository.initializeData()
    }

    fun getInternalFiles() {
        val listOfFiles = ArrayList<DataFile>()
        listOfFiles.add(internalStorageRepository.genInternalFile())
        listOfFiles.add(sharedPreferencesRepository.getSharedPrefFile())
        mFileList.postValue(listOfFiles)
    }
}