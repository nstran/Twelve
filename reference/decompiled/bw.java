/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bw
extends au {
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;

    public bw() {
        this(2);
    }

    public bw(int n2) {
        this(n2, 0, 100, 0, 100);
    }

    private bw(int n2, int n3, int n4, int n5, int n6) {
        this.m = n2;
        this.b(0, 100, 0, 100);
    }

    public final int a() {
        return this.j;
    }

    public final int q() {
        return this.i;
    }

    public final void h(int n2) {
        this.b(n2, this.l, this.k, this.j);
    }

    public final int r() {
        return this.l;
    }

    public final void b(int n2, int n3, int n4, int n5) {
        bw bw2 = this;
        synchronized (bw2) {
            long l2;
            if (n4 == Integer.MAX_VALUE) {
                n4 = 0x7FFFFFFE;
            }
            if (n5 <= n4) {
                n5 = n4 + 1;
            }
            if ((l2 = (long)n5 - (long)n4) > Integer.MAX_VALUE) {
                l2 = Integer.MAX_VALUE;
                n5 = n4 + Integer.MAX_VALUE;
            }
            if (n3 > (int)l2) {
                n3 = (int)l2;
            }
            if (n3 <= 0) {
                n3 = 1;
            }
            if (n2 < n4) {
                n2 = n4;
            }
            if (n2 > n5 - n3) {
                n2 = n5 - n3;
            }
            this.i = n2;
            this.l = n3;
            this.k = n4;
            this.j = n5;
            return;
        }
    }

    public final boolean c(int n2, int n3) {
        return false;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = this.j - this.k - this.l;
        if (n4 > 0) {
            if (this.m == 2) {
                n3 += this.d() + this.i * (this.l - 3) / n4;
                n2 += this.c() - this.e() - 1;
            } else {
                n3 += this.d() - this.f() - 1;
                n2 += this.c() + this.i * (this.l - 3) / n4;
            }
            graphics.setColor(0xFF0000);
            graphics.fillRect(n2, n3 + 1, 1, 1);
            graphics.fillRect(n2 + 1, n3, 1, 1);
            graphics.fillRect(n2 + 1, n3 + 2, 1, 1);
            graphics.fillRect(n2 + 2, n3 + 1, 1, 1);
        }
    }
}

