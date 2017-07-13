package com.example.root.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.example.root.firstapp.Adapters.listAdapters.CustomExerciseListAdapter
import com.example.root.firstapp.DataModels.exerciseCollectionModel
import com.example.root.firstapp.DataModels.exerciseModel
import com.example.root.firstapp.FireBase.activeExReq
import com.example.root.firstapp.FireBase.archiveExReq
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class ExerciseActivity : AppCompatActivity() {
    val firebaseArchiveExerciseURL = "https://myschoolproject-b4a1a.firebaseio.com/ArchiveExercise.json?print=pretty"
    val firebaseActiveExerciseURL = "https://myschoolproject-b4a1a.firebaseio.com/ActiveExercise.json?print=pretty"
    val firebaseChangeValueURL    = "https://myschoolproject-b4a1a.firebaseio.com/Changeflag.json?print=pretty"
    val client = AsyncHttpClient()
    var currentContent = "active";
    var exerciseCollection = exerciseCollectionModel()
    val activeExREQ = activeExReq(firebaseActiveExerciseURL)
    val archiveExREQ = archiveExReq(firebaseArchiveExerciseURL)

    var changeFlag = "a";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseCollection = intent.getSerializableExtra("exerciseCollection") as exerciseCollectionModel
        setContentView(R.layout.activity_exercise)
        //var temp = exerciseCollection.archived_Exercises[0].activity
        //println("+++++")
        //println(temp)
        val lv = findViewById(R.id.list) as ListView
        lv.adapter = CustomExerciseListAdapter(this,exerciseCollection,"active")
        checkUpdate()
    }

    fun setArchive(v: View?){
        val lv = findViewById(R.id.list) as ListView
        lv.adapter = CustomExerciseListAdapter(this,exerciseCollection,"archive")
        val text = findViewById(R.id.exerciseLayoutDescription) as TextView
        text.text = "Hieronder vind je alle gearchiveerde opdrachten die je hebt uitgevoerd"
        currentContent = "archive"
    }
    fun setActive(v: View?){
        val lv = findViewById(R.id.list) as ListView
        lv.adapter = CustomExerciseListAdapter(this,exerciseCollection,"active")
        val text = findViewById(R.id.exerciseLayoutDescription) as TextView
        text.text = "Hieronder vind je alle beschikbare opdrachten/huiswerk opdrachten voor deze periode"
        currentContent = "active"
    }
    fun checkForChange(){
        if(currentContent == "archive"){
            println("inside change - archive")
            val lv = findViewById(R.id.list) as ListView
            lv.adapter = CustomExerciseListAdapter(this,exerciseCollection,"archive")
        }else{
            println("inside change else")
            val lv = findViewById(R.id.list) as ListView
            lv.adapter = CustomExerciseListAdapter(this,exerciseCollection,"active")
        }
    }
    fun urlRequest() {
        client.get(firebaseChangeValueURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                if (changeFlag != responseString) {
                    println("we got a change")
                    println("_from: " + changeFlag)
                    println("_to:   " + responseString)

                    changeFlag = responseString as String;
                    activeExREQ.connect();
                    archiveExREQ.connect();

                    Handler().postDelayed({
                        getData("ActiveExercise")
                        getData("ArchiveExercise")
                    }, 5000)
                }
            }
        })
    }
    fun getData(flag:String){
        if(flag == "ActiveExercise"){
            var helpRESULT = activeExREQ.model
            exerciseCollection.active_Exercises = helpRESULT.active_Exercises
            checkForChange()
        }
        if(flag == "ArchiveExercise"){
            var helpRESULT = archiveExREQ.model
            exerciseCollection.archived_Exercises = helpRESULT.archived_Exercises
            checkForChange()
        }

    }
    fun checkUpdate(){
        val h = Handler()
        val delay = 5000//0 //milliseconds
        h.postDelayed(object : Runnable {
            override fun run() {
                urlRequest()
                h.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }
}

