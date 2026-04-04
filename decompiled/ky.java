/*
 * Decompiled with CFR 0.152.
 */
public final class ky {
    private int[] a;
    private ds[] b;
    private int c;

    public ky(int n2, ds[] dsArray) {
        this.a = new int[n2];
        this.b = dsArray;
        if (dsArray != null) {
            n2 = 0;
            int n3 = 0;
            while (n3 < dsArray.length) {
                int n4 = 0;
                while (n4 < dsArray[n3].b()) {
                    this.a[n2++] = n3;
                    ++n4;
                }
                ++n3;
            }
        }
        if (this.a.length > 0) {
            this.c = cy.a(this.a.length);
        }
    }

    public final ds a() {
        if (this.b == null) {
            return null;
        }
        ds ds2 = this.b[this.a[this.c]];
        ++this.c;
        if (this.c >= this.a.length) {
            this.c = 0;
        }
        return ds2;
    }
}

