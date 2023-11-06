package com.vjapp.catalogshowcase.base

import com.vjapp.catalogshowcase.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttp
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import java.io.File
import java.util.logging.Level
import java.util.logging.Logger


abstract class BaseKoinTest : AutoCloseKoinTest() {


    @ExperimentalCoroutinesApi
    @get:Rule
    open val coroutineTestRule = CoroutineTestRule()


    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    /**
     * Reads input file and converts to json
     */
    fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }


    /**
     * For MockWebServer instance
     */
    private lateinit var mMockServerInstance: MockWebServer

    /**
     * Default, let server be shut down
     */
    private var mShouldStart = false

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
            Logger.getLogger(MockWebServer::class.java.name).level = Level.OFF
            Logger.getLogger(OkHttp::class.java.name).setLevel(Level.OFF)
            mMockServerInstance.startSilently()
        }
    }

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    fun MockWebServer.startSilently() {
        Logger.getLogger(this::class.java.name).level = Level.OFF
        start()
    }

}


