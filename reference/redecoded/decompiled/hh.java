/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class hh
extends ht
implements bq {
    private Image k = f.d("/info/hidenobj");
    private int p = 32;
    private cu q = null;
    private cu r = null;
    private k s = null;
    private k t = null;
    private k[] u;
    private fg v = null;
    private int w = 0;
    private byte[][] x;
    private mg y = null;
    private boolean z = false;
    private az A;
    private az B;
    private az C;
    private ay D;
    private dc E = null;
    private dc[] F = new dc[6];
    private boolean G = false;
    private boolean H = false;
    private boolean I = false;
    private int J = 0;
    private int K = 0;
    private int L = 0;
    private lh M;
    private ox N;
    private boolean O = false;
    private db P = null;
    private boolean Q;
    private gj R;
    private fw S;
    private int T = 0;
    private String U;
    private boolean V = false;
    private dc W;
    private boolean X;
    private int Y;
    private d Z;
    private boolean aa = false;

    public hh(ox object, d d2) {
        k k2;
        k k3;
        k k4;
        k k5;
        k k6;
        Object object2;
        this.b(241202);
        this.a(new ba());
        this.a(this);
        if (d2 == null) {
            d2 = new if(new int[]{0xFF0000, 0xFFFF00});
        }
        this.Z = d2;
        int n2 = 240;
        int n3 = 320 - ba.a;
        if (hh.B()) {
            n2 = 320;
            n3 = v.u - ba.a;
        }
        int n4 = v.t >= n2 ? (v.t - n2) / 2 : 0;
        int n5 = v.u >= n3 ? (v.u - ba.a - n3) / 2 : 0;
        this.a(n4, n5, n2, n3);
        this.N = new ox((ox)object);
        object = this;
        this.q = new cu(6, 4);
        ((hh)object).r = new cu(22, 6);
        if (hh.B()) {
            ((hh)object).s = new k(13, 24, 54, 60);
            object2 = new k(6, 90, 32, 32);
            k6 = new k(6, 126, 32, 32);
            k5 = new k(42, 90, 32, 32);
            k4 = new k(42, 126, 32, 32);
            k3 = new k(6, 162, 32, 32);
            k2 = new k(42, 162, 32, 32);
            ((hh)object).t = new k(79, 23, 226, ((am)object).g - 33);
        } else {
            ((hh)object).s = new k(95, 21, 54, 60);
            object2 = new k(60, 18, 32, 32);
            k6 = new k(60, 53, 32, 32);
            k5 = new k(153, 18, 32, 32);
            k4 = new k(153, 53, 32, 32);
            k3 = new k(189, 18, 32, 32);
            k2 = new k(189, 53, 32, 32);
            ((hh)object).t = new k(9, 88, 220, ((am)object).g - 96);
        }
        ((hh)object).v = new fg(hh.B());
        ((hh)object).v.e(true);
        ((hh)object).v.i = new k(((hh)object).t.a, ((hh)object).t.b, ((hh)object).t.c, ((hh)object).t.d);
        ((hh)object).M = go.k.a();
        Object object3 = ((hh)object).M;
        int n6 = go.l.length - object3.D.length;
        int n7 = 0;
        while (n7 < go.m.length) {
            n6 = go.m[n7].l == 1 ? (n6 += go.m[n7].g) : (go.m[n7].l > 1 ? (n6 += go.m[n7].g / go.m[n7].l + (go.m[n7].g % go.m[n7].l > 0 ? 1 : 0)) : ++n6);
            ++n7;
        }
        ((hh)object).Y = n6;
        if (((hh)object).Y < go.n) {
            ((hh)object).Y = go.n;
        }
        ((hh)object).v.d(((hh)object).Y, 2);
        ((hh)object).v.a((bq)object);
        ((hh)object).h(0);
        ll[] llArray = go.l;
        object3 = object;
        n7 = object3.M.D.length;
        object3.v.t();
        int n8 = 0;
        while (n8 < llArray.length) {
            if (llArray[n8] != null) {
                boolean bl2 = false;
                if (n7 > 0) {
                    int n9 = 0;
                    while (n9 < object3.M.D.length) {
                        if (llArray[n8].c.equals(object3.M.D[n9].c)) {
                            if (llArray[n8].e != 8) {
                                lm[] lmArray = object3;
                                object3.F[llArray[n8].e] = new dc(object3.N.a(mb.a(llArray[n8]) + 98, true), llArray[n8], 0, object3.Z);
                            }
                            --n7;
                            bl2 = true;
                            break;
                        }
                        ++n9;
                    }
                }
                if (!bl2) {
                    lm[] lmArray = object3;
                    dc dc2 = new dc(object3.N.a(mb.a(llArray[n8]) + 98, true), llArray[n8], 0, object3.Z);
                    object3.v.a((Object)dc2);
                }
            }
            ++n8;
        }
        object3 = new lm[go.m.length];
        int n10 = 0;
        while (n10 < ((lm[])object3).length) {
            object3[n10] = go.m[n10].b();
            ++n10;
        }
        super.a((lm[])object3);
        ((hh)object).D = new ay();
        ((hh)object).D.a(new k(((hh)object).t.a, ((hh)object).t.b, ((hh)object).t.c, ((hh)object).t.d));
        ((hh)object).D.b(((hh)object).v);
        ((hh)object).D.h(1);
        ((hh)object).u = new k[]{object2, k6, k5, k4, k3, k2, ((hh)object).t};
        object3 = object;
        ((hh)object).x = new byte[6][4];
        if (hh.B()) {
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
        super.a(((hh)object).M, null);
        ((hh)object).A = new gb(0, 0);
        ((hh)object).C = new gb(3, 2);
        ((hh)object).B = new gb(4, 3);
        ((am)object).a(com.mg.sq.a.n);
        object2 = ((hh)object).A;
        Object object4 = object;
        ((am)object4).a((az)object2, true);
        object2 = ((hh)object).B;
        object4 = object;
        ((am)object4).b((az)object2, true);
        ((hh)object).i(((hh)object).w);
        ((hh)object).U = com.mg.sq.a.a(((hh)object).M.b, ((am)object).i() - 85);
        this.a((az)null);
        this.h(0);
        if (!cs.a.c(143)) {
            od.h(143);
            gr.o = false;
        }
    }

    private static boolean B() {
        return com.mg.sq.a.k == 1;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
    }

    public final void h(int n2) {
        this.L = n2;
        switch (this.L) {
            case 0: {
                this.W = null;
                this.v.a((db)null);
                az az2 = this.A;
                hh hh2 = this;
                hh2.a(az2, true);
                this.a((az)null);
                az2 = this.B;
                hh2 = this;
                hh2.b(az2, true);
                this.O = false;
                return;
            }
            case 1: {
                this.P = new db();
                if (this.v != null) {
                    this.v.a(this.P);
                }
                az az3 = null;
                hh hh3 = this;
                hh3.a(az3, true);
                return;
            }
            case 2: {
                gb gb2 = null;
                hh hh4 = this;
                hh4.a(gb2, true);
                this.a((az)null);
                gb2 = new gb(1235, 3);
                hh4 = this;
                hh4.b(gb2, true);
            }
        }
    }

    private void a(lm[] lmArray) {
        int n2 = 0;
        while (n2 < lmArray.length) {
            int n3;
            int n4 = n3 = lmArray[n2].e == 3 ? 2 : 1;
            if (lmArray[n2].g > 0) {
                Object object;
                if (lmArray[n2].l > 0) {
                    int n5 = lmArray[n2].g;
                    int n6 = lmArray[n2].g / lmArray[n2].l;
                    int n7 = 0;
                    while (n7 < n6) {
                        object = lmArray[n2].b();
                        lmArray[n2].b().g = lmArray[n2].l;
                        n5 -= lmArray[n2].l;
                        Object object2 = object;
                        object = this;
                        object = new dc(null, object2, n3, ((hh)object).Z);
                        this.v.a(object);
                        ++n7;
                    }
                    if (n5 > 0) {
                        lm lm2 = lmArray[n2].b();
                        lmArray[n2].b().g = n5;
                        object = this;
                        object = new dc(null, lm2, n3, ((hh)object).Z);
                        this.v.a(object);
                    }
                } else {
                    object = this;
                    dc dc2 = new dc(null, lmArray[n2], n3, ((hh)object).Z);
                    this.v.a((Object)dc2);
                }
            }
            ++n2;
        }
    }

    public final void a(lm lm2) {
        int n2 = 0;
        lm lm3 = lm2.b();
        a a2 = new a();
        int n3 = 0;
        while (n3 < this.v.s()) {
            lm lm4;
            this.E = (dc)this.v.k(n3);
            if (this.E != null && this.E.k != null && (this.E.j == 1 || this.E.j == 2) && (lm4 = (lm)this.E.k) != null && lm4.a == lm2.a) {
                n2 += lm4.g;
                a2.a(this.E);
            }
            ++n3;
        }
        lm3.g = n2 - lm2.g;
        n3 = 0;
        while (n3 < a2.d()) {
            this.v.a((dc)a2.b(n3));
            ++n3;
        }
        if (lm3.g > 0) {
            this.a(new lm[]{lm3});
        }
        this.v();
    }

    public final void h(int n2, int n3) {
        if (n3 == 0) {
            this.i(n2, Integer.MAX_VALUE);
            return;
        }
        int n4 = this.v.s() - 1;
        while (n4 >= 0) {
            this.E = (dc)this.v.k(n4);
            if (this.E.k instanceof lm) {
                lm lm2 = (lm)this.E.k;
                if (lm2.a == n2) {
                    lm2.g = n3;
                }
            }
            --n4;
        }
        this.v();
    }

    public final void i(int n2, int n3) {
        a a2 = new a();
        int n4 = this.v.s() - 1;
        while (n4 >= 0) {
            this.E = (dc)this.v.k(n4);
            if (this.E.k instanceof lm) {
                lm lm2 = (lm)this.E.k;
                if (lm2.a == n2) {
                    if (lm2.g > n3) {
                        lm2.g -= n3;
                        break;
                    }
                    n3 -= lm2.g;
                    a2.a(this.E);
                }
            }
            --n4;
        }
        n4 = 0;
        while (n4 < a2.d()) {
            this.v.a((dc)a2.b(n4));
            ++n4;
        }
        this.v();
    }

    public final void j(int n2, int n3) {
        com.mg.sq.a.s().v();
        int n4 = 0;
        while (n4 < go.m.length) {
            if (go.m[n4].a == n2) {
                go.b(n2, go.m[n4].g - n3);
                break;
            }
            ++n4;
        }
        if (this.E != null && this.E.j != 0) {
            this.v.a(this.E);
        }
        this.v();
    }

    public final void a(dc dc2) {
        this.v.a(dc2);
        this.v();
    }

    public final void b(dc dc2) {
        this.v.a((Object)dc2);
        this.v();
    }

    private void a(lh lh2, dc[] dcArray) {
        int n2 = 0;
        if (dcArray != null) {
            int n3 = 0;
            while (n3 < dcArray.length) {
                if (dcArray[n3] != null) {
                    ++n2;
                }
                ++n3;
            }
            ll[] llArray = new ll[n2];
            n3 = 0;
            int n4 = 0;
            while (n4 < dcArray.length) {
                if (dcArray[n4] != null && dcArray[n4].j == 0) {
                    llArray[n3] = (ll)dcArray[n4].k;
                    ++n3;
                }
                ++n4;
            }
            lh2.D = llArray;
        }
        this.y = null;
        System.gc();
        ct.b("[upgradeAnimation]");
        this.y = mb.a(lh2, false);
        this.y.a(lc.a(lh2));
        this.y.a(nr.a(lh2));
        this.y.c(2);
        this.y.i();
    }

    public final void u() {
        if (!this.v.m()) {
            ++this.K;
            if (this.K >= 5) {
                this.J = this.J == 0 ? -2 : 0;
                this.K = 0;
            }
        }
        if (this.y != null) {
            this.y.i();
        }
        if (this.D != null) {
            this.D.n();
        }
        int n2 = 0;
        while (n2 < this.F.length) {
            if (this.F[n2] != null) {
                this.F[n2].n();
            }
            ++n2;
        }
        if (this.P != null) {
            this.P.i();
        }
        if (this.R != null) {
            this.R.i();
            if (!this.R.m()) {
                this.R = null;
            }
        }
        if (this.S != null) {
            this.S.i();
        }
        if (this.T < 7) {
            ++this.T;
            if (this.T == 7 && this.E != null && this.E.k != null) {
                Object object = this.E.k;
                hh hh2 = this;
                this.S = new fw(object);
                int n3 = hh2.g;
                hh2.S.a(9, n3, hh2.f - 20, hh2.g / 4);
                hh2.S.c(9, n3);
                hh2.S.a(9, n3 - hh2.S.q() - 7);
            }
        }
    }

    public final void e(int n2) {
        switch (n2) {
            case 0: {
                hh hh2 = this;
                br[] brArray = null;
                if (hh2.L == 0) {
                    brArray = hh2.G ? new br[]{new br("C\u1eadp nh\u1eadt", 111111), new br("Nh\u00e2n V\u1eadt", 111112), new br("C\u1eeda h\u00e0ng", 111113), new br("\u0110\u00f3ng", 111114)} : new br[]{new br("Nh\u00e2n V\u1eadt", 111112), new br("C\u1eeda h\u00e0ng", 111113), new br("\u0110\u00f3ng", 111114)};
                }
                hh2.a(brArray, hh2.C, (az)new bd("", hh2.C.a()), hh2.B);
                return;
            }
            case 4: {
                switch (this.L) {
                    case 1: {
                        this.h(0);
                        return;
                    }
                }
                if (this.m == null) {
                    this.D();
                    return;
                }
                this.A();
                return;
            }
            case 3: {
                this.m.f(95);
                return;
            }
            case 6: {
                ag.b().e(-4561238);
                this.A();
                ag.b().a(this.h(), false);
                if (!this.V) break;
                com.mg.sq.a.G();
                return;
            }
            case 5: {
                ag.b().e(-4561238);
                this.C();
                this.A();
                com.mg.sq.a.s().a((String)null, (il)null);
                return;
            }
            case 9: {
                if (this.E.j == 0) {
                    ks.a().a(new String[]{((ll)this.E.k).c});
                } else if (((lm)this.E.k).l == 1) {
                    lm lm2 = (lm)this.E.k;
                    ks.a().f(lm2.a, 1);
                } else {
                    lm lm3 = (lm)this.E.k;
                    ks.a().f(lm3.a, lm3.g);
                }
                ag.b().e(-4561239);
                com.mg.sq.a.s().a((String)null, (il)null);
                return;
            }
            case 10: {
                ag.b().e(-4561239);
                return;
            }
            case 14: {
                ag.b().e(241212);
                return;
            }
            case 20: {
                ag.b().e(-241439);
                if (!this.Q) break;
                this.Q = false;
                n2 = 0;
                if (this.P != null) {
                    n2 = this.P.r();
                }
                this.h(this.L);
                this.P.l(n2);
                return;
            }
            case 21: {
                Object object = (hl)ag.b().e();
                object = ((hl)object).v();
                this.h(1);
                this.d(this.v.a((lm)object));
                this.a((ll)this.E.k);
                ag.b().e(-241439);
                return;
            }
            case 23: {
                this.h(0);
                return;
            }
            case 22: {
                this.O = true;
                ks.a().a(((lm)this.W.k).a, ((ll)this.E.k).c);
                this.P.d(1);
                return;
            }
            case 27: {
                if (this.E == null) break;
                this.Q = true;
                this.c((ll)this.E.k);
                return;
            }
            case 26: {
                ag.b().a(false);
                com.mg.sq.a.s().H();
                return;
            }
            case 112: {
                ag.b().a(false);
                return;
            }
            case 114: {
                ag.b().a(false);
                return;
            }
            case 111: {
                ag.b().a(false);
                lm lm4 = (lm)this.E.k;
                this.i(lm4.a, 1);
                ks.a().f(lm4.a);
                com.mg.sq.a.s().a("\u0110\u1ee3i x\u00edu nh\u00e9...", (il)null, 500);
                return;
            }
            case 113: {
                ag.b().a(false);
                com.mg.sq.a.s().N();
                return;
            }
            case 118: {
                com.mg.sq.a.s().e(123456);
                ks.a().u();
                return;
            }
            case 119: {
                com.mg.sq.a.s().e(123456);
            }
        }
    }

    public final void a(String[] stringArray) {
        int n2 = 0;
        while (n2 < stringArray.length) {
            dc dc2 = this.v.a(stringArray[n2]);
            if (dc2 != null) {
                if (this.v.m()) {
                    this.v.a(dc2);
                    this.v.i(this.v.q());
                }
                go.b((ll)dc2.k);
            }
            ++n2;
        }
        com.mg.sq.a.s().v();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void f(int n2) {
        switch (this.L) {
            case 1: {
                if (!this.O) break;
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
                    hh hh2 = this;
                    if (n3 < 0) return;
                    if ((n3 = hh2.x[hh2.w][n3]) == -3) {
                        hh2.I = false;
                        hh2.v.d(true);
                        hh2.v.i(0);
                        return;
                    }
                    if (n3 < 0) return;
                    hh2.i(n3);
                    return;
                }
                case 95: {
                    if (this.E == null) return;
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
        if (this.S != null) {
            this.S.g(n2 - this.c, n3 - this.d);
        }
        switch (this.L) {
            case 1: {
                if (!this.O) break;
                return;
            }
        }
        int n4 = 0;
        while (n4 < this.u.length - 1) {
            if (new k(this.u[n4].a + this.c, this.u[n4].b + this.d, this.u[n4].c, this.u[n4].d).a(n2, n3)) {
                this.v.d(false);
                String string = "";
                if (this.E != null) {
                    string = ((ll)this.E.k).c;
                }
                this.i(n4);
                if (this.E != null && ((ll)this.E.k).c.equals(string)) {
                    this.a(this.u[this.w]);
                }
                return;
            }
            ++n4;
        }
        if (new k(this.v.i.a + this.c, this.v.i.b + this.d, this.v.i.c, this.v.i.d).a(n2, n3)) {
            if (!this.v.m()) {
                this.v.d(true);
            }
            this.I = false;
            this.D.c(n2 - this.c, n3 - this.d);
            return;
        }
    }

    public final void i(int n2) {
        this.w = n2;
        this.a((Object)this.F[n2]);
        if (this.E != null) {
            this.I = true;
            this.H = true;
            if (gr.p && this.E.k != null && ((ll)this.E.k).p == 0 && ((ll)this.E.k).c()) {
                gr.p = false;
                od.h(144);
                int n3 = this.u[n2].b - 10 + this.d;
                this.R = new gj("B\u1ea5m phim gi\u1eefa. R\u1ed1i ch\u1ecdn s\u1eeda ch\u1eefa", this.c + this.f / 2, n3, this.f - 20, 80, false);
                this.R.a(this.u[this.w].a - 10 + this.u[n2].c / 2);
                if (!hh.B()) {
                    this.R.g(this.u[n2].b + 35 + this.d);
                    this.R.a(true);
                }
                this.R.a(new k(this.R.n() + 9, this.R.o() + 9, this.R.p() - 20, this.R.q() - 20));
                return;
            }
        } else {
            this.I = false;
        }
    }

    public final void e(int n2, int n3) {
        if (this.v.m()) {
            this.v.e(n2, n3);
        }
    }

    public final void a(k k2) {
        if (this.E == null) {
            return;
        }
        if (this.L == 2) {
            if (this.i != null && this.v.m()) {
                this.i.d(-1, 1234);
            }
            return;
        }
        if (this.L != 0) {
            return;
        }
        bs bs2 = new bs();
        if (this.E.k != null) {
            switch (this.E.j) {
                case 0: {
                    ll ll2 = (ll)this.E.k;
                    if (ll2.b() && ll2.c()) {
                        bs2.a(new br("S\u1eeda ch\u1eefa", 111115));
                    }
                    if (this.I) {
                        br br2 = new br("C\u1edfi ra", 111116);
                        bs2.a(new br("Chi Ti\u1ebft", 111117));
                        if (this.v.s() >= go.n) {
                            br2 = new br("V\u1ee9t b\u1ecf", 111118);
                        }
                        if (ll2.e == 0) {
                            bs2.a(new br[]{this.M.Z ? new br("Hi\u1ec7n N\u00f3n", 1111110) : new br("\u1ea8n N\u00f3n", 111119), br2});
                            break;
                        }
                        bs2.a(br2);
                        break;
                    }
                    if (this.H) {
                        if (((ll)this.E.k).e == 8) {
                            bs2.a(new br("D\u00f9ng", 1111117));
                        } else {
                            bs2.a(new br("Trang b\u1ecb", 1111111));
                        }
                        bs2.a(new br("Chi Ti\u1ebft", 111117));
                        bs2.a(new br("N\u00e2ng c\u1ea5p", 1111116));
                        if (((ll)this.E.k).a()) {
                            bs2.a(new br("Rao b\u00e1n", 1111118));
                        }
                        bs2.a(new br("V\u1ee9t b\u1ecf", 111118));
                        break;
                    }
                    bs2.a(new br("Chi Ti\u1ebft", 111117));
                    bs2.a(new br("N\u00e2ng c\u1ea5p", 1111116));
                    if (((ll)this.E.k).a()) {
                        bs2.a(new br("Rao b\u00e1n", 1111118));
                    }
                    bs2.a(new br("V\u1ee9t b\u1ecf", 111118));
                    break;
                }
                case 1: 
                case 2: {
                    switch (((lm)this.E.k).e) {
                        case 1: {
                            bs2.a(new br("D\u00f9ng", 1111112));
                            break;
                        }
                        case 3: {
                            bs2.a(new br("D\u00f9ng", 1111115));
                            break;
                        }
                        case 9: {
                            bs2.a(new br("M\u1edf", 1111113));
                        }
                    }
                    if (((lm)this.E.k).a()) {
                        bs2.a(new br("Rao b\u00e1n", 1111118));
                    }
                    bs2.a(new br("V\u1ee9t b\u1ecf", 111118));
                }
            }
        } else if (this.E.j == 3) {
            bs2.a(new br("Mua Ng\u0103n Ch\u1ee9a", 1111114));
        }
        int n2 = k2.a + this.c + (k2.c - bs2.e()) / 2;
        int n3 = k2.b + this.d + k2.d;
        if (n3 + bs2.f() > this.d + this.g) {
            n3 = this.d + k2.b - bs2.f();
        }
        n2 = n2 < this.c ? this.c : (n2 + bs2.e() > this.c + this.f ? this.c + this.f - bs2.e() : n2);
        bs2.a_(v.t + bs2.e(), n3);
        bs2.d(n2, n3);
        bs2.a(this);
        bs2.a_(1);
        this.a(bs2, this.C, (az)new bd("", this.C.a()), this.B);
        this.n = true;
    }

    public final void a(String string) {
        com.mg.sq.a.s().e(-446456);
        dc dc2 = this.v.a(string);
        this.v.a(dc2);
        this.a(this.v.r());
        int n2 = 0;
        while (n2 < go.l.length) {
            if (go.l[n2].c.equals(string)) {
                go.b(go.l[n2]);
                return;
            }
            ++n2;
        }
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 111111: {
                this.C();
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 111112: {
                this.V = true;
                if (this.G) {
                    this.D();
                    return true;
                }
                ag.b().a(this.h(), false);
                com.mg.sq.a.G();
                return true;
            }
            case 1111121: {
                gu gu2;
                if (com.mg.sq.a.s().c(-1122154) && (gu2 = (gu)com.mg.sq.a.s().d(-1122154)) != null) {
                    lm lm2 = gu2.t();
                    gu2.t().g = gu2.u();
                    Object object = this;
                    object = new dc(null, lm2, lm2.e == 3 ? 2 : 1, ((hh)object).Z);
                    this.c((dc)object);
                }
                return true;
            }
            case 1111122: {
                com.mg.sq.a.s().e(-1122154);
                return true;
            }
            case 29: {
                com.mg.sq.a.s().e(-446456);
                return true;
            }
            case 1111119: {
                al al2 = (hu)com.mg.sq.a.s().d(241234);
                long l2 = ((hu)al2).t() * 1000L;
                al2 = com.mg.sq.a.s().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 mu\u1ed1n b\u00e1n " + ((hu)al2).k + " gi\u00e1: " + i.a(l2, ",") + " Ken kh\u00f4ng?", "C\u00f3", 28, "Kh\u00f4ng", 29, 1);
                al2.b(-446456);
                al2.a(this);
                com.mg.sq.a.s().a(al2);
                return true;
            }
            case 28: {
                Object object = (hu)com.mg.sq.a.s().d(241234);
                long l3 = ((hu)object).t() * 1000L;
                if (((hu)object).v().j == 0) {
                    object = (ll)((hu)object).v().k;
                    ks.a().a(((ll)object).c, l3);
                    com.mg.sq.a.s().a((String)null, (il)null);
                } else if (((hu)object).v().j == 1) {
                    object = (lm)((hu)object).v().k;
                    ks.a().a(((ld)object).a, ((lm)object).g, l3);
                    com.mg.sq.a.s().a((String)null, (il)null);
                }
                return true;
            }
            case 1111120: {
                com.mg.sq.a.s().e(241234);
                return true;
            }
            case 111113: {
                com.mg.sq.a.s().H();
                return true;
            }
            case 111114: {
                if (this.n) {
                    this.A();
                } else {
                    this.D();
                }
                return true;
            }
            case 111115: {
                if (this.E != null) {
                    this.c((ll)this.E.k);
                }
                if (this.R != null) {
                    this.R = null;
                }
                return true;
            }
            case 111116: {
                this.G = true;
                dc dc2 = this.F[this.w];
                this.F[this.w] = null;
                this.v.a((Object)dc2);
                this.E = null;
                this.a(this.M, this.F);
                return true;
            }
            case 111117: {
                com.mg.sq.a.a((ll)this.E.k, this, "", -1, "\u0110\u00f3ng", 14);
                ag.b().e();
                return true;
            }
            case 111118: {
                hh hh2 = this;
                String string = hh2.E.j == 0 ? ((ll)hh2.E.k).d : ((lm)hh2.E.k).b;
                al al3 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc l\u00e0 mu\u1ed1n v\u1ee9t b\u1ecf " + string + " n\u00e0y kh\u00f4ng?", "C\u00f3", 9, "Kh\u00f4ng", 10, 1);
                al3.a(hh2);
                al3.b(-4561239);
                ag.b().a(al3, false);
                return true;
            }
            case 111119: {
                ks.a().b(true);
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 1111110: {
                ks.a().b(false);
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 1111111: {
                this.G = true;
                n2 = ((ll)this.E.k).e;
                ((ll)this.E.k).o = -1L;
                dc dc3 = null;
                if (this.F[n2] != null) {
                    dc3 = this.F[n2];
                }
                this.F[n2] = this.E;
                if (dc3 != null) {
                    this.v.a(dc3, this.v.b(this.E));
                } else {
                    this.v.a(this.E);
                }
                if (this.v.q() >= this.v.s()) {
                    n2 = this.v.s() - 1;
                    this.v.j(n2 < 0 ? 0 : n2);
                }
                this.a(this.M, this.F);
                this.a(this.v.r());
                return true;
            }
            case 1111117: {
                ag.b().e(-4561238);
                String[] stringArray = new String[]{((ll)this.E.k).c};
                int n3 = 0;
                while (n3 <= 0) {
                    dc dc4 = this.v.a(stringArray[0]);
                    this.v.a(dc4);
                    ++n3;
                }
                ks.a().c(stringArray);
                this.A();
                com.mg.sq.a.s().a((String)null, (il)null);
                break;
            }
            case 1111112: {
                this.h(1);
                this.d(this.E);
                return true;
            }
            case 1111115: {
                ks.a().e(((lm)this.E.k).a);
                return true;
            }
            case 1111113: {
                lm lm3 = (lm)this.E.k;
                hh hh3 = this;
                if (lm3.k > 0L) {
                    al al4 = lm3.k > go.s ? ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n kh\u00f4ng \u0111\u1ee7 Ken \u0111\u1ec3 \u0111\u1eadp tr\u1ee9ng, B\u1ea1n c\u00f3 mu\u1ed1n n\u1ea1p Ken kh\u00f4ng", "C\u00f3", 113, "Kh\u00f4ng", 114, 1) : ag.b().a("Ch\u00fa \u00fd", "\u0110\u1eadp 1 qu\u1ea3 tr\u1ee9ng b\u1ea1n ph\u1ea3i m\u1ea5t " + lm3.k + " Ken. B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u1eadp kh\u00f4ng?", "C\u00f3", 111, "Kh\u00f4ng", 112, 1);
                    al4.a(hh3);
                    ag.b().a(al4);
                } else {
                    lm lm4 = (lm)hh3.E.k;
                    ks.a().f(lm4.a);
                    hh3.i(lm4.a, 1);
                    com.mg.sq.a.s().a("\u0110\u1ee3i x\u00edu nh\u00e9...", (il)null);
                }
                return true;
            }
            case 1111114: {
                hh hh4 = this;
                al al5 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n mua th\u00eam ng\u0103n ch\u1ee9a \u0111\u1ed3 kh\u00f4ng? Ph\u00ed mua 1 ng\u0103n ch\u1ee9a l\u00e0 " + go.o, "C\u00f3", 118, "Kh\u00f4ng", 119, 1);
                al5.b(123456);
                al5.a(hh4);
                ag.b().a(al5);
                return true;
            }
            case 1111116: {
                Object object;
                if (this.G) {
                    object = ag.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111\u1ed3 cho nh\u00e2n v\u1eadt. B\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt ngay kh\u00f4ng?", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
                    ((am)object).a(this);
                    ((am)object).b(-4561238);
                    ag.b().a((al)object, false);
                    this.A();
                    this.G = false;
                } else {
                    String string = ((ll)this.E.k).c;
                    ag.b().a(false);
                    com.mg.sq.a.s().h(string);
                    return true;
                }
            }
            case 1111118: {
                Object object;
                if (this.G) {
                    object = ag.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111\u1ed3 cho nh\u00e2n v\u1eadt. B\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt ngay kh\u00f4ng?", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
                    ((am)object).a(this);
                    ((am)object).b(-4561238);
                    ag.b().a((al)object, false);
                    this.A();
                    this.G = false;
                } else {
                    Object object2;
                    Object object3 = object2 = this;
                    if (((hh)object2).E.k != null) {
                        object3 = object2;
                        if (((hh)object3).E.j == 1) {
                            object3 = object2;
                            object = (lm)((hh)object3).E.k;
                            object3 = object2;
                            Object object4 = new dc(null, ((lm)object).b(), ((hh)object3).E.j, ((hh)object2).Z);
                            object4 = new gu((dc)object4);
                            ((am)object4).a((bf)object2);
                            ((gu)object4).e(((lm)object).g);
                            object2 = new bd("Xong", 1111121);
                            object3 = object4;
                            ((am)object3).a((az)object2, true);
                            ((am)object4).a(new bd("", 1111121));
                            object2 = new bd("H\u1ee7y", 1111122);
                            object3 = object4;
                            ((am)object3).b((az)object2, true);
                            ((am)object4).b(-1122154);
                            ((gu)object4).j(true);
                            ag.b().a((al)object4, false);
                        } else {
                            object3 = object2;
                            if (((hh)object3).E.j == 0) {
                                object3 = object2;
                                ((hh)object2).c(((hh)object3).E);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void c(dc object) {
        object = new hu((dc)object, "Rao b\u00e1n", 1111119, "H\u1ee7y", 1111120);
        ((am)object).a(this);
        ag.b().a((al)object, false);
    }

    protected final void s() {
    }

    private void C() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.F.length) {
            if (this.F[n3] != null) {
                ++n2;
            }
            ++n3;
        }
        String[] stringArray = new String[n2];
        n2 = 0;
        int n4 = 0;
        while (n4 < this.F.length) {
            if (this.F[n4] != null) {
                ll ll2 = (ll)this.F[n4].k;
                stringArray[n2] = ll2.c;
                ++n2;
            }
            ++n4;
        }
        ks.a().c(stringArray);
        this.G = false;
    }

    private void D() {
        this.X = true;
        if (this.G) {
            al al2 = ag.b().a("Ch\u00fa \u00fd", "Ch\u01b0a c\u1eadp nh\u1eadt \u0111\u1ed3 cho nh\u00e2n v\u1eadt. B\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt ngay kh\u00f4ng?", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
            al2.a(this);
            al2.b(-4561238);
            ag.b().a(al2, false);
            this.A();
            this.G = false;
            return;
        }
        ag.b().a(this.h(), false);
    }

    private void a(Object object) {
        this.H = false;
        this.E = (dc)object;
        this.T = 0;
        this.S = null;
        if (this.E != null) {
            if (this.E.j == 3) {
                if (this.L == 2) {
                    this.a((az)null);
                    az az2 = null;
                    object = this;
                    ((am)object).a(az2, true);
                }
                return;
            }
            object = null;
            if (this.E.j == 0 && this.E.k != null) {
                object = (ll)this.E.k;
                int n2 = 0;
                if (((ll)object).i <= this.M.G) {
                    ++n2;
                }
                if (((ll)object).h == 2 || ((ll)object).h == this.M.f) {
                    ++n2;
                }
                this.H = n2 == 2;
            }
            switch (this.L) {
                case 1: {
                    this.a((ll)object);
                    return;
                }
                case 2: {
                    if (this.v.m()) {
                        this.a(new bd("", 1234));
                        bd bd2 = new bd("B\u00e1n", 1234);
                        object = this;
                        ((am)object).a(bd2, true);
                        return;
                    }
                    this.a((az)null);
                    az az3 = null;
                    object = this;
                    ((am)object).a(az3, true);
                }
            }
            return;
        }
        switch (this.L) {
            case 1: {
                this.a((az)null);
                return;
            }
            case 2: {
                this.a((az)null);
                az az4 = null;
                object = this;
                ((am)object).a(az4, true);
            }
        }
    }

    public final void a(ll object) {
        if (object == null) {
            this.a((az)null);
            az az2 = null;
            object = this;
            ((am)object).a(az2, true);
            return;
        }
        if (this.L != 0) {
            if (((ll)object).b()) {
                if (((ll)object).c()) {
                    this.a(new bd("", 22));
                    bd bd2 = new bd("S\u1eeda ch\u1eefa", 22);
                    object = this;
                    ((am)object).a(bd2, true);
                    return;
                }
            } else {
                this.a((az)null);
                az az3 = null;
                object = this;
                ((am)object).a(az3, true);
            }
        }
    }

    public final void j(int n2) {
        lm lm2 = (lm)this.W.k;
        this.O = false;
        lm2.g = n2;
        this.P.d(0);
        this.P.l(n2);
        if (n2 <= 0) {
            this.v.a(this.W);
        }
        if (lm2.g == 0) {
            this.h(0);
        }
    }

    private void c(ll object) {
        if (((hl)(object = com.mg.sq.a.a((bf)this, new bd("Ch\u1ecdn", 21), new bd("\u0110\u00f3ng", 20), new bd("", 21), (ll)object))).u() <= 0) {
            ((hl)object).a("Hi\u1ec7n t\u1ea1i b\u1ea1n kh\u00f4ng c\u00f2n c\u00e2y b\u00faa n\u00e0o \u0111\u1ec3 s\u1eefa ch\u1eefa! B\u1ea1n c\u00f3 mu\u1ed1n v\u00e0o c\u1eeda h\u00e0ng mua kh\u00f4ng?");
            bd bd2 = new bd("C.H\u00e0ng", 26);
            Object object2 = object;
            ((am)object2).a(bd2, true);
            ((hl)object).a(new bd("", 26));
        }
        ((hl)object).e(2);
        this.A();
    }

    private void d(dc dc2) {
        if (dc2 == null || dc2.k == null) {
            return;
        }
        this.W = dc2;
        if (this.P != null) {
            this.P.l(((lm)dc2.k).g);
        }
    }

    public final dc t() {
        return this.E;
    }

    public final void v() {
        this.v.i(this.v.q());
    }

    public final void j(boolean bl2) {
        this.M.Z = bl2;
        this.a(this.M, this.F);
        this.G = true;
    }

    public final void b(aq aq2, int n2) {
        this.a(this.v.u());
    }

    public final void a(aq object, int n2) {
        if (object == null && n2 > this.Y - 1) {
            object = this;
            object = new dc(this.v.k, null, 3, ((hh)object).Z);
        }
        this.a(object);
    }

    public final void a(aq aq2, int n2, int n3) {
        this.a(aq2);
    }

    public final void w() {
        com.mg.sq.a.s().v();
        if (this.V) {
            com.mg.sq.a.G();
        }
        if (this.X) {
            ag.b().a(this.h(), false);
        }
    }

    public final void b(lm lm2) {
        Object object;
        int n2 = 0;
        a a2 = new a();
        int n3 = 0;
        while (n3 < this.v.s()) {
            if ((((dc)this.v.k((int)n3)).j == 1 || ((dc)this.v.k((int)n3)).j == 2) && (object = (lm)((dc)this.v.k((int)n3)).k) != null && ((ld)object).a == lm2.a) {
                n2 += ((lm)object).g;
                a2.a(this.v.k(n3));
            }
            ++n3;
        }
        n2 += lm2.g;
        n3 = 0;
        while (n3 < a2.d()) {
            this.v.a((dc)a2.b(n3));
            ++n3;
        }
        if (lm2.l <= 0) {
            object = lm2.b();
            lm2.b().g = n2;
            lm lm3 = object;
            object = this;
            dc dc2 = new dc(null, lm3, lm2.e == 3 ? 2 : 1, ((hh)object).Z);
            this.v.a((Object)dc2);
            return;
        }
        n3 = n2 / lm2.l + (n2 % lm2.l > 0 ? 1 : 0);
        int n4 = 0;
        while (n4 < n3) {
            object = lm2.b();
            if (n2 >= lm2.l) {
                ((lm)object).g = lm2.l;
                n2 -= lm2.l;
            } else if (n2 > 0) {
                ((lm)object).g = n2;
                n2 = 0;
            }
            Object object2 = object;
            object = this;
            object = new dc(null, object2, lm2.e == 3 ? 2 : 1, ((hh)object).Z);
            this.v.a(object);
            ++n4;
        }
    }

    public final void b(Graphics graphics) {
        bx.d.c(true);
        bx.d.a(graphics, this.U, this.r.a + this.c, this.r.b + this.d, 0);
        bx.d.c(false);
        pc.b(graphics, this.q.a + this.c, this.q.b + this.d, this.M.g);
        bx.d.a(graphics, "C\u1ea5p: " + this.M.G, this.c + this.f - 14 + this.c, this.r.b + this.d, 2);
        int n2 = 0;
        while (n2 < this.u.length - 1) {
            pc.b(graphics, this.u[n2].a + this.c, this.u[n2].b + this.d, this.u[n2].c, this.u[n2].d, 6647295, 0xFFFFFF, 8369663);
            cw.a(graphics, this.k, n2 * this.p, 0, this.p, this.p, this.u[n2].a + this.c + (this.u[n2].c - this.p) / 2, this.u[n2].b + this.d + (this.u[n2].d - this.p) / 2, 0);
            ++n2;
        }
        int n3 = 0;
        while (n3 < this.F.length) {
            if (this.F[n3] != null) {
                ll ll2 = (ll)this.F[n3].k;
                this.F[n3].a(graphics, this.u[ll2.e].a + this.c, this.u[ll2.e].b + this.d);
            }
            ++n3;
        }
        if (this.D != null) {
            this.D.a(graphics, this.c, this.d);
            this.D.c(true);
        }
        if (this.E != null && !this.I && this.E.k != null && this.E.j == 0) {
            ll ll3 = (ll)this.E.k;
            if (ll3.e < this.u.length) {
                int n4 = this.d;
                int n5 = this.c;
                k k2 = this.u[ll3.e];
                ll3 = graphics;
                ll3.setColor(0xFEFF77);
                ll3.drawRect(k2.a + n5, k2.b + n4, k2.c, k2.d);
                ll3.setColor(16776624);
                ll3.drawRect(k2.a - 1 + n5, k2.b - 1 + n4, k2.c + 2, k2.d + 2);
                ll3.setColor(0xFFFDD3);
                ll3.drawRect(k2.a - 2 + n5, k2.b - 2 + n4, k2.c + 4, k2.d + 4);
            }
        }
        if (!this.v.m()) {
            pc.a(graphics, this.u[this.w], this.c, this.d, this.J);
            switch (this.L) {
                case 1: {
                    if (this.P == null) break;
                    this.P.a(graphics, this.u[this.w].a + this.c, this.u[this.w].b + this.u[this.w].d + this.d);
                }
            }
        }
        if (this.R != null) {
            this.R.a(graphics, 0, 0);
        }
        if (hh.B()) {
            bx.d.a(graphics, String.valueOf(this.v.s()) + "/" + go.n, this.t.a, this.t.b + this.t.d - 16, 2);
        } else {
            bx.d.a(graphics, String.valueOf(this.v.s()) + "/" + go.n, this.t.a, this.t.b - 16, 0);
        }
        if (!this.n && this.S != null) {
            this.S.a(graphics, this.c, this.d);
        }
        pc.c(graphics, this.s.a + this.c, this.s.b + this.d, this.s.c, this.s.d);
        if (this.y != null) {
            this.y.a(graphics, this.s.a + this.c, this.s.b + 5 + this.d);
        }
    }

    public final void a(Graphics graphics) {
        pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, true);
    }

    public final void b(ll object) {
        Image image = this.N.a(mb.a((ll)object) + 98, true);
        ll ll2 = object;
        object = this;
        object = new dc(image, ll2, 0, ((hh)object).Z);
        this.b((dc)object);
        this.a(this.v.r());
        this.v.h(this.v.a() + 1);
        com.mg.sq.a.s().v();
    }

    public final void c(lm lm2) {
        this.b(lm2);
        this.a(this.v.r());
        com.mg.sq.a.s().v();
    }

    public final void b(String string) {
        this.a(this.v.r());
        com.mg.sq.a.s().v();
        com.mg.sq.a.t(string);
    }

    public final int x() {
        return this.Y;
    }

    public final ox y() {
        return this.N;
    }

    public final d z() {
        return this.Z;
    }
}

