/*
 * Decompiled with CFR 0.152.
 */
public abstract class ap
extends aq {
    public static int a = 2000;
    private int k = 0;
    protected boolean b = false;
    private boolean l;
    private boolean m;
    private int n;
    protected int c;
    protected int d;

    public ap(int n2) {
        this.k = n2;
        if (this.k == 1) {
            this.n = -1;
            return;
        }
        if (this.k == 0) {
            this.n = a;
        }
    }

    public final int a() {
        return this.c;
    }

    public final int c() {
        return this.d;
    }

    public void a(int n2, int n3, int n4, int n5) {
        this.c = n2;
        this.d = n3;
        this.f = n4;
        this.g = n5;
    }

    public final void a(boolean bl2) {
        this.b = bl2;
    }

    public final boolean d() {
        return this.b;
    }

    public final boolean e() {
        return this.l;
    }

    public final void b(boolean bl2) {
        this.l = true;
    }

    public final boolean f() {
        return this.m;
    }

    public final void c(boolean bl2) {
        this.m = true;
    }

    public void a(int n2) {
        this.n = n2;
    }

    public final void b_() {
        if (this.n > 0) {
            --this.n;
            if (this.n == 1) {
                ak.b().m();
                return;
            }
        }
        this.g();
    }

    protected void g() {
    }
}

