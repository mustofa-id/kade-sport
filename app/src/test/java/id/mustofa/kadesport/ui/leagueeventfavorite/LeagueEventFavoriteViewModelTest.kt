package id.mustofa.kadesport.ui.leagueeventfavorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
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
class LeagueEventFavoriteViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  private val model by lazy { LeagueEventFavoriteViewModel(repository) }
  private val localDb = FakeTheSportDb()

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun getFavoriteEvents() = runBlockingTest {
    `when`(repository.getAllFavoriteEvents())
      .thenReturn(Success(localDb.favoriteEvents()))
    model.loadEvents()

    val events = valueOf(model.favoriteEvents)
    assertEquals(events, localDb.favoriteEvents())

    verify(repository).getAllFavoriteEvents()
  }

  @Test
  fun getLoading() {
    coroutineRule.pauseDispatcher()
    model.loadEvents()
    val loadingOnFetching = valueOf(model.loading)
    assertTrue(loadingOnFetching)

    coroutineRule.resumeDispatcher()
    val loadingAfterFetched = valueOf(model.loading)
    assertFalse(loadingAfterFetched)
  }

  @Test
  fun getMessage() = runBlockingTest {
    `when`(repository.getAllFavoriteEvents())
      .thenReturn(Error(R.string.msg_failed_result))
    model.loadEvents()

    val msgError = valueOf(model.message)
    assertEquals(msgError, R.string.msg_failed_result)

    `when`(repository.getAllFavoriteEvents())
      .thenReturn(Error(R.string.msg_empty_result))
    model.loadEvents()

    val msgEmpty = valueOf(model.message)
    assertEquals(msgEmpty, R.string.msg_empty_result)

    verify(repository, times(2)).getAllFavoriteEvents()
  }
}