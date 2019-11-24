package id.mustofa.kadesport.ui.leagueeventsearch

import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import id.mustofa.kadesport.AsyncUIOperation
import id.mustofa.kadesport.R
import org.junit.Rule
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/23/19
 */
class LeagueEventSearchActivityTest : AsyncUIOperation() {

  @get:Rule
  val activityRule = ActivityTestRule(LeagueEventSearchActivity::class.java)

  @Test
  fun searchEvents() {
    onView(withId(R.id.searchInput))
      .check(matches(isDisplayed()))
      .perform(click())

    onView(isAssignableFrom(EditText::class.java))
      .check(matches(isDisplayed()))
      .perform(typeText("man"), pressKey(KeyEvent.KEYCODE_ENTER))

    onView(withId(R.id.items))
      .check(matches(isDisplayed()))
  }
}