# Huong Dan Thiet Ke Ban Do - Loan 12 Su Quan Online

## 1. TONG QUAN BAN DO TU VIDEO THAM KHAO

Qua phan tich 2 video tham khao (TikTok tu kenh "Loan 12 Su Quan Online Beta 2030"), ban do game co cac dac diem chinh:

### 1.1 Phong cach thi giac
- **The loai**: Side-scrolling 2D Platformer RPG (cuon ngang)
- **Goc nhin**: Camera nhin ngang, theo chan nhan vat
- **Do phan giai goc**: ~240x160px (J2ME), duoc upscale len thiet bi hien dai
- **Mau sac**: Pixel art tuoi sang, phong cach anime/chibi

### 1.2 Cac thanh phan nhin thay tren map (tu video)
- **Nhan vat**: Sprite chibi di chuyen tren nen tang (platform)
- **Quai vat**: "Ga dien" (monsters) dung tren cac platform, co ten hien phia tren
- **Dia hinh**: Co xanh phia tren, dat/da phia duoi, tao thanh cac tang nen
- **Cay coi**: Cay xanh lam nen canh (background decoration)
- **Hieu ung thoi tiet**: Mua roi (rain particles)
- **UI HUD**: Thanh mau (HP), ten map "Hoa Lu 3", thanh EXP, thong so

### 1.3 Phan tich chi tiet tu anh crop gameplay

#### Anh 1 - f354 (Goc nhin xa, doc):
- Dia hinh co DO DOC (slope) - duong di cheo tu tren-trai xuong duoi-phai
- Co xanh phu tren be mat doc, dat nau o mat cat
- Co NHIEU TANG NEN (multi-level platform), khong chi phang
- Quai "Ga dien" nam rai rac tren cac nen tang
- Co bui co xanh nho (decoration) tren mat dat

#### Anh 2 - f486 (Platform da tang):
- Ro rang thay CAU TRUC PLATFORM: 2 tang rieng biet
- Tang tren: co xanh + dat nau, co cay lon lam nen
- Tang duoi: nen tang rieng voi co xanh, cach tang tren 1 khoang trong
- Nhan vat dang chien dau voi nhom "Ga dien" (3 con)
- Cay lon lam FOREGROUND che phu mot phan man hinh

#### Anh 3 - f619 (Nhin gan nhan vat):
- Platform don voi co xanh day, dat nau phia duoi
- Co vach da/tuong doc phia ben trai (slope wall)
- Quai "Ga dien" o ca tang tren va tang duoi
- Khong gian trong (gap) giua 2 platform - nhan vat co the nhay hoac roi xuong

#### Anh 4 - bx2n6nagkdfvkvha3 (BAN GOC J2ME - Rat quan trong!):
- Day la ban GOC CHINH THUC cua game Loan 12 Su Quan tren J2ME
- **Background**: Cuc ky chi tiet - nui xanh, may trang, thac nuoc, dao bay
- **Cong trinh**: Nha co viet nam (mai ngoi hong/do), cong vao lang
- **NPC**: "Truong Lang Gia V..." (ong gia dung canh nha)
- **Quai**: "Ga Dien" + thu cung trang (tho?)
- **Nhan vat**: Chibi toc xanh, than hinh nho, dung tren co
- **Thong bao**: "Ban moi co nhiem vu. Hay bam phim menu..."
- **UI**: Dong ho "09:48", tien "142Kb" (hoac data)
- **Do phan giai**: ~240x320px (man hinh J2ME doc)
- **Phong cach art**: Rat dep, mau sac tuoi, khong phai pixel tho ma la pixel art tinh te

---

## 1.5 SO SANH BAN GOC vs BAN BETA 2030

| Dac diem           | Ban goc J2ME          | Ban Beta 2030 (video)    |
|--------------------|-----------------------|--------------------------|
| Do phan giai       | ~240x320 (doc)        | ~240x160 (ngang, upscale)|
| Background         | Ve tay, rat chi tiet  | Don gian hon, co mua     |
| Cong trinh         | Nha co, cong lang     | Khong thay                |
| NPC                | Co (Truong lang)      | Khong thay                |
| Platform           | 1-2 tang              | 2-3 tang, phuc tap hon   |
| Mau sac            | Tuoi sang, sac net    | Toi hon, co hieu ung mua |
| Monster            | Ga Dien + thu cung    | Ga Dien (nhieu mau sac)  |

**Nhan xet**: Ban Beta 2030 tap trung vao GAMEPLAY (nhieu platform, nhieu quai) trong khi ban goc co ATMOSPHERE tot hon (background dep, NPC, cong trinh). Nen KET HOP ca 2 phong cach.

---

## 2. KIEN TRUC BAN DO (MAP ARCHITECTURE)

### 2.0 Dac diem dia hinh quan trong (Tu anh phan tich)

Qua 4 anh crop, dia hinh map KHONG CHI LA PHANG ma co cac dang:

```
DANG 1: Platform phang (co ban)
  ==================
  ||||||||||||||||||  <- co xanh
  ||||||||||||||||||  <- dat nau
  ||||||||||||||||||  <- da

DANG 2: Doc/Slope (cheo)
       ====
      ====||
     ====||||
    ====||||||  <- mat dat nghieng, nhan vat di len/xuong

DANG 3: Multi-level (nhieu tang)
  ========          ========
  ||||||||          ||||||||  <- tang tren

          ==================
          ||||||||||||||||||  <- tang duoi

DANG 4: Gap/Ho (khoang trong)
  ========        ========
  ||||||||        ||||||||  <- 2 nen tang cach nhau
           (hole)

DANG 5: Tuong doc (vach da ben canh)
  ||====
  ||||||
  ||||||  <- tuong thang dung ben trai hoac phai
  ||||||
```

Dieu nay co nghia he thong tile can ho tro:
- Tile co xanh co CANH TRAI, GIUA, PHAI (edge tiles)
- Tile doc (slope left, slope right)
- Tile goc (inner corner, outer corner)
- Tile trong (empty/sky) cho khoang ho

### 2.1 He thong Layer (Tang lop)
Ban do duoc xep tu nhieu layer chong len nhau:

```
Layer 4: UI/HUD          (thanh mau, ten map, nut dieu khien)
Layer 3: Overlay          (canh vat phia truoc nhan vat - la cay, mai nha...)
Layer 2: Actor            (nhan vat, quai, NPC, item roi)
Layer 1: Foreground Tiles (nen tang dat/co ma nhan vat dung len)
Layer 0: Background       (troi, nui, bien, thap - parallax scrolling)
```

### 2.2 Tile System (He thong gach)
Dua tren file `hoalu.json` hien co va video:

| Thuoc tinh       | Gia tri hien tai | Gia tri video tham khao |
|------------------|------------------|------------------------|
| Grid size        | 33 x 8           | ~33 x 8 tuong tu       |
| Tile size        | 32 x 32 px       | 32 x 32 px             |
| Map pixel size   | 1056 x 256 px    | ~1056 x 256 px         |
| Walkable rows    | Row 5, 6, 7      | Tuong tu (3 hang duoi) |
| Spawn points     | Col 3, 10, 11    | Nhieu vi tri hon       |

### 2.3 Cau truc Map JSON (Mau chuan)
```json
{
  "mapId": 1,
  "mapName": "Hoa Lu",
  "zones": [
    {
      "zoneId": 1,
      "label": "Hoa Lu 1",
      "width": 33,
      "height": 8,
      "tileSize": 32,
      "assets": {
        "backgroundId": 31000,
        "tilesetId": 31002,
        "overlayId": 31201
      },
      "layers": {
        "collision": "... byte array walkable/blocked ...",
        "ground": "... tile index array cho foreground ...",
        "decoration": "... tile index cho vat trang tri ..."
      },
      "spawns": {
        "player": { "x": 124, "y": 132 },
        "monsters": [
          { "id": "ga_dien", "x": 256, "y": 132, "respawnSec": 30 },
          { "id": "ga_dien", "x": 384, "y": 132, "respawnSec": 30 }
        ],
        "npc": [
          { "id": "npc_shop", "x": 64, "y": 132 }
        ]
      },
      "portals": [
        { "x": 1024, "y": 132, "targetZone": 2, "targetX": 32, "targetY": 132 }
      ]
    }
  ]
}
```

---

## 3. QUY TRINH THIET KE 1 BAN DO MOI

### Buoc 1: Chuan bi Asset (Tai nguyen hinh anh)

#### a) Background (Nen - 1056x256px hoac lon hon)
- Ve hoac tai hinh nen phong canh: troi, nui, bien, may
- Su dung AI image gen hoac pixel art editor (Aseprite, Piskel)
- Hien co file mau: `reference/L12SQ/server/assets/maps/hoalu/background.png`
- Background co the lon hon map de tao hieu ung parallax scrolling

#### b) Tileset (Bo gach - spritesheet)
- Tao 1 file PNG chua tat ca cac loai gach 32x32px
- Sap xep theo grid, moi o la 1 tile

**TILESET CHI TIET (dua tren phan tich anh thuc te):**

```
NHOM 1 - CO XANH (grass surface):
+----------+----------+----------+----------+----------+
| grass    | grass    | grass    | grass    | grass    |
| left-end | middle   | right-end| slope-L  | slope-R  |
+----------+----------+----------+----------+----------+

NHOM 2 - DAT NAU (dirt/soil):
+----------+----------+----------+----------+----------+
| dirt     | dirt     | dirt     | dirt     | dirt     |
| left-wal | fill     | right-wal| corner-L | corner-R |
+----------+----------+----------+----------+----------+

NHOM 3 - DA (rock/stone):
+----------+----------+----------+----------+
| rock     | rock     | rock     | rock     |
| surface  | fill     | bottom   | moss     |
+----------+----------+----------+----------+

NHOM 4 - TRANG TRI (decoration):
+----------+----------+----------+----------+
| bush     | flower   | mushroom | puddle   |
| small    | grass    | small    | water    |
+----------+----------+----------+----------+

NHOM 5 - CONG TRINH (structures - tu anh goc J2ME):
+----------+----------+----------+----------+
| house    | house    | gate     | gate     |
| wall     | roof     | left     | right    |
+----------+----------+----------+----------+

NHOM 6 - DAC BIET:
+----------+----------+----------+----------+
| empty    | portal   | spawn    | ladder   |
| (sky)    | glow     | marker   | wood     |
+----------+----------+----------+----------+
```

- Hien co tileset mau: `reference/L12SQ/server/assets/maps/hoalu/tileset.png`
- Luu y: Moi tile 32x32px, tileset PNG nen rong 8-10 tile (256-320px) va cao tuy so luong

#### c) Overlay (Tang phu - cung kich thuoc voi map)
- Cac vat the phia truoc nhan vat (la cay, mai nha rum, hieu ung...)
- Co the la PNG trong suot (transparent)

#### d) Character & Monster Sprites
- Sprite sheet cho moi nhan vat/quai (idle, walk, attack)
- Kich thuoc: ~32x48px moi frame (chibi style)

### Buoc 2: Thiet ke Layout (Bo cuc ban do)

#### Cong cu khuyen dung
1. **Tiled Map Editor** (mien phi, https://www.mapeditor.org/)
   - Tao file .tmx -> export JSON
   - Ve collision layer, ground layer, decoration layer
   - Dat spawn point cho player, monster, NPC

2. **Aseprite / Piskel** (pixel art)
   - Ve tileset va sprite sheets

3. **Thu cong bang JSON**
   - Viet tay file JSON theo mau o Muc 2.3

#### Nguyen tac thiet ke layout
- **Walkable area**: Thuong la 2-3 hang cuoi cua grid (row 5-7 trong grid 8 hang)
- **Platform**: Co the tao cac nen tang nho o giua khong trung
- **Monster placement**: Dat quai tren cac nen tang walkable, cach nhau it nhat 2-3 tiles
- **Portal/Cua**: Dat o 2 dau map de chuyen zone

#### Vi du layout Hoa Lu (nhin tu tren xuong):
```
Row 0: [sky] [sky] [sky] [sky] [sky] [sky] ...
Row 1: [sky] [sky] [sky] [sky] [sky] [sky] ...
Row 2: [sky] [sky] [tree] [sky] [sky] [tree] ...
Row 3: [sky] [sky] [tree] [sky] [sky] [tree] ...
Row 4: [sky] [sky] [sky] [sky] [sky] [sky] ...
Row 5: [grass][grass][grass][grass][grass][grass] ...  <- walkable
Row 6: [dirt] [dirt] [dirt] [dirt] [dirt] [dirt] ...  <- walkable
Row 7: [rock] [rock] [rock] [rock] [rock] [rock] ...  <- walkable
```

### Buoc 3: Tich hop vao Server (.NET 9)

#### a) Them map data vao MapDataStore.cs
Duong dan: `server/Twelve.Core/Maps/MapDataStore.cs`

Moi map moi can dang ky:
- MapRoom voi Id, Label, Width, Height, TileSize
- LogicLayer (byte array: 0=blocked, 1=walkable)
- TilesetId, BackgroundId de client biet tai asset nao

#### b) Cap nhat MapHandler.cs
Duong dan: `server/Twelve.Application/Handlers/MapHandler.cs`

Dam bao Command 11 (MAP_INFO) gui dung data cho map moi, bao gom:
- Ten map, kich thuoc
- Logic layer (walkable/collision)
- Asset IDs

#### c) Them spawn data
Dinh nghia danh sach monster va NPC cho moi zone trong map moi.

### Buoc 4: Tich hop vao Client (React Native + Skia)

#### a) Nang cap MapRenderer.tsx
Duong dan: `client/src/engine/MapRenderer.tsx`

Hien tai dang ve hinh chu nhat mau. Can nang cap:

```typescript
// Thay vi ve rectangle, ve tile tu spritesheet:
// 1. Load tileset image
const tilesetImage = useImage(require('../assets/maps/hoalu/tileset.png'));

// 2. Tinh toa do tile trong spritesheet
function getTileRect(tileIndex: number, tilesPerRow: number): SkRect {
  const col = tileIndex % tilesPerRow;
  const row = Math.floor(tileIndex / tilesPerRow);
  return { x: col * 32, y: row * 32, width: 32, height: 32 };
}

// 3. Ve tung tile len canvas
for (let row = 0; row < mapHeight; row++) {
  for (let col = 0; col < mapWidth; col++) {
    const tileIndex = groundLayer[row * mapWidth + col];
    const srcRect = getTileRect(tileIndex, tilesPerRow);
    const dstRect = { x: col * 32, y: row * 32, width: 32, height: 32 };
    canvas.drawImageRect(tilesetImage, srcRect, dstRect, paint);
  }
}
```

#### b) Them Camera/Viewport System
```typescript
// Camera theo nhan vat
const cameraX = playerX - screenWidth / 2;
const cameraY = playerY - screenHeight / 2;

// Clamp camera trong gioi han map
const clampedX = Math.max(0, Math.min(cameraX, mapPixelWidth - screenWidth));
const clampedY = Math.max(0, Math.min(cameraY, mapPixelHeight - screenHeight));

// Ap dung offset khi ve
canvas.translate(-clampedX, -clampedY);
```

#### c) Them Parallax Background
```typescript
// Background di chuyen cham hon foreground
const parallaxFactor = 0.5;
const bgOffsetX = clampedX * parallaxFactor;
canvas.drawImage(backgroundImage, -bgOffsetX, 0);
```

---

## 4. DANH SACH MAP THEO PLAN DU AN

Dua tren `MapData.ts`, du an co 17 map can thiet ke:

| # | Map Name      | Type       | Level | Mo ta                      | Do uu tien |
|---|---------------|------------|-------|----------------------------|------------|
| 1 | Hoa Lu        | historical | 1     | Map dau tien, huong dan    | CAO        |
| 2 | Luyen Nguc    | arena      | 1     | Noi luyen tap ky nang      | CAO        |
| 3 | Binh Kieu     | historical | 5     | Map co ban                 | TRUNG BINH |
| 4 | Song Lo       | historical | 10    | Map nuoc                   | TRUNG BINH |
| 5 | Dai La Thanh  | historical | 15    | Thanh pho lon              | THAP       |
| 6 | Bo Hai        | historical | 20    | Vung bien                  | THAP       |
| 7 | Chiem Thanh   | historical | 25    | Map rung ram               | THAP       |
| 8 | Phong Chau    | historical | 30    | Vung nui                   | THAP       |
| 9 | Hoa Lu Thuong | historical | 35    | Hoa Lu nang cao            | THAP       |
| 10| Co Loa        | historical | 40    | Thanh co                   | THAP       |
| 11| Thang Long    | historical | 50    | Kinh do                    | THAP       |
| 12| Dau Truong    | arena      | 10    | PvP arena                  | TRUNG BINH |
| 13| Giac Dau      | arena      | 20    | PvP co cap                 | THAP       |
| 14| Hang Dong     | special    | 15    | Dungeon                    | TRUNG BINH |
| 15| Rung Cam      | special    | 25    | Event map                  | THAP       |
| 16| Nui Than      | special    | 35    | Boss raid                  | THAP       |
| 17| Thien Cung    | special    | 50    | End-game                   | THAP       |

---

## 5. WORKFLOW TOM TAT

```
1. CHUAN BI ASSET
   |-- Ve/Tai background.png (phong canh nen)
   |-- Ve/Tai tileset.png (bo gach 32x32)
   |-- Ve/Tai overlay.png (vat the phia truoc)
   |-- Ve sprite sheets (nhan vat, quai)
   |
2. THIET KE LAYOUT
   |-- Dung Tiled Editor hoac viet JSON thu cong
   |-- Dinh nghia collision layer (walkable/blocked)
   |-- Dat spawn point (player, monster, NPC, portal)
   |
3. TICH HOP SERVER
   |-- Them MapRoom vao MapDataStore.cs
   |-- Cap nhat MapHandler.cs (CMD 11)
   |-- Them monster/NPC spawn logic
   |
4. TICH HOP CLIENT
   |-- Copy assets vao client/assets/maps/
   |-- Nang cap MapRenderer.tsx (ve tile thuc te)
   |-- Implement camera viewport + parallax
   |-- Test hien thi tren thiet bi
   |
5. TINH CHINH
   |-- Test di chuyen nhan vat
   |-- Test spawn quai va tuong tac
   |-- Toi uu hieu nang rendering
```

---

## 6. TRANG THAI HIEN TAI CUA DU AN (Tinh den 07/04/2026)

### Da co (DONE):
- Server .NET 9 co MapDataStore voi Hoa Lu (10x8 grid, 3 walkable rows)
- MapHandler xu ly CMD 11 (MAP_INFO), CMD 29 (MAP_JOIN), CMD 43 (SCENE_ACTORS)
- Client co MapRenderer.tsx (Skia) nhung dang ve hinh chu nhat mau placeholder
- Background.png cua Hoa Lu da co (phong canh dep pixel art)
- Tileset.png co nhung chua tich hop vao renderer
- File hoalu.json dinh nghia day du map spec

### Can lam tiep (TODO):
1. **Tich hop tileset thuc te** vao MapRenderer (thay vi rectangle placeholder)
2. **Implement camera viewport** (scroll theo nhan vat)
3. **Render background** voi parallax
4. **Render overlay** layer
5. **Ve/Nhap sprite** cho nhan vat va quai vat
6. **Them nhieu zone** cho map Hoa Lu (Hoa Lu 1, 2, 3 nhu trong video)
7. **Implement portal system** chuyen giua cac zone
8. **Thiet ke cac map con lai** (Binh Kieu, Song Lo, ...)

---

## 7. CONG CU VA TAI NGUYEN HUU ICH

### Cong cu ve Pixel Art
- **Aseprite** (tra phi ~$20) - Tot nhat cho pixel art & animation
- **Piskel** (mien phi, web) - https://www.piskelapp.com/
- **LibreSprite** (mien phi) - Fork cua Aseprite

### Cong cu thiet ke Map
- **Tiled Map Editor** (mien phi) - https://www.mapeditor.org/
- **LDtk** (mien phi) - https://ldtk.io/ (Level Designer Toolkit)

### Tai nguyen tham khao
- OpenGameArt.org - Asset mien phi
- itch.io/game-assets - Tileset va sprite mien phi/tra phi

### AI Image Generation (cho background)
- Dung prompt: "2D side-scrolling game background, pixel art style, Vietnamese ancient landscape, pagoda, mountains, blue sky, 1056x256 pixels"

---

---

## 8. ANH THAM KHAO DA PHAN TICH

| File                                    | Noi dung                                          |
|-----------------------------------------|---------------------------------------------------|
| `raw/images/7687236765567_f354_gamecrop.png` | Map Hoa Lu 3 - goc nhin xa, dia hinh doc       |
| `raw/images/7687236765567_f486_gamecrop.png` | Platform da tang, nhan vat chien dau voi quai   |
| `raw/images/7687236765567_f619_gamecrop.png` | Nhin gan, gap giua 2 platform, slope wall       |
| `raw/images/bx2n6nagkdfvkvha3.jpg`          | **BAN GOC J2ME** - cong trinh, NPC, art dep      |
| `raw/videos/7687236765567.mp4`               | Video gameplay Hoa Lu 3 (Beta 2030)             |
| `raw/videos/7687237089061.mp4`               | Video gameplay Hoa Lu 3 (goc khac)              |

Tat ca file nam trong: `reference/raw/`

---

> **Ghi chu**: File nay la tai lieu song (living document). Cap nhat khi co thay doi trong thiet ke hoac tien do du an.
