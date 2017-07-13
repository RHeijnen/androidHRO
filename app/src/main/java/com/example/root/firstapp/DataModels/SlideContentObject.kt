package com.example.root.firstapp.DataModels

import com.example.root.firstapp.R

/**
 * Created by root on 22-6-2017.
 */
enum class SlideContentObject private constructor(titleResId:Int,
                                                  layoutResId:Int) {
    View1(R.string.gradeSection, R.layout._slide_content_grade),
    View2(R.string.classSection, R.layout._slide_content_classes),
    View3(R.string.studySection, R.layout._slide_content_progress),
    View4(R.string.pointSection, R.layout._slide_content_points);
    var titleResId:Int = 0
    var layoutResId:Int = 0
    init{
        this.titleResId = titleResId
        this.layoutResId = layoutResId
    }
}