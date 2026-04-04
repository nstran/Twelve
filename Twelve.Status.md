# 📊 Twelve Project - Bản Đồ Tiến Độ Chi Tiết

Dự án: **Twelve (Loạn 12 Sứ Quân Modernization 2026)**  
Kiến trúc: **Clean Architecture (.NET 9) + React Native (Skia)**

---

## 🏗️ Giai đoạn 1: Khởi tạo & Giao thức (Foundation)
Mục tiêu: Dựng "xương sống" server và giúp Client J2ME load được vào Map.

| STT | Công việc | Chi tiết kỹ thuật | Trạng thái |
| :--- | :--- | :--- | :--- |
| 1.1 | **Hệ thống Agent & Rule** | Chuẩn hóa `.agent`, Skills & Agents | [🟢 Xong] |
| 1.2 | **Cấu trúc Solution .NET 9** | Twelve.sln (Core, App, Infra, Server) | [🟢 Xong] |
| 1.3 | **Bộ giải mã TlvCodec** | Đọc/Ghi binary Big-Endian (J2ME) | [🟢 Xong] |
| 1.4 | **Auth Handshake (Cmd 1/2)** | Đăng ký & Đăng nhập mồi (Stub Data) | [🟢 Xong] |
| 1.5 | **Map Loader (Cmd 11/13/29)** | Giao thức load gạch (Tiles) Hoa Lư | [🟢 Xong] |
| 1.6 | **WebSocket Bridge (Game)** | Cổng kết nối hiện đại cho Mobile | [🟢 Xong] |
| 1.7 | **Skia Map Renderer** | Hiển thị 10x8 Tiles Hoa Lư (Grid) | [🟢 Xong] |

---

## ⚔️ Giai đoạn 2: Gameplay Core (Linh hồn Game)
Mục tiêu: Tái hiện trận đánh Match-3 và Di chuyển Map.

| STT | Công việc | Chi tiết kỹ thuật | Trạng thái |
| :--- | :--- | :--- | :--- |
| 2.1 | **Side-scrolling Engine** | Skia rendering (Map + Char) | [🔴 Chờ] |
| 2.2 | **Engine Match-3** | Thuật toán rơi ngọc & Tính Combo | [🔴 Chờ] |
| 2.3 | **Hệ thống Chỉ số** | Công, Thủ, Máu, Nộ, MP (Domain Logic) | [🔴 Chờ] |
| 2.4 | **Hệ thống Hệ (Element)** | Skill Hỏa/Thủy/Lôi cho Match 4/5 | [🔴 Chờ] |
| 2.5 | **Kinh tế Game** | 10k Gold battle = 10k Ken game | [🔴 Chờ] |

---

## 🌐 Giai đoạn 3: Tính năng Online & Dữ liệu
Mục tiêu: Lưu trữ, PvP và Shop.

| STT | Công việc | Chi tiết kỹ thuật | Trạng thái |
| :--- | :--- | :--- | :--- |
| 3.1 | **Database (Postgres)** | Dapper setup & Schema (Players, Items) | [🔴 Chờ] |
| 3.2 | **Hệ thống Túi đồ** | Inventory management & Trang bị | [🔴 Chờ] |
| 3.3 | **PvP Lobby** | Tìm trận & Đồng bộ trạng thái ngọc | [🔴 Chờ] |
| 3.4 | **Hệ thống Boss** | AI Boss đơn giản & Phần thưởng | [🔴 Chờ] |

---

## 🚦 Trạng thái Chốt hạ (Target)
- [ ] Server .NET 9 phản hồi được CMD 11.
- [ ] App Mobile (iOS/Android) nhìn thấy nhân vật di chuyển trên map.
- [ ] Thực hiện được 1 trận đấu Match-3 hoàn chỉnh.

---

## 📝 Nhật ký cập nhật (Recent Updates)
- [2026-04-04]: Chốt tên dự án là **Twelve**. Thiết lập chuẩn .NET 9.
