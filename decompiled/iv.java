/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class iv
extends ir {
    private static byte[][] x;

    static {
        byte[][] byArrayArray = new byte[2][];
        byArrayArray[0] = new byte[1];
        byte[] byArray = new byte[9];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 2;
        byArray[6] = 3;
        byArray[7] = 3;
        byArray[8] = 3;
        byArrayArray[1] = byArray;
        x = byArrayArray;
    }

    public iv() {
        mn.a().a(1006);
        this.s = mn.a().q;
        this.t = mn.a().r;
        this.a(x);
        this.r = false;
        this.v = 1;
        this.w = 4;
    }

    public final void j(int n2) {
        this.q = 33;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.u > 0 || !this.r) {
            return;
        }
        int n4 = this.f[this.e][this.g];
        if (this.e == 1 && n4 != 3) {
            graphics.drawRegion(this.b, n4 * this.o, 0, this.o, this.p, 0, this.m + n2, this.n + n3, 36);
            graphics.drawRegion(this.b, n4 * this.o, 0, this.o, this.p, 2, this.m + n2, this.n + n3, 40);
            return;
        }
        super.a(graphics, n2, n3);
    }
}

