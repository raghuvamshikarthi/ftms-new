package com.example.ftmsnew.common;

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

import com.example.ftmsnew.FTMSMainApplication
import com.example.ftmsnew.FTMSMainApplication.Companion.context


import java.io.File
import java.io.IOException

class CommonUiUtils {

    private var finalFile: File? = null

    private var sharedPreferences: SharedPreferences? = null

    @Throws(IOException::class)
    fun createImageFile(picType: String): File {
        sharedPreferences = FTMSMainApplication.context!!.getSharedPreferences("appprefs", Context.MODE_PRIVATE)
        val pictureDirectory = File(CommonUtils.getFTMSFileRootPath() + "FTMS_Pictures/" + picType)
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdirs()
        }
        when (picType) {
            "MaintenanceImage" -> {

                var next = sharedPreferences!!.getInt("increment",0)

                if(next == 0)
                {
                    CommonConstants.fileName = CommonConstants.ImageCode+"_1"
                    finalFile = File(pictureDirectory, CommonConstants.fileName+ CommonConstants.JPEG_FILE_SUFFIX)
                    sharedPreferences!!.edit().putInt("increment", 1).apply()
                }
                else{
                    next += 1

                    if(TextUtils.isEmpty(CommonConstants.fileName))
                    {
                        CommonConstants.fileName = CommonConstants.ImageCode+"_"+next
                        sharedPreferences!!.edit().putInt("increment", next).apply()
                    }
                    finalFile = File(pictureDirectory, CommonConstants.fileName+ CommonConstants.JPEG_FILE_SUFFIX)


                }


                return finalFile!!
            }
        }
        return pictureDirectory
    }


}
