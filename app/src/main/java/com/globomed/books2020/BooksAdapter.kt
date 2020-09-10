package com.globomed.books2020

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter(private val books: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvAuthors: TextView = itemView.findViewById<TextView>(R.id.tvAuthors)
        val tvPublisher = itemView.findViewById<TextView>(R.id.tvPublisher)
        val tvPublishedDate = itemView.findViewById<TextView>(R.id.tvPublishedDate)

        fun bind(book : Book){
            tvTitle.text= book.title
            var authorsString = ""
            var index = 0
            for(author in book.authors){
                authorsString += author
                index++
                if(index < book.authors.size){
                    authorsString += ", "
                }
            }
            tvAuthors.text= authorsString
            tvPublisher.text = book.publisher
            tvPublishedDate.text= book.publishedDate
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_items,parent,false)

        return  BookViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books.get(position)
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }


}