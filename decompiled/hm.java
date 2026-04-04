/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hm
extends hr
implements bt {
    private ou k;
    private fg o;
    private fg p;
    private dg q;
    private bc r;
    private bc s;
    private n t = null;
    private n u = null;
    private lf v;
    private int w;
    private bd x;
    private bd y;
    private byte z = 0;
    private byte[][] A;
    private ex B;
    private au[] C;
    private int D;
    private String E;
    private Object F = new Object();
    private fv G;
    private int H = 0;
    private lk I;
    private int J;
    private long K;
    private String L;
    private boolean M = false;
    private boolean N = false;
    private n O;
    private String P;
    private cb Q;
    private d R;

    public hm(String string, String string2, ou ou2, d d2) {
        this.E = string;
        this.L = string2;
        if (d2 == null) {
            d2 = new ie(new int[]{0xFF0000, 0xFFFF00});
        }
        this.R = d2;
        this.k = new ou(ou2);
        this.b(241231);
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
        this.O = new n(n4, n5, n2, n3);
        this.t();
        bd bd2 = this.x;
        hm hm2 = this;
        hm2.b(bd2, true);
        this.a((bd)null);
    }

    private void t() {
        int n2 = z.r - 20;
        this.t = new n((z.r - n2) / 2, 140, n2, this.g - 155);
        this.u = new n((z.r - n2) / 2, 30, n2, 44);
        this.o = new fg(false);
        this.o.i = new n(this.t.a, this.t.b, this.t.c, this.t.d);
        this.v = gr.j.a();
        Object object = this.v;
        int n3 = gr.k.length - object.D.length;
        int n4 = 0;
        while (n4 < gr.l.length) {
            n3 = gr.l[n4].l == 1 ? (n3 += gr.l[n4].g) : (gr.l[n4].l > 1 ? (n3 += gr.l[n4].g / gr.l[n4].l + (gr.l[n4].g % gr.l[n4].l > 0 ? 1 : 0)) : ++n3);
            ++n4;
        }
        this.w = n3;
        if (this.w < gr.m) {
            this.w = gr.m;
        }
        this.o.d(this.w, 0);
        this.o.a(this);
        lj[] ljArray = gr.k;
        object = this;
        n4 = object.v.D.length;
        object.o.t();
        int n5 = 0;
        while (n5 < ljArray.length) {
            if (ljArray[n5] != null) {
                boolean bl2 = false;
                if (n4 > 0) {
                    int n6 = 0;
                    while (n6 < object.v.D.length) {
                        if (ljArray[n5].b.equals(object.v.D[n6].b)) {
                            --n4;
                            bl2 = true;
                            break;
                        }
                        ++n6;
                    }
                }
                if (!bl2) {
                    dg dg2 = new dg(object.k.a(lz.a(ljArray[n5]) + 98, true), ljArray[n5], 0, object.R);
                    object.o.a((Object)dg2);
                }
            }
            ++n5;
        }
        this.p = new fg(false);
        this.p.i = new n(this.u.a, this.u.b, this.u.c, this.u.d);
        this.p.d(6, 0);
        this.p.a(this);
        this.p.j = true;
        this.p.d(true);
        this.p.i(0);
        object = new lk[gr.l.length];
        int n7 = 0;
        while (n7 < ((lk[])object).length) {
            object[n7] = gr.l[n7].b();
            ++n7;
        }
        this.a((lk[])object);
        object = this;
        this.A = new byte[4][3];
        object.A[0] = new byte[]{1, -1, 1, -1};
        byte[] byArray = new byte[4];
        byArray[0] = 2;
        byArray[2] = 2;
        object.A[1] = byArray;
        object.A[2] = new byte[]{-1, 1, -1, 1};
        this.s = new bc();
        this.s.a(new n(this.u.a, this.u.b, this.u.c, this.u.d + 2));
        this.s.b(this.p);
        this.s.h(2);
        this.r = new bc();
        this.r.a(new n(this.t.a, this.t.b, this.t.c, this.t.d));
        this.r.b(this.o);
        this.r.h(2);
        this.B = new ex("K\u1ebft h\u1ee3p", 2);
        n7 = ca.d.a("K\u1ebft h\u1ee3p") + 10;
        this.B.a(this.c + (z.r - n7) / 2, this.t.b - 25, n7, 18);
        this.C = new au[]{this.s, this.B, this.r};
        this.Q = new cb(0xFF0000);
        this.x = new ga(1, 3);
        this.y = new ga(3, 2);
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
                        object = new dg(null, object, n3, this.R);
                        this.o.a(object);
                        ++n7;
                    }
                    if (n5 > 0) {
                        lk lk2 = lkArray[n2].b();
                        lkArray[n2].b().g = n5;
                        object = new dg(null, lk2, n3, this.R);
                        this.o.a(object);
                    }
                } else {
                    dg dg2 = new dg(null, lkArray[n2], n3, this.R);
                    this.o.a((Object)dg2);
                }
            }
            ++n2;
        }
    }

    private void v() {
        Object object = this.F;
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
            kq.a().a(this.E, stringArray, nArray, nArray2, this.K);
            com.mg.sq.a.a(null, null);
            return;
        }
    }

    protected final boolean g(int n2) {
        switch (n2) {
            case 4: {
                if (this.q.k == null) break;
                hm hm2 = this;
                if (hm2.q.k != null) {
                    if (hm2.q.j == 0) {
                        kq.a().a(hm2.E, (byte)1, ((lj)hm2.q.k).b);
                    } else {
                        hm2.I = (lk)hm2.q.k;
                        int n3 = 0;
                        int n4 = 0;
                        while (n4 < hm2.p.s()) {
                            lk lk2;
                            if ((((dg)hm2.p.k((int)n4)).j == 1 || ((dg)hm2.p.k((int)n4)).j == 5) && (lk2 = (lk)((dg)hm2.p.k((int)n4)).k) != null && lk2.a == hm2.I.a) {
                                n3 += lk2.g;
                            }
                            ++n4;
                        }
                        kq.a().a(hm2.E, (byte)1, hm2.I.a, n3);
                    }
                }
                hm2.M = true;
                com.mg.sq.a.a(null, null);
                if (this.l == null) break;
                this.z();
                break;
            }
            case 5: {
                gs gs2;
                if (!com.mg.sq.a.q().c(-7524) || (gs2 = (gs)com.mg.sq.a.q().d(-7524)) == null) break;
                this.I = gs2.t();
                int n5 = this.I.g = gs2.u();
                kq.a().a(this.E, (byte)0, this.I.a, n5);
                com.mg.sq.a.a(null, null);
                ak.b().a(-7524, false);
                break;
            }
            case 6: {
                ak.b().a(false);
                break;
            }
            case 2: {
                if (this.J == 0 && !this.N) {
                    hm hm3 = this;
                    ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a \u0111\u1ee7 nguy\u00ean li\u1ec7u. Vui l\u00f2ng th\u1eed l\u1ea1i!!!", "\u0110\u00f3ng", 1, 1);
                    ap2.b(199199);
                    ap2.a(hm3);
                    ak.b().a(ap2, false);
                    break;
                }
                if (this.J != 1 || this.N) break;
                if (gr.p > -1L && this.K > gr.p) {
                    hm hm4 = this;
                    ap ap3 = ak.b().a("Ch\u00fa \u00fd", "V\u01b0\u1ee3t qu\u00e1 s\u1ed1 ti\u1ec1n b\u1ea1n \u0111ang c\u00f3. Vui l\u00f2ng th\u1eed l\u1ea1i!!!", "\u0110\u00f3ng", 1, 1);
                    ap3.b(199199);
                    ap3.a(hm4);
                    ak.b().a(ap3, false);
                    break;
                }
                hm hm5 = this;
                ha ha2 = new ha(String.valueOf(hm5.L) + ". B\u1ea1n c\u00f3 mu\u1ed1n k\u1ebft h\u1ee3p kh\u00f4ng?", "K\u1ebft h\u1ee3p", 7, "Kh\u00f4ng", 1);
                ha2.a(hm5);
                ak.b().a(ha2, false);
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
        Object object = this.F;
        synchronized (object) {
            this.e(true);
            dg dg2 = null;
            int n2 = 0;
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
            this.p.t();
            n2 = 0;
            while (n2 < ljArray.length) {
                if (ljArray[n2] != null) {
                    dg2 = new dg(this.k.a(lz.a(ljArray[n2]) + 98, true), ljArray[n2], 0, this.R);
                    this.p.a((Object)dg2);
                    gr.a(ljArray[n2]);
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < lkArray.length) {
                if (lkArray[n2] != null) {
                    dg2 = new dg(null, lkArray[n2], lkArray[n2].e, this.R);
                    this.p.a((Object)dg2);
                    gr.a(lkArray[n2], lkArray[n2].g);
                }
                ++n2;
            }
            this.P = by2 == 1 ? "K\u1ebft h\u1ee3p th\u00e0nh c\u00f4ng" : "K\u1ebft h\u1ee3p th\u1ea5t b\u1ea1i";
            this.N = true;
            return;
        }
    }

    protected final void e(int n2) {
        switch (n2) {
            case 1: {
                if (this.l != null) {
                    this.z();
                    return;
                }
                ak.b().a(false);
                return;
            }
            case 3: {
                this.c(95);
            }
        }
    }

    protected final void f(int n2) {
        byte by2 = this.z;
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (!this.C[this.z].f(n2)) {
                    int n3 = n2 - 96;
                    hm hm2 = this;
                    if (n3 >= 0 && (n3 = hm2.A[hm2.z][n3]) >= 0) {
                        hm2.z = (byte)n3;
                    }
                }
                if (by2 != this.z) {
                    this.C[this.z].d(true);
                    this.C[by2].d(false);
                    if (this.C[this.z] instanceof bc) {
                        fg fg2 = (fg)((bc)this.C[this.z]).w();
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
                this.G = null;
                return;
            }
            case 95: {
                if (this.C[1].m()) {
                    this.i.d(0, ((ex)this.C[1]).a());
                    return;
                }
                this.C[this.z].f(n2);
                return;
            }
        }
        this.C[this.z].f(n2);
    }

    public final void f(int n2, int n3) {
        this.e(true);
        if (this.l != null) {
            boolean bl2;
            block10: {
                int n4 = n3;
                int n5 = n2;
                bd[] bdArray = this.l.a();
                hm hm2 = this;
                int n6 = 0;
                while (n6 < bdArray.length) {
                    if (bdArray[n6] != null && bdArray[n6].a(n5, n4)) {
                        if (hm2.i != null) {
                            hm2.i.d(-1, bdArray[n6].a());
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
        byte by2 = this.z;
        n n7 = null;
        int n8 = 0;
        while (n8 < this.C.length) {
            n7 = this.C[n8].equals(this.r) ? new n(this.o.i.a + this.O.a, this.o.i.b + this.O.b, this.o.i.c, this.o.i.d) : (this.C[n8].equals(this.s) ? new n(this.p.i.a + this.O.a, this.p.i.b + this.O.b, this.p.i.c, this.p.i.d) : new n(this.C[n8].c() + this.O.a, this.C[n8].d() + this.O.b, this.C[n8].e(), this.C[n8].f()));
            if (n7.a(n2, n3)) {
                if (n8 != by2) {
                    this.z = (byte)n8;
                    this.C[by2].d(false);
                    this.C[this.z].d(true);
                }
                this.C[n8].c(n2 - this.O.a, n3 - this.O.b);
                if (this.C[n8] instanceof ex) {
                    this.d(-1, ((ex)this.C[n8]).a());
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
        this.H = 0;
        this.G = null;
        this.q = (dg)object;
    }

    public final void u() {
        int n2 = 0;
        while (n2 < this.C.length) {
            if (this.C[n2] != null) {
                this.C[n2].n();
                this.C[n2].c(true);
            }
            ++n2;
        }
        if (this.G != null) {
            this.G.i();
        }
        if (this.H < 7) {
            ++this.H;
            if (this.H == 7 && this.q != null && this.q.k != null) {
                Object object = this.q.k;
                hm hm2 = this;
                this.G = new fv(object);
                int n3 = hm2.g - 18;
                hm2.G.a(9, n3, hm2.f - 20, hm2.g / 4);
                hm2.G.c(9, n3);
                hm2.G.a(9, n3 - hm2.G.q() - 7);
            }
        }
        if (this.h) {
            --this.D;
            if (this.D <= 0) {
                this.e(false);
                this.D = 0;
            }
        }
    }

    public final void a(String string, String string2, byte by2, long l2) {
        com.mg.sq.a.t();
        Object object = this.F;
        synchronized (object) {
            this.e(true);
            dg dg2 = null;
            lj lj2 = null;
            this.J = by2;
            this.L = string2;
            this.K = l2;
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
        if (this.M) {
            this.J = by2;
            this.L = object;
            this.K = l2;
            this.p.a(this.q);
            lk lk2 = (lk)this.q.k;
            object = this;
            Object object2 = null;
            int n2 = 0;
            a a2 = new a();
            int n3 = 0;
            while (n3 < ((hm)object).o.s()) {
                if ((((dg)((hm)object).o.k((int)n3)).j == 1 || ((dg)((hm)object).o.k((int)n3)).j == 5) && (object2 = (lk)((dg)((hm)object).o.k((int)n3)).k) != null && ((lb)object2).a == lk2.a) {
                    n2 += ((lk)object2).g;
                    a2.a(((hm)object).o.k(n3));
                }
                ++n3;
            }
            n2 += lk2.g;
            n3 = 0;
            while (n3 < a2.d()) {
                ((hm)object).o.a((dg)a2.b(n3));
                ++n3;
            }
            if (lk2.l <= 0) {
                object2 = lk2.b();
                lk2.b().g = n2;
                dg dg2 = new dg(null, object2, lk2.e == 3 ? 5 : 1, ((hm)object).R);
                ((hm)object).o.a((Object)dg2);
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
                    object2 = new dg(null, object2, lk2.e == 3 ? 5 : 1, ((hm)object).R);
                    ((hm)object).o.a(object2);
                    ++n4;
                }
            }
            this.w();
            this.M = false;
            this.q = null;
            this.a(this.p.r());
            return;
        }
    }

    public final void b(String string, String string2, byte by2, long l2) {
        com.mg.sq.a.t();
        Object object = this.F;
        synchronized (object) {
            this.e(true);
            dg dg2 = null;
            lj lj2 = null;
            this.J = by2;
            this.L = string2;
            this.K = l2;
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

    public final void b(String object, byte by2, long l2) {
        com.mg.sq.a.t();
        if (this.I != null) {
            this.J = by2;
            this.L = object;
            this.K = l2;
            object = null;
            by2 = 0;
            a a2 = new a();
            int n2 = 0;
            while (n2 < this.p.s()) {
                if (((dg)this.p.k((int)n2)).j == 1 && (object = (lk)((dg)this.p.k((int)n2)).k) != null && ((lb)object).a == this.I.a) {
                    by2 = (byte)(by2 + ((lk)object).g);
                    a2.a(this.p.k(n2));
                }
                ++n2;
            }
            by2 = (byte)(by2 + this.I.g);
            n2 = 0;
            while (n2 < a2.d()) {
                this.p.a((dg)a2.b(n2));
                ++n2;
            }
            if (this.I.l <= 0) {
                object = this.I.b();
                this.I.b().g = by2;
                dg dg2 = new dg(null, object, 1, this.R);
                this.p.a((Object)dg2);
            } else {
                n2 = by2 / this.I.l + (by2 % this.I.l > 0 ? 1 : 0);
                int n3 = 0;
                while (n3 < n2) {
                    object = this.I.b();
                    if (by2 >= this.I.l) {
                        ((lk)object).g = this.I.l;
                        by2 = (byte)(by2 - this.I.l);
                    } else if (by2 > 0) {
                        ((lk)object).g = by2;
                        by2 = 0;
                    }
                    object = new dg(null, object, 1, this.R);
                    this.p.a(object);
                    ++n3;
                }
            }
            lk lk2 = this.I;
            object = this;
            int n4 = 0;
            lk lk3 = lk2.b();
            a a3 = new a();
            int n5 = 0;
            while (n5 < ((hm)object).o.s()) {
                lk lk4;
                ((hm)object).q = (dg)((hm)object).o.k(n5);
                if (((hm)object).q != null && ((hm)object).q.k != null && (((hm)object).q.j == 1 || ((hm)object).q.j == 5) && (lk4 = (lk)((hm)object).q.k) != null && lk4.a == lk2.a) {
                    n4 += lk4.g;
                    a3.a(((hm)object).q);
                }
                ++n5;
            }
            lk3.g = n4 - lk2.g;
            n5 = 0;
            while (n5 < a3.d()) {
                ((hm)object).o.a((dg)a3.b(n5));
                ++n5;
            }
            if (lk3.g > 0) {
                super.a(new lk[]{lk3});
            }
            super.w();
            this.a(this.o.r());
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.D = 5;
    }

    public final void b(Graphics graphics) {
        this.Q.a(graphics, "Ph\u00ed k\u1ebft h\u1ee3p: " + l.a(this.K, ",") + "KEN", (z.r - ca.d.a("Ph\u00ed k\u1ebft h\u1ee3p: ")) / 2 - 10, 10, 0);
        int n2 = 0;
        if (!this.N) {
            if (this.L != null) {
                String[] stringArray = ca.a(this.L, z.r - 20);
                int n3 = 0;
                while (n3 < stringArray.length) {
                    this.Q.a(graphics, stringArray[n3], (z.r - ca.d.a(stringArray[n3])) / 2, this.p.d() + this.p.f() + 8 + n2, 0);
                    n2 += this.Q.a();
                    ++n3;
                }
            }
        } else if (this.P != null) {
            this.Q.a(graphics, this.P, (z.r - ca.d.a(this.P)) / 2, this.p.d() + this.p.f() + 8, 0);
        }
        if (this.s != null) {
            this.s.a(graphics, this.c, this.d);
            this.s.c(true);
        }
        this.B.a(graphics, 0, 12);
        if (this.r != null) {
            this.r.a(graphics, this.c, this.d + 12);
            this.r.c(true);
        }
        if (!this.m && this.G != null) {
            this.G.a(graphics, this.c, this.d);
        }
        ca.d.a(graphics, String.valueOf(this.o.s()) + "/" + gr.m, this.t.a, this.t.b - 16, 0);
    }

    private void w() {
        this.o.i(this.o.q());
    }

    public final void a(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
    }

    public final void b(au object, int n2) {
        this.a(object);
        if (this.C[this.z] instanceof bc) {
            n n3 = ((fg)((bc)this.C[this.z]).w()).u();
            object = this;
            if (((hm)object).q != null) {
                Object object2;
                bv bv2 = new bv();
                if (((hm)object).s.m() && !((hm)object).N && ((hm)object).p != null && ((hm)object).q.j != 4) {
                    bv2.a(new bu("B\u1ecf ra", 4));
                }
                if (((hm)object).r.m() && !((hm)object).N && ((hm)object).o != null) {
                    Object object3 = object;
                    int n4 = 0;
                    int n5 = 0;
                    while (n5 < ((hm)object3).p.a()) {
                        if (((hm)object3).p.k(n5) != null) {
                            ++n4;
                        }
                        ++n5;
                    }
                    if (n4 < ((hm)object).p.a()) {
                        object2 = object3 = object;
                        if (((hm)object3).q.k != null) {
                            object2 = object3;
                            if (((hm)object2).q.j == 1) {
                                object2 = object3;
                                lk lk2 = (lk)((hm)object2).q.k;
                                object2 = object3;
                                Object object4 = new dg(null, lk2.b(), ((hm)object2).q.j, ((hm)object3).R);
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
                                if (((hm)object2).q.j == 0) {
                                    object2 = object3;
                                    kq.a().a(((hm)object3).E, (byte)0, ((lj)((hm)object2).q.k).b);
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
                bd bd2 = ((hm)object).x;
                object2 = object;
                ((aq)object2).b(bd2, true);
                bd2 = ((hm)object).y;
                object2 = object;
                ((aq)object2).a(bd2, true);
                bv2.a_(1);
                ((hr)object).m = true;
                ((hr)object).a(bv2, ((hm)object).y, null, ((hm)object).x);
            }
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }
}

