/*
 * Decompiled with CFR 0.152.
 */
public final class cq
implements Runnable {
    private static cq a;
    private Object b;
    private cp c = null;
    private a d = new a();

    public static cq a() {
        if (a == null) {
            a = new cq();
        }
        return a;
    }

    protected cq() {
        this.b = new Object();
        new Thread(this).start();
    }

    public final void b() {
        int n2 = this.d.d() - 1;
        while (n2 >= 0) {
            cp cp2 = (cp)this.d.b(n2);
            cp2.a();
            --n2;
        }
        this.d.a();
        this.c = null;
    }

    public final void c() {
        this.d.a();
        this.c = null;
    }

    public final void d() {
        int n2 = this.d.d() - 1;
        while (n2 >= 0) {
            cp cp2 = (cp)this.d.b(n2);
            if (cp2 != null && cp2.d()) {
                cp2.c();
            }
            --n2;
        }
        this.c = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void e() {
        Object object = this.b;
        synchronized (object) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        while (true) {
            Object object = this.b;
            synchronized (object) {
                try {
                    this.b.wait();
                }
                catch (InterruptedException interruptedException) {
                    InterruptedException interruptedException2 = interruptedException;
                    interruptedException.printStackTrace();
                }
            }
        }
    }
}

