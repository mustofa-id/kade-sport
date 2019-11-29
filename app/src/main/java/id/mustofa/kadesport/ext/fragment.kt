/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import id.mustofa.kadesport.KadeApplication
import id.mustofa.kadesport.ViewModelFactory

inline fun <reified T : ViewModel> Fragment.viewModel() = lazy {
  val appContext = requireActivity().application.applicationContext
  val repositories = (appContext as KadeApplication).repositories
  ViewModelProviders.of(requireActivity(), ViewModelFactory(repositories))[T::class.java]
}

fun <T> Fragment.observe(liveData: LiveData<T>, value: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner, Observer(value))
}