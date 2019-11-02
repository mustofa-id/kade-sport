/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch

class LeagueDetailViewModel(
  private val repository: LeagueRepository
) : ViewModel() {

  private val _leagueState = MutableLiveData<State<League>>()
  val leagueState: LiveData<State<League>> = _leagueState

  fun loadLeague(id: Long) {
    _leagueState.postValue(Loading)
    viewModelScope.launch {
      val res = repository.fetchLeagueById(id)
      _leagueState.postValue(res)
    }
  }
}