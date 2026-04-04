/*
 * Decompiled with CFR 0.152.
 */
public final class ct
implements Runnable {
    private static ct a;
    private Object b;
    private cs c = null;
    private a d = new a();

    public static ct a() {
        if (a == null) {
            a = new ct();
        }
        return a;
    }

    protected ct() {
        this.b = new Object();
        new Thread(this).start();
    }

    public final void b() {
        int n2 = this.d.d() - 1;
        while (n2 >= 0) {
            cs cs2 = (cs)this.d.b(n2);
            cs2.a();
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
            cs cs2 = (cs)this.d.b(n2);
            if (cs2 != null && cs2.d()) {
                cs2.c();
            }
            --n2;
        }
        this.c = null;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void e() {
        Object object = this.b;
        // MONITORENTER : object
        // MONITOREXIT : object
    }

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

