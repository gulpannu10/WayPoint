---
name: Serene Clinical
colors:
  surface: '#faf8ff'
  surface-dim: '#d2d9f4'
  surface-bright: '#faf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f3ff'
  surface-container: '#eaedff'
  surface-container-high: '#e2e7ff'
  surface-container-highest: '#dae2fd'
  on-surface: '#131b2e'
  on-surface-variant: '#424754'
  inverse-surface: '#283044'
  inverse-on-surface: '#eef0ff'
  outline: '#727785'
  outline-variant: '#c2c6d6'
  surface-tint: '#005ac2'
  primary: '#0058be'
  on-primary: '#ffffff'
  primary-container: '#2170e4'
  on-primary-container: '#fefcff'
  inverse-primary: '#adc6ff'
  secondary: '#4953bc'
  on-secondary: '#ffffff'
  secondary-container: '#8792fe'
  on-secondary-container: '#17228f'
  tertiary: '#006387'
  on-tertiary: '#ffffff'
  tertiary-container: '#007da9'
  on-tertiary-container: '#fcfcff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d8e2ff'
  primary-fixed-dim: '#adc6ff'
  on-primary-fixed: '#001a42'
  on-primary-fixed-variant: '#004395'
  secondary-fixed: '#e0e0ff'
  secondary-fixed-dim: '#bdc2ff'
  on-secondary-fixed: '#000767'
  on-secondary-fixed-variant: '#2f3aa3'
  tertiary-fixed: '#c4e7ff'
  tertiary-fixed-dim: '#7bd0ff'
  on-tertiary-fixed: '#001e2c'
  on-tertiary-fixed-variant: '#004c69'
  background: '#faf8ff'
  on-background: '#131b2e'
  surface-variant: '#dae2fd'
typography:
  display:
    fontFamily: Plus Jakarta Sans
    fontSize: 48px
    fontWeight: '700'
    lineHeight: 56px
    letterSpacing: -0.02em
  display-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.015em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.01em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 26px
    fontWeight: '600'
    lineHeight: 34px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
  title-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '500'
    lineHeight: 26px
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 26px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 22px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 18px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 11px
    fontWeight: '600'
    lineHeight: 14px
    letterSpacing: 0.03em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1.5rem
  gutter-sm: 1rem
  gutter-lg: 2rem
  margin: 1.5rem
  margin-sm: 1rem
  margin-lg: 3rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2.5rem
---

## Brand & Style

The design system projects a calm, reassuring, and scientifically rigorous presence. It reconciles clinical precision with gentle bedside warmth, removing anxiety from complex medical journeys, diagnostics, and patient-practitioner wayfinding. 

The aesthetic is Modern Humanist Healthcare: an airy, light-filled atmosphere built with soft tonal surfaces, generous negative space, and gentle structural curves. It eschews sterile institutional rigidity in favor of tranquil, breathable visual containers that frame instructional diagrams and empathetic illustrations without visual clutter.

## Colors

The palette establishes an atmosphere of gentle clarity, anchored by soft periwinkle, luminous cerulean, and soothing sky tones:

- **Primary (`#3b82f6`)**: Balanced periwinkle blue used for focused interactions, primary actions, and pivotal status indicators.
- **Secondary (`#818cf8`)**: Soft periwinkle used for supportive badges, secondary actions, and transitional elements.
- **Tertiary (`#38bdf8`)**: Airy powdery cerulean reserved for non-critical informational accents, highlighting, and progressive states.
- **Neutral (`#0f172a`)**: Deep slate midnight providing crisp typographic contrast without the harshness of pure black.

### Supporting Washes & Surfaces
- **Canvas Base**: `#f8fafc` for effortless eye rest.
- **Surface Soft**: `#e0f2fe` and `#dbeafe` applied as tinted surface washes for cards, diagram backdrops, and active table rows.
- **Deep Accent**: `#1e40af` utilized for high-legibility dark mode elements or emphasized clinical headers.

## Typography

The typography uses Plus Jakarta Sans across all levels. Its open apertures, geometric underpinnings, and friendly rounded terminals deliver immediate legibility while mitigating clinical anxiety. 

Titles use slightly condensed tracking to retain crisp visual rhythm, while body text prioritizes generous vertical line-heights (1.6x) to facilitate scanning across sensitive medical data and structured instructions.

## Layout & Spacing

A fluid responsive 12-column grid provides horizontal harmony, transitioning to 8 columns on tablet devices and 4 columns on mobile viewports.

The layout emphasizes generous outer breathing room and vertical cadence:
- **Canvas Margins**: Compact 1rem on mobile devices, expanding to 3rem on desktop to ensure focused, non-overwhelming content densities.
- **Section Pacing**: Major visual modules and diagram blocks are isolated with `space-xl` gaps to reduce cognitive load during patient tasks.
- **Component Breathing Room**: Form controls and card bodies leverage `space-md` and `space-lg` to create approachable touch targets and relaxed scanning zones.

## Elevation & Depth

Visual hierarchy relies on soft tonal containment rather than heavy drop shadows:

- **Level 0 (Base)**: `#f8fafc` canvas background.
- **Level 1 (Card & Containers)**: Flat `#ffffff` surface delineated by a subtle 1px border (`#e2e8f0` or tinted `#dbeafe`), paired with a faint cerulean-tinted ambient glow (`0 4px 20px -2px rgba(59, 130, 246, 0.05)`).
- **Level 2 (Overlays & Dialogs)**: `#ffffff` floating plane elevated by a soft dual shadow (`0 10px 30px -4px rgba(15, 23, 42, 0.06), 0 4px 12px -2px rgba(59, 130, 246, 0.04)`).
- **Backdrop Veil**: Modals and drawers sit above a soothing frosted veil using `rgba(248, 250, 252, 0.8)` with a gentle `backdrop-blur-md` (8px).

## Shapes

The interface embraces organic, friendly curvature to reinforce safety and emotional reassurance.

- **Standard Elements (0.5rem / 8px)**: Input fields, list tiles, and secondary buttons.
- **Containers & Cards (1rem / 16px)**: Information panels, interactive modules, and modal containers.
- **Diagram & Illustration Canvases (1.5rem / 24px)**: Outer wrappers for medical diagrams, charts, and onboarding graphics, framing complex visual data with soft, protective borders.
- **Pill Geometry (9999px)**: Chips, filter tags, badges, and primary action buttons.

## Components

### Buttons
- **Primary**: Full-bleed `#3b82f6` pill shape with crisp white text (`label-lg`), transitioning to `#1e40af` on hover.
- **Secondary**: Soft `#e0f2fe` background with `#1e40af` text and pill shape, providing clear affordance without visual competition.
- **Tertiary / Ghost**: Transparent base, `#3b82f6` text, taking a soft `#f0f9ff` wash on interaction.

### Chips & Badges
- **Status Badges**: Pill-shaped with a 0.25rem vertical and 0.75rem horizontal padding. Rendered with subtle sky tones (`#e0f2fe`) and slate navy text (`#0f172a`).
- **Interactive Filter Chips**: Outlined with 1px `#dbeafe`, filling with soft periwinkle on active states.

### Inputs & Form Fields
- Soft rounded rectangles (8px) filled with `#ffffff`, bounded by a clean 1px `#cbd5e1` outline.
- **Focus State**: Outline shifts to `#3b82f6` accompanied by a soft 3px outer glow in `rgba(59, 130, 246, 0.15)`.

### Cards & Illustration Frames
- **Clinical Cards**: `#ffffff` foundation with a 1px border of `#e2e8f0` and 16px rounded corners.
- **Illustration / Diagram Containers**: Recessed containers with a subtle `#e0f2fe` or `#f0f9ff` solid background, 24px border radius, and centered artwork framed by generous `space-lg` internal padding to emphasize legibility and calm guidance.

### Checkboxes & Radio Buttons
- Rounded geometric frames with 1.5px `#94a3b8` borders. Selected states illuminate in solid `#3b82f6` with a smooth interior micro-transition.

### Lists & Navigation Rails
- Elevated lists separated by clean `#f1f5f9` dividers. Interactive list rows feature generous 12px vertical padding and transition to a soft `#f8fafc` wash on cursor rest.