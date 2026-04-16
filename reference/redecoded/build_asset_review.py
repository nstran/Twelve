#!/usr/bin/env python3
import csv
import shutil
import struct
import zipfile
from pathlib import Path


JAR_PATH = Path("reference/twelvefull.jar")
OUTPUT_ROOT = Path("reference/review_assets")
CANONICAL_DIR = OUTPUT_ROOT / "canonical_from_jar_png"
VERIFIED_DIR = OUTPUT_ROOT / "verified_semantic"

PNG_SIGNATURE = b"\x89PNG"


def reset_dir(path: Path) -> None:
    if path.exists():
        shutil.rmtree(path)
    path.mkdir(parents=True, exist_ok=True)


def decode_mg(payload: bytes) -> bytes:
    if len(payload) < 8:
        raise ValueError("Invalid .mg payload: too short")
    expected_size = struct.unpack(">I", payload[:4])[0]
    decoded = PNG_SIGNATURE + payload[4:]
    if expected_size != len(decoded):
        raise ValueError(
            f"Decoded PNG size mismatch: header={expected_size} actual={len(decoded)}"
        )
    return decoded


def extract_canonical() -> list[tuple[str, Path]]:
    rows: list[tuple[str, Path]] = []
    with zipfile.ZipFile(JAR_PATH, "r") as jar_file:
        for info in jar_file.infolist():
            if info.is_dir() or not info.filename.lower().endswith(".mg"):
                continue
            decoded = decode_mg(jar_file.read(info))
            target = CANONICAL_DIR / Path(info.filename).with_suffix(".png")
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes(decoded)
            rows.append((info.filename, target))
    return rows


def copy_file(source: Path, target: Path) -> None:
    target.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(source, target)


def build_verified() -> list[dict[str, str]]:
    rows: list[dict[str, str]] = []

    for png in sorted(CANONICAL_DIR.rglob("*.png")):
        relative = png.relative_to(CANONICAL_DIR)
        parts = relative.parts
        top = parts[0]
        stem = png.stem

        destinations: list[tuple[Path, str]] = []

        if len(parts) == 1:
            destinations.append(
                (
                    VERIFIED_DIR / "root_named_assets" / relative.name,
                    "Root-level named asset from jar; exact semantic role may still require code review.",
                )
            )
        elif top == "createcs":
            destinations.append(
                (
                    VERIFIED_DIR / "create_character_ui_confirmed" / relative.name,
                    "Loaded directly by the create-character screen.",
                )
            )
        elif top == "play":
            destinations.append(
                (
                    VERIFIED_DIR / "board_play_ui_confirmed" / relative.name,
                    "Loaded directly from /play/* by the board/battle UI.",
                )
            )
        elif top == "m":
            destinations.append(
                (
                    VERIFIED_DIR / "board_match_ui_confirmed" / relative.name,
                    "Loaded directly from /m/* by the match UI.",
                )
            )
        elif top == "info":
            destinations.append(
                (
                    VERIFIED_DIR / "info_icons_confirmed" / relative.name,
                    "Loaded directly from /info/* by HUD and status screens.",
                )
            )
        elif top == "corner":
            destinations.append(
                (
                    VERIFIED_DIR / "window_frames_confirmed" / relative.name,
                    "Loaded directly from /corner/* for frame drawing.",
                )
            )
        elif top == "dialog":
            destinations.append(
                (
                    VERIFIED_DIR / "window_frames_confirmed" / relative.name,
                    "Loaded directly from /dialog/* for frame drawing.",
                )
            )
        elif top == "f":
            destinations.append(
                (
                    VERIFIED_DIR / "version_tags_confirmed" / relative.name,
                    "Loaded directly from /f/* as version label assets.",
                )
            )
        elif top == "offline":
            destinations.append(
                (
                    VERIFIED_DIR / "offline_by_id_or_name" / relative.name,
                    "Canonical offline cache asset; jar stores these by numeric or named cache key.",
                )
            )
            if stem.isdigit() and 99000 <= int(stem) <= 99099:
                destinations.append(
                    (
                        VERIFIED_DIR / "character_body_990xx_confirmed" / relative.name,
                        "Confirmed body-part base range used by the character compositor.",
                    )
                )
        else:
            destinations.append(
                (
                    VERIFIED_DIR / "unassigned_review" / relative.name,
                    "No stricter verified semantic bucket was assigned.",
                )
            )

        for destination, rationale in destinations:
            copy_file(png, destination)
            rows.append(
                {
                    "canonical_path": relative.as_posix(),
                    "verified_path": destination.relative_to(VERIFIED_DIR).as_posix(),
                    "rationale": rationale,
                }
            )

    return rows


def write_csv(path: Path, rows: list[dict[str, str]], fieldnames: list[str]) -> None:
    with path.open("w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


def write_readmes(canonical_rows: list[tuple[str, Path]], verified_rows: list[dict[str, str]]) -> None:
    canonical_text = """# Canonical From Jar

This folder is the strictest PNG reconstruction of `.mg` assets from `reference/twelvefull.jar`.

Rules:
- Path structure matches the jar path exactly.
- Only the file extension changes: `.mg` -> `.png`.
- No semantic regrouping is applied here.

Use this folder when reviewing what the original packaged client actually contains.
"""
    (CANONICAL_DIR / "README.md").write_text(canonical_text, encoding="utf-8")

    verified_text = """# Verified Semantic

This folder contains only review buckets that are defensible from the jar path or decompiled client behavior.

Rules:
- If an asset has an original jar folder such as `/play`, `/m`, `/info`, `/createcs`, `/corner`, or `/dialog`, it can be grouped by that confirmed usage.
- `/offline/*` assets are treated as cache resources first, not as semantic folders.
- Only the `990xx` offline range is promoted into a stronger character body bucket because the body compositor uses that base range directly.

Use this folder when reviewing only what is confirmed, without speculative labels.
"""
    (VERIFIED_DIR / "README.md").write_text(verified_text, encoding="utf-8")

    root_text = f"""# Review Assets

Generated from `{JAR_PATH.as_posix()}`.

Folders:
- `canonical_from_jar_png/`: exact jar path reconstruction
- `verified_semantic/`: only high-confidence review buckets

Counts:
- canonical PNG files: {len(canonical_rows)}
- verified entries: {len(verified_rows)}
"""
    (OUTPUT_ROOT / "README.md").write_text(root_text, encoding="utf-8")


def main() -> int:
    OUTPUT_ROOT.mkdir(parents=True, exist_ok=True)
    reset_dir(CANONICAL_DIR)
    reset_dir(VERIFIED_DIR)

    canonical_rows = extract_canonical()
    verified_rows = build_verified()

    write_csv(
        OUTPUT_ROOT / "canonical_index.csv",
        [{"jar_path": jar_path, "png_path": png.relative_to(OUTPUT_ROOT).as_posix()} for jar_path, png in canonical_rows],
        ["jar_path", "png_path"],
    )
    write_csv(
        OUTPUT_ROOT / "verified_index.csv",
        verified_rows,
        ["canonical_path", "verified_path", "rationale"],
    )
    write_readmes(canonical_rows, verified_rows)

    print(f"canonical_png_count={len(canonical_rows)}")
    print(f"verified_entry_count={len(verified_rows)}")
    print(f"output_root={OUTPUT_ROOT}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
