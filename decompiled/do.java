/*
 * Decompiled with CFR 0.152.
 */
public final class do {
    public lq a;
    public String b;
    public String c;
    public lm d;
    public long e;
    public int f;
    public int g;
    public int h = -1;
    public int i = 0;
    public int j = 0;
    public int k = 0;
    public boolean l = false;
    public int[] m;
    public boolean n = false;
    public boolean o = false;
    public lq p = null;

    public do(eg object, int n2) {
        Object[] objectArray;
        this.e = ((eg)object).e();
        this.f = ((eg)object).h();
        this.c = ((eg)object).b();
        this.g = ((eg)object).f() - 1;
        this.i = ((eg)object).j();
        this.j = ((eg)object).k();
        this.k = ((eg)object).i();
        boolean bl2 = this.l = ((eg)object).m() == 1;
        if (!ov.g) {
            this.g = -1;
        }
        int n3 = 0;
        if (this.g >= 0 && this.g < 20) {
            n3 = ov.c[this.g] + 2;
        } else {
            this.g = -1;
        }
        if (((eg)object).g() != null) {
            this.d = new lm("#\u001b" + ((eg)object).g() + "#", 1, -1, 0, 0, 16);
        }
        this.a = new lq(((eg)object).c(), n3, 0, n2 - n3, ca.d.a(), 2);
        if (oz.a(this.f)) {
            n3 -= 20;
        }
        u u2 = new u("@" + gr.e, 4, -1, 3 - n3, -21, ca.c.a(this.c), ca.c);
        this.a.d().b(u2, 0);
        if (this.c.equals(gr.e)) {
            this.m = new int[0];
        } else {
            objectArray = s.a(this.a.d(), 4);
            this.m = new int[objectArray.length - 1];
            System.arraycopy(objectArray, 1, this.m, 0, this.m.length);
        }
        if (!l.b(((eg)object).l())) {
            String[] stringArray = l.a(((eg)object).l(), ";", -1, true);
            objectArray = stringArray;
            if (stringArray != null && objectArray.length > 0) {
                object = String.valueOf(this.j) + " ng\u01b0\u1eddi th\u00edch: ";
                boolean bl3 = false;
                if (objectArray[0].equals(gr.e)) {
                    object = String.valueOf(object) + "B\u1ea1n ";
                    bl3 = true;
                } else {
                    object = String.valueOf(object) + "@" + (String)objectArray[0];
                }
                int n4 = 1;
                int n5 = objectArray.length;
                while (n4 < n5) {
                    object = n4 == 1 && bl3 ? String.valueOf(object) + "v\u00e0 @" + (String)objectArray[n4] : String.valueOf(object) + ", @" + (String)objectArray[n4];
                    ++n4;
                }
                if (objectArray.length < this.j) {
                    object = String.valueOf(object) + ",...";
                }
                this.p = new lq((String)object, 0, 0, n2 - 4, ca.d.a(), 2);
            }
        }
        u2.a("");
        this.h = 0;
        this.a.a(true);
    }

    public final lq a() {
        return this.a(this.h);
    }

    public final lq a(int n2) {
        if (n2 >= 1000) {
            return this.p;
        }
        return this.a;
    }

    public static int b(int n2) {
        if (n2 >= 1000) {
            return n2 - 1000;
        }
        return n2;
    }

    public final void a(long l2) {
        long l3 = l2;
        long l4 = Math.abs(l3);
        this.b = (l4 /= 1000L) == 0L ? "v\u1eeba t\u1ee9c th\u00ec" : (l4 < 60L ? String.valueOf(l4) + " gi\u00e2y tr\u01b0\u1edbc" : ((l4 /= 60L) < 60L ? String.valueOf(l4) + " ph\u00fat tr\u01b0\u1edbc" : ((l4 /= 60L) < 24L ? String.valueOf(l4) + " gi\u1edd tr\u01b0\u1edbc" : ((l4 /= 24L) < 30L ? String.valueOf(l4) + " ng\u00e0y tr\u01b0\u1edbc" : (l4 < 365L ? String.valueOf(l4 / 30L) + " th\u00e1ng tr\u01b0\u1edbc" : String.valueOf(l4 /= 365L) + " n\u0103m tr\u01b0\u1edbc")))));
    }
}

