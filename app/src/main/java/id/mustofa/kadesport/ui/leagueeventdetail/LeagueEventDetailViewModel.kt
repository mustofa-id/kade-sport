/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventdetail

import androidx.lifecycle.*
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch

class LeagueEventDetailViewModel(
  private val repository: LeagueRepository
) : ViewModel() {

  private var event: LeagueEvent? = null
  private val isInFavorite = MutableLiveData<Boolean>()

  private val _eventState = MutableLiveData<State<LeagueEvent>>()
  val eventState: LiveData<State<LeagueEvent>> = _eventState

  private val _favoriteMessage = MutableLiveData<Int>()
  val favoriteMessage: LiveData<Int> = _favoriteMessage

  val favoriteIcon: LiveData<Int> = Transformations.map(isInFavorite) {
    if (it) R.drawable.ic_favorite_added else R.drawable.ic_favorite_removed
  }

  fun loadEvent(eventId: Long) {
    if (_eventState.value != null) return
    _eventState.postValue(Loading)
    viewModelScope.launch {
      // check favorite
      val localEvent = checkFavorite(eventId)

      // fetch from network
      when (val network = repository.fetchEventById(eventId, true)) {
        // if success fill local event & check favorite
        is Success -> {
          event = network.data
          _eventState.postValue(network)
        }
        // if fail get from local database
        is Error -> {
          val result = localEvent?.let {
            event = it
            Success(it)
          } ?: Error(network.message)
          _eventState.postValue(result)
        }
      }
    }
  }

  fun toggleFavorite() {
    viewModelScope.launch {
      event?.let {
        when (val state = favoriteEventState(it)) {
          is Success -> {
            val message = checkFavorite(it.id)
              ?.let { R.string.msg_ok_add_fav }
              ?: R.string.msg_ok_remove_fav
            _favoriteMessage.postValue(message)
          }
          is Error -> _favoriteMessage.postValue(state.message)
        }
      } ?: _favoriteMessage.postValue(R.string.msg_please_wait)
    }
  }

  // -- private --
  private suspend fun checkFavorite(id: Long): LeagueEvent? {
    val local = repository.getFavoriteEventById(id)
    val result = if (local is Success) local.data else null
    isInFavorite.postValue(result != null)
    return result
  }

  private suspend fun favoriteEventState(it: LeagueEvent): State<Boolean> {
    return when (isInFavorite.value) {
      true -> repository.removeEventFromFavorite(it.id)
      else -> repository.addEventToFavorite(it)
    }
  }
}