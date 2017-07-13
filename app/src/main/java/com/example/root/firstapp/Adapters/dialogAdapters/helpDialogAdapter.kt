package com.example.root.firstapp.Adapters.dialogAdapters

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import com.example.root.firstapp.R
import android.widget.*
import com.example.root.firstapp.Adapters.listAdapters.CustomCommentListAdapter
import com.example.root.firstapp.DataModels.helpModel
import com.example.root.firstapp.MainActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header


/**
 * Created by root on 27-6-2017.
 */
class helpDialogAdapter {
    fun showDialog(activity: Activity, content: helpModel) {
        val dialog = Dialog(activity)
        val client = AsyncHttpClient()

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.help_dialog)
        dialog.setCanceledOnTouchOutside(true);
        // extra close button
        val extraCloseBtn   = dialog.findViewById(R.id.btn_close) as ImageView
        val helpStart       = dialog.findViewById(R.id.helpSTART) as Button
        val helpCOMMENT     = dialog.findViewById(R.id.helpCOMMENT) as Button
        val helpContent     = dialog.findViewById(R.id.helpContent) as TextView
        val helpTitle       = dialog.findViewById(R.id.helpTitle) as TextView
        val helpDescription = dialog.findViewById(R.id.helpDescription) as TextView
        val helpContact     = dialog.findViewById(R.id.helpContact) as TextView
        val helpPoints      = dialog.findViewById(R.id.helpPoints) as TextView
        val infoOverview    = dialog.findViewById(R.id.helpInfo) as LinearLayout
        val commentSection  = dialog.findViewById(R.id.helpCommentList) as ListView
        val commentContainer = dialog.findViewById(R.id.commentContainer) as LinearLayout
        val sendComment    = dialog.findViewById(R.id.sendComment) as Button
        val commentAuthor    = dialog.findViewById(R.id.commentAuthor) as EditText
        val commentContent    = dialog.findViewById(R.id.commentContent) as EditText

        val down: ImageView

        helpContent.text = content.activityLong
        helpTitle.text = content.id
        helpDescription.text = content.activity
        helpContact.text = content.contact
        helpPoints.text = "Punten: " + content.eligiblePoints as String
        if(content.status =="accepted"){
            helpStart.setEnabled(false);
            helpStart.text = "Uitdaging aangenomen"
            helpContent.text = "Je hebt je aangemeld om deze student te helpen, er is bericht verstuurd naar de student. \n je kunt ook zelf contact opnemen met de student met een evt. oplossing of hieronder commentaar plaatsen"
        }
        extraCloseBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                dialog.dismiss();
            }
        })
        sendComment.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                sendComment.setEnabled(false);
                var commentAuthor = commentAuthor.text
                var commentContent = commentContent.text
               // http://localhost:3000/comment?id=REQ-423&uid=123&cnt=694
                client.get(MainActivity().adres+"comment?id="+content.id +"&uid=" +commentAuthor+ "&cnt="+commentContent, object : TextHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    }

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                        Toast.makeText(activity, "Commentaar geplaatst, het zal z.s.m. zichtbaar zijn", Toast.LENGTH_SHORT).show();

                    }
                })




            }
        })

        var booliClicked = false;
        helpCOMMENT.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                if(booliClicked == false){
                    helpCOMMENT.text = "Opdracht Info"
                    infoOverview.visibility = View.GONE
                    commentContainer.visibility = View.VISIBLE
                    booliClicked = true;
                }else{
                    helpCOMMENT.text = "Comments"
                    infoOverview.visibility = View.VISIBLE
                    commentContainer.visibility = View.GONE
                    booliClicked = false;
                }
            }
        })
        helpStart.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                helpContent.text = "Je hebt je aangemeld om deze student te helpen, er is bericht verstuurd naar de student. \n je kunt ook zelf contact opnemen met de student met een evt. oplossing. Ook kan je hieronder commentaar achterlaten"
                client.get(MainActivity().adres+"startHelp?id="+content.id, object : TextHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    }

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    }
                })


            }
        })



        dialog.show()
        commentSection.adapter = CustomCommentListAdapter(activity,content)


    }
}