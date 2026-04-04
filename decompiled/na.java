/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class na
extends aw {
    private boolean s = false;
    private int t;
    private int u;

    public na(Image image, int n2, int n3, int n4) {
        this.b = image;
        this.o = n2;
        this.p = n3;
        this.u = n4;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.c(n2, n3);
        this.h = n4;
        this.i = n5;
        this.r = true;
        this.j = ax.a(this.m, n4, 20) + cy.a(3);
        this.k = ax.a(this.n, n5, 20) + cy.a(3);
        this.s = false;
        this.t = n6;
    }

    public final void k() {
        if (this.t > 0) {
            --this.t;
            return;
        }
        if (this.s) {
            ++this.k;
            ++this.j;
        } else {
            this.j = ax.a(this.m, this.h, 4) + cy.a(2);
            this.k = ax.a(this.n, this.i, 8) + cy.a(2);
        }
        if (this.b(this.h, this.i, this.j, this.k)) {
            if (this.s) {
                this.r = false;
                return;
            }
            this.h = this.m + cy.a(20);
            this.i = this.n + 40 + cy.a(100);
            this.j = ax.a(this.m, this.h, 14) + cy.a(2);
            this.k = ax.a(this.n, this.i, 10) + cy.a(2);
            this.s = true;
            this.b(this.h, this.i, this.j, this.k);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r || this.t > 0) {
            return;
        }
        if (this.g >= 0 && this.f[this.e][this.g] >= 0 && this.b != null) {
            cz.a(graphics, this.b, this.f[this.e][this.g] * this.o, this.u, this.o, this.p, this.m + n2, this.n + n3, this.q);
        }
    }
}

