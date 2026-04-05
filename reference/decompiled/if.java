/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class if
extends at {
    private final Image b = f.d("/corner/3");
    public static Image a;
    private final Image c;

    public if() {
        a = f.d("/corner/cornerskb");
        this.c = f.d("/corner/1");
    }

    public final bd a(int n2) {
        return new ga(-90000, 0);
    }

    public final bd b(int n2) {
        return new ga(-90001, 2);
    }

    public final bd c(int n2) {
        return new ga(-90002, 3);
    }

    public final void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(3236863);
        graphics.drawRect(n2, n3, n4 - 1, n5 - 1);
        graphics.setColor(0xFBFBFB);
        graphics.drawRect(n2 + 1, n3 + 1, n4 - 3, n5 - 3);
        graphics.setColor(16512242);
        graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, n5 - 4);
        graphics.setColor(3968767);
        graphics.drawRect(n2 + 2, n3 + 2, n4 - 5, n5 - 5);
        cz.a(graphics, this.b, 0, 2, 8, 8, n2, n3, 0);
        graphics.drawRegion(this.b, 0, 2, 8, 8, 6, n2, n3 + n5, 36);
        graphics.drawRegion(this.b, 0, 2, 8, 8, 1, n2, n3 + n5, 36);
        graphics.drawRegion(this.b, 0, 2, 8, 8, 5, n2 + n4, n3, 24);
        graphics.drawRegion(this.b, 0, 2, 8, 8, 3, n2 + n4, n3 + n5, 40);
    }

    public final void c(Graphics graphics, int n2, int n3, int n4, int n5) {
        n5 = a.getHeight();
        int n6 = n4 - 18 + 1;
        int n7 = n2 + 9;
        int n8 = 35;
        while (n6 > 0) {
            if (n8 > n6) {
                n8 = n6;
            }
            graphics.drawRegion(a, 9, 0, n8, n5, 0, n7, n3, 0);
            n6 -= n8;
            n7 += n8;
        }
        graphics.drawRegion(a, 0, 0, 9, n5, 0, n2, n3, 0);
        graphics.drawRegion(a, 0, 0, 9, n5, 2, n2 + n4 - 9, n3, 0);
    }

    public final void d(Graphics graphics, int n2, int n3, int n4, int n5) {
        n5 = this.c.getHeight();
        int n6 = n4 - 18 + 1;
        int n7 = n2 + 9;
        int n8 = 35;
        while (n6 > 0) {
            if (n8 > n6) {
                n8 = n6;
            }
            graphics.drawRegion(this.c, 9, 0, n8, n5, 0, n7, n3, 0);
            n6 -= n8;
            n7 += n8;
        }
        graphics.drawRegion(this.c, 0, 0, 9, n5, 0, n2, n3, 0);
        graphics.drawRegion(this.c, 0, 0, 9, n5, 2, n2 + n4, n3, 24);
    }
}

