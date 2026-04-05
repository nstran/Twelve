/*
 * Decompiled with CFR 0.152.
 */
public final class nj {
    public final String a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final int f;

    public nj(String string, int n2, int n3, int n4, int n5, int n6) {
        this.a = string;
        this.e = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
        this.f = n6;
    }

    public final String toString() {
        return "PlayerAttribute " + this.a + "   dam  " + this.e + "  life" + this.f + "   hp = " + this.b + "  mana = " + this.c + "  power = " + this.d;
    }
}

