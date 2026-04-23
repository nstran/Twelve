import { useCallback, useEffect, useMemo, useState, type Dispatch, type SetStateAction } from 'react';
import type { MenuItem } from '../../../components/controls/PopupMenu/PopupMenu';
import {
  getFirstBattleSkillServerPacketReadyFamily,
  isBattleSkillServerPacketReady,
  type BattleCell,
  type BattlePhase,
  type BattleResult,
  type BattleTurn,
  type MoveSpec,
  type SkillFamilyCode,
} from '../core';

interface UseBattleMenuControlsArgs {
  battleElement: number;
  onFlee: () => void;
  phase: BattlePhase;
  result: BattleResult | null;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  showBonusBanner: (msg: string) => void;
  turn: BattleTurn;
}

export const useBattleMenuControls = ({
  battleElement,
  onFlee,
  phase,
  result,
  setHintCell,
  setHintMove,
  showBonusBanner,
  turn,
}: UseBattleMenuControlsArgs) => {
  const [menuVisible, setMenuVisible] = useState(false);
  const [menuSelectedIndex, setMenuSelectedIndex] = useState(0);
  const [skillPanelVisible, setSkillPanelVisible] = useState(false);
  const [selectedSkillFamily, setSelectedSkillFamily] = useState<SkillFamilyCode | null>(null);

  const handleSkill = useCallback(() => {
    if (phase !== 'idle' || turn !== 'player' || result !== null) return;
    setHintCell(null);
    setHintMove(null);
    setSelectedSkillFamily(prev => {
      if (prev && isBattleSkillServerPacketReady(prev)) {
        return prev;
      }
      return getFirstBattleSkillServerPacketReadyFamily(battleElement)
        ?? (battleElement === 0 ? 1000 : battleElement === 1 ? 2000 : 4000);
    });
    setSkillPanelVisible(true);
    setMenuVisible(false);
  }, [battleElement, phase, result, setHintCell, setHintMove, turn]);

  const battleMenuItems = useMemo<MenuItem[]>(() => [
    {
      id: 'battle-skill',
      label: 'Tuyệt Chiêu',
      onPress: handleSkill,
    },
    {
      id: 'battle-bag',
      label: 'Túi đồ',
      onPress: () => showBonusBanner('Túi đồ chưa phục dựng'),
    },
    {
      id: 'battle-surrender',
      label: 'Đầu hàng',
      onPress: onFlee,
    },
  ], [handleSkill, onFlee, showBonusBanner]);

  useEffect(() => {
    if (result !== null && menuVisible) {
      setMenuVisible(false);
    }
  }, [menuVisible, result]);

  useEffect(() => {
    if (result !== null && skillPanelVisible) {
      setSkillPanelVisible(false);
    }
  }, [result, skillPanelVisible]);

  const handleBattleMenuConfirm = useCallback(() => {
    const item = battleMenuItems[menuSelectedIndex];
    if (!item) return;
    item.onPress?.();
    setMenuVisible(false);
  }, [battleMenuItems, menuSelectedIndex]);

  const handleLeftSoftkey = useCallback(() => {
    if (result !== null) return;
    if (menuVisible) {
      handleBattleMenuConfirm();
      return;
    }
    setMenuSelectedIndex(0);
    setMenuVisible(true);
  }, [handleBattleMenuConfirm, menuVisible, result]);

  const handleRightSoftkey = useCallback(() => {
    if (menuVisible) {
      setMenuVisible(false);
    }
  }, [menuVisible]);

  return {
    battleMenuItems,
    handleLeftSoftkey,
    handleRightSoftkey,
    menuSelectedIndex,
    menuVisible,
    selectedSkillFamily,
    setMenuSelectedIndex,
    setMenuVisible,
    setSelectedSkillFamily,
    setSkillPanelVisible,
    skillPanelVisible,
  };
};
