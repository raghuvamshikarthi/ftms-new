package com.example.ftmsnew.utils;

import android.R
import android.app.ProgressDialog
import android.content.Context
import com.example.ftmsnew.cloudhelper.ApplicationThread

class ProgressBar {
    var path: String? = null

    companion object {

        private val LOG_TAG = ProgressBar::class.java.name
        private var mProgressDialog: ProgressDialog? = null

        fun showProgressBar(context: Context, msg: String): ProgressDialog? {
            hideProgressBar()


            ApplicationThread.uiPost(LOG_TAG, "hiding progress bar") {
                try {
                    if (mProgressDialog == null) {
                        mProgressDialog = ProgressDialog(context)
                        mProgressDialog!!.setProgressStyle(R.style.Widget_ProgressBar_Large)
                        mProgressDialog!!.setMessage(msg)
                        mProgressDialog!!.isIndeterminate = true
                        mProgressDialog!!.setCancelable(false)
                        mProgressDialog!!.show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            return mProgressDialog
        }

        fun hideProgressBar() {
            ApplicationThread.uiPost(LOG_TAG, "hiding progress bar") {
                if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                    try {
                        mProgressDialog!!.dismiss()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                mProgressDialog = null
            }
        }
    }

}
