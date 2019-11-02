/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch

class LeagueViewModel(private val repository: LeagueRepository) : ViewModel() {

  private val _leagues = MutableLiveData<List<League>>()
  val leagues: LiveData<List<League>> = _leagues

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  init {
    loadLeagues()
  }

  private fun loadLeagues() {
    _loading.postValue(true)
    viewModelScope.launch {
      when (val state = repository.fetchAllLeagues()) {
        is State.Success -> _leagues.postValue(state.data)
      }
      _loading.postValue(false)
    }
  }
}