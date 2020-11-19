package com.webhubsolution.newshutnew.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
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
import java.text.SimpleDateFormat


class PostListRecyclerAdapter(val dataa: ArrayList<Post>, val context: Context?) : androidx.recyclerview.widget.RecyclerView.Adapter<PostListRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {

        return PostListRecyclerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.fm_home_rv2_layout, p0, false))
    }

    override fun getItemCount() = dataa.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.thumbnail.setImageResource(dataa[position].image)
        //Picasso.get().load(dataa[position].attachments[0].url).into(holder.thumbnail)
        Tools.displayImageThumbnail(context!!, dataa[position], holder.thumbnail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.title.text=  Html.fromHtml(dataa[position].title, Html.FROM_HTML_MODE_LEGACY)
        } else {
            holder.title.text= Html.fromHtml(dataa[position].title)
        }

        //holder.title.text = dataa[position].title
        holder.author.text = ("By "+dataa[position].author.name +"\n"+Main3Activity().dateFormat(dataa[position].date))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Main3Activity::class.java)
            intent.putExtra("id",dataa[position].id)
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val thumbnail = view.findViewById<ImageView>(R.id.iv1)
        val title = view.findViewById<TextView>(R.id.tv3)
        val author  = view.findViewById<TextView>(R.id.tv2)

    }


    /*fun dateFormat(date : String) : String
    {
        //"2019-09-22 20:01:11" given
        //22nd September, 2019 required
        var newFormat : String = ""

        //val datee = "2019-09-22 20:01:11"
        var spf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("dd MMMM, yyyy")


        newFormat = spf.format(newDate!!)

        when (date.substring(9,10).toInt()) {
            1 -> newFormat = (newFormat.substring(0,2)+"st"+newFormat.substring(2,newFormat.length))

            2 -> newFormat = (newFormat.substring(0,2)+"nd"+newFormat.substring(2,newFormat.length))

            3 -> newFormat = (newFormat.substring(0,2)+"rd"+newFormat.substring(2,newFormat.length))

            else -> newFormat = (newFormat.substring(0,2)+"th"+newFormat.substring(2,newFormat.length))
        }



        return newFormat
    }*/

}