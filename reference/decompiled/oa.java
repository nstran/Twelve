/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class oa
extends ao {
    private Image c;
    private int d;
    private int k;
    private int[] l;
    private int[] m;
    private n n;
    private Image o;

    public oa() {
        this.e = -100001;
        oa oa2 = this;
        if (oa2.o == null) {
            oa2.o = f.d("/createcs/bk");
        }
        oa2.b = 0;
        oa2.c = oz.b;
        oa2.n = new n((oa2.f - 70) / 2, (oa2.g - 70) / 2, 70, 70);
        int n2 = oa2.n.a + oa2.n.c / 2;
        int n3 = oa2.n.b + oa2.n.d / 2;
        int n4 = -90;
        oa2.l = new int[8];
        oa2.m = new int[8];
        int n5 = 0;
        while (n5 < 8) {
            oa2.l[n5] = n2 + (25 * o.b(n4) >> 14);
            oa2.m[n5] = n3 + (25 * o.a(n4) >> 14);
            n4 += 45;
            ++n5;
        }
        this.a((be)null);
    }

    public final void b_() {
        try {
            super.b_();
            this.k = this.d;
            this.d = (this.d + 1) % this.l.length;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    protected final void b(Graphics graphics) {
        int n2 = 0;
        if (this.o != null && z.s > this.o.getHeight()) {
            n2 = (z.s - this.o.getHeight()) / 2;
        }
        graphics.drawImage(this.o, this.f / 2, this.g - n2, 33);
        ca.b.a(graphics, String.valueOf(this.a), this.n.a + (this.n.c >> 1), this.n.b - 4 + (this.n.d >> 1), 1);
        n2 = this.c.getWidth() / 3;
        int n3 = this.c.getHeight();
        cz.a(graphics, this.c, 0, 0, n2, n3, this.l[(this.k - 1 + this.l.length) % this.l.length], this.m[(this.k - 1 + this.l.length) % this.l.length], 3);
        cz.a(graphics, this.c, n2, 0, n2, n3, this.l[this.k], this.m[this.k], 3);
        cz.a(graphics, this.c, n2 + n2, 0, n2, n3, this.l[this.d], this.m[this.d], 3);
    }

    protected final void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, z.r, z.s);
    }
}

