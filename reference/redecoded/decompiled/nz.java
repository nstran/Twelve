/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class nz
extends an {
    private ay b;
    public fn a;

    public nz(byte by2) {
        super(-1);
        this.a(new av(0xFFFFFF, 0, 0, this.f, this.g));
        this.a = new fk();
        this.a.a(0, 0, this.f, this.g);
    }

    public final void j(boolean bl2) {
        this.b = new ay(0);
        this.b.a(0, 0, this.f, this.g);
        this.b.b(this.a);
        this.b.h(1);
    }

    public final void a(aq aq2) {
        this.a.b(aq2);
    }

    public final aq f(int n2) {
        return this.a.j(n2);
    }

    public final void g(int n2) {
        this.a.i(1);
    }

    protected final void a(Graphics graphics) {
        if (this.b != null) {
            this.b.a(graphics, 0, 0);
            return;
        }
        this.a.a(graphics, 0, 0);
    }

    protected final void c() {
        if (this.b != null) {
            this.b.n();
            return;
        }
        this.a.n();
    }

    protected final void a(int n2) {
        if (this.b != null) {
            this.b.f(n2);
            return;
        }
        this.a.f(n2);
    }

    protected final void e(int n2) {
        if (this.b != null) {
            this.b.g(n2);
            return;
        }
        this.a.g(n2);
    }

    protected final void e(int n2, int n3) {
        if (this.b != null) {
            this.b.c(n2, n3);
            return;
        }
        this.a.c(n2, n3);
    }

    protected final void f(int n2, int n3) {
        if (this.b != null) {
            this.b.f(n2, n3);
            return;
        }
        this.a.f(n2, n3);
    }

    protected final void g(int n2, int n3) {
        if (this.b != null) {
            this.b.e(n2, n3);
            return;
        }
        this.a.e(n2, n3);
    }
}

