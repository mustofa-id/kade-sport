package id.mustofa.kadesport.ui.league

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import id.mustofa.kadesport.AsyncFragmentTest
import id.mustofa.kadesport.CustomAssertions.recyclerViewItemCount
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.ui.league.LeagueAdapter.LeagueViewHolder
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/23/19
 */
class LeagueFragmentTest : AsyncFragmentTest() {

  override val fragment = LeagueFragment()
  private val items = LeagueDataSource().getLeagues()

  @Test
  fun loadLeagues() {
    onView(withId(R.id.items))
      .check(matches(isDisplayed()))
      .check(recyclerViewItemCount(items.size))
  }

  @Test
  fun checkLeague() {
    onView(withId(R.id.items))
      .perform(
        actionOnItem<LeagueViewHolder>(
          hasDescendant(withText(items[0].name)),
          click()
        )
      )
  }
}