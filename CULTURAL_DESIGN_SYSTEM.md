# Lokola Heritage Design System
**Version**: 1.0  
**Date**: Mai 2026  
**Philosophie**: Géométrie + Rythme + Terre & Lumière

Lokola n’est pas un launcher Material habillé de motifs.  
C’est un système où **chaque pixel, chaque mouvement et chaque lettre** porte une empreinte culturelle congolaise.

---

## 1. Typographie – Le Rythme des Mots

### Familles
- **Kuba Display** → Titres, headers, gros textes (géométrique, présence forte)
- **Rumba Text** → Corps de texte, descriptions, widgets (fluide, rythmé)
- **Ndombolo Accent** → Labels, badges, micro-textes (énergique, condensé)

### Règles d’or
- Titres : tracking serré (-0.5 à -1), forte graisse
- Corps : line-height généreux (1.5–1.6) pour respirer comme un pagne
- Labels : letter-spacing légèrement augmenté pour un effet "rythmique"

### Hiérarchie recommandée
- KubaDisplayLarge (32–40sp)
- KubaHeadline (22–26sp)
- RumbaBodyLarge (16sp)
- RumbaBody (14sp)
- NdomboloLabel (11–12sp)

**Conseil** : Plus le texte est important culturellement (titres de widgets, header), plus il doit sentir le "tambour".

---

## 2. Couleurs – Terre, Or & Lumière du Congo

### Palettes par Thème (enrichies)

**RUMBA** (Énergie & Passion)
- Primary: Rouge profond (#B71C1C)
- Secondary: Or chaud (#F9A825)
- Accent: Jaune vif (#FFEB3B)
- Background: Noir terreux (#1A1A1A)
- Surface: Gris foncé chaud (#2C2C2C)

**SAVANE** (Noblesse & Lumière)
- Primary: Ocre doré (#E65100)
- Secondary: Terre rouge (#8D6E63)
- Accent: Or pâle (#FFE082)
- Background: Beige chaud (#F5E8C7)

**FLEUVE** (Profondeur & Sérénité)
- Primary: Bleu fleuve profond (#0D47A1)
- Secondary: Bleu rivière (#42A5F5)
- Accent: Or doux (#C5B358)
- Background: Bleu nuit (#0A192F)

**FORÊT** (Mystère & Racines)
- Primary: Vert forêt profond (#1B5E20)
- Secondary: Vert mousse (#4CAF50)
- Accent: Or antique (#A1887F)
- Background: Brun terre (#2E2E1F)

### Couleurs Sémantiques (partagées)
- `KubaGold` → Toujours l’or, symbole de valeur et lumière
- `ClayRed` → Terre cuite, chaleur humaine
- `RiverDeep` → Eau profonde, calme
- `ForestShadow` → Mystère et profondeur
- `MaskBrown` → Bois des masques traditionnels

**Règle** : Chaque thème a son "or" (KubaGold) mais avec des teintes légèrement différentes selon l’ambiance.

---

## 3. Motion – Le Langage des Motifs

### 5 Familles d’Animations Culturelles (Lokola Heritage)

| Famille              | Inspiration              | Caractère              | Usage principal                          | Modifier dans le Design System |
|----------------------|--------------------------|------------------------|------------------------------------------|--------------------------------|
| **Kuba Pulse**       | Losanges Kuba            | Géométrique, précis    | Badges, indicateurs, loading             | `.kubaPulse()`                |
| **Rumba Groove**     | Rythme de la rumba       | Énergique, vivant      | Cartes, boutons, FAB, Header             | `.rumbaGroove()`              |
| **River Flow**       | Courant du fleuve        | Fluide, organique      | Transitions, scroll, Weather             | `.riverFlow()`                |
| **Ndombolo Bounce**  | Danse ndombolo           | Rebond, joyeux         | Feedback tactile, sélection, Dock        | `.ndomboloBounce(pressed)`    |
| **Mask Reveal**      | Masques traditionnels    | Mystérieux, profond    | Apparitions, onboarding, Proverbes       | `.maskReveal()`               |

### Tokens de Motion (recommandés)

```kotlin
// Durées
val RumbaQuick = 280
val RumbaStandard = 520
val RiverFlow = 850
val MaskReveal = 720

// Easings
val KubaEasing = FastOutSlowInEasing
val RumbaGroove = CubicBezier(0.4f, 0.0f, 0.2f, 1f)
val RiverWave = CubicBezier(0.25f, 0.1f, 0.25f, 1f)
```

### Règle d’usage
- **Widgets** → Rumba Groove ou Kuba Pulse
- **Transitions entre pages** → River Flow
- **Feedback utilisateur** → Ndombolo Bounce
- **Révélation de contenu** → Mask Reveal

---

## 4. Principes de Composition

1. **Le motif est roi**  
   Utilise les patterns (`drawCulturalPattern`) en arrière-plan, jamais en premier plan sauf sur les cartes sélectionnées.

2. **Le rythme avant tout**  
   Les animations doivent sentir le "pas de danse", pas un simple mouvement UI.

3. **Moins de bruit, plus de profondeur**  
   Privilégie les ombres douces (inspirées du bois et de la terre) plutôt que les ombres Material agressives.

4. **L’or est sacré**  
   `KubaGold` n’est jamais utilisé en fond. Il sert à honorer (badges, accents, highlights).

5. **Chaque écran raconte une histoire**  
   Un écran Rumba doit vibrer. Un écran Fleuve doit respirer.

---

## 5. Checklist d’Application

- [ ] Tous les titres utilisent KubaDisplay ou équivalent
- [ ] Chaque widget applique le bon pattern selon le thème
- [ ] Les cartes ont une animation Rumba Groove ou Ndombolo Bounce au clic
- [ ] Les couleurs de fond respectent la palette du thème actif
- [ ] Les transitions de page utilisent River Flow
- [ ] L’or (KubaGold) est réservé aux éléments de valeur culturelle

---

**Lokola Heritage n’est pas un skin.**  
C’est une façon de voir le monde à travers le prisme de la culture congolaise, traduite en code et en pixels.

*"Chaque interaction est un pas de danse. Chaque couleur est une terre. Chaque lettre porte un tambour."*

— Philosophie Lokola Heritage
