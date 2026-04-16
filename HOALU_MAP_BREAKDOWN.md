# Hoa Lu Map Breakdown

## Mục đích

Tài liệu này chốt cách bắt đầu phục hồi `Hoa Lư` thành map gameplay đầu tiên.

Hoa Lư là ứng viên tốt nhất để làm map mẫu vì:

- xuất hiện đầu tiên trên world map
- có screenshot gameplay rõ
- có nhiều reference mỹ thuật hơn các map khác

## Dữ liệu Chắc Chắn

### 1. Hoa Lư là map đầu tiên trên world map

- danh sách world map ở [og.java:17](/d:/Twelve/reference/redecoded/decompiled/og.java:17)
- `Hoa Lư` nằm ở `index 0`

### 2. World map dùng ảnh chung

- world map load từ [oh.java:53](/d:/Twelve/reference/redecoded/decompiled/oh.java:53)
- file ảnh là `"/m/m"`

### 3. Gameplay map không load từ file `HoaLu.png` trong jar

- gameplay map load image layer qua [om.java:254](/d:/Twelve/reference/redecoded/decompiled/om.java:254) và [om.java:270](/d:/Twelve/reference/redecoded/decompiled/om.java:270)
- resource đi qua ID và cache ở [om.java:1203](/d:/Twelve/reference/redecoded/decompiled/om.java:1203)

Kết luận:

- mọi file `HoaLu.png` trong `reference/raw/images` chỉ nên được xem là `reference art`
- không được coi đó là asset gốc trích xuất trực tiếp từ jar nếu chưa có bằng chứng khác

## Reference Nên Bám Theo

### World map reference

- [m.png](/d:/Twelve/reference/review_assets/canonical_from_jar_png/m/m.png)

Vai trò:

- xác định Hoa Lư là một vùng quan trọng trên world map
- không dùng để dựng gameplay terrain

### Gameplay screenshot reference

- [7687236765567_f354_gamecrop.png](/d:/Twelve/reference/raw/images/7687236765567_f354_gamecrop.png)
- [7687236765567_f486_gamecrop.png](/d:/Twelve/reference/raw/images/7687236765567_f486_gamecrop.png)
- [7687236765567_f619_gamecrop.png](/d:/Twelve/reference/raw/images/7687236765567_f619_gamecrop.png)

Những ảnh này có giá trị cao nhất để dựng gameplay vì chúng cho thấy:

- tỉ lệ nhân vật so với địa hình
- vật liệu nền đất
- độ dày mép cỏ
- layout platform
- trời mưa
- quái xuất hiện trên terrain thật

### Background art reference

- [HoaLu.png](/d:/Twelve/reference/raw/images/HoaLu.png)
- [Map.png](/d:/Twelve/reference/raw/images/Map.png)
- [oldmap2.png](/d:/Twelve/reference/raw/images/oldmap2.png)

Ghi chú:

- `oldmap2.png` và `image1.png` là cùng một nội dung
- các ảnh này nên dùng để rút ra `theme art`, không mặc định coi là final in-game extraction

## Đặc Trưng Mỹ Thuật Của Hoa Lư

Từ screenshot gameplay và reference art, Hoa Lư có các đặc trưng sau:

- nền trời xanh sáng
- nhiều đảo nổi / núi đất treo
- thác nước đổ từ đảo nổi
- mặt nước lớn ở hậu cảnh
- kiến trúc đình/miếu mái cong kiểu cổ
- terrain gameplay chính là đất vàng nâu với mặt cỏ xanh
- có thang đứng nối các platform
- có biến thể thời tiết `mưa`

## Bộ Thành Phần Cần Dựng

### 1. Far background

Cần có:

- trời
- mây
- đảo nổi xa
- mặt nước xa
- núi xa

Nguồn bám:

- [HoaLu.png](/d:/Twelve/reference/raw/images/HoaLu.png)
- [Map.png](/d:/Twelve/reference/raw/images/Map.png)

### 2. Near background

Cần có:

- cổng lớn
- đình nhỏ
- đảo nổi gần
- cây nền
- thác nước gần

Nguồn bám:

- [Map.png](/d:/Twelve/reference/raw/images/Map.png)
- [oldmap2.png](/d:/Twelve/reference/raw/images/oldmap2.png)

### 3. Terrain tiles

Cần tách riêng:

- mép cỏ phẳng
- góc cỏ trái
- góc cỏ phải
- thân đất
- mặt dưới platform
- dốc xuống
- dốc lên
- mép hốc / bậc

Nguồn bám:

- [7687236765567_f354_gamecrop.png](/d:/Twelve/reference/raw/images/7687236765567_f354_gamecrop.png)
- [7687236765567_f486_gamecrop.png](/d:/Twelve/reference/raw/images/7687236765567_f486_gamecrop.png)
- [7687236765567_f619_gamecrop.png](/d:/Twelve/reference/raw/images/7687236765567_f619_gamecrop.png)

### 4. Traversal objects

Cần ít nhất:

- thang đứng
- thang dài
- đá nhỏ
- bụi cỏ nhỏ

### 5. Decorative props

Cần ít nhất:

- cây lớn
- bụi hoa
- cổng
- đình nhỏ

### 6. Weather layer

Hoa Lư phải hỗ trợ:

- `clear`
- `rain`

Mưa phải là overlay riêng. Không được bake cố định vào background chính.

## Layout Gameplay Tối Thiểu Cần Có

Map Hoa Lư đầu tiên nên có:

- một platform chính dài
- ít nhất hai tầng cao độ
- ít nhất một đoạn dốc
- ít nhất một thang đứng
- một khu spawn quái gần đường chính
- một khu để test camera và jump

## Quái Và Tỉ Lệ

Từ screenshot gameplay:

- quái nhỏ kiểu `Gà điện` xuất hiện trên platform
- nhân vật cao khoảng tương đương phần cao của platform edge
- terrain cần giữ cảm giác `J2ME side-scroller`, không nên phóng to quá sạch hoặc quá HD

Nguyên tắc scale:

- ưu tiên giữ `silhouette` của gameplay cũ
- scale map theo nhân vật, không scale theo ảnh background rộng

## Những Gì Cần Xuất Ra Khi Bắt Đầu Dựng

Thư mục đề nghị cho Hoa Lư:

- `background_far.png`
- `background_near.png`
- `tileset_terrain.png`
- `objects_architecture.png`
- `objects_nature.png`
- `weather_rain.png`
- `collision.json`
- `spawn_points.json`
- `layout_notes.md`

## Open Questions

Hiện vẫn chưa biết chắc từ Java cũ:

- BG gốc exact của Hoa Lư có còn trong cache legacy nào khác hay không
- số lượng layer gốc của Hoa Lư nhiều hơn 2 image layer ở runtime tới mức nào
- layout collision gốc có bao nhiêu platform ngoài vùng screenshot hiện có

Do đó, Hoa Lư phải được dựng theo chuẩn:

- `faithful reconstruction`
- không ghi là `extracted original map` trừ khi có bằng chứng mới

## Definition Of Done Cho Hoa Lư Bản Đầu

Hoa Lư được xem là đủ tốt để làm `map mẫu` khi:

1. nhìn vào là nhận ra đúng phong cách cũ
2. có world-map entry rõ ràng
3. có gameplay loop đi, đứng, nhảy, leo thang
4. có quái spawn trên platform đúng kiểu screenshot
5. có biến thể mưa
6. collision không bị lệch với terrain art

## Kết luận

Hoa Lư nên là map đầu tiên để chuẩn hóa pipeline phục hồi map.

Nếu dựng đúng Hoa Lư, ta sẽ có luôn:

- mẫu layer structure
- mẫu tileset terrain
- mẫu weather overlay
- mẫu collision authoring
- mẫu object placement

Từ đó mới nên nhân sang `Đỗ Động Giang`, `Tam Đái`, và các map còn lại.
