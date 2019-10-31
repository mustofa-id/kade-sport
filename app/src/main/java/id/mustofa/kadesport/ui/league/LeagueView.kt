/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import id.mustofa.kadesport.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueView : AnkoComponent<LeagueFragment> {

  override fun createView(ui: AnkoContext<LeagueFragment>) = with(ui) {
    val span = getColumnSpan(ui.ctx)
    verticalLayout {
      lparams(width = matchParent, height = matchParent)
      recyclerView {
        id = R.id.listLeague
        layoutManager = GridLayoutManager(ui.ctx, span)
        clipToPadding = false
        padding = 16
        setHasFixedSize(true)
      }
    }
  }

  private fun getColumnSpan(context: Context) =
    context.resources.getInteger(R.integer.list_league_col_span)

  companion object {
    operator fun invoke(inflater: LayoutInflater, fragment: LeagueFragment): LinearLayout {
      val ankoContext = AnkoContext.Companion.create(inflater.context, fragment, false)
      return LeagueView().createView(ankoContext)
    }
  }
}