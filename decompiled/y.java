/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 *  javax.microedition.rms.RecordStoreFullException
 *  javax.microedition.rms.RecordStoreNotOpenException
 */
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class y {
    private final String a;
    private final int b;
    private int c;
    private int d;
    private int e;
    private a f;
    private a g;
    private a h;
    private int i;
    private int j;
    private int k;
    private Object l;
    private boolean m;
    private RecordStore n;
    private boolean o;
    private int p;

    private y(String string, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2) {
        this.m = false;
        this.n = null;
        this.o = true;
        this.p = 0;
        this.c = n3;
        this.a = string;
        this.b = n2;
        this.d = n4;
        this.e = n5;
        this.j = n6 - (n3 << 1) - n2 * 10;
        this.o = bl2;
        this.p = n7;
        this.f = new a();
        this.g = new a();
        this.h = new a();
        this.l = new Object();
        this.f();
        this.e();
    }

    private byte[] a(RecordStore object) {
        byte[] byArray = new byte[this.c];
        try {
            j.a(object, byArray);
            j.a(object, byArray);
            j.a(object, new byte[]{1});
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        this.i = 1;
        return byArray;
    }

    private void f() {
        if (this.n == null) {
            this.n = j.a(this.a, false);
        }
        try {
            int n2;
            byte[] byArray;
            if (this.n == null || j.a(this.n) < 3) {
                this.d();
                j.b(this.a);
                this.n = j.a(this.a, true);
                byArray = this.a(this.n);
            } else {
                this.i = 0;
                byte[] byArray2 = j.a(this.n, 3);
                if (byArray2 != null) {
                    this.i = byArray2[0];
                }
                if (this.i <= 0 || this.i > 2) {
                    this.i = 1;
                }
                if ((n2 = p.a(byArray = j.a(this.n, this.i), 0)) < this.p) {
                    this.d();
                    j.b(this.a);
                    this.f();
                    return;
                }
            }
            int n3 = j.a(this.n);
            n2 = 3;
            if (this.o) {
                this.d();
            }
            int n4 = 4;
            int n5 = p.a(byArray, 4);
            n4 += 4;
            int n6 = 0;
            while (n6 < n5) {
                x x2 = new x();
                int n7 = n4;
                byte[] byArray3 = byArray;
                x x3 = x2;
                int n8 = n7;
                x3.a = p.a(byArray3, n7);
                x3.e = p.b(byArray3, n7 += 4);
                x3.b = p.a(byArray3, n7 += 8);
                x3.c = new int[p.a(byArray3, n7 += 4)];
                n7 += 4;
                int n9 = 0;
                while (n9 < x3.c.length) {
                    x3.c[n9] = p.a(byArray3, n7);
                    n7 += 4;
                    ++n9;
                }
                n4 += n7 - n8;
                this.f.a(x2);
                n2 += x2.c.length;
                this.k += x2.b;
                ++n6;
            }
            n6 = p.a(byArray, n4);
            n4 += 4;
            int n10 = 0;
            while (n10 < n6) {
                this.g.a(new Integer(p.a(byArray, n4)));
                n4 += 4;
                ++n10;
            }
            if (n3 - 3 > (n2 += this.g.d())) {
                this.e(n3);
                return;
            }
        }
        catch (Throwable throwable) {
            this.d();
            throwable.printStackTrace();
        }
    }

    private void e(int n2) {
        boolean[] blArray = new boolean[n2 + 1];
        int n3 = this.f.d() - 1;
        while (n3 >= 0) {
            x x2 = (x)this.f.b(n3);
            int n4 = x2.c.length - 1;
            while (n4 >= 0) {
                blArray[x2.c[n4]] = true;
                --n4;
            }
            --n3;
        }
        n3 = this.g.d() - 1;
        while (n3 >= 0) {
            int n5 = (Integer)this.g.b(n3);
            blArray[n5] = true;
            --n3;
        }
        n3 = blArray.length - 1;
        while (n3 > 3) {
            if (!blArray[n3]) {
                this.g.a(new Integer(n3));
            }
            --n3;
        }
    }

    private x f(int n2) {
        Object object = this.f.a(n2);
        if (object != null) {
            object = (x)object;
            this.k -= ((x)object).b;
            return object;
        }
        return null;
    }

    private x a(x x2) {
        int n2 = this.h(x2.a);
        if (n2 >= 0) {
            return this.f(n2);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final byte[] a(int n2) {
        Object object = this.l;
        synchronized (object) {
            if (this.n == null) {
                this.n = j.a(this.a, false);
            }
            try {
                byte[] byArray;
                x x2 = this.g(n2);
                if (x2 == null || x2.c == null || this.n == null) {
                    this.d();
                    return null;
                }
                if (x2.d != null) {
                    byArray = x2.d;
                } else {
                    byArray = new byte[x2.b];
                    int n3 = 0;
                    int[] nArray = x2.c;
                    int n4 = 0;
                    while (n4 < nArray.length) {
                        byte[] byArray2 = j.a(this.n, nArray[n4]);
                        if (byArray2 == null) {
                            this.a(x2);
                            this.a(x2.c, 0, x2.c.length);
                            this.d();
                            return null;
                        }
                        int n5 = byArray2.length;
                        if (n5 > byArray.length - n3) {
                            n5 = byArray.length - n3;
                        }
                        System.arraycopy(byArray2, 0, byArray, n3, n5);
                        n3 += n5;
                        ++n4;
                    }
                }
                if (x2.e < System.currentTimeMillis()) {
                    x2.e = System.currentTimeMillis();
                }
                if (this.o) {
                    this.d();
                }
                return byArray;
            }
            catch (Throwable throwable) {
                this.d();
                throwable.printStackTrace();
                return null;
            }
        }
    }

    private boolean g() {
        boolean bl2 = false;
        this.m = true;
        while (this.f.d() >= this.e && this.f.d() > 0) {
            x x2 = (x)this.f.b(0);
            int n2 = 0;
            int n3 = 1;
            while (n3 < this.f.d()) {
                x x3 = (x)this.f.b(n3);
                if (x3.e < x2.e) {
                    x2 = x3;
                    n2 = n3;
                }
                ++n3;
            }
            if (x2 == null || x2.c == null) continue;
            this.a(x2.c, 0, x2.c.length);
            this.f(n2);
            bl2 = true;
        }
        return bl2;
    }

    public final boolean a(int n2, byte[] byArray) {
        return this.a(n2, byArray, 0, byArray.length);
    }

    public final boolean a(int n2, long l2) {
        x x2 = this.g(n2);
        if (x2 != null) {
            x2.e = Long.MAX_VALUE;
            return true;
        }
        return false;
    }

    public final boolean a(int n2, byte[] byArray, int n3, int n4) {
        Object object = this.l;
        synchronized (object) {
            int n5;
            x x2;
            block15: {
                if (byArray == null) {
                    return true;
                }
                if (this.d == 1) {
                    this.g();
                }
                if ((x2 = this.g(n2)) != null) {
                    if (x2.c != null) {
                        this.a(x2.c, 0, x2.c.length);
                    }
                    this.k -= x2.b;
                    x2.b = 0;
                } else {
                    x2 = new x();
                    new x().a = n2;
                    this.f.b(x2, Math.abs(this.h(n2) + 1));
                }
                n5 = this.j - this.k;
                if (n4 <= n5) break block15;
                this.a(x2);
                return false;
            }
            try {
                x2.a = n2;
                x2.e = System.currentTimeMillis();
                x2.b = n4;
                this.k += n4;
                n5 = n4 / this.b;
                if (n4 % this.b != 0) {
                    ++n5;
                }
                int[] nArray = new int[n5];
                int n6 = 0;
                while (n6 < nArray.length) {
                    int n7 = this.g.d() - 1;
                    if (n7 >= 0) {
                        nArray[n6] = (Integer)this.g.b(n7);
                        this.g.a(n7);
                    } else {
                        nArray[n6] = -1;
                    }
                    ++n6;
                }
                x2.c = nArray;
                x2.d = new byte[n4];
                System.arraycopy(byArray, n3, x2.d, 0, n4);
                this.m = true;
                return true;
            }
            catch (Throwable throwable) {
                this.b(n2);
                throwable.printStackTrace();
                return false;
            }
        }
    }

    public final int b(int n2, byte[] byArray, int n3, int n4) {
        this.a(n2, byArray, 0, n4);
        return this.a();
    }

    public final int b(int n2, byte[] byArray) {
        return this.b(n2, byArray, 0, byArray.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int a() {
        if (!this.m) {
            return 0;
        }
        this.m = false;
        Object object = this.l;
        synchronized (object) {
            if (this.n == null) {
                this.n = j.a(this.a, false);
            }
            try {
                int n2;
                if (this.n == null) {
                    this.n = j.a(this.a, true);
                    if (this.n == null) {
                        return -3;
                    }
                    this.a(this.n);
                }
                int n3 = 0;
                int n4 = this.f.d();
                while (n3 < n4) {
                    x x2 = (x)this.f.b(n3);
                    if (x2.d != null) {
                        n2 = this.a(this.n, x2.d, x2.c);
                        x2.d = null;
                        if (n2 < 0) {
                            this.d();
                            return n2;
                        }
                    }
                    ++n3;
                }
                byte[] byArray = new byte[this.c];
                n4 = 0;
                System.arraycopy(p.a(this.p), 0, byArray, 0, 4);
                n4 += 4;
                System.arraycopy(p.a(this.f.d()), 0, byArray, 4, 4);
                n4 += 4;
                int n5 = 0;
                n2 = this.f.d();
                while (n5 < n2) {
                    Object object2 = (x)this.f.b(n5);
                    int n6 = 20 + (((x)object2).c.length << 2);
                    byte[] byArray2 = new byte[n6];
                    int n7 = 0;
                    System.arraycopy(p.a(((x)object2).a), 0, byArray2, 0, 4);
                    n7 += 4;
                    System.arraycopy(p.a(((x)object2).e), 0, byArray2, 4, 8);
                    n7 += 8;
                    System.arraycopy(p.a(((x)object2).b), 0, byArray2, 12, 4);
                    n7 += 4;
                    System.arraycopy(p.a(((x)object2).c.length), 0, byArray2, 16, 4);
                    n7 += 4;
                    int n8 = 0;
                    while (n8 < ((x)object2).c.length) {
                        System.arraycopy(p.a(((x)object2).c[n8]), 0, byArray2, n7, 4);
                        n7 += 4;
                        ++n8;
                    }
                    object2 = byArray2;
                    System.arraycopy(byArray2, 0, byArray, n4, ((Object)object2).length);
                    n4 += ((Object)object2).length;
                    ++n5;
                }
                n5 = this.h.d() - 1;
                while (n5 >= 0) {
                    this.g.a(this.h.b(n5));
                    --n5;
                }
                this.h.a();
                System.arraycopy(p.a(this.g.d()), 0, byArray, n4, 4);
                n4 += 4;
                n5 = this.g.d() - 1;
                while (n5 >= 0) {
                    n2 = (Integer)this.g.b(n5);
                    System.arraycopy(p.a(n2), 0, byArray, n4, 4);
                    n4 += 4;
                    --n5;
                }
                n5 = this.i == 1 ? 2 : 1;
                j.a(this.n, n5, byArray);
                j.a(this.n, 3, new byte[]{(byte)n5});
                this.i = n5;
                if (this.o) {
                    this.d();
                }
                return 0;
            }
            catch (Throwable throwable) {
                this.d();
                throwable.printStackTrace();
                return 0;
            }
        }
    }

    public final void b(int n2) {
        block7: {
            try {
                n2 = this.h(n2);
                if (n2 < 0) break block7;
                x x2 = this.f(n2);
                if (x2.c != null) {
                    int n3 = x2.c.length;
                    boolean bl2 = false;
                    int[] nArray = x2.c;
                    y y2 = this;
                    if (nArray != null) {
                        try {
                            n3 += 0;
                            int n4 = 0;
                            while (n4 < n3) {
                                y2.g.a(new Integer(nArray[n4]));
                                ++n4;
                            }
                        }
                        catch (Throwable throwable) {
                            Throwable throwable2 = throwable;
                            throwable.printStackTrace();
                        }
                    }
                }
                this.m = true;
                return;
            }
            catch (Throwable throwable) {
                Throwable throwable3 = throwable;
                throwable.printStackTrace();
            }
        }
    }

    private x g(int n2) {
        if ((n2 = this.h(n2)) >= 0) {
            return (x)this.f.b(n2);
        }
        return null;
    }

    private void a(int[] nArray, int n2, int n3) {
        if (nArray == null) {
            return;
        }
        n3 += 0;
        n2 = 0;
        while (n2 < n3) {
            this.h.a(new Integer(nArray[n2]));
            ++n2;
        }
    }

    private int a(RecordStore recordStore, byte[] byArray, int[] nArray) {
        byte[] byArray2 = new byte[this.b];
        int n2 = 0;
        int n3 = 0;
        while (n3 < nArray.length) {
            int n4 = byArray.length - n2;
            if (n4 > byArray2.length) {
                n4 = byArray2.length;
            }
            System.arraycopy(byArray, n2, byArray2, 0, n4);
            n2 += n4;
            if (nArray[n3] > 3) {
                try {
                    j.a(recordStore, nArray[n3], byArray2);
                }
                catch (RecordStoreNotOpenException recordStoreNotOpenException) {
                    return -1;
                }
                catch (RecordStoreFullException recordStoreFullException) {
                    return -2;
                }
                catch (RecordStoreException recordStoreException) {
                    return -3;
                }
            }
            try {
                nArray[n3] = j.a(recordStore, byArray2);
            }
            catch (RecordStoreNotOpenException recordStoreNotOpenException) {
                return -1;
            }
            catch (RecordStoreFullException recordStoreFullException) {
                return -2;
            }
            catch (RecordStoreException recordStoreException) {
                return -3;
            }
            ++n3;
        }
        return 0;
    }

    public final boolean c(int n2) {
        return this.h(n2) >= 0;
    }

    private int h(int n2) {
        int n3 = 0;
        int n4 = this.f.d() - 1;
        while (n3 <= n4) {
            int n5 = n3 + n4 >>> 1;
            x x2 = (x)this.f.b(n5);
            if (x2.a < n2) {
                n3 = n5 + 1;
                continue;
            }
            if (x2.a > n2) {
                n4 = n5 - 1;
                continue;
            }
            return n5;
        }
        return -(n3 + 1);
    }

    public final String b() {
        return this.a;
    }

    public final int c() {
        return this.f.d();
    }

    public final int d(int n2) {
        x x2 = this.g(n2);
        if (x2 != null) {
            return x2.b;
        }
        return 0;
    }

    public final void d() {
        if (this.n != null) {
            Object object = this.n;
            try {
                object.closeRecordStore();
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
            this.n = null;
        }
    }

    public final void e() {
        Object object = this.l;
        synchronized (object) {
            if (this.d == 1) {
                if (this.g()) {
                    this.a();
                }
            } else if (this.d == 2) {
                y y2 = this;
                boolean bl2 = false;
                y2.m = true;
                long l2 = System.currentTimeMillis();
                int n2 = 0;
                while (n2 < y2.f.d()) {
                    x x2 = (x)y2.f.b(n2);
                    if (x2.c != null && (l2 - x2.e) / 3600L >= (long)y2.e) {
                        y2.a(x2.c, 0, x2.c.length);
                        y2.f(n2);
                        --n2;
                        bl2 = true;
                    }
                    ++n2;
                }
                if (bl2) {
                    this.a();
                }
            }
            return;
        }
    }

    public static y a(String string, int n2, int n3) {
        return y.a(string, n2, n3, 0, 0);
    }

    public static y a(String string, int n2, int n3, int n4, int n5) {
        return y.a(string, n2, n3, n4, n5, Integer.MAX_VALUE, true);
    }

    public static y a(String string, int n2, int n3, int n4, int n5, int n6, boolean bl2) {
        boolean bl3 = false;
        String string2 = string;
        return new y(string2, n2, n3, n4, n5, n6, 0, bl2);
    }

    public static void a(y y2) {
        if (y2 != null) {
            y2.d();
            j.b(y2.a);
        }
    }

    private y() {
    }

    public static byte[] a(byte[] byArray, String string) {
        try {
            byte by2 = byArray[0];
            byte[] byArray2 = string.getBytes("UTF-8");
            byte[] byArray3 = new byte[Math.max(byArray.length - 1, byArray2.length + by2) + 1];
            byte[] byArray4 = byArray3;
            byArray3[0] = (byte)byArray2.length;
            int n2 = 0;
            while (n2 < byArray4.length - 1) {
                int n3 = 17;
                int n4 = 0;
                if (n2 + 1 < byArray.length) {
                    n3 = byArray[n2 + 1];
                }
                if (n2 >= by2 && n2 - by2 < byArray2.length) {
                    n4 = byArray2[n2 - by2];
                }
                byArray4[n2 + 1] = (byte)(n3 ^ n4);
                ++n2;
            }
            return byArray4;
        }
        catch (Throwable throwable) {
            try {
                byte by3 = byArray[0];
                byte[] byArray5 = string.getBytes();
                byte[] byArray6 = new byte[Math.max(byArray.length - 1, byArray5.length + by3) + 1];
                byte[] byArray7 = byArray6;
                byArray6[0] = (byte)byArray5.length;
                int n5 = 0;
                while (n5 < byArray7.length - 1) {
                    int n6 = 17;
                    int n7 = 0;
                    if (n5 + 1 < byArray.length) {
                        n6 = byArray[n5 + 1];
                    }
                    if (n5 >= by3 && n5 - by3 < byArray5.length) {
                        n7 = byArray5[n5 - by3];
                    }
                    byArray7[n5 + 1] = (byte)(n6 ^ n7);
                    ++n5;
                }
                return byArray7;
            }
            catch (Throwable throwable2) {
                return null;
            }
        }
    }
}

