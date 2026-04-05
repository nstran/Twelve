/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mv
extends ax {
    private int d;
    private int[] e = new int[]{81208, 85576, 23637};
    private int[] f = new int[]{5046585, 6095173, 7209297};
    private int g;
    private int h;
    private int i;
    private Image j;
    private int k = 0;
    private boolean s;
    private int t;
    private na[] u;
    private na[] v;
    private int w;
    private int x;
    private int[] y;
    private int[] z;
    private byte A;
    private int[][] B;
    private Image[] C;
    private Image D;
    private Image E;
    private Image F;
    private le[][] G;
    private ni[] H;
    private ni[] I;
    ni[] a;
    private String[] J;
    private n K;
    private iq[] L;
    mq b;
    private int[] M;
    private int[] N = new int[2];
    private int[] O = new int[2];
    private mg[] P;
    private mg[] Q;
    private mg[] R;
    private mg S;
    public nn c;
    private int T;
    private boolean U;
    private int V;

    public mv(mq object, n object2, byte by2, int n2, int n3) {
        this.b = object;
        this.G = ((mq)object).a();
        this.K = object2;
        this.y = new int[2];
        object2 = this;
        try {
            ((mv)object2).a = new ni[2];
            ((mv)object2).J = new String[2];
            ((mv)object2).H = new ni[((mv)object2).G[0].length];
            ((mv)object2).I = new ni[((mv)object2).G[1].length];
            int n4 = 0;
            while (n4 < ((mv)object2).H.length) {
                ((mv)object2).H[n4] = mv.b(((mv)object2).G[0][n4].a(), 0);
                ++n4;
            }
            n4 = 0;
            while (n4 < ((mv)object2).I.length) {
                try {
                    ((mv)object2).I[n4] = ((mv)object2).G[1][n4].b() && ((mv)object2).G[1][n4].a().aa > 0 ? super.a(((mv)object2).G[1][n4].a(), 1) : mv.b(((mv)object2).G[1][n4].a(), 1);
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                    System.err.println(((mv)object2).G[1][n4]);
                }
                ++n4;
            }
            ((mv)object2).a[0] = ((mv)object2).H[0];
            ((mv)object2).a[1] = ((mv)object2).I[0];
        }
        catch (Exception exception) {
            Exception exception3 = exception;
            exception.printStackTrace();
        }
        this.j = mn.a().f;
        this.A = by2;
        this.c(n2, n3);
        if (by2 == 0) {
            this.c(false);
            int[][] nArrayArray = new int[12][];
            nArrayArray[0] = new int[]{10, 233, this.C[0].getWidth(), this.C[0].getHeight()};
            nArrayArray[1] = new int[]{10, 240, this.D.getWidth(), this.D.getHeight()};
            nArrayArray[2] = new int[]{10, 247, this.E.getWidth(), this.E.getHeight()};
            nArrayArray[3] = new int[]{154, 233, this.C[0].getWidth(), this.C[0].getHeight()};
            nArrayArray[4] = new int[]{154, 240, this.D.getWidth(), this.D.getHeight()};
            nArrayArray[5] = new int[]{154, 247, this.E.getWidth(), this.E.getHeight()};
            nArrayArray[6] = new int[]{50, 316, this.H[0].p(), this.H[0].q()};
            nArrayArray[7] = new int[]{20, 316, this.H[0].p(), this.H[0].q()};
            int[] nArray = new int[4];
            nArray[1] = 316;
            nArray[2] = this.H[0].p();
            nArray[3] = this.H[0].q();
            nArrayArray[8] = nArray;
            nArrayArray[9] = new int[]{200, 316, this.I[0].p(), this.I[0].q()};
            nArrayArray[10] = new int[]{230, 316, this.I[0].p(), this.I[0].q()};
            nArrayArray[11] = new int[]{240, 316, this.I[0].p(), this.I[0].q()};
            this.B = nArrayArray;
            if (this.G[0].length == 3) {
                int[] nArray2 = this.B[6];
                nArray2[1] = nArray2[1] + 5;
                int[] nArray3 = this.B[7];
                nArray3[1] = nArray3[1] - 5;
                int[] nArray4 = this.B[8];
                nArray4[1] = nArray4[1] + 5;
            } else if (this.G[0].length == 2) {
                int[] nArray5 = this.B[7];
                nArray5[1] = nArray5[1] + 5;
                int[] nArray6 = this.B[8];
                nArray6[1] = nArray6[1] - 5;
            }
            if (this.G[1].length == 3) {
                int[] nArray7 = this.B[9];
                nArray7[1] = nArray7[1] + 5;
                int[] nArray8 = this.B[10];
                nArray8[1] = nArray8[1] - 5;
                int[] nArray9 = this.B[11];
                nArray9[1] = nArray9[1] + 5;
            } else if (this.G[1].length == 2) {
                int[] nArray10 = this.B[10];
                nArray10[1] = nArray10[1] + 5;
                int[] nArray11 = this.B[11];
                nArray11[1] = nArray11[1] - 5;
            }
        } else {
            this.c(true);
            this.B = new int[][]{{38, 6, this.C[0].getWidth(), this.C[0].getHeight()}, {31, 6, this.D.getWidth(), this.D.getHeight()}, {24, 6, this.E.getWidth(), this.E.getHeight()}, {277, 6, this.C[0].getWidth(), this.C[0].getHeight()}, {284, 6, this.D.getWidth(), this.D.getHeight()}, {291, 6, this.E.getWidth(), this.E.getHeight()}, {75 - this.H[0].p() / 2, 236, this.H[0].p(), this.H[0].q()}, {35 - this.H[0].p() / 2, 236, this.H[0].p(), this.H[0].q()}, {15 - this.H[0].p() / 2, 236, this.H[0].p(), this.H[0].q()}, {280, 236, this.I[0].p(), this.I[0].q()}, {310, 236, this.I[0].p(), this.I[0].q()}, {330, 236, this.I[0].p(), this.I[0].q()}};
            if (this.G[0].length == 2) {
                int[] nArray = this.B[7];
                nArray[1] = nArray[1] + 4;
                int[] nArray12 = this.B[7];
                nArray12[0] = nArray12[0] + 10;
                int[] nArray13 = this.B[8];
                nArray13[1] = nArray13[1] - 4;
            } else if (this.G[0].length == 3) {
                int[] nArray = this.B[6];
                nArray[1] = nArray[1] + 4;
                int[] nArray14 = this.B[7];
                nArray14[1] = nArray14[1] - 4;
                int[] nArray15 = this.B[8];
                nArray15[1] = nArray15[1] + 4;
            } else {
                int[] nArray = this.B[8];
                nArray[0] = nArray[0] + 5;
            }
            if (this.G[1].length == 2) {
                int[] nArray = this.B[10];
                nArray[1] = nArray[1] + 4;
                int[] nArray16 = this.B[11];
                nArray16[1] = nArray16[1] - 4;
            } else if (this.G[1].length == 3) {
                int[] nArray = this.B[9];
                nArray[1] = nArray[1] + 4;
                int[] nArray17 = this.B[10];
                nArray17[1] = nArray17[1] - 4;
                int[] nArray18 = this.B[11];
                nArray18[1] = nArray18[1] + 4;
            }
        }
        int n5 = 0;
        while (n5 < 2) {
            this.O[n5] = this.G[n5].length == 1 ? 2 : (this.G[n5].length == 2 ? 1 : 0);
            ++n5;
        }
        n5 = 0;
        while (n5 < this.B.length) {
            int[] nArray = this.B[n5];
            nArray[0] = nArray[0] + n2;
            int[] nArray19 = this.B[n5];
            nArray19[1] = nArray19[1] + n3;
            ++n5;
        }
        if (this.G[1][0].b() && by2 == 0) {
            int[] nArray = this.B[9];
            nArray[0] = nArray[0] + 4;
            n5 = this.G[1][0].a().aa;
            if ((n5 = n5 / 10 % 1000) == 18) {
                int[] nArray20 = this.B[9];
                nArray20[0] = nArray20[0] + 35;
                int[] nArray21 = this.B[10];
                nArray21[0] = nArray21[0] + 35;
                int[] nArray22 = this.B[11];
                nArray22[0] = nArray22[0] + 35;
            } else if (n5 == 21 || n5 == 22) {
                int[] nArray23 = this.B[9];
                nArray23[0] = nArray23[0] + 15;
                int[] nArray24 = this.B[10];
                nArray24[0] = nArray24[0] + 15;
                int[] nArray25 = this.B[11];
                nArray25[0] = nArray25[0] + 15;
                int[] nArray26 = this.B[9];
                nArray26[1] = nArray26[1] + 5;
                int[] nArray27 = this.B[10];
                nArray27[1] = nArray27[1] + 5;
                int[] nArray28 = this.B[11];
                nArray28[1] = nArray28[1] + 5;
            } else if (n5 >= 63 && n5 <= 78) {
                int[] nArray29 = this.B[9];
                nArray29[1] = nArray29[1] + 5;
                int[] nArray30 = this.B[10];
                nArray30[1] = nArray30[1] + 5;
                int[] nArray31 = this.B[11];
                nArray31[1] = nArray31[1] + 5;
            } else if (n5 == 25) {
                int[] nArray32 = this.B[9];
                nArray32[1] = nArray32[1] + 1;
                int[] nArray33 = this.B[10];
                nArray33[1] = nArray33[1] + 1;
                int[] nArray34 = this.B[11];
                nArray34[1] = nArray34[1] + 1;
            } else if (n5 == 26) {
                int[] nArray35 = this.B[9];
                nArray35[1] = nArray35[1] + 3;
                int[] nArray36 = this.B[10];
                nArray36[1] = nArray36[1] + 3;
                int[] nArray37 = this.B[11];
                nArray37[1] = nArray37[1] + 3;
            } else if (n5 == 27) {
                int[] nArray38 = this.B[9];
                nArray38[1] = nArray38[1] + 5;
                int[] nArray39 = this.B[10];
                nArray39[1] = nArray39[1] + 5;
                int[] nArray40 = this.B[11];
                nArray40[1] = nArray40[1] + 5;
            }
        }
        String[] stringArray = new String[this.G[0].length];
        n2 = 0;
        while (n2 < stringArray.length) {
            stringArray[n2] = this.G[0][n2].j();
            ++n2;
        }
        this.a(stringArray, 0);
        stringArray = new String[this.G[1].length];
        n2 = 0;
        while (n2 < stringArray.length) {
            stringArray[n2] = this.G[1][n2].j();
            ++n2;
        }
        this.a(stringArray, 1);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[13];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 2;
        byArray[6] = 3;
        byArray[7] = 3;
        byArray[8] = 4;
        byArray[9] = 4;
        byArray[10] = 5;
        byArray[11] = 5;
        byArray[12] = 5;
        byArrayArray[0] = byArray;
        byte[][] byArrayArray2 = byArrayArray;
        this.u = new na[25];
        int n6 = mn.a().b.getWidth() / 6;
        n3 = mn.a().b.getHeight() / 9;
        int n7 = n3 * 1;
        int n8 = 0;
        while (n8 < this.u.length) {
            this.u[n8] = new na(mn.a().b, n6, n3, n7);
            this.u[n8].a(byArrayArray2);
            this.u[n8].b(false);
            ++n8;
        }
        n7 = n3 * 2;
        this.v = new na[10];
        n8 = 0;
        while (n8 < this.v.length) {
            this.v[n8] = new na(mn.a().b, n6, n3, n7);
            this.v[n8].a(byArrayArray2);
            this.v[n8].b(false);
            ++n8;
        }
        this.z = new int[2];
        this.y = new int[2];
        this.M = new int[2];
        this.L = new iq[2];
        n6 = by2 == 0 ? this.C[0].getWidth() : this.C[0].getHeight();
        this.P = new mg[2];
        n8 = 0;
        while (n8 < this.P.length) {
            this.P[n8] = new mg(n6, this.G[n8][0].l());
            this.P[n8].a(0, this.G[n8][0].m(), this.G[n8][0].l(), this.G[n8][0].d());
            ++n8;
        }
        n6 = by2 == 0 ? this.D.getWidth() : this.D.getHeight();
        this.Q = new mg[2];
        n8 = 0;
        while (n8 < this.Q.length) {
            this.Q[n8] = new mg(n6, this.G[n8][0].o());
            this.Q[n8].a(0, this.G[n8][0].n(), this.G[n8][0].o(), 0);
            ++n8;
        }
        n6 = by2 == 0 ? this.E.getWidth() : this.E.getHeight();
        this.R = new mg[2];
        n8 = 0;
        while (n8 < this.R.length) {
            this.R[n8] = new mg(n6, this.G[n8][0].r());
            this.R[n8].a(0, this.G[n8][0].q(), this.G[n8][0].r(), 0);
            ++n8;
        }
        if (op.o == 1) {
            this.S = new mg(n6, ((mq)object).g);
            n6 = 1;
            object = this.S;
            this.S.b = n6;
            this.c = new nn();
            this.c.a();
        }
    }

    private void a(String[] stringArray, int n2) {
        if (n2 == 0) {
            int n3 = 0;
            while (n3 < stringArray.length) {
                int n4 = 0;
                while (n4 < this.G[n2].length) {
                    if (stringArray[n3].equals(this.G[n2][n4].j())) {
                        this.H[n4].c(this.B[6 + this.O[n2] + n3][0], this.B[6 + this.O[n2] + n3][1]);
                        if (n3 == 0) {
                            this.a[n2] = this.H[n4];
                            this.J[n2] = stringArray[n3];
                            this.N[n2] = n4;
                        }
                    }
                    ++n4;
                }
                ++n3;
            }
        } else {
            int n5 = 0;
            while (n5 < stringArray.length) {
                int n6 = 0;
                while (n6 < this.G[n2].length) {
                    if (stringArray[n5].equals(this.G[n2][n6].j())) {
                        this.I[n6].c(this.B[9 + this.O[n2] + n5][0] - this.I[n6].p(), this.B[9 + this.O[n2] + n5][1]);
                        if (n5 == 0) {
                            this.a[n2] = this.I[n6];
                            this.J[n2] = stringArray[n5];
                            this.N[n2] = n6;
                        }
                    }
                    ++n6;
                }
                ++n5;
            }
        }
        this.a[0].a(this.a[1]);
        this.a[1].a(this.a[0]);
    }

    private static Image m(int n2) {
        return ox.a().a(n2, false);
    }

    private ni a(lf lf2, int n2) {
        Image image;
        int n3 = lf2.aa;
        Object object = mv.m(n3 + 5);
        Image image2 = mv.m(n3 + 1);
        Image image3 = mv.m(n3 + 4);
        Image image4 = image = mv.m(n3 + 3);
        Image image5 = mv.m(n3 + 6);
        if ((n3 = n3 / 10 % 1000) == 0 || n3 == 19 || n3 == 20 || n3 == 21) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[28];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 2;
            byArray[15] = 2;
            byArray[16] = 2;
            byArray[17] = 2;
            byArray[18] = 2;
            byArray[19] = 2;
            byArray[20] = 2;
            byArray[21] = 1;
            byArray[22] = 1;
            byArray[23] = 1;
            byArray[24] = 1;
            byArray[25] = 1;
            byArray[26] = 1;
            byArray[27] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray2 = new byte[8];
            byArray2[4] = 1;
            byArray2[5] = 1;
            byArray2[6] = 1;
            byArray2[7] = 1;
            byArrayArray[1] = byArray2;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray3 = new byte[12];
            byArray3[1] = -1;
            byArray3[3] = -1;
            byArray3[5] = -1;
            byArray3[7] = -1;
            byArray3[9] = -1;
            byArray3[11] = -1;
            byArrayArray[4] = byArray3;
            return new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{3, 2, 1, 1, 1});
        }
        if (n3 == 2 || n3 == 3 || n3 == 7) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[28];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 2;
            byArray[15] = 2;
            byArray[16] = 2;
            byArray[17] = 2;
            byArray[18] = 2;
            byArray[19] = 2;
            byArray[20] = 2;
            byArray[21] = 1;
            byArray[22] = 1;
            byArray[23] = 1;
            byArray[24] = 1;
            byArray[25] = 1;
            byArray[26] = 1;
            byArray[27] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray4 = new byte[8];
            byArray4[2] = 1;
            byArray4[3] = 1;
            byArray4[4] = 2;
            byArray4[5] = 2;
            byArray4[6] = 3;
            byArray4[7] = 3;
            byArrayArray[1] = byArray4;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray5 = new byte[12];
            byArray5[1] = -1;
            byArray5[3] = -1;
            byArray5[5] = -1;
            byArray5[7] = -1;
            byArray5[9] = -1;
            byArray5[11] = -1;
            byArrayArray[4] = byArray5;
            object = new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{3, 4, 1, 1, 1});
            new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{3, 4, 1, 1, 1}).d = 20;
            object.e = -5;
            return object;
        }
        if (n3 >= 63 && n3 <= 78) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[28];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 2;
            byArray[15] = 2;
            byArray[16] = 2;
            byArray[17] = 2;
            byArray[18] = 2;
            byArray[19] = 2;
            byArray[20] = 2;
            byArray[21] = 1;
            byArray[22] = 1;
            byArray[23] = 1;
            byArray[24] = 1;
            byArray[25] = 1;
            byArray[26] = 1;
            byArray[27] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray6 = new byte[8];
            byArray6[2] = 1;
            byArray6[3] = 1;
            byArray6[4] = 2;
            byArray6[5] = 2;
            byArray6[6] = 3;
            byArray6[7] = 3;
            byArrayArray[1] = byArray6;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray7 = new byte[12];
            byArray7[1] = -1;
            byArray7[3] = -1;
            byArray7[5] = -1;
            byArray7[7] = -1;
            byArray7[9] = -1;
            byArray7[11] = -1;
            byArrayArray[4] = byArray7;
            object = new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{3, 4, 1, 1, 1});
            new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{3, 4, 1, 1, 1}).d = 20;
            object.e = -8;
            return object;
        }
        if (n3 == 1 || n3 == 14 || n3 == 16) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray8 = new byte[4];
            byArray8[2] = 1;
            byArray8[3] = 1;
            byArrayArray[1] = byArray8;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray9 = new byte[12];
            byArray9[1] = -1;
            byArray9[3] = -1;
            byArray9[5] = -1;
            byArray9[7] = -1;
            byArray9[9] = -1;
            byArray9[11] = -1;
            byArrayArray[4] = byArray9;
            return new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 2, 1, 1, 1});
        }
        if (n3 == 4 || n3 == 12) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray10 = new byte[8];
            byArray10[4] = 1;
            byArray10[5] = 1;
            byArray10[6] = 1;
            byArray10[7] = 1;
            byArrayArray[1] = byArray10;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray11 = new byte[12];
            byArray11[1] = -1;
            byArray11[3] = -1;
            byArray11[5] = -1;
            byArray11[7] = -1;
            byArray11[9] = -1;
            byArray11[11] = -1;
            byArrayArray[4] = byArray11;
            return new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 2, 1, 1, 1});
        }
        if (n3 == 5 || n3 == 11) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray12 = new byte[8];
            byArray12[4] = 1;
            byArray12[5] = 1;
            byArray12[6] = 1;
            byArray12[7] = 1;
            byArrayArray[1] = byArray12;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray13 = new byte[12];
            byArray13[1] = -1;
            byArray13[3] = -1;
            byArray13[5] = -1;
            byArray13[7] = -1;
            byArray13[9] = -1;
            byArray13[11] = -1;
            byArrayArray[4] = byArray13;
            object = new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 2, 1, 1, 1});
            new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 2, 1, 1, 1}).d = 20;
            object.e = 0;
            return object;
        }
        if (n3 == 6) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray14 = new byte[8];
            byArray14[4] = 1;
            byArray14[5] = 1;
            byArray14[6] = 2;
            byArray14[7] = 2;
            byArrayArray[1] = byArray14;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[10];
            byte[] byArray15 = new byte[12];
            byArray15[1] = -1;
            byArray15[3] = -1;
            byArray15[5] = -1;
            byArray15[7] = -1;
            byArray15[9] = -1;
            byArray15[11] = -1;
            byArrayArray[4] = byArray15;
            object = new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 3, 1, 1, 1});
            new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 3, 1, 1, 1}).d = 30;
            return object;
        }
        if (n3 == 17 || n3 == 61) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byArrayArray[1] = new byte[1];
            byte[] byArray16 = new byte[16];
            byArray16[7] = 1;
            byArray16[8] = 1;
            byArray16[9] = 1;
            byArray16[10] = 1;
            byArray16[11] = 1;
            byArray16[12] = 1;
            byArray16[13] = 1;
            byArray16[14] = 1;
            byArray16[15] = 1;
            byArrayArray[2] = byArray16;
            byArrayArray[3] = new byte[8];
            byte[] byArray17 = new byte[12];
            byArray17[1] = -1;
            byArray17[3] = -1;
            byArray17[5] = -1;
            byArray17[7] = -1;
            byArray17[9] = -1;
            byArray17[11] = -1;
            byArrayArray[4] = byArray17;
            byte[][] byArrayArray2 = new byte[3][];
            byte[] byArray18 = new byte[6];
            byArray18[4] = 1;
            byArray18[5] = 1;
            byArrayArray2[0] = byArray18;
            byArrayArray2[1] = new byte[]{2, 2, 3, 3, 3};
            byArrayArray2[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 1}, image5, byArrayArray2, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 1}, image5, byArrayArray2, 4).b = -27;
            object.a = 42;
            return object;
        }
        if (n3 == 8) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[23];
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArray[16] = 1;
            byArray[17] = 1;
            byArray[18] = 1;
            byArray[19] = 1;
            byArray[20] = 1;
            byArray[21] = 1;
            byArray[22] = 1;
            byArrayArray[0] = byArray;
            byArrayArray[1] = new byte[4];
            byte[] byArray19 = new byte[16];
            byArray19[7] = 1;
            byArray19[8] = 1;
            byArray19[9] = 1;
            byArray19[10] = 1;
            byArray19[11] = 1;
            byArray19[12] = 1;
            byArray19[13] = 1;
            byArray19[14] = 1;
            byArray19[15] = 1;
            byArrayArray[2] = byArray19;
            byArrayArray[3] = new byte[8];
            byte[] byArray20 = new byte[12];
            byArray20[1] = -1;
            byArray20[3] = -1;
            byArray20[5] = -1;
            byArray20[7] = -1;
            byArray20[9] = -1;
            byArray20[11] = -1;
            byArrayArray[4] = byArray20;
            byte[][] byArrayArray3 = new byte[3][];
            byte[] byArray21 = new byte[5];
            byArray21[1] = 1;
            byArray21[2] = 1;
            byArray21[3] = 2;
            byArray21[4] = 2;
            byArrayArray3[0] = byArray21;
            byArrayArray3[1] = new byte[3];
            byArrayArray3[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 2}, image5, byArrayArray3, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 2}, image5, byArrayArray3, 4).b = 0;
            object.a = 60;
            return object;
        }
        if (n3 == 10) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray22 = new byte[10];
            byArray22[2] = 1;
            byArray22[3] = 1;
            byArray22[4] = 1;
            byArray22[5] = 1;
            byArray22[6] = 1;
            byArray22[7] = 1;
            byArray22[8] = 1;
            byArray22[9] = 1;
            byArrayArray[1] = byArray22;
            byte[] byArray23 = new byte[16];
            byArray23[7] = 1;
            byArray23[8] = 1;
            byArray23[9] = 1;
            byArray23[10] = 1;
            byArray23[11] = 1;
            byArray23[12] = 1;
            byArray23[13] = 1;
            byArray23[14] = 1;
            byArray23[15] = 1;
            byArrayArray[2] = byArray23;
            byArrayArray[3] = new byte[8];
            byte[] byArray24 = new byte[12];
            byArray24[1] = -1;
            byArray24[3] = -1;
            byArray24[5] = -1;
            byArray24[7] = -1;
            byArray24[9] = -1;
            byArray24[11] = -1;
            byArrayArray[4] = byArray24;
            byte[][] byArrayArray4 = new byte[3][];
            byte[] byArray25 = new byte[4];
            byArray25[2] = 1;
            byArray25[3] = 1;
            byArrayArray4[0] = byArray25;
            byArrayArray4[1] = new byte[]{2, 2, 3, 3, 3};
            byArrayArray4[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray4, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray4, 4).b = 35;
            object.a = 20;
            return object;
        }
        if (n3 == 13) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray26 = new byte[4];
            byArray26[2] = 1;
            byArray26[3] = 1;
            byArrayArray[1] = byArray26;
            byte[] byArray27 = new byte[16];
            byArray27[7] = 1;
            byArray27[8] = 1;
            byArray27[9] = 1;
            byArray27[10] = 1;
            byArray27[11] = 1;
            byArray27[12] = 1;
            byArray27[13] = 1;
            byArray27[14] = 1;
            byArray27[15] = 1;
            byArrayArray[2] = byArray27;
            byArrayArray[3] = new byte[8];
            byte[] byArray28 = new byte[12];
            byArray28[1] = -1;
            byArray28[3] = -1;
            byArray28[5] = -1;
            byArray28[7] = -1;
            byArray28[9] = -1;
            byArray28[11] = -1;
            byArrayArray[4] = byArray28;
            byte[][] byArrayArray5 = new byte[3][];
            byArrayArray5[0] = new byte[]{-1};
            byte[] byArray29 = new byte[8];
            byArray29[2] = 1;
            byArray29[3] = 1;
            byArray29[4] = 2;
            byArray29[5] = 2;
            byArray29[6] = 3;
            byArray29[7] = 3;
            byArrayArray5[1] = byArray29;
            byArrayArray5[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray5, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray5, 4).b = -25;
            object.a = 0;
            return object;
        }
        if (n3 == 9 || n3 == 15) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byArrayArray[1] = new byte[1];
            byte[] byArray30 = new byte[16];
            byArray30[7] = 1;
            byArray30[8] = 1;
            byArray30[9] = 1;
            byArray30[10] = 1;
            byArray30[11] = 1;
            byArray30[12] = 1;
            byArray30[13] = 1;
            byArray30[14] = 1;
            byArray30[15] = 1;
            byArrayArray[2] = byArray30;
            byArrayArray[3] = new byte[8];
            byte[] byArray31 = new byte[12];
            byArray31[1] = -1;
            byArray31[3] = -1;
            byArray31[5] = -1;
            byArray31[7] = -1;
            byArray31[9] = -1;
            byArray31[11] = -1;
            byArrayArray[4] = byArray31;
            byte[][] byArrayArray6 = new byte[3][];
            byte[] byArray32 = new byte[4];
            byArray32[2] = 1;
            byArray32[3] = 1;
            byArrayArray6[0] = byArray32;
            byArrayArray6[1] = new byte[]{2, 2, 2};
            byArrayArray6[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 1}, image5, byArrayArray6, 3);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 1}, image5, byArrayArray6, 3).b = 0;
            object.a = 80;
            return object;
        }
        if (n3 == 18) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray33 = new byte[5];
            byArray33[2] = 1;
            byArray33[3] = 1;
            byArray33[4] = 1;
            byArrayArray[1] = byArray33;
            byte[] byArray34 = new byte[16];
            byArray34[7] = 1;
            byArray34[8] = 1;
            byArray34[9] = 1;
            byArray34[10] = 1;
            byArray34[11] = 1;
            byArray34[12] = 1;
            byArray34[13] = 1;
            byArray34[14] = 1;
            byArray34[15] = 1;
            byArrayArray[2] = byArray34;
            byArrayArray[3] = new byte[8];
            byte[] byArray35 = new byte[12];
            byArray35[1] = -1;
            byArray35[3] = -1;
            byArray35[5] = -1;
            byArray35[7] = -1;
            byArray35[9] = -1;
            byArray35[11] = -1;
            byArrayArray[4] = byArray35;
            byte[][] byArrayArray7 = new byte[3][];
            byte[] byArray36 = new byte[4];
            byArray36[2] = 1;
            byArray36[3] = 1;
            byArrayArray7[0] = byArray36;
            byArrayArray7[1] = new byte[]{2, 2, 2};
            byArrayArray7[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray7, 3);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray7, 3).b = -15;
            object.a = 10;
            return object;
        }
        if (n3 == 22 || n3 == 51) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray37 = new byte[4];
            byArray37[2] = 1;
            byArray37[3] = 1;
            byArrayArray[1] = byArray37;
            byte[] byArray38 = new byte[16];
            byArray38[7] = 1;
            byArray38[8] = 1;
            byArray38[9] = 1;
            byArray38[10] = 1;
            byArray38[11] = 1;
            byArray38[12] = 1;
            byArray38[13] = 1;
            byArray38[14] = 1;
            byArray38[15] = 1;
            byArrayArray[2] = byArray38;
            byArrayArray[3] = new byte[8];
            byte[] byArray39 = new byte[12];
            byArray39[1] = -1;
            byArray39[3] = -1;
            byArray39[5] = -1;
            byArray39[7] = -1;
            byArray39[9] = -1;
            byArray39[11] = -1;
            byArrayArray[4] = byArray39;
            byte[][] byArrayArray8 = new byte[3][];
            byte[] byArray40 = new byte[5];
            byArray40[2] = 1;
            byArray40[3] = 2;
            byArray40[4] = 2;
            byArrayArray8[0] = byArray40;
            byArrayArray8[1] = new byte[]{3, 3, 3};
            byArrayArray8[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray8, 4);
            object.b = n3 == 22 ? -25 : -15;
            object.a = 50;
            return object;
        }
        if (n3 == 43) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray41 = new byte[7];
            byArray41[2] = 1;
            byArray41[3] = 1;
            byArray41[4] = 1;
            byArray41[5] = 1;
            byArray41[6] = 1;
            byArrayArray[1] = byArray41;
            byte[] byArray42 = new byte[16];
            byArray42[7] = 1;
            byArray42[8] = 1;
            byArray42[9] = 1;
            byArray42[10] = 1;
            byArray42[11] = 1;
            byArray42[12] = 1;
            byArray42[13] = 1;
            byArray42[14] = 1;
            byArray42[15] = 1;
            byArrayArray[2] = byArray42;
            byArrayArray[3] = new byte[8];
            byte[] byArray43 = new byte[12];
            byArray43[1] = -1;
            byArray43[3] = -1;
            byArray43[5] = -1;
            byArray43[7] = -1;
            byArray43[9] = -1;
            byArray43[11] = -1;
            byArrayArray[4] = byArray43;
            byte[][] byArrayArray9 = new byte[3][];
            byte[] byArray44 = new byte[5];
            byArray44[2] = 1;
            byArray44[3] = 1;
            byArray44[4] = 1;
            byArrayArray9[0] = byArray44;
            byArrayArray9[1] = new byte[]{2, 2, 3, 3, 3};
            byArrayArray9[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray9, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray9, 4).b = 0;
            object.a = 50;
            return object;
        }
        if (n3 == 47) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray45 = new byte[7];
            byArray45[2] = 1;
            byArray45[3] = 1;
            byArray45[4] = 2;
            byArray45[5] = 2;
            byArray45[6] = 2;
            byArrayArray[1] = byArray45;
            byte[] byArray46 = new byte[16];
            byArray46[7] = 1;
            byArray46[8] = 1;
            byArray46[9] = 1;
            byArray46[10] = 1;
            byArray46[11] = 1;
            byArray46[12] = 1;
            byArray46[13] = 1;
            byArray46[14] = 1;
            byArray46[15] = 1;
            byArrayArray[2] = byArray46;
            byArrayArray[3] = new byte[8];
            byte[] byArray47 = new byte[12];
            byArray47[1] = -1;
            byArray47[3] = -1;
            byArray47[5] = -1;
            byArray47[7] = -1;
            byArray47[9] = -1;
            byArray47[11] = -1;
            byArrayArray[4] = byArray47;
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 3, 2, 1, 1}, image5, new byte[][]{new byte[2], {1, 1, 1, 1}, {-1}}, 2);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 3, 2, 1, 1}, image5, new byte[][]{new byte[2], {1, 1, 1, 1}, {-1}}, 2).b = -5;
            object.a = 50;
            return object;
        }
        if (n3 == 30 || n3 == 31 || n3 == 32) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byArrayArray[1] = new byte[]{-1};
            byte[] byArray48 = new byte[16];
            byArray48[7] = 1;
            byArray48[8] = 1;
            byArray48[9] = 1;
            byArray48[10] = 1;
            byArray48[11] = 1;
            byArray48[12] = 1;
            byArray48[13] = 1;
            byArray48[14] = 1;
            byArray48[15] = 1;
            byArrayArray[2] = byArray48;
            byArrayArray[3] = new byte[8];
            byte[] byArray49 = new byte[12];
            byArray49[1] = -1;
            byArray49[3] = -1;
            byArray49[5] = -1;
            byArray49[7] = -1;
            byArray49[9] = -1;
            byArray49[11] = -1;
            byArrayArray[4] = byArray49;
            byte[][] byArrayArray10 = new byte[3][];
            byte[] byArray50 = new byte[5];
            byArray50[2] = 1;
            byArray50[3] = 2;
            byArray50[4] = 2;
            byArrayArray10[0] = byArray50;
            byArrayArray10[1] = new byte[]{3, 3, 3, 3, 3};
            byArrayArray10[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray10, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray10, 4).b = 10;
            object.a = 20;
            return object;
        }
        if (n3 == 24 || n3 == 33 || n3 == 34) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray51 = new byte[7];
            byArray51[2] = 1;
            byArray51[3] = 1;
            byArray51[4] = 1;
            byArray51[5] = 1;
            byArray51[6] = 1;
            byArrayArray[1] = byArray51;
            byte[] byArray52 = new byte[16];
            byArray52[7] = 1;
            byArray52[8] = 1;
            byArray52[9] = 1;
            byArray52[10] = 1;
            byArray52[11] = 1;
            byArray52[12] = 1;
            byArray52[13] = 1;
            byArray52[14] = 1;
            byArray52[15] = 1;
            byArrayArray[2] = byArray52;
            byArrayArray[3] = new byte[8];
            byte[] byArray53 = new byte[12];
            byArray53[1] = -1;
            byArray53[3] = -1;
            byArray53[5] = -1;
            byArray53[7] = -1;
            byArray53[9] = -1;
            byArray53[11] = -1;
            byArrayArray[4] = byArray53;
            byte[][] byArrayArray11 = new byte[3][];
            byte[] byArray54 = new byte[5];
            byArray54[2] = 1;
            byArray54[3] = 2;
            byArray54[4] = 2;
            byArrayArray11[0] = byArray54;
            byArrayArray11[1] = new byte[]{3, 3, 3};
            byArrayArray11[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray11, 4);
            new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 2, 2, 1, 1}, image5, byArrayArray11, 4).b = 0;
            object.a = 50;
            return object;
        }
        if (n3 == 100) {
            byte[][] byArrayArray = new byte[6][];
            byte[] byArray = new byte[20];
            byArray[5] = 1;
            byArray[6] = 1;
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 2;
            byArray[11] = 2;
            byArray[12] = 2;
            byArray[13] = 2;
            byArray[14] = 2;
            byArray[15] = 1;
            byArray[16] = 1;
            byArray[17] = 1;
            byArray[18] = 1;
            byArray[19] = 1;
            byArrayArray[0] = byArray;
            byArrayArray[1] = new byte[]{3, 3, 4, 4};
            byte[] byArray55 = new byte[7];
            byArray55[3] = 1;
            byArray55[4] = 1;
            byArray55[5] = 2;
            byArray55[6] = 2;
            byArrayArray[2] = byArray55;
            byArrayArray[3] = new byte[8];
            byte[] byArray56 = new byte[12];
            byArray56[1] = 1;
            byArray56[3] = 1;
            byArray56[5] = 1;
            byArray56[7] = 1;
            byArray56[9] = 1;
            byArray56[11] = -1;
            byArrayArray[4] = byArray56;
            byArrayArray[5] = new byte[]{5, 5, 5, 6, 7, 8};
            return new nd(1, new Image[]{object, image2, image2, image4, image4, image2}, byArrayArray, new int[]{3, 3, 2, 2, 2, 4});
        }
        if (n3 == 44 || n3 == 45 || n3 == 46 || n3 == 56 || n3 == 57 || n3 == 58 || n3 == 60 || n3 == 62) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray57 = new byte[8];
            byArray57[4] = 1;
            byArray57[5] = 1;
            byArray57[6] = 1;
            byArray57[7] = 1;
            byArrayArray[1] = byArray57;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray58 = new byte[12];
            byArray58[1] = -1;
            byArray58[3] = -1;
            byArray58[5] = -1;
            byArray58[7] = -1;
            byArray58[9] = -1;
            byArray58[11] = -1;
            byArrayArray[4] = byArray58;
            object = new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 2, 1, 1, 1});
            object.f = this.A == 0 ? 4 : 8;
            return object;
        }
        if (n3 == 23 || n3 == 25 || n3 == 26 || n3 == 27 || n3 == 28 || n3 == 29 || n3 == 35 || n3 == 36 || n3 == 37 || n3 == 38 || n3 == 39 || n3 == 40 || n3 == 41 || n3 == 42 || n3 == 48 || n3 == 49 || n3 == 50 || n3 == 52 || n3 == 53 || n3 == 54 || n3 == 55) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray59 = new byte[8];
            byArray59[4] = 1;
            byArray59[5] = 1;
            byArray59[6] = 1;
            byArray59[7] = 1;
            byArrayArray[1] = byArray59;
            byArrayArray[2] = new byte[1];
            byArrayArray[3] = new byte[8];
            byte[] byArray60 = new byte[12];
            byArray60[1] = -1;
            byArray60[3] = -1;
            byArray60[5] = -1;
            byArray60[7] = -1;
            byArray60[9] = -1;
            byArray60[11] = -1;
            byArrayArray[4] = byArray60;
            return new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 2, 1, 1, 1});
        }
        if (n3 == 101) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray61 = new byte[12];
            byArray61[4] = 1;
            byArray61[5] = 1;
            byArray61[6] = 1;
            byArray61[7] = 1;
            byArray61[8] = 2;
            byArray61[9] = 2;
            byArray61[10] = 2;
            byArray61[11] = 2;
            byArrayArray[1] = byArray61;
            byArrayArray[2] = new byte[4];
            byArrayArray[3] = new byte[8];
            byte[] byArray62 = new byte[12];
            byArray62[1] = -1;
            byArray62[3] = -1;
            byArray62[5] = -1;
            byArray62[7] = -1;
            byArray62[9] = -1;
            byArray62[11] = -1;
            byArrayArray[4] = byArray62;
            object = new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{2, 3, 1, 1, 1});
            return object;
        }
        if (n3 == 102) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byte[] byArray63 = new byte[7];
            byArray63[2] = 1;
            byArray63[3] = 1;
            byArray63[4] = 1;
            byArray63[5] = 2;
            byArray63[6] = 2;
            byArrayArray[1] = byArray63;
            byte[] byArray64 = new byte[16];
            byArray64[7] = 1;
            byArray64[8] = 1;
            byArray64[9] = 1;
            byArray64[10] = 1;
            byArray64[11] = 1;
            byArray64[12] = 1;
            byArray64[13] = 1;
            byArray64[14] = 1;
            byArray64[15] = 1;
            byArrayArray[2] = byArray64;
            byArrayArray[3] = new byte[8];
            byte[] byArray65 = new byte[12];
            byArray65[1] = -1;
            byArray65[3] = -1;
            byArray65[5] = -1;
            byArray65[7] = -1;
            byArray65[9] = -1;
            byArray65[11] = -1;
            byArrayArray[4] = byArray65;
            byte[][] byArrayArray12 = new byte[3][];
            byte[] byArray66 = new byte[5];
            byArray66[2] = 1;
            byArray66[3] = 2;
            byArray66[4] = 2;
            byArrayArray12[0] = byArray66;
            byArrayArray12[1] = new byte[]{3, 3, 3};
            byArrayArray12[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 3, 2, 1, 1}, image5, byArrayArray12, 4);
            return object;
        }
        if (n3 == 103) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[16];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 1;
            byArray[15] = 1;
            byArrayArray[0] = byArray;
            byArrayArray[1] = new byte[7];
            byte[] byArray67 = new byte[16];
            byArray67[7] = 1;
            byArray67[8] = 1;
            byArray67[9] = 1;
            byArray67[10] = 1;
            byArray67[11] = 1;
            byArray67[12] = 1;
            byArray67[13] = 1;
            byArray67[14] = 1;
            byArray67[15] = 1;
            byArrayArray[2] = byArray67;
            byArrayArray[3] = new byte[8];
            byte[] byArray68 = new byte[12];
            byArray68[1] = -1;
            byArray68[3] = -1;
            byArray68[5] = -1;
            byArray68[7] = -1;
            byArray68[9] = -1;
            byArray68[11] = -1;
            byArrayArray[4] = byArray68;
            byte[][] byArrayArray13 = new byte[3][];
            byte[] byArray69 = new byte[5];
            byArray69[2] = 1;
            byArray69[3] = 1;
            byArray69[4] = 1;
            byArrayArray13[0] = byArray69;
            byArrayArray13[1] = new byte[]{2, 2, 3, 3};
            byArrayArray13[2] = new byte[]{-1};
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{2, 1, 2, 1, 1}, image5, byArrayArray13, 4);
            return object;
        }
        if (n3 == 104) {
            byte[][] byArrayArray = new byte[5][];
            byte[] byArray = new byte[21];
            byArray[7] = 1;
            byArray[8] = 1;
            byArray[9] = 1;
            byArray[10] = 1;
            byArray[11] = 1;
            byArray[12] = 1;
            byArray[13] = 1;
            byArray[14] = 2;
            byArray[15] = 2;
            byArray[16] = 2;
            byArray[17] = 2;
            byArray[18] = 2;
            byArray[19] = 2;
            byArray[20] = 2;
            byArrayArray[0] = byArray;
            byte[] byArray70 = new byte[8];
            byArray70[4] = 1;
            byArray70[5] = 1;
            byArray70[6] = 1;
            byArray70[7] = 1;
            byArrayArray[1] = byArray70;
            byte[] byArray71 = new byte[21];
            byArray71[7] = 1;
            byArray71[8] = 1;
            byArray71[9] = 1;
            byArray71[10] = 1;
            byArray71[11] = 1;
            byArray71[12] = 1;
            byArray71[13] = 1;
            byArray71[14] = 2;
            byArray71[15] = 2;
            byArray71[16] = 2;
            byArray71[17] = 2;
            byArray71[18] = 2;
            byArray71[19] = 2;
            byArray71[20] = 2;
            byArrayArray[2] = byArray71;
            byArrayArray[3] = new byte[8];
            byte[] byArray72 = new byte[12];
            byArray72[1] = -1;
            byArray72[3] = -1;
            byArray72[5] = -1;
            byArray72[7] = -1;
            byArray72[9] = -1;
            byArray72[11] = -1;
            byArrayArray[4] = byArray72;
            object = new nl(1, new Image[]{object, image2, object, image, image4}, byArrayArray, new int[]{3, 2, 3, 1, 1}, image5, new byte[][]{new byte[5], {1, 1, 2, 2}, {-1}}, 3);
            return object;
        }
        object = mv.m(100005);
        image2 = mv.m(100001);
        image3 = mv.m(100004);
        image = mv.m(100003);
        image4 = mv.m(100002);
        mv.m(100006);
        byte[][] byArrayArray = new byte[5][];
        byte[] byArray = new byte[28];
        byArray[7] = 1;
        byArray[8] = 1;
        byArray[9] = 1;
        byArray[10] = 1;
        byArray[11] = 1;
        byArray[12] = 1;
        byArray[13] = 1;
        byArray[14] = 2;
        byArray[15] = 2;
        byArray[16] = 2;
        byArray[17] = 2;
        byArray[18] = 2;
        byArray[19] = 2;
        byArray[20] = 2;
        byArray[21] = 1;
        byArray[22] = 1;
        byArray[23] = 1;
        byArray[24] = 1;
        byArray[25] = 1;
        byArray[26] = 1;
        byArray[27] = 1;
        byArrayArray[0] = byArray;
        byte[] byArray73 = new byte[8];
        byArray73[4] = 1;
        byArray73[5] = 1;
        byArray73[6] = 1;
        byArray73[7] = 1;
        byArrayArray[1] = byArray73;
        byArrayArray[2] = new byte[1];
        byArrayArray[3] = new byte[8];
        byte[] byArray74 = new byte[12];
        byArray74[1] = -1;
        byArray74[3] = -1;
        byArray74[5] = -1;
        byArray74[7] = -1;
        byArray74[9] = -1;
        byArray74[11] = -1;
        byArrayArray[4] = byArray74;
        return new ne(1, new Image[]{object, image2, image3, image, image4}, byArrayArray, new int[]{3, 2, 1, 1, 1});
    }

    private static ni b(lf object, int n2) {
        try {
            lz[] lzArray = lz.a((lf)object);
            la la2 = la.a((lf)object);
            np np2 = np.a((lf)object);
            me me2 = lz.a((lf)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            me2.a(la2);
            me2.a(np2);
            me me3 = lz.b((lf)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            me3.a(la2);
            me3.a(np2);
            me me4 = lz.c((lf)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            me4.a(la2);
            me4.a(np2);
            me me5 = lz.e((lf)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            me5.a(la2);
            me5.a(np2);
            me me6 = lz.d((lf)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            me6.a(la2);
            me6.a(np2);
            me me7 = lz.f((lf)object, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            me7.a(la2);
            me7.a(np2);
            object = lz.a((lf)object, mn.a().h, lzArray[0], lzArray[1], lzArray[3], lzArray[2], true);
            ((me)object).a(la2);
            ((me)object).a(np2);
            return new ng(n2, me2, me4, me3, me5, me6, me7, (ma)object, np2);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    private void c(boolean bl2) {
        Object object = this;
        Image image = f.d("/play/hpbar");
        Object object2 = f.b("/play/hpbar");
        h.a(object2, 100);
        Image image2 = f.a(object2);
        h.a(object2, 150);
        Image image3 = f.a(object2);
        h.a(object2, -110);
        Image image4 = f.a(object2);
        h.a(object2, -30);
        object2 = f.a(object2);
        ((mv)object).C = new Image[]{image, image2, image3, image4, (Image)object2};
        this.D = f.d("/play/manabar");
        this.E = f.d("/play/powerbar");
        if (bl2) {
            int n2 = 0;
            while (n2 < this.C.length) {
                this.C[n2] = Image.createImage((Image)this.C[n2], (int)0, (int)0, (int)this.C[n2].getWidth(), (int)this.C[n2].getHeight(), (int)7);
                ++n2;
            }
            this.D = Image.createImage((Image)this.D, (int)0, (int)0, (int)this.D.getWidth(), (int)this.D.getHeight(), (int)7);
            this.E = Image.createImage((Image)this.E, (int)0, (int)0, (int)this.E.getWidth(), (int)this.E.getHeight(), (int)7);
        }
        if (op.o == 1) {
            byte[] byArray = f.b("/play/hpbar");
            object = byArray;
            h.a(byArray, 160);
            this.F = f.a((byte[])object);
            if (bl2) {
                this.F = Image.createImage((Image)this.F, (int)0, (int)0, (int)this.F.getWidth(), (int)this.F.getHeight(), (int)7);
            }
        }
    }

    public final int[] a() {
        return this.B[0];
    }

    public final int[] b() {
        return this.B[3];
    }

    public final int[] c() {
        return this.B[1];
    }

    public final int[] d() {
        return this.B[4];
    }

    public final int[] e() {
        return this.B[2];
    }

    public final int[] f() {
        return this.B[5];
    }

    public final int[] g() {
        return new int[]{this.a[1].n(), this.a[1].o(), this.a[1].p(), this.a[1].q()};
    }

    public final int[] h() {
        return new int[]{this.a[0].n(), this.a[0].o(), this.a[0].p(), this.a[0].q()};
    }

    public final ni[] a(int n2) {
        if (n2 == 0) {
            return this.H;
        }
        return this.I;
    }

    /*
     * Unable to fully structure code
     */
    public final void a(Graphics var1_1, int var2_2, int var3_3) {
        block32: {
            block34: {
                block33: {
                    if (this.s) break block32;
                    var4_4 = this.e;
                    if (this.k == 1) {
                        var4_4 = this.f;
                    }
                    var5_6 = 0;
                    if (this.d >= 10) break block33;
                    if (this.d % 2 != 0) break block34;
                    ** GOTO lbl-1000
                }
                if (this.d < 18) {
                    var5_6 = this.d - 3 & 3;
                } else lbl-1000:
                // 2 sources

                {
                    var5_6 = 3;
                }
            }
            var6_7 = this.K.a - 1;
            var7_10 = this.K.b - 1;
            var8_15 = this.K.c + 1;
            var9_18 = this.K.d + 1;
            --var5_6;
            var10_19 = 0;
            while (var10_19 < var5_6) {
                var1_1.setColor(var4_4[var10_19]);
                var1_1.drawRect(var6_7, var7_10, var8_15, var9_18);
                --var6_7;
                --var7_10;
                var8_15 += 2;
                var9_18 += 2;
                ++var10_19;
            }
            if (var5_6 >= 0) {
                cz.b(var1_1, var4_4[var5_6], var6_7, var7_10, var8_15 + 1, var9_18 + 1);
            }
        }
        var4_5 = 0;
        var5_6 = 0;
        while (var5_6 < this.G.length) {
            var11_20 = this.P[var5_6];
            if (var11_20.b > 1 || this.M[var5_6] < 2) {
                var11_21 = this.A;
                var10_19 = this.B[var4_5 + 0][1];
                var9_18 = this.B[var4_5 + 0][0];
                var8_16 = this.C;
                var7_11 = var1_1;
                var6_8 = this.P[var5_6];
                var12_26 = var6_8.c;
                var13_27 = var12_26 - 1;
                if (var11_21 == 0) {
                    var11_22 = var6_8;
                    var11_23 = var11_22.b;
                    var14_28 = var8_16[0].getHeight();
                    cz.a(var7_11, var8_16[var12_26], 0, 0, var11_23, var14_28, var9_18, var10_19, 0);
                    if (var13_27 >= 0) {
                        cz.a(var7_11, var8_16[var13_27], var11_23, 0, var6_8.a - var11_23, var14_28, var9_18 + var11_23, var10_19, 0);
                    }
                } else {
                    var11_24 = var6_8;
                    var14_28 = var11_24.b;
                    var11_25 = var8_16[0].getWidth();
                    var6_9 = var8_16[0].getHeight() - var14_28;
                    cz.a(var7_11, var8_16[var12_26], 0, var6_9, var11_25, var14_28, var9_18, var10_19 + var6_9, 0);
                    if (var13_27 >= 0) {
                        cz.a(var7_11, var8_16[var13_27], 0, 0, var11_25, var6_9, var9_18, var10_19, 0);
                    }
                }
            }
            this.Q[var5_6].a(var1_1, this.D, this.B[var4_5 + 1][0], this.B[var4_5 + 1][1], this.A);
            if (this.y[var5_6] <= 0) {
                this.R[var5_6].a(var1_1, this.E, this.B[var4_5 + 2][0], this.B[var4_5 + 2][1], this.A);
            }
            var4_5 += 3;
            ++var5_6;
        }
        if (op.o == 1 && this.S != null && this.T <= 0) {
            this.S.a(var1_1, this.F, this.B[5][0], this.B[5][1], this.A);
        }
        var5_6 = this.a[1].c();
        if (this.L[0] != null) {
            this.L[0].b(var1_1, this.a[0].n() + this.a[0].p() / 2, this.a[0].o() + this.a[0].q() - 15);
        }
        if (this.L[1] != null) {
            this.L[1].b(var1_1, this.a[1].n() + this.a[1].p() / 2, this.a[1].o() + this.a[1].q() - 15);
        }
        if (var5_6 == 2 || var5_6 == 5 || var5_6 == 1) {
            var7_12 = 0;
            while (var7_12 < this.H.length) {
                if (this.a[0] != this.H[var7_12]) {
                    this.H[var7_12].a(var1_1, var2_2, var3_3);
                }
                ++var7_12;
            }
            this.a[0].a(var1_1);
            var7_12 = 0;
            while (var7_12 < this.I.length) {
                if (this.a[1] != this.I[var7_12]) {
                    this.I[var7_12].a(var1_1, var2_2, var3_3);
                }
                ++var7_12;
            }
            this.a[1].a(var1_1);
        } else {
            var7_12 = 0;
            while (var7_12 < this.I.length) {
                if (this.a[1] != this.I[var7_12]) {
                    this.I[var7_12].a(var1_1, var2_2, var3_3);
                }
                ++var7_12;
            }
            this.a[1].a(var1_1);
            var7_12 = 0;
            while (var7_12 < this.H.length) {
                if (this.a[0] != this.H[var7_12]) {
                    this.H[var7_12].a(var1_1, var2_2, var3_3);
                }
                ++var7_12;
            }
            this.a[0].a(var1_1);
        }
        if (this.L[0] != null) {
            this.L[0].c(var1_1, this.H[0].n() + this.H[0].p() / 2, this.H[0].o() + this.H[0].q() - 15);
        }
        if (this.L[1] != null) {
            this.L[1].c(var1_1, this.H[1].n() + this.H[1].p() / 2, this.H[1].o() + this.H[1].q() - 15);
        }
        if (this.h > 0 && this.h <= 10) {
            v0 = var7_13 = this.g == 0 ? this.B[6] : this.B[9];
            if (this.h <= 6 && !this.b.a) {
                var8_17 = this.j.getWidth() / 5;
                var9_18 = this.j.getHeight();
                var5_6 = (6 - this.h) % 5;
                var10_19 = 2;
                if (this.g == 0) {
                    var10_19 = 0;
                }
                var1_1.drawRegion(this.j, var5_6 * var8_17, 0, var8_17, var9_18, var10_19, var7_13[0] + var7_13[2] / 2, var7_13[1] + var7_13[3] / 2, 3);
            }
        }
        var7_14 = this.v.length - 1;
        while (var7_14 >= 0) {
            this.v[var7_14].a(var1_1);
            --var7_14;
        }
        var7_14 = this.u.length - 1;
        while (var7_14 >= 0) {
            this.u[var7_14].a(var1_1);
            --var7_14;
        }
    }

    public final void b(int n2) {
        this.a[0].a(0);
    }

    public final void i() {
        Object object;
        int n2;
        int n3 = 0;
        while (n3 < this.H.length) {
            this.H[n3].i();
            ++n3;
        }
        n3 = 0;
        while (n3 < this.I.length) {
            this.I[n3].i();
            ++n3;
        }
        if (this.L[0] != null) {
            this.L[0].i();
        }
        if (this.L[1] != null) {
            this.L[1].i();
        }
        if (!this.s) {
            ++this.t;
            if (this.t == 10) {
                this.t = 0;
            }
            if (this.d < 20) {
                ++this.d;
            }
        }
        if (this.h > 0) {
            --this.h;
            if (this.h == 9) {
                if (!this.U && this.a[this.g].c() != 3) {
                    this.a[this.g].a(this.b.a, this.b.f);
                }
                if (!this.b.a && this.g == 0) {
                    ak.a().b(250);
                }
            } else if (this.h == 15) {
                if (this.b.c) {
                    n2 = 0;
                    object = this.b;
                    this.b.c = n2;
                    ak.a().a(20);
                }
            } else if (this.h == 0) {
                this.U = this.i > 0;
                this.h = this.i;
                this.i = 0;
            }
        }
        n3 = 0;
        while (n3 < this.G.length) {
            if (this.y[n3] > 0) {
                int n4 = n3;
                this.y[n4] = this.y[n4] - 1;
            } else if (this.G[n3][0].p()) {
                this.y[n3] = 1;
            }
            int n5 = n3;
            this.M[n5] = this.M[n5] + 1;
            if (this.M[n3] > 2) {
                this.M[n3] = 0;
            }
            if (this.g == n3 && this.w > 0) {
                --this.w;
            } else {
                this.P[n3].a();
                this.Q[n3].a();
                this.R[n3].a();
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < this.z.length) {
            le le2 = this.G[n3][this.N[n3]];
            if (0 <= this.G[n3][this.N[n3]].k()) {
                int n6 = n3;
                this.z[n6] = this.z[n6] + 1;
                if (this.z[n3] > 1) {
                    this.z[n3] = 0;
                }
            }
            ++n3;
        }
        n3 = this.u.length - 1;
        while (n3 >= 0) {
            this.u[n3].i();
            --n3;
        }
        n3 = this.v.length - 1;
        while (n3 >= 0) {
            this.v[n3].i();
            --n3;
        }
        if (this.x > 0) {
            --this.x;
        }
        if (this.S != null) {
            n3 = com.mg.sq.a.i == 0 ? this.E.getWidth() : this.E.getHeight();
            int n7 = (int)(this.c.g() * (long)n3 / (long)this.b.g);
            if (n7 <= 0) {
                n7 = 1;
            } else if (n7 > n3) {
                n7 = n3;
            }
            n2 = n7;
            object = this.S;
            this.S.b = n2;
            if (this.V > 0) {
                --this.V;
                if (this.V == 0) {
                    this.T = 0;
                    return;
                }
                if (this.T > 0) {
                    --this.T;
                    return;
                }
                this.T = 1;
                return;
            }
            if (this.T > 0) {
                --this.T;
                return;
            }
            if (n7 == n3) {
                this.T = 1;
            }
        }
    }

    public final void c(int n2) {
        if (this.w == 0) {
            this.w = n2;
            return;
        }
        if (this.w < n2) {
            this.w = this.w + n2 >> 1;
        }
    }

    public final void a(int n2, int n3) {
        le le2 = this.G[n2][this.N[0]];
        int n4 = n2 = n2 == 0 ? 1 : 4;
        if (this.A == 0) {
            int n5 = this.B[n2][0] + this.C[0].getWidth() * le2.n() / le2.o();
            n2 = this.B[n2][1] + this.B[n2][3] / 2;
            int n6 = 0;
            while (n6 < this.v.length) {
                if (!this.v[n6].m()) {
                    this.v[n6].a(n5, n2, n5 - 5 + cy.a(80), n2 + cy.a() % 100, 10);
                    this.v[n6].a(cy.a(10));
                }
                ++n6;
            }
            return;
        }
        int n7 = this.B[n2][0] + this.B[n2][2] / 2;
        n2 = this.B[n2][1] + (le2.o() - le2.n()) * this.D.getHeight() / le2.o();
        int n8 = 0;
        while (n8 < this.v.length) {
            if (!this.v[n8].m()) {
                this.v[n8].a(n7, n2, n7 + cy.a() % 100, n2 + 5 - cy.a(80), 10);
                this.v[n8].a(cy.a(10));
            }
            ++n8;
        }
    }

    public final void a(int n2, int n3, boolean bl2) {
        this.U = bl2;
        this.c(this.w);
        le le2 = this.G[n2][this.N[n2]];
        if (this.h <= 0) {
            this.h = n3;
        }
        if (this.h < n3) {
            this.i = n3 - this.h;
            if (this.i < 6) {
                this.i = 0;
            }
        } else {
            this.i = this.h - n3;
            this.h = n3;
        }
        if ((n3 -= 5) < 0) {
            n3 = 0;
        }
        this.g = n2;
        int n4 = n2 == 0 ? 0 : 3;
        int n5 = 15;
        if (this.A == 0) {
            int n6 = this.B[n4][0] + this.C[0].getWidth() * le2.m() / le2.l();
            int n7 = this.B[n4][1] + this.B[n4][3] / 2;
            n4 = 0;
            while (n4 < this.u.length) {
                if (!this.u[n4].m()) {
                    this.u[n4].a(n6, n7, n6 - 5 + cy.a(80), n7 + cy.a() % 100, n3);
                    this.u[n4].a(cy.a(10));
                    if (--n5 <= 0) break;
                }
                ++n4;
            }
        } else {
            int n8 = this.B[n4][0] + this.B[n4][2] / 2;
            int n9 = this.B[n4][1] + (le2.l() - le2.m()) * this.C[0].getHeight() / le2.l();
            n4 = 0;
            while (n4 < this.u.length) {
                if (!this.u[n4].m()) {
                    this.u[n4].a(n8, n9, n8 + cy.a() % 100, n9 + 5 - cy.a(80), n3);
                    this.u[n4].a(cy.a(10));
                    if (--n5 <= 0) break;
                }
                ++n4;
            }
        }
        if (bl2) {
            if (n2 == 1) {
                this.a[0].a(n3, this.b.a, this.b.c, this.b.d, this.b.e, this.b.f);
            } else {
                this.a[1].a(n3, this.b.a, this.b.c, this.b.d, this.b.e, this.b.f);
            }
            n3 = 0;
            mq mq2 = this.b;
            this.b.c = n3;
            this.b.d = false;
        }
    }

    public final void d(int n2) {
        if (this.k != n2) {
            this.d = 0;
        }
        this.k = n2;
    }

    public final void a(boolean bl2) {
        this.s = bl2;
    }

    public final void l(int n2) {
        if (n2 == 0) {
            n2 = 0;
            while (n2 < this.H.length) {
                this.H[n2].d();
                ++n2;
            }
            return;
        }
        n2 = 0;
        while (n2 < this.I.length) {
            this.I[n2].d();
            ++n2;
        }
    }

    public final void a(int n2, int n3, int n4, String string, int n5) {
        try {
            int n6;
            int n7 = 0;
            int n8 = 0;
            while (n8 < this.J.length) {
                if (this.J[n8].equals(string)) {
                    n7 = 1;
                    break;
                }
                ++n8;
            }
            if (n7 == 0) {
                return;
            }
            le le2 = null;
            n7 = 0;
            int n9 = 0;
            while (n9 < this.G.length) {
                n6 = 0;
                while (n6 < this.G[n9].length) {
                    if (this.G[n9][n6].j().equals(string)) {
                        le2 = this.G[n9][n6];
                        n7 = n9;
                        break;
                    }
                    ++n6;
                }
                ++n9;
            }
            if (le2 == null) {
                return;
            }
            if (n2 >= 0) {
                n9 = le2.m();
                n6 = le2.l();
                this.P[n7].a(n9, n2, n6, n5);
            }
            if (n3 >= 0) {
                n9 = le2.n();
                n6 = le2.o();
                this.Q[n7].a(n9, n3, n6, n5);
            }
            if (n4 >= 0) {
                n9 = le2.q();
                n6 = le2.r();
                this.R[n7].a(n9, n4, n6, n5);
            }
            n3 = n5;
            mg mg2 = this.P[n7];
            this.P[n7].c = n3;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void j() {
        this.V = 15;
    }
}

