package com.example.root.firstapp.FireBase

import com.example.root.firstapp.DataModels.StudentModel
import com.example.root.firstapp.DataModels.helpCollectionModel
import com.example.root.firstapp.DataModels.helpModel
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by root on 1-7-2017.
 */
class helpReq (firebaseURL: String) {
    var connectionURL = firebaseURL
    var model         = helpCollectionModel()
    /* - - - - - - - - - - - - */
    val client = AsyncHttpClient()
    val gson = GsonBuilder().create()

    fun connect(){
        client.get(connectionURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                println("REQ HELP SECTION")
                var responseObject = JSONObject(responseString)
                model.help_Exercises.clear()
                for (i in 0..responseObject.names().length() - 1){
                    var tempObject = helpModel()
                    tempObject.id = responseObject.names().getString(i);
                    val tempString = responseObject.get(responseObject.names().getString(i)) as JSONObject
                    for (j in 0..tempString.names().length() - 1){
                        var tempKey = tempString.names().get(j);
                        var tempValue = tempString.get(tempString.names().get(j) as String?)
                        //println("__helpmodel: "+tempKey as String +"__"+tempValue as String )
                        if(tempKey == "activity"){   // todo cleanup
                            tempObject.activity = tempValue as String?;
                        }
                        if(tempKey == "activityLong"){   // todo cleanup
                            tempObject.activityLong = tempValue as String?;
                        }
                        if(tempKey == "contact"){   // todo cleanup
                            tempObject.contact = tempValue as String?;
                        }
                        if(tempKey == "deadline"){   // todo cleanup
                            tempObject.deadline = tempValue as String?;
                        }
                        if(tempKey == "downvotes"){   // todo cleanup
                            tempObject.downvotes = tempValue as String?;
                        }
                        if(tempKey == "upvotes"){   // todo cleanup
                            tempObject.upvotes = tempValue as String?;
                        }
                        if(tempKey == "eligiblePoints"){   // todo cleanup
                            tempObject.eligiblePoints = tempValue as String?;
                        }
                        if(tempKey == "imgFlag"){
                            tempObject.imgFlag = tempValue as String?;
                        }
                        if(tempKey == "lastvote"){
                            tempObject.lastvote = tempValue as String?;
                        }
                        if(tempKey == "title"){
                            tempObject.title = tempValue as String?;
                        }
                        if(tempKey == "status"){
                            tempObject.status = tempValue as String?;
                        }
                        if(tempKey == "commentID"){
                            tempObject.commentID = tempValue.toString();
                        }
                        if(tempKey == "commentContent"){
                            tempObject.commentContent = tempValue.toString();
                        }
                        if(tempKey == "commentDate"){
                            tempObject.commentDate = tempValue.toString();
                        }
                    }
                    model.help_Exercises.add(tempObject)
                }
            }
        })
    }
}
