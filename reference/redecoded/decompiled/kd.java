/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class kd
extends ke {
    private kl f;
    public a b = new a();
    public kg[] c;
    public a d = new a();
    private a g = new a();

    public final void a(Graphics graphics, int n2, int n3) {
        this.a(graphics, n2, n3, null);
    }

    public final void a(Graphics graphics, int n2, int n3, k k2) {
        int n4;
        int n5;
        int n6 = 0;
        while (n6 < this.c.length) {
            if (this.c[n6].m()) {
                this.c[n6].a(graphics, n2, n3 + 5);
            }
            ++n6;
        }
        if (this.d != null) {
            n5 = 0;
            n4 = this.d.d();
            while (n5 < n4) {
                ka ka2;
                ka2.a(graphics, n2, n3 + ((ka2 = (ka)this.d.b(n5)).r() == 1 ? 5 : 7));
                ++n5;
            }
        }
        n4 = 0;
        int n7 = this.g.d();
        while (n4 < n7) {
            at at2 = (at)this.g.b(n4);
            n5 = at2.l() == 10 ? 7 : 5;
            at2.a(graphics, n2, n3 + n5);
            ++n4;
        }
    }

    public final void i() {
        if (this.d != null) {
            int n2 = 0;
            int n3 = this.d.d();
            while (n2 < n3) {
                ka ka2 = (ka)this.d.b(n2);
                ka2.i();
                ++n2;
            }
        }
    }

    public final void a() {
        int n2;
        this.g.a();
        int n3 = 0;
        while (n3 < this.c.length) {
            this.c[n3].i();
            ++n3;
        }
        if (this.f != null) {
            this.g.a(this.f);
        }
        if (this.b != null) {
            n2 = 0;
            while (n2 < this.b.d()) {
                if (this.b.b(n2) instanceof ki) {
                    at at2 = (at)this.b.b(n2);
                    if (this.a.b(at2.n(), at2.o(), at2.p(), at2.q())) {
                        at2.b(true);
                        this.g.a(at2);
                    } else {
                        at2.b(false);
                    }
                }
                ++n2;
            }
        }
        int n4 = 0;
        while (n4 < this.c.length) {
            if (this.a.b(this.c[n4].n(), this.c[n4].o(), this.c[n4].p(), this.c[n4].q())) {
                this.c[n4].b(true);
            } else {
                this.c[n4].b(false);
            }
            ++n4;
        }
        a a2 = this.g;
        n2 = 1;
        int n5 = a2.d();
        while (n2 < n5) {
            int n6 = n2;
            at at3 = (at)a2.b(n6);
            at at4 = (at)a2.b(n6 - 1);
            while (n6 > 0 && (at4.o() + at4.q() > at3.o() + at3.q() || at4.l() > at3.l() && at4.o() + at4.q() == at3.o() + at3.q())) {
                a2.a(n6, n6 - 1);
                at3 = (at)a2.b(--n6);
                at4 = (at)a2.b(n6 - 1);
            }
            ++n2;
        }
    }

    public final void b(Graphics graphics, int n2, int n3) {
        int n4 = 0;
        while (n4 < this.c.length) {
            if (this.c[n4].d.a == 2) {
                ((kk)this.c[n4]).b(graphics, n2, n3);
            }
            ++n4;
        }
    }

    public final void a(kl kl2) {
        this.f = kl2;
        this.b.a(kl2);
    }

    public final kl c() {
        return this.f;
    }

    public final void b() {
        super.b();
        this.d.a();
        this.g.a();
        this.b.a();
        this.f = null;
    }
}

