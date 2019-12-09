/*
 * Mustofa on 11/29/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.repository

import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.Event

interface EventRepository {

  enum class EventType { PAST, NEXT }

  suspend fun get(id: Long): State<Event>

  suspend fun getAll(
    leagueId: Long,
    type: EventType,
    badge: Boolean = false
  ): State<List<Event>>

  suspend fun search(query: String): State<List<Event>>

  suspend fun getFavorite(eventId: Long): State<Event>

  suspend fun getFavorites(): State<List<Event>>

  suspend fun addFavorite(event: Event): State<Boolean>

  suspend fun removeFavorite(id: Long): State<Boolean>
}