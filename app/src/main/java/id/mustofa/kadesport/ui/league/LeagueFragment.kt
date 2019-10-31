/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.source.DefaultLeagueRepository
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailActivity.Companion.EXTRA_LEAGUE
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class LeagueFragment : Fragment() {

  private val repository = DefaultLeagueRepository()
  private val adapter: LeagueAdapter by lazy {
    LeagueAdapter(GlideApp.with(this))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    state: Bundle?
  ) = LeagueView(inflater, this)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    find<RecyclerView>(R.id.listLeague).also { it.adapter = adapter }
    adapter.onItemClick = { startActivity<LeagueDetailActivity>(EXTRA_LEAGUE to it) }
  }

  override fun onResume() {
    super.onResume()
    adapter.submitList(repository.fetchAllLeagues())
  }
}
