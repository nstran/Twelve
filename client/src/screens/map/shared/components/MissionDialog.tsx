import React from 'react';
import { Pressable, ScrollView, Text, TouchableOpacity, View } from 'react-native';
import { CornerFrame } from '../../../../components/ui/CornerFrame/CornerFrame';
import type { MapMissionRecord } from '../../core';
import { styles } from './MissionDialog.styles';

interface MissionDialogProps {
  missions: MapMissionRecord[];
  activeMission: MapMissionRecord | null;
  onSelectMission: (questId: string) => void;
  onAcceptMission: (questId: string) => void;
  onClose: () => void;
}

export const MissionDialog: React.FC<MissionDialogProps> = ({
  missions,
  activeMission,
  onSelectMission,
  onAcceptMission,
  onClose,
}) => {
  const visibleMission = activeMission ?? missions[0] ?? null;

  return (
    <View style={styles.overlay} pointerEvents="box-none">
      <Pressable style={styles.backdrop} onPress={onClose} />
      <CornerFrame style={styles.frame} contentStyle={styles.content}>
        <View style={styles.header}>
          <Text style={styles.title}>Nhiệm Vụ</Text>
          <TouchableOpacity onPress={onClose}>
            <Text style={styles.close}>Đóng</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.body}>
          <View style={styles.listPanel}>
            <Text style={styles.panelTitle}>Danh sách</Text>
            <ScrollView style={styles.listScroll} contentContainerStyle={styles.listContent}>
              {missions.length === 0 ? (
                <Text style={styles.emptyText}>Chưa có nhiệm vụ từ server.</Text>
              ) : missions.map((mission) => {
                const selected = visibleMission?.questId === mission.questId;
                return (
                  <TouchableOpacity
                    key={mission.questId}
                    style={[styles.listItem, selected && styles.listItemActive]}
                    onPress={() => onSelectMission(mission.questId)}
                  >
                    <Text style={[styles.listTitle, selected && styles.listTitleActive]}>
                      {mission.title || mission.questId}
                    </Text>
                    <Text style={styles.listMeta}>{mission.statusFlag ? 'Đã nhận/đang làm' : 'Có thể nhận'}</Text>
                  </TouchableOpacity>
                );
              })}
            </ScrollView>
          </View>

          <View style={styles.detailPanel}>
            {visibleMission ? (
              <>
                <Text style={styles.detailTitle}>{visibleMission.title || visibleMission.questId}</Text>
                <Text style={styles.detailDesc}>{visibleMission.description || 'Không có mô tả.'}</Text>
                <Text style={styles.panelTitle}>Mục tiêu</Text>
                {visibleMission.tasks.length > 0 ? visibleMission.tasks.map((task) => (
                  <Text key={`${task.questId}-${task.rawValue}-${task.text}`} style={styles.taskLine}>• {task.text}</Text>
                )) : <Text style={styles.emptyText}>Chưa có mục tiêu.</Text>}
                <Text style={styles.panelTitle}>Thưởng</Text>
                {visibleMission.rewardLines.length > 0 ? visibleMission.rewardLines.map((line) => (
                  <Text key={line} style={styles.rewardLine}>{line}</Text>
                )) : <Text style={styles.emptyText}>Chưa có dữ liệu thưởng.</Text>}
                <TouchableOpacity
                  style={[styles.actionButton, visibleMission.statusFlag && styles.actionButtonDisabled]}
                  disabled={visibleMission.statusFlag}
                  onPress={() => onAcceptMission(visibleMission.questId)}
                >
                  <Text style={styles.actionButtonText}>{visibleMission.statusFlag ? 'Đã nhận' : 'Nhận nhiệm vụ'}</Text>
                </TouchableOpacity>
              </>
            ) : (
              <Text style={styles.emptyText}>Bấm menu Nhiệm Vụ để tải danh sách.</Text>
            )}
          </View>
        </View>
      </CornerFrame>
    </View>
  );
};
