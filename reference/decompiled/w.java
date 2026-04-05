/*
 * Decompiled with CFR 0.152.
 */
public final class w
implements Runnable {
    private static w a;
    private static Object b;
    private boolean c = true;
    private v[] d;

    private w() {
        b = new Object();
        this.c = false;
    }

    public final void a(int n2) {
        this.d = new v[6];
        this.c = false;
        new Thread(this).start();
    }

    public final void a(v object) {
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

    public final void run() {
        while (!this.c) {
            boolean bl2 = false;
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

    public static w a() {
        if (a == null) {
            a = new w();
        }
        return a;
    }

    public final void b() {
        this.c = true;
        Object object = b;
        synchronized (object) {
            b.notify();
            return;
        }
    }
}

