/*
 * Decompiled with CFR 0.152.
 */
public final class df {
    public int a;
    public String b;
    public int c;
    public dg d;
    public dg e;
    public dg[] f;

    public df(int n2) {
        this.a = n2;
    }

    public final String toString() {
        return this.b;
    }

    public final df a() {
        df df2 = new df(this.a);
        new df(this.a).b = this.b;
        df2.c = this.c;
        df2.d = this.d.a();
        df2.e = this.e.a();
        df2.f = new dg[this.f.length];
        int n2 = 0;
        while (n2 < this.f.length) {
            df2.f[n2] = this.f[n2].a();
            ++n2;
        }
        return df2;
    }
}

