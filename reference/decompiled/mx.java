/*
 * Decompiled with CFR 0.152.
 */
public final class mx
extends mo {
    public mx(mq mq2, mr mr2, op op2) {
        super(mq2, mr2, op2);
        cw.a(" [ViewMatchControler] =======================View========================");
    }

    public final void b(int n2, int n3) {
    }

    public final void a(int n2, int n3) {
    }

    public final void b(int n2) {
    }

    public final void c(int n2) {
    }

    public final void a(String object, String string) {
        object = this.b;
        ((mr)object).b(string);
    }

    public final void a() {
        this.b.j();
    }

    public final void c() {
        ny.m = false;
        this.p();
    }

    protected final void d() {
    }

    protected final void e() {
        this.d(7);
    }

    protected final void a(int n2, int n3, int n4, int n5, int n6, int[] nArray, int[] nArray2, lj[] ljArray, lk[] lkArray) {
        this.g = n2;
        this.d(9);
    }

    protected final void c(no no2) {
        cw.a("[ViewMathchController]=====processSwap===== rv =" + no2.b);
        this.a(no2, true);
        if (no2.g != null) {
            int n2 = 0;
            while (n2 < no2.g.length) {
                this.b(no2.g[n2]);
                ++n2;
            }
        }
        this.a(no2.h);
        this.a(1, no2.l, no2.m, no2.n, no2.o);
        this.t = no2;
        this.b.e();
        this.m = no2.b;
    }
}

