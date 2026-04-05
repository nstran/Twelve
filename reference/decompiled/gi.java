/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gi
extends ax {
    private String[] a;
    private byte b = (byte)10;
    private int c = 15;
    private n d;
    private int e;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private boolean i;
    private gi j;
    private a k;
    private boolean s;
    private boolean t = false;
    private int u = 150;

    public gi(String string, int n2, int n3, int n4) {
        this(string, n2, n3, z.r >= 200 ? 200 : z.r, n4, false);
    }

    public gi(String string, int n2, int n3, int n4, int n5, boolean bl2) {
        this.o = n4;
        this.p = n5;
        this.a = ca.a(string, n4 - this.b - this.b);
        this.e = this.a.length * ca.c.a();
        this.p = this.e + 20 >= n5 ? n5 : this.e + 20;
        this.c = n4 / 2;
        this.m = n2 - this.c;
        this.n = n3 - this.p;
        this.d = new n(this.m + this.b - 1, this.n + this.b - 1, this.o - this.b - this.b, this.p - this.b - this.b);
        this.h = 0;
        this.s = true;
    }

    public final void a(boolean bl2) {
        this.t = true;
    }

    public final boolean a() {
        return this.t;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r || !this.s) {
            return;
        }
        cz.a(graphics, this.m + n2, this.n + n3, this.o, this.p, this.m + this.c + n2, this.t, 14808319, 152707);
        cz.a(graphics);
        cz.a(graphics, this.d.a + n2, this.d.b + n3, this.d.c, this.d.d);
        int n4 = this.a.length - this.h;
        ca.a(graphics, ca.c, this.a, this.h, n4 >= 3 ? 3 : n4, this.m + this.b + n2, this.n + this.b + n3, this.o - this.b - this.b, this.e, 0);
        cz.b(graphics);
    }

    public final void i() {
        if (!this.r) {
            return;
        }
        if (this.i) {
            --this.g;
            if (this.g == this.u / 2) {
                boolean bl2 = false;
                gi gi2 = this;
                this.s = bl2;
            }
            if (this.g <= 0) {
                this.b(false);
                if (this.j != null) {
                    this.j.b(this.m());
                }
            }
        }
    }

    public final void a(int n2) {
        this.c = n2;
    }

    public final void a(n n2) {
        this.d = n2;
    }

    public final void c(boolean bl2) {
        this.i = true;
        int n2 = this.u;
        gi gi2 = this;
        this.g = n2;
    }

    public final void a(gi gi2) {
        this.j = gi2;
    }

    public final void a(String[] stringArray) {
        if (this.k == null) {
            this.k = new a(2);
        }
        int n2 = 0;
        while (n2 < stringArray.length) {
            this.k.a(stringArray[n2]);
            ++n2;
        }
    }

    public final String b() {
        if (this.k != null) {
            return (String)this.k.b(0);
        }
        return "";
    }

    public final void c() {
        if (this.k != null) {
            this.k.a(0);
        }
    }

    public final void d(boolean bl2) {
        this.s = bl2;
    }

    public final boolean d() {
        return this.s;
    }
}

