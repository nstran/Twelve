/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class hh
extends ax {
    private int a;
    private int b;
    private int c;
    private Image d;
    private int e = 1;
    private boolean f;
    private boolean g = true;
    private int h = 3;
    private int i = 0;
    private int[] j;
    private int[] k;

    public hh(int n2, int n3) {
        this.m = n2;
        this.n = n3;
        this.d = oz.b;
        this.a = this.d.getWidth() / 3;
        this.b = this.d.getHeight();
        this.a(1);
    }

    public final void i() {
        if (!this.g) {
            if (!this.f) {
                ++this.c;
                this.i = 0;
                if (this.c >= 2) {
                    this.c = 2;
                    this.f = true;
                }
            } else {
                ++this.i;
                if (this.i > 1) {
                    this.e += 12;
                    --this.c;
                    if (this.c < 0) {
                        this.c = 0;
                        if (this.h == 0) {
                            this.g = true;
                        } else {
                            --this.h;
                            this.f = false;
                            this.e = 1;
                        }
                    }
                    this.i = 0;
                }
            }
        }
        this.a(this.e);
    }

    private void a(int n2) {
        n2 /= 2;
        int n3 = this.m;
        int n4 = this.n;
        int n5 = -90;
        this.j = new int[8];
        this.k = new int[8];
        int n6 = 0;
        while (n6 < 8) {
            this.j[n6] = n3 + (n2 * o.b(n5) >> 14);
            this.k[n6] = n4 + (n2 * o.a(n5) >> 14);
            n5 += 45;
            ++n6;
        }
    }

    public final void a() {
        this.g = false;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.g) {
            return;
        }
        int n4 = 0;
        while (n4 < this.j.length) {
            cz.a(graphics, this.d, this.c * this.a, 0, this.a, this.b, this.j[n4] + n2, this.k[n4] + n3, 3);
            ++n4;
        }
    }
}

