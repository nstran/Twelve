# MAP_SYSTEM_RECONSTRUCTION.md

## Mục tiêu

Khôi phục hệ thống map/world map/runtime map của Twelve từ Java client cũ sang React Native + .NET 9.

Nguyên tắc:

- Java old client là behavior/spec khi có dữ liệu.
- Không tự ý đổi asset key, tọa độ, cursor, softkey hoặc unlock rule nếu chưa có bằng chứng từ Java/data.
- Logic gameplay quan trọng phải ghi nguồn suy luận trong code/tài liệu.
- World map hiện là catalog legacy; runtime playable đầu tiên là Hoa Lư side-scroll.

---

## 1. Legacy world map theo Java

### 1.1 Source Java chính

Các file Java decompile làm nguồn suy luận chính:

- `oh.java`: màn hình world map/country selection, background `/m/m`, cursor, chọn map, softkey `Vào Thành`.
- `fz.java`: danh sách map/country entries, tọa độ icon/label, trạng thái khóa/mở.
- `fg.java`: renderer icon world map, marker, lock, selected state.
- `hi.java`: image/asset loader/cache.
- `pc.java`: metadata/country catalog provider được world map dùng.
- `og.java`: input/canvas/game loop liên quan màn hình map.
- `ks.java`: command/softkey model.

### 1.2 Asset contract

Asset logical key cần giữ theo Java client:

- `/m/m`
- `/m/arena`
- `/m/room`
- `/m/fsw`
- `/m/lock`
- `/m/lock2`
- `/m/hand`
- `/m/arrow`
- `/roomicon`

Rule:

- Có thể map key legacy sang asset React Native nội bộ, nhưng logical contract không đổi.
- World map dùng hệ tọa độ base `480x480`.
- Nếu asset/Skia không khả dụng, fallback renderer vẫn phải hiển thị bằng React Native primitive, không phụ thuộc Skia.

### 1.3 World map UI rules

- Background world map là `/m/m`, scale theo layout `480x480`.
- Input touch/pan dùng `PanResponder`.
- Pointer phải convert về hệ tọa độ `480x480` trước khi hit-test entry.
- Cursor:
  - `/m/arrow`: selected/hover entry thường.
  - `/m/hand`: entry có runtime target có thể vào.
- Softkey trái theo Java: `Vào Thành`.
- `Vào Thành` chỉ active khi selected entry mở và có runtime target hợp lệ.

---

## 2. Server catalog

### 2.1 Endpoint

Endpoint catalog hiện tại:

```http
GET /map/world-catalog
```

Catalog trả về:

- danh sách world map/country entries;
- tọa độ legacy;
- lock/open state;
- optional runtime target.

### 2.2 `RuntimeMapCatalog.AllWorldMaps`

`RuntimeMapCatalog.AllWorldMaps` là source server hiện tại cho world map catalog.

Rule hiện chốt:

- Hoa Lư là entry mở duy nhất trỏ vào side-scroll runtime thật:
  - `runtimeMapId = "Hoa Lu"`
  - `defaultRoomId = 1`
  - `sceneKind = "sideScroll"`
- Các map còn lại tạm là `legacy` hoặc locked.
- Không mở thêm map nếu chưa có runtime data và unlock/progression rule từ Java/data.

---

## 3. Runtime side-scroll architecture

Runtime map playable được tách 3 lớp:

1. **Art layer**
   - Background, midground, foreground, tile/decoration.
   - Chỉ chịu trách nhiệm render.
2. **Navigation layer**
   - Ground, one-way platform, slope, wall, ceiling, portal.
   - Quyết định collision và movement.
3. **Entity layer**
   - Player, NPC, monster, portal marker, trigger/interactable object.
   - Không hard-code entity vào art layer.

Rule authoring:

- Map/room mới cùng topology nên thêm bằng scene config/data/art.
- Nếu cần sửa engine, phải ghi rõ nguồn suy luận Java hoặc tài liệu liên quan.

### 3.1 `GroundSurface` contract

`GroundSurface` đại diện cho bề mặt đứng/chạy.

Thuộc tính cốt lõi:

- `id`
- `x1`, `y1`, `x2`, `y2`
- `kind`
  - `solid`
  - `oneWay`
  - `slope`

Collision rule:

- Player chỉ đứng trên surface hợp lệ khi chân đi từ trên xuống hoặc đang snap vào mặt đất.
- Surface có thể là ngang hoặc slope.
- Không dùng pixel collision nếu chưa có bằng chứng Java yêu cầu.

### 3.2 Runtime movement rules

- Đứng trên mặt đất khi chân chạm `GroundSurface`.
- Chạy trái/phải theo input.
- Nếu không còn surface dưới chân thì chuyển sang falling.
- Khi falling và giao với surface hợp lệ thì đáp xuống.
- Nếu đi lên chạm ceiling thì va đầu và vận tốc Y bị chặn theo navigation rule.
- Không xuyên wall theo navigation layer.
- Camera follow player trong bounds scene.

### 3.3 `CharacterController` status

Status runtime phản ánh movement/collision thực tế:

- `idle`
- `running`
- `falling`
- `jumping`
- trạng thái va chạm/head-hit nếu engine cần expose cho animation/debug.

Không dùng status chỉ để đổi sprite nếu physics chưa tương ứng.

---

## 4. Hoa Lư runtime map

### 4.1 Files chính

- `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`
- `client/src/screens/map/hoa-lu/HoaLuScene.ts`
- `client/src/screens/map/hoa-lu/HoaLuTileMap.tsx`
- `client/src/screens/map/core/*`
- `client/src/engine/character/*`
- `client/src/engine/MapRenderer.tsx`
- `server/Twelve.Core/Maps/RuntimeMapCatalog.cs`

### 4.2 Authoring rule

Hoa Lư là runtime side-scroll đầu tiên.

Khi thêm map/room cùng loại:

- Tạo scene config/data tương tự Hoa Lư.
- Khai báo art/navigation/entities bằng data.
- Không hard-code NPC/monster/portal vào renderer.
- Logic quan trọng mới phải ghi nguồn suy luận từ Java/client/data.

### 4.3 Scene config direction

Scene config nên tiến tới mô hình:

- `sceneId`
- `runtimeMapId`
- `roomId`
- `kind`
- `bounds`
- `spawnPoints`
- `artLayers`
- `navigation`
  - `groundSurfaces`
  - `walls`
  - `ceilings`
  - `portals`
- `entities`
  - `npcs`
  - `monsters`
  - `interactiveObjects`

---

## 5. Chưa làm cho map bên ngoài/world runtime

Các phần cần làm để map bên ngoài playable:

- DB/server roster:
  - runtime maps;
  - rooms;
  - default spawn;
  - unlock/progression;
  - NPC/monster/portal theo room.
- Portal/room transition:
  - trigger vùng portal;
  - chuyển room/map;
  - spawn point đích;
  - loading/animation nếu có bằng chứng Java.
- NPC:
  - roster data-driven;
  - vị trí;
  - sprite/asset;
  - interaction/dialog/shop/quest theo tài liệu liên quan.
- Monster:
  - spawn point;
  - idle/patrol/chase rule;
  - encounter/battle trigger;
  - respawn rule.
- Runtime/server sync:
  - player position persistence;
  - map entry/exit;
  - room state.
- Unlock/progression:
  - hiện chỉ Hoa Lư mở;
  - chưa có rule để mở map khác ngoài Hoa Lư.