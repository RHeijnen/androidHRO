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
import com.example.root.firstapp.DataModels.exerciseCollectionModel
import com.example.root.firstapp.DataModels.exerciseModel
import com.example.root.firstapp.DataModels.helpModel
import com.example.root.firstapp.R
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.util.*

/**
 * Created by root on 24-6-2017.
 */
class CustomCommentListAdapter(context: Context, content: helpModel) : BaseAdapter() {
    private val mInflator: LayoutInflater
    internal var localListContent = LinkedList<exerciseModel>()
    val context: Context
    val gson = GsonBuilder().create()
    var commentIDObject: org.json.JSONArray
    var commentContentObject: org.json.JSONArray
    var commentDateObject: org.json.JSONArray
    init {
        this.mInflator = LayoutInflater.from(context)
        this.context = context
        if(content.commentID != null){
            commentIDObject = org.json.JSONArray(content.commentID)
            commentContentObject = org.json.JSONArray(content.commentContent)
            commentDateObject = org.json.JSONArray(content.commentDate)
        }else{
            commentIDObject = org.json.JSONArray("[' ']")
            commentContentObject = org.json.JSONArray("['Nog geen comments']")
            commentDateObject = org.json.JSONArray("[' ']")
        }

    }

    override fun getCount(): Int {
        return commentIDObject.length()
    }

    override fun getItem(position: Int): Any {
        return commentIDObject[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: commentHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.comment_row, parent, false)
            vh = commentHolder(view,context)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as commentHolder
        }
        vh.date.text = commentDateObject[position] as String
        vh.content.text =  commentContentObject[position] as String
        vh.author.text =  commentIDObject[position] as String
        return view
    }
}

private class commentHolder(row: View?, activity: Context) {
    val date: TextView
    val content: TextView
    val author: TextView


    init {
        this.date = row?.findViewById(R.id.commentDate) as TextView
        this.content = row?.findViewById(R.id.commentContent) as TextView
        this.author = row?.findViewById(R.id.commentAuthor) as TextView
    }
}
