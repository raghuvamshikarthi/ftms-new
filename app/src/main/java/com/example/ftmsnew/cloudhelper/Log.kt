package com.example.ftmsnew.cloudhelper;

object Log {
    val DEBUG_FLAG = true
    private val DEBUG = DEBUG_FLAG
    private val ERROR = DEBUG_FLAG
    private val WARN = DEBUG_FLAG
    private val INFO = DEBUG_FLAG
    private val VERB = DEBUG_FLAG

    fun getStackTraceString(e: Throwable): String {
        return android.util.Log.getStackTraceString(e)
    }

    fun e(clazz: String, ex: Throwable) {
        if (ERROR) {
            if (null != ex.message) android.util.Log.e(clazz, ex.message)
            android.util.Log.e(clazz, ex.javaClass.name)
            for (element in ex.stackTrace) {
                android.util.Log.e(clazz, "\t" + element.toString())
            }
        }
    }

    fun v(clazz: String, msg: String) {
        if (VERB) android.util.Log.v(clazz, "" + msg)
    }

    fun i(clazz: String, msg: String) {
        if (INFO) android.util.Log.i(clazz, "" + msg)
    }

    fun w(clazz: String, msg: String) {
        if (WARN) android.util.Log.w(clazz, "" + msg)
    }

    fun e(clazz: String, msg: String) {
        if (ERROR) android.util.Log.e(clazz, "" + msg)
    }

    fun e(clazz: String, msg: String, ex: Throwable) {
        if (ERROR) {
            e(clazz, msg)
            e(clazz, ex)
        }
    }

    fun d(clazz: String, msg: String) {
        if (DEBUG) android.util.Log.d(clazz, "" + msg)
    }


}