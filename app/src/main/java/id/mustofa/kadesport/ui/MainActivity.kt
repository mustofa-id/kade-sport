package id.mustofa.kadesport.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.tabs.TabLayout
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ui.league.LeagueFragment
import id.mustofa.kadesport.ui.leagueeventfavorite.LeagueEventFavoriteFragment
import id.mustofa.kadesport.ui.leagueeventsearch.LeagueEventSearchActivity
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.viewPager

class MainActivity : AppCompatActivity() {

  private var mainPager: ViewPager? = null
  private var mainTab: TabLayout? = null
  private val fragments = linkedMapOf(
    R.string.title_league to LeagueFragment(),
    R.string.title_event_favorite to LeagueEventFavoriteFragment()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initView()
    mainTab?.setupWithViewPager(mainPager)
  }

  private fun initView() {
    @IdRes val pagerId = 4123
    coordinatorLayout {
      lparams(matchParent, matchParent)
      appBarLayout {
        lparams(matchParent)
        toolbar {
          setTitle(R.string.app_name)
          applyMenu()
        }.lparams(matchParent) {
          scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
        }
        mainTab = tabLayout {
          id = R.id.mainTab
          lparams(matchParent)
        }
      }
      mainPager = viewPager {
        id = pagerId
        isNestedScrollingEnabled = true
        adapter = pagerAdapter()
      }.lparams(matchParent, matchParent) {
        behavior = AppBarLayout.ScrollingViewBehavior()
      }
    }
  }

  private fun Toolbar.applyMenu() {
    menu?.add(0, R.id.menuSearch, 0, R.string.title_event_search)
      ?.setIcon(R.drawable.ic_search)
      ?.setOnMenuItemClickListener {
        if (it.itemId == R.id.menuSearch)
          startActivity<LeagueEventSearchActivity>()
        true
      }
      ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
  }

  private fun pagerAdapter() = object : FragmentStatePagerAdapter(supportFragmentManager, 1) {
    override fun getCount() = fragments.size
    override fun getItem(position: Int) = fragments.values.toList()[position]
    override fun getPageTitle(position: Int) = getString(fragments.keys.toList()[position])
  }
}