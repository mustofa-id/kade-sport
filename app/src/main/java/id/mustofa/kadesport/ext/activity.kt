/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.KadeApplication
import id.mustofa.kadesport.ViewModelFactory
import id.mustofa.kadesport.ui.common.ListingView
import org.jetbrains.anko.custom.ankoView

inline fun <reified T : ViewModel> AppCompatActivity.viewModel() = lazy {
  val appContext = application.applicationContext
  val repositories = (appContext as KadeApplication).repositories
  ViewModelProviders.of(this, ViewModelFactory(repositories))[T::class.java]
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, value: (T) -> Unit) {
  liveData.observe(this, Observer(value))
}

fun Activity.listingView(init: RecyclerView.() -> Unit): ListingView {
  return ankoView({ ListingView(it, init) }, 0) {}
}