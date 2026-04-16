/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class az {
    private int e;
    private bf f;
    protected int a;
    protected int b;
    private int g;
    private int h;
    protected int c;
    protected int d;

    public az(int n2) {
        int n3 = n2;
        az az2 = this;
        this.e = n3;
    }

    public final int a() {
        return this.e;
    }

    public final void a(int n2) {
        this.e = n2;
    }

    public final void a(bf bf2) {
        this.f = bf2;
    }

    public final boolean b(int n2) {
        if (this.f != null) {
            this.f.d(n2, this.e);
            return true;
        }
        return false;
    }

    public final void a(int n2, int n3, int n4) {
        this.a = n2;
        this.g = n2;
        switch (n4) {
            case 24: {
                this.a = n2 - this.c;
                this.g = n2 - v.t / 3;
                break;
            }
            case 17: {
                this.a = n2 - this.c / 2;
                this.g = n2 - v.t / 6;
            }
        }
        this.b = n3;
        this.h = n3;
    }

    public boolean a(int n2, int n3) {
        return n3 >= this.h && n2 >= this.g && n2 <= this.g + v.t / 3;
    }

    public boolean b() {
        this.d = 3;
        return true;
    }

    public abstract void a(Graphics var1);

    public void c() {
        if (this.d > 0) {
            --this.d;
        }
    }
}

