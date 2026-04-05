/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class my
extends aw {
    private int s;

    public my(int n2, int n3) {
        this.c(n2, n3);
        int[][] nArrayArray = new int[2][];
        int[] nArray = new int[6];
        nArray[3] = 2;
        nArray[4] = 1;
        nArray[5] = 2;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{2};
        this.f = nArrayArray;
        this.r = false;
        this.q = 3;
    }

    public final int a(Image image) {
        if (image == null) {
            return 0;
        }
        this.a(image, 3);
        this.r = true;
        this.d(0);
        this.g = 0;
        this.s = 45;
        return this.f[0].length + this.s;
    }

    public final void k() {
        switch (this.e) {
            case 0: {
                if (this.g < this.f[0].length - 1) break;
                this.d(1);
                return;
            }
            case 1: {
                if (this.s <= 0) break;
                --this.s;
                if (this.s != 0) break;
                this.b = null;
                this.r = false;
            }
        }
    }
}

