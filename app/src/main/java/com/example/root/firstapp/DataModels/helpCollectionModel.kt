package com.example.root.firstapp.DataModels

import java.io.Serializable
import java.util.*

/**
 * Created by root on 30-6-2017.
 */
class helpCollectionModel  : Serializable {
    var help_Exercises = LinkedList<helpModel>()
};