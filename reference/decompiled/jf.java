/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class jf
extends il {
    private int[] s;
    private int[] t;
    private int u;
    private Image[] v;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private int B;

    public jf() {
        mn.a().a(2000);
        this.a(mn.a().u, 2);
        this.v = mn.a().v;
        this.A = this.v[0].getWidth() >> 1;
        this.B = this.v[1].getWidth() >> 1;
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[4];
        byArray[2] = 1;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        this.a(byArrayArray);
        this.q = 3;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.u = 1;
        this.r = true;
        this.w = n6;
        this.x = 5;
    }

    public final void a(int[] nArray, int[] nArray2) {
        this.s = nArray;
        this.t = nArray2;
    }

    public final void k() {
        if (this.x <= 0) {
            if (this.u < this.s.length) {
                ++this.u;
            }
            this.x = 5;
        } else {
            --this.x;
        }
        if (this.w > 0) {
            --this.w;
            if (this.w == 0) {
                this.r = false;
            }
        }
        if (this.z > 0) {
            --this.z;
            return;
        }
        this.z = 1;
        if (this.y == 0) {
            this.y = 1;
            return;
        }
        this.y = 0;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        n2 = 0;
        while (n2 < this.u) {
            super.a(graphics, this.s[n2], this.t[n2]);
            ++n2;
        }
        n2 = this.u - 1;
        n3 = 0;
        while (n3 < n2) {
            int n4 = this.t[n3 + 1];
            int n5 = this.s[n3 + 1];
            int n6 = this.t[n3];
            int n7 = this.s[n3];
            Graphics graphics2 = graphics;
            jf jf2 = this;
            int n8 = 0;
            int n9 = 0;
            boolean bl2 = true;
            boolean bl3 = true;
            do {
                if (bl2) {
                    bl2 = n7 > n5;
                    n8 = bl2 ? n7 - 28 : n7 + 28;
                    if (!(bl2 = bl2 == n8 > n5)) {
                        n8 = n5;
                    }
                }
                if (bl3) {
                    bl3 = n6 > n4;
                    n9 = bl3 ? n6 - 28 : n6 + 28;
                    if (!(bl3 = bl3 == n9 > n4)) {
                        n9 = n4;
                    }
                }
                if (n7 < n8) {
                    if (n6 < n9) {
                        cz.a(graphics2, jf2.v[0], jf2.A * jf2.y, 0, jf2.A, jf2.v[0].getHeight(), n7, n6, 20);
                    } else if (n6 > n9) {
                        graphics2.drawRegion(jf2.v[0], jf2.A * jf2.y, 0, jf2.A, jf2.v[0].getHeight(), 2, n7, n9, 20);
                    } else {
                        cz.a(graphics2, jf2.v[1], jf2.B * jf2.y, 0, jf2.B, jf2.v[1].getHeight(), n7, n6, 6);
                    }
                } else if (n7 > n8) {
                    if (n6 < n9) {
                        graphics2.drawRegion(jf2.v[0], jf2.A * jf2.y, 0, jf2.A, jf2.v[0].getHeight(), 2, n8, n6, 20);
                    } else if (n6 > n9) {
                        cz.a(graphics2, jf2.v[0], jf2.A * jf2.y, 0, jf2.A, jf2.v[0].getHeight(), n8, n9, 20);
                    } else {
                        cz.a(graphics2, jf2.v[1], jf2.B * jf2.y, 0, jf2.B, jf2.v[1].getHeight(), n8, n9, 6);
                    }
                } else if (n6 < n9) {
                    graphics2.drawRegion(jf2.v[1], jf2.B * jf2.y, 0, jf2.B, jf2.v[1].getHeight(), 5, n7, n6, 17);
                } else if (n6 > n9) {
                    graphics2.drawRegion(jf2.v[1], jf2.B * jf2.y, 0, jf2.B, jf2.v[1].getHeight(), 5, n7, n9, 17);
                }
                n7 = n8;
                n6 = n9;
            } while (bl2 || bl3);
            ++n3;
        }
    }
}

