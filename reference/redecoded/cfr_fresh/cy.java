/*
 * Decompiled with CFR 0.152.
 */
final class cy {
    int[] a;
    long b = 0L;
    byte[] c = new byte[64];

    public cy() {
        this.a = new int[4];
        this.a[0] = 1732584193;
        this.a[1] = -271733879;
        this.a[2] = -1732584194;
        this.a[3] = 271733878;
    }

    public cy(cy cy2) {
        this();
        int n2 = 0;
        while (n2 < this.c.length) {
            this.c[n2] = cy2.c[n2];
            ++n2;
        }
        n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = cy2.a[n2];
            ++n2;
        }
        this.b = cy2.b;
    }
}

