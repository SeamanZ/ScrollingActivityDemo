package com.example.scrollingactivitydemo

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout.SHOW_DIVIDER_END
import android.widget.LinearLayout.SHOW_DIVIDER_NONE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    private lateinit var tabPageAdapter: TabPageAdapter

    private var colorStateListPinned: ColorStateList? = null
    private var colorStateList: ColorStateList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        colorStateListPinned = ContextCompat.getColorStateList(
            this, R.color.tab_text_color_pinned
        )
        colorStateList = ContextCompat.getColorStateList(
            this, R.color.tab_text_color
        )


        val offset =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                56F,
                resources.displayMetrics
            )

        swipeRefreshLayout.offsetTopAndBottom(offset.toInt())
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 2000L)
        }

        app_bar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                Log.v(
                    "OnOffsetChangedListener",
                    "verticalOffset = $verticalOffset " +
                            " appBarLayout height = ${appBarLayout.height} " +
                            " totalScrollRange = ${appBarLayout.totalScrollRange}"
                )

                val appBarLayoutHeight = appBarLayout.height
                val tabLayoutHeight = tabLayout.height

                //verticalOffset == 0 说明appbar是完全展开的
                val swipeEnabled = verticalOffset == 0
                if (swipeRefreshLayout.isEnabled != swipeEnabled) {
                    swipeRefreshLayout.isEnabled = swipeEnabled
                }

                val isTabPinned = (verticalOffset + appBarLayout.totalScrollRange) == 0

                app_bar.apply {
                    this.isEnabled = isTabPinned
                    this.showDividers = if (isTabPinned) SHOW_DIVIDER_NONE else SHOW_DIVIDER_END
                }

                tabLayout.apply {
                    if (isTabPinned) {
                        this.tabTextColors = colorStateListPinned
                        this.setBackgroundColor(Color.parseColor("#008577"))
                    } else {
                        this.tabTextColors = colorStateList
                        this.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }
                }

                val visibility =
                    if (isTabPinned)
                        View.VISIBLE
                    else
                        View.GONE

                if (fab.visibility != visibility) {
                    fab.visibility = visibility
                }

            })

        indexList.addItemDecoration(
            DividerItemDecoration(
                this@ScrollingActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        indexList.adapter = IndicesAdapter()

        tabPageAdapter = TabPageAdapter(supportFragmentManager)
        tabViewPager.adapter = tabPageAdapter

        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(tabViewPager, true)

        tabLayout.isTabIndicatorFullWidth = false

        //划到顶部
        fab.setOnClickListener {
            (tabPageAdapter.getItem(tabViewPager.currentItem) as TabFragment).apply {
                val itemCount = smoothScrollToTop()
                val delay = itemCount * 50 / 5
                expandedAppBar(Math.min(1000, delay))
            }

        }
    }

    private fun expandedAppBar(itemCount: Int) {
        fab?.postDelayed({
            app_bar.setExpanded(true, true)
        }, (itemCount / 8).toLong())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class TabPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        private val fragments = SparseArray<TabFragment>()
        private val titles = listOf(
            "You may like",
            "Mobile",
            "Hobbies",
            "Electronics",
            "Electronics",
            "Home",
            "sports & outdoor"
        )

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun getCount(): Int = titles.size

        override fun getItem(position: Int): Fragment {
            var tabFragment = fragments[position]
            if (tabFragment == null) {
                tabFragment = TabFragment.newInstance(getPageTitle(position))
                fragments.put(position, tabFragment)
            }
            return tabFragment
        }
    }
}
