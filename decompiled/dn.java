/*
 * Decompiled with CFR 0.152.
 */
public final class dn {
    public boolean a = false;
    private ld d;
    public lo[] b;
    public int c;

    public dn(ld ld2) {
        this.d = ld2;
    }

    public final void a(lo[] loArray) {
        if (loArray == null || loArray.length <= 0) {
            return;
        }
        if (this.b == null) {
            this.b = loArray;
            return;
        }
        int n2 = loArray.length + this.b.length;
        lo[] loArray2 = new lo[n2];
        System.arraycopy(this.b, 0, loArray2, 0, this.b.length);
        System.arraycopy(loArray, 0, loArray2, this.b.length, loArray.length);
        this.b = loArray2;
    }

    public final lo[] a(String string) {
        a a2 = new a();
        int n2 = 0;
        while (n2 < this.b.length) {
            if (!this.b[n2].b.equals(string)) {
                a2.a(this.b[n2]);
            } else {
                --this.d.c;
            }
            ++n2;
        }
        lo[] loArray = new lo[a2.d()];
        int n3 = 0;
        while (n3 < a2.d()) {
            lo lo2;
            loArray[n3] = lo2 = (lo)a2.b(n3);
            ++n3;
        }
        this.b = loArray;
        return loArray;
    }

    public final String a() {
        if (this.d.c < 0) {
            return "";
        }
        if (this.b == null || this.b.length == 0) {
            return "" + this.d.c;
        }
        return String.valueOf(this.c) + "/" + this.d.c;
    }

    public final int b() {
        return this.d.a;
    }

    public final String c() {
        return this.d.b;
    }

    public final void a(int n2) {
        this.d.c = n2;
    }

    public final int d() {
        return this.d.c;
    }

    public final boolean e() {
        return this.b == null || this.b.length == 0;
    }
}

