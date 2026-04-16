from __future__ import annotations

import csv
import json
import shutil
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
SRC_CANONICAL = ROOT / "reference" / "review_assets" / "canonical_from_jar_png"
SRC_OFFLINE = SRC_CANONICAL / "offline"
SRC_CREATECS = SRC_CANONICAL / "createcs"
PARSED_META_JSON = ROOT / "reference" / "redecoded" / "character_meta_parsed.json"
EXTRACTED_META_DIR = ROOT / "reference" / "redecoded" / "extracted_meta" / "offline"
TARGET = ROOT / "reference" / "review_assets" / "character_creation_organized"
CLIENT_TARGET = ROOT / "client" / "assets" / "createcs_legacy"


META_FAMILIES: list[dict[str, str | int]] = [
    {
        "meta_id": 79899,
        "category": "01_core_compositor/gender_base_candidates",
        "label": "meta_79899_base_79800_candidate",
        "confidence": "confirmed_runtime",
        "note": "Instantiated directly by nw.java as one of the two gender base metadata families.",
    },
    {
        "meta_id": 79999,
        "category": "01_core_compositor/gender_base_candidates",
        "label": "meta_79999_base_79900_candidate",
        "confidence": "confirmed_runtime",
        "note": "Instantiated directly by nw.java as one of the two gender base metadata families.",
    },
    {
        "meta_id": 89999,
        "category": "01_core_compositor/default_overlay_candidates",
        "label": "meta_89999_base_89900_candidate",
        "confidence": "confirmed_runtime",
        "note": "Instantiated directly by nw.java as the default overlay family used by the create-character compositor.",
    },
    {
        "meta_id": 90999,
        "category": "02_option_meta_families/appearance_candidates",
        "label": "meta_90999_base_90900_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed appearance candidate family; likely hair or head overlay.",
    },
    {
        "meta_id": 91099,
        "category": "02_option_meta_families/appearance_candidates",
        "label": "meta_91099_base_91000_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed appearance candidate family; likely hair or head overlay.",
    },
    {
        "meta_id": 91299,
        "category": "02_option_meta_families/appearance_candidates",
        "label": "meta_91299_base_91200_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed appearance candidate family; likely face or front-head overlay.",
    },
    {
        "meta_id": 98099,
        "category": "02_option_meta_families/appearance_candidates",
        "label": "meta_98099_base_98000_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed appearance candidate family; visually plausible ornate hair set.",
    },
    {
        "meta_id": 98199,
        "category": "02_option_meta_families/appearance_candidates",
        "label": "meta_98199_base_98100_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed appearance candidate family; visually plausible ornate hair set.",
    },
    {
        "meta_id": 98299,
        "category": "02_option_meta_families/appearance_candidates",
        "label": "meta_98299_base_98200_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed appearance candidate family; visually plausible ornate hair set.",
    },
    {
        "meta_id": 94399,
        "category": "02_option_meta_families/headgear_equipment_candidates",
        "label": "meta_94399_base_94300_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed equipment candidate family; likely headgear rather than bare create-character appearance.",
    },
    {
        "meta_id": 97199,
        "category": "02_option_meta_families/headgear_equipment_candidates",
        "label": "meta_97199_base_97100_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed equipment candidate family; likely headgear rather than bare create-character appearance.",
    },
    {
        "meta_id": 97499,
        "category": "02_option_meta_families/headgear_equipment_candidates",
        "label": "meta_97499_base_97400_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed equipment candidate family; likely headgear rather than bare create-character appearance.",
    },
    {
        "meta_id": 97599,
        "category": "02_option_meta_families/headgear_equipment_candidates",
        "label": "meta_97599_base_97500_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed equipment candidate family; likely headgear rather than bare create-character appearance.",
    },
    {
        "meta_id": 98399,
        "category": "02_option_meta_families/headgear_equipment_candidates",
        "label": "meta_98399_base_98300_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed equipment candidate family; likely headgear rather than bare create-character appearance.",
    },
    {
        "meta_id": 99999,
        "category": "02_option_meta_families/headgear_equipment_candidates",
        "label": "meta_99999_base_99900_candidate",
        "confidence": "meta_group_candidate",
        "note": "Meta-backed seasonal or special equipment candidate family; not treated as base create-character appearance.",
    },
]


def copy_file(src: Path, dst: Path) -> None:
    dst.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(src, dst)


def load_parsed_meta() -> dict[int, dict]:
    if not PARSED_META_JSON.exists():
        raise FileNotFoundError(
            f"Missing parsed meta file: {PARSED_META_JSON}. Run tools/parse_offline_meta.py first."
        )
    data = json.loads(PARSED_META_JSON.read_text(encoding="utf-8"))
    return {int(item["meta_id"]): item for item in data}


def build_readme(base: Path) -> None:
    content = """# Character Creation Asset Set

This folder is organized around the legacy Java compositor architecture.

The key rule is:

- organize by `meta family` when possible
- keep `confirmed runtime pieces` separate from `meta-backed candidate families`
- separate `appearance candidates` from `headgear/equipment candidates`

## Folder layout

- `00_ui_confirmed`
  - direct create-character UI assets from `/createcs/*`
- `01_core_compositor`
  - assets directly proven to participate in the legacy create-character compositor
- `02_option_meta_families`
  - candidate option families grouped by legacy `.meta` units

## Why this structure is better

The legacy Java client does not build create-character by browsing random PNG files.
It builds from:

- metadata families like `79899`, `79999`, `89999`
- base image sheets such as `99000 + frameGroup`
- additional option families resolved through server-provided IDs

That means the most stable organizational unit is the `.meta` family, not a guessed visual label.

## Confidence levels

- `confirmed_runtime`
  - directly instantiated or referenced by the legacy Java create-character runtime
- `meta_group_candidate`
  - valid legacy `.meta` family with coherent sprite sheets, but semantic role is still not fully proven

## Important limitation

This is the strongest organization possible from the local legacy client and jar alone.
It is stronger than raw visual clustering, but it is still not identical to the original live server catalog.
"""
    (base / "README.md").write_text(content, encoding="utf-8")


def build_index(base: Path, rows: list[dict[str, str]]) -> None:
    index_path = base / "index.csv"
    with index_path.open("w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(
            handle,
            fieldnames=["source", "target", "confidence", "note"],
        )
        writer.writeheader()
        writer.writerows(rows)


def build_ui(base: Path, rows: list[dict[str, str]]) -> None:
    for name in ("bk.png", "stone.png"):
        src = SRC_CREATECS / name
        dst = base / "00_ui_confirmed" / name
        copy_file(src, dst)
        rows.append(
            {
                "source": src.relative_to(ROOT).as_posix(),
                "target": dst.relative_to(ROOT).as_posix(),
                "confidence": "confirmed_runtime",
                "note": "Loaded directly by nw.java from /createcs/*.",
            }
        )


def build_body_sheets(base: Path, rows: list[dict[str, str]]) -> None:
    ids = list(range(99000, 99010)) + [99099]
    for asset_id in ids:
        src = SRC_OFFLINE / f"{asset_id}.png"
        dst = base / "01_core_compositor" / "body_sheet_family_990xx_confirmed" / src.name
        copy_file(src, dst)
        rows.append(
            {
                "source": src.relative_to(ROOT).as_posix(),
                "target": dst.relative_to(ROOT).as_posix(),
                "confidence": "confirmed_runtime",
                "note": "Confirmed body sheet family used by mb.java via 99000 + frameGroup.",
            }
        )


def write_meta_summary(folder: Path, meta_summary: dict, family_info: dict) -> None:
    summary = {
        "meta_id": meta_summary["meta_id"],
        "base_image_id": meta_summary["base_image_id"],
        "family_count": meta_summary["family_count"],
        "version": meta_summary["version"],
        "category": family_info["category"],
        "label": family_info["label"],
        "confidence": family_info["confidence"],
        "note": family_info["note"],
    }
    (folder / "meta_summary.json").write_text(
        json.dumps(summary, indent=2), encoding="utf-8"
    )


def build_meta_families(base: Path, rows: list[dict[str, str]], parsed_meta: dict[int, dict]) -> None:
    for family in META_FAMILIES:
        meta_id = int(family["meta_id"])
        meta_summary = parsed_meta[meta_id]
        base_image_id = int(meta_summary["base_image_id"])
        family_root = base / str(family["category"]) / str(family["label"])
        images_root = family_root / "images"
        images_root.mkdir(parents=True, exist_ok=True)

        meta_src = EXTRACTED_META_DIR / f"{meta_id}.meta"
        meta_dst = family_root / f"{meta_id}.meta"
        copy_file(meta_src, meta_dst)
        rows.append(
            {
                "source": meta_src.relative_to(ROOT).as_posix(),
                "target": meta_dst.relative_to(ROOT).as_posix(),
                "confidence": str(family["confidence"]),
                "note": "Original legacy .meta file extracted from twelvefull.jar.",
            }
        )

        for asset_id in range(base_image_id, base_image_id + 10):
            src = SRC_OFFLINE / f"{asset_id}.png"
            dst = images_root / src.name
            copy_file(src, dst)
            rows.append(
                {
                    "source": src.relative_to(ROOT).as_posix(),
                    "target": dst.relative_to(ROOT).as_posix(),
                    "confidence": str(family["confidence"]),
                    "note": str(family["note"]),
                }
            )

        write_meta_summary(family_root, meta_summary, family)


def to_client_rows(reference_rows: list[dict[str, str]]) -> list[dict[str, str]]:
    out = []
    for row in reference_rows:
        rel = Path(row["target"]).relative_to("reference/review_assets/character_creation_organized")
        out.append(
            {
                "source": row["source"],
                "target": str(Path("client/assets/createcs_legacy") / rel).replace("\\", "/"),
                "confidence": row["confidence"],
                "note": row["note"],
            }
        )
    return out


def main() -> None:
    parsed_meta = load_parsed_meta()

    for base in (TARGET, CLIENT_TARGET):
        if base.exists():
            shutil.rmtree(base)
        base.mkdir(parents=True, exist_ok=True)

    reference_rows: list[dict[str, str]] = []

    for base in (TARGET, CLIENT_TARGET):
        local_rows: list[dict[str, str]] = []
        build_ui(base, local_rows)
        build_body_sheets(base, local_rows)
        build_meta_families(base, local_rows, parsed_meta)
        build_readme(base)
        build_index(base, local_rows if base == TARGET else to_client_rows(reference_rows))
        if base == TARGET:
            reference_rows = local_rows

    # Rebuild client index after reference rows are known.
    build_index(CLIENT_TARGET, to_client_rows(reference_rows))


if __name__ == "__main__":
    main()
