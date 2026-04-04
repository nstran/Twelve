/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class od
extends ar
implements aj,
bj,
bt {
    private boolean a;
    private n b;
    private bc c;
    private fg d = null;
    private bc k;
    private fg l = null;
    private n m;
    private n n;
    private bd o;
    private bd p;
    private bd q;
    private Image r;
    private ff s;
    private int[] t = new int[2];
    private int[] u = new int[2];
    private int v = 0;
    private ex w;
    private ex x;
    private int y = 0;
    private int z = 6;
    private String A = "";
    private cx B;
    private cx C;
    private n D;
    private bv E;
    private boolean F;
    private hf G;
    private dg H;
    private cx I;
    private cx J;
    private cx K;
    private int L = 0;
    private int M;
    private boolean N = false;
    private boolean O = false;
    private boolean P = false;
    private boolean Q = false;
    private lk R;
    private ff S;
    private a T = new a();
    private int U;
    private au[] V;
    private byte[][] W;
    private byte X = 0;
    private ou Y;
    private int Z = 0;
    private boolean aa;
    private int ab = 0;
    private boolean ac;
    private int ad = 0;
    private ex ae;
    private int af = 0;
    private nn ag = new nn();
    private lx ah;
    private Object ai = new Object();
    private int aj = 0;
    private String ak = null;
    private d al = new ie(new int[]{0xFF0000, 0xFFFF00});

    public od(String object) {
        super(8);
        this.Y = new ou(null);
        String string = object;
        object = this;
        this.A = string;
        this.a(false);
        this.d(false);
        this.a(new be());
        this.a(this);
        int n2 = z.r;
        int n3 = z.s - be.a;
        if (z.r >= z.s && z.r == 320) {
            n2 = 320;
            n3 = z.s - be.a;
            this.a = true;
        }
        int n4 = z.r >= n2 ? (z.r - n2) / 2 : 0;
        int n5 = z.s >= n3 ? (z.s - be.a - n3) / 2 : 0;
        this.b = new n(n4, n5, n2, n3);
        od od2 = this;
        this.r = f.d("/info/gold");
        n4 = 0;
        od2.I = new cx(7, 7);
        if (od2.a) {
            od2.K = new cx(od2.b.c / 2 - 7, od2.I.b);
            od2.m = new n(10, 25, 112, 78);
            od2.u[0] = 10;
            od2.u[1] = od2.m.b + od2.m.d + 7;
            od2.s = new ff(null, 6, 4);
            od2.s.a(10 + ca.d.a("Gi\u00e1 b\u00e1n: ") + 4, od2.u[1] - 3, 50, 18);
            od2.t[0] = od2.s.c() + od2.s.e() + 1;
            od2.t[1] = od2.s.d() + 3;
            n3 = ca.d.a("\u0110\u1ed3ng \u00fd(5)") + 30;
            od2.w = new ex("\u0110\u1ed3ng \u00fd", 6);
            od2.w.a(od2.s.c() + (od2.s.e() - n3) / 2, od2.s.d() + od2.s.f() + 5, n3, 18);
            n3 = ca.d.a("K. \u0111\u1ed3ng \u00fd") + 20;
            od2.x = new ex("K. \u0111\u1ed3ng \u00fd", 14);
            od2.x.a(od2.s.c() + (od2.s.e() - n3) / 2, od2.s.d() + od2.s.f() + 5, n3, 18);
            od2.B = null;
            od2.J = new cx(od2.f / 2 + 7, 7);
            od2.n = new n(184, 25, 112, 78);
            od2.C = new cx(od2.f / 2 + 7, od2.t[1]);
            od2.w.f();
            n3 = od2.w.d() + od2.w.f() + 5;
            od2.D = new n(7, n3, od2.f - 7 - 7, od2.b.d - n3 - 7);
        } else {
            n3 = 0;
            od2.K = new cx(od2.b.c - 7, od2.I.b);
            n4 = od2.b.c - 7 - 7;
            od2.m = new n(7, 25, n4, 44);
            od2.u[0] = 7;
            od2.u[1] = od2.m.b + od2.m.d + 7;
            od2.s = new ff(null, 6, 4);
            od2.s.a(7 + ca.d.a("Gi\u00e1 b\u00e1n: ") + 4, od2.u[1] - 3, 50, 18);
            od2.s.a(od2);
            od2.t[0] = od2.s.c() + od2.s.e() + 1;
            od2.t[1] = od2.s.d() + 3;
            n3 = ca.d.a("\u0110\u1ed3ng \u00fd(5)") + 20;
            od2.w = new ex("\u0110\u1ed3ng \u00fd", 6);
            od2.w.a(od2.t[0] + ca.d.a(".000 KEN") + 7, od2.s.d(), n3, 18);
            n3 = ca.d.a("K. \u0111\u1ed3ng \u00fd") + 10;
            od2.x = new ex("K. \u0111\u1ed3ng \u00fd", 14);
            od2.x.a(od2.t[0] + ca.d.a(".000 KEN") + 10, od2.s.d(), n3, 18);
            od2.B = new cx(7, od2.s.d() + od2.s.f() + 7);
            n3 = od2.B.b + ca.d.a() + 7;
            od2.n = new n(7, n3, n4, 44);
            od2.J = new cx(7, od2.n.b - ca.d.a() - 3);
            od2.C = new cx(7, od2.n.b + od2.n.d + 5);
            n3 = od2.C.b + ca.d.a() + 3;
            od2.D = new n(7, n3, n4, od2.b.d - n3 - 7);
        }
        od2.d = new fg(false);
        od2.d.a(od2);
        od2.d.i = new n(od2.m.a, od2.m.b, od2.m.c, od2.m.d);
        od2.d.d(od2.z, 0);
        od2.d.j = true;
        od2.c = new bc();
        od2.c.a(new n(od2.m.a, od2.m.b, od2.m.c, od2.m.d + 2));
        od2.c.b(od2.d);
        od2.c.h(2);
        od2.c.d(true);
        od2.d.i(0);
        od2.l = new fg(false);
        od2.l.a(od2);
        od2.l.i = new n(od2.n.a, od2.n.b, od2.n.c, od2.n.d);
        od2.l.d(od2.z, 0);
        od2.l.j = true;
        od2.k = new bc();
        od2.k.a(new n(od2.n.a, od2.n.b, od2.n.c, od2.n.d + 2));
        od2.k.b(od2.l);
        od2.c.h(2);
        od2.o = new ga(0, 0);
        od2.q = new ga(3, 2);
        od2.p = new ga(4, 3);
        od2.a(com.mg.sq.a.l);
        bd bd2 = od2.o;
        od od3 = od2;
        od3.a(bd2, true);
        bd2 = od2.p;
        od3 = od2;
        od3.b(bd2, true);
        od2.S = new ff("", 100, 2);
        od2.S.f(true);
        if (z.x) {
            int n6 = 2;
            if (od2.a) {
                od2.S.a(0, z.s - be.a, z.r - 40, be.a);
                n6 = 0;
            } else {
                od2.S.a(od2.D.a, od2.D.b + od2.D.d - 18, od2.D.c - 45, 18);
            }
            od2.S.a(false);
            od2.ae = new ex("G\u1eedi", -2);
            od2.ae.a(od2.S.e() + od2.S.c() + n6, od2.a ? z.s - 20 : od2.S.d(), 40, 18);
        } else {
            od2.S.a(od2.D.a, od2.D.b + od2.D.d - 18, od2.D.c, 18);
        }
        od2.S.a(false);
        od2.U = (od2.D.d - 6) / ca.c.a();
        od2.T = new a(od2.U);
        od2.V = new au[]{od2.c, od2.s, od2.w, od2.k};
        od od4 = od2;
        od2.W = new byte[4][4];
        if (od4.a) {
            od4.W[0] = new byte[]{3, -1, 1, 3};
            byte[] byArray = new byte[4];
            byArray[0] = 3;
            byArray[1] = -1;
            byArray[2] = 2;
            od4.W[1] = byArray;
            od4.W[2] = new byte[]{3, -1, 3, 1};
            byte[] byArray2 = new byte[4];
            byArray2[0] = -1;
            byArray2[3] = 2;
            od4.W[3] = byArray2;
        } else {
            od4.W[0] = new byte[]{1, -1, 1, -1};
            byte[] byArray = new byte[4];
            byArray[0] = 2;
            byArray[2] = 3;
            od4.W[1] = byArray;
            byte[] byArray3 = new byte[4];
            byArray3[0] = 3;
            byArray3[1] = 1;
            byArray3[2] = 3;
            od4.W[2] = byArray3;
            od4.W[3] = new byte[]{-1, 2, -1, 2};
        }
        od2.ah = new lx(od2.f, od2.g, 30, 10);
    }

    protected final void r() {
    }

    protected final void c() {
        if (this.E != null) {
            this.E.n();
            this.e(true);
        }
        if (this.af > 0) {
            if (this.ah != null) {
                this.ah.i();
            }
            this.e(true);
            if (this.ag != null && this.ag.c() && this.ag.g() >= 60L) {
                this.ag.b();
                ap ap2 = ak.b().a("", "Xin l\u1ed7i! k\u1ebft n\u1ed1i t\u1ea1m th\u1eddi b\u1ecb gi\u00e1n \u0111o\u1ea1n! Giao d\u1ecbch s\u1ebd b\u1ecb h\u1ee7y b\u1ecf. Vui l\u00f2ng th\u1eed l\u1ea1i sau!", "\u0110\u00f3ng", 15, 1);
                ap2.a(this);
                ak.b().a(ap2, false);
            }
            return;
        }
        int n2 = 0;
        while (n2 < this.V.length) {
            if (this.V[n2] != null) {
                this.V[n2].n();
            }
            ++n2;
        }
        if (this.S != null) {
            this.S.n();
            if (this.ae != null) {
                this.ae.n();
            }
        }
        if (this.Z > 0) {
            ex ex2 = (ex)this.V[2];
            if (this.ak == null) {
                this.ak = ex2.q();
            }
            ex2.a(String.valueOf(this.ak) + " (" + this.Z / 25 + ")");
            --this.Z;
            this.e(true);
            if (this.Z <= 0) {
                ex2.a(this.ak);
                this.ak = null;
            }
        }
        if (this.h) {
            --this.aj;
            if (this.aj <= 0) {
                this.e(false);
                this.aj = 0;
            }
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.aj = 5;
    }

    protected final void a(Graphics object) {
        int n2;
        int n3;
        if (this.h) {
            oz.a(object, this.b.a, this.b.b, this.b.c, this.b.d, z.ac, true);
            ca.d.c(true);
            ca.d.a((Graphics)object, gr.e, this.b.a + 7, this.b.b + 7, 0);
            ca.d.c(false);
            String string = com.mg.sq.a.b(gr.p);
            object.drawImage(this.r, this.K.a + this.b.a - ca.d.a(string), this.K.b + this.b.b + 2, 24);
            ca.d.a((Graphics)object, string, this.K.a + this.b.a, this.K.b + this.b.b, 2);
            ca.d.a((Graphics)object, "Ti\u1ec1n g\u1eedi: ", this.b.a + this.u[0], this.b.b + this.u[1], 0);
            ca.d.a((Graphics)object, ".000 KEN", this.t[0] + this.b.a, this.t[1] + this.b.b, 0);
            if (this.B != null) {
                oz.a(object, this.B.a + this.b.a, this.B.b + this.b.b, this.m.c);
            }
            if (this.a) {
                object.setColor(11972261);
                object.drawLine(this.b.a + this.b.c / 2, this.b.b + 4, this.b.a + this.b.c / 2, this.b.b + this.b.d - 8);
            }
            ca.d.c(true);
            ca.d.a((Graphics)object, this.A, this.J.a + this.b.a, this.J.b + this.b.b, 0);
            ca.d.c(false);
            oz.b(object, this.D.a + this.b.a, this.D.b + this.b.b, this.D.c, this.D.d, z.ac, true);
            n3 = 0;
            n2 = 0;
            while (n2 < this.T.d()) {
                ((hd)this.T.b(n2)).a((Graphics)object, this.D.a + 7 + this.b.a, this.D.b + 3 + n3 + this.b.b);
                n3 += ca.c.a();
                ++n2;
            }
        }
        if (this.V != null) {
            n2 = 0;
            n3 = this.V.length;
            while (n2 < n3) {
                this.V[n2].a((Graphics)object, this.b.a, this.b.b);
                ++n2;
            }
        }
        String string = "Ti\u1ec1n g\u1eedi: " + l.a(this.v, ".");
        string = this.v == 0 ? String.valueOf(string) + "KEN" : String.valueOf(string) + ".000 KEN";
        if (this.ac) {
            n3 = z.ac;
            if (this.ad % 2 == 0) {
                n3 = 15081495;
            }
            object.setColor(n3);
            object.fillRect(this.C.a + this.b.a, this.C.b + this.b.b - 2, ca.d.a(string), 18);
            --this.ad;
            if (this.ad == 0) {
                this.ac = false;
            }
        }
        ca.d.a((Graphics)object, string, this.C.a + this.b.a, this.C.b + this.b.b, 0);
        if (this.O || this.N) {
            oz.b(object, this.m.a + this.b.a, this.m.b + this.b.b, this.m.c, this.m.d, 16686236, true);
        } else if (this.c.m()) {
            oz.b(object, this.m.a + this.b.a, this.m.b + this.b.b, this.m.c, this.m.d, 7070703, true);
        }
        if (this.O || this.N) {
            oz.b(object, this.n.a + this.b.a, this.n.b + this.b.b, this.n.c, this.n.d, 16686236, true);
        } else if (this.k.m()) {
            oz.b(object, this.n.a + this.b.a, this.n.b + this.b.b, this.n.c, this.n.d, 7070703, true);
        } else if (this.aa) {
            n3 = z.ac;
            if (this.ab % 2 == 0) {
                n3 = 15081495;
            }
            oz.b(object, this.n.a + this.b.a, this.n.b + this.b.b, this.n.c, this.n.d, n3, true);
            --this.ab;
            if (this.ab == 0) {
                this.aa = false;
            }
        }
        this.k.a((Graphics)object, this.b.a, this.b.b);
        this.c.a((Graphics)object, this.b.a, this.b.b);
        if (this.E != null && this.F) {
            this.E.a((Graphics)object, 0, 0);
        }
        if (this.E != null && !this.F) {
            this.E.a((Graphics)object, 0, 0);
        }
        if (this.af > 0) {
            string = object;
            object = this;
            if (object.ah != null) {
                object.ah.a((Graphics)string);
            }
        }
    }

    protected final void d(Graphics graphics) {
        super.d(graphics);
        if (this.S.i()) {
            if (this.a) {
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(0, z.s - be.a, z.r, be.a);
            }
            this.S.a(graphics, this.b.a, this.b.b);
            if (this.ae != null) {
                this.ae.a(graphics, this.b.a, this.b.b);
            }
        }
    }

    protected final void a(int n2) {
        this.e(true);
        if (this.af > 0) {
            return;
        }
        if (this.E != null) {
            boolean bl2;
            int n3 = n2;
            bd[] bdArray = this.E.a();
            od od2 = this;
            if (n3 == 94 && bdArray[0] != null && bdArray[0].b()) {
                if (od2.i != null) {
                    od2.i.d(-1, bdArray[0].a());
                }
                bl2 = true;
            } else if (n3 == 95 && bdArray[1] != null && bdArray[1].b()) {
                if (od2.i != null) {
                    od2.i.d(-1, bdArray[1].a());
                }
                bl2 = true;
            } else if (n3 == 93 && bdArray[2] != null && bdArray[2].b()) {
                if (od2.i != null) {
                    od2.i.d(-1, bdArray[2].a());
                }
                bl2 = true;
            } else {
                bl2 = false;
            }
            if (bl2) {
                z.c();
                return;
            }
            if (this.E.f(n2)) {
                return;
            }
            this.v();
            return;
        }
        if (this.S.i()) {
            if (n2 == 95 || n2 == 110) {
                this.g();
                return;
            }
            if (!this.S.f(n2)) {
                this.u();
            }
            return;
        }
        if (!this.s.m()) {
            switch (n2) {
                case 95: 
                case 96: 
                case 97: 
                case 98: 
                case 99: {
                    break;
                }
                default: {
                    if (!this.S.i()) {
                        this.t();
                        this.S.f(n2);
                    }
                    return;
                }
            }
        }
        byte by2 = this.X;
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (!this.V[this.X].f(n2)) {
                    byte by3;
                    int n4 = n2 - 96;
                    od od3 = this;
                    if (n4 >= 0 && (by3 = od3.W[od3.X][n4]) >= 0) {
                        od3.X = by3;
                    }
                }
                if (by2 == this.X) break;
                this.V[this.X].d(true);
                this.V[by2].d(false);
                if (this.V[by2].equals(this.s)) {
                    this.y();
                }
                if (!(this.V[this.X] instanceof bc)) break;
                fg fg2 = (fg)((bc)this.V[this.X]).w();
                fg2.i(0);
                return;
            }
            case 95: {
                if (this.V[2].m()) {
                    this.i.d(0, ((ex)this.V[2]).a());
                    return;
                }
                this.V[this.X].f(n2);
                return;
            }
            default: {
                this.V[this.X].f(n2);
            }
        }
    }

    private void g() {
        String string = this.S.r();
        if (string == null || string.equals("")) {
            return;
        }
        kq.a().a(this.A, string);
        this.a(string, (byte)2, ca.d);
        this.S.c("");
    }

    private void f(int n2) {
        this.af = n2;
        this.ag.b();
        this.ag.a();
    }

    protected final void e(int n2, int n3) {
        this.e(true);
        if (this.af > 0) {
            return;
        }
        if (this.E != null) {
            boolean bl2;
            block16: {
                int n4 = n3;
                int n5 = n2;
                bd[] bdArray = this.E.a();
                od od2 = this;
                int n6 = 0;
                while (n6 < bdArray.length) {
                    if (bdArray[n6] != null && bdArray[n6].a(n5, n4)) {
                        if (od2.i != null) {
                            od2.i.d(-1, bdArray[n6].a());
                        }
                        bl2 = true;
                        break block16;
                    }
                    ++n6;
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
            if (this.E.c(n2, n3)) {
                return;
            }
            this.v();
            return;
        }
        byte by2 = this.X;
        n n7 = null;
        int n8 = 0;
        while (n8 < this.V.length) {
            n7 = this.V[n8].equals(this.c) ? new n(this.d.i.a + this.b.a, this.d.i.b + this.b.b, this.d.i.c, this.d.i.d) : (this.V[n8].equals(this.k) ? new n(this.l.i.a + this.b.a, this.l.i.b + this.b.b, this.l.i.c, this.l.i.d) : new n(this.V[n8].c() + this.b.a, this.V[n8].d() + this.b.b, this.V[n8].e(), this.V[n8].f()));
            if (n7.a(n2, n3)) {
                if (this.S.i()) {
                    this.u();
                }
                if (n8 != by2) {
                    this.X = (byte)n8;
                    this.V[by2].d(false);
                    if (this.V[by2].equals(this.s)) {
                        this.y();
                    }
                    this.V[this.X].d(true);
                }
                this.V[n8].c(n2 - this.b.a, n3 - this.b.b);
                if (this.V[n8] instanceof ex) {
                    this.d(-1, ((ex)this.V[n8]).a());
                }
                return;
            }
            n8 = (byte)(n8 + 1);
        }
        if (!this.S.i()) {
            this.t();
        } else if (this.ae != null && this.ae.h().a(n2 - this.b.a, n3 - this.b.b)) {
            this.g();
        }
        this.S.c(n2, n3);
    }

    private void t() {
        this.S.a(true);
        this.S.d(true);
        this.S.c("");
        bd bd2 = null;
        od od2 = this;
        od2.a(bd2, true);
        bd2 = null;
        od2 = this;
        od2.b(bd2, true);
        this.e(true);
    }

    private void u() {
        this.S.d(false);
        this.S.a(false);
        bd bd2 = this.o;
        od od2 = this;
        od2.a(bd2, true);
        bd2 = this.p;
        od2 = this;
        od2.b(bd2, true);
        if (this.X != 0) {
            this.V[this.X].d(false);
            this.X = 0;
            this.V[this.X].d(true);
            this.d.i(0);
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void d(int var1_1, int var2_8) {
        this.e(true);
        if (var2_8 == 15) {
            kq.a().m();
            ak.b().k();
            return;
        }
        if (this.af > 0) {
            return;
        }
        if (this.g(var2_8)) {
            this.v();
            return;
        }
        switch (var2_8) {
            case 4: {
                if (this.E != null) {
                    this.v();
                    return;
                }
                this.x();
                return;
            }
            case 0: {
                var1_2 = this;
                var2_9 = new bv();
                if (var1_2.N) {
                    var2_9.a(new bu("\u0110\u1ed3ng \u00fd", 11119));
                } else {
                    var2_9.a(new bu[]{var1_2.O != false ? new bu("K. \u0111\u1ed3ng \u00fd", 14) : new bu("\u0110\u1ed3ng \u00fd", 11119)});
                }
                var2_9.a(new bu("H\u1ee7y giao d\u1ecbch", 11117));
                var3_11 = var2_9.e() > var2_9.f() ? var2_9.e() : var2_9.f();
                var2_9.a_(-var3_11, var1_2.j() - var2_9.f() + var3_11);
                var2_9.d(0, z.s - be.a - var2_9.f());
                var2_9.a(var1_2);
                var1_2.b(var2_9);
                var5_16 = var1_2.p;
                var4_18 = var1_2;
                var4_18.b(var5_16, true);
                var5_16 = var1_2.q;
                var4_18 = var1_2;
                var4_18.a(var5_16, true);
                var2_9.c(com.mg.sq.a.l);
                return;
            }
            case 3: {
                this.c(95);
                return;
            }
            case 5: {
                return;
            }
            case 6: {
                this.g(11119);
                return;
            }
            case 1: {
                return;
            }
            case 1235: {
                ak.b().a(241202, false);
                return;
            }
            case 1234: {
                var4_19 /* !! */  = this.G;
                if (var4_19 /* !! */ .k.k == null) break;
                var4_19 /* !! */  = this.G;
                if (var4_19 /* !! */ .k.j == 1) ** GOTO lbl60
                var4_19 /* !! */  = this.G;
                if (var4_19 /* !! */ .k.j != 5) ** GOTO lbl79
lbl60:
                // 2 sources

                var4_19 /* !! */  = this.G;
                var1_3 = (lk)var4_19 /* !! */ .k.k;
                var4_19 /* !! */  = this.G;
                var2_10 = new dg(null, var1_3.b(), var4_19 /* !! */ .k.j, this.al);
                var3_12 = new gs(var2_10);
                var3_12.a(this);
                var3_12.e(var1_3.g);
                var5_17 = new bh("Xong", 12);
                var4_19 /* !! */  = var3_12;
                var4_19 /* !! */ .a(var5_17, true);
                var3_12.a(new bh("", 12));
                var5_17 = new bh("H\u1ee7y", 13);
                var4_19 /* !! */  = var3_12;
                var4_19 /* !! */ .b(var5_17, true);
                var3_12.b(-7524);
                var3_12.j(true);
                ak.b().a(var3_12, false);
                ak.b().a(241202, false);
                return;
lbl79:
                // 1 sources

                if (this.y >= this.z) break;
                this.M = 0;
                var4_19 /* !! */  = this.G;
                if (var4_19 /* !! */ .k.j != 0) break;
                var4_19 /* !! */  = this.G;
                if (!((lj)var4_19 /* !! */ .k.k).a()) break;
                var4_19 /* !! */  = this.G;
                kq.a().k(((lj)var4_19 /* !! */ .k.k).b);
                this.f(this.af + 1);
                ak.b().a(241202, false);
                return;
            }
            case 7: {
                ak.b().a(241212, false);
                return;
            }
            case 8: {
                var1_4 = this;
                var2_8 = gr.k.length;
                var2_8 -= gr.j.D.length;
                var3_13 = 0;
                while (var3_13 < gr.l.length) {
                    ++var2_8;
                    ++var3_13;
                }
                var3_14 = null;
                var4_20 = 0;
                while (var4_20 < var1_4.l.s()) {
                    var3_14 = (dg)var1_4.l.k(var4_20);
                    if (var3_14.k != null) {
                        var3_14.j;
                        ++var2_8;
                    }
                    ++var4_20;
                }
                var4_20 = 0;
                while (var4_20 < var1_4.d.s()) {
                    var3_14 = (dg)var1_4.d.k(var4_20);
                    if (var3_14.k != null) {
                        var3_14.j;
                        --var2_8;
                    }
                    ++var4_20;
                }
                v0 = var1_4.G != null ? var2_8 > var1_4.G.w() : var2_8 > gr.m;
                if (v0) {
                    com.mg.sq.a.a((bj)this, "H\u00e0nh Trang", 1110, "\u0110\u00f3ng", 110);
                    return;
                }
                this.P = true;
                kq.a().n();
                ak.b().a(-7894, false);
                this.f(this.af + 1);
                return;
            }
            case 9: {
                ak.b().a(-7894, false);
                return;
            }
            case 10: {
                ak.b().k();
                kq.a().m();
                ak.b().f(1);
                return;
            }
            case 11: {
                ak.b().a(-7946, false);
                return;
            }
            case 12: {
                if (!com.mg.sq.a.q().c(-7524) || (var1_5 = (gs)com.mg.sq.a.q().d(-7524)) == null) break;
                var2_8 = 0;
                var3_15 = null;
                var3_15 = null;
                this.R = var1_5.t();
                var1_6 = this.R.g = var1_5.u();
                var4_21 = 0;
                while (var4_21 < this.d.s()) {
                    var3_15 = (dg)this.d.k(var4_21);
                    if (var3_15.k != null && var3_15.j == 1 && (var3_15 = (lk)var3_15.k) != null && var3_15.a == this.R.a) {
                        var1_6 += var3_15.g;
                        var2_8 = 1;
                    }
                    ++var4_21;
                }
                if (var2_8 != 0 || this.y < this.z) {
                    this.M = 2;
                    kq.a().e(this.R.a, var1_6);
                    this.f(this.af + 1);
                }
                ak.b().a(-7524, false);
                return;
            }
            case 13: {
                ak.b().a(-7524, false);
                return;
            }
            case 14: {
                kq.a().o();
                this.f(this.af + 1);
                this.v();
                return;
            }
            case 16: {
                ak.b().a(-7426, false);
                return;
            }
            case 17: {
                ak.b().a(false);
                ak.b().f(1);
                return;
            }
            case 1110: {
                ak.b().a(241209, false);
                var1_7 = new hf(this.Y, this.al);
                ak.b().a(var1_7);
                return;
            }
            case 110: {
                ak.b().a(241209, false);
            }
        }
    }

    private void b(bv bv2) {
        this.E = bv2;
        if (bv2 != null) {
            if (this.s != null && this.s.m()) {
                this.y();
                return;
            }
            if (this.S.i()) {
                this.g();
            }
        }
    }

    private void v() {
        this.E = null;
        bd bd2 = this.p;
        od od2 = this;
        od2.b(bd2, true);
        bd2 = this.o;
        od2 = this;
        od2.a(bd2, true);
        this.e(true);
        this.F = false;
    }

    private boolean g(int n2) {
        switch (n2) {
            case 11119: {
                if (this.Z > 0) {
                    ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Vui l\u00f2ng ki\u1ec3m tra c\u00e1c m\u00f3n h\u00e0ng giao d\u1ecbch tr\u01b0\u1edbc khi \u0111\u1ed3ng \u00fd! vui l\u00f2ng ch\u1edb h\u1ebft th\u01a1i gian \u0111\u1ebfm ng\u01b0\u1ee3c.", "\u0110\u00f3ng", 16, 1);
                    ap2.a(this);
                    ap2.b(-7426);
                    ak.b().a(ap2, false);
                    return true;
                }
                this.d(-1, 8);
                return true;
            }
            case 11118: {
                if (this.G == null) {
                    this.G = new hf(this.Y, this.al);
                    this.G.a(this);
                    this.G.h(2);
                }
                ak.b().a(this.G);
                return true;
            }
            case 11117: {
                this.x();
                return true;
            }
            case 11116: {
                if (this.H != null && this.H.k != null) {
                    if (this.H.j == 0) {
                        com.mg.sq.a.a((lj)this.H.k, this, "", -23434, "\u0110\u00f3ng", 7);
                    } else {
                        com.mg.sq.a.a((lk)this.H.k, null);
                    }
                }
                return true;
            }
            case 11115: {
                if (this.H.k != null) {
                    if (this.H.j == 0) {
                        kq.a().l(((lj)this.H.k).b);
                        this.M = 0;
                    } else {
                        this.R = (lk)this.H.k;
                        n2 = 0;
                        int n3 = 0;
                        while (n3 < this.d.s()) {
                            lk lk2;
                            if ((((dg)this.d.k((int)n3)).j == 1 || ((dg)this.d.k((int)n3)).j == 5) && (lk2 = (lk)((dg)this.d.k((int)n3)).k) != null && lk2.a == this.R.a) {
                                n2 += lk2.g;
                            }
                            ++n3;
                        }
                        kq.a().e(this.R.a, n2 - this.R.g);
                        this.M = 2;
                    }
                }
                this.Q = true;
                this.f(this.af + 1);
                return true;
            }
        }
        return false;
    }

    public final void d() {
        --this.af;
        this.w();
        switch (this.M) {
            case 1: {
                if (this.Q) {
                    this.Q = false;
                    this.d.a(this.H);
                    this.G.b(this.H);
                    this.d.i(this.d.q());
                    --this.y;
                    return;
                }
                ++this.y;
                hf hf2 = this.G;
                this.d.a((Object)hf2.k);
                hf2 = this.G;
                this.G.a(hf2.k);
                this.d.i(this.d.q());
                return;
            }
            case 2: {
                if (this.Q) {
                    this.d.a(this.H);
                    this.G.b((lk)this.H.k);
                    this.G.t();
                    this.Q = false;
                    this.H = null;
                    --this.y;
                    return;
                }
                if (this.R != null) {
                    Object object = null;
                    int n2 = 0;
                    a a2 = new a();
                    int n3 = 0;
                    while (n3 < this.d.s()) {
                        if (((dg)this.d.k((int)n3)).j == 1 && (object = (lk)((dg)this.d.k((int)n3)).k) != null && ((lb)object).a == this.R.a) {
                            n2 += ((lk)object).g;
                            a2.a(this.d.k(n3));
                        }
                        ++n3;
                    }
                    n2 += this.R.g;
                    n3 = 0;
                    while (n3 < a2.d()) {
                        this.d.a((dg)a2.b(n3));
                        --this.y;
                        ++n3;
                    }
                    if (this.R.l <= 0) {
                        object = this.R.b();
                        this.R.b().g = n2;
                        dg dg2 = new dg(null, object, 1, this.al);
                        this.d.a((Object)dg2);
                        ++this.y;
                    } else {
                        n3 = n2 / this.R.l + (n2 % this.R.l > 0 ? 1 : 0);
                        int n4 = 0;
                        while (n4 < n3) {
                            object = this.R.b();
                            if (n2 >= this.R.l) {
                                ((lk)object).g = this.R.l;
                                n2 -= this.R.l;
                            } else if (n2 > 0) {
                                ((lk)object).g = n2;
                                n2 = 0;
                            }
                            object = new dg(null, object, 1, this.al);
                            this.d.a(object);
                            ++this.y;
                            ++n4;
                        }
                    }
                    this.G.a(this.R);
                }
                this.d.i(this.d.q());
                return;
            }
            case 3: {
                this.s.c(String.valueOf(this.L));
                return;
            }
            case 0: {
                if (this.Q) {
                    this.Q = false;
                    this.d.a(this.H);
                    this.G.b(this.H);
                    this.d.i(this.d.q());
                    --this.y;
                    return;
                }
                ++this.y;
                hf hf3 = this.G;
                this.d.a((Object)hf3.k);
                hf3 = this.G;
                this.G.a(hf3.k);
                this.d.i(this.d.q());
            }
        }
    }

    private void w() {
        Object object = this.ai;
        synchronized (object) {
            if (this.O || this.N) {
                this.a("----- Kh\u00f4ng ch\u1ea5p nh\u1eadn -----", (byte)0, ca.d);
                this.e(true);
                this.N = false;
                this.O = false;
                this.P = false;
                this.V[2] = this.w;
                this.V[2].d(this.x.m());
                this.x.d(false);
            }
            return;
        }
    }

    public final void a(lj lj2) {
        Object object = this.ai;
        synchronized (object) {
            this.e(true);
            this.Z = 125;
            this.w();
            dg dg2 = new dg(this.Y.a(lz.a(lj2) + 98, true), lj2, 0, this.al);
            new dg(this.Y.a(lz.a(lj2) + 98, true), lj2, 0, this.al).k = lj2;
            this.l.a((Object)dg2);
            this.ab = 10;
            this.aa = true;
            this.a(String.valueOf(this.A) + " v\u1eeba c\u1eadp nh\u1eadt " + lj2.c, (byte)0, com.mg.sq.a.g);
            return;
        }
    }

    public final void a(lk lk2, int n2) {
        Object object = this.ai;
        synchronized (object) {
            this.e(true);
            this.Z = 125;
            this.a(String.valueOf(this.A) + " v\u1eeba c\u1eadp nh\u1eadt " + n2 + " " + lk2.b, (byte)0, com.mg.sq.a.g);
            this.w();
            lk[] lkArray = new a();
            dg dg2 = null;
            int n3 = 0;
            while (n3 < this.l.s()) {
                lk lk3;
                dg2 = (dg)this.l.k(n3);
                if (dg2 != null && dg2.j != 0 && (lk3 = (lk)dg2.k) != null && lk3.a == lk2.a) {
                    lkArray.a(dg2);
                }
                ++n3;
            }
            n3 = 0;
            while (n3 < lkArray.d()) {
                this.l.a((dg)lkArray.b(n3));
                ++n3;
            }
            if (n2 > 0) {
                int n4 = n3 = lk2.e == 3 ? 5 : 1;
                if (lk2.l <= 0) {
                    dg2 = new dg(null, lk2, n3, this.al);
                    this.l.a((Object)dg2);
                    return;
                }
                int n5 = lk2.l == 1 ? n2 : n2 / lk2.l + (n2 % lk2.l > 0 ? 1 : 0);
                lkArray = new lk[n5];
                int n6 = 0;
                while (n6 < n5) {
                    lkArray[n6] = lk2.b();
                    if (lk2.l > 1) {
                        if (n2 >= lk2.l) {
                            lkArray[n6].g = lk2.l;
                            n2 -= lk2.l;
                        } else if (n2 > 0) {
                            lkArray[n6].g = n2;
                        }
                    } else {
                        lkArray[n6].g = 1;
                    }
                    dg2 = new dg(null, lkArray[n6], n3, this.al);
                    this.l.a((Object)dg2);
                    ++n6;
                }
            }
            this.ab = 10;
            this.aa = true;
            return;
        }
    }

    public final void a(int n2, boolean bl2) {
        Object object = this.ai;
        synchronized (object) {
            this.e(true);
            if (bl2) {
                this.Z = 125;
                this.w();
                this.a(String.valueOf(this.A) + " v\u1eeba c\u1eadp nh\u1eadt " + com.mg.sq.a.b(n2), (byte)0, com.mg.sq.a.g);
            }
            this.v = n2 / 1000;
            this.ad = 10;
            this.ac = true;
            return;
        }
    }

    public final void a(lj[] ljArray, lk[] lkArray, int n2) {
        Object object = this.ai;
        synchronized (object) {
            this.e(true);
            --this.af;
            if (this.P) {
                this.O = true;
                this.V[2] = this.x;
                this.V[2].d(this.w.m());
                this.w.d(false);
                this.Z = 0;
            } else {
                this.N = true;
                this.Z = 125;
            }
            this.a(n2, false);
            this.l.t();
            this.T.a();
            dg dg2 = null;
            this.a("Ki\u1ec3m tra th\u00f4ng tin:", (byte)0, com.mg.sq.a.g);
            int n3 = 0;
            while (n3 < ljArray.length) {
                dg2 = new dg(this.Y.a(lz.a(ljArray[n3]) + 98, true), ljArray[n3], 0, this.al);
                this.l.a((Object)dg2);
                this.a("- " + ljArray[n3].c, (byte)0, com.mg.sq.a.g);
                ++n3;
            }
            n3 = 0;
            while (n3 < lkArray.length) {
                int n4 = lkArray[n3].g;
                if (n4 > 0) {
                    int n5;
                    int n6 = n5 = lkArray[n3].e == 3 ? 5 : 1;
                    if (lkArray[n3].l <= 0) {
                        dg2 = new dg(null, lkArray[n3], n5, this.al);
                        this.l.a((Object)dg2);
                    } else {
                        int n7 = lkArray[n3].l == 1 ? n4 : n4 / lkArray[n3].l + (n4 % lkArray[n3].l > 0 ? 1 : 0);
                        lk[] lkArray2 = new lk[n7];
                        int n8 = 0;
                        while (n8 < n7) {
                            lkArray2[n8] = lkArray[n3].b();
                            if (lkArray[n3].l > 1) {
                                if (n4 >= lkArray[n3].l) {
                                    lkArray2[n8].g = lkArray[n3].l;
                                    n4 -= lkArray[n3].l;
                                } else if (n4 > 0) {
                                    lkArray2[n8].g = n4;
                                }
                            } else {
                                lkArray2[n8].g = 1;
                            }
                            dg2 = new dg(null, lkArray2[n8], n5, this.al);
                            this.l.a((Object)dg2);
                            ++n8;
                        }
                    }
                }
                this.a("- " + lkArray[n3].g + " " + lkArray[n3].b, (byte)0, com.mg.sq.a.g);
                ++n3;
            }
            this.a("- S\u1ed1 KEN giao d\u1ecbch: " + com.mg.sq.a.b(n2), (byte)0, com.mg.sq.a.g);
            return;
        }
    }

    public final void a(String string) {
        Object object = this.ai;
        synchronized (object) {
            this.e(true);
            this.Z = 125;
            this.w();
            this.ab = 10;
            this.aa = true;
            Object object2 = null;
            object2 = null;
            int n2 = 0;
            while (n2 < this.l.s()) {
                object2 = (dg)this.l.k(n2);
                if (((dg)object2).j == 0 && ((dg)object2).k != null) {
                    object2 = (lj)((dg)object2).k;
                    if (((lj)object2).b.equals(string)) {
                        this.a(String.valueOf(this.A) + " v\u1eeba l\u1ea5y l\u1ea1i " + ((lj)object2).c, (byte)0, com.mg.sq.a.g);
                        this.l.l(n2);
                        return;
                    }
                }
                ++n2;
            }
            return;
        }
    }

    public final void a(lk lk2) {
        Object object = this.ai;
        synchronized (object) {
            this.e(true);
            this.Z = 125;
            this.w();
            Object object2 = null;
            int n2 = 0;
            while (n2 < this.l.s()) {
                object2 = (dg)this.l.k(n2);
                if (((dg)object2).k != null && (((dg)object2).j == 1 || ((dg)object2).j == 5)) {
                    object2 = (lk)((dg)object2).k;
                    if (((lb)object2).a == lk2.a) {
                        this.a(String.valueOf(this.A) + " v\u1eeba l\u1ea5y l\u1ea1i " + ((lb)object2).b, (byte)0, com.mg.sq.a.g);
                        this.l.l(n2);
                        break;
                    }
                }
                ++n2;
            }
            this.ab = 10;
            this.aa = true;
            return;
        }
    }

    public final void b(String object) {
        Object object2 = this.ai;
        synchronized (object2) {
            this.e(true);
            if (!((String)object).equals(this.A)) {
                cw.a("[TradeScreen ] khac t\u00ean ng giao d\u1ecbch nen huy");
                return;
            }
            object = null;
            int n2 = 0;
            while (n2 < this.l.s()) {
                object = (dg)this.l.k(n2);
                if (((dg)object).k != null) {
                    if (((dg)object).j == 0) {
                        gr.a((lj)((dg)object).k);
                    } else {
                        object = (lk)((dg)object).k;
                        gr.a((lk)object, ((lk)object).g);
                    }
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < this.d.s()) {
                object = (dg)this.d.k(n2);
                if (((dg)object).k != null) {
                    if (((dg)object).j == 0) {
                        gr.b((lj)((dg)object).k);
                    } else {
                        gr.a((lk)((dg)object).k);
                    }
                }
                ++n2;
            }
            ak.b().k();
            ak.b().f(1);
            return;
        }
    }

    private void x() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n h\u1ee7y b\u1ecf giao d\u1ecbch?", "C\u00f3", 10, "Kh\u00f4ng", 11, 1);
        ap2.a(this);
        ap2.b(-7946);
        ak.b().a(ap2, false);
    }

    public final void a(String stringArray, byte by2, d d2) {
        this.e(true);
        stringArray = ca.a((String)stringArray, this.D.c - 10, d2);
        hd hd2 = null;
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (this.T.d() >= this.U) {
                this.T.a(0);
            }
            hd2 = new hd(stringArray[n2], by2, d2);
            hd2.a(-5);
            hd2.h(this.D.c - 3);
            hd2.i(d2.a());
            this.T.a(hd2);
            ++n2;
        }
    }

    public final void e() {
        this.w();
        --this.af;
    }

    protected final void e(int n2) {
        if (this.S.i()) {
            this.S.g(n2);
            return;
        }
        if (this.s.m()) {
            this.s.g(n2);
        }
    }

    private void y() {
        int n2 = this.L;
        String string = this.s.r();
        this.L = !string.equals("") ? Integer.parseInt(string) : 0;
        if (n2 != this.L) {
            if ((long)(this.L * 1000) > gr.p) {
                this.L = (int)(gr.p / 1000L);
            }
            this.M = 3;
            kq.a().f(this.L * 1000);
            this.f(this.af + 1);
        }
    }

    public final void a(ai ai2) {
        char c2 = ai2.a();
        if (c2 < '0' || c2 > '9') {
            ai2.a('\u001a');
        }
    }

    public final void f() {
        if (this.af != 0) {
            this.af = 0;
        }
    }

    public final void b(au object, int n2) {
        if (object == null && n2 >= 6) {
            object = new dg(this.l.k, null, 4, this.al);
        }
        Object object2 = object;
        object = this;
        this.H = (dg)object2;
        if (this.V[this.X] instanceof bc) {
            object2 = ((fg)((bc)this.V[this.X]).w()).u();
            object = this;
            bv bv2 = new bv();
            if (((od)object).H != null && ((od)object).H.j != 4) {
                bv2.a(new bu("Chi Ti\u1ebft", 11116));
            }
            if (((od)object).c.m()) {
                if (((od)object).H != null) {
                    if (((od)object).H.j != 4) {
                        bv2.a(new bu("B\u1ecf ra", 11115));
                    }
                } else {
                    bv2.a(new bu[]{new bu("R\u01b0\u01a1ng \u0110\u1ed3", 11118)});
                }
            }
            if (bv2.s() == null || bv2.s().length == 0) {
                return;
            }
            int n3 = ((n)object2).a + ((od)object).b.a + (((n)object2).c - bv2.e()) / 2;
            int n4 = ((n)object2).b + ((od)object).b.b + ((n)object2).d;
            if (n4 + bv2.f() > z.s - be.a) {
                n4 = z.s - be.a - bv2.f();
            }
            bv2.a_(((od)object).b.a + ((od)object).b.c + bv2.e(), n4);
            bv2.d(n3 < ((od)object).b.a ? ((od)object).b.a : (n3 + bv2.e() > ((od)object).b.a + ((od)object).b.c ? ((od)object).b.a + ((od)object).b.c - bv2.e() : n3), n4);
            bv2.a((bj)object);
            bd bd2 = ((od)object).p;
            Object object3 = object;
            ((aq)object3).b(bd2, true);
            bd2 = ((od)object).q;
            object3 = object;
            ((aq)object3).a(bd2, true);
            bv2.a_(1);
            super.b(bv2);
            ((od)object).F = true;
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final void c(String object) {
        this.af = 0;
        if (object == null) {
            ak.b().k();
            object = ak.b().a("", "Giao d\u1ecbch \u0111\u00e3 b\u1ecb h\u1ee7y b\u1edfi h\u1ec7 th\u1ed1ng!", "\u0110\u00f3ng", 17, 1);
            ((aq)object).a(this);
            ak.b().a((ap)object, false);
            return;
        }
        od od2 = this;
        if (((String)object).equals(od2.A)) {
            ak.b().k();
            object = ak.b().a("", String.valueOf(object) + " \u0111\u00e3 h\u1ee7y giao d\u1ecbch!", "\u0110\u00f3ng", 17, 1);
            ((aq)object).a(this);
            ak.b().a((ap)object, false);
            return;
        }
        ak.b().k();
        ak.b().f(1);
    }
}

