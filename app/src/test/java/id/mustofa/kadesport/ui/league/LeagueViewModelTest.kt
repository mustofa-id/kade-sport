package id.mustofa.kadesport.ui.league

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.repository.LeagueRepository
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
 * Indonesia on 11/15/19
 */
@ExperimentalCoroutinesApi
class LeagueViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @Mock
  private lateinit var repository: LeagueRepository

  private val leaguesSource = LeagueDataSource().getLeagues()
  private val model by lazy { LeagueViewModel(repository) }

  @Before
  fun setUp() {
    initMocks(this)
  }

  @Test
  fun getLeagues() = runBlockingTest {
    `when`(repository.getAll())
      .thenReturn(Success(leaguesSource))

    val observer: Observer<List<League>> = mock()
    model.leagues.observeForever(observer)

    val leagues = valueOf(model.leagues)
    assertEquals(leagues, leaguesSource)

    verify(observer).onChanged(leaguesSource)
  }

  @Test
  fun getLoading() {
    coroutineRule.pauseDispatcher()
    val loadingOnFetching = valueOf(model.loading)
    assertTrue(loadingOnFetching)

    coroutineRule.resumeDispatcher()
    val loadingAfterFetched = valueOf(model.loading)
    assertFalse(loadingAfterFetched)
  }
}