export type BattleElementIndex = 0 | 1 | 2;

export type SkillFamilyCode =
  | 1000 | 1001 | 1002 | 1003 | 1004 | 1005 | 1006 | 1007 | 1008
  | 2000 | 2001 | 2002 | 2003 | 2004 | 2005 | 2006 | 2007 | 2008
  | 4000 | 4001 | 4002 | 4003 | 4004 | 4005 | 4006 | 4007 | 4008;

export type SkillPattern =
  | 'projectile_pair'
  | 'tile_burst'
  | 'board_helper'
  | 'element_helper'
  | 'flare_gate'
  | 'lob_impact'
  | 'pillar'
  | 'descending_column'
  | 'chain_path'
  | 'beam'
  | 'side_wave'
  | 'dual_layer'
  | 'particle_burst'
  | 'anchor_burst'
  | 'linear_shot'
  | 'static_aura';

export type SkillBoardMode =
  | 'tiles'
  | 'marks'
  | 'staged_tiles'
  | 'columns'
  | 'helper'
  | 'none';

export type SkillRuntimeAssetMode = 'dedicated' | 'icon_only' | 'reuse';

export interface BattleSkillDefinition {
  familyCode: SkillFamilyCode;
  elementIndex: BattleElementIndex;
  elementLabel: string;
  javaClass: string;
  runtimeAssetFamilyCode: SkillFamilyCode;
  runtimeAssetMode: SkillRuntimeAssetMode;
  title: string;
  summary: string;
  serverNote: string;
  pattern: SkillPattern;
  boardMode: SkillBoardMode;
  icon: any;
  runtimeFrames: any[];
  hitShakePx: number;
  impactDelayMs: number;
  totalMs: number;
}

const SKILL_RUNTIME_ASSET_META: Record<
  SkillFamilyCode,
  {
    runtimeAssetFamilyCode: SkillFamilyCode;
    runtimeAssetMode: SkillRuntimeAssetMode;
  }
> = {
  1000: { runtimeAssetFamilyCode: 1000, runtimeAssetMode: 'dedicated' },
  1001: { runtimeAssetFamilyCode: 1001, runtimeAssetMode: 'dedicated' },
  1002: { runtimeAssetFamilyCode: 1002, runtimeAssetMode: 'icon_only' },
  1003: { runtimeAssetFamilyCode: 1003, runtimeAssetMode: 'dedicated' },
  1004: { runtimeAssetFamilyCode: 1004, runtimeAssetMode: 'dedicated' },
  1005: { runtimeAssetFamilyCode: 1005, runtimeAssetMode: 'dedicated' },
  1006: { runtimeAssetFamilyCode: 1006, runtimeAssetMode: 'dedicated' },
  1007: { runtimeAssetFamilyCode: 1007, runtimeAssetMode: 'dedicated' },
  1008: { runtimeAssetFamilyCode: 1008, runtimeAssetMode: 'dedicated' },
  2000: { runtimeAssetFamilyCode: 2000, runtimeAssetMode: 'dedicated' },
  2001: { runtimeAssetFamilyCode: 2001, runtimeAssetMode: 'icon_only' },
  2002: { runtimeAssetFamilyCode: 2002, runtimeAssetMode: 'icon_only' },
  2003: { runtimeAssetFamilyCode: 2003, runtimeAssetMode: 'dedicated' },
  2004: { runtimeAssetFamilyCode: 2004, runtimeAssetMode: 'dedicated' },
  2005: { runtimeAssetFamilyCode: 2005, runtimeAssetMode: 'dedicated' },
  2006: { runtimeAssetFamilyCode: 2006, runtimeAssetMode: 'dedicated' },
  2007: { runtimeAssetFamilyCode: 2007, runtimeAssetMode: 'dedicated' },
  2008: { runtimeAssetFamilyCode: 2008, runtimeAssetMode: 'dedicated' },
  4000: { runtimeAssetFamilyCode: 4000, runtimeAssetMode: 'dedicated' },
  4001: { runtimeAssetFamilyCode: 4001, runtimeAssetMode: 'dedicated' },
  4002: { runtimeAssetFamilyCode: 4002, runtimeAssetMode: 'dedicated' },
  4003: { runtimeAssetFamilyCode: 4003, runtimeAssetMode: 'dedicated' },
  4004: { runtimeAssetFamilyCode: 4004, runtimeAssetMode: 'dedicated' },
  4005: { runtimeAssetFamilyCode: 4005, runtimeAssetMode: 'dedicated' },
  4006: { runtimeAssetFamilyCode: 4006, runtimeAssetMode: 'dedicated' },
  4007: { runtimeAssetFamilyCode: 4007, runtimeAssetMode: 'dedicated' },
  4008: { runtimeAssetFamilyCode: 4000, runtimeAssetMode: 'reuse' },
};

const frames = (...assets: any[]) => assets;

const family = (
  familyCode: SkillFamilyCode,
  elementIndex: BattleElementIndex,
  elementLabel: string,
  javaClass: string,
  summary: string,
  serverNote: string,
  pattern: SkillPattern,
  boardMode: SkillBoardMode,
  hitShakePx: number,
  impactDelayMs: number,
  totalMs: number,
): BattleSkillDefinition => ({
  familyCode,
  elementIndex,
  elementLabel,
  javaClass,
  ...SKILL_RUNTIME_ASSET_META[familyCode],
  title: `${familyCode} / ${javaClass}`,
  summary,
  serverNote,
  pattern,
  boardMode,
  icon: SKILL_ICONS[familyCode],
  runtimeFrames: SKILL_RUNTIME_FRAMES[familyCode],
  hitShakePx,
  impactDelayMs,
  totalMs,
});

export const SKILL_ICONS: Record<SkillFamilyCode, any> = {
  1000: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1000_is/skill_icon/1000000.png'),
  1001: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1001_it/skill_icon/1001000.png'),
  1002: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1002_no_dedicated_class/skill_icon/1002000.png'),
  1003: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1003_io_elementVariant-0/skill_icon/1003000.png'),
  1004: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1004_iu/skill_icon/1004000.png'),
  1005: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1005_iv/skill_icon/1005000.png'),
  1006: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1006_iw/skill_icon/1006000.png'),
  1007: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1007_ix/skill_icon/1007000.png'),
  1008: require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1008_iy/skill_icon/1008000.png'),
  2000: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2000_jg/skill_icon/2000000.png'),
  2001: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2001_no_dedicated_class/skill_icon/2001000.png'),
  2002: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2002_no_dedicated_class/skill_icon/2002000.png'),
  2003: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2003_jh/skill_icon/2003000.png'),
  2004: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2004_ji/skill_icon/2004000.png'),
  2005: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2005_io_elementVariant-1/skill_icon/2005000.png'),
  2006: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2006_jj/skill_icon/2006000.png'),
  2007: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2007_jk/skill_icon/2007000.png'),
  2008: require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2008_jl/skill_icon/2008000.png'),
  4000: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4000_iz/skill_icon/4000000.png'),
  4001: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4001_ja/skill_icon/4001000.png'),
  4002: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4002_jb/skill_icon/4002000.png'),
  4003: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4003_jc/skill_icon/4003000.png'),
  4004: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4004_io_elementVariant-2/skill_icon/4004000.png'),
  4005: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4005_jd/skill_icon/4005000.png'),
  4006: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4006_je/skill_icon/4006000.png'),
  4007: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4007_jf/skill_icon/4007000.png'),
  4008: require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4008_no_dedicated_class/skill_icon/4008000.png'),
};

export const SKILL_RUNTIME_FRAMES: Record<SkillFamilyCode, any[]> = {
  1000: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1000_is/runtime_png/1000001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1000_is/runtime_png/1000002.png'),
  ),
  1001: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1001_it/runtime_png/1001001.png'),
  ),
  1002: [],
  1003: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1003_io_elementVariant-0/runtime_png/1003001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1003_io_elementVariant-0/runtime_png/1003002.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1003_io_elementVariant-0/runtime_png/1003003.png'),
  ),
  1004: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1004_iu/runtime_png/1004001.png'),
  ),
  1005: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1005_iv/runtime_png/1005001.png'),
  ),
  1006: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1006_iw/runtime_png/1006001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1006_iw/runtime_png/1006002.png'),
  ),
  1007: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1007_ix/runtime_png/1007001.png'),
  ),
  1008: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1008_iy/runtime_png/1008001.png'),
  ),
  2000: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2000_jg/runtime_png/2000001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2000_jg/runtime_png/2000002.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2000_jg/runtime_png/2000003.png'),
  ),
  2001: [],
  2002: [],
  2003: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2003_jh/runtime_png/2003001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2003_jh/runtime_png/2003002.png'),
  ),
  2004: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2004_ji/runtime_png/2004001.png'),
  ),
  2005: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2005_io_elementVariant-1/runtime_png/2005001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2005_io_elementVariant-1/runtime_png/2005002.png'),
  ),
  2006: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2006_jj/runtime_png/2006001.png'),
  ),
  2007: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2007_jk/runtime_png/2007001.png'),
  ),
  2008: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_200x_loi_thunder_likely/family_2008_jl/runtime_png/2008001.png'),
  ),
  4000: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4000_iz/runtime_png/4000001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4000_iz/runtime_png/4000002.png'),
  ),
  4001: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4001_ja/runtime_png/4001001.png'),
  ),
  4002: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4002_jb/runtime_png/4002001.png'),
  ),
  4003: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4003_jc/runtime_png/4003001.png'),
  ),
  4004: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4004_io_elementVariant-2/runtime_png/4004001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4004_io_elementVariant-2/runtime_png/4004002.png'),
  ),
  4005: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4005_jd/runtime_png/4005001.png'),
  ),
  4006: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4006_je/runtime_png/4006001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4006_je/runtime_png/4006002.png'),
  ),
  4007: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4007_jf/runtime_png/4007001.png'),
  ),
  4008: frames(
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4000_iz/runtime_png/4000001.png'),
    require('../../../../assets/skill/02_elemental_runtime_families/group_400x_thuy_water_likely/family_4000_iz/runtime_png/4000002.png'),
  ),
};

export const SKILL_FAMILIES_BY_ELEMENT: Record<BattleElementIndex, SkillFamilyCode[]> = {
  0: [1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008],
  1: [2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008],
  2: [4000, 4001, 4002, 4003, 4004, 4005, 4006, 4007, 4008],
};

export const BATTLE_SKILLS: Record<SkillFamilyCode, BattleSkillDefinition> = {
  1000: family(1000, 0, 'Hỏa', 'is', 'Projectile 2-frame. `mt` bắn một quả vào tâm victim rồi lặp lên các cell target; `mq` vẫn clear từng ô target riêng.', 'Target cells và damage phải lấy từ server packet; client chỉ được render theo mảng đó.', 'projectile_pair', 'tiles', 16, 520, 1320),
  1001: family(1001, 0, 'Hỏa', 'it', 'Burst đặt thẳng lên từng cell target. `mq` mark cell qua `p[]`, không dùng cùng nhánh clear thường.', 'Đây là skill mark-tile; đừng render như projectile chung.', 'tile_burst', 'marks', 8, 420, 1180),
  1002: family(1002, 0, 'Hỏa', 'helper', 'Không có runtime sheet riêng; Java chỉ gọi helper object trên board/actor.', 'Tên chính thức và mechanical meaning vẫn thuộc server catalog.', 'board_helper', 'helper', 6, 260, 720),
  1003: family(1003, 0, 'Hỏa', 'io(0)', 'Shared elemental helper variant Hỏa. `mt.d(side)` mở pipeline `io`, không đi nhánh projectile thường.', 'Board result và damage thật vẫn phải theo server.', 'element_helper', 'none', 10, 460, 1260),
  1004: family(1004, 0, 'Hỏa', 'iu', 'Gate/flare xuất hiện tại victim, rồi gọi hit helper `c(side, 26, 16)`.', 'Không có loop phá ô chuẩn ở `mq`; trọng tâm là burst vào actor.', 'flare_gate', 'none', 14, 520, 1380),
  1005: family(1005, 0, 'Hỏa', 'iv', 'Projectile cong từ caster sang victim rồi nổ, sau đó `mt` gọi hit helper trung bình.', 'Board-side không có clear-loop tiêu chuẩn trong `mq`.', 'lob_impact', 'none', 12, 540, 1420),
  1006: family(1006, 0, 'Hỏa', 'iw', 'Biến thể projectile 2-frame; `mq` clear từng ô target, `mt` dùng hit helper nặng hơn 1000.', 'Render board + victim cùng lúc, không chỉ một đầu.', 'projectile_pair', 'tiles', 18, 560, 1380),
  1007: family(1007, 0, 'Hỏa', 'ix', 'Pillar neo ở victim rồi lặp lên danh sách target cells; `mq` cũng clear trực tiếp từng cell.', 'Danh sách cột/ô vẫn là server truth.', 'pillar', 'tiles', 14, 620, 1480),
  1008: family(1008, 0, 'Hỏa', 'iy', 'Cột rơi theo stage. `mq` chia group target và giảm delay từng đợt.', 'Đây là staged impact, không phải toàn bộ tile nổ cùng lúc.', 'descending_column', 'staged_tiles', 8, 640, 1500),
  2000: family(2000, 1, 'Lôi', 'jg', 'Path nối nhiều điểm target rồi chốt vào tâm victim. `mq` vẫn clear từng ô target ngoài board.', 'Chain path phải bám các điểm server trả về.', 'chain_path', 'tiles', 14, 520, 1420),
  2001: family(2001, 1, 'Lôi', 'helper', 'Không có class runtime riêng; `mq` gọi helper board-object và `mt` gọi helper actor.', 'Đây không phải projectile family đầy đủ.', 'board_helper', 'helper', 6, 260, 720),
  2002: family(2002, 1, 'Lôi', 'helper', 'Tương tự 2001 nhưng dùng helper kind khác ở actor/board.', 'Client không được đoán tên thật từ icon.', 'board_helper', 'helper', 6, 260, 720),
  2003: family(2003, 1, 'Lôi', 'jh', 'Projectile 2-frame kiểu Lôi; `mq` clear từng ô target và `mt` dùng hit helper nặng.', 'Đường bay và burst actor phải đi cùng nhau.', 'projectile_pair', 'tiles', 18, 520, 1280),
  2004: family(2004, 1, 'Lôi', 'ji', 'Beam từ mép caster sang điểm trước victim, sau đó gọi hit helper trung bình.', 'Trong `mq` đây là helper/status branch, không phải clear board thường.', 'beam', 'helper', 12, 420, 1120),
  2005: family(2005, 1, 'Lôi', 'io(1)', 'Shared elemental helper variant Lôi, cùng pipeline với 1003/4004.', 'Board mutation và damage vẫn thuộc packet battle.', 'element_helper', 'none', 10, 460, 1260),
  2006: family(2006, 1, 'Lôi', 'jj', 'Projectile quét cột. `mq` sort cột rồi sweep toàn cột theo chiều phụ thuộc side.', 'Đây là family phải có board sweep rõ ràng.', 'side_wave', 'columns', 10, 500, 1300),
  2007: family(2007, 1, 'Lôi', 'jk', 'Layer kép quét dọc các điểm target; `mq` vẫn clear ô trực tiếp.', 'Hit helper dùng thời lượng động theo path.', 'dual_layer', 'tiles', 14, 560, 1380),
  2008: family(2008, 1, 'Lôi', 'jl', 'Burst phân mảnh từ các cell target quanh victim rồi kết thúc bằng direct hit mạnh.', 'Danh sách cell nguồn là input runtime, không phải random client.', 'particle_burst', 'tiles', 16, 520, 1460),
  4000: family(4000, 2, 'Thủy', 'iz', 'Projectile 2-frame kiểu Thủy. `mq` clear từng ô target và `mt` đánh mạnh vào victim.', '4008 reuse cùng renderer này.', 'projectile_pair', 'tiles', 16, 520, 1280),
  4001: family(4001, 2, 'Thủy', 'ja', 'Anchor burst đứng tại victim; actor-side chỉ đẩy timer helper ngắn.', 'Không có clear-loop chuẩn ở `mq`.', 'anchor_burst', 'none', 8, 360, 980),
  4002: family(4002, 2, 'Thủy', 'jb', 'Linear shot sang victim rồi gọi helper actor branch đặc biệt.', 'Trong `mq` đây là helper/status family.', 'linear_shot', 'helper', 8, 420, 1060),
  4003: family(4003, 2, 'Thủy', 'jc', 'Aura neo thẳng vào victim rồi mới follow-up hit helper.', 'Không có clear-loop chuẩn trên board.', 'static_aura', 'none', 10, 340, 920),
  4004: family(4004, 2, 'Thủy', 'io(2)', 'Shared elemental helper variant Thủy.', 'Bám pipeline `io`, không tự ý suy cơ chế từ icon.', 'element_helper', 'none', 10, 460, 1260),
  4005: family(4005, 2, 'Thủy', 'jd', 'Projectile cong variant Thủy rồi nổ vào victim với burst trung bình-nặng.', 'Board-side không có clear-loop chuẩn trong `mq`.', 'lob_impact', 'none', 14, 540, 1420),
  4006: family(4006, 2, 'Thủy', 'je', 'Projectile 2-frame variant Thủy khác asset; `mq` clear từng ô target.', 'Cần render vừa tile hits vừa burst actor.', 'projectile_pair', 'tiles', 12, 520, 1280),
  4007: family(4007, 2, 'Thủy', 'jf', 'Pillar variant Thủy trên các cell target, kèm dynamic actor hit helper.', 'Danh sách target cell là server truth.', 'pillar', 'tiles', 14, 620, 1480),
  4008: family(4008, 2, 'Thủy', 'iz(reuse)', 'Không có class riêng; Java route lại sang `iz` và dùng asset 4000.', 'Client phải reuse runtime family 4000 thay vì dựng family mới.', 'projectile_pair', 'tiles', 16, 540, 1340),
};

const SERVER_PACKET_READY_SKILL_FAMILIES = new Set<SkillFamilyCode>([
  1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008,
  2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008,
  4000, 4001, 4002, 4003, 4004, 4005, 4006, 4007, 4008,
]);

export const getBattleElement = (elementIndex?: number): BattleElementIndex =>
  elementIndex === 1 ? 1 : elementIndex === 2 ? 2 : 0;

export const getSkillFamiliesForElement = (elementIndex?: number): BattleSkillDefinition[] =>
  SKILL_FAMILIES_BY_ELEMENT[getBattleElement(elementIndex)].map(code => BATTLE_SKILLS[code]);

export const isBattleSkillServerPacketReady = (familyCode: SkillFamilyCode): boolean =>
  SERVER_PACKET_READY_SKILL_FAMILIES.has(familyCode);

export const getFirstBattleSkillServerPacketReadyFamily = (
  elementIndex?: number,
): SkillFamilyCode | null =>
  SKILL_FAMILIES_BY_ELEMENT[getBattleElement(elementIndex)]
    .find(isBattleSkillServerPacketReady) ?? null;

export const hasDedicatedSkillRuntimeFrames = (familyCode: SkillFamilyCode): boolean =>
  BATTLE_SKILLS[familyCode].runtimeAssetMode === 'dedicated';

export const reusesSkillRuntimeFrames = (familyCode: SkillFamilyCode): boolean =>
  BATTLE_SKILLS[familyCode].runtimeAssetMode === 'reuse';

export const doesSkillHitActor = (familyCode: SkillFamilyCode): boolean => {
  switch (familyCode) {
    case 1000:
    case 1004:
    case 1005:
    case 1006:
    case 1007:
    case 1008:
    case 2000:
    case 2003:
    case 2006:
    case 2007:
    case 4000:
    case 4003:
    case 4005:
    case 4006:
    case 4007:
    case 4008:
      return true;
    default:
      return false;
  }
};
