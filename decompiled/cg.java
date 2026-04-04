/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.MGMIDlet;

public abstract class cg
extends ar
implements bj {
    private int[] a;
    private int b;
    private String c;
    private boolean d;
    private bd k;
    private boolean l;
    private cf[] m;

    public final void d() {
        bd bd2;
        this.c = cv.h();
        this.a = cv.f();
        this.b = 0;
        int n2 = this.a.length - 1;
        while (n2 >= 0) {
            if (this.a[n2] < 0) {
                this.b = n2;
                break;
            }
            --n2;
        }
        Object object = this;
        byte[] byArray = cv.e();
        this.d = byArray == null;
        ((cg)object).m = new cf[(null).length];
        int n3 = 0;
        while (n3 < ((cg)object).m.length) {
            ((cg)object).m[n3] = new cf(null, (int)null[n3], (int)null[n3]);
            ((cg)object).m[n3].k(0);
            ++n3;
        }
        if (!((cg)object).d) {
            cg cg2 = object;
            cg cg3 = cg2;
            bd2 = cg.a("Tr\u1edf v\u1ec1", 5);
            cg3 = cg2;
            cg2.b(bd2, true);
        }
        cg cg4 = object;
        cg cg5 = cg4;
        bd2 = cg.a("C\u1ea7n bi\u1ebft", 6);
        cg5 = cg4;
        cg4.a(bd2, true);
        ((cg)object).k = cg.a("M\u1edf", 1);
        ((aq)object).a((bd)null);
        if (((cg)object).a[((cg)object).b] < 0) {
            ((aq)object).a(((cg)object).k);
        }
        this.l = cv.a(this.a);
        if (this.l) {
            byte[] byArray2 = cv.g();
            object = byArray2;
            if (byArray2 == null) {
                cv.i();
                MGMIDlet.f().d();
                return;
            }
            if (this.c != null) {
                ca.a("Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 s\u01b0u t\u1eadp \u0111\u1ee7 b\u1ed9 V\u1eadt ph\u1ea9m. H\u00e3y b\u1ea5m n\u00fat g\u1eedi \u0111\u1ec3 nh\u1eadn ph\u1ea7n th\u01b0\u1edfng: " + this.c, this.f - 10);
            }
            bd2 = null;
            cg5 = this;
            cg5.b(bd2, true);
            this.a((bd)null);
            bd2 = null;
            cg5 = this;
            cg5.a(bd2, true);
            this.a(cg.a("G\u1eedi", 2));
        }
    }

    private static bd a(String string, int n2) {
        return new bh(string, n2);
    }
}

