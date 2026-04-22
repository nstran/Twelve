/*
 * Decompiled with CFR 0.152.
 */
final class pb
implements ko {
    private int b;
    private int c;
    byte[] a;
    private pa d;

    pb(pa pa2) {
        this.d = pa2;
    }

    public final void a(int n2, int n3, byte[] byArray) {
        System.arraycopy(byArray, 0, this.a, this.c, byArray.length);
        this.c += byArray.length;
        nx.f(byArray.length);
        if (++n3 < this.b) {
            ks.a().a(n2, n3);
            return;
        }
        pa.a(this.d);
    }

    public final void a(int n2, int n3, int n4) {
        this.a = new byte[n3];
        this.b = n4;
        this.c = 0;
        ks.a().a(n2, 0);
    }
}

