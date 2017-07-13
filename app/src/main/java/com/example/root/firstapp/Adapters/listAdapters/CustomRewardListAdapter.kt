package com.example.root.firstapp.Adapters.listAdapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.root.firstapp.Adapters.dialogAdapters.checkoutDialogAdapter
import com.example.root.firstapp.R
import java.util.*


/**
 * Created by root on 24-6-2017.
 */
class CustomRewardListAdapter(context: Context) : BaseAdapter() {
    internal var sList = arrayOf(
            "Bakje koffie",
            "Bakje Thee",
            "Petfles Spa Blauw 0.5L",
            "Petfles Spa Rood 0.5L",
            "Broodje Kroket",
            "Broodje Gezond",
            "Broodje Kaas",
            "Broodje Kip Kerry",
            "Roomboter Croissant",
            "Tomaten Soep",
            "Red-Bull",
            "Petfles Coca-Cola 0.5L",
            "Petfles Coca-Cola Cherry 0.5L"            )
    private val picList = arrayOf<Int>(
            R.drawable.koffie,
            R.drawable.thee,
            R.drawable.spablauw,
            R.drawable.sparood,
            R.drawable.broodjekroket,
            R.drawable.broodjegezond,
            R.drawable.broodjekaas,
            R.drawable.broodjekip,
            R.drawable.cro,
            R.drawable.soeptomaat,
            R.drawable.redbull,
            R.drawable.cola,
            R.drawable.colacherry
    )
    var context: Context
    //internal var picList = arrayOf("drawable/cola.jpg")
    internal var DescList = arrayOf(
            "Een kopje koffie, de beste manier om je dag te beginnen",
            "Heerlijk bakje thee, in verschillende smaken beschikbaar",
            "Flesje bronwater Spa Blauw",
            "Flesje bronwater met prikkels!",
            "Heerlijk broodje kroket, perfect voor de lunch",
            "Gezond broodje voor de bewuste lunch",
            "Broodje Kaas",
            "Broodje Kip kerry, authentische smaak",
            "Heerlijke croissant gemaakt met room boter",
            "Heerlijk bakje tomaten soep, zoals oma het maakt",
            "Blikje Red-bull, perfect voor als je een oppeper nodig hebt",
            "Verfrissende Cola, perfect voor op een warme dag",
            "Heerlijke Cola met een kersen smaak")

    internal var kostList = arrayOf(1,1,2,2,3,3,3,3,2,2,3,2,2)
    private val mInflator: LayoutInflater

    init {
        this.mInflator = LayoutInflater.from(context)
        this.context = context
    }

    override fun getCount(): Int {
        return sList.size
    }

    override fun getItem(position: Int): Any {
        return sList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val random = Random()
        val view: View?
        val vh: container
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.reward_list_row, parent, false)
            vh = container(view,context)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as container
        }
        vh.header.text  = sList[position]
        vh.content.text = DescList[position]
        vh.imgContent.setImageResource(picList[position])
        vh.kost.text    = "Prijs: "+kostList[position].toString()+ " punten"
        vh.doSomething(position,kostList[position])
        val randomA = random.nextInt(150) + 50
        val randomB = random.nextInt(50) + 10
        val randomC = random.nextInt(100) + 30
        val randomD = random.nextInt(10) + 5
        val randomScore = random.nextInt(9) + 2
        vh.foodvalue.text = "- Energie: "+ randomA +"kcal\n- KH: "+ randomB +"gr\n- Suikers: "+ randomC+"gr\n- Vet: "+ randomD+"gr"
        vh.foodscore.text = randomScore.toString()
        // - blablabla \n- blabalbla \n- blablabla
        //vh.label.text = sList[position]
        return view
    }
}

    private class container(row: View?, context: Context) {
        public val header: TextView
        public val content: TextView
        public val kost: TextView
        public val imgContent: ImageView
        public val button: Button
        public val foodvalue: TextView
        public val foodscore: TextView
        public val context: Context



        init {
            this.header = row?.findViewById(R.id.header) as TextView
            this.content = row?.findViewById(R.id.content) as TextView
            this.kost = row?.findViewById(R.id.kost) as TextView
            this.imgContent = row?.findViewById(R.id.image) as ImageView
            this.button = row?.findViewById(R.id.buy) as Button
            this.foodvalue = row?.findViewById(R.id.foodvalue) as TextView
            this.foodscore = row?.findViewById(R.id.score) as TextView
            this.context = context as Activity

        }
        fun doSomething(position: Int,kost:Int) {
            button.setOnClickListener(object: View.OnClickListener {
                override fun onClick(view: View): Unit {
                    val alert = checkoutDialogAdapter()
                    alert.showDialog(context as Activity, header.text.toString(),kost as Int)
                }
            })
        }
    }
