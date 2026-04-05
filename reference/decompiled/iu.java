/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class iu
extends il {
    private static int[][] t;
    private static byte[][] u;
    protected ln[] s;
    private int v;
    private int w;
    private int x = 0;

    static {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[6];
        nArray[0] = 138;
        nArray[2] = 26;
        nArray[3] = 19;
        nArray[4] = 56;
        nArray[5] = 24;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{138, 19, 57, 27, 41, 20};
        int[] nArray2 = new int[6];
        nArray2[2] = 138;
        nArray2[3] = 66;
        nArrayArray[2] = nArray2;
        t = nArrayArray;
        byte[][] byArrayArray = new byte[2][];
        byArrayArray[0] = new byte[]{-1};
        byte[] byArray = new byte[7];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 2;
        byArray[6] = 2;
        byArrayArray[1] = byArray;
        u = byArrayArray;
    }

    public iu() {
        mn.a().a(1005);
        this.a(mn.a().p, t);
        this.a(u);
        this.o = 155;
        this.p = 66;
        this.s = new ln[6];
        int n2 = 0;
        while (n2 < this.s.length) {
            this.s[n2] = new ln(0);
            ++n2;
        }
        this.r = false;
        this.a(false);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        if ((n2 -= 60) < n4) {
            this.c(n2, n3);
            this.c(0);
            this.j(6);
            this.h = n4;
        } else {
            this.h = n4 - 50;
            this.c(n2, n3);
            this.c(2);
            this.j(6);
        }
        this.v = n4;
        this.j = 0;
        this.x = 0;
        if (n6 > 0) {
            this.w = n6;
            this.d(0);
        } else {
            this.d(1);
        }
        this.d(1);
        this.r = true;
    }

    public final void k() {
        int n2;
        int n3 = 0;
        while (n3 < this.s.length) {
            this.s[n3].i();
            ++n3;
        }
        if (this.e == 1) {
            ++this.x;
            this.j += this.x;
            if (this.e(this.h, this.j)) {
                n3 = this.o() + 30;
                n2 = 0;
                while (n2 < 3) {
                    this.s[n2 << 1].b(this.v - 10 + cy.a() % 5, n3 + cy.a() % 10, cy.a(6));
                    this.s[(n2 << 1) + 1].b(this.v + 10 + cy.a() % 5, n3 + cy.a() % 10, cy.a(6));
                    n3 -= 25;
                    ++n2;
                }
                this.d(0);
            }
        }
        if (this.e == 0) {
            if (this.w > 0) {
                --this.w;
                if (this.w == 0) {
                    this.d(1);
                }
                return;
            }
            n3 = 0;
            n2 = 0;
            while (n2 < this.s.length) {
                if (this.s[n2].m()) {
                    n3 = 1;
                    break;
                }
                ++n2;
            }
            if (n3 == 0) {
                this.r = false;
            }
        }
    }

    public final void a(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.s.length) {
            this.s[n2].a(graphics);
            ++n2;
        }
        super.a(graphics);
    }
}

