/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class jy
extends kg {
    public jy(Image image, jm jm2, int n2) {
        super(image, 1);
        this.d = jm2;
        if (jm2.d / 32 < oa.c.e / 2) {
            this.a(0);
        } else {
            this.a(1);
        }
        this.b(jm2.b);
        this.m = this.c == 0 ? 0 : n2 - this.o;
        this.n = jm2.e + (jm2.g - this.p) / 2;
    }

    protected final void a(String string) {
        int n2 = bx.b.a(string);
        int n3 = 1 + n2 / 32;
        this.o = n3 << 5;
        --n3;
        if (this.o <= 32) {
            this.o += 32;
        }
        this.p = 32;
        if (this.a == null) {
            int n4;
            int n5 = 0;
            this.a = Image.createImage((int)this.o, (int)32);
            Graphics graphics = this.a.getGraphics();
            graphics.setColor(65280);
            graphics.fillRect(0, 0, this.o, 32);
            if (this.c == 1) {
                graphics.drawRegion(this.f, 32, 0, 32, 32, 0, this.o, 0, 24);
                graphics.drawRegion(this.f, 0, 0, 32, 32, 0, 0, 0, 20);
                n4 = 0;
                this.m = this.m - this.o + 32;
            } else {
                n5 = 2;
                graphics.drawRegion(this.f, 32, 0, 32, 32, 2, 0, 0, 20);
                graphics.drawRegion(this.f, 0, 0, 32, 32, 2, this.o, 0, 24);
                n4 = 32;
            }
            int n6 = 0;
            while (n6 < n3) {
                graphics.drawRegion(this.f, 0, 0, 32, 32, n5, n4, 0, 20);
                n4 += 32;
                ++n6;
            }
            bx.b.a(graphics, string, this.o >> 1, 5, 1);
            this.a = ki.a(this.a, 65280);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.a != null) {
            graphics.drawImage(this.a, this.m + n2, this.n + n3, this.q);
        }
    }

    public final void c(int n2, int n3) {
        super.c(n2, n3);
    }
}

