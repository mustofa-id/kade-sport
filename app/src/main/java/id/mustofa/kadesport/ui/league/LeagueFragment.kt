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
import androidx.fragment.app.viewModels
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.withViewModelFactory
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity.Companion.EXTRA_LEAGUE_ID
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.startActivity

class LeagueFragment : Fragment() {

  private val model by viewModels<LeagueViewModel> { withViewModelFactory() }
  private val adapter by lazy { LeagueAdapter(GlideApp.with(this)) }
  private lateinit var leagueView: LeagueView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    leagueView = LeagueView(adapter)
    val ankoContext = AnkoContext.Companion.create(inflater.context, this, false)
    return leagueView.createView(ankoContext)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    adapter.onItemClick = { startActivity<LeagueDetailActivity>(EXTRA_LEAGUE_ID to it.id) }
    observe(model.leagues) { adapter.submitList(it) }
    observe(model.loading) { leagueView.isLoading(it) }
  }
}
