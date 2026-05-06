/**
 * React hook for PvP UI responsive scaling
 * 
 * Provides scaled dimensions based on Java client evidence and current screen size.
 * Re-calculates on window dimension changes (orientation, split-screen, etc.)
 */

import { useWindowDimensions } from 'react-native';
import { useMemo } from 'react';
import { calculatePvpScaleFactor, getScaledPvpConstants, scalePvpDimension } from '../pvpResponsiveScaling';

/**
 * Hook to get responsive PvP UI scale factor
 * 
 * Automatically updates when window dimensions change (orientation, etc.)
 * 
 * @returns Scale factor for current screen
 */
export function useResponsivePvpScale(): number {
  const { width, height } = useWindowDimensions();
  
  return useMemo(() => {
    // Force recalculation when dimensions change
    return calculatePvpScaleFactor();
  }, [width, height]);
}

/**
 * Hook to get all scaled PvP UI constants
 * 
 * Automatically updates when window dimensions change
 * 
 * @returns Object with all scaled PvP constants
 */
export function useScaledPvpConstants() {
  const { width, height } = useWindowDimensions();
  
  return useMemo(() => {
    return getScaledPvpConstants();
  }, [width, height]);
}

/**
 * Hook to scale a specific Java dimension
 * 
 * @param javaDimension - Original Java pixel dimension
 * @returns Scaled dimension for current screen
 */
export function useScaledPvpDimension(javaDimension: number): number {
  const scale = useResponsivePvpScale();
  
  return useMemo(() => {
    return scalePvpDimension(javaDimension, scale);
  }, [javaDimension, scale]);
}
