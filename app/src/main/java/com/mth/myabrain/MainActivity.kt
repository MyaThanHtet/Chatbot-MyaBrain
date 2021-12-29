/*
 *  Created by Mya Than Htet on 12/28/21, 5:22 PM
 *     Last modified 12/28/21, 4:08 PM
 *     Copyright (c) 2021.
 *     All rights reserved.
 */

package com.mth.myabrain

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mth.myabrain.adapter.ChatRecyclerAdapter
import com.mth.myabrain.model.ChatModel
import com.mth.myabrain.model.MessageModel
import com.mth.myabrain.retrofit.RetrofitBulider
import com.mth.myabrain.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var chatsRecyclerViwe: RecyclerView
    private lateinit var sendMsgIB: ImageButton
    private lateinit var userMsgEdt: EditText
    private lateinit var messageModelModalArrayList: ArrayList<ChatModel>
    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter

    private val USER_KEY = "user"
    private val BOT_KEY = "bot"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            val titleText = "MyaBrain"
            val titleTextColor = ForegroundColorSpan(Color.BLACK)
            val spannString = SpannableString(titleText)
            spannString.setSpan(
                titleTextColor, 0, titleText.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            title = spannString
        }
        chatsRecyclerViwe = findViewById(R.id.idRVChats)
        sendMsgIB = findViewById(R.id.idIBSend)
        userMsgEdt = findViewById(R.id.idEdtMessage)
        messageModelModalArrayList = arrayListOf<ChatModel>()
        sendMsgIB.setOnClickListener {
            if (userMsgEdt.text.toString().isEmpty()) {

                Toast.makeText(
                    applicationContext,
                    "Please enter your message..",
                    Toast.LENGTH_SHORT
                ).show();

            } else {
                sendMessage(userMsgEdt.text.toString());

                userMsgEdt.setText("");
            }


        }


        chatRecyclerAdapter = ChatRecyclerAdapter(messageModelModalArrayList, this)
        val linearLayoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        chatsRecyclerViwe.layoutManager = linearLayoutManager
        chatsRecyclerViwe.adapter = chatRecyclerAdapter
    }

    private fun sendMessage(userMsg: String) {
        messageModelModalArrayList.add(ChatModel(userMsg, USER_KEY))
        chatRecyclerAdapter.notifyDataSetChanged()
        var url =
            "http://api.brainshop.ai/get?bid=162431&key=k3wHupdOOCVidgiE&uid=[uid]&msg=$userMsg"


        val retrofit = RetrofitBulider.buildService(RetrofitClient::class.java)
        retrofit.getMessage(url)
            .enqueue(
                object : Callback<MessageModel> {

                    override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                        Log.i(MainActivity::class.simpleName, "on FAILURE!!!!$t")
                    }


                    override fun onResponse(
                        call: Call<MessageModel>,
                        response: Response<MessageModel>
                    ) {
                        if (response.isSuccessful) {
                            val model: MessageModel = response.body()!!


                            messageModelModalArrayList.add(ChatModel(model.cnt, BOT_KEY))
                            chatRecyclerAdapter.notifyDataSetChanged()

                            Log.i(MainActivity::class.simpleName, "${model.cnt}")

                        }

                    }
                }
            )

    }
}