# Kế Hoạch Thu Thập Evidence Java Server Cho NPC & Nhiệm Vụ

Mục tiêu: ghi lại toàn bộ dữ liệu Java server còn thiếu cho phần NPC và Nhiệm vụ để không quên, không đoán mò, không tạo field/logic dư thừa, và không nhầm remake policy thành hành vi gốc của game.

## Nguyên Tắc

- Chỉ làm trong phạm vi NPC và Nhiệm vụ.
- Không hỏi lại phần quái trên map/monster roster vì phần đó thuộc module khác.
- Không mô tả bằng số packet khó hiểu nếu không cần; ưu tiên mô tả bằng hành động trong game như vào map, nói chuyện NPC, mở danh sách nhiệm vụ, nhận nhiệm vụ, trả nhiệm vụ.
- Nếu chưa có Java server evidence thì ghi rõ là remake policy hoặc user policy.
- Nếu có dữ liệu từ video/screenshot thì chỉ dùng làm evidence phụ, không dùng để suy công thức reward hoặc logic server.

## 1. Checklist NPC Catalog

Cần tìm danh sách NPC thật của game.

### Cần Có

- [ ] Mã NPC trên server.
- [ ] Tên NPC hiển thị trong game.
- [ ] Sprite/ảnh NPC tương ứng trong bộ `110000..110160`.
- [ ] NPC nào dùng chung sprite nhưng khác tên theo map.
- [ ] NPC nào là NPC thật.
- [ ] NPC nào chỉ là prop/cổng/map object, không phải NPC nói chuyện.
- [ ] NPC nào xuất hiện nhiều lần ở nhiều map.

### Gợi Ý Tên File/Bảng Cần Tìm

- [ ] `npc`
- [ ] `npc_catalog`
- [ ] `npc_info`
- [ ] `npc_config`
- [ ] `npc_template`
- [ ] `map_npc`
- [ ] `npc_map`

### Mẫu Ghi Evidence

```text
Nguồn:
File/bảng/log:
Mã NPC:
Tên NPC:
Sprite:
Vai trò:
Evidence hiện có:
Còn thiếu:
Độ tin cậy: Java server evidence / packet log / video evidence / user policy / remake policy
```

## 2. Checklist NPC Theo Map

Cần tìm NPC nào đứng ở map nào và đứng ở đâu.

### Cần Có

- [ ] Tên map.
- [ ] Mã map nếu server dùng mã riêng.
- [ ] Room/channel nếu có.
- [ ] NPC nào xuất hiện trong map đó.
- [ ] Tọa độ X/Y của từng NPC.
- [ ] NPC có xuất hiện ở nhiều map bằng cùng một mã không.
- [ ] Nếu là lính theo từng map, server lưu sẵn tên như `Lính Hoa Lư`, `Lính Kỷ Bố`, hay server/client tự ghép `Lính + tên map`.

### Gợi Ý Log Cần Tìm

- [ ] Log khi nhân vật vào map và server trả về danh sách NPC của map.
- [ ] Log khi nhân vật đổi map/phòng và server trả về danh sách NPC mới.
- [ ] Dump bảng map NPC nếu có.

### Mẫu Ghi Evidence

```text
Nguồn:
File/bảng/log:
Map:
Room/channel:
NPC:
Tên hiển thị trong map:
Tọa độ:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 3. Checklist Vai Trò NPC

Cần xác định NPC làm gì trong game.

### Cần Có

- [ ] NPC chỉ nói chuyện.
- [ ] NPC giao nhiệm vụ.
- [ ] NPC trả nhiệm vụ.
- [ ] NPC bán đồ/shop.
- [ ] NPC rèn/nâng cấp/blacksmith.
- [ ] NPC là cổng dịch chuyển/portal.
- [ ] NPC có nhiều vai trò cùng lúc không.
- [ ] Điều kiện để vai trò đó xuất hiện.

### Gợi Ý Tên File/Bảng Cần Tìm

- [ ] `npc_role`
- [ ] `npc_menu`
- [ ] `npc_action`
- [ ] `npc_shop`
- [ ] `npc_dialog`
- [ ] `npc_portal`
- [ ] `npc_quest`
- [ ] `npc_mission`

### Mẫu Ghi Evidence

```text
NPC:
Vai trò:
Menu/option liên quan:
Điều kiện mở:
Hành động sau khi chọn:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 4. Checklist Dialog/Menu NPC

Cần tìm nội dung server trả về khi người chơi nói chuyện với NPC.

### Cần Có

- [ ] Câu thoại đầu tiên của NPC.
- [ ] Câu thoại khi bấm tiếp tục.
- [ ] Danh sách lựa chọn/menu hiện ra.
- [ ] Lựa chọn nào mở danh sách nhiệm vụ.
- [ ] Lựa chọn nào mở shop.
- [ ] Lựa chọn nào mở rèn/nâng cấp.
- [ ] Lựa chọn nào dịch chuyển map.
- [ ] Điều kiện hiện/ẩn từng lựa chọn.
- [ ] Thông báo khi không đủ điều kiện.

### Gợi Ý Log Cần Tìm

- [ ] Log khi bấm `Nói Chuyện` với NPC.
- [ ] Log khi bấm `Tiếp tục` trong dialog NPC.
- [ ] Log khi chọn từng menu trong dialog NPC.
- [ ] File/bảng lời thoại NPC nếu có.

### Mẫu Ghi Evidence

```text
NPC:
Map:
Câu thoại đầu:
Câu thoại tiếp tục:
Menu:
Điều kiện:
Kết quả khi chọn:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 5. Checklist Mission Catalog

Cần tìm danh sách nhiệm vụ thật.

### Cần Có

- [ ] Mã nhiệm vụ.
- [ ] Tên nhiệm vụ.
- [ ] Mô tả nhiệm vụ.
- [ ] Nhiệm vụ thuộc NPC nào.
- [ ] Nhiệm vụ xuất hiện ở map nào.
- [ ] Nhiệm vụ chính/phụ/hằng ngày/lặp lại nếu game có.
- [ ] Nhiệm vụ thuộc chuỗi nhiệm vụ nào không.
- [ ] Thứ tự trong chuỗi nhiệm vụ.

### Gợi Ý Tên File/Bảng Cần Tìm

- [ ] `mission`
- [ ] `quest`
- [ ] `task`
- [ ] `quest_catalog`
- [ ] `mission_catalog`
- [ ] `npc_quest`
- [ ] `npc_mission`
- [ ] `quest_chain`

### Mẫu Ghi Evidence

```text
Mã nhiệm vụ:
Tên nhiệm vụ:
Mô tả:
NPC giao:
Map:
Loại nhiệm vụ:
Nhiệm vụ trước:
Nhiệm vụ sau:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 6. Checklist Điều Kiện Nhận Nhiệm Vụ

Cần tìm điều kiện để mission xuất hiện hoặc được nhận.

### Cần Có

- [ ] Cần level tối thiểu không.
- [ ] Cần hoàn thành nhiệm vụ nào trước đó không.
- [ ] Cần đang ở map nào không.
- [ ] Cần gặp NPC nào không.
- [ ] Cần item nào trong túi không.
- [ ] Cần hệ/phái/class/element nào không.
- [ ] Mission có giới hạn theo giờ/ngày/số lần không.
- [ ] Khi không đủ điều kiện thì server báo gì.

### Gợi Ý Log Cần Tìm

- [ ] Log khi mở danh sách nhiệm vụ.
- [ ] Log khi bấm nhận nhiệm vụ thành công.
- [ ] Log khi bấm nhận nhiệm vụ nhưng bị từ chối.
- [ ] Bảng điều kiện mission nếu có.

### Mẫu Ghi Evidence

```text
Mission:
Điều kiện level:
Điều kiện nhiệm vụ trước:
Điều kiện map/NPC:
Điều kiện item:
Điều kiện khác:
Thông báo lỗi nếu không đủ điều kiện:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 7. Checklist Mục Tiêu Nhiệm Vụ

Cần tìm mục tiêu thật của từng nhiệm vụ.

### Cần Có

- [ ] Nói chuyện với NPC nào.
- [ ] Đánh quái nào.
- [ ] Cần đánh bao nhiêu con.
- [ ] Nhặt item nào.
- [ ] Cần nhặt bao nhiêu item.
- [ ] Đến map nào.
- [ ] Giao item cho NPC nào.
- [ ] Trả nhiệm vụ cho NPC nào.
- [ ] Mission có nhiều bước nối tiếp không.
- [ ] Tất cả bước phải hoàn thành hay chỉ cần một trong nhiều bước.

### Gợi Ý Tên File/Bảng Cần Tìm

- [ ] `quest_task`
- [ ] `mission_task`
- [ ] `objective`
- [ ] `quest_objective`
- [ ] `task_target`
- [ ] `mission_objective`

### Mẫu Ghi Evidence

```text
Mission:
Bước số:
Loại mục tiêu:
Target:
Số lượng cần:
Text hiển thị:
Điều kiện hoàn thành:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 8. Checklist Tiến Độ Nhiệm Vụ

Cần tìm server lưu tiến độ nhiệm vụ như thế nào.

### Cần Có

- [ ] Trạng thái chưa nhận.
- [ ] Trạng thái đang làm.
- [ ] Trạng thái hoàn thành nhưng chưa trả.
- [ ] Trạng thái đã trả/đã nhận thưởng.
- [ ] Đang làm đến bước nào.
- [ ] Số quái đã đánh.
- [ ] Số item đã nhặt.
- [ ] Khi logout/login lại tiến độ phục hồi thế nào.
- [ ] Khi hủy nhiệm vụ thì tiến độ bị xóa hay giữ lại.

### Gợi Ý Log/File Cần Tìm

- [ ] Log sau khi đánh quái có liên quan nhiệm vụ.
- [ ] Log sau khi nhặt item nhiệm vụ.
- [ ] Log sau khi nói chuyện NPC để cập nhật nhiệm vụ.
- [ ] Log sau khi hủy nhiệm vụ.
- [ ] Bảng player mission progress nếu có.

### Mẫu Ghi Evidence

```text
Player:
Mission:
Trạng thái:
Bước hiện tại:
Tiến độ:
Sự kiện làm thay đổi tiến độ:
Dữ liệu lưu sau logout/login:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 9. Checklist Phần Thưởng Nhiệm Vụ

Scope hiện tại đã chốt chỉ dùng 3 loại reward: `Exp`, `Item`, `Equipment`. Nếu tìm thấy loại khác thì ghi lại, nhưng chưa thêm schema cho tới khi được chốt.

### Cần Có

- [ ] Thưởng bao nhiêu exp.
- [ ] Thưởng item nào.
- [ ] Số lượng item thưởng.
- [ ] Thưởng trang bị nào.
- [ ] Trang bị có chỉ số cố định hay random.
- [ ] Có tiền/currency không.
- [ ] Có reward khác không.
- [ ] Text phần thưởng hiển thị trong popup.

### Gợi Ý Tên File/Bảng Cần Tìm

- [ ] `quest_reward`
- [ ] `mission_reward`
- [ ] `reward`
- [ ] `item_reward`
- [ ] `equipment_reward`
- [ ] `exp_reward`

### Mẫu Ghi Evidence

```text
Mission:
Reward type: Exp / Item / Equipment / khác
Reward key:
Số lượng:
Text hiển thị:
Cách grant:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 10. Checklist Cách Nhận Thưởng

Cần tìm flow trả nhiệm vụ và nhận thưởng.

### Cần Có

- [ ] Hoàn thành là tự nhận thưởng ngay.
- [ ] Phải quay lại NPC để trả nhiệm vụ.
- [ ] Phải bấm nút nhận/hoàn thành.
- [ ] Nếu túi đầy thì server xử lý thế nào.
- [ ] Nếu trang bị đầy thì server xử lý thế nào.
- [ ] Nếu grant reward lỗi thì nhiệm vụ có bị đánh dấu đã hoàn thành không.
- [ ] Popup `Bạn nhận được` do server gửi sẵn text hay client tự ghép.

### Gợi Ý Log Cần Tìm

- [ ] Log khi mission vừa hoàn thành.
- [ ] Log khi mở popup hoàn thành nhiệm vụ.
- [ ] Log khi nhận reward.
- [ ] Log inventory trước/sau khi nhận reward.
- [ ] Log exp trước/sau khi nhận reward.

### Mẫu Ghi Evidence

```text
Mission:
Cách trả nhiệm vụ:
Cách nhận thưởng:
Xử lý khi túi đầy:
Xử lý khi lỗi:
Popup hiển thị:
Evidence hiện có:
Còn thiếu:
Độ tin cậy:
```

## 11. Luồng Trong Game Nên Record Nếu Có Client Cũ

Nếu có client cũ hoặc log packet, hãy record theo thứ tự:

- [ ] Đăng nhập vào game, vào map đầu tiên, ghi lại NPC nào xuất hiện.
- [ ] Đi từng map và ghi lại NPC từng map.
- [ ] Bấm nói chuyện từng NPC và ghi lại dialog/menu.
- [ ] Mở danh sách nhiệm vụ.
- [ ] Xem chi tiết từng nhiệm vụ.
- [ ] Nhận nhiệm vụ.
- [ ] Làm tiến độ nhiệm vụ.
- [ ] Hủy nhiệm vụ.
- [ ] Hoàn thành/trả nhiệm vụ.
- [ ] Nhận thưởng và xem inventory/exp thay đổi.

## 12. Thứ Tự Ưu Tiên Tìm Dữ Liệu

1. [ ] Bảng/file NPC theo từng map.
2. [ ] Bảng/file mission thuộc NPC nào.
3. [ ] Bảng/file mục tiêu mission.
4. [ ] Bảng/file phần thưởng mission `Exp`, `Item`, `Equipment`.
5. [ ] Log nói chuyện NPC để biết dialog/menu.
6. [ ] Log nhận/hủy/hoàn thành mission để biết flow server.
7. [ ] Log tiến độ mission để biết cách cập nhật progress.

## 13. Mẫu Ghi Khi Tìm Được Evidence

```text
Nguồn:
File/bảng/log:
NPC/Mission liên quan:
Dữ liệu tìm được:
Phần còn thiếu:
Độ tin cậy: Java server evidence / packet log / video evidence / user policy / remake policy
Ghi chú:
```

Ví dụ:

```text
Nguồn: DB dump cũ
File/bảng/log: quest_reward.csv
NPC/Mission liên quan: mission_001
Dữ liệu tìm được: exp 100, item 5001 x1
Phần còn thiếu: điều kiện nhận mission
Độ tin cậy: Java server evidence
Ghi chú: cần đối chiếu thêm với popup reward trong client
```

## 14. Bản Mẫu Review Hiện Tại

Các dữ liệu đang có trong seed hiện tại cần user duyệt:

| Hạng mục | Giá trị hiện tại | Loại evidence | Cần xác nhận |
|---|---|---|---|
| Lính mỗi map | Có | User policy | Có đúng game gốc không |
| Tên lính | `Lính ` + tên map | User policy/remake policy | Server gốc lưu sẵn hay ghép động |
| Sprite lính | `110110` | Remake policy pending evidence | Có đúng sprite lính không |
| Tọa độ lính | `x=6`, `y=23` | Remake policy | Tọa độ thật từng map |
| Mission mẫu 1 | `mission_placeholder_001` | Remake policy | Tên/id/mục tiêu/reward thật |
| Mission mẫu 2 | `mission_placeholder_002` | Remake policy | Tên/id/mục tiêu/reward thật |
| Reward hiện hỗ trợ | `Exp`, `Item`, `Equipment` | User policy | Reward thật từng mission |

## 15. Cách Duyệt Dữ Liệu

Khi xem từng dòng, user có thể chọn một trong ba hướng:

1. Chấp nhận tạm: giữ làm remake policy để test runtime, sau thay bằng dữ liệu gốc.
2. Sửa ngay: đổi theo trí nhớ hoặc file/log user tìm được.
3. Xóa khỏi seed: nếu dễ gây hiểu nhầm là Java evidence hoặc chưa nên có.
