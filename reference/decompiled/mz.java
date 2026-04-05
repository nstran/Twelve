/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mz
extends ax {
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean e = false;
    private Image f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;

    public mz(Image image) {
        this.f = image;
        if (image == null) {
            return;
        }
        this.o = image.getWidth() / 6;
        this.p = image.getHeight() / 10;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.c(n2, n3);
        this.k = n6;
        this.a = n4;
        this.b = n5;
        this.c = n2;
        this.d = n3;
        n6 = 20 + cy.a(40);
        n2 = Math.abs(n2 - n4);
        n3 = Math.abs(n3 - n5);
        if (n2 > n3) {
            this.d -= n2 - n3;
            this.d -= n6;
            this.c = n4 < z.r / 2 ? (this.c += n6) : (this.c -= n6);
        } else {
            this.d -= n6;
            this.c = n4 < z.r / 2 ? (this.c += n6) : (this.c -= (n6 += n3 - n2));
        }
        this.j = n4 < z.r / 2 ? 2 : 0;
        this.i = 0;
        this.e = false;
        this.r = true;
        this.g = ax.a(this.m, this.c, 14) + cy.a(4);
        this.h = ax.a(this.n, this.d, 14) + cy.a(4);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r || this.f == null) {
            return;
        }
        graphics.drawRegion(this.f, this.i * this.o, this.k, this.o, this.p, this.j, this.m, this.n, 3);
    }

    public final void i() {
        if (!this.r) {
            return;
        }
        if (this.e) {
            if (this.i < 2) {
                ++this.i;
            }
            ++this.g;
            ++this.h;
            if (this.b(this.a, this.b, this.g, this.h)) {
                this.r = false;
                this.i = 1;
                return;
            }
        } else if (this.b(this.c, this.d, this.g, this.h)) {
            this.e = true;
            this.g = ax.a(this.m, this.a, 28);
            this.h = ax.a(this.n, this.b, 28);
        }
    }
}

