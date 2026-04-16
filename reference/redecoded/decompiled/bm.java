/*
 * Decompiled with CFR 0.152.
 */
public final class bm
implements bo {
    private a a = new a();
    private bp b;

    public final void a(Object[] objectArray, int n2) {
        int n3 = 0;
        int n4 = n2;
        int n5 = n2 + objectArray.length;
        while (n4 < n5) {
            this.a.b(objectArray[n3++], n4);
            ++n4;
        }
        if (this.b != null) {
            this.b.d(n2, objectArray.length);
        }
    }

    public final void a(Object object, int n2) {
        this.a.b(object, n2);
        if (this.b != null) {
            this.b.m(n2);
        }
    }

    public final void a(bp bp2) {
        this.b = bp2;
    }

    public final Object a(int n2) {
        return this.a.b(n2);
    }

    public final int a() {
        return this.a.d();
    }

    public final void a(Object object) {
        this.b(this.a.c(object));
    }

    public final void b(int n2) {
        this.a.a(n2);
        if (this.b != null) {
            this.b.n(n2);
        }
    }

    public final void b(Object object, int n2) {
        this.a.a(object, n2);
        if (this.b != null) {
            this.b.l(n2);
        }
    }

    public final void b() {
        this.a.a();
        if (this.b != null) {
            this.b.n(-1);
        }
    }
}

