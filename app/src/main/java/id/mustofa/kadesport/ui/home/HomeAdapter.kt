/*
 * Mustofa on 12/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.home

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ui.eventfavorite.EventFavoriteFragment
import id.mustofa.kadesport.ui.league.LeagueFragment

class HomeAdapter(context: Context, manager: FragmentManager) :
  FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  private val fragments = linkedMapOf(
    context.getString(R.string.title_league) to LeagueFragment(),
    context.getString(R.string.title_event_favorite) to EventFavoriteFragment()
  )

  override fun getCount() = fragments.size

  override fun getItem(position: Int) =
    fragments.values.toList()[position]

  override fun getPageTitle(position: Int) =
    fragments.keys.toList()[position]
}