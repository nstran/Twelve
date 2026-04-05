/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mb
extends me {
    static final byte[][] s = new byte[][]{new byte[3], {1, 2, 3, 1, 2, 3}};

    public mb(Image[] imageArray, int[][] nArray, int[][] nArray2, m m2) {
        super(imageArray, nArray, nArray2, m2);
    }

    public final void d(int n2) {
        super.d(n2);
    }

    public final void s() {
        ++this.g;
        if (this.g >= this.f[this.e].length) {
            this.g = 0;
        }
    }

    public final void t() {
        --this.g;
        if (this.g < 0) {
            this.g = this.f[this.e].length - 1;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r || this.g < 0) {
            return;
        }
        if (this.e == 1) {
            n2 -= 5;
        }
        super.a(graphics, n2 + (this.a == 2 ? 10 : 0), n3);
    }
}

