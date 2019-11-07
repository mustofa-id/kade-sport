/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import id.mustofa.kadesport.KadeApplication
import id.mustofa.kadesport.ViewModelFactory

inline fun <reified T : ViewModel> AppCompatActivity.viewModel() = lazy {
  val appContext = application.applicationContext
  val repository = (appContext as KadeApplication).leagueRepository
  ViewModelProviders.of(this, ViewModelFactory(repository))[T::class.java]
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, value: (T) -> Unit) {
  liveData.observe(this, Observer(value))
}