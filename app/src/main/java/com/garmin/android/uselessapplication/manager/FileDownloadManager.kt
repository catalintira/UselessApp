package com.garmin.android.uselessapplication.manager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

class FileDownloadManager : BroadcastReceiver() {
    private var downloadId = -1L

    override fun onReceive(p0: Context?, p1: Intent?) {
        val receivedId = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, EXTRA_DEFAULT_VALUE)
        if (receivedId == downloadId) {
            Toast.makeText(p0, TOAST_MESSAGE_ON_FINISH, Toast.LENGTH_SHORT).show()
        }
    }

    fun startDownload(context: Context) {
        val request = DownloadManager
            .Request(Uri.parse(SOURCE_URI)).apply {
                setTitle(TITLE)
                setDescription(DESCRIPTION)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                setAllowedOverMetered(true)
            }

        downloadId = getDownloadManager(context).enqueue(request)
    }

    fun queryDownloadStatus(context: Context) {
        val downloadManagerQuery = DownloadManager.Query()
        downloadManagerQuery.setFilterById(downloadId)
        getDownloadManager(context).query(downloadManagerQuery)?.let { cursor ->
            val reasonText: String?    // applicable only for STATUS_PAUSED & STATUS_FAILED
            if (cursor.moveToFirst()) {
                val dmId = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID))
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                val dmReason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))

                when (status) {
                    DownloadManager.STATUS_PENDING -> Log.d(getLogTag(), "[queryStatus] $dmId -> STATUS_PENDING")
                    DownloadManager.STATUS_RUNNING -> Log.d(getLogTag(), "[queryStatus] $dmId -> STATUS_RUNNING")
                    DownloadManager.STATUS_PAUSED -> {
                        reasonText = when (dmReason) {
                            DownloadManager.PAUSED_WAITING_TO_RETRY -> "PAUSED_WAITING_TO_RETRY"
                            DownloadManager.PAUSED_WAITING_FOR_NETWORK -> "PAUSED_WAITING_FOR_NETWORK"
                            DownloadManager.PAUSED_QUEUED_FOR_WIFI -> "PAUSED_QUEUED_FOR_WIFI"
                            else -> "PAUSED_UNKNOWN"
                        }
                        Log.d(getLogTag(), "[queryStatus] $dmId -> STATUS_PAUSED ($reasonText)")
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        Log.d(getLogTag(), "[queryStatus] $dmId -> STATUS_SUCCESSFUL")
                    }

                    DownloadManager.STATUS_FAILED -> {
                        reasonText = when (dmReason) {
                            DownloadManager.ERROR_FILE_ERROR -> "ERROR_FILE_ERROR"
                            DownloadManager.ERROR_UNHANDLED_HTTP_CODE -> "ERROR_UNHANDLED_HTTP_CODE"
                            DownloadManager.ERROR_HTTP_DATA_ERROR -> "ERROR_HTTP_DATA_ERROR"
                            DownloadManager.ERROR_TOO_MANY_REDIRECTS -> "ERROR_TOO_MANY_REDIRECTS"
                            DownloadManager.ERROR_INSUFFICIENT_SPACE -> "ERROR_INSUFFICIENT_SPACE"
                            DownloadManager.ERROR_DEVICE_NOT_FOUND -> "ERROR_DEVICE_NOT_FOUND"
                            DownloadManager.ERROR_CANNOT_RESUME -> "ERROR_CANNOT_RESUME"
                            DownloadManager.ERROR_FILE_ALREADY_EXISTS -> "ERROR_FILE_ALREADY_EXISTS"
                            else -> "ERROR_UNKNOWN"
                        }
                        Log.d(getLogTag(), "[queryStatus] $dmId -> STATUS_FAILED ($reasonText)")
                    }
                }
            }
        }
    }

    private fun getDownloadManager(context: Context) =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    private fun getLogTag() =
        "TEST_TAG"

    companion object {
        private const val TITLE = "ZIP Download"
        private const val EXTRA_DEFAULT_VALUE = -1L
        private const val DESCRIPTION = "Downloading the zip file"
        private const val TOAST_MESSAGE_ON_FINISH = "Download completed"
        private const val SOURCE_URI = "http://ipv4.download.thinkbroadband.com/100MB.zip"
    }
}