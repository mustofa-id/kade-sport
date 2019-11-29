package id.mustofa.kadesport.ui.leagueevents

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import id.mustofa.kadesport.AsyncUIOperation
import id.mustofa.kadesport.R
import id.mustofa.kadesport.activityTestRuleIntentOf
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity.Companion.EVENT_TYPE
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity.Companion.LEAGUE_ID
import org.junit.Rule
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/23/19
 */
class LeagueEventActivityTest : AsyncUIOperation() {

  @get:Rule
  val activityRule = activityTestRuleIntentOf<LeagueEventActivity> {
    putExtra(LEAGUE_ID, 4328L)
    putExtra(EVENT_TYPE, EventType.NEXT)
  }

  @Test
  fun loadEvents() {
    onView(withId(R.id.items))
      .check(matches(isDisplayed()))
  }
}