package com.example.mensmorris.model.impl

import com.example.mensmorris.data.impl.GameEndData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameEndScreen
import com.example.mensmorris.model.ModelModel

/**
 * game end model
 */
class GameEndModel : ModelModel {
    override var render: ScreenModel = GameEndScreen()
    override val data = GameEndData()
}
