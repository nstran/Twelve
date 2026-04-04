/*
 * Decompiled with CFR 0.152.
 */
public final class di {
    public int a;
    public String b;
    public int c;
    public dj d;
    public dj e;
    public dj[] f;

    public di(int n2) {
        this.a = n2;
    }

    public final String toString() {
        return this.b;
    }

    public final di a() {
        di di2 = new di(this.a);
        new di(this.a).b = this.b;
        di2.c = this.c;
        di2.d = this.d.a();
        di2.e = this.e.a();
        di2.f = new dj[this.f.length];
        int n2 = 0;
        while (n2 < this.f.length) {
            di2.f[n2] = this.f[n2].a();
            ++n2;
        }
        return di2;
    }
}

