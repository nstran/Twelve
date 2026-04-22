/*
 * Decompiled with CFR 0.152.
 */
public final class lv
extends ld {
    public int e;
    public int f;
    public int g;
    public String[] h;

    public lv(int n2) {
        super(n2);
    }

    public final String toString() {
        Object object = this;
        object = "Skill " + ((ld)object).a + ": " + this.b + "\n";
        object = String.valueOf(object) + this.d + "\n";
        object = String.valueOf(object) + "Level: " + this.f + "  Mana: " + this.e + "\n";
        object = String.valueOf(object) + "Max Level: " + this.g + "\n";
        if (this.h != null) {
            int n2 = 0;
            while (n2 < this.h.length) {
                object = String.valueOf(object) + this.h[n2] + "\n";
                ++n2;
            }
        }
        return object;
    }
}

