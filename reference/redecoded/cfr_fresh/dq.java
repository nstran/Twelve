/*
 * Decompiled with CFR 0.152.
 */
public final class dq {
    private String d;
    public final String a;
    private short e;
    public final short b;
    public final eb[] c;

    public dq(String string, String string2, short s2, short s3, eb[] ebArray) {
        this.d = string;
        this.a = string2;
        this.e = s2;
        this.b = s3;
        this.c = ebArray;
    }

    public final String toString() {
        String string = "[Type] " + (this.e == 3 ? "Inline" : "Popup") + " [Spot] " + String.valueOf(this.b) + (this.d != null ? " [Title] " + this.d : " [Description] " + this.a);
        if (this.c != null) {
            int n2 = 0;
            while (n2 < this.c.length) {
                if (this.c[n2] != null) {
                    string = String.valueOf(string) + " [Inline " + n2 + "] " + this.c[n2].toString();
                }
                ++n2;
            }
        }
        return string;
    }
}

