package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.Team
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.local.EventDataSource
import id.mustofa.kadesport.data.source.remote.LeagueEventResponse
import id.mustofa.kadesport.data.source.remote.LeagueResponse
import id.mustofa.kadesport.data.source.remote.TeamResponse
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Response

/**
 * @author Habib Mustofa
 * Indonesia on 11/16/19
 */
@ExperimentalCoroutinesApi
class DefaultLeagueRepositoryTest {

  @Mock
  private lateinit var sportDbService: TheSportDbService

  @Mock
  private lateinit var cacheableSportDbService: TheSportDbService

  @Mock
  private lateinit var eventDataSource: EventDataSource

  private val leagueId = 4328L
  private val eventId = 602247L // Man United vs Brighton
  private val leagueDataSource = LeagueDataSource()
  private val sportDb = FakeTheSportDb()
  private val fakeTeamResponse = TeamResponse(listOf(Team(1, "Man United", "/badge.jpg")))

  private val repository by lazy {
    DefaultLeagueRepository(
      leagueDataSource,
      cacheableSportDbService,
      sportDbService,
      eventDataSource
    )
  }

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun fetchAllLeagues() = runBlockingTest {
    val leagues = repository.fetchAllLeagues()
    assertTrue(leagues is Success)

    val data = (leagues as Success).data
    assertEquals(data, leagueDataSource.getLeagues())
  }

  @Test
  fun fetchLeagueById() = runBlockingTest {
    val result = sportDb.lookupLeague(leagueId)
    val response = LeagueResponse(listOf(result))
    `when`(cacheableSportDbService.lookupLeague(leagueId))
      .thenReturn(Response.success(response))

    val league = repository.fetchLeagueById(leagueId)
    assertTrue(league is Success)

    val data = (league as Success).data
    assertEquals(data, sportDb.lookupLeague(leagueId))

    verify(cacheableSportDbService).lookupLeague(leagueId)
  }

  @Test
  fun fetchEventsNextLeague() = runBlockingTest {
    val results = sportDb.eventsNextLeague()
    val response = LeagueEventResponse(results)
    `when`(sportDbService.eventsNextLeague(leagueId))
      .thenReturn(Response.success(response))

    val nextEventsState = repository.fetchEventsNextLeague(leagueId)
    assertTrue(nextEventsState is Success)

    val nextEvents = (nextEventsState as Success).data
    assertEquals(nextEvents, results)

    `when`(cacheableSportDbService.lookupTeam(any(Long::class.java)))
      .thenReturn(Response.success(fakeTeamResponse))

    val nextEventsWithBadgeState = repository.fetchEventsNextLeague(leagueId, true)
    assertTrue(nextEventsWithBadgeState is Success)

    val nextEventsIsContainBadge = (nextEventsWithBadgeState as Success).data.containsBadge()
    assertTrue(nextEventsIsContainBadge)

    verify(sportDbService, times(2)).eventsNextLeague(leagueId)
    verify(cacheableSportDbService, times(results.size * 2)) // 2 teams (home & away)
      .lookupTeam(any(Long::class.java))
  }

  @Test
  fun fetchEventsPastLeague() = runBlockingTest {
    val results = sportDb.eventsPastLeague()
    val response = LeagueEventResponse(results)
    `when`(sportDbService.eventsPastLeague(leagueId))
      .thenReturn(Response.success(response))

    val pastEventsState = repository.fetchEventsPastLeague(leagueId)
    assertTrue(pastEventsState is Success)

    val pastEvents = (pastEventsState as Success).data
    assertEquals(pastEvents, results)

    `when`(cacheableSportDbService.lookupTeam(any(Long::class.java)))
      .thenReturn(Response.success(fakeTeamResponse))

    val pastEventsWithBadgeState = repository.fetchEventsPastLeague(leagueId, true)
    assertTrue(pastEventsWithBadgeState is Success)

    val pastEventsIsContainBadge = (pastEventsWithBadgeState as Success).data.containsBadge()
    assertTrue(pastEventsIsContainBadge)

    verify(sportDbService, times(2)).eventsPastLeague(leagueId)
    verify(cacheableSportDbService, times(results.size * 2)).lookupTeam(any(Long::class.java))
  }

  @Test
  fun fetchEventById() = runBlockingTest {
    val result = sportDb.eventById(eventId)
    val response = LeagueEventResponse(listOf(result))
    `when`(sportDbService.lookupEvent(eventId))
      .thenReturn(Response.success(response))

    val eventState = repository.fetchEventById(eventId)
    assertTrue(eventState is Success)

    val event = (eventState as Success).data
    assertEquals(event, result)

    `when`(cacheableSportDbService.lookupTeam(any(Long::class.java)))
      .thenReturn(Response.success(fakeTeamResponse))

    val eventStateWithBadge = repository.fetchEventById(eventId, badge = true)
    assertTrue(eventStateWithBadge is Success)

    val eventWithBadge = (eventStateWithBadge as Success).data
    assertTrue(eventWithBadge.let {
      !it.homeBadgePath.isNullOrBlank() &&
          !it.awayBadgePath.isNullOrBlank()
    })

    verify(sportDbService, times(2)).lookupEvent(eventId)
    verify(cacheableSportDbService, times(2)).lookupTeam(any(Long::class.java))
  }

  @Test
  fun searchEvents() = runBlockingTest {
    val query = "united"
    val results = sportDb.searchEvents(query)
    val response = LeagueEventResponse(results)
    `when`(sportDbService.searchEvents(query))
      .thenReturn(Response.success(response))

    `when`(cacheableSportDbService.lookupTeam(any(Long::class.java)))
      .thenReturn(Response.success(fakeTeamResponse))

    val eventsState = repository.searchEvents(query)
    assertTrue(eventsState is Success)

    val events = (eventsState as Success).data
    // result will contains badge automatically
    // when DefaultLeagueRepository#searchEvents(query) was invoked
    // because DefaultLeagueRepository#applyBadge(constraint)
    // function directly modify source/original list object
    assertEquals(events, results)

    verify(sportDbService).searchEvents(query)
    verify(cacheableSportDbService, times(results.size * 2)).lookupTeam(any(Long::class.java))
  }

  @Test
  fun getAllFavoriteEvents() = runBlockingTest {
    val result = sportDb.favoriteEvents()
    `when`(eventDataSource.getAllFavorites()).thenReturn(result)

    val favoriteEventsState = repository.getAllFavoriteEvents()
    assertTrue(favoriteEventsState is Success)

    val favoriteEvents = (favoriteEventsState as Success).data
    assertEquals(favoriteEvents, result)

    verify(eventDataSource).getAllFavorites()
  }

  @Test
  fun getFavoriteEventById() = runBlockingTest {
    val result = sportDb.favoriteEvents().first { it.id == eventId }
    `when`(eventDataSource.getFavorite(eventId)).thenReturn(result)

    val favoriteEventState = repository.getFavoriteEventById(eventId)
    assertTrue(favoriteEventState is Success)

    val favoriteEvent = (favoriteEventState as Success).data
    assertEquals(favoriteEvent, result)

    verify(eventDataSource).getFavorite(eventId)
  }

  @Test
  fun addEventToFavorite() = runBlockingTest {
    val eventToAdd = sportDb.eventsPastLeague().first { it.id == eventId }
    `when`(eventDataSource.addFavorite(eventToAdd)).thenReturn(eventId)

    val addState = repository.addEventToFavorite(eventToAdd)
    assertTrue(addState is Success)

    val added = (addState as Success).data
    assertTrue(added)

    verify(eventDataSource).addFavorite(eventToAdd)
  }

  @Test
  fun removeEventFromFavorite() = runBlockingTest {
    `when`(eventDataSource.removeFavorite(eventId)).thenReturn(1)

    val removeState = repository.removeEventFromFavorite(eventId)
    assertTrue(removeState is Success)

    val removed = (removeState as Success).data
    assertTrue(removed)

    verify(eventDataSource).removeFavorite(eventId)
  }

  private fun List<LeagueEvent>.containsBadge() = this
    .map { it.homeBadgePath != null && it.awayBadgePath != null }
    .all { it }
}