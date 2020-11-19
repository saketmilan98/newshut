package com.webhubsolution.newshutnew.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.media.MediaCodec.MetricsConstants.MODE
import android.media.MediaPlayer
import android.media.session.MediaController
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.webhubsolution.newshutnew.Main2Activity
import com.webhubsolution.newshutnew.R
import java.nio.file.Files.size
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentManager
import com.example.kloadingspin.KLoadingSpin
import com.squareup.picasso.Picasso
import com.webhubsolution.newshutnew.Main3Activity
import com.webhubsolution.newshutnew.adapter.HomeTopRecyclerAdapter
import com.webhubsolution.newshutnew.adapter.PostListRecyclerAdapter
import com.webhubsolution.newshutnew.apiKotlin
import com.webhubsolution.newshutnew.dataclass.Post
import com.webhubsolution.newshutnew.dataclass.callbacks.CallbackDetailsPost
import com.webhubsolution.newshutnew.dataclass.callbacks.CallbackListPost
import com.webhubsolution.newshutnew.utils.Tools
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment: androidx.fragment.app.Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val handler = Handler()
    private var count : Int = 0
    lateinit var layoutManager : LinearLayoutManager
    lateinit var layoutManager2 : LinearLayoutManager
    lateinit var layoutManager3 : LinearLayoutManager
    lateinit var layoutManager4 : LinearLayoutManager
    lateinit var layoutManager5 : LinearLayoutManager
    lateinit var layoutManager6 : LinearLayoutManager
    private var loggedIn = false
    //lateinit var toolbar: Toolbar
    lateinit var viewPager : ViewPager
    lateinit var tabLayout : TabLayout

    lateinit var mediaController : android.widget.MediaController

    lateinit var call1: Call<CallbackListPost>

    lateinit var rv1 : RecyclerView
    lateinit var rv2 : RecyclerView
    lateinit var topIv : ImageView
    lateinit var topCategoryTv : TextView
    lateinit var topAuthorTv : TextView
    lateinit var customProgressBar : KLoadingSpin
    lateinit var retryBtn : CardView
    private var currentPage : Int = 1
    lateinit var rvProgress : ConstraintLayout

    lateinit var mAdapter2 : PostListRecyclerAdapter

    lateinit var rvProgressBar : ProgressBar
    lateinit var retryBtnRv : CardView
    lateinit var endOfGroupTv : CardView
    private var totalPageCount : Int = 1
    private var isLoading = false

    lateinit var mAdView : View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        rv1 = rootView.findViewById(R.id.fm_home_rv1)
        rv2 = rootView.findViewById(R.id.fm_home_rv2)
        topIv = rootView.findViewById(R.id.fm_home_iv1)
        topCategoryTv = rootView.findViewById(R.id.fm_home_tv1)
        topAuthorTv = rootView.findViewById(R.id.fm_home_tv2)
        customProgressBar = rootView.findViewById(R.id.KLoadingSpin)
        retryBtn = rootView.findViewById(R.id.retry_btn)
        rvProgress = rootView.findViewById(R.id.rv_progress1)
        rvProgressBar = rootView.findViewById(R.id.rvprogressbar)
        retryBtnRv = rootView.findViewById(R.id.retry_btn_rv)
        endOfGroupTv = rootView.findViewById(R.id.tv_endoffeed)
        mAdView = rootView.findViewById(R.id.adView)


        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customProgressBar.startAnimation()
        customProgressBar.setIsVisible(true)
        //Picasso.get().load("https://graph.facebook.com/100003358807063/picture?type=large").into(topIv)

        postsByPageApiCall(1)
        currentPage = 1

        retryBtn.setOnClickListener {
            postsByPageApiCall(1)
            retryBtn.visibility = View.GONE
            customProgressBar.startAnimation()
            customProgressBar.setIsVisible(true)
        }

        retryBtnRv.setOnClickListener {
            postsByPageApiCall(currentPage)
            retryBtnRv.visibility = View.GONE
            rvProgress.visibility = View.VISIBLE
            rvProgressBar.visibility = View.VISIBLE

        }

        //val sharedPreferences = context!!.getSharedPreferences("LoginCheck", MODE_PRIVATE)
        //loggedIn = sharedPreferences.getBoolean("loggedIn",false)
        //activity!!.setActionBar(toolbar)






    }

    /*fun setRecyclerView()
    {

        val topRecyclerData = ArrayList<Post>()

        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler2_5, "Andhadhun"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler6_2, "Mission Mangal"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler2_5, "Andhadhun"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler6_2, "Mission Mangal"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler2_5, "Andhadhun"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler6_2, "Mission Mangal"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler2_5, "Andhadhun"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler6_2, "Mission Mangal"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler2_5, "Andhadhun"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler6_2, "Mission Mangal"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler2_5, "Andhadhun"))
        watchListData.add(WatchListDataClass(R.drawable.hometoprecycler6_2, "Mission Mangal"))

        /*videoEarnData.add(Fm1Rv3DataClass("2019-07-24 to 2019-07-24", "1", "5", "List"))
        videoEarnData.add(Fm1Rv3DataClass("2019-07-16 to 2019-07-16", "2", "5", "List"))
        videoEarnData.add(Fm1Rv3DataClass("2019-07-08 to 2019-07-08", "3", "15", "List"))
        videoEarnData.add(Fm1Rv3DataClass("2019-07-03 to 2019-07-06", "8", "30", "List"))*/



        val mAdapter3 = WatchListRecyclerAdapter(watchListData,context)
        fm6_rv1.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context,2)
        //fm1_rv3.isNestedScrollingEnabled = false
        fm6_rv1.adapter = mAdapter3


    }*/


    fun postsByPageApiCall(page : Int)
    {
        var postData = ArrayList<Post>()
        val retrofit2 = Retrofit.Builder()
            .baseUrl(apiKotlin.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit2.create(apiKotlin::class.java)

        call1 = api.getPostByPage(page,10)

        call1.enqueue(object : Callback<CallbackListPost> {
            override fun onResponse(
                call: Call<CallbackListPost>,
                response: Response<CallbackListPost>
            ) {

                val jsondata = response.body()
                Log.i("jsonData=", jsondata.toString())
                postData = jsondata!!.posts as ArrayList<Post>









                if(page==1) {
                    totalPageCount = jsondata.pages

                    val random = Random()
                    val randomInt = random.nextInt((9 - 0) + 1) + 0
                    Tools.displayImageThumbnailFull(context!!,postData[randomInt],topIv)
                    topCategoryTv.text = postData[randomInt].categories[0].title
                    topAuthorTv.text = ("By "+postData[randomInt].author.name +" - "+ Main3Activity().dateFormat(postData[randomInt].date))
                    topAuthorTv.visibility = View.VISIBLE

                    topIv.setOnClickListener {
                        val intent = Intent(context, Main3Activity::class.java)
                        intent.putExtra("id",postData[randomInt].id)
                        context!!.startActivity(intent)
                    }

                    val mAdapter = HomeTopRecyclerAdapter(postData, activity)
                    rv1.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
                    rv1.adapter = mAdapter

                    mAdapter2 = PostListRecyclerAdapter(postData, activity)
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                        activity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    rv2.layoutManager = layoutManager
                    rv2.adapter = mAdapter2
                }
                else{
                    mAdapter2.dataa.addAll(postData)
                    mAdapter2.notifyDataSetChanged()
                    rvProgress.visibility = View.GONE
                    rvProgressBar.visibility = View.GONE
                    retryBtnRv.visibility = View.GONE
                }


                rv2.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if(currentPage >totalPageCount){
                            rvProgress.visibility = View.VISIBLE
                            rvProgressBar.visibility = View.GONE
                            retryBtnRv.visibility = View.GONE
                            endOfGroupTv.visibility = View.VISIBLE
                        }
                        else if(layoutManager.findLastVisibleItemPosition() == (mAdapter2.itemCount-1)){
                            call1.cancel()
                            postsByPageApiCall(currentPage)
                            rvProgress.visibility = View.VISIBLE
                            rvProgressBar.visibility = View.VISIBLE
                            retryBtnRv.visibility = View.GONE
                            isLoading = true
                        }
                    }
                })



                //mAdapter2 = PostListRecyclerAdapter(postData, activity)
                //rv2.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
                //rv2.adapter = mAdapter2
                currentPage++
                customProgressBar.stopAnimation()

                //val img = ImageView(context)
                //Picasso.get().load(jsondata!!.response.images).into(img)
                //topConstraint.setBackgroundDrawable(img.drawable)
            }

            override fun onFailure(call: Call<CallbackListPost>, t: Throwable) {
                //Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                if(page==1) {
                    customProgressBar.stopAnimation()
                    retryBtn.visibility = View.VISIBLE
                }//call1.cancel()
                //postsByPageApiCall()
                else if(isLoading) {
                    rvProgress.visibility = View.VISIBLE
                    rvProgressBar.visibility = View.VISIBLE
                    retryBtnRv.visibility = View.GONE
                    isLoading = false
                }
                else{
                    rvProgress.visibility = View.VISIBLE
                    retryBtnRv.visibility = View.VISIBLE
                    rvProgressBar.visibility = View.GONE
                }
                isLoading = true
            }
        })
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onResume() {
        super.onResume()

        //setRecyclerView()


    }





    override fun onPause() {
        super.onPause()
        rvProgress.visibility = View.GONE
        isLoading = false
        call1.cancel()

        //videoView_activity4.stopPlayback()
    }


    override fun onDetach() {
        super.onDetach()
        rvProgress.visibility = View.GONE
        isLoading = false
        call1.cancel()
        rvProgress.visibility = View.GONE
        //videoView_activity4.stopPlayback()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
