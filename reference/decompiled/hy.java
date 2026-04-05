/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class hy
extends hr
implements bq,
bt {
    private bc k;
    private ba o;
    private dn[] p;
    private lf q;
    private dc r;
    private ou s;
    private lj[] t;
    private boolean u = false;
    private lo v;
    private boolean w;
    private lo[] x;
    private boolean[] y;
    private lo[] z;
    private fs A;
    private bd B;
    private bd C;
    private bd D;
    private d E = new ie(new int[]{0xFF0000, 0xFFFF00});
    private Image F = f.d("/taythuytinh");

    public hy(ld[] object) {
        this.s = new ou(null);
        this.b(241210);
        this.a(0, 0, z.r, z.s);
        this.q = gr.j.a();
        int n2 = 0;
        while (n2 < this.q.D.length) {
            int n3 = 0;
            while (n3 < gr.k.length) {
                if (this.q.D[n2].b.equals(gr.k[n3].b)) {
                    this.q.D[n2] = gr.k[n3];
                }
                ++n3;
            }
            ++n2;
        }
        this.r = new dc(this.q);
        this.k = new bc(0);
        this.k.a(this.a(), this.r.q() + 6, this.i(), this.j() - this.r.q() - 6 - be.a);
        this.o = new ba();
        this.o.a(this);
        this.o.a(this);
        this.o.e(true);
        this.k.b(this.o);
        this.p = new dn[((ld[])object).length];
        n2 = 0;
        while (n2 < this.p.length) {
            this.p[n2] = new dn(object[n2]);
            ++n2;
        }
        this.t();
        this.a(com.mg.sq.a.l);
        this.a(this);
        this.x = new lo[4];
        this.y = new boolean[4];
        this.t = new lj[4];
        this.t[0] = this.q.a(0);
        this.t[1] = this.q.a(1);
        this.t[2] = this.q.a(2);
        this.t[3] = this.q.a(3);
        this.A = new fs(gr.p / 2L);
        this.A.a(gr.p);
        bd bd2 = this.B = new ga(1, 0);
        object = this;
        object.a(bd2, true);
        this.C = new ga(2, 2);
        this.D = new ga(3, 3);
    }

    private void t() {
        int n2;
        if (this.p == null) {
            return;
        }
        int n3 = 0;
        int n4 = 0;
        while (n4 < this.p.length) {
            ++n3;
            if (this.p[n4].a && !this.p[n4].e()) {
                n3 += this.p[n4].b.length;
                if (this.p[n4].b.length < this.p[n4].d()) {
                    ++n3;
                }
            }
            ++n4;
        }
        Object object = new Object[n3];
        n3 = 0;
        int n5 = 0;
        while (n5 < this.p.length) {
            object[n3++] = this.p[n5];
            if (this.p[n5].a && !this.p[n5].e()) {
                n2 = 0;
                while (n2 < this.p[n5].b.length) {
                    object[n3++] = this.p[n5].b[n2];
                    ++n2;
                }
                if (this.p[n5].b.length < this.p[n5].d()) {
                    object[n3++] = new dr("Xem th\u00eam", this.p[n5]);
                }
            }
            ++n5;
        }
        n5 = this.o.s();
        n2 = this.k.r().b;
        ba ba2 = this.o;
        synchronized (ba2) {
            au au2;
            this.o.q();
            this.o.a((Object[])object);
            this.o.k(n5);
            n5 = n2;
            object = this;
            au au3 = object.o.o(object.o.s());
            if (object.o.s() < object.o.a() - 1 && (au2 = object.o.o(object.o.s() + 1)).d() + au2.f() - n5 > object.o.f()) {
                n5 = au2.d() + au2.f() - object.o.f();
            }
            if (au3.d() + au3.f() - n5 > object.o.f()) {
                n5 = au3.d() + au3.f() - object.o.f();
            }
            object.k.k(n5);
            this.e(true);
            return;
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.k.c(bl2);
    }

    public final void a(int n2, lo[] loArray) {
        int n3 = 0;
        while (n3 < this.p.length) {
            if (this.p[n3].b() == n2) {
                this.p[n3].a(loArray);
                this.p[n3].a = true;
                this.t();
                break;
            }
            ++n3;
        }
        com.mg.sq.a.t();
    }

    private static void a(dn dn2) {
        int n2 = dn2.b == null ? 0 : dn2.b.length;
        kq.a().d(dn2.b(), n2);
        com.mg.sq.a.a(null, null);
    }

    public final void f(int n2) {
        this.k.f(n2);
    }

    public final void f(int n2, int n3) {
        this.k.c(n2, n3);
    }

    public final void e(int n2, int n3) {
        this.k.e(n2, n3);
    }

    public final void g(int n2, int n3) {
        this.k.f(n2, n3);
    }

    public final void u() {
        this.r.i();
        this.k.n();
        if (this.A != null) {
            this.A.i();
            this.A.b(true);
        }
    }

    public final void a(Graphics graphics) {
        if (this.l != null) {
            this.k.c(true);
        }
        if (this.k.k()) {
            graphics.setColor(z.af);
            graphics.fillRect(0, 0, this.i(), this.j());
            graphics.drawImage(oz.d, this.a() + this.i(), this.c() + this.j() - be.a, 40);
            oz.a(graphics, 2, this.r.q() + 5, this.i() - 4);
        }
        this.k.a(graphics, this.a(), this.c());
        this.k.c(true);
        graphics.setColor(z.af);
        graphics.fillRect(0, 0, this.r.p(), this.r.q());
        this.r.a(graphics);
        if (this.A != null) {
            this.A.a(graphics, 0, 0);
        }
    }

    public final void b(Graphics graphics) {
    }

    private void a(lj lj2) {
        if (lj2 != null) {
            int n2 = 0;
            while (n2 < this.o.a()) {
                if (this.o.o(n2) instanceof fi) {
                    fi fi2 = (fi)this.o.o(n2);
                    if (fi2.j == lj2.a) {
                        fi2.i = false;
                        fi2.c(true);
                        return;
                    }
                }
                ++n2;
            }
        }
    }

    public final void b(au object, int n2) {
        object = this.o.i(n2);
        if (object instanceof dn) {
            object = (dn)object;
            if (((dn)object).a) {
                ((dn)object).a = false;
                this.t();
                return;
            }
            if (((dn)object).d() <= 0 || ((dn)object).b != null && ((dn)object).b.length > 0) {
                ((dn)object).a = true;
                this.t();
                return;
            }
            hy.a((dn)object);
            return;
        }
        if (object instanceof dr) {
            object = (dr)object;
            hy.a((dn)((dr)object).b);
            return;
        }
        if (object instanceof lo) {
            Object object2 = (lo)object;
            object = this;
            this.v = object2;
            bv bv2 = new bv();
            Object object3 = null;
            int[] nArray = null;
            if (((lo)object2).e instanceof lj) {
                object2 = (lj)((lo)object2).e;
                if (((lj)object2).g == 2 || ((lj)object2).g == ((hy)object).q.f) {
                    if (((hy)object).x[((lj)object2).d] != null && ((hy)object).x[((lj)object2).d].e.equals(object2)) {
                        object3 = new String[]{"C\u1edfi ra", "Mua", "C.Ti\u1ebft"};
                        nArray = new int[]{11113, 11111, 11112};
                    } else {
                        object3 = new String[]{"M\u1eb7c th\u1eed", "Mua", "C.Ti\u1ebft"};
                        nArray = new int[]{11114, 11111, 11112};
                    }
                }
            }
            if (object3 == null) {
                object3 = new String[]{"Mua", "C.Ti\u1ebft"};
                nArray = new int[]{11111, 11112};
            }
            object2 = new bu[(object3).length];
            int n3 = 0;
            while (n3 < ((bu[])object2).length) {
                object2[n3] = new bu(object3[n3], (int)nArray[n3]);
                ++n3;
            }
            bv2.a((bu[])object2);
            n3 = ((hy)object).o.s();
            object2 = ((hy)object).o.o(n3);
            object3 = ((hy)object).k.r();
            int n4 = (z.r - bv2.e()) / 2;
            int n5 = ((hy)object).k.d() + ((au)object2).d() - object3.b;
            if (n5 + bv2.f() > z.s - be.a) {
                n5 = z.s - be.a - bv2.f();
            }
            bv2.a_(z.r + bv2.e(), n5);
            bv2.d(n4, n5);
            bv2.a_(1);
            bv2.a((bj)object);
            ((hr)object).a(bv2, ((hy)object).C, com.mg.sq.a.l, ((hy)object).D);
            ((hr)object).m = true;
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) == null) {
            return null;
        }
        if (object instanceof dn) {
            return new fh((dn)object);
        }
        if (object instanceof dr) {
            return new gk(37);
        }
        Image image = null;
        if (object instanceof lo) {
            object = (lo)object;
            if (((lo)object).e instanceof lk) {
                object = (lk)((lo)object).e;
                return new fi(((lb)object).b, ((lb)object).a, ((lk)object).j % 30000, ((lk)object).g, ((lk)object).h, -1, false, null, null, 0L);
            }
            if (((lo)object).e instanceof ls) {
                object = (ls)((lo)object).e;
                return new fi(((ls)object).a, ((ls)object).d, ((ls)object).c, this.F);
            }
            if (((lo)object).e instanceof lj) {
                object = (lj)((lo)object).e;
                boolean bl2 = false;
                if (this.x[((lj)object).d] != null && this.x[((lj)object).d].e.equals(object)) {
                    bl2 = true;
                }
                try {
                    image = this.s.a(lz.a(((lj)object).m), true);
                }
                catch (Throwable throwable) {}
                return new fi(((lj)object).c, ((lj)object).a, ((lj)object).k, ((lj)object).l, bl2, image, ((lj)object).e, ((lj)object).i, this.E, null, 0L);
            }
        }
        return null;
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 11111: {
                hy hy2 = this;
                boolean cfr_ignored_0 = hy2.v.e instanceof ls;
                ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n mua m\u00f3n \u0111\u1ed3 n\u00e0y kh\u00f4ng?", "C\u00f3", 13, "Kh\u00f4ng", 12, 1);
                ap2.a(hy2);
                ak.b().a(ap2, false);
                return true;
            }
            case 11112: {
                hy hy3 = this;
                if (hy3.v != null) {
                    if (hy3.v.e instanceof lj) {
                        lj lj2 = (lj)hy3.v.e;
                        ((lj)hy3.v.e).n = lj2.o == 0 ? -1 : lj2.o;
                        com.mg.sq.a.a(lj2, hy3, "", -123233, "\u0110\u00f3ng", 5);
                    } else if (hy3.v.e instanceof lk) {
                        com.mg.sq.a.a((lk)hy3.v.e, null);
                    } else if (hy3.v.e instanceof ls) {
                        ls ls2 = (ls)hy3.v.e;
                        ap ap3 = ak.b().a(ls2.a, ls2.b, "\u0110\u00f3ng", 11119, 1);
                        ap3.b(1515);
                        ak.b().a(ap3);
                        ap3.a(hy3);
                    }
                }
                return true;
            }
            case 11113: {
                hy hy4 = this;
                if (hy4.v != null && hy4.v.e instanceof lj) {
                    lj lj3 = (lj)hy4.v.e;
                    fi fi2 = (fi)hy4.o.o(hy4.o.s());
                    ((fi)hy4.o.o(hy4.o.s())).i = false;
                    hy4.x[lj3.d] = null;
                    hy4.y[lj3.d] = false;
                    if (hy4.t[lj3.d] == null) {
                        hy4.r.b(lj3);
                    } else {
                        hy4.r.a(hy4.t[lj3.d]);
                    }
                }
                return true;
            }
            case 11114: {
                hy hy5 = this;
                if (hy5.v != null && hy5.v.e instanceof lj) {
                    lj lj4 = (lj)hy5.v.e;
                    Object object = hy5.x[lj4.d] != null ? (lj)hy5.x[lj4.d].e : null;
                    hy5.a((lj)object);
                    object = (fi)hy5.o.o(hy5.o.s());
                    ((fi)hy5.o.o(hy5.o.s())).i = true;
                    hy5.x[lj4.d] = hy5.v;
                    hy5.y[lj4.d] = true;
                    hy5.r.a(lj4);
                }
                return true;
            }
            case 11115: {
                a a2 = new a();
                int n3 = 0;
                while (n3 < this.x.length) {
                    if (this.x[n3] != null & this.y[n3]) {
                        a2.a(this.x[n3]);
                    }
                    ++n3;
                }
                if (a2.d() == 0) {
                    this.v();
                } else {
                    lo[] loArray = new lo[a2.d()];
                    int n4 = 0;
                    while (n4 < loArray.length) {
                        loArray[n4] = (lo)a2.b(n4);
                        ++n4;
                    }
                    this.a(loArray);
                }
                return true;
            }
            case 11116: {
                int n5 = 0;
                while (n5 < this.x.length) {
                    if (this.x[n5] != null && this.y[n5]) {
                        hy hy6 = this;
                        ap ap4 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n \u0111\u00e3 ch\u1ecdn m\u1ed9t s\u1ed1 m\u00f3n \u0111\u1ed3. B\u1ea1n c\u00f3 mu\u1ed1n mua kh\u00f4ng?", "C\u00f3", 11, "\u0110\u00f3ng", 8, 1);
                        ap4.a(hy6);
                        ap4.b(1345779);
                        ak.b().a(ap4, false);
                        this.u = true;
                        return true;
                    }
                    ++n5;
                }
                this.w();
                return true;
            }
            case 11117: {
                com.mg.sq.a.q().k();
                hf hf2 = new hf(null, null);
                ak.b().a(hf2);
                return true;
            }
            case 11118: {
                ak.b().a(241209, false);
                return true;
            }
        }
        return false;
    }

    public final void e(int n2) {
        switch (n2) {
            case 2: {
                if (this.l == null) break;
                this.l.f(95);
                return;
            }
            case 3: {
                this.z();
                return;
            }
            case 1: {
                hy hy2 = this;
                bu[] buArray = new bu[]{new bu("Gi\u1ecf h\u00e0ng", 11115), new bu("\u0110\u00f3ng", 11116)};
                hy2.a(buArray, hy2.C, com.mg.sq.a.l, hy2.D);
                return;
            }
            case 5: {
                com.mg.sq.a.q().a(241212, false);
                return;
            }
            case 8: {
                com.mg.sq.a.q().a(1345779, false);
                if (!this.u) break;
                this.w();
                return;
            }
            case 9: {
                if (gr.b()) {
                    com.mg.sq.a.a((bj)this, "H\u00e0nh Trang", 11117, "\u0110\u00f3ng", 11118);
                    return;
                }
                this.w = true;
                com.mg.sq.a.q().a(154896, false);
                int[] nArray = new int[this.z.length];
                int n3 = 0;
                while (n3 < nArray.length) {
                    nArray[n3] = this.z[n3].a;
                    ++n3;
                }
                kq.a().a(nArray);
                com.mg.sq.a.a(null, null);
                return;
            }
            case 10: {
                com.mg.sq.a.q().a(154896, false);
                if (!this.u) break;
                this.w();
                return;
            }
            case 11: {
                com.mg.sq.a.q().a(1345779, false);
                a a2 = new a();
                int n4 = 0;
                while (n4 < this.x.length) {
                    if (this.x[n4] != null & this.y[n4]) {
                        a2.a(this.x[n4]);
                    }
                    ++n4;
                }
                if (a2.d() == 0) {
                    this.v();
                    return;
                }
                lo[] loArray = new lo[a2.d()];
                int n5 = 0;
                while (n5 < loArray.length) {
                    loArray[n5] = (lo)a2.b(n5);
                    ++n5;
                }
                this.a(loArray);
                return;
            }
            case 13: {
                ak.b().a(false);
                hy hy3 = this;
                this.w = false;
                if (hy3.v != null) {
                    kq.a().a(new int[]{hy3.v.a});
                    com.mg.sq.a.a(null, null);
                }
                return;
            }
            case 12: {
                ak.b().a(false);
                return;
            }
            case 11119: {
                ak.b().e(1515);
            }
        }
    }

    public final void a(String[] stringArray, int[] nArray) {
        com.mg.sq.a.t();
        if (this.w) {
            lj lj2 = null;
            int n2 = 0;
            while (n2 < this.x.length) {
                int n3 = 0;
                while (n3 < nArray.length) {
                    if (this.x[n2] != null && this.x[n2].a == nArray[n3]) {
                        this.y[n2] = false;
                        lj2 = ((lj)this.x[n2].e).d();
                        ((lj)this.x[n2].e).d().n = lj2.o == 0 ? -1 : lj2.o;
                        lj2.b = stringArray[n3];
                        gr.a(lj2);
                        this.a((lj)this.x[n2].e);
                        break;
                    }
                    ++n3;
                }
                ++n2;
            }
            if (this.u) {
                this.w();
                return;
            }
        } else {
            if (this.v == null) {
                return;
            }
            if (this.v.e instanceof lj) {
                lj lj3 = ((lj)this.v.e).d();
                ((lj)this.v.e).d().n = lj3.o == 0 ? -1 : lj3.o;
                lj3.b = stringArray[0];
                gr.a(lj3);
                int n4 = 0;
                while (n4 < nArray.length) {
                    int n5 = 0;
                    while (n5 < this.x.length) {
                        if (this.x[n5] != null && this.x[n5].a == nArray[n4]) {
                            this.y[n5] = false;
                            this.a((lj)this.x[n5].e);
                            break;
                        }
                        ++n5;
                    }
                    ++n4;
                }
                this.a(lj3.c);
            }
            return;
        }
    }

    public final void a(int[] nArray, int[] nArray2) {
        if (this.v == null) {
            return;
        }
        if (this.v.e instanceof lk) {
            lk lk2 = ((lk)this.v.e).b();
            if (lk2.a == nArray[0]) {
                gr.a(lk2, nArray2[0]);
                this.a(String.valueOf(nArray2[0]) + " " + lk2.b);
            }
        }
        com.mg.sq.a.t();
        if (this.u) {
            this.w();
        }
    }

    private void v() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Gi\u1ecf h\u00e0ng tr\u1ed1ng. B\u1ea1n ch\u01b0a ch\u1ecdn m\u00f3n \u0111\u1ed3 n\u00e0o", "\u0110\u00f3ng", 8, 1);
        ap2.a(this);
        ap2.b(1345779);
        ak.b().a(ap2, false);
    }

    private void a(String object) {
        object = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n v\u1eeba mua th\u00e0nh c\u00f4ng! " + (String)object, "\u0110\u00f3ng", 8, 1);
        ((aq)object).a(this);
        ((aq)object).b(1345779);
        ak.b().a((ap)object, false);
    }

    private void a(lo[] object) {
        this.z = object;
        object = new gv(this.z);
        bh bh2 = new bh("Mua", 9);
        Object object2 = object;
        ((aq)object).a(bh2, true);
        bh2 = new bh("\u0110\u00f3ng", 10);
        object2 = object;
        ((aq)object2).b(bh2, true);
        ((aq)object).a(this);
        ((aq)object).b(154896);
        ak.b().a((ap)object, false);
    }

    public final void a(long l2) {
        if (this.A != null) {
            this.A.a(l2);
        }
    }

    private void w() {
        com.mg.sq.a.q().e(this.h());
        ap ap2 = com.mg.sq.a.q().d(241202);
        if (ap2 != null) {
            com.mg.sq.a.q().b(ap2, false);
            ap2 = new hf(null, null);
            ak.b().a(ap2);
        }
    }
}

