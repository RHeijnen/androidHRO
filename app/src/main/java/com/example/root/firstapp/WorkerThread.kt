package com.example.root.firstapp

import android.os.AsyncTask.execute
import com.example.root.firstapp.Adapters.getJSONObjectFromURL
import org.json.JSONObject
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.getAs
import javax.xml.transform.Result


/**
 * Created by root on 24-6-2017.
 */
class WorkerThread : Thread()  {

    @Volatile var running = true
    var content = {}

    override fun run() {
        println("test");
        //var tempJsonOBject = getJSONObjectFromURL("https://myschoolproject-b4a1a.firebaseio.com/content.json?print=pretty");
//an extension over string (support GET, PUT, POST, DELETE with httpGet(), httpPut(), httpPost(), httpDelete())
        Fuel.get("https://myschoolproject-b4a1a.firebaseio.com/content.json?print=pretty").responseString { request, response, result ->
            //do something with response
            result.fold({ d ->
                println(d)
            }, { err ->
                //do something with error
            })
        }
        running = false;
        if (!running) return
    }
}