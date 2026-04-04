/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class hz
extends ap
implements b,
bj,
bt {
    private lf k;
    private bc l;
    private boolean m = false;
    private n n = new n(0, 0, z.r, z.s);
    private int o;
    private String[] p;
    private cx q = null;
    private n r = null;
    private cx s = null;
    private n t = null;
    private n u = null;
    private int v = 0;
    private int w = 2;
    private int x = 0;
    private String[] y = null;
    private byte z;
    private bd A;
    private bd B;
    private bd C;
    private bd D;
    private n E;
    private dh F;
    private n G;
    private cx H;
    private bv I;
    private boolean J;
    private fu K;
    private n L;
    private gi M;
    private int N = 0;
    private boolean O = false;
    private String[] P = new String[]{"Ch\u1ecdn tuy\u1ec7t chi\u00eau c\u00f3 bi\u1ec3u t\u01b0\u1ee3ng + m\u00e0u \u0111\u1ecf. B\u1ea5m ph\u00edm gi\u1eefa. R\u1ed3i ch\u1ecdn n\u00e2ng c\u1ea5p.", "B\u1ea5m ph\u00edm l\u00ean \u0111\u1ec3 t\u0103ng c\u1ea5p, n\u1ebfu sau khi t\u0103ng mu\u1ed1n gi\u1ea3m l\u1ea1i ph\u1ea5m ph\u00edm xu\u1ed1ng. Xong r\u1ed3i th\u00ec b\u1ea5m ph\u00edm gi\u1eefa", "Ch\u1ecdn menu tr\u00e1i > C\u1eadp nh\u1eadt. \u0110\u1ec3 c\u1eadp nh\u1eadt tuy\u1ec7t chi\u00eau cho nh\u00e2n v\u1eadt"};

    public hz() {
        super(1);
        this.b(241203);
        this.a(new be());
        this.f = 240;
        this.g = 320 - be.a;
        this.c = 0;
        this.d = 0;
        if (hz.t()) {
            this.f = z.r;
            this.g = 240 - be.a;
        }
        this.c = z.r >= this.f ? (z.r - this.f) / 2 : 0;
        this.d = z.s >= this.g ? (z.s - be.a - this.g) / 2 : 0;
        this.a(this.c, this.d, this.f, this.g);
        this.a(gr.j);
        hz hz2 = this;
        this.H = new cx(10, 22);
        hz2.q = new cx(hz2.H.a, hz2.H.b + 30 + 4);
        hz2.r = new n(hz2.H.a, 0, hz2.f - 30, 100);
        hz2.t = new n(12, 32, 218, 218);
        hz2.t.a = (hz2.f - hz2.t.c) / 2;
        hz2.u = new n(161, 10, 69, 14);
        if (hz.t()) {
            hz2.q = new cx(hz2.H.a + 30 + 4, hz2.H.b + 4);
            hz2.t.d = 138;
            hz2.u.a = hz2.f - hz2.u.c - 10;
        }
        hz2.s = new cx(hz2.u.a + hz2.u.c / 2, hz2.u.b + 1);
        hz2.G = new n(hz2.t.a, hz2.t.b + hz2.t.d + 4, hz2.t.c, 34);
        hz2.A = new ga(0, 0);
        hz2.B = new bh("\u0110\u00f3ng", 10);
        hz2.D = new ga(12, 2);
        hz2.C = new ga(13, 3);
        hz2.F = new dh(hz2.k.g / 2);
        hz2.F.a(hz2.k);
        hz2.F.i = new n(hz2.t.a, hz2.t.b, 218, 218);
        hz2.F.a(hz2);
        hz2.l = new bc();
        hz2.l.a(new n(hz2.t.a, hz2.t.b, hz2.t.c, hz2.t.d));
        hz2.l.b(hz2.F);
        hz2.l.h(1);
        hz2.a((byte)0);
        this.a(this);
        if (!cv.a.c(142)) {
            ob.h(142);
            gp.n = false;
        }
    }

    private static boolean t() {
        return com.mg.sq.a.i == 1;
    }

    public final void c(Graphics graphics) {
        cz.a(graphics, this.n);
        oz.d(graphics, this.c, this.d, this.f, this.g, z.ac);
        cz.b(graphics, new n(this.c, this.d, this.f, this.g));
        int n2 = this.u.a + this.c;
        int n3 = this.u.b + this.d;
        oz.b(graphics, n2, n3, this.u.c, this.u.d, 1070484, 16579764, 14542575);
        ca.d.c(true);
        ca.d.a(graphics, "\u0110i\u1ec3m K\u1ef9 N\u0103ng", n2 - 5, n3, 2);
        ca.d.c(false);
        ca.e.a(graphics, String.valueOf(this.F.y()), this.s.a + this.c, this.s.b + this.d, 1);
        switch (this.z) {
            case 0: {
                int n4 = this.d;
                int n5 = this.c;
                Graphics graphics2 = graphics;
                hz hz2 = this;
                if (hz2.l != null) {
                    hz2.l.a(graphics2, n5, n4);
                    hz2.l.c(true);
                }
                if (this.F.t().b == null) break;
                ca.d.c(true);
                int n6 = this.c + this.G.a;
                int n7 = this.d + this.G.b;
                ca.d.a(graphics, this.F.t().b, n6 + 8, n7 + 4, 0);
                ca.d.c(false);
                if (this.F.r() > 0) break;
                ca.d.a(graphics, "Ch\u01b0a h\u1ecdc", n6 + 8, n7 + ca.d.a() + 4, 0);
                break;
            }
            default: {
                int n8 = this.d;
                int n9 = this.c;
                Graphics graphics3 = graphics;
                hz hz3 = this;
                if (hz3.F.r() > 0) {
                    graphics3.drawImage(hz3.F.j[hz3.v], hz3.H.a + n9, hz3.H.b + n8, 0);
                } else {
                    graphics3.drawImage(hz3.F.k[hz3.v], hz3.H.a + n9, hz3.H.b + n8, 0);
                }
                ca.c.a(graphics3, String.valueOf(hz3.F.r()), hz3.H.a + n9 + hz3.F.j[hz3.v].getWidth(), hz3.H.b + n8 + hz3.F.j[hz3.v].getHeight() - ca.c.a(), 2);
                int n10 = hz3.q.a + n9;
                if (hz3.F.t().b != null) {
                    ca.d.c(true);
                    ca.d.a(graphics3, hz3.F.t().b, hz3.q.a + n9, hz3.q.b + n8, 0);
                    ca.d.c(false);
                }
                n8 = hz3.q.b + n8 + ca.d.a();
                if (hz3.F.r() <= 0) {
                    com.mg.sq.a.h.a(graphics3, "Ch\u01b0a h\u1ecdc", n10, n8, 0);
                    n8 += ca.d.a();
                } else {
                    ca.c.a(graphics3, " - Ti\u00eau hao n\u1ed9i l\u1ef1c: " + hz3.o / 9 + " th\u00e0nh n\u1ed9i l\u1ef1c", n10, n8, 0);
                    n8 += com.mg.sq.a.h.a();
                }
                oz.a(graphics3, hz3.c + 4, n8 + 4, hz3.f - 8);
                com.mg.sq.a.g.a(graphics3, "M\u00f4 T\u1ea3: ", hz3.c + hz3.f / 2, n8 += 8, 1);
                n8 += ca.d.a();
                if (hz3.y != null) {
                    ca.a(graphics3, ca.c, hz3.y, hz3.r.a + n9, n8, hz3.r.c, hz3.r.d, 0);
                    ca.c.a();
                }
                if (hz3.F.r() >= hz3.F.t().c.length) break;
                oz.b(graphics3, hz3.E.a, hz3.E.b, hz3.E.c, hz3.E.d, z.ac, false);
                n8 = hz3.E.b + 5;
                com.mg.sq.a.h.a(graphics3, hz3.F.r() > 0 ? "\u0110i\u1ec1u ki\u1ec7n t\u0103ng c\u1ea5p: " : "\u0110i\u1ec1u ki\u1ec7n \u0111\u01b0\u1ee3c h\u1ecdc: ", hz3.E.a + 4, n8, 0);
                ca.a(graphics3, com.mg.sq.a.h, hz3.p, hz3.E.a + 4, n8 += com.mg.sq.a.h.a(), hz3.E.c, hz3.E.d, 0);
                n9 = hz3.F.r() > 0 ? hz3.F.r() - 1 : 0;
                com.mg.sq.a.h.a(graphics3, " + Ti\u00eau hao: " + hz3.F.t().c[n9].c + " \u0111i\u1ec3m k\u1ef9 n\u0103ng", hz3.E.a + 4, n8 += hz3.p.length * com.mg.sq.a.h.a(), 0);
            }
        }
        if (this.K != null) {
            this.K.a(graphics, 0, 0);
        }
        if (!this.J) {
            cz.c(graphics, this.n);
        }
        if (this.I != null) {
            this.I.a(graphics, 0, 0);
        }
        if (this.M != null) {
            this.M.a(graphics);
        }
    }

    private void a(byte by2) {
        this.z = by2;
        switch (this.z) {
            case 0: {
                this.a(com.mg.sq.a.l);
                Object object = this.A;
                Object object2 = this;
                ((aq)object2).a((bd)object, true);
                object = object2 = new ga(1112, 3);
                object2 = this;
                ((aq)object2).b((bd)object, true);
                return;
            }
            case 1: {
                this.e(this.v);
                bd bd2 = null;
                hz hz2 = this;
                hz2.a(bd2, true);
                bd2 = null;
                hz2 = this;
                hz2.b(bd2, true);
                this.a(this.B);
            }
        }
    }

    protected final void g() {
        if (this.l != null) {
            this.l.n();
        }
        ++this.x;
        if (this.x > 3) {
            this.x = 0;
            this.w = -this.w;
        }
        if (this.I != null) {
            this.I.n();
            return;
        }
        if (this.K != null) {
            this.K.i();
        }
        if (this.M != null) {
            this.M.i();
        }
    }

    public final void a(lf lf2) {
        this.k = lf2;
        if (this.F != null) {
            this.F.a(lf2);
        }
    }

    private void e(int n2) {
        int n3;
        String string;
        int n4 = n2;
        dh dh2 = this.F;
        this.F.l = n4;
        int n5 = this.F.r();
        lu[] luArray = this.F.s();
        if (n5 == 0) {
            this.p = new String[2];
            this.p[0] = " + Nh\u00e2n v\u1eadt \u0111\u1ea1t c\u1ea5p: " + luArray[n2].c[n5].b;
            string = "";
            n3 = 0;
            while (n3 < luArray.length) {
                if (luArray[n3].a == luArray[n2].d) {
                    string = luArray[n3].b;
                    break;
                }
                ++n3;
            }
            this.p[1] = " + Ph\u1ea3i h\u1ecdc: " + string;
        } else if (this.F.r() < luArray[n2].c.length) {
            this.p = new String[1];
            this.p[0] = " + Nh\u00e2n v\u1eadt \u0111\u1ea1t c\u1ea5p: " + luArray[n2].c[n5].b;
        }
        this.o = n5 - 1 < 0 ? luArray[n2].c[0].d : luArray[n2].c[n5 - 1].d;
        string = n5 - 1 < 0 ? "N\u1ebfu sau khi h\u1ecdc s\u1ebd: " + luArray[n2].c[0].e : luArray[n2].c[n5 - 1].e;
        this.y = ca.a(string, this.r.c, ca.c);
        if (this.p != null) {
            n3 = (this.p.length + 2) * ca.c.a() + 4 + 4;
            this.E = new n(this.c + 7, this.d + this.g - n3 - 10, this.f - 15, n3);
        }
    }

    public final void c(int n2) {
        if (this.I != null) {
            boolean bl2;
            int n3 = n2;
            bd[] bdArray = this.I.a();
            hz hz2 = this;
            if (n3 == 94 && bdArray[0] != null && bdArray[0].b()) {
                if (hz2.i != null) {
                    hz2.i.d(-1, bdArray[0].a());
                }
                bl2 = true;
            } else if (n3 == 95 && bdArray[1] != null && bdArray[1].b()) {
                if (hz2.i != null) {
                    hz2.i.d(-1, bdArray[1].a());
                }
                bl2 = true;
            } else if (n3 == 93 && bdArray[2] != null && bdArray[2].b()) {
                if (hz2.i != null) {
                    hz2.i.d(-1, bdArray[2].a());
                }
                bl2 = true;
            } else {
                bl2 = false;
            }
            if (bl2) {
                z.c();
                return;
            }
            if (this.I.f(n2)) {
                return;
            }
            this.u();
            return;
        }
        if (this.z == 1) {
            switch (n2) {
                case 97: 
                case 99: {
                    --this.v;
                    if (this.v < 0) {
                        this.v = this.F.s().length - 1;
                    }
                    this.e(this.v);
                    return;
                }
                case 96: 
                case 98: {
                    ++this.v;
                    if (this.v >= this.F.s().length) {
                        this.v = 0;
                    }
                    this.e(this.v);
                }
            }
            return;
        }
        if (this.l != null) {
            this.l.f(n2);
        }
    }

    public final void d(int n2) {
        if (this.I != null && this.I.g(n2)) {
            return;
        }
    }

    public final int a(Object object, Object object2) {
        if (object instanceof lt) {
            object = (lt)object;
            object2 = (lt)object2;
            return ((lb)object2).a - ((lb)object).a;
        }
        object = (lu)object;
        object2 = (lu)object2;
        return ((lu)object2).a - ((lu)object).a;
    }

    private void a(bv object) {
        this.I = object;
        bd bd2 = this.C;
        object = this;
        ((aq)object).b(bd2, true);
        bd2 = this.D;
        object = this;
        ((aq)object).a(bd2, true);
        this.a(new bh("", 12));
    }

    private void u() {
        z.c();
        this.I = null;
        this.e(true);
        this.a(this.z);
        this.J = false;
        if (this.m) {
            this.m = false;
            this.a(new bh("Xong", 20));
        }
    }

    public final void d(int n2, int n3) {
        boolean bl2;
        int n4 = n3;
        hz hz2 = this;
        switch (n4) {
            case 1111: {
                com.mg.sq.a.E();
                if (hz2.F.u()) {
                    hz2.w();
                    bl2 = true;
                    break;
                }
                ak.b().a(hz2.h(), false);
                bl2 = true;
                break;
            }
            case 1112: {
                if (hz2.F.u()) {
                    hz2.w();
                    bl2 = true;
                    break;
                }
                ak.b().a(hz2.h(), false);
                bl2 = true;
                break;
            }
            case 1113: {
                hz2.v();
                bl2 = true;
                break;
            }
            case 1114: {
                hz2.a((byte)1);
                bl2 = true;
                break;
            }
            case 1115: {
                hz2.k(true);
                hz2.m = true;
                n n5 = hz2.L;
                hz hz3 = hz2;
                String string = hz3.F.r() - 1 < 0 ? "B\u1ea5m ph\u00edm l\u00ean \u0111\u1ec3 n\u00e2ng c\u1ea5p." : hz3.F.t().c[hz3.F.r() - 1].e;
                hz3.K = new fu();
                int n6 = hz3.c + 10;
                int n7 = hz3.d + n5.b + n5.d + 3;
                hz3.K.a(n6, n7, hz3.f - 20, hz3.g / 4);
                hz3.K.a(string);
                if (n7 + hz3.K.q() > hz3.d + hz3.g) {
                    n7 = hz3.d + n5.b - hz3.K.q() - 3;
                }
                hz3.K.c(z.r + hz3.K.p(), n7);
                hz3.K.a(n6, n7);
                if (hz2.O && hz2.N < 2) {
                    hz2.M = null;
                    hz2.M = new gi(hz2.P[hz2.N], hz2.f / 2 + hz2.c, hz2.d + hz2.g - 8, hz2.f - 20, 75, false);
                    ++hz2.N;
                }
                bl2 = true;
                break;
            }
            default: {
                bl2 = false;
            }
        }
        if (bl2) {
            this.u();
            return;
        }
        switch (n3) {
            case 9: {
                return;
            }
            case 0: {
                hz2 = this;
                bv bv2 = new bv();
                if (hz2.F.u()) {
                    bv2.a(new bu[]{new bu("C\u1eadp nh\u1eadt", 1113), new bu("Chi Ti\u1ebft", 1114), new bu("Nh\u00e2n V\u1eadt", 1111), new bu("\u0110\u00f3ng", 1112)});
                } else {
                    bv2.a(new bu[]{new bu("Chi Ti\u1ebft", 1114), new bu("Nh\u00e2n V\u1eadt", 1111), new bu("\u0110\u00f3ng", 1112)});
                }
                int n8 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n8, hz2.j() - bv2.f() + n8);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(hz2);
                hz2.a(bv2);
                hz2.k(false);
                if (hz2.O && hz2.N > 2) {
                    hz2.M = null;
                    hz2.O = false;
                }
                return;
            }
            case 10: {
                this.a((byte)0);
                return;
            }
            case 13: {
                this.u();
                return;
            }
            case 12: {
                this.I.f(95);
                return;
            }
            case 16: {
                ak.b().e(-4561239);
                this.u();
                ak.b().a(this.h(), false);
                return;
            }
            case 15: {
                ak.b().e(-4561239);
                this.v();
                this.u();
                com.mg.sq.a.q().e(this.h());
                return;
            }
            case 19: {
                this.u();
                return;
            }
            case 20: {
                this.k(false);
                this.a(com.mg.sq.a.l);
                return;
            }
            case 21: {
                this.I.f(95);
            }
        }
    }

    public final void a(int n2, int n3) {
        if (this.I != null) {
            boolean bl2;
            block7: {
                int n4 = n3;
                int n5 = n2;
                bd[] bdArray = this.I.a();
                hz hz2 = this;
                int n6 = 0;
                while (n6 < bdArray.length) {
                    if (bdArray[n6] != null && bdArray[n6].a(n5, n4)) {
                        if (hz2.i != null) {
                            hz2.i.d(-1, bdArray[n6].a());
                        }
                        bl2 = true;
                        break block7;
                    }
                    ++n6;
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
            if (this.I.c(n2, n3)) {
                return;
            }
            this.u();
            return;
        }
        if (this.z == 1) {
            return;
        }
        this.l.c(n2 -= this.c, n3 -= this.d);
    }

    public final void b(int n2, int n3) {
        if (this.I != null && this.I.f(n2, n3)) {
            return;
        }
        if (this.z == 1) {
            return;
        }
        this.l.c(n2, n3);
    }

    public final void c(int n2, int n3) {
        if (this.I != null && this.I.e(n2, n3)) {
            return;
        }
        if (this.z == 1) {
            return;
        }
        this.l.e(n2, n3);
    }

    private void v() {
        com.mg.sq.a.a(null, null);
        int[] nArray = new int[this.F.s().length];
        int n2 = 0;
        while (n2 < nArray.length) {
            nArray[n2] = this.F.j((int)n2).a;
            ++n2;
        }
        kq.a().a(nArray, this.F.x());
    }

    private void w() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111i\u1ec3m cho nh\u00e2n v\u1eadt. B\u1ea1n mu\u1ed1n c\u1eadp nh\u1eadt kh\u00f4ng?", "C\u00f3", 15, "Kh\u00f4ng", 16, 1);
        ap2.a(this);
        ap2.b(-4561239);
        ak.b().a(ap2, false);
    }

    private void k(boolean bl2) {
        if (this.F != null) {
            this.F.e(bl2);
        }
        if (!bl2) {
            this.K = null;
            if (this.O && this.N == 2) {
                this.M = new gi(this.P[this.N], 10, z.s - be.a - 8, 200, 70, false);
                this.M.f(10);
                this.M.a(10);
                this.M.a(new n(this.M.n() + 9, this.M.o() + 9, this.M.p() - 20, this.M.q() - 20));
                this.M.c(true);
                ++this.N;
            }
        }
    }

    public final void j(boolean n2) {
        if (gp.q && gr.j.G < 10) {
            ob.h(145);
            gp.q = false;
            n2 = 0;
            while (n2 < gr.o.length) {
                if (this.F.i(n2)) {
                    this.O = true;
                    this.N = 0;
                    cx cx2 = this.F.h(n2);
                    this.M = new gi(this.P[this.N], 10 + this.c, cx2.b - 10 + this.d, this.f - 20, 70, false);
                    this.M.f(10 + this.c);
                    this.M.a(cx2.a + 15);
                    if (this.M.o() < 0) {
                        this.M.g(cx2.b + 35 + this.d);
                        this.M.a(true);
                    }
                    this.M.a(new n(this.M.n() + 9, this.M.o() + 9, this.M.p() - 20, this.M.q() - 20));
                    ++this.N;
                    return;
                }
                ++n2;
            }
        }
    }

    public final void b(au object, int n2) {
        int n3 = this.F.i(n2);
        n n4 = this.F.a();
        object = this;
        this.L = n4;
        bv bv2 = new bv();
        if (n3 != 0) {
            bv2.a(new bu[]{new bu("N\u00e2ng c\u1ea5p", 1115), new bu("Chi Ti\u1ebft", 1114), new bu("\u0110\u00f3ng", 1112)});
        } else {
            bv2.a(new bu[]{new bu("Chi Ti\u1ebft", 1114), new bu("\u0110\u00f3ng", 1112)});
        }
        n3 = ((ap)object).c + n4.a + (n4.c - bv2.e()) / 2;
        int n5 = ((ap)object).d + n4.b + n4.d;
        if (n5 + bv2.f() > ((ap)object).d + ((aq)object).g) {
            n5 = ((ap)object).d + ((aq)object).g - bv2.f();
        }
        n3 = n3 < ((ap)object).c ? ((ap)object).c : (n3 + bv2.e() > ((ap)object).c + ((aq)object).f ? ((ap)object).c + ((aq)object).f - bv2.e() : n3);
        bv2.a_(z.r + bv2.e(), n5);
        bv2.d(n3, n5);
        bv2.a((bj)object);
        bv2.a_(1);
        super.a(bv2);
        ((hz)object).J = true;
        if (((hz)object).N == 1 && ((hz)object).M != null) {
            ((hz)object).M.g(((hz)object).M.o() + (((hz)object).M.a() ? 70 : 0));
            ((hz)object).M.a(new n(((hz)object).M.n() + 9, ((hz)object).M.o() + 9, ((hz)object).M.p() - 20, ((hz)object).M.q() - 20));
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au object, int n2, int n3) {
        if (this.F.q()) {
            if (this.K != null) {
                Object object2 = object = this.F.r() - 1 < 0 ? "Ch\u01b0a h\u1ecdc" : this.F.t().c[this.F.r() - 1].e;
                n n4 = this.L;
                object = this;
                ((hz)object).K.a((String)object2);
                int n5 = ((ap)object).c + 10;
                int n6 = ((ap)object).d + n4.b + n4.d + 3;
                if (n6 + ((hz)object).K.q() > ((ap)object).d + ((aq)object).g) {
                    n6 = ((ap)object).d + n4.b - ((hz)object).K.q() - 3;
                }
                ((hz)object).K.a(n5, n6);
                if (((hz)object).K.o() != n6) {
                    ((hz)object).K.g(n6);
                }
                return;
            }
        } else {
            this.v = n3;
        }
    }
}

