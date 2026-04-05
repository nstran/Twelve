/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nb
extends aw
implements mp {
    private int s = 0;
    private int t;
    private int u;
    private int x;
    private int y;
    private boolean z;
    private static n A = new n();
    private Image[] B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;

    public final void d(int n2, int n3) {
        this.u = n2;
        this.y = 1035;
    }

    public nb(Image[] imageArray) {
        this.B = imageArray;
        this.b = this.B[0];
        this.o = this.b.getWidth() / 7;
        this.p = this.b.getHeight();
    }

    public final void a(int n2, int n3, int n4, byte by2, int n5) {
        this.s = n5;
        if (n5 > 0) {
            this.D = n2;
            this.E = n3;
            this.C = n4;
            this.F = 1;
            this.l(n5);
            this.a(by2);
            return;
        }
        this.a(n2, n3, n4, by2);
    }

    private void a(byte by2) {
        this.G = cy.a(4);
    }

    public final void a(int n2, int n3, int n4, byte by2) {
        try {
            this.c(n2, n3);
            this.a(by2);
            this.b = this.B[n4];
            this.s = 0;
            this.d(1);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            cw.a("loi  " + n4);
            return;
        }
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, byte by2) {
        this.d(n2, n3, n4, n5);
        this.a(by2);
        this.b = this.B[n6];
        this.s = 0;
        this.d(4);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, byte by2, int n7) {
        this.d(n2, n3, n4, n5);
        this.a(by2);
        this.b = this.B[n6];
        this.s = n7;
        this.d(3);
    }

    public final void c(int n2, int n3, int n4, int n5) {
        this.s = n5;
        if (n5 > 0) {
            this.D = n2;
            this.E = n3;
            this.C = n4;
            this.F = 2;
            this.l(n5);
            return;
        }
        this.c(n2, n3);
        this.d(2);
    }

    public final void i(int n2, int n3) {
        this.t = 28;
        this.d(5);
        this.s = n3;
    }

    private void d(int n2, int n3, int n4, int n5) {
        this.c(n2, n3);
        this.b(n4, n5);
    }

    public final void d(int n2) {
        super.d(n2);
        this.g = 0;
    }

    private void l(int n2) {
        this.s = n2;
        this.d(6);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.e == 5) {
            n2 = this.m - this.t / 2;
            n3 = this.n - this.t / 2;
            cz.a(graphics, A);
            cz.a(graphics, A, n2, n3, this.t, this.t);
            this.b(graphics);
            cz.c(graphics, A);
            return;
        }
        this.b(graphics);
    }

    private void b(Graphics graphics) {
        if (!this.r) {
            cw.a("[chess] draw enable");
            return;
        }
        if (this.b != null && this.g >= 0 && this.f[this.e][this.g] >= 0) {
            cz.a(graphics, this.b, this.f[this.e][this.g] * this.o, 0, this.o, this.p, this.m, this.n, this.q);
        }
    }

    public final void k() {
        if (this.x < this.y) {
            ++this.x;
            if (this.x == this.u) {
                this.z = true;
            }
            if (this.z && this.x == this.u + 3) {
                this.z = false;
            }
        } else {
            this.x = 0;
        }
        if (this.s > 0) {
            --this.s;
            return;
        }
        switch (this.e) {
            case 1: {
                break;
            }
            case 6: {
                this.c(this.D, this.E);
                this.b = this.B[this.C];
                this.d(this.F);
                break;
            }
            case 2: {
                if (this.g < this.f[2].length - 1) break;
                this.d(0);
                break;
            }
            case 3: {
                if (!this.f(this.i, ax.a(this.n, this.i, 2))) break;
                this.d(1);
                break;
            }
            case 4: {
                if (!this.b(this.h, this.i, ax.a(this.m, this.h, 2), ax.a(this.n, this.i, 2))) break;
                this.d(1);
                break;
            }
            case 0: {
                break;
            }
            case 5: {
                if (this.t <= 0) {
                    this.d(0);
                    return;
                }
                this.t -= 3;
                if (this.t >= 0) break;
                this.t = 0;
            }
        }
        ++this.H;
        if (this.H > 3) {
            this.H = 0;
            ++this.G;
            if (this.G > 3) {
                this.G = 0;
            }
        }
    }
}

