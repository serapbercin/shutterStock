package com.serapbercin.shutterstock

import android.support.test.InstrumentationRegistry
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

val gsonUpperCamel = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .create()!!

inline fun <reified T> Gson.from2(fileName: String): T {

    val assets = InstrumentationRegistry.getContext().resources.assets
    val resource = assets.open(fileName)
    val reader = BufferedReader(InputStreamReader(resource, "UTF-8"))

    val type = object : TypeToken<T>() {}.type

    return fromJson(reader, type)
}

infix inline fun <reified T> String.parseFile(gson: Gson): T = gson.from2(this)