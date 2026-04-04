/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class jz
extends aw {
    private byte s = 0;
    public lj v;
    public int w = 1;

    public jz(Image image, int n2) {
        this.b = image;
        this.c = n2;
        this.o = image.getWidth() / n2;
        this.p = image.getHeight();
    }

    public final void a(byte[][] byArray, int n2) {
        this.a(byArray);
        this.d = new int[byArray.length][];
        int n3 = 0;
        while (n3 < byArray.length) {
            this.d[n3] = new int[byArray[n3].length];
            int n4 = 0;
            while (n4 < byArray[n3].length) {
                this.d[n3][n4] = n2 * byArray[n3][n4];
                ++n4;
            }
            ++n3;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        if (this.b == null || this.d == null) {
            return;
        }
        cz.a(graphics, this.b, this.d[this.e][this.g], 0, this.o, this.p, this.m + n2, this.n + n3, 20);
    }

    public final void b(byte by2) {
        this.s = 1;
    }

    public final byte r() {
        return this.s;
    }

    public void a(byte by2) {
    }
}

