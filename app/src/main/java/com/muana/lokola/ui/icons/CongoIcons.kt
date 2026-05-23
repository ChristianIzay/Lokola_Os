package com.muana.lokola.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Pack d'icônes inspirées des arts congolais traditionnels
 * Ces icônes remplacent les icônes Material Design standard par des symboles culturels
 */

/**
 * Icône Tambour - Pour l'application Téléphone
 * Inspirée des tambours traditionnels congolais (ngoma)
 */
val CongoIcons.Drum: ImageVector
    get() {
        if (_drum != null) return _drum!!
        _drum = ImageVector.Builder(
            name = "Drum",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Corps du tambour
            path(
                fill = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 2f)
                curveTo(8f, 2f, 5f, 4f, 5f, 7f)
                verticalLineTo(17f)
                curveTo(5f, 20f, 8f, 22f, 12f, 22f)
                curveTo(16f, 22f, 19f, 20f, 19f, 17f)
                verticalLineTo(7f)
                curveTo(19f, 4f, 16f, 2f, 12f, 2f)
                close()
            }
            
            // Peau supérieure du tambour
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                strokeLineWidth = 0f
            ) {
                moveTo(12f, 2f)
                curveTo(8f, 2f, 5f, 3.5f, 5f, 5f)
                curveTo(5f, 6.5f, 8f, 8f, 12f, 8f)
                curveTo(16f, 8f, 19f, 6.5f, 19f, 5f)
                curveTo(19f, 3.5f, 16f, 2f, 12f, 2f)
                close()
            }
            
            // Motifs décoratifs sur le corps
            path(
                fill = SolidColor(Color(0xFFF7D618)), // Or congolais
                strokeLineWidth = 1f,
                stroke = SolidColor(Color(0xFF000000))
            ) {
                moveTo(7f, 10f)
                horizontalLineTo(17f)
                moveTo(7f, 13f)
                horizontalLineTo(17f)
                moveTo(7f, 16f)
                horizontalLineTo(17f)
            }
        }.build()
        return _drum!!
    }

private var _drum: ImageVector? = null

/**
 * Icône Message Traditionnel - Pour l'application Messages
 * Inspirée des symboles de communication traditionnels
 */
val CongoIcons.TalkingDrum: ImageVector
    get() {
        if (_talkingDrum != null) return _talkingDrum!!
        _talkingDrum = ImageVector.Builder(
            name = "TalkingDrum",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Forme de sablier (tambour parlant)
            path(
                fill = SolidColor(Color(0xFF8D6E63)), // Terre ocre
                strokeLineWidth = 1.5f,
                stroke = SolidColor(Color(0xFF3E2723))
            ) {
                moveTo(7f, 3f)
                curveTo(7f, 3f, 9f, 8f, 9f, 12f)
                curveTo(9f, 16f, 7f, 21f, 7f, 21f)
                horizontalLineTo(17f)
                curveTo(17f, 21f, 15f, 16f, 15f, 12f)
                curveTo(15f, 8f, 17f, 3f, 17f, 3f)
                close()
            }
            
            // Cordes de tension
            path(
                fill = null,
                strokeLineWidth = 1f,
                stroke = SolidColor(Color(0xFFF7D618))
            ) {
                moveTo(9f, 6f)
                lineTo(15f, 6f)
                moveTo(8.5f, 9f)
                lineTo(15.5f, 9f)
                moveTo(8.5f, 15f)
                lineTo(15.5f, 15f)
                moveTo(9f, 18f)
                lineTo(15f, 18f)
            }
        }.build()
        return _talkingDrum!!
    }

private var _talkingDrum: ImageVector? = null

/**
 * Icône Globe Africain - Pour l'application Internet/Browser
 * Représentation stylisée de l'Afrique
 */
val CongoIcons.AfricaGlobe: ImageVector
    get() {
        if (_africaGlobe != null) return _africaGlobe!!
        _africaGlobe = ImageVector.Builder(
            name = "AfricaGlobe",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Cercle du globe
            path(
                fill = null,
                strokeLineWidth = 1.5f,
                stroke = SolidColor(Color(0xFF0288D1))
            ) {
                moveTo(12f, 2f)
                arcTo(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 22f)
                arcTo(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 2f)
                close()
            }
            
            // Continent africain stylisé
            path(
                fill = SolidColor(Color(0xFF4CAF50)), // Vert Afrique
                strokeLineWidth = 0f
            ) {
                moveTo(12f, 5f)
                curveTo(11f, 5f, 10f, 6f, 10f, 7f)
                curveTo(10f, 8f, 11f, 9f, 11f, 10f)
                curveTo(11f, 11f, 10f, 12f, 10f, 13f)
                curveTo(10f, 14f, 11f, 15f, 11f, 16f)
                curveTo(11f, 17f, 12f, 18f, 12f, 19f)
                curveTo(12f, 18f, 13f, 17f, 13f, 16f)
                curveTo(13f, 15f, 14f, 14f, 14f, 13f)
                curveTo(14f, 12f, 13f, 11f, 13f, 10f)
                curveTo(13f, 9f, 14f, 8f, 14f, 7f)
                curveTo(14f, 6f, 13f, 5f, 12f, 5f)
                close()
            }
            
            // Lignes de latitude/longitude
            path(
                fill = null,
                strokeLineWidth = 0.5f,
                stroke = SolidColor(Color(0xFF0288D1).copy(alpha = 0.5f))
            ) {
                moveTo(2f, 12f)
                horizontalLineTo(22f)
                moveTo(12f, 2f)
                verticalLineTo(22f)
            }
        }.build()
        return _africaGlobe!!
    }

private var _africaGlobe: ImageVector? = null

/**
 * Icône Masque Traditionnel - Pour l'application Photo/Caméra
 * Inspirée des masques congolais traditionnels
 */
val CongoIcons.TraditionalMask: ImageVector
    get() {
        if (_traditionalMask != null) return _traditionalMask!!
        _traditionalMask = ImageVector.Builder(
            name = "TraditionalMask",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Forme ovale du masque
            path(
                fill = SolidColor(Color(0xFF8D6E63)),
                strokeLineWidth = 1.5f,
                stroke = SolidColor(Color(0xFF3E2723))
            ) {
                moveTo(12f, 3f)
                curveTo(8f, 3f, 5f, 6f, 5f, 10f)
                curveTo(5f, 14f, 7f, 18f, 9f, 20f)
                curveTo(10f, 21f, 11f, 21f, 12f, 21f)
                curveTo(13f, 21f, 14f, 21f, 15f, 20f)
                curveTo(17f, 18f, 19f, 14f, 19f, 10f)
                curveTo(19f, 6f, 16f, 3f, 12f, 3f)
                close()
            }
            
            // Yeux
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                strokeLineWidth = 1f,
                stroke = SolidColor(Color(0xFF000000))
            ) {
                moveTo(8.5f, 10f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10f, 11.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.5f, 13f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7f, 11.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.5f, 10f)
                close()
                
                moveTo(15.5f, 10f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 17f, 11.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.5f, 13f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14f, 11.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.5f, 10f)
                close()
            }
            
            // Bouche
            path(
                fill = null,
                strokeLineWidth = 1.5f,
                stroke = SolidColor(Color(0xFF000000))
            ) {
                moveTo(9f, 16f)
                quadraticBezierTo(12f, 18f, 15f, 16f)
            }
            
            // Motifs décoratifs
            path(
                fill = SolidColor(Color(0xFFF7D618)),
                strokeLineWidth = 0f
            ) {
                moveTo(11f, 6f)
                lineTo(12f, 8f)
                lineTo(13f, 6f)
                close()
            }
        }.build()
        return _traditionalMask!!
    }

private var _traditionalMask: ImageVector? = null

/**
 * Collection complète des icônes Congo
 */
object CongoIcons
