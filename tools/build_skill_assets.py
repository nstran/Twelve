from __future__ import annotations

import csv
import json
import shutil
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
CANONICAL = ROOT / "reference" / "review_assets" / "canonical_from_jar_png"
REFERENCE_TARGET = ROOT / "reference" / "review_assets" / "skill_system_organized"
CLIENT_TARGET = ROOT / "client" / "assets" / "skill_legacy"
REDECODED = ROOT / "reference" / "redecoded"


TREE_UI_FILES = [
    ("info/skilltree.png", "skilltree.png"),
    ("info/increase.png", "increase.png"),
    ("info/decrease.png", "decrease.png"),
]

SHARED_FILES = {
    "named_effects": [
        "castingball.png",
        "barrier.png",
        "firerage.png",
        "firerageext.png",
        "magicgate.png",
        "miniexplosionfire.png",
        "skillupdownstat.png",
    ],
    "battle_scene_support": [
        "zap.png",
        "ice.png",
    ],
}

GROUPS = [
    {
        "folder": "group_100x_hoa_fire_likely",
        "label": "100x",
        "element": "Hỏa / fire",
        "families": [
            {
                "code": 1000,
                "java_class": "is",
                "runtime_files": [1000001, 1000002],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Projectile pair using two numeric runtime sheets.",
            },
            {
                "code": 1001,
                "java_class": "it",
                "runtime_files": [1001001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Single-sheet loop effect.",
            },
            {
                "code": 1002,
                "java_class": None,
                "runtime_files": [],
                "shared_named": [],
                "status": "icon_only_or_unresolved",
                "notes": "Icon exists, but no dedicated runtime loader was found in the local Java jar.",
            },
            {
                "code": 1003,
                "java_class": "io(elementVariant=0)",
                "runtime_files": [1003001, 1003002, 1003003],
                "shared_named": ["firerage.png", "firerageext.png", "miniexplosionfire.png"],
                "status": "confirmed_runtime",
                "notes": "Burst family with shared named fire-rage assets and mini explosion helper.",
            },
            {
                "code": 1004,
                "java_class": "iu",
                "runtime_files": [1004001],
                "shared_named": ["miniexplosionfire.png"],
                "status": "confirmed_runtime",
                "notes": "Runtime sheet plus shared explosion helper instances.",
            },
            {
                "code": 1005,
                "java_class": "iv",
                "runtime_files": [1005001],
                "shared_named": ["miniexplosionfire.png"],
                "status": "confirmed_runtime",
                "notes": "Runtime sheet plus shared explosion helper instances.",
            },
            {
                "code": 1006,
                "java_class": "iw",
                "runtime_files": [1006001, 1006002],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Projectile pair variant based on the same base runtime pattern as 1000.",
            },
            {
                "code": 1007,
                "java_class": "ix",
                "runtime_files": [1007001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Large single-sheet runtime effect.",
            },
            {
                "code": 1008,
                "java_class": "iy",
                "runtime_files": [1008001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Large single-sheet runtime effect.",
            },
        ],
    },
    {
        "folder": "group_200x_loi_thunder_likely",
        "label": "200x",
        "element": "Lôi / thunder",
        "families": [
            {
                "code": 2000,
                "java_class": "jg",
                "runtime_files": [2000001, 2000002, 2000003],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Path-following effect with two extra segment images.",
            },
            {
                "code": 2001,
                "java_class": None,
                "runtime_files": [],
                "shared_named": [],
                "status": "icon_only_or_unresolved",
                "notes": "Icon exists, but no dedicated runtime loader was found in the local Java jar.",
            },
            {
                "code": 2002,
                "java_class": None,
                "runtime_files": [],
                "shared_named": [],
                "status": "icon_only_or_unresolved",
                "notes": "Icon exists, but no dedicated runtime loader was found in the local Java jar.",
            },
            {
                "code": 2003,
                "java_class": "jh",
                "runtime_files": [2003001, 2003002],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Projectile pair variant.",
            },
            {
                "code": 2004,
                "java_class": "ji",
                "runtime_files": [2004001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Single-sheet segmented beam effect.",
            },
            {
                "code": 2005,
                "java_class": "io(elementVariant=1)",
                "runtime_files": [2005001, 2005002],
                "shared_named": ["firerage.png", "firerageext.png", "miniexplosionfire.png"],
                "status": "confirmed_runtime",
                "notes": "Variant branch in io using recolored fire-rage style assets and shared explosion helper.",
            },
            {
                "code": 2006,
                "java_class": "jj",
                "runtime_files": [2006001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Travel plus impact sequence.",
            },
            {
                "code": 2007,
                "java_class": "jk",
                "runtime_files": [2007001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Two-layer runtime effect from the same numeric sheet.",
            },
            {
                "code": 2008,
                "java_class": "jl",
                "runtime_files": [2008001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Multi-instance particle burst from one runtime sheet.",
            },
        ],
    },
    {
        "folder": "group_400x_thuy_water_likely",
        "label": "400x",
        "element": "Thủy / water",
        "families": [
            {
                "code": 4000,
                "java_class": "iz",
                "runtime_files": [4000001, 4000002],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Projectile pair variant.",
            },
            {
                "code": 4001,
                "java_class": "ja",
                "runtime_files": [4001001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Single-sheet loop effect.",
            },
            {
                "code": 4002,
                "java_class": "jb",
                "runtime_files": [4002001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Travel plus finish sequence.",
            },
            {
                "code": 4003,
                "java_class": "jc",
                "runtime_files": [4003001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Single-sheet anchored effect.",
            },
            {
                "code": 4004,
                "java_class": "io(elementVariant=2)",
                "runtime_files": [4004001, 4004002],
                "shared_named": ["firerage.png", "firerageext.png", "miniexplosionfire.png"],
                "status": "confirmed_runtime",
                "notes": "Variant branch in io using recolored fire-rage style assets and shared explosion helper.",
            },
            {
                "code": 4005,
                "java_class": "jd",
                "runtime_files": [4005001],
                "shared_named": ["miniexplosionfire.png"],
                "status": "confirmed_runtime",
                "notes": "Runtime sheet plus recolored explosion helper instances.",
            },
            {
                "code": 4006,
                "java_class": "je",
                "runtime_files": [4006001, 4006002],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Projectile pair variant.",
            },
            {
                "code": 4007,
                "java_class": "jf",
                "runtime_files": [4007001],
                "shared_named": [],
                "status": "confirmed_runtime",
                "notes": "Large single-sheet runtime effect.",
            },
            {
                "code": 4008,
                "java_class": None,
                "runtime_files": [],
                "shared_named": [],
                "status": "icon_only_with_loader_alias",
                "notes": "Icon exists. mp loader aliases 4008 fallback to the 4000 projectile pair sheets.",
            },
        ],
    },
]


def ensure_clean(path: Path) -> None:
    if path.exists():
        shutil.rmtree(path)
    path.mkdir(parents=True, exist_ok=True)


def copy_file(src: Path, dest: Path) -> None:
    dest.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(src, dest)


def write_text(path: Path, text: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(text, encoding="utf-8")


def build_target(target: Path) -> list[dict[str, str]]:
    ensure_clean(target)
    rows: list[dict[str, str]] = []

    tree_root = target / "00_skill_tree_ui_confirmed"
    for relative_src, dest_name in TREE_UI_FILES:
        src = CANONICAL / relative_src
        dest = tree_root / "skill_tree_board" / dest_name
        copy_file(src, dest)
        rows.append(
            {
                "section": "skill_tree_ui",
                "group": "skill_tree_board",
                "family_code": "",
                "java_class": "de",
                "file_type": "ui",
                "source": str(src.relative_to(ROOT)).replace("\\", "/"),
                "target": str(dest.relative_to(ROOT)).replace("\\", "/"),
            }
        )

    shared_root = target / "01_battle_skill_shared_confirmed"
    for group_name, files in SHARED_FILES.items():
        for filename in files:
            src = CANONICAL / filename
            dest = shared_root / group_name / filename
            copy_file(src, dest)
            rows.append(
                {
                    "section": "shared_runtime",
                    "group": group_name,
                    "family_code": "",
                    "java_class": "mp/om",
                    "file_type": "shared_named",
                    "source": str(src.relative_to(ROOT)).replace("\\", "/"),
                    "target": str(dest.relative_to(ROOT)).replace("\\", "/"),
                }
            )

    family_root = target / "02_elemental_runtime_families"
    for group in GROUPS:
        for family in group["families"]:
            family_code = int(family["code"])
            family_folder = (
                family_root
                / group["folder"]
                / f"family_{family_code}_"
                f"{(family['java_class'] or 'no_dedicated_class').replace('(', '_').replace(')', '').replace('=', '-')}"
            )
            icon_id = family_code * 1000
            icon_src = CANONICAL / "offline" / f"{icon_id}.png"
            if icon_src.exists():
                icon_dest = family_folder / "skill_icon" / f"{icon_id}.png"
                copy_file(icon_src, icon_dest)
                rows.append(
                    {
                        "section": "family_runtime",
                        "group": group["folder"],
                        "family_code": str(family_code),
                        "java_class": family["java_class"] or "",
                        "file_type": "skill_icon",
                        "source": str(icon_src.relative_to(ROOT)).replace("\\", "/"),
                        "target": str(icon_dest.relative_to(ROOT)).replace("\\", "/"),
                    }
                )

            for runtime_id in family["runtime_files"]:
                src = CANONICAL / "offline" / f"{runtime_id}.png"
                dest = family_folder / "runtime_png" / f"{runtime_id}.png"
                copy_file(src, dest)
                rows.append(
                    {
                        "section": "family_runtime",
                        "group": group["folder"],
                        "family_code": str(family_code),
                        "java_class": family["java_class"] or "",
                        "file_type": "runtime_png",
                        "source": str(src.relative_to(ROOT)).replace("\\", "/"),
                        "target": str(dest.relative_to(ROOT)).replace("\\", "/"),
                    }
                )

            summary = {
                "family_code": family_code,
                "element_group_folder": group["folder"],
                "element_label": group["element"],
                "java_class": family["java_class"],
                "skill_icon_id": icon_id if icon_src.exists() else None,
                "runtime_file_ids": family["runtime_files"],
                "shared_named_dependencies": family["shared_named"],
                "status": family["status"],
                "notes": family["notes"],
            }
            write_text(
                family_folder / "family_summary.json",
                json.dumps(summary, indent=2, ensure_ascii=False) + "\n",
            )

    readme = """# Skill System Asset Set

This folder is organized around the legacy Java skill runtime.

The key rule is:

- separate skill-tree UI from battle runtime effects
- keep shared named assets separate from numeric family sheets
- group runtime assets by `family code` such as `1000`, `2005`, or `4007`
- do not rename numeric families into final player-facing skill names unless the old Java runtime or server catalog proves that mapping

Folder layout:

- `00_skill_tree_ui_confirmed`
- `01_battle_skill_shared_confirmed`
- `02_elemental_runtime_families`

Detailed technical reference:

- [SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md)
"""
    write_text(target / "README.md", readme)

    with (target / "index.csv").open("w", newline="", encoding="utf-8") as fh:
        writer = csv.DictWriter(
            fh,
            fieldnames=["section", "group", "family_code", "java_class", "file_type", "source", "target"],
        )
        writer.writeheader()
        writer.writerows(rows)

    return rows


def write_manifest(rows: list[dict[str, str]]) -> None:
    manifest_json: list[dict[str, str]] = []
    for row in rows:
        manifest_json.append(row)

    write_text(
        REDECODED / "skill_runtime_manifest.json",
        json.dumps(manifest_json, indent=2, ensure_ascii=False) + "\n",
    )

    with (REDECODED / "skill_runtime_manifest.csv").open("w", newline="", encoding="utf-8") as fh:
        writer = csv.DictWriter(
            fh,
            fieldnames=["section", "group", "family_code", "java_class", "file_type", "source", "target"],
        )
        writer.writeheader()
        writer.writerows(rows)


def main() -> None:
    reference_rows = build_target(REFERENCE_TARGET)
    build_target(CLIENT_TARGET)
    write_manifest(reference_rows)


if __name__ == "__main__":
    main()
