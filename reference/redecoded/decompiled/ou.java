/*
 * Decompiled with CFR 0.152.
 */
import com.mg.sq.a;

public final class ou {
    private static ou a;
    private ks b = ks.a();
    private a c = com.mg.sq.a.s();

    public static ou a() {
        if (a == null) {
            a = new ou();
        }
        return a;
    }

    public final void a(int n2, Throwable throwable, String string) {
        this.c.j(n2);
        this.b.b(n2, String.valueOf(throwable.getMessage()) + "#" + throwable.toString() + "#" + string);
    }
}

