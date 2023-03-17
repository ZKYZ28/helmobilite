package com.example.projettupreferes

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projettupreferes.activities.MainActivity
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testContent() {
        onView(withId(R.id.titleNormal)).check(matches(withText(R.string.normal_game)))
        onView(withId(R.id.textNormal)).check(matches(withText(R.string.description_normal_game_home)))

        onView(withId(R.id.titleCreation)).check(matches(withText(R.string.creation_game)))
        onView(withId(R.id.textCreation)).check(matches(withText(R.string.creation_game_description)))
    }

    @Test
    fun testComponent() {
        onView(withId(R.id.normalGame))
            .check(matches(isDisplayed()))
            .check(matches(instanceOf(ConstraintLayout::class.java)))

        onView(withId(R.id.personnel))
            .check(matches(isDisplayed()))
            .check(matches(instanceOf(ConstraintLayout::class.java)))


        onView(withId(R.id.titleNormal))
            .check(matches(isDisplayed()))
            .check(matches(instanceOf(TextView::class.java)))

        onView(withId(R.id.titleCreation))
            .check(matches(isDisplayed()))
            .check(matches(instanceOf(TextView::class.java)))

        onView(withId(R.id.textNormal))
            .check(matches(isDisplayed()))
            .check(matches(instanceOf(TextView::class.java)))

        onView(withId(R.id.textCreation))
            .check(matches(isDisplayed()))
            .check(matches(instanceOf(TextView::class.java)))
    }

    @Test
    fun testNormalGameButton() {
        // Cliquer sur le bouton normalGame
        onView(withId(R.id.normalGame)).perform(click())

        // Vérifier que l'écran NormalGameFragment est affiché
        onView(withId(R.id.playGameFragment)).check(matches(isDisplayed()))

        //Retour à l'écran
        onView(withId(R.id.backButton)).perform(click())
    }

    @Test
    fun testPersonnelButton() {
        // Cliquer sur le bouton personnel
        onView(withId(R.id.personnel)).perform(click())

        // Vérifier que l'écran PersonnalFragment est affiché
        onView(withId(R.id.personnelFragment)).check(matches(isDisplayed()))

        //Retour à l'écran
        onView(withId(R.id.backButton)).perform(click())
    }
}