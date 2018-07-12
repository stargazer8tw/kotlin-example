package com.roma.kotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.fragments.FragmentHome
import com.roma.kotlin.fragments.FragmentItem
import com.roma.kotlin.fragments.FragmentChart
import com.roma.kotlin.fragments.FragmentTool
import com.roma.kotlin.fragments.FragmentCategory
import com.roma.kotlin.fragments.FragmentCloud

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.OnFragmentInteractionListener,
        FragmentItem.OnListFragmentInteractionListener,
        FragmentChart.OnFragmentInteractionListener,
        FragmentTool.OnFragmentInteractionListener,
        FragmentCategory.OnListFragmentInteractionListener,
        FragmentCloud.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // TODO: memorize last fragment?
        addFragment(FragmentHome(), R.id.fragment_container)
        // focus on navigation list in the drawer on create
        nav_view.checkItem(R.id.nav_home)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                replaceFragment(FragmentHome(), R.id.fragment_container)
            }
            R.id.nav_view_list-> {
                replaceFragment(FragmentItem(), R.id.fragment_container)
            }
            R.id.nav_chart -> {
                replaceFragment(FragmentChart(), R.id.fragment_container)
            }
            R.id.nav_tool -> {
                replaceFragment(FragmentTool(), R.id.fragment_container)
            }
            R.id.nav_category -> {
                replaceFragment(FragmentCategory(), R.id.fragment_container)
                // we don't want display fab with doing category crud

            }
            R.id.nav_cloud -> {
                replaceFragment(FragmentChart(), R.id.fragment_container)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onFragmentInteraction() {
        // TODO Implement
    }

    override fun onListFragmentInteraction() {
        // TODO
    }

    override fun onListFragmentInteraction(category: Category?) {
        // TODO
    }

    // see https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
    // original https://stackoverflow.com/questions/45713747/any-code-improvement-in-adding-replacing-fragment/45715022#45715022
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

    // see https://stackoverflow.com/questions/43246059/how-to-set-selected-item-in-bottomnavigationview
    internal fun NavigationView.checkItem(actionId: Int) {
        menu.findItem(actionId)?.isChecked = true
    }

    // end of example code
}