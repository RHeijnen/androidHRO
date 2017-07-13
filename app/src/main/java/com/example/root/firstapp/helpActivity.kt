package com.example.root.firstapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.root.firstapp.Adapters.listAdapters.CustomHelpListAdapter
import com.example.root.firstapp.DataModels.helpCollectionModel
import com.example.root.firstapp.DataModels.helpModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.security.SecureRandom
import android.widget.Toast
import com.example.root.firstapp.FireBase.helpReq


class helpActivity : AppCompatActivity() {
    var helpContainer = helpCollectionModel()
    val firebaseHelpURL = "https://myschoolproject-b4a1a.firebaseio.com/HelpRequests.json?print=pretty"
    val firebaseChangeValueURL    = "https://myschoolproject-b4a1a.firebaseio.com/Changeflag.json?print=pretty"
    val client = AsyncHttpClient()
    var changeFlag = "a"
    val helpREQ = helpReq(firebaseHelpURL)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helpContainer = intent.getSerializableExtra("helpCollection") as helpCollectionModel
        var localListContent = helpContainer.help_Exercises
        println("__ ids we got from main")
        for (i in 0..localListContent.size - 1){
            println(localListContent[i].id)
            println(localListContent[i].commentID)
        }
        setContentView(R.layout.activity_help)
        val lv = findViewById(R.id.helpList) as ListView
        lv.adapter = CustomHelpListAdapter(this,helpContainer)
        var helpViewInfo = findViewById(R.id.helpViewInfo) as TextView
        helpViewInfo.text = "Hieronder vind je alle uitdagingen van studenten. klik op een opdracht voor meer informatie"
        checkUpdate()
    }
    // change checker
    fun getChangeInformation() {
        client.get(firebaseChangeValueURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                if (changeFlag != responseString) {
                    println("we got a change")
                    println("_from: " + changeFlag)
                    println("_to:   " + responseString)
                    changeFlag = responseString as String;
                    helpREQ.connect();
                    Handler().postDelayed({
                        updateHelpContent()
                    }, 5000)
                }
            }
        })
    }
    fun updateHelpContent(){
        var helpRESULT = helpREQ.model
        helpContainer = helpRESULT
        setChange()
    }
    fun setChange(){
        val lv = findViewById(R.id.helpList) as ListView
        lv.adapter = CustomHelpListAdapter(this,helpContainer)
    }
    fun checkUpdate(){
        val h = Handler()
        val delay = 5000//0 //milliseconds
        h.postDelayed(object : Runnable {
            override fun run() {
                getChangeInformation()
                h.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }

    fun setViewCreate(v: View?){
        val lv = findViewById(R.id.helpList) as ListView
        lv.visibility= View.GONE
        val create = findViewById(R.id.createField) as LinearLayout
        create.visibility = View.VISIBLE
        var helpViewInfo = findViewById(R.id.helpViewInfo) as TextView
        helpViewInfo.text = "Vul het onderstaande formulier in en je verzoek voor hulp zal geindexeerd worden"

    }

    fun setViewOverview(v: View?){
        val create = findViewById(R.id.createField) as LinearLayout
        create.visibility = View.GONE
        val lv = findViewById(R.id.helpList) as ListView
        lv.visibility= View.VISIBLE
        var helpViewInfo = findViewById(R.id.helpViewInfo) as TextView
        helpViewInfo.text = "Hieronder vind je alle uitdagingen van studenten. klik op een opdracht voor meer informatie"

    }
    fun sendReqeust(v:View?){
        val titleTextValue = findViewById(R.id.titleTextValue) as EditText
        val activityTextValue = findViewById(R.id.activityTextValue) as EditText
        val activityLongTextValue = findViewById(R.id.activityLongTextValue) as EditText
        val activityDeadlineTextValue = findViewById(R.id.activityDeadlineTextValue) as EditText
        val activityPointValue = findViewById(R.id.activityPointValue) as EditText
        val activityImgValue = findViewById(R.id.activityImgValue) as EditText
        val contactTextValue = findViewById(R.id.contactTextValue) as EditText

        var id = "REQ-" +randomString(5) as String
        var titleTextValueContent = titleTextValue.text
        var activityTextValueContent = activityTextValue.text
        var activityDeadlineTextValueContent = activityDeadlineTextValue.text
        var activityLongTextValueContent = activityLongTextValue.text
        var activityPointValueContent = activityPointValue.text
        var activityImgValueContent = activityImgValue.text
        var contactTextValueContent = contactTextValue.text
        // http://localhost:3000/addHelp?id=DUMMYREPLACEMEPLZ_I_M_UNIQUE_&short=hey&desc=heyheyhey&cont=0872002@hr.nl&deadline=10-10-2018&points=5&img=js&tit=title

        var contentString = MainActivity().adres+"addHelp?id="+id+"&short="+activityTextValueContent+"&desc="+ activityLongTextValueContent +"&cont="+ contactTextValueContent +"&deadline="+ activityDeadlineTextValueContent + "&points="+ activityPointValueContent + "&img="+ activityImgValueContent + "&tit=" +titleTextValueContent
        client.get(contentString, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                Toast.makeText(getApplicationContext(), "Bericht geplaatst, het zal z.s.m. zichtbaar zijn", Toast.LENGTH_SHORT).show();
                titleTextValue.setText("");
                activityTextValue.setText("");
                activityDeadlineTextValue.setText("");
                activityLongTextValue.setText("");
                activityImgValue.setText("");
                contactTextValue.setText("");
                // close keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (v != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
                // repeat
                val create = findViewById(R.id.createField) as LinearLayout
                create.visibility = View.GONE
                val lv = findViewById(R.id.helpList) as ListView
                lv.visibility= View.VISIBLE
                var helpViewInfo = findViewById(R.id.helpViewInfo) as TextView
                helpViewInfo.text = "Hieronder vind je alle uitdagingen van studenten. klik op een opdracht voor meer informatie"
            }
        })
    }

    fun randomString(length: Int): String {
        val rnd = SecureRandom()
        val AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        var temp = "" as String
        val sb = StringBuilder(length)
        for (i in 0..length - 1)
            sb.append(
                    AB.get(
                            rnd.nextInt(
                                    AB.lastIndex
                            )
                    )
            )
        return sb.toString()
    }
}
