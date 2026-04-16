# Restoration Roadmap

## Goal

Restore **Loạn 12 Sứ Quân** into a maintainable modern codebase for iOS and Android, using:

- React Native / Expo client
- .NET server
- legacy Java as behavioral reference
- recovered jar assets as art and protocol evidence

This roadmap is designed to be followed over time, not just as a one-time note.

## Strategic Outcome

There are three realistic restoration levels.

### Level A: Playable vertical slice

Features:

- login/register
- create character
- enter one map
- movement
- visible monsters / actors
- basic battle scene

Status:

- partially present already

### Level B: Coherent alpha

Features:

- stable core protocol
- restored create-character model
- one real map loop
- actor sync
- battle rules beyond visuals
- persistence for core player state

Status:

- not done yet, but achievable with current materials

### Level C: Authentic online restoration

Features:

- broader packet coverage
- multiple maps / rooms
- fuller combat behavior
- stronger asset identity recovery
- server-authoritative game state
- progression loop closer to legacy

Status:

- long-term goal

## Core Workstreams

All future work should be organized into these workstreams.

### 1. Legacy protocol recovery

Output:

- legacy command registry
- tag registry
- packet examples
- mapping to current client/server protocol

Primary references:

- `du.java`
- related network classes
- current `Protocol.ts`
- current `SocketClient.ts`

Success criterion:

- a documented protocol matrix exists for current commands and target commands

### 2. Asset and resource recovery

Output:

- canonical jar-derived assets
- verified semantic buckets
- unresolved numeric cache inventory
- known character body-part ranges

Primary references:

- `f.java`
- `pa.java`
- `jar-contents.txt`
- `review_assets`

Success criterion:

- no important runtime asset class is “mystery data” for the gameplay slice being built

### 3. Character restoration

Output:

- create-character flow aligned with legacy logic
- appearance option mapping
- body-part compositor port
- server-side canonical appearance state

Primary references:

- `mb.java`
- `nw.java`
- current create-character screen
- current create-character server handler

Success criterion:

- new characters are composed using a legacy-faithful model rather than ad hoc sprite layering

### 4. Map restoration

Output:

- one authentic map loop
- map art + logic layer + actor spawn model
- walkability and room logic
- map resource loading model

Primary references:

- `om.java`
- `pa.java`
- current map screen
- current `MapHandler`

Success criterion:

- player can enter a map whose logic and actor model are not merely hardcoded placeholder behavior

### 5. Battle restoration

Output:

- battle UI aligned to legacy art behavior
- turn state model
- action sequencing
- client/server battle packet model

Primary references:

- `mv.java`
- `mx.java`
- other battle-related decompiled classes
- current battle UI and hooks

Success criterion:

- battle is governed by explicit game rules, not just client-side animation staging

### 6. Server authority and persistence

Output:

- authoritative session/player state
- map state handling
- movement validation
- battle state persistence where needed

Primary references:

- legacy behavior inferred from Java
- current handlers and repositories

Success criterion:

- server owns important truth; client is not the sole source of state

## Phased Plan

## Phase 0: Documentation and evidence lock

Objective:

- stop project drift
- define what is confirmed vs inferred

Tasks:

- maintain `ASSESSMENT.md`
- maintain `JAVA_REFERENCE_USAGE.md`
- maintain `RESTORATION_ROADMAP.md`
- preserve canonical and verified asset review folders
- define current protocol coverage and missing areas

Deliverables:

- stable restoration docs
- shared terminology for confirmed / likely / uncertain

Acceptance criteria:

- future contributors can tell what is canonical without asking

## Phase 1: Protocol baseline

Objective:

- make the transport and command layer trustworthy enough for future restoration

Tasks:

- audit current commands in `Protocol.ts`
- map current commands to known legacy commands where possible
- document unsupported legacy commands
- add packet fixtures for commands already supported
- normalize current magic command usage in handlers

Current evidence:

- current protocol subset in [client/src/network/Protocol.ts](../../client/src/network/Protocol.ts)
- dispatcher registration in [server/Twelve.Application/ServiceCollectionExtensions.cs](../../server/Twelve.Application/ServiceCollectionExtensions.cs)

Deliverables:

- `protocol_matrix.md` or equivalent future note
- packet test fixtures
- fewer unexplained command numbers in server/client code

Acceptance criteria:

- current login / create-character / map / move flows are documented and testable

## Phase 2: Character system recovery

Objective:

- replace approximate character creation/rendering with a model aligned to legacy logic

Tasks:

- port body-part metadata parsing behavior from `mb.java`
- recover the meaning of current appearance ranges enough for actual composer use
- redesign create-character client to operate on recovered appearance metadata
- update server character creation payloads to align with restored model
- persist canonical appearance identifiers, not just UI indexes

Current gap:

- current create-character screen uses handcrafted layer arrays and simplified fields
- current server uses simplified tags and stores a subset of appearance values

Primary code targets:

- [client/src/screens/character/create/CreateCharacterScreen.tsx](../../client/src/screens/character/create/CreateCharacterScreen.tsx)
- [server/Twelve.Application/Handlers/CreateCharacterHandler.cs](../../server/Twelve.Application/Handlers/CreateCharacterHandler.cs)

Deliverables:

- character compositor module
- appearance metadata loader
- character preview that is closer to legacy composition

Acceptance criteria:

- one created character renders consistently across create screen, map, and battle contexts

## Phase 3: One authentic map loop

Objective:

- turn the current placeholder Hoa Lư loop into a structured restored gameplay slice

Tasks:

- align map payload semantics with legacy evidence
- restore room/actor assumptions where possible
- separate map art from walkability logic
- move from static placeholder actors to server-owned actor model
- add reproducible spawn/state update behavior

Current gap:

- `MapHandler` is mostly handcrafted
- movement is echoed rather than authoritative

Primary code targets:

- [server/Twelve.Application/Handlers/MapHandler.cs](../../server/Twelve.Application/Handlers/MapHandler.cs)
- [server/Twelve.Application/Handlers/MoveHandler.cs](../../server/Twelve.Application/Handlers/MoveHandler.cs)
- [client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx](../../client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx)

Deliverables:

- restored map payload model for one area
- actor sync messages
- map-specific asset loader or registry

Acceptance criteria:

- player enters map, sees authentic-enough actors, moves under server constraints, and remains synchronized

## Phase 4: Battle rule restoration

Objective:

- convert battle from a mainly visual/mobile remake into a game system grounded in legacy rules

Tasks:

- inventory battle packets and Java battle classes
- define state machine for battle
- clarify local vs server-resolved actions
- restore bar, gem, and effect usage according to legacy intent
- align combat actor presentation with restored character/monster assets

Current gap:

- battle UI exists, but gameplay rule fidelity is not yet proven

Primary code targets:

- battle hooks and UI in `client/src/screens/battle`
- future server battle handlers

Deliverables:

- battle rules doc
- first server-aware battle loop
- verified battle packet set

Acceptance criteria:

- battle outcome is rule-driven and reproducible, not just animation-driven

## Phase 5: Progression and broader online loop

Objective:

- expand from vertical slice to coherent alpha

Tasks:

- persist map position and meaningful player state
- add more commands from legacy reference
- add inventory/equipment or priority progression features
- add additional maps or map rooms
- refine actor categories, monsters, and interaction flows

Deliverables:

- alpha progression loop
- additional packet coverage
- more complete player model

Acceptance criteria:

- player can progress through more than one isolated demo flow

## Phase 6: Authenticity pass

Objective:

- reduce deviation from legacy behavior where recovery evidence exists

Tasks:

- revisit uncertain asset groups
- compare modern implementations against Java-derived behavior
- reduce placeholder logic
- document intentional deviations

Deliverables:

- authenticity review checklist
- deviation log

Acceptance criteria:

- major deviations are intentional and documented, not accidental

## Immediate Priority Backlog

These are the most valuable next tasks in order.

### P1. Build protocol gap matrix

Reason:

- protocol is the spine of restoration

Output:

- list of current commands
- list of known legacy commands
- missing commands
- confidence per mapping

### P2. Port character composition model

Reason:

- this unlocks correct create-character, map character render, and later battle identity

Output:

- reusable client character composition module

### P3. Replace placeholder map state with actor model

Reason:

- current map loop is too static to support authentic online restoration

Output:

- server-owned actor representation and sync

### P4. Formalize asset registry

Reason:

- current asset knowledge is still scattered

Output:

- one registry that says which assets are canonical, verified, likely, or unresolved

### P5. Battle rules audit

Reason:

- battle is likely the hardest system to “look finished” while still being semantically wrong

Output:

- battle state machine note

## Delivery Strategy

### What to ship first

Ship in this order:

1. reliable login/create-character/map slice
2. reliable character composition
3. reliable movement and actor sync
4. reliable battle rule subset

### What not to front-load

Do not front-load:

- cosmetic perfection
- social features
- SMS or billing compatibility
- speculative asset relabeling

## Risks

### Risk 1: Protocol drift

If modern commands diverge too far from legacy semantics too early, later restoration gets more expensive.

Mitigation:

- maintain protocol matrix
- document every deviation

### Risk 2: Asset mislabeling

If uncertain asset ranges are mislabeled as canonical, later work will be built on false assumptions.

Mitigation:

- keep numeric cache identity unless proven otherwise
- maintain confidence labeling

### Risk 3: Placeholder logic becoming permanent

Map and battle placeholder code can look “good enough” and then block faithful restoration later.

Mitigation:

- mark placeholders explicitly
- keep a deviation log

### Risk 4: Porting too much Java structure

If the new code mirrors the obfuscated Java layout too literally, maintainability collapses.

Mitigation:

- port semantics into modern modules, not legacy class topology

## Decision Rules

When facing uncertainty:

### If behavior is confirmed in Java

- follow Java behavior unless there is a documented reason not to

### If behavior is only likely

- implement behind a clearly marked assumption
- record the assumption and source

### If behavior is uncertain

- do not bake it into canonical protocol or asset naming
- isolate it behind a review note or candidate bucket

## Definition of Success

The project is succeeding if:

- each new feature is grounded in evidence
- the mobile client becomes more authentic without inheriting J2ME technical debt
- the server becomes more authoritative over time
- assets and protocol become less mysterious with each phase
- the team can explain exactly what is restored versus approximated

## Practical Summary

The restoration path is:

1. lock evidence
2. stabilize protocol
3. restore character composition
4. restore one real map loop
5. restore battle rules
6. expand online gameplay
7. run authenticity pass

That sequence gives the best chance of shipping a playable modern mobile game while still staying faithful to the original.
