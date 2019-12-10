/*
 * Mustofa on 12/10/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Loading
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.data.source.repository.TeamRepository
import kotlinx.coroutines.launch

class TeamViewModel(private val teamRepo: TeamRepository) : ViewModel() {

  private var currentId = 0L

  private val _teamState = MutableLiveData<State<List<Team>>>()
  val teamState: LiveData<State<List<Team>>> = _teamState

  fun loadTeams(leagueId: Long) {
    if (leagueId == currentId) return

    _teamState.value = Loading
    viewModelScope.launch {
      val state = teamRepo.getAll(leagueId)
      _teamState.postValue(state)
    }
  }
}