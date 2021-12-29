/*
 *  Created by Mya Than Htet on 12/29/21, 9:24 AM
 *     Last modified 12/29/21, 9:24 AM
 *     Copyright (c) 2021.
 *     All rights reserved.
 */

package com.mth.myabrain.retrofit

import com.mth.myabrain.model.MessageModel
import retrofit2.Call
import retrofit2.http.*


interface RetrofitClient {


    @GET
    fun getMessage(@Url url:String): Call<MessageModel>


}



