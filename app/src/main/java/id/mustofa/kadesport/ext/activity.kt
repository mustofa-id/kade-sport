/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import id.mustofa.kadesport.KadeApplication
import id.mustofa.kadesport.ViewModelFactory

fun Activity.withViewModelFactory(): ViewModelFactory {
  val leagueRepository = (applicationContext as KadeApplication).leagueRepository
  return ViewModelFactory(leagueRepository)
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, value: (T) -> Unit) {
  liveData.observe(this, Observer(value))
}