/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class fw
extends au {
    private a i;
    private int j = 0;
    private String k;
    private int l;
    private n m;
    private boolean n = false;
    private bl o = null;

    public fw(String string) {
        this.k = string;
        this.d = new n(0, 0, z.r - 20, z.s);
        this.m = new n(0, 0, this.d.c, 9 + ca.d.a());
        this.i = new a();
        this.l = this.m.d + 5;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        this.c = true;
    }

    public final void a(ey ey2) {
        int n2;
        ey2.a(this);
        ey2.a(9, this.l, 13, 13);
        this.l += ey2.f() + 5;
        this.d.d = this.l + 5;
        this.e(this.d.d);
        int n3 = this.i.d();
        this.i.a(ey2);
        if (this.i.d() == 1) {
            this.j = 0;
            ey2.d(true);
            ey2.e(true);
        } else if (ey2.a()) {
            n2 = 0;
            while (n2 < n3) {
                ((ey)this.i.b(n2)).e(false);
                ++n2;
            }
        }
        if (ey2.m()) {
            this.j = n3;
            n2 = 0;
            while (n2 < n3) {
                ((ey)this.i.b(n2)).d(false);
                ++n2;
            }
        }
    }

    public final void b(int n2, int n3) {
        super.b(n2, n3);
        this.m = new n(0, 0, this.d.c, 9 + ca.d.a());
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.m = new n(this.d.a, this.d.b, this.d.c, 9 + ca.d.a());
        this.k = com.mg.sq.a.a(this.k, this.d.c - 4);
    }

    public final boolean f(int n2) {
        if (this.i.d() <= 0) {
            return false;
        }
        int n3 = this.j;
        int n4 = 0;
        switch (n2) {
            case 98: {
                if (this.j >= this.i.d() - 1) break;
                ++this.j;
                n4 = 1;
                break;
            }
            case 99: {
                if (this.j <= 0) break;
                --this.j;
                n4 = 1;
                break;
            }
            case 95: {
                ey ey2 = (ey)this.i.b(this.j);
                ey2.e(true);
                int n5 = 0;
                n4 = this.i.d();
                while (n5 < n4) {
                    if (n5 != this.j) {
                        ((ey)this.i.b(n5)).e(false);
                    }
                    ++n5;
                }
                if (this.b != null) {
                    this.b.c(true);
                }
                this.c(true);
                n4 = 1;
            }
        }
        if (n3 != this.j) {
            ((ey)this.i.b(n3)).d(false);
            ((ey)this.i.b(this.j)).d(true);
            this.c = true;
            if (this.b != null) {
                this.b.c(true);
            }
        }
        return n4 != 0;
    }

    public final boolean c(int n2, int n3) {
        n2 -= this.c();
        n3 -= this.d();
        int n4 = 0;
        int n5 = this.i.d();
        while (n4 < n5) {
            ey ey2 = (ey)this.i.b(n4);
            if (ey2.h().a(n2, n3)) {
                this.j = n4;
                ey2.d(true);
                ey2.e(true);
                n3 = 0;
                while (n3 < n5) {
                    if (n4 != n3) {
                        ey ey3 = (ey)this.i.b(n3);
                        ey3.d(false);
                        ey3.e(false);
                    }
                    ++n3;
                }
                this.c(true);
                if (this.b == null) break;
                this.b.c(true);
                break;
            }
            ++n4;
        }
        return true;
    }

    public final void h(int n2) {
        int n3 = 0;
        while (n3 < this.i.d()) {
            ey ey2 = (ey)this.i.b(n3);
            if (ey2.q() == 0) {
                ey2.e(true);
            } else {
                ey2.e(false);
            }
            ++n3;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.m()) {
            oz.a(graphics, this.c() + n2, this.d() + n3, this.e(), this.f(), 7070703, -1);
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3, this.m.c, this.m.d);
        }
        int n4 = (n3 += this.d()) + 4;
        int n5 = (n2 += this.c()) + 4;
        ca.d.c(true);
        ca.d.a(graphics, this.k, n5, n4, 0);
        ca.d.c(false);
        n5 = 0;
        int n6 = this.i.d();
        while (n5 < n6) {
            ey ey2 = (ey)this.i.b(n5);
            if (n5 == this.j) {
                graphics.setColor(0xFF0000);
                graphics.fillRect(n2 + ey2.c(), n3 + ey2.d(), ey2.e(), ey2.f());
            }
            ey2.a(graphics, n2, n3);
            ++n5;
        }
        this.c = false;
    }

    public final ey a() {
        ey ey2 = null;
        int n2 = 0;
        while (n2 < this.i.d()) {
            ey2 = (ey)this.i.b(n2);
            if (ey2.a()) {
                return ey2;
            }
            ++n2;
        }
        return null;
    }
}

