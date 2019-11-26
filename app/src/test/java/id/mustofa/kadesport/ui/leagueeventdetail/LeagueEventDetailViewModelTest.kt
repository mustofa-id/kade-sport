package id.mustofa.kadesport.ui.leagueeventdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.source.LeagueRepository
import id.mustofa.kadesport.mock
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
class LeagueEventDetailViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  private val model by lazy { LeagueEventDetailViewModel(repository) }
  private val sportDb = FakeTheSportDb()
  private val eventId = 602249L

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun `getEventState Loading`() {
    coroutineRule.pauseDispatcher()
    model.loadEvent(eventId)

    val loadingState = valueOf(model.eventState)
    assertEquals(loadingState, Loading)

    coroutineRule.resumeDispatcher()
    val state = valueOf(model.eventState)
    assertNotEquals(state, Loading)
  }

  @Test
  fun `getEventState Success`() = runBlockingTest {
    val success = Success(sportDb.eventById(eventId))
    `when`(repository.fetchEventById(eventId, true)).thenReturn(success)

    val observer: Observer<State<LeagueEvent>> = mock()
    model.eventState.observeForever(observer)

    model.loadEvent(eventId)
    val state = valueOf(model.eventState)
    assertEquals(state, success)

    verify(observer).onChanged(success)
  }

  @Test
  fun `getEventState Error`() = runBlockingTest {
    val errorResult = Error(0)
    `when`(repository.fetchEventById(1, true)).thenReturn(errorResult)
    `when`(repository.getFavoriteEventById(1)).thenReturn(errorResult)

    val observer: Observer<State<LeagueEvent>> = mock()
    model.eventState.observeForever(observer)

    model.loadEvent(1)
    val state = valueOf(model.eventState)
    assertTrue(state is Error)

    verify(observer).onChanged(errorResult)
  }

  @Test
  fun getFavoriteMessage() = runBlockingTest {
    val event = sportDb.eventById(eventId)

    val observer: Observer<Int> = mock()
    model.favoriteMessage.observeForever(observer)

    // toggling before event loaded with show wait message
    model.toggleFavorite()
    val waitMsg = valueOf(model.favoriteMessage)
    assertEquals(waitMsg, R.string.msg_please_wait)

    verify(observer).onChanged(R.string.msg_please_wait)

    // load event
    coroutineRule.pauseDispatcher()
    `when`(repository.fetchEventById(eventId, true))
      .thenReturn(Success(event))
    model.loadEvent(eventId)
    coroutineRule.resumeDispatcher()

    // toggling after event loaded and event is not in favorite
    `when`(repository.addEventToFavorite(event))
      .thenReturn(Success(true))
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Success(event)) // favorite exist
    model.toggleFavorite()

    val addedMsg = valueOf(model.favoriteMessage)
    assertEquals(addedMsg, R.string.msg_ok_add_fav)

    verify(observer).onChanged(R.string.msg_ok_add_fav)

    // toggling when event is in favorite
    `when`(repository.removeEventFromFavorite(eventId))
      .thenReturn(Success(true))
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Error(R.string.msg_empty_result)) // no favorite
    model.toggleFavorite()
    val removedMsg = valueOf(model.favoriteMessage)
    assertEquals(removedMsg, R.string.msg_ok_remove_fav)

    verify(observer).onChanged(R.string.msg_ok_remove_fav)
  }

  @Test
  fun `getFavoriteIcon InFavorite`() = runBlockingTest {
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Success(sportDb.eventById(eventId)))

    val observer: Observer<Int> = mock()
    model.favoriteIcon.observeForever(observer)

    model.loadEvent(eventId)
    val icon = valueOf(model.favoriteIcon)
    assertEquals(icon, R.drawable.ic_favorite_added)

    verify(observer).onChanged(R.drawable.ic_favorite_added)
  }

  @Test
  fun `getFavoriteIcon NotInFavorite`() = runBlockingTest {
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Error(R.string.msg_empty_result))

    val observer: Observer<Int> = mock()
    model.favoriteIcon.observeForever(observer)

    model.loadEvent(eventId)
    val icon = valueOf(model.favoriteIcon)
    assertEquals(icon, R.drawable.ic_favorite_removed)

    verify(observer).onChanged(R.drawable.ic_favorite_removed)
  }
}