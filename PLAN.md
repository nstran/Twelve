# 🗺️ LỘ TRÌNH PHÁT TRIỂN TWELVE (PLAN.md)

Dưới đây là kế hoạch chi tiết để đưa **Loạn 12 Sứ Quân** trở lại rực rỡ nhất nam 2026.

---

## 🏗️ GIAI ĐOẠN 1: NỀN TẢNG & KẾT NỐI (DONE)
Mục tiêu: Xây dựng hệ thống Server & Client ban đầu.

- [x] **Backend .NET 9**: Kiến trúc Clean Architecture, hiệu năng cao.
- [x] **TlvCodec**: Bộ giải mã Binary tương thích J2ME.
- [x] **WebSocket Bridge**: Cổng kết nối hiện đại cho Mobile.
- [x] **Auth System**: Đăng nhập mồi (Stub Data) để vào Game.
- [x] **Map Loader**: Nạp thông số bản đồ Hoa Lư (10x8 Tiles).
- [x] **Expo Initialization**: Khởi tạo Mobile Client (RN + Skia).

---

## ⚔️ GIAI ĐOẠN 2: GAMEPLAY CORE (TIẾP THEO)
Mục tiêu: Tái hiện linh hồn Match-3 và di chuyển trên Map.

### 2.1 Skia Rendering Engine (🟢 Đang làm)
- [ ] **Character Sprite**: Hiển thị nhân vật trên Map.
- [ ] **Animations**: Di chuyển nhân vật (Walk/Idle).
- [ ] **Map Viewport**: Hệ thống Camera theo chân nhân vật.

### 2.2 Hệ thống Trận đánh (Match-3 Battle)
- [ ] **Battle Socket Handler**: Gói tin đồng bộ trạng thái ngọc.
- [ ] **Match-3 Logic**: Thuật toán rơi ngọc, Combo, Phá hủy.
- [ ] **Vòng lặp Turn-based**: Lượt chơi giữa người và Boss/Người chơi khác.

---

## 🌐 GIAI ĐOẠN 3: TÍNH NĂNG TRỰC TUYẾN
Mục tiêu: Lưu trữ dữ liệu, PvP và Cộng đồng.

- [ ] **PostgreSQL**: Lưu trữ nhân vật, EXP, Trang bị.
- [ ] **Inventory System**: Quản lý túi đồ và Cường hóa.
- [ ] **PvP Arena**: Đấu trường thời gian thực.
- [ ] **Chat System**: Hệ thống Chat kênh thế giới.

---

## 📊 KẾ HOẠCH TEST (VERIFICATION)
1. **Manual Test**: Chạy Server .NET -> Mở Mobile App -> Thấy Map Hoa Lư.
2. **Stress Test**: Kết nối 100 client ảo (Simulated) để thử nghiệm tải Server.
3. **Build iOS/Android**: Xuất file APK/IPA thử nghiệm trên thiết bị thật.

**Twelve - Một Huyền Thoại Tái Sinh**
