/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.eventfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.event.EventAdapter
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.view.StateView
import id.mustofa.kadesport.view.stateView
import org.jetbrains.anko.support.v4.UI

class EventFavoriteFragment : Fragment() {

  private val model: EventFavoriteViewModel by viewModel()
  private lateinit var stateView: StateView<List<Event>>
  private lateinit var adapter: EventAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    state: Bundle?
  ): View? = UI { stateView = stateView() }.view

  override fun onViewCreated(view: View, state: Bundle?) {
    super.onViewCreated(view, state)
    setupStateView()
    subscribeObservers()
    model.loadEvents()
  }

  private fun setupStateView() {
    adapter = EventAdapter(GlideApp.with(this))
    stateView.setup(adapter, LinearLayoutManager(context))
  }

  private fun subscribeObservers() {
    observe(model.favoriteEvents) {
      stateView.handleState(it, adapter::submitList)
    }
  }
}