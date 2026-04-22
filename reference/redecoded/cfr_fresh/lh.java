/*
 * Decompiled with CFR 0.152.
 */
public final class lh
extends ld {
    public byte e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public int z;
    public int A;
    public int B;
    public int C;
    public ll[] D;
    public lv[] E;
    public lm[] F;
    public int G;
    public int H;
    public int I;
    public int J;
    public int K;
    public int L;
    public int M;
    public int N;
    public boolean O = false;
    public String P = "";
    public String Q = null;
    public String R = "";
    public String S = "";
    public byte T;
    public df U;
    public df V;
    public df W;
    public long X;
    public int Y;
    public boolean Z = false;
    public boolean aa = false;
    public int ab;
    public lt[] ac;
    public boolean ad = true;

    public final lh a() {
        int n2;
        lh lh2 = new lh(this.a);
        new lh(this.a).b = this.b;
        lh2.d = this.d;
        lh2.c = this.c;
        lh2.f = this.f;
        lh2.g = this.g;
        lh2.s = this.s;
        lh2.r = this.r;
        lh2.u = this.u;
        lh2.t = this.t;
        lh2.w = this.w;
        lh2.v = this.v;
        lh2.z = this.z;
        if (this.D != null) {
            lh2.D = new ll[this.D.length];
            n2 = 0;
            while (n2 < lh2.D.length) {
                lh2.D[n2] = this.D[n2].d();
                ++n2;
            }
        }
        if (this.F != null) {
            lh2.F = new lm[this.F.length];
            n2 = 0;
            while (n2 < lh2.F.length) {
                lh2.F[n2] = this.F[n2].b();
                ++n2;
            }
        }
        if (this.E != null) {
            lh2.E = new lv[this.E.length];
            n2 = 0;
            while (n2 < lh2.E.length) {
                lv lv2 = this.E[n2];
                lv lv3 = new lv(lv2.a);
                new lv(lv2.a).d = lv2.d;
                lv3.c = lv2.c;
                lv3.b = lv2.b;
                lv3.f = lv2.f;
                lv3.e = lv2.e;
                lv3.g = lv2.g;
                lv3.h = lv2.h;
                lh2.E[n2] = lv3;
                ++n2;
            }
        }
        lh2.G = this.G;
        lh2.H = this.H;
        lh2.J = this.J;
        lh2.K = this.K;
        lh2.L = this.L;
        lh2.M = this.M;
        lh2.N = this.N;
        lh2.O = this.O;
        lh2.Z = this.Z;
        lh2.C = this.C;
        lh2.i = this.i;
        lh2.A = this.A;
        lh2.B = this.B;
        lh2.k = this.k;
        lh2.x = this.x;
        lh2.y = this.y;
        lh2.h = this.h;
        lh2.j = this.j;
        lh2.U = this.U.a();
        lh2.W = this.W.a();
        lh2.V = this.V.a();
        return lh2;
    }

    public lh(int n2) {
        super(n2);
    }

    public final String toString() {
        String string = "[CHARATER ] name = " + this.b + "   id = " + this.a + "\n";
        string = String.valueOf(string) + "Streng = " + this.h + "\n";
        string = String.valueOf(string) + "vitalit = " + this.k + "\n";
        string = String.valueOf(string) + "agility = " + this.j + "\n";
        string = String.valueOf(string) + "magic = " + this.i + "\n";
        string = String.valueOf(string) + "addStreng = " + this.l + "\n";
        string = String.valueOf(string) + "addvitalit = " + this.o + "\n";
        string = String.valueOf(string) + "addagility = " + this.m + "\n";
        string = String.valueOf(string) + "addmagic = " + this.l + "\n";
        string = String.valueOf(string) + "   - HP: " + this.s + " / " + this.r + "\n";
        string = String.valueOf(string) + "   - Mana: " + this.u + " / " + this.t + "\n";
        string = String.valueOf(string) + "   - Power: " + this.w + " / " + this.v + "\n";
        string = String.valueOf(string) + "   - MinDam: " + this.x + " / " + this.y + "\n";
        string = String.valueOf(string) + "   - Dodgerate: " + this.A + " hitrate: " + this.B + "\n";
        string = String.valueOf(string) + "   - Defende: " + this.z + " criticaldamge " + this.C + "\n";
        string = String.valueOf(string) + "   - Skills: ";
        string = String.valueOf(string) + "    - h\u1ec7: " + this.g + "\n";
        int n2 = 0;
        while (n2 < this.E.length) {
            string = String.valueOf(string) + this.E[n2].toString();
            ++n2;
        }
        string = String.valueOf(string) + "\n   - Equinemt: ";
        n2 = 0;
        while (n2 < this.D.length) {
            string = String.valueOf(string) + this.D[n2].toString() + "\n";
            ++n2;
        }
        string = String.valueOf(string) + "\n";
        return string;
    }

    public final ll a(int n2) {
        if (this.D != null && this.D.length > 0) {
            int n3 = 0;
            while (n3 < this.D.length) {
                if (this.D[n3].e == n2) {
                    return this.D[n3];
                }
                ++n3;
            }
        }
        return null;
    }

    public final ll b() {
        if (this.D != null && this.D.length > 0) {
            int n2 = 0;
            while (n2 < this.D.length) {
                if (this.D[n2].e == 4) {
                    this.ad = true;
                    return this.D[n2];
                }
                ++n2;
            }
        }
        this.ad = false;
        return null;
    }

    public final int c() {
        int n2 = 0;
        if (this.D != null) {
            int n3 = 0;
            while (n3 < this.D.length) {
                if (this.D[n3].j >= 7 && this.D[n3].e != 8 && this.D[n3].e != 4) {
                    ++n2;
                }
                ++n3;
            }
            n3 = 0;
            if (n2 >= 4) {
                n2 = 0;
                while (n2 < this.D.length) {
                    if (this.D[n2].e != 8 && this.D[n2].e != 4) {
                        if (this.D[n2].j < 9) {
                            return 1;
                        }
                        if (this.D[n2].j < 11) {
                            return 2;
                        }
                        if (this.D[n2].j < 13) {
                            return 3;
                        }
                        if (this.D[n2].j >= 13) {
                            ++n3;
                        }
                        if (n3 >= 4) {
                            return 4;
                        }
                    }
                    ++n2;
                }
            }
        }
        return 0;
    }
}

