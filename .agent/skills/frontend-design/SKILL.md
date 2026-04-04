# Game Frontend Design (React Native + Skia)

## 🏗️ Structure & components
- **Navigation**: Use `react-navigation` (Stack/Tabs).
- **Core Screen**: 
  - Standard UI: React Native components (View, Text, Image).
  - Game World/Battle: `Canvas` from `react-native-skia`.
- **Hybrid Approach**: Use standard UI for menus/shop/profile; use Skia for the "side-scrolling" map and "Match-3" board.

## 🎨 Performance & Graphics (Skia)
- **Framerate**: Target 60FPS for all Skia-based screens.
- **Sprite Animation**: Use `useClock` and `useValue` for smooth sprite transitions.
- **Batched Rendering**: Draw map tiles in a single pass to minimize draw calls.
- **HD Assets**: Always use `@2x` or `@3x` assets for modern displays.

## 📝 State & Logic
- **Store**: Use **Zustand** for lightweight global state (Player info, Currencies).
- **Hooks**: Use custom hooks for socket communication (e.g., `useGameSocket`).
- **Optimization**: Use `React.memo` and `useCallback` to prevent unnecessary re-renders in the main game loop.

## 🧹 UI Aesthetics
- **Theme**: "Technical, Sharp" (No Purple). 
- **Modernization**: Use gradients, glassmorphism, and subtle micro-animations for a premium 2024 feel.
- **Typography**: Inter or Roboto (Modern sans-serif).
