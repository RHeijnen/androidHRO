package com.example.root.firstapp.FireBase

import com.example.root.firstapp.DataModels.*
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by root on 1-7-2017.
 */
class archiveExReq (firebaseURL: String) {
    var connectionURL = firebaseURL
    var model         = exerciseCollectionModel()
    /* - - - - - - - - - - - - */
    val client = AsyncHttpClient()
    val gson = GsonBuilder().create()

    fun connect(){
        client.get(connectionURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                val mainObject = JSONObject(responseString)
                model.archived_Exercises.clear()

                //println(mainObject)
                for (i in 0..mainObject.names().length() - 1){
                    var tempExerciseData = exerciseModel()
                    tempExerciseData.classCode = mainObject.names().getString(i);
                    val tempString = mainObject.get(mainObject.names().getString(i)) as JSONObject
                    println("REQ ARCHIVE EXERCISE SECTION")

                    for (j in 0..tempString.names().length() - 1){
                        var tempKey = tempString.names().get(j);
                        var tempValue = tempString.get(tempString.names().get(j) as String?)
                        if(tempKey == "activity"){   // todo cleanup
                            tempExerciseData.activity = tempValue as String?;
                        }
                        if(tempKey == "activityLong"){   // todo cleanup
                            tempExerciseData.activityLong = tempValue as String?;
                        }
                        if (tempKey =="deadline"){
                            tempExerciseData.deadline = tempValue as String?;
                        }
                        if(tempKey == "recievedPoints"){
                            tempExerciseData.recievedPoints = tempValue as String?;
                        }
                        if(tempKey == "maxPoints"){
                            tempExerciseData.maxPoints = tempValue as String?;
                        }
                        if(tempKey == "exerciseCode"){
                            tempExerciseData.exerciseCode = tempValue as String?;
                        }
                        if(tempKey == "exerciseTitle"){
                            tempExerciseData.exerciseTitle = tempValue as String?;
                        }
                        if(tempKey == "contact"){
                            tempExerciseData.contact = tempValue as String?;
                        }
                        if(tempKey == "imgFlag"){
                            tempExerciseData.imgFlag = tempValue as String?;
                        }
                        if(tempKey == "notes"){
                            tempExerciseData.notes = tempValue as String?;
                        }
                        if(tempKey == "progres"){
                            tempExerciseData.progres = tempValue as String?;
                        }
                        if(tempKey == "demands"){
                            tempExerciseData.demands = tempValue as String?;
                        }
                        //println(tempKey)
                        //println(tempValue)
                    }
                    model.archived_Exercises.add(tempExerciseData)
                }
            }
        })
    }
}
