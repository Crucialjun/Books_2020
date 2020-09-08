package com.globomed.books2020

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_blook_list.*
import java.lang.Exception


class BlookListFragment : Fragment() {


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
            val jsonResult = ApiUtil.getJson(bookUrl!!)
            tvResponse.text = jsonResult
        } catch (e:Exception){
            Log.d("ERROR", "onViewCreated:${e.message} ")
        }


    }
}