/*
 * Decompiled with CFR 0.152.
 */
public class cs {
    private static int b = 10;
    public static u a;

    public static final boolean a() {
        return a.c(-14);
    }

    public static final byte[] b() {
        return a.a(-14);
    }

    public static void a(byte[] byArray) {
        a.b(-14, byArray, 0, 13);
    }

    public static void c() {
        if (!v.V) {
            return;
        }
        v.Y = 0;
        byte[] byArray = a.a(-8);
        if (byArray != null && byArray.length >= 4) {
            v.Y = m.c(byArray);
        }
        byArray = m.a(++v.Y);
        a.b(-8, byArray, 0, 4);
    }

    public static void d() {
        if (!v.V) {
            return;
        }
        cs.a(false);
    }

    public static void a(boolean bl2) {
        if (!v.V) {
            return;
        }
        if (bl2) {
            if (--v.T <= 0) {
                v.S = false;
                v.T = 0;
                return;
            }
        } else {
            v.S = true;
            v.T = 4;
        }
    }

    public static byte[] e() {
        return ((u)null).a(-12);
    }

    public static int[] f() {
        int[] nArray = new int[b];
        byte[] byArray = a.a(-1);
        if (byArray == null) {
            byArray = new byte[b + 1 << 2];
            byte[] byArray2 = m.a(cv.a(4) + 1);
            System.arraycopy(byArray2, 0, byArray, byArray.length - 4, 4);
            a.b(-1, byArray, 0, byArray.length);
        }
        int n2 = 0;
        while (n2 < nArray.length) {
            nArray[n2] = m.a(byArray, n2 << 2);
            ++n2;
        }
        return nArray;
    }

    /*
     * WARNING - void declaration
     */
    public static boolean a(int[] nArray) {
        int n2;
        void var2_4;
        if (nArray == null) {
            return false;
        }
        boolean[] blArray = new boolean[4];
        int bl2 = nArray.length - 1;
        while (var2_4 >= 0) {
            if (nArray[var2_4] > 0) {
                blArray[nArray[var2_4] - 1] = true;
            }
            --var2_4;
        }
        boolean bl3 = true;
        int n22 = 0;
        while (n22 < 4) {
            n2 &= blArray[n22];
            ++n22;
        }
        return n2 != 0;
    }

    public static void a(int n2, int n3, String string, String string2) {
        if (Math.abs(n2) >= b) {
            return;
        }
        byte[] byArray = a.a(-1);
        if (byArray == null) {
            return;
        }
        Object[] objectArray = new int[b + 1];
        int[] nArray = new int[4];
        int n4 = 0;
        while (n4 < objectArray.length) {
            objectArray[n4] = m.a(byArray, n4 << 2);
            if (n4 < objectArray.length - 1 && objectArray[n4] > 0) {
                int n5 = objectArray[n4] - 1;
                nArray[n5] = nArray[n5] + 1;
            }
            ++n4;
        }
        n4 = -1;
        if (n3 != 1) {
            int n6 = Math.abs(objectArray[objectArray.length - 1]) - 1;
            int n7 = nArray[0] + nArray[1] + nArray[2] + nArray[3];
            if (n7 >= 10) {
                return;
            }
            if (n7 >= 7) {
                n7 = 0;
                while (n7 < 4) {
                    if (n7 != n6 && nArray[n7] <= 0) {
                        n4 = n7;
                        break;
                    }
                    ++n7;
                }
                if (n4 == -1) {
                    while ((n4 = cv.a(4)) == n6) {
                    }
                }
            } else {
                while ((n4 = cv.a(4)) == n6) {
                }
            }
            ++n4;
        } else {
            if (string == null) {
                return;
            }
            n4 = Math.abs(objectArray[objectArray.length - 1]);
            objectArray = string.getBytes();
            a.a(-2, (byte[])objectArray, 0, objectArray.length);
        }
        ct.a("index=" + n2 + ",luckyNum=" + n3 + ",codeWin=" + string + "recordChoose=" + n4);
        byte[] byArray2 = m.a(n4);
        System.arraycopy(byArray2, 0, byArray, n2 << 2, 4);
        a.a(-1, byArray, 0, byArray.length);
        if (string2 != null) {
            if (n3 == 1 && string != null) {
                byArray = string2.trim().getBytes();
                a.a(-3, byArray, 0, byArray.length);
                byArray = string.trim().getBytes();
                a.a(-2, byArray, 0, byArray.length);
            } else {
                byArray = a.a(-2);
                if (byArray == null) {
                    byArray = string2.trim().getBytes();
                    a.a(-3, byArray, 0, byArray.length);
                }
            }
        }
        a.a();
    }

    public static byte[] g() {
        return a.a(-2);
    }

    public static String h() {
        byte[] byArray = a.a(-3);
        if (byArray == null) {
            return null;
        }
        return new String(byArray);
    }

    public static void i() {
        try {
            a.b(-1);
            a.b(-2);
            a.b(-3);
            a.a();
            return;
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            throwable.printStackTrace();
            return;
        }
    }

    public static void a(int n2, be be2) {
        n2 = v.Y;
        ci.a("linc0", n2, be2);
    }
}

