package id.mustofa.kadesport.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mustofa.kadesport.*
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.data.source.fake.FakeEventRepository
import id.mustofa.kadesport.data.source.fake.FakeTeamRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * @author Habib Mustofa
 * Indonesia on 1/2/20
 */
@ExperimentalCoroutinesApi
class SearchViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private val eventRepo = FakeEventRepository()
  private val teamRepo = FakeTeamRepository()
  private val model = SearchViewModel(eventRepo, teamRepo)
  private val query = "man"

  @Test
  fun searchTest() = runBlockingTest {
    val eventSource = eventRepo.search(query).succeed
    val teamSource = teamRepo.search(query).succeed
    val resultSource = eventSource + teamSource

    val observer: Observer<List<Entity>> = mock()
    model.result.observeForever(observer)
    model.search(query)
    val searchResult = valueOf(model.result)
    verify(observer).onChanged(searchResult)

    assertEquals(resultSource.size, searchResult.size)
    assertEquals(resultSource, searchResult)
  }

  @Test
  fun loadingTest() {
    coroutineRule.pauseDispatcher()

    val observer: Observer<Boolean> = mock()
    model.loading.observeForever(observer)
    model.search(query)

    val loadingBefore = valueOf(model.loading)
    verify(observer).onChanged(loadingBefore)
    assertTrue(loadingBefore)

    coroutineRule.resumeDispatcher()

    val loadingAfter = valueOf(model.loading)
    verify(observer).onChanged(loadingAfter)
    assertFalse(loadingAfter)
  }

  @Test
  fun errorTest() {
    eventRepo.shouldReturnError = true

    val observer: Observer<Int> = mock()
    model.error.observeForever(observer)
    model.search(query)

    val error = valueOf(model.error)
    verify(observer).onChanged(error)

    assertEquals(R.string.msg_response_error, error)
  }
}