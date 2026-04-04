/*
 * Decompiled with CFR 0.152.
 */
public final class lk
extends lb {
    public byte e = 0;
    public int f = 0;
    public int g;
    public long h;
    public int i;
    public int j;
    public long k;
    public int l = -1;
    public byte m = 1;

    public lk(int n2) {
        super(n2);
    }

    public final boolean a() {
        return this.m == 1;
    }

    public final String toString() {
        return "Item[id=" + this.a + "; qty=" + this.g + "; $=" + this.h + "        type = " + this.e + " res id = " + this.j + "     ]" + " name = " + this.b + "  displaynam = " + this.c + "  requireKen = " + this.k + "  des = " + this.d + "  slotCapacity = " + this.l + "   trade   " + this.m;
    }

    public final lk b() {
        lk lk2 = new lk(this.a);
        new lk(this.a).e = this.e;
        lk2.d = this.d;
        lk2.g = this.g;
        lk2.f = this.f;
        lk2.h = this.h;
        lk2.i = this.i;
        lk2.b = this.b;
        lk2.c = this.c;
        lk2.j = this.j;
        lk2.k = this.k;
        lk2.l = this.l;
        lk2.m = this.m;
        return lk2;
    }
}

