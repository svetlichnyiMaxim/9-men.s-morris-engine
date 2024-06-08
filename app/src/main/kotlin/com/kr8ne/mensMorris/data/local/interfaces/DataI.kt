package com.kr8ne.mensMorris.data.local.interfaces

abstract class DataI : DataInterface {
    init {
        this.invokeBackend()
    }
}

/**
 * model we use for all data providing classes
 */
private interface DataInterface {

    /**
     * invokes backend part
     * launched with coroutine
     */
    fun invokeBackend() {}
}
