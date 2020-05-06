package com.example.ftmsnew.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectRepository {
    private var apiService: RetrofitInterface? = null
    var commonList:TranList?=null

    init {
        apiService = RetrofitInstance.buildService(RetrofitInterface::class.java)

    }
    companion object {
        private var projectRepository: ProjectRepository? = null

        @Synchronized
        fun  getInstance(): ProjectRepository {

            if (projectRepository == null) {
                projectRepository = ProjectRepository()
            }

            return projectRepository as ProjectRepository

        }

    }
    fun getMaster():LiveData<MasterList>{

        val data = MutableLiveData<MasterList>()

        apiService!!.gettingMasterDetails().enqueue(object : Callback<MasterList>{
            override fun onResponse(call: Call<MasterList>, response: Response<MasterList>) {

                if(response.isSuccessful) {
                    data.value = response.body()
                }
            }
            override fun onFailure(call: Call<MasterList>, t: Throwable) {
                Log.v("error",t.message)
            }

        })

        return data
    }
    fun getTransaction(commonList: TranList): MutableLiveData<ResponseBody> {
        val data = MutableLiveData<ResponseBody>()

        apiService!!.sendingDetails(commonList).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v("error",t.message)

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    Log.v("sentData","compleate")
                    data.value=response.body()

                }

            }

        })

      return data

    }




}