# Map Reconstruction Guide

## Mục tiêu

Tài liệu này chốt cách phục hồi `map gameplay` của **Loạn 12 Sứ Quân** khi:

- Java cũ chỉ còn đủ để làm `spec hành vi`
- `world map` vẫn còn trong jar
- `map gameplay BG` gốc nhiều khả năng không còn trong jar và phải dựng lại

Hướng đúng là:

- dùng Java cũ để lấy `cấu trúc map`
- dùng ảnh gameplay cũ để lấy `bố cục và mỹ thuật`
- dựng lại `background / tile / collision / object / weather`
- tuyệt đối không giả định rằng một file PNG trong jar là toàn bộ map gameplay

## Điều Java Cũ Xác Nhận

### 1. World map tồn tại sẵn trong jar

- file `"/m/m"` được load ở [oh.java:53](/d:/Twelve/reference/redecoded/decompiled/oh.java:53)
- đây là bản đồ tổng có các vùng như Hoa Lư, Tam Đái, Cổ Loa

### 2. Danh sách địa danh nằm trong client

- danh sách map xuất hiện ở [og.java:17](/d:/Twelve/reference/redecoded/decompiled/og.java:17)
- ví dụ:
  - `Hoa Lư`
  - `Tam Đái`
  - `Đỗ Động Giang`

### 3. Chọn map từ world map không đi thẳng vào một PNG cố định

- client request vào thành bằng `M99 + index` ở [og.java:123](/d:/Twelve/reference/redecoded/decompiled/og.java:123)
- request này được đóng gói ở [ks.java:1053](/d:/Twelve/reference/redecoded/decompiled/ks.java:1053)

Điều này rất quan trọng: `M99` là màn chọn thành, không phải mã của map gameplay Hoa Lư hay Tam Đái.

### 4. Map gameplay được server cấp dữ liệu

- metadata map được parse ở [ky.java:1391](/d:/Twelve/reference/redecoded/decompiled/ky.java:1391) và [ky.java:1392](/d:/Twelve/reference/redecoded/decompiled/ky.java:1392)
- map resource được cache ở [pa.java:247](/d:/Twelve/reference/redecoded/decompiled/pa.java:247)
- đường đi lấy dữ liệu map theo `map code`, không phải hardcoded local BG, thể hiện ở [pa.java:257](/d:/Twelve/reference/redecoded/decompiled/pa.java:257) và [pa.java:410](/d:/Twelve/reference/redecoded/decompiled/pa.java:410)

### 5. Map gameplay gồm nhiều thành phần

Client map runtime load ít nhất các phần sau:

- image layer 1 ở [om.java:254](/d:/Twelve/reference/redecoded/decompiled/om.java:254)
- image layer 2 ở [om.java:270](/d:/Twelve/reference/redecoded/decompiled/om.java:270)
- resource bytes được lấy từ `oa.b[...]` ở [om.java:1203](/d:/Twelve/reference/redecoded/decompiled/om.java:1203)
- object placement đi qua `jm[]` ở [jm.java](/d:/Twelve/reference/redecoded/decompiled/jm.java)

Kết luận kỹ thuật:

- một map gameplay không phải chỉ là `1 ảnh nền`
- nó là `2 image layers + tile/logic + objects + actors`

## Hệ Mô Hình Map Phải Giữ

Mỗi map gameplay khi phục hồi cần được chia thành 5 lớp:

1. `far_background`
2. `near_background`
3. `terrain_tiles`
4. `interactive_objects`
5. `collision_and_spawn`

### 1. `far_background`

Thường là:

- trời
- núi xa
- nước xa
- đảo xa
- kiến trúc xa

Lớp này tạo chiều sâu và không quyết định va chạm.

### 2. `near_background`

Thường là:

- đảo nổi gần hơn
- cổng, đình, kiến trúc nền
- cây lớn nền
- waterfall hoặc chi tiết môi trường lớn

Lớp này vẫn chủ yếu là trang trí, nhưng quan trọng để map trông giống game cũ.

### 3. `terrain_tiles`

Đây là phần bắt buộc phải dựng hệ tile:

- mặt cỏ
- thân đất / vách đất
- góc dốc / mép nền
- nền giữa
- thang dây / thang gỗ
- bậc / gờ

Ảnh gameplay cũ cho thấy đây là phần lặp lại theo tile, không phải một bức tranh phẳng duy nhất.

### 4. `interactive_objects`

Bao gồm:

- cổng
- cây
- nhà
- đá
- vật trang trí có chiều sâu
- cổng dịch chuyển nếu có

Object phải được tách khỏi `terrain_tiles` để còn di chuyển, che lớp, và tái sử dụng.

### 5. `collision_and_spawn`

Đây là phần Java cũ đặc biệt hữu ích:

- vùng đứng được
- nền một chiều hay nền đặc
- điểm spawn quái
- điểm spawn player
- điểm vào/ra map

Nếu chỉ vẽ lại đẹp mà không dựng đúng lớp này, map sẽ sai cảm giác chơi.

## Quy Trình Dựng Lại Map

### Bước 1. Chốt map reference thật

Nguồn ưu tiên:

1. screenshot gameplay cũ
2. video gameplay cũ
3. world map để xác định theme khu vực
4. Java cũ để xác định cấu trúc runtime

Không dùng:

- asset mới tự dựng không có chứng cứ
- mockup chưa đối chiếu screenshot

### Bước 2. Tách screenshot thành thành phần

Mỗi screenshot cần annotate theo:

- trời
- hậu cảnh xa
- hậu cảnh gần
- nền cỏ
- thân đất
- dốc
- thang
- props
- quái
- UI
- hiệu ứng thời tiết

### Bước 3. Dựng tile set tối thiểu

Ít nhất phải có:

- `grass_top`
- `grass_edge_left`
- `grass_edge_right`
- `dirt_fill`
- `dirt_slope_left`
- `dirt_slope_right`
- `platform_bottom`
- `ladder`
- `rock_small`

Nguyên tắc:

- tile phải loop tốt
- texture đất không bị vỡ nhịp khi ghép dọc
- mép cỏ phải đủ dày để nhìn đúng tỉ lệ J2ME

### Bước 4. Dựng background lớn riêng

Background lớn nên dựng dưới dạng:

- panorama rộng
- hoặc nhiều panel ghép ngang

Không nên trộn luôn platform vào background, vì sẽ làm hỏng collision và camera slicing.

### Bước 5. Dựng object library

Tối thiểu với mỗi map cần:

- nhà/cổng chính
- cây lớn
- bụi cỏ
- thác nước
- đá nhỏ
- kiến trúc xa

### Bước 6. Chốt collision từ layout gameplay

Cần xuất riêng:

- platform rectangles
- ladder rectangles
- spawn points
- monster patrol bands

### Bước 7. Gắn weather/lighting

Ảnh gameplay Hoa Lư cho thấy có biến thể `mưa`.

Biến thể thời tiết phải được xem là `layer riêng`, không bake vào terrain chính.

## Những Gì Không Được Làm

- Không gọi `m.png` là map gameplay.
- Không lấy một screenshot có UI rồi dùng thẳng làm background cuối cùng.
- Không vẽ liền cả map vào một ảnh duy nhất rồi mới nghĩ tới collision.
- Không coi Java cũ là nguồn cung cấp file BG gốc nếu bằng chứng không có.

## Deliverable Chuẩn Cho Mỗi Map

Mỗi map nên có thư mục riêng với các file sau:

- `README.md`
- `references/`
- `background_far.png`
- `background_near.png`
- `tileset.png`
- `objects/`
- `collision.json`
- `spawn_points.json`
- `map_layout.json`

## Thứ Tự Làm Việc Khuyến Nghị

1. `Hoa Lư`
2. `Đỗ Động Giang`
3. `Tam Đái`

Lý do:

- Hoa Lư hiện có nhiều screenshot/reference nhất
- Đỗ Động Giang đã có ít nhất một ảnh gameplay rõ
- Tam Đái cần dựng sau khi pipeline map đã ổn

## Kết Luận

Với dữ liệu hiện tại, chiến lược đúng không phải là `đi tìm một file BG PNG cuối cùng trong jar`.

Chiến lược đúng là:

- dùng Java cũ để lấy `map model`
- dùng screenshot/video cũ để lấy `visual truth`
- dựng lại map thành các lớp có thể chơi được

Đây là `reconstruction`, không phải `raw extraction`.
