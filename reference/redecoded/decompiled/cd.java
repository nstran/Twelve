/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.MGMIDlet;

public abstract class cd
extends an
implements bf {
    private int[] a;
    private int b;
    private String c;
    private boolean d;
    private az k;
    private boolean l;
    private cc[] m;

    public final void d() {
        az az2;
        this.c = cs.h();
        this.a = cs.f();
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
        byte[] byArray = cs.e();
        this.d = byArray == null;
        ((cd)object).m = new cc[(null).length];
        int n3 = 0;
        while (n3 < ((cd)object).m.length) {
            if (((cd)object).a[n3] != -1 && ((cd)object).a[n3] != 0) {
                int[] cfr_ignored_0 = ((cd)object).a;
            }
            ((cd)object).m[n3] = new cc(null, (int)null[n3], (int)null[n3]);
            ((cd)object).m[n3].k(0);
            ++n3;
        }
        if (!((cd)object).d) {
            cd cd2 = object;
            cd cd3 = cd2;
            az2 = cd.a("Tr\u1edf v\u1ec1", 5);
            cd3 = cd2;
            cd2.b(az2, true);
        }
        cd cd4 = object;
        Object object2 = cd4;
        az2 = cd.a("C\u1ea7n bi\u1ebft", 6);
        object2 = cd4;
        cd4.a(az2, true);
        ((cd)object).k = cd.a("M\u1edf", 1);
        ((am)object).a((az)null);
        if (((cd)object).a[((cd)object).b] < 0) {
            ((am)object).a(((cd)object).k);
        }
        this.l = cs.a(this.a);
        if (this.l) {
            byte[] byArray2 = cs.g();
            object = byArray2;
            if (byArray2 == null) {
                cs.i();
                object2 = MGMIDlet.d();
                object2.notifyDestroyed();
                return;
            }
            if (this.c != null) {
                bx.a("Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 s\u01b0u t\u1eadp \u0111\u1ee7 b\u1ed9 V\u1eadt ph\u1ea9m. H\u00e3y b\u1ea5m n\u00fat g\u1eedi \u0111\u1ec3 nh\u1eadn ph\u1ea7n th\u01b0\u1edfng: " + this.c, this.f - 10);
            }
            az2 = null;
            object2 = this;
            object2.b(az2, true);
            this.a((az)null);
            az2 = null;
            object2 = this;
            object2.a(az2, true);
            this.a(cd.a("G\u1eedi", 2));
        }
    }

    private static az a(String string, int n2) {
        return new bd(string, n2);
    }
}

