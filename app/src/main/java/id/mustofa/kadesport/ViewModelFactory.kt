/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import id.mustofa.kadesport.ui.league.LeagueViewModel
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailViewModel
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailViewModel
import id.mustofa.kadesport.ui.leagueeventfavorite.LeagueEventFavoriteViewModel
import id.mustofa.kadesport.ui.leagueevents.LeagueEventViewModel
import id.mustofa.kadesport.ui.leagueeventsearch.LeagueEventSearchViewModel

class ViewModelFactory(repositories: Map<String, Any>) : ViewModelProvider.NewInstanceFactory() {

  private val leagueRepository: LeagueRepository by repositories
  private val eventRepository: EventRepository by repositories

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(model: Class<T>) = with(model) {
    when {
      isAssignableFrom(LeagueDetailViewModel::class.java) ->
        LeagueDetailViewModel(leagueRepository, eventRepository)
      isAssignableFrom(LeagueViewModel::class.java) ->
        LeagueViewModel(leagueRepository)
      isAssignableFrom(LeagueEventViewModel::class.java) ->
        LeagueEventViewModel(eventRepository)
      isAssignableFrom(LeagueEventDetailViewModel::class.java) ->
        LeagueEventDetailViewModel(eventRepository)
      isAssignableFrom(LeagueEventSearchViewModel::class.java) ->
        LeagueEventSearchViewModel(eventRepository)
      isAssignableFrom(LeagueEventFavoriteViewModel::class.java) ->
        LeagueEventFavoriteViewModel(eventRepository)
      else -> throw IllegalArgumentException("Unknown ViewModel class: $model")
    } as T
  }
}