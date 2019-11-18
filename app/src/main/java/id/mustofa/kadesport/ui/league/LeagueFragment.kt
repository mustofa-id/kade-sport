/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.integer
import id.mustofa.kadesport.ext.listingView
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.common.ListingView
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity.Companion.EXTRA_LEAGUE_ID
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity

class LeagueFragment : Fragment() {

  private val model by viewModel<LeagueViewModel>()
  private val adapter by lazy { LeagueAdapter(GlideApp.with(this)) }
  private lateinit var leagueView: ListingView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return UI {
      leagueView = listingView {
        adapter = this@LeagueFragment.adapter
        layoutManager = GridLayoutManager(context, integer(R.integer.list_league_col_span))
      }
    }.view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    adapter.onItemClick = { startActivity<LeagueDetailActivity>(EXTRA_LEAGUE_ID to it.id) }
    observe(model.leagues) { adapter.submitList(it) }
    observe(model.loading) { leagueView.isLoading(it) }
  }
}
