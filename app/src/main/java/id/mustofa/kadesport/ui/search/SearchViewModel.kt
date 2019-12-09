/*
 * Mustofa on 11/11/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.source.repository.EventRepository
import kotlinx.coroutines.launch

class SearchViewModel(
  private val eventRepo: EventRepository
) : ViewModel() {

  var currentQuery = ""

  private val _eventState = MutableLiveData<State<List<Event>>>()
  val eventState: LiveData<State<List<Event>>> = _eventState

  init {
    _eventState.value = State.Empty
  }

  fun search(query: String) {
    if (query == currentQuery) return

    currentQuery = query
    _eventState.value = State.Loading
    viewModelScope.launch {
      val state = eventRepo.search(query)
      _eventState.postValue(state)
    }
  }
}