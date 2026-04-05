/*
 * Decompiled with CFR 0.152.
 */
public class br {
    a a = new a();
    bs b;

    public void a(Object[] objectArray, int n2) {
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

    public void a(Object object, int n2) {
        this.a.b(object, n2);
        if (this.b != null) {
            this.b.m(n2);
        }
    }

    public void a(Object object) {
        this.a(this.a.c(object));
    }

    public void a(int n2) {
        this.a.a(n2);
        if (this.b != null) {
            this.b.n(n2);
        }
    }

    public void a() {
        this.a.a();
        if (this.b != null) {
            this.b.n(-1);
        }
    }

    public void b(Object object, int n2) {
        this.a.a(object, n2);
        if (this.b != null) {
            this.b.l(n2);
        }
    }

    public Object b(int n2) {
        return this.a.b(n2);
    }

    public int b() {
        return this.a.d();
    }

    public void a(bs bs2) {
        this.b = bs2;
    }
}

