/*
 * Decompiled with CFR 0.152.
 */
public final class di {
    private ds c;
    private a d;
    private int e;
    private boolean f;
    public boolean a = false;
    public dq b = null;

    public final boolean a() {
        return this.f;
    }

    public final void a(boolean bl2) {
        this.f = bl2;
    }

    public di(ds object, int n2, boolean bl2, dq dq2) {
        ds ds2 = object;
        object = this;
        this.c = ds2;
        boolean bl3 = bl2;
        object = this;
        this.f = bl3;
        this.e = n2;
        this.d = new a(30);
        this.b = dq2;
        if (dq2 != null) {
            this.a(dq2.a, 0, 2);
        }
    }

    public final void a(ds ds2) {
        this.c = ds2;
    }

    public final boolean a(String string, int n2) {
        return this.a(string, n2, 0);
    }

    public final boolean b(String string, int n2) {
        return this.a(string, n2, 1);
    }

    private boolean a(String string, int n2, int n3) {
        boolean bl2 = false;
        if (this.d.d() >= 30) {
            this.d.a(0);
            bl2 = true;
        }
        dm dm2 = null;
        if (this.d.d() > 0) {
            dm2 = this.a(this.d.d() - 1);
        }
        if (dm2 != null && dm2.a == n3) {
            dm2.a(string, n2, this.e);
            return bl2;
        }
        this.d.a(new dm(string, n2, this.e, n3));
        return bl2;
    }

    public final ds b() {
        return this.c;
    }

    public final a c() {
        return this.d;
    }

    public final int d() {
        return this.d.d();
    }

    public final dm a(int n2) {
        return (dm)this.d.b(n2);
    }
}

