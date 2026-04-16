/*
 * Decompiled with CFR 0.152.
 */
public abstract class jp
implements jz {
    protected int a;
    protected int b;
    protected int c;
    protected int d;

    protected jp() {
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public static final jz a(int n2) {
        switch (n2) {
            case 1: {
                return new jq();
            }
            case 2: {
                return new js();
            }
            case 4: {
                return new jr();
            }
        }
        return null;
    }
}

