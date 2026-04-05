/*
 * Decompiled with CFR 0.152.
 */
public final class is
extends il {
    private static byte[][] s;
    private int t;

    static {
        byte[][] byArrayArray = new byte[2][];
        byArrayArray[0] = new byte[]{-1};
        byte[] byArray = new byte[11];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 2;
        byArray[6] = 3;
        byArray[7] = 3;
        byArray[8] = 4;
        byArray[9] = 4;
        byArray[10] = 4;
        byArrayArray[1] = byArray;
        s = byArrayArray;
    }

    public is() {
        mn.a().a(1001);
        this.a(mn.a().m, 5);
        this.a(s);
        this.r = false;
        this.j(3);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.c(n2, n3);
        this.g = 0;
        if (n6 <= 0) {
            this.d(1);
        } else {
            this.d(0);
        }
        this.r = true;
        this.t = n6;
    }

    public final void k() {
        if (this.t > 0) {
            --this.t;
            if (this.t == 0) {
                this.d(1);
            }
        }
        if (this.e == 1 && this.j()) {
            this.r = false;
        }
    }
}

