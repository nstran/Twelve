/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mg {
    private boolean d = false;
    private long e = 0L;
    private long f = 0L;
    int a = 0;
    private long g;
    int b;
    int c;

    public mg(int n2, int n3) {
        this.a = n2;
        this.g = n3;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.e = n2;
        this.f = n3;
        this.g = n4;
        this.d = true;
        this.c = n5;
    }

    public final void a() {
        if (!this.d) {
            return;
        }
        long l2 = this.f - this.e;
        if (l2 < 0L) {
            if (Math.abs(l2) >= 6L) {
                l2 /= 6L;
            }
            this.e += l2;
        } else if (l2 > 0L) {
            if (l2 <= this.g * 30L / 100L || l2 == this.g) {
                if (Math.abs(l2) >= 6L) {
                    l2 /= 6L;
                }
                this.e += l2;
            } else {
                this.e += l2;
            }
        } else {
            this.d = false;
        }
        this.b = (int)(this.e * (long)this.a / this.g);
        if (this.b > this.a) {
            this.b = this.a;
            return;
        }
        if (this.b == 0 && this.e > 0L) {
            this.b = 1;
        }
    }

    public final void a(Graphics graphics, Image image, int n2, int n3, int n4) {
        if (n4 == 0) {
            mg mg2 = this;
            int n5 = mg2.b;
            int n6 = image.getHeight();
            cz.a(graphics, image, 0, 0, n5, n6, n2, n3, 0);
            return;
        }
        mg mg3 = this;
        int n7 = mg3.b;
        int n8 = image.getWidth();
        int n9 = image.getHeight() - n7;
        cz.a(graphics, image, 0, n9, n8, n7, n2, n3 + n9, 0);
    }
}

