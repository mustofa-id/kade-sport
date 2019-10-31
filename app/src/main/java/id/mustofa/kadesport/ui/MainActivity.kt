package id.mustofa.kadesport.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.ui.league.LeagueFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportFragmentManager.beginTransaction().apply {
      replace(android.R.id.content, LeagueFragment())
      commit()
    }
  }
}
