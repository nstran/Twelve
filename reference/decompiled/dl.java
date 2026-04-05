/*
 * Decompiled with CFR 0.152.
 */
public class dl {
    private du c;
    private a d;
    private int e;
    private boolean f;
    public boolean a;
    public ds b;

    public final boolean a() {
        return this.f;
    }

    public final void a(boolean bl2) {
        this.f = bl2;
    }

    public dl(du object, int n2, boolean bl2, ds ds2) {
        this.a = false;
        this.b = null;
        du du2 = object;
        object = this;
        this.c = du2;
        boolean bl3 = bl2;
        object = this;
        this.f = bl3;
        this.e = n2;
        this.d = new a(30);
        this.b = ds2;
        if (ds2 != null) {
            this.a(ds2.a(), 0, 2);
        }
    }

    public final void a(du du2) {
        this.c = du2;
    }

    public final boolean a(String string, int n2) {
        return this.a(string, n2, 0);
    }

    public final boolean b(String string, int n2) {
        return this.a(string, n2, 1);
    }

    private boolean a(String string, int n2, int n3) {
        boolean bl2 = false;
        if (this.d.d() >= 30) {
            this.d.a(0);
            bl2 = true;
        }
        i i2 = null;
        if (this.d.d() > 0) {
            i2 = this.a(this.d.d() - 1);
        }
        if (i2 != null && i2.a == n3) {
            i2.a(string, n2, this.e);
            return bl2;
        }
        this.d.a(new i(string, n2, this.e, n3));
        return bl2;
    }

    public final du b() {
        return this.c;
    }

    public final a c() {
        return this.d;
    }

    public final int d() {
        return this.d.d();
    }

    public final i a(int n2) {
        return (i)this.d.b(n2);
    }

    public dl() {
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

