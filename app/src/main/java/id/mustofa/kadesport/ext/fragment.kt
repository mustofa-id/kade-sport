/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import id.mustofa.kadesport.KadeApplication
import id.mustofa.kadesport.ViewModelFactory

fun Fragment.withViewModelFactory(): ViewModelFactory {
  val leagueRepository = (requireContext().applicationContext as KadeApplication).leagueRepository
  return ViewModelFactory(leagueRepository)
}

fun <T> Fragment.observe(liveData: LiveData<T>, value: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner, Observer(value))
}