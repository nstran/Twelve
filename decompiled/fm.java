/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class fm
extends au
implements bx {
    protected m j;
    protected a k = new a();
    protected int l;
    protected n m;
    bt n;

    public fm() {
        this.j = new m();
    }

    public final void a(bt bt2) {
        this.n = bt2;
    }

    public final int q() {
        return this.l;
    }

    public final int r() {
        return this.k.d();
    }

    public final au[] s() {
        au[] auArray = new au[this.k.d()];
        int n2 = 0;
        while (n2 < auArray.length) {
            auArray[n2] = (au)this.k.b(n2);
            ++n2;
        }
        return auArray;
    }

    public synchronized void b(au au2) {
        this.k.a(au2);
        au2.a(this);
        if (this.k.d() == 1) {
            this.i(0);
        } else {
            this.a();
        }
        if (this.b != null && this.b instanceof bc) {
            ((bc)this.b).o();
        }
    }

    public synchronized void a(au au2, int n2) {
        this.k.b(au2, n2);
        au2.a(this);
        if (n2 < this.l) {
            this.i(this.l + 1);
        }
        this.a();
        if (this.b != null && this.b instanceof bc) {
            ((bc)this.b).o();
        }
    }

    public synchronized void c(au au2) {
        int n2 = 0;
        while (n2 < this.k.d()) {
            if (this.k.b(n2).equals(au2)) {
                this.h(n2);
                return;
            }
            ++n2;
        }
    }

    public synchronized void h(int n2) {
        if (n2 < 0 || n2 >= this.k.d()) {
            return;
        }
        this.k.a(n2);
        if (n2 == this.l) {
            if (n2 == this.k.d() - 1) {
                this.i(this.l - 1);
            } else {
                this.i(this.l);
            }
        } else if (n2 < this.l) {
            this.i(this.l - 1);
        }
        this.a();
        if (this.b != null && this.b instanceof bc) {
            ((bc)this.b).o();
        }
    }

    public final synchronized void t() {
        this.k.a();
        if (this.b != null && this.b instanceof bc) {
            ((bc)this.b).o();
        }
        this.a();
        this.l = 0;
    }

    public final void a(au au2) {
        super.a(au2);
        if (au2 instanceof bc) {
            this.m = ((bc)au2).r();
        }
    }

    public final au u() {
        return this.j(this.l);
    }

    public final void i(int n2) {
        if (n2 < 0 || n2 >= this.k.d()) {
            cw.a("[MGLayout]setSelectedComponent(...) vuot qua mang index = " + n2);
            return;
        }
        au au2 = null;
        int n3 = 0;
        while (n3 < this.k.d()) {
            au2 = this.j(n3);
            if (n3 != n2) {
                au2.d(false);
            } else {
                au2.d(true);
                this.l = n2;
            }
            ++n3;
        }
        if (this.n != null) {
            this.n.a(au2, 0, n2);
        }
        this.a();
    }

    public final au j(int n2) {
        if (n2 < 0) {
            return null;
        }
        au au2 = (au)this.k.b(n2);
        return au2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.m == null) {
            this.m = this.l() != null ? ((bc)this.l()).r() : new n(0, 0, this.e(), this.f());
        }
        n2 += this.c() - this.m.a;
        n3 += this.d() - this.m.b;
        au au2 = null;
        int n4 = this.k.d();
        int n5 = 0;
        while (n5 < n4) {
            au2 = (au)this.k.b(n5);
            if (au2 != null && au2.h().a(this.m)) {
                au2.a(graphics, n2, n3);
                au2.c(true);
            }
            ++n5;
        }
    }

    public final void n() {
        au au2 = this.u();
        if (au2 != null) {
            au2.n();
        }
    }

    public final m v() {
        return new m(this.j.a, this.j.b);
    }

    public int w() {
        return 10;
    }

    protected void a() {
    }
}

