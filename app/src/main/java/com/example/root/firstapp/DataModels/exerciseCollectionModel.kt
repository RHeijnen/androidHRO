package com.example.root.firstapp.DataModels

import java.io.Serializable
import java.util.*

/**
 * Created by root on 24-6-2017.
 */
class exerciseCollectionModel  : Serializable {
    var active_Exercises = LinkedList<exerciseModel>()
    var archived_Exercises = LinkedList<exerciseModel>()
};