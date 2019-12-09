/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import kotlinx.coroutines.launch

class LeagueViewModel(private val leagueRepo: LeagueRepository) : ViewModel() {

  private val _leagues = MutableLiveData<State<List<League>>>()
  val leagues: LiveData<State<List<League>>> = _leagues

  init {
    loadLeagues()
  }

  private fun loadLeagues() {
    _leagues.postValue(Loading)
    viewModelScope.launch {
      val state = leagueRepo.getAll()
      _leagues.postValue(state)
    }
  }
}