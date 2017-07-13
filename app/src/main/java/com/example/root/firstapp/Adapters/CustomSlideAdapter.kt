package com.example.root.firstapp.Adapters

/**
 * Created by root on 22-6-2017.
 */
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.root.firstapp.DataModels.SlideContentObject
import com.example.root.firstapp.R

class CustomSlideAdapter(context: Context): PagerAdapter() {
    override fun getCount(): Int {
        return SlideContentObject.values().size
    }
    private val mContext: Context
    init{
        mContext = context
    }
    override fun instantiateItem(collection: ViewGroup, position:Int):Any {
        val modelObject = SlideContentObject.values()[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(modelObject.layoutResId,
                collection, false) as ViewGroup

        collection.addView(layout)
        return layout
    }
    override fun destroyItem(collection: ViewGroup, position:Int, view:Any) {
        collection.removeView(view as View)
    }
    override fun isViewFromObject(view: View, `object`:Any):Boolean {
        return view === `object`
    }
    override fun getPageTitle(position:Int):CharSequence {
        val customPagerEnum = SlideContentObject.values()[position]
        return mContext.getString(customPagerEnum.titleResId)
    }
}