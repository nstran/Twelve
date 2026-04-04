/*
 * Decompiled with CFR 0.152.
 */
public class dm {
    lq a;
    private int b;

    public final lq a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public dm(String string, int n2, int n3) {
        this.b = n2 - 1;
        if (!ov.g) {
            this.b = -1;
        }
        n2 = 0;
        if (this.b >= 0 && this.b < 20) {
            n2 = ov.c[this.b] + 2;
        } else {
            this.b = -1;
        }
        if (string.equals("") || string.equals("")) {
            string = " ";
        }
        this.a = new lq(ca.d, string, n2, 0, n3 - n2, ca.d.a(), 2);
    }

    public final int c() {
        int n2 = this.a.c();
        if (this.b >= 0 && n2 < ov.d[this.b]) {
            n2 = ov.d[this.b];
        }
        return n2;
    }

    public dm() {
    }
}

