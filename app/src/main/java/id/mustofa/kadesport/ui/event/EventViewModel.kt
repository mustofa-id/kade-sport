/*
 * Mustofa on 11/4/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class EventViewModel(
  private val eventRepo: EventRepository
) : ViewModel() {

  private var currentId = 0L

  private val _eventState = MutableLiveData<State<List<Event>>>()
  val eventsState: LiveData<State<List<Event>>> = _eventState

  private val _notifier = MutableLiveData<Boolean>()
  val notifier: LiveData<Boolean> = _notifier

  fun loadEvents(leagueId: Long, type: EventType) {
    if (eventsState.value != null && leagueId == currentId) return

    val notifierTimer = initNotifier()
    _eventState.value = Loading
    viewModelScope.launch {
      val result = eventRepo.getAll(leagueId, type, true)
      _eventState.postValue(result)

      notifierTimer.cancel()
      _notifier.postValue(false)
    }
  }

  // notify user in case long response occurred
  private fun initNotifier() = Timer().schedule(7_000) {
    _notifier.postValue(eventsState.value is Loading)
  }
}