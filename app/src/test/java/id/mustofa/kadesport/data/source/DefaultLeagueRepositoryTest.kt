package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.Team
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.local.EventDataSource
import id.mustofa.kadesport.data.source.remote.TeamResponse
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

/**
 * @author Habib Mustofa
 * Indonesia on 11/16/19
 */
@ExperimentalCoroutinesApi
class DefaultLeagueRepositoryTest {

  @Mock
  private lateinit var sportDbService: TheSportDbService

  @Mock
  private lateinit var cacheableSportDbService: TheSportDbService

  @Mock
  private lateinit var eventDataSource: EventDataSource

  private val leagueId = 4328L
  private val eventId = 602247L // Man United vs Brighton
  private val leagueDataSource = LeagueDataSource()
  private val sportDb = FakeTheSportDb()
  private val fakeTeamResponse = TeamResponse(listOf(Team(1, "Man United", "/badge.jpg")))

  @Before
  fun setUp() {
    initMocks(this)
  }

  private fun List<Event>.containsBadge() = this
    .map { it.homeBadgePath != null && it.awayBadgePath != null }
    .all { it }
}