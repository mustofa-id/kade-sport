package id.mustofa.kadesport.ui.leagueeventsearch

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
class LeagueEventSearchViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  //  private val model by lazy { LeagueEventSearchViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val searchQuery = "liver"

  @Before
  fun setUp() {
    initMocks(this)
  }
//
//  @Test
//  fun getEvents() = runBlockingTest {
//    val events = sportDb.searchEvents(searchQuery)
//    `when`(repository.searchEvents(searchQuery))
//      .thenReturn(State.Success(events))
//
//    val observer: Observer<List<Event>> = mock()
//    model.events.observeForever(observer)
//
//    model.search(searchQuery)
//    val eventResults = valueOf(model.events)
//    assertEquals(eventResults, events)
//
//    verify(observer).onChanged(events)
//  }
//
//  @Test
//  fun getLoading() {
//    coroutineRule.pauseDispatcher()
//    model.search(searchQuery)
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
//    val failQuery = "indonesia"
//    `when`(repository.searchEvents(failQuery))
//      .thenReturn(Error(R.string.msg_empty_result))
//
//    val observer: Observer<Int> = mock()
//    model.message.observeForever(observer)
//
//    model.search(failQuery)
//    val message = valueOf(model.message)
//    assertEquals(message, R.string.msg_empty_result)
//
//    verify(observer).onChanged(R.string.msg_empty_result)
//  }
}