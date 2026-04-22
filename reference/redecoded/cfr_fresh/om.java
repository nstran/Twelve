/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class om
extends aq
implements bf,
bg {
    private Image r;
    private Image s;
    private Image t;
    private final ks u;
    private az v;
    private az w;
    private az x;
    private az y;
    private az z;
    private az A;
    private az B;
    private az C;
    private az D;
    public static cu i = null;
    private static cu E = null;
    public static byte j = 0;
    public static int k = 8;
    private kh F;
    public kl l;
    private kf G;
    private kd H;
    private a I;
    private kj J;
    public jv m;
    private byte[][] K;
    private kg[] L;
    private kg M;
    private boolean N;
    private boolean O;
    private boolean P;
    private ln Q;
    private cu R;
    private String S;
    private int T;
    public a n;
    private int U;
    private jt V;
    private a W;
    private oa X;
    private int Y;
    private boolean Z;
    public boolean o;
    private jw aa;
    private ka ab;
    private int ac;
    private int[] ad;
    private boolean ae;
    private String af;
    private a ag;
    private az ah;
    public static boolean p = false;
    public ha q;
    private gj ai;
    private br aj;
    private int ak;
    private ox al;
    private ol am;
    private ax an;
    private byte ao;
    private jx[] ap;
    private boolean aq;

    /*
     * Unable to fully structure code
     */
    public om(int var1_1, ol var2_4, oa var3_5) {
        block16: {
            block17: {
                super();
                this.r = f.d("/monster");
                this.s = f.d("/zap");
                this.t = f.d("/ice");
                this.u = ks.a();
                this.D = null;
                this.l = null;
                this.G = null;
                this.H = null;
                this.I = new a();
                this.J = new kj();
                this.m = new km();
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
                this.aa = new jw();
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
                ct.b("map");
                this.am = var2_4;
                this.X = var3_5;
                this.a_(1);
                this.al = new ox(null);
                this.ad = new int[20];
                if (v.ah) break block17;
                var1_2 = i.c(System.currentTimeMillis());
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
            this.an = new ax(3);
            var2_4.a(this.an);
            this.d(v.t);
            this.e(v.u);
            var2_4.a(new ba());
            om.p = false;
            var1_3 = this;
            if (!var1_3.P) {
                try {
                    var1_3.P = true;
                    var1_3.Z = true;
                    var1_3.am.a(var1_3);
                    var1_3.w = new gb(109, 1);
                    var1_3.x = new gb(105, 0);
                    var1_3.y = new gb(113, 2);
                    var1_3.z = new gb(106, 3);
                    var1_3.v = new bd("V\u00e0o", 101);
                    var1_3.B = new bd("N\u00f3i Chuy\u1ec7n", 107);
                    var1_3.ah = new bd("Ti\u1ebfp t\u1ee5c", 118);
                    var1_3.A = new bd("Nh\u1eb7t", 110);
                    var1_3.C = com.mg.sq.a.n;
                    var1_3.am.a(var1_3.x);
                    var1_3.am.b(var1_3.w);
                    var1_3.am.c(var1_3.C);
                    try {
                        var1_3.x();
                    }
                    catch (OutOfMemoryError v0) {
                        ct.a("out of memory " + com.mg.sq.a.m);
                        if (com.mg.sq.a.m != null) {
                            com.mg.sq.a.m.G();
                            com.mg.sq.a.s().v();
                        }
                        try {
                            var1_3.x();
                        }
                        catch (OutOfMemoryError v1) {
                            var2_4 = v1;
                            v1.printStackTrace();
                            com.mg.sq.a.s().j(1);
                            break block16;
                        }
                    }
                    catch (Exception v2) {
                        var2_4 = v2;
                        v2.printStackTrace();
                        ou.a().a(4, (Throwable)var2_4, "MapTabView.init()#1");
                        break block16;
                    }
                    var2_4 = com.mg.sq.a.s();
                    if (com.mg.sq.a.i != null) {
                        var2_4.q(com.mg.sq.a.i);
                        com.mg.sq.a.i = null;
                    }
                    com.mg.sq.a.s().e(241230);
                }
                catch (OutOfMemoryError v3) {
                    com.mg.sq.a.s().j(1);
                    break block16;
                }
                catch (Exception v4) {
                    var2_4 = v4;
                    v4.printStackTrace();
                    ou.a().a(4, (Throwable)var2_4, "MapTabView.init()#2");
                }
                if (com.mg.sq.a.m != null) {
                    com.mg.sq.a.m.L();
                }
                var1_3.t();
                oa.c = null;
                oa.b = null;
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
        graphics.fillRect(0, 0, v.t, v.u);
        try {
            this.F.a(graphics);
            if (this.S != null) {
                pc.b(graphics, this.R.a - (this.T >> 1), this.R.b, this.T, 17, 1070484, 16579764, 14542575);
                bx.d.a(graphics, this.S, this.R.a, this.R.b + 2, 1);
            }
            if (gr.a && this.V != null) {
                this.V.a(graphics);
                if (this.am != null && this.am.s) {
                    graphics.drawImage(dc.i, this.V.p() + 1, (this.V.q() - dc.i.getHeight()) / 2, 20);
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
        System.out.println("Loading map " + oa.c);
        Image image = this.g(oa.c.f, this.ac);
        this.F = new kh(image);
        this.K = om.a(oa.c.j, oa.c.d, oa.c.e);
        this.n = new a();
        int n3 = 0;
        while (n3 < oa.c.d) {
            n2 = 0;
            while (n2 < oa.c.e) {
                if (this.K[n3][n2] == 2) {
                    this.n.a(new cu(n2, n3));
                    this.K[n3][n2] = 0;
                }
                ++n2;
            }
            ++n3;
        }
        Object object = this.g(oa.c.g, this.ac);
        object = new kc((Image)object);
        this.F.a((kb)object);
        n2 = om.a(oa.c.k, oa.c.m);
        byte[] byArray = oa.b[n2];
        object = byArray;
        h.b(byArray, (this.ac << 1) / 3);
        object = f.a((byte[])object);
        this.G = new kf((Image)object, om.a(oa.c.h, oa.c.d, oa.c.e), 32, 32);
        this.F.a(this.G);
        this.F.h(this.G.p());
        this.F.i(this.G.q());
        this.H = new kd();
        this.F.a(this.H);
        this.Q = new ln();
        this.L = new kg[oa.c.l.length];
        byte[] byArray2 = f.b("/gate");
        object = byArray2;
        h.b(byArray2, (this.ac << 1) / 3);
        object = f.a((byte[])object);
        int n4 = 0;
        while (n4 < oa.c.l.length) {
            jm jm2 = oa.c.l[n4];
            if (jm2.a == 1) {
                this.L[n4] = new kg(this.Q, jm2);
                this.L[n4].e = this.Q;
            } else if (jm2.a == 0) {
                this.L[n4] = new jy((Image)object, jm2, this.F.p());
            } else {
                Image image2 = this.al.a(jm2.i, true);
                if (image2 == null) {
                    image2 = this.g(jm2.i, 0);
                    this.al.a(jm2.i, image2);
                }
                this.L[n4] = new kk(image2, jm2, 1);
            }
            if (jm2.c == go.x && i == null) {
                int n5 = 0;
                if (jm2.a == 0) {
                    n5 = this.L[n4].b() == 0 ? this.L[n4].d.f : (n5 -= 26);
                }
                i = new cu(jm2.d + n5, jm2.e);
            }
            ++n4;
        }
        this.l = new kl();
        this.l.a(go.k);
        this.l.c(om.i.a, om.i.b);
        if (j == 4) {
            j = 0;
        }
        this.l.a(j, k);
        i = null;
        this.H.a(this.l);
        this.H.c = this.L;
        if (go.u != null && go.u.length > 0 && E != null) {
            Image image3 = f.d("/info/itemchest");
            int n6 = 0;
            while (n6 < go.u.length) {
                ju ju2 = new ju(image3, 1);
                ju2.l(60);
                ju2.v = go.u[n6];
                ju2.c(om.E.a, om.E.b - ju2.q());
                ju2.a(new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]}, ju2.p());
                ju2.a(new byte[][]{new byte[1], new byte[1], new byte[1], new byte[1]});
                ju2.b((byte)1);
                ju2.a((byte)1);
                ju2.w = cv.a(2) == 0 ? -1 : 1;
                this.H.d.a(ju2);
                ++n6;
            }
            go.u = new ll[0];
        }
        this.G.a(om.a(oa.c.i, oa.c.d, oa.c.e));
        this.F.a(this.l.t);
        this.S = oa.c.b;
        this.T = bx.d.a(this.S) + 10;
        this.R = new cu(v.t - this.T / 2, 0);
        this.V = new jt(go.k);
        this.a(go.k.ac);
        this.O = true;
    }

    public final void a(jo[] joArray, boolean bl2) {
        this.W.a();
        int n2 = (this.l.t.a + this.l.t.c / 2) / 32;
        int n3 = (this.l.t.b + this.l.t.d / 2) / 32;
        int n4 = n2;
        while (n4 < this.K[n3].length) {
            if (this.K[n3][n4] != 0) break;
            this.W.a(new cu(n4, n3));
            ++n4;
        }
        n4 = n2;
        while (n4 >= 0) {
            if (this.K[n3][n4] != 0) break;
            this.W.a(new cu(n4, n3));
            --n4;
        }
        a a2 = new a();
        boolean bl3 = false;
        int n5 = 0;
        while (n5 < joArray.length) {
            Image image = null;
            int n6 = 0;
            while (n6 < joArray[n5].f) {
                Object object;
                Object object2;
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
                n4 = cv.a(this.n.d());
                cu cu2 = (cu)this.n.b(n4);
                int n7 = 0;
                while (n7 < this.W.d()) {
                    object2 = (cu)this.W.b(n7);
                    if (((cu)object2).b != cu2.b) break;
                    if (cu2.a == ((cu)object2).a) {
                        a2.a(cu2);
                        this.n.a(n4);
                        if (this.n.d() > 0) {
                            n4 = cv.a(this.n.d());
                            cu2 = (cu)this.n.b(n4);
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
                    object2 = this.a(cu2.a << 5, cu2.b << 5, joArray[n5], n6 == 0 ? null : image);
                    if (object2 == null) break;
                    int n8 = 0;
                    while (n8 < this.I.d()) {
                        ki ki2 = (ki)this.I.b(n8);
                        if (ki2.n() == ((at)object2).n() && ki2.o() == ((at)object2).o()) {
                            ((ki)object2).a(0, ki2.c == 2 ? 3 : 2);
                            break;
                        }
                        k k2 = ki2.e;
                        object = object2;
                        if (((ki)object).e.a(k2)) {
                            ((ki)object2).a(0, ki2.c == 2 ? 3 : 2);
                            break;
                        }
                        ++n8;
                    }
                    this.I.a(object2);
                } else {
                    object2 = this.a(cu2.a << 5, cu2.b << 5, joArray[n5], n6 == 0 ? null : image);
                    if (object2 == null) break;
                    this.I.a(object2);
                }
                if (n6 == 0) {
                    object = object2;
                    image = ((ki)object).g;
                }
                a2.a(cu2);
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

    public final void a(jo[] joArray) {
        int n2 = 0;
        while (n2 < joArray.length) {
            int n3 = 0;
            block1: while (n3 < this.I.d()) {
                Object object = (ki)this.I.b(n3);
                if (object != null && ((ki)object).f.a.equals(joArray[n2].a)) {
                    this.I.a(n3);
                    object = joArray[n2].a;
                    kd kd2 = this.H;
                    int n4 = 0;
                    while (n4 < kd2.b.d()) {
                        if (kd2.b.b(n4) instanceof ki) {
                            ki ki2 = (ki)kd2.b.b(n4);
                            if (ki2.f.a.equals(object)) {
                                kd2.b.a(n4);
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

    private ki a(int n2, int n3, jo object, Image object2) {
        int n4;
        Image image;
        if (object == null) {
            return null;
        }
        switch (((jo)object).c >> 1) {
            case 0: {
                image = this.r;
                n4 = 14;
                break;
            }
            case 1: {
                image = this.s;
                n4 = 5;
                break;
            }
            default: {
                image = this.t;
                n4 = 13;
            }
        }
        object = new ki(image, 1, 6, (jo)object, go.k, (Image)object2);
        ((ki)object).c(n4);
        ((ki)object).a(((at)object).n(), ((at)object).o(), ((at)object).p() - 12, 20);
        int n5 = cv.a(2, 4);
        object2 = object;
        ((ki)object).d = n5;
        ((ki)object).c(n2 - (((at)object).p() - 32), n3 - (((at)object).q() - 32));
        ((ki)object).a(0, cv.a(2, 3));
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
            Object object;
            int n3;
            block54: {
                block53: {
                    Object object2;
                    block55: {
                        block57: {
                            block58: {
                                block56: {
                                    if (!this.O) {
                                        ct.b("chua init map complete k update");
                                        return;
                                    }
                                    if (this.Z) {
                                        this.u.d(go.w);
                                        this.Z = false;
                                    }
                                    if (this.Y > 0) {
                                        --this.Y;
                                        if (this.Y == 0) {
                                            ag.b().a(this.q, false);
                                        } else if (this.Y < 0) {
                                            this.q();
                                        }
                                    }
                                    if (this.N) {
                                        this.l.i();
                                        kl kl2 = this.l;
                                        if (kl2.j != 4) {
                                            this.l.a(4);
                                        }
                                        return;
                                    }
                                    if (this.l == null) break block54;
                                    this.m.a(this.l, this.F);
                                    object2 = this.l;
                                    if (((kl)object2).j == 4) break block55;
                                    if (this.M != null) break block56;
                                    n3 = 0;
                                    while (n3 < this.L.length) {
                                        if (this.l.t.b(this.L[n3].d.d, this.L[n3].d.e, this.L[n3].d.f, this.L[n3].d.g)) {
                                            this.M = this.L[n3];
                                            this.ab = null;
                                            this.D = this.am.s();
                                            if (this.M.d.a == 2) {
                                                if (this.M.c()) break;
                                                object = this;
                                                ((om)object).am.v();
                                                object2 = (kk)this.M;
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
                                            if (this.H.d.b(n2) instanceof ju) {
                                                this.ab = (ka)this.H.d.b(n2);
                                                if (this.ab.r() == 1 && this.ab.h() == 0 && this.l.t.b(this.ab.n(), this.ab.o(), this.ab.p(), this.ab.q())) {
                                                    this.D = this.am.s();
                                                    this.am.c(this.A);
                                                    break block53;
                                                }
                                            }
                                            if (n2 == n3 - 1) {
                                                this.ab = null;
                                                this.am.c(this.C);
                                            }
                                            ++n2;
                                        }
                                    }
                                    break block53;
                                }
                                if (this.M.d.a != 0) break block57;
                                object2 = this.l;
                                if (((kl)object2).j == 1) break block58;
                                object2 = this.l;
                                if (((kl)object2).j != 7) break block57;
                            }
                            switch (this.M.b()) {
                                case 0: {
                                    if (this.o || (this.l.k & 4) == 0 || this.l.t.a > this.M.n() + (this.M.p() >> 1)) break;
                                    this.e(false);
                                    this.u.b(go.w, this.M.d.c);
                                    com.mg.sq.a.s().a((String)null, (il)null);
                                    this.m.a();
                                    this.o = true;
                                    return;
                                }
                                default: {
                                    if (this.o || (this.l.k & 8) == 0 || this.l.t.a + this.l.t.c < this.M.n() + (this.M.p() >> 1)) break;
                                    this.u.b(go.w, this.M.d.c);
                                    com.mg.sq.a.s().a((String)null, (il)null);
                                    this.m.a();
                                    this.o = true;
                                    return;
                                }
                            }
                        }
                        if (!this.l.t.b(this.M.d.d, this.M.d.e, this.M.d.f, this.M.d.g)) {
                            if (this.M.d.a == 2) {
                                object2 = this;
                                ((om)object2).am.w();
                            }
                            this.M = null;
                            this.am.c(this.D);
                            this.D = null;
                        }
                        break block53;
                    }
                    if (this.l.m()) {
                        n3 = this.I.d();
                        n2 = 0;
                        while (n2 < n3) {
                            object = (ki)this.I.b(n2);
                            if (((at)object).m()) {
                                object2 = object;
                                if (this.l.u.a(((ki)object2).e)) {
                                    this.a((ki)object, true);
                                    this.U = n2;
                                    if (((ki)object).e.a < this.l.t.a) {
                                        ((ki)object).b(3);
                                        this.l.b(4);
                                    } else {
                                        ((ki)object).b(2);
                                        this.l.b(8);
                                    }
                                    break;
                                }
                            }
                            ++n2;
                        }
                    }
                }
                this.F.a(this.l.t);
            }
            try {
                n3 = 0;
                n2 = this.I.d();
                while (n3 < n2) {
                    object = (ki)this.I.b(n3);
                    if (object == null) break;
                    if (this.U != n3) {
                        this.J.a((ki)object, this.l, this.K, this.F, this.aq);
                        if (((ki)object).a() == 1 && !com.mg.sq.a.s().c(191919)) {
                            this.U = n3;
                            this.a((ki)object, false);
                            this.am.c(null);
                        }
                    }
                    if (this.N && this.U == n3) {
                        ((ki)object).i();
                        if (((ki)object).a() != 1) {
                            ((ki)object).a(1);
                        }
                    }
                    ++n3;
                }
                n2 = 0;
                int n4 = this.H.d.d();
                while (n2 < n4) {
                    ka ka2 = (ka)this.H.d.b(n2);
                    if (ka2.r() == 1) {
                        this.aa.a(ka2, this.F);
                        if (!ka2.m()) {
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
                ct.a("[Map] Loi update monster");
            }
            if (this.Q != null) {
                this.Q.i();
            }
            if (this.F != null) {
                this.F.i();
            }
            if (this.ae) {
                this.ae = false;
                this.X.u();
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
            com.mg.sq.a.s().j(1);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            if (this.ao > 9) {
                this.ao = 0;
                com.mg.sq.a.s().j(0);
            }
            this.ao = (byte)(this.ao + 1);
            ct.a("Null pointer map " + this.ao);
            return;
        }
        this.ao = 0;
    }

    protected final void a() {
        if (this.aj == null || this.ai == null) {
            return;
        }
        try {
            this.ai = (gj)this.an.a();
            int n2 = this.aj.n() + this.aj.p();
            this.ai.f(n2 + 5);
            this.ai.g(this.aj.o() - this.ai.q() - 5);
            this.ai.a(10);
            this.ai.a(new k(this.ai.n() + 9, this.ai.o() + 9, this.ai.p() - 20, this.ai.q() - 20));
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void q() {
        ki ki2;
        this.Y = 0;
        ag.b().a(false);
        this.l.a(0);
        this.e(false);
        if (this.U >= 0 && (ki2 = (ki)this.I.b(this.U)) != null) {
            ki2.a(0);
        }
        this.m.a();
        this.l.x = 30;
        this.N = false;
        this.U = -1;
    }

    private void a(ki ki2, boolean bl2) {
        this.y();
        ct.b("fightingMonster");
        try {
            if (!bl2) {
                ag.a().b(10);
            }
            ag.b().l();
            this.N = true;
            this.Y = 10;
            this.r();
            this.e(false);
            E = new cu(ki2.e.a + ki2.e.c / 2, ki2.e.b + ki2.e.d);
            this.q = new ha(this.l.e.u(), ki2.b(), false, bl2, 99030, (bf)this.X);
            this.q.a(this.am);
            int cfr_ignored_0 = ki2.f.d;
            int cfr_ignored_1 = ki2.f.e;
            ks.a().a(ki2.f.a, bl2);
            return;
        }
        catch (Throwable throwable) {
            ct.b("fightering monster err");
            throwable.printStackTrace();
            return;
        }
    }

    public final void r() {
        if (this.l != null) {
            i = new cu(this.l.t.a, this.l.t.b);
            j = (byte)(this.l.j == 5 ? 6 : (byte)this.l.j);
            k = this.l.k;
        }
    }

    public final void s() {
        if (!this.O) {
            return;
        }
        try {
            int n2 = 0;
            while (n2 < this.H.d.d()) {
                if (this.H.d.b(n2) instanceof ka) {
                    this.H.d.b(n2);
                }
                ++n2;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 105: {
                om om2 = this;
                if (om2.N || om2.X == null || om2.am == null) break;
                bs bs2 = new bs();
                oa cfr_ignored_0 = om2.X;
                br[] brArray = oa.a(om2.am);
                if (brArray != null) {
                    bs2.a(brArray);
                    int n4 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
                    bs2.a_(-n4, om2.f() - bs2.f() + n4);
                    bs2.d(0, v.u - ba.a - bs2.f());
                    bs2.a(om2);
                    bs2.c(om2.C);
                    bs2.a(om2.y);
                    bs2.b(om2.z);
                    om2.m.a();
                    if (om2.an.a() != null && om2.an.a() instanceof gj) {
                        om2.an.a().b(false);
                        om2.an.i();
                        gj gj2 = (gj)om2.an.a();
                        br[] brArray2 = bs2.s();
                        at at2 = null;
                        if (gj2 != null) {
                            int n5 = 0;
                            while (n5 < brArray2.length) {
                                if (brArray2[n5].b().equals(gj2.b())) {
                                    at2 = brArray2[n5];
                                }
                                ++n5;
                            }
                            if (at2 != null) {
                                n5 = at2.n() + at2.p();
                                gj2.f(n5 + 5);
                                gj2.g(at2.o());
                                gj2.a(gj2.n() + 10);
                                gj2.a(new k(gj2.n() + 9, gj2.o() + 9, gj2.p() - 20, gj2.q() - 20));
                                gj2.c(true);
                                om2.aj = at2;
                                om2.ai = gj2;
                                gj2.c();
                            }
                        }
                    }
                    om2.am.a(bs2);
                }
                return;
            }
            case 101: {
                com.mg.sq.a.s().a((String)null, (il)null);
                i = null;
                this.e(false);
                this.m.a();
                this.l.a(0);
                this.u.b(go.w, this.M.d.c);
                return;
            }
            case 107: {
                this.am.c(this.C);
                this.m.a();
                this.af = this.M.a();
                this.ak = this.M.d.c;
                this.u.b(go.w, this.ak);
                com.mg.sq.a.s().a((String)null, (il)null);
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
                if (go.b()) {
                    com.mg.sq.a.a((bf)this, "H\u00e0nh Trang", 114, "\u0110\u00f3ng", 115);
                    return;
                }
                com.mg.sq.a.a(this.ab.v, this, "Nh\u1eb7t", 2000, "B\u1ecf qua", 1000);
                return;
            }
            case 2000: {
                om om3 = this;
                om3.ag.a(om3.ab.v);
                om3.u.b(new String[]{om3.ab.v.c});
                ag.b().a(241212, false);
                om3.H.d.b(om3.ab);
                om3.ab = null;
                om3.am.c(om3.C);
                return;
            }
            case 1000: {
                ag.b().a(241212, false);
                this.H.d.b(this.ab);
                this.ab = null;
                this.am.c(this.C);
                return;
            }
            case 112: {
                if (ag.b().c(241212)) {
                    Object object = (hg)ag.b().d(241212);
                    object = ((hg)object).k;
                    go.a((ll)object);
                    this.u.b(new String[]{((ll)object).c});
                    ag.b().a(241212, false);
                }
                this.am.c(this.C);
                return;
            }
            case 114: {
                ag.b().a(241209, false);
                this.X.v();
                return;
            }
            case 115: {
                ag.b().a(241209, false);
                return;
            }
            case 118: {
                kk kk2 = (kk)this.M;
                if (this.M == null) break;
                break;
            }
            case -999: {
                ag.b().a(false);
                return;
            }
            default: {
                ks.a().a(n3 - 99999990, go.w, this.ak);
                com.mg.sq.a.s().a((String)null, (il)null);
                ag.b().a(-9898989, false);
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
        if (v.ai) {
            char c2 = ae.a(n2);
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
                    if (this.M.b() != 0 || this.o || this.l.t.a > this.M.n() + this.M.p() / 2) break;
                    i = null;
                    this.e(false);
                    this.u.b(go.w, this.M.d.c);
                    com.mg.sq.a.s().a((String)null, (il)null);
                    this.o = true;
                    break;
                }
                case 96: {
                    if (this.M.b() != 1 || this.o || this.l.t.a + this.l.t.c + 10 < this.M.n() + this.M.p() / 2) break;
                    i = null;
                    this.e(false);
                    this.u.b(go.w, this.M.d.c);
                    com.mg.sq.a.s().a((String)null, (il)null);
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
        if (v.ai) {
            char c2 = ae.a(n2);
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

    public final void a(lh lh2, boolean bl2) {
        this.V.a(lh2);
        if (bl2) {
            this.l.b(lh2);
            this.l.a(lh2);
        }
    }

    public final void e(boolean bl2) {
        this.l.b(bl2);
    }

    public final boolean c(int n2, int n3) {
        if (!this.O) {
            return false;
        }
        if (new k(this.l.n() - v.t / 2, this.l.o(), v.t / 2, this.l.q()).a(n2 -= this.F.n(), n3 -= this.F.o())) {
            this.f(97);
            return true;
        }
        if (new k(this.l.n() + this.l.p(), this.l.o(), v.t / 2, this.l.p()).a(n2, n3)) {
            this.f(96);
            return true;
        }
        if (new k(this.l.n(), this.l.o() - v.u / 2, this.l.p(), v.u / 2).a(n2, n3)) {
            this.f(99);
            return true;
        }
        if (new k(this.l.n(), this.l.o() + this.l.q(), this.l.p(), v.u / 2).a(n2, n3)) {
            this.f(98);
            return true;
        }
        if (new k(this.l.n() - v.t / 2, this.l.o() - v.u / 2, v.t / 2, v.u / 2).a(n2, n3)) {
            this.f(99);
            this.f(97);
            return true;
        }
        if (new k(this.l.n() + this.l.p(), this.l.o() - v.u / 2, v.t / 2, v.u / 2).a(n2, n3)) {
            this.f(99);
            this.f(96);
            return true;
        }
        if (new k(this.l.n(), this.l.o(), this.l.p(), this.l.q()).a(n2, n3)) {
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
        if (this.l.j == 7) {
            this.g(97);
            this.g(96);
            this.g(99);
            this.g(98);
        }
        if (new k(this.l.n() - v.t / 2, this.l.o(), v.t / 2, this.l.q()).a(n2, n3)) {
            this.g(97);
            return true;
        }
        if (new k(this.l.n() + this.l.p(), this.l.o(), v.t / 2, this.l.p()).a(n2, n3)) {
            this.g(96);
            return true;
        }
        if (new k(this.l.n(), this.l.o() - v.u / 2, this.l.p(), v.u / 2).a(n2, n3)) {
            this.g(99);
            return true;
        }
        if (new k(this.l.n(), this.l.o() + this.l.q(), this.l.p(), v.u / 2).a(n2, n3)) {
            this.g(98);
            return true;
        }
        if (new k(this.l.n() - v.t / 2, this.l.o() - v.u / 2, v.t / 2, v.u / 2).a(n2, n3)) {
            this.g(99);
            this.g(97);
            return true;
        }
        if (new k(this.l.n() + this.l.p(), this.l.o() - v.u / 2, v.t / 2, v.u / 2).a(n2, n3)) {
            this.g(99);
            this.g(96);
            return true;
        }
        if (new k(this.l.n(), this.l.o(), this.l.p(), this.l.q()).a(n2, n3)) {
            return this.g(95);
        }
        return false;
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        br br2 = (br)object;
        if (this.ai != null && br2.b().equals(this.ai.b())) {
            this.ai.b(false);
        }
        this.X.a(n3, br2);
        this.y();
    }

    private void y() {
        Object object;
        this.am.t();
        this.aj = null;
        this.ai = null;
        if (this.an.a() != null && this.an.a() instanceof gj) {
            this.an.i();
            object = (gj)this.an.a();
            if (object != null && ((gj)object).d()) {
                ((at)object).b(false);
                this.an.i();
                object = (gj)this.an.a();
            }
            if (object != null) {
                ((gj)object).d(true);
                ((at)object).g(this.d() + this.f() - ((at)object).q() - 30);
                ((at)object).f(this.c() + 5);
                ((gj)object).a(10);
                ((gj)object).a(new k(((at)object).n() + 9, ((at)object).o() + 9, ((at)object).p() - 20, ((at)object).q() - 20));
                ((gj)object).c(true);
            }
        }
        if ((object = ag.b().e()) != null && ((am)object).h() == 241203) {
            object = (ib)object;
            ((ib)object).j(true);
        }
    }

    private Image g(int n2, int n3) {
        byte[] byArray = oa.b[om.a(n2, oa.c.m)];
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
        if (gr.m && go.k.G < 3) {
            gr.m = false;
            try {
                int n2 = bx.c.a() * 3 + 20;
                object = new gj("Ch\u00e0o m\u1eebng \u0111\u1ebfn v\u1edbi s\u1ee9 qu\u00e2n online! H\u00e3y ki\u1ec3m tra nhi\u1ec7m v\u1ee5. Ch\u1ecdn Menu > Nhi\u1ec7m v\u1ee5", this.c() + 15, this.d() + this.f() - 30, n2);
                ((at)object).f(this.c() + 5);
                ((gj)object).a(10);
                ((gj)object).a(new k(((at)object).n() + 9, ((at)object).o() + 9, ((at)object).p() - 20, ((at)object).q() - 20));
                ((gj)object).c(true);
                this.an.a((at)object);
                gj gj2 = this.e() > 240 ? new gj("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nhi\u1ec7m v\u1ee5", this.c() + 15, this.d() + this.f() - 30, n2) : new gj("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nhi\u1ec7m v\u1ee5", this.c() + 15, this.d() + this.f() - 30, this.e() - 80, n2, false);
                gj2.a(new String[]{"Nhi\u1ec7m V\u1ee5", "Nhi\u1ec7m V\u1ee5"});
                gj2.f(this.c() + 5);
                gj2.a(10);
                gj2.a(new k(gj2.n() + 9, gj2.o() + 9, gj2.p() - 20, gj2.q() - 20));
                gj2.c(true);
                ((gj)object).a(gj2);
                this.an.a(gj2);
                bl2 = false;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        if (gr.n && go.k.G <= 6) {
            int[] nArray = new int[9];
            try {
                int n3 = 0;
                while (n3 < go.k.E.length) {
                    lv lv2 = go.k.E[n3];
                    int n4 = lv2.a % 100;
                    nArray[n4] = go.k.E[n3].f;
                    ++n3;
                }
                int n5 = 0;
                while (n5 < go.r.length) {
                    n3 = nArray[n5];
                    if (n3 == 2) {
                        n3 = 1;
                    }
                    if (n3 < go.r[n5].c.length && go.r[n5].c[n3].b <= go.k.G && go.k.L >= go.r[n5].c[n3].c) {
                        gr.n = false;
                        int n6 = bx.c.a() * 3 + 20;
                        gj gj3 = new gj("B\u1ea1n v\u1eeba l\u00ean c\u1ea5p, c\u00f3 th\u1ec3 t\u0103ng \u0111i\u1ec3m tuy\u1ec7t chi\u00eau. B\u1ea5m menu tr\u00e1i > Nh\u00e2n v\u1eadt > Tuy\u1ec7t chi\u00eau", this.c() + 15, this.d() + this.f() - 30, n6);
                        gj3.f(this.c() + 5);
                        gj3.a(10);
                        gj3.a(new k(gj3.n() + 9, gj3.o() + 9, gj3.p() - 20, gj3.q() - 20));
                        gj3.c(true);
                        gj3.d(bl2);
                        this.an.a(gj3);
                        gj gj4 = this.e() > 240 ? new gj("Ch\u1ecdn Nh\u00e2n v\u1eadt > Tuy\u1ec7t chi\u00eau", this.c() + 15, this.d() + this.f() - 30, n6) : new gj("Ch\u1ecdn Nh\u00e2n v\u1eadt > Tuy\u1ec7t chi\u00eau", this.c() + 15, this.d() + this.f() - 30, this.e() - 80, n6, false);
                        gj4.f(this.c() + 5);
                        gj4.a(10);
                        gj4.a(new k(gj4.n() + 9, gj4.o() + 9, gj4.p() - 20, gj4.q() - 20));
                        gj4.a(new String[]{"Nh\u00e2n V\u1eadt", "Tuy\u1ec7t Chi\u00eau"});
                        gj4.c(true);
                        gj3.a(gj4);
                        this.an.a(gj4);
                        return;
                    }
                    ++n5;
                }
                return;
            }
            catch (Exception exception) {
                object = exception;
                ct.a(exception);
            }
        }
    }

    public final void a(String object, byte by2) {
        if (object == null) {
            this.X.u();
            return;
        }
        try {
            if (this.M != null) {
                if (this.M.d.b.equals(this.af)) {
                    as as2 = new as(this.M.f, 1);
                    as2.a(new byte[][]{new byte[1]});
                    as2.c(2);
                    object = new ic(this.M.n(), this.M.o() - 10, this.F.a(), as2, (String)object, this.M.d.b);
                    ag.b().a((al)object);
                    return;
                }
                this.af = null;
                return;
            }
            com.mg.sq.a.s().q((String)object);
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
        int n2 = 0;
        while (n2 < this.L.length) {
            if (this.L[n2].d.a == 2) {
                kk kk2 = (kk)this.L[n2];
                kk2.a(false, 0);
                kk2.d();
                this.ae = false;
                if (this.l.t.b(this.L[n2].d.d, this.L[n2].d.e, this.L[n2].d.f, this.L[n2].d.g)) {
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
        int n2 = 0;
        while (n2 < ((String[])object).length) {
            int n3 = 0;
            while (n3 < this.ag.d()) {
                ll ll2 = (ll)this.ag.b(n3);
                if (object[n2].equals(ll2.c)) {
                    go.a(ll2);
                    this.ag.a(n3);
                    break;
                }
                ++n3;
            }
            ++n2;
        }
        if (gr.o) {
            gr.o = false;
            n2 = bx.c.a() * 3 + 20;
            gj gj2 = new gj("B\u1ea1n v\u1eeba c\u00f3 v\u1eadt ph\u1ea9m. H\u00e3y b\u1ea5m ph\u00edm menu tr\u00e1i > Nh\u00e2n v\u1eadt > R\u01b0\u01a1ng \u0111\u1ed3", this.c() + 15, this.d() + this.f() - 30, n2);
            gj2.f(this.c() + 5);
            gj2.a(10);
            gj2.a(new k(gj2.n() + 9, gj2.o() + 9, gj2.p() - 20, gj2.q() - 20));
            gj2.c(true);
            this.an.a(gj2);
            object = this.e() > 240 ? new gj("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nh\u00e2n v\u1eadt > R\u01b0\u01a1ng \u0111\u1ed3", this.c() + 15, this.d() + this.f() - 30, n2) : new gj("T\u1ed1t l\u1eafm! H\u00e3y ch\u1ecdn Nh\u00e2n v\u1eadt > R\u01b0\u01a1ng \u0111\u1ed3", this.c() + 15, this.d() + this.f() - 30, this.e() - 80, n2, false);
            ((gj)object).a(new String[]{"Nh\u00e2n V\u1eadt", "R\u01b0\u01a1ng \u0110\u1ed3"});
            ((at)object).f(this.c() + 5);
            ((gj)object).a(10);
            ((gj)object).a(new k(((at)object).n() + 9, ((at)object).o() + 9, ((at)object).p() - 20, ((at)object).q() - 20));
            ((gj)object).c(true);
            gj2.a((gj)object);
            this.an.a((at)object);
        }
    }

    public final void a(int[] nArray, String[] object) {
        int n2;
        he he2 = new he();
        he2.b(-9898989);
        he2.a(new ba());
        he2.a(this);
        int n3 = 10;
        aq[] aqArray = new ex[nArray.length];
        int n4 = 0;
        int n5 = 0;
        while (n5 < nArray.length) {
            n2 = bx.d.a(object[n5]) + 30;
            if (n2 > n4) {
                n4 = n2;
            }
            aqArray[n5] = new ex(object[n5], nArray[n5] + 99999990);
            aqArray[n5].a(10, n3, n4, 18);
            n3 += aqArray[n5].f() + 5;
            ++n5;
        }
        he2.a(aqArray);
        n5 = aqArray.length - 1;
        while (n5 >= 0) {
            aqArray[n5].d(n4);
            --n5;
        }
        n5 = n3 + 5;
        n2 = v.t - (n4 += 20) >> 1;
        int n6 = v.u - n5 >> 1;
        he2.a(n2, n6, n4, n5);
        object = new gb(-999, 3);
        he he3 = he2;
        ((am)he3).b((az)object, true);
        if (this.M != null) {
            he2.a(he2.a(), this.M.o() + this.F.o() - he2.j(), he2.i(), he2.j());
        }
        he2.f(0);
        ag.b().a(he2, false);
    }

    public final void w() {
        this.am.a((bf)null);
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
        ct.b("Finish Map.destroy()");
    }

    public final void a(lt[] ltArray) {
        if (ltArray != null) {
            this.ap = new jx[ltArray.length];
            int n2 = 0;
            while (n2 < ltArray.length) {
                int n3 = ltArray[n2].a;
                pa pa2 = pa.a();
                pa2 = f.a(pa2.b(n3, false));
                this.ap[n2] = new jx((Image)pa2, 1);
                this.ap[n2].a(ltArray[n2].b);
                this.aq = ltArray[n2].a != 200000 || ltArray[n2].b <= 0L;
                ++n2;
            }
        }
    }
}

