# Hoa Lu Mission End-to-End Checklist

Manual QA checklist for the current NPC/Mission remake flow.

## Scope

- Map: Hoa Lu room 1.
- Missions:
  - `hoa_lu_ga_dien_quay_pha_001`
  - `hoa_lu_bao_tin_cho_linh_002`
  - `hoa_lu_tuan_tra_cung_linh_003`
- Evidence boundary:
  - Java client proves mission request/notification command flow.
  - Mission catalog, objective quantities, rewards, and claim-by-accept are approved remake policy.

## Preconditions

- Fresh or known test account with character created.
- Server database migrated with NPC seed data.
- Client connects to the local server and enters Hoa Lu.

## Checklist

- [ ] Open map menu, choose `Nhiem Vu`, and verify the mission dialog opens without covering softkey state incorrectly.
- [ ] Verify mission list loads from server, not from hardcoded client data.
- [ ] Select each Hoa Lu mission and verify title, description, objectives, and reward lines render in the detail panel.
- [ ] Accept `hoa_lu_ga_dien_quay_pha_001`; reopen mission dialog and verify the mission shows accepted/in progress.
- [ ] Defeat the required Ga Dien target and claim battle result; verify the map shows mission progress toast after returning from battle.
- [ ] When the kill objective reaches its required amount, verify completion toast appears and the mission status changes on reload.
- [ ] Accept `hoa_lu_bao_tin_cho_linh_002`; talk to `npc_110110`; verify TalkNpc progress toast/update appears.
- [ ] Accept `hoa_lu_tuan_tra_cung_linh_003`; defeat the configured Ga Dien target and verify progress uses DB objective target/quantity.
- [ ] Claim each completed mission by sending accept again; verify EXP/item/equipment reward applies once.
- [ ] Try claiming the same completed mission again; verify no duplicate reward is granted.
- [ ] Refresh/reconnect; verify accepted/completed/reward-claimed states persist.

## Expected Current Result

- Mission UI is playable for list/detail/accept/reward inspection.
- Progress notifications appear from socket mission packets and battle result mission updates.
- Objective target keys, required amount, and rewards remain seed-driven.
