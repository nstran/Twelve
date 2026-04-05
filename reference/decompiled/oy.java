/*
 * Decompiled with CFR 0.152.
 */
final class oy
implements kn {
    private int b;
    private int c;
    byte[] a;
    private ox d;

    oy(ox ox2) {
        this.d = ox2;
    }

    public final void a(int n2, int n3, byte[] byArray) {
        System.arraycopy(byArray, 0, this.a, this.c, byArray.length);
        this.c += byArray.length;
        nv.f(byArray.length);
        if (++n3 < this.b) {
            kq.a().a(n2, n3);
            return;
        }
        ox.a(this.d);
    }

    public final void a(int n2, int n3, int n4) {
        this.a = new byte[n3];
        this.b = n4;
        this.c = 0;
        kq.a().a(n2, 0);
    }
}

