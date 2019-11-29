/*
 * Mustofa on 11/11/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.source.repository.EventRepository
import kotlinx.coroutines.launch

class LeagueEventSearchViewModel(
  private val eventRepo: EventRepository
) : ViewModel() {

  private val _events = MutableLiveData<List<Event>>()
  val events: LiveData<List<Event>> = _events

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  private val _message = MutableLiveData<Int>()
  val message: LiveData<Int> = _message

  fun search(query: String) {
    if (_loading.value == true) return
    initValue()
    viewModelScope.launch {
      when (val state = eventRepo.search(query)) {
        is Success -> _events.postValue(state.data)
        is Error -> _message.postValue(state.message)
      }
      _loading.postValue(false)
    }
  }

  private fun initValue() {
    _message.postValue(0)
    _loading.postValue(true)
    _events.postValue(emptyList())
  }
}