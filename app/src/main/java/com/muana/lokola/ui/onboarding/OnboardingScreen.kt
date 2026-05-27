package com.muana.lokola.ui.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.R
import com.muana.lokola.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    onLanguageSelected: (String) -> Unit = {},
    onThemeSelected: (com.muana.lokola.ui.theme.CongoTheme) -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()
    
    // Sélection de thème par défaut
    var selectedTheme by remember { mutableStateOf(com.muana.lokola.ui.theme.CongoTheme.FLEUVE) }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> OnboardingPage1()
                1 -> OnboardingPage2()
                2 -> OnboardingPage3(
                    onComplete = onComplete,
                    onLanguageSelected = onLanguageSelected
                )
                3 -> ThemeSelectionPage(
                    selectedTheme = selectedTheme,
                    onThemeSelected = { 
                        selectedTheme = it
                        onThemeSelected(it)   // Save immediately via ViewModel
                    },
                    onFinish = {
                        onComplete()
                    }
                )
            }
        }

        // Page indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (pagerState.currentPage == index) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                )
            }
        }

        // Navigation buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (pagerState.currentPage < 2) {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(stringResource(R.string.common_next))
                }
            }
        }
    }
}

@Composable
fun OnboardingPage1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🇨🇩",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_theme_title),
            style = CongoTypography.KubaHeadline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_theme_subtitle),
            style = CongoTypography.RumbaBodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 32.dp)
        )

                Text(
                    text = stringResource(R.string.onboarding_welcome_subtitle),
                    style = CongoTypography.RumbaBodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
        
        // Message philosophique culturel
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.onboarding_welcome_philosophy_title),
                    style = CongoTypography.RumbaBodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.onboarding_welcome_philosophy_subtitle),
                    style = CongoTypography.NdomboloLabel,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.onboarding_welcome_philosophy_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun OnboardingPage2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📊",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_datasaver_title),
            style = CongoTypography.KubaHeadline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_datasaver_subtitle),
            style = CongoTypography.RumbaBodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun OnboardingPage3(
    onComplete: () -> Unit,
    onLanguageSelected: (String) -> Unit = {}
) {
    var selectedLanguage by remember { mutableStateOf("fr") }

    // Animation de transition lors du changement de langue (effet "pop" culturel)
    val frScale by animateFloatAsState(
        targetValue = if (selectedLanguage == "fr") 1.08f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "frLanguageScale"
    )
    val lingScale by animateFloatAsState(
        targetValue = if (selectedLanguage == "ling") 1.08f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "lingLanguageScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🌍",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_language_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_language_subtitle),
            style = CongoTypography.RumbaBodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Language selection buttons
        LanguageButton(
            text = stringResource(R.string.onboarding_language_french),
            isSelected = selectedLanguage == "fr",
            onClick = { 
                selectedLanguage = "fr"
                onLanguageSelected("fr")
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .scale(frScale)
                .ndomboloBounce(selectedLanguage == "fr")
        )

        LanguageButton(
            text = stringResource(R.string.onboarding_language_lingala),
            isSelected = selectedLanguage == "ling",
            onClick = { 
                selectedLanguage = "ling"
                onLanguageSelected("ling")
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .scale(lingScale)
                .ndomboloBounce(selectedLanguage == "ling")
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onComplete,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(R.string.onboarding_language_button),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun LanguageButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .ndomboloBounce(pressed = isSelected),   // Rebond culturel Ndombolo (Design System)
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected)
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else
            BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Text(
            text = text,
            style = CongoTypography.NdomboloLabel,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun ThemeSelectionPage(
    selectedTheme: CongoTheme,
    onThemeSelected: (CongoTheme) -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎨",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_theme_title),
            style = CongoTypography.KubaHeadline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.onboarding_theme_subtitle),
            style = CongoTypography.RumbaBodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Options de thèmes
        CongoTheme.values().forEach { theme ->
            val colors = LokolaHeritage.colorsFor(theme)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onThemeSelected(theme) }
                    .border(
                        width = if (selectedTheme == theme) 3.dp else 1.dp,
                        color = if (selectedTheme == theme) colors.primary 
                                else MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .kubaPulse(),   // Animation culturelle (Design System)
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
            Text(
                text = theme.displayName,
                style = CongoTypography.RumbaBody
            )
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = theme.displayName,
                            style = CongoTypography.RumbaBody
                        )
                        Text(
                            text = getDescriptionForTheme(theme),
                            style = CongoTypography.NdomboloLabel,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                    
                    if (selectedTheme == theme) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(R.string.onboarding_theme_finish),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun getDescriptionForTheme(theme: CongoTheme): String {
    return when (theme) {
        CongoTheme.RUMBA -> stringResource(R.string.onboarding_theme_rumba)
        CongoTheme.SAVANE -> stringResource(R.string.onboarding_theme_savane)
        CongoTheme.FLEUVE -> stringResource(R.string.onboarding_theme_fleuve)
        CongoTheme.FORET -> stringResource(R.string.onboarding_theme_foret)
    }
}
