# Character Creation Reconstruction

This is the top-level entrypoint for the legacy create-character restoration work.

If you want the deep technical reference, use:

- [reference/redecoded/CHARACTER_CREATION_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/CHARACTER_CREATION_RECONSTRUCTION.md)

## Quick Position

The create-character system must be rebuilt from the old Java runtime model, not from guessed PNG labels.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/createcs = working asset input for the new client`
- organize and port by `.meta family`, not by guessed labels like `hair` or `helmet`

## Main Working Folders

- [client/assets/createcs](/d:/Twelve/client/assets/createcs)
- [reference/review_assets/character_creation_organized](/d:/Twelve/reference/review_assets/character_creation_organized)
- [reference/redecoded/character_meta_parsed.csv](/d:/Twelve/reference/redecoded/character_meta_parsed.csv)

## Confirmed Runtime Pieces

These are safe to build around first:

- `/createcs/bk` and `/createcs/stone`
  - [nw.java](/d:/Twelve/reference/redecoded/decompiled/nw.java:46)
  - [nw.java](/d:/Twelve/reference/redecoded/decompiled/nw.java:47)
- `79899`
- `79999`
- `89999`
- `99000` through `99009`
- `99099`

The compositor body base comes from `99000 + frameGroup`:

- [mb.java](/d:/Twelve/reference/redecoded/decompiled/mb.java:108)

The create-character flow instantiates the core families here:

- [nw.java](/d:/Twelve/reference/redecoded/decompiled/nw.java:242)
- [nw.java](/d:/Twelve/reference/redecoded/decompiled/nw.java:244)
- [nw.java](/d:/Twelve/reference/redecoded/decompiled/nw.java:245)

## Candidate Families

These are valid `.meta` families and should stay available, but not be over-labeled yet.

Appearance-side candidates:

- `90999`
- `91099`
- `91299`
- `98099`
- `98199`
- `98299`

Headgear or equipment-side candidates:

- `94399`
- `97199`
- `97499`
- `97599`
- `98399`
- `99999`

Do not rename these to final labels like `hair`, `face`, or `helmet` until the new preview renderer confirms behavior.

## Folder Reading Order

Use this order when working:

1. [00_ui_confirmed](/d:/Twelve/client/assets/createcs/00_ui_confirmed)
2. [01_core_compositor](/d:/Twelve/client/assets/createcs/01_core_compositor)
3. [02_option_meta_families](/d:/Twelve/client/assets/createcs/02_option_meta_families)

## Port Order

1. Rebuild create-character UI from `bk.png` and `stone.png`.
2. Render body sheets from `990xx`.
3. Add `79899`, `79999`, and `89999`.
4. Verify gender switching and preview parity.
5. Add candidate families one group at a time.

## Next Practical Step

The next coding step should be a preview renderer for `createcs`.

That is the point where candidate families can start being promoted into final semantic roles.
