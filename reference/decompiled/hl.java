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

public final class hl
extends hr
implements bq,
bt {
    private bc k;
    private ba o;
    private dn[] p;
    private ou q = new ou(null);
    private boolean r = false;
    private fs s;
    private bd t;
    private bd u;
    private bd v;
    private lo w;
    private d x;
    private boolean y;
    private int z;

    public hl(ld[] object) {
        this.b(241233);
        this.a(0, 0, z.r, z.s);
        this.k = new bc(0);
        this.k.a(this.a(), this.c(), this.i(), this.j() - be.a);
        this.o = new ba();
        this.o.a(this);
        this.o.a(this);
        this.o.e(true);
        this.k.b(this.o);
        if (this.x == null) {
            this.x = new ie(new int[]{0xFF0000, 0xFFFF00});
        }
        this.p = new dn[((ld[])object).length];
        int n2 = 0;
        while (n2 < this.p.length) {
            this.p[n2] = new dn(object[n2]);
            ++n2;
        }
        this.t();
        this.a(com.mg.sq.a.l);
        this.a(this);
        this.s = new fs(gr.p / 2L);
        this.s.a(gr.p);
        bd bd2 = this.t = new ga(1, 0);
        object = this;
        object.a(bd2, true);
        this.u = new ga(2, 2);
        this.v = new ga(3, 3);
    }

    private void t() {
        if (this.p == null) {
            return;
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.p.length) {
            ++n2;
            if (this.p[n3].a && !this.p[n3].e()) {
                n2 += this.p[n3].b.length;
                if (this.p[n3].b.length < this.p[n3].d()) {
                    ++n2;
                }
            }
            ++n3;
        }
        Object[] objectArray = new Object[n2];
        n2 = 0;
        int n4 = 0;
        while (n4 < this.p.length) {
            objectArray[n2++] = this.p[n4];
            if (this.p[n4].a && !this.p[n4].e()) {
                int n5 = 0;
                while (n5 < this.p[n4].b.length) {
                    objectArray[n2++] = this.p[n4].b[n5];
                    ++n5;
                }
                if (this.p[n4].b.length < this.p[n4].d()) {
                    objectArray[n2++] = new dr("Xem th\u00eam", this.p[n4]);
                }
            }
            ++n4;
        }
        n4 = this.o.s();
        cw.b("[reloadShop()]=================");
        ba ba2 = this.o;
        synchronized (ba2) {
            this.o.q();
            this.o.a(objectArray);
            this.o.k(n4);
            this.e(true);
            return;
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.k.c(bl2);
    }

    public final void a(int n2, int n3, lo[] loArray) {
        dn dn2;
        int n4 = -1;
        int n5 = 0;
        while (n5 < this.p.length) {
            if (this.p[n5].b() != n2) {
                this.p[n5].a = false;
                this.p[n5].c = 0;
            } else {
                n4 = n5;
            }
            ++n5;
        }
        if (n4 >= 0 && (dn2 = this.p[n4]).b() == n2) {
            dn2.c += loArray.length;
            dn dn3 = dn2;
            dn2.b = null;
            this.o.k(n4);
            this.q.a();
            if (!dn2.a) {
                dn2.a = true;
            }
            dn2.a(loArray);
            dn2.a(n3);
            this.t();
        }
        com.mg.sq.a.t();
    }

    private void a(dn dn2) {
        if (!this.y) {
            if (this.z <= dn2.d()) {
                this.z = this.z + dn2.b.length <= dn2.d() ? (this.z += dn2.b.length) : (this.z += dn2.d() - this.z);
            }
        } else {
            this.z = 0;
        }
        kq.a().b(dn2.b(), 10, this.z);
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
        this.k.n();
        if (this.s != null) {
            this.s.i();
            this.s.b(true);
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
        }
        this.k.a(graphics, this.a(), this.c());
        this.k.c(true);
        graphics.setColor(z.af);
        if (this.s != null) {
            this.s.a(graphics, 0, 0);
        }
    }

    public final void b(Graphics graphics) {
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
            this.y = true;
            this.a((dn)object);
            ((dn)object).c = 0;
            return;
        }
        if (object instanceof dr) {
            object = (dr)object;
            this.y = false;
            this.a((dn)((dr)object).b);
            return;
        }
        if (object instanceof lo) {
            Object object2;
            Object object3 = (String[])object;
            object = this;
            this.w = object3;
            bv bv2 = new bv();
            if (!gr.e.equals(((lo)object3).f)) {
                object3 = new String[]{"Mua", "C.Ti\u1ebft"};
                object2 = new int[]{11111, 11112};
            } else {
                object3 = new String[]{"C.Ti\u1ebft"};
                object2 = new int[]{11112};
            }
            bu[] buArray = new bu[((String[])object3).length];
            int n3 = 0;
            while (n3 < buArray.length) {
                buArray[n3] = new bu(object3[n3], object2[n3]);
                ++n3;
            }
            bv2.a(buArray);
            n3 = ((hl)object).o.s();
            object3 = ((hl)object).o.o(n3);
            object2 = ((hl)object).k.r();
            int n4 = (z.r - bv2.e()) / 2;
            int n5 = ((hl)object).k.d() + ((au)object3).d() - object2.b;
            if (n5 + bv2.f() > z.s - be.a) {
                n5 = z.s - be.a - bv2.f();
            }
            bv2.a_(z.r + bv2.e(), n5);
            bv2.d(n4, n5);
            bv2.a_(1);
            bv2.a((bj)object);
            ((hr)object).a(bv2, ((hl)object).u, com.mg.sq.a.l, ((hl)object).v);
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
                lk lk2 = (lk)((lo)object).e;
                return new fi(lk2.b, lk2.a, lk2.j % 30000, lk2.g, lk2.h, -1, false, null, ((lo)object).f, 0L);
            }
            if (((lo)object).e instanceof lj) {
                lj lj2 = (lj)((lo)object).e;
                try {
                    image = this.q.a(lz.a(lj2.m), true);
                }
                catch (Throwable throwable) {}
                return new fi(lj2.c, lj2.a, lj2.k, lj2.l, false, image, lj2.e, lj2.i, this.x, ((lo)object).f, 0L);
            }
        }
        return null;
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 11111: {
                hl hl2 = this;
                boolean cfr_ignored_0 = hl2.w.e instanceof ls;
                ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n mua m\u00f3n \u0111\u1ed3 n\u00e0y kh\u00f4ng?", "C\u00f3", 13, "Kh\u00f4ng", 12, 1);
                ap2.a(hl2);
                ak.b().a(ap2, false);
                return true;
            }
            case 11112: {
                hl hl3 = this;
                if (hl3.w != null) {
                    if (hl3.w.e instanceof lj) {
                        lj lj2 = (lj)hl3.w.e;
                        com.mg.sq.a.a(lj2, hl3, "", -123233, "\u0110\u00f3ng", 5);
                    } else if (hl3.w.e instanceof lk) {
                        com.mg.sq.a.a((lk)hl3.w.e, null);
                    } else if (hl3.w.e instanceof ls) {
                        Object object = (ls)hl3.w.e;
                        object = ak.b().a(((ls)object).a, ((ls)object).b, "\u0110\u00f3ng", 11119, 1);
                        ((aq)object).b(1515);
                        ak.b().a((ap)object);
                        ((aq)object).a(hl3);
                    }
                }
                return true;
            }
            case 11116: {
                hl hl4 = this;
                com.mg.sq.a.q().e(hl4.h());
                ap ap3 = com.mg.sq.a.q().d(241202);
                if (ap3 != null) {
                    com.mg.sq.a.q().b(ap3, false);
                    ap3 = new hf(null, null);
                    ak.b().a(ap3);
                }
                return true;
            }
            case 11114: {
                ak.b().a(199199, false);
                return true;
            }
            case 11115: {
                kq.a().j();
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 11113: {
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
                hl hl2 = this;
                bu[] buArray = new bu[]{new bu("\u0110ang b\u00e1n", 11115), new bu("Rao b\u00e1n", 11113), new bu("\u0110\u00f3ng", 11116)};
                hl2.a(buArray, hl2.u, com.mg.sq.a.l, hl2.v);
                return;
            }
            case 5: {
                com.mg.sq.a.q().a(241212, false);
                return;
            }
            case 8: {
                com.mg.sq.a.q().a(1345779, false);
                return;
            }
            case 13: {
                ak.b().a(false);
                if (gr.p > -1L && (long)this.w.d > gr.p) {
                    hl hl3 = this;
                    ap ap2 = ak.b().a("Ch\u00fa \u00fd", "V\u01b0\u1ee3t qu\u00e1 s\u1ed1 ti\u1ec1n b\u1ea1n \u0111ang c\u00f3. Vui l\u00f2ng th\u1eed l\u1ea1i!!!", "\u0110\u00f3ng", 11114, 1);
                    ap2.b(199199);
                    ap2.a(hl3);
                    ak.b().a(ap2, false);
                    return;
                }
                hl hl4 = this;
                if (hl4.w != null) {
                    kq.a().i(hl4.w.b);
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

    public final void a(String object, lj[] ljArray, lk[] lkArray) {
        com.mg.sq.a.t();
        if (this.w == null) {
            return;
        }
        String string = object;
        object = this;
        dn dn2 = null;
        int n2 = 0;
        while (n2 < ((hl)object).o.a()) {
            Object object2 = ((hl)object).o.i(n2);
            if (object2 instanceof dn) {
                dn2 = (dn)object2;
            } else if (object2 instanceof lo) {
                object2 = (lo)object2;
                if (((lo)object2).b.equals(string)) {
                    ((hl)object).o.j(n2);
                    dn2.a(string);
                    break;
                }
            }
            ++n2;
        }
        if (ljArray != null && ljArray.length > 0) {
            gr.a(ljArray[0]);
            this.a(ljArray[0].c);
        }
        if (lkArray != null && lkArray.length > 0) {
            gr.a(lkArray[0], lkArray[0].g);
            this.a(String.valueOf(lkArray[0].g) + " " + lkArray[0].b);
        }
    }

    private void a(String object) {
        object = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n v\u1eeba mua th\u00e0nh c\u00f4ng! " + (String)object, "\u0110\u00f3ng", 8, 1);
        ((aq)object).a(this);
        ((aq)object).b(1345779);
        ak.b().a((ap)object, false);
    }
}

