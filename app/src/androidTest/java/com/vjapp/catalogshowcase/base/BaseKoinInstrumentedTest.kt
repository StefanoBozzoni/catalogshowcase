package com.vjapp.catalogshowcase.base

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.Reader
import java.net.HttpURLConnection


abstract class BaseKoinInstrumentedTest : KoinTest {

    /**
     * For MockWebServer instance
     */
    private lateinit var mMockServerInstance: MockWebServer

    /**
     * Default, let server be shut down
     */
    private var mShouldStart = false

    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )


    fun mockAllNetworkResponsesWithJson(responseCode: Int) {
        val mockdispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/v1/searchresult" -> return MockResponse().setResponseCode(responseCode).setBody(getJson("catalog_response.json"))
                    "/v1/item" -> return MockResponse().setResponseCode(responseCode).setBody(getJson("product_response.json"))
                }
                return MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            }
        }
        mMockServerInstance.dispatcher = mockdispatcher
    }


    /**
     * Reads input file from the asset folder and converts it to json
     */
    fun getJson(path: String): String {
        var content: String
        val testContext = InstrumentationRegistry.getInstrumentation().context
        val inputStream = testContext.assets.open(path)
        val reader = BufferedReader(inputStream.reader() as Reader?)
        reader.use { thisreader ->
            content = thisreader.readText()
        }
        return content
    }


    @Before
    open fun setUp() {
        startMockServer(true)
    }

    @After
    open fun tearDown() {
        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }

    /**
     * Stop Mockwebserver
     */
    private fun stopMockServer() {
        if (mShouldStart) {
            mMockServerInstance.shutdown()
        }
    }


    /**
     * Start Mockwebserver
     */
    fun startMockServer(shouldStart: Boolean) {
        if (shouldStart) {
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            //Logger.getLogger(MockWebServer::class.java.name).level = Level.OFF
            //Logger.getLogger(OkHttp::class.java.name).setLevel(Level.OFF)

            mMockServerInstance.startSilently()
        }
    }

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    fun MockWebServer.startSilently() {
        //Logger.getLogger(this::class.java.name).level = Level.OFF
        start()
    }

}


