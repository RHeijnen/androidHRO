package com.example.root.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.root.firstapp.Adapters.listAdapters.CustomRewardListAdapter
import kotlinx.android.synthetic.main.list_row.*


class RewardActivity : AppCompatActivity() {
    var pointBalance = 5;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        var pointfield = findViewById(R.id.currentpoints) as TextView
        pointfield.text = "Punten beschikbaar: "+ pointBalance
        var act = activity;
        val lv = findViewById(R.id.rewardList) as ListView
        lv.adapter = CustomRewardListAdapter(this)
    }

}
