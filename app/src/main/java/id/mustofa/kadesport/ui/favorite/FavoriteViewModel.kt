/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.TeamRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
  private val eventRepo: EventRepository,
  private val teamRepo: TeamRepository
) : ViewModel() {

  private val _favorites = MutableLiveData<List<Entity>>()
  val favorites: LiveData<List<Entity>> = _favorites

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  private val _error = MutableLiveData<Int>()
  val error: LiveData<Int> = _error

  private val favoritesSet = mutableSetOf<Entity>()

  fun loadEvents() {
    _loading.value = true
    viewModelScope.launch {
      launch { postState(eventRepo.getFavorites()) }
      launch { postState(teamRepo.getFavorites()) }
    }.invokeOnCompletion(::onJobsComplete)
  }

  private fun postState(state: State<List<Entity>>) {
    when (state) {
      is State.Success -> {
        favoritesSet.addAll(state.data)
        val result = favoritesSet.toList().sortByFavoriteDate()
        _favorites.postValue(result)
      }
      is State.Error -> _error.postValue(state.message)
    }
  }

  private fun onJobsComplete(thr: Throwable?) {
    if (thr != null) {
      _error.postValue(R.string.msg_user_unknown_error)
      Log.e(javaClass.name, "onJobsComplete: ", thr)
    }
    _loading.postValue(false)
    favoritesSet.clear()
  }

  private fun List<Entity>.sortByFavoriteDate() = sortedWith(
    compareByDescending {
      when (it) {
        is Event -> it.favoriteDate
        is Team -> it.favoriteDate
        else -> 0
      }
    }
  )
}