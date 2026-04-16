# Java Reference Usage Guide

## Purpose

This document defines how the legacy J2ME Java client must be used during restoration of **Loạn 12 Sứ Quân** into the current mobile/server stack.

The rule is strict:

- The legacy Java is a **reference specification**.
- The current TypeScript/React Native client and C#/.NET server are the **implementation targets**.
- We do **not** attempt to revive the Java codebase as production code.

This guide exists to keep future work consistent, avoid speculative rewrites, and prevent accidental drift away from the original game behavior.

## Operating Principles

### 1. Treat Java as behavior, not as code

Use decompiled Java to recover:

- packet structure
- command IDs
- tag IDs
- asset loading rules
- sprite composition rules
- map and actor behavior
- battle flow
- UI flow and menu flow
- persistence assumptions

Do not use decompiled Java as:

- production client code
- production server code
- exact naming authority
- maintainable architecture

### 2. Prefer proof over intuition

Every restoration claim should be assigned a confidence level:

- `confirmed`: directly proven by decompiled code or jar path
- `likely`: strongly suggested by multiple references
- `uncertain`: possible, but not yet proven enough for canonical naming or protocol decisions

This confidence labeling must be preserved in documentation, asset grouping, and implementation notes.

### 3. Port rules, not syntax

When porting from Java:

- preserve game behavior
- preserve data meaning
- preserve packet semantics
- preserve timing rules when known
- preserve visual composition order

Do not preserve:

- MIDlet lifecycle
- J2ME APIs
- RecordStore API shapes
- SMS/OLA/mobile carrier integrations
- obfuscated class structure

### 4. Keep a split between restored truth and modern adaptation

For every area of the game, distinguish:

- `legacy truth`: what the old game actually did
- `modern adaptation`: how we implement that in the new stack

If the new implementation intentionally differs, that deviation must be written down explicitly.

## Current Source of Truth

### Legacy reference artifacts

Primary reference inputs currently available:

- [twelvefull.jar](../twelvefull.jar)
- [ASSESSMENT.md](./ASSESSMENT.md)
- [jar-contents.txt](./jar-contents.txt)
- [decompiled](./decompiled)
- [review_assets](../review_assets)

### Current implementation targets

Primary implementation targets currently available:

- [client](../../client)
- [server](../../server)

## Legacy Reference Priority

When multiple legacy clues disagree, use this priority order.

### Priority 1: runtime behavior encoded in code paths

Examples:

- resource loader logic in `f.java`
- cache/install loader logic in `pa.java`
- character compositor logic in `mb.java`
- create-character flow in `nw.java`
- map/combat scene behavior in `om.java`
- socket and reconnect behavior in `du.java`

Why:

- These determine what the game actually did.
- Names may be obfuscated, but execution logic is still meaningful.

### Priority 2: jar structure and packaged assets

Examples:

- `jar-contents.txt`
- `canonical_from_jar_png`

Why:

- The packaged jar tells us which assets were actually bundled.
- This is stronger than guesses based on file appearance.

### Priority 3: explicit string constants

Examples:

- menu labels
- host lists
- URLs
- status messages
- hardcoded flow text

Why:

- These are useful for semantics and system identification.
- But they are weaker than executable logic.

### Priority 4: modern notes, experiments, and assumptions

Examples:

- prior rewrite docs
- current art grouping guesses
- hand-built runtime approximations

Why:

- They are useful working notes, but must yield to direct legacy evidence.

## What Each High-Value Java Area Is For

### `f.java`

Use for:

- `.mg` decoding
- packaged asset reconstruction
- runtime image loading rules

Do not use for:

- architectural guidance

### `pa.java`

Use for:

- numeric offline resource lookup
- install cache behavior
- map asset retrieval
- understanding why many assets are numeric IDs instead of semantic folders

This file is critical because it explains why `offline/*.mg` cannot be treated as naturally semantic.

### `mb.java`

Use for:

- body-part metadata parsing
- frame composition order
- body part image addressing
- appearance rendering model

This is one of the most important files in the restoration project.

### `nw.java`

Use for:

- create-character UI flow
- create-character options
- relation between appearance selections and sprite data

### `du.java` and related networking classes

Use for:

- socket behavior
- reconnect logic
- host and port expectations
- packet transport assumptions

### `om.java`

Use for:

- map scene composition
- monster/effect loading
- runtime scene behavior

### `mv.java`, `mx.java`

Use for:

- battle board visuals
- HUD and bar assets
- battle screen layering rules

## Legacy Areas That Should Not Drive Core Architecture

These may be referenced, but should not define the new architecture:

- `MGMIDlet` and MIDlet shell logic
- `RecordStore` wrapper design
- SMS and wireless messaging
- OLA social integrations
- carrier billing flows
- `platformRequest` calls

Those may matter for historical understanding, but they are not restoration priorities for a modern iOS/Android game.

## Mapping Legacy Knowledge Into The Current Codebase

### Client

Current client direction:

- React Native / Expo
- binary WebSocket transport
- custom renderers for character and monster

Relevant files:

- [Protocol.ts](../../client/src/network/Protocol.ts)
- [SocketClient.ts](../../client/src/network/SocketClient.ts)
- [CreateCharacterScreen.tsx](../../client/src/screens/character/create/CreateCharacterScreen.tsx)
- [HoaLuMapScreen.tsx](../../client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx)
- [CharacterSprite.tsx](../../client/src/engine/character/CharacterSprite.tsx)
- [MonsterSprite.tsx](../../client/src/engine/MonsterSprite.tsx)

How Java should influence client work:

- define packet payload meaning
- define what art composition is supposed to do
- define which map/battle elements are authentic
- define which current placeholders must be replaced by proper restored logic

### Server

Current server direction:

- .NET
- WebSocket for mobile
- TCP kept for legacy-compatible paths
- database-backed account/player storage

Relevant files:

- [ServiceCollectionExtensions.cs](../../server/Twelve.Application/ServiceCollectionExtensions.cs)
- [MapHandler.cs](../../server/Twelve.Application/Handlers/MapHandler.cs)
- [MoveHandler.cs](../../server/Twelve.Application/Handlers/MoveHandler.cs)
- [CreateCharacterHandler.cs](../../server/Twelve.Application/Handlers/CreateCharacterHandler.cs)
- [WebSocketGameMiddleware.cs](../../server/Twelve.Server/Middleware/WebSocketGameMiddleware.cs)
- [TcpServerService.cs](../../server/Twelve.Server/TcpServerService.cs)

How Java should influence server work:

- command coverage
- authoritative actor state
- map asset/resource protocol
- create-character selections and sprite IDs
- battle protocol and action sequencing

## Restoration Workflow Rules

Future work should follow this order.

### Step 1: Identify a behavior to restore

Examples:

- create-character sprite composition
- map entry protocol
- movement acknowledgement
- battle gem animation

### Step 2: Find the strongest legacy reference

Look first in:

- `ASSESSMENT.md`
- `decompiled`
- `jar-contents.txt`
- `review_assets`

### Step 3: Record the evidence before coding

For each restoration task, write down:

- source class
- relevant method or block
- confidence level
- current implementation gap

### Step 4: Implement in TypeScript/C#

Port the behavior into:

- React Native client
- .NET server

### Step 5: Preserve a verification note

After implementation, note:

- what was proven by Java
- what was inferred
- what remains unverified

## Rules For Asset Decisions

### Allowed

- use canonical jar path as truth
- use `verified_semantic` only for proven groupings
- keep numeric `offline` assets numeric unless proven otherwise

### Not allowed

- rename `offline` numeric ranges to semantic buckets without proof
- treat attractive visual guesses as canonical
- mix confirmed and uncertain assets into the same “official” folder

## Rules For Protocol Decisions

### Allowed

- infer packet semantics when multiple Java references align
- create compatibility notes between modern protocol enums and legacy commands

### Not allowed

- invent protocol IDs because they “seem likely”
- collapse multiple legacy commands into one modern command without documenting it

## Rules For Character Restoration

Character restoration must follow this hierarchy:

1. restore body-part composition model
2. restore appearance option IDs and metadata interpretation
3. restore create-character flow semantics
4. only then polish visuals

The correct restoration target is not “looks close enough”.
The correct target is “composition model matches legacy behavior closely enough to support future protocol and asset work”.

## Rules For Map Restoration

Map restoration must distinguish:

- packaged art
- walkability / logic layer
- actor spawn protocol
- room segmentation
- camera behavior

Do not lock map implementation to handcrafted screen art if the Java suggests dynamic or resource-driven behavior.

## Rules For Battle Restoration

Battle restoration must distinguish:

- UI art
- gem rendering
- turn logic
- action resolution
- networking model

Battle art alone is not battle restoration.

## Deliverables This Guide Expects To Exist

As restoration continues, maintain these artifacts:

- `ASSESSMENT.md`
- `JAVA_REFERENCE_USAGE.md`
- `RESTORATION_ROADMAP.md`
- asset review folders
- protocol gap notes
- feature-specific verification notes

## Non-Negotiable Constraints

- Do not delete evidence that is still useful.
- Do not label uncertain behavior as canonical.
- Do not let the current rewrite drift too far from legacy truth without documenting the deviation.
- Do not allow a temporary placeholder implementation to silently become permanent.

## Practical Summary

The correct mental model is:

- Java tells us **what the old game meant and how it behaved**
- the current mobile/server code decides **how we realize that behavior today**

If future work follows that split consistently, the project can move fast without losing authenticity.
