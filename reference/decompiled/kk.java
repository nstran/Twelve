/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class kk
extends ax {
    public int a;
    public static final byte[] b;
    public static final byte[] c;
    private me y = null;
    public me d;
    private me z;
    public me e;
    private me A;
    public mb f;
    public me g;
    private int B = 0;
    public int h = 4;
    public int i = -1;
    private int C = -1;
    public int j = 0;
    public int k;
    public n s = new n(this.m, this.n, this.E, 32);
    public n t = new n(this.m, this.n, 26, 32);
    private int D = 0;
    private int E = 17;
    public boolean u = false;
    private lf F;
    private int G;
    private int H;
    private n I = null;
    private int J;
    public int v = 0;
    public int w = 50;
    private boolean K = true;
    private int L = 0;
    private int M;
    private int N = 0;
    public boolean x = false;
    private np O;
    private static final byte[] P;
    private int Q;
    private int R;

    static {
        byte[] byArray = new byte[11];
        byArray[4] = -1;
        byArray[5] = -1;
        byArray[6] = -1;
        byArray[8] = 1;
        byArray[9] = 1;
        byArray[10] = 1;
        b = byArray;
        byte[] byArray2 = new byte[11];
        byArray2[1] = -1;
        byArray2[2] = 1;
        byArray2[5] = -1;
        byArray2[6] = 1;
        byArray2[9] = -1;
        byArray2[10] = 1;
        c = byArray2;
        P = new byte[]{-1, 1};
    }

    public kk() {
        this.b(false);
        this.e(10);
    }

    public final void a(lf lf2) {
        this.F = lf2;
        this.x = lf2.af;
        lz[] lzArray = lz.a(this.F);
        this.d = null;
        this.z = null;
        this.e = null;
        this.A = null;
        this.g = null;
        this.f = null;
        System.gc();
        la la2 = la.a(this.F);
        this.O = np.a(this.F);
        this.d = lz.a(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
        this.d.a(la2);
        this.d.a(this.O);
        this.z = lz.b(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
        this.z.a(la2);
        this.z.a(this.O);
        this.e = lz.c(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
        this.e.a(la2);
        this.e.a(this.O);
        this.A = lz.g(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
        this.A.a(la2);
        this.g = lz.h(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
        this.g.a(la2);
        this.f = lz.i(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
        this.f.a(la2);
        this.G = -this.s.c;
        this.H = this.s.d - this.d.q();
        this.o = this.d.p();
        this.p = this.d.q();
        this.y = this.d;
        this.J = this.y.q() - this.s.d;
        this.I = new n(this.s.a, this.s.b - this.J, this.s.c, this.y.q());
        this.a(0, 8);
        this.b(this.F);
    }

    public final void b(lf lf2) {
        this.h = 4 + lf2.G / 10;
        if (this.h > 9) {
            this.h = 9;
        }
        this.a = 11 + lf2.G / 10;
        if (this.a > 16) {
            this.a = 16;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.K && this.y != null) {
            this.y.a(graphics, this.s.a + this.G + this.L + n2, this.s.b + this.H + n3);
        }
    }

    public final void i() {
        block21: {
            int n2;
            block20: {
                kk kk2;
                block19: {
                    if (this.w > 0) {
                        --this.w;
                        boolean bl2 = this.K = !this.K;
                        if (this.w == 0) {
                            this.K = true;
                            this.b(true);
                        }
                    }
                    if (this.N > 0) {
                        --this.N;
                        if (this.N == 0) {
                            this.h = this.h;
                            this.M = 0;
                        }
                    }
                    switch (this.i) {
                        case 5: {
                            if (this.y.h() != 0 || !this.y.j()) break;
                            this.y.d(1);
                            break;
                        }
                        case 6: {
                            if (this.y.h() != 0 || !this.y.j()) break;
                            this.y.d(1);
                            break;
                        }
                        case 7: {
                            break;
                        }
                        case 0: {
                            if (!this.x) break;
                            ++this.R;
                            if (this.R <= 10) break;
                            this.H += P[this.Q];
                            ++this.Q;
                            if (this.Q > P.length - 1) {
                                this.Q = 0;
                            }
                            this.R = 0;
                            break;
                        }
                        case 2: {
                            if (this.u) break;
                            return;
                        }
                        case 8: {
                            kk kk3 = this;
                            if (kk3.y.j()) {
                                this.a(2);
                            }
                            int n3 = this.B;
                            this.B = this.y.f();
                            if (n3 == this.B) break;
                            this.s.b += 3 * c[this.j];
                            break;
                        }
                        case 4: {
                            kk kk4 = this;
                            if (!kk4.y.j()) break;
                            this.a(0);
                        }
                    }
                    if (this.i != 2 && this.i != 8 && this.i != 3) break block19;
                    this.m = this.s.a - (this.o - this.s.c) / 2;
                    this.n = this.s.b - (this.p - this.s.d);
                    kk2 = this;
                    n2 = -2;
                    break block20;
                }
                this.m = this.s.a;
                this.n = this.s.b - (this.p - this.s.d);
                if (this.y == null) break block21;
                kk2 = this;
                n2 = this.y.g() == 0 ? -4 : 2;
            }
            kk2.L = n2;
        }
        this.I.b = this.s.b - this.J;
        this.I.a = this.s.a;
        if (this.y != null) {
            this.y.i();
        }
    }

    public final void a(int n2, int n3) {
        this.b(n3);
        this.a(n2);
    }

    public final void a(int n2) {
        this.x = this.F.af;
        this.C = this.i;
        me me2 = this.y;
        this.i = n2;
        if (this.O != null) {
            this.O.d(0);
        }
        switch (n2) {
            case 1: {
                if (this.C != this.i) {
                    this.y = this.z;
                    if (me2 != null) {
                        this.y.c(me2.g());
                    }
                }
                if (this.O == null) break;
                this.O.d(1);
                break;
            }
            case 6: {
                this.y = this.g;
                if (me2 != null) {
                    this.y.c(me2.g());
                }
                this.y.d(0);
                this.k = 1;
                break;
            }
            case 7: {
                this.y = this.g;
                this.y.d(2);
                this.B = 0;
                break;
            }
            case 5: {
                this.y = this.A;
                if (me2 != null) {
                    this.y.c(me2.g());
                }
                this.y.d(0);
                this.k = this.a;
                this.v = 0;
                break;
            }
            case 0: {
                this.y = this.d;
                if (me2 == null) break;
                this.y.c(me2.g());
                break;
            }
            case 2: {
                if (this.y != this.f) {
                    this.y = this.f;
                }
                this.y.d(1);
                break;
            }
            case 3: {
                this.y.d(0);
                if (me2 == null) break;
                this.y.c(me2.g());
                break;
            }
            case 8: {
                this.y = this.f;
                this.y.d(1);
                if (me2 == null) break;
                this.y.c(me2.g());
                break;
            }
            case 4: {
                this.b(true);
                this.y = this.e;
                this.y.a(0);
                if (me2 != null) {
                    this.y.c(me2.g());
                }
                this.t.b = this.s.b;
                this.t.a = this.y.g() == 0 ? this.s.a - this.t.c : this.s.a + this.s.c;
                if (this.O == null) break;
                this.O.d(2);
            }
        }
        this.y.r();
        if (this.y.f() < 0) {
            this.y.i();
        }
    }

    public final void b(int n2) {
        if (this.y != null) {
            this.y.c((n2 & 8) != 0 ? 2 : ((n2 & 4) != 0 ? 0 : this.y.g()));
        }
        this.j = n2;
    }

    public final boolean a(n n2) {
        return this.s.a(n2);
    }

    public final boolean a() {
        return this.y.j();
    }

    public final int b() {
        return this.i;
    }

    public final void b(int n2, int n3) {
        this.s.a += n2;
        this.s.b += n3;
    }

    public final void c(int n2, int n3) {
        this.s.a = n2;
        this.s.b = n3;
        this.m = this.s.a;
        this.n = this.s.b - (this.p - this.s.d);
    }

    public final void g(int n2) {
        this.s.b = n2;
        this.n = this.s.b - (this.p - this.s.d);
    }

    public final void f(int n2) {
        this.m = this.s.a = n2;
    }

    public final int n() {
        return this.s.a - (this.o - this.s.c) / 2;
    }

    public final int o() {
        return this.s.b + this.H;
    }
}

