/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventdetail

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
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.ext.str
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.tintedImageView
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.custom.ankoView

class LeagueEventDetailView(
  private val state: State<LeagueEvent>
) : AnkoComponent<LeagueEventDetailActivity> {

  // TODO: implement DRY
  override fun createView(ui: AnkoContext<LeagueEventDetailActivity>) = with(ui) {
    val glide = GlideApp.with(ctx)
    when (state) {
      is Loading -> frameLayout {
        lparams(matchParent, matchParent) { padding = dip(16) }
        progressBar {
          isIndeterminate = true
        }.lparams(matchParent) {
          gravity = Gravity.CENTER
        }
      }

      is Error -> verticalLayout {
        lparams(matchParent, matchParent)
        gravity = Gravity.CENTER
        padding = dip(16)
        imageView(R.drawable.ic_error)
          .lparams(matchParent, dip(92))
        textView(state.message) {
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          textSize = 16f
        }.lparams(matchParent)
      }

      is Success -> {
        val e = state.data
        scrollView {
          verticalLayout {
            relativeLayout {
              lparams(matchParent) {
                verticalPadding = dip(16)
                verticalMargin = dip(16)
              }

              // home badge
              imageView {
                id = R.id.badgeHome
                adjustViewBounds = true
                e.homeTeam?.let { glide.load(it.badgePath).into(this) }
              }.lparams(height = dip(96)) {
                alignParentStart()
                horizontalMargin = dip(24)
                startOf(R.id.infoContainer)
              }

              // home name
              textView(e.homeName) {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
              }.lparams {
                alignStart(R.id.badgeHome)
                alignEnd(R.id.badgeHome)
                below(R.id.badgeHome)
                topMargin = dip(8)
              }

              // score & date container
              verticalLayout {
                id = R.id.infoContainer
                textView("${e.homeScore.str()} : ${e.awayScore.str()}") {
                  textSize = 36f
                  typeface = Typeface.DEFAULT_BOLD
                  textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                textView("${e.dateEvent}\n${e.time}") {
                  textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
              }.lparams {
                centerInParent()
              }

              // away badge
              imageView {
                id = R.id.badgeAway
                adjustViewBounds = true
                e.awayTeam?.let { glide.load(it.badgePath).into(this) }
              }.lparams(height = dip(96)) {
                alignParentEnd()
                horizontalMargin = dip(24)
                endOf(R.id.infoContainer)
              }

              // away name
              textView(e.awayName) {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
              }.lparams {
                alignStart(R.id.badgeAway)
                alignEnd(R.id.badgeAway)
                below(R.id.badgeAway)
                topMargin = dip(8)
              }
            }

            separator()

            // Goal
            sectionInfoView(
              homeText = e.homeGoalDetails,
              awayText = e.awayGoalDetails,
              icon = R.drawable.ic_goal,
              color = R.color.colorBall
            )

            separator()

            // yellow card
            sectionInfoView(
              homeText = e.homeYellowCards,
              awayText = e.awayYellowCards,
              icon = R.drawable.ic_card,
              color = R.color.colorYellowCard
            )

            // red card
            sectionInfoView(
              homeText = e.homeRedCards,
              awayText = e.awayRedCards,
              icon = R.drawable.ic_card,
              color = R.color.colorRedCard
            )

            // home team lineup
            teamLineupView(
              title = "${e.homeName} Team Lineup",
              color = R.color.colorHomeStadium,
              goalKeeper = e.homeLineupGoalkeeper,
              defense = e.homeLineupDefense,
              midfield = e.homeLineupMidfield,
              forward = e.homeLineupForward
            )

            // away team lineup
            teamLineupView(
              title = "${e.awayName} Team Lineup",
              color = R.color.colorAwayStadium,
              goalKeeper = e.awayLineupGoalkeeper,
              defense = e.awayLineupDefense,
              midfield = e.awayLineupMidfield,
              forward = e.awayLineupForward
            )
          }
        }
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