/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.data.League
import org.jetbrains.anko.setContentView

class LeagueDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val league = intent.extras?.getParcelable<League>(EXTRA_LEAGUE)
    LeagueDetailView(league).setContentView(this)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val EXTRA_LEAGUE = "_itemLeagueExtra__"
  }
}