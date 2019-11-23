package id.mustofa.kadesport.ui.leagueevents

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.source.LeagueRepository
import id.mustofa.kadesport.ui.common.EventType
import id.mustofa.kadesport.valueOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.internal.stubbing.answers.AnswersWithDelay

/**
 * @author Habib Mustofa
 * Indonesia on 11/16/19
 */
@ExperimentalCoroutinesApi
class LeagueEventViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  private val model by lazy { LeagueEventViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val leagueId = 4328L

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun `getEvents Past`() = runBlockingTest {
    val pastEvents = sportDb.eventsPastLeague()
    `when`(repository.fetchEventsPastLeague(leagueId, true))
      .thenReturn(Success(pastEvents))
    model.loadEvents(leagueId, EventType.PAST)

    val events = valueOf(model.events)
    assertEquals(events, pastEvents)

    verify(repository).fetchEventsPastLeague(leagueId, true)
  }

  @Test
  fun `getEvents Next`() = runBlockingTest {
    val nextEvents = sportDb.eventsNextLeague()
    `when`(repository.fetchEventsNextLeague(leagueId, true))
      .thenReturn(Success(nextEvents))
    model.loadEvents(leagueId, EventType.NEXT)

    val events = valueOf(model.events)
    assertEquals(events, nextEvents)

    verify(repository).fetchEventsNextLeague(leagueId, true)
  }

  @Test
  fun getLoading() {
    coroutineRule.pauseDispatcher()
    model.loadEvents(leagueId, EventType.NEXT)
    val loadingOnFetching = valueOf(model.loading)
    assertTrue(loadingOnFetching)

    coroutineRule.resumeDispatcher()
    val loadingAfterFetched = valueOf(model.loading)
    assertFalse(loadingAfterFetched)
  }

  @Test
  fun getMessage() = runBlockingTest {
    `when`(repository.fetchEventsNextLeague(leagueId, true))
      .thenReturn(Error(R.string.msg_failed_result))
    model.loadEvents(leagueId, EventType.NEXT)

    val message = valueOf(model.message)
    assertEquals(message, R.string.msg_failed_result)

    verify(repository).fetchEventsNextLeague(leagueId, true)
  }

  @Test
  fun getNotifier() = runBlockingTest {
    // notifier posted after 7s delay fetching
    doAnswer(AnswersWithDelay(7_000 + 100) {
      Success(sportDb.eventsNextLeague())
    }).`when`(repository).fetchEventsNextLeague(leagueId, true)
    model.loadEvents(leagueId, EventType.NEXT)

    val notifier = valueOf(model.notifier)
    assertEquals(notifier, R.string.msg_long_wait)

    val events = valueOf(model.events)
    assertTrue(events.isNotEmpty())

    verify(repository).fetchEventsNextLeague(leagueId, true)
  }
}