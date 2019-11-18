/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventdetail

import android.app.Activity
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.ext.eventView
import id.mustofa.kadesport.ui.common.StateView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.tintedImageView
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.custom.ankoView

class LeagueEventDetailView(state: State<LeagueEvent>) : StateView<LeagueEvent>(state) {

  override fun successView(ui: AnkoContext<Activity>, data: LeagueEvent) = with(ui) {
    scrollView {
      id = R.id.parentContainer
      verticalLayout {
        eventView { bind(data) }
        separator()

        // Goal
        sectionInfoView(
          homeText = data.homeGoalDetails,
          awayText = data.awayGoalDetails,
          icon = R.drawable.ic_goal,
          color = R.color.colorBall
        )
        separator()

        // yellow card
        sectionInfoView(
          homeText = data.homeYellowCards,
          awayText = data.awayYellowCards,
          icon = R.drawable.ic_card,
          color = R.color.colorYellowCard
        )

        // red card
        sectionInfoView(
          homeText = data.homeRedCards,
          awayText = data.awayRedCards,
          icon = R.drawable.ic_card,
          color = R.color.colorRedCard
        )

        // home team lineup
        teamLineupView(
          title = "${data.homeName} Team Lineup",
          color = R.color.colorHomeStadium,
          goalKeeper = data.homeLineupGoalkeeper,
          defense = data.homeLineupDefense,
          midfield = data.homeLineupMidfield,
          forward = data.homeLineupForward
        )

        // away team lineup
        teamLineupView(
          title = "${data.awayName} Team Lineup",
          color = R.color.colorAwayStadium,
          goalKeeper = data.awayLineupGoalkeeper,
          defense = data.awayLineupDefense,
          midfield = data.awayLineupMidfield,
          forward = data.awayLineupForward
        )
      }
    }
  }

  private fun ViewManager.sectionInfoView(
    homeText: String?,
    awayText: String?,
    @DrawableRes icon: Int,
    @ColorRes color: Int
  ): View {
    return if (homeText.isNullOrBlank() && awayText.isNullOrBlank())
      view { isVisible = false }
    else ankoView({ _LinearLayout(it) }, theme = 0) {
      orientation = LinearLayout.HORIZONTAL
      weightSum = 2f
      bottomPadding = dip(8)
      lparams(matchParent)

      textView(splitLiner(homeText)) {
        horizontalPadding = dip(8)
        gravity = Gravity.END
      }.lparams(0, weight = 1f)

      tintedImageView(icon) {
        setColorFilter(ContextCompat.getColor(context, color))
      }.lparams(height = matchParent)

      textView(splitLiner(awayText)) {
        horizontalPadding = dip(8)
      }.lparams(0, weight = 1f)
    }
  }

  private fun ViewManager.teamLineupView(
    title: String,
    @ColorRes color: Int,
    goalKeeper: String?,
    defense: String?,
    midfield: String?,
    forward: String?
  ) = ankoView({ _CardView(it) }, theme = 0) {
    lparams(matchParent) {
      horizontalMargin = dip(16)
      verticalMargin = dip(8)
    }
    setCardBackgroundColor(ContextCompat.getColor(context, color))
    verticalLayout {
      lparams(matchParent) { padding = dip(8) }

      // title
      textView(title) {
        typeface = Typeface.DEFAULT_BOLD
        textSize = 16f
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
      }.lparams(matchParent)

      // lineups
      lineupView(split(goalKeeper))
      lineupView(split(defense))
      lineupView(split(midfield))
      lineupView(split(forward))
    }
  }

  private fun ViewManager.lineupView(values: List<String>?): View {
    if (values.isNullOrEmpty()) return view()
    return ankoView({ _LinearLayout(it) }, theme = 0) {
      lparams(matchParent) {
        weightSum = values.size.toFloat()
      }
      orientation = LinearLayout.HORIZONTAL
      values.forEach {
        textView(it) {
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_position, 0, 0)
        }.lparams(0) {
          margin = dip(4)
          weight = 1f
        }
      }
    }
  }

  private fun _LinearLayout.separator() {
    view {
      backgroundResource = R.color.colorSeparator
    }.lparams(matchParent, dip(1)) {
      bottomMargin = dip(8)
    }
  }

  private fun splitLiner(text: String?) = split(text)?.joinToString("\n")

  private fun split(text: String?) = text?.split(';')?.dropLastWhile { it.isBlank() }
}