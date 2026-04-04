/*
 * Decompiled with CFR 0.152.
 */
public final class n {
    public int a;
    public int b;
    public int c;
    public int d;

    public n() {
    }

    public n(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public final boolean a(int n2, int n3) {
        return n2 > this.a && n2 < this.a + this.c && n3 > this.b && n3 < this.b + this.d;
    }

    public final boolean a(n n2) {
        return this.b(n2.a, n2.b, n2.c, n2.d);
    }

    public final boolean b(int n2, int n3, int n4, int n5) {
        int n6 = this.c;
        int n7 = this.d;
        if (n4 <= 0 || n5 <= 0 || n6 <= 0 || n7 <= 0) {
            return false;
        }
        int n8 = this.a;
        int n9 = this.b;
        return !((n4 += n2) >= n2 && n4 <= n8 || (n5 += n3) >= n3 && n5 <= n9 || (n6 += n8) >= n8 && n6 <= n2 || (n7 += n9) >= n9 && n7 <= n3);
    }

    public final boolean b(int n2, int n3) {
        int n4 = this.c;
        int n5 = this.d;
        if ((n4 | n5) < 0) {
            return false;
        }
        int n6 = this.a;
        int n7 = this.b;
        if (n2 < n6 || n3 < n7) {
            return false;
        }
        return !((n4 += n6) >= n6 && n4 <= n2 || (n5 += n7) >= n7 && n5 <= n3);
    }

    public final String toString() {
        String string = "[X]: " + this.a + " [Y]: " + this.b + " [W]: " + this.c + " [H]: " + this.d;
        return string;
    }
}

