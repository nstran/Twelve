/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ir
extends il {
    private static byte[][] x;
    protected Image s;
    protected Image t;
    protected int u;
    protected int v;
    protected int w;

    static {
        byte[][] byArrayArray = new byte[2][];
        byArrayArray[0] = new byte[1];
        byte[] byArray = new byte[13];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 1;
        byArray[5] = 2;
        byArray[6] = 2;
        byArray[7] = 3;
        byArray[8] = 3;
        byArray[9] = 3;
        byArray[10] = 4;
        byArray[11] = 4;
        byArray[12] = 4;
        byArrayArray[1] = byArray;
        x = byArrayArray;
    }

    public ir() {
        mn.a().a(1000);
        this.s = mn.a().k;
        this.t = mn.a().l;
        this.a(x);
        this.r = false;
        this.v = 1;
        this.w = 5;
    }

    public void d(int n2) {
        super.d(n2);
        this.g = 0;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.b(n4, n5);
        this.c(n2, n3);
        if (n2 < n4) {
            this.c(2);
        } else {
            this.c(0);
        }
        this.a(this.s, this.v);
        this.d(0);
        this.j(33);
        this.j = ax.a(n2, n4, 10);
        this.k = ax.a(n3, n5, 10);
        this.r = true;
        this.u = n6;
    }

    public final void k() {
        if (this.u > 0) {
            --this.u;
            return;
        }
        if (this.e == 0) {
            if (this.b(this.h, this.i, this.j, this.k)) {
                this.a(this.t, this.w);
                this.j(3);
                this.d(1);
                return;
            }
        } else if (this.e == 1 && this.j()) {
            this.r = false;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.u > 0) {
            return;
        }
        super.a(graphics, n2, n3);
    }
}

