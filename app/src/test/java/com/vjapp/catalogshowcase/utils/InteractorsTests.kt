package com.example.examplevoidapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.vjapp.catalogshowcase.base.BaseKoinTest
import com.vjapp.catalogshowcase.cdi.domainModule
import com.vjapp.catalogshowcase.cdi.repositoryModule
import com.vjapp.catalogshowcase.cdi.viewModelModule
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.ResourceState
import com.vjapp.catalogshowcase.utils.observeOnce
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import localModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import remoteModule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class InteractorsTests : BaseKoinTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {

        MockKAnnotations.init(this)

        startKoin {
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    localModule,
                    remoteModule,
                    domainModule
                )
            )
        }
    }

    @MockK
    lateinit var mgetCatalogUseCase: GetCatalogUseCase
    @MockK
    lateinit var mgetProductUseCase: GetProductUseCase
    lateinit var mMainViewModel: MainViewModel
    lateinit var mDetailViewModel: DetailViewModel

    @ExperimentalCoroutinesApi
    @Test
    fun GetCatalogTest() {
        mMainViewModel = MainViewModel(mgetCatalogUseCase, coroutineTestRule.dispatcher)
        val sampleResponse = getJson("catalog_response.json")
        val jsonObj = Gson().fromJson(sampleResponse, CatalogEntity::class.java)
        coEvery { mgetCatalogUseCase.execute() } returns jsonObj

        coroutineTestRule.dispatcher.runBlockingTest() {
            mMainViewModel.getCatalog()
            mMainViewModel.getCatalogLiveData.observeOnce() {}
        }

        System.out.println("3->" + mMainViewModel.getCatalogLiveData.value?.status?.toString())
        val value = mMainViewModel.getCatalogLiveData.value
        assert(value?.status == ResourceState.SUCCESS)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun GetProductTest() {
        mDetailViewModel = DetailViewModel(mgetProductUseCase, coroutineTestRule.dispatcher)
        val sampleResponse = getJson("product_response.json")
        var jsonObj = Gson().fromJson(sampleResponse, ProductEntity::class.java)
        coEvery { mgetProductUseCase.execute() } returns jsonObj

        coroutineTestRule.dispatcher.runBlockingTest() {
            mDetailViewModel.getProduct()
            mDetailViewModel.getProductLiveData.observeOnce() {}
        }
        System.out.println("3->" + mDetailViewModel.getProductLiveData.value?.status?.toString())
        val value = mDetailViewModel.getProductLiveData.value
        assert(value?.status == ResourceState.SUCCESS)
    }


}