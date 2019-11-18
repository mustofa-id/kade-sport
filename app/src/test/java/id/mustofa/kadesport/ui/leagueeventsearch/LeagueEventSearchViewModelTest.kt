package id.mustofa.kadesport.ui.leagueeventsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.source.FakeTheSportDb
import id.mustofa.kadesport.data.source.LeagueRepository
import id.mustofa.kadesport.valueOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
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
class LeagueEventSearchViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  private val model by lazy { LeagueEventSearchViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val searchQuery = "liver"

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun getEvents() = runBlockingTest {
    val events = sportDb.searchEvents(searchQuery)
    `when`(repository.searchEvents(searchQuery))
      .thenReturn(State.Success(events))
    model.search(searchQuery)

    val eventResults = valueOf(model.events)
    assertEquals(events, eventResults)

    verify(repository).searchEvents(searchQuery)
  }

  @Test
  fun getLoading() {
    coroutineRule.pauseDispatcher()
    model.search(searchQuery)
    val loadingOnFetching = valueOf(model.loading)
    assertTrue(loadingOnFetching)

    coroutineRule.resumeDispatcher()
    val loadingAfterFetched = valueOf(model.loading)
    assertFalse(loadingAfterFetched)
  }

  @Test
  fun getMessage() = runBlockingTest {
    val failQuery = "indonesia"
    `when`(repository.searchEvents(failQuery))
      .thenReturn(Error(R.string.msg_empty_result))

    model.search(failQuery)
    verify(repository).searchEvents(failQuery)

    val message = valueOf(model.message)
    assertEquals(message, R.string.msg_empty_result)
  }
}