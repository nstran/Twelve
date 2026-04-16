/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class an
extends am {
    private av a;
    private boolean b;
    private bs c;
    private boolean d;
    private boolean k;
    private az[] l;

    public an(int n2) {
        this.b(n2);
        this.b = false;
        this.c = null;
        this.d = false;
        this.k = false;
        this.l = new az[3];
        this.a = null;
    }

    public final void a(bf bf2) {
        super.a(bf2);
    }

    public final boolean a_() {
        return this.b;
    }

    public final void a(boolean bl2) {
        this.b = false;
    }

    public final void a(av av2) {
        this.a = av2;
    }

    public final void a(bs object) {
        Object object2 = null;
        object2 = null;
        object2 = null;
        bs bs2 = object;
        object = this;
        Object object3 = new ao((an)object, 0);
        object2 = ag.c().b(-90001);
        ((az)object2).a(-90001);
        ((az)object2).a((bf)object3);
        bs2.a((az)object2);
        object2 = ag.c().c(-90002);
        ((az)object2).a(-90002);
        ((az)object2).a((bf)object3);
        bs2.b((az)object2);
        object2 = ag.c().a(-90000);
        ((az)object2).a(-90000);
        ((az)object2).a((bf)object3);
        object3 = object2;
        object2 = object;
        ((am)object2).a((az)object3, true);
        ((an)object).c = bs2;
        ((an)object).d = false;
    }

    public final void b(boolean bl2) {
        this.k = true;
    }

    protected final void c(boolean n2) {
        v.c();
        if (this.c != null) {
            this.d = n2;
            if (n2 != 0) {
                n2 = 0;
                while (n2 < this.j.length) {
                    this.l[n2] = this.j[n2];
                    ++n2;
                }
                az az2 = this.c.a()[0];
                an an2 = this;
                an2.a(az2, true);
                this.a((az)null);
                az2 = this.c.a()[2];
                an2 = this;
                an2.b(az2, true);
                int n3 = this.c.e() > this.c.f() ? this.c.e() : this.c.f();
                this.c.a_(-n3, v.u);
                this.c.d(0, v.u - ba.a - this.c.f());
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
        if (this.a != null) {
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

    static bs a(an an2) {
        return an2.c;
    }
}

