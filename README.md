# Kade Sport
Kade Sport is Android apps for lookup football league events using [TheSportDB](https://www.thesportsdb.com/api.php) API.

# Endpoints
- League detail: https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id={leagueId}
- Next match: https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id={leagueId}
- Previous match: https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id={leagueId}
- Match detail: https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id={eventId}
- Match search: https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e={query}

# Unit Tests
* LeagueViewModelTest
  * getLeagues
    * Memastikan nilai `leagues` diproses dari repository dan sesuai dengan data source
    * Memastikan fungsi `fetchAllLeagues()` pada repository terpanggil
  * getLoading
    * Memastikan property `loading` bernilai `true` saat memuat data
    * Memastikan property `loading` bernilai `false` saat data selesai dimuat

* LeagueDetailViewModelTest
  * getLeagueState Loading
    * Memastikan nilai awal property `leagueState` adalah `Loading` saat fungsi `loadLeague(id)` dipanggil
    * Memastikan nilai property `leagueState` tidak sama dengan `Loading` setelah proses `loadLeague(id)`
  * getLeagueState Success
    * Memanggil fungsi `loadLeague(id)`
    * Memastikan nilai property `leagueState` adalah `Success`
    * Memverifikasi fungsi `fetchLeagueById(id)` dari repository terpanggil
  * getLeagueState Error NoData
    * Memanggil fungsi `loadLeague(id)`
    * Memastikan nilai property `leagueState` adalah `Error` dan berisi `message` `No data!`
    * Memverifikasi fungsi `fetchLeagueById(id)` dari repository terpanggil
  * getLeagueState Error FetchFailed
    * Memanggil fungsi `loadLeague(id)`
    * Memastikan nilai property `leagueState` adalah `Error` dan berisi `message` `Failed to fetch data!`
    * Memverifikasi fungsi `fetchLeagueById(id)` dari repository terpanggil
  * getPastEvents
    * Memanggil fungsi `loadPastEvent(id)`
    * Memastikan property `pastEvents` bernilai sesuai dengan data source
    * Memverisfikasi fungsi `fetchEventsPastLeague(id)` dari repository terpanggil
  * getNextEvents
    * Memanggil fungsi `loadNextEvent(id)`
    * Memastikan property `nextEvents` bernilai sesuai dengan data source
    * Memverisfikasi fungsi `fetchEventsNextLeague(id)` dari repository terpanggil
  * getPastEvents Error
    * Memanggil fungsi `loadPastEvent(id)`
    * Memastikan property `pastEventError` bernilai `No data!`
    * Memverisfikasi fungsi `fetchEventsPastLeague(id)` dari repository terpanggil
  * getNextEvents Error
    * Memanggil fungsi `loadNextEvent(id)`
    * Memastikan property `nextEventError` bernilai `No data!`
    * Memverisfikasi fungsi `fetchEventsNextLeague(id)` dari repository terpanggil

* LeagueEventDetailViewModelTest
  * getEventState Loading
  * getEventState Success
  * getEventState Error
  * getFavoriteMessage
  * getFavoriteIcon InFavorite
  * getFavoriteIcon NotInFavorite

* LeagueEventFavoriteViewModelTest
  * getFavoriteEvents
  * getLoading
  * getMessage
  
* LeagueEventViewModelTest
  * getEvents Past
  * getEvents Next
  * getLoading
  * getMessage
  * getNotifier
  
* LeagueEventSearchViewModelTest
  * getEvents
  * getLoading
  * getMessage

* DbHelperTest
  * createColumnsTest
  * createContentValueTest

* DefaultLeagueRepositoryTest
  * fetchAllLeagues
  * fetchLeagueById
  * fetchEventsNextLeague
  * fetchEventsPastLeague
  * fetchEventById
  * searchEvents
  * getAllFavoriteEvents
  * getFavoriteEventById
  * addEventToFavorite
  * removeEventFromFavorite

# Instrumentation Tests
* LeagueFragmentTest
  * loadLeagues
  * checkLeague

* LeagueDetailActivityTest
  * loadLeague

* LeagueEventDetailActivityTest
  * loadEvent
  * toggleFavorite

* LeagueEventFavoriteFragmentTest
  * loadFavorites

* LeagueEventActivityTest
  * loadEvents

* LeagueEventSearchActivityTest
  * searchEvents

* KadeSportTest
  * app
