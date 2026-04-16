/*
 * Decompiled with CFR 0.152.
 */
public final class dt {
    private String a;
    private short b;
    private ds[] c;

    public dt(String string) {
        this.a = string;
    }

    public final short a() {
        return this.b;
    }

    public final void a(short s2) {
        this.b = s2;
    }

    public final String b() {
        return this.a;
    }

    public final ds[] c() {
        return this.c;
    }

    public final void a(ds[] dsArray) {
        this.c = dsArray;
        this.d();
    }

    public final void d() {
        if (this.c == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.c.length) {
            if (this.c[n2].e() != 2) {
                boolean bl2 = false;
                int n3 = this.c.length - 1;
                while (n3 > n2) {
                    if (this.c[n3].e() == 2) {
                        bl2 = true;
                        ds ds2 = this.c[n2];
                        this.c[n2] = this.c[n3];
                        this.c[n3] = ds2;
                    }
                    --n3;
                }
                if (!bl2) {
                    return;
                }
            }
            ++n2;
        }
    }
}

