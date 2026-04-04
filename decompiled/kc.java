/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class kc
extends kd {
    private kk f;
    public a b = new a();
    public kf[] c;
    public a d = new a();
    private a g = new a();

    public final void a(Graphics graphics, int n2, int n3) {
        this.a(graphics, n2, n3, null);
    }

    public final void a(Graphics graphics, int n2, int n3, n n4) {
        int n5;
        int n6;
        int n7 = 0;
        while (n7 < this.c.length) {
            if (this.c[n7].m()) {
                this.c[n7].a(graphics, n2, n3 + 5);
            }
            ++n7;
        }
        if (this.d != null) {
            n6 = 0;
            n5 = this.d.d();
            while (n6 < n5) {
                jz jz2;
                jz2.a(graphics, n2, n3 + ((jz2 = (jz)this.d.b(n6)).r() == 1 ? 5 : 7));
                ++n6;
            }
        }
        ax ax2 = null;
        n6 = 0;
        n5 = 0;
        int n8 = this.g.d();
        while (n5 < n8) {
            ax2 = (ax)this.g.b(n5);
            n6 = ax2.l() == 10 ? 7 : 5;
            ax2.a(graphics, n2, n3 + n6);
            ++n5;
        }
    }

    public final void i() {
        if (this.d != null) {
            int n2 = 0;
            int n3 = this.d.d();
            while (n2 < n3) {
                jz jz2 = (jz)this.d.b(n2);
                jz2.i();
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
            ax ax2 = null;
            n2 = 0;
            while (n2 < this.b.d()) {
                if (this.b.b(n2) instanceof kh) {
                    ax2 = (ax)this.b.b(n2);
                    if (this.a.b(ax2.n(), ax2.o(), ax2.p(), ax2.q())) {
                        ax2.b(true);
                        this.g.a(ax2);
                    } else {
                        ax2.b(false);
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
            ax ax3 = (ax)a2.b(n6);
            ax ax4 = (ax)a2.b(n6 - 1);
            while (n6 > 0 && (ax4.o() + ax4.q() > ax3.o() + ax3.q() || ax4.l() > ax3.l() && ax4.o() + ax4.q() == ax3.o() + ax3.q())) {
                a2.a(n6, n6 - 1);
                ax3 = (ax)a2.b(--n6);
                ax4 = (ax)a2.b(n6 - 1);
            }
            ++n2;
        }
    }

    public final void b(Graphics graphics, int n2, int n3) {
        int n4 = 0;
        while (n4 < this.c.length) {
            if (this.c[n4].d.a == 2) {
                ((kj)this.c[n4]).b(graphics, n2, n3);
            }
            ++n4;
        }
    }

    public final void a(kk kk2) {
        this.f = kk2;
        this.b.a(kk2);
    }

    public final kk c() {
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

