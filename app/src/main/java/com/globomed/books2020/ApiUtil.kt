package com.globomed.books2020

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object ApiUtil {
    private val BASE_API_URL: String = "https://www.googleapis.com/books/v1/volumes?"
    val QUERY_PARAMETER_KEY = "q"

    public fun buildUrl(title: String): URL? {

        var url: URL? = null

        val uri = Uri.parse(BASE_API_URL).buildUpon().appendQueryParameter(QUERY_PARAMETER_KEY,title).build()
        try {
            url = URL(uri.toString())
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