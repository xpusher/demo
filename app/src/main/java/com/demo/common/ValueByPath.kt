package com.demo.common

import com.google.gson.internal.LinkedTreeMap

class ValueByPath(var linkedTreeMap: LinkedTreeMap<*, *>?){

    fun getValue(path:String):Any?{


        var returnValue:Any?=linkedTreeMap

        val arrayKey=path.split("/")
        arrayKey.forEach {key->
            returnValue
                ?.let { t->
                    if(t is LinkedTreeMap<*,*>)
                    {
                        returnValue=t[key]
                    }
                    else
                    {
                        return@forEach
                    }
                }
                ?: kotlin.run {
                    return@forEach
                }
        }


        return returnValue
    }
    fun getString(path:String):String?{
        getValue(path)?.let {
            return try {
                it as String
            }catch (e:Exception){
                null
            }
        } ?: kotlin.run {
            return null
        }
    }
}