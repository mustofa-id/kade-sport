package id.mustofa.kadesport.ui.leaguedetail

import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import id.mustofa.kadesport.AsyncUIOperation
import id.mustofa.kadesport.R
import id.mustofa.kadesport.activityTestRuleIntentOf
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity.Companion.EXTRA_LEAGUE_ID
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/23/19
 */
class LeagueDetailActivityTest : AsyncUIOperation() {

  @get:Rule
  val activityRule = activityTestRuleIntentOf<LeagueDetailActivity> {
    putExtra(EXTRA_LEAGUE_ID, 4328L)
  }

  @Test
  fun loadLeague() {
    onView(withId(R.id.itemBadge))
      .check(matches(isDisplayed()))

    onView(withId(R.id.itemTitle))
      .check(matches(isDisplayed()))

    onView(withText("About this league"))
      .check(matches(isDisplayed()))
      .perform(click())

    onView(allOf(isAssignableFrom(Button::class.java), withText("OK")))
      .check(matches(isDisplayed()))
      .perform(click())

    onView(withId(R.id.itemDescText))
      .check(matches(isDisplayed()))

    onView(withId(R.id.parentContainer))
      .perform(swipeUp())

    onView(withId(R.id.eventNextText))
      .check(matches(isDisplayed()))

    onView(withId(R.id.eventPastText))
      .check(matches(isDisplayed()))
  }
}