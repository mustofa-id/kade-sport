/*
 * Mustofa on 11/4/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueevents

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.LeagueEventMin
import id.mustofa.kadesport.ext.selectableItemBackground
import id.mustofa.kadesport.ext.str
import id.mustofa.kadesport.util.GlideRequests
import org.jetbrains.anko.*

class LeagueEventItemView(
  private val parent: ViewGroup,
  private val glide: GlideRequests
) : AnkoComponent<ViewGroup> {

  private lateinit var homeBadge: ImageView
  private lateinit var awayBadge: ImageView
  private lateinit var homeName: TextView
  private lateinit var awayName: TextView
  private lateinit var score: TextView
  private lateinit var playTime: TextView

  override fun createView(ui: AnkoContext<ViewGroup>): View {
    return with(ui) {
      relativeLayout {
        background = selectableItemBackground(ctx)
        lparams(matchParent) {
          verticalPadding = dip(16)
        }

        // home badge
        homeBadge = imageView {
          id = R.id.badgeHome
          adjustViewBounds = true
        }.lparams(height = dip(96)) {
          alignParentStart()
          horizontalMargin = dip(24)
          startOf(R.id.infoContainer)
        }

        // home name
        homeName = textView {
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
          score = textView {
            textSize = 36f
            typeface = Typeface.DEFAULT_BOLD
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          }
          playTime = textView {
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          }
        }.lparams {
          centerInParent()
        }

        // away badge
        awayBadge = imageView {
          id = R.id.badgeAway
          adjustViewBounds = true
        }.lparams(height = dip(96)) {
          alignParentEnd()
          horizontalMargin = dip(24)
          endOf(R.id.infoContainer)
        }

        // away name
        awayName = textView {
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }.lparams {
          alignStart(R.id.badgeAway)
          alignEnd(R.id.badgeAway)
          below(R.id.badgeAway)
          topMargin = dip(8)
        }
      }
    }
  }

  fun bind(event: LeagueEventMin): Unit = with(event) {
    val bothScore = "${homeScore.str()} : ${awayScore.str()}"
    val dateTime = "${time.str()}\n${date.str()}"
    homeName.text = home
    awayName.text = away
    score.text = bothScore
    playTime.text = dateTime
    homeBadgePath?.let { glide.load(it).into(homeBadge) }
    awayBadgePath?.let { glide.load(it).into(awayBadge) }
  }

  fun create(): View {
    val ankoContext = AnkoContext.Companion.create(parent.context, parent)
    return createView(ankoContext)
  }
}