package id.mustofa.kadesport.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.ext.currentTimeMillis
import id.mustofa.kadesport.util.createColumns
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.createTable

class SportDBHelper(context: Context, dbName: String) :
  ManagedSQLiteOpenHelper(context, dbName, version = 2) {

  override fun onCreate(db: SQLiteDatabase?) {
    createTables(db)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldV: Int, newV: Int) {
    if (newV > oldV) {
      val teamColumns = createColumns<Team>()
      db?.createTable(TABLE_FAVORITE_TEAM, true, *teamColumns)

      val time = currentTimeMillis()
      db?.execSQL("ALTER TABLE $TABLE_FAVORITE_EVENT ADD COLUMN favoriteDate INTEGER DEFAULT $time")
    }
  }

  private fun createTables(db: SQLiteDatabase?) {
    val eventColumns = createColumns<Event>()
    db?.createTable(TABLE_FAVORITE_EVENT, true, *eventColumns)

    val teamColumns = createColumns<Team>()
    db?.createTable(TABLE_FAVORITE_TEAM, true, *teamColumns)
  }

  companion object {
    const val TABLE_FAVORITE_EVENT = "favorite_events"
    const val TABLE_FAVORITE_TEAM = "favorite_team"
  }
}
