/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.view.Gravity
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.integer
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueView(
  private val leagueAdapter: LeagueAdapter
) : AnkoComponent<LeagueFragment> {

  private lateinit var progress: ProgressBar

  override fun createView(ui: AnkoContext<LeagueFragment>) = with(ui) {
    frameLayout {
      lparams(width = matchParent, height = matchParent)
      recyclerView {
        id = R.id.listLeague
        layoutManager = GridLayoutManager(ui.ctx, integer(R.integer.list_league_col_span))
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
    progress.isVisible = constraint
  }
}