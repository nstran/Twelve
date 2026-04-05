/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class in
extends ax {
    private mr a;
    private int b;
    private int c = 0;
    private ln[] d;

    public in(int n2, mr mr2) {
        this.a = mr2;
        this.b = n2;
        this.r = false;
    }

    public final void a(int n2, ng ng2, int n3, int n4) {
        int n5 = n4 + 30;
        if (this.d == null) {
            this.d = new ln[6];
            n4 = 0;
            while (n4 < this.d.length) {
                this.d[n4] = new ln(n2);
                ++n4;
            }
        }
        n4 = 0;
        while (n4 < 3) {
            this.d[n4 << 1].b(n3 - 10 + cy.a() % 5, n5 + cy.a() % 10, cy.a(6) + 25);
            this.d[(n4 << 1) + 1].b(n3 + 10 + cy.a() % 5, n5 + cy.a() % 10, cy.a(6) + 25);
            n5 -= 25;
            ++n4;
        }
        switch (n2) {
            case 0: {
                mn.a().a(1003);
                break;
            }
            case 1: {
                mn.a().a(2005);
                break;
            }
            case 2: {
                mn.a().a(4004);
            }
        }
        this.c = 10;
        this.r = true;
    }

    public final void a() {
        this.r = false;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        int n4 = 0;
        while (n4 < this.d.length) {
            this.d[n4].a(graphics, n2, n3);
            ++n4;
        }
    }

    public final void i() {
        if (this.r) {
            if (this.c > 0) {
                --this.c;
                if (this.c == 0) {
                    this.a.a((this.b + 1) % 2, 34, 24, true);
                }
            }
            int n2 = 0;
            while (n2 < this.d.length) {
                this.d[n2].i();
                ++n2;
            }
        }
    }
}

