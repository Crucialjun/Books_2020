package com.globomed.books2020

import android.net.Uri
import android.net.UrlQuerySanitizer
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

object ApiUtil {
    private const val BASE_API_URL: String = "https://www.googleapis.com/books/v1/volumes?"
    private const val QUERY_PARAMETER_KEY = "q"
    private const val KEY = "key"
    private const val API_KEY = "AIzaSyCrJwf_YfwjQvWOqH4Tqh3rwNezFf3bFC8"
    private const val TITLE = "intitle"
    private const val AUTHOR = "inauthor"
    private const val PUBLISHER = "inpublisher"
    private const val ISBN = "isbn"

    fun buildUrl(title: String): URL? {

        var url: URL? = null

        val uri = Uri.parse(BASE_API_URL).buildUpon()
            .appendQueryParameter(KEY, API_KEY)
            .appendQueryParameter(QUERY_PARAMETER_KEY, title).build()
        try {
            url = URL(uri.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return url
    }

    fun buildUrl(title:String,author:String,publisher : String,isbn:String): URL? {
        var url : URL? = null
        val sb = StringBuilder()
        if(title.isNotEmpty()){
            sb.append(TITLE + title + "+")
        }
        if(author.isNotEmpty()){
            sb.append(AUTHOR + author + "+")
        }
        if(publisher.isNotEmpty()){
            sb.append(PUBLISHER + publisher + "+")
        }
        if(isbn.isNotEmpty()){
            sb.append(ISBN + isbn + "+")
        }
        sb.setLength(sb.length - 1)

        val query = sb.toString()
        val uri = Uri.parse(query).buildUpon().appendQueryParameter(QUERY_PARAMETER_KEY,query).appendQueryParameter(
            KEY, API_KEY).build()

        try {
            url = URL(uri.toString())
        }catch (e:Exception){
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

    fun getBooksFromJson(json: String): ArrayList<Book>? {
        val id: String = "id"
        val title: String = "title"
        val subTitle: String = "subtitle"
        val authors: String = "authors"
        val publisher: String = "publisher"
        val publishedDate: String = "publishedDate"
        val items: String = "items"
        val volumeInfo: String = "volumeInfo"
        val description: String = "description"
        val thumbNail: String = "thumbnail"

        val books: ArrayList<Book> = ArrayList()
        try {
            val jsonObject = JSONObject(json)
            val arrayBooks = jsonObject.getJSONArray(items)
            val numberOfBooks = arrayBooks.length()
            for (x in 0 until numberOfBooks) {
                val bookJson = arrayBooks.getJSONObject(x)
                val volumeInfoJson = bookJson.getJSONObject(volumeInfo)
                val imageLinks = volumeInfoJson.getJSONObject("imageLinks")
                val authorsNumbers = volumeInfoJson.getJSONArray(authors).length()
                val authorsArray: ArrayList<String> = ArrayList()
                for (y in 0 until authorsNumbers) {
                    authorsArray.add(volumeInfoJson.getJSONArray(authors).get(y).toString())
                }
                val book = Book(
                    bookJson.getString(id),
                    volumeInfoJson.getString(title),
                    volumeInfoJson.getString(title),
                    authorsArray,
                    volumeInfoJson.getString(publisher),
                    volumeInfoJson.getString(publishedDate),
                    volumeInfoJson.getString(description),
                    imageLinks.getString(thumbNail)
                )

                books.add(book)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return books
    }
}