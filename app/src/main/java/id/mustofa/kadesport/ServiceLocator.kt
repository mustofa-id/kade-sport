/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import android.content.Context
import com.google.gson.GsonBuilder
import id.mustofa.kadesport.data.source.DefaultEventRepository
import id.mustofa.kadesport.data.source.DefaultLeagueRepository
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.local.EventDataSource
import id.mustofa.kadesport.data.source.local.SportDBHelper
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {

  @Volatile
  private var leagueRepository: LeagueRepository? = null
  @Volatile
  private var eventRepository: EventRepository? = null

  fun provideLeagueRepository(context: Context) = leagueRepository ?: synchronized(this) {
    leagueRepository ?: DefaultLeagueRepository(
      embeddedSource = LeagueDataSource(),
      remoteSource = createTheSportDbService(context)
    )
  }

  fun provideEventRepository(context: Context) = eventRepository ?: synchronized(this) {
    eventRepository ?: DefaultEventRepository(
      remoteSource = createTheSportDbService(context),
      cacheableRemoteSource = createTheSportDbService(context, true),
      localSource = createEventDataSource(context)
    )
  }

  private fun createTheSportDbService(
    context: Context,
    cacheable: Boolean = false
  ): TheSportDbService {
    val baseUrl = "https://www.thesportsdb.com/api/v1/json/1/"
    val okHttpClient = OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.MINUTES)
      .readTimeout(1, TimeUnit.MINUTES)

    // enable caching for data that changes over
    // a long period of time like League or Team
    if (cacheable) {
      val cacheSize = (10 * 1024 * 1024).toLong()
      val cache = Cache(context.cacheDir, cacheSize)

      val cacheControl = CacheControl.Builder()
        .maxStale(7, TimeUnit.DAYS)
        .build()

      okHttpClient
        .cache(cache)
        .addInterceptor { chain ->
          val request = chain
            .request()
            .newBuilder()
            .cacheControl(cacheControl)
            .build()
          chain.proceed(request)
        }
    }

    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
      .client(okHttpClient.build())
      .build()
      .create(TheSportDbService::class.java)
  }

  private fun createEventDataSource(context: Context): EventDataSource {
    val dbName = context.getString(R.string.db_name)
    val dbHelper = SportDBHelper(context, dbName)
    return EventDataSource(dbHelper)
  }
}
