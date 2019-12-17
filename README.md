# Kade Sport
Kade Sport is Android apps for lookup football league events using [TheSportDB](https://www.thesportsdb.com/api.php) API.

# Endpoints
- Detail liga: https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id={idLeague}
- Daftar tim: https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id={idLeague}
- Klasemen pertandingan: https://www.thesportsdb.com/api/v1/json/1/lookuptable.php?l={idLeague}
- Detail tim: https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id={idTeam}
- Daftar pemain: https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id={idTeam}
- Detail pemain: https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id={idPemain}
- Daftar next match:
    https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id={idLeague}
    https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id={idTeam}
- Daftar previous match:
    https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id={idLeague}
    https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id={idTeam}
- Detail pertandingan: https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id={idEvent}
- Pencarian pertandingan: https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e={query}
- Pencarian tim: https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t={query}

# Unit Tests
<details>
<summary>Show unit test case</summary>
<p>

* LeagueViewModelTest
  * getLeagues
    * Memastikan nilai `leagues` diproses dan sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `leagues` LiveData
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
    * Memastikan terjadi perubahan nilai pada `leagueState` LiveData
  * getLeagueState Error NoData
    * Memanggil fungsi `loadLeague(id)`
    * Memastikan nilai property `leagueState` adalah `Error` dan berisi `message` `No data!`
    * Memastikan terjadi perubahan nilai pada `leagueState` LiveData
  * getLeagueState Error FetchFailed
    * Memanggil fungsi `loadLeague(id)`
    * Memastikan nilai property `leagueState` adalah `Error` dan berisi `message` `Failed to fetch data!`
    * Memastikan terjadi perubahan nilai pada `leagueState` LiveData
  * getPastEvents
    * Memanggil fungsi `loadPastEvent(id)`
    * Memastikan property `pastEvents` bernilai sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `pastEvents` LiveData
  * getNextEvents
    * Memanggil fungsi `loadNextEvent(id)`
    * Memastikan property `nextEvents` bernilai sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `nextEvents` LiveData
  * getPastEvents Error
    * Memanggil fungsi `loadPastEvent(id)`
    * Memastikan property `pastEventError` bernilai `No data!`
    * Memastikan terjadi perubahan nilai pada `pastEventError` LiveData
  * getNextEvents Error
    * Memanggil fungsi `loadNextEvent(id)`
    * Memastikan property `nextEventError` bernilai `No data!`
    * Memastikan terjadi perubahan nilai pada `nextEventError` LiveData

* LeagueEventDetailViewModelTest
  * getEventState Loading
    * Memastikan nilai awal property `eventState` adalah `Loading` saat fungsi `loadEvent(id)` dipanggil
    * Memastikan nilai property `eventState` tidak sama dengan `Loading` setelah proses `loadEvent(id)`
  * getEventState Success
    * Memanggil fungsi `loadEvent(id)`
    * Memastikan nilai property `eventState` adalah `Success`
    * Memastikan terjadi perubahan nilai pada `eventState` LiveData
  * getEventState Error
    * Memanggil fungsi `loadEvent(id)`
    * Memastikan nilai dari property `eventState` adalah `Error`
    * Memastikan terjadi perubahan nilai pada `eventState` LiveData
  * getFavoriteMessage
    * Memanggil fungsi `toggleFavorite()`
    * Memastikan nilai `favoriteMessage` adalah `Please wait...` karena data event belum dimuat
    * Memastikan terjadi perubahan nilai pada `favoriteMessage` LiveData
    * Memanggil fungsi `loadEvent(id)`
    * Memanggil fungsi `toggleFavorite()`
    * Memastikan terjadi perubahan nilai pada `favoriteMessage` LiveData
    * Memastikan nilai `favoriteMessage` adalah `Added to favorite`
    * Memanggil fungsi `toggleFavorite()`
    * Memastikan nilai `favoriteMessage` adalah `Removed from favorite`
    * Memastikan terjadi perubahan nilai pada `favoriteMessage` LiveData
  * getFavoriteIcon InFavorite
    * Memanggil fungsi `loadEvent()`
    * Memastikan nilai `favoriteIcon` adalah `R.drawable.ic_favorite_added`
    * Memastikan terjadi perubahan nilai pada `favoriteIcon` LiveData
  * getFavoriteIcon NotInFavorite
    * Memanggil fungsi `loadEvent()`
    * Memastikan nilai `favoriteIcon` adalah `R.drawable.ic_favorite_removed`
    * Memastikan terjadi perubahan nilai pada `favoriteIcon` LiveData

* LeagueEventFavoriteViewModelTest
  * getFavoriteEvents
    * Memanggil fungsi `loadEvents()`
    * Memastikan nilai dari property `favoriteEvents` sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `favoriteEvents` LiveData
  * getLoading
    * Memastikan property `loading` bernilai `true` saat memuat data
    * Memastikan property `loading` bernilai `false` saat data selesai dimuat
  * getMessage
    * Memanggil fungsi `loadEvents()`
    * Memastikan terjadi perubahan nilai pada `message` LiveData
  
* LeagueEventViewModelTest
  * getEvents Past
    * Memanggil fungsi `loadEvents(leagueId, type)`
    * Memastikan nilai propery `events` sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `events` LiveData
  * getEvents Next
    * Memanggil fungsi `loadEvents(leagueId, type)`
    * Memastikan nilai propery `events` sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `events` LiveData
  * getLoading
    * Memastikan property `loading` bernilai `true` saat memuat data
    * Memastikan property `loading` bernilai `false` saat data selesai dimuat
  * getMessage
    * Memanggil fungsi `loadEvents(leagueId, type)`
    * Memastikan nilai property `message` adalah `Failed to fetch data!`
    * Memastikan terjadi perubahan nilai pada `message` LiveData
  * getNotifier
    * Memanggil fungsi `loadEvents(leagueId, type)`
    * Memastikan nilai property `notifier` adalah `R.string.msg_long_wait` setelah proses memuat data selama 7100ms
    * Memastikan terjadi perubahan nilai pada `notifier` LiveData
  
* LeagueEventSearchViewModelTest
  * getEvents
    * Memanggil fungsi `search(query)`
    * Memastikan nilai dari property `events` sesuai dengan data source
    * Memastikan terjadi perubahan nilai pada `events` LiveData
  * getLoading
    * Memastikan property `loading` bernilai `true` saat memuat data
    * Memastikan property `loading` bernilai `false` saat data selesai dimuat
  * getMessage
    * Memanggil fungsi `search(query)`
    * Memastikan nilai property `message` adalah `No data!`
    * Memastikan terjadi perubahan nilai pada `message` LiveData

* DbHelperTest
  * createColumns
    * Memastikan nilai balik fungsi `createColumns<type>()` sama dengan nilai array yang ditulis manual
  * contentValueOf
    * Memastikan nilai balik fungsi `contentValueOf()` sama dengan nilai array yang ditulis manual

* DefaultLeagueRepositoryTest
  * fetchAllLeagues
    * Memastikan fungsi mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
  * fetchLeagueById
    * Memastikan fungsi mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memverifikasi fungsi `lookupLeague()` terpanggil dari data source
  * fetchEventsNextLeague
    * Memastikan fungsi dengan nilai parameter `badge` adalah `false` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memastikan fungsi dengan nilai parameter `badge` adalah `true` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source dan memiliki nilai pada property `homeBadgePath` dan `awayBadgePath`
    * Memverifikasi fungsi `eventsNextLeague(leagueId)` dan `lookupTeam(id)` terpanggil pada data source
  * fetchEventsPastLeague
    * Memastikan fungsi dengan nilai parameter `badge` adalah `false` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memastikan fungsi dengan nilai parameter `badge` adalah `true` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source dan memiliki nilai pada property `homeBadgePath` dan `awayBadgePath`
    * Memverifikasi fungsi `eventsPastLeague(leagueId)` dan `lookupTeam(id)` terpanggil pada data source
  * fetchEventById
    * Memastikan fungsi dengan nilai parameter `badge` adalah `false` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memastikan fungsi dengan nilai parameter `badge` adalah `true` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source dan memiliki nilai pada property `homeBadgePath` dan `awayBadgePath`
    * Memverifikasi fungsi `lookupEvent(id)` dan `lookupTeam(id)` terpanggil pada data source
  * searchEvents
    * Memastikan fungsi dengan parameter `query` mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memverifikasi fungsi `searchEvents(query)` dan `lookupTeam(id)` terpanggil pada data source
  * getAllFavoriteEvents
    * Memastikan fungsi mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memverifikasi fungsi `getAllFavorites()` terpanggil pada data source
  * getFavoriteEventById
    * Memastikan fungsi mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` sama dengan data source
    * Memverifikasi fungsi `getFavorite(id)` terpanggil pada data source
  * addEventToFavorite
    * Memastikan fungsi mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` adalah `true`
    * Memverifikasi fungsi `addFavorite(event)` terpanggil pada data source
  * removeEventFromFavorite
    * Memastikan fungsi mengembalikan objek `Success`
    * Memastikan nilai property `data` pada objek `Success` adalah `true`
    * Memverifikasi fungsi `removeFavorite(id)` terpanggil pada data source
</p>
</details>

# Instrumentation Tests

<details>
<summary>Show instrumentation test case</summary>
<p>

* LeagueFragmentTest
  * loadLeagues
    * Memastikan `RecyclerView` tampil
    * Memastikan jumlah data pada `RecyclverView` sesuai dengan data source
  * checkLeague
    * Memastikan data pada indeks pertama terdapat pada `RecyclerView`

* LeagueDetailActivityTest
  * loadLeague
    * Memastikan gambar badge tampil
    * Memastikan judul liga tampil
    * Memastikan deskripsi lengkap liga tampil pada popup saat diklik
    * Memastikan popup deskripsi lengkap menghilang saat klik OK
    * Memastikan deskripsi liga tampil
    * Melakukan aksi swipe up untuk melihat semua events
    * memastikan next dan past event tampil

* LeagueEventDetailActivityTest
  * loadEvent
    * Memastikan gambar badge tim home tampil
    * Memastikan nama tim home tampil dan dengan teks sesuai data
    * Memastikan score pertandingan tampil dan dengan teks sesuai data
    * Memastikan waktu pertandingan tampil dan sesuai dengan data
    * Memastikan gambar badge tim away tampil
    * Memastikan nama tim away tampil dan dengan teks sesuai data
    * Memastikan nama pemain dan waktu goal home dan away tampil dan sesuai dengan data
    * Memastikan nama pemain dengan kartu kuning tampil dan teks sesuai dengan data
    * Melakukan aksi swipe up
    * Memastikan home dan away team lineup tampil
  * toggleFavorite
    * Memastikan tampil dan memberi aksi klik pada menu icon favorite
    * Memastikan tampil pesan `Added to favorite` atau `Removed from favorite!`

* LeagueEventFavoriteFragmentTest
  * loadFavorites
    * Memastikan `RecyclerView` tampil dan jumlah data sesuai

* LeagueEventActivityTest
  * loadEvents
    * Memastikan `RecyclerView` tampil dan terdapat data sesuai id dan tipe liga

* LeagueEventSearchActivityTest
  * searchEvents
    * Memastikan tampil dan memberi aksi klik pada `SearchView`
    * Memastikan element turunan `EditText` tampil dan mengetik karakter `man` lalu menekan tombol enter atau search
    * Memastikan `RecyclerView` dan data hasil search tampil

* KadeSportTest
  * app
    * Memastikan jumlah tab dan judul pada masing-masing tab sesuai
    * Memastikan `RecyclerView` tampil untuk daftar liga dan memberi aksi klik pada data pertama
    * Memastikan judul liga tampil pada layar detail liga
    * Memastikan deskripsi lengkap liga tampil pada popup saat diklik
    * Memastikan popup deskripsi lengkap menghilang saat klik OK
    * Melakukan aksi swipe up untuk melihat semua events
    * Memastikan tampil dan memberi aksi klik pada past event
    * Memastikan `RecyclerView` events tampil dan terdapat data sesuai id dan tipe liga dan memberi aksi klik pada data pertama
    * Memastikan badge dan title tim home dan away tampil
    * Memastikan tampil dan memberi aksi klik pada menu icon favorite
    * Memastikan tampil pesan `Added to favorite` atau `Removed from favorite!`
    * Memberi aksi tombol back hingga kembali pada layar main
    * Memberi aksi klik pada tab `Favorite event`
    * Memastikan data favorite events tampil dan memberi aksi klik pada data pertama lalu menekan tombol back
    * Atau jika tidak ada data favorite maka memastikan pesan `No data!` tampil
    * Memberi aksi klik pada icon menu search
    * Memastikan element turunan `EditText` tampil dan mengetik karakter `man` lalu menekan tombol enter atau search
    * Memastikan `RecyclerView` dan data hasil search tampil
</p>
</details>

# Warning!

### You must be familiar with image bellow, right?

[![Dicoding plagiarism notice](images/dicoding_plagiarism_warn.jpeg)](https://www.dicoding.com/academies/55/)
