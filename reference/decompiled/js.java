/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class js
extends ax {
    private Image a;
    private n b;
    private n c;
    private int d = 0;
    private int e = 0;
    private int[] f = new int[]{7930113, 12386818, 13894146, 0xFD0D0D, 12911106, 10289666, 9372162};
    private int[] g = new int[]{2193665, 3456258, 3855362, 5111053, 3589378, 2858242, 2592514};

    public js(lf lf2) {
        this.b = new n(3, 2, 87, 8);
        this.c = new n(3, 12, 87, 8);
        this.a = f.d("/info/gauge");
        this.a(lf2);
        this.h(this.a.getWidth());
        this.i(this.a.getHeight());
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.a, n2, n3, 0);
        oz.a(graphics, this.b, this.d, this.f, n2, n3);
        oz.a(graphics, this.c, this.e, this.g, n2, n3);
    }

    public final void i() {
    }

    public final void a(lf lf2) {
        this.d = lf2.s * (this.b.c - 2) / lf2.r;
        int n2 = lf2.J - lf2.M;
        int n3 = lf2.N - lf2.M;
        this.e = n2 * (this.c.c - 2) / n3;
        if (this.e > this.c.c) {
            this.e = this.c.c - 2;
        }
        if (this.d > this.b.c) {
            this.d = this.b.c - 2;
        }
    }
}

