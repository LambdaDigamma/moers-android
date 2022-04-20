package com.lambdadigamma.moers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lambdadigamma.moers.core.geo.GoogleMapsNavigationProvider
import com.lambdadigamma.moers.core.geo.TravelMode
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoogleMapsNavigationProviderTest {

    @Test
    fun testBuildUri() {
        val provider = GoogleMapsNavigationProvider()

        assertEquals(
            "https://www.google.com/maps/dir/?api=1&destination=51.448212%2C6.659806&travelmode=driving&dir_action=navigate",
            provider.buildUri(
                latitude = 51.448212,
                longitude = 6.659806,
                travelMode = TravelMode.DRIVING
            ).toString()
        )
    }

}