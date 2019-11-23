package id.mustofa.kadesport.ui.leaguedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.source.LeagueRepository
import id.mustofa.kadesport.valueOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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

  private val model by lazy { LeagueDetailViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val leagueId = 4328L

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun `getLeagueState Loading`() {
    coroutineRule.pauseDispatcher()
    model.loadLeague(leagueId)

    val loadingState = valueOf(model.leagueState)
    assertEquals(loadingState, Loading)

    coroutineRule.resumeDispatcher()
    val state = valueOf(model.leagueState)
    assertNotEquals(state, Loading)
  }

  @Test
  fun `getLeagueState Success`() = runBlockingTest {
    val successResult = Success(sportDb.lookupLeague(leagueId))

    `when`(repository.fetchLeagueById(leagueId))
      .thenReturn(successResult)

    model.loadLeague(leagueId)
    val state = valueOf(model.leagueState)
    assertEquals(state, successResult)

    verify(repository).fetchLeagueById(leagueId)
  }

  @Test
  fun `getLeagueState Error NoData`() = runBlockingTest {
    val errorResultNoData = Error(R.string.msg_empty_result)

    `when`(repository.fetchLeagueById(1))
      .thenReturn(errorResultNoData)

    model.loadLeague(1)
    val stateErrorNoData = valueOf(model.leagueState)
    assertEquals(stateErrorNoData, errorResultNoData)

    verify(repository).fetchLeagueById(1)
  }

  @Test
  fun `getLeagueState Error FetchFailed`() = runBlockingTest {
    val errorResultFetchFailed = Error(R.string.msg_failed_result)

    `when`(repository.fetchLeagueById(leagueId))
      .thenReturn(errorResultFetchFailed)

    model.loadLeague(leagueId)
    val stateErrorFetchFailed = valueOf(model.leagueState)
    assertEquals(stateErrorFetchFailed, errorResultFetchFailed)

    verify(repository).fetchLeagueById(leagueId)
  }

  @Test
  fun getPastEvents() = runBlockingTest {
    val pastEvents = sportDb.eventsPastLeague()

    `when`(repository.fetchEventsPastLeague(leagueId))
      .thenReturn(Success(pastEvents))

    model.loadPastEvent(leagueId)
    val events = valueOf(model.pastEvents)
    assertEquals(events, pastEvents)

    verify(repository).fetchEventsPastLeague(leagueId)
  }

  @Test
  fun getNextEvents() = runBlockingTest {
    val nextEvents = sportDb.eventsNextLeague()

    `when`(repository.fetchEventsNextLeague(leagueId))
      .thenReturn(Success(nextEvents))

    model.loadNextEvent(leagueId)
    val events = valueOf(model.nextEvents)
    assertEquals(events, nextEvents)

    verify(repository).fetchEventsNextLeague(leagueId)
  }

  @Test
  fun `getPastEvent Error`() = runBlockingTest {
    `when`(repository.fetchEventsPastLeague(leagueId))
      .thenReturn(Error(R.string.msg_empty_result))

    model.loadPastEvent(leagueId)
    val errorMsg = valueOf(model.pastEventError)
    assertEquals(errorMsg, R.string.msg_empty_result)

    verify(repository).fetchEventsPastLeague(leagueId)
  }

  @Test
  fun `getNextEvent Error`() = runBlockingTest {
    `when`(repository.fetchEventsNextLeague(leagueId))
      .thenReturn(Error(R.string.msg_empty_result))

    model.loadNextEvent(leagueId)
    val errorMsg = valueOf(model.nextEventError)
    assertEquals(errorMsg, R.string.msg_empty_result)

    verify(repository).fetchEventsNextLeague(leagueId)
  }
}