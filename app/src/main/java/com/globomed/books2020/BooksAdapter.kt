package com.globomed.books2020

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter(private val books: ArrayList<Book>
                   ,private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {


    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvAuthors: TextView = itemView.findViewById(R.id.tvAuthors)
        private val tvPublisher: TextView = itemView.findViewById(R.id.tvPublisher)
        private val tvPublishedDate: TextView = itemView.findViewById(R.id.tvPublishedDate)

        init {
            itemView.setOnClickListener(this)


        }


        fun bind(book : Book){
            tvTitle.text= book.title
            tvAuthors.text= book.authors
            tvPublisher.text = book.publisher
            tvPublishedDate.text= book.publishedDate


        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_items,parent,false)

        return  BookViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)

    }

    override fun getItemCount(): Int {
        return books.size
    }


    interface OnItemClickListener{
        fun onItemClicked(position: Int)
    }
    

}