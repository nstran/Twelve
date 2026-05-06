# Plan NPC & Nhiệm Vụ Mẫu Cho Map Hoa Lư

Mục tiêu: dựng một bản plan có thể duyệt cho NPC và nhiệm vụ ở map Hoa Lư. Đây là bản thiết kế trước khi implement, dùng để user đọc/sửa/chốt. Những phần chưa có Java server evidence đều ghi rõ là remake policy pending approval.

## 1. Evidence Hiện Có

### Java/User Evidence Đã Có

- Map runtime hiện có: `Hoa Lu` / hiển thị `Hoa Lư`.
- Monster seed hiện có trong DB: `Gà Điên` ở Hoa Lư, asset key `MONSTER_1000_SLOT_0`.
- NPC seed hiện có:
  - `npc_110020` = `Trưởng làng Gia Viễn`.
  - `npc_110110` = `Lính Hoa Lư`.
- User policy đã chốt:
  - NPC có thể chứa nhiều nhiệm vụ.
  - Reward nhiệm vụ trước mắt chỉ hỗ trợ `Exp`, `Item`, `Equipment`.
  - Lính mỗi map có tên dạng `Lính + tên map`.

### Remake Policy Pending Approval

- Tọa độ NPC trong Hoa Lư hiện chưa có Java server evidence.
- Mission dưới đây là nhiệm vụ mẫu để user duyệt, chưa phải dữ liệu Java server gốc.
- EXP/reward dưới đây là số tạm để duyệt gameplay flow, chưa phải reward gốc.

## 2. NPC Đề Xuất Cho Hoa Lư

### 2.1. Trưởng làng Gia Viễn

```text
NpcKey: npc_110020
Tên hiển thị: Trưởng làng Gia Viễn
Map: Hoa Lư
Vai trò đề xuất: NPC nhiệm vụ đầu game
Sprite: assets/npc/110020.png
Tọa độ đề xuất: x=10, y=23
Evidence hiện có:
- Có trong seed NPC hiện tại.
- User đã nhập tên NPC.
Remake policy pending approval:
- Tọa độ x=10, y=23.
- Vai trò giao nhiệm vụ đầu game.
Missing Java server evidence:
- Vị trí thật trong map Hoa Lư.
- Danh sách nhiệm vụ thật của Trưởng làng Gia Viễn.
- Dialog/menu thật.
```

### 2.2. Lính Hoa Lư

```text
NpcKey: npc_110110
Tên hiển thị: Lính Hoa Lư
Map: Hoa Lư
Vai trò đề xuất: NPC phụ trợ/hướng dẫn/nhiệm vụ tuần tra
Sprite: assets/npc/110110.png
Tọa độ đề xuất: x=6, y=23
Evidence hiện có:
- Có trong seed NPC hiện tại.
- User policy: lính mỗi map có tên Lính + tên map.
Remake policy pending approval:
- Tọa độ x=6, y=23.
- Vai trò NPC phụ trợ/hướng dẫn.
Missing Java server evidence:
- Lính Hoa Lư có nhiệm vụ thật không.
- Dialog/menu thật.
- Vị trí thật trong map Hoa Lư.
```

## 3. Chuỗi Nhiệm Vụ Mẫu Cho Hoa Lư

Đề xuất flow nhẹ đầu game:

1. Người chơi gặp `Trưởng làng Gia Viễn`.
2. Trưởng làng giao nhiệm vụ đánh `Gà Điên` để làm quen combat.
3. Người chơi đánh đủ số lượng `Gà Điên`.
4. Người chơi quay lại `Trưởng làng Gia Viễn` trả nhiệm vụ.
5. Sau đó mở nhiệm vụ phụ từ `Lính Hoa Lư` để tuần tra/nói chuyện.

## 4. Mission 1: Gà Điên Quấy Phá

### 4.1. Mission Catalog

```text
MissionKey: hoa_lu_ga_dien_quay_pha_001
Tên nhiệm vụ: Gà Điên Quấy Phá
NPC giao: Trưởng làng Gia Viễn
Map: Hoa Lư
Loại nhiệm vụ: nhiệm vụ đầu game / đánh quái
Mô tả đề xuất: Gần làng xuất hiện nhiều Gà Điên làm dân làng hoảng sợ. Hãy đánh bại chúng để giúp Trưởng làng ổn định lại Hoa Lư.
Evidence hiện có:
- Gà Điên có trong monster seed Hoa Lư.
- Trưởng làng Gia Viễn có trong NPC seed.
Remake policy pending approval:
- Tên nhiệm vụ.
- Nội dung mô tả.
- Trưởng làng là NPC giao nhiệm vụ.
Missing Java server evidence:
- Mission gốc có tồn tại không.
- Tên/mô tả/reward thật.
```

### 4.2. Điều Kiện Nhận

```text
Điều kiện đề xuất:
- Level tối thiểu: 1
- Mission trước: không có
- Map yêu cầu: Hoa Lư
- NPC yêu cầu: Trưởng làng Gia Viễn
Remake policy pending approval:
- Toàn bộ điều kiện trên.
```

### 4.3. Mục Tiêu

```text
ObjectiveType: KillMonster
TargetKey: MONSTER_1000_SLOT_0
TargetName: Gà Điên
RequiredAmount: 3
Text hiển thị đề xuất: Đánh bại 3 Gà Điên ở Hoa Lư.
Evidence hiện có:
- `Gà Điên` đang có trong monster seed Hoa Lư.
Remake policy pending approval:
- Số lượng 3.
- TargetKey dùng asset key hiện tại của monster seed.
Missing Java server evidence:
- Mission gốc có yêu cầu đánh Gà Điên không.
- Số lượng thật.
```

### 4.4. Reward

```text
Reward 1:
RewardType: Exp
Amount: 80
Ghi chú: số tạm cho nhiệm vụ đầu game.
```

```text
Reward 2:
RewardType: Item
RewardKey: 5001
Amount: 2
Ghi chú: item id 5001 là item seed hiện có; cần user xác nhận có phù hợp làm thưởng không.
```

```text
Reward 3:
RewardType: Equipment
RewardKey: null
Amount: 0
Ghi chú: không thưởng equipment ở nhiệm vụ đầu để tránh phát trang bị quá sớm, trừ khi user muốn.
```

### 4.5. Flow Hoàn Thành

```text
Flow đề xuất:
1. Nhận nhiệm vụ từ Trưởng làng Gia Viễn.
2. Đánh đủ 3 Gà Điên.
3. Nhiệm vụ chuyển trạng thái có thể trả.
4. Quay lại Trưởng làng Gia Viễn.
5. Nhận EXP + item.
```

## 5. Mission 2: Báo Tin Cho Lính Hoa Lư

### 5.1. Mission Catalog

```text
MissionKey: hoa_lu_bao_tin_cho_linh_002
Tên nhiệm vụ: Báo Tin Cho Lính Hoa Lư
NPC giao: Trưởng làng Gia Viễn
NPC nhận/đích: Lính Hoa Lư
Map: Hoa Lư
Loại nhiệm vụ: nói chuyện NPC / dẫn flow
Mô tả đề xuất: Sau khi xử lý Gà Điên, hãy báo lại cho Lính Hoa Lư để khu vực quanh làng được canh phòng cẩn thận hơn.
Evidence hiện có:
- Trưởng làng Gia Viễn và Lính Hoa Lư có trong seed NPC.
Remake policy pending approval:
- Mission này chỉ là flow mẫu để nối NPC với NPC.
Missing Java server evidence:
- Mission gốc có tồn tại không.
- NPC nào giao/nhận thật.
```

### 5.2. Điều Kiện Nhận

```text
Điều kiện đề xuất:
- Cần hoàn thành mission `Gà Điên Quấy Phá`.
- Level tối thiểu: 1
- Map yêu cầu: Hoa Lư
```

### 5.3. Mục Tiêu

```text
ObjectiveType: TalkNpc
TargetKey: npc_110110
TargetName: Lính Hoa Lư
RequiredAmount: 1
Text hiển thị đề xuất: Báo tin cho Lính Hoa Lư.
```

### 5.4. Reward

```text
Reward 1:
RewardType: Exp
Amount: 50
```

```text
Reward 2:
RewardType: Item
RewardKey: null
Amount: 0
```

```text
Reward 3:
RewardType: Equipment
RewardKey: null
Amount: 0
```

### 5.5. Flow Hoàn Thành

```text
Flow đề xuất:
1. Nhận nhiệm vụ từ Trưởng làng Gia Viễn sau khi hoàn thành mission 1.
2. Đi tới Lính Hoa Lư.
3. Bấm nói chuyện.
4. Mission hoàn thành và nhận EXP.
```

## 6. Mission 3: Tuần Tra Cùng Lính Hoa Lư

### 6.1. Mission Catalog

```text
MissionKey: hoa_lu_tuan_tra_cung_linh_003
Tên nhiệm vụ: Tuần Tra Cùng Lính Hoa Lư
NPC giao: Lính Hoa Lư
Map: Hoa Lư
Loại nhiệm vụ: đánh quái phụ
Mô tả đề xuất: Lính Hoa Lư nhờ bạn tiếp tục tuần tra và đánh bại thêm Gà Điên quanh khu vực làng.
Evidence hiện có:
- Lính Hoa Lư có trong NPC seed.
- Gà Điên có trong monster seed Hoa Lư.
Remake policy pending approval:
- Đây là nhiệm vụ phụ mẫu.
Missing Java server evidence:
- Mission gốc có tồn tại không.
```

### 6.2. Điều Kiện Nhận

```text
Điều kiện đề xuất:
- Cần hoàn thành mission `Báo Tin Cho Lính Hoa Lư`.
- Level tối thiểu: 1
- Map yêu cầu: Hoa Lư
```

### 6.3. Mục Tiêu

```text
ObjectiveType: KillMonster
TargetKey: MONSTER_1000_SLOT_0
TargetName: Gà Điên
RequiredAmount: 5
Text hiển thị đề xuất: Đánh bại 5 Gà Điên khi tuần tra Hoa Lư.
```

### 6.4. Reward

```text
Reward 1:
RewardType: Exp
Amount: 120
```

```text
Reward 2:
RewardType: Item
RewardKey: 5001
Amount: 3
```

```text
Reward 3:
RewardType: Equipment
RewardKey: 30094
Amount: 1
Ghi chú: 30094 hiện là Trứng gà trong equipment/item seed, cần user xác nhận có nên dùng làm reward nhiệm vụ hay không.
```

## 7. Dialog/Menu Đề Xuất

### 7.1. Trưởng làng Gia Viễn

```text
Câu thoại đầu:
- Hoa Lư gần đây không yên ổn. Gà Điên xuất hiện quanh làng, làm dân làng lo sợ.

Menu đề xuất:
- Nhận nhiệm vụ: Gà Điên Quấy Phá
- Xem nhiệm vụ đang làm
- Kết thúc trò chuyện

Sau khi hoàn thành mission 1:
- Tốt lắm. Hãy báo lại cho Lính Hoa Lư để họ tăng cường tuần tra.
```

### 7.2. Lính Hoa Lư

```text
Câu thoại đầu:
- Ta đang canh giữ khu vực này. Nếu thấy Gà Điên xuất hiện, hãy báo ngay.

Menu đề xuất:
- Nhận nhiệm vụ: Tuần Tra Cùng Lính Hoa Lư
- Trả nhiệm vụ nếu đã hoàn thành
- Kết thúc trò chuyện
```

## 8. Bảng Tổng Hợp Để User Duyệt

| Thứ tự | NPC giao | Tên nhiệm vụ | Mục tiêu | Reward đề xuất | Evidence |
|---|---|---|---|---|---|
| 1 | Trưởng làng Gia Viễn | Gà Điên Quấy Phá | Đánh 3 Gà Điên | 80 EXP + Item 5001 x2 | Remake policy pending approval |
| 2 | Trưởng làng Gia Viễn | Báo Tin Cho Lính Hoa Lư | Nói chuyện Lính Hoa Lư | 50 EXP | Remake policy pending approval |
| 3 | Lính Hoa Lư | Tuần Tra Cùng Lính Hoa Lư | Đánh 5 Gà Điên | 120 EXP + Item 5001 x3 + Equipment 30094 x1 | Remake policy pending approval |

## 9. Các Điểm Cần User Duyệt Trước Khi Implement

- [ ] Có chấp nhận dùng `Trưởng làng Gia Viễn` làm NPC nhiệm vụ chính đầu Hoa Lư không?
- [ ] Có chấp nhận dùng `Lính Hoa Lư` làm NPC nhiệm vụ phụ/hướng dẫn không?
- [ ] Có chấp nhận mission đầu là đánh `Gà Điên` không?
- [ ] Số lượng đánh `3` và `5` có ổn không?
- [ ] EXP `80`, `50`, `120` có ổn cho đầu game không?
- [ ] Item `5001` dùng làm reward có đúng ý không?
- [ ] Equipment/item `30094` có nên dùng làm reward nhiệm vụ không?
- [ ] Có cần mission nào thưởng equipment thật thay vì trứng/item không?
- [ ] Có cần đổi tên nhiệm vụ/mô tả/dialog cho gần game gốc hơn không?
- [ ] Nếu chưa chắc, có muốn giữ tất cả dưới trạng thái remake policy để sau thay bằng Java server evidence không?

## 10. Plan Implement Sau Khi Duyệt

Nếu user duyệt, bước implement nên làm theo thứ tự:

1. Cập nhật seed `MissionCatalog` với 3 mission trên.
2. Cập nhật `MissionObjectives`:
   - KillMonster cho Gà Điên.
   - TalkNpc cho Lính Hoa Lư.
3. Cập nhật `MissionRewards` với reward `Exp`, `Item`, `Equipment` đã duyệt.
4. Cập nhật `NpcMissionLinks`:
   - Gắn mission 1 và 2 vào Trưởng làng Gia Viễn.
   - Gắn mission 3 vào Lính Hoa Lư.
5. Cập nhật tài liệu `NPC_SYSTEM_RECONSTRUCTION.md` và `CHANGELOG.md`.
6. Chạy build server để đảm bảo migration/resource không lỗi.

## 11. Boundary

- Plan này chưa phải Java server evidence.
- Plan này là bản remake policy để user duyệt gameplay đầu Hoa Lư.
- Không implement trước khi user chốt các dòng ở mục 9.
