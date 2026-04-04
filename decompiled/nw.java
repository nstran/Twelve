/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class nw
extends ar
implements ij {
    private final int a;
    private boolean b;
    private int c;
    private String d;

    public final void a(String string) {
        this.d = string;
    }

    public nw(int n2) {
        super(7);
        this.a((be)null);
        this.a = n2;
        this.c = 5;
        this.b = false;
    }

    protected final void r() {
        System.gc();
    }

    protected final void c() {
        if (this.b) {
            return;
        }
        --this.c;
        if (this.c == 1) {
            System.gc();
            this.b = true;
            switch (this.a) {
                case 0: {
                    ak.b().f(0);
                    return;
                }
                case 1: {
                    ox.a().a(ny.c, this);
                    return;
                }
                case 5: {
                    ak.b().a(5, new Object[]{this.d});
                }
            }
        }
    }

    protected final void a(Graphics graphics) {
    }

    public final void a(jm jm2, byte[][] byArray) {
        ny.l = jm2;
        ny.k = byArray;
        ak.b().f(1);
    }

    public final void d() {
    }
}

