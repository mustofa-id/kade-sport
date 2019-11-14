/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

class LeagueEventDetailActivity : AppCompatActivity() {

  @DrawableRes
  private var favoriteIcon = R.drawable.ic_favorite_removed
  private val model by viewModel<LeagueEventDetailViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    loadExtras()
    subscribeObservers()
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.clear() // prevent duplicate menu on config change
    menu?.add(0, R.id.menuFavorite, 0, R.string.title_event_favorite)?.setIcon(favoriteIcon)
      ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    return super.onPrepareOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    else if (item.itemId == R.id.menuFavorite) model.toggleFavorite()
    return super.onOptionsItemSelected(item)
  }

  private fun loadExtras() {
    intent.extras?.let {
      val eventId = it.getLong(EVENT_ID)
      model.loadEvent(eventId)
    }
  }

  private fun subscribeObservers() {
    val root = find<View>(android.R.id.content)
    observe(model.eventState) { LeagueEventDetailView(it).setContentView(this) }
    observe(model.favoriteMessage) { root.snackbar(it) }
    observe(model.favoriteIcon) {
      favoriteIcon = it
      invalidateOptionsMenu()
    }
  }

  companion object {
    const val EVENT_ID = "_ExtraEventId_"
  }
}