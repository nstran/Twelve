# Hoa Lu Stone AI Pack

This file is the single source of truth for generating only modular stone terrain assets for Hoa Lu.

Use it when generating:
- platform top pieces
- left and right edge pieces
- cliff body pieces
- bottom cliff pieces
- terrace pieces
- step pieces
- corner pieces
- rare floating stone accents

Do not use this file for:
- trees
- grass-only decorations
- waterfalls
- temple buildings
- full map backgrounds

## Goal

Create a reusable modular stone kit for Hoa Lu so the terrain can be assembled by hand.

The result must support:
- clear walkable tops
- straight modular connection edges
- reusable cliff body pieces
- crisp readable edges at game scale
- old mobile fantasy game style
- natural stone underside instead of plain rectangular blocks
- visible limestone breakups and chipped rock forms

The result must NOT include:
- characters
- monsters
- UI
- text
- full scene backgrounds
- blur
- photorealism

## References

Upload these 2 images:

- `reference/raw/images/bx2n6nagkdfvkvha3.jpg`
  Role: old mobile fantasy style, color direction

- `client/assets/createcs/00_ui_confirmed/stone.png`
  Role: grassy top strip and simple readable stone material

Optional:

- `reference/raw/images/oldmap2.png`
  Role: side-view terrain feeling

## Core Rules

- side view only
- one asset per image, or at most 3 assets if they belong to the same stone family
- plain black or plain sky-blue background for easy crop
- sharp crisp edges
- bright green grassy top
- pale warm limestone or soft tan rock
- flat walkable top
- straight cut side edges when the piece needs to connect
- no random bumps on connection edges
- keep only the connection edges straight, not the whole stone shape
- underside should feel like natural limestone, not a plain box
- preserve chipped rock forms and broken stone silhouette on the bottom
- no scene background

## Important Balance

This is the main balance for good stone generation:

- top and connection edges must be modular
- bottom silhouette must still feel like natural rock

Do not let Gemini simplify the asset into a plain rectangle.
The top should be usable, but the underside should still look like stylized limestone.

## Stone Types

Generate these first:

1. `platform_top_middle`
2. `platform_top_left_edge`
3. `platform_top_right_edge`
4. `cliff_body_tile`
5. `cliff_body_wide_piece`
6. `bottom_cliff_piece`
7. `long_terrace_platform`
8. `narrow_step_platform`
9. `outer_corner_cliff_piece`
10. `inner_corner_cliff_piece`

## Mini Prompts

### Stone 1 Asset

```text
Create 1 modular side-view stone platform piece for a 2D mobile game. Pale limestone, bright green grass top, flat walkable top, straight cut connection edges, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No tree, no character, no text, no full scene.
```

### Stone 3 Asset Batch

```text
Create exactly 3 modular side-view stone platform pieces for a 2D mobile game on a plain black background for easy crop: 1 center platform, 1 left edge, 1 right edge. Pale limestone, bright green grass top, flat walkable tops, straight cut connection edges, natural chipped limestone undersides, sharp crisp edges, large spacing between assets. No tree, no text, no full scene.
```

### Cliff Body Batch

```text
Create exactly 3 modular side-view cliff body stone pieces on a plain black background for easy crop. Pale limestone, no grass, straight cut side edges, natural broken limestone faces, clean reusable shapes, sharp crisp edges, large spacing between assets. No text, no full scene.
```

### Terrace / Step Batch

```text
Create exactly 3 modular side-view stone terrain pieces on a plain black background for easy crop: 1 long terrace platform, 1 narrow step platform, 1 outer corner cliff piece. Pale limestone, bright green grass top, flat walkable tops, straight cut connection edges where needed, natural chipped limestone undersides, sharp crisp edges, large spacing between assets. No text, no full scene.
```

## Per-Asset Prompts

### Platform Top Middle

```text
Create 1 modular side-view platform top middle tile. Pale limestone, bright green grass top, flat walkable top, straight cut left and right edges, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Platform Top Left Edge

```text
Create 1 modular side-view platform top left edge tile. Pale limestone, bright green grass top, clear left outer edge, straight cut right edge, flat walkable top, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Platform Top Right Edge

```text
Create 1 modular side-view platform top right edge tile. Pale limestone, bright green grass top, clear right outer edge, straight cut left edge, flat walkable top, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Cliff Body Tile

```text
Create 1 modular side-view cliff body tile. Pale limestone, no grass, straight cut left and right edges, natural broken limestone face, clean reusable rock surface, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Cliff Body Wide Piece

```text
Create 1 modular side-view wide cliff body piece. Pale limestone, no grass, straight cut left and right edges, natural broken limestone face, clean reusable rock surface, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Bottom Cliff Piece

```text
Create 1 modular side-view bottom cliff piece. Pale limestone, natural tapered and chipped bottom, straight cut side edges, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Long Terrace Platform

```text
Create 1 modular side-view long terrace platform. Pale limestone, bright green grass top, broad flat walkable top, straight cut left and right edges, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Narrow Step Platform

```text
Create 1 modular side-view narrow step platform. Pale limestone, bright green grass top, small flat walkable top, straight cut side edges, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Outer Corner Cliff Piece

```text
Create 1 modular side-view outer corner cliff piece. Pale limestone, bright green grass top, one exposed outer corner, opposite side cut straight for snapping, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Inner Corner Cliff Piece

```text
Create 1 modular side-view inner corner cliff piece. Pale limestone, bright green grass top, clear inner corner shape, modular cut outer sides, natural chipped limestone underside, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Floating Accent Stone

```text
Create 1 small side-view floating accent stone platform. Pale limestone, bright green grass top, simple readable shape, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

## Fix Prompt

Use this when the stone looks nice but cannot be assembled:

```text
Regenerate this as a true modular stone piece for a 2D side-scrolling game. The left and right connection edges must be cut straight. Keep the top flat and walkable. Keep the underside natural like chipped limestone, not a plain box. Remove random outward bumps on the connection sides. Make it sharp, crisp, and game-ready. Plain black background. No full scene.
```

## Boxy Output Fix Prompt

Use this when Gemini makes the stone too rectangular and generic:

```text
Regenerate this stone so it keeps modular straight connection edges but does not become a plain rectangle. The top must stay flat and usable, but the underside must look like natural chipped limestone with broken rock forms. Keep the style sharp, crisp, and readable for a 2D mobile game. Plain black background. No full scene.
```

## Avoid List

```text
no blur, no haze, no soft focus, no full scene, no sky, no mountains, no character, no monster, no UI, no text, no photorealism, no muddy texture, no curved connection edges, no random spikes on connection sides
```

## Approval Checklist

Accept the asset only if:

- it reads clearly in side view
- the top looks walkable
- the connection edges are straight where needed
- it stays crisp after downscaling
- it looks reusable with other stone pieces

Reject or refine if:

- the side edges are curved or bumpy
- the top is too uneven
- the silhouette is muddy
- the asset looks like part of a painting instead of a modular piece
