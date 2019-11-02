/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import com.google.gson.GsonBuilder
import id.mustofa.kadesport.data.source.DefaultLeagueRepository
import id.mustofa.kadesport.data.source.LeagueRepository
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

  private var leagueRepository: LeagueRepository? = null

  fun provideLeagueRepository(): LeagueRepository {
    synchronized(this) {
      return leagueRepository ?: leagueRepository ?: createLeagueRepository()
    }
  }

  private fun createLeagueRepository() = DefaultLeagueRepository(
    leagueDataSource = LeagueDataSource(),
    theSportDbService = createTheSportDbService()
  )

  private fun createTheSportDbService(): TheSportDbService {
    val baseUrl = "https://www.thesportsdb.com/api/v1/json/1/"
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .build()
      .create(TheSportDbService::class.java)
  }
}
