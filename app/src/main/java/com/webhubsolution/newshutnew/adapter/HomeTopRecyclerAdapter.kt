package com.webhubsolution.newshutnew.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.webhubsolution.newshutnew.utils.Tools
import com.squareup.picasso.Picasso
import com.webhubsolution.newshutnew.Main3Activity
import com.webhubsolution.newshutnew.dataclass.Post
import com.webhubsolution.newshutnew.R


class HomeTopRecyclerAdapter(val dataa: ArrayList<Post>, val context: Context?) : androidx.recyclerview.widget.RecyclerView.Adapter<HomeTopRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {

        return HomeTopRecyclerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.fm_home_rv1_layout, p0, false))
    }

    override fun getItemCount() = dataa.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.thumbnail.setImageResource(dataa[position].image)
        //Picasso.get().load(dataa[position].attachments[0].url).into(holder.thumbnail)
        Tools.displayImageThumbnail(context!!, dataa[position], holder.thumbnail)
        holder.category.text = dataa[position].categories[0].title
        holder.author.text = (dataa[position].author.name +" - "+ Main3Activity().dateFormat(dataa[position].date))
        holder.itemView.setOnClickListener {
            val intent = Intent(context, Main3Activity::class.java)
            intent.putExtra("id",dataa[position].id)
            context.startActivity(intent)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val thumbnail = view.findViewById<ImageView>(R.id.iv1)
        val category = view.findViewById<TextView>(R.id.tv1)
        val author  = view.findViewById<TextView>(R.id.tv2)

    }


}