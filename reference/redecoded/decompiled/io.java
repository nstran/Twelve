/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class io
extends at {
    private mt a;
    private int b;
    private int c = 0;
    private lp[] d;

    public io(int n2, mt mt2) {
        this.a = mt2;
        this.b = n2;
        this.r = false;
    }

    public final void a(int n2, ni ni2, int n3, int n4) {
        int n5 = n4 + 30;
        if (this.d == null) {
            this.d = new lp[6];
            n4 = 0;
            while (n4 < this.d.length) {
                this.d[n4] = new lp(n2);
                ++n4;
            }
        }
        n4 = 0;
        while (n4 < 3) {
            this.d[n4 << 1].b(n3 - 10 + cv.a() % 5, n5 + cv.a() % 10, cv.a(6) + 25);
            this.d[(n4 << 1) + 1].b(n3 + 10 + cv.a() % 5, n5 + cv.a() % 10, cv.a(6) + 25);
            n5 -= 25;
            ++n4;
        }
        switch (n2) {
            case 0: {
                mp.a().a(1003);
                break;
            }
            case 1: {
                mp.a().a(2005);
                break;
            }
            case 2: {
                mp.a().a(4004);
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

