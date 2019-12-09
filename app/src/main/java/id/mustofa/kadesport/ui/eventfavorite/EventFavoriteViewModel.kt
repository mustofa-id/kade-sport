/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.eventfavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.source.repository.EventRepository
import kotlinx.coroutines.launch

class EventFavoriteViewModel(
  private val eventRepo: EventRepository
) : ViewModel() {

  private val _favoriteEvents = MutableLiveData<State<List<Event>>>()
  val favoriteEvents: LiveData<State<List<Event>>> = _favoriteEvents

  fun loadEvents() {
    _favoriteEvents.postValue(Loading)
    viewModelScope.launch {
      val state = eventRepo.getFavorites()
      _favoriteEvents.postValue(state)
    }
  }
}