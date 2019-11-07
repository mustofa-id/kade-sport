/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch

class LeagueDetailViewModel(
  private val repository: LeagueRepository
) : ViewModel() {

  private val _leagueState = MutableLiveData<State<League>>()
  val leagueState: LiveData<State<League>> = _leagueState

  private val _pastEvents = MutableLiveData<List<LeagueEvent>>()
  val pastEvents: LiveData<List<LeagueEvent>> = _pastEvents

  private val _nextEvents = MutableLiveData<List<LeagueEvent>>()
  val nextEvents: LiveData<List<LeagueEvent>> = _nextEvents

  private val _pastEventError = MutableLiveData<Int>()
  val pastEventError: LiveData<Int> = _pastEventError

  private val _nextEventError = MutableLiveData<Int>()
  val nextEventError: LiveData<Int> = _nextEventError

  fun loadLeague(id: Long) {
    if (leagueState.value != null) return

    _leagueState.postValue(Loading)
    viewModelScope.launch {
      val res = repository.fetchLeagueById(id)
      _leagueState.postValue(res)

      if (res is Success) {
        loadNextEvent(id)
        loadPastEvent(id)
      }
    }
  }

  private suspend fun loadNextEvent(leagueId: Long) {
    when (val result = repository.fetchEventsNextLeague(leagueId)) {
      is Success -> _nextEvents.postValue(result.data)
      is Error -> _nextEventError.postValue(R.string.msg_failed_result)
    }
  }

  private suspend fun loadPastEvent(leagueId: Long) {
    when (val result = repository.fetchEventsPastLeague(leagueId)) {
      is Success -> _pastEvents.postValue(result.data)
      is Error -> _pastEventError.postValue(R.string.msg_failed_result)
    }
  }

//  private inline fun <T> State<T>.on(
//    error: MutableLiveData<Int>,
//    onSuccess: (T) -> Unit
//  ) {
//    when (this) {
//      is Success -> onSuccess(data)
//      is Error -> error.postValue(R.string.msg_error_events)
//    }
//  }
}