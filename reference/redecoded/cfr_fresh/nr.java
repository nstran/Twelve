/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nr
extends as {
    private as s;
    private as t;
    private final int[][] u;
    private Image v;
    private int w;

    private nr(int n2) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[2];
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{2, 3};
        nArrayArray[2] = new int[]{3, 3, 3, 3};
        this.u = nArrayArray;
        int n3 = n2;
        Object object = this;
        object = pa.a();
        this.v = ((pa)object).a(n3, false);
        this.w = n2;
        object = this;
        this.s = new as(((nr)object).v, 4);
        ((nr)object).s.a(((nr)object).u);
        ((nr)object).s.d(0);
        ((nr)object).s.b(8);
        ((nr)object).t = new as(((nr)object).v, 4);
        ((nr)object).t.a(((nr)object).u);
        ((nr)object).t.d(0);
        ((nr)object).t.c(2);
        ((nr)object).t.b(8);
    }

    public final void d(int n2) {
        this.s.d(n2);
        this.s.a(0);
        this.t.a(0);
        switch (n2) {
            case 2: {
                this.s.b(2);
                this.t.b(2);
                return;
            }
        }
        this.s.b(8);
        this.t.b(8);
    }

    public final void c(int n2) {
        this.s.c(n2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        if (this.w == 130000) {
            n3 -= 16;
        }
        if (this.s.g() == 0) {
            n4 = n2 + 20;
            if (this.s.h() == 2) {
                if (this.s.f() == 0 || this.s.f() == 3) {
                    n3 -= 5;
                    n4 = n2 + 25;
                } else {
                    n3 -= 5;
                    n4 = n2 + 20;
                }
            }
        } else if (this.s.h() == 1) {
            n4 = n2 - 30;
        } else if (this.s.h() == 2) {
            if (this.s.f() == 0 || this.s.f() == 3) {
                n3 -= 5;
                n4 = n2 - 33;
            } else {
                n3 -= 5;
                n4 = n2 - 29;
            }
        } else {
            n4 = n2 + 5;
        }
        if (this.s.h() == 0) {
            this.s.c(0);
            if (this.t != null) {
                this.t.a(graphics, n4 - this.t.p() / 2, n3);
            }
        }
        if (this.s != null) {
            this.s.a(graphics, n4, n3);
        }
    }

    public final void k() {
        if (this.s != null) {
            this.s.i();
        }
        if (this.t != null) {
            this.t.i();
        }
    }

    public static nr a(lh lh2) {
        if (lh2.b() != null) {
            return new nr(lh2.b().n);
        }
        return null;
    }
}

