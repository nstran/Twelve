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
extends ah
implements bn,
ih,
kq {
    private il p;
    public static d g;
    public static d h;
    public static String i;
    public static byte j;
    private static String q;
    private static a r;
    public static byte k;
    public static String[] l;
    public static oi m;
    private cp s;
    public static final az n;
    private int t;
    public static boolean o;
    private static String u;
    private static String[] v;
    private static String[] w;

    static {
        i = null;
        j = (byte)-1;
        r = new a();
        l = null;
        m = null;
        n = new ge();
        o = false;
    }

    public static void q() {
        g = new by(4863);
        g.a(4863);
        h = new if(new int[]{0xFFFFFF, 0xFE0000});
        h.a(0xFE0000);
        if (v.t >= 230 && v.u >= 310 || v.u > v.t) {
            k = 0;
            return;
        }
        k = 1;
    }

    public static void r() {
        g = h = bx.d;
        oy.b();
    }

    public static a s() {
        return (a)ag.b();
    }

    public a() {
        this.a(new ax(5));
    }

    public static boolean t() {
        return v.O;
    }

    protected final void h() {
        if (m != null) {
            m.H();
        }
        ks.a().c();
        if (o) {
            du.a().g();
        }
    }

    private boolean n(int n2) {
        return this.a != null && this.a.h() == 1;
    }

    public static void a(am am2) {
        if (am2 == null) {
            return;
        }
        com.mg.sq.a.i(am2.h());
    }

    public static void i(int n2) {
        if (!(v.N && (!v.ah || !v.O))) {
            return;
        }
        switch (n2) {
            case 0: {
                co.b().a("battle", -1);
                co.b().a(155141);
                co.b().e();
                return;
            }
            case 2: {
                co.b().a("title", 1);
                co.b().a(250000);
                co.b().e();
                return;
            }
        }
        co.b().a("lv1", -1);
        co.b().a(139051);
        co.b().e();
    }

    public final void p() {
        this.d = new ab();
        this.c = new ac();
    }

    protected final an a(int n2, int n3, Object[] object) {
        ct.b("Before createScreen(" + n2 + ", " + n3 + ")");
        v.ag = false;
        Object object2 = null;
        switch (n3) {
            case 1: {
                object2 = new oa();
                break;
            }
            case 2: {
                object2 = new ob();
                break;
            }
            case 3: {
                object2 = new nw();
                break;
            }
            case 4: {
                object2 = new og();
                break;
            }
            case 6: {
                object2 = new nv();
                break;
            }
            case 5: {
                if (object != null && ((Object[])object).length > 0) {
                    object2 = (String)object[0];
                    return new nx((String)object2);
                }
                object2 = new nx();
                break;
            }
            case 7: {
                int n4 = (Integer)object[0];
                object2 = new ny(n4);
                if (((Object[])object).length == 2) {
                    ((ny)object2).a((String)object[1]);
                }
                break;
            }
            case 8: {
                object = (String)object[0];
                object2 = new of((String)object);
                break;
            }
            case 9: {
                object2 = new od(n2);
                break;
            }
            case 10: {
                if (object == null || ((Object[])object).length <= 0) {
                    return null;
                }
                object2 = (byte[])object[0];
                object = (byte[])object[1];
                object2 = new oe(n2, (byte[])object2, (byte[])object);
            }
        }
        ct.b("After createScreen(" + n2 + ", " + n3 + ")");
        return object2;
    }

    public static void a(be be2) {
        ci.a("KS " + go.e, "8031", be2);
    }

    public static void b(be be2) {
        ci.a("MS " + go.e, "8031", be2);
    }

    public static void c(be be2) {
        ci.a("XT " + go.e, "8031", be2);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void d(int var1_1, int var2_14) {
        super.d(var1_1, var2_14);
        switch (var2_14) {
            case 12357: {
                this.a(false);
                this.S();
                return;
            }
            case 12356: {
                this.a(false);
                com.mg.sq.a.c(null);
                return;
            }
            case 12353: 
            case 12354: {
                MGMIDlet.d();
                MGMIDlet.b("1900588883");
                if (var2_14 != 12353) break;
                this.a(false);
                return;
            }
            case 12352: {
                this.a(false);
                this.N();
                return;
            }
            case 12350: {
                this.a(false);
                return;
            }
            case 12349: {
                var1_2 = MGMIDlet.d();
                var1_2.notifyDestroyed();
                return;
            }
            case 12361: {
                pa.a().c();
                var1_3 = MGMIDlet.d();
                var1_3.notifyDestroyed();
                return;
            }
            case 12345: {
                this.v();
                if (this.p == null) break;
                this.p.t();
                this.p = null;
                return;
            }
            case 12347: {
                com.mg.sq.a.X();
                return;
            }
            case 100: {
                ks.a().f();
                ag.b().a(false);
                return;
            }
            case 203: {
                ks.a().l();
                ag.b().a(false);
                return;
            }
            case 102: {
                if (!this.c(-241249)) {
                    return;
                }
                var1_4 = (he)this.d(-241249);
                var3_17 = (ff)var1_4.e(1);
                var2_15 = var3_17.r().toLowerCase().trim();
                if (i.b(var2_15)) ** GOTO lbl81
                if (var2_15.equals(go.e)) ** GOTO lbl79
                var3_17 = (ff)var1_4.e(5);
                var8_19 = 0L;
                if (!var3_17.r().equals("")) {
                    var8_19 = Integer.parseInt(var3_17.r()) * 1000;
                }
                if (go.k.aa) {
                    var1_4 = "B\u1ea1n \u0111ang \u1edf b\u1eadt ch\u1ebf \u0111\u1ed9 ch\u1eb7n khi\u00eau chi\u1ebfn. B\u1ea1n kh\u00f4ng th\u1ec3 chi\u1ebfn \u0111\u1ea5u ngay l\u00fac n\u00e0y. H\u00e3y t\u1eaft ch\u1ebf \u0111\u1ed9 ch\u1eb7n khi\u00eau chi\u1ebfn \u0111\u1ec3 chi\u1ebfn \u0111\u1ea5u n\u00e0o! (H\u01b0\u1edbng d\u1eabn: B\u1ea1n h\u00e3y ch\u1ecdn Menu > H\u1ed7 tr\u1ee3 > C\u00e0i \u0111\u1eb7t)";
                } else if (go.s > -1L && var8_19 > go.s) {
                    var1_4 = "V\u01b0\u1ee3t qu\u00e1 s\u1ed1 ti\u1ec1n b\u1ea1n \u0111ang c\u00f3. Vui l\u00f2ng th\u1eed l\u1ea1i!!!";
                } else {
                    if (go.s < 0L) {
                        var8_19 = 0L;
                    }
                    ag.b().a(false);
                    var3_17 = (ey)var1_4.e(6);
                    var4_21 = (ey)var1_4.e(7);
                    var1_4 = (ey)var1_4.e(10);
                    ks.a().a(var2_15, "Ngon th\u00ec nh\u00e0o v\u00f4 \u0111\u00e2y!", var3_17.a(), var8_19, var4_21.a(), var1_4.a());
                    this.f(var2_15);
                    ((oa)this.a).l = var2_15;
                    return;
lbl79:
                    // 1 sources

                    var1_4 = "Nh\u1eadp tr\u00f9ng t\u00ean. Vui l\u00f2ng nh\u1eadp l\u1ea1i!!!";
                }
                ** GOTO lbl82
lbl81:
                // 1 sources

                var1_4 = "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean \u0111\u1ed1i th\u1ee7.";
lbl82:
                // 4 sources

                var1_4 = ag.b().a("Ch\u00fa \u00fd", (String)var1_4, "\u0110\u00f3ng", 199199, 1);
                var1_4.b(199199);
                var1_4.a(this);
                ag.b().a((al)var1_4, false);
                return;
            }
            case 101: {
                ag.b().e(-241249);
                return;
            }
            case 199199: {
                ag.b().e(199199);
                return;
            }
            case 202: {
                ks.a().f();
                ag.b().e(-241249);
                return;
            }
            case 201: {
                ks.a().l();
                ag.b().e(241214);
                return;
            }
            case 200: {
                var8_20 = (he)this.d(-241249);
                if (var8_20 == null) {
                    return;
                }
                var2_16 = ((ff)var8_20.e(1)).r().toLowerCase().trim();
                if (!i.b(var2_16)) {
                    if (!var2_16.equals(go.e)) {
                        ag.b().a(-241249, false);
                        com.mg.sq.a.x(var2_16);
                        ks.a().j(var2_16);
                        return;
                    }
                    var1_5 = ag.b().a("Ch\u00fa \u00fd", "Nh\u1eadp tr\u00f9ng t\u00ean. Vui l\u00f2ng nh\u1eadp l\u1ea1i!!!", "\u0110\u00f3ng", 199199, 1);
                    var1_5.b(199199);
                    var1_5.a(this);
                    ag.b().a(var1_5, false);
                    return;
                }
                var1_6 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean \u0111\u1ed1i th\u1ee7.", "\u0110\u00f3ng", 199199, 1);
                var1_6.b(199199);
                var1_6.a(this);
                ag.b().a(var1_6, false);
                return;
            }
            case 204: {
                this.a(241215, false);
                return;
            }
            case 205: {
                ag.b().a(false);
                if (this.a.h() != 1) break;
                ((oa)this.a).u();
                return;
            }
            case 207: {
                this.a(false);
                return;
            }
            case 208: {
                com.mg.sq.a.X();
                this.l();
                return;
            }
            case 209: {
                if (!this.c(241217)) break;
                try {
                    var1_7 = (he)this.d(241217);
                    var9_23 = ((ff)var1_7.e(2)).r();
                    var3_18 = ((ff)var1_7.e(3)).r();
                    if (var9_23 == null || var9_23.equals("")) {
                        com.mg.sq.a.t("Vui l\u00f2ng nh\u1eadp s\u1ed1 PIN th\u1ebb c\u00e0o");
                        return;
                    }
                    if (var3_18 == null || var3_18.equals("")) {
                        com.mg.sq.a.t("Vui l\u00f2ng nh\u1eadp m\u00e3 s\u1ed1 th\u1ebb c\u00e0o!");
                        return;
                    }
                    var4_22 = (gg)var1_7.e(1);
                    ks.a().a(var3_18, var9_23, com.mg.sq.a.v[var4_22.a()], com.mg.sq.a.u);
                    this.a((String)null, (il)null);
                    return;
                }
                catch (Exception v0) {
                    var9_24 = v0;
                    v0.printStackTrace();
                    return;
                }
            }
            case 300: {
                if (!this.c(0)) break;
                try {
                    var1_8 = (he)this.d(0);
                    var9_25 = com.mg.sq.a.w[((fx)var1_8.e(1)).a().q()];
                    ci.a("DEPO", com.mg.sq.a.u, var9_25);
                }
                catch (Exception v1) {
                    var9_26 = v1;
                    v1.printStackTrace();
                }
                this.a(0, false);
                return;
            }
            case 301: {
                ag.b().a(false);
                if (com.mg.sq.a.m == null) break;
                var1_9 = com.mg.sq.a.m;
                var1_9.b(null, "rss://ola/0/2011/12/napken");
                var1_9.i(true);
                return;
            }
            case 302: {
                ag.b().a(false);
                this.m(this.t);
                return;
            }
            case 12358: {
                this.a(false);
                du.a().b((short)2412);
                this.a((String)null, (il)null, 10000);
                return;
            }
            case 123523: {
                this.l();
                this.Y();
                return;
            }
            case 303: {
                var1_10 = (he)this.d(241221);
                pd.b(((ey)var1_10.e(6)).a());
                this.a(241221, false);
                this.N();
                return;
            }
            case 304: {
                var1_11 = (he)this.d(241221);
                pd.b(((ey)var1_11.e(6)).a());
                this.a(241221, false);
                return;
            }
            case 12359: {
                var1_12 /* !! */  = (he)this.d(241223);
                if (var1_12 /* !! */  == null) break;
                var9_27 = ((ff)var1_12 /* !! */ .e(1)).r();
                if (!i.b(var9_27)) {
                    du.a().b(var9_27, null, com.mg.sq.a.q);
                    this.e(241223);
                    var1_12 /* !! */  = this.a("", "G\u1eedi th\u00e0nh c\u00f4ng!", "\u0110\u00f3ng", 12350, 1);
                    var1_12 /* !! */ .a(this);
                    ag.b().a(var1_12 /* !! */ );
                    return;
                }
                var1_12 /* !! */  = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean nick", "\u0110\u00f3ng", 12350, 1);
                var1_12 /* !! */ .a(this);
                ag.b().a(var1_12 /* !! */ );
                return;
            }
            case 12360: {
                var1_13 = (he)this.d(241223);
                if (var1_13 == null) break;
                var9_28 = new hd();
                var9_28.a(this);
                ag.b().a(var9_28, false);
                return;
            }
            case 305: {
                this.a(false);
            }
        }
    }

    public static void u() {
        com.mg.sq.a.b(true);
    }

    public static void b(boolean bl2) {
        if (bl2) {
            al al2 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u0103ng xu\u1ea5t kh\u00f4ng?", "C\u00f3", 12347, "Kh\u00f4ng", 12350, 1);
            al2.a(ag.b());
            ag.b().a(al2, false);
            return;
        }
        com.mg.sq.a.X();
    }

    private static void X() {
        co.b().d();
        ks.a().b();
        go.a();
        go.w = null;
        oa.b = null;
        oa.c = null;
        om.i = null;
        om.k = 8;
        om.j = 0;
        o = false;
        oq.v();
        i = null;
        if (m != null) {
            m.G();
        }
        pa.a().d();
        ((a)ag.b()).l();
        if (v.ae) {
            ((a)ag.b()).a((String)null, (il)null);
            ((a)ag.b()).f(2);
            return;
        }
        MGMIDlet mGMIDlet = MGMIDlet.d();
        mGMIDlet.notifyDestroyed();
    }

    public final void o() {
        this.v();
        int n2 = 12350;
        if (this.a != null && this.a.h() == 1) {
            if (this.d(-241249) != null) {
                this.a(-241249, false);
                n2 = 100;
            }
            if (this.d(241214) != null) {
                ks.a().l();
                this.a(241214, false);
                n2 = 203;
            }
            ((oa)this.a).y();
        }
        this.c("H\u1ebft th\u1eddi gian th\u1ef1c thi.", n2);
    }

    public final void v() {
        this.a(11111, false);
    }

    public final void a(String string) {
        this.c(string, 12350);
    }

    private void c(String object, int n2) {
        object = this.a("Th\u00f4ng tin", (String)object, "\u0110\u00f3ng", n2, "H\u1ed7 tr\u1ee3", 12353, 1);
        ((am)object).a(this);
        this.a((al)object, false);
    }

    public final void a(String string, il il2) {
        this.a(string, il2, al.a);
    }

    public final void a(String object, il il2, int n2) {
        this.p = il2;
        object = new ie((String)object);
        ((am)object).b(11111);
        ((al)object).a(n2);
        this.a((al)object);
    }

    public static al a(lh object, int n2, int n3, bf bf2, long l2, String string, boolean bl2, boolean bl3) {
        try {
            ((lh)object).X = l2;
            gt gt2 = new gt((lh)object, string, bl2, bl3);
            ag.b().a(gt2, false);
            return gt2;
        }
        catch (Exception exception) {
            object = ag.b().a("", String.valueOf(((ld)object).b) + " mu\u1ed1n khi\u00eau chi\u1ebfn b\u1ea1n. B\u1ea1n \u0111\u1ed3ng \u00fd kh\u00f4ng?", "\u0110\u1ed3ng \u00fd", n2, "Kh\u00f4ng", n3, 1);
            ((am)object).b(-241209);
            ((am)object).a(bf2);
            ag.b().a((al)object, false);
            return object;
        }
    }

    public final void j(int n2) {
        int n3 = 12349;
        String string = "L\u1ed7i \u1ee9ng d\u1ee5ng s\u1ed1: " + n2;
        if (n2 == 3) {
            n3 = 12361;
            string = String.valueOf(string) + ". M\u00e1y kh\u00f4ng \u0111\u1ee7 b\u1ed9 nh\u1edb. Vui l\u00f2ng x\u00f3a b\u1edbt \u1ee9ng d\u1ee5ng ho\u1eb7c chuy\u1ec3n game qua b\u1ed9 nh\u1edb m\u00e1y ho\u1eb7c th\u1ebb. Y\u00eau c\u1ea7u b\u1ed9 nh\u1edb tr\u1ed1ng \u00edt nh\u1ea5t 5Mb. M\u1ecdi chi ti\u1ebft xin li\u00ean h\u1ec7 t\u1ed5ng \u0111\u00e0i \u0111\u1ec3 h\u1ed7 tr\u1ee3!";
        }
        al al2 = this.a("Th\u00f4ng tin", string, "Tho\u00e1t", n3, "H\u1ed7 tr\u1ee3", 12354, 1);
        al2.a(this);
        this.a(al2, false);
    }

    public final void a(int n2, String string) {
        r.a();
        if (this.b != null && this.b.h() == 241219) {
            al al2 = ag.b().a("Th\u00f4ng tin", string, "\u0110\u00f3ng", 12350, 1);
            al2.a((a)ag.b());
            ag.b().a(al2, false);
            return;
        }
        if (this.a != null && this.a.h() != 2 && !this.c(241222)) {
            this.a(false);
        }
        if (n2 == 13) {
            if (this.a instanceof oa) {
                ((oa)this.a).a(string);
            }
            return;
        }
        if (n2 == 35) {
            al al3 = ag.b().a("Ch\u00fa \u00fd", "T\u00e0i kho\u1ea3n ch\u01b0a \u0111\u01b0\u1ee3c x\u00e1c th\u01b0c. Vui l\u00f2ng x\u00e1c th\u1ef1c, \u0111\u1ec3 s\u1eed d\u1ee5ng c\u00e1c t\u00ednh n\u0103ng n\u00e0y!", "X\u00e1c th\u1ef1c", 123523, "\u0110\u00f3ng", 12350, 1);
            al3.a((a)ag.b());
            ag.b().a(al3, false);
            return;
        }
        if (n2 == 12) {
            al al4 = ag.b().a("Ch\u00fa \u00fd", string == null ? "R\u01b0\u01a1ng \u0111\u00e3 \u0111\u1ea7y. Vui l\u00f2ng b\u1ecf b\u1edbt \u0111\u1ed3 ra ho\u1eb7c mua th\u00eam ng\u0103n ch\u1ee9a" : string, "\u0110\u00f3ng", 305, 1);
            al4.a((a)ag.b());
            ag.b().a(al4, false);
            return;
        }
        string = string == null ? " - L\u1ed7i kh\u00f4ng x\u00e1c \u0111\u1ecbnh." : string;
        this.a(11111, false);
        int n3 = 12350;
        String string2 = "\u0110\u00f3ng";
        int n4 = 12353;
        String string3 = "H\u1ed7 tr\u1ee3";
        if (n2 == 18) {
            n3 = 12352;
            string2 = "N\u1ea1p KEN";
            n4 = 12350;
            string3 = "\u0110\u00f3ng";
        } else if (n2 == 1 || n2 == 2) {
            if (this.a != null && this.a.h() == 5 && (n2 = !((nx)this.a).e() && n2 != 2 ? 0 : 1) != 0) {
                string = "Kh\u00f4ng th\u1ec3 k\u1ebft n\u1ed1i t\u1edbi m\u00e1y ch\u1ee7. Vui l\u00f2ng ki\u1ec3m tra c\u1ea5u h\u00ecnh v\u00e0 k\u1ebft n\u1ed1i GPRS";
                n3 = 12349;
                string2 = "Tho\u00e1t";
                n4 = 12354;
            }
        } else if (n2 == 11) {
            ((oa)this.a).A();
        } else if (n2 == 0 && this.a != null) {
            if (this.a.h() == 8) {
                ((of)this.a).f();
            } else if (this.a.h() == 1) {
                ((oa)this.a).x();
            }
        }
        al al5 = ag.b().a("Th\u00f4ng tin", string, string2, n3, string3, n4, 1);
        al5.a((a)ag.b());
        ag.b().a(al5, false);
    }

    public final void w() {
        al al2 = this.a(" ", "T\u00e0i kho\u1ea3n c\u1ee7a b\u1ea1n v\u1eeba \u0111\u01b0\u1ee3c \u0111\u0103ng nh\u1eadp \u1edf m\u1ed9t n\u01a1i kh\u00e1c.", "\u0110\u00f3ng", 12347, 1);
        al2.a(false);
        al2.b(241227);
        al2.a(this);
        this.a(al2);
    }

    public final void x() {
        if (this.d(11111) != null) {
            this.v();
        }
        this.a("M\u00e3 h\u00f3a d\u1eef li\u1ec7u...", (il)null);
    }

    public final void y() {
        if (this.d(11111) != null) {
            this.v();
        }
        this.a("Chuy\u1ec3n m\u00e1y ch\u1ee7...", (il)null);
    }

    public final void z() {
        al al2 = this.a("Ch\u00fa \u00fd", "M\u1ea5t k\u1ebft n\u1ed1i v\u1edbi m\u00e1y ch\u1ee7!", "\u0110\u00f3ng", 12347, 1);
        al2.a(this);
        this.a(al2, false);
    }

    public final void a(String object, do object2, String object3, long l2) {
        if (this.a != null && this.a.h() == 1) {
            long l3 = l2;
            String string = object3;
            object3 = object2;
            object2 = object;
            object = (oa)this.a;
            if (((oa)object).k != null) {
                long l4 = l3;
                object = ((oa)object).k;
                if (((ol)object).p instanceof os) {
                    ((os)((ol)object).p).b((String)object2, (do)object3, string, l4);
                }
            }
        }
    }

    public final void b(String object, do object2, String object3, long l2) {
        if (this.a != null && this.a.h() == 1) {
            long l3 = l2;
            String string = object3;
            object3 = object2;
            object2 = object;
            object = (oa)this.a;
            if (((oa)object).k != null) {
                long l4 = l3;
                object = ((oa)object).k;
                if (((ol)object).p instanceof os) {
                    ((os)((ol)object).p).a((String)object2, (do)object3, string, l4);
                }
            }
        }
    }

    public final void a(String object, String string) {
        if (this.a != null && this.a.h() == 1) {
            String string2 = string;
            string = object;
            object = (oa)this.a;
            if (((oa)object).k != null) {
                object = ((oa)object).k;
                if (((ol)object).p instanceof os) {
                    ((os)((ol)object).p).a(string, string2);
                }
            }
        }
    }

    public final void A() {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).g();
        }
    }

    public final void a(ns[] nsArray) {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).a(nsArray);
        }
        this.v();
    }

    public final void B() {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).t();
        }
        this.v();
    }

    public final void a(lh lh2, byte by2) {
        block16: {
            block18: {
                block19: {
                    block17: {
                        if (by2 == 103) {
                            com.mg.sq.a.b(lh2);
                            this.v();
                            return;
                        }
                        if (by2 == 1) {
                            go.k = lh2;
                        } else if (by2 == 101) {
                            if (this.a != null && this.a.h() == 1) {
                                byte by3 = by2;
                                lh lh3 = lh2;
                                bf bf2 = (oa)this.a;
                                if (bf2.k != null) {
                                    bf2 = bf2.k;
                                    if (by3 == 101 && ((ol)bf2).p instanceof os) {
                                        ((os)((ol)bf2).p).b(lh3);
                                    }
                                }
                            }
                        } else if (by2 == 99) {
                            this.v();
                            go.k = lh2;
                            com.mg.sq.a.G();
                            return;
                        }
                        if (this.a == null) break block16;
                        if (this.a.h() != 3) break block17;
                        break block18;
                    }
                    if (this.a.h() != 1) break block19;
                    if (by2 == 104 || by2 == 106) {
                        com.mg.sq.a.b(lh2);
                        this.v();
                        return;
                    }
                    break block16;
                }
                if (this.a.h() != 2) break block16;
                if (gr.k) {
                    ob.d();
                }
            }
            ks.a().b(go.k.g);
        }
    }

    public final void a(df[] dfArray, df[] dfArray2, df[] dfArray3, df[] dfArray4, df[] dfArray5, df[] dfArray6) {
        if (this.a != null && this.a.h() == 2) {
            if (gr.k) {
                ob.d();
            }
            nw nw2 = new nw();
            nw2.a(dfArray, dfArray2, dfArray3, dfArray4, dfArray5, dfArray6);
            ag.b().a(nw2);
            co.b().a("lv1", -1);
            co.b().e();
            ((a)ag.b()).v();
        }
    }

    public final void a(String object, do[] object2) {
        if (this.a != null && this.a.h() == 1) {
            do[] doArray = object2;
            object2 = object;
            object = (oa)this.a;
            if (((oa)object).k != null) {
                object = ((oa)object).k;
                if (((ol)object).p instanceof os) {
                    ((os)((ol)object).p).a((String)object2, doArray);
                }
            }
        }
    }

    public final void b(String string, String string2) {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).a(string, string2);
        }
    }

    public final void a(String string, String string2, int n2) {
        al al2 = this.d(241222);
        if (al2 != null) {
            gy.a(string, string2, n2);
            return;
        }
        if (this.a != null && this.a instanceof og) {
        }
    }

    public final void a(String string, int n2, int n3) {
        if (this.a != null) {
            if (this.a.h() == 4) {
                if (n3 == 0 && !string.equals("M99")) {
                    go.x = n2;
                    go.w = string;
                    ag.b().a(5, new Object[]{string});
                }
                return;
            }
            if (this.a.h() == 1) {
                ((oa)this.a).a(string, n2, n3);
            }
        }
    }

    public final void a(jn jn2, int[] nArray, int n2, int[] nArray2, int n3) {
        pa.a().a(jn2, nArray, n2, nArray2, n3);
    }

    public final void a(String string, jm[] jmArray) {
        pa.a().a(string, jmArray);
    }

    public final void a(jo[] joArray, String string) {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).a(joArray, string);
        }
    }

    public final void b(jo[] joArray, String string) {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).a(joArray, string);
        }
    }

    public final void c(jo[] object, String joArray) {
        if (this.a != null && this.a.h() == 1) {
            jo[] joArray2 = joArray;
            joArray = object;
            object = (oa)this.a;
            if (go.w.equals(joArray2)) {
                object = object.k;
                if (object.p instanceof om) {
                    ((om)object.p).a(joArray);
                }
            }
        }
    }

    public final void a(lh lh2, lh lh3, boolean bl2, byte[] byArray, byte[] byArray2, byte[] byArray3, int n2, byte by2, byte by3) {
        lh[] lhArray = new lh[1];
        lh[] lhArray2 = lhArray;
        lhArray[0] = lh2;
        lh[] lhArray3 = new lh[1];
        lh[] lhArray4 = lhArray3;
        lhArray3[0] = lh3;
        hs.k = go.k.J;
        oq.i = lhArray2;
        oq.j = lhArray4;
        oq.n = by2;
        oq.o = by3;
        oq.k = byArray;
        oq.l = byArray2;
        oq.m = byArray3;
        oq.p = n2;
        hs.k = go.k.J;
        hs.l = go.k.G;
        hs.m = go.k.H;
        if (this.a != null && this.a.h() == 1) {
            if (by3 == 9) {
                ((oa)this.a).a(lh2, lh3, bl2);
                return;
            }
            ((oa)this.a).a(lh2, lh3, bl2, byArray, byArray2, byArray3, n2, by2, by3);
        }
    }

    public final void a(ns ns2, boolean bl2) {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).a(ns2, bl2);
        }
    }

    public final void a(lw[] lwArray) {
        go.r = lwArray;
        if (this.a != null) {
            if (this.a.h() == 2) {
                ks.a().b(go.e);
                return;
            }
            if (this.a.h() == 3) {
                ks.a().b(go.e);
            }
        }
    }

    public final void b(String object) {
        if (this.a != null) {
            if (this.a.h() == 4) {
                ((a)ag.b()).v();
                object = ag.b().a("", (String)object, "\u0110\u00f3ng", 2, 1);
                ((am)object).a(ag.b());
                ag.b().a((al)object);
                return;
            }
            if (this.a.h() == 1) {
                Object object2 = object;
                object = (oa)this.a;
                object = ((oa)object).k;
                if (((ol)object).p == null || !(((ol)object).p instanceof om)) {
                    return;
                }
                ((a)ag.b()).v();
                ((om)((ol)object).p).e(true);
                ((om)((ol)object).p).l.a(0);
                ((om)((ol)object).p).m.a();
                ((om)((ol)object).p).o = false;
                object2 = ag.b().a("Ch\u00fa \u00fd", (String)object2, "\u0110\u00f3ng", 0, 1);
                ((am)object2).a((bf)object);
                ag.b().a((al)object2, false);
            }
        }
    }

    public final void e(int n2, int n3) {
        ah ah2 = ag.b();
        go.a(n2, n3);
        if (ah2.c(241202)) {
            int n4 = n3;
            n3 = n2;
            hh hh2 = (hh)ah2.d(241202);
            ((a)ag.b()).v();
            hh2.h(n3, n4);
        }
        if (v.O) {
            if (this.s == null) {
                this.s = new cp("useitem");
            }
            this.s.b();
        }
    }

    public final void c(String string) {
        this.f.a(new gi(string));
    }

    public final void a(long l2) {
        if (l2 <= 2000L && go.s > l2) {
            go.t = true;
        }
        go.s = l2;
        Object object = new ft(0L);
        ((ft)object).a(l2);
        this.b().a((at)object);
        if (this.c(241210)) {
            object = (ia)this.d(241210);
            ((ia)object).a(l2);
        }
    }

    public final void C() {
        al al2 = this.a("Th\u00f4ng tin", "Phi\u00ean b\u1ea3n hi\u1ec7n t\u1ea1i ch\u01b0a h\u1ed7 tr\u1ee3 t\u00ednh n\u0103ng n\u00e0y!", "\u0110\u00f3ng", 2, 1);
        al2.a(this);
        this.a(al2, false);
    }

    public static void d(String object) {
        he he2 = new he();
        bb bb2 = new bb("Nh\u1eadp nick", 157, bx.d);
        bb2.a_(10, 10);
        he2.a(bb2);
        int n2 = 10 + (bb2.f() + 3);
        ff ff2 = new ff(null, 100, 2);
        ff2.a_(1);
        ff2.a(10, n2, 154, 20);
        ff2.d(true);
        ff2.c((String)object);
        he2.a(ff2);
        n2 += ff2.f() + 5;
        ff2 = new ff("\u0110\u1eb7t C\u01b0\u1ee3c:", 4, 4);
        ff2.a(10, n2 += bx.d.a(), bx.d.a("99999"), 18);
        ff2.d(false);
        ff2.a_(5);
        he2.a(ff2);
        bb2 = new bb(".000 KEN", 157, bx.d);
        bb2.a_(10 + ff2.e() + 1, n2 + 2);
        he2.a(bb2);
        object = new ey("Cho xem", true);
        ((aq)object).a(bb2.c() + bx.d.a(".000 KEN0"), ff2.d() + 2, 13, 13);
        ((aq)object).d(false);
        ((aq)object).a_(6);
        he2.a((aq)object);
        bb2 = new bb("Ki\u1ec3u quy\u1ebft \u0111\u1ea5u:", 157, bx.d);
        bb2.a_(10, n2 += ff2.f() + 5);
        he2.a(bb2);
        object = new ey("1 chi\u1ec1u", false);
        ((aq)object).a(10, n2 += bb2.f() + 3, 13, 13);
        ((aq)object).d(false);
        ((aq)object).a_(7);
        he2.a((aq)object);
        n2 += ((aq)object).f() + 5;
        object = new ey("Kh\u00f4ng ch\u01a1i Tuy\u1ec7t Chi\u00eau", false);
        ((aq)object).a(10, n2, 13, 13);
        ((aq)object).d(false);
        ((aq)object).a_(10);
        he2.a((aq)object);
        n2 += ((aq)object).f() + 5;
        object = ex.a("G\u1eedi", 102);
        ((ex)object).a((a)ag.b());
        ((aq)object).a_(2);
        int n3 = 87 - ((aq)object).e() >> 1;
        ((aq)object).a_(n3, n2);
        he2.a((aq)object);
        object = ex.a("H\u1ee7y", 101);
        ((ex)object).a((a)ag.b());
        ((aq)object).a_(3);
        n3 = (87 - ((aq)object).e() >> 1) + 87;
        ((aq)object).a_(n3, n2);
        he2.a((aq)object);
        int n4 = n2 += ((aq)object).f() + 10;
        he2.a(v.t - 174 >> 1, v.u - n4 >> 1, 174, n4);
        he2.a((a)ag.b());
        he2.b(-241249);
        bd bd2 = new bd("", 102);
        Object object2 = he2;
        ((am)object2).a(bd2, true);
        bd2 = new bd("", 101);
        object2 = he2;
        ((am)object2).b(bd2, true);
        ag.b().a(he2);
        object2 = new int[][]{{1, 1, 1, 1}, {1, 2, 1, 2}, {1, 6, 1, 4}, {2, 4, 2, 4}, {1, 6, 2, 6}, {6, 6, 6, 6}, {2, 7, 4, 7}, {6, 8, 6, 8}, {7, 8, 9, 9}, {7, 9, 8, 8}};
        he2.a((int[][])object2);
    }

    public static void e(String object) {
        object = com.mg.sq.a.a((String)object, "Nh\u1eadp nick", "G\u1eedi", 200, "H\u1ee7y", 101);
        ((am)object).b(-241249);
        ag.b().a((al)object);
    }

    public static he a(String object, String object2, String string, int n2, String string2, int n3) {
        he he2 = new he();
        object = new bb((String)object, 158, bx.d);
        ((aq)object).a_(5, 10);
        he2.a((aq)object);
        int n4 = 10 + (((aq)object).f() + (object2 != null ? bx.d.a() : 0) + 3);
        object2 = new ff((String)object2, 100, 2);
        ((aq)object2).a_(1);
        ((ff)object2).a(5, n4, 165, 20);
        ((ff)object2).d(true);
        he2.a((aq)object2);
        n4 += ((aq)object2).f() + 5;
        boolean bl2 = bx.d.a(string) >= bx.d.a(string2);
        ex ex2 = ex.a(string, n2);
        if (!bl2) {
            ex2.d(ex.a(string2, n3).e());
        }
        ex2.a_(2);
        int n5 = 87 - ex2.e() >> 1;
        ex2.a_(n5, n4);
        he2.a(ex2);
        ex2 = ex.a(string2, n3);
        if (bl2) {
            ex2.d(ex.a(string, n2).e());
        }
        ex2.a_(3);
        n5 = (87 - ex2.e() >> 1) + 87;
        ex2.a_(n5, n4);
        he2.a(ex2);
        n4 += ex2.f() + 10;
        he2.a(v.t - 175 >> 1, v.u - n4 >> 1, 175, n4);
        he2.a((a)ag.b());
        he2.b(241220);
        bd bd2 = new bd("", n2);
        he he3 = he2;
        ((am)he3).a(bd2, true);
        bd2 = new bd("", n3);
        he3 = he2;
        ((am)he3).b(bd2, true);
        return he2;
    }

    public static he a(String object, String object2, String object3, int n2, String string, int n3, int n4, bf bf2) {
        object2 = new he();
        object = new bb((String)object, 158, bx.d);
        ((aq)object).a_(5, 10);
        ((he)object2).a((aq)object);
        int n5 = 10 + (((aq)object).f() + 3);
        Object object4 = new hp(1);
        ff ff2 = new ff(null, 300, 2);
        ff2.a((hp)object4);
        ff2.f(true);
        ff2.a_(1);
        ff2.a(5, n5, 165, 20);
        ff2.d(true);
        ((he)object2).a(ff2);
        n5 += ff2.f() + 5;
        n5 += 2;
        if (oy.f != null) {
            fu fu2 = new fu(null, n4);
            fu2.a((175 - oy.e) / 2, n5, oy.e, oy.e);
            fu2.a(bf2);
            fu2.a_(4);
            ((he)object2).a(fu2);
            n5 += fu2.f() + 5;
        }
        n4 = bx.d.a((String)object3) >= bx.d.a(string) ? 1 : 0;
        object4 = ex.a((String)object3, n2);
        ((ex)object4).a(bf2);
        if (n4 == 0) {
            ((aq)object4).d(ex.a(string, n3).e());
        }
        ((aq)object4).a_(2);
        int n6 = 87 - ((aq)object4).e() >> 1;
        ((aq)object4).a_(n6, n5);
        ((he)object2).a((aq)object4);
        object4 = ex.a(string, n3);
        ((ex)object4).a(bf2);
        if (n4 != 0) {
            ((aq)object4).d(ex.a((String)object3, n2).e());
        }
        ((aq)object4).a_(3);
        n6 = (87 - ((aq)object4).e() >> 1) + 87;
        ((aq)object4).a_(n6, n5);
        ((he)object2).a((aq)object4);
        n5 += ((aq)object4).f() + 10;
        ff2.e(true);
        ((al)object2).a(v.t - 175 >> 1, v.u - n5 >> 1, 175, n5);
        ((am)object2).a(bf2);
        ((am)object2).b(241220);
        object3 = new bd("", n2);
        Object object5 = object2;
        ((am)object5).a((az)object3, true);
        object3 = new bd("", n3);
        object5 = object2;
        ((am)object5).b((az)object3, true);
        return object2;
    }

    public static void D() {
        String[] stringArray = new String[]{"- Thanh ki\u1ebfm \u0111\u1ec3 t\u1ea5n c\u00f4ng \u0111\u1ed1i ph\u01b0\u01a1ng.", "- Tr\u00e1i tim c\u00f3 t\u00e1c d\u1ee5ng h\u1ed3i sinh l\u1ef1c trong tr\u1eadn chi\u1ebfn.", "- Xo\u00e1y \u00e2m d\u01b0\u01a1ng gi\u00fap ph\u1ee5c h\u1ed3i n\u1ed9i l\u1ef1c trong tr\u1eadn \u0111\u1ea5u \u0111\u1ec3 s\u1eed d\u1ee5ng tuy\u1ec7t chi\u00eau.", "- Tr\u00e1i \u0111\u00e0o gi\u00fap ph\u1ee5c h\u1ed3i s\u1ee9c m\u1ea1nh trong tr\u1eadn chi\u1ebfn \u0111\u1ec3 nh\u00e2n \u0111\u00f4i l\u1ef1c t\u1ea5n c\u00f4ng cho m\u1ed9t l\u01b0\u1ee3t.", "- Gi\u1ecdt n\u01b0\u1edbc c\u00f3 t\u00e1c d\u1ee5ng t\u0103ng \u0111i\u1ec3m kinh nghi\u1ec7m \u0111\u1ec3 l\u00ean c\u1ea5p, nh\u01b0ng \u0111i\u1ec3m ch\u1ec9 b\u1eb1ng 1/2 ng\u00f4i sao.", "- Ng\u00f4i sao c\u00f3 t\u00e1c d\u1ee5ng t\u0103ng \u0111i\u1ec3m kinh nghi\u1ec7m \u0111\u1ec3 l\u00ean c\u1ea5p.", "- Th\u1ecfi v\u00e0ng \u0111\u1ec3 \u0111\u1ed5i KEN, khi \u0111\u1ee7 10.000 l\u01b0\u1ee3ng v\u00e0ng h\u1ec7 th\u1ed1ng s\u1ebd t\u1ef1 \u0111\u1ed9ng n\u1ea1p v\u00e0o t\u00e0i kho\u1ea3n c\u1ee7a nh\u00e2n v\u1eadt 10.000 KEN.", "", "- Ki\u1ebfm l\u1eeda c\u00f3 l\u1ef1c t\u1ea5n c\u00f4ng m\u1ea1nh h\u01a1n v\u00e0 l\u00e0m n\u1ed5 c\u00e1c bi\u1ec3u t\u01b0\u1ee3ng bao quanh n\u00f3. Ki\u1ebfm l\u1eeda c\u00f3 th\u1ec3 x\u1ebfp c\u00f9ng v\u1edbi ki\u1ebfm th\u01b0\u1eddng."};
        he he2 = new he();
        he2.a(new ba());
        he2.g(he2.j() - ba.a);
        aq aq2 = new gc("H\u01b0\u1edbng d\u1eabn", he2.i() - 20, bx.d);
        ((gc)aq2).e(true);
        aq2.a_(10, 10);
        ((gc)aq2).h(1);
        he2.a(aq2);
        aq2 = new ay(0);
        aq2.a(3, 32, he2.i() - 6, he2.j() - 35);
        aw aw2 = new aw();
        aw2.a((a)ag.b());
        aw2.e(true);
        ((ay)aq2).b(aw2);
        Image[] imageArray = new Image[9];
        int n2 = 0;
        while (n2 < 9) {
            if (n2 != 7) {
                imageArray[n2] = f.d("/chess" + n2);
                gn gn2 = new gn(imageArray[n2], stringArray[n2], aq2.e());
                aw2.a((Object)gn2);
            }
            ++n2;
        }
        he2.a(aq2);
        he2.a((a)ag.b());
        he2.a(new bd("\u0110\u00f3ng", 12350));
        ag.b().a(he2, false);
    }

    public static void a(String[] stringArray) {
        he he2 = new he();
        he2.a(new ba());
        he2.g(he2.j() - ba.a);
        aq aq2 = new gc("Nh\u1eefng t\u00ednh n\u0103ng m\u1edbi:", he2.i() - 20, bx.d);
        ((gc)aq2).e(true);
        aq2.a_(10, 10);
        ((gc)aq2).h(1);
        he2.a(aq2);
        aq2 = new gc("Phi\u00ean B\u1ea3n 0.18.0", he2.i() - 20, bx.d);
        ((gc)aq2).e(true);
        aq2.a_(10, 28);
        ((gc)aq2).h(1);
        he2.a(aq2);
        aq2 = new ay(0);
        aq2.a(3, 50, he2.i() - 6, he2.j() - 50 - 2);
        aw aw2 = new aw();
        aw2.a((a)ag.b());
        aw2.e(true);
        ((ay)aq2).b(aw2);
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (n2 != 8) {
                gn gn2 = new gn(null, stringArray[n2], aq2.e());
                aw2.a((Object)gn2);
            }
            ++n2;
        }
        he2.a(aq2);
        he2.a((a)ag.b());
        he2.a(new bd("\u0110\u00f3ng", 12350));
        ag.b().a(he2, false);
    }

    public static void b(String[] stringArray) {
        he he2 = new he();
        he2.a(new ba());
        he2.g(he2.j() - ba.a);
        aq aq2 = new gc("Th\u00f4ng b\u00e1o:", he2.i() - 20, bx.d);
        ((gc)aq2).e(true);
        aq2.a_(10, 10);
        ((gc)aq2).h(1);
        he2.a(aq2);
        aq2 = new ay(0);
        aq2.a(3, 28, he2.i() - 6, he2.j() - 28 - 2);
        aw aw2 = new aw();
        aw2.a((a)ag.b());
        aw2.e(true);
        ((ay)aq2).b(aw2);
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (n2 != 8) {
                gn gn2 = new gn(null, stringArray[n2], aq2.e());
                aw2.a((Object)gn2);
            }
            ++n2;
        }
        he2.a(aq2);
        he2.a((a)ag.b());
        he2.a(new bd("\u0110\u00f3ng", 12350));
        ag.b().a(he2, false);
    }

    public static String k(int n2) {
        he he2 = (he)((a)ag.b()).d(n2);
        if (he2 == null) {
            return "";
        }
        return ((ff)he2.e(1)).r().toLowerCase().trim();
    }

    public final void a(lh lh2, String string, long l2, String string2, boolean bl2, boolean bl3) {
        if (this.a != null && this.a.h() == 1) {
            r.a(string2);
            ((oa)this.a).a(lh2, string, l2, string2, bl2, bl3);
            return;
        }
        ks.a().a(false, string2, "Tui ch\u01b0a s\u1eb5n s\u00e0ng!!!");
    }

    public final void a(boolean bl2, String string) {
        gt gt2 = (gt)((a)ag.b()).d(-241209);
        String string2 = "";
        if (gt2 != null) {
            string2 = gt2.t();
            this.b(gt2, false);
        }
        if (bl2) {
            int n2 = r.d() - 1;
            while (n2 >= 0) {
                if (string2.equals(r.b(n2))) {
                    ks.a().a(bl2, string2, string);
                } else {
                    ks.a().a(false, (String)r.b(n2), string);
                }
                --n2;
            }
            r.a();
            this.l();
            this.a((String)null, (il)null);
            return;
        }
        ks.a().a(bl2, string2, string);
        r.a(r.d() - 1);
    }

    public final void f(String object) {
        object = new ie("\u0110ang khi\u00eau chi\u1ebfn \"" + (String)object + "\". Vui l\u00f2ng ch\u1edd...");
        ((am)object).a(this);
        ((am)object).a(new ba());
        bd bd2 = new bd("H\u1ee7y", 202);
        Object object2 = object;
        ((am)object2).b(bd2, true);
        ((am)object).b(-241249);
        ((ie)object).j(true);
        ((ie)object).e(30);
        this.a((al)object, false);
    }

    private static al x(String object) {
        object = new ie("\u0110ang m\u1eddi \"" + (String)object + "\" Giao d\u1ecbch. Vui l\u00f2ng ch\u1edd...");
        ((am)object).a(ag.b());
        ((am)object).a(new ba());
        bd bd2 = new bd("H\u1ee7y", 201);
        Object object2 = object;
        ((am)object2).b(bd2, true);
        ((am)object).b(241214);
        ((ie)object).j(true);
        ((ie)object).e(30);
        ag.b().a((al)object, false);
        return object;
    }

    public final al a(String object, String object2, String[] stringArray, int[] nArray, int n2) {
        try {
            n2 = v.t;
            int n3 = v.u;
            if (n2 > n3) {
                if (n2 >= 320) {
                    n2 = 320;
                }
            } else if (n2 >= 240) {
                n2 = 240;
            }
            n2 -= 20;
            n3 = 6;
            he he2 = new he();
            if (object != null && !((String)object).equals("")) {
                object = new gc((String)object, n2 - 10 - 10, bx.d);
                ((gc)object).e(true);
                ((aq)object).a_(10, 6);
                ((gc)object).h(1);
                he2.a((aq)object);
                n3 = 6 + (((gc)object).f() + 6);
            }
            object = new gc((String)object2, n2 - 10 - 10, bx.d);
            ((aq)object).a_(10, n3);
            ((gc)object).h(1);
            he2.a((aq)object);
            n3 += ((gc)object).f() + 6;
            if (stringArray != null && nArray != null) {
                int n4 = n2 / stringArray.length;
                object2 = ex.a(stringArray[0], nArray[0]);
                ((aq)object2).a_(2);
                int n5 = n4 - ((aq)object2).e() >> 1;
                ((aq)object2).a_(n5, n3);
                ((aq)object2).d(true);
                bd bd2 = new bd("", nArray[0]);
                he he3 = he2;
                ((am)he3).a(bd2, true);
                he2.a((aq)object2);
                if (nArray.length > 1) {
                    int n6 = bx.d.a(stringArray[0]) >= bx.d.a(stringArray[1]) ? 1 : 0;
                    if (n6 == 0) {
                        ((aq)object2).d(ex.a(stringArray[1], nArray[1]).e());
                        ((aq)object2).a_(n4 - ((aq)object2).e() >> 1, n3);
                    }
                    object2 = ex.a(stringArray[1], nArray[1]);
                    if (n6 != 0) {
                        ((aq)object2).d(ex.a(stringArray[0], nArray[0]).e());
                    }
                    ((aq)object2).a_(3);
                    n6 = (n4 - ((aq)object2).e() >> 1) + n4;
                    ((aq)object2).a_(n6, n3);
                    he2.a((aq)object2);
                    bd2 = new bd("", nArray[1]);
                    he he4 = he2;
                    ((am)he4).b(bd2, true);
                }
                n3 += ((aq)object2).f();
            }
            he2.a(v.t - n2 >> 1, v.u - (n3 += 10) >> 1, n2, n3);
            return he2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static void g(String object) {
        int n2 = v.t;
        int n3 = v.u;
        if (n2 > n3) {
            if (n2 >= 320) {
                n2 = 320;
            }
        } else if (n2 >= 240) {
            n2 = 240;
        }
        he he2 = new he();
        aq aq2 = new gc("Ch\u00fa \u00fd", (n2 -= 20) - 10 - 10, bx.d);
        ((gc)aq2).e(true);
        aq2.a_(10, 6);
        ((gc)aq2).h(1);
        he2.a(aq2);
        n3 = 6 + (((gc)aq2).f() + 6);
        aq2 = new gc((String)object, n2 - 10 - 10, bx.d);
        aq2.a_(10, n3);
        ((gc)aq2).h(1);
        he2.a(aq2);
        object = new ey("Kh\u00f4ng nh\u1eafc n\u1eefa", false);
        ((aq)object).a_(6);
        ((aq)object).a(16, n3 += ((gc)aq2).f() + 6, 13, 13);
        he2.a((aq)object);
        n3 += ((aq)object).f() + 6;
        int n4 = n2 / 2;
        aq2 = ex.a("C\u00f3", 303);
        aq2.d(ex.a("Kh\u00f4ng", 304).e());
        aq2.a_(2);
        int n5 = n4 - aq2.e() >> 1;
        aq2.a_(n5, n3);
        aq2.d(true);
        bd bd2 = new bd("", 303);
        he he3 = he2;
        ((am)he3).a(bd2, true);
        he2.a(aq2);
        aq2 = ex.a("Kh\u00f4ng", 304);
        aq2.a_(3);
        int n6 = (n4 - aq2.e() >> 1) + n4;
        aq2.a_(n6, n3);
        he2.a(aq2);
        bd2 = new bd("", 304);
        he he4 = he2;
        ((am)he4).b(bd2, true);
        int n7 = aq2.e();
        aq2 = (ex)he2.e(2);
        aq2.d(n7);
        aq2.b_(n4 - aq2.e() >> 1);
        n3 += aq2.f();
        he2.a(v.t - n2 >> 1, v.u - (n3 += 10) >> 1, n2, n3);
        he2.a((a)ag.b());
        he2.b(241221);
        ag.b().a(he2, false);
    }

    public static void E() {
        al al2 = ag.b().a("Th\u00f4ng tin", "\u0110\u00e3 g\u1eedi Me th\u00e0nh c\u00f4ng", "\u0110\u00f3ng", 2, 1);
        al2.a(ag.b());
        ag.b().a(al2, false);
    }

    private static void b(lh object) {
        object = new gt((lh)object, 1, false, false);
        ag.b().a((al)object, false);
    }

    public final void a(boolean bl2, String string, int n2, int[] nArray, int n3) {
        if (this.a != null && this.a.h() == 5) {
            ((nx)this.a).a(bl2, string, n2, nArray, n3);
        }
    }

    public final void a(int n2, String[] stringArray) {
        if (stringArray.length > 0) {
            pd.g(n2);
            l = stringArray;
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

    public static void F() {
        ag.b().a(9, true, false, null, null);
    }

    public final void a(ll[] llArray, lm[] lmArray, int n2, int n3) {
        go.a(llArray, lmArray, n2, n3);
        if (this.a.h() == 2) {
            ((ob)this.a).e();
        } else if (this.a.h() == 3) {
            ((nw)this.a).d();
        }
        this.v();
    }

    public static void G() {
        gy gy2 = new gy();
        ag.b().a(gy2);
    }

    public static al a(ll object, bf object2, String object3, int n2, String string, int n3) {
        object = new hg((ll)object);
        if (object2 != null) {
            ((am)object).a((bf)object2);
            object3 = new bd((String)object3, n2);
            object2 = object;
            ((am)object2).a((az)object3, true);
            object3 = new bd(string, n3);
            object2 = object;
            ((am)object2).b((az)object3, true);
        }
        ag.b().a((al)object, false);
        return object;
    }

    public static void a(bf bf2, String object, int n2, String string, int n3) {
        object = ag.b().a("Ch\u00fa \u00fd", "Th\u00f9ng \u0111\u1ed3 \u0111\u00e3 \u0111\u1ea7y. B\u1ea1n c\u00f3 ph\u1ea3i b\u1ecf b\u1edbt \u0111\u1ed3 \u0111i!", (String)object, n2, string, n3, 1);
        ((am)object).a(bf2);
        ((am)object).b(241209);
        ag.b().a((al)object, false);
    }

    public static String[] a(ll stringArray) {
        String[] stringArray2 = new String[10];
        int n2 = 0;
        if (stringArray.r.a > 0) {
            stringArray2[0] = "+ " + stringArray.r.a + " c\u01b0\u1eddng l\u1ef1c";
            ++n2;
        } else if (stringArray.r.a < 0) {
            stringArray2[0] = "- " + stringArray.r.a + " c\u01b0\u1eddng l\u1ef1c";
            ++n2;
        }
        if (stringArray.r.c > 0) {
            stringArray2[n2] = "+ " + stringArray.r.c + " n\u1ed9i l\u1ef1c";
            ++n2;
        } else if (stringArray.r.c < 0) {
            stringArray2[n2] = "- " + stringArray.r.c + " n\u1ed9i l\u1ef1c";
            ++n2;
        }
        if (stringArray.r.b > 0) {
            stringArray2[n2] = "+ " + stringArray.r.b + " th\u00e2n ph\u00e1p";
            ++n2;
        } else if (stringArray.r.b < 0) {
            stringArray2[n2] = "- " + stringArray.r.b + " th\u00e2n ph\u00e1p";
            ++n2;
        }
        if (stringArray.r.d > 0) {
            stringArray2[n2] = "+ " + stringArray.r.d + " th\u1ec3 l\u1ef1c";
            ++n2;
        } else if (stringArray.r.d < 0) {
            stringArray2[n2] = "- " + stringArray.r.d + " th\u1ec3 l\u1ef1c";
            ++n2;
        }
        if (stringArray.r.e > 0) {
            stringArray2[n2] = "+ " + stringArray.r.e + " s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        } else if (stringArray.r.e < 0) {
            stringArray2[n2] = "- " + stringArray.r.e + " s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        }
        if (stringArray.r.f > 0) {
            stringArray2[n2] = "+ " + stringArray.r.f + " ph\u00f2ng th\u1ee7";
            ++n2;
        } else if (stringArray.r.f < 0) {
            stringArray2[n2] = "- " + stringArray.r.f + " ph\u00f2ng th\u1ee7";
            ++n2;
        }
        if (stringArray.r.h > 0) {
            stringArray2[n2] = "+ " + stringArray.r.h + " n\u00e9 tr\u00e1nh";
            ++n2;
        } else if (stringArray.r.h < 0) {
            stringArray2[n2] = "- " + stringArray.r.h + " n\u00e9 tr\u00e1nh";
            ++n2;
        }
        if (stringArray.r.g > 0) {
            stringArray2[n2] = "+ " + stringArray.r.g + "% ch\u00ed m\u1ea1ng";
            ++n2;
        } else if (stringArray.r.g < 0) {
            stringArray2[n2] = "- " + stringArray.r.g + "% ch\u00ed m\u1ea1ng";
            ++n2;
        }
        if (stringArray.r.i > 0) {
            stringArray2[n2] = "+ " + stringArray.r.i + " sinh l\u1ef1c";
            ++n2;
        } else if (stringArray.r.i < 0) {
            stringArray2[n2] = "- " + stringArray.r.i + " sinh l\u1ef1c";
            ++n2;
        }
        if (stringArray.r.j > 0) {
            stringArray2[n2] = "+ " + stringArray.r.j + "% h\u1ea5p thu s\u00e1t th\u01b0\u01a1ng";
            ++n2;
        } else if (stringArray.r.j < 0) {
            stringArray2[n2] = "- " + stringArray.r.j + "% h\u1ea5p thu s\u00e1t th\u01b0\u01a1ng";
            ++n2;
        }
        if (stringArray.r.k > 0) {
            stringArray2[n2] = "+ " + stringArray.r.k + "% \u0111\u00e1nh xuy\u00ean gi\u00e1p";
            ++n2;
        } else if (stringArray.r.k < 0) {
            stringArray2[n2] = "- " + stringArray.r.k + "% \u0111\u00e1nh xuy\u00ean gi\u00e1p";
            ++n2;
        }
        if (stringArray.r.l > 0) {
            stringArray2[n2] = "+ " + stringArray.r.l + "% c\u1ea3n \u0111\u00f2n";
            ++n2;
        } else if (stringArray.r.l < 0) {
            stringArray2[n2] = "- " + stringArray.r.l + "% c\u1ea3n \u0111\u00f2n";
            ++n2;
        }
        if (stringArray.r.m > 0) {
            stringArray2[n2] = "+ " + stringArray.r.m + "% h\u1ed3i sinh";
            ++n2;
        } else if (stringArray.r.m < 0) {
            stringArray2[n2] = "- " + stringArray.r.m + "% h\u1ed3i sinh";
            ++n2;
        }
        if (stringArray.r.n > 0) {
            stringArray2[n2] = "+ " + stringArray.r.n + "% s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        } else if (stringArray.r.n < 0) {
            stringArray2[n2] = "- " + stringArray.r.n + "% s\u1ee9c t\u1ea5n c\u00f4ng";
            ++n2;
        }
        if (stringArray.r.o > 0) {
            stringArray2[n2] = "+ " + stringArray.r.o + "% sinh l\u1ef1c";
            ++n2;
        } else if (stringArray.r.o < 0) {
            stringArray2[n2] = "- " + stringArray.r.o + "% sinh l\u1ef1c";
            ++n2;
        }
        stringArray = new String[n2];
        System.arraycopy(stringArray2, 0, stringArray, 0, n2);
        return stringArray;
    }

    public final void b(String object, int n2, int n3) {
        ll ll2 = null;
        if (go.l != null) {
            int n4 = 0;
            while (n4 < go.l.length) {
                if (go.l[n4].c.equals(object)) {
                    go.l[n4].p = go.l[n4].q;
                    ll2 = go.l[n4];
                    break;
                }
                ++n4;
            }
            n4 = 0;
            while (n4 < go.m.length) {
                if (go.m[n4].a == n2) {
                    go.m[n4].g = n3;
                    break;
                }
                ++n4;
            }
        }
        if (this.a != null && this.a.h() == 1) {
            object = (oa)this.a;
            ((oa)this.a).a = false;
            if (((oa)object).k != null) {
                ((oa)object).k.s = false;
            }
        }
        if (this.b != null && this.b.h() == 241202) {
            ((hh)this.b).a(ll2);
            ((hh)this.b).j(n3);
        }
    }

    public static hl a(bf bf2, az az2, az az3, az az4, ll object) {
        Object object2;
        lm[] lmArray = new lm[go.m.length];
        int n2 = 0;
        int n3 = 0;
        while (n3 < go.m.length) {
            if (go.m[n3].e == 1) {
                lmArray[n2] = go.m[n3];
                ++n2;
            }
            ++n3;
        }
        lm[] lmArray2 = new lm[n2];
        System.arraycopy(lmArray, 0, lmArray2, 0, n2);
        int n4 = 1;
        int n5 = lmArray2.length;
        while (n4 < n5) {
            n2 = n4;
            while (n2 > 0 && lmArray2[n2 - 1].f > lmArray2[n2].f) {
                object2 = lmArray2[n2];
                lmArray2[n2] = lmArray2[n2 - 1];
                lmArray2[n2 - 1] = object2;
                --n2;
            }
            ++n4;
        }
        object2 = new hl("B\u00faa s\u1eeda ch\u1eefa: ", lmArray2);
        ((hl)object2).a((ll)object);
        az az5 = az2;
        object = object2;
        ((am)object).a(az5, true);
        ((hl)object2).b(az2);
        az5 = az3;
        object = object2;
        ((am)object).b(az5, true);
        ((hl)object2).a(az4);
        ((am)object2).a(bf2);
        ag.b().a((al)object2);
        return object2;
    }

    public final void H() {
        ks.a().k();
        this.a((String)null, (il)null);
    }

    public final void I() {
        ks.a().j();
        this.a((String)null, (il)null);
    }

    public final void J() {
        ks.a().v();
        this.a((String)null, (il)null);
    }

    public final void h(String string) {
        ks.a().r(string);
        this.a((String)null, (il)null);
    }

    public final void a(lf[] object) {
        if (this.c(241210)) {
            this.e(241210);
        }
        object = new ia((lf[])object);
        ag.b().a((al)object, false);
        this.v();
    }

    public final void a(int n2, lq[] lqArray) {
        al al2 = this.d(241210);
        if (al2 != null) {
            ((ia)al2).a(n2, lqArray);
        }
    }

    public final void a(String[] stringArray, int[] nArray) {
        if (this.c(241210)) {
            ia ia2 = (ia)this.d(241210);
            ia2.a(stringArray, nArray);
        }
    }

    public final void a(int[] nArray, int[] nArray2) {
        if (this.c(241210)) {
            ia ia2 = (ia)this.d(241210);
            ia2.a(nArray, nArray2);
        }
    }

    public final void i(String string) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).c(string);
        }
        this.v();
    }

    public final void j(String string) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).a(string);
        }
    }

    public final void a(lm lm2) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).a(lm2);
        }
        this.v();
    }

    public final void k(String object) {
        if (this.c(241207) || this.a.h() != 1 || gr.l) {
            ks.a().a(false);
            String string = object;
            object = this;
            ((ah)object).f.a(new gi("V\u1eeba t\u1ef1 \u0111\u1ed9ng t\u1eeb ch\u1ed1i giao d\u1ecbch t\u1eeb @" + string));
            return;
        }
        ((oa)this.a).b((String)object);
    }

    public final void a(ll[] llArray, lm[] lmArray, int n2) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).a(llArray, lmArray, n2);
        }
        this.v();
    }

    public final void l(String object) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).b((String)object);
        }
        this.v();
        object = ag.b().a("Ch\u00fac m\u1eebng", "Giao d\u1ecbch th\u00e0nh c\u00f4ng!", "\u0110\u00f3ng", 12350, 1);
        ((am)object).a(this);
        ag.b().a((al)object, false);
    }

    public final void b(ll ll2) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).a(ll2);
        }
    }

    public final void a(lm lm2, int n2) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).a(lm2, n2);
        }
    }

    public final void l(int n2) {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).a(n2, true);
        }
    }

    public final void K() {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).d();
        }
        this.v();
    }

    public final void m(String object) {
        this.a(241214, false);
        object = ag.b().a("", String.valueOf(object) + " Kh\u00f4ng mu\u1ed1n giao d\u1ecbch v\u1edbi b\u1ea1n!", "\u0110\u00f3ng", 12350, 1);
        ((am)object).a(this);
        ag.b().a((al)object, false);
    }

    public final void n(String string) {
        ag.b().a(8, true, false, null, new Object[]{string});
        this.a(241214, false);
    }

    public static lh a(lh lh2) {
        jz jz2 = jp.a(lh2.g);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        jz2.a(lh2.h + lh2.l, lh2.j + lh2.m, lh2.i + lh2.n, lh2.k + lh2.o);
        int n11 = 0;
        while (n11 < lh2.D.length) {
            lb lb2 = lh2.D[n11].r;
            if (lb2 != null) {
                n2 += lb2.a;
                n3 += lb2.d;
                n4 += lb2.b;
                n5 += lb2.c;
                n6 += lb2.e;
                n6 += jz2.c() * lb2.n / 100;
                n7 += lb2.g;
                n8 += lb2.f;
                n9 += lb2.h;
                n10 += lb2.i;
            }
            ++n11;
        }
        jz2.a(lh2.h + n2 + lh2.l, lh2.j + n4 + lh2.m, lh2.i + n5 + lh2.n, lh2.k + n3 + lh2.o);
        lh2.r = jz2.a() + n10;
        lh2.A = jz2.e() + n9;
        lh2.C = jz2.g() + n7;
        lh2.z = jz2.d() + n8;
        lh2.B = jz2.f();
        lh2.x = jz2.b() + n6;
        lh2.y = jz2.c() + n6;
        return lh2;
    }

    public static al a(lm object, bf object2) {
        object = new hl("Chi Ti\u1ebft", (lm)object);
        ((am)object).b(241215);
        ((am)object).a((a)ag.b());
        bd bd2 = null;
        object2 = object;
        ((am)object2).a(bd2, true);
        bd2 = new bd("", 204);
        object2 = object;
        ((am)object2).b(bd2, true);
        ((am)object).a(new bd("\u0110\u00f3ng", 204));
        ag.b().a((al)object);
        return object;
    }

    public final void L() {
        ks.a().p();
        this.a((String)null, (il)null);
    }

    public final void o(String string) {
        this.v();
        if (this.a != null && this.a.h() == 10) {
            ((oe)this.a).a(string);
        }
    }

    public final void c(String object, String string) {
        if (this.a != null) {
            if (this.a.h() == 8) {
                ((of)this.a).a(string, (byte)1, bx.d);
                return;
            }
            if (this.a.h() == 1) {
                String string2 = string;
                string = object;
                object = (oa)this.a;
                if (((oa)object).k != null) {
                    object = ((oa)object).k;
                    if (((ol)object).p != null && ((ol)object).p.b() == 2) {
                        ((oq)((ol)object).p).a(string, string2);
                    }
                }
            }
        }
    }

    public static void p(String object) {
        if (go.e.equals(object)) {
            object = ag.b().a("", "B\u1ea1n kh\u00f4ng th\u1ec3 giao d\u1ecbch v\u1edbi ch\u00ednh b\u1ea1n! vui l\u00f2ng ki\u1ec3m tra l\u1ea1i", "\u0110\u00f3ng", 12350, 1);
            ((am)object).a((a)ag.b());
            ag.b().a((al)object, false);
            return;
        }
        com.mg.sq.a.x((String)object);
        ks.a().j((String)object);
    }

    public final void a(String string, byte by2) {
        if (string == null) {
            return;
        }
        if (this.a.h() == 1) {
            boolean bl2;
            byte by3 = 0;
            String string2 = string;
            bf bf2 = (oa)this.a;
            if (bf2.k != null) {
                bf2 = bf2.k;
                if (((ol)bf2).p != null && ((ol)bf2).p.b() == 1) {
                    ((om)((ol)bf2).p).a(string2, by3);
                    bl2 = true;
                } else {
                    bl2 = false;
                }
            } else {
                bl2 = false;
            }
            if (!bl2) {
                this.q(string);
            }
        } else {
            i = string;
            j = 0;
        }
        this.v();
    }

    public final void q(String object) {
        object = this.a("", (String)object, "\u0110\u00f3ng", 205, 1);
        ((am)object).a(this);
        this.a((al)object, false);
    }

    public final void M() {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).u();
        }
    }

    public static String b(long l2) {
        String string = l2 < 0L ? "?  KEN" : String.valueOf(i.a(l2, ".")) + "  KEN";
        return string;
    }

    public final void r(String string) {
        this.a((String)null, (il)null);
        ks.a().f(string);
    }

    public final void b(String string, int n2) {
        ks.a().a(string, (byte)n2);
        this.a((String)null, (il)null);
    }

    public final void N() {
        if (m != null && o) {
            this.m(99004);
            return;
        }
        al al2 = this.a("Th\u00f4ng tin", "Vui l\u00f2ng \u0111\u0103ng nh\u1eadp Ola \u0111\u1ec3 s\u1eed d\u1ee5ng t\u00ednh n\u0103ng n\u1ea1p KEN", "\u0110\u00f3ng", 12350, "H\u1ed7 tr\u1ee3", 12353, 1);
        al2.a(this);
        this.a(al2, false);
    }

    public final void c(String[] object) {
        if (this.a.h() == 1) {
            String[] stringArray = object;
            object = (oa)this.a;
            if (object.k != null) {
                object = object.k;
                if (object.p != null && object.p.b() == 1) {
                    ((om)object.p).a(stringArray);
                }
            }
        }
    }

    public final void d(String[] stringArray) {
        hh hh2 = (hh)this.d(241202);
        if (hh2 != null) {
            hh2.a(stringArray);
        }
    }

    public final void O() {
        this.a((String)null, (il)null);
    }

    public final void P() {
        if (this.a != null && this.a.h() == 8) {
            ((of)this.a).e();
        }
        this.v();
    }

    public final void a(int n2, dh[] dhArray) {
        if (this.a != null && this.a.h() == 1) {
            ((oa)this.a).a(n2, dhArray);
        }
        this.v();
    }

    public final void Q() {
        ks.a().c(0);
        this.a((String)null, (il)null);
    }

    public final void R() {
        ks.a().c(1);
        this.a((String)null, (il)null);
    }

    public final void s(String object) {
        this.l();
        object = this.a(null, (String)object, "\u0110\u00f3ng", 208, 1);
        ((am)object).a(this);
        this.a((al)object, false);
    }

    public final void a(String string, boolean bl2, boolean bl3) {
        if (string == null) {
            string = "";
        }
        go.h = bl2;
        go.i = bl3;
        go.g = string;
    }

    public final void a(String string, boolean bl2, boolean bl3, boolean bl4) {
        if (string == null) {
            string = "";
        }
        go.h = bl3;
        go.i = bl4;
        go.g = string;
        go.j = bl2;
        this.Y();
    }

    public final void b(String string, boolean bl2, boolean bl3, boolean bl4) {
        if (string == null) {
            string = "";
        }
        go.h = bl3;
        go.i = bl4;
        go.g = string;
        go.j = bl2;
        if (this.c(241216)) {
            hv hv2 = (hv)this.d(241216);
            hv2.a(string, bl2, bl3);
        }
        this.v();
    }

    private void Y() {
        hv hv2 = new hv(go.g, go.j, go.h);
        this.a(hv2);
        this.v();
    }

    public final void S() {
        ks.a().q();
        this.a((String)null, (il)null);
    }

    public static final void d(String object, String object2) {
        q = object2;
        object2 = new he();
        ((am)object2).b(241223);
        object = new bb((String)object, 163, bx.d);
        ((aq)object).a_(10, 10);
        ((he)object2).a((aq)object);
        int n2 = 10 + (((aq)object).f() + 3);
        aq aq2 = new ff(null, 100, 2);
        aq2.a_(1);
        ((ff)aq2).a(10, n2, 136, 20);
        ((ff)aq2).d(true);
        ((he)object2).a(aq2);
        int n3 = 10 + (aq2.e() + 4);
        Image image = f.d("/iconbt");
        fu fu2 = new fu(null, 12360);
        fu2.a((a)ag.b());
        fu2.a_(4);
        fu2.a(n3, n2, 20, 20);
        fu2.a(image);
        fu2.b(0, 0, 20, 20);
        fu2.a(22523, 9287679, 22523);
        ((he)object2).a(fu2);
        n2 += aq2.f() + 5;
        n3 = aq2.c() + 9;
        aq2 = new ex("G\u1eedi", 12359);
        aq2.a(n3, n2, 50, 18);
        aq2.a_(2);
        ((he)object2).a(aq2);
        aq2 = new ex("\u0110\u00f3ng", 12350);
        aq2.a(n3 += 68, n2, 50, 18);
        aq2.a_(3);
        ((he)object2).a(aq2);
        n3 = n2 += aq2.f() + 10;
        ((al)object2).a(v.t - 180 >> 1, v.u - n3 >> 1, 180, n3);
        ((am)object2).a((a)ag.b());
        ((am)object2).b(241223);
        bd bd2 = new bd("", 12359);
        Object object3 = object2;
        ((am)object3).a(bd2, true);
        bd2 = new bd("", 12350);
        object3 = object2;
        ((am)object3).b(bd2, true);
        ag.b().a((al)object2);
    }

    public static void a(String string, String string2, String[] stringArray, String[] object) {
        ((a)ag.b()).v();
        u = string2;
        w = object;
        int n2 = v.t < 320 ? v.t - 20 : 200;
        object = new he();
        aq aq2 = new bb(string != null ? string : "Vui l\u00f2ng ch\u1ecdn s\u1ed1 Ken mu\u1ed1n mua!", n2 - 20, bx.c);
        aq2.a_(10, 10);
        int n3 = 10 + (aq2.f() + 5);
        ((he)object).a(aq2);
        aq2 = new fx("Mua Ken");
        aq2.a_(1);
        ((fx)aq2).d(true);
        ((fx)aq2).a(10, n3, n2 - 20, 70);
        int n4 = 0;
        int n5 = stringArray.length;
        while (n4 < n5) {
            ey ey2 = new ey(stringArray[n4], false);
            ey2.h(n4);
            if (n4 == n5 - 1) {
                ey2.e(true);
            }
            ((fx)aq2).a(ey2);
            ++n4;
        }
        ((he)object).a(aq2);
        n3 += aq2.f();
        int n6 = n2 / 2 - 70;
        ex ex2 = new ex("N\u1ea1p", 300);
        ex2.a(n6, n3 += 5, 50, 20);
        ((he)object).a(ex2);
        n6 = ex2.c() + ex2.e() + 40;
        ex2 = new ex("\u0110\u00f3ng", 12350);
        ex2.a(n6, n3, 50, 20);
        ((he)object).a(ex2);
        n3 += ex2.f();
        ((al)object).a(v.t - n2 >> 1, v.u - (n3 += 10) >> 1, n2, n3);
        ((am)object).a((a)ag.b());
        ((am)object).b(0);
        bd bd2 = new bd("", 300);
        Object object2 = object;
        ((am)object2).a(bd2, true);
        bd2 = new bd("", 12350);
        object2 = object;
        ((am)object2).b(bd2, true);
        ag.b().a((al)object);
    }

    public static al t(String object) {
        object = ag.b().a("Th\u00f4ng tin", (String)object, "\u0110\u00f3ng", 12350, 1);
        ((am)object).a((a)ag.b());
        ag.b().a((al)object, false);
        return object;
    }

    public final boolean m(int n2) {
        if (n2 == 99006) {
            du.a().p();
            this.a((String)null, (il)null);
            return true;
        }
        if (m == null || !o || gr.b) {
            if (n2 == 99005) {
                ks.a().w();
                this.a((String)null, (il)null);
                return true;
            }
            if (n2 == 99004) {
                du.a().o();
                this.a((String)null, (il)null);
                return true;
            }
            return false;
        }
        if (n2 == 99005 || n2 == 99004) {
            if (n2 == 99005) {
                ks.a().w();
                this.a((String)null, (il)null);
                return true;
            }
            if (!gr.b) {
                this.t = n2;
                al al2 = this.a("Ch\u00fa \u00fd", "B\u1ea1n n\u00ean tham kh\u1ea3o nh\u1eefng \u0111i\u1ec1u kho\u1ea3n v\u1ec1 n\u1ea1p KEN.", "N\u1ea1p KEN", 302, "Xem", 301, 1);
                al2.a(this);
                this.a(al2, false);
                gr.b = true;
                return true;
            }
            if (n2 == 99004) {
                du.a().o();
                this.a((String)null, (il)null);
                return true;
            }
        }
        return false;
    }

    public static void e(String[] stringArray) {
        if (((a)ag.b()).c(241218)) {
            hm hm2 = (hm)((a)ag.b()).d(241218);
            hm2.a(stringArray);
            return;
        }
        hm hm3 = new hm(stringArray);
        ag.b().a(hm3, false);
    }

    public final void c(boolean bl2) {
        go.k.Z = bl2;
        if (this.c(241202)) {
            hh hh2 = (hh)this.d(241202);
            hh2.j(bl2);
        }
        if (this.a != null && this.a.h() == 1) {
            bf bf2 = (oa)this.a;
            if (bf2.k != null) {
                bf2 = bf2.k;
                if (((ol)bf2).p != null && ((ol)bf2).p.b() == 1) {
                    ((om)((ol)bf2).p).a(go.k, true);
                }
            }
        }
        this.v();
    }

    public final void d(boolean bl2) {
        go.k.aa = bl2;
        this.v();
        if (bl2) {
            go.k.e = (byte)2;
            return;
        }
        if (go.k.e == 2) {
            go.k.e = 0;
        }
    }

    public final void a(int[] object, String[] objectArray) {
        this.v();
        if (this.n(1)) {
            oa oa2 = (oa)this.a;
            String[] stringArray = objectArray;
            objectArray = (Object[])object;
            object = oa2;
            if (oa2.k != null) {
                object.k.a((int[])objectArray, stringArray);
            }
        }
    }

    public final void a(lr[] lrArray) {
        if (this.n(1)) {
            g.a(lrArray, new b(this));
            ((oa)this.a).a(lrArray);
        }
    }

    public final void a(String object, String string, long l2, int n2) {
        if (this.a != null && this.a.h() == 1) {
            int n3 = n2;
            long l3 = l2;
            String string2 = string;
            string = object;
            object = (oa)this.a;
            if (((oa)object).k != null) {
                ((oa)object).k.a(string, string2, l3, n3);
            }
        }
    }

    public final void a(byte[] byArray, byte[] byArray2) {
        this.v();
        ag.b().a(10, true, false, null, new Object[]{byArray, byArray2});
    }

    public final aq a(aw object, int n2) {
        if ((object = ((aw)object).i(n2)) instanceof gn) {
            return (gn)object;
        }
        return null;
    }

    public final void u(String string) {
        he he2 = (he)this.d(241223);
        ((ff)he2.e(1)).c(string);
    }

    public final void T() {
        if (this.a.h() == 2) {
            ct.a("[SQLOGIN] login thanh cong!");
            ks.a().a(go.e, (byte)1);
            return;
        }
        if (this.a.h() == 3) {
            ct.a("[SQLOGIN] login thanh cong!");
            ks.a().a(go.e, (byte)1);
        }
    }

    public final void a(String object, byte by2, byte by3, df df2, df df3, df df4) {
        Object object2;
        byte by4;
        df df5;
        df df6;
        if (go.k != null && go.k.b.equals(object)) {
            df6 = df4;
            df5 = df3;
            df df7 = df2;
            by4 = by3;
            byte by5 = by2;
            object2 = go.k;
            go.k.g = by5;
            ((lh)object2).f = by4;
            ((lh)object2).U = df7;
            ((lh)object2).V = df5;
            ((lh)object2).W = df6;
        }
        if (this.a != null && this.a.h() == 1) {
            String string = object;
            object = df4;
            df6 = df3;
            df5 = df2;
            byte by6 = by3;
            by4 = by2;
            String string2 = string;
            object2 = (oa)this.a;
            if (((oa)object2).k != null) {
                ol cfr_ignored_0 = ((oa)object2).k;
            }
        }
    }

    public final void a(String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        int n13;
        if (go.k != null && go.k.b.equals(string)) {
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
            lh lh2 = go.k;
            go.k.G = n8;
            lh2.h = n9;
            lh2.j = n10;
            lh2.i = n11;
            lh2.k = n12;
            lh2.l = n13;
            lh2.m = n18;
            lh2.n = n17;
            lh2.o = n16;
            lh2.p = n15;
            lh2.q = n14;
        }
        if (this.a != null && this.a.h() == 1) {
            n13 = n6;
            n12 = n5;
            n11 = n4;
            n10 = n3;
            n9 = n2;
            String string2 = string;
            oa oa2 = (oa)this.a;
            if (oa2.k != null) {
                ol ol2 = oa2.k;
                if (ol2.p != null && ol2.p.b() == 1) {
                    ((om)ol2.p).a(go.k, true);
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
        if (go.k != null && go.k.b.equals(string)) {
            n14 = n8;
            n13 = n7;
            n12 = n6;
            n11 = n5;
            n10 = n4;
            n9 = n3;
            int n15 = n2;
            object = go.k;
            go.k.s = n15;
            ((lh)object).r = n9;
            ((lh)object).J = n10;
            ((lh)object).M = n11;
            ((lh)object).N = n12;
            ((lh)object).H = n13;
            ((lh)object).I = n14;
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
            object = (oa)this.a;
            if (((oa)object).k != null) {
                ol ol2 = ((oa)object).k;
                if (ol2.p != null && ol2.p.b() == 1) {
                    ((om)ol2.p).a(go.k, false);
                }
            }
        }
    }

    public final void a(String object, ll[] llArray) {
        if (go.k != null && object.equals(go.k.b)) {
            mb.a();
            go.k.D = llArray;
        }
        if (this.a != null && this.a.h() == 1) {
            ll[] llArray2 = object;
            object = llArray;
            object = llArray2;
            object = (oa)this.a;
            if (object.k != null) {
                object = object.k;
                if (object.p != null && object.p.b() == 1) {
                    ((om)object.p).a(go.k, true);
                }
            }
        }
        if ((object = this.d(241202)) != null) {
            ((hh)object).w();
        }
    }

    public final void a(lv[] lvArray) {
        go.k.E = lvArray;
    }

    public final void a(String object, int n2, int n3, int n4, String string, String string2, String string3) {
        Object object2;
        int n5;
        int n6;
        String string4;
        String string5;
        if (go.k != null && go.k.b.equals(object)) {
            string5 = string3;
            string4 = string2;
            String string6 = string;
            n6 = n4;
            n5 = n3;
            int n7 = n2;
            object2 = go.k;
            go.k.K = n7;
            ((lh)object2).L = n5;
            ((lh)object2).ab = n6;
            ((lh)object2).S = string6;
            ((lh)object2).R = string4;
            ((lh)object2).Q = string5;
        }
        if (this.a != null && this.a.h() == 1) {
            String string7 = object;
            object = string3;
            string5 = string2;
            string4 = string;
            int n8 = n4;
            n6 = n3;
            n5 = n2;
            String string8 = string7;
            object2 = (oa)this.a;
            if (((oa)object2).k != null) {
                object = ((oa)object2).k;
                if (((ol)object).p != null && ((ol)object).p.b() == 1) {
                    ((om)((ol)object).p).a(go.k, true);
                }
            }
        }
    }

    public final void U() {
        this.v();
        al al2 = ((a)ag.b()).d(241201);
        if (al2 != null) {
            lh lh2 = go.k;
            al2 = (gy)al2;
            ((gy)al2).k.b(lh2);
        }
        if ((al2 = ((a)ag.b()).d(241203)) != null) {
            ((ib)al2).a(go.k);
        }
    }

    public final void a(String[] stringArray, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        int n2;
        int n3 = 0;
        while (n3 < stringArray.length) {
            n2 = 0;
            while (n2 < go.l.length) {
                if (go.l[n2].c.equals(stringArray[n3])) {
                    go.l[n2].p = nArray[n3];
                    go.l[n2].q = nArray2[n3];
                    break;
                }
                ++n2;
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < nArray3.length) {
            n2 = 0;
            while (n2 < go.m.length) {
                if (go.m[n2].a == nArray3[n3]) {
                    go.m[n2].g = nArray4[n3];
                    break;
                }
                ++n2;
            }
            ++n3;
        }
    }

    public final void v(String string) {
        if (this.c(241202)) {
            hh hh2 = (hh)this.d(241202);
            hh2.b(string);
            return;
        }
        com.mg.sq.a.t(string);
    }

    public final void c(ll ll2) {
        go.a(ll2);
        if (this.c(241202)) {
            hh hh2 = (hh)this.d(241202);
            hh2.b(ll2);
        }
    }

    public final void a(lm[] lmArray) {
        int n2 = 0;
        while (n2 < lmArray.length) {
            lm lm2 = lmArray[n2];
            go.a(lm2, lm2.g);
            if (this.c(241202)) {
                hh hh2 = (hh)this.d(241202);
                hh2.c(lm2);
            }
            ++n2;
        }
    }

    public final void f(int n2, int n3) {
        al al2 = this.d(241202);
        if (al2 != null) {
            ((hh)al2).j(n2, n3);
            return;
        }
        int n4 = 0;
        while (n4 < go.m.length) {
            if (go.m[n4].a == n2) {
                go.b(n2, go.m[n4].g - n3);
                return;
            }
            ++n4;
        }
    }

    public final void a(lt[] object) {
        go.k.ac = object;
        if (this.a != null && this.a.h() == 1) {
            lt[] ltArray = object;
            object = (oa)this.a;
            if (object.k != null) {
                object = object.k;
                if (object.p instanceof om) {
                    ((om)object.p).a(ltArray);
                }
            }
        }
    }

    public final void e(String object, String string) {
        if (this.c(241231)) {
            this.e(241231);
        }
        ox ox2 = null;
        d d2 = null;
        hh hh2 = (hh)this.d(241202);
        if (hh2 != null) {
            ox2 = hh2.y();
            d2 = hh2.z();
        }
        object = new ho((String)object, string, ox2, d2);
        this.a((al)object, false);
        this.v();
    }

    public final void a(String object, String string, String string2) {
        if (this.c(241232)) {
            this.e(241232);
        }
        ox ox2 = null;
        d d2 = null;
        hh hh2 = (hh)this.d(241202);
        if (hh2 != null) {
            ox2 = hh2.y();
            d2 = hh2.z();
        }
        object = new id((String)object, string, string2, ox2, d2);
        this.a((al)object, false);
        this.v();
    }

    public final void a(String object, ll[] llArray, lm[] lmArray, byte by2) {
        object = this.d(241231);
        if (object != null) {
            ((ho)object).a(llArray, lmArray, by2);
        }
    }

    public final void a(String string, String string2, byte by2, long l2) {
        al al2 = this.d(241231);
        if (al2 != null) {
            ((ho)al2).b(string, string2, by2, l2);
        }
    }

    public final void b(String string, String string2, byte by2, long l2) {
        al al2 = this.d(241231);
        if (al2 != null) {
            ((ho)al2).a(string, string2, by2, l2);
        }
    }

    public final void a(String string, byte by2, long l2) {
        al al2 = this.d(241231);
        if (al2 != null) {
            ((ho)al2).b(string, by2, l2);
        }
    }

    public final void a(int n2, int n3, String string, byte by2, long l2) {
        al al2 = this.d(241231);
        if (al2 != null) {
            ((ho)al2).a(string, by2, l2);
        }
    }

    public final void c(String string, String string2, byte by2, long l2) {
        al al2 = this.d(241232);
        if (al2 != null) {
            ((id)al2).a(string, string2, by2, l2);
        }
    }

    public final void b(String string, byte by2, long l2) {
        al al2 = this.d(241232);
        if (al2 != null) {
            ((id)al2).b(string, by2, l2);
        }
    }

    public final void b(int n2, int n3, String string, byte by2, long l2) {
        al al2 = this.d(241232);
        if (al2 != null) {
            ((id)al2).a(string, by2, l2);
        }
    }

    public final void d(String string, String string2, byte by2, long l2) {
        al al2 = this.d(241232);
        if (al2 != null) {
            ((id)al2).b(string, string2, by2, l2);
        }
    }

    public final void b(String object, ll[] llArray, lm[] lmArray, byte by2) {
        object = this.d(241232);
        if (object != null) {
            ((id)object).a(llArray, lmArray, by2);
        }
    }

    public final void w(String string) {
        this.v();
        ((a)ag.b()).a(241217, false);
        if (i.b(string)) {
            string = "N\u1ea1p th\u1ebb th\u00e0nh c\u00f4ng";
        }
        com.mg.sq.a.t(string);
    }

    public final void b(String string, String string2, String[] object, String[] object2) {
        this.v();
        ((a)ag.b()).v();
        u = string2;
        v = object2;
        int n2 = v.t < 320 ? v.t - 20 : 200;
        object2 = new he();
        aq aq2 = new bb(string, n2 - 20, bx.c);
        aq2.a_(10, 10);
        int n3 = 10 + (aq2.f() + 5 + bx.d.a());
        ((he)object2).a(aq2);
        aq2 = new gg("M\u1ea1ng \u0111i\u1ec7n tho\u1ea1i");
        aq2.a_(1);
        ((gg)aq2).a((Object[])object);
        ((gg)aq2).a(10, n3, n2 - 20, 20);
        ((gg)aq2).d(true);
        ((he)object2).a(aq2);
        object = new ff("S\u1ed1 Seri", 100, 2);
        ((ff)object).i(2);
        ((aq)object).a_(3);
        ((ff)object).a(10, n3 += aq2.f() + 5 + bx.d.a(), n2 - 20, 20);
        n3 += ((aq)object).f() + 5 + bx.d.a();
        ((he)object2).a((aq)object);
        object = new ff("M\u00e3 s\u1ed1 n\u1ea1p ti\u1ec1n", 100, 4);
        ((ff)object).i(3);
        ((aq)object).a_(2);
        ((ff)object).a(10, n3, n2 - 20, 20);
        ((he)object2).a((aq)object);
        int n4 = n2 / 2 - 70;
        aq2 = new ex("N\u1ea1p", 209);
        aq2.a(n4, n3 += ((aq)object).f() + 5, 50, 20);
        ((he)object2).a(aq2);
        n4 = aq2.c() + aq2.e() + 40;
        aq2 = new ex("\u0110\u00f3ng", 12350);
        aq2.a(n4, n3, 50, 20);
        ((he)object2).a(aq2);
        n3 += aq2.f();
        ((al)object2).a(v.t - n2 >> 1, v.u - (n3 += 10) >> 1, n2, n3);
        ((am)object2).a((a)ag.b());
        ((am)object2).b(241217);
        bd bd2 = new bd("", 209);
        Object object3 = object2;
        ((am)object3).a(bd2, true);
        bd2 = new bd("", 12350);
        object3 = object2;
        ((am)object3).b(bd2, true);
        ag.b().a((al)object2);
    }

    public final void V() {
        ct.a("[SQGameBoard]======================receiveQuitRoom=============");
        bf bf2 = (oa)this.a;
        if (bf2.k != null) {
            bf2 = bf2.k;
            if (((ol)bf2).p != null && ((ol)bf2).p.b() == 3) {
                ((os)((ol)bf2).p).r();
                return;
            }
            ((ol)bf2).r = null;
        }
    }

    public final void b(lr[] lrArray) {
        ((oa)this.a).b(lrArray);
    }

    public final void W() {
        ks.a().b();
        Object object = "Snb\u001fcn\u001fF`ld\u001fjgnmf\u001fahmg\u001fsgtnmf+\u001fjds\u001fmnh\u001funh\u001fl`x\u001fbgt\u001frd\u001fah\u001fmf`s";
        object = "Snb\u001fcn\u001fF`ld\u001fjgnmf\u001fahmg\u001fsgtnmf+\u001fjds\u001fmnh\u001funh\u001fl`x\u001fbgt\u001frd\u001fah\u001fmf`s".toCharArray();
        int n2 = 0;
        while (n2 < ((Object)object).length) {
            object[n2] = (char)(object[n2] + true);
            ++n2;
        }
        object = new String((char[])object);
        al al2 = ag.b().a(" ", (String)object, "\u0110\u00f3ng", 12349, 1);
        al2.a(false);
        al2.a(this);
        ag.b().a(al2);
    }

    public final void a(String string, long l2) {
        this.v();
        System.out.println("[receiveAddEquipToMarket]====================" + l2);
        ((a)ag.b()).e(241234);
        if (this.c(241202)) {
            hh hh2 = (hh)this.d(241202);
            hh2.a(string);
            return;
        }
        if (this.c(241235)) {
            this.d(241235);
            hq.t();
        }
    }

    public final void a(String string, ll[] llArray, lm[] lmArray) {
        this.v();
        if (this.c(241235)) {
            hq hq2 = (hq)this.d(241235);
            hq2.a(string, llArray, lmArray);
        }
    }

    public final void g(int n2, int n3) {
        block4: {
            this.v();
            this.e(241234);
            if (this.c(241202)) {
                hh hh2 = (hh)this.d(241202);
                int n4 = n3;
                n3 = n2;
                hh hh3 = hh2;
                ((a)ag.b()).e(-446456);
                ((a)ag.b()).e(-1122154);
                hh3.i(n3, n4);
                int n5 = 0;
                while (n5 < go.m.length) {
                    if (go.m[n5].a == n3) {
                        go.b(n3, n4);
                        break block4;
                    }
                    ++n5;
                }
                return;
            }
            if (this.c(241235)) {
                this.d(241235);
                hq.t();
            }
        }
    }

    public final void a(int n2, int n3, lq[] lqArray) {
        if (n2 != -1) {
            al al2 = this.d(241233);
            if (al2 != null) {
                ((hn)al2).a(n2, n3, lqArray);
                return;
            }
        } else {
            if (this.c(241233)) {
                this.e(241233);
            }
            if (this.c(241235)) {
                al al3 = this.d(241235);
                ((hq)al3).a(lqArray);
                return;
            }
            hq hq2 = new hq(lqArray);
            this.a(hq2, false);
            this.v();
        }
    }

    public final void b(String string, ll[] llArray, lm[] lmArray) {
        if (this.c(241233)) {
            hn hn2 = (hn)this.d(241233);
            hn2.a(string, llArray, lmArray);
        }
    }

    public final void b(lf[] object) {
        if (this.c(241233)) {
            this.e(241233);
        } else if (this.c(241235)) {
            this.e(241235);
        }
        object = new hn((lf[])object);
        this.a((al)object, false);
        this.v();
    }
}

