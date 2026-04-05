/*
 * Decompiled with CFR 0.152.
 */
public class iy
extends ir {
    private static byte[][] x;

    static {
        byte[][] byArrayArray = new byte[2][];
        byArrayArray[0] = new byte[1];
        byte[] byArray = new byte[7];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 2;
        byArray[6] = 2;
        byArrayArray[1] = byArray;
        x = byArrayArray;
    }

    public iy() {
        mn.a().a(4000);
        this.s = mn.a().D;
        this.t = mn.a().E;
        this.a(x);
        this.r = false;
        this.v = 1;
        this.w = 3;
    }

    public final void d(int n2) {
        super.d(n2);
        if (n2 == 1) {
            this.j(3);
        }
    }
}

