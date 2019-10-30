/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.League

interface LeagueRepository {

  fun fetchAllLeagues() : List<League>
}