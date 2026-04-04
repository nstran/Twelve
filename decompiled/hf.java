/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class hf
extends hr
implements bt {
    private Image o = f.d("/info/hidenobj");
    private int p = 32;
    private cx q = null;
    private cx r = null;
    private n s = null;
    private n t = null;
    private n[] u;
    private fg v = null;
    private int w = 0;
    private byte[][] x;
    private me y = null;
    private boolean z = false;
    private bd A;
    private bd B;
    private bd C;
    private bc D;
    public dg k = null;
    private dg[] E = new dg[6];
    private boolean F = false;
    private boolean G = false;
    private boolean H = false;
    private int I = 0;
    private int J = 0;
    private int K = 0;
    private lf L;
    private ou M;
    private boolean N = false;
    private df O = null;
    private boolean P;
    private gi Q;
    private fv R;
    private int S = 0;
    private String T;
    private boolean U = false;
    private dg V;
    private boolean W;
    private int X;
    private d Y;
    private boolean Z = false;

    public hf(ou object, d d2) {
        n n2;
        n n3;
        n n4;
        n n5;
        n n6;
        Object object2;
        this.b(241202);
        this.a(new be());
        this.a(this);
        if (d2 == null) {
            d2 = new ie(new int[]{0xFF0000, 0xFFFF00});
        }
        this.Y = d2;
        int n7 = 240;
        int n8 = 320 - be.a;
        if (hf.A()) {
            n7 = 320;
            n8 = z.s - be.a;
        }
        int n9 = z.r >= n7 ? (z.r - n7) / 2 : 0;
        int n10 = z.s >= n8 ? (z.s - be.a - n8) / 2 : 0;
        this.a(n9, n10, n7, n8);
        this.M = new ou((ou)object);
        object = this;
        this.q = new cx(6, 4);
        ((hf)object).r = new cx(22, 6);
        if (hf.A()) {
            ((hf)object).s = new n(13, 24, 54, 60);
            object2 = new n(6, 90, 32, 32);
            n6 = new n(6, 126, 32, 32);
            n5 = new n(42, 90, 32, 32);
            n4 = new n(42, 126, 32, 32);
            n3 = new n(6, 162, 32, 32);
            n2 = new n(42, 162, 32, 32);
            ((hf)object).t = new n(79, 23, 226, ((aq)object).g - 33);
        } else {
            ((hf)object).s = new n(95, 21, 54, 60);
            object2 = new n(60, 18, 32, 32);
            n6 = new n(60, 53, 32, 32);
            n5 = new n(153, 18, 32, 32);
            n4 = new n(153, 53, 32, 32);
            n3 = new n(189, 18, 32, 32);
            n2 = new n(189, 53, 32, 32);
            ((hf)object).t = new n(9, 88, 220, ((aq)object).g - 96);
        }
        ((hf)object).v = new fg(hf.A());
        ((hf)object).v.i = new n(((hf)object).t.a, ((hf)object).t.b, ((hf)object).t.c, ((hf)object).t.d);
        ((hf)object).L = gr.j.a();
        Object object3 = ((hf)object).L;
        int n11 = gr.k.length - object3.D.length;
        int n12 = 0;
        while (n12 < gr.l.length) {
            n11 = gr.l[n12].l == 1 ? (n11 += gr.l[n12].g) : (gr.l[n12].l > 1 ? (n11 += gr.l[n12].g / gr.l[n12].l + (gr.l[n12].g % gr.l[n12].l > 0 ? 1 : 0)) : ++n11);
            ++n12;
        }
        ((hf)object).X = n11;
        if (((hf)object).X < gr.m) {
            ((hf)object).X = gr.m;
        }
        ((hf)object).v.d(((hf)object).X, 2);
        ((hf)object).v.a((bt)object);
        ((hf)object).h(0);
        lj[] ljArray = gr.k;
        object3 = object;
        n12 = object3.L.D.length;
        object3.v.t();
        int n13 = 0;
        while (n13 < ljArray.length) {
            if (ljArray[n13] != null) {
                boolean bl2 = false;
                if (n12 > 0) {
                    int n14 = 0;
                    while (n14 < object3.L.D.length) {
                        if (ljArray[n13].b.equals(object3.L.D[n14].b)) {
                            if (ljArray[n13].d != 8) {
                                lk[] lkArray = object3;
                                object3.E[ljArray[n13].d] = new dg(object3.M.a(lz.a(ljArray[n13]) + 98, true), ljArray[n13], 0, object3.Y);
                            }
                            --n12;
                            bl2 = true;
                            break;
                        }
                        ++n14;
                    }
                }
                if (!bl2) {
                    lk[] lkArray = object3;
                    dg dg2 = new dg(object3.M.a(lz.a(ljArray[n13]) + 98, true), ljArray[n13], 0, object3.Y);
                    object3.v.a((Object)dg2);
                }
            }
            ++n13;
        }
        object3 = new lk[gr.l.length];
        int n15 = 0;
        while (n15 < ((lk[])object3).length) {
            object3[n15] = gr.l[n15].b();
            ++n15;
        }
        super.a((lk[])object3);
        ((hf)object).D = new bc();
        ((hf)object).D.a(new n(((hf)object).t.a, ((hf)object).t.b, ((hf)object).t.c, ((hf)object).t.d));
        ((hf)object).D.b(((hf)object).v);
        ((hf)object).D.h(1);
        ((hf)object).u = new n[]{object2, n6, n5, n4, n3, n2, ((hf)object).t};
        object3 = object;
        ((hf)object).x = new byte[6][4];
        if (hf.A()) {
            byte[] byArray = new byte[4];
            byArray[0] = 2;
            byArray[2] = 1;
            object3.x[0] = byArray;
            byte[] byArray2 = new byte[4];
            byArray2[0] = 3;
            byArray2[1] = 1;
            byArray2[2] = 4;
            object3.x[1] = byArray2;
            byte[] byArray3 = new byte[4];
            byArray3[0] = -3;
            byArray3[2] = 3;
            byArray3[3] = 2;
            object3.x[2] = byArray3;
            object3.x[3] = new byte[]{-3, 1, 5, 2};
            object3.x[4] = new byte[]{5, 4, 4, 1};
            object3.x[5] = new byte[]{-3, 4, 5, 3};
        } else {
            object3.x[0] = new byte[]{2, -2, 1, -2};
            byte[] byArray = new byte[4];
            byArray[0] = 3;
            byArray[1] = -1;
            byArray[2] = -3;
            object3.x[1] = byArray;
            byte[] byArray4 = new byte[4];
            byArray4[0] = 4;
            byArray4[2] = 3;
            byArray4[3] = -1;
            object3.x[2] = byArray4;
            object3.x[3] = new byte[]{5, 1, -3, 2};
            object3.x[4] = new byte[]{4, 2, 5, 4};
            object3.x[5] = new byte[]{5, 3, -3, 4};
        }
        super.a(((hf)object).L, null);
        ((hf)object).A = new ga(0, 0);
        ((hf)object).C = new ga(3, 2);
        ((hf)object).B = new ga(4, 3);
        ((aq)object).a(com.mg.sq.a.l);
        object2 = ((hf)object).A;
        Object object4 = object;
        ((aq)object4).a((bd)object2, true);
        object2 = ((hf)object).B;
        object4 = object;
        ((aq)object4).b((bd)object2, true);
        ((hf)object).i(((hf)object).w);
        ((hf)object).T = com.mg.sq.a.a(((hf)object).L.b, ((aq)object).i() - 85);
        this.a((bd)null);
        this.h(0);
        if (!cv.a.c(143)) {
            ob.h(143);
            gp.o = false;
        }
    }

    private static boolean A() {
        return com.mg.sq.a.i == 1;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
    }

    public final void h(int n2) {
        this.K = n2;
        switch (this.K) {
            case 0: {
                this.V = null;
                this.v.a((df)null);
                bd bd2 = this.A;
                hf hf2 = this;
                hf2.a(bd2, true);
                this.a((bd)null);
                bd2 = this.B;
                hf2 = this;
                hf2.b(bd2, true);
                this.N = false;
                return;
            }
            case 1: {
                this.O = new df();
                if (this.v != null) {
                    this.v.a(this.O);
                }
                bd bd3 = null;
                hf hf3 = this;
                hf3.a(bd3, true);
                return;
            }
            case 2: {
                ga ga2 = null;
                hf hf4 = this;
                hf4.a(ga2, true);
                this.a((bd)null);
                ga2 = new ga(1235, 3);
                hf4 = this;
                hf4.b(ga2, true);
            }
        }
    }

    private void a(lk[] lkArray) {
        int n2 = 0;
        while (n2 < lkArray.length) {
            int n3;
            int n4 = n3 = lkArray[n2].e == 3 ? 5 : 1;
            if (lkArray[n2].g > 0) {
                Object object;
                if (lkArray[n2].l > 0) {
                    int n5 = lkArray[n2].g;
                    int n6 = lkArray[n2].g / lkArray[n2].l;
                    int n7 = 0;
                    while (n7 < n6) {
                        object = lkArray[n2].b();
                        lkArray[n2].b().g = lkArray[n2].l;
                        n5 -= lkArray[n2].l;
                        Object object2 = object;
                        object = this;
                        object = new dg(null, object2, n3, ((hf)object).Y);
                        this.v.a(object);
                        ++n7;
                    }
                    if (n5 > 0) {
                        lk lk2 = lkArray[n2].b();
                        lkArray[n2].b().g = n5;
                        object = this;
                        object = new dg(null, lk2, n3, ((hf)object).Y);
                        this.v.a(object);
                    }
                } else {
                    object = this;
                    dg dg2 = new dg(null, lkArray[n2], n3, ((hf)object).Y);
                    this.v.a((Object)dg2);
                }
            }
            ++n2;
        }
    }

    public final void a(lk lk2) {
        int n2 = 0;
        lk lk3 = lk2.b();
        a a2 = new a();
        int n3 = 0;
        while (n3 < this.v.s()) {
            lk lk4;
            this.k = (dg)this.v.k(n3);
            if (this.k != null && this.k.k != null && (this.k.j == 1 || this.k.j == 5) && (lk4 = (lk)this.k.k) != null && lk4.a == lk2.a) {
                n2 += lk4.g;
                a2.a(this.k);
            }
            ++n3;
        }
        lk3.g = n2 - lk2.g;
        n3 = 0;
        while (n3 < a2.d()) {
            this.v.a((dg)a2.b(n3));
            ++n3;
        }
        if (lk3.g > 0) {
            this.a(new lk[]{lk3});
        }
        this.t();
    }

    private void k(int n2, int n3) {
        lk lk2 = null;
        a a2 = new a();
        int n4 = 0;
        while (n4 < this.v.s()) {
            lk lk3;
            this.k = (dg)this.v.k(n4);
            if (this.k != null && this.k.k != null && this.k.j != 0 && this.k.j != 4 && (lk3 = (lk)this.k.k) != null && lk3.a == n2) {
                a2.a(this.k);
                lk2 = lk3;
                break;
            }
            ++n4;
        }
        if (lk2 == null) {
            return;
        }
        lk2.g -= n3;
        n4 = 0;
        while (n4 < a2.d()) {
            this.v.a((dg)a2.b(n4));
            ++n4;
        }
        if (lk2.g > 0) {
            this.a(new lk[]{lk2});
        }
        this.t();
    }

    public final void h(int n2, int n3) {
        com.mg.sq.a.t();
        int n4 = 0;
        while (n4 < gr.l.length) {
            if (gr.l[n4].a == n2) {
                gr.a(n2, gr.l[n4].g - n3);
                break;
            }
            ++n4;
        }
        if (this.k != null && this.k.j != 0) {
            this.v.a(this.k);
        }
        this.t();
    }

    public final void a(dg dg2) {
        this.v.a(dg2);
        this.t();
    }

    public final void b(dg dg2) {
        this.v.a((Object)dg2);
        this.t();
    }

    private void a(lf lf2, dg[] dgArray) {
        int n2 = 0;
        if (dgArray != null) {
            int n3 = 0;
            while (n3 < dgArray.length) {
                if (dgArray[n3] != null) {
                    ++n2;
                }
                ++n3;
            }
            lj[] ljArray = new lj[n2];
            n3 = 0;
            int n4 = 0;
            while (n4 < dgArray.length) {
                if (dgArray[n4] != null && dgArray[n4].j == 0) {
                    ljArray[n3] = (lj)dgArray[n4].k;
                    ++n3;
                }
                ++n4;
            }
            lf2.D = ljArray;
        }
        this.y = null;
        System.gc();
        cw.b("[upgradeAnimation]");
        this.y = lz.a(lf2, false);
        this.y.a(la.a(lf2));
        this.y.a(np.a(lf2));
        this.y.c(2);
        this.y.i();
    }

    public final void u() {
        if (!this.v.m()) {
            ++this.J;
            if (this.J >= 5) {
                this.I = this.I == 0 ? -2 : 0;
                this.J = 0;
            }
        }
        if (this.y != null) {
            this.y.i();
        }
        if (this.D != null) {
            this.D.n();
        }
        int n2 = 0;
        while (n2 < this.E.length) {
            if (this.E[n2] != null) {
                this.E[n2].n();
            }
            ++n2;
        }
        if (this.O != null) {
            this.O.i();
        }
        if (this.Q != null) {
            this.Q.i();
            if (!this.Q.m()) {
                this.Q = null;
            }
        }
        if (this.R != null) {
            this.R.i();
        }
        if (this.S < 7) {
            ++this.S;
            if (this.S == 7 && this.k != null && this.k.k != null) {
                Object object = this.k.k;
                hf hf2 = this;
                this.R = new fv(object);
                int n3 = hf2.g;
                hf2.R.a(9, n3, hf2.f - 20, hf2.g / 4);
                hf2.R.c(9, n3);
                hf2.R.a(9, n3 - hf2.R.q() - 7);
            }
        }
    }

    public final void e(int n2) {
        switch (n2) {
            case 0: {
                hf hf2 = this;
                bu[] buArray = null;
                if (hf2.K == 0) {
                    buArray = hf2.F ? new bu[]{new bu("C\u1eadp nh\u1eadt", 111111), new bu("Nh\u00e2n V\u1eadt", 111112), new bu("C\u1eeda h\u00e0ng", 111113), new bu("\u0110\u00f3ng", 111114)} : new bu[]{new bu("Nh\u00e2n V\u1eadt", 111112), new bu("C\u1eeda h\u00e0ng", 111113), new bu("\u0110\u00f3ng", 111114)};
                }
                hf2.a(buArray, hf2.C, (bd)new bh("", hf2.C.a()), hf2.B);
                return;
            }
            case 4: {
                switch (this.K) {
                    case 1: {
                        this.h(0);
                        return;
                    }
                }
                if (this.l == null) {
                    this.C();
                    return;
                }
                this.z();
                return;
            }
            case 3: {
                this.l.f(95);
                return;
            }
            case 6: {
                ak.b().e(-4561238);
                this.z();
                ak.b().a(this.h(), false);
                if (!this.U) break;
                com.mg.sq.a.E();
                return;
            }
            case 5: {
                ak.b().e(-4561238);
                this.B();
                this.z();
                com.mg.sq.a.a(null, null);
                return;
            }
            case 9: {
                if (this.k.j == 0) {
                    kq.a().a(new String[]{((lj)this.k.k).b});
                } else if (((lk)this.k.k).l == 1) {
                    lk lk2 = (lk)this.k.k;
                    kq.a().f(lk2.a, 1);
                } else {
                    lk lk3 = (lk)this.k.k;
                    kq.a().f(lk3.a, lk3.g);
                }
                ak.b().e(-4561239);
                com.mg.sq.a.a(null, null);
                return;
            }
            case 10: {
                ak.b().e(-4561239);
                return;
            }
            case 14: {
                ak.b().e(241212);
                return;
            }
            case 20: {
                ak.b().e(-241439);
                if (!this.P) break;
                this.P = false;
                n2 = 0;
                if (this.O != null) {
                    n2 = this.O.r();
                }
                this.h(this.K);
                this.O.l(n2);
                return;
            }
            case 21: {
                Object object = (hj)ak.b().e();
                object = ((hj)object).v();
                this.h(1);
                this.d(this.v.a((lk)object));
                this.a((lj)this.k.k);
                ak.b().e(-241439);
                return;
            }
            case 23: {
                this.h(0);
                return;
            }
            case 22: {
                this.N = true;
                kq.a().a(((lk)this.V.k).a, ((lj)this.k.k).b);
                this.O.d(1);
                return;
            }
            case 27: {
                if (this.k == null) break;
                this.P = true;
                this.c((lj)this.k.k);
                return;
            }
            case 26: {
                ak.b().a(false);
                com.mg.sq.a.F();
                return;
            }
            case 112: {
                ak.b().a(false);
                return;
            }
            case 114: {
                ak.b().a(false);
                return;
            }
            case 111: {
                ak.b().a(false);
                lk lk4 = (lk)this.k.k;
                this.k(lk4.a, 1);
                kq.a().e(lk4.a);
                com.mg.sq.a.a("\u0110\u1ee3i x\u00edu nh\u00e9...", null, 500);
                return;
            }
            case 113: {
                ak.b().a(false);
                com.mg.sq.a.M();
                return;
            }
            case 118: {
                com.mg.sq.a.q().e(123456);
                kq.a().w();
                return;
            }
            case 119: {
                com.mg.sq.a.q().e(123456);
            }
        }
    }

    public final void a(String[] stringArray) {
        int n2 = 0;
        while (n2 < stringArray.length) {
            dg dg2 = this.v.a(stringArray[n2]);
            if (dg2 != null) {
                if (this.v.m()) {
                    this.v.a(dg2);
                    this.v.i(this.v.q());
                }
                gr.b((lj)dg2.k);
            }
            ++n2;
        }
        com.mg.sq.a.t();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void f(int n2) {
        switch (this.K) {
            case 1: {
                if (!this.N) break;
                return;
            }
        }
        if (!this.v.m()) {
            switch (n2) {
                case 96: 
                case 97: 
                case 98: 
                case 99: {
                    int n3 = n2 - 96;
                    hf hf2 = this;
                    if (n3 < 0) return;
                    if ((n3 = hf2.x[hf2.w][n3]) == -3) {
                        hf2.H = false;
                        hf2.v.d(true);
                        hf2.v.i(0);
                        return;
                    }
                    if (n3 < 0) return;
                    hf2.i(n3);
                    return;
                }
                case 95: {
                    if (this.k == null) return;
                    this.a(this.u[this.w]);
                }
                default: {
                    return;
                }
            }
        }
        this.v.f(n2);
        if (this.v.m()) return;
        this.i(this.w);
    }

    public final void f(int n2, int n3) {
        if (this.R != null) {
            this.R.g(n2 - this.c, n3 - this.d);
        }
        switch (this.K) {
            case 1: {
                if (!this.N) break;
                return;
            }
        }
        int n4 = 0;
        while (n4 < this.u.length - 1) {
            if (new n(this.u[n4].a + this.c, this.u[n4].b + this.d, this.u[n4].c, this.u[n4].d).a(n2, n3)) {
                this.v.d(false);
                String string = "";
                if (this.k != null) {
                    string = ((lj)this.k.k).b;
                }
                this.i(n4);
                if (this.k != null && ((lj)this.k.k).b.equals(string)) {
                    this.a(this.u[this.w]);
                }
                return;
            }
            ++n4;
        }
        if (new n(this.v.i.a + this.c, this.v.i.b + this.d, this.v.i.c, this.v.i.d).a(n2, n3)) {
            if (!this.v.m()) {
                this.v.d(true);
            }
            this.H = false;
            this.D.c(n2 - this.c, n3 - this.d);
            return;
        }
    }

    public final void i(int n2) {
        this.w = n2;
        this.a((Object)this.E[n2]);
        if (this.k != null) {
            this.H = true;
            this.G = true;
            if (gp.p && this.k.k != null && ((lj)this.k.k).n == 0 && ((lj)this.k.k).c()) {
                gp.p = false;
                ob.h(144);
                int n3 = this.u[n2].b - 10 + this.d;
                this.Q = new gi("B\u1ea5m phim gi\u1eefa. R\u1ed1i ch\u1ecdn s\u1eeda ch\u1eefa", this.c + this.f / 2, n3, this.f - 20, 80, false);
                this.Q.a(this.u[this.w].a - 10 + this.u[n2].c / 2);
                if (!hf.A()) {
                    this.Q.g(this.u[n2].b + 35 + this.d);
                    this.Q.a(true);
                }
                this.Q.a(new n(this.Q.n() + 9, this.Q.o() + 9, this.Q.p() - 20, this.Q.q() - 20));
                return;
            }
        } else {
            this.H = false;
        }
    }

    public final void e(int n2, int n3) {
        if (this.v.m()) {
            this.v.e(n2, n3);
        }
    }

    public final void a(n n2) {
        if (this.k == null) {
            return;
        }
        if (this.K == 2) {
            if (this.i != null && this.v.m()) {
                this.i.d(-1, 1234);
            }
            return;
        }
        if (this.K != 0) {
            return;
        }
        bv bv2 = new bv();
        if (this.k.k != null) {
            switch (this.k.j) {
                case 0: {
                    lj lj2 = (lj)this.k.k;
                    if (lj2.b() && lj2.c()) {
                        bv2.a(new bu("S\u1eeda ch\u1eefa", 111115));
                    }
                    if (this.H) {
                        bu bu2 = new bu("C\u1edfi ra", 111116);
                        bv2.a(new bu("Chi Ti\u1ebft", 111117));
                        if (this.v.s() >= gr.m) {
                            bu2 = new bu("V\u1ee9t b\u1ecf", 111118);
                        }
                        if (lj2.d == 0) {
                            bv2.a(new bu[]{this.L.ab ? new bu("Hi\u1ec7n N\u00f3n", 1111110) : new bu("\u1ea8n N\u00f3n", 111119), bu2});
                            break;
                        }
                        bv2.a(bu2);
                        break;
                    }
                    if (this.G) {
                        if (((lj)this.k.k).d == 8) {
                            bv2.a(new bu("D\u00f9ng", 1111117));
                        } else {
                            bv2.a(new bu("Trang b\u1ecb", 1111111));
                        }
                        bv2.a(new bu("Chi Ti\u1ebft", 111117));
                        bv2.a(new bu("N\u00e2ng c\u1ea5p", 1111116));
                        if (((lj)this.k.k).a()) {
                            bv2.a(new bu("Rao b\u00e1n", 1111118));
                        }
                        bv2.a(new bu("V\u1ee9t b\u1ecf", 111118));
                        break;
                    }
                    bv2.a(new bu("Chi Ti\u1ebft", 111117));
                    bv2.a(new bu("N\u00e2ng c\u1ea5p", 1111116));
                    if (((lj)this.k.k).a()) {
                        bv2.a(new bu("Rao b\u00e1n", 1111118));
                    }
                    bv2.a(new bu("V\u1ee9t b\u1ecf", 111118));
                    break;
                }
                case 1: 
                case 5: {
                    switch (((lk)this.k.k).e) {
                        case 1: {
                            bv2.a(new bu("D\u00f9ng", 1111112));
                            break;
                        }
                        case 3: {
                            bv2.a(new bu("D\u00f9ng", 1111115));
                            break;
                        }
                        case 9: {
                            bv2.a(new bu("\u0110\u1eadp", 1111113));
                        }
                    }
                    if (((lk)this.k.k).a()) {
                        bv2.a(new bu("Rao b\u00e1n", 1111118));
                    }
                    bv2.a(new bu("V\u1ee9t b\u1ecf", 111118));
                }
            }
        } else if (this.k.j == 4) {
            bv2.a(new bu("Mua Ng\u0103n Ch\u1ee9a", 1111114));
        }
        int n3 = n2.a + this.c + (n2.c - bv2.e()) / 2;
        int n4 = n2.b + this.d + n2.d;
        if (n4 + bv2.f() > this.d + this.g) {
            n4 = this.d + n2.b - bv2.f();
        }
        n3 = n3 < this.c ? this.c : (n3 + bv2.e() > this.c + this.f ? this.c + this.f - bv2.e() : n3);
        bv2.a_(z.r + bv2.e(), n4);
        bv2.d(n3, n4);
        bv2.a(this);
        bv2.a_(1);
        this.a(bv2, this.C, (bd)new bh("", this.C.a()), this.B);
        this.m = true;
    }

    public final void a(String string) {
        com.mg.sq.a.q().e(-446456);
        dg dg2 = this.v.a(string);
        this.v.a(dg2);
        this.a(this.v.r());
        int n2 = 0;
        while (n2 < gr.k.length) {
            if (gr.k[n2].b.equals(string)) {
                gr.b(gr.k[n2]);
                return;
            }
            ++n2;
        }
    }

    public final void i(int n2, int n3) {
        com.mg.sq.a.q().e(-446456);
        com.mg.sq.a.q().e(-1122154);
        this.k(n2, n3);
        int n4 = 0;
        while (n4 < gr.l.length) {
            if (gr.l[n4].a == n2) {
                gr.a(n2, n3);
                return;
            }
            ++n4;
        }
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 111111: {
                this.B();
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 111112: {
                this.U = true;
                if (this.F) {
                    this.C();
                    return true;
                }
                ak.b().a(this.h(), false);
                com.mg.sq.a.E();
                return true;
            }
            case 1111121: {
                gs gs2;
                if (com.mg.sq.a.q().c(-1122154) && (gs2 = (gs)com.mg.sq.a.q().d(-1122154)) != null) {
                    lk lk2 = gs2.t();
                    gs2.t().g = gs2.u();
                    Object object = this;
                    object = new dg(null, lk2, lk2.e == 3 ? 5 : 1, ((hf)object).Y);
                    this.c((dg)object);
                }
                return true;
            }
            case 1111122: {
                com.mg.sq.a.q().e(-1122154);
                return true;
            }
            case 29: {
                com.mg.sq.a.q().e(-446456);
                return true;
            }
            case 1111119: {
                ap ap2 = (hs)com.mg.sq.a.q().d(241234);
                long l2 = ((hs)ap2).t() * 1000L;
                ap2 = com.mg.sq.a.q().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 mu\u1ed1n b\u00e1n " + ((hs)ap2).k + " gi\u00e1: " + l.a(l2, ",") + " Ken" + " kh\u00f4ng?", "C\u00f3", 28, "Kh\u00f4ng", 29, 1);
                ap2.b(-446456);
                ap2.a(this);
                com.mg.sq.a.q().a(ap2);
                return true;
            }
            case 28: {
                Object object = (hs)com.mg.sq.a.q().d(241234);
                long l3 = ((hs)object).t() * 1000L;
                if (((hs)object).v().j == 0) {
                    object = (lj)((hs)object).v().k;
                    kq.a().a(((lj)object).b, l3);
                    com.mg.sq.a.a(null, null);
                } else if (((hs)object).v().j == 1) {
                    object = (lk)((hs)object).v().k;
                    kq.a().a(((lb)object).a, ((lk)object).g, l3);
                    com.mg.sq.a.a(null, null);
                }
                return true;
            }
            case 1111120: {
                com.mg.sq.a.q().e(241234);
                return true;
            }
            case 111113: {
                com.mg.sq.a.F();
                return true;
            }
            case 111114: {
                if (this.m) {
                    this.z();
                } else {
                    this.C();
                }
                return true;
            }
            case 111115: {
                if (this.k != null) {
                    this.c((lj)this.k.k);
                }
                if (this.Q != null) {
                    this.Q = null;
                }
                return true;
            }
            case 111116: {
                this.F = true;
                dg dg2 = this.E[this.w];
                this.E[this.w] = null;
                this.v.a((Object)dg2);
                this.k = null;
                this.a(this.L, this.E);
                return true;
            }
            case 111117: {
                com.mg.sq.a.a((lj)this.k.k, this, "", -1, "\u0110\u00f3ng", 14);
                ak.b();
                return true;
            }
            case 111118: {
                hf hf2 = this;
                String string = hf2.k.j == 0 ? ((lj)hf2.k.k).c : ((lk)hf2.k.k).b;
                ap ap3 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc l\u00e0 mu\u1ed1n v\u1ee9t b\u1ecf " + string + " n\u00e0y kh\u00f4ng?", "C\u00f3", 9, "Kh\u00f4ng", 10, 1);
                ap3.a(hf2);
                ap3.b(-4561239);
                ak.b().a(ap3, false);
                return true;
            }
            case 111119: {
                kq.a().b(true);
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 1111110: {
                kq.a().b(false);
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 1111111: {
                this.F = true;
                n2 = ((lj)this.k.k).d;
                dg dg3 = null;
                if (this.E[n2] != null) {
                    dg3 = this.E[n2];
                }
                this.E[n2] = this.k;
                if (dg3 != null) {
                    this.v.a(dg3, this.v.b(this.k));
                } else {
                    this.v.a(this.k);
                }
                if (this.v.q() >= this.v.s()) {
                    n2 = this.v.s() - 1;
                    this.v.j(n2 < 0 ? 0 : n2);
                }
                this.a(this.L, this.E);
                this.a(this.v.r());
                return true;
            }
            case 1111117: {
                ak.b().e(-4561238);
                String[] stringArray = new String[]{((lj)this.k.k).b};
                int n3 = 0;
                while (n3 < stringArray.length) {
                    dg dg4 = this.v.a(stringArray[n3]);
                    this.v.a(dg4);
                    ++n3;
                }
                kq.a().c(stringArray);
                this.z();
                com.mg.sq.a.a(null, null);
                break;
            }
            case 1111112: {
                this.h(1);
                this.d(this.k);
                return true;
            }
            case 1111115: {
                kq.a().d(((lk)this.k.k).a);
                return true;
            }
            case 1111113: {
                lk lk3 = (lk)this.k.k;
                hf hf3 = this;
                if (lk3.k > 0L) {
                    ap ap4 = lk3.k > gr.p ? ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n kh\u00f4ng \u0111\u1ee7 Ken \u0111\u1ec3 \u0111\u1eadp tr\u1ee9ng, B\u1ea1n c\u00f3 mu\u1ed1n n\u1ea1p Ken kh\u00f4ng", "C\u00f3", 113, "Kh\u00f4ng", 114, 1) : ak.b().a("Ch\u00fa \u00fd", "\u0110\u1eadp 1 qu\u1ea3 tr\u1ee9ng b\u1ea1n ph\u1ea3i m\u1ea5t " + lk3.k + " Ken. B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u1eadp kh\u00f4ng?", "C\u00f3", 111, "Kh\u00f4ng", 112, 1);
                    ap4.a(hf3);
                    ak.b().a(ap4);
                } else {
                    lk lk4 = (lk)hf3.k.k;
                    kq.a().e(lk4.a);
                    hf3.k(lk4.a, 1);
                    com.mg.sq.a.a("\u0110\u1ee3i x\u00edu nh\u00e9...", null);
                }
                return true;
            }
            case 1111114: {
                hf hf4 = this;
                ap ap5 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n mua th\u00eam ng\u0103n ch\u1ee9a \u0111\u1ed3 kh\u00f4ng? Ph\u00ed mua 1 ng\u0103n ch\u1ee9a l\u00e0 " + gr.n, "C\u00f3", 118, "Kh\u00f4ng", 119, 1);
                ap5.b(123456);
                ap5.a(hf4);
                ak.b().a(ap5);
                return true;
            }
            case 1111116: {
                Object object;
                if (this.F) {
                    object = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111\u1ed3 cho nh\u00e2n v\u1eadt. B\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt ngay kh\u00f4ng?", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
                    ((aq)object).a(this);
                    ((aq)object).b(-4561238);
                    ak.b().a((ap)object, false);
                    this.z();
                    this.F = false;
                } else {
                    String string = ((lj)this.k.k).b;
                    ak.b().a(false);
                    com.mg.sq.a.h(string);
                    return true;
                }
            }
            case 1111118: {
                Object object;
                if (this.F) {
                    object = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111\u1ed3 cho nh\u00e2n v\u1eadt. B\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt ngay kh\u00f4ng?", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
                    ((aq)object).a(this);
                    ((aq)object).b(-4561238);
                    ak.b().a((ap)object, false);
                    this.z();
                    this.F = false;
                } else {
                    Object object2;
                    Object object3 = object2 = this;
                    if (((hf)object2).k.k != null) {
                        object3 = object2;
                        if (((hf)object3).k.j == 1) {
                            object3 = object2;
                            object = (lk)((hf)object3).k.k;
                            object3 = object2;
                            Object object4 = new dg(null, ((lk)object).b(), ((hf)object3).k.j, ((hf)object2).Y);
                            object4 = new gs((dg)object4);
                            ((aq)object4).a((bj)object2);
                            ((gs)object4).e(((lk)object).g);
                            object2 = new bh("Xong", 1111121);
                            object3 = object4;
                            ((aq)object3).a((bd)object2, true);
                            ((aq)object4).a(new bh("", 1111121));
                            object2 = new bh("H\u1ee7y", 1111122);
                            object3 = object4;
                            ((aq)object3).b((bd)object2, true);
                            ((aq)object4).b(-1122154);
                            ((gs)object4).j(true);
                            ak.b().a((ap)object4, false);
                        } else {
                            object3 = object2;
                            if (((hf)object3).k.j == 0) {
                                object3 = object2;
                                ((hf)object2).c(((hf)object3).k);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void c(dg object) {
        object = new hs((dg)object, "Rao b\u00e1n", 1111119, "H\u1ee7y", 1111120);
        ((aq)object).a(this);
        ak.b().a((ap)object, false);
    }

    protected final void s() {
    }

    private void B() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.E.length) {
            if (this.E[n3] != null) {
                ++n2;
            }
            ++n3;
        }
        String[] stringArray = new String[n2];
        n2 = 0;
        lj lj2 = null;
        int n4 = 0;
        while (n4 < this.E.length) {
            if (this.E[n4] != null) {
                lj2 = (lj)this.E[n4].k;
                stringArray[n2] = lj2.b;
                ++n2;
            }
            ++n4;
        }
        kq.a().c(stringArray);
        this.F = false;
    }

    private void C() {
        this.W = true;
        if (this.F) {
            ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111\u1ed3 cho nh\u00e2n v\u1eadt. B\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt ngay kh\u00f4ng?", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
            ap2.a(this);
            ap2.b(-4561238);
            ak.b().a(ap2, false);
            this.z();
            this.F = false;
            return;
        }
        ak.b().a(this.h(), false);
    }

    private void a(Object object) {
        this.G = false;
        this.k = (dg)object;
        this.S = 0;
        this.R = null;
        if (this.k != null) {
            if (this.k.j == 4) {
                if (this.K == 2) {
                    this.a((bd)null);
                    bd bd2 = null;
                    object = this;
                    ((aq)object).a(bd2, true);
                }
                return;
            }
            object = null;
            if (this.k.j == 0 && this.k.k != null) {
                object = (lj)this.k.k;
                int n2 = 0;
                if (((lj)object).h <= this.L.G) {
                    ++n2;
                }
                if (((lj)object).g == 2 || ((lj)object).g == this.L.f) {
                    ++n2;
                }
                this.G = n2 == 2;
            }
            switch (this.K) {
                case 1: {
                    this.a((lj)object);
                    return;
                }
                case 2: {
                    if (this.v.m()) {
                        this.a(new bh("", 1234));
                        bh bh2 = new bh("B\u00e1n", 1234);
                        object = this;
                        ((aq)object).a(bh2, true);
                        return;
                    }
                    this.a((bd)null);
                    bd bd3 = null;
                    object = this;
                    ((aq)object).a(bd3, true);
                }
            }
            return;
        }
        switch (this.K) {
            case 1: {
                this.a((bd)null);
                return;
            }
            case 2: {
                this.a((bd)null);
                bd bd4 = null;
                object = this;
                ((aq)object).a(bd4, true);
            }
        }
    }

    public final void a(lj object) {
        if (object == null) {
            this.a((bd)null);
            bd bd2 = null;
            object = this;
            ((aq)object).a(bd2, true);
            return;
        }
        if (this.K != 0) {
            if (((lj)object).b()) {
                if (((lj)object).c()) {
                    this.a(new bh("", 22));
                    bh bh2 = new bh("S\u1eeda ch\u1eefa", 22);
                    object = this;
                    ((aq)object).a(bh2, true);
                    return;
                }
            } else {
                this.a((bd)null);
                bd bd3 = null;
                object = this;
                ((aq)object).a(bd3, true);
            }
        }
    }

    public final void j(int n2) {
        lk lk2 = (lk)this.V.k;
        this.N = false;
        lk2.g = n2;
        this.O.d(0);
        this.O.l(n2);
        if (n2 <= 0) {
            this.v.a(this.V);
        }
        if (lk2.g == 0) {
            this.h(0);
        }
    }

    private void c(lj object) {
        if (((hj)(object = com.mg.sq.a.a((bj)this, new bh("Ch\u1ecdn", 21), new bh("\u0110\u00f3ng", 20), new bh("", 21), (lj)object))).u() <= 0) {
            ((hj)object).a("Hi\u1ec7n t\u1ea1i b\u1ea1n kh\u00f4ng c\u00f2n c\u00e2y b\u00faa n\u00e0o \u0111\u1ec3 s\u1eefa ch\u1eefa! B\u1ea1n c\u00f3 mu\u1ed1n v\u00e0o c\u1eeda h\u00e0ng mua kh\u00f4ng?");
            bh bh2 = new bh("C.H\u00e0ng", 26);
            Object object2 = object;
            ((aq)object2).a(bh2, true);
            ((hj)object).a(new bh("", 26));
        }
        ((hj)object).e(2);
        this.z();
    }

    private void d(dg dg2) {
        if (dg2 == null || dg2.k == null) {
            return;
        }
        this.V = dg2;
        if (this.O != null) {
            this.O.l(((lk)dg2.k).g);
        }
    }

    public final void t() {
        this.v.i(this.v.q());
    }

    public final void j(boolean bl2) {
        this.L.ab = bl2;
        this.a(this.L, this.E);
        this.F = true;
    }

    public final void b(au au2, int n2) {
        this.a(this.v.u());
    }

    public final void a(au object, int n2) {
        if (object == null && n2 > this.X - 1) {
            object = this;
            object = new dg(this.v.k, null, 4, ((hf)object).Y);
        }
        this.a(object);
    }

    public final void a(au au2, int n2, int n3) {
        this.a(au2);
    }

    public final void v() {
        com.mg.sq.a.t();
        if (this.U) {
            com.mg.sq.a.E();
        }
        if (this.W) {
            ak.b().a(this.h(), false);
        }
    }

    public final void b(lk lk2) {
        Object object = null;
        int n2 = 0;
        a a2 = new a();
        int n3 = 0;
        while (n3 < this.v.s()) {
            if ((((dg)this.v.k((int)n3)).j == 1 || ((dg)this.v.k((int)n3)).j == 5) && (object = (lk)((dg)this.v.k((int)n3)).k) != null && ((lb)object).a == lk2.a) {
                n2 += ((lk)object).g;
                a2.a(this.v.k(n3));
            }
            ++n3;
        }
        n2 += lk2.g;
        n3 = 0;
        while (n3 < a2.d()) {
            this.v.a((dg)a2.b(n3));
            ++n3;
        }
        if (lk2.l <= 0) {
            object = lk2.b();
            lk2.b().g = n2;
            lk lk3 = object;
            object = this;
            dg dg2 = new dg(null, lk3, lk2.e == 3 ? 5 : 1, ((hf)object).Y);
            this.v.a((Object)dg2);
            return;
        }
        n3 = n2 / lk2.l + (n2 % lk2.l > 0 ? 1 : 0);
        int n4 = 0;
        while (n4 < n3) {
            object = lk2.b();
            if (n2 >= lk2.l) {
                ((lk)object).g = lk2.l;
                n2 -= lk2.l;
            } else if (n2 > 0) {
                ((lk)object).g = n2;
                n2 = 0;
            }
            Object object2 = object;
            object = this;
            object = new dg(null, object2, lk2.e == 3 ? 5 : 1, ((hf)object).Y);
            this.v.a(object);
            ++n4;
        }
    }

    public final void b(Graphics graphics) {
        ca.d.c(true);
        ca.d.a(graphics, this.T, this.r.a + this.c, this.r.b + this.d, 0);
        ca.d.c(false);
        oz.b(graphics, this.q.a + this.c, this.q.b + this.d, this.L.g);
        ca.d.a(graphics, "C\u1ea5p: " + this.L.G, this.c + this.f - 14 + this.c, this.r.b + this.d, 2);
        int n2 = 0;
        while (n2 < this.u.length - 1) {
            oz.b(graphics, this.u[n2].a + this.c, this.u[n2].b + this.d, this.u[n2].c, this.u[n2].d, 6647295, 0xFFFFFF, 8369663);
            cz.a(graphics, this.o, n2 * this.p, 0, this.p, this.p, this.u[n2].a + this.c + (this.u[n2].c - this.p) / 2, this.u[n2].b + this.d + (this.u[n2].d - this.p) / 2, 0);
            ++n2;
        }
        lj lj2 = null;
        int n3 = 0;
        while (n3 < this.E.length) {
            if (this.E[n3] != null) {
                lj2 = (lj)this.E[n3].k;
                this.E[n3].a(graphics, this.u[lj2.d].a + this.c, this.u[lj2.d].b + this.d);
            }
            ++n3;
        }
        if (this.D != null) {
            this.D.a(graphics, this.c, this.d);
            this.D.c(true);
        }
        if (this.k != null && !this.H && this.k.k != null && this.k.j == 0) {
            lj2 = (lj)this.k.k;
            if (lj2.d < this.u.length) {
                int n4 = this.d;
                int n5 = this.c;
                n n6 = this.u[lj2.d];
                lj2 = graphics;
                lj2.setColor(0xFEFF77);
                lj2.drawRect(n6.a + n5, n6.b + n4, n6.c, n6.d);
                lj2.setColor(16776624);
                lj2.drawRect(n6.a - 1 + n5, n6.b - 1 + n4, n6.c + 2, n6.d + 2);
                lj2.setColor(0xFFFDD3);
                lj2.drawRect(n6.a - 2 + n5, n6.b - 2 + n4, n6.c + 4, n6.d + 4);
            }
        }
        if (!this.v.m()) {
            oz.a(graphics, this.u[this.w], this.c, this.d, this.I);
            switch (this.K) {
                case 1: {
                    if (this.O == null) break;
                    this.O.a(graphics, this.u[this.w].a + this.c, this.u[this.w].b + this.u[this.w].d + this.d);
                }
            }
        }
        if (this.Q != null) {
            this.Q.a(graphics, 0, 0);
        }
        if (hf.A()) {
            ca.d.a(graphics, String.valueOf(this.v.s()) + "/" + gr.m, this.t.a, this.t.b + this.t.d - 16, 2);
        } else {
            ca.d.a(graphics, String.valueOf(this.v.s()) + "/" + gr.m, this.t.a, this.t.b - 16, 0);
        }
        if (!this.m && this.R != null) {
            this.R.a(graphics, this.c, this.d);
        }
        oz.c(graphics, this.s.a + this.c, this.s.b + this.d, this.s.c, this.s.d);
        if (this.y != null) {
            this.y.a(graphics, this.s.a + this.c, this.s.b + 5 + this.d);
        }
    }

    public final void a(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
    }

    public final void b(lj object) {
        Image image = this.M.a(lz.a((lj)object) + 98, true);
        lj lj2 = object;
        object = this;
        object = new dg(image, lj2, 0, ((hf)object).Y);
        this.b((dg)object);
        this.a(this.v.r());
        this.v.h(this.v.a() + 1);
        com.mg.sq.a.t();
    }

    public final void c(lk lk2) {
        this.b(lk2);
        this.a(this.v.r());
        com.mg.sq.a.t();
    }

    public final void b(String string) {
        this.a(this.v.r());
        com.mg.sq.a.t();
        com.mg.sq.a.u(string);
    }

    public final int w() {
        return this.X;
    }

    public final void j(int n2, int n3) {
        com.mg.sq.a.t();
        lk lk2 = null;
        a a2 = new a();
        int n4 = 0;
        while (n4 < this.v.s()) {
            this.k = (dg)this.v.k(n4);
            if (this.k != null && this.k.k != null && this.k.j != 0 && this.k.j != 4) {
                lk lk3 = (lk)this.k.k;
                cw.a("[receiveItemUsed]" + lk3.a + ",,,,,," + n2);
                if (lk3 != null && lk3.a == n2) {
                    a2.a(this.k);
                    lk2 = lk3;
                }
            }
            ++n4;
        }
        if (lk2 == null) {
            return;
        }
        lk2.g = n3;
        n4 = 0;
        while (n4 < a2.d()) {
            this.v.a((dg)a2.b(n4));
            ++n4;
        }
        if (lk2.g > 0) {
            this.a(new lk[]{lk2});
        }
        this.t();
    }

    public final ou x() {
        return this.M;
    }

    public final d y() {
        return this.Y;
    }
}

