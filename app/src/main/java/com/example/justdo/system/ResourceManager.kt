package com.example.justdo.system

import android.content.Context
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(id: Int) = context.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) = String.format(context.getString(id, *formatArgs))
}