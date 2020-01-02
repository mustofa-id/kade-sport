package id.mustofa.kadesport.ui.event

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mustofa.kadesport.*
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.source.fake.FakeEventRepository
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * @author Habib Mustofa
 */
@ExperimentalCoroutinesApi
class EventViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private val eventRepo = FakeEventRepository()
  private val model = EventViewModel(eventRepo)
  private val leagueId = 4328L

  @Test
  fun `eventStateTest Success`() = runBlockingTest {
    val sourceState = eventRepo.getAll(leagueId, EventType.NEXT)

    val observer: Observer<State<List<Event>>> = mock()
    model.eventsState.observeForever(observer)

    model.loadEvents(leagueId, EventType.NEXT)
    val state = valueOf(model.eventsState)

    verify(observer).onChanged(state)

    assertTrue(state is Success)
    assertEquals(sourceState.succeed, state.succeed)
  }

  @Test
  fun `eventStateTest Error`() = runBlockingTest {
    eventRepo.shouldReturnError = true
    val sourceState = eventRepo.getAll(leagueId, EventType.PAST)

    val observer: Observer<State<List<Event>>> = mock()
    model.eventsState.observeForever(observer)

    model.loadEvents(leagueId, EventType.PAST)
    val state = valueOf(model.eventsState)

    verify(observer).onChanged(state)

    assertTrue(state is Error)
    assertEquals(sourceState.error, state.error)
  }

  @Test
  fun notifierTest() {
    TODO("Do it latter")
  }
}