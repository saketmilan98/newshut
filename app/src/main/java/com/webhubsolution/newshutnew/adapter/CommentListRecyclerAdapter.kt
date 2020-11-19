package com.webhubsolution.newshutnew.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.webhubsolution.newshutnew.utils.Tools
import com.squareup.picasso.Picasso
import com.webhubsolution.newshutnew.Main2Activity
import com.webhubsolution.newshutnew.Main3Activity
import com.webhubsolution.newshutnew.dataclass.Post
import com.webhubsolution.newshutnew.R
import com.webhubsolution.newshutnew.dataclass.Comment
import java.text.SimpleDateFormat


class CommentListRecyclerAdapter(val dataa: ArrayList<Comment>, val context: Context?) : androidx.recyclerview.widget.RecyclerView.Adapter<CommentListRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {

        return CommentListRecyclerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.commentadapter_rv_layout, p0, false))
    }

    override fun getItemCount() = dataa.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = Html.fromHtml(dataa[position].content)
        holder.author.text = dataa[position].name
        holder.date.text = (Main3Activity().dateFormat(dataa[position].date))

        /*holder.itemView.setOnClickListener {
            val intent = Intent(context, Main3Activity::class.java)
            intent.putExtra("id",dataa[position].id)
            context!!.startActivity(intent)
        }*/
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        //val thumbnail = view.findViewById<ImageView>(R.id.iv1)
        val title = view.findViewById<TextView>(R.id.commentContent)
        val author  = view.findViewById<TextView>(R.id.commentName)
        val date = view.findViewById<TextView>(R.id.commentDate)

    }


}