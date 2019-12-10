/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import id.mustofa.kadesport.data.source.repository.TeamRepository
import id.mustofa.kadesport.ui.event.EventViewModel
import id.mustofa.kadesport.ui.eventdetail.EventDetailViewModel
import id.mustofa.kadesport.ui.eventfavorite.EventFavoriteViewModel
import id.mustofa.kadesport.ui.league.LeagueViewModel
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailViewModel
import id.mustofa.kadesport.ui.search.SearchViewModel
import id.mustofa.kadesport.ui.team.TeamViewModel

class ViewModelFactory(repositories: Map<String, Any>) : ViewModelProvider.NewInstanceFactory() {

  private val leagueRepository: LeagueRepository by repositories
  private val eventRepository: EventRepository by repositories
  private val teamRepository: TeamRepository by repositories

  private val models = mapOf(
    LeagueDetailViewModel::class.java to LeagueDetailViewModel(
      leagueRepository, eventRepository, teamRepository
    ),
    LeagueViewModel::class.java to LeagueViewModel(leagueRepository),
    EventViewModel::class.java to EventViewModel(eventRepository),
    EventDetailViewModel::class.java to EventDetailViewModel(eventRepository),
    SearchViewModel::class.java to SearchViewModel(eventRepository, teamRepository),
    EventFavoriteViewModel::class.java to EventFavoriteViewModel(eventRepository),
    TeamViewModel::class.java to TeamViewModel(teamRepository)
  )

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(model: Class<T>): T {
    return models[model] as T? ?: throw IllegalArgumentException("Unknown ViewModel class: $model")
  }
}