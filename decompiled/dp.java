/*
 * Decompiled with CFR 0.152.
 */
public final class dp {
    public String a;
    public int b;
    public lq c;
    public int d = -1;

    public dp(String string, String string2, int n2) {
        this(string, 1, n2);
        this.a = string2;
    }

    public dp(String string, int n2, int n3) {
        this.b = n2;
        if (n2 == 0) {
            ca.d.c(true);
            this.c = new lq(string, 0, 0, n3, ca.c.a() + 4, 1);
            ca.d.c();
            return;
        }
        this.c = new lq(string, 0, 0, n3, ca.d.a() + 4, 1);
        if (n2 == 2) {
            String string2 = this.c.g();
            if (!l.b(string2) && this.c.i().trim().equals(string2)) {
                string = "B\u1ea5m v\u00e0o \u0111\u1ec3 xem... " + string;
                this.c = new lq(string, 0, 0, n3, ca.d.a() + 4, 1);
            }
            this.d = this.c.j();
        }
    }
}

