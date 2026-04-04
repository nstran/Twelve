# 🧪 BẢN KIỂM THỬ TIẾN ĐỘ (TEST PROGRESS)

Tệp này dùng để theo dõi các tính năng đã được bạn (USER) kiểm tra thực tế trên máy. 
Hãy đánh dấu `[x]` khi bạn đã test thành công một mục nhé!

---

## 🏗️ 1. HẠ TẦNG BACKEND (.NET 9)
- [ ] **Khởi động Server**: Chạy lệnh `dotnet run` không báo lỗi, hiện log "TCP Server started on port 2026".
- [ ] **Cổng WebSocket**: Truy cập `http://localhost:5000` (hoặc IP máy) thấy phản hồi từ Server.
- [ ] **Log Giao tiếp**: Thấy Server nhận kết nối mới khi mở Client.

---

## 📱 2. MOBILE CLIENT (EXPO + SKIA)
- [ ] **Khởi động Expo**: Quét mã QR và nạp được App lên điện thoại/emulator.
- [ ] **Kết nối WebSocket**: Màn hình hiện trạng thái "CONNECTED" hoặc "AUTHENTICATED".
- [ ] **Xử lý Binary**: Không bị lỗi "Buffer not found" hay lỗi giải mã gói tin.

---

## 🗺️ 3. TÍNH NĂNG GAME (PHASE 1)
- [ ] **Đăng nhập (Auth)**: Client tự động gửi CMD 4 và nhận được CMD 1/2 từ Server.
- [ ] **Nạp Map Hoa Lư**: Nhận được gói tin CMD 11 chứa dữ liệu Map 10x8.
- [ ] **Hiển thị Skia**: Nhìn thấy lưới (Grid) bản đồ với các ô vuông màu Xanh (đi bộ) và Đỏ (Spawn).

---

## 📝 GHI CHÚ / LỖI PHÁT SINH
*Nếu bạn gặp lỗi trong quá trình test, hãy ghi chú vào đây:*
- ...

---

**Twelve - Một Huyền Thoại Tái Sinh**
