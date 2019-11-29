/*
 * Mustofa on 11/4/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.common

import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.ext.str
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.*

class EventView(context: Context) : _RelativeLayout(context) {

  private val glide = GlideApp.with(context)
  private val homeBadge: ImageView
  private val awayBadge: ImageView
  private val homeTitle: TextView
  private val awayTitle: TextView

  private lateinit var score: TextView
  private lateinit var playTime: TextView

  init {
    id = R.id.parentContainer
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
    homeTitle = textView {
      id = R.id.titleHome
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
        id = R.id.scoreText
        textSize = 36f
        typeface = Typeface.DEFAULT_BOLD
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
      }
      playTime = textView {
        id = R.id.playTimeText
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
    awayTitle = textView {
      id = R.id.titleAway
      textAlignment = TextView.TEXT_ALIGNMENT_CENTER
    }.lparams {
      alignStart(R.id.badgeAway)
      alignEnd(R.id.badgeAway)
      below(R.id.badgeAway)
      topMargin = dip(8)
    }
  }

  fun bind(event: Event): Unit = with(event) {
    val bothScore = "${homeScore.str()} : ${awayScore.str()}"
    val dateTime = "${time.str()}\n${date.str()}"
    homeTitle.text = homeName
    awayTitle.text = awayName
    score.text = bothScore
    playTime.text = dateTime
    homeBadgePath?.let { glide.load(it).into(homeBadge) }
    awayBadgePath?.let { glide.load(it).into(awayBadge) }
  }
}