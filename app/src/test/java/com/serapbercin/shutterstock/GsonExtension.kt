package com.serapbercin.shutterstock

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

val gsonUpper = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()!!

inline fun <reified T> Gson.from(fileName: String): T {
    val type = object : TypeToken<T>() {}.type
    val resource = javaClass.classLoader.getResourceAsStream(fileName)
    val reader = BufferedReader(InputStreamReader(resource, "UTF-8"))
    return fromJson(reader, type)
}

infix inline fun <reified T> String.parseFileWith(gson: Gson): T = gson.from(this)

