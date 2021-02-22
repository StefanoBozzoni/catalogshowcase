package com.example.examplevoidapp

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vjapp.catalogshowcase.base.BaseKoinTest
import com.vjapp.catalogshowcase.di.configureTestAppComponent
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.ResourceState
import com.vjapp.catalogshowcase.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.greaterThan
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.get
import org.koin.test.inject
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

    val mMockWebServer: MockWebServer by inject()

    @ExperimentalCoroutinesApi
    @Test
    fun GetCatalogTest() {
        val mGetCatalogUseCase: GetCatalogUseCase =get()

        mMainViewModel = MainViewModel(mGetCatalogUseCase, coroutineTestRule.dispatcher)

        mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_OK)

        //metodo alternativo che fa il mokk della GetCataloGUseCase, ma testa solo il codice del VieModel
        //val sampleResponse = getJson("catalog_response.json")
        //val jsonObj = Gson().fromJson(sampleResponse, CatalogEntity::class.java)
        //coEvery { mgetCatalogUseCase.execute() } returns jsonObj

        coroutineTestRule.dispatcher.runBlockingTest() {
            mMainViewModel.getCatalog(SearchTypes.SEARCHRESULT)

            mMainViewModel.getCatalogLiveData.getOrAwaitValue {}
            while (mMainViewModel.getCatalogLiveData.value?.first?.status == ResourceState.LOADING) {
                mMainViewModel.getCatalogLiveData.getOrAwaitValue {}
            }
        }

        System.out.println("3->" + mMainViewModel.getCatalogLiveData.value?.first?.status?.toString())

        val value = mMainViewModel.getCatalogLiveData.value
        Assert.assertThat(value?.first?.status, `is`(ResourceState.SUCCESS))
        Assert.assertThat(value?.first?.data!!.catalogList.size, `is`(40))
        Assert.assertThat(value.first.data!!.catalogList[0].cod10.length, greaterThan(0))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun GetCatalogTest_error() {
        val mGetCatalogUseCase: GetCatalogUseCase =get()

        mMainViewModel = MainViewModel(mGetCatalogUseCase, coroutineTestRule.dispatcher)

        mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_INTERNAL_ERROR)

        //metodo alternativo che fa il mokk della GetCataloGUseCase, ma testa solo il codice del VieModel
        //val sampleResponse = getJson("catalog_response.json")
        //val jsonObj = Gson().fromJson(sampleResponse, CatalogEntity::class.java)
        //coEvery { mgetCatalogUseCase.execute() } returns jsonObj

        coroutineTestRule.dispatcher.runBlockingTest() {
            mMainViewModel.getCatalog(SearchTypes.SEARCHRESULT)

            mMainViewModel.getCatalogLiveData.getOrAwaitValue {}
            while (mMainViewModel.getCatalogLiveData.value?.first?.status == ResourceState.LOADING) {
                mMainViewModel.getCatalogLiveData.getOrAwaitValue {}
            }
        }

        System.out.println("3->" + mMainViewModel.getCatalogLiveData.value?.first?.status?.toString())
        SystemClock.sleep(2000)
        val value = mMainViewModel.getCatalogLiveData.value
        Assert.assertThat(value?.first?.status, `is`(ResourceState.ERROR))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun GetProductTest() {
        val mgetProductUseCase: GetProductUseCase = get()
        mDetailViewModel = DetailViewModel(mgetProductUseCase, coroutineTestRule.dispatcher)

        //val sampleResponse = getJson("product_response.json")
        //var jsonObj = Gson().fromJson(sampleResponse, ProductEntity::class.java)
        //coEvery { mgetProductUseCase.execute() } returns jsonObj
        mockNetworkResponseWithFileContent("product_response.json", HttpURLConnection.HTTP_OK)

        coroutineTestRule.dispatcher.runBlockingTest() {
            mDetailViewModel.getProduct()
            mDetailViewModel.getProductLiveData.getOrAwaitValue() {}
            while (mDetailViewModel.getProductLiveData.value?.status == ResourceState.LOADING) {
                mDetailViewModel.getProductLiveData.getOrAwaitValue {}
            }
        }
        System.out.println("3->" + mDetailViewModel.getProductLiveData.value?.status?.toString())
        val value = mDetailViewModel.getProductLiveData.value
        assert(value?.status == ResourceState.SUCCESS)
    }




}
