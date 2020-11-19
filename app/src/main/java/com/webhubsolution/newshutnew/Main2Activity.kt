package com.webhubsolution.newshutnew

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.webhubsolution.newshutnew.dataclass.Category
import com.webhubsolution.newshutnew.dataclass.callbacks.CallbackCategories
import com.webhubsolution.newshutnew.fragment.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener{//, DashboardFragment.OnFragmentInteractionListener, CouponDetailsFragment.OnFragmentInteractionListener, CouponRedeemFragment.OnFragmentInteractionListener, PersonalDetailsFragment.OnFragmentInteractionListener, BankDetailsFragment.OnFragmentInteractionListener {

    private val mFragmentManager = supportFragmentManager
    lateinit var toolbar: Toolbar
    lateinit var viewPager : ViewPager
    lateinit var tabLayout : TabLayout
    //lateinit var call1 : Call<CallbackCategories>
    lateinit var navHeaderTitle : TextView
    lateinit var navView: NavigationView
    lateinit var categoryListGlobal : List<Category>
    private var doubleBackToExitPressedOnce = false

    //lateinit var actionbar : ActionBar
    //var toolbarTitle = "Dashboard"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        toolbar = findViewById(R.id.toolbar)
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        tabLayout =  findViewById<TabLayout>(R.id.tabs)
        //actionbar = supportActionBar!!
        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(resources.getColor(R.color.white))

        val actionBarText = "<font color=#0c7a7b>NEWS</font><font color=#e4b244>HUT</font>"

        //supportActionBar!!.setTitle(Html.fromHtml(actionBarText))
        supportActionBar!!.setLogo(resources.getDrawable(R.drawable.text_logo_new))

        window.decorView.setBackgroundColor(resources.getColor(android.R.color.white))



        /*FirebaseMessaging.getInstance().subscribeToTopic("news").addOnSuccessListener(object: OnSuccessListener<Void> {
        override fun onSuccess(aVoid : Void) {
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
        }
    })*/


        //mFragmentManager.beginTransaction().add(R.id.mainActivity2_FrameLayout, DashboardFragment()).commit()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_home_white_24dp))

        fab.setOnClickListener{
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)


        val headerView  = navView.getHeaderView(0)
        navHeaderTitle = headerView.findViewById(R.id.nav_header_title)

        val navHeadText = "<font color=#0c7a7b>NEWS</font><font color=#e4b244>HUT</font>"

        navHeaderTitle.text = Html.fromHtml(navHeadText)

        /*val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()*/

        navView.setNavigationItemSelectedListener(this)
        viewPager.offscreenPageLimit = 2

        setupViewPager(viewPager)

        tabLayout.setupWithViewPager(viewPager)


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageSelected(position: Int) {
                if(position==0) {

                }
                else{

                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "হোম")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList.get(position)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main2, menu)
        menuInflater.inflate(R.menu.main2activitytoolbarmenu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_share -> {

                /*val shareBody = ("Download Newshut Live News & Breaking News in Bangali through this link http://rebrand.ly/newshutapp .")
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Newshut")
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(shareIntent)*/

                return true


            }

            R.id.action_search -> {

                //Toast.makeText(this@Main2Activity, "Search clicked", Toast.LENGTH_LONG).show()

                //val intent = Intent(this@Main2Activity,SearchActivity::class.java)
                //startActivity(intent)

                return true


            }

            else ->
                super.onOptionsItemSelected(item)
        }

        return false

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.


        if(item.title.equals("হোম"))
        {
            viewPager.currentItem = 0
        }

        else if(item.title.equals("Settings")){

            //Log.e("Settings item id",item.itemId.toString())

                //val intent = Intent(this@Main2Activity, SettingsActivity::class.java)
                //startActivity(intent)
        }

        else if(item.title.equals("Rate us!")){

            val uri = Uri.parse("http://rebrand.ly/newshutapp") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        else if(item.title.equals("Log out")){

            Log.e("Log out item id",item.itemId.toString())

            val sharedPreference = getSharedPreferences("settingsPref", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putBoolean("loginStatus",false)
            editor.putString("name","anonymous")
            editor.putString("email", "Not available")
            editor.putString("profilePic", "")
            editor.putString("phone", "Not available")
            editor.apply()

            finish()

            //val intent = Intent(this@Main2Activity, LoginActivity::class.java)
            //startActivity(intent)
        }


        else if(item.title.equals("Log in")){

            Log.e("Log in item id",item.itemId.toString())
            finish()

            //val intent = Intent(this@Main2Activity, LoginActivity::class.java)
            //startActivity(intent)
        }

        else {
            for (i in 0..((categoryListGlobal.size) - 1)) {
                if (item.title.equals(categoryListGlobal[i].title)) {
                    viewPager.currentItem = (i + 1)
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        val menu = navView.menu

        //menu.removeItem()


    }

    override fun onPause() {
        super.onPause()
        //call1.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        //call1.cancel()
    }

    override fun onBackPressed() {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        else {

            //{
            // super.onBackPressed()
            //}

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented")
    }

}
