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

public final class hn
extends ht
implements bn,
bq {
    private ay k;
    private aw p;
    private dk[] q;
    private ox r = new ox(null);
    private boolean s = false;
    private ft t;
    private az u;
    private az v;
    private az w;
    private lq x;
    private d y;
    private boolean z;
    private int A;

    public hn(lf[] object) {
        this.b(241233);
        this.a(0, 0, v.t, v.u);
        this.k = new ay(0);
        this.k.a(this.a(), this.c(), this.i(), this.j() - ba.a);
        this.p = new aw();
        this.p.a(this);
        this.p.a(this);
        this.p.e(true);
        this.k.b(this.p);
        if (this.y == null) {
            this.y = new if(new int[]{0xFF0000, 0xFFFF00});
        }
        this.q = new dk[((lf[])object).length];
        int n2 = 0;
        while (n2 < this.q.length) {
            this.q[n2] = new dk(object[n2]);
            ++n2;
        }
        this.t();
        this.a(com.mg.sq.a.n);
        this.a(this);
        this.t = new ft(go.s / 2L);
        this.t.a(go.s);
        az az2 = this.u = new gb(1, 0);
        object = this;
        object.a(az2, true);
        this.v = new gb(2, 2);
        this.w = new gb(3, 3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void t() {
        if (this.q == null) {
            return;
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.q.length) {
            ++n2;
            if (this.q[n3].a && !this.q[n3].e()) {
                n2 += this.q[n3].b.length;
                if (this.q[n3].b.length < this.q[n3].d()) {
                    ++n2;
                }
            }
            ++n3;
        }
        Object[] objectArray = new Object[n2];
        n2 = 0;
        int n4 = 0;
        while (n4 < this.q.length) {
            objectArray[n2++] = this.q[n4];
            if (this.q[n4].a && !this.q[n4].e()) {
                int n5 = 0;
                while (n5 < this.q[n4].b.length) {
                    objectArray[n2++] = this.q[n4].b[n5];
                    ++n5;
                }
                if (this.q[n4].b.length < this.q[n4].d()) {
                    objectArray[n2++] = new dp("Xem th\u00eam", this.q[n4]);
                }
            }
            ++n4;
        }
        n4 = this.p.s();
        ct.b("[reloadShop()]=================");
        aw aw2 = this.p;
        synchronized (aw2) {
            this.p.q();
            this.p.a(objectArray);
            this.p.k(n4);
            this.e(true);
            return;
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.k.c(bl2);
    }

    public final void a(int n2, int n3, lq[] lqArray) {
        dk dk2;
        int n4 = -1;
        int n5 = 0;
        while (n5 < this.q.length) {
            if (this.q[n5].b() != n2) {
                this.q[n5].a = false;
                this.q[n5].c = 0;
            } else {
                n4 = n5;
            }
            ++n5;
        }
        if (n4 >= 0 && (dk2 = this.q[n4]).b() == n2) {
            dk2.c += lqArray.length;
            dk dk3 = dk2;
            dk2.b = null;
            this.p.k(n4);
            this.r.a();
            if (!dk2.a) {
                dk2.a = true;
            }
            dk2.a(lqArray);
            dk2.a(n3);
            this.t();
        }
        com.mg.sq.a.s().v();
    }

    private void a(dk dk2) {
        if (!this.z) {
            if (this.A <= dk2.d()) {
                this.A = this.A + dk2.b.length <= dk2.d() ? (this.A += dk2.b.length) : (this.A += dk2.d() - this.A);
            }
        } else {
            this.A = 0;
        }
        ks.a().b(dk2.b(), 10, this.A);
        com.mg.sq.a.s().a((String)null, (il)null);
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
        if (this.t != null) {
            this.t.a(graphics, 0, 0);
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
                this.t();
                return;
            }
            this.z = true;
            this.a((dk)object);
            ((dk)object).c = 0;
            return;
        }
        if (object instanceof dp) {
            object = (dp)object;
            this.z = false;
            this.a((dk)((dp)object).b);
            return;
        }
        if (object instanceof lq) {
            Object object2 = (br[])object;
            object = this;
            this.x = object2;
            bs bs2 = new bs();
            Object object3 = null;
            int[] nArray = null;
            if (!go.e.equals(((lq)object2).f)) {
                object3 = new String[]{"Mua", "C.Ti\u1ebft"};
                nArray = new int[]{11111, 11112};
            } else {
                object3 = new String[]{"C.Ti\u1ebft"};
                nArray = new int[]{11112};
            }
            object2 = new br[((String[])object3).length];
            int n3 = 0;
            while (n3 < ((br[])object2).length) {
                object2[n3] = new br(object3[n3], nArray[n3]);
                ++n3;
            }
            bs2.a((br[])object2);
            n3 = ((hn)object).p.s();
            object2 = ((hn)object).p.o(n3);
            object3 = ((hn)object).k.r();
            int n4 = (v.t - bs2.e()) / 2;
            int n5 = ((hn)object).k.d() + ((aq)object2).d() - object3.b;
            if (n5 + bs2.f() > v.u - ba.a) {
                n5 = v.u - ba.a - bs2.f();
            }
            bs2.a_(v.t + bs2.e(), n5);
            bs2.d(n4, n5);
            bs2.a_(1);
            bs2.a((bf)object);
            ((ht)object).a(bs2, ((hn)object).v, com.mg.sq.a.n, ((hn)object).w);
            ((ht)object).n = true;
        }
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
        Image image = null;
        if (object instanceof lq) {
            object = (lq)object;
            if (((lq)object).e instanceof lm) {
                lm lm2 = (lm)((lq)object).e;
                return new fj(lm2.b, lm2.a, lm2.j, lm2.g, lm2.h, -1, false, null, ((lq)object).f, 0L);
            }
            if (((lq)object).e instanceof ll) {
                ll ll2 = (ll)((lq)object).e;
                try {
                    image = this.r.a(mb.a(ll2.n), true);
                }
                catch (Throwable throwable) {}
                return new fj(ll2.d, ll2.b, ll2.l, ll2.m, false, image, ll2.f, ll2.j, this.y, ((lq)object).f, 0L);
            }
        }
        return null;
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 11111: {
                hn hn2 = this;
                boolean cfr_ignored_0 = hn2.x.e instanceof lu;
                al al2 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n mua m\u00f3n \u0111\u1ed3 n\u00e0y kh\u00f4ng?", "C\u00f3", 13, "Kh\u00f4ng", 12, 1);
                al2.a(hn2);
                ag.b().a(al2, false);
                return true;
            }
            case 11112: {
                hn hn3 = this;
                if (hn3.x != null) {
                    if (hn3.x.e instanceof ll) {
                        ll ll2 = (ll)hn3.x.e;
                        com.mg.sq.a.a(ll2, hn3, "", -123233, "\u0110\u00f3ng", 5);
                    } else if (hn3.x.e instanceof lm) {
                        com.mg.sq.a.a((lm)hn3.x.e, null);
                    } else if (hn3.x.e instanceof lu) {
                        Object object = (lu)hn3.x.e;
                        object = ag.b().a(((lu)object).a, ((lu)object).b, "\u0110\u00f3ng", 11119, 1);
                        ((am)object).b(1515);
                        ag.b().a((al)object);
                        ((am)object).a(hn3);
                    }
                }
                return true;
            }
            case 11116: {
                hn hn4 = this;
                com.mg.sq.a.s().e(hn4.h());
                al al3 = com.mg.sq.a.s().d(241202);
                if (al3 != null) {
                    com.mg.sq.a.s().b(al3, false);
                    al3 = new hh(null, null);
                    ag.b().a(al3);
                }
                return true;
            }
            case 11114: {
                ag.b().a(199199, false);
                return true;
            }
            case 11115: {
                ks.a().i();
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 11113: {
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
                hn hn2 = this;
                br[] brArray = new br[]{new br("\u0110ang b\u00e1n", 11115), new br("Rao b\u00e1n", 11113), new br("\u0110\u00f3ng", 11116)};
                hn2.a(brArray, hn2.v, com.mg.sq.a.n, hn2.w);
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
                if (go.s > -1L && (long)this.x.d > go.s) {
                    hn hn3 = this;
                    al al2 = ag.b().a("Ch\u00fa \u00fd", "V\u01b0\u1ee3t qu\u00e1 s\u1ed1 ti\u1ec1n b\u1ea1n \u0111ang c\u00f3. Vui l\u00f2ng th\u1eed l\u1ea1i!!!", "\u0110\u00f3ng", 11114, 1);
                    al2.b(199199);
                    al2.a(hn3);
                    ag.b().a(al2, false);
                    return;
                }
                hn hn4 = this;
                if (hn4.x != null) {
                    ks.a().i(hn4.x.b);
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

    public final void a(String object, ll[] llArray, lm[] lmArray) {
        com.mg.sq.a.s().v();
        if (this.x == null) {
            return;
        }
        String string = object;
        object = this;
        dk dk2 = null;
        int n2 = 0;
        while (n2 < ((hn)object).p.a()) {
            Object object2 = ((hn)object).p.i(n2);
            if (object2 instanceof dk) {
                dk2 = (dk)object2;
            } else if (object2 instanceof lq) {
                object2 = (lq)object2;
                if (((lq)object2).b.equals(string)) {
                    ((hn)object).p.j(n2);
                    dk2.a(string);
                    break;
                }
            }
            ++n2;
        }
        if (llArray != null && llArray.length > 0) {
            go.a(llArray[0]);
            this.a(llArray[0].d);
        }
        if (lmArray != null && lmArray.length > 0) {
            go.a(lmArray[0], lmArray[0].g);
            this.a(String.valueOf(lmArray[0].g) + " " + lmArray[0].b);
        }
    }

    private void a(String object) {
        object = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n v\u1eeba mua th\u00e0nh c\u00f4ng! " + (String)object, "\u0110\u00f3ng", 8, 1);
        ((am)object).a(this);
        ((am)object).b(1345779);
        ag.b().a((al)object, false);
    }
}

