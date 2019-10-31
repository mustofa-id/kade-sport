/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.graphics.Typeface
import android.view.Gravity
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.*

class LeagueDetailView(private val league: League?) : AnkoComponent<LeagueDetailActivity> {

  override fun createView(ui: AnkoContext<LeagueDetailActivity>) = with(ui) {
    if (league == null) {
      // empty layout
      verticalLayout {
        gravity = Gravity.CENTER
        textView(R.string.msg_no_data) {
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          textSize = 24f
        }
      }
    } else {
      val (_, name, desc, badge) = league

      // show actionBar back button & remove elevation
      ui.owner.supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(true)
        elevation = 0f
      }

      // detail layout
      scrollView {
        lparams(matchParent)

        relativeLayout {
          lparams(matchParent)

          view {
            backgroundResource = R.color.colorPrimary
          }.lparams(matchParent, matchParent) {
            alignParentTop()
            sameBottom(R.id.itemBadge)
            bottomMargin = dip(95)
          }

          imageView {
            id = R.id.itemBadge
            GlideApp.with(ui.ctx)
              .load(badge).into(this)
          }.lparams(
            height = dip(200),
            width = matchParent
          ) {
            centerHorizontally()
            topMargin = dip(16)
            bottomMargin = dip(8)
          }

          textView(name) {
            id = R.id.itemTitle
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          }.lparams(matchParent) {
            below(R.id.itemBadge)
            marginStart = dip(16)
            marginEnd = dip(16)
          }

          textView(desc) {

          }.lparams(matchParent) {
            below(R.id.itemTitle)
            margin = dip(16)
          }
        }
      }
    }
  }
}