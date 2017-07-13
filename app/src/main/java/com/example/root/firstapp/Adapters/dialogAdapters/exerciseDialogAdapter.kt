package com.example.root.firstapp.Adapters.dialogAdapters

import android.widget.TextView
import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import com.example.root.firstapp.R
import android.widget.ImageView
import android.widget.Toast
import com.example.root.firstapp.DataModels.exerciseModel


/**
 * Created by root on 27-6-2017.
 */
class exerciseDialogAdapter {
    fun showDialog(activity: Activity, model: exerciseModel, flag:String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.exercise_dialog)
        val title = dialog.findViewById(R.id.dialogTitle) as TextView
        title.text = model.exerciseTitle
        val description = dialog.findViewById(R.id.dialogDescription) as TextView
        description.text = model.classCode + " - " + model.exerciseCode
        val dialogContact = dialog.findViewById(R.id.dialogContact) as TextView
        dialogContact.text = model.contact
        val dialogPoints = dialog.findViewById(R.id.dialogPoints) as TextView
        dialogPoints.text = model.maxPoints + " Punten"
        val dialogContent = dialog.findViewById(R.id.dialogContent) as TextView
        dialogContent.text = model.activityLong
        val dialogDemands = dialog.findViewById(R.id.dialogDemands) as TextView
        dialogDemands.text = "Eisen: " +model.demands
        if(flag == "archive"){
            // TODO disable buttons
        }
        val startBtn = dialog.findViewById(R.id.exerciseSTART) as Button
        val statusBtn = dialog.findViewById(R.id.exerciseSTATUS) as Button
        var boolii = false;
        startBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                if(boolii == false){
                    Toast.makeText(activity, "Ingeschreven voor de opdracht, De docent zal contact z.s.m. contact met je opnemen.", Toast.LENGTH_SHORT).show();
                    startBtn.setEnabled(false);
                    boolii = true;
                }else{
                    Toast.makeText(activity, "Je bent al ingeschreven, De docent zal contact z.s.m. contact met je opnemen.", Toast.LENGTH_SHORT).show();
                }
            }
        })
        var infoVisible = true
        statusBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                if(infoVisible){
                    dialogContent.text = "Feedback: " + model.notes
                    statusBtn.text = "Opdracht Info"
                    infoVisible = false
                }else{
                    dialogContent.text = model.activityLong
                    statusBtn.text = "Bekijk feedback"
                    infoVisible= true
                }
            }
        })
        dialog.setCanceledOnTouchOutside(true);
        // close dialog butn
        val extraCloseBtn = dialog.findViewById(R.id.btn_close) as ImageView
        extraCloseBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                    dialog.dismiss();
            }
        })
        //
        dialog.show()
    }
}