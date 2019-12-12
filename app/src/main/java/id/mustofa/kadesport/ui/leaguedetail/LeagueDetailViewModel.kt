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
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import id.mustofa.kadesport.data.source.repository.TeamRepository
import kotlinx.coroutines.launch

class LeagueDetailViewModel(
  private val leagueRepo: LeagueRepository,
  private val eventRepo: EventRepository,
  private val teamRepo: TeamRepository
) : ViewModel() {

  private var currentLeagueId = 0L

  private val _leagueState = MutableLiveData<State<League>>()
  val leagueState: LiveData<State<League>> = _leagueState

  private val _teamState = MutableLiveData<State<List<Team>>>()
  val teamState: LiveData<State<List<Team>>> = _teamState

  private val _pastEventState = MutableLiveData<State<List<Event>>>()
  val pastEventState: LiveData<State<List<Event>>> = _pastEventState

  private val _nextEventState = MutableLiveData<State<List<Event>>>()
  val nextEventState: LiveData<State<List<Event>>> = _nextEventState

  fun loadAll(leagueId: Long) {
    if (leagueId == currentLeagueId) return
    currentLeagueId = leagueId
    loadLeague()
    loadTeam()
    loadNextEvent()
    loadPastEvent()
  }

  private fun loadLeague() {
    _leagueState.value = Loading
    viewModelScope.launch {
      val state = leagueRepo.get(currentLeagueId)
      _leagueState.postValue(state)
    }
  }

  private fun loadTeam() {
    _teamState.value = Loading
    viewModelScope.launch {
      val result = teamRepo.getAll(currentLeagueId)
      _teamState.postValue(result)
    }
  }

  private fun loadNextEvent() {
    _nextEventState.value = Loading
    viewModelScope.launch {
      val result = eventRepo.getAll(currentLeagueId, EventType.NEXT)
      _nextEventState.postValue(result)
    }
  }

  private fun loadPastEvent() {
    _pastEventState.value = Loading
    viewModelScope.launch {
      val result = eventRepo.getAll(currentLeagueId, EventType.PAST)
      _pastEventState.postValue(result)
    }
  }

//  private fun shouldReload(leagueId: Long) = listOf(
//    leagueState.value, teamState.value,
//    nextEventState.value, pastEventState.value
//  ).all { it == null } && leagueId != currentId
}