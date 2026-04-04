/*
 * Decompiled with CFR 0.152.
 */
public final class ek
implements b {
    private int a = 1;

    public ek() {
        this(true);
    }

    public ek(boolean bl2) {
    }

    public final int a(Object object, Object object2) {
        object = (eg)object;
        object2 = (eg)object2;
        long l2 = ((eg)object).d() - ((eg)object2).d();
        int n2 = 0;
        if (l2 < 0L) {
            n2 = -1;
        } else if (l2 > 0L) {
            n2 = 1;
        }
        return this.a * n2;
    }
}

