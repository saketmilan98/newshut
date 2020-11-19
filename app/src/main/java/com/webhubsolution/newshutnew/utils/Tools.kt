package com.webhubsolution.newshutnew.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.webhubsolution.newshutnew.dataclass.Post

class Tools {

    companion object {
        fun displayImageThumbnail(ctx: Context, p: Post, imageView: ImageView) {
            try {
                var url = ""
                url = p.thumbnail_images.medium.url

                //if(p.getThumbnail() !=null && !p.getThumbnail().equals("")) {
                //url = p.getThumbnail();
                //}else
                /*if (p.attachments.size > 0) {
                    for (a in p.attachments) {
                        if (a.mime_type.equals("image/jpeg") || a.mime_type.equals("image/png")|| a.mime_type.equals("image/jpg")) {
                            url = a.url
                            break
                        }
                    }
                }*/
                Log.e("thumbnailurl", url)
                if (!TextUtils.isEmpty(url)) {
                    Picasso.get().load(url).into(imageView)
                }
            } catch (e: Exception) {
                Log.e("WORDPRESS", "Failed when display image - " + e.message)
            }

        }


        fun displayImageThumbnailFull(ctx: Context, p: Post, imageView: ImageView) {
            try {
                var url = ""
                url = p.thumbnail_images.full.url

                //if(p.getThumbnail() !=null && !p.getThumbnail().equals("")) {
                //url = p.getThumbnail();
                //}else
                /*if (p.attachments.size > 0) {
                    for (a in p.attachments) {
                        if (a.mime_type.equals("image/jpeg") || a.mime_type.equals("image/png")|| a.mime_type.equals("image/jpg")) {
                            url = a.url
                            break
                        }
                    }
                }*/
                Log.e("thumbnailurl", url)
                if (!TextUtils.isEmpty(url)) {
                    Picasso.get().load(url).into(imageView)
                }
            } catch (e: Exception) {
                Log.e("WORDPRESS", "Failed when display image - " + e.message)
            }

        }



    }

}