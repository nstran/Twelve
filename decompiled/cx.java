/*
 * Decompiled with CFR 0.152.
 */
public final class cx {
    public int a;
    public int b;

    public cx(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public cx() {
        this(0, 0);
    }

    public final boolean equals(Object object) {
        if (object != null && object instanceof cx) {
            object = (cx)object;
            return this.a == ((cx)object).a && this.b == ((cx)object).b;
        }
        return false;
    }

    public final int hashCode() {
        int n2 = 629 + this.a;
        n2 = n2 * 17 + this.b;
        return n2;
    }
}

