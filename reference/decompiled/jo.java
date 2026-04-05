/*
 * Decompiled with CFR 0.152.
 */
public abstract class jo
implements jy {
    protected int a;
    protected int b;
    protected int c;
    protected int d;

    protected jo() {
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public static final jy a(int n2) {
        switch (n2) {
            case 1: {
                return new jp();
            }
            case 2: {
                return new jr();
            }
            case 4: {
                return new jq();
            }
        }
        return null;
    }
}

