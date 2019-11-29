package id.mustofa.kadesport.ui.leagueevents

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

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

  //  private val model by lazy { LeagueEventViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val leagueId = 4328L

  @Before
  fun setUp() {
    initMocks(this)
  }
//
//  @Test
//  fun `getEvents Past`() = runBlockingTest {
//    val pastEvents = sportDb.eventsPastLeague()
//    `when`(repository.fetchEventsPastLeague(leagueId, true))
//      .thenReturn(Success(pastEvents))
//
//    val observer: Observer<List<Event>> = mock()
//    model.events.observeForever(observer)
//
//    model.loadEvents(leagueId, EventType.PAST)
//    val events = valueOf(model.events)
//    assertEquals(events, pastEvents)
//
//    verify(observer).onChanged(pastEvents)
//  }
//
//  @Test
//  fun `getEvents Next`() = runBlockingTest {
//    val nextEvents = sportDb.eventsNextLeague()
//    `when`(repository.fetchEventsNextLeague(leagueId, true))
//      .thenReturn(Success(nextEvents))
//
//    val observer: Observer<List<Event>> = mock()
//    model.events.observeForever(observer)
//
//    model.loadEvents(leagueId, EventType.NEXT)
//    val events = valueOf(model.events)
//    assertEquals(events, nextEvents)
//
//    verify(observer).onChanged(nextEvents)
//  }
//
//  @Test
//  fun getLoading() {
//    coroutineRule.pauseDispatcher()
//    model.loadEvents(leagueId, EventType.NEXT)
//    val loadingOnFetching = valueOf(model.loading)
//    assertTrue(loadingOnFetching)
//
//    coroutineRule.resumeDispatcher()
//    val loadingAfterFetched = valueOf(model.loading)
//    assertFalse(loadingAfterFetched)
//  }
//
//  @Test
//  fun getMessage() = runBlockingTest {
//    `when`(repository.fetchEventsNextLeague(leagueId, true))
//      .thenReturn(Error(R.string.msg_failed_result))
//
//    val observer: Observer<Int> = mock()
//    model.message.observeForever(observer)
//
//    model.loadEvents(leagueId, EventType.NEXT)
//    val message = valueOf(model.message)
//    assertEquals(message, R.string.msg_failed_result)
//
//    verify(observer).onChanged(R.string.msg_failed_result)
//  }
//
//  @Test
//  fun getNotifier() = runBlockingTest {
//    // notifier posted after 7s delay fetching
//    doAnswer(AnswersWithDelay(7_000 + 100) {
//      Success(sportDb.eventsNextLeague())
//    }).`when`(repository).fetchEventsNextLeague(leagueId, true)
//
//    val observer: Observer<Int> = mock()
//    model.notifier.observeForever(observer)
//
//    model.loadEvents(leagueId, EventType.NEXT)
//    val notifier = valueOf(model.notifier)
//    assertEquals(notifier, R.string.msg_long_wait)
//
//    val events = valueOf(model.events)
//    assertTrue(events.isNotEmpty())
//
//    verify(observer).onChanged(R.string.msg_long_wait)
//  }
}