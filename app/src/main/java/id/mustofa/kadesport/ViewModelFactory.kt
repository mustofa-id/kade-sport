/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mustofa.kadesport.data.source.LeagueRepository
import id.mustofa.kadesport.ui.league.LeagueViewModel
import id.mustofa.kadesport.ui.leaguedetail.LeagueDetailViewModel
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailViewModel
import id.mustofa.kadesport.ui.leagueeventfavorite.LeagueEventFavoriteViewModel
import id.mustofa.kadesport.ui.leagueevents.LeagueEventViewModel
import id.mustofa.kadesport.ui.leagueeventsearch.LeagueEventSearchViewModel

class ViewModelFactory(
  private val leagueRepository: LeagueRepository
) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(model: Class<T>) = with(model) {
    when {
      isAssignableFrom(LeagueDetailViewModel::class.java) ->
        LeagueDetailViewModel(leagueRepository)
      isAssignableFrom(LeagueViewModel::class.java) ->
        LeagueViewModel(leagueRepository)
      isAssignableFrom(LeagueEventViewModel::class.java) ->
        LeagueEventViewModel(leagueRepository)
      isAssignableFrom(LeagueEventDetailViewModel::class.java) ->
        LeagueEventDetailViewModel(leagueRepository)
      isAssignableFrom(LeagueEventSearchViewModel::class.java) ->
        LeagueEventSearchViewModel(leagueRepository)
      isAssignableFrom(LeagueEventFavoriteViewModel::class.java) ->
        LeagueEventFavoriteViewModel(leagueRepository)
      else -> throw IllegalArgumentException("Unknown ViewModel class: $model")
    } as T
  }
}