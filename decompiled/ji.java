/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ji
extends il {
    private static int[][] s;
    private static byte[][] t;
    private int u;

    static {
        int[][] nArrayArray = new int[6][];
        int[] nArray = new int[6];
        nArray[2] = 54;
        nArray[3] = 13;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[6];
        nArray2[1] = 13;
        nArray2[2] = 57;
        nArray2[3] = 18;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[6];
        nArray3[1] = 31;
        nArray3[2] = 57;
        nArray3[3] = 15;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[6];
        nArray4[0] = 57;
        nArray4[2] = 38;
        nArray4[3] = 38;
        nArrayArray[3] = nArray4;
        int[] nArray5 = new int[6];
        nArray5[0] = 95;
        nArray5[2] = 40;
        nArray5[3] = 45;
        nArrayArray[4] = nArray5;
        int[] nArray6 = new int[6];
        nArray6[0] = 135;
        nArray6[2] = 47;
        nArray6[3] = 46;
        nArrayArray[5] = nArray6;
        s = nArrayArray;
        byte[][] byArrayArray = new byte[3][];
        byte[] byArray = new byte[6];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 2;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{3, 3, 4, 4, 5, 5, 5};
        byArrayArray[2] = new byte[]{-1};
        t = byArrayArray;
    }

    public ji() {
        mn.a().a(2006);
        this.a(mn.a().A, s);
        this.a(t);
        this.a(false);
        this.b(false);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        if (n2 < n4) {
            this.c(0);
            this.j(10);
            n2 += 54;
        } else {
            this.c(2);
            this.j(6);
            n2 -= 54;
        }
        this.u = n6;
        if (this.u > 0) {
            this.d(2);
        } else {
            this.d(0);
        }
        this.c(n2, n3);
        this.b(n4, n5);
        this.j = Math.abs(n2 - n4) / 6;
        if (this.j > 30) {
            this.j = 30;
        }
        this.b(true);
    }

    public final void k() {
        switch (this.e) {
            case 0: {
                if (!this.e(this.h, this.j)) break;
                this.d(1);
                return;
            }
            case 1: {
                if (!this.j()) break;
                this.b(false);
                return;
            }
            case 2: {
                if (this.u <= 0) break;
                --this.u;
                if (this.u != 0) break;
                this.d(0);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r || this.g < 0) {
            return;
        }
        int n4 = this.f[this.e][this.g];
        if (n4 >= 0) {
            this.h(s[n4][2]);
            this.i(s[n4][3]);
        }
        super.a(graphics, n2, n3);
    }
}

