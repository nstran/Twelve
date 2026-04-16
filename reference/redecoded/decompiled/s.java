/*
 * Decompiled with CFR 0.152.
 */
public final class s
implements Runnable {
    private static s a;
    private static Object b;
    private boolean c = true;
    private r[] d;

    private s() {
        b = new Object();
        this.c = false;
    }

    public final void a(int n2) {
        this.d = new r[6];
        this.c = false;
        new Thread(this).start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(r object) {
        if (this.d == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.d.length) {
            if (this.d[n2] == null) {
                this.d[n2] = object;
                try {
                    object = b;
                    synchronized (object) {
                        b.notify();
                        return;
                    }
                }
                catch (Exception exception) {
                    try {
                        object = b;
                        synchronized (object) {
                            b.notify();
                            return;
                        }
                    }
                    catch (Exception exception2) {
                        return;
                    }
                }
            }
            ++n2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        while (!this.c) {
            boolean bl2;
            do {
                bl2 = false;
                int n2 = 0;
                while (n2 < this.d.length) {
                    if (this.d[n2] != null) {
                        this.d[n2].a();
                        this.d[n2] = null;
                        bl2 = true;
                    }
                    ++n2;
                }
            } while (bl2);
            try {
                Object object = b;
                synchronized (object) {
                    b.wait();
                }
            }
            catch (Exception exception) {}
        }
    }

    public static s a() {
        if (a == null) {
            a = new s();
        }
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b() {
        this.c = true;
        Object object = b;
        synchronized (object) {
            b.notify();
            return;
        }
    }
}

