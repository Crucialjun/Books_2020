package com.globomed.books2020

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_blook_list.*
import java.io.IOException
import java.lang.Exception
import java.net.URL


class BookListFragment : Fragment(), SearchView.OnQueryTextListener,
    BooksAdapter.OnItemClickListener {

    var books = ArrayList<Book>()
    val booksAdapter = BooksAdapter(books, this)
    private val args : BookListFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
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
        val booksLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvResponse.layoutManager = booksLayoutManager
        val url = args.searchUrl!!
        val bookUrl: URL?
        try {
            if(url.isEmpty()){
                bookUrl = ApiUtil.buildUrl("cooking")
            }else{
                bookUrl = URL(url)
            }
            BooksQueryTask(this.activity).execute(bookUrl)

        } catch (e: Exception) {
            Log.d("ERROR", "onViewCreated:${e.message} ")
        }

        progressBarLoading


    }

    inner class BooksQueryTask(private val bookListFragment: FragmentActivity?) :
        AsyncTask<URL, Void, String>() {
        private val fragment = bookListFragment
        override fun doInBackground(vararg p0: URL?): String? {
            val searchUrl = p0[0]
            var result: String? = null
            try {
                result = ApiUtil.getJson(searchUrl!!)
            } catch (e: IOException) {
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
            val tvError = fragment?.findViewById<TextView>(R.id.textViewError)
            val rvResponse = fragment?.findViewById<RecyclerView>(R.id.rvResponse)

            if (result == null) {
                rvResponse?.visibility = View.INVISIBLE
                tvError?.visibility = View.VISIBLE
            } else {
                tvError?.visibility = View.INVISIBLE
                rvResponse?.visibility = View.VISIBLE
            }

            books.addAll(ApiUtil.getBooksFromJson(result!!)!!)


            booksAdapter.notifyDataSetChanged()
            rvResponse?.adapter = booksAdapter


        }


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.book_list_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_advanced_search) {
            findNavController().navigate(R.id.action_blookListFragment_to_searchFragment)
            return true
        } else
            return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(p0: String?): Boolean {
        try {
            val bookUrl = ApiUtil.buildUrl(p0!!)
            BooksQueryTask(this.activity).execute(bookUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onItemClicked(position: Int) {


        Toast.makeText(context, "You clicked Position $position", Toast.LENGTH_SHORT).show()
        val action =
            BookListFragmentDirections.actionBlookListFragmentToBookDetailFragment(books[position])
        findNavController().navigate(action)
    }
}