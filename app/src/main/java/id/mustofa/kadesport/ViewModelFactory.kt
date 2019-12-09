/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import id.mustofa.kadesport.ui.event.EventViewModel
import id.mustofa.kadesport.ui.eventdetail.EventDetailViewModel
import id.mustofa.kadesport.ui.eventfavorite.EventFavoriteViewModel
import id.mustofa.kadesport.ui.league.LeagueViewModel
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailViewModel
import id.mustofa.kadesport.ui.search.SearchViewModel

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
      isAssignableFrom(EventViewModel::class.java) ->
        EventViewModel(eventRepository)
      isAssignableFrom(EventDetailViewModel::class.java) ->
        EventDetailViewModel(eventRepository)
      isAssignableFrom(SearchViewModel::class.java) ->
        SearchViewModel(eventRepository)
      isAssignableFrom(EventFavoriteViewModel::class.java) ->
        EventFavoriteViewModel(eventRepository)
      else -> throw IllegalArgumentException("Unknown ViewModel class: $model")
    } as T
  }
}