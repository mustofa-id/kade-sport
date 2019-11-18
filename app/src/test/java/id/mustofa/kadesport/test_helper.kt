package id.mustofa.kadesport

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
  }
}

fun <T> valueOf(liveData: LiveData<T>): T {
  val data = arrayOfNulls<Any>(1)
  val latch = CountDownLatch(1)
  val observer = object : Observer<T> {
    override fun onChanged(t: T) {
      data[0] = t
      latch.countDown()
      liveData.removeObserver(this)
    }
  }
  liveData.observeForever(observer)
  latch.await(2, TimeUnit.SECONDS)
  @Suppress("UNCHECKED_CAST") return data[0] as T
}