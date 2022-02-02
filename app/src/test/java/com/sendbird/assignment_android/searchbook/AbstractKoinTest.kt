package com.sendbird.assignment_android.searchbook

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sendbird.assignment_android.searchbook.di.appModule
import org.junit.Rule
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

abstract class AbstractKoinTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.NONE)
        modules(appModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> Mockito.mock(clazz.java)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
}