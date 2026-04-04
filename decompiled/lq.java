/*
 * Decompiled with CFR 0.152.
 */
public final class lq
extends r {
    public lq() {
    }

    public lq(d d2, String string, int n2, int n3, int n4, int n5, int n6) {
        super(d2, string, n2, n3, n4, n5, n6);
    }

    public lq(String string, int n2, int n3, int n4, int n5, int n6) {
        super(ca.d, string, n2, 0, n4, n5, n6);
    }

    protected final void a(String string, int n2, int n3, long l2, int n4, int n5) {
        n2 = (int)(l2 >>> 32);
        int n6 = (int)l2;
        if (n2 + 16 > n5) {
            n6 += n4;
            n2 = 0;
        }
        this.a.a(new lm(string, 1, n3, n2, n6, 16));
    }

    protected final q a(String object, int n2, int n3, int n4, int n5, int n6) {
        object = super.a((String)object, n2, n3, n4, n5, n6);
        switch (((q)object).c()) {
            case 3: 
            case 4: 
            case 5: {
                ((u)object).a(com.mg.sq.a.g);
            }
        }
        return object;
    }

    public final int f(int n2) {
        int n3 = n2;
        a a2 = this.a;
        int n4 = 0;
        while (n4 < a2.d()) {
            q q2 = (q)a2.b(n4);
            if (q2.c() == n3) {
                return n4;
            }
            ++n4;
        }
        return -1;
    }
}

