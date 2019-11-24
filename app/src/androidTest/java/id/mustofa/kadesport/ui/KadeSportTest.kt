package id.mustofa.kadesport.ui

import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import id.mustofa.kadesport.AsyncUIOperation
import id.mustofa.kadesport.CustomMatchers.tabTitle
import id.mustofa.kadesport.CustomMatchers.tabsCount
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ui.league.LeagueAdapter.LeagueViewHolder
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/18/19
 */
class KadeSportTest : AsyncUIOperation() {

  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun app() {
    // tabs
    onView(withId(R.id.mainTab))
      .check(
        matches(
          allOf(
            tabsCount(2),
            tabTitle(0, R.string.title_league),
            tabTitle(1, R.string.title_event_favorite)
          )
        )
      )

    // league
    onView(allOf(withId(R.id.items), isDisplayed()))
      .perform(actionOnItemAtPosition<LeagueViewHolder>(0, click()))

    // league detail
    onView(withId(R.id.itemTitle))
      .check(matches(isDisplayed()))

    onView(withText("About this league"))
      .check(matches(isDisplayed()))
      .perform(click())

    onView(allOf(isAssignableFrom(Button::class.java), withText("OK")))
      .check(matches(isDisplayed()))
      .perform(click())

    onView(withId(R.id.parentContainer))
      .perform(swipeUp())

    onView(withText("Latest result"))
      .check(matches(isDisplayed()))
      .perform(click())

    // events
    onView(withId(R.id.items))
      .check(matches(isDisplayed()))
      .perform(actionOnItemAtPosition<LeagueViewHolder>(0, click()))

    // events detail
    onView(withId(R.id.badgeHome))
      .check(matches(isDisplayed()))

    onView(withId(R.id.titleHome))
      .check(matches(isDisplayed()))

    onView(withId(R.id.badgeAway))
      .check(matches(isDisplayed()))

    onView(withId(R.id.titleAway))
      .check(matches(isDisplayed()))

    onView(withId(R.id.menuFavorite))
      .check(matches(isDisplayed()))
      .perform(click())

    try {
      onView(withText(R.string.msg_ok_add_fav))
        .check(matches(isDisplayed()))
    } catch (e: NoMatchingViewException) {
      onView(withText(R.string.msg_ok_remove_fav))
        .check(matches(isDisplayed()))
    }

    // navigate to favorite event tab
    pressBack()
    pressBack()
    pressBack()
    onView(withText(R.string.title_event_favorite))
      .check(matches(isDisplayed()))
      .perform(click())

    // favorite event
    try {
      onView(allOf(withId(R.id.items), isDisplayed()))
        .perform(actionOnItemAtPosition<LeagueViewHolder>(0, click()))

      pressBack()
    } catch (e: PerformException) {
      // in case favorite event is empty
      onView(allOf(withId(R.id.messageContainer), isDisplayed()))
        .check(matches(isDisplayed()))
      onView(withText(R.string.msg_empty_result))
        .check(matches(isDisplayed()))
    }

    // navigate to search
    onView(withId(R.id.menuSearch))
      .check(matches(isDisplayed()))
      .perform(click())

    // search
    onView(isAssignableFrom(EditText::class.java))
      .check(matches(isDisplayed()))
      .perform(typeText("man"), pressKey(KeyEvent.KEYCODE_ENTER))

    onView(withId(R.id.items))
      .check(matches(isDisplayed()))
  }
}