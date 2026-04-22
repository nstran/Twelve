/*
 * Decompiled with CFR 0.152.
 */
public final class la {
    private final int[] a;
    private final dq[] b;
    private int c;

    public la(int n2, dq[] dqArray) {
        this.a = new int[n2];
        this.b = dqArray;
        if (dqArray != null) {
            n2 = 0;
            int n3 = 0;
            while (n3 < dqArray.length) {
                int n4 = 0;
                while (n4 < dqArray[n3].b) {
                    this.a[n2++] = n3;
                    ++n4;
                }
                ++n3;
            }
        }
        if (this.a.length > 0) {
            this.c = cv.a(this.a.length);
        }
    }

    public final dq a() {
        if (this.b == null) {
            return null;
        }
        dq dq2 = this.b[this.a[this.c]];
        ++this.c;
        if (this.c >= this.a.length) {
            this.c = 0;
        }
        return dq2;
    }
}

