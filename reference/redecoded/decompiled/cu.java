/*
 * Decompiled with CFR 0.152.
 */
public final class cu {
    public int a;
    public int b;

    public cu(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public cu() {
        this(0, 0);
    }

    public final boolean equals(Object object) {
        if (object != null && object instanceof cu) {
            object = (cu)object;
            return this.a == ((cu)object).a && this.b == ((cu)object).b;
        }
        return false;
    }

    public final int hashCode() {
        int n2 = 629 + this.a;
        n2 = n2 * 17 + this.b;
        return n2;
    }
}

