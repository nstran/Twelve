/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ke
extends ka {
    private Image b;
    private byte[][] c;
    private byte[][] d;
    private byte e = (byte)32;
    private byte f = (byte)32;
    private int g = 0;
    private int h = 0;
    private int i = 0;

    public ke(Image image, byte[][] byArray, byte by2, byte by3) {
        Object object = image;
        ke ke2 = this;
        this.b = object;
        object = byArray;
        ke2 = this;
        this.c = (byte[][])object;
        ke2.g = ((Image)object).length;
        ke2.h = ((Image)object[0]).length;
        this.i = image.getWidth() / 32;
        this.p = byArray.length * this.e;
        this.o = byArray[0].length * this.e;
    }

    public final void a(n n2) {
        super.a(n2);
        this.c(n2.a, n2.b);
    }

    public final void a(Graphics graphics) {
    }

    public final void a(Graphics graphics, int n2, int n3, n n4) {
        int n5;
        int n6;
        int n7 = n4.a / this.e - 1;
        if (n7 < 0) {
            n7 = 0;
        }
        if ((n6 = n4.b / this.f - 1) < 0) {
            n6 = 0;
        }
        n5 = (n5 = (n4.a + n4.c) / this.e + 1) > this.h ? this.h : n5;
        int n8 = (n4.b + n4.d) / this.f + 1;
        n8 = n8 > this.g ? this.g : n8;
        int n9 = 0;
        while (n6 < n8) {
            int n10 = n7;
            while (n10 < n5) {
                n9 = (this.c[n6][n10] & 0xFF) - 1;
                if (n9 >= 0) {
                    cz.a(graphics, this.b, n9 % this.i * this.e, n9 / this.i * this.f, (int)this.e, (int)this.f, n10 * this.e + n2, n6 * this.f + n3, 0);
                }
                ++n10;
            }
            ++n6;
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

