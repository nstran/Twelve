/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package com.mg.sq;

import com.mg.smsgame.MGMIDlet;
import com.mg.sq.b;
import javax.microedition.lcdui.Image;

public final class a
extends al
implements bq,
ig,
kp {
    private ik n;
    public static d g;
    public static d h;
    private static String o;
    private static byte p;
    private static String q;
    private static a r;
    public static byte i;
    public static String[] j;
    public static og k;
    private cs s;
    public static final bd l;
    private int[] t = new int[]{155, 151, 155, 154, 155, 156, 150, 156, 152};
    private int[] u = new int[]{155, 152, 154, 157, 151, 151, 150, 156, 152};
    private int v;
    public static boolean m;
    private static String w;
    private static String[] x;
    private static String[] y;

    static {
        o = null;
        p = (byte)-1;
        r = new a();
        j = null;
        k = null;
        l = new gd();
        m = false;
    }

    public static void o() {
        g = new cb(4863);
        g.a(4863);
        h = new ie(new int[]{0xFFFFFF, 0xFE0000});
        h.a(0xFE0000);
        if (z.r >= 230 && z.s >= 310 || z.s > z.r) {
            i = 0;
            return;
        }
        i = 1;
    }

    public static void p() {
        g = h = ca.d;
        ov.b();
    }

    public static a q() {
        return (a)ak.b();
    }

    public a() {
        this.a(new bb(5));
        ak.a().a(new av(-241260, this.t, this));
        ak.a().a(new av(-241261, this.u, this));
    }

    public static boolean r() {
        return z.L;
    }

    protected final void g() {
        if (k != null) {
            k.H();
        }
        kq.a().c();
        if (m) {
            dv.a().g();
        }
    }

    public static void a(aq aq2) {
        if (aq2 == null) {
            return;
        }
        com.mg.sq.a.i(aq2.h());
    }

    public static void i(int n2) {
        if (!(z.K && (!z.aa || !z.L))) {
            return;
        }
        switch (n2) {
            case 0: {
                cr.b().a("battle", -1);
                cr.b().a(155141);
                cr.b().e();
                return;
            }
            case 2: {
                cr.b().a("title", 1);
                cr.b().a(250000);
                cr.b().e();
                return;
            }
        }
        cr.b().a("lv1", -1);
        cr.b().a(139051);
        cr.b().e();
    }

    public final void n() {
        this.d = new af();
        this.c = new ag();
    }

    protected final ar a(int n2, int n3, Object[] object) {
        cw.b("Before createScreen(" + n2 + ", " + n3 + ")");
        z.Z = false;
        Object object2 = null;
        switch (n3) {
            case 1: {
                object2 = new ny();
                break;
            }
            case 2: {
                object2 = new nz();
                break;
            }
            case 3: {
                object2 = new nu();
                break;
            }
            case 4: {
                object2 = new oe();
                break;
            }
            case 6: {
                object2 = new nt();
                break;
            }
            case 5: {
                if (object != null && ((Object[])object).length > 0) {
                    object2 = (String)object[0];
                    return new nv((String)object2);
                }
                object2 = new nv();
                break;
            }
            case 7: {
                int n4 = (Integer)object[0];
                object2 = new nw(n4);
                if (((Object[])object).length == 2) {
                    ((nw)object2).a((String)object[1]);
                }
                break;
            }
            case 8: {
                object = (String)object[0];
                object2 = new od((String)object);
                break;
            }
            case 9: {
                object2 = new ob(n2);
                break;
            }
            case 10: {
                if (object == null || ((Object[])object).length <= 0) {
                    return null;
                }
                object2 = (byte[])object[0];
                object = (byte[])object[1];
                object2 = new oc(n2, (byte[])object2, (byte[])object);
            }
        }
        cw.b("After createScreen(" + n2 + ", " + n3 + ")");
        return object2;
    }

    public static void a(bi bi2) {
        cl.a("KS " + gr.e, "8031", bi2);
    }

    public static void b(bi bi2) {
        cl.a("MS " + gr.e, "8031", bi2);
    }

    public static void c(bi bi2) {
        cl.a("XT " + gr.e, "8031", bi2);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void d(int var1_1, int var2_12) {
        super.d(var1_1, var2_12);
        switch (var2_12) {
            case 12357: {
                this.a(false);
                com.mg.sq.a.Q();
                return;
            }
            case 12356: {
                this.a(false);
                com.mg.sq.a.c(null);
                return;
            }
            case 12353: 
            case 12354: {
                MGMIDlet.b("1900588883");
                if (var2_12 != 12353) break;
                this.a(false);
                return;
            }
            case 12352: {
                this.a(false);
                com.mg.sq.a.M();
                return;
            }
            case 12350: {
                this.a(false);
                return;
            }
            case 12349: {
                MGMIDlet.f().d();
                return;
            }
            case 12361: {
                ox.a().c();
                MGMIDlet.f().d();
                return;
            }
            case -241261: {
                MGMIDlet.f().e();
                return;
            }
            case -241260: {
                kq.a().q();
                return;
            }
            case 12345: {
                com.mg.sq.a.t();
                if (this.n == null) break;
                this.n.t();
                this.n = null;
                return;
            }
            case 12347: {
                com.mg.sq.a.V();
                return;
            }
            case 100: {
                kq.a().g();
                ak.b().a(false);
                return;
            }
            case 203: {
                kq.a().m();
                ak.b().a(false);
                return;
            }
            case 102: {
                if (!this.c(-241249)) {
                    return;
                }
                var1_2 = (hc)this.d(-241249);
                var3_15 = (ff)var1_2.e(1);
                var2_13 = var3_15.r().toLowerCase().trim();
                if (l.b(var2_13)) ** GOTO lbl84
                if (var2_13.equals(gr.e)) ** GOTO lbl82
                var3_15 = (ff)var1_2.e(5);
                var8_17 = 0L;
                if (!var3_15.r().equals("")) {
                    var8_17 = Integer.parseInt(var3_15.r()) * 1000;
                }
                if (gr.j.ac) {
                    var1_2 = "B\u1ea1n \u0111ang \u1edf b\u1eadt ch\u1ebf \u0111\u1ed9 ch\u1eb7n khi\u00eau chi\u1ebfn. B\u1ea1n kh\u00f4ng th\u1ec3 chi\u1ebfn \u0111\u1ea5u ngay l\u00fac n\u00e0y. H\u00e3y t\u1eaft ch\u1ebf \u0111\u1ed9 ch\u1eb7n khi\u00eau chi\u1ebfn \u0111\u1ec3 chi\u1ebfn \u0111\u1ea5u n\u00e0o! (H\u01b0\u1edbng d\u1eabn: B\u1ea1n h\u00e3y ch\u1ecdn Menu > H\u1ed7 tr\u1ee3 > C\u00e0i \u0111\u1eb7t)";
                } else if (gr.p > -1L && var8_17 > gr.p) {
                    var1_2 = "V\u01b0\u1ee3t qu\u00e1 s\u1ed1 ti\u1ec1n b\u1ea1n \u0111ang c\u00f3. Vui l\u00f2ng th\u1eed l\u1ea1i!!!";
                } else {
                    if (gr.p < 0L) {
                        var8_17 = 0L;
                    }
                    ak.b().a(false);
                    var3_15 = (ey)var1_2.e(6);
                    var4_19 = (ey)var1_2.e(7);
                    var1_2 = (ey)var1_2.e(10);
                    kq.a().a(var2_13, "Ngon th\u00ec nh\u00e0o v\u00f4 \u0111\u00e2y!", var3_15.a(), var8_17, var4_19.a(), var1_2.a());
                    com.mg.sq.a.f(var2_13);
                    ((ny)this.a).o = var2_13;
                    return;
lbl82:
                    // 1 sources

                    var1_2 = "Nh\u1eadp tr\u00f9ng t\u00ean. Vui l\u00f2ng nh\u1eadp l\u1ea1i!!!";
                }
                ** GOTO lbl85
lbl84:
                // 1 sources

                var1_2 = "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean \u0111\u1ed1i th\u1ee7.";
lbl85:
                // 4 sources

                var1_2 = ak.b().a("Ch\u00fa \u00fd", (String)var1_2, "\u0110\u00f3ng", 199199, 1);
                var1_2.b(199199);
                var1_2.a(this);
                ak.b().a((ap)var1_2, false);
                return;
            }
            case 101: {
                ak.b().e(-241249);
                return;
            }
            case 199199: {
                ak.b().e(199199);
                return;
            }
            case 202: {
                kq.a().g();
                ak.b().e(-241249);
                return;
            }
            case 201: {
                kq.a().m();
                ak.b().e(241214);
                return;
            }
            case 200: {
                var8_18 = (hc)this.d(-241249);
                if (var8_18 == null) {
                    return;
                }
                var2_14 = ((ff)var8_18.e(1)).r().toLowerCase().trim();
                if (!l.b(var2_14)) {
                    if (!var2_14.equals(gr.e)) {
                        ak.b().a(-241249, false);
                        com.mg.sq.a.y(var2_14);
                        kq.a().j(var2_14);
                        return;
                    }
                    var1_3 = ak.b().a("Ch\u00fa \u00fd", "Nh\u1eadp tr\u00f9ng t\u00ean. Vui l\u00f2ng nh\u1eadp l\u1ea1i!!!", "\u0110\u00f3ng", 199199, 1);
                    var1_3.b(199199);
                    var1_3.a(this);
                    ak.b().a(var1_3, false);
                    return;
                }
                var1_4 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean \u0111\u1ed1i th\u1ee7.", "\u0110\u00f3ng", 199199, 1);
                var1_4.b(199199);
                var1_4.a(this);
                ak.b().a(var1_4, false);
                return;
            }
            case 204: {
                this.a(241215, false);
                return;
            }
            case 205: {
                ak.b().a(false);
                if (this.a.h() != 1) break;
                ((ny)this.a).g();
                return;
            }
            case 207: {
                this.a(false);
                return;
            }
            case 208: {
                com.mg.sq.a.V();
                this.a(false);
                return;
            }
            case 209: {
                if (!this.c(241217)) break;
                try {
                    var1_5 = (hc)this.d(241217);
                    var9_21 = ((ff)var1_5.e(2)).r();
                    var3_16 = ((ff)var1_5.e(3)).r();
                    if (var9_21 == null || var9_21.equals("")) {
                        com.mg.sq.a.u("Vui l\u00f2ng nh\u1eadp s\u1ed1 PIN th\u1ebb c\u00e0o");
                        return;
                    }
                    if (var3_16 == null || var3_16.equals("")) {
                        com.mg.sq.a.u("Vui l\u00f2ng nh\u1eadp m\u00e3 s\u1ed1 th\u1ebb c\u00e0o!");
                        return;
                    }
                    var4_20 = (gf)var1_5.e(1);
                    kq.a().a(var3_16, var9_21, com.mg.sq.a.x[var4_20.a()], com.mg.sq.a.w);
                    com.mg.sq.a.a(null, null);
                    return;
                }
                catch (Exception v0) {
                    var9_22 = v0;
                    v0.printStackTrace();
                    return;
                }
            }
            case 300: {
                if (!this.c(0)) break;
                try {
                    var1_6 = (hc)this.d(0);
                    var9_23 = com.mg.sq.a.y[((fw)var1_6.e(1)).a().q()];
                    cl.a("DEPO", com.mg.sq.a.w, var9_23);
                }
                catch (Exception v1) {
                    var9_24 = v1;
                    v1.printStackTrace();
                }
                this.a(0, false);
                return;
            }
            case 301: {
                ak.b().a(false);
                if (com.mg.sq.a.k == null) break;
                var1_7 = com.mg.sq.a.k;
                var1_7.b(null, "rss://ola/0/2011/12/napken");
                var1_7.k(true);
                return;
            }
            case 302: {
                ak.b().a(false);
                com.mg.sq.a.m(this.v);
                return;
            }
            case 12358: {
                this.a(false);
                dv.a().b((short)2412);
                com.mg.sq.a.a(null, null, 10000);
                return;
            }
            case 123523: {
                this.k();
                com.mg.sq.a.W();
                return;
            }
            case 303: {
                var1_8 = (hc)this.d(241221);
                pa.b(((ey)var1_8.e(6)).a());
                ak.b().a(241221, false);
                com.mg.sq.a.M();
                return;
            }
            case 304: {
                var1_9 = (hc)this.d(241221);
                pa.b(((ey)var1_9.e(6)).a());
                ak.b().a(241221, false);
                return;
            }
            case 12359: {
                var1_10 /* !! */  = (hc)this.d(241223);
                if (var1_10 /* !! */  == null) break;
                var9_25 = ((ff)var1_10 /* !! */ .e(1)).r();
                if (!l.b(var9_25)) {
                    dv.a().b(var9_25, null, com.mg.sq.a.q);
                    ak.b().e(241223);
                    var1_10 /* !! */  = ak.b().a("", "G\u1eedi th\u00e0nh c\u00f4ng!", "\u0110\u00f3ng", 12350, 1);
                    var1_10 /* !! */ .a(this);
                    ak.b().a(var1_10 /* !! */ );
                    return;
                }
                var1_10 /* !! */  = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean nick", "\u0110\u00f3ng", 12350, 1);
                var1_10 /* !! */ .a(this);
                ak.b().a(var1_10 /* !! */ );
                return;
            }
            case 12360: {
                var1_11 = (hc)this.d(241223);
                if (var1_11 == null) break;
                var9_26 = new hb();
                var9_26.a(this);
                ak.b().a(var9_26, false);
                return;
            }
            case 305: {
                this.a(false);
            }
        }
    }

    public static void s() {
        com.mg.sq.a.b(true);
    }

    public static void b(boolean bl2) {
        if (bl2) {
            ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u0103ng xu\u1ea5t kh\u00f4ng?", "C\u00f3", 12347, "Kh\u00f4ng", 12350, 1);
            ap2.a(ak.b());
            ak.b().a(ap2, false);
            return;
        }
        com.mg.sq.a.V();
    }

    private static void V() {
        cr.b().d();
        kq.a().b();
        gr.a();
        ny.c = null;
        ny.k = null;
        ny.l = null;
        ok.i = null;
        ok.k = 8;
        ok.j = 0;
        m = false;
        op.v();
        o = null;
        if (k != null) {
            k.G();
        }
        ox.a().d();
        ak.b().k();
        if (z.X) {
            com.mg.sq.a.a(null, null);
            ak.b().f(2);
            return;
        }
        MGMIDlet.f().d();
    }

    public final void m() {
        com.mg.sq.a.t();
        int n2 = 12350;
        if (this.a != null && this.a.h() == 1) {
            if (this.d(-241249) != null) {
                this.a(-241249, false);
                n2 = 100;
            }
            if (this.d(241214) != null) {
                kq.a().m();
                this.a(241214, false);
                n2 = 203;
            }
            ((ny)this.a).v();
        }
        com.mg.sq.a.c("H\u1ebft th\u1eddi gian th\u1ef1c thi.", n2);
    }

    public static void t() {
        ak.b().a(11111, false);
    }

    public static void a(String string) {
        com.mg.sq.a.c(string, 12350);
    }

    private static void c(String object, int n2) {
        object = ak.b().a("Th\u00f4ng tin", (String)object, "\u0110\u00f3ng", n2, "H\u1ed7 tr\u1ee3", 12353, 1);
        ((aq)object).a((a)ak.b());
        ak.b().a((ap)object, false);
    }

    public static ap a(String string, ik ik2) {
        return com.mg.sq.a.a(string, ik2, ap.a);
    }

    public static ap a(String object, ik ik2, int n2) {
        ((a)ak.b()).n = ik2;
        object = new ic((String)object);
        ((aq)object).b(11111);
        ((ap)object).a(n2);
        ak.b().a((ap)object);
        return object;
    }

    public static void b(String string, ik ik2, int n2) {
        com.mg.sq.a.a(null, null, n2);
    }

    public static ap a(lf object, int n2, int n3, bj bj2, long l2, String string, boolean bl2, boolean bl3) {
        try {
            ((lf)object).Z = l2;
            id id2 = new id((lf)object, string, bl2, bl3);
            ak.b().a(id2, false);
            return id2;
        }
        catch (Exception exception) {
            object = ak.b().a("", String.valueOf(((lb)object).b) + " mu\u1ed1n khi\u00eau chi\u1ebfn b\u1ea1n. B\u1ea1n \u0111\u1ed3ng \u00fd kh\u00f4ng?", "\u0110\u1ed3ng \u00fd", n2, "Kh\u00f4ng", n3, 1);
            ((aq)object).b(-241209);
            ((aq)object).a(bj2);
            ak.b().a((ap)object, false);
            return object;
        }
    }

    public static ap j(int n2) {
        cw.a("showDialogErrorCode " + n2);
        MGMIDlet.f().e();
        int n3 = 12349;
        String string = "Tho\u00e1t";
        String string2 = "L\u1ed7i \u1ee9ng d\u1ee5ng s\u1ed1: " + n2;
        if (n2 == 3) {
            n3 = 12361;
            string2 = "L\u1ed7i \u1ee9ng d\u1ee5ng s\u1ed1: " + n2 + ". M\u00e1y kh\u00f4ng \u0111\u1ee7 b\u1ed9 nh\u1edb. Vui l\u00f2ng x\u00f3a b\u1edbt \u1ee9ng d\u1ee5ng ho\u1eb7c chuy\u1ec3n game qua b\u1ed9 nh\u1edb m\u00e1y ho\u1eb7c th\u1ebb. Y\u00eau c\u1ea7u b\u1ed9 nh\u1edb tr\u1ed1ng \u00edt nh\u1ea5t 5Mb. M\u1ecdi chi ti\u1ebft xin li\u00ean h\u1ec7 t\u1ed5ng \u0111\u00e0i \u0111\u1ec3 h\u1ed7 tr\u1ee3!";
        }
        ap ap2 = ak.b().a("Th\u00f4ng tin", string2, string, n3, "H\u1ed7 tr\u1ee3", 12354, 1);
        ap2.a((a)ak.b());
        ak.b().a(ap2, false);
        return ap2;
    }

    public final void a(int n2, String object) {
        r.a();
        if (this.b != null && this.b.h() == 241219) {
            ap ap2 = ak.b().a("Th\u00f4ng tin", (String)object, "\u0110\u00f3ng", 12350, 1);
            ap2.a((a)ak.b());
            ak.b().a(ap2, false);
            return;
        }
        if (this.a != null && this.a.h() != 2 && !this.c(241222)) {
            this.a(false);
        }
        if (n2 == 13) {
            if (this.a instanceof ny) {
                String string = object;
                ny ny2 = (ny)this.a;
                object = ak.b().a("", String.valueOf(ny2.o) + " \u0111ang \u0111\u00e1nh nhau v\u1edbi " + string + ". Mu\u1ed1n xem tr\u1eadn \u0111\u00e1nh kh\u00f4ng?", "Xem", 121, "\u0110\u00f3ng", 12, 1);
                ((aq)object).a(ny2);
                ak.b().a((ap)object, false);
            }
            return;
        }
        if (n2 == 35) {
            ap ap3 = ak.b().a("Ch\u00fa \u00fd", "T\u00e0i kho\u1ea3n ch\u01b0a \u0111\u01b0\u1ee3c x\u00e1c th\u01b0c. Vui l\u00f2ng x\u00e1c th\u1ef1c, \u0111\u1ec3 s\u1eed d\u1ee5ng c\u00e1c t\u00ednh n\u0103ng n\u00e0y!", "X\u00e1c th\u1ef1c", 123523, "\u0110\u00f3ng", 12350, 1);
            ap3.a((a)ak.b());
            ak.b().a(ap3, false);
            return;
        }
        if (n2 == 12) {
            ap ap4 = ak.b().a("Ch\u00fa \u00fd", (String)(object == null ? "R\u01b0\u01a1ng \u0111\u00e3 \u0111\u1ea7y. Vui l\u00f2ng b\u1ecf b\u1edbt \u0111\u1ed3 ra ho\u1eb7c mua th\u00eam ng\u0103n ch\u1ee9a" : object), "\u0110\u00f3ng", 305, 1);
            ap4.a((a)ak.b());
            ak.b().a(ap4, false);
            return;
        }
        object = object == null ? " - L\u1ed7i kh\u00f4ng x\u00e1c \u0111\u1ecbnh." : object;
        this.a(11111, false);
        int n3 = 12350;
        String string = "\u0110\u00f3ng";
        int n4 = 12353;
        String string2 = "H\u1ed7 tr\u1ee3";
        if (n2 == 18) {
            n3 = 12352;
            string = "N\u1ea1p KEN";
            n4 = 12350;
            string2 = "\u0110\u00f3ng";
        } else if (n2 == 1 || n2 == 2) {
            if (this.a != null && this.a.h() == 5 && (n2 = !((nv)this.a).e() && n2 != 2 ? 0 : 1) != 0) {
                object = "Kh\u00f4ng th\u1ec3 k\u1ebft n\u1ed1i t\u1edbi m\u00e1y ch\u1ee7. Vui l\u00f2ng ki\u1ec3m tra c\u1ea5u h\u00ecnh v\u00e0 k\u1ebft n\u1ed1i GPRS";
                n3 = 12349;
                string = "Tho\u00e1t";
                n4 = 12354;
            }
        } else if (n2 == 11) {
            ((ny)this.a).x();
        } else if (n2 == 0 && this.a != null) {
            if (this.a.h() == 8) {
                ((od)this.a).f();
            } else if (this.a.h() == 1) {
                ny ny3 = (ny)this.a;
                if (ny3.n != null) {
                    oj oj2 = ny3.n;
                    if (oj2.o != null && oj2.o.b() == 1) {
                        ((ok)oj2.o).u();
                    }
                }
                ak.b().k();
            }
        }
        ap ap5 = ak.b().a("Th\u00f4ng tin", (String)object, string, n3, string2, n4, 1);
        ap5.a((a)ak.b());
        ak.b().a(ap5, false);
    }

    public final void u() {
        ap ap2 = ak.b().a(" ", "T\u00e0i kho\u1ea3n c\u1ee7a b\u1ea1n v\u1eeba \u0111\u01b0\u1ee3c \u0111\u0103ng nh\u1eadp \u1edf m\u1ed9t n\u01a1i kh\u00e1c.", "\u0110\u00f3ng", 12347, 1);
        ap2.a(false);
        ap2.b(241227);
        ap2.a(this);
        ak.b().a(ap2);
    }

    public final void v() {
        if (((a)ak.b()).d(11111) != null) {
            com.mg.sq.a.t();
        }
        com.mg.sq.a.a("M\u00e3 h\u00f3a d\u1eef li\u1ec7u...", null);
    }

    public final void w() {
        if (((a)ak.b()).d(11111) != null) {
            com.mg.sq.a.t();
        }
        com.mg.sq.a.a("Chuy\u1ec3n m\u00e1y ch\u1ee7...", null);
    }

    public final void x() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "M\u1ea5t k\u1ebft n\u1ed1i v\u1edbi m\u00e1y ch\u1ee7!", "\u0110\u00f3ng", 12347, 1);
        ap2.a(this);
        ak.b().a(ap2, false);
    }

    public final void a(String object, dq object2, String object3, long l2) {
        if (this.a != null && this.a.h() == 1) {
            long l3 = l2;
            String string = object3;
            object3 = object2;
            object2 = object;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                long l4 = l3;
                object = ((ny)object).n;
                if (((oj)object).o instanceof or) {
                    ((or)((oj)object).o).b((String)object2, (dq)object3, string, l4);
                }
            }
        }
    }

    public final void b(String object, dq object2, String object3, long l2) {
        if (this.a != null && this.a.h() == 1) {
            long l3 = l2;
            String string = object3;
            object3 = object2;
            object2 = object;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                long l4 = l3;
                object = ((ny)object).n;
                if (((oj)object).o instanceof or) {
                    ((or)((oj)object).o).a((String)object2, (dq)object3, string, l4);
                }
            }
        }
    }

    public final void a(String object, String string) {
        if (this.a != null && this.a.h() == 1) {
            String string2 = string;
            string = object;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                object = ((ny)object).n;
                if (((oj)object).o instanceof or) {
                    ((or)((oj)object).o).a(string, string2);
                }
            }
        }
    }

    public final void y() {
        if (this.a != null && this.a.h() == 1) {
            com.mg.sq.a.t();
            if (ak.b().c(241204)) {
                ((hp)ak.b().d(241204)).a((byte)1);
            }
        }
    }

    public final void a(nq[] nqArray) {
        if (this.a != null && this.a.h() == 1) {
            ((ny)this.a).a(nqArray);
        }
        com.mg.sq.a.t();
    }

    public final void z() {
        if (this.a != null && this.a.h() == 1) {
            ((ny)this.a).f();
        }
        com.mg.sq.a.t();
    }

    public final void a(lf lf2, byte by2) {
        block15: {
            block16: {
                block17: {
                    cw.a("[SQGameboar]============receiveCharacterInfo================ " + by2);
                    if (by2 == 103) {
                        com.mg.sq.a.b(lf2);
                        com.mg.sq.a.t();
                        return;
                    }
                    if (by2 == 1) {
                        gr.j = lf2;
                    } else if (by2 == 101) {
                        if (this.a != null && this.a.h() == 1) {
                            ((ny)this.a).a(lf2, (int)by2);
                        }
                    } else {
                        if (by2 == 99) {
                            com.mg.sq.a.t();
                            gr.j = lf2;
                            com.mg.sq.a.E();
                            return;
                        }
                        if (by2 == 100) {
                            if (this.a != null && this.a.h() == 1) {
                                ((ny)this.a).a(lf2, (int)by2);
                            }
                            return;
                        }
                    }
                    if (this.a == null) break block15;
                    if (this.a.h() == 3) break block16;
                    if (this.a.h() != 1) break block17;
                    if (by2 == 104 || by2 == 106) {
                        com.mg.sq.a.b(lf2);
                        com.mg.sq.a.t();
                        return;
                    }
                    break block15;
                }
                if (this.a.h() != 2) break block15;
                if (gp.k) {
                    nz.d();
                }
            }
            kq.a().b(gr.j.g);
        }
    }

    public final void a(di[] diArray, di[] diArray2, di[] diArray3, di[] diArray4, di[] diArray5, di[] diArray6) {
        if (this.a != null && this.a.h() == 2) {
            if (gp.k) {
                nz.d();
            }
            nu nu2 = new nu();
            nu2.a(diArray, diArray2, diArray3, diArray4, diArray5, diArray6);
            ak.b().a(nu2);
            cr.b().a("lv1", -1);
            cr.b().e();
            com.mg.sq.a.t();
        }
    }

    public final void a(String string, String string2, int n2) {
        ap ap2 = this.d(241222);
        if (ap2 != null) {
            if (gr.e.equals(string)) {
                if (string2.equals("M99")) {
                    ak.b().f(4);
                    return;
                }
                ny.d = n2;
                ny.c = string2;
                ak.b().a(5, new Object[]{string2});
            }
            return;
        }
        if (this.a != null && this.a.h() == 4) {
            ((oe)this.a).a(string, string2, n2);
        }
    }

    public final void a(String object, dq[] object2) {
        if (this.a != null && this.a.h() == 1) {
            dq[] dqArray = object2;
            object2 = object;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                object = ((ny)object).n;
                if (((oj)object).o instanceof or) {
                    ((or)((oj)object).o).a((String)object2, dqArray);
                }
            }
        }
    }

    public final void b(String object, String string) {
        if (this.a != null && this.a.h() == 1) {
            Object object2 = string;
            string = object;
            object = (ny)this.a;
            ak.b().a(false);
            object2 = ak.b().a("", (String)(object2 == null ? String.valueOf(string) + " kh\u00f4ng mu\u1ed1n \u0111\u00e1nh v\u1edbi b\u1ea1n." : object2), "\u0110\u00f3ng", 12, 1);
            ((ny)object).o = string;
            ((aq)object2).a((bj)object);
            ak.b().a((ap)object2, false);
        }
    }

    public final void a(String string, int n2, int n3) {
        if (this.a != null) {
            if (this.a.h() == 4) {
                oe.a(string, n2, n3);
                return;
            }
            if (this.a.h() == 1) {
                ((ny)this.a).a(string, n2, n3);
            }
        }
    }

    public final void a(jm jm2, int[] nArray, int n2, int[] nArray2, int n3) {
        ox.a().a(jm2, nArray, n2, nArray2, n3);
    }

    public final void a(String string, jl[] jlArray) {
        ox.a().a(string, jlArray);
    }

    public final void a(jn[] jnArray, String string) {
        if (this.a != null && this.a.h() == 1) {
            ((ny)this.a).a(jnArray, string);
        }
    }

    public final void b(jn[] jnArray, String string) {
        if (this.a != null && this.a.h() == 1) {
            ((ny)this.a).a(jnArray, string);
        }
    }

    public final void c(jn[] object, String jnArray) {
        if (this.a != null && this.a.h() == 1) {
            jn[] jnArray2 = jnArray;
            jnArray = object;
            object = (ny)this.a;
            if (ny.c.equals(jnArray2)) {
                object = object.n;
                if (object.o instanceof ok) {
                    ((ok)object.o).a(jnArray);
                }
            }
        }
    }

    public final void a(lf lf2, lf lf3, boolean bl2, byte[] byArray, byte[] byArray2, byte[] byArray3, int n2, byte by2, byte by3) {
        lf[] lfArray = new lf[1];
        lf[] lfArray2 = lfArray;
        lfArray[0] = lf2;
        lf[] lfArray3 = new lf[1];
        lf[] lfArray4 = lfArray3;
        lfArray3[0] = lf3;
        hq.k = gr.j.J;
        op.i = lfArray2;
        op.j = lfArray4;
        op.n = by2;
        op.o = by3;
        op.k = byArray;
        op.l = byArray2;
        op.m = byArray3;
        op.p = n2;
        hq.k = gr.j.J;
        hq.l = gr.j.G;
        hq.m = gr.j.H;
        if (this.a != null && this.a.h() == 1) {
            if (by3 == 9) {
                ((ny)this.a).a(lf2, lf3, bl2);
                return;
            }
            ((ny)this.a).a(lf2, lf3, bl2, byArray, byArray2, byArray3, n2, by2, by3);
        }
    }

    public final void a(nq nq2, boolean bl2) {
        if (this.a != null && this.a.h() == 1) {
            if (ak.b().c(241204)) {
                hp hp2 = (hp)ak.b().d(241204);
                hp2.a(nq2);
                if (bl2) {
                    hp2.a((byte)2);
                } else {
                    hp2.a((byte)1);
                }
            }
            com.mg.sq.a.t();
        }
    }

    public final void a(lu[] luArray) {
        gr.o = luArray;
        if (this.a != null) {
            if (this.a.h() == 2) {
                kq.a().b(gr.e);
                return;
            }
            if (this.a.h() == 3) {
                kq.a().b(gr.e);
                return;
            }
            if (this.a.h() == 1) {
                ((ny)this.a).e();
            }
        }
    }

    public final void b(String object) {
        if (this.a != null) {
            if (this.a.h() == 4) {
                oe.a((String)object);
                return;
            }
            if (this.a.h() == 1) {
                Object object2 = object;
                object = (ny)this.a;
                object = ((ny)object).n;
                if (((oj)object).o == null || !(((oj)object).o instanceof ok)) {
                    return;
                }
                com.mg.sq.a.t();
                ((ok)((oj)object).o).e(true);
                ((ok)((oj)object).o).l.a(0);
                ((ok)((oj)object).o).m.a();
                ((ok)((oj)object).o).o = false;
                object2 = ak.b().a("Ch\u00fa \u00fd", (String)object2, "\u0110\u00f3ng", 0, 1);
                ((aq)object2).a((bj)object);
                ak.b().a((ap)object2, false);
            }
        }
    }

    public final void e(int n2, int n3) {
        lk[] lkArray = gr.l;
        int n4 = 0;
        while (n4 < lkArray.length) {
            if (lkArray[n4].a == n2) {
                lkArray[n4].g = n3;
                break;
            }
            ++n4;
        }
        if (ak.b().c(241202)) {
            ((hf)ak.b().d(241202)).j(n2, n3);
        }
        if (com.mg.sq.a.r()) {
            if (this.s == null) {
                this.s = new cs("useitem");
            }
            this.s.b();
        }
    }

    public final void c(String string) {
        this.f.a(new gh(string));
    }

    public final void a(long l2) {
        if (l2 <= 2000L && gr.p > l2) {
            gr.q = true;
        }
        gr.p = l2;
        Object object = new fs(0L);
        ((fs)object).a(l2);
        this.b().a((ax)object);
        if (this.c(241210)) {
            object = (hy)this.d(241210);
            ((hy)object).a(l2);
        }
    }

    public static void A() {
        ap ap2 = ak.b().a("Th\u00f4ng tin", "Phi\u00ean b\u1ea3n hi\u1ec7n t\u1ea1i ch\u01b0a h\u1ed7 tr\u1ee3 t\u00ednh n\u0103ng n\u00e0y!", "\u0110\u00f3ng", 2, 1);
        ap2.a(ak.b());
        ak.b().a(ap2, false);
    }

    public static void d(String object) {
        hc hc2 = new hc();
        bf bf2 = new bf("Nh\u1eadp nick", 157, ca.d);
        bf2.a_(10, 10);
        hc2.a(bf2);
        int n2 = 10 + (bf2.f() + 3);
        ff ff2 = new ff(null, 100, 2);
        ff2.a_(1);
        ff2.a(10, n2, 154, 20);
        ff2.d(true);
        ff2.c((String)object);
        hc2.a(ff2);
        n2 += ff2.f() + 5;
        ff2 = new ff("\u0110\u1eb7t C\u01b0\u1ee3c:", 4, 4);
        ff2.a(10, n2 += ca.d.a(), ca.d.a("99999"), 18);
        ff2.d(false);
        ff2.a_(5);
        hc2.a(ff2);
        bf2 = new bf(".000 KEN", 157, ca.d);
        bf2.a_(10 + ff2.e() + 1, n2 + 2);
        hc2.a(bf2);
        object = new ey("Cho xem", true);
        ((au)object).a(bf2.c() + ca.d.a(".000 KEN0"), ff2.d() + 2, 13, 13);
        ((au)object).d(false);
        ((au)object).a_(6);
        hc2.a((au)object);
        bf2 = new bf("Ki\u1ec3u quy\u1ebft \u0111\u1ea5u:", 157, ca.d);
        bf2.a_(10, n2 += ff2.f() + 5);
        hc2.a(bf2);
        object = new ey("1 chi\u1ec1u", false);
        ((au)object).a(10, n2 += bf2.f() + 3, 13, 13);
        ((au)object).d(false);
        ((au)object).a_(7);
        hc2.a((au)object);
        n2 += ((au)object).f() + 5;
        object = new ey("Kh\u00f4ng ch\u01a1i Tuy\u1ec7t Chi\u00eau", false);
        ((au)object).a(10, n2, 13, 13);
        ((au)object).d(false);
        ((au)object).a_(10);
        hc2.a((au)object);
        n2 += ((au)object).f() + 5;
        object = ex.a("G\u1eedi", 102);
        ((ex)object).a((a)ak.b());
        ((au)object).a_(2);
        int n3 = 87 - ((au)object).e() >> 1;
        ((au)object).a_(n3, n2);
        hc2.a((au)object);
        object = ex.a("H\u1ee7y", 101);
        ((ex)object).a((a)ak.b());
        ((au)object).a_(3);
        n3 = (87 - ((au)object).e() >> 1) + 87;
        ((au)object).a_(n3, n2);
        hc2.a((au)object);
        hc2.a(z.r - 174 >> 1, z.s - (n2 += ((au)object).f() + 10) >> 1, 174, n2);
        hc2.a((a)ak.b());
        hc2.b(-241249);
        bh bh2 = new bh("", 102);
        object = hc2;
        ((aq)object).a(bh2, true);
        bh2 = new bh("", 101);
        object = hc2;
        ((aq)object).b(bh2, true);
        ak.b().a(hc2);
        object = new int[][]{{1, 1, 1, 1}, {1, 2, 1, 2}, {1, 6, 1, 4}, {2, 4, 2, 4}, {1, 6, 2, 6}, {6, 6, 6, 6}, {2, 7, 4, 7}, {6, 8, 6, 8}, {7, 8, 9, 9}, {7, 9, 8, 8}};
        hc2.a((int[][])object);
    }

    public static void e(String object) {
        object = com.mg.sq.a.a((String)object, "Nh\u1eadp nick", "G\u1eedi", 200, "H\u1ee7y", 101);
        ((aq)object).b(-241249);
        ak.b().a((ap)object);
    }

    public static hc a(String object, String object2, String string, int n2, String string2, int n3) {
        hc hc2 = new hc();
        object = new bf((String)object, 158, ca.d);
        ((au)object).a_(5, 10);
        hc2.a((au)object);
        int n4 = 10 + (((au)object).f() + (object2 != null ? ca.d.a() : 0) + 3);
        object2 = new ff((String)object2, 100, 2);
        ((au)object2).a_(1);
        ((ff)object2).a(5, n4, 165, 20);
        ((ff)object2).d(true);
        hc2.a((au)object2);
        n4 += ((au)object2).f() + 5;
        boolean bl2 = ca.d.a(string) >= ca.d.a(string2);
        ex ex2 = ex.a(string, n2);
        if (!bl2) {
            ex2.d(ex.a(string2, n3).e());
        }
        ex2.a_(2);
        int n5 = 87 - ex2.e() >> 1;
        ex2.a_(n5, n4);
        hc2.a(ex2);
        ex2 = ex.a(string2, n3);
        if (bl2) {
            ex2.d(ex.a(string, n2).e());
        }
        ex2.a_(3);
        n5 = (87 - ex2.e() >> 1) + 87;
        ex2.a_(n5, n4);
        hc2.a(ex2);
        n4 += ex2.f() + 10;
        hc2.a(z.r - 175 >> 1, z.s - n4 >> 1, 175, n4);
        hc2.a((a)ak.b());
        hc2.b(241220);
        bh bh2 = new bh("", n2);
        hc hc3 = hc2;
        ((aq)hc3).a(bh2, true);
        bh2 = new bh("", n3);
        hc3 = hc2;
        ((aq)hc3).b(bh2, true);
        return hc2;
    }

    public static hc a(String object, String object2, String object3, int n2, String string, int n3, int n4, bj bj2) {
        object2 = new hc();
        object = new bf((String)object, 158, ca.d);
        ((au)object).a_(5, 10);
        ((hc)object2).a((au)object);
        int n5 = 10 + (((au)object).f() + 3);
        Object object4 = new hn(1);
        ff ff2 = new ff(null, 300, 2);
        ff2.a((hn)object4);
        ff2.f(true);
        ff2.a_(1);
        ff2.a(5, n5, 165, 20);
        ff2.d(true);
        ((hc)object2).a(ff2);
        n5 += ff2.f() + 5;
        n5 += 2;
        if (ov.f != null) {
            ft ft2 = new ft(null, n4);
            ft2.a((175 - ov.e) / 2, n5, ov.e, ov.e);
            ft2.a(bj2);
            ft2.a_(4);
            ((hc)object2).a(ft2);
            n5 += ft2.f() + 5;
        }
        n4 = ca.d.a((String)object3) >= ca.d.a(string) ? 1 : 0;
        object4 = ex.a((String)object3, n2);
        ((ex)object4).a(bj2);
        if (n4 == 0) {
            ((au)object4).d(ex.a(string, n3).e());
        }
        ((au)object4).a_(2);
        int n6 = 87 - ((au)object4).e() >> 1;
        ((au)object4).a_(n6, n5);
        ((hc)object2).a((au)object4);
        object4 = ex.a(string, n3);
        ((ex)object4).a(bj2);
        if (n4 != 0) {
            ((au)object4).d(ex.a((String)object3, n2).e());
        }
        ((au)object4).a_(3);
        n6 = (87 - ((au)object4).e() >> 1) + 87;
        ((au)object4).a_(n6, n5);
        ((hc)object2).a((au)object4);
        n5 += ((au)object4).f() + 10;
        ff2.e(true);
        ((ap)object2).a(z.r - 175 >> 1, z.s - n5 >> 1, 175, n5);
        ((aq)object2).a(bj2);
        ((aq)object2).b(241220);
        object3 = new bh("", n2);
        Object object5 = object2;
        ((aq)object5).a((bd)object3, true);
        object3 = new bh("", n3);
        object5 = object2;
        ((aq)object5).b((bd)object3, true);
        return object2;
    }

    public static void B() {
        String[] stringArray = new String[]{"- Thanh ki\u1ebfm \u0111\u1ec3 t\u1ea5n c\u00f4ng \u0111\u1ed1i ph\u01b0\u01a1ng.", "- Tr\u00e1i tim c\u00f3 t\u00e1c d\u1ee5ng h\u1ed3i sinh l\u1ef1c trong tr\u1eadn chi\u1ebfn.", "- Xo\u00e1y \u00e2m d\u01b0\u01a1ng gi\u00fap ph\u1ee5c h\u1ed3i n\u1ed9i l\u1ef1c trong tr\u1eadn \u0111\u1ea5u \u0111\u1ec3 s\u1eed d\u1ee5ng tuy\u1ec7t chi\u00eau.", "- Tr\u00e1i \u0111\u00e0o gi\u00fap ph\u1ee5c h\u1ed3i s\u1ee9c m\u1ea1nh trong tr\u1eadn chi\u1ebfn \u0111\u1ec3 nh\u00e2n \u0111\u00f4i l\u1ef1c t\u1ea5n c\u00f4ng cho m\u1ed9t l\u01b0\u1ee3t.", "- Gi\u1ecdt n\u01b0\u1edbc c\u00f3 t\u00e1c d\u1ee5ng t\u0103ng \u0111i\u1ec3m kinh nghi\u1ec7m \u0111\u1ec3 l\u00ean c\u1ea5p, nh\u01b0ng \u0111i\u1ec3m ch\u1ec9 b\u1eb1ng 1/2 ng\u00f4i sao.", "- Ng\u00f4i sao c\u00f3 t\u00e1c d\u1ee5ng t\u0103ng \u0111i\u1ec3m kinh nghi\u1ec7m \u0111\u1ec3 l\u00ean c\u1ea5p.", "- Th\u1ecfi v\u00e0ng \u0111\u1ec3 \u0111\u1ed5i KEN, khi \u0111\u1ee7 10.000 l\u01b0\u1ee3ng v\u00e0ng h\u1ec7 th\u1ed1ng s\u1ebd t\u1ef1 \u0111\u1ed9ng n\u1ea1p v\u00e0o t\u00e0i kho\u1ea3n c\u1ee7a nh\u00e2n v\u1eadt 10.000 KEN.", "", "- Ki\u1ebfm l\u1eeda c\u00f3 l\u1ef1c t\u1ea5n c\u00f4ng m\u1ea1nh h\u01a1n v\u00e0 l\u00e0m n\u1ed5 c\u00e1c bi\u1ec3u t\u01b0\u1ee3ng bao quanh n\u00f3. Ki\u1ebfm l\u1eeda c\u00f3 th\u1ec3 x\u1ebfp c\u00f9ng v\u1edbi ki\u1ebfm th\u01b0\u1eddng."};
        hc hc2 = new hc();
        hc2.a(new be());
        hc2.g(hc2.j() - be.a);
        au au2 = new gb("H\u01b0\u1edbng d\u1eabn", hc2.i() - 20, ca.d);
        ((gb)au2).e(true);
        au2.a_(10, 10);
        ((gb)au2).h(1);
        hc2.a(au2);
        au2 = new bc(0);
        au2.a(3, 32, hc2.i() - 6, hc2.j() - 35);
        ba ba2 = new ba();
        ba2.a((a)ak.b());
        ba2.e(true);
        ((bc)au2).b(ba2);
        gm gm2 = null;
        Image[] imageArray = new Image[stringArray.length];
        int n2 = 0;
        while (n2 < imageArray.length) {
            if (n2 != 7) {
                imageArray[n2] = f.d("/chess" + n2);
                gm2 = new gm(imageArray[n2], stringArray[n2], au2.e());
                ba2.a((Object)gm2);
            }
            ++n2;
        }
        hc2.a(au2);
        hc2.a((a)ak.b());
        hc2.a(new bh("\u0110\u00f3ng", 12350));
        ak.b().a(hc2, false);
    }

    public static void a(String[] stringArray) {
        hc hc2 = new hc();
        hc2.a(new be());
        hc2.g(hc2.j() - be.a);
        au au2 = new gb("Nh\u1eefng t\u00ednh n\u0103ng m\u1edbi:", hc2.i() - 20, ca.d);
        ((gb)au2).e(true);
        au2.a_(10, 10);
        ((gb)au2).h(1);
        hc2.a(au2);
        au2 = new gb("Phi\u00ean B\u1ea3n 0.17.0", hc2.i() - 20, ca.d);
        ((gb)au2).e(true);
        au2.a_(10, 28);
        ((gb)au2).h(1);
        hc2.a(au2);
        au2 = new bc(0);
        au2.a(3, 50, hc2.i() - 6, hc2.j() - 50 - 2);
        ba ba2 = new ba();
        ba2.a((a)ak.b());
        ba2.e(true);
        ((bc)au2).b(ba2);
        gm gm2 = null;
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (n2 != 8) {
                gm2 = new gm(null, stringArray[n2], au2.e());
                ba2.a((Object)gm2);
            }
            ++n2;
        }
        hc2.a(au2);
        hc2.a((a)ak.b());
        hc2.a(new bh("\u0110\u00f3ng", 12350));
        ak.b().a(hc2, false);
    }

    public static void b(String[] stringArray) {
        hc hc2 = new hc();
        hc2.a(new be());
        hc2.g(hc2.j() - be.a);
        au au2 = new gb("Th\u00f4ng b\u00e1o:", hc2.i() - 20, ca.d);
        ((gb)au2).e(true);
        au2.a_(10, 10);
        ((gb)au2).h(1);
        hc2.a(au2);
        au2 = new bc(0);
        au2.a(3, 28, hc2.i() - 6, hc2.j() - 28 - 2);
        ba ba2 = new ba();
        ba2.a((a)ak.b());
        ba2.e(true);
        ((bc)au2).b(ba2);
        gm gm2 = null;
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (n2 != 8) {
                gm2 = new gm(null, stringArray[n2], au2.e());
                ba2.a((Object)gm2);
            }
            ++n2;
        }
        hc2.a(au2);
        hc2.a((a)ak.b());
        hc2.a(new bh("\u0110\u00f3ng", 12350));
        ak.b().a(hc2, false);
    }

    public static String k(int n2) {
        hc hc2 = (hc)((a)ak.b()).d(n2);
        if (hc2 == null) {
            return "";
        }
        return ((ff)hc2.e(1)).r().toLowerCase().trim();
    }

    public final void a(lf lf2, String string, long l2, String string2, boolean bl2, boolean bl3) {
        if (this.a != null && this.a.h() == 1) {
            r.a(string2);
            ((ny)this.a).a(lf2, string, l2, string2, bl2, bl3);
            return;
        }
        kq.a().a(false, string2, "Tui ch\u01b0a s\u1eb5n s\u00e0ng!!!");
    }

    public static void a(boolean bl2, String string) {
        id id2 = (id)((a)ak.b()).d(-241209);
        String string2 = "";
        if (id2 != null) {
            string2 = id2.t();
            ((a)ak.b()).b(id2, false);
        }
        if (bl2) {
            int n2 = r.d() - 1;
            while (n2 >= 0) {
                if (string2.equals(r.b(n2))) {
                    kq.a().a(bl2, string2, string);
                } else {
                    kq.a().a(false, (String)r.b(n2), string);
                }
                --n2;
            }
            r.a();
            ((a)ak.b()).k();
            com.mg.sq.a.a(null, null);
            return;
        }
        kq.a().a(bl2, string2, string);
        r.a(r.d() - 1);
    }

    public static ap f(String object) {
        object = new ic("\u0110ang khi\u00eau chi\u1ebfn \"" + (String)object + "\". Vui l\u00f2ng ch\u1edd...");
        ((aq)object).a(ak.b());
        ((aq)object).a(new be());
        bh bh2 = new bh("H\u1ee7y", 202);
        Object object2 = object;
        ((aq)object2).b(bh2, true);
        ((aq)object).b(-241249);
        ((ic)object).j(true);
        ((ic)object).e(30);
        ak.b().a((ap)object, false);
        return object;
    }

    private static ap y(String object) {
        object = new ic("\u0110ang m\u1eddi \"" + (String)object + "\" Giao d\u1ecbch. Vui l\u00f2ng ch\u1edd...");
        ((aq)object).a(ak.b());
        ((aq)object).a(new be());
        bh bh2 = new bh("H\u1ee7y", 201);
        Object object2 = object;
        ((aq)object2).b(bh2, true);
        ((aq)object).b(241214);
        ((ic)object).j(true);
        ((ic)object).e(30);
        ak.b().a((ap)object, false);
        return object;
    }

    public final ap a(String object, String object2, String[] stringArray, int[] nArray, int n2) {
        try {
            n2 = z.r;
            int n3 = z.s;
            if (n2 > n3) {
                if (n2 >= 320) {
                    n2 = 320;
                }
            } else if (n2 >= 240) {
                n2 = 240;
            }
            n2 -= 20;
            n3 = 6;
            hc hc2 = new hc();
            if (object != null && !((String)object).equals("")) {
                object = new gb((String)object, n2 - 10 - 10, ca.d);
                ((gb)object).e(true);
                ((au)object).a_(10, 6);
                ((gb)object).h(1);
                hc2.a((au)object);
                n3 = 6 + (((gb)object).f() + 6);
            }
            object = new gb((String)object2, n2 - 10 - 10, ca.d);
            ((au)object).a_(10, n3);
            ((gb)object).h(1);
            hc2.a((au)object);
            n3 += ((gb)object).f() + 6;
            if (stringArray != null && nArray != null) {
                int n4 = n2 / stringArray.length;
                object2 = ex.a(stringArray[0], nArray[0]);
                ((au)object2).a_(2);
                int n5 = n4 - ((au)object2).e() >> 1;
                ((au)object2).a_(n5, n3);
                ((au)object2).d(true);
                bh bh2 = new bh("", nArray[0]);
                hc hc3 = hc2;
                ((aq)hc3).a(bh2, true);
                hc2.a((au)object2);
                if (nArray.length > 1) {
                    int n6 = ca.d.a(stringArray[0]) >= ca.d.a(stringArray[1]) ? 1 : 0;
                    if (n6 == 0) {
                        ((au)object2).d(ex.a(stringArray[1], nArray[1]).e());
                        ((au)object2).a_(n4 - ((au)object2).e() >> 1, n3);
                    }
                    object2 = ex.a(stringArray[1], nArray[1]);
                    if (n6 != 0) {
                        ((au)object2).d(ex.a(stringArray[0], nArray[0]).e());
                    }
                    ((au)object2).a_(3);
                    n6 = (n4 - ((au)object2).e() >> 1) + n4;
                    ((au)object2).a_(n6, n3);
                    hc2.a((au)object2);
                    bh2 = new bh("", nArray[1]);
                    hc hc4 = hc2;
                    ((aq)hc4).b(bh2, true);
                }
                n3 += ((au)object2).f();
            }
            hc2.a(z.r - n2 >> 1, z.s - (n3 += 10) >> 1, n2, n3);
            return hc2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static void g(String object) {
        int n2 = z.r;
        int n3 = z.s;
        if (n2 > n3) {
            if (n2 >= 320) {
                n2 = 320;
            }
        } else if (n2 >= 240) {
            n2 = 240;
        }
        hc hc2 = new hc();
        au au2 = new gb("Ch\u00fa \u00fd", (n2 -= 20) - 10 - 10, ca.d);
        ((gb)au2).e(true);
        au2.a_(10, 6);
        ((gb)au2).h(1);
        hc2.a(au2);
        n3 = 6 + (((gb)au2).f() + 6);
        au2 = new gb((String)object, n2 - 10 - 10, ca.d);
        au2.a_(10, n3);
        ((gb)au2).h(1);
        hc2.a(au2);
        object = new ey("Kh\u00f4ng nh\u1eafc n\u1eefa", false);
        ((au)object).a_(6);
        ((au)object).a(16, n3 += ((gb)au2).f() + 6, 13, 13);
        hc2.a((au)object);
        n3 += ((au)object).f() + 6;
        int n4 = n2 / 2;
        au2 = ex.a("C\u00f3", 303);
        au2.d(ex.a("Kh\u00f4ng", 304).e());
        au2.a_(2);
        int n5 = n4 - au2.e() >> 1;
        au2.a_(n5, n3);
        au2.d(true);
        bh bh2 = new bh("", 303);
        hc hc3 = hc2;
        ((aq)hc3).a(bh2, true);
        hc2.a(au2);
        au2 = ex.a("Kh\u00f4ng", 304);
        au2.a_(3);
        int n6 = (n4 - au2.e() >> 1) + n4;
        au2.a_(n6, n3);
        hc2.a(au2);
        bh2 = new bh("", 304);
        hc hc4 = hc2;
        ((aq)hc4).b(bh2, true);
        int n7 = au2.e();
        au2 = (ex)hc2.e(2);
        au2.d(n7);
        au2.b_(n4 - au2.e() >> 1);
        n3 += au2.f();
        hc2.a(z.r - n2 >> 1, z.s - (n3 += 10) >> 1, n2, n3);
        hc2.a((a)ak.b());
        hc2.b(241221);
        ak.b().a(hc2, false);
    }

    public static void C() {
        ap ap2 = ak.b().a("Th\u00f4ng tin", "\u0110\u00e3 g\u1eedi Me th\u00e0nh c\u00f4ng", "\u0110\u00f3ng", 2, 1);
        ap2.a(ak.b());
        ak.b().a(ap2, false);
    }

    private static void b(lf object) {
        object = new id((lf)object, 1, false, false);
        ak.b().a((ap)object, false);
    }

    public final void a(boolean bl2, String string, int n2, int[] nArray, int n3) {
        if (this.a != null && this.a.h() == 5) {
            ((nv)this.a).a(bl2, string, n2, nArray, n3);
        }
    }

    public final void a(int n2, String[] stringArray) {
        if (stringArray != null && stringArray.length > 0) {
            pa.h(n2);
            j = stringArray;
        }
    }

    public static String a(String string, int n2) {
        return com.mg.sq.a.a(string, g, n2);
    }

    public static String a(String object, d d2, int n2) {
        if (d2.a((String)object) > n2) {
            int n3 = 0;
            object = ((String)object).toCharArray();
            int n4 = 0;
            while (n4 < ((Object)object).length) {
                if ((n3 += d2.a((char)object[n4])) >= n2) break;
                ++n4;
            }
            if (n4 >= ((Object)object).length) {
                n4 = ((Object)object).length - 1;
            }
            int n5 = n4 - 3;
            while (n5 <= n4) {
                object[n5] = 46;
                ++n5;
            }
            object = String.valueOf((char[])object, 0, n4);
        }
        return object;
    }

    public static void D() {
        ak.b().a(9, true, false, null, null);
    }

    public final void a(lj[] ljArray, lk[] lkArray, int n2, int n3) {
        gr.k = ljArray;
        gr.l = lkArray;
        if (n2 > 0) {
            gr.m = n2;
        }
        gr.n = n3;
        if (this.a.h() == 2) {
            ((nz)this.a).e();
        } else if (this.a.h() == 3) {
            ((nu)this.a).d();
        }
        com.mg.sq.a.t();
    }

    public static void E() {
        gw gw2 = new gw();
        ak.b().a(gw2);
    }

    public static ap a(lj object, bj object2, String object3, int n2, String string, int n3) {
        object = new he((lj)object);
        if (object2 != null) {
            ((aq)object).a((bj)object2);
            object3 = new bh((String)object3, n2);
            object2 = object;
            ((aq)object2).a((bd)object3, true);
            object3 = new bh(string, n3);
            object2 = object;
            ((aq)object2).b((bd)object3, true);
        }
        ak.b().a((ap)object, false);
        return object;
    }

    public static void a(bj bj2, String object, int n2, String string, int n3) {
        object = ak.b().a("Ch\u00fa \u00fd", "Th\u00f9ng \u0111\u1ed3 \u0111\u00e3 \u0111\u1ea7y. B\u1ea1n c\u00f3 ph\u1ea3i b\u1ecf b\u1edbt \u0111\u1ed3 \u0111i!", (String)object, n2, string, n3, 1);
        ((aq)object).a(bj2);
        ((aq)object).b(241209);
        ak.b().a((ap)object, false);
    }

    public static String[] a(lj stringArray) {
        String[] stringArray2 = new String[10];
        int n2 = 0;
        if (stringArray.p.a > 0) {
            stringArray2[0] = "+ " + stringArray.p.a + " c\u01b0\u1eddng l\u1ef1c";
            ++n2;
        } else if (stringArray.p.a < 0) {
            stringArray2[0] = "- " + stringArray.p.a + " c\u01b0\u1eddng l\u1ef1c";
            ++n2;
        }
        if (stringArray.p.c > 0) {
            stringArray2[n2] = "+ " + stringArray.p.c + " n\u1ed9i l\u1ef1c";
            ++n2;
        } else if (stringArray.p.c < 0) {
            stringArray2[n2] = "- " + stringArray.p.c + " n\u1ed9i l\u1ef1c";
            ++n2;
        }
        if (stringArray.p.b > 0) {
            stringArray2[n2] = "+ " + stringArray.p.b + " th\u00e2n ph\u00e1p";
            ++n2;
        } else if (stringArray.p.b < 0) {
            stringArray2[n2] = "- " + stringArray.p.b + " th\u00e2n ph\u00e1p";
            ++n2;
        }
        if (stringArray.p.d > 0) {
            stringArray2[n2] = "+ " + stringArray.p.d + " th\u1ec3 l\u1ef1c";
            ++n2;
        } else if (stringArray.p.d < 0) {
            stringArray2[n2] = "- " + stringArray.p.d + " th\u1ec3 l\u1ef1c";
            ++n2;
        }
        if (stringArray.p.e > 0) {
            stringArray2[n2] = "+ " + stringArray.p.e + " s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        } else if (stringArray.p.e < 0) {
            stringArray2[n2] = "- " + stringArray.p.e + " s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        }
        if (stringArray.p.f > 0) {
            stringArray2[n2] = "+ " + stringArray.p.f + " ph\u00f2ng th\u1ee7";
            ++n2;
        } else if (stringArray.p.f < 0) {
            stringArray2[n2] = "- " + stringArray.p.f + " ph\u00f2ng th\u1ee7";
            ++n2;
        }
        if (stringArray.p.h > 0) {
            stringArray2[n2] = "+ " + stringArray.p.h + " n\u00e9 tr\u00e1nh";
            ++n2;
        } else if (stringArray.p.h < 0) {
            stringArray2[n2] = "- " + stringArray.p.h + " n\u00e9 tr\u00e1nh";
            ++n2;
        }
        if (stringArray.p.g > 0) {
            stringArray2[n2] = "+ " + stringArray.p.g + "% ch\u00ed m\u1ea1ng";
            ++n2;
        } else if (stringArray.p.g < 0) {
            stringArray2[n2] = "- " + stringArray.p.g + "% ch\u00ed m\u1ea1ng";
            ++n2;
        }
        if (stringArray.p.i > 0) {
            stringArray2[n2] = "+ " + stringArray.p.i + " sinh l\u1ef1c";
            ++n2;
        } else if (stringArray.p.i < 0) {
            stringArray2[n2] = "- " + stringArray.p.i + " sinh l\u1ef1c";
            ++n2;
        }
        if (stringArray.p.j > 0) {
            stringArray2[n2] = "+ " + stringArray.p.j + "% h\u1ea5p thu s\u00e1t th\u01b0\u01a1ng";
            ++n2;
        } else if (stringArray.p.j < 0) {
            stringArray2[n2] = "- " + stringArray.p.j + "% h\u1ea5p thu s\u00e1t th\u01b0\u01a1ng";
            ++n2;
        }
        if (stringArray.p.k > 0) {
            stringArray2[n2] = "+ " + stringArray.p.k + "% \u0111\u00e1nh xuy\u00ean gi\u00e1p";
            ++n2;
        } else if (stringArray.p.k < 0) {
            stringArray2[n2] = "- " + stringArray.p.k + "% \u0111\u00e1nh xuy\u00ean gi\u00e1p";
            ++n2;
        }
        if (stringArray.p.l > 0) {
            stringArray2[n2] = "+ " + stringArray.p.l + "% c\u1ea3n \u0111\u00f2n";
            ++n2;
        } else if (stringArray.p.l < 0) {
            stringArray2[n2] = "- " + stringArray.p.l + "% c\u1ea3n \u0111\u00f2n";
            ++n2;
        }
        if (stringArray.p.m > 0) {
            stringArray2[n2] = "+ " + stringArray.p.m + "% h\u1ed3i sinh";
            ++n2;
        } else if (stringArray.p.m < 0) {
            stringArray2[n2] = "- " + stringArray.p.m + "% h\u1ed3i sinh";
            ++n2;
        }
        if (stringArray.p.n > 0) {
            stringArray2[n2] = "+ " + stringArray.p.n + "% s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        } else if (stringArray.p.n < 0) {
            stringArray2[n2] = "- " + stringArray.p.n + "% s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        }
        if (stringArray.p.o > 0) {
            stringArray2[n2] = "+ " + stringArray.p.o + "% sinh l\u1ef1c";
            ++n2;
        } else if (stringArray.p.o < 0) {
            stringArray2[n2] = "- " + stringArray.p.o + "% sinh l\u1ef1c";
            ++n2;
        }
        stringArray = new String[n2];
        System.arraycopy(stringArray2, 0, stringArray, 0, n2);
        return stringArray;
    }

    public final void b(String object, int n2, int n3) {
        lj lj2 = null;
        if (gr.k != null) {
            int n4 = 0;
            while (n4 < gr.k.length) {
                if (gr.k[n4].b.equals(object)) {
                    gr.k[n4].n = gr.k[n4].o;
                    lj2 = gr.k[n4];
                    break;
                }
                ++n4;
            }
            n4 = 0;
            while (n4 < gr.l.length) {
                if (gr.l[n4].a == n2) {
                    gr.l[n4].g = n3;
                    break;
                }
                ++n4;
            }
        }
        if (this.a != null && this.a.h() == 1) {
            object = (ny)this.a;
            ((ny)this.a).a = false;
            if (((ny)object).n != null) {
                ((ny)object).n.r = false;
            }
        }
        if (this.b != null && this.b.h() == 241202) {
            ((hf)this.b).a(lj2);
            ((hf)this.b).j(n3);
        }
    }

    public static hj a(bj bj2, bd bd2, bd bd3, bd bd4, lj object) {
        Object object2;
        lk[] lkArray = new lk[gr.l.length];
        int n2 = 0;
        int n3 = 0;
        while (n3 < gr.l.length) {
            if (gr.l[n3].e == 1) {
                lkArray[n2] = gr.l[n3];
                ++n2;
            }
            ++n3;
        }
        lk[] lkArray2 = new lk[n2];
        System.arraycopy(lkArray, 0, lkArray2, 0, n2);
        int n4 = 1;
        int n5 = lkArray2.length;
        while (n4 < n5) {
            n2 = n4;
            while (n2 > 0 && lkArray2[n2 - 1].f > lkArray2[n2].f) {
                object2 = lkArray2[n2];
                lkArray2[n2] = lkArray2[n2 - 1];
                lkArray2[n2 - 1] = object2;
                --n2;
            }
            ++n4;
        }
        object2 = new hj("B\u00faa s\u1eeda ch\u1eefa: ", lkArray2);
        ((hj)object2).a((lj)object);
        bd bd5 = bd2;
        object = object2;
        ((aq)object).a(bd5, true);
        ((hj)object2).b(bd2);
        bd5 = bd3;
        object = object2;
        ((aq)object).b(bd5, true);
        ((hj)object2).a(bd4);
        ((aq)object2).a(bj2);
        ak.b().a((ap)object2);
        return object2;
    }

    public static void F() {
        kq.a().l();
        com.mg.sq.a.a(null, null);
    }

    public static void G() {
        kq.a().k();
        com.mg.sq.a.a(null, null);
    }

    public static void H() {
        kq.a().x();
        com.mg.sq.a.a(null, null);
    }

    public static void h(String string) {
        kq.a().r(string);
        com.mg.sq.a.a(null, null);
    }

    public final void a(ld[] object) {
        if (this.c(241210)) {
            this.e(241210);
        }
        object = new hy((ld[])object);
        ak.b().a((ap)object, false);
        com.mg.sq.a.t();
    }

    public final void a(int n2, lo[] loArray) {
        ap ap2 = this.d(241210);
        if (ap2 != null) {
            ((hy)ap2).a(n2, loArray);
        }
    }

    public final void a(String[] stringArray, int[] nArray) {
        if (this.c(241210)) {
            hy hy2 = (hy)this.d(241210);
            hy2.a(stringArray, nArray);
        }
    }

    public final void a(int[] nArray, int[] nArray2) {
        if (this.c(241210)) {
            hy hy2 = (hy)this.d(241210);
            hy2.a(nArray, nArray2);
        }
    }

    public final void i(String string) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).c(string);
        }
        com.mg.sq.a.t();
    }

    public final void j(String string) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).a(string);
        }
    }

    public final void a(lk lk2) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).a(lk2);
        }
        com.mg.sq.a.t();
    }

    public final void k(String object) {
        if (this.c(241207) || this.a.h() != 1 || gp.l) {
            kq.a().a(false);
            String string = object;
            object = this;
            ((al)object).f.a(new gh("V\u1eeba t\u1ef1 \u0111\u1ed9ng t\u1eeb ch\u1ed1i giao d\u1ecbch t\u1eeb @" + string));
            return;
        }
        ((ny)this.a).a((String)object);
    }

    public final void a(lj[] ljArray, lk[] lkArray, int n2) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).a(ljArray, lkArray, n2);
        }
        com.mg.sq.a.t();
    }

    public final void l(String object) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).b((String)object);
        }
        com.mg.sq.a.t();
        object = ak.b().a("Ch\u00fac m\u1eebng", "Giao d\u1ecbch th\u00e0nh c\u00f4ng!", "\u0110\u00f3ng", 12350, 1);
        ((aq)object).a(this);
        ak.b().a((ap)object, false);
    }

    public final void b(lj lj2) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).a(lj2);
        }
    }

    public final void a(lk lk2, int n2) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).a(lk2, n2);
        }
    }

    public final void l(int n2) {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).a(n2, true);
        }
    }

    public final void I() {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).d();
        }
        com.mg.sq.a.t();
    }

    public final void m(String object) {
        this.a(241214, false);
        object = ak.b().a("", String.valueOf(object) + " Kh\u00f4ng mu\u1ed1n giao d\u1ecbch v\u1edbi b\u1ea1n!", "\u0110\u00f3ng", 12350, 1);
        ((aq)object).a(this);
        ak.b().a((ap)object, false);
    }

    public final void n(String string) {
        ak.b().a(8, true, false, null, new Object[]{string});
        this.a(241214, false);
    }

    public static lf a(lf lf2) {
        jy jy2 = jo.a(lf2.g);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        jy2.a(lf2.h + lf2.l, lf2.j + lf2.m, lf2.i + lf2.n, lf2.k + lf2.o);
        int n11 = 0;
        while (n11 < lf2.D.length) {
            kz kz2 = lf2.D[n11].p;
            if (kz2 != null) {
                n2 += kz2.a;
                n3 += kz2.d;
                n4 += kz2.b;
                n5 += kz2.c;
                n6 += kz2.e;
                n6 += jy2.c() * kz2.n / 100;
                n7 += kz2.g;
                n8 += kz2.f;
                n9 += kz2.h;
                n10 += kz2.i;
            }
            ++n11;
        }
        jy2.a(lf2.h + n2 + lf2.l, lf2.j + n4 + lf2.m, lf2.i + n5 + lf2.n, lf2.k + n3 + lf2.o);
        lf2.r = jy2.a() + n10;
        lf2.A = jy2.e() + n9;
        lf2.C = jy2.g() + n7;
        lf2.z = jy2.d() + n8;
        lf2.B = jy2.f();
        lf2.x = jy2.b() + n6;
        lf2.y = jy2.c() + n6;
        return lf2;
    }

    public static ap a(lk object, bj object2) {
        object = new hj("Chi Ti\u1ebft", (lk)object);
        ((aq)object).b(241215);
        ((aq)object).a((a)ak.b());
        bh bh2 = null;
        object2 = object;
        ((aq)object2).a(bh2, true);
        bh2 = new bh("", 204);
        object2 = object;
        ((aq)object2).b(bh2, true);
        ((aq)object).a(new bh("\u0110\u00f3ng", 204));
        ak.b().a((ap)object);
        return object;
    }

    public static void J() {
        kq.a().r();
        com.mg.sq.a.a(null, null);
    }

    public final void o(String string) {
        com.mg.sq.a.t();
        if (this.a != null && this.a.h() == 10) {
            ((oc)this.a).a(string);
        }
    }

    public final void c(String object, String string) {
        if (this.a != null) {
            if (this.a.h() == 8) {
                ((od)this.a).a(string, (byte)1, ca.d);
                return;
            }
            if (this.a.h() == 1) {
                String string2 = string;
                string = object;
                object = (ny)this.a;
                if (((ny)object).n != null) {
                    object = ((ny)object).n;
                    if (((oj)object).o != null && ((oj)object).o.b() == 2) {
                        ((op)((oj)object).o).a(string, string2);
                    }
                }
            }
        }
    }

    public static void p(String object) {
        if (gr.e.equals(object)) {
            object = ak.b().a("", "B\u1ea1n kh\u00f4ng th\u1ec3 giao d\u1ecbch v\u1edbi ch\u00ednh b\u1ea1n! vui l\u00f2ng ki\u1ec3m tra l\u1ea1i", "\u0110\u00f3ng", 12350, 1);
            ((aq)object).a((a)ak.b());
            ak.b().a((ap)object, false);
            return;
        }
        com.mg.sq.a.y((String)object);
        kq.a().j((String)object);
    }

    public final void a(String string, byte by2) {
        if (string == null) {
            return;
        }
        if (this.a.h() == 1) {
            boolean bl2;
            byte by3 = 0;
            String string2 = string;
            bj bj2 = (ny)this.a;
            if (bj2.n != null) {
                bj2 = bj2.n;
                if (((oj)bj2).o != null && ((oj)bj2).o.b() == 1) {
                    ((ok)((oj)bj2).o).a(string2, by3);
                    bl2 = true;
                } else {
                    bl2 = false;
                }
            } else {
                bl2 = false;
            }
            if (!bl2) {
                com.mg.sq.a.q(string);
            }
        } else {
            o = string;
            p = 0;
        }
        com.mg.sq.a.t();
    }

    public static void q(String object) {
        object = ak.b().a("", (String)object, "\u0110\u00f3ng", 205, 1);
        ((aq)object).a((a)ak.b());
        ak.b().a((ap)object, false);
    }

    public static void K() {
        if (o != null) {
            com.mg.sq.a.q(o);
            o = null;
        }
    }

    public static void L() {
        a a2 = (a)ak.b();
        if (a2 != null && a2.a != null && a2.a.h() == 1) {
            ((ny)a2.a).g();
        }
    }

    public static String b(long l2) {
        String string = l2 < 0L ? "? KEN" : String.valueOf(l.a(l2, ".")) + " " + "KEN";
        return string;
    }

    public static void r(String string) {
        com.mg.sq.a.a(null, null);
        kq.a().f(string);
    }

    public static void b(String string, int n2) {
        com.mg.sq.a.a(null, null);
        kq.a().a(string, (byte)n2);
    }

    public static void M() {
        if (k != null && m) {
            com.mg.sq.a.m(99004);
            return;
        }
        ap ap2 = ak.b().a("Th\u00f4ng tin", "Vui l\u00f2ng \u0111\u0103ng nh\u1eadp Ola \u0111\u1ec3 s\u1eed d\u1ee5ng t\u00ednh n\u0103ng n\u1ea1p KEN", "\u0110\u00f3ng", 12350, "H\u1ed7 tr\u1ee3", 12353, 1);
        ap2.a((a)ak.b());
        ak.b().a(ap2, false);
    }

    public final void c(String[] object) {
        if (this.a.h() == 1) {
            String[] stringArray = object;
            object = (ny)this.a;
            if (object.n != null) {
                object = object.n;
                if (object.o != null && object.o.b() == 1) {
                    ((ok)object.o).a(stringArray);
                }
            }
        }
    }

    public final void d(String[] stringArray) {
        hf hf2 = (hf)this.d(241202);
        if (hf2 != null) {
            hf2.a(stringArray);
        }
    }

    public final void N() {
        com.mg.sq.a.a(null, null);
    }

    public final void O() {
        if (this.a != null && this.a.h() == 8) {
            ((od)this.a).e();
        }
        com.mg.sq.a.t();
    }

    public final void a(dk[] dkArray) {
        if (this.a != null && this.a.h() == 1) {
            ((ny)this.a).a(dkArray);
        }
        com.mg.sq.a.t();
    }

    public static void P() {
        kq.a().e();
        com.mg.sq.a.a(null, null);
    }

    public final void s(String object) {
        this.k();
        object = ak.b().a(null, (String)object, "\u0110\u00f3ng", 208, 1);
        ((aq)object).a(this);
        ak.b().a((ap)object, false);
    }

    public final void a(String string, boolean bl2, boolean bl3) {
        if (string == null) {
            string = "";
        }
        gr.h = bl2;
        gr.g = string;
    }

    public final void a(String string, boolean bl2, boolean bl3, boolean bl4) {
        if (string == null) {
            string = "";
        }
        gr.h = bl3;
        gr.g = string;
        gr.i = bl2;
        com.mg.sq.a.W();
    }

    public final void b(String string, boolean bl2, boolean bl3, boolean bl4) {
        if (string == null) {
            string = "";
        }
        gr.h = bl3;
        gr.g = string;
        gr.i = bl2;
        if (this.c(241216)) {
            ht ht2 = (ht)this.d(241216);
            ht2.a(string, bl2, bl3);
        }
        com.mg.sq.a.t();
    }

    private static void W() {
        ht ht2 = new ht(gr.g, gr.i, gr.h);
        ak.b().a(ht2);
        com.mg.sq.a.t();
    }

    public static final void Q() {
        kq.a().s();
        com.mg.sq.a.a(null, null);
    }

    public static final void t(String object) {
        object = ak.b().a("Ch\u00fa \u00fd", (String)object, "C\u00f3", 12358, "\u0110\u00f3ng", 12350, 1);
        ((aq)object).a((a)ak.b());
        ak.b().a((ap)object, false);
    }

    public static final void a(String object, int[] nArray, long[] lArray, short[] sArray) {
        if (sArray == null || sArray.length == 0) {
            object = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u00f3 danh s\u00e1ch Vip Icon!", "\u0110\u00f3ng", 12350, 1);
            ak.b().a((ap)object, false);
            return;
        }
        object = new gt((String)object, gr.e, nArray, lArray, sArray);
        ak.b().a((ap)object, false);
    }

    public static final void d(String object, String object2) {
        q = object2;
        object2 = new hc();
        ((aq)object2).b(241223);
        object = new bf((String)object, 163, ca.d);
        ((au)object).a_(10, 10);
        ((hc)object2).a((au)object);
        int n2 = 10 + (((au)object).f() + 3);
        au au2 = new ff(null, 100, 2);
        au2.a_(1);
        ((ff)au2).a(10, n2, 136, 20);
        ((ff)au2).d(true);
        ((hc)object2).a(au2);
        int n3 = 10 + (au2.e() + 4);
        Image image = f.d("/iconbt");
        ft ft2 = new ft(null, 12360);
        ft2.a((a)ak.b());
        ft2.a_(4);
        ft2.a(n3, n2, 20, 20);
        ft2.a(image);
        ft2.b(0, 0, 20, 20);
        ft2.a(22523, 9287679, 22523);
        ((hc)object2).a(ft2);
        n2 += au2.f() + 5;
        n3 = au2.c() + 9;
        au2 = new ex("G\u1eedi", 12359);
        au2.a(n3, n2, 50, 18);
        au2.a_(2);
        ((hc)object2).a(au2);
        au2 = new ex("\u0110\u00f3ng", 12350);
        au2.a(n3 += 68, n2, 50, 18);
        au2.a_(3);
        ((hc)object2).a(au2);
        ((ap)object2).a(z.r - 180 >> 1, z.s - (n2 += au2.f() + 10) >> 1, 180, n2);
        ((aq)object2).a((a)ak.b());
        ((aq)object2).b(241223);
        bh bh2 = new bh("", 12359);
        Object object3 = object2;
        ((aq)object3).a(bh2, true);
        bh2 = new bh("", 12350);
        object3 = object2;
        ((aq)object3).b(bh2, true);
        ak.b().a((ap)object2);
    }

    public static void a(String string, String string2, String[] stringArray, String[] object) {
        com.mg.sq.a.t();
        w = string2;
        y = object;
        int n2 = z.r < 320 ? z.r - 20 : 200;
        object = new hc();
        au au2 = new bf(string != null ? string : "Vui l\u00f2ng ch\u1ecdn s\u1ed1 Ken mu\u1ed1n mua!", n2 - 20, ca.c);
        au2.a_(10, 10);
        int n3 = 10 + (au2.f() + 5);
        ((hc)object).a(au2);
        au2 = new fw("Mua Ken");
        au2.a_(1);
        ((fw)au2).d(true);
        ((fw)au2).a(10, n3, n2 - 20, 70);
        int n4 = 0;
        int n5 = stringArray.length;
        while (n4 < n5) {
            ey ey2 = new ey(stringArray[n4], false);
            ey2.h(n4);
            if (n4 == n5 - 1) {
                ey2.e(true);
            }
            ((fw)au2).a(ey2);
            ++n4;
        }
        ((hc)object).a(au2);
        n3 += au2.f();
        int n6 = n2 / 2 - 70;
        ex ex2 = new ex("N\u1ea1p", 300);
        ex2.a(n6, n3 += 5, 50, 20);
        ((hc)object).a(ex2);
        n6 = ex2.c() + ex2.e() + 40;
        ex2 = new ex("\u0110\u00f3ng", 12350);
        ex2.a(n6, n3, 50, 20);
        ((hc)object).a(ex2);
        n3 += ex2.f();
        ((ap)object).a(z.r - n2 >> 1, z.s - (n3 += 10) >> 1, n2, n3);
        ((aq)object).a((a)ak.b());
        ((aq)object).b(0);
        bh bh2 = new bh("", 300);
        Object object2 = object;
        ((aq)object2).a(bh2, true);
        bh2 = new bh("", 12350);
        object2 = object;
        ((aq)object2).b(bh2, true);
        ak.b().a((ap)object);
    }

    public static ap u(String object) {
        object = ak.b().a("Th\u00f4ng tin", (String)object, "\u0110\u00f3ng", 12350, 1);
        ((aq)object).a((a)ak.b());
        ak.b().a((ap)object, false);
        return object;
    }

    public static boolean m(int n2) {
        if (n2 == 99006) {
            dv.a().p();
            com.mg.sq.a.a(null, null);
            return true;
        }
        if (k == null || !m || gp.b) {
            if (n2 == 99005) {
                kq.a().y();
                com.mg.sq.a.a(null, null);
                return true;
            }
            if (n2 == 99004) {
                dv.a().o();
                com.mg.sq.a.a(null, null);
                return true;
            }
            return false;
        }
        if (n2 == 99005 || n2 == 99004) {
            if (n2 == 99005) {
                kq.a().y();
                com.mg.sq.a.a(null, null);
                return true;
            }
            if (!gp.b) {
                ((a)ak.b()).v = n2;
                ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n n\u00ean tham kh\u1ea3o nh\u1eefng \u0111i\u1ec1u kho\u1ea3n v\u1ec1 n\u1ea1p KEN.", "N\u1ea1p KEN", 302, "Xem", 301, 1);
                ap2.a((a)ak.b());
                ak.b().a(ap2, false);
                gp.b = true;
                return true;
            }
            if (n2 == 99004) {
                dv.a().o();
                com.mg.sq.a.a(null, null);
                return true;
            }
        }
        return false;
    }

    public static void e(String[] stringArray) {
        if (((a)ak.b()).c(241218)) {
            hk hk2 = (hk)((a)ak.b()).d(241218);
            hk2.a(stringArray);
            return;
        }
        hk hk3 = new hk(stringArray);
        ak.b().a(hk3, false);
    }

    public final void c(boolean bl2) {
        gr.j.ab = bl2;
        if (this.c(241202)) {
            hf hf2 = (hf)this.d(241202);
            hf2.j(bl2);
        }
        if (this.a != null && this.a.h() == 1) {
            bj bj2 = (ny)this.a;
            if (bj2.n != null) {
                bj2 = bj2.n;
                if (((oj)bj2).o != null && ((oj)bj2).o.b() == 1) {
                    ((ok)((oj)bj2).o).a(gr.j, true);
                }
            }
        }
        com.mg.sq.a.t();
    }

    public final void d(boolean bl2) {
        gr.j.ac = bl2;
        com.mg.sq.a.t();
        if (bl2) {
            gr.j.e = (byte)2;
            return;
        }
        if (gr.j.e == 2) {
            gr.j.e = 0;
        }
    }

    public final void a(int[] object, String[] objectArray) {
        com.mg.sq.a.t();
        if (this.a != null && this.a.h() == 1) {
            ny ny2 = (ny)this.a;
            String[] stringArray = objectArray;
            objectArray = (Object[])object;
            object = ny2;
            if (ny2.n != null) {
                oj oj2 = object.n;
                object = oj2;
                if (oj2.o != null && object.o.b() == 1) {
                    ((ok)object.o).a((int[])objectArray, stringArray);
                }
            }
        }
    }

    public final void a(lp[] object) {
        if (this.a != null && this.a.h() == 1) {
            k.a((Object[])object, new b(this));
            lp[] lpArray = object;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                object = ((ny)object).n;
                ((ny)object).n.s = false;
                if (((oj)object).o == null || ((oj)object).o.b() != 3) {
                    ((oj)object).j(3);
                    or.l = lpArray;
                } else if (((oj)object).o.b() == 3) {
                    ((or)((oj)object).o).a(lpArray);
                }
            }
            com.mg.sq.a.t();
        }
    }

    public final void a(String object, String string, long l2, int n2) {
        if (this.a != null && this.a.h() == 1) {
            int n3 = n2;
            long l3 = l2;
            String string2 = string;
            string = object;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                ((ny)object).n.a(string, string2, l3, n3);
            }
        }
    }

    public final void a(byte[] byArray, byte[] byArray2) {
        com.mg.sq.a.t();
        ak.b().a(10, true, false, null, new Object[]{byArray, byArray2});
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof gm) {
            return (gm)object;
        }
        return null;
    }

    public final void v(String string) {
        hc hc2 = (hc)this.d(241223);
        ((ff)hc2.e(1)).c(string);
    }

    public final void R() {
        if (this.a.h() == 2) {
            cw.a("[SQLOGIN] login thanh cong!");
            kq.a().a(gr.e, (byte)1);
            return;
        }
        if (this.a.h() == 3) {
            cw.a("[SQLOGIN] login thanh cong!");
            kq.a().a(gr.e, (byte)1);
        }
    }

    public final void a(String object, byte by2, byte by3, di di2, di di3, di di4) {
        if (gr.j != null && gr.j.b.equals(object)) {
            object = gr.j;
            gr.j.g = by2;
            ((lf)object).f = by3;
            ((lf)object).W = di2;
            ((lf)object).X = di3;
            ((lf)object).Y = di4;
        }
    }

    public final void a(String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        int n13;
        if (gr.j != null && gr.j.b.equals(string)) {
            int n14 = n12;
            int n15 = n11;
            int n16 = n10;
            int n17 = n9;
            int n18 = n8;
            n13 = n7;
            n12 = n6;
            n11 = n5;
            n10 = n4;
            n9 = n3;
            n8 = n2;
            lf lf2 = gr.j;
            gr.j.G = n8;
            lf2.h = n9;
            lf2.j = n10;
            lf2.i = n11;
            lf2.k = n12;
            lf2.l = n13;
            lf2.m = n18;
            lf2.n = n17;
            lf2.o = n16;
            lf2.p = n15;
            lf2.q = n14;
        }
        if (this.a != null && this.a.h() == 1) {
            n13 = n6;
            n12 = n5;
            n11 = n4;
            n10 = n3;
            n9 = n2;
            String string2 = string;
            ny ny2 = (ny)this.a;
            if (ny2.n != null) {
                oj oj2 = ny2.n;
                if (oj2.o != null && oj2.o.b() == 1) {
                    ((ok)oj2.o).a(gr.j, true);
                }
            }
        }
    }

    public final void a(String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        Object object;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        if (gr.j != null && gr.j.b.equals(string)) {
            n14 = n8;
            n13 = n7;
            n12 = n6;
            n11 = n5;
            n10 = n4;
            n9 = n3;
            int n15 = n2;
            object = gr.j;
            gr.j.s = n15;
            ((lf)object).r = n9;
            ((lf)object).J = n10;
            ((lf)object).M = n11;
            ((lf)object).N = n12;
            ((lf)object).H = n13;
            ((lf)object).I = n14;
        }
        if (this.a != null && this.a.h() == 1) {
            int n16 = n8;
            n14 = n7;
            n13 = n6;
            n12 = n5;
            n11 = n4;
            n10 = n3;
            n9 = n2;
            String string2 = string;
            object = (ny)this.a;
            if (((ny)object).n != null) {
                oj oj2 = ((ny)object).n;
                if (oj2.o != null && oj2.o.b() == 1) {
                    ((ok)oj2.o).a(gr.j, false);
                }
            }
        }
    }

    public final void a(String object, lj[] ljArray) {
        if (gr.j != null && object.equals(gr.j.b)) {
            lz.a();
            gr.j.D = ljArray;
        }
        if (this.a != null && this.a.h() == 1) {
            lj[] ljArray2 = object;
            object = ljArray;
            object = ljArray2;
            object = (ny)this.a;
            if (object.n != null) {
                object = object.n;
                if (object.o != null && object.o.b() == 1) {
                    ((ok)object.o).a(gr.j, true);
                }
            }
        }
        if ((object = this.d(241202)) != null) {
            ((hf)object).v();
        }
    }

    public final void a(lt[] ltArray) {
        gr.j.E = ltArray;
    }

    public final void a(String object, int n2, int n3, int n4, int n5, int n6, String string, String string2, String string3) {
        Object object2;
        int n7;
        int n8;
        int n9;
        int n10;
        String string4;
        String string5;
        if (gr.j != null && gr.j.b.equals(object)) {
            string5 = string3;
            string4 = string2;
            String string6 = string;
            n10 = n6;
            n9 = n5;
            n8 = n4;
            n7 = n3;
            int n11 = n2;
            object2 = gr.j;
            gr.j.K = n11;
            ((lf)object2).L = n7;
            ((lf)object2).O = n8;
            ((lf)object2).P = n9;
            ((lf)object2).ad = n10;
            ((lf)object2).U = string6;
            ((lf)object2).T = string4;
            ((lf)object2).S = string5;
        }
        if (this.a != null && this.a.h() == 1) {
            String string7 = object;
            object = string3;
            string5 = string2;
            string4 = string;
            int n12 = n6;
            n10 = n5;
            n9 = n4;
            n8 = n3;
            n7 = n2;
            String string8 = string7;
            object2 = (ny)this.a;
            if (((ny)object2).n != null) {
                object = ((ny)object2).n;
                if (((oj)object).o != null && ((oj)object).o.b() == 1) {
                    ((ok)((oj)object).o).a(gr.j, true);
                }
            }
        }
    }

    public final void S() {
        com.mg.sq.a.t();
        ap ap2 = ((a)ak.b()).d(241201);
        if (ap2 != null) {
            lf lf2 = gr.j;
            ap2 = (gw)ap2;
            ((gw)ap2).k.b(lf2);
        }
        if ((ap2 = ((a)ak.b()).d(241203)) != null) {
            ((hz)ap2).a(gr.j);
        }
    }

    public final void a(String[] stringArray, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        int n2;
        int n3 = 0;
        while (n3 < stringArray.length) {
            n2 = 0;
            while (n2 < gr.k.length) {
                if (gr.k[n2].b.equals(stringArray[n3])) {
                    gr.k[n2].n = nArray[n3];
                    gr.k[n2].o = nArray2[n3];
                    break;
                }
                ++n2;
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < nArray3.length) {
            n2 = 0;
            while (n2 < gr.l.length) {
                if (gr.l[n2].a == nArray3[n3]) {
                    gr.l[n2].g = nArray4[n3];
                    break;
                }
                ++n2;
            }
            ++n3;
        }
    }

    public final void w(String string) {
        if (this.c(241202)) {
            hf hf2 = (hf)this.d(241202);
            hf2.b(string);
            return;
        }
        com.mg.sq.a.u(string);
    }

    public final void c(lj lj2) {
        gr.a(lj2);
        if (this.c(241202)) {
            hf hf2 = (hf)this.d(241202);
            hf2.b(lj2);
        }
    }

    public final void a(lk[] lkArray) {
        int n2 = 0;
        while (n2 < lkArray.length) {
            lk lk2 = lkArray[n2];
            gr.a(lk2, lk2.g);
            if (this.c(241202)) {
                hf hf2 = (hf)this.d(241202);
                hf2.c(lk2);
            }
            ++n2;
        }
    }

    public final void f(int n2, int n3) {
        ap ap2 = this.d(241202);
        if (ap2 != null) {
            ((hf)ap2).h(n2, n3);
            return;
        }
        int n4 = 0;
        while (n4 < gr.l.length) {
            if (gr.l[n4].a == n2) {
                gr.a(n2, gr.l[n4].g - n3);
                return;
            }
            ++n4;
        }
    }

    public final void a(lr[] object) {
        gr.j.ae = object;
        if (this.a != null && this.a.h() == 1) {
            lr[] lrArray = object;
            object = (ny)this.a;
            if (object.n != null) {
                object = object.n;
                if (object.o instanceof ok) {
                    ((ok)object.o).a(lrArray);
                }
            }
        }
    }

    public final void e(String object, String string) {
        if (this.c(241231)) {
            this.e(241231);
        }
        ou ou2 = null;
        d d2 = null;
        hf hf2 = (hf)this.d(241202);
        if (hf2 != null) {
            ou2 = hf2.x();
            d2 = hf2.y();
        }
        object = new hm((String)object, string, ou2, d2);
        ak.b().a((ap)object, false);
        com.mg.sq.a.t();
    }

    public final void a(String object, String string, String string2) {
        if (this.c(241232)) {
            this.e(241232);
        }
        ou ou2 = null;
        d d2 = null;
        hf hf2 = (hf)this.d(241202);
        if (hf2 != null) {
            ou2 = hf2.x();
            d2 = hf2.y();
        }
        object = new ib((String)object, string, string2, ou2, d2);
        ak.b().a((ap)object, false);
        com.mg.sq.a.t();
    }

    public final void a(String object, lj[] ljArray, lk[] lkArray, byte by2) {
        object = this.d(241231);
        if (object != null) {
            ((hm)object).a(ljArray, lkArray, by2);
        }
    }

    public final void a(String string, String string2, byte by2, long l2) {
        ap ap2 = this.d(241231);
        if (ap2 != null) {
            ((hm)ap2).b(string, string2, by2, l2);
        }
    }

    public final void b(String string, String string2, byte by2, long l2) {
        ap ap2 = this.d(241231);
        if (ap2 != null) {
            ((hm)ap2).a(string, string2, by2, l2);
        }
    }

    public final void a(String string, byte by2, long l2) {
        ap ap2 = this.d(241231);
        if (ap2 != null) {
            ((hm)ap2).b(string, by2, l2);
        }
    }

    public final void a(int n2, int n3, String string, byte by2, long l2) {
        ap ap2 = this.d(241231);
        if (ap2 != null) {
            ((hm)ap2).a(string, by2, l2);
        }
    }

    public final void c(String string, String string2, byte by2, long l2) {
        ap ap2 = this.d(241232);
        if (ap2 != null) {
            ((ib)ap2).a(string, string2, by2, l2);
        }
    }

    public final void b(String string, byte by2, long l2) {
        ap ap2 = this.d(241232);
        if (ap2 != null) {
            ((ib)ap2).b(string, by2, l2);
        }
    }

    public final void b(int n2, int n3, String string, byte by2, long l2) {
        ap ap2 = this.d(241232);
        if (ap2 != null) {
            ((ib)ap2).a(string, by2, l2);
        }
    }

    public final void d(String string, String string2, byte by2, long l2) {
        ap ap2 = this.d(241232);
        if (ap2 != null) {
            ((ib)ap2).b(string, string2, by2, l2);
        }
    }

    public final void b(String object, lj[] ljArray, lk[] lkArray, byte by2) {
        object = this.d(241232);
        if (object != null) {
            ((ib)object).a(ljArray, lkArray, by2);
        }
    }

    public final void x(String string) {
        com.mg.sq.a.t();
        ((a)ak.b()).a(241217, false);
        if (l.b(string)) {
            string = "N\u1ea1p th\u1ebb th\u00e0nh c\u00f4ng";
        }
        com.mg.sq.a.u(string);
    }

    public final void b(String string, String string2, String[] object, String[] object2) {
        com.mg.sq.a.t();
        com.mg.sq.a.t();
        w = string2;
        x = object2;
        int n2 = z.r < 320 ? z.r - 20 : 200;
        object2 = new hc();
        au au2 = new bf(string, n2 - 20, ca.c);
        au2.a_(10, 10);
        int n3 = 10 + (au2.f() + 5 + ca.d.a());
        ((hc)object2).a(au2);
        au2 = new gf("M\u1ea1ng \u0111i\u1ec7n tho\u1ea1i");
        au2.a_(1);
        ((gf)au2).a((Object[])object);
        ((gf)au2).a(10, n3, n2 - 20, 20);
        ((gf)au2).d(true);
        ((hc)object2).a(au2);
        object = new ff("S\u1ed1 Seri", 100, 2);
        ((ff)object).i(2);
        ((au)object).a_(3);
        ((ff)object).a(10, n3 += au2.f() + 5 + ca.d.a(), n2 - 20, 20);
        n3 += ((au)object).f() + 5 + ca.d.a();
        ((hc)object2).a((au)object);
        object = new ff("M\u00e3 s\u1ed1 n\u1ea1p ti\u1ec1n", 100, 4);
        ((ff)object).i(3);
        ((au)object).a_(2);
        ((ff)object).a(10, n3, n2 - 20, 20);
        ((hc)object2).a((au)object);
        int n4 = n2 / 2 - 70;
        au2 = new ex("N\u1ea1p", 209);
        au2.a(n4, n3 += ((au)object).f() + 5, 50, 20);
        ((hc)object2).a(au2);
        n4 = au2.c() + au2.e() + 40;
        au2 = new ex("\u0110\u00f3ng", 12350);
        au2.a(n4, n3, 50, 20);
        ((hc)object2).a(au2);
        n3 += au2.f();
        ((ap)object2).a(z.r - n2 >> 1, z.s - (n3 += 10) >> 1, n2, n3);
        ((aq)object2).a((a)ak.b());
        ((aq)object2).b(241217);
        bh bh2 = new bh("", 209);
        Object object3 = object2;
        ((aq)object3).a(bh2, true);
        bh2 = new bh("", 12350);
        object3 = object2;
        ((aq)object3).b(bh2, true);
        ak.b().a((ap)object2);
    }

    public final void T() {
        cw.a("[SQGameBoard]======================receiveQuitRoom=============");
        bj bj2 = (ny)this.a;
        if (bj2.n != null) {
            bj2 = bj2.n;
            if (((oj)bj2).o != null && ((oj)bj2).o.b() == 3) {
                ((or)((oj)bj2).o).r();
                return;
            }
            ((oj)bj2).q = null;
        }
    }

    public final void b(lp[] object) {
        lp[] lpArray = object;
        object = (ny)this.a;
        if (object.n != null) {
            object = object.n;
            if (object.o.b() == 3) {
                ((or)object.o).b(lpArray);
            }
        }
        com.mg.sq.a.t();
    }

    public final void U() {
        kq.a().b();
        Object object = "Snb\u001fcn\u001fF`ld\u001fjgnmf\u001fahmg\u001fsgtnmf+\u001fjds\u001fmnh\u001funh\u001fl`x\u001fbgt\u001frd\u001fah\u001fmf`s";
        object = "Snb\u001fcn\u001fF`ld\u001fjgnmf\u001fahmg\u001fsgtnmf+\u001fjds\u001fmnh\u001funh\u001fl`x\u001fbgt\u001frd\u001fah\u001fmf`s".toCharArray();
        int n2 = 0;
        while (n2 < ((Object)object).length) {
            object[n2] = (char)(object[n2] + true);
            ++n2;
        }
        object = new String((char[])object);
        ap ap2 = ak.b().a(" ", (String)object, "\u0110\u00f3ng", 12349, 1);
        ap2.a(false);
        ap2.a(this);
        ak.b().a(ap2);
    }

    public final void a(String string, long l2) {
        com.mg.sq.a.t();
        System.out.println("[receiveAddEquipToMarket]====================" + l2);
        ((a)ak.b()).e(241234);
        if (this.c(241202)) {
            hf hf2 = (hf)this.d(241202);
            hf2.a(string);
            return;
        }
        if (this.c(241235)) {
            ho.t();
        }
    }

    public final void a(String string, lj[] ljArray, lk[] lkArray) {
        com.mg.sq.a.t();
        if (this.c(241235)) {
            ho ho2 = (ho)this.d(241235);
            ho2.a(string, ljArray, lkArray);
        }
    }

    public final void a(int n2, int n3, long l2) {
        com.mg.sq.a.t();
        System.out.println("[receiveAddItemToMarke]========================" + l2);
        ((a)ak.b()).e(241234);
        if (this.c(241202)) {
            hf hf2 = (hf)this.d(241202);
            hf2.i(n2, n3);
            return;
        }
        if (this.c(241235)) {
            ho.t();
        }
    }

    public final void a(int n2, int n3, lo[] loArray) {
        if (n2 != -1) {
            ap ap2 = this.d(241233);
            if (ap2 != null) {
                ((hl)ap2).a(n2, n3, loArray);
                return;
            }
        } else {
            if (this.c(241233)) {
                this.e(241233);
            }
            if (this.c(241235)) {
                ap ap3 = this.d(241235);
                ((ho)ap3).a(loArray);
                return;
            }
            ho ho2 = new ho(loArray);
            ak.b().a(ho2, false);
            com.mg.sq.a.t();
        }
    }

    public final void b(String string, lj[] ljArray, lk[] lkArray) {
        if (this.c(241233)) {
            hl hl2 = (hl)this.d(241233);
            hl2.a(string, ljArray, lkArray);
        }
    }

    public final void b(ld[] object) {
        if (this.c(241233)) {
            this.e(241233);
        } else if (this.c(241235)) {
            this.e(241235);
        }
        object = new hl((ld[])object);
        ak.b().a((ap)object, false);
        com.mg.sq.a.t();
    }
}

