/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;

public final class p {
    static {
        new Random(System.currentTimeMillis());
    }

    public static int a(byte by2) {
        return by2 & 0xFF;
    }

    public static int a(byte[] byArray) {
        if (byArray.length < 2) {
            return 0;
        }
        return p.a(byArray[0], byArray[1]);
    }

    public static int a(byte by2, byte by3) {
        return ((by2 & 0xFF) << 8) + (by3 & 0xFF);
    }

    public static int b(byte[] byArray) {
        if (byArray.length < 3) {
            return 0;
        }
        byte by2 = byArray[2];
        byte by3 = byArray[1];
        byte by4 = byArray[0];
        return ((by4 & 0xFF) << 16) + ((by3 & 0xFF) << 8) + (by2 & 0xFF);
    }

    public static int c(byte[] byArray) {
        if (byArray.length < 4) {
            return 0;
        }
        return p.a(byArray[0], byArray[1], byArray[2], byArray[3]);
    }

    public static int a(byte[] byArray, int n2) {
        return p.a(byArray[n2], byArray[n2 + 1], byArray[n2 + 2], byArray[n2 + 3]);
    }

    public static int a(byte by2, byte by3, byte by4, byte by5) {
        return ((by2 & 0xFF) << 24) + ((by3 & 0xFF) << 16) + ((by4 & 0xFF) << 8) + (by5 & 0xFF);
    }

    public static short b(byte by2, byte by3) {
        return (short)(((by2 & 0xFF) << 8) + (by3 & 0xFF));
    }

    public static byte[] a(short s2) {
        return new byte[]{(byte)(s2 >>> 8), (byte)s2};
    }

    public static byte[] a(int n2) {
        return new byte[]{(byte)(n2 >>> 24), (byte)(n2 >>> 16), (byte)(n2 >>> 8), (byte)n2};
    }

    public static byte[] a(long l2) {
        return new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2};
    }

    public static long b(byte[] byArray, int n2) {
        int n3 = byArray.length - n2;
        if (n3 < 8) {
            long l2 = 0L;
            n3 = 56;
            int n4 = n2;
            while (n4 < byArray.length) {
                l2 += ((long)byArray[n4] & 0xFFL) << n3;
                n3 -= 8;
                ++n4;
            }
            n4 -= n2;
            while (n4 < 8) {
                l2 += (long)(0 << n3);
                n3 -= 8;
                ++n4;
            }
            return l2;
        }
        if (n3 > 8) {
            long l3 = 0L;
            n3 = 56;
            int n5 = n2;
            n2 += 8;
            while (n5 < n2) {
                l3 += ((long)byArray[n5] & 0xFFL) << n3;
                n3 -= 8;
                ++n5;
            }
            return l3;
        }
        byte by2 = byArray[n2];
        byte by3 = byArray[n2 + 7];
        byte by4 = byArray[n2 + 6];
        byte by5 = byArray[n2 + 5];
        byte by6 = byArray[n2 + 4];
        byte by7 = byArray[n2 + 3];
        n3 = byArray[n2 + 2];
        n2 = byArray[n2 + 1];
        byte by8 = by2;
        return ((long)by2 << 56) + ((long)(n2 & 0xFF) << 48) + ((long)(n3 & 0xFF) << 40) + ((long)(by7 & 0xFF) << 32) + ((long)(by6 & 0xFF) << 24) + (long)((by5 & 0xFF) << 16) + (long)((by4 & 0xFF) << 8) + (long)(by3 & 0xFF);
    }

    public static long d(byte[] byArray) {
        return p.b(byArray, 0);
    }
}

