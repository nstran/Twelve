/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class fz
extends au {
    private String i = "";
    private ez j;

    public fz(String string) {
        this.i = string;
        this.j = new ez();
        this.j.a(this.c(), ca.d.a() + 10, this.e(), 20);
        this.e(ca.d.a() + this.j.f() + 15);
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
            ca.d.a(graphics, this.i, n5, n4 + 3, 0);
            ca.d.c();
        }
        this.j.a(graphics, n5 + 3, n4);
    }

    public final void n() {
        this.j.n();
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.j.a(n2, n3 + 20, this.e(), 18);
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
        return this.j.c(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.j.f(n2, n3);
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        if (this.j != null) {
            this.j.d(bl2);
        }
    }

    public final long a() {
        return this.j.a();
    }
}

