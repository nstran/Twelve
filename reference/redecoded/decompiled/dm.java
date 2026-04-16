/*
 * Decompiled with CFR 0.152.
 */
public final class dm {
    private a d = new a();
    public int a;
    public int b = -1;
    public int c = 0;

    public dm(String string, int n2, int n3, int n4) {
        this.a = n4;
        this.a(string, n2, n3);
    }

    public final void a(String string, int n2, int n3) {
        this.d.a(new dj(string, n2, n3));
    }

    public final int a() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.d.d()) {
            n2 += this.a(n3);
            ++n3;
        }
        return n2 -= 4;
    }

    public final int a(int n2) {
        return this.b(n2).c() + 8;
    }

    public final dj b(int n2) {
        return (dj)this.d.b(n2);
    }

    public final ls c(int n2) {
        dj dj2 = this.b(n2);
        return dj2.a;
    }

    public final ls b() {
        dj dj2 = this.b(this.c);
        return dj2.a;
    }

    public final int c() {
        return this.d.d();
    }
}

