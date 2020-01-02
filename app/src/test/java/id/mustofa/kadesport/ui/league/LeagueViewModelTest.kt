package id.mustofa.kadesport.ui.league

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mustofa.kadesport.MainCoroutineRule
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.source.fake.FakeLeagueRepository
import id.mustofa.kadesport.mock
import id.mustofa.kadesport.succeed
import id.mustofa.kadesport.valueOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * @author Habib Mustofa
 */
@ExperimentalCoroutinesApi
class LeagueViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private val leagueRepo = FakeLeagueRepository()

  @Test
  fun loadLeaguesTest() {
    val model = LeagueViewModel(leagueRepo)

    val observer: Observer<State<List<League>>> = mock()
    model.leagues.observeForever(observer)

    val leagues = valueOf(model.leagues)
    verify(observer).onChanged(leagues)

    assertTrue(leagues is Success)
    assertTrue(leagues.succeed.isNotEmpty())
  }
}