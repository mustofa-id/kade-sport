/*
 * Mustofa on 12/10/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.view.StateView
import id.mustofa.kadesport.view.navigationUpEnable
import id.mustofa.kadesport.view.searchMenuEnable
import id.mustofa.kadesport.view.stateView
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.verticalPadding

class TeamFragment : Fragment() {

  private val model: TeamViewModel by viewModel()
  private val args: TeamFragmentArgs by navArgs()

  private lateinit var adapter: TeamAdapter
  private lateinit var stateView: StateView<List<Team>>

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = UI {
    coordinatorLayout {
      appBarLayout {
        lparams(matchParent)
        toolbar {
          title = args.desc
          navigationUpEnable()
          searchMenuEnable()
        }.lparams(matchParent) {
          scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
        }
      }
      stateView = stateView<List<Team>> {
        setRecyclerView {
          clipToPadding = false
          verticalPadding = dip(16)
        }
      }.lparams(matchParent, matchParent) {
        behavior = AppBarLayout.ScrollingViewBehavior()
      }
    }
  }.view

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter = TeamAdapter(GlideApp.with(this))
    stateView.setup(adapter, LinearLayoutManager(context))

    model.loadTeams(args.leagueId)
    observe(model.teamState) { stateView.handleState(it, adapter::submitList) }
  }
}