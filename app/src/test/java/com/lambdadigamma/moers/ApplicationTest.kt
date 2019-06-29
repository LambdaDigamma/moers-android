package com.lambdadigamma.moers

import org.junit.Test

class ApplicationTest {

    @Test
    fun appHasSharedPreference() {
        assert(App.preferenceName == "moers")
    }

}