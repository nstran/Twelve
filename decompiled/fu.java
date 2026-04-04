/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class fu
extends ax {
    private int a = 7;
    private String[] b;
    private int c;
    private int d;

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        oz.b(graphics, n2 += this.m, n3 += this.n, this.o, this.p, 0xFFFFFF, true);
        ca.a(graphics, ca.c, this.b, n2 += this.a, n3 += this.a, this.o, this.p, 0);
    }

    public void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
    }

    public final void a(String string) {
        this.b = ca.a(string, this.o - this.a - this.a);
        this.p = 0 + this.b.length * ca.c.a() + this.a + this.a;
    }

    public final void i() {
        int n2;
        if (!this.r) {
            return;
        }
        if (this.c != this.n()) {
            n2 = this.c - this.n();
            if (Math.abs(n2) > 4) {
                n2 /= 2;
            }
            this.f(this.n() + n2);
        }
        if (this.d != this.o()) {
            n2 = this.d - this.o();
            if (Math.abs(n2) > 4) {
                n2 /= 2;
            }
            this.g(this.o() + n2);
        }
    }

    public final void a(int n2, int n3) {
        this.c = n2;
        this.d = n3;
    }
}

