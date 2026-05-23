package com.muana.lokola.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Patterns inspirés des pagnes congolais traditionnels
 * Ces motifs géométriques sont caractéristiques de l'art textile africain
 */

/**
 * Pattern de losanges - Inspiré des motifs Kuba
 */
fun DrawScope.drawKubaPattern(
    size: Size,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Dp = 2.dp
) {
    val patternSize = 40.dp.toPx()
    val stroke = strokeWidth.toPx()
    
    var x = 0f
    while (x < size.width) {
        var y = 0f
        while (y < size.height) {
            // Losange principal
            val path = Path().apply {
                moveTo(x + patternSize / 2, y)
                lineTo(x + patternSize, y + patternSize / 2)
                lineTo(x + patternSize / 2, y + patternSize)
                lineTo(x, y + patternSize / 2)
                close()
            }
            
            drawPath(
                path = path,
                color = if ((x / patternSize + y / patternSize).toInt() % 2 == 0) 
                    primaryColor.copy(alpha = 0.3f) 
                else 
                    secondaryColor.copy(alpha = 0.3f),
                style = Stroke(width = stroke)
            )
            
            y += patternSize
        }
        x += patternSize
    }
}

/**
 * Pattern de triangles - Inspiré des motifs Luba
 */
fun DrawScope.drawLubaPattern(
    size: Size,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Dp = 2.dp
) {
    val patternSize = 50.dp.toPx()
    val stroke = strokeWidth.toPx()
    
    var x = 0f
    while (x < size.width) {
        var y = 0f
        while (y < size.height) {
            // Triangle vers le haut
            val path1 = Path().apply {
                moveTo(x + patternSize / 2, y)
                lineTo(x + patternSize, y + patternSize)
                lineTo(x, y + patternSize)
                close()
            }
            
            drawPath(
                path = path1,
                color = primaryColor.copy(alpha = 0.2f),
                style = Stroke(width = stroke)
            )
            
            // Triangle vers le bas (décalé)
            if (x + patternSize / 2 < size.width && y + patternSize / 2 < size.height) {
                val path2 = Path().apply {
                    moveTo(x + patternSize / 2, y + patternSize)
                    lineTo(x + patternSize * 1.5f, y)
                    lineTo(x - patternSize / 2, y)
                    close()
                }
                
                drawPath(
                    path = path2,
                    color = secondaryColor.copy(alpha = 0.2f),
                    style = Stroke(width = stroke)
                )
            }
            
            y += patternSize
        }
        x += patternSize
    }
}

/**
 * Pattern de vagues - Inspiré du fleuve Congo
 */
fun DrawScope.drawRiverPattern(
    size: Size,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Dp = 3.dp
) {
    val waveHeight = 30.dp.toPx()
    val waveWidth = 60.dp.toPx()
    val stroke = strokeWidth.toPx()
    
    var y = 0f
    while (y < size.height) {
        val path = Path().apply {
            var x = 0f
            moveTo(x, y + waveHeight / 2)
            
            while (x < size.width) {
                cubicTo(
                    x1 = x + waveWidth / 4,
                    y1 = y,
                    x2 = x + waveWidth * 3 / 4,
                    y2 = y + waveHeight,
                    x3 = x + waveWidth,
                    y3 = y + waveHeight / 2
                )
                x += waveWidth
            }
        }
        
        drawPath(
            path = path,
            color = if ((y / waveHeight).toInt() % 2 == 0) 
                primaryColor.copy(alpha = 0.3f) 
            else 
                secondaryColor.copy(alpha = 0.3f),
            style = Stroke(width = stroke)
        )
        
        y += waveHeight
    }
}

/**
 * Pattern de cercles concentriques - Inspiré des masques traditionnels
 */
fun DrawScope.drawMaskPattern(
    size: Size,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Dp = 2.dp
) {
    val spacing = 80.dp.toPx()
    val stroke = strokeWidth.toPx()
    
    var x = spacing / 2
    while (x < size.width) {
        var y = spacing / 2
        while (y < size.height) {
            // Cercles concentriques
            for (i in 1..3) {
                drawCircle(
                    color = if (i % 2 == 0) 
                        primaryColor.copy(alpha = 0.2f / i) 
                    else 
                        secondaryColor.copy(alpha = 0.2f / i),
                    radius = (spacing / 4 * i).toFloat(),
                    center = Offset(x, y),
                    style = Stroke(width = stroke)
                )
            }
            
            y += spacing
        }
        x += spacing
    }
}

/**
 * Pattern de zigzag - Énergie de la rumba
 */
fun DrawScope.drawRumbaPattern(
    size: Size,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Dp = 3.dp
) {
    val zigzagHeight = 40.dp.toPx()
    val zigzagWidth = 30.dp.toPx()
    val stroke = strokeWidth.toPx()
    
    var y = 0f
    while (y < size.height) {
        val path = Path().apply {
            var x = 0f
            moveTo(x, y + zigzagHeight / 2)
            
            while (x < size.width) {
                lineTo(x + zigzagWidth / 2, y)
                lineTo(x + zigzagWidth, y + zigzagHeight / 2)
                lineTo(x + zigzagWidth * 1.5f, y + zigzagHeight)
                lineTo(x + zigzagWidth * 2, y + zigzagHeight / 2)
                x += zigzagWidth * 2
            }
        }
        
        drawPath(
            path = path,
            color = if ((y / zigzagHeight).toInt() % 2 == 0) 
                primaryColor.copy(alpha = 0.25f) 
            else 
                secondaryColor.copy(alpha = 0.25f),
            style = Stroke(width = stroke)
        )
        
        y += zigzagHeight
    }
}

/**
 * Applique un pattern culturel en arrière-plan
 */
fun DrawScope.drawCulturalPattern(
    size: Size,
    theme: CongoTheme,
    colors: ThemeColors
) {
    when (theme) {
        CongoTheme.RUMBA -> drawRumbaPattern(size, colors.primary, colors.secondary)
        CongoTheme.SAVANE -> drawKubaPattern(size, colors.primary, colors.accent)
        CongoTheme.FLEUVE -> drawRiverPattern(size, colors.primary, colors.secondary)
        CongoTheme.FORET -> drawLubaPattern(size, colors.primary, colors.secondary)
    }
}
