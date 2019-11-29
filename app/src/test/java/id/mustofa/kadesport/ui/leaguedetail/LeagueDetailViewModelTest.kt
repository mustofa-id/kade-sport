package id.mustofa.kadesport.ui.leaguedetail

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
class LeagueDetailViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  //  private val model by lazy { LeagueDetailViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val leagueId = 4328L

  @Before
  fun setUp() {
    initMocks(this)
  }
//
//  @Test
//  fun `getLeagueState Loading`() {
//    coroutineRule.pauseDispatcher()
//    model.loadLeague(leagueId)
//
//    val loadingState = valueOf(model.leagueState)
//    assertEquals(loadingState, Loading)
//
//    coroutineRule.resumeDispatcher()
//    val state = valueOf(model.leagueState)
//    assertNotEquals(state, Loading)
//  }
//
//  @Test
//  fun `getLeagueState Success`() = runBlockingTest {
//    val successResult = Success(sportDb.lookupLeague(leagueId))
//
//    `when`(repository.get(leagueId))
//      .thenReturn(successResult)
//
//    model.loadLeague(leagueId)
//
//    val observer: Observer<State<League>> = mock()
//    model.leagueState.observeForever(observer)
//
//    val state = valueOf(model.leagueState)
//    assertEquals(state, successResult)
//
//    verify(observer).onChanged(successResult)
//  }
//
//  @Test
//  fun `getLeagueState Error NoData`() = runBlockingTest {
//    val errorResultNoData = Error(R.string.msg_empty_result)
//
//    `when`(repository.get(1))
//      .thenReturn(errorResultNoData)
//
//    val observer: Observer<State<League>> = mock()
//    model.leagueState.observeForever(observer)
//
//    model.loadLeague(1)
//    val stateErrorNoData = valueOf(model.leagueState)
//    assertEquals(stateErrorNoData, errorResultNoData)
//
//    verify(observer).onChanged(errorResultNoData)
//  }
//
//  @Test
//  fun `getLeagueState Error FetchFailed`() = runBlockingTest {
//    val errorResultFetchFailed = Error(R.string.msg_failed_result)
//
//    `when`(repository.get(leagueId))
//      .thenReturn(errorResultFetchFailed)
//
//    val observer: Observer<State<League>> = mock()
//    model.leagueState.observeForever(observer)
//
//    model.loadLeague(leagueId)
//    val stateErrorFetchFailed = valueOf(model.leagueState)
//    assertEquals(stateErrorFetchFailed, errorResultFetchFailed)
//
//    verify(observer).onChanged(errorResultFetchFailed)
//  }
//
//  @Test
//  fun getPastEvents() = runBlockingTest {
//    val pastEvents = sportDb.eventsPastLeague()
//
//    `when`(repository.fetchEventsPastLeague(leagueId))
//      .thenReturn(Success(pastEvents))
//
//    val observer: Observer<List<Event>> = mock()
//    model.pastEvents.observeForever(observer)
//
//    model.loadPastEvent(leagueId)
//    val events = valueOf(model.pastEvents)
//    assertEquals(events, pastEvents)
//
//    verify(observer).onChanged(pastEvents)
//  }
//
//  @Test
//  fun getNextEvents() = runBlockingTest {
//    val nextEvents = sportDb.eventsNextLeague()
//
//    `when`(repository.fetchEventsNextLeague(leagueId))
//      .thenReturn(Success(nextEvents))
//
//    val observer: Observer<List<Event>> = mock()
//    model.nextEvents.observeForever(observer)
//
//    model.loadNextEvent(leagueId)
//    val events = valueOf(model.nextEvents)
//    assertEquals(events, nextEvents)
//
//    verify(observer).onChanged(nextEvents)
//  }
//
//  @Test
//  fun `getPastEvent Error`() = runBlockingTest {
//    `when`(repository.fetchEventsPastLeague(leagueId))
//      .thenReturn(Error(R.string.msg_empty_result))
//
//    val observer: Observer<Int> = mock()
//    model.pastEventError.observeForever(observer)
//
//    model.loadPastEvent(leagueId)
//    val errorMsg = valueOf(model.pastEventError)
//    assertEquals(errorMsg, R.string.msg_empty_result)
//
//    verify(observer).onChanged(R.string.msg_empty_result)
//  }
//
//  @Test
//  fun `getNextEvent Error`() = runBlockingTest {
//    `when`(repository.fetchEventsNextLeague(leagueId))
//      .thenReturn(Error(R.string.msg_empty_result))
//
//    val observer: Observer<Int> = mock()
//    model.nextEventError.observeForever(observer)
//
//    model.loadNextEvent(leagueId)
//    val errorMsg = valueOf(model.nextEventError)
//    assertEquals(errorMsg, R.string.msg_empty_result)
//
//    verify(observer).onChanged(R.string.msg_empty_result)
//  }
}