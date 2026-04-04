/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oi
extends fb
implements bj,
bk,
bq,
bt {
    ba o;
    private bc s;
    fa[] p;
    fa[][] q;
    public boolean r = false;
    private ex t;
    private String[] u;
    private int v = 0;

    public oi() {
        super(110, 4, "Kho Game");
        this.a(0, 0, z.r, z.s);
        this.u = ca.a("C\u00e0i \u0111\u1eb7t MI\u1ec4N PH\u00cd v\u00f4 s\u1ed1 tr\u00f2 ch\u01a1i v\u00e0 \u1ee9ng d\u1ee5ng c\u1ef1c k\u1ef3 h\u1ea5p d\u1eabn. Ngo\u00e0i ra, b\u1ea1n c\u00f2n c\u00f3 c\u01a1 h\u1ed9i nh\u1eadn ngay c\u00e1c ph\u1ea7n qu\u00e0 GI\u00c1 TR\u1eca t\u1eeb h\u1ec7 th\u1ed1ng.", z.r, ca.d);
        this.t = new ex("Kho Game", -2);
        this.t.a((z.r - 100) / 2, 10 + ca.d.a() + 10 + 10 + ca.d.a() * this.u.length, 100, 20);
        this.s = new bc(0);
        this.s.a(this.c(), ca.d.a() + 10, this.e(), this.f() - ca.d.a() - 10 - be.a);
        this.o = new ba();
        this.o.a(this);
        this.o.a(this);
        this.o.e(true);
        this.s.b(this.o);
        this.a(new be());
        this.a(new ga(-1, 0));
        this.b(new ga(-2, 1));
        this.c(com.mg.sq.a.l);
        this.a(this);
        boolean bl2 = false;
        oi oi2 = this;
        this.v = 0;
        oi2.t.d(true);
        oi2.c(true);
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2 && this.v == 4) {
            this.s.c(true);
        }
    }

    protected final au y() {
        return this;
    }

    public final void u() {
        this.r = false;
        this.c(true);
    }

    public final void x() {
    }

    public final boolean f(int n2) {
        if (n2 == 97 || n2 == 96) {
            return false;
        }
        if (this.v == 0) {
            if (n2 == 95) {
                oi.v();
                return true;
            }
            return false;
        }
        return this.s.f(n2);
    }

    private static void v() {
        com.mg.sq.a.a(null, null);
        dv.a().n();
    }

    public final boolean c(int n2, int n3) {
        if (this.v == 0) {
            if (this.t.h().b(n2, n3)) {
                this.f(95);
                return true;
            }
        } else {
            return this.s.c(n2, n3);
        }
        return super.c(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        if (this.v != 0) {
            return this.s.e(n2, n3);
        }
        return super.e(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        if (this.v != 0) {
            return this.s.f(n2, n3);
        }
        return super.f(n2, n3);
    }

    public final void n() {
        if (this.v != 0) {
            this.s.n();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.v == 0) {
            if (this.c) {
                this.a(graphics);
                ca.d.c(true);
                ca.d.a(graphics, "Gi\u1edbi thi\u1ec7u", z.r >>> 1, 10, 1);
                ca.d.c();
                n2 = 10 + (10 + ca.d.a());
                ca.a(graphics, ca.d, this.u, 0, n2, z.r, z.s, 1);
                this.t.a(graphics, this.c(), this.d());
                this.c(false);
                return;
            }
        } else {
            boolean bl2 = this.s.k();
            n2 = bl2 ? 1 : 0;
            if (bl2) {
                this.a(graphics);
                oz.a(graphics, 4, 20, z.r - 8);
                ca.d.c(true);
                ca.d.a(graphics, "Kho Game", this.e() / 2, 2, 1);
                ca.d.c();
            }
            this.s.a(graphics, this.c(), this.d());
        }
    }

    private void a(Graphics graphics) {
        graphics.setColor(z.af);
        graphics.fillRect(this.c(), this.d(), this.e(), this.f());
        graphics.drawImage(oz.d, this.c() + this.e(), this.d() + this.f() - be.a, 40);
    }

    public final void a(eb[] ebArray) {
        if (ebArray == null) {
            com.mg.sq.a.t();
            return;
        }
        this.v = 4;
        this.p = new fa[ebArray.length];
        this.q = new fa[ebArray.length][];
        int n2 = 0;
        while (n2 < ebArray.length) {
            this.p[n2] = new fa(true, ebArray[n2].d(), String.valueOf(ebArray[n2].b()) + "(" + ebArray[n2].e() + ")", ebArray[n2].b(), ebArray[n2].e(), this.o.e());
            ++n2;
        }
        this.o.q();
        this.o.a(this.p);
        com.mg.sq.a.t();
    }

    public final void a() {
        int n2;
        if (this.p == null) {
            return;
        }
        int n3 = 0;
        int n4 = 0;
        while (n4 < this.p.length) {
            ++n3;
            if (this.p[n4].i && this.q[n4] != null && this.q[n4].length > 0) {
                n3 += this.q[n4].length;
                if (this.q[n4].length < this.p[n4].l) {
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
            if (this.p[n5].i && this.q[n5] != null && this.q[n5].length > 0) {
                n2 = 0;
                while (n2 < this.q[n5].length) {
                    object[n3++] = this.q[n5][n2];
                    ++n2;
                }
                if (this.q[n5].length < this.p[n5].l) {
                    object[n3++] = new dr("Xem th\u00eam", String.valueOf(this.p[n5].k) + '\u001a' + this.q[n5].length);
                }
            }
            ++n5;
        }
        n5 = this.o.s();
        n2 = this.s.r().b;
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
            object.s.k(n5);
            this.c(true);
            return;
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                oi oi2 = this;
                bv bv2 = new bv();
                bv2.a(new ga(0, 2));
                bv2.b(new ga(1, 3));
                bu[] buArray = oi2.v == 0 ? new bu[]{new bu("Kho Game", 6), new bu("\u0110\u00f3ng", 5)} : new bu[]{new bu("\u0110\u00f3ng", 5)};
                if (com.mg.sq.a.k != null) {
                    buArray = com.mg.sq.a.k.a(buArray, buArray.length - 1);
                    buArray = og.b(buArray, buArray.length - 1);
                }
                bv2.a(buArray);
                int n4 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n4, z.s);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(oi2);
                oi2.a(bv2);
                return;
            }
            case 1: {
                this.t();
                return;
            }
            case 0: {
                this.l.f(95);
                return;
            }
            case 5: {
                this.r = true;
                if (this.b == null) break;
                ((fc)this.b).d(this);
                return;
            }
            default: {
                ((fc)this.b).a();
            }
        }
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        bu bu2 = (bu)object;
        switch (n3) {
            case 5: {
                this.r = true;
                if (this.b == null) break;
                ((fc)this.b).d(this);
                break;
            }
            case 6: {
                oi.v();
                break;
            }
            case 11399: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.b(bu2.b());
                break;
            }
            default: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }

    private static void a(long l2, int n2) {
        com.mg.sq.a.a(null, null);
        dv.a().a(l2, n2);
    }

    public final void b(au object, int n2) {
        if (this.o.i(n2) instanceof dr) {
            object = (dr)this.o.i(n2);
            String string = (String)((dr)object).b;
            int n3 = string.indexOf("\u001a");
            long l2 = Long.parseLong(string.substring(0, n3));
            n3 = Integer.parseInt(string.substring(n3 + 1));
            oi.a(l2, n3);
            return;
        }
        object = (fa)this.o.i(n2);
        if (((fa)object).j) {
            n2 = 0;
            while (n2 < this.p.length) {
                if (this.p[n2].equals(object)) {
                    if (this.q[n2] != null) {
                        ((fa)object).i = !((fa)object).i;
                        this.a();
                        return;
                    }
                    oi.a(((fa)object).k, 0);
                }
                ++n2;
            }
            return;
        }
        com.mg.sq.a.a(null, null);
        dv.a().b(((fa)object).k);
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final au a(ba ba2, int n2) {
        if (ba2.i(n2) instanceof dr) {
            return new gk(25);
        }
        return (au)ba2.i(n2);
    }
}

