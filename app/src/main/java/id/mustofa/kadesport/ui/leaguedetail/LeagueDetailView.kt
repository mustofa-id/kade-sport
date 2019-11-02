/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.graphics.Typeface
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.ext.errorView
import id.mustofa.kadesport.ext.loadingView
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.util.usePlaceholder
import org.jetbrains.anko.*

class LeagueDetailView(private val state: State<League>) : AnkoComponent<LeagueDetailActivity> {

  override fun createView(ui: AnkoContext<LeagueDetailActivity>) = with(ui) {
    when (state) {
      is Loading -> loadingView()
      is Error -> errorView(state.message)
      is Success -> {
        val (_, name, desc, _, badgeUrl) = state.data
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
              textSize = 18f
              typeface = Typeface.DEFAULT_BOLD
              textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            }.lparams(matchParent) {
              below(R.id.itemBadge)
              marginStart = dip(16)
              marginEnd = dip(16)
            }

            // description
            textView(desc)
              .lparams(matchParent) {
                below(R.id.itemTitle)
                margin = dip(16)
              }
          }
        }
      }
    }
  }

}