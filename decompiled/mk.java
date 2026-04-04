/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mk
extends ax {
    private Image a;
    private int b;
    private int c;
    private int d;

    public mk() {
        this.a = mn.a().e[0];
    }

    public final void b(int n2, int n3, int n4) {
        this.c(n2, n3);
        this.b = -1;
        this.c = n4;
    }

    public final void a() {
        this.b = 0;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.b != 0) {
            n2 = 8;
            if (this.d >= 3) {
                ++n2;
            }
            if ((this.c & 1) == 1) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 6, this.m - n2, this.n, 10);
            }
            if ((this.c & 2) == 2) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 5, this.m + n2, this.n, 6);
            }
            if ((this.c & 4) == 4) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 0, this.m, this.n - n2, 33);
            }
            if ((this.c & 8) == 8) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 3, this.m, this.n + n2, 17);
            }
        }
    }

    public final void i() {
        if (this.b != 0) {
            if (this.b > 0) {
                --this.b;
            }
            ++this.d;
            if (this.d > 6) {
                this.d = 0;
            }
        }
    }
}

