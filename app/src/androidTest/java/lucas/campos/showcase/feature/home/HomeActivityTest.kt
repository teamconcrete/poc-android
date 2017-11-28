package lucas.campos.showcase.feature.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import lucas.campos.showcase.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Lucas Campos
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule @JvmField
    var activity = ActivityTestRule(HomeActivity::class.java, true, false)

    @Test
    fun shouldDisplayItems() {
        home {
            loadData(activity)
        } startActivity {
            displayItems()
        }
    }
}

class HomeRobot {

    fun loadData(activityRule: ActivityTestRule<HomeActivity>) {
        activityRule.launchActivity(null)
    }

    infix fun startActivity(func: HomeResult.() -> Unit): HomeResult {
        return HomeResult().apply { func() }
    }
}

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }

class HomeResult {
    fun displayItems(): HomeResult {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        return this
    }
}

