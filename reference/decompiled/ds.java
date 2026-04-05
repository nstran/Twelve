/*
 * Decompiled with CFR 0.152.
 */
public final class ds {
    private String a;
    private String b;
    private short c;
    private short d;
    private ec[] e;

    public final void a(String string) {
        this.a = string;
    }

    public final String a() {
        return this.b;
    }

    public final void b(String string) {
        this.b = string;
    }

    public final void a(short s2) {
        this.c = s2;
    }

    public final short b() {
        return this.d;
    }

    public final void b(short s2) {
        this.d = s2;
    }

    public final ec[] c() {
        return this.e;
    }

    public final void a(ec[] ecArray) {
        this.e = ecArray;
    }

    public final String toString() {
        String string = "[Type] " + (this.c == 3 ? "Inline" : "Popup") + " [Spot] " + String.valueOf(this.d) + (this.a != null ? " [Title] " + this.a : " [Description] " + this.b);
        if (this.e != null) {
            int n2 = 0;
            while (n2 < this.e.length) {
                if (this.e[n2] != null) {
                    string = String.valueOf(string) + " [Inline " + n2 + "] " + this.e[n2].toString();
                }
                ++n2;
            }
        }
        return string;
    }
}

