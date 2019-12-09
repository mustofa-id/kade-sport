package id.mustofa.kadesport.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.util.createColumns
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.createTable
import org.jetbrains.anko.db.dropTable

class SportDBHelper(context: Context, dbName: String) :
  ManagedSQLiteOpenHelper(context, dbName) {

  override fun onCreate(db: SQLiteDatabase?) {
    val columns = createColumns<Event>()
    db?.createTable(TABLE_FAVORITE_EVENT, true, *columns)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldV: Int, newV: Int) {
    db?.dropTable(TABLE_FAVORITE_EVENT, true)
  }

  companion object {
    const val TABLE_FAVORITE_EVENT = "favorite_events"
  }
}

//"id" to INTEGER + PRIMARY_KEY + UNIQUE,
//"session" to TEXT,
//"homeName" to TEXT,
//"awayName" to TEXT,
//"homeScore" to INTEGER,
//"round" to INTEGER,
//"awayScore" to INTEGER,
//"leagueId" to INTEGER,
//"homeGoalDetails" to TEXT,
//"homeRedCards" to TEXT,
//"homeYellowCards" to TEXT,
//"homeLineupGoalkeeper" to TEXT,
//"homeLineupDefense" to TEXT,
//"homeLineupMidfield" to TEXT,
//"homeLineupForward" to TEXT,
//"homeLineupSubstitutes" to TEXT,
//"homeFormation" to TEXT,
//"awayGoalDetails" to TEXT,
//"awayRedCards" to TEXT,
//"awayYellowCards" to TEXT,
//"awayLineupGoalkeeper" to TEXT,
//"awayLineupDefense" to TEXT,
//"awayLineupMidfield" to TEXT,
//"awayLineupForward" to TEXT,
//"awayLineupSubstitutes" to TEXT,
//"awayFormation" to TEXT,
//"dateEvent" to TEXT,
//"dateEventLocal" to TEXT,
//"date" to TEXT,
//"time" to TEXT,
//"timeLocal" to TEXT,
//"homeTeamId" to INTEGER,
//"awayTeamId" to INTEGER