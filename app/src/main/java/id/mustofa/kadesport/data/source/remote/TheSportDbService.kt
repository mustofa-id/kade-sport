/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportDbService {

  @GET("lookupleague.php")
  suspend fun lookupLeague(@Query("id") id: Long): Response<LeagueResponse>
}