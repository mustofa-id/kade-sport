package id.mustofa.kadesport.ui.leagueeventdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State.*
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
import org.mockito.Mockito.*
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

    model.loadEvent(eventId)
    verify(repository).fetchEventById(eventId, true)

    val state = valueOf(model.eventState)
    assertEquals(state, success)
  }

  @Test
  fun `getEventState Error`() = runBlockingTest {
    `when`(repository.fetchEventById(1, true)).thenReturn(Error(0))
    `when`(repository.getFavoriteEventById(1)).thenReturn(Error(0))

    model.loadEvent(1)
    verify(repository).fetchEventById(1, true)
    verify(repository).getFavoriteEventById(1)

    val state = valueOf(model.eventState)
    assertTrue(state is Error)
  }

  @Test
  fun getFavoriteMessage() = runBlockingTest {
    val event = sportDb.eventById(eventId)

    // toggling before event loaded with show wait message
    model.toggleFavorite()
    val waitMsg = valueOf(model.favoriteMessage)
    assertEquals(waitMsg, R.string.msg_please_wait)

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

    // toggling when event is in favorite
    `when`(repository.removeEventFromFavorite(eventId))
      .thenReturn(Success(true))
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Error(R.string.msg_empty_result)) // no favorite
    model.toggleFavorite()
    val removedMsg = valueOf(model.favoriteMessage)
    assertEquals(removedMsg, R.string.msg_ok_remove_fav)

    // verify all
    verify(repository).fetchEventById(eventId, true)
    verify(repository).addEventToFavorite(event)
    verify(repository).removeEventFromFavorite(eventId)
    verify(repository, times(3)).getFavoriteEventById(eventId)
  }

  @Test
  fun `getFavoriteIcon InFavorite`() = runBlockingTest {
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Success(sportDb.eventById(eventId)))
    model.loadEvent(eventId)

    val icon = valueOf(model.favoriteIcon)
    assertEquals(icon, R.drawable.ic_favorite_added)
  }

  @Test
  fun `getFavoriteIcon NotInFavorite`() = runBlockingTest {
    `when`(repository.getFavoriteEventById(eventId))
      .thenReturn(Error(R.string.msg_empty_result))
    model.loadEvent(eventId)

    val icon = valueOf(model.favoriteIcon)
    assertEquals(icon, R.drawable.ic_favorite_removed)
  }
}