/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nf
extends ax {
    private static final int[][] a;
    private nm[] b;
    private int c;
    private final Image d;

    static {
        int[][] nArrayArray = new int[1][];
        int[] nArray = new int[13];
        nArray[3] = 1;
        nArray[4] = 1;
        nArray[5] = 2;
        nArray[6] = 2;
        nArray[7] = 3;
        nArray[8] = 3;
        nArray[9] = 4;
        nArray[10] = 4;
        nArray[11] = 5;
        nArray[12] = 5;
        nArrayArray[0] = nArray;
        a = nArrayArray;
    }

    public nf(Image image) {
        this(image, 6);
    }

    private nf(Image image, int n2) {
        this.d = image;
        if (image == null) {
            return;
        }
        this.o = image.getWidth() / 6;
        this.p = image.getHeight() / 9;
        this.b = new nm[6];
        int n3 = 0;
        while (n3 < this.b.length) {
            this.b[n3] = new nm();
            this.b[n3].a(a);
            ++n3;
        }
    }

    public final void a(int n2, int n3, n n4, int n5, int n6, boolean bl2) {
        if (this.d == null) {
            return;
        }
        n5 = this.d.getHeight() / 9 * n5;
        this.c = n6;
        n6 = 0;
        while (n6 < this.b.length) {
            this.b[n6].a(cy.a(a[0].length));
            this.b[n6].a(n2, n3, n4, this.d, n5, this.o, this.p, bl2);
            ++n6;
        }
        this.r = true;
    }

    public final void i() {
        block7: {
            block8: {
                if (!this.r) break block7;
                if (this.c > 0) {
                    --this.c;
                    return;
                }
                int n2 = 0;
                if (this.b == null) break block8;
                int n3 = this.b.length - 1;
                while (n3 >= 0) {
                    if (this.b[n3] != null && this.b[n3].m()) {
                        this.b[n3].i();
                    } else {
                        ++n2;
                    }
                    --n3;
                }
                if (n2 < this.b.length) break block7;
            }
            this.r = false;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.r) {
            if (this.c > 0) {
                return;
            }
            if (this.b != null) {
                n2 = this.b.length - 1;
                while (n2 >= 0) {
                    if (this.b[n2] != null) {
                        this.b[n2].a(graphics);
                    }
                    --n2;
                }
            }
        }
    }
}

