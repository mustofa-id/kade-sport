/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch

class LeagueEventDetailViewModel(
  private val repository: LeagueRepository
) : ViewModel() {

  private val _eventState = MutableLiveData<State<LeagueEvent>>()
  val eventState: LiveData<State<LeagueEvent>> = _eventState

  fun loadEvent(eventId: Long) {
    if (_eventState.value != null) return
    _eventState.postValue(Loading)
    viewModelScope.launch {
      val result = repository.fetchEventById(eventId, true)
      _eventState.postValue(result)
    }
  }
}