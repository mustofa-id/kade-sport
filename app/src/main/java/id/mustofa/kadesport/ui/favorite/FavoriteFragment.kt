/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.favorite

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.common.EventTeamAdapter
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.view.ListingView
import id.mustofa.kadesport.view.listingView
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.margin
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.verticalPadding

class FavoriteFragment : Fragment() {

  private val model: FavoriteViewModel by viewModel()

  private lateinit var listingView: ListingView
  private lateinit var adapter: EventTeamAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    state: Bundle?
  ): View? = UI {
    listingView = listingView {
      id = R.id.favoriteListContainer
      setRecyclerView {
        id = R.id.favoriteList
        clipToPadding = false
        verticalPadding = dip(8)
      }
      floatingActionButton {
        imageResource = R.drawable.ic_filter
        setFilterOptions()
      }.lparams {
        gravity = Gravity.END or Gravity.BOTTOM
        margin = dip(16)
      }
    }
  }.view

  override fun onViewCreated(view: View, state: Bundle?) {
    super.onViewCreated(view, state)
    setupStateView()
    subscribeObservers()
    model.loadFavorites()
  }

  private fun FloatingActionButton.setFilterOptions() {
    val types = enumValues<FavoriteType>()
    val popup = PopupMenu(context, this)
    types.forEachIndexed { i, type -> popup.menu.add(0, i, i, type.title) }
    popup.setOnMenuItemClickListener {
      model.currentType = types[it.itemId]
      model.loadFavorites()
      true
    }
    setOnClickListener { popup.show() }
  }

  private fun setupStateView() {
    adapter = EventTeamAdapter(GlideApp.with(this))
    listingView.setup(adapter, LinearLayoutManager(context))
  }

  private fun subscribeObservers() {
    observe(model.favorites, adapter::submitList)
    observe(model.loading, listingView::isLoading)
    observe(model.error, listingView::setError)
  }
}