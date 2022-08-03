/*
 *  Created by Mya Than Htet on 12/29/21, 9:19 AM
 *     Last modified 12/29/21, 9:19 AM
 *     Copyright (c) 2021.
 *     All rights reserved.
 */

package com.mth.myabrain.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    private val client = OkHttpClient.Builder().build()
    private var gson = GsonBuilder()
        .setLenient()
        .create()!!
    private const val BASE_URL="http://api.brainshop.ai/"
    /* retrofit with GsonConverterFactory*/
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}