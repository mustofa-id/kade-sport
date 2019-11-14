/*
 * Mustofa on 11/4/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueevents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.source.LeagueRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class LeagueEventViewModel(private val repository: LeagueRepository) : ViewModel() {

  private val _events = MutableLiveData<List<LeagueEvent>>()
  val events: LiveData<List<LeagueEvent>> = _events

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  private val _message = MutableLiveData<Int>()
  val message: LiveData<Int> = _message

  private val _notifier = MutableLiveData<Int>()
  val notifier: LiveData<Int> = _notifier

  fun loadEvents(leagueId: Long, type: LeagueEventType) {
    if (events.value != null) return
    _loading.postValue(true)
    _message.postValue(0)
    initNotifier()
    viewModelScope.launch {
      when (val result = getEventResponse(leagueId, type)) {
        is Success -> _events.postValue(result.data)
        is Error -> _message.postValue(result.message)
      }
      _loading.postValue(false)
    }
  }

  // notify user in case long response occurred
  private fun initNotifier() {
    Timer().schedule(7_000) {
      if (_loading.value == true) {
        _notifier.postValue(R.string.msg_long_wait)
      }
    }
  }

  private suspend fun getEventResponse(id: Long, type: LeagueEventType) = when (type) {
    LeagueEventType.NEXT -> repository.fetchEventsNextLeague(id, true)
    LeagueEventType.PAST -> repository.fetchEventsPastLeague(id, true)
  }
}