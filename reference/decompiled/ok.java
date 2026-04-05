/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ok
extends au
implements bj,
bk {
    private Image r;
    private Image s;
    private Image t;
    private kq u;
    private bd v;
    private bd w;
    private bd x;
    private bd y;
    private bd z;
    private bd A;
    private bd B;
    private bd C;
    private bd D;
    public static cx i = null;
    private static cx E = null;
    public static byte j = 0;
    public static int k = 8;
    private kg F;
    public kk l;
    private ke G;
    private kc H;
    private a I;
    private ki J;
    public ju m;
    private byte[][] K;
    private kf[] L;
    private kf M;
    private boolean N;
    private boolean O;
    private boolean P;
    private ll Q;
    private cx R;
    private String S;
    private int T;
    public a n;
    private int U;
    private js V;
    private a W;
    private ny X;
    private int Y;
    private boolean Z;
    public boolean o;
    private jv aa;
    private jz ab;
    private int ac;
    private int[] ad;
    private boolean ae;
    private String af;
    private a ag;
    private bd ah;
    public static boolean p = false;
    public gy q;
    private gi ai;
    private bu aj;
    private int ak;
    private ou al;
    private oj am;
    private bb an;
    private byte ao;
    private jw[] ap;
    private boolean aq;

    /*
     * Unable to fully structure code
     */
    public ok(int var1_1, oj var2_4, ny var3_5) {
        block15: {
            block16: {
                super();
                this.r = f.d("/monster");
                this.s = f.d("/zap");
                this.t = f.d("/ice");
                this.u = kq.a();
                this.D = null;
                this.l = null;
                this.G = null;
                this.H = null;
                this.I = new a();
                this.J = new ki();
                this.m = new kl();
                this.K = null;
                this.M = null;
                this.N = false;
                this.O = false;
                this.P = false;
                this.S = null;
                this.U = -1;
                this.W = new a();
                this.X = null;
                this.o = false;
                this.aa = new jv();
                this.ab = null;
                this.ac = 0;
                this.ae = false;
                this.af = null;
                this.ag = new a();
                this.q = null;
                this.ai = null;
                this.aj = null;
                this.ak = -1;
                this.ao = 0;
                this.aq = true;
                cw.b("map");
                this.am = var2_4;
                this.X = var3_5;
                this.a_(1);
                this.al = new ou(null);
                this.ad = new int[20];
                if (z.aa) break block16;
                var1_2 = l.c(System.currentTimeMillis());
                var1_1 = var1_2.get(11);
                if (var1_1 < 6) ** GOTO lbl-1000
                if (var1_1 < 19) {
                    this.ac = 0;
                } else if (var1_1 <= 21) {
                    this.ac = -25;
                } else lbl-1000:
                // 2 sources

                {
                    this.ac = -40;
                }
            }
            this.an = new bb(3);
            var2_4.a(this.an);
            this.d(z.r);
            this.e(z.s);
            var2_4.a(new be());
            ok.p = false;
            var1_3 = this;
            if (!var1_3.P) {
                try {
                    var1_3.P = true;
                    var1_3.Z = true;
                    var1_3.am.a(var1_3);
                    var1_3.w = new ga(109, 1);
                    var1_3.x = new ga(105, 0);
                    var1_3.y = new ga(113, 2);
                    var1_3.z = new ga(106, 3);
                    var1_3.v = new bh("V\u00e0o", 101);
                    var1_3.B = new bh("N\u00f3i Chuy\u1ec7n", 107);
                    var1_3.ah = new bh("Ti\u1ebfp t\u1ee5c", 118);
                    var1_3.A = new bh("Nh\u1eb7t", 110);
                    var1_3.C = com.mg.sq.a.l;
                    var1_3.am.a(var1_3.x);
                    var1_3.am.b(var1_3.w);
                    var1_3.am.c(var1_3.C);
                    try {
                        var1_3.x();
                    }
                    catch (OutOfMemoryError v0) {
                        cw.a("out of memory " + com.mg.sq.a.k);
                        if (com.mg.sq.a.k != null) {
                            com.mg.sq.a.k.G();
                            com.mg.sq.a.t();
                        }
                        try {
                            var1_3.x();
                        }
                        catch (OutOfMemoryError v1) {
                            var2_4 = v1;
                            v1.printStackTrace();
                            com.mg.sq.a.j(1);
                            break block15;
                        }
                    }
                    catch (Exception v2) {
                        var2_4 = v2;
                        v2.printStackTrace();
                        com.mg.sq.a.j(4);
                        break block15;
                    }
                    com.mg.sq.a.K();
                    com.mg.sq.a.q().e(241230);
                }
                catch (OutOfMemoryError v3) {
                    com.mg.sq.a.j(1);
                    break block15;
                }
                catch (Exception v4) {
                    var2_4 = v4;
                    v4.printStackTrace();
                    com.mg.sq.a.j(4);
                }
                if (com.mg.sq.a.k != null) {
                    com.mg.sq.a.k.L();
                }
                var1_3.t();
                ny.l = null;
                ny.k = null;
                var1_3.ad = null;
                System.gc();
            }
        }
        com.mg.sq.a.i(1);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.O) {
            return;
        }
        graphics.setColor(0);
        graphics.fillRect(0, 0, z.r, z.s);
        try {
            this.F.a(graphics);
            if (this.S != null) {
                oz.b(graphics, this.R.a - (this.T >> 1), this.R.b, this.T, 17, 1070484, 16579764, 14542575);
                ca.d.a(graphics, this.S, this.R.a, this.R.b + 2, 1);
            }
            if (gp.a && this.V != null) {
                this.V.a(graphics);
                if (this.am != null && this.am.r) {
                    graphics.drawImage(dg.i, this.V.p() + 1, (this.V.q() - dg.i.getHeight()) / 2, 20);
                }
                if (this.ap != null) {
                    n2 = this.V.q() + 2;
                    n3 = 0;
                    while (n3 < this.ap.length) {
                        this.ap[n3].a(graphics, 0, n2);
                        n2 += this.ap[n3].q() + 2;
                        ++n3;
                    }
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.am.c(true);
    }

    private void x() {
        int n2;
        cw.b("[map tab]  loadmap");
        Image image = this.g(ny.l.h, this.ac);
        this.F = new kg(image);
        this.K = ok.a(ny.l.l, ny.l.d, ny.l.e);
        this.n = new a();
        int n3 = 0;
        while (n3 < ny.l.d) {
            n2 = 0;
            while (n2 < ny.l.e) {
                if (this.K[n3][n2] == 2) {
                    this.n.a(new cx(n2, n3));
                    this.K[n3][n2] = 0;
                }
                ++n2;
            }
            ++n3;
        }
        Object object = this.g(ny.l.i, this.ac);
        object = new kb((Image)object);
        this.F.a((ka)object);
        n2 = ok.a(ny.l.m, ny.l.o);
        byte[] byArray = ny.k[n2];
        object = byArray;
        h.b(byArray, (this.ac << 1) / 3);
        object = f.a((byte[])object);
        this.G = new ke((Image)object, ok.a(ny.l.j, ny.l.d, ny.l.e), 32, 32);
        this.F.a(this.G);
        this.F.h(this.G.p());
        this.F.i(this.G.q());
        this.H = new kc();
        this.F.a(this.H);
        this.Q = new ll();
        this.L = new kf[ny.l.n.length];
        Object var2_5 = null;
        byte[] byArray2 = f.b("/gate");
        object = byArray2;
        h.b(byArray2, (this.ac << 1) / 3);
        object = f.a((byte[])object);
        int n4 = 0;
        while (n4 < ny.l.n.length) {
            jl jl2 = ny.l.n[n4];
            if (jl2.a == 1) {
                this.L[n4] = new kf(this.Q, jl2);
                this.L[n4].e = this.Q;
            } else if (jl2.a == 0) {
                this.L[n4] = new jx((Image)object, jl2, this.F.p());
            } else {
                Image image2 = this.al.a(jl2.i, true);
                if (image2 == null) {
                    image2 = this.g(jl2.i, 0);
                    this.al.a(jl2.i, image2);
                }
                this.L[n4] = new kj(image2, jl2, 1);
            }
            if (jl2.c == ny.d && i == null) {
                int n5 = 0;
                if (jl2.a == 0) {
                    n5 = this.L[n4].b() == 0 ? this.L[n4].d.f : (n5 -= 26);
                }
                i = new cx(jl2.d + n5, jl2.e);
            }
            ++n4;
        }
        this.l = new kk();
        this.l.a(gr.j);
        this.l.c(ok.i.a, ok.i.b);
        if (j == 4) {
            j = 0;
        }
        this.l.a(j, k);
        i = null;
        this.H.a(this.l);
        this.H.c = this.L;
        if (gr.r != null && gr.r.length > 0 && E != null) {
            Image image3 = f.d("/info/itemchest");
            int n6 = 0;
            while (n6 < gr.r.length) {
                jt jt2 = new jt(image3, 1);
                jt2.l(60);
                jt2.v = gr.r[n6];
                jt2.c(ok.E.a, ok.E.b - jt2.q());
                jt2.a(new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, jt2.p());
                jt2.a(new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]});
                jt2.b((byte)1);
                jt2.a((byte)1);
                jt2.w = cy.a(2) == 0 ? -1 : 1;
                this.H.d.a(jt2);
                ++n6;
            }
            gr.r = new lj[0];
        }
        this.G.a(ok.a(ny.l.k, ny.l.d, ny.l.e));
        this.F.a(this.l.s);
        this.S = ny.l.b;
        this.T = ca.d.a(this.S) + 10;
        this.R = new cx(z.r - this.T / 2, 0);
        this.V = new js(gr.j);
        this.a(gr.j.ae);
        this.O = true;
    }

    public final void a(jn[] jnArray, boolean bl2) {
        this.W.a();
        int n2 = (this.l.s.a + this.l.s.c / 2) / 32;
        int n3 = (this.l.s.b + this.l.s.d / 2) / 32;
        Image image = null;
        int n4 = n2;
        while (n4 < this.K[n3].length) {
            if (this.K[n3][n4] != 0) break;
            this.W.a(new cx(n4, n3));
            ++n4;
        }
        n4 = n2;
        while (n4 >= 0) {
            if (this.K[n3][n4] != 0) break;
            this.W.a(new cx(n4, n3));
            --n4;
        }
        cx cx2 = null;
        Object object = null;
        object = null;
        a a2 = new a();
        boolean bl3 = false;
        int n5 = 0;
        while (n5 < jnArray.length) {
            image = null;
            int n6 = 0;
            while (n6 < jnArray[n5].f) {
                if (this.n.d() <= 0) {
                    this.n = a2;
                    if (bl2) {
                        return;
                    }
                    if (bl3) {
                        return;
                    }
                    bl3 = true;
                    a2 = new a();
                }
                n4 = cy.a(this.n.d());
                cx2 = (cx)this.n.b(n4);
                int n7 = 0;
                while (n7 < this.W.d()) {
                    object = (cx)this.W.b(n7);
                    if (((cx)object).b != cx2.b) break;
                    if (cx2.a == ((cx)object).a) {
                        a2.a(cx2);
                        this.n.a(n4);
                        if (this.n.d() > 0) {
                            n4 = cy.a(this.n.d());
                            cx2 = (cx)this.n.b(n4);
                        } else {
                            this.n = a2;
                            if (bl2) {
                                return;
                            }
                            if (bl3) {
                                return;
                            }
                            bl3 = true;
                            a2 = new a();
                        }
                    }
                    ++n7;
                }
                if (bl3) {
                    object = this.a(cx2.a << 5, cx2.b << 5, jnArray[n5], n6 == 0 ? null : image);
                    if (object == null) break;
                    kh kh2 = null;
                    int n8 = 0;
                    while (n8 < this.I.d()) {
                        kh2 = (kh)this.I.b(n8);
                        if (kh2.n() == ((ax)object).n() && kh2.o() == ((ax)object).o()) {
                            ((kh)object).a(0, kh2.b() == 2 ? 3 : 2);
                            break;
                        }
                        if (((kh)object).a(kh2.e)) {
                            ((kh)object).a(0, kh2.b() == 2 ? 3 : 2);
                            break;
                        }
                        ++n8;
                    }
                    this.I.a(object);
                } else {
                    object = this.a(cx2.a << 5, cx2.b << 5, jnArray[n5], n6 == 0 ? null : image);
                    if (object == null) break;
                    this.I.a(object);
                }
                if (n6 == 0) {
                    image = ((kh)object).a();
                }
                a2.a(cx2);
                this.n.a(n4);
                ++n6;
            }
            ++n5;
        }
        n5 = 0;
        while (n5 < a2.d()) {
            this.n.a(a2.b(n5));
            ++n5;
        }
    }

    public final void a(jn[] jnArray) {
        int n2 = 0;
        while (n2 < jnArray.length) {
            int n3 = 0;
            block1: while (n3 < this.I.d()) {
                Object object = (kh)this.I.b(n3);
                if (object != null && ((kh)object).f.a.equals(jnArray[n2].a)) {
                    this.I.a(n3);
                    object = jnArray[n2].a;
                    kc kc2 = this.H;
                    int n4 = 0;
                    while (n4 < kc2.b.d()) {
                        if (kc2.b.b(n4) instanceof kh) {
                            kh kh2 = (kh)kc2.b.b(n4);
                            if (kh2.f.a.equals(object)) {
                                kc2.b.a(n4);
                                break block1;
                            }
                        }
                        ++n4;
                    }
                    break;
                }
                ++n3;
            }
            ++n2;
        }
    }

    private kh a(int n2, int n3, jn object, Image image) {
        int n4;
        Image image2;
        if (object == null) {
            return null;
        }
        switch (((jn)object).c >> 1) {
            case 0: {
                image2 = this.r;
                n4 = 14;
                break;
            }
            case 1: {
                image2 = this.s;
                n4 = 5;
                break;
            }
            default: {
                image2 = this.t;
                n4 = 13;
            }
        }
        object = new kh(image2, 1, 6, (jn)object, gr.j, image);
        ((kh)object).d(n4);
        ((kh)object).a(((ax)object).n(), ((ax)object).o(), ((ax)object).p() - 12, 20);
        ((kh)object).c(cy.a(2, 4));
        ((kh)object).c(n2 - (((ax)object).p() - 32), n3 - (((ax)object).q() - 32));
        ((kh)object).a(0, cy.a(2, 3));
        this.H.b.a(object);
        return object;
    }

    private static int a(int n2, int[] nArray) {
        int n3 = 0;
        int n4 = 0;
        while (n4 < nArray.length) {
            if (n2 == nArray[n4]) {
                n3 = n4;
                break;
            }
            ++n4;
        }
        return n3;
    }

    private static byte[][] a(byte[] byArray, int n2, int n3) {
        byte[][] byArray2 = new byte[n2][n3];
        int n4 = 0;
        while (n4 < byArray2.length) {
            System.arraycopy(byArray, n4 * n3, byArray2[n4], 0, n3);
            ++n4;
        }
        return byArray2;
    }

    public final void n() {
        try {
            int n2;
            int n3;
            if (!this.O) {
                cw.b("chua init map complete k update");
                return;
            }
            if (this.Z) {
                this.u.d(ny.c);
                this.Z = false;
            }
            Object object = null;
            if (this.Y > 0) {
                --this.Y;
                if (this.Y == 0) {
                    ak.b().a(this.q, false);
                } else if (this.Y < 0) {
                    this.q();
                }
            }
            if (this.N) {
                this.l.i();
                if (this.l.b() != 4) {
                    this.l.a(4);
                }
                return;
            }
            if (this.l != null) {
                this.m.a(this.l, this.F);
                if (this.l.b() != 4) {
                    if (this.M == null) {
                        n3 = 0;
                        while (n3 < this.L.length) {
                            if (this.l.s.b(this.L[n3].d.d, this.L[n3].d.e, this.L[n3].d.f, this.L[n3].d.g)) {
                                this.M = this.L[n3];
                                this.ab = null;
                                this.D = this.am.s();
                                if (this.M.d.a == 2) {
                                    if (this.M.c()) break;
                                    object = this;
                                    ((ok)object).am.v();
                                    object = (kj)this.M;
                                    this.am.c(this.B);
                                    break;
                                }
                                this.am.c(this.v);
                                break;
                            }
                            ++n3;
                        }
                        if (this.M == null) {
                            n3 = this.H.d.d();
                            n2 = 0;
                            while (n2 < n3) {
                                if (this.H.d.b(n2) instanceof jt) {
                                    this.ab = (jz)this.H.d.b(n2);
                                    if (this.ab.r() == 1 && this.ab.h() == 0 && this.l.s.b(this.ab.n(), this.ab.o(), this.ab.p(), this.ab.q())) {
                                        this.D = this.am.s();
                                        this.am.c(this.A);
                                        break;
                                    }
                                }
                                if (n2 == n3 - 1) {
                                    this.ab = null;
                                    this.am.c(this.C);
                                }
                                ++n2;
                            }
                        }
                    } else {
                        if (this.M.d.a == 0 && (this.l.b() == 1 || this.l.b() == 7)) {
                            switch (this.M.b()) {
                                case 0: {
                                    if (this.o || (this.l.j & 4) == 0 || this.l.s.a > this.M.n() + (this.M.p() >> 1)) break;
                                    this.e(false);
                                    this.u.b(ny.c, this.M.d.c);
                                    com.mg.sq.a.a(null, null);
                                    this.m.a();
                                    this.o = true;
                                    return;
                                }
                                default: {
                                    if (this.o || (this.l.j & 8) == 0 || this.l.s.a + this.l.s.c < this.M.n() + (this.M.p() >> 1)) break;
                                    this.u.b(ny.c, this.M.d.c);
                                    com.mg.sq.a.a(null, null);
                                    this.m.a();
                                    this.o = true;
                                    return;
                                }
                            }
                        }
                        if (!this.l.s.b(this.M.d.d, this.M.d.e, this.M.d.f, this.M.d.g)) {
                            if (this.M.d.a == 2) {
                                object = this;
                                ((ok)object).am.w();
                            }
                            this.M = null;
                            this.am.c(this.D);
                            this.D = null;
                        }
                    }
                } else if (this.l.m()) {
                    n3 = this.I.d();
                    n2 = 0;
                    while (n2 < n3) {
                        object = (kh)this.I.b(n2);
                        if (((ax)object).m() && this.l.t.a(((kh)object).d())) {
                            this.a((kh)object, true);
                            this.U = n2;
                            if (((kh)object).e.a < this.l.s.a) {
                                ((kh)object).b(3);
                                this.l.b(4);
                            } else {
                                ((kh)object).b(2);
                                this.l.b(8);
                            }
                            break;
                        }
                        ++n2;
                    }
                }
                this.F.a(this.l.s);
            }
            try {
                n3 = 0;
                n2 = this.I.d();
                while (n3 < n2) {
                    object = (kh)this.I.b(n3);
                    if (object == null) break;
                    if (this.U != n3) {
                        this.J.a((kh)object, this.l, this.K, this.F, this.aq);
                        if (((kh)object).c() == 1 && !com.mg.sq.a.q().c(191919)) {
                            this.U = n3;
                            this.a((kh)object, false);
                            this.am.c(null);
                        }
                    }
                    if (this.N && this.U == n3) {
                        ((kh)object).i();
                        if (((kh)object).c() != 1) {
                            ((kh)object).a(1);
                        }
                    }
                    ++n3;
                }
                n2 = 0;
                int n4 = this.H.d.d();
                while (n2 < n4) {
                    jz jz2 = (jz)this.H.d.b(n2);
                    if (jz2.r() == 1) {
                        this.aa.a(jz2, this.F);
                        if (!jz2.m()) {
                            if (this.am.s().equals(this.A)) {
                                this.am.c(this.C);
                            }
                            this.H.d.a(n2);
                            --n4;
                            --n2;
                        }
                    }
                    ++n2;
                }
            }
            catch (Exception exception) {
                cw.a("[Map] Loi update monster");
            }
            if (this.Q != null) {
                this.Q.i();
            }
            if (this.F != null) {
                this.F.i();
            }
            if (this.ae) {
                this.ae = false;
                this.X.g();
            }
            if (this.ap != null) {
                int n5 = 0;
                while (n5 < this.ap.length) {
                    this.ap[n5].i();
                    ++n5;
                }
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            com.mg.sq.a.j(1);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            if (this.ao > 9) {
                this.ao = 0;
                com.mg.sq.a.j(0);
            }
            this.ao = (byte)(this.ao + 1);
            cw.a("Null pointer map " + this.ao);
            return;
        }
        this.ao = 0;
    }

    protected final void a() {
        if (this.aj == null || this.ai == null) {
            return;
        }
        try {
            this.ai = (gi)this.an.a();
            int n2 = this.aj.n() + this.aj.p();
            this.ai.f(n2 + 5);
            this.ai.g(this.aj.o() - this.ai.q() - 5);
            this.ai.a(10);
            this.ai.a(new n(this.ai.n() + 9, this.ai.o() + 9, this.ai.p() - 20, this.ai.q() - 20));
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void q() {
        kh kh2;
        this.Y = 0;
        ak.b().a(false);
        this.l.a(0);
        this.e(false);
        if (this.U >= 0 && (kh2 = (kh)this.I.b(this.U)) != null) {
            kh2.a(0);
        }
        this.m.a();
        this.l.w = 30;
        this.N = false;
        this.U = -1;
    }

    private void a(kh kh2, boolean bl2) {
        this.y();
        cw.b("fightingMonster");
        try {
            if (!bl2) {
                ak.a().b(10);
            }
            ak.b().k();
            this.N = true;
            this.Y = 10;
            this.r();
            this.e(false);
            E = new cx(kh2.e.a + kh2.e.c / 2, kh2.e.b + kh2.e.d);
            this.q = new gy(this.l.d.u(), kh2.e(), false, bl2, 99030, (bj)this.X);
            this.q.a(this.am);
            kq.a().a(kh2.f.a, bl2);
            return;
        }
        catch (Throwable throwable) {
            cw.b("fightering monster err");
            throwable.printStackTrace();
            return;
        }
    }

    public final void r() {
        if (this.l != null) {
            i = new cx(this.l.s.a, this.l.s.b);
            j = (byte)(this.l.i == 5 ? 6 : (byte)this.l.i);
            k = this.l.j;
        }
    }

    public final void s() {
        if (!this.O) {
            return;
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 105: {
                ok ok2 = this;
                if (ok2.N || ok2.X == null || ok2.am == null) break;
                bv bv2 = new bv();
                bu[] buArray = ny.a(ok2.am);
                if (buArray != null) {
                    bv2.a(buArray);
                    int n4 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                    bv2.a_(-n4, ok2.f() - bv2.f() + n4);
                    bv2.d(0, z.s - be.a - bv2.f());
                    bv2.a(ok2);
                    bv2.c(ok2.C);
                    bv2.a(ok2.y);
                    bv2.b(ok2.z);
                    ok2.m.a();
                    if (ok2.an.a() != null && ok2.an.a() instanceof gi) {
                        ok2.an.a().b(false);
                        ok2.an.i();
                        gi gi2 = (gi)ok2.an.a();
                        bu[] buArray2 = bv2.s();
                        ax ax2 = null;
                        if (gi2 != null) {
                            int n5 = 0;
                            while (n5 < buArray2.length) {
                                if (buArray2[n5].b().equals(gi2.b())) {
                                    ax2 = buArray2[n5];
                                }
                                ++n5;
                            }
                            if (ax2 != null) {
                                n5 = ax2.n() + ax2.p();
                                gi2.f(n5 + 5);
                                gi2.g(ax2.o());
                                gi2.a(gi2.n() + 10);
                                gi2.a(new n(gi2.n() + 9, gi2.o() + 9, gi2.p() - 20, gi2.q() - 20));
                                gi2.c(true);
                                ok2.aj = ax2;
                                ok2.ai = gi2;
                                gi2.c();
                            }
                        }
                    }
                    ok2.am.a(bv2);
                }
                return;
            }
            case 101: {
                com.mg.sq.a.a(null, null);
                i = null;
                this.e(false);
                this.m.a();
                this.l.a(0);
                this.u.b(ny.c, this.M.d.c);
                return;
            }
            case 107: {
                this.am.c(this.C);
                this.m.a();
                this.af = this.M.a();
                this.ak = this.M.d.c;
                this.u.b(ny.c, this.ak);
                com.mg.sq.a.a(null, null);
                return;
            }
            case 109: {
                this.m.a();
                ((fc)this.am.l()).a();
                return;
            }
            case 106: {
                this.y();
                return;
            }
            case 113: {
                this.am.l.f(95);
                return;
            }
            case 110: {
                if (gr.b()) {
                    com.mg.sq.a.a((bj)this, "H\u00e0nh Trang", 114, "\u0110\u00f3ng", 115);
                    return;
                }
                com.mg.sq.a.a(this.ab.v, this, "Nh\u1eb7t", 2000, "B\u1ecf qua", 1000);
                return;
            }
            case 2000: {
                ok ok3 = this;
                ok3.ag.a(ok3.ab.v);
                ok3.u.b(new String[]{ok3.ab.v.b});
                ak.b().a(241212, false);
                ok3.H.d.b(ok3.ab);
                ok3.ab = null;
                ok3.am.c(ok3.C);
                return;
            }
            case 1000: {
                ak.b().a(241212, false);
                this.H.d.b(this.ab);
                this.ab = null;
                this.am.c(this.C);
                return;
            }
            case 112: {
                if (ak.b().c(241212)) {
                    Object object = (he)ak.b().d(241212);
                    object = ((he)object).k;
                    gr.a((lj)object);
                    this.u.b(new String[]{((lj)object).b});
                    ak.b().a(241212, false);
                }
                this.am.c(this.C);
                return;
            }
            case 114: {
                ak.b().a(241209, false);
                ny.t();
                return;
            }
            case 115: {
                ak.b().a(241209, false);
                return;
            }
            case 118: {
                kj kj2 = (kj)this.M;
                if (this.M == null) break;
                break;
            }
            default: {
                if (n3 == -999) {
                    return;
                }
                kq.a().a(n3 - 99999990, ny.c, this.ak);
                com.mg.sq.a.a(null, null);
                ak.b().a(-9898989, false);
            }
        }
    }

    public final boolean f(int n2) {
        if (!this.O) {
            return false;
        }
        if (this.am.l != null) {
            this.d(0, 106);
            return true;
        }
        if (this.N) {
            return true;
        }
        if (z.ab) {
            char c2 = ai.a(n2);
            if (c2 == 'w' || c2 == 'W') {
                n2 = 99;
            } else if (c2 == 'a' || c2 == 'A') {
                n2 = 97;
            } else if (c2 == 'd' || c2 == 'D') {
                n2 = 96;
            } else if (c2 == 's' || c2 == 'x' || c2 == 'X' || c2 == 'S') {
                n2 = 98;
            }
        }
        if (this.M != null && this.M.d.a == 0) {
            switch (n2) {
                case 97: {
                    if (this.M.b() != 0 || this.o || this.l.s.a > this.M.n() + this.M.p() / 2) break;
                    i = null;
                    this.e(false);
                    this.u.b(ny.c, this.M.d.c);
                    com.mg.sq.a.a(null, null);
                    this.o = true;
                    break;
                }
                case 96: {
                    if (this.M.b() != 1 || this.o || this.l.s.a + this.l.s.c + 10 < this.M.n() + this.M.p() / 2) break;
                    i = null;
                    this.e(false);
                    this.u.b(ny.c, this.M.d.c);
                    com.mg.sq.a.a(null, null);
                    this.o = true;
                }
            }
        }
        if (this.F == null) {
            return true;
        }
        if (this.l != null) {
            this.m.a(n2, this.l);
        }
        return true;
    }

    public final boolean g(int n2) {
        if (!this.O) {
            return false;
        }
        if (z.ab) {
            char c2 = ai.a(n2);
            if (c2 == 'w' || c2 == 'W') {
                n2 = 99;
            } else if (c2 == 'a' || c2 == 'A') {
                n2 = 97;
            } else if (c2 == 'd' || c2 == 'D') {
                n2 = 96;
            } else if (c2 == 's' || c2 == 'x' || c2 == 'X' || c2 == 'S') {
                n2 = 98;
            }
        }
        if (this.l != null) {
            this.m.a(n2);
        }
        return true;
    }

    public final void a(lf lf2, boolean bl2) {
        this.V.a(lf2);
        if (bl2) {
            this.l.b(lf2);
            this.l.a(lf2);
        }
    }

    public final void e(boolean bl2) {
        this.l.b(bl2);
    }

    public final boolean c(int n2, int n3) {
        if (!this.O) {
            return false;
        }
        if (new n(this.l.n() - z.r / 2, this.l.o(), z.r / 2, this.l.q()).a(n2 -= this.F.n(), n3 -= this.F.o())) {
            this.f(97);
            return true;
        }
        if (new n(this.l.n() + this.l.p(), this.l.o(), z.r / 2, this.l.p()).a(n2, n3)) {
            this.f(96);
            return true;
        }
        if (new n(this.l.n(), this.l.o() - z.s / 2, this.l.p(), z.s / 2).a(n2, n3)) {
            this.f(99);
            return true;
        }
        if (new n(this.l.n(), this.l.o() + this.l.q(), this.l.p(), z.s / 2).a(n2, n3)) {
            this.f(98);
            return true;
        }
        if (new n(this.l.n() - z.r / 2, this.l.o() - z.s / 2, z.r / 2, z.s / 2).a(n2, n3)) {
            this.f(99);
            this.f(97);
            return true;
        }
        if (new n(this.l.n() + this.l.p(), this.l.o() - z.s / 2, z.r / 2, z.s / 2).a(n2, n3)) {
            this.f(99);
            this.f(96);
            return true;
        }
        if (new n(this.l.n(), this.l.o(), this.l.p(), this.l.q()).a(n2, n3)) {
            this.f(95);
        }
        return false;
    }

    public final boolean f(int n2, int n3) {
        if (!this.O) {
            return false;
        }
        n2 -= this.F.n();
        n3 -= this.F.o();
        if (this.l.i == 7) {
            this.g(97);
            this.g(96);
            this.g(99);
            this.g(98);
        }
        if (new n(this.l.n() - z.r / 2, this.l.o(), z.r / 2, this.l.q()).a(n2, n3)) {
            this.g(97);
            return true;
        }
        if (new n(this.l.n() + this.l.p(), this.l.o(), z.r / 2, this.l.p()).a(n2, n3)) {
            this.g(96);
            return true;
        }
        if (new n(this.l.n(), this.l.o() - z.s / 2, this.l.p(), z.s / 2).a(n2, n3)) {
            this.g(99);
            return true;
        }
        if (new n(this.l.n(), this.l.o() + this.l.q(), this.l.p(), z.s / 2).a(n2, n3)) {
            this.g(98);
            return true;
        }
        if (new n(this.l.n() - z.r / 2, this.l.o() - z.s / 2, z.r / 2, z.s / 2).a(n2, n3)) {
            this.g(99);
            this.g(97);
            return true;
        }
        if (new n(this.l.n() + this.l.p(), this.l.o() - z.s / 2, z.r / 2, z.s / 2).a(n2, n3)) {
            this.g(99);
            this.g(96);
            return true;
        }
        if (new n(this.l.n(), this.l.o(), this.l.p(), this.l.q()).a(n2, n3)) {
            return this.g(95);
        }
        return false;
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        bu bu2 = (bu)object;
        if (this.ai != null && bu2.b().equals(this.ai.b())) {
            this.ai.b(false);
        }
        this.X.a(n3, bu2);
        this.y();
    }

    private void y() {
        Object object;
        this.am.t();
        this.aj = null;
        this.ai = null;
        if (this.an.a() != null && this.an.a() instanceof gi) {
            this.an.i();
            object = (gi)this.an.a();
            if (object != null && ((gi)object).d()) {
                ((ax)object).b(false);
                this.an.i();
                object = (gi)this.an.a();
            }
            if (object != null) {
                ((gi)object).d(true);
                ((ax)object).g(this.d() + this.f() - ((ax)object).q() - 30);
                ((ax)object).f(this.c() + 5);
                ((gi)object).a(10);
                ((gi)object).a(new n(((ax)object).n() + 9, ((ax)object).o() + 9, ((ax)object).p() - 20, ((ax)object).q() - 20));
                ((gi)object).c(true);
            }
        }
        if ((object = ak.b().e()) != null && ((aq)object).h() == 241203) {
            object = (hz)object;
            ((hz)object).j(true);
        }
    }

    private Image g(int n2, int n3) {
        byte[] byArray = ny.k[ok.a(n2, ny.l.o)];
        if (n3 != 0) {
            boolean bl2 = true;
            int n4 = 0;
            while (n4 < this.ad.length) {
                if (this.ad[n4] == 0) {
                    this.ad[n4] = n2;
                    break;
                }
                if (this.ad[n4] == n2) {
                    bl2 = false;
                    break;
                }
                ++n4;
            }
            if (n4 == this.ad.length) {
                int[] nArray = new int[n4 + 10];
                System.arraycopy(this.ad, 0, nArray, 0, n4);
                this.ad = nArray;
            }
            if (bl2) {
                h.b(byArray, n3);
            }
        }
        return f.a(byArray);
    }

    public final void t() {
        Object object;
        boolean bl2 = true;
        if (gp.m && gr.j.G < 3) {
            gp.m = false;
            try {
                int n2 = ca.c.a() * 3 + 20;
                object = new gi("Ch\u00e0o m\u1eebng \u0111\u1ebfn v\u1edbi s\u1ee9 qu\u00e2n online! H\u00e3y ki\u1ec3m tra nhi\u1ec7m v\u1ee5. Ch\u1ecdn Menu > Nhi\u1ec7m v\u1ee5", this.c() + 15, this.d() + this.f() - 30, n2);
                ((ax)object).f(this.c() + 5);
                ((gi)object).a(10);
                ((gi)object).a(new n(((ax)object).n() + 9, ((ax)object).o() + 9, ((ax)object).p() - 20, ((ax)object).q() - 20));
                ((gi)object).c(true);
                this.an.a((ax)object);
                gi gi2 = this.e() > 240 ? new gi("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nhi\u1ec7m v\u1ee5", this.c() + 15, this.d() + this.f() - 30, n2) : new gi("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nhi\u1ec7m v\u1ee5", this.c() + 15, this.d() + this.f() - 30, this.e() - 80, n2, false);
                gi2.a(new String[]{"Nhi\u1ec7m V\u1ee5", "Nhi\u1ec7m V\u1ee5"});
                gi2.f(this.c() + 5);
                gi2.a(10);
                gi2.a(new n(gi2.n() + 9, gi2.o() + 9, gi2.p() - 20, gi2.q() - 20));
                gi2.c(true);
                ((gi)object).a(gi2);
                this.an.a(gi2);
                bl2 = false;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        if (gp.n && gr.j.G <= 6) {
            int[] nArray = new int[9];
            try {
                int n3 = 0;
                while (n3 < gr.j.E.length) {
                    lt lt2 = gr.j.E[n3];
                    int n4 = lt2.a % 100;
                    nArray[n4] = gr.j.E[n3].f;
                    ++n3;
                }
                int n5 = 0;
                while (n5 < gr.o.length) {
                    n3 = nArray[n5];
                    if (n3 == 2) {
                        n3 = 1;
                    }
                    if (n3 < gr.o[n5].c.length && gr.o[n5].c[n3].b <= gr.j.G && gr.j.L >= gr.o[n5].c[n3].c) {
                        gp.n = false;
                        int n6 = ca.c.a() * 3 + 20;
                        gi gi3 = new gi("B\u1ea1n v\u1eeba l\u00ean c\u1ea5p, c\u00f3 th\u1ec3 t\u0103ng \u0111i\u1ec3m tuy\u1ec7t chi\u00eau. B\u1ea5m menu tr\u00e1i > Nh\u00e2n v\u1eadt > Tuy\u1ec7t chi\u00eau", this.c() + 15, this.d() + this.f() - 30, n6);
                        gi3.f(this.c() + 5);
                        gi3.a(10);
                        gi3.a(new n(gi3.n() + 9, gi3.o() + 9, gi3.p() - 20, gi3.q() - 20));
                        gi3.c(true);
                        gi3.d(bl2);
                        this.an.a(gi3);
                        gi gi4 = this.e() > 240 ? new gi("Ch\u1ecdn Nh\u00e2n v\u1eadt > Tuy\u1ec7t chi\u00eau", this.c() + 15, this.d() + this.f() - 30, n6) : new gi("Ch\u1ecdn Nh\u00e2n v\u1eadt > Tuy\u1ec7t chi\u00eau", this.c() + 15, this.d() + this.f() - 30, this.e() - 80, n6, false);
                        gi4.f(this.c() + 5);
                        gi4.a(10);
                        gi4.a(new n(gi4.n() + 9, gi4.o() + 9, gi4.p() - 20, gi4.q() - 20));
                        gi4.a(new String[]{"Nh\u00e2n V\u1eadt", "Tuy\u1ec7t Chi\u00eau"});
                        gi4.c(true);
                        gi3.a(gi4);
                        this.an.a(gi4);
                        return;
                    }
                    ++n5;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                cw.a(exception);
            }
        }
    }

    public final void a(String object, byte by2) {
        if (object == null) {
            this.X.g();
            return;
        }
        try {
            if (this.M != null) {
                if (this.M.d.b.equals(this.af)) {
                    aw aw2 = new aw(this.M.f, 1);
                    aw2.a(new byte[][]{new byte[1]});
                    aw2.c(2);
                    object = new ia(this.M.n(), this.M.o() - 10, this.F.a(), aw2, (String)object, this.M.d.b);
                    ak.b().a((ap)object);
                    return;
                }
                this.af = null;
                return;
            }
            com.mg.sq.a.q((String)object);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void u() {
        if (this.N) {
            this.q();
        }
    }

    public final void v() {
        kj kj2 = null;
        int n2 = 0;
        while (n2 < this.L.length) {
            if (this.L[n2].d.a == 2) {
                kj2 = (kj)this.L[n2];
                kj2.a(false, 0);
                kj2.d();
                this.ae = false;
                if (this.l.s.b(this.L[n2].d.d, this.L[n2].d.e, this.L[n2].d.f, this.L[n2].d.g)) {
                    this.M = this.L[n2];
                    this.ab = null;
                    if (this.M.c()) break;
                    this.am.c(this.B);
                    return;
                }
            }
            ++n2;
        }
    }

    public final void a(String[] object) {
        lj lj2 = null;
        int n2 = 0;
        while (n2 < ((String[])object).length) {
            int n3 = 0;
            while (n3 < this.ag.d()) {
                lj2 = (lj)this.ag.b(n3);
                if (object[n2].equals(lj2.b)) {
                    gr.a(lj2);
                    this.ag.a(n3);
                    break;
                }
                ++n3;
            }
            ++n2;
        }
        if (gp.o) {
            gp.o = false;
            n2 = ca.c.a() * 3 + 20;
            gi gi2 = new gi("B\u1ea1n v\u1eeba c\u00f3 v\u1eadt ph\u1ea9m. H\u00e3y b\u1ea5m ph\u00edm menu tr\u00e1i > Nh\u00e2n v\u1eadt > R\u01b0\u01a1ng \u0111\u1ed3", this.c() + 15, this.d() + this.f() - 30, n2);
            gi2.f(this.c() + 5);
            gi2.a(10);
            gi2.a(new n(gi2.n() + 9, gi2.o() + 9, gi2.p() - 20, gi2.q() - 20));
            gi2.c(true);
            this.an.a(gi2);
            object = this.e() > 240 ? new gi("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nh\u00e2n v\u1eadt > R\u01b0\u01a1ng \u0111\u1ed3", this.c() + 15, this.d() + this.f() - 30, n2) : new gi("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nh\u00e2n v\u1eadt > R\u01b0\u01a1ng \u0111\u1ed3", this.c() + 15, this.d() + this.f() - 30, this.e() - 80, n2, false);
            ((gi)object).a(new String[]{"Nh\u00e2n V\u1eadt", "R\u01b0\u01a1ng \u0110\u1ed3"});
            ((ax)object).f(this.c() + 5);
            ((gi)object).a(10);
            ((gi)object).a(new n(((ax)object).n() + 9, ((ax)object).o() + 9, ((ax)object).p() - 20, ((ax)object).q() - 20));
            ((gi)object).c(true);
            gi2.a((gi)object);
            this.an.a((ax)object);
        }
    }

    public final void a(int[] nArray, String[] object) {
        int n2;
        hc hc2 = new hc();
        hc2.b(-9898989);
        hc2.a(new be());
        hc2.a(this);
        int n3 = 10;
        au[] auArray = new ex[nArray.length];
        int n4 = 0;
        int n5 = 0;
        while (n5 < nArray.length) {
            n2 = ca.d.a(object[n5]) + 30;
            if (n2 > n4) {
                n4 = n2;
            }
            auArray[n5] = new ex(object[n5], nArray[n5] + 99999990);
            auArray[n5].a(10, n3, n4, 18);
            n3 += auArray[n5].f() + 5;
            ++n5;
        }
        hc2.a(auArray);
        n5 = auArray.length - 1;
        while (n5 >= 0) {
            auArray[n5].d(n4);
            --n5;
        }
        n5 = n3 + 5;
        n2 = z.r - (n4 += 20) >> 1;
        int n6 = z.s - n5 >> 1;
        hc2.a(n2, n6, n4, n5);
        object = new ga(-999, 3);
        hc hc3 = hc2;
        ((aq)hc3).b((bd)object, true);
        if (this.M != null) {
            hc2.a(hc2.a(), this.M.o() + this.F.o() - hc2.j(), hc2.i(), hc2.j());
        }
        hc2.f(0);
        ak.b().a(hc2, false);
    }

    public final void w() {
        this.am.a((bj)null);
        this.al = null;
        this.I = null;
        this.q = null;
        this.W = null;
        this.V = null;
        this.aa = null;
        this.ab = null;
        this.K = null;
        this.ag = null;
        this.t = null;
        this.r = null;
        this.s = null;
        if (this.H != null) {
            this.H.b();
        }
        this.H = null;
        this.G.b();
        this.G = null;
        this.n = null;
        this.l = null;
        this.af = null;
        this.X = null;
        cw.b("Finish Map.destroy()");
    }

    public final void a(lr[] lrArray) {
        if (lrArray != null) {
            this.ap = new jw[lrArray.length];
            int n2 = 0;
            while (n2 < lrArray.length) {
                Image image = f.a(ox.a().b(lrArray[n2].a));
                this.ap[n2] = new jw(image, 1);
                this.ap[n2].a(lrArray[n2].b);
                this.aq = lrArray[n2].a != 200000 || lrArray[n2].b <= 0L;
                ++n2;
            }
        }
    }
}

