/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.local

import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.util.contentValueOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.db.*

class EventDataSource(private val db: ManagedSQLiteOpenHelper) {

  private val tableFavorite = SportDBHelper.TABLE_FAVORITE_EVENT

  suspend fun getAllFavorites() = withContext(Dispatchers.IO) {
    db.use {
      select(tableFavorite)
        .parseList(classParser<Event>())
    }
  }

  suspend fun getFavorite(eventId: Long) = withContext(Dispatchers.IO) {
    db.use {
      select(tableFavorite)
        .whereArgs("id = {eventId}", "eventId" to eventId)
        .parseOpt(classParser<Event>())
    }
  }

  suspend fun addFavorite(event: Event) = withContext(Dispatchers.IO) {
    val values = contentValueOf(event)
    db.use { replace(tableFavorite, *values) }
  }

  suspend fun removeFavorite(eventId: Long) = withContext(Dispatchers.IO) {
    db.use {
      delete(
        tableFavorite,
        "id = {eventId}",
        "eventId" to eventId
      )
    }
  }
}