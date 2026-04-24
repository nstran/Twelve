namespace Twelve.Core.GameLogic
{
    /// <summary>
    /// Canonical mapping between server storage element and Java client wire code.
    /// Java raw code: 1 = Hoa, 2 = Loi, 4 = Thuy.
    /// Storage code: 0 = Hoa, 1 = Loi, 2 = Thuy.
    /// </summary>
    public static class ElementMapper
    {
        public const int StorageHoa = 0;
        public const int StorageLoi = 1;
        public const int StorageThuy = 2;

        public const int RawJavaHoa = 1;
        public const int RawJavaLoi = 2;
        public const int RawJavaThuy = 4;

        public static int ToRawJavaCode(int storageElement) =>
            storageElement switch
            {
                StorageHoa => RawJavaHoa,
                StorageLoi => RawJavaLoi,
                StorageThuy => RawJavaThuy,
                _ => RawJavaHoa
            };

        public static int ToStorageCode(int rawJavaCode) =>
            rawJavaCode switch
            {
                RawJavaHoa => StorageHoa,
                RawJavaLoi => StorageLoi,
                RawJavaThuy => StorageThuy,
                _ => StorageHoa
            };

        public static bool IsValidStorageCode(int storageElement) =>
            storageElement is StorageHoa or StorageLoi or StorageThuy;

        public static bool IsValidRawJavaCode(int rawJavaCode) =>
            rawJavaCode is RawJavaHoa or RawJavaLoi or RawJavaThuy;
    }
}

