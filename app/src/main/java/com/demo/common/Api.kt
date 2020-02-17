package com.demo.common

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.Exception
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

class Api {
    val client: OkHttpClient
    val mCookieManager: java.net.CookieManager
    val apiIG:ApiIG
    lateinit var uAgent:String
    init {
        mCookieManager = java.net.CookieManager()
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        client=OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(mCookieManager))
            .addInterceptor { chain ->
                try {
                    val request=chain.request()
                    val host = request.url().host()
                    when {
                        host.contains("i.instagram.com",true) -> {
                            chain.proceed(request)
                        }
                        host.contains("instagram.com",true) -> {
                            chain.proceed(request.newBuilder().addHeader("User-Agent",uAgent).build())
                        }
                        else -> {
                            chain.proceed(request)
                        }
                    }
                }
                catch (e: Exception){
                    okhttp3.Response.Builder()
                        .code(404)
                        .protocol(Protocol.HTTP_1_0)
                        .request(Request.Builder().url("http://localhost").build())
                        .message("Net error")
                        .body(ResponseBody.create(MediaType.parse("text"),e.toString()))
                        .build()
                }
            }
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
        apiIG= Retrofit.Builder()
            .baseUrl("https://www.instagram.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ApiIG::class.java)

    }
    interface ApiIG
    {

        @GET
        fun urlGetAsync(@Url url:String): Deferred<Response<ResponseBody>>

        @FormUrlEncoded
        @POST
        fun urlPostAsync(
            @Url url:String,
            @Header("X-CSRFToken") csrftoken:String,
            @FieldMap hm:HashMap<String, String>
        ): Deferred<Response<ResponseBody>>

    }
}