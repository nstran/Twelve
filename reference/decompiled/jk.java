/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class jk
extends il {
    private static final byte[][] u;
    private int v = -1;
    public int s;
    public aw[] t;

    static {
        byte[][] byArrayArray = new byte[4][];
        byArrayArray[0] = new byte[]{-1};
        byte[] byArray = new byte[6];
        byArray[2] = 1;
        byArray[3] = 2;
        byArray[4] = 2;
        byArray[5] = 2;
        byArrayArray[1] = byArray;
        byArrayArray[2] = new byte[]{2};
        byArrayArray[3] = new byte[]{3, 3, 4, 4, 4};
        u = byArrayArray;
    }

    public jk() {
        mn.a().a(2008);
        Image image = mn.a().C;
        this.t = new aw[36];
        int n2 = 0;
        while (n2 < this.t.length) {
            this.t[n2] = new aw(image, 5);
            this.t[n2].a(u);
            this.t[n2].a(false);
            this.t[n2].j(3);
            this.t[n2].b(false);
            ++n2;
        }
        this.a(u);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.c(n2, n3);
        this.b(n4, n5);
        this.d(0);
        this.r = true;
    }

    public final void k() {
        int n2 = 0;
        while (n2 < this.s) {
            this.t[n2].i();
            ++n2;
        }
        switch (this.e) {
            case 0: {
                this.v = 5;
                n2 = 0;
                while (n2 < this.s) {
                    int n3 = n2;
                    this.t[n3].b(true);
                    this.t[n3].d(1);
                    int n4 = this.h + cy.a() % 60;
                    int n5 = this.i + cy.a() % 60;
                    int n6 = 7 + cy.a(10);
                    int n7 = Math.abs(this.t[n3].n() - n4) / n6;
                    n6 = Math.abs(this.t[n3].o() - n5) / n6;
                    if (n7 <= 0) {
                        n7 = 1;
                    }
                    if (n6 <= 0) {
                        n6 = 1;
                    }
                    this.t[n3].b(n4, n5);
                    this.t[n3].a(n7, n6);
                    ++n2;
                }
                this.d(1);
                return;
            }
            case 1: {
                if (this.v < 0) break;
                --this.v;
                if (this.v != 0) break;
                n2 = 0;
                while (n2 < this.s) {
                    int n8 = n2++;
                    this.t[n8].d(2);
                }
                this.d(2);
                return;
            }
            case 2: {
                n2 = 1;
                int n9 = 0;
                while (n9 < this.s) {
                    if (this.t[n9].h() == 2) {
                        if (this.t[n9].b(this.t[n9].a(), this.t[n9].b(), this.t[n9].c(), this.t[n9].d())) {
                            this.t[n9].d(3);
                        }
                        n2 = 0;
                    } else if (this.t[n9].m() && this.t[n9].h() == 3) {
                        if (this.t[n9].j()) {
                            this.t[n9].b(false);
                        } else {
                            n2 = 0;
                        }
                    }
                    ++n9;
                }
                if (n2 == 0) break;
                this.b(false);
            }
        }
    }

    public final void a(Graphics graphics) {
        if (!this.r) {
            return;
        }
        int n2 = 0;
        while (n2 < this.s) {
            this.t[n2].a(graphics);
            ++n2;
        }
    }
}

