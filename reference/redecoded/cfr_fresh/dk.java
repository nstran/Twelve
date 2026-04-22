/*
 * Decompiled with CFR 0.152.
 */
public final class dk {
    public boolean a = false;
    private lf d;
    public lq[] b;
    public int c;

    public dk(lf lf2) {
        this.d = lf2;
    }

    public final void a(lq[] lqArray) {
        if (lqArray == null || lqArray.length <= 0) {
            return;
        }
        if (this.b == null) {
            this.b = lqArray;
            return;
        }
        int n2 = lqArray.length + this.b.length;
        lq[] lqArray2 = new lq[n2];
        System.arraycopy(this.b, 0, lqArray2, 0, this.b.length);
        System.arraycopy(lqArray, 0, lqArray2, this.b.length, lqArray.length);
        this.b = lqArray2;
    }

    public final lq[] a(String string) {
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
        lq[] lqArray = new lq[a2.d()];
        int n3 = 0;
        while (n3 < a2.d()) {
            lq lq2;
            lqArray[n3] = lq2 = (lq)a2.b(n3);
            ++n3;
        }
        this.b = lqArray;
        return lqArray;
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

