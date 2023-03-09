/*
package com.example.projettupreferes

import org.junit.Test
import org.junit.Assert.*
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Statistics
import com.example.projettupreferes.presenters.HomeFragmentPresenter
import com.example.projettupreferes.presenters.MainActivityPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.IHomeFragment
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class HomeFragmentPresenterTest {

    @Mock
    private lateinit var homeFragmentMock: IHomeFragment

    @Mock
    private lateinit var mainPresenterMock: MainActivityPresenter

    private lateinit var gameManagerMock: GameManager
    private lateinit var homeFragmentPresenter: HomeFragmentPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        gameManagerMock = GameManager(
            statistics = Statistics(UUID.randomUUID(), 0, 0, 0, 0)
        )

        val categoriesMap = mutableMapOf<String, Category>()
        categoriesMap["NORMAL"] = Category(UUID.randomUUID(), "NORMAL", "")

        gameManagerMock.categoriesMap = categoriesMap


        homeFragmentPresenter = HomeFragmentPresenter(homeFragmentMock, mainPresenterMock, gameManagerMock)
    }

    @Test
    fun `goToNormalGame should update statistics and load pairs`() {
        // Given
        val desiredFragment = FragmentsName.NormalGame
        val normalCategory = gameManagerMock.categoriesMap["NORMAL"]
        val expectedGamesPlayed = 1

        // When
        homeFragmentPresenter.goToNormalGame(desiredFragment)

        // Then
        Mockito.verify(mainPresenterMock).loadPair(normalCategory?.idCategory!!, desiredFragment)
        assertEquals(expectedGamesPlayed, gameManagerMock.statistics.gamesPlayed)
        Mockito.verify(TuPreferesRepository.getInstance())?.updateStatics(gameManagerMock.statistics)
    }

    @Test
    fun `goToPersonnal should request switch view`() {
        // Given
        val desiredFragment = FragmentsName.Personnal

        // When
        homeFragmentPresenter.goToPersonnal(desiredFragment)

        // Then
        Mockito.verify(mainPresenterMock).requestSwitchView(desiredFragment)
    }
}*/
