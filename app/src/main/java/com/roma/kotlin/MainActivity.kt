package com.roma.kotlin

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v4.widget.DrawerLayout
import android.support.v4.app.DialogFragment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.fragments.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.OnFragmentInteractionListener,
        FragmentItem.OnListFragmentInteractionListener,
        FragmentChart.OnFragmentInteractionListener,
        FragmentTool.OnFragmentInteractionListener,
        FragmentCloud.OnFragmentInteractionListener,
        OnDialogInteractionListener {


    var fabAction = FAB_ACTION_ADD_ITEM
    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // see https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer
        drawerToggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        fab.setOnClickListener { view ->
            when (fabAction) {
                FAB_ACTION_ADD_ITEM -> {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                }
                FAB_ACTION_ADD_CATEGORY -> {
                    onFullscreenDialog(FragmentAddCategory())
                }
            }
        }
        // TODO: memorize last fragment?
        addFragment(FragmentHome(), R.id.fragment_container)
        // focus on navigation list in the drawer on create
        nav_view.checkItem(R.id.nav_home)
    }

    fun onFullscreenDialog(dialog : DialogFragment) {
        setDrawerState(false)
        fab.hide()
        replaceFragment(dialog, R.id.fragment_container)
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_close)
        // see comment https://stackoverflow.com/questions/37965231/return-to-default-onclicklistener-navigation-drawer-icon
        // set on click listener on toggle action instead of toobar
        drawerToggle.setToolbarNavigationClickListener {
            // we don't need keep state after close
            dialog.dismissAllowingStateLoss()
            supportFragmentManager.popBackStack()
            enableDrawer()
        }
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
        // set fab default action
        fabAction = FAB_ACTION_ADD_ITEM
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
                fabAction = FAB_ACTION_ADD_CATEGORY
            }
            R.id.nav_cloud -> {
                replaceFragment(FragmentCloud(), R.id.fragment_container)
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

    override fun onCloseDialogInteraction() {
        // cannot get
//        var editCategoryName = findViewById(R.id.editCategoryName) as EditText
//        Toast.makeText(this, editCategoryName.text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
        enableDrawer()
        when (fabAction) {
            FAB_ACTION_ADD_CATEGORY -> {
                replaceFragment(FragmentCategory(), R.id.fragment_container)
            }
        }
    }

    override fun onOpenDialogInteraction(dialog : DialogFragment) {
        Toast.makeText(this, "open dialog", Toast.LENGTH_SHORT).show()
        onFullscreenDialog(dialog)
    }

    fun enableDrawer() {
        hideKeyboard()
        drawerToggle.setToolbarNavigationClickListener(null)
        drawerToggle.setHomeAsUpIndicator(null)
        setDrawerState(true)
        fab.show()
    }

    companion object {
        const val FAB_ACTION_ADD_ITEM = 0
        const val FAB_ACTION_ADD_CATEGORY = 1
        const val FAB_ACTION_ADD_SUBCATEGORY = 2
    }

    /**
     * see https://stackoverflow.com/questions/19439320/disabling-navigation-drawer-toggling-home-button-up-indicator-in-fragments
     * see https://github.com/mikepenz/MaterialDrawer
     * see https://github.com/mikepenz/MaterialDrawer/issues/76
     */
    fun setDrawerState(isEnabled: Boolean) {
        if (isEnabled) {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            drawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED)
            drawerToggle.setDrawerIndicatorEnabled(true)
            drawerToggle.syncState()
        } else {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            drawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            drawerToggle.setDrawerIndicatorEnabled(false)
            drawerToggle.syncState()
        }
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

    /**
     * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard?page=1&tab=votes#tab-top
     */
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = this.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    // end of example code
}