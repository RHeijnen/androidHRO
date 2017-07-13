package com.example.root.firstapp.Adapters

import org.json.JSONObject
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by root on 24-6-2017.
 */
@Throws(IOException::class, JSONException::class)
fun getJSONObjectFromURL(urlString: String): JSONObject {

    var urlConnection: HttpURLConnection? = null

    val url = URL(urlString)

    urlConnection = url.openConnection() as HttpURLConnection

    urlConnection!!.setRequestMethod("GET")
    urlConnection!!.setReadTimeout(10000 /* milliseconds */)
    urlConnection!!.setConnectTimeout(15000 /* milliseconds */)

    urlConnection!!.setDoOutput(true)

    urlConnection!!.connect()

    val br = BufferedReader(InputStreamReader(url.openStream()))

    val buffer = CharArray(1024)

    var jsonString = String()

    val sb = StringBuilder()
    var line: String = ""
    while (br.readLine() != null) {
        sb.append(line + "\n")
    }
    br.close()

    jsonString = sb.toString()

    println("JSON: " + jsonString)

    return JSONObject(jsonString)
}