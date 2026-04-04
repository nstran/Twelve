/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class ax {
    protected int l;
    protected int m;
    protected int n;
    protected int o;
    protected int p;
    protected int q;
    protected boolean r = true;

    public final int l() {
        return this.l;
    }

    public final void e(int n2) {
        this.l = 10;
    }

    public final boolean m() {
        return this.r;
    }

    public void b(boolean bl2) {
        this.r = bl2;
    }

    public void c(int n2, int n3) {
        this.f(n2);
        this.g(n3);
    }

    public void a(int n2, int n3, int n4, int n5) {
        this.f(n2);
        this.g(n3);
        n3 = n4;
        ax ax2 = this;
        this.o = n3;
        n3 = n5;
        ax2 = this;
        this.p = n3;
    }

    public int n() {
        return this.m;
    }

    public void f(int n2) {
        this.m = n2;
    }

    public int o() {
        return this.n;
    }

    public void g(int n2) {
        this.n = n2;
    }

    public int p() {
        return this.o;
    }

    public final void h(int n2) {
        this.o = n2;
    }

    public int q() {
        return this.p;
    }

    public final void i(int n2) {
        this.p = n2;
    }

    public void j(int n2) {
        this.q = n2;
    }

    public final void k(int n2) {
        if ((n2 & 0x20) == 32) {
            this.n -= this.p;
        } else if ((n2 & 2) == 2) {
            this.n -= this.p / 2;
        }
        if ((n2 & 8) == 8) {
            this.m -= this.o;
            return;
        }
        if ((n2 & 1) == 1) {
            this.m -= this.o / 2;
        }
    }

    public abstract void i();

    public abstract void a(Graphics var1, int var2, int var3);

    public void a(Graphics graphics) {
        this.a(graphics, 0, 0);
    }

    public final boolean b(int n2, int n3, int n4, int n5) {
        n2 = this.e(n2, n4) ? 1 : 0;
        n3 = this.f(n3, n5) ? 1 : 0;
        return n2 != 0 && n3 != 0;
    }

    public static int a(int n2, int n3, int n4) {
        if ((n2 = Math.abs(n3 - n2)) <= n4) {
            return 1;
        }
        return n2 / n4;
    }

    public final boolean e(int n2, int n3) {
        int n4 = this.m > n2 ? 1 : 0;
        this.m = n4 != 0 ? (this.m -= n3) : (this.m += n3);
        int n5 = n3 = this.m > n2 ? 1 : 0;
        if (n4 != n3) {
            this.m = n2;
        }
        return this.m == n2;
    }

    public final boolean f(int n2, int n3) {
        int n4 = this.n > n2 ? 1 : 0;
        this.n = n4 != 0 ? (this.n -= n3) : (this.n += n3);
        int n5 = n3 = this.n > n2 ? 1 : 0;
        if (n4 != n3) {
            this.n = n2;
        }
        return this.n == n2;
    }

    public void g(int n2, int n3) {
    }

    public void h(int n2, int n3) {
    }
}

