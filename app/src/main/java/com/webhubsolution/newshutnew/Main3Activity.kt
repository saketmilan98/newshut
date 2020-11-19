package com.webhubsolution.newshutnew

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kloadingspin.KLoadingSpin
import com.squareup.picasso.Picasso
import com.webhubsolution.newshutnew.adapter.CommentListRecyclerAdapter
import com.webhubsolution.newshutnew.adapter.PostListRecyclerAdapter
import com.webhubsolution.newshutnew.dataclass.Comment
import com.webhubsolution.newshutnew.dataclass.Post
import com.webhubsolution.newshutnew.dataclass.callbacks.CallbackCategoryDetails
import com.webhubsolution.newshutnew.dataclass.callbacks.CallbackComment
import com.webhubsolution.newshutnew.dataclass.callbacks.CallbackDetailsPost
import com.webhubsolution.newshutnew.utils.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class Main3Activity : AppCompatActivity() {

    lateinit var webView1 : WebView
    lateinit var call1 : Call<CallbackDetailsPost>
    private var postId : Int = 1
    lateinit var categoryName : TextView
    lateinit var postTitle : TextView
    lateinit var copyBy : TextView
    lateinit var authorAndDate : TextView
    lateinit var thumbnail : ImageView
    lateinit var toolbarr : Toolbar

    lateinit var customProgressBar : KLoadingSpin
    lateinit var retryBtn : CardView

    lateinit var commentsNumber : TextView

    lateinit var view1 : View

    lateinit var shareBtn : ImageView
    lateinit var socialShareBtn : ImageView

    lateinit var sendCommentConstraint : ConstraintLayout
    lateinit var sendCommentProPic : ImageView
    lateinit var sendCommentEditText : EditText
    lateinit var sendCommentBtn : ImageView

    lateinit var layoutManager : LinearLayoutManager
    lateinit var rv2 : RecyclerView
    lateinit var mAdapter2 : CommentListRecyclerAdapter

    private var totalCommentCount = 0

    lateinit var progressBarSendComment : ProgressBar

    lateinit var mAdView : View
    lateinit var mAdView1 : View

    private var fromNoti : Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        webView1 = findViewById(R.id.webView1)
        categoryName = findViewById(R.id.categoryName)
        postTitle = findViewById(R.id.title)
        copyBy = findViewById(R.id.copyBy)
        authorAndDate = findViewById(R.id.authorAndDate)
        thumbnail = findViewById(R.id.thumbnail)
        toolbarr = findViewById(R.id.toolbar)
        customProgressBar = findViewById(R.id.KLoadingSpin)
        retryBtn = findViewById(R.id.retry_btn)
        commentsNumber = findViewById(R.id.commentNumber)
        shareBtn = findViewById(R.id.shareBtn)
        socialShareBtn = findViewById(R.id.socialShareBtn)
        view1 = findViewById(R.id.view1)
        sendCommentConstraint = findViewById(R.id.constraintSendComment)
        sendCommentProPic = findViewById(R.id.sendCommentProPic)
        sendCommentEditText = findViewById(R.id.sendCommentEditText)
        sendCommentBtn = findViewById(R.id.sendCommentBtn)
        progressBarSendComment = findViewById(R.id.sendCommentProgressBar)
        mAdView = findViewById(R.id.adView)
        mAdView1 = findViewById(R.id.adView1)

        rv2 = findViewById(R.id.commentRecyclerView)

        setSupportActionBar(toolbarr)

        toolbarr.setBackgroundColor(resources.getColor(R.color.white))

        customProgressBar.startAnimation()
        customProgressBar.setIsVisible(true)

        val actionBarText = "<font color=#0c7a7b>NEWS</font><font color=#e4b244>HUT</font>"

        supportActionBar!!.setLogo(resources.getDrawable(R.drawable.text_logo_new))

        //supportActionBar!!.setTitle(Html.fromHtml(actionBarText))


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fromNoti = intent.getBooleanExtra("fromNoti",false)
        postId = intent.getIntExtra("id",1)


        //val sharedPreferences = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)
        //val loggedIn = sharedPreferences.getBoolean("loginStatus", false)
        //val skippedSignIn = sharedPreferences.getBoolean("skippedSignIn",false)


        //if(loggedIn&&skippedSignIn){
         //   val intent = Intent(this@Main3Activity, LoginActivity::class.java)
       //     startActivity(intent)
       // }
        //else{
            postDetailByIdApiCall(postId)
       // }

        /*if(postId==1) {
            val bundle = intent.extras
            if (bundle != null) {
                postId = bundle.getInt("id")
                //bundle must contain all info sent in "data" field of the notification
            }
        }*/







        retryBtn.setOnClickListener {
            postDetailByIdApiCall(postId)
            retryBtn.visibility = View.GONE
            customProgressBar.startAnimation()
            customProgressBar.setIsVisible(true)
        }

    }


    fun postDetailByIdApiCall(id :Int)
    {

        val retrofit2 = Retrofit.Builder()
            .baseUrl(apiKotlin.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit2.create(apiKotlin::class.java)

        call1 = api.getPostDetailsById(id)

        call1.enqueue(object : Callback<CallbackDetailsPost> {
            override fun onResponse(
                call: Call<CallbackDetailsPost>,
                response: Response<CallbackDetailsPost>
            ) {

                val jsondata = response.body()
                Log.i("jsonData=", jsondata.toString())
                if(jsondata!=null)
                {
                    val catNum = jsondata.post!!.categories.size
                    var catText = ""
                    for(i in 0..(catNum-1)){
                        catText+=jsondata.post!!.categories[i].title
                        if(i!=(catNum-1))
                            catText+=",\n"
                    }

                    categoryName.text = catText


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        postTitle.text=  Html.fromHtml(jsondata.post!!.title, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        postTitle.text= Html.fromHtml(jsondata.post!!.title)
                    }

                    //postTitle.text = Html.fromHtml(jsondata.post!!.title)
                    copyBy.text = ("Copy By "+jsondata.post!!.custom_fields.copy_by[0])
                    authorAndDate.text = ("By "+jsondata.post!!.author.name +" - "+ dateFormat(jsondata.post!!.date))
                    authorAndDate.visibility = View.VISIBLE
                    view1.visibility = View.VISIBLE
                    sendCommentConstraint.visibility = View.VISIBLE
                    Tools.displayImageThumbnailFull(this@Main3Activity,jsondata.post!!,thumbnail)

                    totalCommentCount = jsondata.post!!.comment_count

                    if(jsondata.post!!.comment_count==0){
                        commentsNumber.text = "This post has no comments."
                    }
                    else if(jsondata.post!!.comment_count==1){
                        commentsNumber.text = ("1 comment")
                    }
                    else{
                        commentsNumber.text = (""+(jsondata.post!!.comment_count) + " comments")
                    }

                    val postData = jsondata.post!!.comments as ArrayList

                    mAdapter2 = CommentListRecyclerAdapter(postData, this@Main3Activity)
                    layoutManager = LinearLayoutManager(
                        this@Main3Activity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    rv2.layoutManager = layoutManager
                    rv2.adapter = mAdapter2





                    webView1.settings.javaScriptEnabled = true
                    webView1.webChromeClient = WebChromeClient()

                    //var htmlData = ((jsondata.post!!.content).replace("\"", "&#34;"))
                    //htmlData = (htmlData.replace("\'", "&#39;"))
                    val content1 = jsondata.post!!.content
                    val content2 = content1.substringBeforeLast("</p>")
                    webView1.loadDataWithBaseURL(null,"<style>img{max-width:100%;height:auto;} iframe{width:100%;}</style> $content2","text/html; charset=utf-8","UTF-8",null)



                    socialShareBtn.setOnClickListener {
                        val shareBody = (jsondata.post!!.title +" \n" + jsondata.post!!.url)
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Newshut")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(shareIntent)
                    }

                    shareBtn.setOnClickListener {
                        val shareBody = (jsondata.post!!.title +" \n" + jsondata.post!!.url)
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Newshut")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(shareIntent)
                    }

                    sendCommentBtn.setOnClickListener{
                        progressBarSendComment.visibility=View.VISIBLE

                        val sharedPreference = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)
                        val name = ""+sharedPreference.getString("name","default userapp")
                        val email = ""+sharedPreference.getString("email","defaultuserapp@gmail.com")
                        val flag1 = sharedPreference.getBoolean("skippedSignIn",false)

                        if(flag1){

                            progressBarSendComment.visibility=View.GONE
                            val editor = sharedPreference.edit()
                            editor.putBoolean("fromPostActivity",true)
                            editor.apply()

                            val dialogBuilder = AlertDialog.Builder(this@Main3Activity)

                            // set message of alert dialog
                            dialogBuilder.setMessage("You must signed in to post comment. Proceed to sign in?")
                                // if the dialog is cancelable
                                .setCancelable(false)
                                // positive button text and action
                                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                        dialog, id ->



                                    //val intent = Intent(this@Main3Activity, LoginActivity::class.java)
                                    //startActivity(intent)


                                })
                                // negative button text and action
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                                        dialog, id -> dialog.cancel()
                                })

                            // create dialog box
                            val alert = dialogBuilder.create()
                            // set title for alert dialog box
                            alert.setTitle("Sign in Required")
                            // show alert dialog
                            alert.show()




                        }


                        else {
                        }
                    }


                }

                customProgressBar.stopAnimation()



                //val img = ImageView(context)
                //Picasso.get().load(jsondata!!.response.images).into(img)
                //topConstraint.setBackgroundDrawable(img.drawable)
            }

            override fun onFailure(call: Call<CallbackDetailsPost>, t: Throwable) {
                //Toast.makeText(this@Main3Activity, t.message, Toast.LENGTH_SHORT).show()
                customProgressBar.stopAnimation()
                retryBtn.visibility = View.VISIBLE
                //call1.cancel()
                //postsByPageApiCall()
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)

        if(item.itemId==android.R.id.home) {
            this@Main3Activity.finish()


            /*if(fromNoti){

                val sharedPreferences = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)

                val flag1 = sharedPreferences.getBoolean("loggedInFromPostActivity",false)
                if(flag1){
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("loggedInFromPostActivity",false)
                    editor.apply()
                    val intent = Intent(this@Main3Activity, Main2Activity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@Main3Activity, Main2Activity::class.java)
                    startActivity(intent)
                }
            }

            else{
                val sharedPreferences = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)

                val flag1 = sharedPreferences.getBoolean("loggedInFromPostActivity",false)
                if(flag1){
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("loggedInFromPostActivity",false)
                    editor.apply()
                    val intent = Intent(this@Main3Activity, Main2Activity::class.java)
                    startActivity(intent)
                }
            }*/




        }
        return true
    }

    fun dateFormat(date : String) : String
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

        /*if(fromNoti){

            val sharedPreferences = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)

            val flag1 = sharedPreferences.getBoolean("loggedInFromPostActivity",false)
            if(flag1){
                val editor = sharedPreferences.edit()
                editor.putBoolean("loggedInFromPostActivity",false)
                editor.apply()
                val intent = Intent(this@Main3Activity, Main2Activity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this@Main3Activity, Main2Activity::class.java)
                startActivity(intent)
            }
        }

        else{
            val sharedPreferences = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)

            val flag1 = sharedPreferences.getBoolean("loggedInFromPostActivity",false)
            if(flag1){
                val editor = sharedPreferences.edit()
                editor.putBoolean("loggedInFromPostActivity",false)
                editor.apply()
                val intent = Intent(this@Main3Activity, Main2Activity::class.java)
                startActivity(intent)
            }
        }*/


    }

    override fun onPause() {
        super.onPause()
        call1.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        call1.cancel()
    }

}
