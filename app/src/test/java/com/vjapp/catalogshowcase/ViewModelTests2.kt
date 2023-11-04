package com.example.examplevoidapp

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vjapp.catalogshowcase.base.BaseKoinTest
import com.vjapp.catalogshowcase.di.configureTestAppComponent
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.ResourceState
import com.vjapp.catalogshowcase.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.get
import java.net.HttpURLConnection

/**
 * Advanced ViewModel Test
 * This Test executes test directly on ViewModel using coroutines, while latest internet examples
 * fails doing that, limitig the test on only viewmodel code (see VieModelTests 1 examples)
 */
@RunWith(JUnit4::class)
class ViewModelTests2 : BaseKoinTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Before
    fun start() {

        MockKAnnotations.init(this)

        startKoin {
            modules(configureTestAppComponent(getMockWebServerUrl()))
        }

    }

    @MockK
    lateinit var mMainViewModel: MainViewModel
    @MockK
    lateinit var mDetailViewModel: DetailViewModel

    //val mMockWebServer: MockWebServer by inject()

    @ExperimentalCoroutinesApi
    @Test
    fun getCatalogTest() {
        val mGetCatalogUseCase: GetCatalogUseCase = get()

        mMainViewModel = MainViewModel(mGetCatalogUseCase, coroutineTestRule.dispatcher)

        mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_OK)

        //metodo alternativo che fa il mokk della GetCataloGUseCase, ma testa solo il codice del VieModel
        //val sampleResponse = getJson("catalog_response.json")
        //val jsonObj = Gson().fromJson(sampleResponse, CatalogEntity::class.java)
        //coEvery { mgetCatalogUseCase.execute() } returns jsonObj

        runTest {
            mMainViewModel.getCatalog(SearchTypes.SEARCHRESULT)

            mMainViewModel.getCatalogLiveData.getOrAwaitValue {}
            while (mMainViewModel.getCatalogLiveData.value?.first?.status == ResourceState.LOADING) {
                mMainViewModel.getCatalogLiveData.getOrAwaitValue {}
            }
        }

        println("3->" + mMainViewModel.getCatalogLiveData.value?.first?.status?.toString())

        val value = mMainViewModel.getCatalogLiveData.value
        assertThat(value?.first?.status, `is`(ResourceState.SUCCESS))
        assertThat(value?.first?.data!!.catalogList.size, `is`(40))
        assertThat(value.first.data!!.catalogList[0].cod10.length, greaterThan(0))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCatalogTest_error() {
        val mGetCatalogUseCase: GetCatalogUseCase =get()

        mMainViewModel = MainViewModel(mGetCatalogUseCase, coroutineTestRule.dispatcher)

        mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_INTERNAL_ERROR)

        //metodo alternativo che fa il mokk della GetCataloGUseCase, ma testa solo il codice del VieModel
        //val sampleResponse = getJson("catalog_response.json")
        //val jsonObj = Gson().fromJson(sampleResponse, CatalogEntity::class.java)
        //coEvery { mgetCatalogUseCase.execute() } returns jsonObj

        runTest {
            mMainViewModel.getCatalog(SearchTypes.SEARCHRESULT)
            mMainViewModel.getCatalogLiveData.getOrAwaitValue(intialCountDown=2) {}
        }

        System.out.println("3->" + mMainViewModel.getCatalogLiveData.value?.first?.status?.toString())
        SystemClock.sleep(2000)
        val value = mMainViewModel.getCatalogLiveData.value
        assertThat(value?.first?.status, `is`(ResourceState.ERROR))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getProductTest() {
        //val mgetProductUseCase: GetProductUseCase = get()
        mDetailViewModel = DetailViewModel(get(), coroutineTestRule.dispatcher)

        //val sampleResponse = getJson("product_response.json")
        //var jsonObj = Gson().fromJson(sampleResponse, ProductEntity::class.java)
        //coEvery { mgetProductUseCase.execute() } returns jsonObj
        mockNetworkResponseWithFileContent("product_response.json", HttpURLConnection.HTTP_OK)

        runTest {
            mDetailViewModel.getProduct()
            mDetailViewModel.getProductLiveDataState().getOrAwaitValue(intialCountDown=2) {} //<<-- look here!!! to wait for success!!
        }
        println("3->" + mDetailViewModel.getProductLiveDataState().value?.status?.toString())
        val value = mDetailViewModel.getProductLiveDataState().value
        //works but i don't like that way: assert(value?.status == ResourceState.SUCCESS)
        assertThat(value?.status, `is`(ResourceState.SUCCESS))
    }


    //runBlockingTest it's better then runBlocking for testing purpose because it skips delays and time consuming operation
}
