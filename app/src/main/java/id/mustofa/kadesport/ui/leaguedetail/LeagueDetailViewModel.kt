/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import kotlinx.coroutines.launch

class LeagueDetailViewModel(
  private val leagueRepo: LeagueRepository,
  private val eventRepo: EventRepository
) : ViewModel() {

  private val _leagueState = MutableLiveData<State<League>>()
  val leagueState: LiveData<State<League>> = _leagueState

  private val _pastEvents = MutableLiveData<List<Event>>()
  val pastEvents: LiveData<List<Event>> = _pastEvents

  private val _nextEvents = MutableLiveData<List<Event>>()
  val nextEvents: LiveData<List<Event>> = _nextEvents

  private val _pastEventError = MutableLiveData<Int>()
  val pastEventError: LiveData<Int> = _pastEventError

  private val _nextEventError = MutableLiveData<Int>()
  val nextEventError: LiveData<Int> = _nextEventError

  fun loadLeague(id: Long) {
    if (leagueState.value != null) return

    _leagueState.postValue(Loading)
    viewModelScope.launch {
      val res = leagueRepo.get(id)
      _leagueState.postValue(res)

      if (res is Success) {
        loadNextEvent(id)
        loadPastEvent(id)
      }
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  suspend fun loadNextEvent(leagueId: Long) {
    when (val result = eventRepo.getAll(leagueId, EventType.NEXT)) {
      is Success -> _nextEvents.postValue(result.data)
      is Error -> _nextEventError.postValue(result.message)
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  suspend fun loadPastEvent(leagueId: Long) {
    when (val result = eventRepo.getAll(leagueId, EventType.PAST)) {
      is Success -> _pastEvents.postValue(result.data)
      is Error -> _pastEventError.postValue(result.message)
    }
  }
}