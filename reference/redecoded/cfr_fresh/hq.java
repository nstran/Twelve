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

public final class hq
extends ht
implements bn,
bq {
    private ay k;
    private aw p;
    private dk[] q;
    private lf r;
    private ox s;
    private boolean t = false;
    private ft u;
    private az v;
    private az w;
    private az x;
    private lq y;
    private d z = new if(new int[]{0xFF0000, 0xFFFF00});

    public hq(lq[] object) {
        this.s = new ox(null);
        this.b(241235);
        this.a(0, 0, v.t, v.u);
        this.k = new ay(0);
        this.k.a(this.a(), this.c(), this.i(), this.j() - ba.a);
        this.p = new aw();
        this.p.a(this);
        this.p.a(this);
        this.p.e(true);
        this.k.b(this.p);
        this.r = new lf(999, "\u0110ang b\u00e1n", ((lq[])object).length);
        this.q = new dk[1];
        int n2 = 0;
        while (n2 < this.q.length) {
            this.q[n2] = new dk(this.r);
            ++n2;
        }
        if (0 < this.q.length) {
            this.q[0].a((lq[])object);
            this.q[0].a = true;
            this.q[0].c = ((lq[])object).length;
            this.v();
        }
        this.z = new if(new int[]{0xFF0000, 0xFFFF00});
        this.a(com.mg.sq.a.n);
        this.a(this);
        this.u = new ft(go.s / 2L);
        this.u.a(go.s);
        az az2 = this.v = new gb(1, 0);
        object = this;
        object.a(az2, true);
        this.w = new gb(2, 2);
        this.x = new gb(3, 3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void v() {
        int n2;
        if (this.q == null) {
            return;
        }
        int n3 = 0;
        int n4 = 0;
        while (n4 < this.q.length) {
            ++n3;
            if (this.q[n4].a && !this.q[n4].e()) {
                n3 += this.q[n4].b.length;
                if (this.q[n4].b.length < this.q[n4].d()) {
                    ++n3;
                }
            }
            ++n4;
        }
        Object object = new Object[n3];
        n3 = 0;
        int n5 = 0;
        while (n5 < this.q.length) {
            object[n3++] = this.q[n5];
            if (this.q[n5].a && !this.q[n5].e()) {
                n2 = 0;
                while (n2 < this.q[n5].b.length) {
                    object[n3++] = this.q[n5].b[n2];
                    ++n2;
                }
                if (this.q[n5].b.length < this.q[n5].d()) {
                    object[n3++] = new dp("Xem th\u00eam", this.q[n5]);
                }
            }
            ++n5;
        }
        n5 = this.p.s();
        n2 = this.k.r().b;
        aw aw2 = this.p;
        synchronized (aw2) {
            this.p.q();
            this.p.a((Object[])object);
            this.p.k(n5);
            n5 = n2;
            object = this;
            aq aq2 = object.p.o(object.p.s());
            if (aq2 != null) {
                aq aq3;
                if (object.p.s() < object.p.a() - 1 && (aq3 = object.p.o(object.p.s() + 1)).d() + aq3.f() - n5 > object.p.f()) {
                    n5 = aq3.d() + aq3.f() - object.p.f();
                }
                if (aq2.d() + aq2.f() - n5 > object.p.f()) {
                    n5 = aq2.d() + aq2.f() - object.p.f();
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

    public final void a(lq[] lqArray) {
        com.mg.sq.a.s().v();
        this.q[0] = new dk(new lf(999, "\u0110ang b\u00e1n", lqArray.length));
        this.q[0].a(lqArray);
        this.q[0].a = true;
        this.q[0].c = lqArray.length;
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
        if (this.u != null) {
            this.u.i();
            this.u.b(true);
        }
    }

    public final void a(Graphics graphics) {
        if (this.m != null) {
            this.k.c(true);
        }
        if (this.k.k()) {
            graphics.setColor(v.am);
            graphics.fillRect(0, 0, this.i(), this.j());
            graphics.drawImage(pc.d, this.a() + this.i(), this.c() + this.j() - ba.a, 40);
        }
        this.k.a(graphics, this.a(), this.c());
        this.k.c(true);
        graphics.setColor(v.am);
        if (this.u != null) {
            this.u.a(graphics, 0, 0);
        }
    }

    public final void b(Graphics graphics) {
    }

    public final void b(aq object, int n2) {
        object = this.p.i(n2);
        if (object instanceof dk) {
            object = (dk)object;
            if (((dk)object).a) {
                ((dk)object).a = false;
                this.v();
                return;
            }
            hq.t();
            return;
        }
        if (object instanceof dp) {
            object = (dp)object;
            object = (dk)((dp)object).b;
            n2 = ((dk)object).b == null ? 0 : ((dk)object).b.length;
            ks.a().b(((dk)object).b(), 10, n2);
            com.mg.sq.a.s().a((String)null, (il)null);
            return;
        }
        if (object instanceof lq) {
            Object object2 = (lq)object;
            object = this;
            this.y = object2;
            object2 = new bs();
            Object object3 = null;
            Object object4 = null;
            object3 = new String[]{"Ng\u01b0ng b\u00e1n", "C.Ti\u1ebft", "Gia h\u1ea1n"};
            object4 = new int[]{11111, 11112, 11114};
            br[] brArray = new br[3];
            int n3 = 0;
            while (n3 < 3) {
                brArray[n3] = new br(object3[n3], object4[n3]);
                ++n3;
            }
            ((bs)object2).a(brArray);
            n3 = ((hq)object).p.s();
            object3 = ((hq)object).p.o(n3);
            object4 = ((hq)object).k.r();
            int n4 = (v.t - ((aq)object2).e()) / 2;
            int n5 = ((hq)object).k.d() + ((aq)object3).d() - object4.b;
            if (n5 + ((aq)object2).f() > v.u - ba.a) {
                n5 = v.u - ba.a - ((aq)object2).f();
            }
            ((bs)object2).a_(v.t + ((aq)object2).e(), n5);
            ((bs)object2).d(n4, n5);
            ((aq)object2).a_(1);
            ((bs)object2).a((bf)object);
            ((ht)object).a((bs)object2, ((hq)object).w, com.mg.sq.a.n, ((hq)object).x);
            ((ht)object).n = true;
        }
    }

    public static void t() {
        ks.a().i();
        com.mg.sq.a.s().a((String)null, (il)null);
    }

    public final void a(aq aq2, int n2) {
    }

    public final void a(aq aq2, int n2, int n3) {
    }

    public final aq a(aw object, int n2) {
        if ((object = ((aw)object).i(n2)) == null) {
            return null;
        }
        if (object instanceof dk) {
            return new fi((dk)object);
        }
        if (object instanceof dp) {
            return new gl(37);
        }
        ct.a("[getListCellRenderer]========================");
        Image image = null;
        if (object instanceof lq) {
            object = (lq)object;
            if (((lq)object).e instanceof lm) {
                lm lm2 = (lm)((lq)object).e;
                return new fj(lm2.b, lm2.a, lm2.j, lm2.g, lm2.h, -1, false, null, null, ((lq)object).g);
            }
            if (((lq)object).e instanceof ll) {
                ll ll2 = (ll)((lq)object).e;
                try {
                    image = this.s.a(mb.a(ll2.n), true);
                }
                catch (Throwable throwable) {}
                return new fj(ll2.d, ll2.b, ll2.l, ll2.m, false, image, ll2.f, ll2.j, this.z, null, ((lq)object).g);
            }
        }
        return null;
    }

    private void a(String string) {
        dk dk2 = null;
        int n2 = 0;
        while (n2 < this.p.a()) {
            Object object = this.p.i(n2);
            if (object instanceof dk) {
                dk2 = (dk)object;
            } else if (object instanceof lq) {
                object = (lq)object;
                if (((lq)object).b.equals(string)) {
                    this.p.j(n2);
                    dk2.a(string);
                    return;
                }
            }
            ++n2;
        }
    }

    public final void a(String string, ll[] llArray, lm[] lmArray) {
        this.a(string);
        if (llArray != null && llArray.length > 0) {
            go.a(llArray[0]);
        }
        if (lmArray != null && lmArray.length > 0) {
            go.a(lmArray[0], lmArray[0].g);
        }
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 11111: {
                hq hq2 = this;
                ks.a().h(hq2.y.b);
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 11112: {
                hq hq3 = this;
                if (hq3.y != null) {
                    if (hq3.y.e instanceof ll) {
                        ll ll2 = (ll)hq3.y.e;
                        com.mg.sq.a.a(ll2, hq3, "", -123233, "\u0110\u00f3ng", 5);
                    } else if (hq3.y.e instanceof lm) {
                        com.mg.sq.a.a((lm)hq3.y.e, null);
                    } else if (hq3.y.e instanceof lu) {
                        Object object = (lu)hq3.y.e;
                        object = ag.b().a(((lu)object).a, ((lu)object).b, "\u0110\u00f3ng", 11119, 1);
                        ((am)object).b(1515);
                        ag.b().a((al)object);
                        ((am)object).a(hq3);
                    }
                }
                return true;
            }
            case 11114: {
                hq hq4 = this;
                hq4.a(hq4.y.b);
                ks.a().g(hq4.y.b);
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 11116: {
                hq hq5 = this;
                com.mg.sq.a.s().e(hq5.h());
                return true;
            }
            case 11115: {
                com.mg.sq.a.s().I();
                return true;
            }
            case 11117: {
                com.mg.sq.a.s().l();
                hh hh2 = new hh(null, null);
                ag.b().a(hh2);
                return true;
            }
            case 11118: {
                ag.b().a(241209, false);
                return true;
            }
        }
        return false;
    }

    public final void e(int n2) {
        switch (n2) {
            case 2: {
                if (this.m == null) break;
                this.m.f(95);
                return;
            }
            case 3: {
                this.A();
                return;
            }
            case 1: {
                hq hq2 = this;
                br[] brArray = new br[]{new br("Ch\u1ee3 tr\u1eddi", 11115), new br("\u0110\u00f3ng", 11116)};
                hq2.a(brArray, hq2.w, com.mg.sq.a.n, hq2.x);
                return;
            }
            case 5: {
                com.mg.sq.a.s().a(241212, false);
                return;
            }
            case 8: {
                com.mg.sq.a.s().a(1345779, false);
                return;
            }
            case 13: {
                ag.b().a(false);
                hq hq3 = this;
                if (hq3.y != null) {
                    ks.a().i(hq3.y.b);
                    com.mg.sq.a.s().a((String)null, (il)null);
                }
                return;
            }
            case 12: {
                ag.b().a(false);
                return;
            }
            case 11119: {
                ag.b().e(1515);
            }
        }
    }
}

