# 🐲 Twelve Bug Tracking & Logs

## 🚩 Lỗi Chưa Fix (Open Bugs)
- **#001**: Chưa khởi tạo project - Đang kẹt ở bước setup.
- **#002**: Lỗi packet Cmd 11 - J2ME client yêu cầu dữ liệu gạch nhưng server chưa gửi.

---

## 🛠️ Lỗi Đã Fix (Resolved Bugs)
- **#004**: Đã đổi tên dự án từ L12SQ sang **Twelve** toàn cục.

---

## 📒 Nhật ký kỹ thuật (Reflector)
- [2026-04-04]: Quyết định dùng **.NET 9 + Dapper** thay cho **ABP** để tối ưu tốc độ cho socket game.
- [2026-04-04]: Cấu trúc TLV: Header (10 bytes) -> Payload (N bytes Tags).
