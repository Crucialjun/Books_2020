package com.globomed.books2020

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Book() : Parcelable {
    var id: String? = null
    var title: String? = null
    var subTittle: String? = null
    var authors: String? = null
    var publisher: String? = null
    var publishedDate: String? = null
    var description: String? = null
    var thumbnail: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        title = parcel.readString()
        subTittle = parcel.readString()
        authors = parcel.readString()
        publisher = parcel.readString()
        publishedDate = parcel.readString()
        description = parcel.readString()
        thumbnail = parcel.readString()
    }


    constructor(
        id: String?,
        title: String?,
        subTittle: String?,
        authors: ArrayList<String>,
        publisher: String?,
        publishedDate: String?,
        description: String?,
        thumbnail: String?
    ) : this() {
        this.id = id
        this.title = title
        this.subTittle = subTittle
        this.authors = TextUtils.join(", ", authors)
        this.publisher = publisher
        this.publishedDate = publishedDate
        this.description = description
        this.thumbnail = thumbnail
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(subTittle)
        parcel.writeString(authors)
        parcel.writeString(publisher)
        parcel.writeString(publishedDate)
        parcel.writeString(description)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }


}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).placeholder(R.drawable.ic_baseline_menu_book_24).into(view)
}