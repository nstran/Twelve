import React from 'react';
import { SideScrollMapScreen } from '../shared/SideScrollMapScreen';
import type { CharacterStatKey } from '../core';
import type { CharacterAppearance, CharacterShopResponse } from '../../character/shared';
import type {
  BattleResultRewardResponse,
  MonsterBattleBootstrapResponse,
  ResolveMonsterBattleBootstrap,
  ResolvePvpBattleBootstrap,
  ResolvePvpChallengeApi,
  ResolvePvpOpponents,
} from '../../battle';
import type { MonsterType } from '../../../engine/MonsterSprite';
import type { ResolveMapMonsterRoster } from '../core';

interface HoaLuMapScreenProps {
  mapId: string;
  roomId: number;
  roomLabel?: string;
  appearance: CharacterAppearance;
  defeatBlinkToken?: number;
  onBack: () => void;
  onLogout: () => void;
  onBattle?: (
    monsterType: MonsterType,
    initialTurn: 'player' | 'monster',
    monsterBootstrap: MonsterBattleBootstrapResponse,
  ) => void;
  pendingBattleResult?: BattleResultRewardResponse | null;
  onConsumeBattleResult?: () => void;
  resolveMonsterRoster?: ResolveMapMonsterRoster;
  resolveMonsterBootstrap?: ResolveMonsterBattleBootstrap;
  resolvePvpOpponents?: ResolvePvpOpponents;
  resolvePvpBootstrap?: ResolvePvpBattleBootstrap;
  resolvePvpChallengeApi?: ResolvePvpChallengeApi;
  onAllocateStat?: (stat: CharacterStatKey) => Promise<string | null>;
  onAllocateSkill?: (familyCode: number) => Promise<string | null>;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
  onUpgradeEquipment?: (equipKey: string, materialItemIds: number[]) => Promise<string | null>;
  shop?: CharacterShopResponse | null;
  onLoadShop?: () => Promise<CharacterShopResponse | null>;
  onBuyShopOffer?: (offerKey: string) => Promise<string | null>;
}

export const HoaLuMapScreen: React.FC<HoaLuMapScreenProps> = (props) => (
  <SideScrollMapScreen {...props} />
);
