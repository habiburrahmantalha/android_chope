package com.tree.chope


import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {


    var imageView: ImageView  = this

}

@BindingAdapter("timeText")
fun TextView.setTimeText(text: String?){
    val tv: TextView = this
    tv.text = text?.toDate()?.formatTo()
}

@BindingAdapter("visibility")
fun TextView.setVisibility(text: String?) {
    val tv: TextView = this
    tv.visibility = if (text == null || text.isEmpty()) View.GONE else View.VISIBLE
}

fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date? {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String = "hh:mm a dd MMM", timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}