/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.ext.listingView
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.common.EventAdapter
import id.mustofa.kadesport.ui.common.ListingView
import org.jetbrains.anko.support.v4.UI

class LeagueEventFavoriteFragment : Fragment() {

  private val model by viewModel<LeagueEventFavoriteViewModel>()

  private lateinit var adapter: EventAdapter
  private lateinit var favoriteView: ListingView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    adapter = EventAdapter(requireContext())
    return UI {
      favoriteView = listingView {
        adapter = this@LeagueEventFavoriteFragment.adapter
        layoutManager = LinearLayoutManager(context)
      }
    }.view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    observe(model.favoriteEvents) { adapter.submitList(it) }
    observe(model.loading) { favoriteView.isLoading(it) }
    observe(model.message) { favoriteView.setMessage(it) }
  }

  override fun onStart() {
    super.onStart()
    model.loadEvents()
  }
}