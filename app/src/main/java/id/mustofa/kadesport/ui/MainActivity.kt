package id.mustofa.kadesport.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ui.league.LeagueFragment
import id.mustofa.kadesport.ui.leagueeventfavorite.LeagueEventFavoriteFragment
import id.mustofa.kadesport.ui.leagueeventsearch.LeagueEventSearchActivity
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.viewPager

class MainActivity : AppCompatActivity() {

  private val fragments = linkedMapOf(
    R.string.title_league to LeagueFragment(),
    R.string.title_event_favorite to LeagueEventFavoriteFragment()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    @IdRes val pagerId = 4123
    frameLayout {
      lparams(matchParent, matchParent)
      viewPager {
        id = pagerId
        adapter = pagerAdapter()
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menu?.add(0, 0, 0, R.string.title_event_search)?.setIcon(R.drawable.ic_search)
      ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == 0) startActivity<LeagueEventSearchActivity>()
    return super.onOptionsItemSelected(item)
  }

  private fun pagerAdapter() = object : FragmentStatePagerAdapter(supportFragmentManager, 1) {
    override fun getCount() = fragments.size
    override fun getItem(position: Int) = fragments.values.toList()[position]
    override fun getPageTitle(position: Int) = getString(fragments.keys.toList()[position])
  }
}