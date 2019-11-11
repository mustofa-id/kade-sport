package id.mustofa.kadesport.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ui.league.LeagueFragment
import id.mustofa.kadesport.ui.leagueeventsearch.LeagueEventSearchActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportFragmentManager.beginTransaction().apply {
      replace(android.R.id.content, LeagueFragment())
      commit()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menu?.add(0, 0, 0, R.string.title_event_search)
      ?.setIcon(R.drawable.ic_search)
      ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == 0) startActivity<LeagueEventSearchActivity>()
    return super.onOptionsItemSelected(item)
  }
}
