/*
 * Decompiled with CFR 0.152.
 */
final class gp
implements b {
    private gp() {
    }

    public final int a(Object object, Object object2) {
        object = (ll)object;
        object2 = (ll)object2;
        int n2 = ll.a[((ll)object2).m] - ll.a[((ll)object).m];
        if (n2 == 0) {
            n2 = ((ll)object).h - ((ll)object2).h;
            if (n2 == 0) {
                n2 = ((ll)object).e - ((ll)object2).e;
                if (n2 == 0) {
                    n2 = ((ll)object2).j - ((ll)object).j;
                    if (n2 == 0) {
                        n2 = ((ll)object2).i - ((ll)object).i;
                        if (n2 == 0) {
                            return ((ll)object).c.compareTo(((ll)object2).c);
                        }
                        return n2;
                    }
                    return n2;
                }
                return n2;
            }
            return n2;
        }
        return n2;
    }

    gp(byte by2) {
        this();
    }
}

