package com.vjapp.catalogshowcase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vjapp.catalogshowcase.base.BaseKoinTest
import com.vjapp.catalogshowcase.data.exceptions.NetworkCommunicationException
import com.vjapp.catalogshowcase.di.configureTestAppComponent
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.get
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class InteractorsTest : BaseKoinTest(){

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val mCount = 40

    @Before
    fun start(){
        super.setUp()
        MockKAnnotations.init(this)
        //Start Koin with required dependencies
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_getCatalog_returns_expected_value()= runBlocking{
        val mGetCatalogUseCase: GetCatalogUseCase =get()

        mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_OK)

        val dataReceived = mGetCatalogUseCase.execute(GetCatalogUseCase.Params(SearchTypes.SEARCHRESULT))

        Assert.assertNotNull(dataReceived)
        Assert.assertEquals(dataReceived.catalogList.size, mCount)
        Assert.assertThat(dataReceived.catalogList[0].cod10.length, greaterThan(0))
    }


    @Test
    fun test_getCatalog_returns_interna_server_error()= runBlocking{
        val mGetCatalogUseCase: GetCatalogUseCase =get()

        mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_INTERNAL_ERROR)

        //var exception : Exception?=null
        val exception:Exception? = try {
            val dataReceived = mGetCatalogUseCase.execute(GetCatalogUseCase.Params(SearchTypes.SEARCHRESULT))
            null
        } catch(e:Exception) {
            e
        }

        assert(exception is NetworkCommunicationException)
    }



}