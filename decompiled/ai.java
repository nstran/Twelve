/*
 * Decompiled with CFR 0.152.
 */
public final class ai {
    private char a;

    public final char a() {
        return this.a;
    }

    public final void a(char c2) {
        this.a = c2;
    }

    public static final char a(int n2) {
        if (n2 < 132 || n2 > 226) {
            return '\u001a';
        }
        return " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~".charAt(n2 - 132);
    }
}

