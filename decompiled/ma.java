/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ma
extends me {
    private static int[][] A;
    private static byte[][] B;
    static final byte[][] s;
    private aw C;
    public boolean t = false;

    static {
        int[][] nArrayArray = new int[10][];
        int[] nArray = new int[6];
        nArray[0] = 78;
        nArray[2] = 11;
        nArray[3] = 11;
        nArray[4] = 83;
        nArray[5] = 20;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[6];
        nArray2[0] = 61;
        nArray2[2] = 17;
        nArray2[3] = 17;
        nArray2[4] = 80;
        nArray2[5] = 17;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[6];
        nArray3[2] = 22;
        nArray3[3] = 24;
        nArray3[4] = 79;
        nArray3[5] = 12;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[6];
        nArray4[0] = 44;
        nArray4[2] = 17;
        nArray4[3] = 17;
        nArray4[4] = 80;
        nArray4[5] = 17;
        nArrayArray[3] = nArray4;
        int[] nArray5 = new int[6];
        nArray5[0] = 22;
        nArray5[2] = 22;
        nArray5[3] = 22;
        nArray5[4] = 77;
        nArray5[5] = 13;
        nArrayArray[4] = nArray5;
        int[] nArray6 = new int[6];
        nArray6[1] = 24;
        nArray6[2] = 50;
        nArray6[3] = 23;
        nArray6[4] = 28;
        nArray6[5] = 13;
        nArrayArray[5] = nArray6;
        nArrayArray[6] = new int[]{50, 17, 32, 32, 23, 8};
        int[] nArray7 = new int[6];
        nArray7[0] = 89;
        nArray7[2] = 62;
        nArray7[3] = 42;
        nArray7[4] = 4;
        nArray7[5] = 2;
        nArrayArray[7] = nArray7;
        int[] nArray8 = new int[6];
        nArray8[0] = 151;
        nArray8[2] = 68;
        nArray8[3] = 45;
        nArray8[5] = 1;
        nArrayArray[8] = nArray8;
        int[] nArray9 = new int[6];
        nArray9[0] = 219;
        nArray9[2] = 24;
        nArray9[3] = 49;
        nArray9[4] = 50;
        nArrayArray[9] = nArray9;
        A = nArrayArray;
        byte[][] byArrayArray = new byte[4][];
        byte[] byArray = new byte[7];
        byArray[0] = -1;
        byArray[1] = -1;
        byArray[4] = 1;
        byArray[5] = 1;
        byArray[6] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{2, 2, 3, 3, 4, 4};
        byArrayArray[2] = new byte[]{5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9};
        byArrayArray[3] = new byte[]{-1};
        B = byArrayArray;
        s = new byte[][]{new byte[7], new byte[6], {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2}};
    }

    public ma(Image[] imageArray, int[][] nArray, int[][] nArray2, m m2, Image image) {
        super(imageArray, nArray, nArray2, m2);
        this.a(0, 0, imageArray[0].getWidth(), imageArray[0].getHeight());
        this.a(s);
        this.C = new aw(image, A);
        this.C.a(B);
        this.C.a(0, 0, 0, 0);
        this.C.j(36);
    }

    public final void d(int n2) {
        super.d(n2);
        if (n2 == 2 && !this.t) {
            this.C.d(3);
            return;
        }
        this.C.d(n2);
    }

    public final void r() {
        super.r();
        this.C.a(0);
    }

    public final void c(int n2) {
        super.c(n2);
        this.C.c(n2);
    }

    public final void k() {
        switch (this.e) {
            case 0: {
                if (!this.j()) break;
                this.d(1);
            }
        }
        this.C.i();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        n2 += this.m;
        n3 += this.n;
        if (this.C != null) {
            if ((this.q & 0x20) != 0) {
                this.C.a(graphics, this.a == 0 ? n2 - 53 : n2 + this.o + 42, n3 - this.p + 8);
                return;
            }
            this.C.a(graphics, this.a == 0 ? n2 - 53 : n2 + this.o + 42, n3 + 8);
        }
    }
}

