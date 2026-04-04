/*
 * Decompiled with CFR 0.152.
 */
import com.mg.sq.a;

public final class lj {
    public int a;
    public String b;
    public String c;
    public byte d;
    public byte e;
    public String f;
    public byte g;
    public int h;
    public int i;
    public long j = -1L;
    public long k;
    private String s;
    public int l = 0;
    public int m;
    public int n;
    public int o;
    public kz p;
    public byte q;
    public byte r = 1;

    public lj(String string, byte by2) {
        this.b = string;
        this.d = by2;
    }

    public final boolean a() {
        return this.r == 1;
    }

    public final boolean b() {
        return this.o > 0 && this.n < this.o;
    }

    public final boolean c() {
        return this.j > 0L;
    }

    public final lj d() {
        lj lj2 = new lj(this.b, this.d);
        new lj(this.b, this.d).c = this.c;
        lj2.e = this.e;
        lj2.g = this.g;
        lj2.h = this.h;
        lj2.j = this.j;
        lj2.i = this.i;
        lj2.l = this.l;
        lj2.m = this.m;
        lj2.n = this.n;
        lj2.o = this.o;
        lj2.p = this.p;
        lj2.a = this.a;
        lj2.s = this.s;
        lj2.q = this.q;
        lj2.r = this.r;
        return lj2;
    }

    public final String toString() {
        return "Equip[key=" + this.b + " resid = " + this.m + " ; name=" + this.c + "; type=" + this.d + " rank = " + this.l + "    \n" + this.p + " levle = " + this.i + " tradeable" + this.r;
    }

    public static d a(int n2) {
        d d2 = null;
        try {
            switch (n2) {
                case 2: 
                case 3: {
                    d2 = new cb(11731964);
                    break;
                }
                case 4: 
                case 7: {
                    d2 = new ie(new int[]{9008914, 0xFFFF00});
                    break;
                }
                case 1: {
                    d2 = com.mg.sq.a.g;
                    break;
                }
                case 0: {
                    d2 = ca.d;
                    break;
                }
                default: {
                    d2 = ca.d;
                    break;
                }
            }
        }
        catch (Exception exception) {
            d2 = ca.d;
        }
        return d2;
    }
}

