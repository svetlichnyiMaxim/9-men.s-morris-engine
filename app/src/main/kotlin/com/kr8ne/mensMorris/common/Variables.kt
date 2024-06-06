package com.kr8ne.mensMorris.common

/**
 * fast way for creating green piece
 */
const val GREEN = true

/**
 * fast way for creating blue piece
 */
const val BLUE_ = false

/**
 * fast way for creating empty piece
 */
inline val EMPTY: Boolean? get() = null

/**
 * The server's address.
 * put your network ip here
 */
const val SERVER_ADDRESS = "://10.68.154.156:8080"

/**
 * The API endpoint for user-related operations.
 */
const val USER_API = "/api/v1/user"
