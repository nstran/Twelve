/*
 * Decompiled with CFR 0.152.
 */
public final class ns {
    public final String a;
    public final String b;
    public final String c;
    public final long d;
    public boolean e;
    public nt[] f;
    public String[] g;

    public ns(String string, String string2, String string3, long l2) {
        this.a = string;
        this.b = string2;
        this.c = string3;
        this.d = l2;
        this.g = new String[0];
        this.f = new nt[0];
    }

    public final String toString() {
        String string = "QUEST: id=" + this.a + "-" + this.b + "-" + this.c;
        if (this.f != null && this.f.length > 0) {
            int n2 = 0;
            while (n2 < this.f.length) {
                if (this.f[n2] != null) {
                    string = String.valueOf(string) + "\n  " + this.f[n2].toString();
                }
                ++n2;
            }
        }
        return string;
    }
}

