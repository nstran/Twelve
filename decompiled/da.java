/*
 * Decompiled with CFR 0.152.
 */
public final class da {
    private db a = new db();
    private db b = null;
    private static byte[] c;

    static {
        byte[] byArray = new byte[64];
        byArray[0] = -128;
        c = byArray;
        char[] cArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    private da() {
    }

    private void a(db db2, byte[] byArray, int n2, int[] nArray) {
        int n3 = db2.a[0];
        int n4 = db2.a[1];
        int n5 = db2.a[2];
        int n6 = db2.a[3];
        int[] nArray2 = nArray;
        nArray[0] = byArray[n2] & 0xFF | (byArray[n2 + 1] & 0xFF) << 8 | (byArray[n2 + 2] & 0xFF) << 16 | byArray[n2 + 3] << 24;
        nArray[1] = byArray[n2 + 4] & 0xFF | (byArray[n2 + 5] & 0xFF) << 8 | (byArray[n2 + 6] & 0xFF) << 16 | byArray[n2 + 7] << 24;
        nArray[2] = byArray[n2 + 8] & 0xFF | (byArray[n2 + 9] & 0xFF) << 8 | (byArray[n2 + 10] & 0xFF) << 16 | byArray[n2 + 11] << 24;
        nArray[3] = byArray[n2 + 12] & 0xFF | (byArray[n2 + 13] & 0xFF) << 8 | (byArray[n2 + 14] & 0xFF) << 16 | byArray[n2 + 15] << 24;
        nArray[4] = byArray[n2 + 16] & 0xFF | (byArray[n2 + 17] & 0xFF) << 8 | (byArray[n2 + 18] & 0xFF) << 16 | byArray[n2 + 19] << 24;
        nArray[5] = byArray[n2 + 20] & 0xFF | (byArray[n2 + 21] & 0xFF) << 8 | (byArray[n2 + 22] & 0xFF) << 16 | byArray[n2 + 23] << 24;
        nArray[6] = byArray[n2 + 24] & 0xFF | (byArray[n2 + 25] & 0xFF) << 8 | (byArray[n2 + 26] & 0xFF) << 16 | byArray[n2 + 27] << 24;
        nArray[7] = byArray[n2 + 28] & 0xFF | (byArray[n2 + 29] & 0xFF) << 8 | (byArray[n2 + 30] & 0xFF) << 16 | byArray[n2 + 31] << 24;
        nArray[8] = byArray[n2 + 32] & 0xFF | (byArray[n2 + 33] & 0xFF) << 8 | (byArray[n2 + 34] & 0xFF) << 16 | byArray[n2 + 35] << 24;
        nArray[9] = byArray[n2 + 36] & 0xFF | (byArray[n2 + 37] & 0xFF) << 8 | (byArray[n2 + 38] & 0xFF) << 16 | byArray[n2 + 39] << 24;
        nArray[10] = byArray[n2 + 40] & 0xFF | (byArray[n2 + 41] & 0xFF) << 8 | (byArray[n2 + 42] & 0xFF) << 16 | byArray[n2 + 43] << 24;
        nArray[11] = byArray[n2 + 44] & 0xFF | (byArray[n2 + 45] & 0xFF) << 8 | (byArray[n2 + 46] & 0xFF) << 16 | byArray[n2 + 47] << 24;
        nArray[12] = byArray[n2 + 48] & 0xFF | (byArray[n2 + 49] & 0xFF) << 8 | (byArray[n2 + 50] & 0xFF) << 16 | byArray[n2 + 51] << 24;
        nArray[13] = byArray[n2 + 52] & 0xFF | (byArray[n2 + 53] & 0xFF) << 8 | (byArray[n2 + 54] & 0xFF) << 16 | byArray[n2 + 55] << 24;
        nArray[14] = byArray[n2 + 56] & 0xFF | (byArray[n2 + 57] & 0xFF) << 8 | (byArray[n2 + 58] & 0xFF) << 16 | byArray[n2 + 59] << 24;
        nArray[15] = byArray[n2 + 60] & 0xFF | (byArray[n2 + 61] & 0xFF) << 8 | (byArray[n2 + 62] & 0xFF) << 16 | byArray[n2 + 63] << 24;
        n3 += (n4 & n5 | ~n4 & n6) + nArray2[0] + -680876936;
        n3 = (n3 << 7 | n3 >>> 25) + n4;
        n6 += (n3 & n4 | ~n3 & n5) + nArray2[1] + -389564586;
        n6 = (n6 << 12 | n6 >>> 20) + n3;
        n5 += (n6 & n3 | ~n6 & n4) + nArray2[2] + 606105819;
        n5 = (n5 << 17 | n5 >>> 15) + n6;
        n4 += (n5 & n6 | ~n5 & n3) + nArray2[3] + -1044525330;
        n4 = (n4 << 22 | n4 >>> 10) + n5;
        n3 += (n4 & n5 | ~n4 & n6) + nArray2[4] + -176418897;
        n3 = (n3 << 7 | n3 >>> 25) + n4;
        n6 += (n3 & n4 | ~n3 & n5) + nArray2[5] + 1200080426;
        n6 = (n6 << 12 | n6 >>> 20) + n3;
        n5 += (n6 & n3 | ~n6 & n4) + nArray2[6] + -1473231341;
        n5 = (n5 << 17 | n5 >>> 15) + n6;
        n4 += (n5 & n6 | ~n5 & n3) + nArray2[7] + -45705983;
        n4 = (n4 << 22 | n4 >>> 10) + n5;
        n3 += (n4 & n5 | ~n4 & n6) + nArray2[8] + 1770035416;
        n3 = (n3 << 7 | n3 >>> 25) + n4;
        n6 += (n3 & n4 | ~n3 & n5) + nArray2[9] + -1958414417;
        n6 = (n6 << 12 | n6 >>> 20) + n3;
        n5 += (n6 & n3 | ~n6 & n4) + nArray2[10] + -42063;
        n5 = (n5 << 17 | n5 >>> 15) + n6;
        n4 += (n5 & n6 | ~n5 & n3) + nArray2[11] + -1990404162;
        n4 = (n4 << 22 | n4 >>> 10) + n5;
        n3 += (n4 & n5 | ~n4 & n6) + nArray2[12] + 1804603682;
        n3 = (n3 << 7 | n3 >>> 25) + n4;
        n6 += (n3 & n4 | ~n3 & n5) + nArray2[13] + -40341101;
        n6 = (n6 << 12 | n6 >>> 20) + n3;
        n5 += (n6 & n3 | ~n6 & n4) + nArray2[14] + -1502002290;
        n5 = (n5 << 17 | n5 >>> 15) + n6;
        n4 += (n5 & n6 | ~n5 & n3) + nArray2[15] + 1236535329;
        n4 = (n4 << 22 | n4 >>> 10) + n5;
        n3 += (n4 & n6 | n5 & ~n6) + nArray2[1] + -165796510;
        n3 = (n3 << 5 | n3 >>> 27) + n4;
        n6 += (n3 & n5 | n4 & ~n5) + nArray2[6] + -1069501632;
        n6 = (n6 << 9 | n6 >>> 23) + n3;
        n5 += (n6 & n4 | n3 & ~n4) + nArray2[11] + 643717713;
        n5 = (n5 << 14 | n5 >>> 18) + n6;
        n4 += (n5 & n3 | n6 & ~n3) + nArray2[0] + -373897302;
        n4 = (n4 << 20 | n4 >>> 12) + n5;
        n3 += (n4 & n6 | n5 & ~n6) + nArray2[5] + -701558691;
        n3 = (n3 << 5 | n3 >>> 27) + n4;
        n6 += (n3 & n5 | n4 & ~n5) + nArray2[10] + 38016083;
        n6 = (n6 << 9 | n6 >>> 23) + n3;
        n5 += (n6 & n4 | n3 & ~n4) + nArray2[15] + -660478335;
        n5 = (n5 << 14 | n5 >>> 18) + n6;
        n4 += (n5 & n3 | n6 & ~n3) + nArray2[4] + -405537848;
        n4 = (n4 << 20 | n4 >>> 12) + n5;
        n3 += (n4 & n6 | n5 & ~n6) + nArray2[9] + 568446438;
        n3 = (n3 << 5 | n3 >>> 27) + n4;
        n6 += (n3 & n5 | n4 & ~n5) + nArray2[14] + -1019803690;
        n6 = (n6 << 9 | n6 >>> 23) + n3;
        n5 += (n6 & n4 | n3 & ~n4) + nArray2[3] + -187363961;
        n5 = (n5 << 14 | n5 >>> 18) + n6;
        n4 += (n5 & n3 | n6 & ~n3) + nArray2[8] + 1163531501;
        n4 = (n4 << 20 | n4 >>> 12) + n5;
        n3 += (n4 & n6 | n5 & ~n6) + nArray2[13] + -1444681467;
        n3 = (n3 << 5 | n3 >>> 27) + n4;
        n6 += (n3 & n5 | n4 & ~n5) + nArray2[2] + -51403784;
        n6 = (n6 << 9 | n6 >>> 23) + n3;
        n5 += (n6 & n4 | n3 & ~n4) + nArray2[7] + 1735328473;
        n5 = (n5 << 14 | n5 >>> 18) + n6;
        n4 += (n5 & n3 | n6 & ~n3) + nArray2[12] + -1926607734;
        n4 = (n4 << 20 | n4 >>> 12) + n5;
        n3 += (n4 ^ n5 ^ n6) + nArray2[5] + -378558;
        n3 = (n3 << 4 | n3 >>> 28) + n4;
        n6 += (n3 ^ n4 ^ n5) + nArray2[8] + -2022574463;
        n6 = (n6 << 11 | n6 >>> 21) + n3;
        n5 += (n6 ^ n3 ^ n4) + nArray2[11] + 1839030562;
        n5 = (n5 << 16 | n5 >>> 16) + n6;
        n4 += (n5 ^ n6 ^ n3) + nArray2[14] + -35309556;
        n4 = (n4 << 23 | n4 >>> 9) + n5;
        n3 += (n4 ^ n5 ^ n6) + nArray2[1] + -1530992060;
        n3 = (n3 << 4 | n3 >>> 28) + n4;
        n6 += (n3 ^ n4 ^ n5) + nArray2[4] + 1272893353;
        n6 = (n6 << 11 | n6 >>> 21) + n3;
        n5 += (n6 ^ n3 ^ n4) + nArray2[7] + -155497632;
        n5 = (n5 << 16 | n5 >>> 16) + n6;
        n4 += (n5 ^ n6 ^ n3) + nArray2[10] + -1094730640;
        n4 = (n4 << 23 | n4 >>> 9) + n5;
        n3 += (n4 ^ n5 ^ n6) + nArray2[13] + 681279174;
        n3 = (n3 << 4 | n3 >>> 28) + n4;
        n6 += (n3 ^ n4 ^ n5) + nArray2[0] + -358537222;
        n6 = (n6 << 11 | n6 >>> 21) + n3;
        n5 += (n6 ^ n3 ^ n4) + nArray2[3] + -722521979;
        n5 = (n5 << 16 | n5 >>> 16) + n6;
        n4 += (n5 ^ n6 ^ n3) + nArray2[6] + 76029189;
        n4 = (n4 << 23 | n4 >>> 9) + n5;
        n3 += (n4 ^ n5 ^ n6) + nArray2[9] + -640364487;
        n3 = (n3 << 4 | n3 >>> 28) + n4;
        n6 += (n3 ^ n4 ^ n5) + nArray2[12] + -421815835;
        n6 = (n6 << 11 | n6 >>> 21) + n3;
        n5 += (n6 ^ n3 ^ n4) + nArray2[15] + 530742520;
        n5 = (n5 << 16 | n5 >>> 16) + n6;
        n4 += (n5 ^ n6 ^ n3) + nArray2[2] + -995338651;
        n4 = (n4 << 23 | n4 >>> 9) + n5;
        n3 += (n5 ^ (n4 | ~n6)) + nArray2[0] + -198630844;
        n3 = (n3 << 6 | n3 >>> 26) + n4;
        n6 += (n4 ^ (n3 | ~n5)) + nArray2[7] + 1126891415;
        n6 = (n6 << 10 | n6 >>> 22) + n3;
        n5 += (n3 ^ (n6 | ~n4)) + nArray2[14] + -1416354905;
        n5 = (n5 << 15 | n5 >>> 17) + n6;
        n4 += (n6 ^ (n5 | ~n3)) + nArray2[5] + -57434055;
        n4 = (n4 << 21 | n4 >>> 11) + n5;
        n3 += (n5 ^ (n4 | ~n6)) + nArray2[12] + 1700485571;
        n3 = (n3 << 6 | n3 >>> 26) + n4;
        n6 += (n4 ^ (n3 | ~n5)) + nArray2[3] + -1894986606;
        n6 = (n6 << 10 | n6 >>> 22) + n3;
        n5 += (n3 ^ (n6 | ~n4)) + nArray2[10] + -1051523;
        n5 = (n5 << 15 | n5 >>> 17) + n6;
        n4 += (n6 ^ (n5 | ~n3)) + nArray2[1] + -2054922799;
        n4 = (n4 << 21 | n4 >>> 11) + n5;
        n3 += (n5 ^ (n4 | ~n6)) + nArray2[8] + 1873313359;
        n3 = (n3 << 6 | n3 >>> 26) + n4;
        n6 += (n4 ^ (n3 | ~n5)) + nArray2[15] + -30611744;
        n6 = (n6 << 10 | n6 >>> 22) + n3;
        n5 += (n3 ^ (n6 | ~n4)) + nArray2[6] + -1560198380;
        n5 = (n5 << 15 | n5 >>> 17) + n6;
        n4 += (n6 ^ (n5 | ~n3)) + nArray2[13] + 1309151649;
        n4 = (n4 << 21 | n4 >>> 11) + n5;
        n3 += (n5 ^ (n4 | ~n6)) + nArray2[4] + -145523070;
        n3 = (n3 << 6 | n3 >>> 26) + n4;
        n6 += (n4 ^ (n3 | ~n5)) + nArray2[11] + -1120210379;
        n6 = (n6 << 10 | n6 >>> 22) + n3;
        n5 += (n3 ^ (n6 | ~n4)) + nArray2[2] + 718787259;
        n5 = (n5 << 15 | n5 >>> 17) + n6;
        n4 += (n6 ^ (n5 | ~n3)) + nArray2[9] + -343485551;
        n4 = (n4 << 21 | n4 >>> 11) + n5;
        db2.a[0] = db2.a[0] + n3;
        db2.a[1] = db2.a[1] + n4;
        db2.a[2] = db2.a[2] + n5;
        db2.a[3] = db2.a[3] + n6;
    }

    private void a(db db2, byte[] byArray, int n2, int n3) {
        int n4;
        this.b = null;
        if (n3 > byArray.length) {
            n3 = byArray.length;
        }
        n2 = (int)(db2.b & 0x3FL);
        db2.b += (long)n3;
        int n5 = 64 - n2;
        if (n3 >= n5) {
            int[] nArray = new int[16];
            if (n5 == 64) {
                n5 = 0;
            } else {
                n4 = 0;
                while (n4 < n5) {
                    db2.c[n4 + n2] = byArray[n4];
                    ++n4;
                }
                this.a(db2, db2.c, 0, nArray);
            }
            n4 = n5;
            while (n4 + 63 < n3) {
                this.a(db2, byArray, n4, nArray);
                n4 += 64;
            }
            n2 = 0;
        } else {
            n4 = 0;
        }
        if (n4 < n3) {
            n5 = n4;
            while (n4 < n3) {
                db2.c[n2 + n4 - n5] = byArray[n4];
                ++n4;
            }
        }
    }

    private static byte[] a(int[] nArray, int n2) {
        byte[] byArray = new byte[n2];
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            byArray[n3] = (byte)nArray[n4];
            byArray[n3 + 1] = (byte)(nArray[n4] >>> 8);
            byArray[n3 + 2] = (byte)(nArray[n4] >>> 16);
            byArray[n3 + 3] = (byte)(nArray[n4] >>> 24);
            ++n4;
            n3 += 4;
        }
        return byArray;
    }

    public static byte[] a(String string) {
        String string2 = string;
        string = null;
        string = string2;
        return da.a(l.c(string2, null));
    }

    public static byte[] a(byte[] object) {
        Object object2 = new da();
        ((da)object2).a(((da)object2).a, (byte[])object, 0, ((byte[])object).length);
        da da2 = object2;
        object = da2;
        if (da2.b == null) {
            db db2 = new db(((da)object).a);
            int[] nArray = new int[]{(int)(db2.b << 3), (int)(db2.b >> 29)};
            object2 = nArray;
            object2 = da.a(nArray, 8);
            int n2 = (int)(db2.b & 0x3FL);
            n2 = n2 < 56 ? 56 - n2 : 120 - n2;
            super.a(db2, c, 0, n2);
            super.a(db2, (byte[])object2, 0, 8);
            ((da)object).b = db2;
        }
        return da.a(((da)object).b.a, 16);
    }

    public static boolean a(byte[] byArray, byte[] byArray2) {
        if (byArray == null) {
            return byArray2 == null;
        }
        if (byArray2 == null) {
            return false;
        }
        int n2 = 16;
        if (byArray.length < 16) {
            if (byArray2.length != byArray.length) {
                return false;
            }
            n2 = byArray.length;
        } else if (byArray2.length < 16) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (byArray[n3] != byArray2[n3]) {
                return false;
            }
            ++n3;
        }
        return true;
    }
}

