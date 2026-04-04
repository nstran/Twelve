/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gc
extends au {
    private String i = "";
    private ff j;
    private Image k;
    private cx l;

    public gc(String string) {
        this.i = string;
        this.j = new ff("", 300, 2);
        this.j.a(this.c() + 3, ca.d.a() + 10, this.e() - 6, 20);
        this.e(ca.d.a() + this.j.f() + 15);
    }

    public gc(byte[] byArray) {
        this.i = "Nh\u1eadp l\u1ea1i nh\u1eefng k\u00fd t\u1ef1 sau:";
        if (byArray != null && byArray.length > 0) {
            this.k = f.a(byArray);
        }
        int n2 = ca.d.a() + 10;
        if (this.k != null) {
            this.l = new cx((z.r - this.k.getWidth()) / 2, n2);
            n2 += this.k.getHeight() + 5;
        }
        this.j = new ff("", 300, 2);
        this.j.a(this.c() + 3, n2, this.e() - 6, 20);
        this.e(n2 + this.j.f() + 3);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        int n4 = this.d() + n3;
        int n5 = this.c() + n2;
        if (this.m()) {
            oz.a(graphics, this.c() + n2, this.d() + n3, this.e(), this.f(), 7070703, -1);
            graphics.setColor(7267055);
            graphics.fillRect(n5, n4, this.e(), ca.d.a() + 6);
        }
        if (this.i != null) {
            ca.d.c(true);
            ca.d.a(graphics, this.i, n5 + 1, n4 + 3, 0);
            ca.d.c();
        }
        if (this.k != null) {
            graphics.drawImage(this.k, this.l.a + n5, this.l.b + n4, 20);
        }
        this.j.a(graphics, n5, n4);
    }

    public final void n() {
        this.j.n();
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.j.a(this.c() + 3, ca.d.a() + 10, this.e() - 10, 20);
    }

    public final void d(int n2) {
        super.d(n2);
        this.j.d(n2 - 5);
    }

    public final boolean f(int n2) {
        return this.j.f(n2);
    }

    public final boolean g(int n2) {
        return this.j.g(n2);
    }

    public final boolean c(int n2, int n3) {
        return this.j.c(n2 -= this.c(), n3 -= this.d());
    }

    public final void h(int n2) {
        this.j.j(4);
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        if (this.j != null) {
            this.j.d(bl2);
        }
    }

    public final String a() {
        return this.j.r();
    }

    public final void i(int n2) {
        this.j.k(1);
    }

    public final void a(String string) {
        this.j.c(string);
    }

    public final void b(boolean bl2) {
        super.b(bl2);
        this.j.b(bl2);
    }
}

