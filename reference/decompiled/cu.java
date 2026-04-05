/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;

public final class cu {
    private static byte a = 0;
    private static byte b;
    private static boolean c;
    private static long d;
    private static boolean e;

    static {
        c = true;
        d = 0L;
        e = false;
    }

    protected cu() {
    }

    public static void a() {
        e = true;
    }

    public static void b() {
        switch (z.J) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 6: 
            case 7: {
                c = false;
                break;
            }
            default: {
                c = false;
            }
        }
        if (!c) {
            return;
        }
        cu.j();
    }

    private static final void j() {
        if (!cv.a()) {
            if (z.U > 5) {
                a = (byte)2;
                return;
            }
            cu.l();
            cu.k();
            return;
        }
        byte[] byArray = cv.b();
        byte[] byArray2 = new byte[3];
        byte[] byArray3 = byArray2;
        byArray2[0] = byArray[10];
        byArray3[1] = byArray[11];
        byArray3[2] = byArray[12];
        byArray[10] = 0;
        byArray[11] = 0;
        byArray[12] = 0;
        byte[] byArray4 = da.a(byArray);
        if (byArray3[0] != byArray4[byArray4.length - 3] || byArray3[1] != byArray4[byArray4.length - 2] || byArray3[2] != byArray4[byArray4.length - 1]) {
            a = (byte)2;
            return;
        }
        a = byArray[0];
        b = byArray[1];
        byte[] byArray5 = new byte[8];
        byArray3 = byArray5;
        byArray5[0] = byArray[2];
        byArray3[1] = byArray[3];
        byArray3[2] = byArray[4];
        byArray3[3] = byArray[5];
        byArray3[4] = byArray[6];
        byArray3[5] = byArray[7];
        byArray3[6] = byArray[8];
        byArray3[7] = byArray[9];
        d = p.d(byArray3);
    }

    private static final void k() {
        byte[] byArray = new byte[13];
        byte[] byArray2 = byArray;
        byArray[0] = a;
        byArray2[1] = b;
        byte[] byArray3 = p.a(d);
        byArray2[2] = byArray3[0];
        byArray2[3] = byArray3[1];
        byArray2[4] = byArray3[2];
        byArray2[5] = byArray3[3];
        byArray2[6] = byArray3[4];
        byArray2[7] = byArray3[5];
        byArray2[8] = byArray3[6];
        byArray2[9] = byArray3[7];
        byArray2[10] = 0;
        byArray2[11] = 0;
        byArray2[12] = 0;
        byArray3 = da.a(byArray2);
        byArray2[10] = byArray3[byArray3.length - 3];
        byArray2[11] = byArray3[byArray3.length - 2];
        byArray2[12] = byArray3[byArray3.length - 1];
        cv.a(byArray2);
    }

    public static final boolean c() {
        cu.j();
        return a == 2;
    }

    public static final boolean d() {
        return a == 1;
    }

    public static final void e() {
        a = (byte)2;
        b = (byte)6;
        cu.k();
    }

    private static void l() {
        a = 0;
        b = 0;
        d = 0L;
    }

    public static final void f() {
        long l2;
        if (e) {
            e = false;
            return;
        }
        if (!c) {
            return;
        }
        if (a == 1) {
            return;
        }
        cu.j();
        if (d == 0L) {
            d = System.currentTimeMillis();
        }
        if ((l2 = System.currentTimeMillis()) - d >= 300000L || b >= 5) {
            a = 1;
        } else {
            b = (byte)(b + 1);
        }
        cu.k();
    }

    public static final void g() {
        if (!c) {
            return;
        }
        cu.l();
        cu.k();
    }

    public static final void h() {
        Object object = l.a("K\u00edch th\u01b0\u1edbc m\u00e0n h\u00ecnh c\u1ee7a b\u1ea1n: %d:%d, kh\u00f4ng ph\u00f9 h\u1ee3p v\u1edbi y\u00eau c\u1ea7u c\u1ee7a game (%d:%d)", "%d", new String[]{String.valueOf(z.r), String.valueOf(z.s), String.valueOf(z.p), String.valueOf(z.q)});
        object = ak.b().a("Ch\u00fa \u00fd", (String)object, "\u0110\u00f3ng", 4, 1);
        ((aq)object).a(ak.b());
        ak.b().a((ap)object);
    }

    public static final void i() {
        Object object = "Thi\u1ebft b\u1ecb kh\u00f4ng ph\u00f9 h\u1ee3p \u0111\u1ec3 ch\u01a1i game! Xin c\u1ea3m \u01a1n.";
        object = ak.b().a("Ch\u00fa \u00fd", (String)object, "\u0110\u00f3ng", 4, "H\u1ed7 tr\u1ee3", 8, 1);
        ((aq)object).a(ak.b());
        ak.b().a((ap)object);
    }

    public static final boolean a(boolean bl2) {
        try {
            byte[] byArray = da.a(z.d());
            return da.a(z.k, byArray);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return true;
        }
    }
}

