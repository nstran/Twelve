import { useCallback, useEffect, useState } from 'react';
import type { MonsterType } from '../../../../engine/MonsterSprite';
import type { CharacterAppearance } from '../../../character/shared';
import type {
  MonsterBattleBootstrapResponse,
  PvpChallengeTicket,
  PvpOpponentEntry,
  ResolvePvpChallengeApi,
  ResolvePvpOpponents,
} from '../../../battle';
import type { MapCharacterDialogKind } from '../../core';
import type { PvpDialogMode, PvpDialogStatus, PvpStartOptions } from '../components/PvpDialog';
import type { PvpIncomingPromptState } from '../components/PvpIncomingPrompt';

interface UseMapPvpRuntimeArgs {
  appearance: CharacterAppearance;
  onBattle?: (
    monsterType: MonsterType,
    initialTurn: 'player' | 'monster',
    monsterBootstrap: MonsterBattleBootstrapResponse,
  ) => void;
  resolvePvpOpponents?: ResolvePvpOpponents;
  resolvePvpChallengeApi?: ResolvePvpChallengeApi;
  setMenuVisible: React.Dispatch<React.SetStateAction<boolean>>;
  setActiveCharacterDialog: React.Dispatch<React.SetStateAction<MapCharacterDialogKind | null>>;
}

export const useMapPvpRuntime = ({
  appearance,
  onBattle,
  resolvePvpOpponents,
  resolvePvpChallengeApi,
  setMenuVisible,
  setActiveCharacterDialog,
}: UseMapPvpRuntimeArgs) => {
  const [activePvpDialog, setActivePvpDialog] = useState<PvpDialogMode | null>(null);
  const [pvpOpponents, setPvpOpponents] = useState<PvpOpponentEntry[]>([]);
  const [pvpStatus, setPvpStatus] = useState<PvpDialogStatus>('idle');
  const [pvpError, setPvpError] = useState<string | null>(null);
  const [pvpTarget, setPvpTarget] = useState('');
  const [pvpStakeThousands, setPvpStakeThousands] = useState('0');
  const [pvpAllowSpectators, setPvpAllowSpectators] = useState(true);
  const [pvpOneWay, setPvpOneWay] = useState(false);
  const [pvpDisableSpecialSkills, setPvpDisableSpecialSkills] = useState(false);
  const [pvpIncomingPrompt, setPvpIncomingPrompt] = useState<PvpIncomingPromptState | null>(null);
  const [pvpPendingTicketId, setPvpPendingTicketId] = useState<string | null>(null);

  const loadPvpOpponents = useCallback((mode: PvpDialogMode = activePvpDialog ?? 'challenge') => {
    const username = appearance.username?.trim();
    if (!username) {
      setPvpOpponents([]);
      setPvpStatus('error');
      setPvpError('Thiếu tên nhân vật');
      return;
    }

    if (!resolvePvpOpponents) {
      setPvpOpponents([]);
      setPvpStatus('error');
      setPvpError('Chưa có resolver Lôi Đài');
      return;
    }

    setPvpStatus('loading');
    setPvpError(null);
    void Promise.resolve(resolvePvpOpponents({ username, registerPresence: mode === 'arena' }))
      .then((response) => {
        const opponents = response?.opponents ?? [];
        setPvpOpponents(opponents);
        setPvpStatus('ready');
        setPvpTarget((current) => current.trim() || (opponents[0]?.username ?? ''));
      })
      .catch(() => {
        setPvpOpponents([]);
        setPvpStatus('error');
        setPvpError('Không tải được danh sách');
      });
  }, [activePvpDialog, appearance.username, resolvePvpOpponents]);

  const openPvpDialog = useCallback((mode: PvpDialogMode, target = '') => {
    setMenuVisible(false);
    setActiveCharacterDialog(null);
    setActivePvpDialog(mode);
    setPvpError(null);
    if (mode === 'challenge') {
      setPvpTarget(target);
    } else if (target) {
      setPvpTarget(target);
    }
    loadPvpOpponents(mode);
  }, [loadPvpOpponents, setActiveCharacterDialog, setMenuVisible]);

  const startPvpBattle = useCallback((target: string, options: PvpStartOptions) => {
    const username = appearance.username?.trim();
    const targetUsername = target.trim();
    if (!username) {
      setPvpStatus('error');
      setPvpError('Thiếu tên nhân vật');
      return;
    }
    if (!targetUsername) {
      setPvpStatus('error');
      setPvpError('Chưa nhập nick');
      return;
    }
    if (targetUsername.toLowerCase() === username.toLowerCase()) {
      setPvpStatus('error');
      setPvpError('Không thể khiêu chiến chính mình');
      return;
    }
    if (!resolvePvpChallengeApi || !onBattle) {
      setPvpStatus('error');
      setPvpError('Chưa có resolver khiêu chiến');
      return;
    }

    const sameRoomCandidate = pvpOpponents.find(
      (opponent) => opponent.username.toLowerCase() === targetUsername.toLowerCase(),
    );
    if (!sameRoomCandidate) {
      setPvpStatus('error');
      setPvpError('Đối thủ không ở cùng khu/phòng hoặc đang bận');
      return;
    }

    setPvpStatus('starting');
    setPvpError(null);
    void Promise.resolve(resolvePvpChallengeApi.create({
      username,
      targetUsername,
      stake: options.stake,
    }))
      .then((ticket) => {
        if (!ticket) {
          setPvpStatus('error');
          setPvpError('Không gửi được lời khiêu chiến');
          return;
        }

        setPvpPendingTicketId(ticket.ticketId);
        setPvpStatus('ready');
        setPvpError('Đã gửi lời mời, đang chờ đối thủ đồng ý...');
      })
      .catch(() => {
        setPvpStatus('error');
        setPvpError('Không gửi được lời khiêu chiến');
      });
  }, [appearance.username, onBattle, pvpOpponents, resolvePvpChallengeApi]);

  const handlePvpAcceptedBootstrap = useCallback((bootstrap: MonsterBattleBootstrapResponse) => {
    if (!onBattle) return;
    setActivePvpDialog(null);
    setPvpIncomingPrompt(null);
    setPvpPendingTicketId(null);
    setPvpStatus('idle');
    setPvpError(null);
    onBattle('fire', bootstrap.initialTurnSide === 'enemy' ? 'monster' : 'player', bootstrap);
  }, [onBattle]);

  const acceptPvpChallenge = useCallback((ticket: PvpChallengeTicket) => {
    const username = appearance.username?.trim();
    if (!username || !resolvePvpChallengeApi) return;
    setPvpIncomingPrompt({ ticket, status: 'starting' });
    void resolvePvpChallengeApi.accept({ ticketId: ticket.ticketId, username })
      .then((response) => {
        if (response?.bootstrap) {
          handlePvpAcceptedBootstrap(response.bootstrap);
          return;
        }
        setPvpIncomingPrompt(null);
      })
      .catch(() => setPvpIncomingPrompt(null));
  }, [appearance.username, handlePvpAcceptedBootstrap, resolvePvpChallengeApi]);

  const declinePvpChallenge = useCallback((ticket: PvpChallengeTicket) => {
    const username = appearance.username?.trim();
    setPvpIncomingPrompt(null);
    if (!username || !resolvePvpChallengeApi) return;
    void resolvePvpChallengeApi.decline({ ticketId: ticket.ticketId, username });
  }, [appearance.username, resolvePvpChallengeApi]);

  useEffect(() => {
    const username = appearance.username?.trim();
    if (!username || !resolvePvpChallengeApi || !onBattle) return;
    let cancelled = false;
    const poll = () => {
      void resolvePvpChallengeApi.list(username).then((inbox) => {
        if (cancelled) return;
        const incoming = inbox?.incoming.find(ticket => ticket.state.toLowerCase() === 'pending') ?? null;
        if (incoming && !pvpIncomingPrompt) {
          setPvpIncomingPrompt({ ticket: incoming, status: 'pending' });
        }
      });
      if (pvpPendingTicketId) {
        void resolvePvpChallengeApi.status(pvpPendingTicketId, username).then((status) => {
          if (cancelled || !status) return;
          if (status.bootstrap) {
            handlePvpAcceptedBootstrap(status.bootstrap);
          } else if (!status.ticket.state.toLowerCase().includes('pending')) {
            setPvpPendingTicketId(null);
            setPvpError(status.ticket.state === 'Declined' ? 'Đối thủ đã từ chối' : 'Lời mời đã hết hiệu lực');
          }
        });
      }
    };
    poll();
    const timer = setInterval(poll, 1200);
    return () => {
      cancelled = true;
      clearInterval(timer);
    };
  }, [appearance.username, handlePvpAcceptedBootstrap, onBattle, pvpIncomingPrompt, pvpPendingTicketId, resolvePvpChallengeApi]);

  return {
    activePvpDialog,
    setActivePvpDialog,
    pvpOpponents,
    pvpStatus,
    pvpError,
    pvpTarget,
    setPvpTarget,
    pvpStakeThousands,
    setPvpStakeThousands,
    pvpAllowSpectators,
    setPvpAllowSpectators,
    pvpOneWay,
    setPvpOneWay,
    pvpDisableSpecialSkills,
    setPvpDisableSpecialSkills,
    pvpIncomingPrompt,
    setPvpIncomingPrompt,
    loadPvpOpponents,
    openPvpDialog,
    startPvpBattle,
    acceptPvpChallenge,
    declinePvpChallenge,
  };
};
