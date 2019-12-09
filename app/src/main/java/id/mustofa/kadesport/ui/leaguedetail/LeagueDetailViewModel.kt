/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import kotlinx.coroutines.launch

class LeagueDetailViewModel(
  private val leagueRepo: LeagueRepository,
  private val eventRepo: EventRepository
) : ViewModel() {

  private var currentId: Long = 0

  private val _pastEventState = MutableLiveData<State<List<Event>>>()
  val pastEventState: LiveData<State<List<Event>>> = _pastEventState

  private val _nextEventState = MutableLiveData<State<List<Event>>>()
  val nextEventState: LiveData<State<List<Event>>> = _nextEventState

  private val _leagueState = MutableLiveData<State<League>>()
  val leagueState: LiveData<State<League>> = _leagueState

  fun loadLeague(leagueId: Long) {
    if (leagueState.value != null && leagueId == currentId) return
    currentId = leagueId
    _leagueState.value = Loading

    viewModelScope.launch {
      when (val leagueState = leagueRepo.get(leagueId)) {
        is Empty -> _leagueState.postValue(Empty)
        is Error -> {
          _leagueState.postValue(leagueState)
        }
        is Success -> {
          _leagueState.postValue(leagueState)
          loadNextEvent(leagueId)
          loadPastEvent(leagueId)
        }
      }
    }
  }

  private suspend fun loadNextEvent(leagueId: Long) {
    _nextEventState.postValue(Loading)
    val result = eventRepo.getAll(leagueId, EventType.NEXT)
    _nextEventState.postValue(result)
  }

  private suspend fun loadPastEvent(leagueId: Long) {
    _pastEventState.postValue(Loading)
    val result = eventRepo.getAll(leagueId, EventType.PAST)
    _pastEventState.postValue(result)
  }
}