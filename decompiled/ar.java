/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class ar
extends aq {
    private az a;
    private boolean b;
    private bv c;
    private boolean d;
    private boolean k;
    private bd[] l;

    public ar(int n2) {
        this.b(n2);
        this.b = false;
        this.c = null;
        this.d = false;
        this.k = false;
        this.l = new bd[3];
        this.a = null;
    }

    public final void a(bj bj2) {
        super.a(bj2);
    }

    public final boolean a_() {
        return this.b;
    }

    public final void a(boolean bl2) {
        this.b = false;
    }

    public final void a(az az2) {
        this.a = az2;
    }

    public final void a(bv object) {
        Object object2 = null;
        object2 = null;
        object2 = null;
        bv bv2 = object;
        object = this;
        Object object3 = new as((ar)object);
        object2 = ak.c().b(-90001);
        ((bd)object2).a(-90001);
        ((bd)object2).a((bj)object3);
        bv2.a((bd)object2);
        object2 = ak.c().c(-90002);
        ((bd)object2).a(-90002);
        ((bd)object2).a((bj)object3);
        bv2.b((bd)object2);
        object2 = ak.c().a(-90000);
        ((bd)object2).a(-90000);
        ((bd)object2).a((bj)object3);
        object3 = object2;
        object2 = object;
        ((aq)object2).a((bd)object3, true);
        ((ar)object).c = bv2;
        ((ar)object).d = false;
    }

    public final void b(boolean bl2) {
        this.k = true;
    }

    protected final void c(boolean n2) {
        z.c();
        if (this.c != null) {
            this.d = n2;
            if (n2 != 0) {
                n2 = 0;
                while (n2 < this.j.length) {
                    this.l[n2] = this.j[n2];
                    ++n2;
                }
                bd bd2 = this.c.a()[0];
                ar ar2 = this;
                ar2.a(bd2, true);
                this.a((bd)null);
                bd2 = this.c.a()[2];
                ar2 = this;
                ar2.b(bd2, true);
                int n3 = this.c.e() > this.c.f() ? this.c.e() : this.c.f();
                this.c.a_(-n3, z.s);
                this.c.d(0, z.s - be.a - this.c.f());
                return;
            }
            n2 = 0;
            while (n2 < this.j.length) {
                this.j[n2] = this.l[n2];
                ++n2;
            }
            this.c.t();
            return;
        }
        this.d = false;
    }

    protected void a(int n2) {
    }

    public final void c(int n2) {
        if (this.d) {
            if (this.c != null && !this.c.f(n2)) {
                this.c(false);
            }
            return;
        }
        this.a(n2);
    }

    protected void e(int n2) {
    }

    public final void d(int n2) {
        if (this.d) {
            return;
        }
        this.e(n2);
    }

    protected void e(int n2, int n3) {
    }

    public final void a(int n2, int n3) {
        if (this.d) {
            if (this.c != null && !this.c.c(n2, n3)) {
                this.c(false);
            }
            return;
        }
        this.e(n2, n3);
    }

    protected void f(int n2, int n3) {
    }

    public final void b(int n2, int n3) {
        if (this.d) {
            return;
        }
        this.f(n2, n3);
    }

    protected void g(int n2, int n3) {
    }

    public final void c(int n2, int n3) {
        if (this.d) {
            return;
        }
        this.g(n2, n3);
    }

    protected abstract void c();

    public final void b_() {
        if (this.d) {
            if (this.c == null) {
                this.d = false;
            } else {
                this.c.n();
            }
            if (!this.k) {
                return;
            }
        }
        this.c();
    }

    protected abstract void a(Graphics var1);

    public final void c(Graphics graphics) {
        if (this.a != null) {
            this.a.a(graphics);
        }
        this.a(graphics);
        if (this.d) {
            if (this.c == null) {
                this.d = false;
                return;
            }
            this.c.a(graphics, 0, 0);
        }
    }

    static bv a(ar ar2) {
        return ar2.c;
    }
}

