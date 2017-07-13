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
import com.example.root.firstapp.Adapters.dialogAdapters.exerciseDialogAdapter
import com.example.root.firstapp.DataModels.exerciseCollectionModel
import com.example.root.firstapp.DataModels.exerciseModel
import com.example.root.firstapp.R
import java.util.*

/**
 * Created by root on 24-6-2017.
 */
class CustomExerciseListAdapter(context: Context, exerciseContent: exerciseCollectionModel, flag:String) : BaseAdapter() {
    private val mInflator: LayoutInflater
    internal var localListContent = LinkedList<exerciseModel>()
    val context: Context
    var flag = ""
    init {
        this.mInflator = LayoutInflater.from(context)
        this.flag = flag
        if(flag == "active"){
            localListContent = exerciseContent.active_Exercises
        }else{
            localListContent = exerciseContent.archived_Exercises
        }
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
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.list_row, parent, false)
            vh = ListRowHolder(view,context,localListContent[position],flag)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
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
        }
        vh.exerciseCode.text = localListContent[position].classCode  + " - " + localListContent[position].exerciseTitle
        vh.activity.text =  localListContent[position].activity
        vh.points.text =  "Punten: " + localListContent[position].recievedPoints + " / " +localListContent[position].maxPoints
        vh.deadline.text =  "Deadline: "+localListContent[position].deadline
        return view
    }
}

private class ListRowHolder(row: View?, activity: Context, model: exerciseModel, flag:String) {
    val exerciseCode: TextView
    val activity: TextView
    val points: TextView
    val deadline: TextView
    var layout: LinearLayout
    val context: Activity
    val exerciseIMG: ImageView
    var flag =""

    init {
        this.exerciseCode = row?.findViewById(R.id.exerciseCode) as TextView
        this.activity = row?.findViewById(R.id.activity) as TextView
        this.points = row?.findViewById(R.id.points) as TextView
        this.deadline = row?.findViewById(R.id.deadline) as TextView
        this.layout = row?.findViewById(R.id.layout) as LinearLayout
        this.context  = activity as Activity
        this.exerciseIMG = row?.findViewById(R.id.exerciseIMG) as ImageView
        this.flag = flag;

        layout.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                val alert = exerciseDialogAdapter()
                alert.showDialog(context,model,flag)
            }
        })
    }
}
