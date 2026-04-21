# Map System Reconstruction

Tài liệu khôi phục và chuẩn hóa hệ thống map.

Mục tiêu hiện tại không còn chỉ là phục dựng `world map / zone-select` từ Java cũ, mà là chốt một kiến trúc map dùng lại được cho nhiều scene:

- `world map`: màn chọn khu / chọn địa danh
- `side-scrolling map`: map chạy ngang có quái, nhiều tầng, platform, dốc
- `navigation layer`: dữ liệu va chạm tách khỏi art để có thể tái sử dụng

## Phạm Vi

Quy ước ổn định:

- `Java old client = behavior/spec` khi có mâu thuẫn
- `art` và `navigation` là 2 lớp khác nhau
- không hard-code `groundY` cho cả map
- mọi map mới nên được author bằng `surface data`, không viết lại controller

## Legacy World Map

Phần này là map chọn khu từ Java cũ.

### Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [oh.java](/e:/Twelve/reference/redecoded/decompiled/oh.java) | `oh` | World map scene controller, load `/m/m` và `/m/lock` |
| [fz.java](/e:/Twelve/reference/redecoded/decompiled/fz.java) | `fz` | Render marker con, load `/m/arena`, `/m/room`, `/m/lock` |
| [fg.java](/e:/Twelve/reference/redecoded/decompiled/fg.java) | `fg` | Overlay phụ, load `/m/lock2` |
| [hi.java](/e:/Twelve/reference/redecoded/decompiled/hi.java) | `hi` | Splash / marker `/m/fsw` |
| [pc.java](/e:/Twelve/reference/redecoded/decompiled/pc.java) | `pc` | UI cursor `/m/hand`, `/m/arrow`, `/roomicon` |

### Asset Contract

| Asset | Vai trò |
|-------|---------|
| `/m/m` | Main overworld sheet |
| `/m/arena` | Marker arena |
| `/m/room` | Marker room |
| `/m/fsw` | Marker fsw / forest |
| `/m/lock` | Overlay khu khóa |
| `/m/lock2` | Overlay khóa cấp khác |
| `/m/hand` | Cursor focus |
| `/m/arrow` | Gợi ý khu tiếp theo |
| `/roomicon` | Icon room dùng chung |

### Working Folder

- [client/assets/map](/e:/Twelve/client/assets/map)

## Side-Scrolling Map

Đây là hướng triển khai mới cho Hoa Lư và các map chạy ngang sau này.

### Kiến Trúc

Map được chia thành 3 lớp:

- `art layer`: background, đất, đá, cây, nhà, props
- `navigation layer`: các đoạn có thể đứng / chạy / nhảy / rơi
- `entity layer`: player, quái, NPC bám theo `surface`

Điểm quan trọng:

- controller không biết hình đá cụ thể
- controller chỉ biết `surface`
- map khác chỉ cần thay data, không thay engine

### Reusable Engine

Các file nền tảng:

- [character.types.ts](/e:/Twelve/client/src/engine/character/character.types.ts)
- [surface.ts](/e:/Twelve/client/src/engine/character/surface.ts)
- [CharacterController.tsx](/e:/Twelve/client/src/engine/character/CharacterController.tsx)
- [HoaLuMapScreen.tsx](/e:/Twelve/client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx)

### Navigation Contract

Kiểu dữ liệu chuẩn:

```ts
type GroundSurface = {
  id: string;
  x1: number;
  x2: number;
  y?: number;
  y1?: number;
  y2?: number;
  ceilingOffset?: number;
  kind?: 'ground' | 'platform';
  oneWay?: boolean;
}
```

Ý nghĩa:

- `y`: mặt phẳng ngang
- `y1`, `y2`: đoạn dốc, nội suy tuyến tính từ `x1 -> x2`
- `oneWay`: sàn mỏng, nhảy từ dưới lên xuyên qua được, rơi từ trên xuống thì đáp
- `ceilingOffset`: độ dày khối rắn tính từ mặt đứng xuống mặt dưới để chặn đầu khi nhảy

### Runtime Rules

#### 1. Đứng trên mặt nào

- lấy `footX = charLeft + charWidth / 2`
- tìm mọi `surface` chứa `footX`
- chọn `surface` có độ cao gần `currentFootY` nhất

#### 2. Chạy ngang

- `groundY = yAt(footX)`
- map phẳng và map dốc dùng chung một thuật toán

#### 3. Rơi / đáp

- khi đang rơi, kiểm tra đoạn `fromFootY -> toFootY`
- nếu có `surface` cắt qua đoạn rơi thì đáp xuống đó
- `oneWay` chỉ đỡ khi đang rơi từ trên xuống

#### 4. Va đầu

- surface không phải `oneWay` có thể có mặt dưới
- nếu đầu nhân vật chạm `ceilingY`, pha bay lên dừng và chuyển sang rơi

### Helper Functions

Các hàm dùng chung nằm ở [surface.ts](/e:/Twelve/client/src/engine/character/surface.ts):

- `surfaceContainsX`
- `getSurfaceStartY`
- `getSurfaceEndY`
- `getSurfaceYAtX`
- `getSurfaceYAtFootX`
- `getSurfaceCeilingYAtX`
- `getSurfaceCeilingYAtFootX`

### Character Controller Rules

`CharacterController` hiện đã hỗ trợ:

- nhiều mặt phẳng
- platform rời nhau
- dốc lên / dốc xuống
- `oneWay platform`
- va đầu vào khối rắn
- nhảy và đáp sang tảng đá khác nếu quỹ đạo rơi cắt đúng `surface`

Tham chiếu:

- chọn mặt đứng theo `footX`: [CharacterController.tsx](/e:/Twelve/client/src/engine/character/CharacterController.tsx:194)
- support `oneWay`: [CharacterController.tsx](/e:/Twelve/client/src/engine/character/CharacterController.tsx:216)
- tìm mặt đáp khi rơi: [CharacterController.tsx](/e:/Twelve/client/src/engine/character/CharacterController.tsx:226)
- chặn đầu khi nhảy: [CharacterController.tsx](/e:/Twelve/client/src/engine/character/CharacterController.tsx:245)

## Hoa Lư

Hoa Lư là map side-scrolling đầu tiên đang dùng contract mới.

Các file:

- [HoaLuMapScreen.tsx](/e:/Twelve/client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx)
- [hoaLu.navigation.ts](/e:/Twelve/client/src/screens/map/hoa-lu/hoaLu.navigation.ts)

Nguyên tắc authoring:

- chỉ đánh dấu phần cỏ / mép đá mà chân có thể đứng
- không lấy full sprite đá làm hitbox
- nếu mặt cong, cắt thành nhiều `surface` ngắn
- nếu là dốc, ưu tiên `y1/y2`

Ví dụ:

```ts
[
  { id: 'ground', x1: 0, x2: 900, y: 720, kind: 'ground' },
  { id: 'slope_a', x1: 900, x2: 1080, y1: 720, y2: 660, kind: 'ground' },
  { id: 'rock_1', x1: 1120, x2: 1250, y: 610, kind: 'platform', oneWay: true },
  { id: 'rock_2', x1: 1320, x2: 1450, y: 540, kind: 'ground', ceilingOffset: 40 },
]
```

## Cách Dùng Cho Map Mới

Khi thêm map khác, không copy logic Hoa Lư. Chỉ cần:

1. thêm background / art riêng của map
2. tạo file `navigation.ts` chứa danh sách `GroundSurface`
3. đặt vị trí spawn player, quái, NPC theo `surface`
4. dùng lại `CharacterController`

Điều này áp dụng cho:

- map đá khác nhau
- địa hình khác nhau
- nhiều tầng
- platform rời nhau
- dốc
- map có trần cứng hoặc sàn mỏng

## Chưa Làm

Các phần chưa chốt hẳn:

- format dữ liệu map dùng chung ở cấp project, ví dụ `client/src/maps/<map-id>/navigation.ts`
- công cụ author surface trực quan
- `jump links` cho AI / auto path giữa các platform
- ceiling authoring chi tiết cho những khối có underside phức tạp

## Kết Luận

Map system nên xem như 2 bài toán riêng:

- `world map` cũ: phục dựng asset + marker + select flow
- `side-scrolling map` mới: dùng `surface navigation` làm nền tảng

Phần quan trọng nhất đã chốt:

- engine hiện tại là dùng chung
- không phụ thuộc riêng Hoa Lư
- map mới chỉ cần thay `surface data` và art
