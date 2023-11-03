package com.example.examplevoidapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.vjapp.catalogshowcase.base.BaseKoinTest
import com.vjapp.catalogshowcase.di.configureTestAppComponent
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.ResourceState
import com.vjapp.catalogshowcase.utils.observeOnce
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject

/**
 * This test mock interactors so that tast is faster, and tests only viemodel code
 */

@RunWith(JUnit4::class)
class ViewModelTests : BaseKoinTest() {

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
    lateinit var mgetCatalogUseCase: GetCatalogUseCase

    @MockK
    lateinit var mgetProductUseCase: GetProductUseCase
    lateinit var mMainViewModel: MainViewModel
    lateinit var mDetailViewModel: DetailViewModel
    //val mMockWebServer: MockWebServer by inject()

    @ExperimentalCoroutinesApi
    @Test
    fun GetCatalogTest() {
        mMainViewModel = MainViewModel(mgetCatalogUseCase, coroutineTestRule.dispatcher)
        val sampleResponse = getJson("catalog_response.json")
        val jsonObj = Gson().fromJson(sampleResponse, CatalogEntity::class.java)
        coEvery { mgetCatalogUseCase.execute(any()) } returns jsonObj

        runTest {
            mMainViewModel.getCatalog(SearchTypes.SEARCHRESULT)
            mMainViewModel.getCatalogLiveData.observeOnce {}
        }

        System.out.println("3->" + mMainViewModel.getCatalogLiveData.value?.first?.status?.toString())
        val value = mMainViewModel.getCatalogLiveData.value
        assert(value?.first?.status == ResourceState.SUCCESS)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun GetProductTest() {
        mDetailViewModel = DetailViewModel(mgetProductUseCase, coroutineTestRule.dispatcher)
        val sampleResponse = getJson("product_response.json")
        val jsonObj = Gson().fromJson(sampleResponse, ProductEntity::class.java)
        coEvery { mgetProductUseCase.execute() } returns jsonObj

        runTest {//runTest replaces runBlockingTest
            mDetailViewModel.getProduct()
            mDetailViewModel.getProductLiveDataState().observeOnce() {}
        }
        System.out.println("3->" + mDetailViewModel.getProductLiveData.value?.status?.toString())
        val value = mDetailViewModel.getProductLiveData.value
        assert(value?.status == ResourceState.SUCCESS)
    }

}
