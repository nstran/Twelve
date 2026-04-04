# 🐲 PROJECT TWELVE - 2026 🐲

**Twelve** là dự án hiện đại hóa huyền thoại J2ME **"Loạn 12 Sứ Quân"** thành một trò chơi trực tuyến đa nền tảng (iOS & Android) với hiệu năng cực cao.

---

## 🛰️ Tầm nhìn (Vision)
Kế thừa linh hồn của phiên bản J2ME cũ, nhưng nâng tầm trải nghiệm đồ họa lên **60FPS** và hệ thống Server **.NET 9** hiện đại. 

---

## 🛠️ Yêu cầu hệ thống (Prerequisites)

Trước khi bắt đầu, hãy đảm bảo máy tính của bạn đã cài đặt các công cụ sau:

1.  **Backend**: [.NET 9 SDK](https://dotnet.microsoft.com/en-us/download/dotnet/9.0)
2.  **Frontend**: [Node.js (LTS)](https://nodejs.org/)
3.  **Mobile Testing**: Cài đặt ứng dụng **Expo Go** từ App Store hoặc Google Play trên điện thoại của bạn.

---

## 🏗️ Cài đặt & Khởi chạy (Setup)

### 1. Khởi chạy Server (Backend)
Mở terminal tại thư mục gốc dự án:
```powershell
# Di chuyển vào thư mục Server
cd server/Twelve.Server

# Khôi phục các gói phụ thuộc
dotnet restore

# Chạy Server
dotnet run
```
*Ghi chú: Server sẽ lắng nghe tại cổng TCP **2026** (cho J2ME) và cổng HTTP **5000** (cho WebSocket di động).*

### 2. Khởi chạy Mobile App (Frontend)
Mở một terminal mới:
```powershell
# Di chuyển vào thư mục Client
cd client

# Cài đặt các thư viện (chỉ thực hiện lần đầu)
npm install

# Khởi chạy Expo
npx expo start
```
*Quét mã QR hiển thị trên màn hình bằng ứng dụng **Expo Go**.*

---

## 📡 Mẹo kết nối thiết bị thật (Networking)

Nếu bạn chạy App trên điện thoại thật (thay vì Emulator), hãy lưu ý:
1.  **Cùng mạng Wi-Fi**: Đảm bảo điện thoại và máy tính chạy Server kết nối cùng một mạng Wi-Fi.
2.  **Cấu hình IP**: Trong tệp `client/src/screens/MainScreen.tsx`, hãy thay đổi `localhost` thành địa chỉ IP nội bộ của máy tính bạn (ví dụ: `192.168.1.5`).

---

## 🗺️ Lộ trình (Roadmap)
Xem chi tiết các giai đoạn tiếp theo tại [**PLAN.md**](./PLAN.md).

---

**Twelve - Một Huyền Thoại Tái Sinh**
