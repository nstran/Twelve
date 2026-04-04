/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class bd {
    private int e;
    private bj f;
    protected int a;
    protected int b;
    private int g;
    private int h;
    protected int c;
    protected int d;

    public bd(int n2) {
        int n3 = n2;
        bd bd2 = this;
        this.e = n3;
    }

    public final int a() {
        return this.e;
    }

    public final void a(int n2) {
        this.e = n2;
    }

    public final void a(bj bj2) {
        this.f = bj2;
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
                this.g = n2 - z.r / 3;
                break;
            }
            case 17: {
                this.a = n2 - this.c / 2;
                this.g = n2 - z.r / 6;
            }
        }
        this.b = n3;
        this.h = n3;
    }

    public boolean a(int n2, int n3) {
        return n3 >= this.h && n2 >= this.g && n2 <= this.g + z.r / 3;
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

