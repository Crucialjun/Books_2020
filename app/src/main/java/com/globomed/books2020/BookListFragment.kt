package com.globomed.books2020

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_blook_list.*
import java.io.IOException
import java.lang.Exception
import java.net.URL


class BookListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blook_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val bookUrl = ApiUtil.buildUrl("cooking")
            BooksQueryTask(this.activity).execute(bookUrl)

        } catch (e:Exception){
            Log.d("ERROR", "onViewCreated:${e.message} ")
        }

        progressBarLoading



    }
    public class BooksQueryTask(bookListFragment: FragmentActivity?) : AsyncTask<URL, Void, String>() {
        private val fragment = bookListFragment
        override fun doInBackground(vararg p0: URL?): String? {
            val searchUrl = p0[0]
            var result : String? = null
            try {
                result = ApiUtil.getJson(searchUrl!!)
            }catch (e : IOException){
                Log.d("Error", "doInBackground: ${e.message}")
            }

            return result
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val pbLoading = fragment?.findViewById<ProgressBar>(R.id.progressBarLoading)
            pbLoading?.visibility = View.VISIBLE
        }

        override fun onPostExecute(result: String?) {
            val pbLoading = fragment?.findViewById<ProgressBar>(R.id.progressBarLoading)
            pbLoading?.visibility = View.INVISIBLE
            val tvResponse = fragment?.findViewById<TextView>(R.id.tvResponse)
            tvResponse?.text = result
        }
    }
}