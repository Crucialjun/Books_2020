package com.globomed.books2020

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object ApiUtil {
    private val BASE_API_URL: String = "https://www.googleapis.com/books/v1/volumes"

    public fun buildUrl(title: String): URL? {
        val fullUrl = BASE_API_URL + "q=" + title
        var url: URL? = null
        try {
            url = URL(fullUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return url
    }

    @Throws(IOException::class)
    fun getJson(url: URL): String? {
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        try {
            val stream: InputStream = connection.inputStream
            val scanner = Scanner(stream)
            scanner.useDelimiter("\\A")

            val hasData = scanner.hasNext()
            if (hasData) {
                return scanner.next()
            } else {
                return null
            }
        } catch (e: Exception) {
            Log.d("Error", "getJson: ${e.toString()}")
            return null
        } finally {
            connection.disconnect()
        }
    }
}