from __future__ import annotations

import argparse
import csv
import json
import subprocess
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
JAR_PATH = ROOT / "reference" / "twelvefull.jar"
TMP_DIR = ROOT / "reference" / "redecoded" / "extracted_meta"


def extract_jar_entry(entry: str) -> bytes:
    result = subprocess.run(
        ["jar", "xvf", str(JAR_PATH), entry],
        cwd=TMP_DIR,
        check=True,
        capture_output=True,
    )
    extracted_path = TMP_DIR / entry
    data = extracted_path.read_bytes()
    return data


def parse_meta(meta_id: int, data: bytes) -> dict:
    idx = 0
    version = data[idx]
    idx += 1

    base_image_id = int.from_bytes(data[idx : idx + 4], "big", signed=True)
    idx += 4

    family_count = data[idx]
    idx += 1

    families = []
    for _ in range(family_count):
        family_slot = data[idx]
        idx += 1

        frame_width_divisor = data[idx]
        idx += 1

        frame_count = data[idx]
        idx += 1

        frames = []
        for _ in range(frame_count):
            source_index = data[idx]
            idx += 1

            x_offset = int.from_bytes(data[idx : idx + 2], "big", signed=True)
            idx += 2

            y_offset = int.from_bytes(data[idx : idx + 2], "big", signed=True)
            idx += 2

            frames.append(
                {
                    "source_index": source_index,
                    "x_offset": x_offset,
                    "y_offset": y_offset,
                }
            )

        families.append(
            {
                "family_slot": family_slot,
                "frame_width_divisor": frame_width_divisor,
                "frame_count": frame_count,
                "frames": frames,
            }
        )

    return {
        "meta_id": meta_id,
        "version": version,
        "base_image_id": base_image_id,
        "family_count": family_count,
        "families": families,
    }


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--meta-ids", nargs="+", type=int, required=True)
    parser.add_argument("--json-out", type=Path, required=True)
    parser.add_argument("--csv-out", type=Path, required=True)
    args = parser.parse_args()

    TMP_DIR.mkdir(parents=True, exist_ok=True)

    parsed = []
    for meta_id in args.meta_ids:
        entry = f"offline/{meta_id}.meta"
        data = extract_jar_entry(entry)
        parsed.append(parse_meta(meta_id, data))

    args.json_out.parent.mkdir(parents=True, exist_ok=True)
    args.json_out.write_text(json.dumps(parsed, indent=2), encoding="utf-8")

    args.csv_out.parent.mkdir(parents=True, exist_ok=True)
    with args.csv_out.open("w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(
            handle,
            fieldnames=[
                "meta_id",
                "base_image_id",
                "family_count",
                "family_slot",
                "frame_width_divisor",
                "frame_count",
            ],
        )
        writer.writeheader()
        for meta in parsed:
            for family in meta["families"]:
                writer.writerow(
                    {
                        "meta_id": meta["meta_id"],
                        "base_image_id": meta["base_image_id"],
                        "family_count": meta["family_count"],
                        "family_slot": family["family_slot"],
                        "frame_width_divisor": family["frame_width_divisor"],
                        "frame_count": family["frame_count"],
                    }
                )


if __name__ == "__main__":
    main()
