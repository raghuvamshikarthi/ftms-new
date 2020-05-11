package com.example.ftmsnew

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import android.os.StrictMode
import com.example.ftmsnew.cloudhelper.ApplicationThread
import com.example.ftmsnew.cloudhelper.Config


class FTMSMainApplication : Application() {


    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    override fun onCreate() {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build())
        }
        super.onCreate()
        context = this
        ApplicationThread.start()


    }

    companion object {

        var context: FTMSMainApplication? = null

    }



    override fun onTerminate() {
        ApplicationThread.stop()
        super.onTerminate()
    }


}

