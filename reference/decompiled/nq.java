/*
 * Decompiled with CFR 0.152.
 */
public final class nq {
    public String a;
    public String b;
    public String c;
    public nr[] d;
    public String[] e;

    public nq(String string, String string2, String string3) {
        this.a = string;
        this.b = string2;
        this.c = string3;
        this.e = new String[0];
        this.d = new nr[0];
    }

    public final String toString() {
        String string = "QUEST: id=" + this.a + "-" + this.b + "-" + this.c;
        if (this.d != null && this.d.length > 0) {
            int n2 = 0;
            while (n2 < this.d.length) {
                if (this.d[n2] != null) {
                    string = String.valueOf(string) + "\n  " + this.d[n2].toString();
                }
                ++n2;
            }
        }
        return string;
    }
}

