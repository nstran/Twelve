/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bc
extends au {
    private int i = 0;
    private int j = 0;
    private g k;
    private au l;
    private bw m;
    private bw n;
    private n o;
    private n p;
    private boolean q = true;
    private boolean r = false;
    private int s = 50;
    private cx t = new cx(-1987, -1987);
    private boolean u = false;
    private long v = -1L;

    public bc() {
        this(0);
    }

    public bc(int n2) {
        this.i = n2;
        ak.b();
        this.m = al.h(2);
        ak.b();
        this.n = al.h(1);
        this.o = new n();
        this.p = new n();
        int n3 = 100;
        int n4 = 100;
        bc bc2 = this;
        super.b(n4, n3);
        this.d(true);
    }

    public final void e(boolean bl2) {
        this.q = false;
    }

    public final void h(int n2) {
        this.j = n2;
    }

    public final void b(au au2) {
        if (this.l != null) {
            this.l.b = null;
        }
        if (au2 != null && au2 instanceof bx) {
            this.l = au2;
            this.l.a(this);
            this.r = true;
            this.x();
        }
    }

    private void x() {
        Object object = (bx)((Object)this.l);
        Object object2 = object = object.v();
        object2 = this;
        Object object3 = ((au)object2).g();
        g g2 = ((bc)object2).a();
        int n2 = ((m)object3).a - ((bc)object2).k.b - g2.d;
        int n3 = ((m)object3).b - ((bc)object2).k.a - g2.c;
        m m2 = new m(n2, n3);
        object3 = this.a();
        if (this.r || this.l.e() != m2.a || this.l.f() != m2.b) {
            this.l.a(((g)object3).b + this.c(), this.d() + ((g)object3).a, m2.a, m2.b);
            this.r = false;
        }
        this.n.b(this.n.q(), m2.a, 0, ((m)object).a);
        this.n.a(this.c() + ((g)object3).b, this.d() + m2.b, this.e() - ((g)object3).b - ((g)object3).d, this.n.f());
        this.m.b(this.m.q(), m2.b, 0, ((m)object).b);
        this.m.a(this.c() + ((g)object3).b + m2.a, this.d() + ((g)object3).a, this.m.e(), this.f() - ((g)object3).a - ((g)object3).c);
        this.o.a(this.n.q(), this.m.q(), this.n.r(), this.m.r());
        this.p.a(this.n.q(), this.m.q(), this.n.r(), this.m.r());
    }

    public final g a() {
        if (this.k == null) {
            this.k = new g(0, 0, 0, 0);
        }
        return this.k;
    }

    public final void b(int n2, int n3) {
        super.b(n2, n3);
    }

    public final void a(g g2) {
        this.k = g2;
        this.x();
    }

    public final n q() {
        return this.o;
    }

    public final n r() {
        return this.p;
    }

    private void y() {
        this.p.a = this.n.q();
        this.p.b = this.m.q();
    }

    public final void d(int n2, int n3) {
        int n4;
        int n5 = n4 = ((bx)((Object)this.l)).w();
        n4 = n3;
        n3 = n2;
        bc bc2 = this;
        if (n4 < 0) {
            n5 = -n5;
        }
        if (n3 == 1) {
            bc2.i(n5);
            return;
        }
        bc2.j(n5);
    }

    public final void i(int n2) {
        if (this.j == 0) {
            if (this.o.a < 0 || this.o.a + this.n.r() > this.n.a()) {
                this.o.a += n2 / 3;
                Math.abs(n2);
                return;
            }
            n2 += this.n.q();
            if (this.n.r() < this.n.a()) {
                if (n2 < 0 && this.o.a <= 0) {
                    this.o.a = n2;
                } else if (n2 + this.o.c > this.n.a() && this.o.a >= this.n.q()) {
                    this.o.a = n2;
                }
                this.n.h(n2);
            } else {
                this.n.h(n2);
                this.o.a = this.n.q();
            }
        } else {
            this.n.h(this.n.q() + n2);
        }
        this.y();
    }

    public final void j(int n2) {
        if (this.j == 0) {
            if (this.o.b < 0 || this.o.b + this.m.r() > this.m.a()) {
                if (Math.abs(n2) > 3) {
                    n2 /= 3;
                }
                if (Math.abs(n2) > 30) {
                    n2 = n2 < 0 ? -30 : 30;
                }
                this.o.b += n2;
                return;
            }
            n2 += this.m.q();
            if (this.m.r() < this.m.a()) {
                if (n2 < 0 && this.o.b <= 0) {
                    this.o.b = n2;
                } else if (n2 + this.o.d > this.m.a() && this.o.b >= this.m.q()) {
                    this.o.b = n2;
                }
                this.m.h(n2);
            } else {
                this.m.h(n2);
                this.o.b = this.m.q();
            }
        } else {
            this.m.h(this.m.q() + n2);
        }
        this.y();
    }

    public final boolean s() {
        return this.m.q() + this.m.r() >= this.m.a();
    }

    public final boolean t() {
        return this.m.q() <= 0;
    }

    public final boolean u() {
        return this.n.q() <= 0;
    }

    public final boolean v() {
        return this.n.q() + this.n.r() >= this.n.a();
    }

    public final void k(int n2) {
        this.m.h(n2);
        this.o.b = this.m.q();
        this.y();
    }

    public final void f(boolean bl2) {
        this.m.h(0);
        this.y();
    }

    public final void g(boolean bl2) {
        this.m.h(this.m.a());
        this.y();
        if (!bl2) {
            this.o.b = this.m.q();
        }
    }

    public final void h(boolean bl2) {
        this.n.h(0);
        this.y();
    }

    public final void i(boolean bl2) {
        this.n.h(this.n.a() - this.n.r());
        this.y();
    }

    public final au w() {
        return this.l;
    }

    public final void n() {
        if (this.l == null) {
            return;
        }
        if (this.v > 0L && System.currentTimeMillis() - this.v > 100L) {
            this.v = -1L;
            if (this.m.q() != this.o.b) {
                this.k(this.o.b);
                this.u = true;
            }
            if (this.n.q() != this.o.a) {
                int n2 = this.o.a;
                bc bc2 = this;
                bc2.n.h(n2);
                bc2.o.a = bc2.n.q();
                bc2.y();
                this.u = true;
            }
            if (this.t.a >= 0 && this.t.b >= 0) {
                this.l.c(this.t.a + this.o.a, this.t.b + this.o.b);
            }
        }
        int n3 = 0;
        if (this.u && (this.o.b < 0 || this.o.b + this.m.r() > this.m.a())) {
            n3 = 1;
        } else {
            if (this.n.q() != this.o.a) {
                n3 = this.n.q() - this.o.a;
                if (Math.abs(n3) > 3) {
                    n3 /= 3;
                }
                if (z.x && Math.abs(n3) > this.s) {
                    n3 = n3 < 0 ? -this.s : this.s;
                }
                this.o.a += n3;
                n3 = 1;
            }
            if (this.m.q() != this.o.b) {
                n3 = this.m.q() - this.o.b;
                if (Math.abs(n3) > 3) {
                    n3 /= 3;
                }
                if (z.x && Math.abs(n3) > this.s) {
                    n3 = n3 < 0 ? -this.s : this.s;
                }
                this.o.b += n3;
                n3 = 1;
            }
        }
        if (n3 != 0) {
            this.c(true);
        }
        this.l.n();
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (this.l != null) {
            this.l.c(bl2);
        }
    }

    public final boolean k() {
        if (this.l != null) {
            return this.l.k();
        }
        return super.k();
    }

    public final boolean m() {
        if (this.l != null) {
            return this.l.m();
        }
        return super.m();
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        if (this.l != null) {
            this.l.d(bl2);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.l != null) {
            this.l.a(graphics, n2, n3);
            if (this.q && this.i != 2) {
                if (this.m.r() < this.m.a()) {
                    this.m.a(graphics, n2, n3);
                }
                if (this.n.r() < this.n.a()) {
                    this.n.a(graphics, n2, n3);
                }
            }
            this.c(false);
        }
    }

    public final boolean f(int n2) {
        if (this.l != null) {
            boolean bl2 = this.l.f(n2);
            if (!bl2) {
                switch (n2) {
                    case 99: {
                        if (this.t()) {
                            return false;
                        }
                        this.d(2, -1);
                        return true;
                    }
                    case 98: {
                        if (this.s()) {
                            return false;
                        }
                        this.d(2, 1);
                        return true;
                    }
                    case 97: {
                        if (this.u()) {
                            return false;
                        }
                        this.d(1, -1);
                        return true;
                    }
                    case 96: {
                        if (this.v()) {
                            return false;
                        }
                        this.d(1, 1);
                        return true;
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public final boolean g(int n2) {
        if (this.l != null) {
            return this.l.g(n2);
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (this.l.h().a(n2, n3)) {
            if (!this.m()) {
                this.d(true);
            }
            this.u = false;
            this.v = System.currentTimeMillis();
            this.t.a = n2;
            this.t.b = n3;
            return true;
        }
        if (this.n.h().a(n2, n3)) {
            return this.n.c(n2, n3);
        }
        if (this.m.h().a(n2, n3)) {
            return this.m.c(n2, n3);
        }
        return false;
    }

    public final boolean f(int n2, int n3) {
        if (this.u) {
            this.u = false;
            if (this.t.a >= 0 && this.t.b >= 0) {
                n2 = (n2 - this.t.a) / 15 * 180;
                n3 = (n3 - this.t.b) / 15 * 180;
                if (n2 != 0) {
                    this.i(-n2);
                }
                if (n3 != 0) {
                    this.j(-n3);
                }
            }
            this.t.b = -1;
            this.t.a = -1;
            return true;
        }
        if (this.v >= 0L && !this.u && this.t.a >= 0 && this.t.b >= 0 && this.l != null && this.l.c(this.t.a + this.o.a, this.t.b + this.o.b)) {
            this.t.a = -1;
            this.t.b = -1;
            this.v = -1L;
            this.u = false;
            return true;
        }
        this.t.a = -1;
        this.t.b = -1;
        this.v = -1L;
        this.u = false;
        this.u = false;
        if (this.n.h().a(n2, n3)) {
            return this.n.f(n2, n3);
        }
        if (this.m.h().a(n2, n3)) {
            return this.m.f(n2, n3);
        }
        return false;
    }

    public final boolean e(int n2, int n3) {
        this.v = -1L;
        if (this.t.a >= 0 && this.t.b >= 0) {
            if (this.u) {
                this.t.a += n2;
                this.t.b += n3;
            }
            this.u = true;
            if (n2 != 0) {
                this.i(-n2);
            }
            if (n3 != 0) {
                this.j(-n3);
            }
            return true;
        }
        return false;
    }

    public final void o() {
        this.x();
    }

    public final void p() {
        this.x();
    }
}

