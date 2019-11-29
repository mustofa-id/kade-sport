/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.app.Activity
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import id.mustofa.kadesport.ext.clusterView
import id.mustofa.kadesport.ui.common.StateView
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity.Companion.EVENT_TYPE
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity.Companion.LEAGUE_ID
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.util.usePlaceholder
import org.jetbrains.anko.*

class LeagueDetailView(state: State<League>) : StateView<League>(state) {

  override fun successView(ui: AnkoContext<Activity>, data: League) = with(ui) {
    val (leagueId, name, desc, badgeUrl) = data
    scrollView {
      id = R.id.parentContainer
      lparams(matchParent)
      relativeLayout {
        lparams(matchParent) { bottomPadding = dip(16) }

        // header background
        view {
          backgroundResource = R.color.colorPrimary
        }.lparams(matchParent, matchParent) {
          alignParentTop()
          sameBottom(R.id.itemBadge)
          bottomMargin = dip(95)
        }

        // badge
        imageView {
          id = R.id.itemBadge
          GlideApp
            .with(ctx)
            .load(badgeUrl)
            .usePlaceholder(ctx)
            .into(this)
        }.lparams(
          height = dip(200),
          width = matchParent
        ) {
          centerHorizontally()
          topMargin = dip(16)
          bottomMargin = dip(8)
        }

        // title
        textView(name) {
          id = R.id.itemTitle
          textSize = 24f
          typeface = Typeface.DEFAULT_BOLD
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }.lparams(matchParent) {
          below(R.id.itemBadge)
          horizontalMargin = dip(16)
          bottomPadding = dip(8)
        }

        // TODO: social & web links

        // description
        clusterView("About this league", onClick = {
          ctx.alert(desc, name) {
            yesButton { it.dismiss() }
            show()
          }
        }) {
          id = R.id.itemDescContainer
          textView(desc) {
            id = R.id.itemDescText
            maxLines = 4
            ellipsize = TextUtils.TruncateAt.END
            horizontalPadding = dip(16)
          }
        }.lparams(matchParent) {
          below(R.id.itemTitle)
        }

        // Upcoming events
        clusterView("Upcoming events", onClick = {
          ctx.startActivity<LeagueEventActivity>(
            LEAGUE_ID to leagueId,
            EVENT_TYPE to EventType.NEXT
          )
        }) {
          id = R.id.eventNextContainer
          textView("Loading...") {
            id = R.id.eventNextText
            horizontalPadding = dip(16)
          }
        }.lparams(matchParent) {
          below(R.id.itemDescContainer)
        }

        // Latest events
        clusterView("Latest result", onClick = {
          ctx.startActivity<LeagueEventActivity>(
            LEAGUE_ID to leagueId,
            EVENT_TYPE to EventType.PAST
          )
        }) {
          id = R.id.eventPastContainer
          textView("Loading...") {
            id = R.id.eventPastText
            horizontalPadding = dip(16)
          }
        }.lparams(matchParent) {
          below(R.id.eventNextContainer)
        }
      }
    }
  }

}