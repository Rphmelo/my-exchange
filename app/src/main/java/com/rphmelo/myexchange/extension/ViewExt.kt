package com.rphmelo.myexchange.extension

import android.app.Activity
import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun getResourceByName(activity: Activity, resourceName: String, resourceType: String): Int? {
    return activity.resources.getIdentifier(resourceName, resourceType, activity.packageName)
}