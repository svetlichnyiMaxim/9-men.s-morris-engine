package com.example.mensmorris.domain.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.common.Locate
import com.example.mensmorris.common.Position
import com.example.mensmorris.domain.ScreenModel

/**
 * shows how many pieces we still have
 */
class PieceCountFragment(
    /**
     * our position
     */
    val pos: MutableState<Position>
) : ScreenModel {
    @Composable
    override fun InvokeRender() {
        Locate(Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (pos.value.pieceToMove) 1.5f else 1f)
                    .background(Color.Green, CircleShape)
                    .alpha(if (pos.value.freePieces.first == 0.toUByte()) 0f else 1f),
                Alignment.Center
            ) {
                Text(color = Color.Blue, text = pos.value.freePieces.first.toString())
            }
        }
        Locate(Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (!pos.value.pieceToMove) 1.5f else 1f)
                    .background(Color.Blue, CircleShape)
                    .alpha(if (pos.value.freePieces.second == 0.toUByte()) 0f else 1f),
                Alignment.Center
            ) {
                Text(color = Color.Green, text = pos.value.freePieces.second.toString())
            }
        }
    }
}
