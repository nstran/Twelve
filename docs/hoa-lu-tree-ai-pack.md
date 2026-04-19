# Hoa Lu Tree AI Pack

This file is the single source of truth for generating only modular vegetation assets for Hoa Lu.

Use it when generating:
- small trees
- bonsai-like trees
- leaning cliff trees
- banana trees
- palm-like trees
- grass clumps
- bushes
- small platform plants

Do not use this file for:
- stone terrain
- waterfalls
- temple buildings
- full map backgrounds

## Goal

Create a reusable vegetation kit for Hoa Lu that can be placed on top of stone platforms without blocking gameplay.

The result must support:
- small readable trees
- compact silhouettes
- platform-friendly grass and bushes
- old mobile fantasy game style
- crisp readable edges at game scale

The result must NOT include:
- characters
- monsters
- UI
- text
- full scene backgrounds
- blur
- photorealism

## References

Upload this image:

- `reference/raw/images/bx2n6nagkdfvkvha3.jpg`
  Role: old mobile fantasy style, color direction

Optional:

- `reference/raw/images/oldmap2.png`
  Role: side-view map feeling

## Core Rules

- side view only
- one asset per image, or at most 3 assets if they belong to the same vegetation family
- plain black or plain sky-blue background for easy crop
- sharp crisp edges
- compact readable silhouette
- small to medium size
- suitable for standing on a platform without blocking movement
- no scene background

## Vegetation Types

Generate these first:

1. `small_bonsai_tree`
2. `leaning_cliff_tree`
3. `banana_tree`
4. `palm_like_tree`
5. `short_round_canopy_tree`
6. `windswept_platform_tree`
7. `grass_clump_small`
8. `grass_clump_medium`
9. `bush_small`
10. `bush_round`

## Mini Prompts

### Tree 1 Asset

```text
Create 1 small side-view fantasy tree for a 2D mobile game. Compact readable silhouette, sharp crisp edges, old mobile fantasy style, plain black background for easy crop. No character, no text, no full scene.
```

### Tree 3 Asset Batch

```text
Create exactly 3 small side-view fantasy tree assets for a 2D mobile game on a plain black background for easy crop. The 3 trees should be different variations from the same visual family, with large spacing between assets, sharp crisp edges, and compact readable silhouettes. No text, no full scene.
```

### Grass / Bush Batch

```text
Create exactly 3 small side-view vegetation assets for a 2D mobile game on a plain black background for easy crop: 1 small grass clump, 1 medium grass clump, 1 small bush. Sharp crisp edges, compact readable silhouettes, old mobile fantasy style, large spacing between assets. No text, no full scene.
```

## Per-Asset Prompts

### Small Bonsai Tree

```text
Create 1 small side-view bonsai-like fantasy tree. Curved trunk, compact canopy, sharp crisp edges, old mobile fantasy style, plain black background for easy crop. No text, no full scene.
```

### Leaning Cliff Tree

```text
Create 1 small side-view leaning cliff tree. Elegant curved trunk, compact foliage, sharp crisp edges, old mobile fantasy style, plain black background for easy crop. No text, no full scene.
```

### Banana Tree

```text
Create 1 small side-view banana tree for a 2D mobile game. Compact readable trunk and leaves, tropical fantasy style, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Palm-Like Tree

```text
Create 1 small side-view palm-like fantasy tree. Compact readable trunk and leaf shapes, sharp crisp edges, old mobile fantasy style, plain black background for easy crop. No text, no full scene.
```

### Short Round Canopy Tree

```text
Create 1 short side-view fantasy tree with a rounded canopy. Compact readable silhouette, sharp crisp edges, old mobile fantasy style, plain black background for easy crop. No text, no full scene.
```

### Windswept Platform Tree

```text
Create 1 small side-view windswept fantasy tree. Compact canopy shaped by mountain wind, sharp crisp edges, old mobile fantasy style, plain black background for easy crop. No text, no full scene.
```

### Small Grass Clump

```text
Create 1 small side-view grass clump for a 2D mobile game. Bright green, compact readable shape, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Medium Grass Clump

```text
Create 1 medium side-view grass clump for a 2D mobile game. Bright green, platform-friendly shape, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Small Bush

```text
Create 1 small side-view fantasy bush for a 2D mobile game. Compact readable foliage, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

### Round Bush

```text
Create 1 round side-view fantasy bush for a 2D mobile game. Compact readable foliage, sharp crisp edges, plain black background for easy crop. No text, no full scene.
```

## Fix Prompt

Use this when the tree or grass looks too big or too blurry:

```text
Regenerate this as a smaller, sharper, more game-readable side-view vegetation asset for a 2D side-scrolling game. Keep the silhouette compact and clean. Do not make it oversized or painterly. Plain black background. No full scene.
```

## Avoid List

```text
no blur, no haze, no soft focus, no full scene, no sky, no mountains, no character, no monster, no UI, no text, no photorealism, no oversized canopy, no giant roots, no muddy silhouette
```

## Approval Checklist

Accept the asset only if:

- it reads clearly in side view
- it stays crisp after downscaling
- it does not block too much platform space
- it matches the old mobile fantasy style
- it is easy to crop and place on the map

Reject or refine if:

- it is too large
- it is blurry
- the silhouette is muddy
- it looks like part of a full painting instead of a modular asset
