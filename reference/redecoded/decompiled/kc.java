/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kc
extends ke {
    private Image b;
    private int c = 0;
    private int d = 0;
    private int f = 20;
    private int g = 0;

    public kc(Image image) {
        this.b = image;
        this.m = 0;
        this.g = v.u;
        this.f = 36;
    }

    public final void i() {
        int n2 = 0;
        int n3 = this.e.d();
        while (n2 < n3) {
            ((at)this.e.b(n2)).i();
            ++n2;
        }
    }

    public final void a(Graphics graphics, int n2, int n3, k k2) {
        int n4 = this.d;
        while (n4 <= this.c) {
            graphics.drawImage(this.b, this.m + n4 * this.b.getWidth(), this.g, this.f);
            ++n4;
        }
        n4 = 0;
        int n5 = this.e.d();
        while (n4 < n5) {
            ((at)this.e.b(n4)).a(graphics, n2, n3);
            ++n4;
        }
    }

    public final void c(int n2, int n3) {
        super.c(n2, n3);
    }

    public final void f(int n2) {
        super.f(n2);
    }

    public final void g(int n2) {
        super.g(n2);
    }

    public final void a(int n2, int n3) {
        super.a(n2, n3);
        n2 = this.b.getWidth();
        this.c = (v.t - (this.m + n2)) / n2 + 1;
        if (this.m > 0) {
            n3 = this.m / n2 + (this.m % n2 > 0 ? 1 : 0);
            this.c += n3;
            this.m -= n3 * n2;
        }
        if (this.a != null) {
            n3 = this.a.c / n2 + 1;
            this.d = this.c >= n3 ? this.c - n3 : 0;
        }
    }
}

