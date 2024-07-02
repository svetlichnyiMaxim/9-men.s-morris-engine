package com.kroune.nineMensMorrisApp.viewModel.impl.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kroune.nineMensMorrisApp.data.remote.Common.networkScope
import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryI
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * view model for viewing account
 */
class ViewAccountViewModel @AssistedInject constructor(
    private val accountInfoRepositoryI: AccountInfoRepositoryI,
    /**
     * account id
     */
    @Assisted val id: Long
) : ViewModelI() {

    /**
     * factory for [AssistedInject]
     */
    @AssistedFactory
    interface AssistedVMFactory {
        /**
         * creates [ViewAccountViewModel] using id
         */
        fun create(id: Long): ViewAccountViewModel
    }

    companion object {
        /**
         * provides factory
         */
        fun provideFactory(
            assistedVMFactory: AssistedVMFactory,
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedVMFactory.create(id) as T
            }
        }
    }

    /**
     * account name or null if it is still loading
     */
    val accountName = MutableStateFlow<String?>(null)

    /**
     * file with account picture or null if it is still loading
     */
    val tempPictureFile = MutableStateFlow<File?>(null)

    /**
     * account creation date or null if it is still loading
     */
    val accountCreationDate = MutableStateFlow<String?>(null)

    /**
     * updates [accountCreationDate]
     */
    fun getLoginById() {
        if (accountCreationDate.value != null) {
            return
        }
        CoroutineScope(networkScope).launch {
            var calendar: Triple<Int, Int, Int>? = null
            @Suppress("UnusedPrivateProperty")
            for (i in 0..5) {
                val newBuffer = accountInfoRepositoryI.getAccountDateById(id).getOrNull()
                if (newBuffer != null) {
                    calendar = newBuffer
                    break
                }
            }
            if (calendar == null)
                return@launch
            val finalString = "${calendar.first}-${calendar.second}-${calendar.third}"
            accountCreationDate.value = finalString
        }
    }

    /**
     * updates [tempPictureFile]
     */
    fun getProfilePicture() {
        if (tempPictureFile.value != null) {
            return
        }
        CoroutineScope(networkScope).launch {
            var buffer: ByteArray? = null
            @Suppress("UnusedPrivateProperty")
            for (i in 0..5) {
                val newBuffer = accountInfoRepositoryI.getAccountPictureById(id).getOrNull()
                if (newBuffer != null) {
                    buffer = newBuffer
                    break
                }
            }
            if (buffer == null)
                return@launch
            val file = withContext(Dispatchers.IO) {
                File.createTempFile("profilePicture-$id", ".webp")
            }
            file.writeBytes(buffer)
            tempPictureFile.value = file
        }
    }

    /**
     * updates [accountName]
     */
    fun getProfileName() {
        if (accountName.value != null) {
            return
        }
        CoroutineScope(networkScope).launch {
            var name: String? = null
            @Suppress("UnusedPrivateProperty")
            for (i in 0..5) {
                val newBuffer = accountInfoRepositoryI.getAccountNameById(id).getOrNull()
                if (newBuffer != null) {
                    name = newBuffer
                    break
                }
            }
            if (name == null)
                return@launch
            accountName.value = name
        }
    }

    init {
        getProfilePicture()
        getLoginById()
        getProfileName()
    }
}
