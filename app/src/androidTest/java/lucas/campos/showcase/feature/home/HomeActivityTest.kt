package lucas.campos.showcase.feature.home

import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

/**
 * @author Lucas Campos
 */
class HomeActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule(HomeActivity::class.java, true, false)

    @Test
    fun name() {
        activityRule.launchActivity(null)

    }
}