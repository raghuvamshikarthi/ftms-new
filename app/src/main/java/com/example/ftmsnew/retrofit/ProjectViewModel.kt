package com.example.ftmsnew.retrofit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ftmsnew.database.FTMSDatabase
import com.google.gson.JsonObject
import okhttp3.ResponseBody

class ProjectViewModel: ViewModel() {

    private lateinit var masterList: LiveData<MasterList>
    private lateinit var transactionLlist:LiveData<ResponseBody>
    private lateinit var locationDetails: LiveData<ResponseBody>
    var dataBase: FTMSDatabase?=null



    fun getMasterData():LiveData<MasterList>{

        if(!::masterList.isInitialized){
            masterList = MutableLiveData()
            masters()

        }

        return masterList
    }
    private fun masters(){
        masterList = ProjectRepository.getInstance().getMaster()
    }

    fun sendTransData(commonList: TranList):LiveData<ResponseBody>{
        if(!::transactionLlist.isInitialized){
            transactionLlist = MutableLiveData()
            transactions(commonList)

        }

        return transactionLlist

    }
    private fun transactions(commonList: TranList){
        transactionLlist=ProjectRepository.getInstance().getTransaction(commonList)
    }



//    fun transactionSync(context: Context):LiveData<ResponseBody>{
//        dataBase= FTMSDatabase(context)
//        val tList= dataBase!!.getTransList()
//        val result =  sendTransData(tList!!)
//
//
//        return  result
//    }




}

