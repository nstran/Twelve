# Verified Semantic

This folder contains only review buckets that are defensible from the jar path or decompiled client behavior.

Rules:
- If an asset has an original jar folder such as `/play`, `/m`, `/info`, `/createcs`, `/corner`, or `/dialog`, it can be grouped by that confirmed usage.
- `/offline/*` assets are treated as cache resources first, not as semantic folders.
- Only the `990xx` offline range is promoted into a stronger character body bucket because the body compositor uses that base range directly.

Use this folder when reviewing only what is confirmed, without speculative labels.
