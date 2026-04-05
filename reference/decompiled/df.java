/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class df
extends aw {
    private int s = 0;
    private static final int[][] t;

    static {
        int[][] nArrayArray = new int[2][];
        nArrayArray[0] = new int[2];
        int[] nArray = new int[3];
        nArray[1] = 1;
        nArray[2] = 2;
        nArrayArray[1] = nArray;
        t = nArrayArray;
    }

    public df() {
        this.a(f.a(ox.a().b(30098)), 3);
        this.a(t);
        this.d(0);
        this.g = 0;
    }

    public final void l(int n2) {
        this.s = n2;
    }

    public final int r() {
        return this.s;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        cz.a(graphics, this.b, this.f[this.e][this.g] * this.o, 0, this.o, this.p, this.m + n2, this.n + n3, 36);
        if (this.e == 0) {
            ca.c.a(graphics, String.valueOf(this.s), this.m + this.o + n2, this.n - 15 + n3, 2);
        }
    }
}

