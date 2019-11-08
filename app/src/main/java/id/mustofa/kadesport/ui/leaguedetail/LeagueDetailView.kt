/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.IdRes
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.ext.clusterView
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity.Companion.EVENT_TYPE
import id.mustofa.kadesport.ui.leagueevents.LeagueEventActivity.Companion.LEAGUE_ID
import id.mustofa.kadesport.ui.leagueevents.LeagueEventType
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.util.usePlaceholder
import org.jetbrains.anko.*

class LeagueDetailView(private val state: State<League>) : AnkoComponent<LeagueDetailActivity> {

  override fun createView(ui: AnkoContext<LeagueDetailActivity>) = with(ui) {
    @IdRes val detailId = 1123
    @IdRes val nextEventId = 1124
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
        val (leagueId, name, desc, badgeUrl) = state.data
        // detail layout
        scrollView {
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
                .with(ui.ctx)
                .load(badgeUrl)
                .usePlaceholder(ui.ctx)
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
              id = detailId
              textView(desc) {
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
                EVENT_TYPE to LeagueEventType.NEXT
              )
            }) {
              id = nextEventId
              textView("Loading...") {
                id = R.id.eventNext
                horizontalPadding = dip(16)
              }
            }.lparams(matchParent) {
              below(detailId)
            }

            // Latest events
            clusterView("Latest result", onClick = {
              ctx.startActivity<LeagueEventActivity>(
                LEAGUE_ID to leagueId,
                EVENT_TYPE to LeagueEventType.PAST
              )
            }) {
              textView("Loading...") {
                id = R.id.eventPast
                horizontalPadding = dip(16)
              }
            }.lparams(matchParent) {
              below(nextEventId)
            }
          }
        }
      }
    }
  }
}