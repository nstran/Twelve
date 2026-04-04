/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class it
extends il {
    private static byte[][] s;
    private ln[] t;
    private int u;
    private int v = 0;

    static {
        byte[][] byArrayArray = new byte[2][];
        byArrayArray[0] = new byte[]{-1};
        byte[] byArray = new byte[16];
        byArray[3] = 1;
        byArray[4] = 1;
        byArray[5] = 2;
        byArray[6] = 2;
        byArray[7] = 3;
        byArray[8] = 3;
        byArray[9] = 4;
        byArray[10] = 4;
        byArray[11] = 5;
        byArray[12] = 5;
        byArray[13] = 6;
        byArray[14] = 6;
        byArray[15] = 6;
        byArrayArray[1] = byArray;
        s = byArrayArray;
    }

    public it() {
        mn.a().a(1004);
        this.a(mn.a().o, 7);
        this.a(s);
        this.t = new ln[6];
        int n2 = 0;
        while (n2 < this.t.length) {
            this.t[n2] = new ln(0);
            ++n2;
        }
        this.r = false;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.r = true;
        if (n4 >= z.r / 2) {
            this.c(0);
            this.j(40);
            this.b(n4 + 20, n5);
            this.c(this.h - this.o - 20, n5);
            this.u = n4;
        } else {
            this.c(2);
            this.j(36);
            this.b(n4 - 20, n5);
            this.c(this.h + this.o + 20, n5);
            this.u = n4;
        }
        this.f(this.h);
        this.v = 1;
        this.d(1);
    }

    public final void k() {
        int n2;
        int n3 = 0;
        while (n3 < this.t.length) {
            this.t[n3].i();
            ++n3;
        }
        if (this.e == 1) {
            if (this.v == 0) {
                if (this.j()) {
                    ++this.v;
                    this.f(this.h);
                    this.a(-1);
                }
            } else {
                if (this.g == s[1].length - 5) {
                    n3 = this.o() - 10;
                    n2 = 0;
                    while (n2 < 3) {
                        this.t[n2 << 1].b(this.u - 10 + cy.a() % 5, n3 + cy.a() % 10, cy.a(6));
                        this.t[(n2 << 1) + 1].b(this.u + 10 + cy.a() % 5, n3 + cy.a() % 10, cy.a(6));
                        n3 -= 25;
                        ++n2;
                    }
                }
                if (this.j()) {
                    this.d(0);
                }
            }
        }
        if (this.e == 0) {
            n3 = 0;
            n2 = 0;
            while (n2 < this.t.length) {
                if (this.t[n2].m()) {
                    n3 = 1;
                    break;
                }
                ++n2;
            }
            if (n3 == 0) {
                this.r = false;
            }
        }
    }

    public final void a(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.t.length) {
            this.t[n2].a(graphics);
            ++n2;
        }
        super.a(graphics);
    }
}

