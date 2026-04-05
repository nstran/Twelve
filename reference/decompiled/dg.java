/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class dg
extends au {
    public static final Image i = f.d("/broken_heart");
    public int j = 0;
    private Image l = null;
    public Object k;
    private byte m = 0;
    private byte n;
    private int o = 3;
    private int p;
    private int q;
    private int r = 3;
    private d s;

    public dg(Image image, Object object, int n2, d d2) {
        this.k = object;
        this.s = d2;
        int n3 = n2;
        dg dg2 = this;
        this.j = n3;
        if (image != null) {
            this.l = image;
            this.d(this.l.getWidth());
            this.e(this.l.getHeight());
        } else {
            this.d(32);
            this.e(32);
        }
        if (this.k instanceof lj && (((lj)object).l == 4 || ((lj)object).l == 7)) {
            this.m = (byte)cy.a(3);
            this.r = cy.a(3, 6);
            this.p = oz.b.getWidth() / 3;
            this.q = oz.b.getHeight();
        }
    }

    public final void n() {
        this.n = (byte)(this.n + 1);
        if (this.n > this.r) {
            this.n = 0;
            this.m = (byte)(this.m + 1);
            if (this.m >= this.o) {
                this.m = 0;
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 += this.c();
        n3 += this.d();
        switch (this.j) {
            case 0: {
                if (this.l == null) {
                    return;
                }
                graphics.drawImage(this.l, n2, n3, 0);
                if (this.k != null && ((lj)this.k).n == 0) {
                    graphics.drawImage(i, n2 + i.getWidth(), n3 + this.f(), 40);
                }
                if (this.k != null && (((lj)this.k).l == 4 || ((lj)this.k).l == 7)) {
                    cz.a(graphics, oz.b, this.m * this.p, 0, this.p, this.q, n2 + this.l.getWidth(), n3, 24);
                }
                if (this.k == null || ((lj)this.k).i <= 0 || this.s == null) break;
                this.s.a(graphics, "+" + ((lj)this.k).i, n2 + 32, n3 + 32 - ca.c.a(), 2);
                return;
            }
            case 2: {
                graphics.drawImage(this.l, n2, n3, 0);
                ca.c.a(graphics, "" + 0, n2 + this.l.getWidth(), n3 + this.l.getHeight() - ca.c.a(), 2);
                return;
            }
            default: {
                oz.g(graphics, ((lk)this.k).j % 30000, n2, n3, 0);
                if (((lk)this.k).l == 1) break;
                ca.c.a(graphics, "" + ((lk)this.k).g, n2 + 32, n3 + 32 - ca.c.a(), 2);
            }
        }
    }

    public final String toString() {
        String string = "type = " + this.j + "    info =  " + this.k;
        return string;
    }
}

