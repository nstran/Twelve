/*
 * Decompiled with CFR 0.152.
 */
public abstract class ni
extends ax {
    protected ni g;
    protected int h;
    protected boolean i;
    protected int j;
    protected int k;
    protected int s;
    protected int t = 0;
    protected int u = -1;
    protected int v = 0;
    protected boolean w = false;
    protected boolean x = false;
    protected boolean y = false;
    protected ip z;
    protected li A;
    protected lh B;
    protected ad C;
    protected n D;
    protected int E;

    public ni(int n2) {
        this.h = n2;
        this.z = new ip(mn.a().N, mn.a().O);
        this.A = new li();
        this.B = new lh();
    }

    public abstract void a(boolean var1, boolean var2);

    public abstract void a(int var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6);

    public abstract void d();

    public abstract void e();

    public abstract void a(boolean var1);

    public abstract void a(int var1);

    public abstract int c();

    public final boolean f() {
        return this.E == 0 || this.E == 6;
    }

    public final void g() {
        int n2 = this.n() + this.p() / 2;
        int n3 = this.o() + this.q() + 11;
        n2 = this.h == 0 ? (n2 -= 5) : (n2 += 5);
        this.z.a(n2, n3, n2, n3, 0);
    }

    public final void h() {
        int n2 = this.n() + this.p() / 2;
        int n3 = this.o() + this.q() + 11;
        n2 = this.h == 0 ? (n2 -= 5) : (n2 += 5);
        this.A.d(n2, n3);
    }

    public final void j() {
        int n2 = this.n() + this.p() / 2;
        int n3 = this.o() + this.q() + 11;
        n2 = this.h == 0 ? (n2 -= 5) : (n2 += 5);
        this.B.d(n2, n3);
    }

    public final void a(ni ni2) {
        this.g = ni2;
    }
}

