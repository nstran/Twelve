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

public final class da
extends aq
implements bf,
bu {
    private static final int[] k = new int[]{7930113, 12386818, 13894146, 0xFD0D0D, 0xFD0D0D, 12911106, 12911106, 10289666, 10289666, 9372162, 9372162, 9372162, 9372162};
    private static final int[] l = new int[]{227841, 375554, 381954, 719882, 719882, 377602, 377602, 231937, 236033, 236033, 231937, 231937, 231937};
    private static final int[] m = new int[]{14067456, 15579392, 15579392, 16765211, 16765211, 14593280, 14593280, 12095488, 12095488, 11109376, 11109376, 11109376, 11109376};
    private cu n;
    private cu o;
    private k p = null;
    private k q = null;
    private k r = null;
    private k s = null;
    private k[] t = new k[7];
    private k[] u = new k[8];
    private Image v;
    private Image w;
    private Image x;
    private int[] y;
    private int[] z;
    private int A;
    private static final int[] B = new int[]{1, 1, 1, 1};
    private static final int[] C = new int[]{1, 1, 1, 1};
    private int D = 0;
    private int[][] E;
    private int F = 1;
    private int G;
    private fu[] H = new fu[8];
    private mg I = null;
    private mg J = null;
    private mg K = null;
    private mg L = null;
    private mc M = null;
    private boolean N = false;
    private lh O;
    private boolean P = true;
    public boolean i = true;
    private k Q;
    private k R;
    private k S;
    private static final String[] T = new String[]{"C\u1ea5p", "D.V\u1ecdng"};
    private static final String[] U = new String[]{"C\u01b0\u1eddng L\u1ef1c", "N\u1ed9i L\u1ef1c", "Th\u00e2n Ph\u00e1p", "Th\u1ec3 L\u1ef1c"};
    private static String[] V = new String[]{"T\u1ea5n C\u00f4ng", "Ch\u00ednh x\u00e1c", "Sinh l\u1ef1c"};
    private static String[] W = new String[]{"P.Th\u1ee7", "N\u00e9 Tr\u00e1nh", "Ch\u00ed M\u1ea1ng"};
    private cu X;
    private cu Y;
    private cu Z;
    public k j;
    private boolean aa = false;
    private int ab;
    private int ac;
    private k ad;
    private k ae;
    private String af;
    private String ag;
    private jz ah;
    private int ai;
    private int aj;
    private int ak;
    private int al;
    private int am;
    private int an;
    private int ao;
    private String ap = "";
    private String aq = "";
    private String ar = "";
    private int as = 0;
    private int at = 0;
    private int au = 0;
    private int av = 0;
    private int aw = 0;
    private int ax = 0;
    private int ay = 0;
    private int az = 0;
    private int aA;
    private boolean aB;
    private int aC;
    private int aD;
    private int aE = 0;
    private int[][] aF;
    private static int[] aG = new int[]{1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007};
    private String aH;
    private byte[] aI;
    private final d aJ;

    public da() {
        byte[] byArray = new byte[4];
        byArray[1] = 2;
        byArray[2] = 1;
        this.aI = byArray;
        int n2 = 240;
        int n3 = 320 - ba.a;
        if (com.mg.sq.a.k == 1) {
            n2 = v.t;
        }
        this.j = new k(0, 0, n2, n3);
        this.ag = "Ch\u01b0a c\u00f3";
        this.af = "Ch\u01b0a c\u00f3";
        this.aJ = new if(new int[]{0xFFFFFF, 0xFF7F00});
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.N) {
            return;
        }
        Object object = this;
        object = ((ay)((aq)object).b).q();
        n2 = this.j.a + n2 - ((k)object).a;
        int n4 = n3 = this.j.b + n3 - ((k)object).b;
        int n5 = n2;
        Graphics graphics2 = graphics;
        object = this;
        pc.d(graphics2, n5, n4, ((da)object).j.c, ((da)object).j.d, v.aj);
        pc.c(graphics2, ((da)object).p.a + n5, ((da)object).p.b + n4, ((da)object).p.c, ((da)object).p.d);
        int n6 = 0;
        int n7 = 0;
        while (n7 < 2) {
            pc.b(graphics2, ((da)object).Q.a + n5, ((da)object).Q.b + n6 + n4, ((da)object).Q.c, ((da)object).Q.d, 1070484, 16579764, 14542575);
            bx.c.a(graphics2, T[n7], ((da)object).Q.a - 2 + n5, ((da)object).Q.b + n4 + n6, 2);
            n6 += ((da)object).Q.d + 2;
            ++n7;
        }
        n6 = 0;
        n7 = 0;
        while (n7 < 3) {
            pc.b(graphics2, ((da)object).ad.a + n5, ((da)object).ad.b + n6 + n4, ((da)object).ad.c, ((da)object).ad.d, 1070484, 16579764, 14542575);
            bx.c.a(graphics2, V[n7], ((da)object).ad.a - 5 + n5, ((da)object).ad.b + n4 + n6, 2);
            n6 += ((da)object).ad.d + 3;
            ++n7;
        }
        n6 = 0;
        n7 = 0;
        while (n7 < 3) {
            pc.b(graphics2, ((da)object).ae.a + n5, ((da)object).ae.b + n6 + n4, ((da)object).ae.c, ((da)object).ae.d, 1070484, 16579764, 14542575);
            bx.c.a(graphics2, W[n7], ((da)object).ae.a - 5 + n5, ((da)object).ae.b + n4 + n6, 2);
            n6 += ((da)object).ae.d + 3;
            ++n7;
        }
        n6 = 0;
        n7 = 0;
        while (n7 < 4) {
            pc.b(graphics2, ((da)object).R.a + n5, ((da)object).R.b + n6 + n4, ((da)object).R.c, ((da)object).R.d, 1070484, 16579764, 14542575);
            if (n7 == ((da)object).aI[((da)object).O.g / 2]) {
                com.mg.sq.a.h.a(graphics2, U[n7], ((da)object).R.a - 5 + n5, ((da)object).R.b + n4 + n6, 2);
            } else {
                bx.c.a(graphics2, U[n7], ((da)object).R.a - 5 + n5, ((da)object).R.b + n4 + n6, 2);
            }
            n6 += ((da)object).R.d + 3;
            ++n7;
        }
        pc.a(graphics2, ((da)object).S.a + n5, ((da)object).S.b + n4, ((da)object).S.c, ((da)object).S.d, 10323806, 14273459);
        bx.d.c(true);
        bx.d.a(graphics2, "\u0110i\u1ec3m", ((da)object).S.a - 5 + n5, ((da)object).S.b + n4, 2);
        bx.d.c(false);
        graphics2.drawImage(((da)object).x, ((da)object).q.a - 3 + n5, ((da)object).q.b + ((da)object).q.d + n4, 40);
        pc.a(graphics2, ((da)object).q.a + n5, ((da)object).q.b + n4, ((da)object).q.c, ((da)object).q.d, 1463700, 16311483);
        graphics2.drawImage(((da)object).w, ((da)object).r.a - 3 + n5, ((da)object).r.b + ((da)object).r.d + n4, 40);
        pc.a(graphics2, ((da)object).r.a + n5, ((da)object).r.b + n4, ((da)object).r.c, ((da)object).r.d, 1993479, 16311483);
        graphics2.drawImage(((da)object).v, ((da)object).s.a - 3 + n5, ((da)object).s.b + ((da)object).s.d + n4, 40);
        pc.a(graphics2, ((da)object).s.a + n5, ((da)object).s.b + n4, ((da)object).s.c, ((da)object).s.d, 7293991, 16311483);
        pc.a(graphics2, ((da)object).Y.a + n5, ((da)object).Y.b + n4, ((da)object).j.c - (((da)object).Y.a << 1));
        pc.a(graphics2, ((da)object).X.a + n5, ((da)object).X.b + n4, ((da)object).j.c - (((da)object).X.a << 1));
        if (this.O != null) {
            int n8 = (this.Q.d - bx.c.a()) / 2 + 1;
            int n9 = this.Q.a + 7 + n2;
            n5 = this.Q.a + this.Q.c - 5 + n2;
            com.mg.sq.a.h.a(graphics, this.O.Q, this.j.c - 15 + n2, this.o.b + n3, 2);
            bx.c.a(graphics, String.valueOf(this.O.G), n9, this.Q.b + n8 + n3, 0);
            bx.c.a(graphics, this.af, n5, this.Q.b + n8 + n3, 2);
            bx.c.a(graphics, String.valueOf(this.O.ab), n9, this.Q.b + (n8 += this.Q.d + 2) + n3, 0);
            bx.c.a(graphics, this.ag, n5, this.Q.b + n8 + n3, 2);
            n9 = this.R.a + this.R.c / 2 + n2;
            n8 = (this.Q.d - bx.d.a()) / 2;
            n5 = this.R.d + 3;
            com.mg.sq.a.g.a(graphics, String.valueOf(this.z[0] + this.as), n9, this.R.b + n8 + n3, 1);
            if (this.O.l > 0) {
                n4 = bx.c.a(String.valueOf(this.z[0] + this.as));
                com.mg.sq.a.h.a(graphics, " + " + this.O.l, n9 + n4 / 2, this.R.b + n8 + n3, 0);
            }
            com.mg.sq.a.g.a(graphics, String.valueOf(this.z[1] + this.at), n9, this.R.b + (n8 += n5) + n3, 1);
            if (this.O.n > 0) {
                n4 = bx.c.a(String.valueOf(this.z[1] + this.at));
                com.mg.sq.a.h.a(graphics, " + " + this.O.n, n9 + n4 / 2, this.R.b + n8 + n3, 0);
            }
            com.mg.sq.a.g.a(graphics, String.valueOf(this.z[2] + this.au), n9, this.R.b + (n8 += n5) + n3, 1);
            if (this.O.m > 0) {
                n4 = bx.c.a(String.valueOf(this.z[2] + this.au));
                com.mg.sq.a.h.a(graphics, " + " + this.O.m, n9 + n4 / 2, this.R.b + n8 + n3, 0);
            }
            com.mg.sq.a.g.a(graphics, String.valueOf(this.z[3] + this.av), n9, this.R.b + (n8 += n5) + n3, 1);
            if (this.O.o > 0) {
                n4 = bx.c.a(String.valueOf(this.z[3] + this.av));
                com.mg.sq.a.h.a(graphics, " + " + this.O.o, n9 + n4 / 2, this.R.b + n8 + n3, 0);
            }
            n9 = this.ad.a + this.ad.c / 2 + n2;
            n8 = (this.ad.d - bx.d.a()) / 2 + 1;
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ak), n9, this.ad.b + n8 + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.an), n9, this.ad.b + (n8 += this.ad.d + 3) + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ai), n9, this.ad.b + (n8 += this.ad.d + 3) + n3, 1);
            n9 = this.ae.a + this.ae.c / 2 + n2;
            n8 = (this.ae.d - bx.d.a()) / 2 + 1;
            com.mg.sq.a.g.a(graphics, String.valueOf(this.al), n9, this.ae.b + n8 + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.am), n9, this.ae.b + (n8 += this.ae.d + 3) + n3, 1);
            com.mg.sq.a.g.a(graphics, String.valueOf(this.ao) + "%", n9, this.ae.b + (n8 += this.ae.d + 3) + n3, 1);
            bx.d.a(graphics, String.valueOf(this.A), this.S.a + this.S.c / 2 + n2, this.S.b + (this.S.d - bx.d.a()) / 2 + n3, 1);
            pc.a(graphics, this.q, this.aj * (this.q.c - 2) / this.ai, k, n2, n3);
            bx.d.a(graphics, this.ar, this.q.a + (this.q.c >> 1) + n2, this.q.b + n3, 1);
            pc.a(graphics, this.r, this.ab, l, n2, n3);
            bx.d.a(graphics, this.ap, this.r.a + (this.q.c >> 1) + n2, this.r.b + n3, 1);
            n4 = this.O.H * this.s.c / this.O.I;
            if (n4 > this.s.c) {
                n4 %= this.s.c;
            }
            pc.a(graphics, this.s, n4, m, n2, n3);
            bx.d.a(graphics, this.aq, this.s.a + (this.s.c >> 1) + n2, this.s.b + n3, 1);
            n8 = n2 + this.p.a;
            n9 = n3 + this.p.b;
            if (this.I != null) {
                this.I.a(graphics, n8, n9 + 4);
            }
            n8 = n2 + this.n.a;
            n9 = n3 + this.n.b;
            pc.b(graphics, n8, n9, this.O.g);
            n8 = n2 + 10;
            n9 = n3 + 95 - bx.d.b();
            String string = com.mg.sq.a.b(go.s);
            this.aJ.a(graphics, string, n8, n9, 0);
            n8 = n2 + this.o.a;
            n9 = n3 + this.o.b;
            bx.d.c(true);
            bx.d.a(graphics, this.aH, n8, n9, 0);
            bx.d.c(false);
            if (this.i && this.P) {
                n8 = 0;
                while (n8 < this.H.length) {
                    this.H[n8].a(graphics, n2, n3);
                    ++n8;
                }
                pc.e(graphics, this.t[this.D].a - 4 + n2, this.t[this.D].b - 4 + n3, this.t[this.D].c + 8, this.t[this.D].d + 8, this.F);
            }
        }
    }

    public final boolean a() {
        int n2 = 0;
        while (n2 < this.y.length) {
            if (this.y[n2] > 0) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private static boolean s() {
        return com.mg.sq.a.k == 1;
    }

    public final void q() {
        if (!this.N) {
            this.w = f.d("/info/expicon");
            this.x = f.d("/info/heart");
            this.v = f.d("/info/gold");
            this.Q = da.s() ? new k(170, 20, 124, 16) : new k(110, 25, 118, 16);
            this.Y = new cu(10, this.Q.b + (this.Q.d + 2 << 2) + 6);
            int n2 = da.s() ? this.j.c - 52 : 204;
            this.q = new k(26, this.Y.b + 6 + 2, n2, 14);
            this.r = new k(26, this.q.b + this.q.d + 4, n2, 14);
            this.s = new k(26, this.r.b + this.r.d + 4, n2, 14);
            this.R = new k(81, this.s.b + this.s.d + 4, 93, 15);
            if (da.s()) {
                this.R.c = 126;
            }
            this.S = new k(this.s.a + this.s.c - 65, this.R.b + (this.R.d + 3 << 2) + 2, 65, 14);
            this.X = new cu(10, this.S.b + this.S.d + 2 + 6);
            n2 = da.s() ? 60 : 45;
            this.ad = new k(70, this.X.b + 6 + 2, n2, 15);
            this.ae = new k(this.s.a + this.s.c - n2, this.ad.b, n2, 15);
            this.Z = new cu(this.Y.a, this.ae.b + (this.ae.d + 3) * 3 + 2);
            this.n = new cu(8, 4);
            this.o = new cu(25, 7);
            this.p = new k(10, 22, 50, 59);
            n2 = 0;
            int n3 = this.R.b + (this.R.d - 12) / 2;
            int n4 = this.R.a + this.R.c + 4;
            int n5 = n4 + 12 + 3;
            int n6 = 0;
            while (n6 < 4) {
                this.u[n6] = new k(n4, n3 + n2, 12, 12);
                this.u[n6 + 4] = new k(n5, n3 + n2, 12, 12);
                n2 += this.R.d - 12 + 3 + 12;
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
            this.aF = nArrayArray;
            n3 = 0;
            while (n3 < this.H.length) {
                this.H[n3] = new fu(image, aG[n3]);
                this.H[n3].a(this);
                n2 = n3 > 4 ? 2 : 0;
                this.H[n3].b(this.aF[n2][0], this.aF[n2][1], this.aF[n2][2], this.aF[n2][3]);
                this.H[n3].b(false);
                this.H[n3].a(this.u[n3].a, this.u[n3].b, this.aF[n2][2], this.aF[n2][3]);
                ++n3;
            }
            this.t = new k[]{this.u[0], this.u[1], this.u[2], this.u[3], this.u[4], this.u[5], this.u[6], this.u[7]};
            this.j.d = this.Z.b + 6 + 4;
            da da2 = this;
            this.E = new int[da2.t.length][4];
            da2.E[0] = new int[]{1, -1, 4, -1};
            int[] nArray5 = new int[4];
            nArray5[0] = 2;
            nArray5[2] = 5;
            nArray5[3] = -1;
            da2.E[1] = nArray5;
            da2.E[2] = new int[]{3, 1, 6, -1};
            da2.E[3] = new int[]{-1, 2, 7, -1};
            int[] nArray6 = new int[4];
            nArray6[0] = 5;
            nArray6[1] = -1;
            nArray6[2] = -1;
            da2.E[4] = nArray6;
            da2.E[5] = new int[]{6, 4, -1, 1};
            da2.E[6] = new int[]{7, 5, -1, 2};
            da2.E[7] = new int[]{-1, 6, -1, 3};
            this.ah = jp.a(this.O.g);
            this.y = new int[4];
            this.z = new int[4];
            this.c(this.O);
            this.N = true;
            this.a(this.j.a, this.j.b, this.j.c, this.j.d);
            this.aH = com.mg.sq.a.a(this.O.b, this.e() - 85);
        }
        this.u();
    }

    public final void a(lh lh2) {
        Object object;
        this.O = lh2;
        this.au = 0;
        this.at = 0;
        this.as = 0;
        this.av = 0;
        this.aw = 0;
        this.ay = 0;
        this.az = 0;
        this.ax = 0;
        this.aA = 0;
        if (this.ah == null) {
            this.ah = jp.a(this.O.g);
            this.ah.a(this.O.h + this.as + this.O.l, this.O.j + this.au + this.O.m, this.O.i + this.at + this.O.n, this.O.k + this.av + this.O.o);
        }
        int n2 = 0;
        while (n2 < this.O.D.length) {
            int n3 = 0;
            while (n3 < go.l.length) {
                if (go.l[n3].c.equals(this.O.D[n2].c) && go.l[n3].p != 0) {
                    object = go.l[n3].r;
                    this.as += ((lb)object).a;
                    this.av += ((lb)object).d;
                    this.au += ((lb)object).b;
                    this.at += ((lb)object).c;
                    this.aw += ((lb)object).e;
                    this.aw += this.ah.c() * ((lb)object).n / 100;
                    this.ay += ((lb)object).g;
                    this.az += ((lb)object).f;
                    this.ax += ((lb)object).h;
                    this.aA += ((lb)object).i;
                }
                ++n3;
            }
            ++n2;
        }
        this.ah.a(this.O.h + this.as + this.O.l, this.O.j + this.au + this.O.m, this.O.i + this.at + this.O.n, this.O.k + this.av + this.O.o);
        mb[] mbArray = mb.a(lh2);
        nr nr2 = nr.a(lh2);
        object = lc.a(lh2);
        this.J = mb.a(lh2, false);
        this.J.a((lc)object);
        this.J.a(nr2);
        this.L = mb.c(lh2, mbArray[0], mbArray[1], mbArray[3], mbArray[2], false);
        this.L.a((lc)object);
        this.L.a(nr2);
        this.K = mb.h(lh2, mbArray[0], mbArray[1], mbArray[3], mbArray[2], false);
        this.K.a((lc)object);
        this.K.a(nr2);
        object = f.d("/castingball");
        this.M = mb.a(lh2, (Image)object, mbArray[0], mbArray[1], mbArray[3], mbArray[2], false);
        this.M.a(nr2);
        if (!this.aB) {
            this.h(0);
        } else {
            this.h(1);
        }
        this.aB = true;
        this.x();
        this.af = "[" + this.O.S + "]";
        this.ag = "[" + this.O.R + "]";
    }

    private void h(int n2) {
        this.aC = n2;
        switch (n2) {
            case 1: {
                this.I = this.J;
                break;
            }
            case 0: {
                this.I = this.K;
                this.I.g(-70);
                this.aD = 1;
                break;
            }
            case 2: {
                this.I = this.L;
                break;
            }
            case 3: {
                this.I = this.M;
            }
        }
        this.I.c(2);
        this.I.j(20);
        this.I.i();
    }

    private void c(lh object) {
        this.y = new int[4];
        this.z = new int[4];
        int n2 = ((lh)object).J - ((lh)object).M;
        int n3 = ((lh)object).N + 1 - ((lh)object).M;
        this.ab = n2 * this.r.c / n3;
        if (this.ab > this.r.c) {
            this.ab = this.r.c - 2;
        }
        this.ac = n2 * 1000 / n3;
        this.ap = String.valueOf(this.ac / 10) + "," + this.ac % 10 + "%";
        this.aq = String.valueOf(i.a(((lh)object).H, ".")) + "/" + i.a(((lh)object).I, ".");
        this.t();
        object = this;
        ((da)object).z[0] = ((da)object).O.h;
        ((da)object).z[2] = ((da)object).O.j;
        ((da)object).z[1] = ((da)object).O.i;
        ((da)object).z[3] = ((da)object).O.k;
        ((da)object).A = ((da)object).O.K;
        if (((da)object).O.K <= 0) {
            ((da)object).P = false;
        }
    }

    private void t() {
        int n2 = 0;
        int n3 = this.H.length / 2;
        while (n2 < n3) {
            if (this.A >= B[n2]) {
                this.H[n2].b(true);
                this.H[n2].b(this.aF[1][0], this.aF[1][1], this.aF[1][2], this.aF[1][3]);
            } else {
                this.H[n2].b(false);
                this.H[n2].b(this.aF[0][0], this.aF[0][1], this.aF[0][2], this.aF[0][3]);
            }
            if (this.y[n2] > 0) {
                this.H[n2 + 4].b(true);
                this.H[n2 + 4].b(this.aF[3][0], this.aF[3][1], this.aF[3][2], this.aF[3][3]);
            } else {
                this.H[n2 + 4].b(false);
                this.H[n2 + 4].b(this.aF[2][0], this.aF[2][1], this.aF[2][2], this.aF[2][3]);
            }
            ++n2;
        }
    }

    private void u() {
        this.t();
        this.ah.a(this.z[0] + this.as + this.O.l, this.z[2] + this.au + this.O.m, this.z[1] + this.at + this.O.n, this.z[3] + this.av + this.O.o);
        this.x();
    }

    private void x() {
        this.ai = this.ah.a() + this.aA + this.O.p;
        this.aj = this.O.s + (this.ai - this.O.r) * this.O.q / 100 / 100;
        this.am = this.ah.e() + this.ax;
        this.ao = this.ah.g() + this.ay;
        this.al = this.ah.d() + this.az;
        this.an = this.ah.f();
        this.ak = this.ah.b() + this.aw;
        this.ah.c();
        this.ar = String.valueOf(i.a(this.aj, ".")) + "/" + i.a(this.ai, ".");
    }

    public final void n() {
        if (!this.N) {
            return;
        }
        switch (this.aC) {
            case 0: {
                if (this.I.f(0, this.aD) && this.I.j()) {
                    this.h(2);
                }
                this.aD += 3;
                break;
            }
            case 2: {
                if (!this.I.j()) break;
                ++this.aE;
                if (this.aE < 3) break;
                this.h(3);
                this.aE = 0;
                break;
            }
            case 3: {
                int n2 = this.I.h();
                if (n2 == 1 && this.I.j()) {
                    this.M.t = true;
                    this.M.d(2);
                    break;
                }
                if (n2 != 2 || !this.I.j()) break;
                this.h(1);
            }
        }
        ++this.G;
        if (this.G > 3) {
            this.F = -this.F;
            this.G = 0;
        }
        this.I.i();
    }

    private void i(int n2) {
        if ((n2 = this.E[this.D][n2]) >= 0) {
            if (this.D != n2) {
                this.H[this.D].d(false);
                this.H[n2].d(true);
            }
            this.D = n2;
        }
    }

    public final void r() {
        ks.a().a(this.y[0] / B[0] * C[0], this.y[2] / B[1] * C[1], this.y[1] / B[2] * C[2], this.y[3] / B[3] * C[3]);
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
                if (this.D >= this.H.length) break;
                return this.H[this.D].f(n2);
            }
            case 135: {
                this.I = this.M;
                this.M.d(0);
                this.p.a = 100;
                this.I.c(2);
                break;
            }
            case 142: {
                this.I = this.M;
                this.M.d(0);
                this.p.a = 100;
                this.I.c(0);
            }
        }
        return true;
    }

    public final void d(int n2, int n3) {
        if (n3 >= aG[0]) {
            n2 = n3 % aG[0];
            if (n2 < 4) {
                if (this.A >= B[n2]) {
                    this.A -= B[n2];
                    int n4 = n2;
                    this.y[n4] = this.y[n4] + B[n2];
                    int n5 = n2;
                    this.z[n5] = this.z[n5] + C[n2];
                    this.u();
                    return;
                }
            } else if (this.y[n2 -= 4] >= B[n2]) {
                int n6 = n2;
                this.y[n6] = this.y[n6] - B[n2];
                int n7 = n2;
                this.z[n7] = this.z[n7] - C[n2];
                this.A += B[n2];
                this.u();
            }
        }
    }

    public final void b(lh lh2) {
        this.a(lh2);
        this.c(lh2);
        this.u();
    }

    public final g v() {
        return new g(this.j.c, this.j.d);
    }

    public final int w() {
        return 10;
    }

    public final boolean c(int n2, int n3) {
        n2 -= this.j.a;
        n3 -= this.j.b;
        int n4 = 0;
        while (n4 < this.H.length) {
            if (this.H[n4].c(n2, n3)) {
                if (this.D != n4) {
                    this.H[this.D].d(false);
                    this.D = n4;
                }
                return true;
            }
            ++n4;
        }
        return false;
    }

    public final boolean a(bf bf2, String object, int n2, String string, int n3) {
        n2 = 0;
        while (n2 < this.y.length) {
            if (this.y[n2] > 0) {
                object = ag.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111i\u1ec3m cho nh\u00e2n v\u1eadt. B\u1ea1n mu\u1ed1n c\u1eadp nh\u1eadt kh\u00f4ng?", (String)object, 5, string, 6, 1);
                ((am)object).a(bf2);
                ((am)object).b(241226);
                ag.b().a((al)object, false);
                return true;
            }
            ++n2;
        }
        return false;
    }
}

