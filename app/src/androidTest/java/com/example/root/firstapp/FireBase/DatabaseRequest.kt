package com.example.root.firstapp.FireBase

import com.beust.klaxon.Parser
import com.example.root.firstapp.DataModels.StudentModel
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header

/**
 * Database request on FIREBASE Rest
 */

class DatabaseRequest (
        firebaseURL: String, model: StudentModel){
    /* - - - - - - - - - - - - */
    val client = AsyncHttpClient()
    val gson = GsonBuilder().create()
    var studentDBRQ = StudentModel()
    var url:String = ""
    init{
        this.url = firebaseURL
        this.studentDBRQ = model;
    }

    fun retURL(): String {
        return url;
    }
    fun sendRequest() {
        client.get(url, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                println(responseString)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                println(responseString)
                studentDBRQ = gson.fromJson(responseString, StudentModel::class.java)
            }
        })
    }


}