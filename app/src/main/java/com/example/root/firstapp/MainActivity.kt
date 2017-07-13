package com.example.root.firstapp

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View

import com.example.root.firstapp.Adapters.CustomSlideAdapter
import android.os.Handler
import com.example.root.firstapp.DataModels.*
import com.example.root.firstapp.FireBase.activeExReq
import com.example.root.firstapp.FireBase.archiveExReq
import com.example.root.firstapp.FireBase.helpReq
import com.google.gson.GsonBuilder
import com.loopj.android.http.TextHttpResponseHandler
import com.loopj.android.http.AsyncHttpClient
import cz.msebera.android.httpclient.Header
import org.json.JSONObject




class MainActivity : AppCompatActivity() {
    val user = "0872002"
    //val adres = "http://192.168.37.1:3000/"
    val adres = "http://145.24.181.123:3000/"
    val client = AsyncHttpClient()
    val gson = GsonBuilder().create()
    val firebaseTestURL = "https://myschoolproject-b4a1a.firebaseio.com/DashboardInfo.json?print=pretty"
    val firebaseArchiveExerciseURL = "https://myschoolproject-b4a1a.firebaseio.com/ArchiveExercise.json?print=pretty"
    val firebaseActiveExerciseURL = "https://myschoolproject-b4a1a.firebaseio.com/ActiveExercise.json?print=pretty"
    val firebaseHelpURL = "https://myschoolproject-b4a1a.firebaseio.com/HelpRequests.json?print=pretty"
    var studentContainer = StudentModel()
    var exerciseContainer = exerciseCollectionModel()
    var helpContainer     = helpCollectionModel()
    //var schedualContainer = schedualModel()
    val archiveExREQ = archiveExReq(firebaseArchiveExerciseURL)
    val activeExREQ = activeExReq(firebaseActiveExerciseURL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager = findViewById(R.id.viewpager) as ViewPager
        viewPager.adapter = CustomSlideAdapter(this)
        activeExREQ.connect()
        archiveExREQ.connect()
        Handler().postDelayed({
            urlRequest("ActiveExercise")
            urlRequest("ArchiveExercise")
        }, 5000)
        getData()
    }


    fun getData(){
        urlRequest("dashboard")
        urlRequest("helpCentre")
        println("--------- DONE ALL REQUESTS -----------")
        checkDashBoardUpdate()
        //verifyUpdate()

    }
    fun urlRequest(flag:String){
        if(flag == "dashboard"){
            println("REQ DASHBOARD SECTION")

            client.get(firebaseTestURL, object : TextHttpResponseHandler() {
                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                }
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    studentContainer = gson.fromJson(responseString, StudentModel::class.java)

                }
            })
        }
       if(flag == "ActiveExercise"){
           println("REQ ActiveExercise SECTION")
           var helpRESULT = activeExREQ.model
           exerciseContainer.active_Exercises = helpRESULT.active_Exercises
       }
        if(flag == "ArchiveExercise"){
            println("REQ ArchiveExercise SECTION")
            var helpRESULT = archiveExREQ.model
            exerciseContainer.archived_Exercises = helpRESULT.archived_Exercises
        }

        if(flag == "helpCentre"){
            val helpREQ = helpReq(firebaseHelpURL)
            helpREQ.connect();
            var helpRESULT = helpREQ.model
            helpContainer = helpRESULT
        }


    }

    fun checkDashBoardUpdate(){
        val h = Handler()
        val delay = 5000//0 //milliseconds
        h.postDelayed(object : Runnable {
            override fun run() {
                urlRequest("dashboard")
                urlRequest("helpCentre")
                h.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }
    /*

                onclick events

     */

    fun newView(v: View?){
        val intent = Intent(this, RoosterActivity::class.java)
        startActivity(intent);
    }

    fun openHint(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://login.hro.nl/v1/login?service=https%3a%2f%2fhint.hr.nl%2fnl%2fHome%2f&allow=mcr-nt-upt"))
        startActivity(intent)
    }
    fun openOsiris(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://student.osiris.hro.nl:9021/osiris_student/LoginDirect.do"))
        startActivity(intent)
    }
    fun openOfficePortal(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://office365.hr.nl/portal/"))
        startActivity(intent)
    }
    fun openOutlook(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/?realm=hrnl.onmicrosoft.com&exsvurl=1&ll-cc=1033&modurl=0"))
        startActivity(intent)
    }
    fun openClassroom(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://classroom.google.com/u/0/"))
        startActivity(intent)
    }
    fun openAgenda(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/?realm=hrnl.onmicrosoft.com&exsvurl=1&ll-cc=1033&modurl=0&path=/calendar/view/Month"))
        startActivity(intent)
    }
    fun openExercises(v: View?){
        val intent = Intent(this, ExerciseActivity::class.java)
        intent.putExtra ("exerciseCollection", exerciseContainer);
        startActivity(intent)
    }
    fun openHelpCentre(v: View?){
        val intent = Intent(this, helpActivity::class.java)
        intent.putExtra ("helpCollection", helpContainer);
        startActivity(intent)
    }
    fun openRewardSystem(v: View?){
        val intent = Intent(this, AboutExerciseActivity::class.java)
        startActivity(intent)
    }
    fun openNatschool(v: View?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://natschool.hro.nl/"))
        startActivity(intent)
    }
    fun openRewards(v: View?){
        val intent = Intent(this, RewardActivity::class.java)
        startActivity(intent)
    }
    fun logOut(v: View?){
        println("logout")
    }
}

/*
        Thread(Runnable {
            val db = DatabaseRequest("http");
            println(db.retURL())
        }).start()
        */