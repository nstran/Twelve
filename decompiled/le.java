/*
 * Decompiled with CFR 0.152.
 */
public final class le {
    private lf a = null;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h;

    public le(lf object) {
        lf lf2 = object;
        object = this;
        this.a = lf2;
    }

    public final lf a() {
        return this.a;
    }

    public final boolean b() {
        return this.a.Q;
    }

    public final int c() {
        return this.a.G;
    }

    public final byte d() {
        return this.a.V;
    }

    public final boolean e() {
        return this.d > 0;
    }

    public final void a(int n2) {
        this.d = n2;
    }

    public final boolean f() {
        return this.g > 0;
    }

    public final void b(int n2) {
        this.g = n2;
    }

    public final boolean g() {
        return this.e > 0;
    }

    public final void c(int n2) {
        this.e = n2;
    }

    public final boolean h() {
        return this.f > 0;
    }

    public final void d(int n2) {
        this.f = n2;
    }

    public final void e(int n2) {
        this.h = n2;
    }

    public final boolean i() {
        return this.h > 0;
    }

    public final String j() {
        return this.a.b;
    }

    public final void f(int n2) {
        this.b = 3;
    }

    public final int k() {
        return this.b;
    }

    public final int l() {
        return this.a.r;
    }

    public final int m() {
        return this.a.s;
    }

    public final void g(int n2) {
        this.a.s = n2;
    }

    public final int n() {
        return this.a.u;
    }

    public final int o() {
        return this.a.t;
    }

    public final void h(int n2) {
        this.a.u = n2;
    }

    public final lt i(int n2) {
        lt lt2 = null;
        int n3 = 0;
        while (n3 < this.a.E.length) {
            if (this.a.E[n3].a == n2) {
                lt2 = this.a.E[n3];
                break;
            }
            ++n3;
        }
        return lt2;
    }

    public final boolean p() {
        return this.a.w >= this.a.v;
    }

    public final int q() {
        return this.a.w;
    }

    public final void j(int n2) {
        this.a.w = n2;
    }

    public final int r() {
        return this.a.v;
    }

    public final String toString() {
        return this.a.toString();
    }

    public final void s() {
        if (this.d > 0) {
            --this.d;
        }
        if (this.e > 0) {
            --this.e;
        }
        if (this.f > 0) {
            --this.f;
        }
        if (this.g > 0) {
            --this.g;
        }
        if (this.h > 0) {
            --this.h;
        }
    }
}

