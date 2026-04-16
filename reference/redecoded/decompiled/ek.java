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
        object = (ef)object;
        object2 = (ef)object2;
        long l2 = ((ef)object).d() - ((ef)object2).d();
        int n2 = 0;
        if (l2 < 0L) {
            n2 = -1;
        } else if (l2 > 0L) {
            n2 = 1;
        }
        return this.a * n2;
    }
}

