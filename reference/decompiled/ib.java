/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ib
extends hr
implements bt {
    private ou k;
    private fg o;
    private fg p;
    private fg q;
    private dg r;
    private bc s;
    private bc t;
    private bc u;
    private n v = null;
    private n w = null;
    private n x = null;
    private lf y;
    private int z;
    private bd A;
    private bd B;
    private byte C = 0;
    private byte[][] D;
    private ex E;
    private au[] F;
    private int G;
    private String H;
    private Object I = new Object();
    private fv J;
    private int K = 0;
    private lk L;
    private int M;
    private long N;
    private String O;
    private boolean P = false;
    private boolean Q = false;
    private n R;
    private String S;
    private cb T;
    private d U;

    public ib(String string, String object, String string2, ou ou2, d d2) {
        this.H = string;
        this.O = string2;
        if (d2 == null) {
            d2 = new ie(new int[]{0xFF0000, 0xFFFF00});
        }
        this.U = d2;
        this.k = new ou(ou2);
        this.b(241232);
        this.a(new be());
        this.a(this);
        int n2 = 240;
        int n3 = 320 - be.a;
        if (com.mg.sq.a.i == 1) {
            n2 = 320;
            n3 = z.s - be.a;
        }
        int n4 = z.r >= n2 ? (z.r - n2) / 2 : 0;
        int n5 = z.s >= n3 ? (z.s - be.a - n3) / 2 : 0;
        this.R = new n(n4, n5, n2, n3);
        this.t();
        this.a((String)object);
        object = this.A;
        ib ib2 = this;
        ib2.b((bd)object, true);
        this.a((bd)null);
    }

    private void t() {
        int n2 = z.r - 20;
        this.v = new n((z.r - n2) / 2, 130, n2, this.g - 155);
        this.x = new n((z.r - n2) / 2 - 4, 30, 44, 44);
        this.w = new n((z.r - n2) / 2 + 46, 30, n2 - 44, 44);
        this.o = new fg(false);
        this.o.i = new n(this.v.a, this.v.b, this.v.c, this.v.d);
        this.y = gr.j.a();
        Object object = this.y;
        int n3 = gr.k.length - object.D.length;
        int n4 = 0;
        while (n4 < gr.l.length) {
            n3 = gr.l[n4].l == 1 ? (n3 += gr.l[n4].g) : (gr.l[n4].l > 1 ? (n3 += gr.l[n4].g / gr.l[n4].l + (gr.l[n4].g % gr.l[n4].l > 0 ? 1 : 0)) : ++n3);
            ++n4;
        }
        this.z = n3;
        if (this.z < gr.m) {
            this.z = gr.m;
        }
        this.o.d(this.z, 0);
        this.o.a(this);
        lj[] ljArray = gr.k;
        object = this;
        n4 = object.y.D.length;
        object.o.t();
        int n5 = 0;
        while (n5 < ljArray.length) {
            if (ljArray[n5] != null) {
                boolean bl2 = false;
                if (n4 > 0) {
                    int n6 = 0;
                    while (n6 < object.y.D.length) {
                        if (ljArray[n5].b.equals(object.y.D[n6].b)) {
                            --n4;
                            bl2 = true;
                            break;
                        }
                        ++n6;
                    }
                }
                if (!bl2) {
                    dg dg2 = new dg(object.k.a(lz.a(ljArray[n5]) + 98, true), ljArray[n5], 0, object.U);
                    object.o.a((Object)dg2);
                }
            }
            ++n5;
        }
        this.p = new fg(false);
        this.p.i = new n(this.w.a, this.w.b, this.w.c, this.w.d);
        this.p.d(5, 0);
        this.p.a(this);
        this.p.j = true;
        this.q = new fg(false);
        this.q = new fg(false);
        this.q.i = new n(this.x.a + 2, this.x.b, this.x.c, this.x.d);
        this.q.d(1, 0);
        this.q.a(this);
        this.q.j = true;
        this.q.d(true);
        this.q.i(0);
        object = new lk[gr.l.length];
        int n7 = 0;
        while (n7 < ((lk[])object).length) {
            object[n7] = gr.l[n7].b();
            ++n7;
        }
        this.a((lk[])object);
        object = this;
        this.D = new byte[4][4];
        object.D[0] = new byte[]{1, -1, 2, -1};
        byte[] byArray = new byte[4];
        byArray[0] = 2;
        byArray[2] = 2;
        byArray[3] = -1;
        object.D[1] = byArray;
        byte[] byArray2 = new byte[4];
        byArray2[0] = 3;
        byArray2[1] = 2;
        byArray2[2] = 3;
        object.D[2] = byArray2;
        object.D[3] = new byte[]{-1, 2, -1, 2};
        this.t = new bc();
        this.t.a(new n(this.w.a, this.w.b, this.w.c, this.w.d + 2));
        this.t.b(this.p);
        this.t.h(2);
        this.s = new bc();
        this.s.a(new n(this.v.a, this.v.b, this.v.c, this.v.d));
        this.s.b(this.o);
        this.s.h(2);
        this.u = new bc();
        this.u.a(new n(this.x.a, this.x.b, this.x.c, this.x.d + 2));
        this.u.b(this.q);
        this.u.h(2);
        this.E = new ex("N\u00e2ng c\u1ea5p", 2);
        n7 = ca.d.a("N\u00e2ng c\u1ea5p") + 10;
        this.E.a(this.c + (z.r - n7) / 2, this.v.b - 25, n7, 18);
        this.F = new au[]{this.u, this.t, this.E, this.s};
        this.T = new cb(0xFF0000);
        this.A = new ga(1, 3);
        this.B = new ga(3, 2);
    }

    private void a(lk[] lkArray) {
        int n2 = 0;
        while (n2 < lkArray.length) {
            int n3;
            int n4 = n3 = lkArray[n2].e == 3 ? 5 : 1;
            if (lkArray[n2].g > 0) {
                if (lkArray[n2].l > 0) {
                    Object object;
                    int n5 = lkArray[n2].g;
                    int n6 = lkArray[n2].g / lkArray[n2].l;
                    int n7 = 0;
                    while (n7 < n6) {
                        object = lkArray[n2].b();
                        lkArray[n2].b().g = lkArray[n2].l;
                        n5 -= lkArray[n2].l;
                        object = new dg(null, object, n3, this.U);
                        this.o.a(object);
                        ++n7;
                    }
                    if (n5 > 0) {
                        lk lk2 = lkArray[n2].b();
                        lkArray[n2].b().g = n5;
                        object = new dg(null, lk2, n3, this.U);
                        this.o.a(object);
                    }
                } else {
                    dg dg2 = new dg(null, lkArray[n2], n3, this.U);
                    this.o.a((Object)dg2);
                }
            }
            ++n2;
        }
    }

    private void v() {
        Object object = this.I;
        synchronized (object) {
            this.e(true);
            int n2 = 0;
            int n3 = 0;
            dg dg2 = null;
            int n4 = 0;
            while (n4 < this.p.s()) {
                dg2 = (dg)this.p.k(n4);
                if (dg2.j == 0) {
                    if (dg2.k != null) {
                        ++n2;
                    }
                } else if (dg2.j == 1) {
                    ++n3;
                }
                ++n4;
            }
            n4 = 0;
            int n5 = 0;
            String[] stringArray = new String[n2];
            int[] nArray = new int[n3];
            int[] nArray2 = new int[n3];
            int n6 = 0;
            while (n6 < this.p.s()) {
                dg2 = (dg)this.p.k(n6);
                if (dg2.j == 0) {
                    if (dg2.k != null) {
                        stringArray[n4] = ((lj)dg2.k).b;
                        ++n4;
                    }
                } else if (dg2.j == 1 && dg2.k != null) {
                    nArray[n5] = ((lk)dg2.k).a;
                    nArray2[n5] = ((lk)dg2.k).g;
                    ++n5;
                }
                ++n6;
            }
            kq.a().b(this.H, stringArray, nArray, nArray2, this.N);
            com.mg.sq.a.a(null, null);
            return;
        }
    }

    protected final boolean g(int n2) {
        switch (n2) {
            case 4: {
                if (this.r.k == null) break;
                ib ib2 = this;
                if (ib2.r.k != null) {
                    if (ib2.r.j == 0) {
                        kq.a().b(ib2.H, (byte)1, ((lj)ib2.r.k).b);
                    } else {
                        ib2.L = (lk)ib2.r.k;
                        int n3 = 0;
                        int n4 = 0;
                        while (n4 < ib2.p.s()) {
                            lk lk2;
                            if ((((dg)ib2.p.k((int)n4)).j == 1 || ((dg)ib2.p.k((int)n4)).j == 5) && (lk2 = (lk)((dg)ib2.p.k((int)n4)).k) != null && lk2.a == ib2.L.a) {
                                n3 += lk2.g;
                            }
                            ++n4;
                        }
                        kq.a().b(ib2.H, (byte)1, ib2.L.a, n3);
                    }
                }
                ib2.P = true;
                com.mg.sq.a.a(null, null);
                if (this.l == null) break;
                this.z();
                break;
            }
            case 5: {
                gs gs2;
                if (!com.mg.sq.a.q().c(-7524) || (gs2 = (gs)com.mg.sq.a.q().d(-7524)) == null) break;
                this.L = gs2.t();
                int n5 = this.L.g = gs2.u();
                kq.a().b(this.H, (byte)0, this.L.a, n5);
                com.mg.sq.a.a(null, null);
                ak.b().a(-7524, false);
                break;
            }
            case 6: {
                ak.b().a(false);
                break;
            }
            case 2: {
                cw.a("Command Upgradeready " + this.M + "processUpgrade" + this.Q);
                if (!this.Q) {
                    if (this.M == 0) {
                        ib ib3 = this;
                        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a \u0111\u1ee7 nguy\u00ean li\u1ec7u. Vui l\u00f2ng th\u1eed l\u1ea1i!!!", "\u0110\u00f3ng", 6, 1);
                        ap2.b(199199);
                        ap2.a(ib3);
                        ak.b().a(ap2, false);
                        break;
                    }
                    if (this.M != 1) break;
                    if (gr.p > -1L && this.N > gr.p) {
                        ib ib4 = this;
                        ap ap3 = ak.b().a("Ch\u00fa \u00fd", "V\u01b0\u1ee3t qu\u00e1 s\u1ed1 ti\u1ec1n b\u1ea1n \u0111ang c\u00f3. Vui l\u00f2ng th\u1eed l\u1ea1i!!!", "\u0110\u00f3ng", 6, 1);
                        ap3.b(199199);
                        ap3.a(ib4);
                        ak.b().a(ap3, false);
                        break;
                    }
                    ib ib5 = this;
                    ha ha2 = new ha(String.valueOf(ib5.O) + ". B\u1ea1n c\u00f3 mu\u1ed1n n\u00e2ng c\u1ea5p kh\u00f4ng?", "N\u00e2ng c\u1ea5p", 7, "Kh\u00f4ng", 6);
                    ha2.a(ib5);
                    ak.b().a(ha2, false);
                    break;
                }
                this.w();
                this.E.a("N\u00e2ng c\u1ea5p");
                this.Q = false;
                this.S = null;
                this.O = null;
                break;
            }
            case 7: {
                ak.b().a(false);
                this.v();
            }
        }
        return false;
    }

    public final void a(lj[] ljArray, lk[] lkArray, byte by2) {
        com.mg.sq.a.t();
        Object object = this.I;
        synchronized (object) {
            this.e(true);
            dg dg2 = null;
            int n2 = 0;
            while (n2 < this.q.s()) {
                dg2 = (dg)this.q.k(n2);
                if (dg2.k != null) {
                    if (dg2.j == 0) {
                        gr.b((lj)dg2.k);
                    } else {
                        gr.a((lk)dg2.k);
                    }
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < this.p.s()) {
                dg2 = (dg)this.p.k(n2);
                if (dg2.k != null) {
                    if (dg2.j == 0) {
                        gr.b((lj)dg2.k);
                    } else {
                        gr.a((lk)dg2.k);
                    }
                }
                ++n2;
            }
            this.q.t();
            this.p.t();
            n2 = 0;
            while (n2 < ljArray.length) {
                if (ljArray[n2] != null) {
                    dg2 = new dg(this.k.a(lz.a(ljArray[n2]) + 98, true), ljArray[n2], 0, this.U);
                    this.q.a((Object)dg2);
                    gr.a(ljArray[n2]);
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < lkArray.length) {
                if (lkArray[n2] != null) {
                    dg2 = new dg(null, lkArray[n2], lkArray[n2].e, this.U);
                    this.q.a((Object)dg2);
                    gr.a(lkArray[n2], lkArray[n2].g);
                }
                ++n2;
            }
        }
        this.S = by2 == 1 ? "N\u00e2ng c\u1ea5p th\u00e0nh c\u00f4ng" : "N\u00e2ng c\u1ea5p th\u1ea5t b\u1ea1i";
        this.Q = true;
        this.E.a("Ti\u1ebfp t\u1ee5c");
    }

    protected final void e(int n2) {
        switch (n2) {
            case 1: {
                if (this.l != null) {
                    this.z();
                    return;
                }
                ak.b().a(false);
                if (com.mg.sq.a.q().c(241202)) {
                    com.mg.sq.a.q().e(241202);
                }
                hf hf2 = new hf(null, null);
                ak.b().a(hf2);
                return;
            }
            case 3: {
                this.c(95);
            }
        }
    }

    protected final void f(int n2) {
        byte by2 = this.C;
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (!this.F[this.C].f(n2)) {
                    int n3 = n2 - 96;
                    ib ib2 = this;
                    if (n3 >= 0 && (n3 = ib2.D[ib2.C][n3]) >= 0) {
                        ib2.C = (byte)n3;
                    }
                }
                if (by2 != this.C) {
                    this.F[this.C].d(true);
                    this.F[by2].d(false);
                    if (this.F[this.C] instanceof bc) {
                        fg fg2 = (fg)((bc)this.F[this.C]).w();
                        fg2.i(0);
                    }
                }
                if (this.o.m()) {
                    this.a(this.o.r());
                    return;
                }
                if (this.p.m()) {
                    this.a(this.p.r());
                    return;
                }
                if (this.q.m()) {
                    this.a(this.q.r());
                    return;
                }
                this.J = null;
                return;
            }
            case 95: {
                if (this.F[2].m()) {
                    this.i.d(0, ((ex)this.F[2]).a());
                    return;
                }
                this.F[this.C].f(n2);
                return;
            }
        }
        this.F[this.C].f(n2);
    }

    public final void f(int n2, int n3) {
        this.e(true);
        if (this.l != null) {
            boolean bl2;
            block10: {
                int n4 = n3;
                int n5 = n2;
                bd[] bdArray = this.l.a();
                ib ib2 = this;
                int n6 = 0;
                while (n6 < bdArray.length) {
                    if (bdArray[n6] != null && bdArray[n6].a(n5, n4)) {
                        if (ib2.i != null) {
                            ib2.i.d(-1, bdArray[n6].a());
                        }
                        bl2 = true;
                        break block10;
                    }
                    ++n6;
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
            if (this.l.c(n2, n3)) {
                return;
            }
            this.z();
            return;
        }
        byte by2 = this.C;
        n n7 = null;
        int n8 = 0;
        while (n8 < this.F.length) {
            n7 = this.F[n8].equals(this.s) ? new n(this.o.i.a + this.R.a, this.o.i.b + this.R.b, this.o.i.c, this.o.i.d) : (this.F[n8].equals(this.t) ? new n(this.p.i.a + this.R.a, this.p.i.b + this.R.b, this.p.i.c, this.p.i.d) : new n(this.F[n8].c() + this.R.a, this.F[n8].d() + this.R.b, this.F[n8].e(), this.F[n8].f()));
            if (n7.a(n2, n3)) {
                if (n8 != by2) {
                    this.C = (byte)n8;
                    this.F[by2].d(false);
                    this.F[this.C].d(true);
                }
                this.F[n8].c(n2 - this.R.a, n3 - this.R.b);
                if (this.F[n8] instanceof ex) {
                    this.d(-1, ((ex)this.F[n8]).a());
                }
                return;
            }
            n8 = (byte)(n8 + 1);
        }
    }

    public final void e(int n2, int n3) {
        if (this.o.m()) {
            this.o.e(n2, n3);
        }
    }

    private void a(Object object) {
        this.K = 0;
        this.J = null;
        this.r = (dg)object;
    }

    public final void u() {
        int n2 = 0;
        while (n2 < this.F.length) {
            if (this.F[n2] != null) {
                this.F[n2].n();
                this.F[n2].c(true);
            }
            ++n2;
        }
        if (this.J != null) {
            this.J.i();
        }
        if (this.K < 7) {
            ++this.K;
            if (this.K == 7 && this.r != null && this.r.k != null) {
                Object object = this.r.k;
                ib ib2 = this;
                this.J = new fv(object);
                int n3 = ib2.g - 18;
                ib2.J.a(9, n3, ib2.f - 20, ib2.g / 4);
                ib2.J.c(9, n3);
                ib2.J.a(9, n3 - ib2.J.q() - 7);
            }
        }
        if (this.h) {
            --this.G;
            if (this.G <= 0) {
                this.e(false);
                this.G = 0;
            }
        }
    }

    public final void a(String string, String string2, byte by2, long l2) {
        com.mg.sq.a.t();
        Object object = this.I;
        synchronized (object) {
            this.e(true);
            dg dg2 = null;
            lj lj2 = null;
            this.M = by2;
            this.O = string2;
            this.N = l2;
            int n2 = 0;
            while (n2 < this.p.s()) {
                dg2 = (dg)this.p.k(n2);
                if (dg2.j == 0 && dg2.k != null) {
                    lj2 = (lj)dg2.k;
                    if (lj2.b.equals(string)) {
                        this.p.a(dg2);
                        this.o.a((Object)dg2);
                        this.a(this.p.r());
                        return;
                    }
                }
                ++n2;
            }
            return;
        }
    }

    public final void a(String object, byte by2, long l2) {
        com.mg.sq.a.t();
        if (this.P) {
            this.M = by2;
            this.O = object;
            this.N = l2;
            this.p.a(this.r);
            lk lk2 = (lk)this.r.k;
            object = this;
            Object object2 = null;
            int n2 = 0;
            a a2 = new a();
            int n3 = 0;
            while (n3 < ((ib)object).o.s()) {
                if ((((dg)((ib)object).o.k((int)n3)).j == 1 || ((dg)((ib)object).o.k((int)n3)).j == 5) && (object2 = (lk)((dg)((ib)object).o.k((int)n3)).k) != null && ((lb)object2).a == lk2.a) {
                    n2 += ((lk)object2).g;
                    a2.a(((ib)object).o.k(n3));
                }
                ++n3;
            }
            n2 += lk2.g;
            n3 = 0;
            while (n3 < a2.d()) {
                ((ib)object).o.a((dg)a2.b(n3));
                ++n3;
            }
            if (lk2.l <= 0) {
                object2 = lk2.b();
                lk2.b().g = n2;
                dg dg2 = new dg(null, object2, lk2.e == 3 ? 5 : 1, ((ib)object).U);
                ((ib)object).o.a((Object)dg2);
            } else {
                n3 = n2 / lk2.l + (n2 % lk2.l > 0 ? 1 : 0);
                int n4 = 0;
                while (n4 < n3) {
                    object2 = lk2.b();
                    if (n2 >= lk2.l) {
                        ((lk)object2).g = lk2.l;
                        n2 -= lk2.l;
                    } else if (n2 > 0) {
                        ((lk)object2).g = n2;
                        n2 = 0;
                    }
                    object2 = new dg(null, object2, lk2.e == 3 ? 5 : 1, ((ib)object).U);
                    ((ib)object).o.a(object2);
                    ++n4;
                }
            }
            this.x();
            this.P = false;
            this.r = null;
            this.a(this.p.r());
            return;
        }
    }

    public final void b(String string, String string2, byte by2, long l2) {
        com.mg.sq.a.t();
        Object object = this.I;
        synchronized (object) {
            this.e(true);
            dg dg2 = null;
            lj lj2 = null;
            this.M = by2;
            this.O = string2;
            this.N = l2;
            int n2 = 0;
            while (n2 < this.o.s()) {
                dg2 = (dg)this.o.k(n2);
                if (dg2.j == 0 && dg2.k != null) {
                    lj2 = (lj)dg2.k;
                    if (lj2.b.equals(string)) {
                        this.o.a(dg2);
                        this.p.a((Object)dg2);
                        this.a(this.o.r());
                        return;
                    }
                }
                ++n2;
            }
            return;
        }
    }

    private void a(String string) {
        dg dg2 = null;
        lj lj2 = null;
        int n2 = 0;
        while (n2 < this.o.s()) {
            dg2 = (dg)this.o.k(n2);
            if (dg2.j == 0 && dg2.k != null) {
                lj2 = (lj)dg2.k;
                if (lj2.b.equals(string)) {
                    this.o.a(dg2);
                    this.q.a((Object)dg2);
                    this.a(this.q.r());
                    return;
                }
            }
            ++n2;
        }
    }

    private void w() {
        dg dg2 = null;
        int n2 = 0;
        while (n2 < this.q.s()) {
            dg2 = (dg)this.q.k(n2);
            kq.a().r(((lj)dg2.k).b);
            ++n2;
        }
    }

    public final void b(String object, byte by2, long l2) {
        com.mg.sq.a.t();
        if (this.L != null) {
            this.M = by2;
            this.O = object;
            this.N = l2;
            object = null;
            by2 = 0;
            a a2 = new a();
            int n2 = 0;
            while (n2 < this.p.s()) {
                if (((dg)this.p.k((int)n2)).j == 1 && (object = (lk)((dg)this.p.k((int)n2)).k) != null && ((lb)object).a == this.L.a) {
                    by2 = (byte)(by2 + ((lk)object).g);
                    a2.a(this.p.k(n2));
                }
                ++n2;
            }
            by2 = (byte)(by2 + this.L.g);
            n2 = 0;
            while (n2 < a2.d()) {
                this.p.a((dg)a2.b(n2));
                ++n2;
            }
            if (this.L.l <= 0) {
                object = this.L.b();
                this.L.b().g = by2;
                dg dg2 = new dg(null, object, 1, this.U);
                this.p.a((Object)dg2);
            } else {
                n2 = by2 / this.L.l + (by2 % this.L.l > 0 ? 1 : 0);
                int n3 = 0;
                while (n3 < n2) {
                    object = this.L.b();
                    if (by2 >= this.L.l) {
                        ((lk)object).g = this.L.l;
                        by2 = (byte)(by2 - this.L.l);
                    } else if (by2 > 0) {
                        ((lk)object).g = by2;
                        by2 = 0;
                    }
                    object = new dg(null, object, 1, this.U);
                    this.p.a(object);
                    ++n3;
                }
            }
            lk lk2 = this.L;
            object = this;
            int n4 = 0;
            lk lk3 = lk2.b();
            a a3 = new a();
            int n5 = 0;
            while (n5 < ((ib)object).o.s()) {
                lk lk4;
                ((ib)object).r = (dg)((ib)object).o.k(n5);
                if (((ib)object).r != null && ((ib)object).r.k != null && (((ib)object).r.j == 1 || ((ib)object).r.j == 5) && (lk4 = (lk)((ib)object).r.k) != null && lk4.a == lk2.a) {
                    n4 += lk4.g;
                    a3.a(((ib)object).r);
                }
                ++n5;
            }
            lk3.g = n4 - lk2.g;
            n5 = 0;
            while (n5 < a3.d()) {
                ((ib)object).o.a((dg)a3.b(n5));
                ++n5;
            }
            if (lk3.g > 0) {
                super.a(new lk[]{lk3});
            }
            super.x();
            this.a(this.o.r());
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.G = 5;
    }

    public final void b(Graphics graphics) {
        this.T.a(graphics, "Ph\u00ed k\u1ebft h\u1ee3p: " + l.a(this.N, ",") + "KEN", (z.r - ca.d.a("Ph\u00ed k\u1ebft h\u1ee3p: ")) / 2 - 10, 10, 0);
        if (!this.Q) {
            if (this.O != null) {
                this.T.a(graphics, this.O, (z.r - ca.d.a(this.O)) / 2, this.p.d() + this.p.f() + 8, 0);
            }
        } else if (this.S != null) {
            this.T.a(graphics, this.S, (z.r - ca.d.a(this.S)) / 2, this.p.d() + this.p.f() + 8, 0);
        }
        if (this.t != null) {
            this.t.a(graphics, this.c, this.d);
            this.t.c(true);
        }
        if (this.u != null) {
            this.u.a(graphics, this.c, this.d);
            this.u.c(true);
        }
        this.E.a(graphics, 0, 0);
        if (this.s != null) {
            this.s.a(graphics, this.c, this.d);
            this.s.c(true);
        }
        if (!this.m && this.J != null) {
            this.J.a(graphics, this.c, this.d);
        }
        ca.d.a(graphics, String.valueOf(this.o.s()) + "/" + gr.m, this.v.a, this.v.b - 16, 0);
    }

    private void x() {
        this.o.i(this.o.q());
    }

    public final void a(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
    }

    public final void b(au object, int n2) {
        this.a(object);
        if (this.F[this.C] instanceof bc) {
            n n3 = ((fg)((bc)this.F[this.C]).w()).u();
            object = this;
            if (((ib)object).r != null) {
                Object object2;
                bv bv2 = new bv();
                if (((ib)object).t.m() && !((ib)object).Q && ((ib)object).p != null && ((ib)object).r.j != 4) {
                    bv2.a(new bu("B\u1ecf ra", 4));
                }
                if (((ib)object).s.m() && !((ib)object).Q && ((ib)object).o != null) {
                    Object object3 = object;
                    int n4 = 0;
                    int n5 = 0;
                    while (n5 < ((ib)object3).p.a()) {
                        if (((ib)object3).p.k(n5) != null) {
                            ++n4;
                        }
                        ++n5;
                    }
                    if (n4 < ((ib)object).p.a()) {
                        object2 = object3 = object;
                        if (((ib)object3).r.k != null) {
                            object2 = object3;
                            if (((ib)object2).r.j == 1) {
                                object2 = object3;
                                lk lk2 = (lk)((ib)object2).r.k;
                                object2 = object3;
                                Object object4 = new dg(null, lk2.b(), ((ib)object2).r.j, ((ib)object3).U);
                                object4 = new gs((dg)object4);
                                ((aq)object4).a((bj)object3);
                                ((gs)object4).e(lk2.g);
                                object3 = new bh("Xong", 5);
                                object2 = object4;
                                ((aq)object2).a((bd)object3, true);
                                ((aq)object4).a(new bh("", 5));
                                object3 = new bh("H\u1ee7y", 6);
                                object2 = object4;
                                ((aq)object2).b((bd)object3, true);
                                ((aq)object4).b(-7524);
                                ((gs)object4).j(true);
                                ak.b().a((ap)object4, false);
                            } else {
                                object2 = object3;
                                if (((ib)object2).r.j == 0) {
                                    object2 = object3;
                                    kq.a().b(((ib)object3).H, (byte)0, ((lj)((ib)object2).r.k).b);
                                    com.mg.sq.a.a(null, null);
                                }
                            }
                        }
                    }
                }
                if (bv2.s() == null || bv2.s().length == 0) {
                    return;
                }
                int n6 = n3.a + ((ap)object).c + (n3.c - bv2.e()) / 2;
                int n7 = n3.b + ((ap)object).d + n3.d;
                if (n7 + bv2.f() > z.s - be.a) {
                    n7 = z.s - be.a - bv2.f();
                }
                bv2.a_(((ap)object).c + ((aq)object).f + bv2.e(), n7);
                bv2.d(n6 < ((ap)object).c ? ((ap)object).c : (n6 + bv2.e() > ((ap)object).c + ((aq)object).f ? ((ap)object).c + ((aq)object).f - bv2.e() : n6), n7);
                bv2.a((bj)object);
                bd bd2 = ((ib)object).A;
                object2 = object;
                ((aq)object2).b(bd2, true);
                bd2 = ((ib)object).B;
                object2 = object;
                ((aq)object2).a(bd2, true);
                bv2.a_(1);
                ((hr)object).m = true;
                ((hr)object).a(bv2, ((ib)object).B, null, ((ib)object).A);
            }
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }
}

