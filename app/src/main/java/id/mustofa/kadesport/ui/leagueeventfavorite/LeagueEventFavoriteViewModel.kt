/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventfavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch

class LeagueEventFavoriteViewModel(private val repository: LeagueRepository) : ViewModel() {

  private val _favoriteEvents = MutableLiveData<List<LeagueEvent>>()
  val favoriteEvents: LiveData<List<LeagueEvent>> = _favoriteEvents

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  private val _message = MutableLiveData<Int>()
  val message: LiveData<Int> = _message

  fun loadEvents() {
    initValue()
    viewModelScope.launch {
      when (val state = repository.getAllFavoriteEvents()) {
        is State.Success -> _favoriteEvents.postValue(state.data)
        is State.Error -> _message.postValue(state.message)
      }
      _loading.postValue(false)
    }
  }

  private fun initValue() {
    _loading.postValue(true)
    _message.postValue(0)
    _favoriteEvents.postValue(emptyList())
  }
}