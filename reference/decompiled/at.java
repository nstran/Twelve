/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class at {
    private Image a = f.d("/_corner");
    private int b = z.af & 0xFFFFFF;
    private int c;

    public at() {
        int n2 = this.b >> 16 & 0xFF;
        int n3 = this.b >> 8 & 0xFF;
        int n4 = this.b & 0xFF;
        this.c = (255 - n2) / 6 + n2 << 16 | (255 - n3) / 6 + n3 << 8 | (255 - n4) / 6 + n4;
    }

    public bd a(int n2) {
        return new bh("Menu", -90000);
    }

    public bd b(int n2) {
        return new bh("Ch\u1ecdn", -90001);
    }

    public bd c(int n2) {
        return new bh("Tr\u1edf v\u1ec1", -90002);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(z.ac);
        graphics.fillRect(n2 + 3, n3 + 3, n4 - 6, n5 - 6);
        graphics.setColor(z.ae);
        graphics.drawRect(n2 + 2, n3 + 2, n4 - 5, n5 - 5);
        graphics.setColor(z.ad);
        graphics.drawRect(n2 + 1, n3 + 1, n4 - 3, n5 - 3);
        int n6 = this.a.getWidth();
        int n7 = this.a.getHeight();
        graphics.drawRegion(this.a, 0, 0, n6, n7, 0, n2, n3, 20);
        graphics.drawRegion(this.a, 0, 0, n6, n7, 2, n2 + n4, n3, 24);
        graphics.drawRegion(this.a, 0, 0, n6, n7, 1, n2, n3 + n5, 36);
        graphics.drawRegion(this.a, 0, 0, n6, n7, 3, n2 + n4, n3 + n5, 40);
    }

    public void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(0xFF0000);
        graphics.fillRect(n2, n3, n4, n5);
    }

    public void c(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(this.c);
        graphics.drawRect(n2, n3 + 1, n4 - 1, n5 - 1);
        graphics.setColor(this.b);
        graphics.fillRect(n2 + 1, n3 + 2, n4 - 2, n5 - 2);
    }

    public void d(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(this.c);
        graphics.drawRect(n2, n3 + 1, n4 - 1, n5 - 1);
        graphics.setColor(this.b);
        graphics.fillRect(n2 + 1, n3 + 2, n4 - 2, n5 - 2);
    }
}

