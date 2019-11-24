package id.mustofa.kadesport.ui.leagueeventdetail

import android.widget.ScrollView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import id.mustofa.kadesport.AsyncUIOperation
import id.mustofa.kadesport.R
import id.mustofa.kadesport.activityTestRuleIntentOf
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.ext.splitLiner
import id.mustofa.kadesport.ext.str
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity.Companion.EVENT_ID
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

/**
 * @author Habib Mustofa
 * Indonesia on 11/23/19
 */
class LeagueEventDetailActivityTest : AsyncUIOperation() {

  private val fakeData = FakeTheSportDb()
  private val eventId = 602247L // Man United vs Brighton

  @get:Rule
  val activityRule = activityTestRuleIntentOf<LeagueEventDetailActivity> {
    putExtra(EVENT_ID, eventId)
  }

  @Test
  fun loadEvent() {
    val evt = fakeData.eventById(eventId)

    // -- event view --
    onView(withId(R.id.badgeHome))
      .check(matches(isDisplayed()))

    onView(withId(R.id.titleHome))
      .check(matches(allOf(isDisplayed(), withText(evt.homeName))))

    val score = "${evt.homeScore.str()} : ${evt.awayScore.str()}"
    onView(withId(R.id.scoreText))
      .check(matches(allOf(isDisplayed(), withText(score))))

    val dateTime = "${evt.time.str()}\n${evt.date.str()}"
    onView(withId(R.id.playTimeText))
      .check(matches(allOf(isDisplayed(), withText(dateTime))))

    onView(withId(R.id.badgeAway))
      .check(matches(isDisplayed()))

    onView(withId(R.id.titleAway))
      .check(matches(allOf(isDisplayed(), withText(evt.awayName))))

    // -- goal --
    val homeGoal = evt.homeGoalDetails.splitLiner()
    onView(withText(homeGoal))
      .check(matches(isDisplayed()))

    val awayGoal = evt.awayGoalDetails.splitLiner()
    onView(withText(awayGoal))
      .check(matches(isDisplayed()))

    // -- yellow card --
    val homeYellowCards = evt.homeYellowCards.splitLiner()
    onView(withText(homeYellowCards))
      .check(matches(isDisplayed()))

    val awayYellowCards = evt.awayYellowCards.splitLiner()
    onView(withText(awayYellowCards))
      .check(matches(isDisplayed()))

    onView(
      allOf(
        isAssignableFrom(ScrollView::class.java),
        withId(R.id.parentContainer)
      )
    ).perform(swipeUp())

    // -- home team lineup --
    onView(withText("${evt.homeName} Team Lineup"))
      .check(matches(isDisplayed()))

    // -- away team lineup --
    onView(withText("${evt.awayName} Team Lineup"))
      .check(matches(isDisplayed()))
  }

  @Test
  fun toggleFavorite() {
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
  }
}