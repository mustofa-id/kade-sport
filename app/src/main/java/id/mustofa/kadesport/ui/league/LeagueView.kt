/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import id.mustofa.kadesport.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueView(
  private val leagueAdapter: LeagueAdapter
) : AnkoComponent<LeagueFragment> {

  private lateinit var progress: ProgressBar

  override fun createView(ui: AnkoContext<LeagueFragment>) = with(ui) {
    val span = getColumnSpan(ui.ctx)
    frameLayout {
      lparams(width = matchParent, height = matchParent)
      recyclerView {
        id = R.id.listLeague
        layoutManager = GridLayoutManager(ui.ctx, span)
        adapter = leagueAdapter
        clipToPadding = false
        padding = 16
        setHasFixedSize(true)
      }.lparams(matchParent, matchParent)

      progress = progressBar {
        isIndeterminate = true
      }.lparams(matchParent) {
        gravity = Gravity.CENTER
      }
    }
  }

  fun isLoading(constraint: Boolean) {
    progress.visibility = if (constraint) View.VISIBLE else View.GONE
  }

  private fun getColumnSpan(context: Context) =
    context.resources.getInteger(R.integer.list_league_col_span)

}