/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class dd
extends au
implements bj,
bx {
    private cx k;
    private cx l;
    private n m = null;
    private n n = null;
    private n o = null;
    private n p = null;
    private n[] q = new n[7];
    private n[] r = new n[8];
    private Image s;
    private Image t;
    private Image u;
    private int[] v;
    private int[] w;
    private int x;
    private static final int[] y = new int[]{1, 1, 1, 1};
    private static final int[] z = new int[]{1, 1, 1, 1};
    private int A = 0;
    private int[][] B;
    private int C = 1;
    private int D;
    private ft[] E = new ft[8];
    private me F = null;
    private me G = null;
    private me H = null;
    private me I = null;
    private ma J = null;
    private boolean K = false;
    private lf L;
    private boolean M = true;
    private cx N;
    public boolean i = true;
    private n O;
    private n P;
    private n Q;
    private String[] R = new String[]{"Danh Hi\u1ec7u", "X\u1ebfp H\u1ea1ng", "Danh v\u1ecdng", "Th\u1eafng/Thua"};
    private String[] S = new String[]{"C\u01b0\u1eddng L\u1ef1c", "N\u1ed9i L\u1ef1c", "Th\u00e2n Ph\u00e1p", "Th\u1ec3 L\u1ef1c"};
    private String[] T = new String[]{"T\u1ea5n C\u00f4ng", "Ch\u00ednh x\u00e1c", "Sinh l\u1ef1c"};
    private String[] U = new String[]{"P.Th\u1ee7", "N\u00e9 Tr\u00e1nh", "Ch\u00ed M\u1ea1ng"};
    private cx V;
    private cx W;
    private cx X;
    public n j;
    private boolean Y = false;
    private int Z;
    private int aa;
    private n ab;
    private n ac;
    private jy ad;
    private int ae;
    private int af;
    private int ag;
    private int ah;
    private int ai;
    private int aj;
    private int ak;
    private String al = "";
    private String am = "";
    private String an = "";
    private int ao = 0;
    private int ap = 0;
    private int aq = 0;
    private int ar = 0;
    private int as = 0;
    private int at = 0;
    private int au = 0;
    private int av = 0;
    private int aw;
    private boolean ax;
    private int ay;
    private int az;
    private int aA = 0;
    private int[][] aB;
    private static int[] aC = new int[]{1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007};
    private String aD;
    private int[] aE;
    private int[] aF;
    private int[] aG;
    private byte[] aH;

    public dd() {
        byte[] byArray = new byte[4];
        byArray[1] = 2;
        byArray[2] = 1;
        this.aH = byArray;
        this.aE = new int[]{7930113, 12386818, 13894146, 0xFD0D0D, 0xFD0D0D, 12911106, 12911106, 10289666, 10289666, 9372162, 9372162, 9372162, 9372162};
        this.aF = new int[]{227841, 375554, 381954, 719882, 719882, 377602, 377602, 3710992, 236033, 236033, 231937, 231937, 231937};
        this.aG = new int[]{14067456, 15579392, 15579392, 16765211, 16765211, 14593280, 14593280, 12095488, 12095488, 11109376, 11109376, 11109376, 11109376};
        int n2 = 240;
        int n3 = 320 - be.a;
        if (com.mg.sq.a.i == 1) {
            n2 = z.r;
        }
        this.j = new n(0, 0, n2, n3);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.K) {
            return;
        }
        Object object = this;
        object = ((bc)((au)object).b).q();
        n2 = this.j.a + n2 - ((n)object).a;
        int n4 = n3 = this.j.b + n3 - ((n)object).b;
        int n5 = n2;
        Graphics graphics2 = graphics;
        object = this;
        oz.d(graphics2, n5, n4, ((dd)object).j.c, ((dd)object).j.d, z.ac);
        oz.c(graphics2, ((dd)object).m.a + n5, ((dd)object).m.b + n4, ((dd)object).m.c, ((dd)object).m.d);
        int n6 = 0;
        int n7 = 0;
        while (n7 < 4) {
            oz.b(graphics2, ((dd)object).O.a + n5, ((dd)object).O.b + n6 + n4, ((dd)object).O.c, ((dd)object).O.d, 1070484, 16579764, 14542575);
            ca.c.a(graphics2, ((dd)object).R[n7], ((dd)object).O.a - 2 + n5, ((dd)object).O.b + n4 + n6, 2);
            n6 += ((dd)object).O.d + 2;
            ++n7;
        }
        n6 = 0;
        n7 = 0;
        while (n7 < 3) {
            oz.b(graphics2, ((dd)object).ab.a + n5, ((dd)object).ab.b + n6 + n4, ((dd)object).ab.c, ((dd)object).ab.d, 1070484, 16579764, 14542575);
            ca.c.a(graphics2, ((dd)object).T[n7], ((dd)object).ab.a - 5 + n5, ((dd)object).ab.b + n4 + n6, 2);
            n6 += ((dd)object).ab.d + 3;
            ++n7;
        }
        n6 = 0;
        n7 = 0;
        while (n7 < 3) {
            oz.b(graphics2, ((dd)object).ac.a + n5, ((dd)object).ac.b + n6 + n4, ((dd)object).ac.c, ((dd)object).ac.d, 1070484, 16579764, 14542575);
            ca.c.a(graphics2, ((dd)object).U[n7], ((dd)object).ac.a - 5 + n5, ((dd)object).ac.b + n4 + n6, 2);
            n6 += ((dd)object).ac.d + 3;
            ++n7;
        }
        n6 = 0;
        n7 = 0;
        while (n7 < 4) {
            oz.b(graphics2, ((dd)object).P.a + n5, ((dd)object).P.b + n6 + n4, ((dd)object).P.c, ((dd)object).P.d, 1070484, 16579764, 14542575);
            if (n7 == ((dd)object).aH[((dd)object).L.g / 2]) {
                com.mg.sq.a.h.a(graphics2, ((dd)object).S[n7], ((dd)object).P.a - 5 + n5, ((dd)object).P.b + n4 + n6, 2);
            } else {
                ca.c.a(graphics2, ((dd)object).S[n7], ((dd)object).P.a - 5 + n5, ((dd)object).P.b + n4 + n6, 2);
            }
            n6 += ((dd)object).P.d + 3;
            ++n7;
        }
        oz.a(graphics2, ((dd)object).Q.a + n5, ((dd)object).Q.b + n4, ((dd)object).Q.c, ((dd)object).Q.d, 10323806, 14273459);
        ca.d.c(true);
        ca.d.a(graphics2, "\u0110i\u1ec3m", ((dd)object).Q.a - 5 + n5, ((dd)object).Q.b + n4, 2);
        ca.d.c(false);
        graphics2.drawImage(((dd)object).u, ((dd)object).n.a - 3 + n5, ((dd)object).n.b + ((dd)object).n.d + n4, 40);
        oz.a(graphics2, ((dd)object).n.a + n5, ((dd)object).n.b + n4, ((dd)object).n.c, ((dd)object).n.d, 1463700, 16311483);
        graphics2.drawImage(((dd)object).t, ((dd)object).o.a - 3 + n5, ((dd)object).o.b + ((dd)object).o.d + n4, 40);
        oz.a(graphics2, ((dd)object).o.a + n5, ((dd)object).o.b + n4, ((dd)object).o.c, ((dd)object).o.d, 1993479, 16311483);
        graphics2.drawImage(((dd)object).s, ((dd)object).p.a - 3 + n5, ((dd)object).p.b + ((dd)object).p.d + n4, 40);
        oz.a(graphics2, ((dd)object).p.a + n5, ((dd)object).p.b + n4, ((dd)object).p.c, ((dd)object).p.d, 7293991, 16311483);
        oz.a(graphics2, ((dd)object).W.a + n5, ((dd)object).W.b + n4, ((dd)object).j.c - (((dd)object).W.a << 1));
        oz.a(graphics2, ((dd)object).V.a + n5, ((dd)object).V.b + n4, ((dd)object).j.c - (((dd)object).V.a << 1));
        if (this.L != null) {
            int n8 = (this.O.d - ca.c.a()) / 2 + 1;
            int n9 = this.O.a + this.O.c / 2 + n2;
            ca.c.a(graphics, "C\u1ea5p: " + this.L.G, this.j.c - 15 + n2, this.l.b + n3 - 2, 2);
            com.mg.sq.a.h.a(graphics, this.L.S, n9, this.O.b + n8 + n3, 1);
            ca.c.a(graphics, this.L.U, n9, this.O.b + (n8 += this.O.d + 2) + n3, 1);
            ca.c.a(graphics, String.valueOf(this.L.ad), n9, this.O.b + (n8 += this.O.d + 2) + n3, 1);
            ca.c.a(graphics, String.valueOf(this.L.O) + "/" + this.L.P, n9, this.O.b + (n8 += this.O.d + 2) + n3, 1);
            n9 = this.P.a + this.P.c / 2 + n2;
            n8 = (this.O.d - ca.d.a()) / 2;
            n5 = this.P.d + 3;
            com.mg.sq.a.g.a(graphics, String.valueOf(this.w[0] + this.ao), n9, this.P.b + n8 + n3, 1);
            if (this.L.l > 0) {
                n4 = ca.c.a(String.valueOf(this.w[0] + this.ao));
                com.mg.sq.a.h.a(graphics, " + " + this.L.l, n9 + n4 / 2, this.P.b + n8 + n3, 0);
            }
            com.mg.sq.a.g.a(graphics, String.valueOf(this.w[1] + this.ap), n9, this.P.b + (n8 += n5) + n3, 1);
            if (this.L.n > 0) {
                n4 = ca.c.a(String.valueOf(this.w[1] + this.ap));
                com.mg.sq.a.h.a(graphics, " + " + this.L.n, n9 + n4 / 2, this.P.b + n8 + n3, 0);
            }
            com.mg.sq.a.g.a(graphics, String.valueOf(this.w[2] + this.aq), n9, this.P.b + (n8 += n5) + n3, 1);
            if (this.L.m > 0) {
                n4 = ca.c.a(String.valueOf(this.w[2] + this.aq));
                com.mg.sq.a.h.a(graphics, " + " + this.L.m, n9 + n4 / 2, this.P.b + n8 + n3, 0);
            }
            com.mg.sq.a.g.a(graphics, String.valueOf(this.w[3] + this.ar), n9, this.P.b + (n8 += n5) + n3, 1);
            if (this.L.o > 0) {
                n4 = ca.c.a(String.valueOf(this.w[3] + this.ar));
                com.mg.sq.a.h.a(graphics, " + " + this.L.o, n9 + n4 / 2, this.P.b + n8 + n3, 0);
            }
            n9 = this.ab.a + this.ab.c / 2 + n2;
            n8 = (this.ab.d - ca.d.a()) / 2 + 1;
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ag), n9, this.ab.b + n8 + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.aj), n9, this.ab.b + (n8 += this.ab.d + 3) + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ae), n9, this.ab.b + (n8 += this.ab.d + 3) + n3, 1);
            n9 = this.ac.a + this.ac.c / 2 + n2;
            n8 = (this.ac.d - ca.d.a()) / 2 + 1;
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ah), n9, this.ac.b + n8 + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ai), n9, this.ac.b + (n8 += this.ac.d + 3) + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ak) + "%", n9, this.ac.b + (n8 += this.ac.d + 3) + n3, 1);
            ca.d.a(graphics, String.valueOf(this.x), this.Q.a + this.Q.c / 2 + n2, this.Q.b + (this.Q.d - ca.d.a()) / 2 + n3, 1);
            oz.a(graphics, this.n, this.af * (this.n.c - 2) / this.ae, this.aE, n2, n3);
            ca.d.a(graphics, this.an, this.n.a + (this.n.c >> 1) + n2, this.n.b + n3, 1);
            oz.a(graphics, this.o, this.Z, this.aF, n2, n3);
            ca.d.a(graphics, this.al, this.o.a + (this.n.c >> 1) + n2, this.o.b + n3, 1);
            n4 = this.L.H * this.p.c / this.L.I;
            if (n4 > this.p.c) {
                n4 %= this.p.c;
            }
            oz.a(graphics, this.p, n4, this.aG, n2, n3);
            ca.d.a(graphics, this.am, this.p.a + (this.p.c >> 1) + n2, this.p.b + n3, 1);
            n8 = n2 + this.m.a;
            n9 = n3 + this.m.b;
            if (this.F != null) {
                this.F.a(graphics, n8, n9 + 4);
            }
            n8 = n2 + this.k.a;
            n9 = n3 + this.k.b;
            oz.b(graphics, n8, n9, this.L.g);
            n8 = n2 + this.N.a;
            n9 = n3 + this.N.b - ca.d.b();
            String string = com.mg.sq.a.b(gr.p);
            ca.d.a(graphics, string, n8, n9, 0);
            n8 = n2 + this.l.a;
            n9 = n3 + this.l.b;
            ca.d.c(true);
            ca.d.a(graphics, this.aD, n8, n9, 0);
            ca.d.c(false);
            if (this.i && this.M) {
                n8 = 0;
                while (n8 < this.E.length) {
                    this.E[n8].a(graphics, n2, n3);
                    ++n8;
                }
                oz.e(graphics, this.q[this.A].a - 4 + n2, this.q[this.A].b - 4 + n3, this.q[this.A].c + 8, this.q[this.A].d + 8, this.C);
            }
        }
    }

    public final boolean a() {
        int n2 = 0;
        while (n2 < this.v.length) {
            if (this.v[n2] > 0) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private static boolean s() {
        return com.mg.sq.a.i == 1;
    }

    public final void q() {
        if (!this.K) {
            this.t = f.d("/info/expicon");
            this.u = f.d("/info/heart");
            this.s = f.d("/info/gold");
            this.O = dd.s() ? new n(170, 20, 124, 16) : new n(136, 20, 94, 16);
            this.W = new cx(10, this.O.b + (this.O.d + 2 << 2) + 6);
            int n2 = dd.s() ? this.j.c - 52 : 204;
            this.n = new n(26, this.W.b + 6 + 2, n2, 14);
            this.o = new n(26, this.n.b + this.n.d + 4, n2, 14);
            this.p = new n(26, this.o.b + this.o.d + 4, n2, 14);
            this.P = new n(81, this.p.b + this.p.d + 4, 93, 15);
            if (dd.s()) {
                this.P.c = 126;
            }
            this.Q = new n(this.p.a + this.p.c - 65, this.P.b + (this.P.d + 3 << 2) + 2, 65, 14);
            this.V = new cx(10, this.Q.b + this.Q.d + 2 + 6);
            n2 = dd.s() ? 60 : 45;
            this.ab = new n(70, this.V.b + 6 + 2, n2, 15);
            this.ac = new n(this.p.a + this.p.c - n2, this.ab.b, n2, 15);
            this.X = new cx(this.W.a, this.ac.b + (this.ac.d + 3) * 3 + 2);
            ca.d.a();
            this.k = new cx(8, 4);
            this.l = new cx(25, 7);
            this.m = new n(10, 22, 50, 59);
            this.N = new cx(10, 95);
            int n3 = 0;
            n2 = this.P.b + (this.P.d - 12) / 2;
            int n4 = this.P.a + this.P.c + 4;
            int n5 = n4 + 12 + 3;
            int n6 = 0;
            while (n6 < 4) {
                this.r[n6] = new n(n4, n2 + n3, 12, 12);
                this.r[n6 + 4] = new n(n5, n2 + n3, 12, 12);
                n3 += this.P.d - 12 + 3 + 12;
                ++n6;
            }
            Image image = f.d("/info/btinscrease");
            n2 = image.getWidth() >> 2;
            n3 = image.getHeight();
            int[][] nArrayArray = new int[4][];
            int[] nArray = new int[4];
            nArray[2] = n2;
            nArray[3] = n3;
            nArrayArray[0] = nArray;
            int[] nArray2 = new int[4];
            nArray2[0] = n2;
            nArray2[2] = n2;
            nArray2[3] = n3;
            nArrayArray[1] = nArray2;
            int[] nArray3 = new int[4];
            nArray3[0] = n2 << 1;
            nArray3[2] = n2;
            nArray3[3] = n3;
            nArrayArray[2] = nArray3;
            int[] nArray4 = new int[4];
            nArray4[0] = n2 * 3;
            nArray4[2] = n2;
            nArray4[3] = n3;
            nArrayArray[3] = nArray4;
            this.aB = nArrayArray;
            n2 = 0;
            n3 = 0;
            while (n3 < this.E.length) {
                this.E[n3] = new ft(image, aC[n3]);
                this.E[n3].a(this);
                n2 = n3 > 4 ? 2 : 0;
                this.E[n3].b(this.aB[n2][0], this.aB[n2][1], this.aB[n2][2], this.aB[n2][3]);
                this.E[n3].b(false);
                this.E[n3].a(this.r[n3].a, this.r[n3].b, this.aB[n2][2], this.aB[n2][3]);
                ++n3;
            }
            this.q = new n[]{this.r[0], this.r[1], this.r[2], this.r[3], this.r[4], this.r[5], this.r[6], this.r[7]};
            this.j.d = this.X.b + 6 + 4;
            dd dd2 = this;
            this.B = new int[dd2.q.length][4];
            dd2.B[0] = new int[]{1, -1, 4, -1};
            int[] nArray5 = new int[4];
            nArray5[0] = 2;
            nArray5[2] = 5;
            nArray5[3] = -1;
            dd2.B[1] = nArray5;
            dd2.B[2] = new int[]{3, 1, 6, -1};
            dd2.B[3] = new int[]{-1, 2, 7, -1};
            int[] nArray6 = new int[4];
            nArray6[0] = 5;
            nArray6[1] = -1;
            nArray6[2] = -1;
            dd2.B[4] = nArray6;
            dd2.B[5] = new int[]{6, 4, -1, 1};
            dd2.B[6] = new int[]{7, 5, -1, 2};
            dd2.B[7] = new int[]{-1, 6, -1, 3};
            this.ad = jo.a(this.L.g);
            this.v = new int[4];
            this.w = new int[4];
            this.c(this.L);
            this.K = true;
            this.a(this.j.a, this.j.b, this.j.c, this.j.d);
            this.aD = com.mg.sq.a.a(this.L.b, this.e() - 85);
        }
        this.u();
    }

    public final void a(lf lf2) {
        Object object;
        this.L = lf2;
        this.aq = 0;
        this.ap = 0;
        this.ao = 0;
        this.ar = 0;
        this.as = 0;
        this.au = 0;
        this.av = 0;
        this.at = 0;
        this.aw = 0;
        if (this.ad == null) {
            this.ad = jo.a(this.L.g);
            this.ad.a(this.L.h + this.ao + this.L.l, this.L.j + this.aq + this.L.m, this.L.i + this.ap + this.L.n, this.L.k + this.ar + this.L.o);
        }
        int n2 = 0;
        while (n2 < this.L.D.length) {
            int n3 = 0;
            while (n3 < gr.k.length) {
                if (gr.k[n3].b.equals(this.L.D[n2].b) && gr.k[n3].n != 0) {
                    object = gr.k[n3].p;
                    this.ao += ((kz)object).a;
                    this.ar += ((kz)object).d;
                    this.aq += ((kz)object).b;
                    this.ap += ((kz)object).c;
                    this.as += ((kz)object).e;
                    this.as += this.ad.c() * ((kz)object).n / 100;
                    this.au += ((kz)object).g;
                    this.av += ((kz)object).f;
                    this.at += ((kz)object).h;
                    this.aw += ((kz)object).i;
                }
                ++n3;
            }
            ++n2;
        }
        this.ad.a(this.L.h + this.ao + this.L.l, this.L.j + this.aq + this.L.m, this.L.i + this.ap + this.L.n, this.L.k + this.ar + this.L.o);
        lz[] lzArray = lz.a(lf2);
        np np2 = np.a(lf2);
        object = la.a(lf2);
        this.G = lz.a(lf2, false);
        this.G.a((la)object);
        this.G.a(np2);
        this.I = lz.c(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], false);
        this.I.a((la)object);
        this.I.a(np2);
        this.H = lz.h(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], false);
        this.H.a((la)object);
        this.H.a(np2);
        object = f.d("/castingball");
        this.J = lz.a(lf2, (Image)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], false);
        this.J.a(np2);
        if (!this.ax) {
            this.h(0);
        } else {
            this.h(1);
        }
        this.ax = true;
        this.x();
    }

    private void h(int n2) {
        this.ay = n2;
        switch (n2) {
            case 1: {
                this.F = this.G;
                break;
            }
            case 0: {
                this.F = this.H;
                this.F.g(-70);
                this.az = 1;
                break;
            }
            case 2: {
                this.F = this.I;
                break;
            }
            case 3: {
                this.F = this.J;
            }
        }
        this.F.c(2);
        this.F.j(20);
        this.F.i();
    }

    private void c(lf object) {
        this.v = new int[4];
        this.w = new int[4];
        int n2 = ((lf)object).J - ((lf)object).M;
        int n3 = ((lf)object).N + 1 - ((lf)object).M;
        this.Z = n2 * this.o.c / n3;
        if (this.Z > this.o.c) {
            this.Z = this.o.c - 2;
        }
        this.aa = n2 * 1000 / n3;
        this.al = String.valueOf(this.aa / 10) + "," + this.aa % 10 + "%";
        this.am = String.valueOf(l.a(((lf)object).H, ".")) + "/" + l.a(((lf)object).I, ".");
        this.t();
        object = this;
        ((dd)object).w[0] = ((dd)object).L.h;
        ((dd)object).w[2] = ((dd)object).L.j;
        ((dd)object).w[1] = ((dd)object).L.i;
        ((dd)object).w[3] = ((dd)object).L.k;
        ((dd)object).x = ((dd)object).L.K;
        if (((dd)object).L.K <= 0) {
            ((dd)object).M = false;
        }
    }

    private void t() {
        int n2 = 0;
        int n3 = this.E.length / 2;
        while (n2 < n3) {
            if (this.x >= y[n2]) {
                this.E[n2].b(true);
                this.E[n2].b(this.aB[1][0], this.aB[1][1], this.aB[1][2], this.aB[1][3]);
            } else {
                this.E[n2].b(false);
                this.E[n2].b(this.aB[0][0], this.aB[0][1], this.aB[0][2], this.aB[0][3]);
            }
            if (this.v[n2] > 0) {
                this.E[n2 + 4].b(true);
                this.E[n2 + 4].b(this.aB[3][0], this.aB[3][1], this.aB[3][2], this.aB[3][3]);
            } else {
                this.E[n2 + 4].b(false);
                this.E[n2 + 4].b(this.aB[2][0], this.aB[2][1], this.aB[2][2], this.aB[2][3]);
            }
            ++n2;
        }
    }

    private void u() {
        this.t();
        this.ad.a(this.w[0] + this.ao + this.L.l, this.w[2] + this.aq + this.L.m, this.w[1] + this.ap + this.L.n, this.w[3] + this.ar + this.L.o);
        this.x();
    }

    private void x() {
        this.ae = this.ad.a() + this.aw + this.L.p;
        this.af = this.L.s + (this.ae - this.L.r) * this.L.q / 100 / 100;
        this.ai = this.ad.e() + this.at;
        this.ak = this.ad.g() + this.au;
        this.ah = this.ad.d() + this.av;
        this.aj = this.ad.f();
        this.ag = this.ad.b() + this.as;
        this.an = String.valueOf(l.a(this.af, ".")) + "/" + l.a(this.ae, ".");
    }

    public final void n() {
        if (!this.K) {
            return;
        }
        switch (this.ay) {
            case 0: {
                if (this.F.f(0, this.az) && this.F.j()) {
                    this.h(2);
                }
                this.az += 3;
                break;
            }
            case 2: {
                if (!this.F.j()) break;
                ++this.aA;
                if (this.aA < 3) break;
                this.h(3);
                this.aA = 0;
                break;
            }
            case 3: {
                int n2 = this.F.h();
                if (n2 == 1) {
                    if (!this.F.j()) break;
                    this.J.t = true;
                    this.J.d(2);
                    break;
                }
                if (n2 != 2 || !this.F.j()) break;
                this.h(1);
            }
        }
        ++this.D;
        if (this.D > 3) {
            this.C = -this.C;
            this.D = 0;
        }
        this.F.i();
    }

    private void i(int n2) {
        if ((n2 = this.B[this.A][n2]) >= 0) {
            if (this.A != n2) {
                this.E[this.A].d(false);
                this.E[n2].d(true);
            }
            this.A = n2;
        }
    }

    public final void r() {
        kq.a().a(this.v[0] / y[0] * z[0], this.v[2] / y[1] * z[1], this.v[1] / y[2] * z[2], this.v[3] / y[3] * z[3]);
    }

    public final boolean f(int n2) {
        if (super.f(n2)) {
            return true;
        }
        switch (n2) {
            case 98: {
                this.i(0);
                break;
            }
            case 99: {
                this.i(1);
                break;
            }
            case 97: {
                this.i(3);
                break;
            }
            case 96: {
                this.i(2);
                break;
            }
            case 95: {
                if (!this.i) {
                    return true;
                }
                if (this.A >= this.E.length) break;
                return this.E[this.A].f(n2);
            }
            case 135: {
                this.F = this.J;
                this.J.d(0);
                this.m.a = 100;
                this.F.c(2);
                break;
            }
            case 142: {
                this.F = this.J;
                this.J.d(0);
                this.m.a = 100;
                this.F.c(0);
            }
        }
        return true;
    }

    public final void d(int n2, int n3) {
        if (n3 >= aC[0]) {
            n2 = n3 % aC[0];
            if (n2 < 4) {
                if (this.x >= y[n2]) {
                    this.x -= y[n2];
                    int n4 = n2;
                    this.v[n4] = this.v[n4] + y[n2];
                    int n5 = n2;
                    this.w[n5] = this.w[n5] + z[n2];
                    this.u();
                    return;
                }
            } else if (this.v[n2 -= 4] >= y[n2]) {
                int n6 = n2;
                this.v[n6] = this.v[n6] - y[n2];
                int n7 = n2;
                this.w[n7] = this.w[n7] - z[n2];
                this.x += y[n2];
                this.u();
            }
        }
    }

    public final void b(lf lf2) {
        this.a(lf2);
        this.c(lf2);
        this.u();
    }

    public final m v() {
        return new m(this.j.c, this.j.d);
    }

    public final int w() {
        return 10;
    }

    public final boolean c(int n2, int n3) {
        n2 -= this.j.a;
        n3 -= this.j.b;
        int n4 = 0;
        while (n4 < this.E.length) {
            if (this.E[n4].c(n2, n3)) {
                if (this.A != n4) {
                    this.E[this.A].d(false);
                    this.A = n4;
                }
                return true;
            }
            ++n4;
        }
        return false;
    }

    public final boolean a(bj bj2, String object, int n2, String string, int n3) {
        n2 = 0;
        while (n2 < this.v.length) {
            if (this.v[n2] > 0) {
                object = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111i\u1ec3m cho nh\u00e2n v\u1eadt. B\u1ea1n mu\u1ed1n c\u1eadp nh\u1eadt kh\u00f4ng?", (String)object, 5, string, 6, 1);
                ((aq)object).a(bj2);
                ((aq)object).b(241226);
                ak.b().a((ap)object, false);
                return true;
            }
            ++n2;
        }
        return false;
    }
}

