/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kf
extends kb {
    private Image b;
    private byte[][] c;
    private byte[][] d;
    private byte e = (byte)32;
    private byte f = (byte)32;
    private int g = 0;
    private int h = 0;
    private int i = 0;

    public kf(Image image, byte[][] byArray, byte by2, byte by3) {
        Object object = image;
        kf kf2 = this;
        this.b = object;
        object = byArray;
        kf2 = this;
        this.c = (byte[][])object;
        kf2.g = ((Image)object).length;
        kf2.h = ((Image)object[0]).length;
        this.i = image.getWidth() / 32;
        this.p = byArray.length * this.e;
        this.o = byArray[0].length * this.e;
    }

    public final void a(k k2) {
        super.a(k2);
        this.c(k2.a, k2.b);
    }

    public final void a(Graphics graphics) {
    }

    public final void a(Graphics graphics, int n2, int n3, k k2) {
        int n4;
        int n5;
        int n6 = k2.a / this.e - 1;
        if (n6 < 0) {
            n6 = 0;
        }
        if ((n5 = k2.b / this.f - 1) < 0) {
            n5 = 0;
        }
        n4 = (n4 = (k2.a + k2.c) / this.e + 1) > this.h ? this.h : n4;
        int n7 = (k2.b + k2.d) / this.f + 1;
        n7 = n7 > this.g ? this.g : n7;
        int n8 = n5;
        while (n8 < n7) {
            int n9 = n6;
            while (n9 < n4) {
                n5 = (this.c[n8][n9] & 0xFF) - 1;
                if (n5 >= 0) {
                    cw.a(graphics, this.b, n5 % this.i * this.e, n5 / this.i * this.f, (int)this.e, (int)this.f, n9 * this.e + n2, n8 * this.f + n3, 0);
                }
                ++n9;
            }
            ++n8;
        }
    }

    public final void i() {
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final byte b(int n2, int n3) {
        if (this.d == null || n3 < 0 || n2 < 0 || n2 >= this.g || n3 >= this.h) {
            return 0;
        }
        return this.d[n2][n3];
    }

    public final void a(byte[][] byArray) {
        this.d = byArray;
    }

    public final void b() {
        this.c = null;
        this.d = null;
        this.b = null;
    }
}

