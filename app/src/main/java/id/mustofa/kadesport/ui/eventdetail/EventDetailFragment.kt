/*
 * Mustofa on 12/4/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.eventdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.base.Entity
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
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.UI

class EventDetailFragment : Fragment() {

  private val args: EventDetailFragmentArgs by navArgs()
  private val model: EventDetailViewModel by viewModel()

  private lateinit var adapter: EventDetailAdapter
  private lateinit var stateView: StateView<Entity>
  private lateinit var toolbar: Toolbar

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    state: Bundle?
  ): View? = UI {
    coordinatorLayout {
      appBarLayout {
        lparams(matchParent)
        toolbar = toolbar {
          title = "Loading..."
          navigationUpEnable()
          setupMenu()
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

  override fun onViewCreated(view: View, state: Bundle?) {
    super.onViewCreated(view, state)

    adapter = EventDetailAdapter(GlideApp.with(this))
    stateView.setup(adapter, LinearLayoutManager(context))

    subscribeObservers()
    model.loadEvent(args.eventId)
  }

  private fun Toolbar.setupMenu() {
    menu.add(0, R.id.menuFavorite, 0, R.string.menu_favorite)
      .setIcon(R.drawable.ic_favorite_removed)
      .setOnMenuItemClickListener { model.toggleFavorite(); true }
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
  }

  private fun subscribeObservers() {
    observe(model.favoriteIcon) { toolbar.menu.findItem(R.id.menuFavorite).setIcon(it) }
    observe(model.favoriteMessage) { if (it != 0) stateView.longSnackbar(it) }
    observe(model.eventState) { state ->
      stateView.handleState(state) {
        if (it is Event) {
          toolbar.title = "${it.homeName} VS ${it.awayName}"
        }
        adapter.submitList(listOf(it))
      }
    }
  }
}