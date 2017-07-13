package com.example.root.firstapp.Adapters.listAdapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.root.firstapp.Adapters.dialogAdapters.helpDialogAdapter
import com.example.root.firstapp.DataModels.helpCollectionModel
import com.example.root.firstapp.DataModels.helpModel
import com.example.root.firstapp.MainActivity
import com.example.root.firstapp.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import java.util.*

/**
 * Created by root on 24-6-2017.
 */
class CustomHelpListAdapter(context: Context, exerciseContent: helpCollectionModel) : BaseAdapter() {
    private val mInflator: LayoutInflater
    internal var localListContent = LinkedList<helpModel>()
    val context: Context
    init {
        this.mInflator = LayoutInflater.from(context)
        localListContent = exerciseContent.help_Exercises
        this.context = context
    }

    override fun getCount(): Int {
        return localListContent.size
    }

    override fun getItem(position: Int): Any {
        return localListContent[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: HelpRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.help_list_row, parent, false)
            vh = HelpRowHolder(view,context,localListContent[position])
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as HelpRowHolder
        }
        if(localListContent[position].imgFlag == "js"){
            vh.exerciseIMG.setImageResource(R.drawable.js)
        }else if(localListContent[position].imgFlag == "java"){
            vh.exerciseIMG.setImageResource(R.drawable.java)
        }else if(localListContent[position].imgFlag == "sports"){
            vh.exerciseIMG.setImageResource(R.drawable.sports)
        }else if(localListContent[position].imgFlag == "python"){
            vh.exerciseIMG.setImageResource(R.drawable.python)
        }else if(localListContent[position].imgFlag == "csharp"){
            vh.exerciseIMG.setImageResource(R.drawable.csharp)
        }else if(localListContent[position].imgFlag == "html"){
            vh.exerciseIMG.setImageResource(R.drawable.html5)
        }else{
            vh.exerciseIMG.setImageResource(R.drawable.js)
        }
        vh.exerciseCode.text = localListContent[position].id
        vh.activity.text =  localListContent[position].activity + " \n \n - "+localListContent[position].contact
        vh.points.text =  "Bounty: "+localListContent[position].eligiblePoints + " Punten"
        vh.deadline.text =  "Deadline: "+localListContent[position].deadline
        var count = 0;
        count += localListContent[position].upvotes!!.toInt()
        count = count - localListContent[position].downvotes!!.toInt();
        vh.voteCount.text = count.toString()

        return view
    }
}

private class HelpRowHolder(row: View?, activity: Context, model: helpModel) {
    val exerciseCode: TextView
    val activity: TextView
    val points: TextView
    val deadline: TextView
    var layout: LinearLayout
    val context: Activity
    val exerciseIMG: ImageView
    val up: ImageView
    val down: ImageView
    val client = AsyncHttpClient()
    var voteStatus = ""
    val voteCount: TextView

    init {
        this.exerciseCode = row?.findViewById(R.id.exerciseCode) as TextView
        this.activity = row?.findViewById(R.id.activity) as TextView
        this.points = row?.findViewById(R.id.points) as TextView
        this.deadline = row?.findViewById(R.id.deadline) as TextView
        this.layout = row?.findViewById(R.id.layout) as LinearLayout
        this.context  = activity as Activity
        this.exerciseIMG = row?.findViewById(R.id.exerciseIMG) as ImageView
        this.voteStatus = model.lastvote as String
        this.up = row?.findViewById(R.id.upvote) as ImageView
        this.down = row?.findViewById(R.id.downvote) as ImageView
        this.voteCount = row?.findViewById(R.id.voteCount) as TextView

        layout.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                val alert = helpDialogAdapter()
                alert.showDialog(context,model)
            }
        })
        if(voteStatus =="none"){
            down.setColorFilter(activity.getResources().getColor(R.color.redneut));
            up.setColorFilter(activity.getResources().getColor(R.color.redneut));
        }else if(voteStatus =="up"){
            up.setColorFilter(activity.getResources().getColor(R.color.redup));
            down.setColorFilter(activity.getResources().getColor(R.color.redneut));
        }else if(voteStatus =="down"){
            up.setColorFilter(activity.getResources().getColor(R.color.redneut));
            down.setColorFilter(activity.getResources().getColor(R.color.reddown));
        }
        up.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                up.setColorFilter(activity.getResources().getColor(R.color.redup));
                down.setColorFilter(activity.getResources().getColor(R.color.redneut));
                var tempText = exerciseCode.text as String
                println("++++" + tempText +"++++")
                client.get(MainActivity().adres+"upvote?id="+tempText, object : TextHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    }

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {

                    }
                })
            }
        })
        down.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                up.setColorFilter(activity.getResources().getColor(R.color.redneut));
                down.setColorFilter(activity.getResources().getColor(R.color.reddown));
                var tempText = exerciseCode.text as String
                println("++++" + tempText +"++++")
                client.get(MainActivity().adres+"downvote?id="+tempText, object : TextHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    }

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {

                    }
                })
            }
        })
    }
}
