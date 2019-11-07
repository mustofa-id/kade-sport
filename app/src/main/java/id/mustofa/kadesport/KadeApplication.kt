/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import android.app.Application
import id.mustofa.kadesport.data.source.LeagueRepository

class KadeApplication : Application() {

  val leagueRepository: LeagueRepository
    get() = ServiceLocator.provideLeagueRepository(this)
}