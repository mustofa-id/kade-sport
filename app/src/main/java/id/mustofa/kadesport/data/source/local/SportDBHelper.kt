package id.mustofa.kadesport.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.util.createColumns
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.createTable
import org.jetbrains.anko.db.dropTable

class SportDBHelper(context: Context, dbName: String) :
  ManagedSQLiteOpenHelper(context, dbName) {

  override fun onCreate(db: SQLiteDatabase?) {
    val eventColumns = createColumns<Event>()
    db?.createTable(TABLE_FAVORITE_EVENT, true, *eventColumns)

    val teamColumns = createColumns<Team>()
    db?.createTable(TABLE_FAVORITE_TEAM, true, *teamColumns)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldV: Int, newV: Int) {
    db?.dropTable(TABLE_FAVORITE_EVENT, true)
    db?.dropTable(TABLE_FAVORITE_TEAM, true)
  }

  companion object {
    const val TABLE_FAVORITE_EVENT = "favorite_events"
    const val TABLE_FAVORITE_TEAM = "favorite_team"
  }
}
