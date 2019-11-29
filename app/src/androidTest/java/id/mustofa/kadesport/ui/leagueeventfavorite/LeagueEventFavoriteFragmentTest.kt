package id.mustofa.kadesport.ui.leagueeventfavorite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import id.mustofa.kadesport.AsyncFragmentTest
import id.mustofa.kadesport.CustomAssertions.recyclerViewItemCount
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.data.source.local.SportDBHelper
import id.mustofa.kadesport.data.source.local.SportDBHelper.Companion.TABLE_FAVORITE_EVENT
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.junit.Before
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/23/19
 */
class LeagueEventFavoriteFragmentTest : AsyncFragmentTest() {

  override val fragment = LeagueEventFavoriteFragment()
  private var favoriteCount = 0

  @Before
  fun setUp() {
    val context = activityRule.activity.applicationContext
    SportDBHelper(context, context.getString(R.string.db_name)).use {
      favoriteCount = select(TABLE_FAVORITE_EVENT)
        .parseList(classParser<Event>()).size
    }
  }

  @Test
  fun loadFavorites() {
    onView(withId(R.id.items))
      .check(matches(isDisplayed()))
      .check(recyclerViewItemCount(favoriteCount))
  }
}