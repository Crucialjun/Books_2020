package com.globomed.books2020

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_search.setOnClickListener{
            val title = etTitle.text.trim() as String
            val author = etAuthor.text.trim() as String
            val publisher = etPublisher.text.trim() as String
            val isbn = etIsbn.text.trim() as String

            if(title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
                Toast.makeText(context, "Please Insert Valid Search Terms", Toast.LENGTH_LONG).show()
            }else{
                val  url = ApiUtil.buildUrl(title,author,publisher,isbn)
                val action = SearchFragmentDirections.actionSearchFragmentToBlookListFragment(url.toString())
                findNavController().navigate(action)
            }
        }
    }
}