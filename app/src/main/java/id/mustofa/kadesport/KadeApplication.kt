/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import android.app.Application

class KadeApplication : Application() {

  val repositories: Map<String, Any>
    get() = mapOf(
      "leagueRepository" to ServiceLocator.provideLeagueRepository(this),
      "eventRepository" to ServiceLocator.provideEventRepository(this)
    )
}