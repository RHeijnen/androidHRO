package com.example.root.firstapp.Adapters.dialogAdapters

import android.widget.TextView
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.View
import android.view.Window
import com.example.root.firstapp.R
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout


/**
 * Created by root on 27-6-2017.
 */
class checkoutDialogAdapter {
    fun showDialog(activity: Activity, msg: String, cost:Int) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.food_dialog)

        var textContent = dialog.findViewById(R.id.text_dialog) as TextView
        textContent.text = msg
        var textdescr = dialog.findViewById(R.id.dialogTitleFood) as TextView
        dialog.setCanceledOnTouchOutside(true);
        // close dialog butn
        val extraCloseBtn = dialog.findViewById(R.id.btn_close) as ImageView
        extraCloseBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                    dialog.dismiss();
            }
        })
        // load icon
        val rotateAnimation = RotateAnimation(360f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)

        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = Animation.INFINITE
        dialog.findViewById(R.id.loadicon).startAnimation(rotateAnimation)
        var background = dialog.findViewById(R.id.dialogLL) as LinearLayout
        var tempBtn = dialog.findViewById(R.id.loadicon) as ImageView
        var bought = false;
        tempBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                if(bought == false){
                    var temp = activity.findViewById(R.id.currentpoints) as TextView
                    var tempContent = temp.text
                    val parts = tempContent.split(": ")
                    val result = Integer.parseInt(parts[1])
                    val remainder  = result - cost
                    if(remainder == 0 || remainder > 0){
                        bought = true
                        background.setBackgroundColor(Color.parseColor("#66BB6A"));
                        tempBtn.setImageResource(R.drawable.ic_mood_black_24dp);
                        textdescr.text = " "
                        textContent.text = "Success, je hebt betaald!"
                        dialog.findViewById(R.id.loadicon).clearAnimation()
                        temp.text = parts[0] + ": " + remainder
                    }else{
                        tempBtn.setImageResource(R.drawable.ic_mood_bad_black_24dp);
                        textdescr.text = " "
                        textContent.text = "Helaas, je hebt niet genoeg punten."
                        dialog.findViewById(R.id.loadicon).clearAnimation()
                    }

                }



            }
        })
        //
        dialog.show()
    }
}