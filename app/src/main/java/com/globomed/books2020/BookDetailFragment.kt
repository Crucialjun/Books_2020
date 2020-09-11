package com.globomed.books2020

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.globomed.books2020.databinding.FragmentBookDetailBinding
import kotlinx.android.synthetic.main.fragment_book_detail.*


class BookDetailFragment : Fragment() {

    private val args : BookDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_book_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val book = args.book
        val binding : FragmentBookDetailBinding = DataBindingUtil.setContentView(requireActivity(),R.layout.fragment_book_detail)
        binding.book = book



    }


}