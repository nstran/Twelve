/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nl
extends ni {
    private aw c = new aw();
    private aw d;
    private Image[] e;
    private int[] f;
    private boolean F = false;
    private int G = -1000;
    public int a = 0;
    public int b = 0;

    public nl(int n2, Image[] imageArray, byte[][] byArray, int[] nArray, Image image, byte[][] byArray2, int n3) {
        super(n2);
        this.e = imageArray;
        this.f = nArray;
        this.c.a(byArray);
        this.a(0);
        this.d = new aw(image, n3);
        this.d.a(byArray2);
        this.d.a(false);
        if (n2 == 0) {
            this.c.c(2);
            this.d.c(2);
        } else {
            this.c.c(0);
            this.d.c(0);
        }
        this.d.d(2);
        this.C = new ad();
        this.C.a(20);
        this.C.a(com.mg.sq.a.h);
        this.j(36);
        this.D = new n(this.c.n(), this.c.o(), this.c.p(), this.c.q());
    }

    public final int c() {
        return this.c.h();
    }

    public final void j(int n2) {
        super.j(n2);
        this.c.j(n2);
    }

    public final void a(int n2) {
        this.E = n2;
        if (n2 == 4) {
            this.c.a(false);
        } else {
            this.c.a(true);
        }
        this.c.d(n2);
        this.c.a(this.e[n2], this.f[n2]);
    }

    public final int n() {
        return this.c.n();
    }

    public final int o() {
        return this.c.o() - this.c.q();
    }

    public final int p() {
        return this.c.p();
    }

    public final int q() {
        return this.c.q();
    }

    public final void c(int n2, int n3) {
        this.c.c(n2, n3);
        this.j = n2;
    }

    public final void d() {
        if (this.c.e().length > 4) {
            this.a(4);
        }
    }

    public final void a(int n2, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.w = bl2;
        if (!this.x) {
            this.x = bl3;
        }
        if (this.h == 0) {
            nl nl2 = this;
            this.s = this.g.n() + this.g.p() / 2 - nl2.c.p();
            nl nl3 = this;
            nl2 = nl3;
            nl nl4 = this;
            nl2 = nl4;
            nl2 = this;
            this.d.c(nl3.c.n() + nl4.c.p() / 2, this.o() - nl2.c.q());
        } else {
            this.s = this.g.n() + this.g.p() / 2 - 10;
        }
        nl nl5 = this;
        this.d.c(nl5.c.n() - this.a, this.o() - this.b);
        this.k = ax.a(this.s, this.c.n(), 6);
        this.t = n2 - 6;
        this.i = true;
        this.d.d(2);
        if (bl4) {
            this.g();
        }
        this.a(2);
    }

    public final void a(boolean bl2, boolean bl3) {
        if (bl2) {
            this.F = bl2;
            if (this.G == -1000) {
                this.G = this.c.n();
                if (!this.C.a()) {
                    nl nl2 = this;
                    nl nl3 = nl2;
                    nl3 = this;
                    this.C.a("X\u00ed H\u1ee5t", nl2.c.n() + nl3.c.p() / 2, this.o() + 20);
                }
                this.s = this.h == 0 ? this.c.n() - 15 : this.c.n() + 15;
                this.k = 7;
                this.i = false;
                return;
            }
        } else if (this.c.e().length > 3) {
            this.a(3);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        this.z.b(graphics);
        this.c.a(graphics, n2, n3);
        this.d.a(graphics, n2, n3);
        this.z.c(graphics);
        this.C.a(graphics, n2, n3);
    }

    public final void i() {
        if (this.t > 0) {
            --this.t;
        }
        this.z.i();
        switch (this.c.h()) {
            case 1: {
                if (this.d.h() == 0) {
                    if (!this.d.e(this.s, this.k)) break;
                    this.d.d(1);
                    this.d.j(17);
                    if (this.x) {
                        ak.a().a(20);
                    }
                    this.x = false;
                    this.g.a(this.w, this.y);
                    break;
                }
                if (this.d.h() != 1 || !this.d.j()) break;
                this.d.d(2);
                this.a(0);
                break;
            }
            case 2: {
                if (!this.i || this.t <= 0 || this.t != 1) break;
                this.d.d(0);
                this.d.j(20);
                this.a(1);
                break;
            }
            case 3: {
                if (!this.c.j()) break;
                this.a(0);
            }
        }
        if (this.F) {
            if (this.i) {
                if (this.c.e(this.s, this.k)) {
                    this.a(0);
                    this.G = -1000;
                    this.F = false;
                }
            } else if (this.c.e(this.s, this.k)) {
                this.s = this.G;
                this.i = true;
                this.k = 7;
            } else if (this.k > 2) {
                this.k -= 2;
            }
        }
        this.c.i();
        this.d.i();
        this.C.b();
        this.D.a = this.c.n();
        this.D.b = this.c.o();
    }

    public final void a(boolean bl2) {
    }

    public final void e() {
    }
}

