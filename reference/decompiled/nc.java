/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nc
extends ax {
    private aw[] a;
    private int b = 3;
    private int c = 100;
    private boolean d = true;
    private int e = 35;
    private int f;
    private ad g;

    public final boolean a() {
        return this.d;
    }

    public nc(Image image) {
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[3];
        byArray[1] = 1;
        byArray[2] = 2;
        byArrayArray[0] = byArray;
        byte[][] byArrayArray2 = byArrayArray;
        this.a = new aw[20];
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = new aw(image, 3);
            this.a[n2].a(byArrayArray2);
            ++n2;
        }
        this.g = new ad();
        this.g.a(12);
        this.f = 360 / this.a.length;
    }

    public final void a(int n2, int n3, int n4, String string) {
        this.b = 3;
        this.c(n2, n3);
        this.d = true;
        this.g.a(string, n2, n3 + 3);
        this.c = this.e + (n4 - 1) * 5;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.d) {
            return;
        }
        n2 = 0;
        n3 = 0;
        while (n3 < this.a.length) {
            int n4 = this.m + (this.b * o.b(n2) >> 14);
            int n5 = this.n + (this.b * o.a(n2) >> 14);
            if (this.a[n3] != null) {
                this.a[n3].a(graphics, n4, n5);
            }
            n2 += this.f;
            ++n3;
        }
        if (this.b >= 7) {
            this.g.a(graphics, 0, 0);
        }
    }

    public final void i() {
        if (!this.d) {
            return;
        }
        int n2 = 0;
        while (n2 < this.a.length) {
            if (this.a[n2] != null) {
                this.a[n2].i();
            }
            ++n2;
        }
        this.b += 3;
        if (this.b >= this.c) {
            this.d = false;
        }
        if (this.b >= 7) {
            this.g.b();
        }
    }
}

