package com.kroune.nineMensMorrisApp

import android.os.Bundle
import androidx.navigation.NavType
import com.kr8ne.mensMorris.Position
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * suppress every fucking warning produced by detekt & idea
 */
@Suppress("KDocMissingDocumentation", "UndocumentedPublicProperty", "UndocumentedPublicClass")
@Serializable
sealed class Navigation {
    @Suppress("UndocumentedPublicClass")
    @Serializable
    data class SignUp(val nextRoute: Navigation) : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data class SignIn(val nextRoute: Navigation) : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data class GameEnd(val position: Position) : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data class OnlineGame(val id: Long) : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data class ViewAccount(val id: Long) : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data object Welcome : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data object AppStartAnimation : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data object GameWithBot : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data object GameWithFriend : Navigation()

    @Suppress("UndocumentedPublicClass")
    @Serializable
    data object SearchingOnlineGame : Navigation()
}

/**
 * custom nav type for position
 * used for "type safe compose navigation"
 */
class PositionNavType : NavType<Position>(false) {
    override fun serializeAsValue(value: Position): String {
        return Json.encodeToString(value)
    }

    override fun get(bundle: Bundle, key: String): Position? {
        val posAsJson = bundle.getString(key) ?: return null
        return Json.decodeFromString<Position>(posAsJson)
    }

    override fun parseValue(value: String): Position {
        return Json.decodeFromString<Position>(value)
    }

    override fun put(bundle: Bundle, key: String, value: Position) {
        val posAsJson = Json.encodeToString(value)
        bundle.putString(key, posAsJson)
    }
}

/**
 * custom nav type for navigation sealed class
 * used for "type safe compose navigation"
 */
class NavigationNavType : NavType<Navigation>(false) {
    override fun serializeAsValue(value: Navigation): String {
        return Json.encodeToString(value)
    }

    override fun get(bundle: Bundle, key: String): Navigation? {
        val posAsJson = bundle.getString(key) ?: return null
        return Json.decodeFromString<Navigation>(posAsJson)
    }

    override fun parseValue(value: String): Navigation {
        return Json.decodeFromString<Navigation>(value)
    }

    override fun put(bundle: Bundle, key: String, value: Navigation) {
        val posAsJson = Json.encodeToString(value)
        bundle.putString(key, posAsJson)
    }
}
