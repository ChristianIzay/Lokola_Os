# Intégration Rapide – Lokola Heritage Design System

## 1. Utiliser la nouvelle typographie

Dans tes Composables :

```kotlin
Text(
    text = "Mbote! 🇨🇩",
    style = CongoTypography.KubaDisplayLarge
)

Text(
    text = "Boyeyi Bolamu na LokolaOS",
    style = CongoTypography.RumbaBodyLarge
)
```

## 2. Récupérer les couleurs par thème

```kotlin
val themeColors = getCongoColorsForTheme(currentTheme)

// Exemple d'utilisation
Card(
    colors = CardDefaults.cardColors(containerColor = themeColors.surface)
) { ... }
```

## 3. Appliquer les animations culturelles

```kotlin
// Animation géométrique Kuba
Modifier.kubaPulse()

// Groove rythmique Rumba
Modifier.rumbaGroove()

// Vague fluide du Fleuve
Modifier.riverFlow()
```

## 4. Dans le Theme principal (recommandé)

Remplace ou enrichis `getThemeColors()` dans `CongoThemes.kt` ou `Theme.kt` en utilisant `getCongoColorsForTheme()`.

---

**Prochaines étapes suggérées :**

1. Remplacer progressivement les `TextStyle` dans `HeaderSection`, widgets et Settings par `CongoTypography`.
2. Appliquer `getCongoColorsForTheme()` dans `LauncherScreen` et les widgets.
3. Ajouter `.rumbaGroove()` ou `.kubaPulse()` sur les cartes et le FAB.
4. Rendre les patterns de `CulturalPatterns.kt` plus visibles avec les nouvelles couleurs.

Le système est conçu pour être adopté progressivement sans tout casser d’un coup.
