# Bản Mẫu Review Evidence NPC & Nhiệm Vụ

Mục tiêu: đây là bản mẫu đã điền thử để user duyệt cách ghi evidence. Nội dung bên dưới không phải Java server evidence, trừ khi từng dòng ghi rõ có nguồn Java client hoặc user confirmation.

## Quy Ước Evidence

- **Java client evidence**: đã thấy trong code Java client hoặc asset JAR.
- **Java server evidence**: dữ liệu lấy từ server gốc, DB dump gốc, config gốc, hoặc packet log thật.
- **User policy**: user chốt để remake dùng tạm.
- **Remake policy pending approval**: giá trị tạm để server mới chạy được, cần user duyệt.
- **Missing Java server evidence**: còn thiếu dữ liệu server gốc.

## 1. NPC Mẫu: Lính Theo Từng Map

### 1.1. Bản Ghi NPC Catalog

```text
NpcKey: npc_110110
Tên gốc trong catalog: Lính Hoa Lư
Sprite: client/assets/npc/110110.png
Evidence hiện có:
- User confirmation: 110000..110160 là sprite NPC.
- User policy: lính mỗi map có tên dạng Lính + tên map.
Remake policy pending approval:
- Tạm dùng npc_110110 làm NPC lính chung cho mọi map.
Missing Java server evidence:
- Chưa có file server gốc xác nhận npc_110110 là id thật.
- Chưa có file server gốc xác nhận sprite 110110 là lính.
- Chưa có file server gốc xác nhận lính dùng chung một NPC catalog hay mỗi map có NPC riêng.
```

### 1.2. Bản Ghi NPC Theo Map: Hoa Lư

```text
Map: Hoa Lư
NPC hiển thị: Lính Hoa Lư
NPC catalog dùng chung: npc_110110
Tọa độ tạm: x=6, y=23
Evidence hiện có:
- User policy: lính mỗi map thì tên là Lính + tên map.
Remake policy pending approval:
- Tọa độ x=6, y=23 chỉ là tọa độ tạm.
Missing Java server evidence:
- Tọa độ đúng trên server gốc.
- Lính Hoa Lư có thật sự xuất hiện ở Hoa Lư không.
- Lính Hoa Lư có giao nhiệm vụ/shop/dialog gì không.
```

### 1.3. Bản Ghi NPC Theo Map: Kỷ Bố

```text
Map: Kỷ Bố
NPC hiển thị: Lính Kỷ Bố
NPC catalog dùng chung: npc_110110
Tọa độ tạm: x=6, y=23
Evidence hiện có:
- User policy: lính mỗi map thì tên là Lính + tên map.
Remake policy pending approval:
- Tọa độ x=6, y=23 chỉ là tọa độ tạm.
Missing Java server evidence:
- Tọa độ đúng trên server gốc.
- Lính Kỷ Bố có thật sự xuất hiện ở Kỷ Bố không.
- Lính Kỷ Bố có giao nhiệm vụ/shop/dialog gì không.
```

### 1.4. Câu Hỏi Cần Duyệt Cho NPC Lính

- [ ] Có đúng là mỗi map đều có một lính không?
- [ ] Lính dùng chung sprite `110110` cho tất cả map hay mỗi map có sprite riêng?
- [ ] Lính có nhiệm vụ không hay chỉ là NPC hướng dẫn/thông báo?
- [ ] Lính có shop/menu/dịch chuyển không?
- [ ] Tọa độ lính trên từng map là bao nhiêu?
- [ ] Server gốc lưu tên đầy đủ như `Lính Hoa Lư`, `Lính Kỷ Bố`, hay tự ghép tên động?

## 2. Mission Mẫu 1

### 2.1. Bản Ghi Mission Catalog

```text
MissionKey: mission_placeholder_001
Tên nhiệm vụ: Nhiệm vụ mẫu 1
Mô tả: Placeholder mission; replace when original NPC mission data is available.
NPC đang gắn: npc_110110
Evidence hiện có:
- User policy: schema mission hiện hỗ trợ reward Exp, Item, Equipment.
Remake policy pending approval:
- Đây là mission placeholder để test quan hệ NPC có nhiều mission.
Missing Java server evidence:
- Mã mission gốc.
- Tên mission gốc.
- Mô tả mission gốc.
- NPC nào giao mission này trong game gốc.
- Mission này có thuộc chuỗi nhiệm vụ nào không.
```

### 2.2. Mục Tiêu Mission Mẫu 1

```text
ObjectiveType: TalkNpc
TargetKey: npc_110110
RequiredAmount: 1
Evidence hiện có:
- Không có Java server evidence.
Remake policy pending approval:
- Mục tiêu tạm để test flow mission.
Missing Java server evidence:
- Mission gốc yêu cầu nói chuyện, đánh quái, nhặt item, giao item, hay đi tới map.
- Số lượng yêu cầu thật.
- Text mục tiêu thật hiển thị trên client.
```

### 2.3. Phần Thưởng Mission Mẫu 1

```text
Reward 1:
RewardType: Exp
Amount: 100
Evidence hiện có:
- User policy: schema hiện cho phép reward Exp.
Remake policy pending approval:
- Số 100 là placeholder.
Missing Java server evidence:
- Số exp thật của mission.
```

```text
Reward 2:
RewardType: Item
RewardKey: 5001
Amount: 1
Evidence hiện có:
- User policy: schema hiện cho phép reward Item.
Remake policy pending approval:
- Item 5001 là placeholder.
Missing Java server evidence:
- Item id thật.
- Số lượng thật.
- Nếu túi đầy thì server xử lý thế nào.
```

### 2.4. Câu Hỏi Cần Duyệt Cho Mission Mẫu 1

- [ ] Mission này tên thật là gì?
- [ ] Mission này thuộc NPC nào?
- [ ] Mission này mở ở map nào?
- [ ] Điều kiện nhận mission là gì?
- [ ] Mục tiêu thật là nói chuyện/đánh quái/nhặt item/đến map?
- [ ] Phần thưởng exp thật là bao nhiêu?
- [ ] Phần thưởng item thật là gì?
- [ ] Hoàn thành là tự nhận thưởng hay phải quay lại NPC?

## 3. Mission Mẫu 2

### 3.1. Bản Ghi Mission Catalog

```text
MissionKey: mission_placeholder_002
Tên nhiệm vụ: Nhiệm vụ mẫu 2
Mô tả: Placeholder mission; proves one NPC can own many missions.
NPC đang gắn: npc_110110
Evidence hiện có:
- User policy: một NPC có thể chứa nhiều mission.
- User policy: schema mission hiện hỗ trợ reward Exp, Item, Equipment.
Remake policy pending approval:
- Đây là mission placeholder để test một NPC có nhiều mission.
Missing Java server evidence:
- Mã mission gốc.
- Tên mission gốc.
- Mô tả mission gốc.
- NPC nào giao mission này trong game gốc.
```

### 3.2. Mục Tiêu Mission Mẫu 2

```text
ObjectiveType: TalkNpc
TargetKey: npc_110110
RequiredAmount: 1
Evidence hiện có:
- Không có Java server evidence.
Remake policy pending approval:
- Mục tiêu tạm.
Missing Java server evidence:
- Objective thật của mission.
```

### 3.3. Phần Thưởng Mission Mẫu 2

```text
Reward 1:
RewardType: Exp
Amount: 250
Evidence hiện có:
- User policy: schema hiện cho phép reward Exp.
Remake policy pending approval:
- Số 250 là placeholder.
Missing Java server evidence:
- Số exp thật của mission.
```

```text
Reward 2:
RewardType: Equipment
RewardKey: 30094
Amount: 1
Evidence hiện có:
- User policy: schema hiện cho phép reward Equipment.
Remake policy pending approval:
- Equipment 30094 là placeholder.
Missing Java server evidence:
- Equipment id thật.
- Trang bị có chỉ số cố định hay random.
- Nếu túi/trang bị đầy thì server xử lý thế nào.
```

## 4. Bảng Review Nhanh

| Hạng mục | Giá trị tạm | Loại evidence hiện tại | Cần user/server xác nhận |
|---|---|---|---|
| Lính mỗi map | Có | User policy | Có đúng với game gốc không |
| Tên lính | Lính + tên map | User policy/remake policy | Server gốc lưu sẵn hay ghép động |
| Sprite lính | 110110 | Remake policy pending approval | Sprite đúng của lính |
| Tọa độ lính | x=6, y=23 | Remake policy pending approval | Tọa độ thật từng map |
| Mission 1 owner | npc_110110 | Remake policy pending approval | NPC giao mission thật |
| Mission 1 objective | TalkNpc npc_110110 | Remake policy pending approval | Objective thật |
| Mission 1 reward | Exp 100 + Item 5001 | Remake policy pending approval | Reward thật |
| Mission 2 owner | npc_110110 | Remake policy pending approval | NPC giao mission thật |
| Mission 2 objective | TalkNpc npc_110110 | Remake policy pending approval | Objective thật |
| Mission 2 reward | Exp 250 + Equipment 30094 | Remake policy pending approval | Reward thật |

## 5. Cách Duyệt

User có thể duyệt từng dòng theo một trong ba hướng:

1. **Chấp nhận tạm**: giữ làm remake policy để test DB/runtime, sau thay bằng data gốc.
2. **Sửa ngay**: đổi tên NPC, map, tọa độ, mission, objective, reward theo trí nhớ hoặc file/log tìm được.
3. **Không chấp nhận**: xóa khỏi seed để tránh nhầm là Java evidence.

## 6. Kết Luận Bản Mẫu

Bản mẫu này đang cố tình tách rõ:

- cái gì là user policy;
- cái gì là remake policy pending approval;
- cái gì là missing Java server evidence;
- cái gì cần tìm thêm trước khi coi là bám sát game gốc.

Nếu user duyệt cách ghi này, các NPC/mission tiếp theo nên được ghi cùng format để tránh lẫn giữa dữ liệu thật và dữ liệu tạm.
