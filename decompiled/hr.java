/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class hr
extends ap
implements bj {
    protected bv l;
    protected boolean m = false;
    private n k = new n();
    bd[] n = new bd[3];

    public hr() {
        super(1);
    }

    public final void d(int n2, int n3) {
        if (this.g(n3)) {
            this.z();
            return;
        }
        this.e(n3);
    }

    protected final void g() {
        if (this.l != null) {
            this.l.n();
        }
        this.u();
    }

    public final void c(Graphics graphics) {
        this.a(graphics);
        cz.a(graphics, this.k);
        cz.a(graphics, this.c, this.d, this.f + 1, this.g + 1);
        this.b(graphics);
        if (this.l != null && this.m) {
            this.l.a(graphics, 0, 0);
        }
        cz.c(graphics, this.k);
        if (this.l != null && !this.m) {
            this.l.a(graphics, 0, 0);
        }
    }

    protected abstract boolean g(int var1);

    protected abstract void e(int var1);

    public abstract void u();

    public abstract void b(Graphics var1);

    public abstract void a(Graphics var1);

    public final void c(int n2) {
        if (this.l != null) {
            boolean bl2;
            int n3 = n2;
            bd[] bdArray = this.l.a();
            hr hr2 = this;
            if (n3 == 94 && bdArray[0] != null && bdArray[0].b()) {
                if (hr2.i != null) {
                    hr2.i.d(-1, bdArray[0].a());
                }
                bl2 = true;
            } else if (n3 == 95 && bdArray[1] != null && bdArray[1].b()) {
                if (hr2.i != null) {
                    hr2.i.d(-1, bdArray[1].a());
                }
                bl2 = true;
            } else if (n3 == 93 && bdArray[2] != null && bdArray[2].b()) {
                if (hr2.i != null) {
                    hr2.i.d(-1, bdArray[2].a());
                }
                bl2 = true;
            } else {
                bl2 = false;
            }
            if (bl2) {
                z.c();
                return;
            }
            if (this.l.f(n2)) {
                return;
            }
            this.z();
            return;
        }
        this.f(n2);
    }

    protected void f(int n2) {
    }

    protected final void a(bv bv2, bd bd2, bd bd3, bd bd4) {
        this.l = bv2;
        this.n = new bd[this.j.length];
        int n2 = 0;
        while (n2 < this.j.length) {
            this.n[n2] = this.j[n2];
            ++n2;
        }
        hr hr2 = this;
        hr2.a(bd2, true);
        bd2 = bd4;
        hr2 = this;
        hr2.b(bd2, true);
        this.a(bd3);
        this.e(true);
    }

    protected final void z() {
        if (this.l == null) {
            return;
        }
        this.l = null;
        this.m = false;
        int n2 = 0;
        while (n2 < this.n.length) {
            this.j[n2] = this.n[n2];
            ++n2;
        }
        this.e(true);
    }

    public final void d(int n2) {
        if (this.l != null) {
            this.l.g(n2);
            return;
        }
    }

    public final void c(int n2, int n3) {
        if (this.l != null) {
            this.l.e(n2, n3);
            return;
        }
        this.e(n2, n3);
    }

    public void e(int n2, int n3) {
    }

    public final void a(int n2, int n3) {
        if (this.l != null) {
            boolean bl2;
            block6: {
                int n4 = n3;
                int n5 = n2;
                bd[] bdArray = this.l.a();
                hr hr2 = this;
                int n6 = 0;
                while (n6 < bdArray.length) {
                    if (bdArray[n6] != null && bdArray[n6].a(n5, n4)) {
                        if (hr2.i != null) {
                            hr2.i.d(-1, bdArray[n6].a());
                        }
                        bl2 = true;
                        break block6;
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
        this.f(n2, n3);
    }

    public void f(int n2, int n3) {
    }

    public final void b(int n2, int n3) {
        if (this.l != null) {
            this.l.f(n2, n3);
            return;
        }
        this.g(n2, n3);
    }

    public void g(int n2, int n3) {
    }

    public final void a(bu[] buArray, bd bd2, bd bd3, bd bd4) {
        bv bv2 = new bv();
        bv2.a(buArray);
        int n2 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
        bv2.a_(-n2, this.j() - bv2.f() + n2);
        bv2.d(0, z.s - be.a - bv2.f());
        bv2.a(this);
        this.a(bv2, bd2, bd3, bd4);
    }

    public void a(n n2) {
    }
}

