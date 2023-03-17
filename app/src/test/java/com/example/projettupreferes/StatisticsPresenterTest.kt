package com.example.projettupreferes

import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Statistics
import com.example.projettupreferes.presenters.MainActivityPresenter
import com.example.projettupreferes.presenters.StatisticsPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.IStatisticsFragment
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class StatisticsPresenterTest {

    @Mock
    private lateinit var statisticsFragmentMock: IStatisticsFragment

    @Mock
    private lateinit var gameManagerMock: GameManager

    private lateinit var statisticsPresenter: StatisticsPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        statisticsPresenter = StatisticsPresenter(statisticsFragmentMock, gameManagerMock)
    }

    @Test
    fun `udpateStatisticsInformation should update statistics information in fragment`() {
        // Given
        `when`(gameManagerMock.statistics).thenReturn(Statistics(UUID.randomUUID(), 10, 20, 5, 3))

        // When
        statisticsPresenter.updateStatisticsInformation()

        // Then
        verify(statisticsFragmentMock).udpateDisplayStatistics(3, 5, 20, 10)
    }

    @Test
    fun `StatisticsPresenter constructor should set statistics presenter in fragment`() {
        // When
        val statisticsPresenter = StatisticsPresenter(statisticsFragmentMock, gameManagerMock)

        // Then
        verify(statisticsFragmentMock).setStatisticsPresenter(statisticsPresenter)
    }
}