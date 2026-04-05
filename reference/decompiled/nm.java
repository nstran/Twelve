/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nm
extends aw {
    private int s = 30;
    private int t = 40;
    private int u;
    private int v;
    private int w;
    private n x;
    private boolean y;

    public nm() {
        this.q = 3;
    }

    public final void a(int n2, int n3, n n4, Image image, int n5, int n6, int n7, boolean n8) {
        this.y = n8;
        this.x = n4;
        n8 = n4.c >> 1;
        int n9 = n4.d >> 1;
        n8 = n8 <= 0 ? 1 : n8;
        n9 = n9 <= 0 ? 1 : n9;
        int n10 = n4.a + cy.a() % n8;
        int n11 = n4.b + cy.a() % n9;
        n9 = n7;
        n8 = n6;
        n7 = n5;
        Image image2 = image;
        n5 = n11;
        int n12 = n10;
        int n13 = n3 + cy.a() % 13;
        n3 = n2 + cy.a() % 13;
        nm nm2 = this;
        nm2.c(n3, n13);
        nm2.h = n12;
        nm2.i = n5;
        nm2.r = true;
        nm2.j = ax.a(nm2.m, n12, nm2.s) + cy.a(4);
        nm2.k = ax.a(nm2.n, n5, nm2.t) + cy.a(4);
        nm2.b = image2;
        nm2.u = n7;
        nm2.o = n8;
        nm2.p = n9;
    }

    public final void k() {
        if (this.y) {
            if (this.v != this.x.a) {
                this.h = this.x.a + this.x.c / 2 + cy.a() % (this.x.c >> 1);
                this.v = this.x.a;
            }
            if (this.w != this.x.b) {
                this.i = this.x.b + this.x.d / 2 + cy.a() % (this.x.d >> 1);
                this.w = this.x.b;
            }
        }
        if (this.b(this.h, this.i, this.j, this.k)) {
            this.r = false;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.r && this.b != null && this.g >= 0 && this.f[this.e][this.g] >= 0) {
            cz.a(graphics, this.b, this.f[this.e][this.g] * this.o, this.u, this.o, this.p, this.m + n2, this.n + n3, this.q);
        }
    }
}

