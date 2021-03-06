/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.asset

import com.almasb.fxgl.core.Disposable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class AssetCacheTest {

    private lateinit var cache: AssetCache

    @BeforeEach
    fun setUp() {
        cache = AssetCache(5)
    }

    @Test
    fun `Test disposable`() {
        val asset1 = DisposableAsset()
        val asset2 = DisposableAsset()
        val asset3 = DisposableAsset()
        val asset4 = DisposableAsset()
        val asset5 = DisposableAsset()

        cache.put("Test1", asset1)
        cache.put("Test2", asset2)
        cache.put("Test3", asset3)
        cache.put("Test4", asset4)
        cache.put("Test5", asset5)

        assertThat(asset1.disposed, `is`(false))
        assertThat(asset2.disposed, `is`(false))
        assertThat(asset3.disposed, `is`(false))
        assertThat(asset4.disposed, `is`(false))
        assertThat(asset5.disposed, `is`(false))

        val asset6 = DisposableAsset()

        cache.put("Test6", asset6)

        assertThat(asset1.disposed, `is`(true))
        assertThat(asset2.disposed, `is`(false))
        assertThat(asset3.disposed, `is`(false))
        assertThat(asset4.disposed, `is`(false))
        assertThat(asset5.disposed, `is`(false))
        assertThat(asset6.disposed, `is`(false))
    }

    private class DisposableAsset : Disposable {
        var disposed = false

        override fun dispose() {
            disposed = true
        }
    }
}