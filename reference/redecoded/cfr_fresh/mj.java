/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class mj
extends at {
    private int a;
    private int b;
    private String[] c;
    private int d;
    private k e;
    private int f = 0;
    private int g;
    private int h;
    private int i;

    public mj(int n2, int n3, int n4, int n5) {
        this.a(n2, n3, n4, 20);
        this.g = n4;
        this.h = n2;
        this.f = this.o() + 20;
        this.e = new k();
    }

    public final void a(String string, int n2, int n3, int n4) {
        this.a = n3;
        this.d = n2;
        this.c = bx.a(string, this.g, bx.d);
        this.i(this.c.length * 20);
        this.g(this.f - this.q());
        if (n2 == 0) {
            int n5 = bx.d.a(this.c[0]) + 20;
            if (this.c.length == 1 && n5 < this.g) {
                this.h(n5);
            }
            this.m = this.h;
            this.b = this.m + 20;
            return;
        }
        int n6 = bx.d.a(this.c[0]) + 20;
        if (this.c.length == 1 && n6 < this.g) {
            this.h(n6);
        }
        this.m = this.h + this.g - this.p();
        this.b = this.m + this.o - 20;
    }

    public final void a(String string, int n2) {
        this.a(string, n2, 100, 20);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.c == null) {
            return;
        }
        n3 += this.o() + (this.d == 1 ? this.i : 0);
        if (this.d == 0) {
            cw.a(graphics, this.n(), n3, this.p(), this.q(), this.b, 14808319, 152707);
        } else {
            cw.a(graphics, this.n(), n3, this.p(), this.q(), this.b, 16775619, 8023552);
        }
        cw.a(graphics, this.e);
        cw.a(graphics, this.m + 6, n3 + 4, this.p() - 8, this.q());
        bx.a(graphics, bx.d, this.c, this.m + 6, n3 + 4, this.p() - 8, this.q(), 0);
        cw.c(graphics, this.e);
    }

    public final void i() {
        if (this.a > 0) {
            --this.a;
            if (this.a == 0) {
                this.c = null;
                this.h(this.g);
                this.f(this.h);
            }
        }
    }

    public final void a(int n2) {
        if (n2 > 0) {
            return;
        }
        this.i = n2;
    }
}

