/*
 * Decompiled with CFR 0.152.
 */
public abstract class ab {
    protected int a;
    protected int b;
    protected boolean c;
    protected String d;
    protected int e;
    protected int f = 8;
    protected d g = ca.c;

    public final void a(int n2) {
        this.f = n2;
    }

    public final boolean a() {
        return this.c;
    }

    public final void a(d d2) {
        this.g = d2;
    }

    public void a(String string, int n2, int n3) {
        this.e = 0;
        this.d = string;
        this.a = n2;
        this.b = n3;
        this.c = true;
    }

    public void b() {
        if (this.e < this.f) {
            ++this.e;
            return;
        }
        this.c = false;
    }
}

