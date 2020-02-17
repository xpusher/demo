package com.demo.common

import android.app.Application
import android.webkit.WebSettings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.CancellationException

class SharedViewModel(var app:Application) : AndroidViewModel(app) {
    var job: Job?=null
    val exception=MutableLiveData<Exception?>()
    val dataSlider=MutableLiveData<ArrayList<String>>()
    val api=Api()
    init {
        api.uAgent=WebSettings.getDefaultUserAgent(app)
    }

    fun request() {
        job=GlobalScope.launch(Dispatchers.IO) {
            val arrayList=ArrayList<String>()
            try {
                val resp=api.apiIG.urlGetAsync(
                    "https://www.instagram.com/graphql/query/?query_hash=90cba7a4c91000cf16207e4f3bee2fa2&variables=" +
                            Gson().toJson(
                                hashMapOf(
                                    "tag_name" to "fashion",
                                    "first" to 4,
                                    "after" to ""
                                )
                            )
                ).await()

                if(resp.isSuccessful){
                    val string=resp.body()?.string()
                    val  lhm= Gson().fromJson(string, LinkedTreeMap::class.java)
                    ValueByPath(lhm).getValue("data/hashtag/edge_hashtag_to_media/edges")?.let { edges->
                        edges as ArrayList<*>
                        edges.forEach { edge->
                            edge as LinkedTreeMap<*,*>
                            ValueByPath(edge).getString("node/display_url")?.let {
                                arrayList.add(it)
                            }
                        }
                    }
                }else{
                    if(resp.code()==404)
                        throw Exception(app.getString(android.R.string.httpErrorBadUrl))
                    else
                        throw Exception(app.getString(android.R.string.unknownName))
                }

                GlobalScope.launch(Dispatchers.Main) {
                    dataSlider.value=arrayList
                }
            }catch (e:Exception){
                if(e !is CancellationException){
                    GlobalScope.launch(Dispatchers.Main) {
                        exception.value = e
                    }
                }
            }
        }
    }

    fun cancel() {
        job?.cancel(CancellationException())
    }


}