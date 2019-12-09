/*
 * Mustofa on 12/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import id.mustofa.kadesport.ext.asClusterEvents
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.leaguedetail.view.LoadingView
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.StateView
import id.mustofa.kadesport.view.navigationUpEnable
import id.mustofa.kadesport.view.searchMenuEnable
import id.mustofa.kadesport.view.stateView
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.UI

class LeagueDetailFragment : Fragment() {

  private val args: LeagueDetailFragmentArgs by navArgs()
  private val model: LeagueDetailViewModel by viewModel()

  private var leagueItems: List<Entity>? = null

  private lateinit var glide: GlideRequests
  private lateinit var adapter: LeagueDetailAdapter
  private lateinit var stateView: StateView<Entity>
  private lateinit var toolbar: Toolbar

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    state: Bundle?
  ): View? = UI {
    coordinatorLayout {
      lparams(matchParent, matchParent)
      appBarLayout {
        lparams(matchParent)
        isLiftOnScroll = true
        toolbar = toolbar {
          title = "Loading..."
          navigationUpEnable()
          searchMenuEnable()
        }.lparams(matchParent) {
          scrollFlags = SCROLL_FLAG_SNAP
        }
      }
      stateView = stateView<Entity>()
        .lparams(matchParent, matchParent) {
          behavior = AppBarLayout.ScrollingViewBehavior()
        }
    }
  }.view

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    glide = GlideApp.with(this)
    setupRecyclerView()
    observeLeague()
    observeNextEvents()
    observePastEvents()
    model.loadLeague(args.leagueId)
  }

  private fun setupRecyclerView() {
    adapter = LeagueDetailAdapter(glide)
    stateView.setup(adapter, LinearLayoutManager(context))
  }

  private fun observeLeague() {
    observe(model.leagueState) { state ->
      stateView.handleState(state) {
        if (it is League) {
          toolbar.title = it.name
          leagueItems = emptyList()
          submitItems(it)
        }
      }
    }
  }

  private fun observeNextEvents() {
    observe(model.nextEventState) {
      val title = "Upcoming events"
      submitItems(it.asClusterEvents(title, glide) {
        navigateToEvents(title, EventType.NEXT)
      })
    }
  }

  private fun observePastEvents() {
    observe(model.pastEventState) {
      val title = "Latest events"
      submitItems(it.asClusterEvents(title, glide) {
        navigateToEvents(title, EventType.PAST)
      })
    }
  }

  private fun submitItems(vararg items: Entity) {
    leagueItems = leagueItems?.run { filter { it != LoadingView.Model } + items.toList() }
    adapter.submitList(leagueItems)
  }

  private fun navigateToEvents(title: String, type: EventType) {
    val desc = "$title | ${toolbar.title}"
    val action = LeagueDetailFragmentDirections.actionToEvent(args.leagueId, type, desc)
    findNavController().navigate(action)
  }
}