import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Modal,
  FlatList,
  StyleSheet,
  Image,
} from 'react-native';
import { styles } from './CalendarPicker.styles';

const ASSET_ARROW = require('../../../../assets/ui/10_focus_confirmed/arrowfocus1.png');

interface CalendarPickerProps {
  visible: boolean;
  initialDate?: string; 
  onSelect: (dateStr: string) => void;
  onClose: () => void;
}

const YEARS = Array.from({ length: 151 }, (_, i) => 1900 + i); 
const MONTHS = Array.from({ length: 12 }, (_, i) => i + 1);

export const CalendarPicker: React.FC<CalendarPickerProps> = ({
  visible,
  initialDate,
  onSelect,
  onClose,
}) => {
  const parseInitialDate = () => {
    if (!initialDate) return new Date();
    const parts = initialDate.split(' - ');
    if (parts.length === 3) {
      return new Date(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0]));
    }
    return new Date();
  };

  const [viewDate, setViewDate] = useState(parseInitialDate());
  const [selectedDate, setSelectedDate] = useState(parseInitialDate());
  const [showYearSelect, setShowYearSelect] = useState(false);

  const year = viewDate.getFullYear();
  const month = viewDate.getMonth();

  const daysInMonth = new Date(year, month + 1, 0).getDate();
  const firstDay = new Date(year, month, 1).getDay();
  const startOffset = firstDay === 0 ? 6 : firstDay - 1;

  const changeMonth = (delta: number) => {
    setViewDate(new Date(year, month + delta, 1));
  };

  const selectYear = (y: number) => {
    setViewDate(new Date(y, month, 1));
    setShowYearSelect(false);
  };

  const onDatePress = (d: number) => {
    setSelectedDate(new Date(year, month, d));
  };

  const handleConfirm = () => {
    const dd = String(selectedDate.getDate()).padStart(2, '0');
    const mm = String(selectedDate.getMonth() + 1).padStart(2, '0');
    const yyyy = selectedDate.getFullYear();
    onSelect(`${dd} - ${mm} - ${yyyy}`);
    onClose();
  };

  const renderCells = () => {
    const cells = [];
    const totalCells = startOffset + daysInMonth;
    const rows = Math.ceil(totalCells / 7);

    for (let i = 0; i < rows * 7; i++) {
        const dayNum = i - startOffset + 1;
        const columnIdx = i % 7; 
        const rowIdx = Math.floor(i / 7);
        const isGreyRow = rowIdx % 2 !== 0;
        const isSat = columnIdx === 5;
        const isSun = columnIdx === 6;

        if (dayNum > 0 && dayNum <= daysInMonth) {
            const isSelected = 
                selectedDate.getDate() === dayNum && 
                selectedDate.getMonth() === month && 
                selectedDate.getFullYear() === year;

            cells.push(
                <TouchableOpacity 
                    key={`day-${dayNum}`}
                    style={[
                        styles.dayCell,
                        isGreyRow && styles.greyRow,
                        isSat && styles.satColumn,
                        isSun && styles.sunColumn,
                        isSelected && { backgroundColor: '#0055cc' }, 
                    ]}
                    onPress={() => onDatePress(dayNum)}
                >
                    <Text style={[
                        styles.dayText,
                        isSelected && { color: '#ffffff', fontWeight: 'bold' }
                    ]}>
                        {dayNum}
                    </Text>
                </TouchableOpacity>
            );
        } else {
            cells.push(
                <View key={`empty-${i}`} style={[
                    styles.dayCell, 
                    isGreyRow && styles.greyRow,
                    isSat && styles.satColumn,
                    isSun && styles.sunColumn
                ]} />
            );
        }
    }
    return cells;
  };

  return (
    <Modal visible={visible} transparent animationType="none">
      <View style={styles.backdrop}>
        
        {/* J2ME Classic Group Box Style */}
        <View style={styles.groupBox}>
          <Text style={styles.groupLabel}>Ngày</Text>
          
          {/* Year Button (Toggle List) */}
          <TouchableOpacity style={styles.yearBtn} onPress={() => setShowYearSelect(true)}>
            <Text style={styles.yearBtnText}>{year}</Text>
          </TouchableOpacity>

          {/* Month Navigation Row */}
          <View style={styles.monthRow}>
            <TouchableOpacity onPress={() => changeMonth(-1)} style={styles.arrowBtn}>
                <Image source={ASSET_ARROW} style={[styles.arrowIcon, styles.arrowIconLeft]} resizeMode="contain" />
            </TouchableOpacity>
            
            <View style={styles.monthBtn}>
                <Text style={styles.yearBtnText}>{`Tháng ${month + 1}`}</Text>
            </View>

            <TouchableOpacity onPress={() => changeMonth(1)} style={styles.arrowBtn}>
                <Image source={ASSET_ARROW} style={styles.arrowIcon} resizeMode="contain" />
            </TouchableOpacity>
          </View>

          {/* Fixed J2ME Grid Header (7x6 Style) */}
          <View style={styles.gridContainer}>
            <View style={styles.daysGrid}>
                {renderCells()}
            </View>
          </View>

          {/* Action Buttons */}
          <View style={styles.footer}>
            <TouchableOpacity style={styles.footerBtn} onPress={onClose}>
                <Text style={styles.yearBtnText}>Hủy</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.footerBtn} onPress={handleConfirm}>
                <Text style={styles.yearBtnText}>Chọn</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* Year Selection Popup */}
        <Modal visible={showYearSelect} transparent animationType="fade">
            <View style={styles.yearModalBackdrop}>
                <View style={styles.yearList}>
                    <FlatList
                        data={YEARS}
                        keyExtractor={item => item.toString()}
                        initialScrollIndex={YEARS.indexOf(year) - 5}
                        getItemLayout={(_, index) => ({ length: 40, offset: 40 * index, index })}
                        renderItem={({ item }) => (
                            <TouchableOpacity 
                                style={[styles.yearListItem, item === year && { backgroundColor: '#7fbffb' }]}
                                onPress={() => selectYear(item)}
                            >
                                <Text style={styles.yearBtnText}>{item}</Text>
                            </TouchableOpacity>
                        )}
                    />
                </View>
            </View>
        </Modal>

      </View>
    </Modal>
  );
};
