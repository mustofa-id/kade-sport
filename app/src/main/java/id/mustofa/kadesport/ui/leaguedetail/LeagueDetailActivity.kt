/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.withViewModelFactory
import org.jetbrains.anko.setContentView

class LeagueDetailActivity : AppCompatActivity() {

  private val model: LeagueDetailViewModel by viewModels { withViewModelFactory() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    subscribeObservers()
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      elevation = 0f
    }
  }

  private fun subscribeObservers() {
    intent.extras?.getLong(EXTRA_LEAGUE_ID)?.let { model.loadLeague(it) }
    observe(model.leagueState) { LeagueDetailView(it).setContentView(this) }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val EXTRA_LEAGUE_ID = "_ExtraLeagueId__"
  }
}