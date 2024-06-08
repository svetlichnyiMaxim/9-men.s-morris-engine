package com.kr8ne.mensMorris.data.local.impl.game

import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.data.local.interfaces.DataI

/**
 * data for game with friend screen
 */
class GameWithFriendData : DataI() {
    override fun invokeBackend() {
        Cache.resetCacheDepth()
    }
}
