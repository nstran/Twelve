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

public final class ho
extends hr
implements bq,
bt {
    private bc k;
    private ba o;
    private dn[] p;
    private ld q;
    private ou r;
    private boolean s = false;
    private fs t;
    private bd u;
    private bd v;
    private bd w;
    private lo x;
    private d y = new ie(new int[]{0xFF0000, 0xFFFF00});

    public ho(lo[] object) {
        this.r = new ou(null);
        this.b(241235);
        this.a(0, 0, z.r, z.s);
        this.k = new bc(0);
        this.k.a(this.a(), this.c(), this.i(), this.j() - be.a);
        this.o = new ba();
        this.o.a(this);
        this.o.a(this);
        this.o.e(true);
        this.k.b(this.o);
        this.q = new ld(999, "\u0110ang b\u00e1n", ((lo[])object).length);
        this.p = new dn[1];
        int n2 = 0;
        while (n2 < this.p.length) {
            this.p[n2] = new dn(this.q);
            ++n2;
        }
        if (0 < this.p.length) {
            this.p[0].a((lo[])object);
            this.p[0].a = true;
            this.p[0].c = ((lo[])object).length;
            this.v();
        }
        this.y = new ie(new int[]{0xFF0000, 0xFFFF00});
        this.a(com.mg.sq.a.l);
        this.a(this);
        this.t = new fs(gr.p / 2L);
        this.t.a(gr.p);
        bd bd2 = this.u = new ga(1, 0);
        object = this;
        object.a(bd2, true);
        this.v = new ga(2, 2);
        this.w = new ga(3, 3);
    }

    private void v() {
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
            this.o.q();
            this.o.a((Object[])object);
            this.o.k(n5);
            n5 = n2;
            object = this;
            au au2 = object.o.o(object.o.s());
            if (au2 != null) {
                au au3;
                if (object.o.s() < object.o.a() - 1 && (au3 = object.o.o(object.o.s() + 1)).d() + au3.f() - n5 > object.o.f()) {
                    n5 = au3.d() + au3.f() - object.o.f();
                }
                if (au2.d() + au2.f() - n5 > object.o.f()) {
                    n5 = au2.d() + au2.f() - object.o.f();
                }
                object.k.k(n5);
            }
            this.e(true);
            return;
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.k.c(bl2);
    }

    public final void a(lo[] loArray) {
        com.mg.sq.a.t();
        this.p[0] = new dn(new ld(999, "\u0110ang b\u00e1n", loArray.length));
        this.p[0].a(loArray);
        this.p[0].a = true;
        this.p[0].c = loArray.length;
        this.v();
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
        if (this.t != null) {
            this.t.i();
            this.t.b(true);
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
        if (this.t != null) {
            this.t.a(graphics, 0, 0);
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
                this.v();
                return;
            }
            ho.t();
            return;
        }
        if (object instanceof dr) {
            object = (dr)object;
            object = (dn)((dr)object).b;
            n2 = ((dn)object).b == null ? 0 : ((dn)object).b.length;
            kq.a().b(((dn)object).b(), 10, n2);
            com.mg.sq.a.a(null, null);
            return;
        }
        if (object instanceof lo) {
            Object object2 = (lo)object;
            object = this;
            this.x = object2;
            object2 = new bv();
            Object object3 = new String[]{"Ng\u01b0ng b\u00e1n", "C.Ti\u1ebft", "Gia h\u1ea1n"};
            Object object4 = new int[]{11111, 11112, 11114};
            bu[] buArray = new bu[((String[])object3).length];
            int n3 = 0;
            while (n3 < buArray.length) {
                buArray[n3] = new bu(object3[n3], object4[n3]);
                ++n3;
            }
            ((bv)object2).a(buArray);
            n3 = ((ho)object).o.s();
            object3 = ((ho)object).o.o(n3);
            object4 = ((ho)object).k.r();
            int n4 = (z.r - ((au)object2).e()) / 2;
            int n5 = ((ho)object).k.d() + ((au)object3).d() - object4.b;
            if (n5 + ((au)object2).f() > z.s - be.a) {
                n5 = z.s - be.a - ((au)object2).f();
            }
            ((bv)object2).a_(z.r + ((au)object2).e(), n5);
            ((bv)object2).d(n4, n5);
            ((au)object2).a_(1);
            ((bv)object2).a((bj)object);
            ((hr)object).a((bv)object2, ((ho)object).v, com.mg.sq.a.l, ((ho)object).w);
            ((hr)object).m = true;
        }
    }

    public static void t() {
        kq.a().j();
        com.mg.sq.a.a(null, null);
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
        cw.a("[getListCellRenderer]========================");
        Image image = null;
        if (object instanceof lo) {
            object = (lo)object;
            if (((lo)object).e instanceof lk) {
                lk lk2 = (lk)((lo)object).e;
                return new fi(lk2.b, lk2.a, lk2.j % 30000, lk2.g, lk2.h, -1, false, null, null, ((lo)object).g);
            }
            if (((lo)object).e instanceof lj) {
                lj lj2 = (lj)((lo)object).e;
                try {
                    image = this.r.a(lz.a(lj2.m), true);
                }
                catch (Throwable throwable) {}
                return new fi(lj2.c, lj2.a, lj2.k, lj2.l, false, image, lj2.e, lj2.i, this.y, null, ((lo)object).g);
            }
        }
        return null;
    }

    private void a(String string) {
        dn dn2 = null;
        int n2 = 0;
        while (n2 < this.o.a()) {
            Object object = this.o.i(n2);
            if (object instanceof dn) {
                dn2 = (dn)object;
            } else if (object instanceof lo) {
                object = (lo)object;
                if (((lo)object).b.equals(string)) {
                    this.o.j(n2);
                    dn2.a(string);
                    return;
                }
            }
            ++n2;
        }
    }

    public final void a(String string, lj[] ljArray, lk[] lkArray) {
        this.a(string);
        if (ljArray != null && ljArray.length > 0) {
            gr.a(ljArray[0]);
        }
        if (lkArray != null && lkArray.length > 0) {
            gr.a(lkArray[0], lkArray[0].g);
        }
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 11111: {
                ho ho2 = this;
                kq.a().h(ho2.x.b);
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 11112: {
                ho ho3 = this;
                if (ho3.x != null) {
                    if (ho3.x.e instanceof lj) {
                        lj lj2 = (lj)ho3.x.e;
                        com.mg.sq.a.a(lj2, ho3, "", -123233, "\u0110\u00f3ng", 5);
                    } else if (ho3.x.e instanceof lk) {
                        com.mg.sq.a.a((lk)ho3.x.e, null);
                    } else if (ho3.x.e instanceof ls) {
                        Object object = (ls)ho3.x.e;
                        object = ak.b().a(((ls)object).a, ((ls)object).b, "\u0110\u00f3ng", 11119, 1);
                        ((aq)object).b(1515);
                        ak.b().a((ap)object);
                        ((aq)object).a(ho3);
                    }
                }
                return true;
            }
            case 11114: {
                ho ho4 = this;
                ho4.a(ho4.x.b);
                kq.a().g(ho4.x.b);
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 11116: {
                ho ho5 = this;
                com.mg.sq.a.q().e(ho5.h());
                return true;
            }
            case 11115: {
                com.mg.sq.a.G();
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
                ho ho2 = this;
                bu[] buArray = new bu[]{new bu("Ch\u1ee3 tr\u1eddi", 11115), new bu("\u0110\u00f3ng", 11116)};
                ho2.a(buArray, ho2.v, com.mg.sq.a.l, ho2.w);
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
                ho ho3 = this;
                if (ho3.x != null) {
                    kq.a().i(ho3.x.b);
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
}

