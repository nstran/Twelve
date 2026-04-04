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

public final class ng
extends ni {
    private me a;
    private me b;
    private me c;
    private me d;
    private me e;
    private me f;
    private ma F;
    private me G;
    private int[] H;
    private int I;
    private int J;
    private Image K;
    private int L;
    private int M;
    private int N;
    private np O;
    private int P;

    public ng(int n2, me me2, me me3, me me4, me me5, me me6, me me7, ma ma2, np np2) {
        super(n2);
        int[] nArray = new int[9];
        nArray[0] = -10;
        nArray[1] = -10;
        nArray[2] = 10;
        nArray[3] = 10;
        this.H = nArray;
        this.L = -1000;
        this.a = me2;
        this.b = me3;
        this.c = me4;
        this.d = me6;
        this.e = me5;
        this.f = me7;
        this.F = ma2;
        this.O = np2;
        this.G = this.a;
        if (n2 == 0) {
            this.G.c(2);
        } else {
            this.G.c(0);
        }
        this.K = mn.a().i;
        this.a(0);
        this.C = new ad();
        this.C.a(20);
        this.C.a(com.mg.sq.a.h);
        this.D = new n(this.G.n(), this.G.o(), this.G.p(), this.G.q());
    }

    public final void a(int n2, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.w = bl2;
        this.y = bl6;
        if (!this.x) {
            this.x = bl3;
        }
        if (this.h == 0) {
            ng ng2 = this;
            this.s = this.g.n() + this.g.p() / 2 - ng2.G.p();
        } else {
            this.s = this.g.n() + this.g.p() / 2;
        }
        this.M = this.g.o() + this.g.q() - this.G.q();
        if (this.v <= 0 && (this.E == 0 || this.E == 6)) {
            this.k = ax.a(this.s, this.G.n(), 2);
            if (this.k < 5) {
                this.k = 5;
            }
            this.N = ax.a(this.M, this.G.o(), 2);
        }
        if (n2 < 11) {
            n2 = 11;
        }
        this.t = n2;
        this.i = true;
        if (bl4) {
            this.g();
            this.v = 13;
            this.u = 2;
            return;
        }
        if (bl5) {
            this.h();
            this.v = 13;
            this.u = 2;
            return;
        }
        if (this.v <= 0 && (this.E == 0 || this.E == 6)) {
            this.a(2);
        }
    }

    public final void a(boolean bl2, boolean bl3) {
        if (bl2) {
            if (this.L == -1000) {
                this.L = this.G.n();
                this.s = this.h == 0 ? this.G.n() - 10 : this.G.n() + 10;
                this.k = 5;
                this.i = false;
                this.a(7);
                if (!this.C.a()) {
                    ng ng2 = this;
                    ng ng3 = ng2;
                    ng ng4 = this;
                    ng3 = ng4;
                    ng3 = this;
                    this.C.a("X\u00ed H\u1ee5t", ng2.G.n() + ng4.G.p() / 2, ng3.G.o() + 20);
                    return;
                }
            }
        } else if (bl3) {
            this.j();
            if (!this.C.a()) {
                ng ng5 = this;
                ng ng6 = ng5;
                ng ng7 = this;
                ng6 = ng7;
                ng6 = this;
                this.C.a("\u0110\u1ee1 \u0111\u00f2n", ng5.G.n() + ng7.G.p() / 2, ng6.G.o() + 20);
                return;
            }
        } else {
            this.a(3);
        }
    }

    public final void d() {
        this.a(4);
    }

    public final void a(boolean bl2) {
        this.F.t = bl2;
        this.F.d(2);
        this.a(6);
    }

    public final void e() {
        this.F.d(0);
        this.a(6);
    }

    public final int c() {
        return this.E;
    }

    public final void a(int n2) {
        this.v = 0;
        me me2 = this.G;
        this.E = n2;
        if (this.O != null) {
            this.O.d(0);
        }
        if (n2 == 0) {
            this.G = this.a;
        } else if (n2 == 1) {
            this.G = this.b;
            if (this.O != null) {
                this.O.d(2);
            }
        } else if (n2 == 3) {
            this.G = this.e;
        } else if (n2 == 4) {
            this.G = this.f;
        } else if (n2 == 6) {
            this.G = this.F;
        } else if (n2 == 7) {
            this.G = this.i ? this.c : this.d;
        } else if (n2 == 8) {
            this.G = this.e;
        } else {
            this.G = this.i ? this.c : this.d;
            if (this.O != null) {
                this.O.d(1);
            }
        }
        this.G.r();
        this.G.c(me2.n(), me2.o());
        this.G.c(me2.g());
        if (n2 == 0) {
            n2 = cy.a(me.u[0].length);
            this.G.a(n2);
        }
    }

    public final int n() {
        return this.G.n();
    }

    public final int o() {
        return this.G.o();
    }

    public final int p() {
        return this.G.p();
    }

    public final int q() {
        return this.G.q();
    }

    public final void c(int n2, int n3) {
        this.G.c(n2, n3 - this.G.q());
        this.j = n2;
        this.P = this.G.o();
        this.J = n3 - 2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        this.z.b(graphics);
        graphics.drawImage(this.K, n2 + this.G.n() + this.G.p() / 2 + (this.G.g() == 2 ? -4 : 4), this.J, 17);
        this.G.a(graphics, n2, n3);
        this.z.c(graphics);
        this.C.a(graphics, n2, n3);
        this.A.a(graphics);
        this.B.a(graphics);
    }

    public final void i() {
        if (!this.r) {
            return;
        }
        if (this.t > 0) {
            --this.t;
        }
        if (this.v > 0) {
            --this.v;
            if (this.v == 0) {
                this.a(this.u);
            }
        }
        this.z.i();
        this.A.i();
        this.B.i();
        switch (this.E) {
            case 1: {
                if (this.G.f() == this.G.e()[this.G.h()].length - 6) {
                    if (this.x) {
                        ak.a().a(20);
                    }
                    this.x = false;
                    this.g.a(this.w, this.y);
                }
                if (!this.G.j() || this.t != 0) break;
                --this.t;
                this.s = this.j;
                this.M = this.P;
                this.I = 0;
                this.k = ax.a(this.s, this.G.n(), 2);
                this.N = ax.a(this.M, this.G.o(), 2);
                this.i = false;
                this.a(5);
                break;
            }
            case 2: {
                if (this.i) {
                    if (!this.G.b(this.s, this.M, this.k, this.N)) break;
                    this.a(1);
                    break;
                }
                this.G.g(this.P + this.H[this.I++]);
                if (!this.G.e(this.j, this.k)) break;
                this.G.g(this.P);
                this.a(0);
                break;
            }
            case 5: {
                this.G.g(this.P + this.H[this.I++]);
                if (!this.G.e(this.j, this.k)) break;
                this.G.g(this.P);
                this.a(0);
                break;
            }
            case 3: {
                if (!this.G.j()) break;
                this.a(0);
                break;
            }
            case 6: {
                int n2 = this.F.h();
                if (n2 != 2 || !this.F.j()) break;
                this.a(0);
                break;
            }
            case 7: {
                if (this.i) {
                    if (!this.G.e(this.s, this.k)) break;
                    this.a(0);
                    this.L = -1000;
                    break;
                }
                if (this.G.e(this.s, this.k)) {
                    this.i = true;
                    this.a(7);
                    this.s = this.L;
                    this.k = 5;
                    break;
                }
                if (this.k <= 2) break;
                this.k -= 2;
            }
        }
        this.G.i();
        this.C.b();
        this.D.a = this.G.n();
        this.D.b = this.G.o();
    }
}

