/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.DateField
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Item
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;

public final class og
extends fb
implements bj,
bk,
bq,
bt,
dw,
CommandListener,
v {
    private oh t;
    private oo u;
    ol o;
    om p;
    private oi v;
    private a w;
    private a x;
    private boolean[] y;
    private bc z;
    private ba A;
    private int B = 0;
    public static long q;
    private boolean C;
    public String[] r;
    public String[] s;
    private ec[] D;
    private long E;
    private bu F;
    private eu G = null;
    private ec H = null;
    private ky I = null;
    private boolean J = false;
    private String K = "";
    private long L;
    private int M;

    public static void f(boolean bl2) {
        if (z.aa || bl2) {
            t.i();
            oz.c();
            lw.g();
            fn.q();
            ov.b();
            pb.a(null);
        }
        System.gc();
    }

    public static void v() {
        block5: {
            t.h();
            oz.a();
            oz.b();
            lw.f();
            fn.a();
            try {
                ov.a();
                pb pb2 = pb.a();
                if (pb2.a != null) break block5;
                byte[] byArray = pa.q();
                pb2 = pb.a();
                if (byArray != null) {
                    try {
                        pb2.a(ep.a(byArray));
                        break block5;
                    }
                    catch (Throwable throwable) {}
                }
                return;
            }
            catch (Throwable throwable) {
                Throwable throwable2 = throwable;
                throwable.printStackTrace();
                ov.b();
                pb.a(null);
            }
        }
    }

    public og() {
        super(104, 1, "B\u1eb1ng H\u1eefu");
        this.a(0, 0, z.r, z.s);
        this.w = new a();
        this.z = new bc(0);
        this.z.a(this.c(), this.d(), this.e(), this.f() - 20);
        this.A = new ba();
        this.A.a(this);
        this.A.a(this);
        this.A.e(true);
        this.z.b(this.A);
        if (!z.x) {
            this.z.h(1);
        }
        this.a(new be());
        this.t = new oh(105, 2, "Danh S\u00e1ch \u0110\u00e0m \u0110\u1ea1o");
        this.u = new oo(109, 7, "B\u00e1o online");
        this.o = new ol(107, 5, "Ola Me");
        this.p = new om(108, 6, "Xem \u1ea2nh");
        this.v = new oi();
        this.a(new ga(-1, 0));
        this.b(new ga(-2, 1));
        this.c(new gd());
        this.a(this);
        com.mg.sq.a.m = false;
    }

    public final ol w() {
        return this.o;
    }

    public final oh A() {
        return this.t;
    }

    public final void commandAction(Command object, Displayable displayable) {
        if (l.a(object.getLabel(), "Xong")) {
            object = "";
            ed[] edArray = this.H.f();
            if (edArray != null) {
                int n2 = 0;
                while (n2 < edArray.length) {
                    eq eq2 = (eq)og.a(edArray[n2]);
                    object = String.valueOf(object) + " " + eq2.a();
                    ++n2;
                }
            }
            object = String.valueOf(this.H.g()) + (String)object;
            cl.a((String)object, this.H.d(), null);
            MGMIDlet.f().a((Displayable)ak.a(), true);
        } else if (l.a(object.getLabel(), "H\u1ee7y")) {
            MGMIDlet.f().a((Displayable)ak.a(), true);
        }
        ((Form)displayable).deleteAll();
    }

    public final boolean b(String string) {
        return this.b(string, this.D);
    }

    public final boolean b(String object, ec[] ecArray) {
        if (ecArray == null) {
            return false;
        }
        int n2 = 0;
        while (n2 < ecArray.length) {
            ec ec2 = ecArray[n2];
            if (ec2.e().equals(object)) {
                this.H = ec2;
                object = "FREE";
                if (!(l.b("FREE") || ((String)(object = ((String)object).toUpperCase())).equals("FREE") || ((String)object).equals("MI\u1ec4N PH\u00cd"))) {
                    object = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n s\u1ebd t\u1ed1n " + (String)object, "C\u00f3", -1006, "Kh\u00f4ng", 6, 1);
                    ((aq)object).a(this);
                    ak.b().a((ap)object, false);
                    return true;
                }
                this.a(ec2);
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final void a(ec ec2) {
        String string = ec2.a();
        if (string.equals("wap")) {
            this.a(null, ec2.b(), false);
            return;
        }
        if (string.equals("app")) {
            try {
                long l2 = Long.parseLong(ec2.b());
                dv.a().b(l2);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
        if (string.equals("rss")) {
            this.a(null, "rss://" + ec2.b(), false);
            return;
        }
        if (string.equals("call")) {
            MGMIDlet.b(ec2.c());
            return;
        }
        if (string.equals("msg") || string.equals("sms")) {
            Form form = new Form(ec2.e());
            ed[] edArray = ec2.f();
            if (edArray != null) {
                int n2 = 0;
                while (n2 < edArray.length) {
                    form.append(og.a(edArray[n2]));
                    ++n2;
                }
            }
            form.addCommand(new Command("Xong", 4, 1));
            form.addCommand(new Command("H\u1ee7y", 3, 1));
            form.setCommandListener((CommandListener)this);
            MGMIDlet.f().a((Displayable)form, true);
        }
    }

    private void a(j[] object, boolean bl2) {
        int n2;
        int n3 = 0;
        while (n3 < ((j[])object).length) {
            n2 = 0;
            while (n2 < this.w.d()) {
                j j2 = this.k(n2);
                if (j2.b().equals(object[n3].b())) {
                    this.w.a(n2);
                    break;
                }
                ++n2;
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < ((j[])object).length) {
            if (object[n3].c() == null) {
                object[n3].a(new du[0]);
            }
            if (bl2) {
                this.w.b(object[n3], 0);
            } else {
                this.w.a(object[n3]);
            }
            ++n3;
        }
        object = this;
        boolean[] blArray = new boolean[((og)object).w.d()];
        n3 = 0;
        while (n3 < blArray.length) {
            blArray[n3] = true;
            ++n3;
        }
        if (((og)object).y != null) {
            n3 = blArray.length > ((og)object).y.length ? ((og)object).y.length : blArray.length;
            n2 = 0;
            while (n2 < n3) {
                blArray[n2] = ((og)object).y[n2];
                ++n2;
            }
        }
        ((og)object).y = blArray;
        super.M();
        super.N();
    }

    private void M() {
        if (gp.c) {
            this.x = this.w;
            return;
        }
        this.x = new a();
        int n2 = 0;
        while (n2 < this.w.d()) {
            j j2 = this.k(n2);
            du[] duArray = j2.c();
            j j3 = new j(j2.b());
            j3.a(j2.a());
            int n3 = 0;
            int n4 = 0;
            while (n4 < duArray.length) {
                if (duArray[n4].e() == 2) {
                    ++n3;
                }
                ++n4;
            }
            du[] duArray2 = new du[n3];
            n3 = 0;
            int n5 = 0;
            while (n5 < duArray.length) {
                if (duArray[n5].e() == 2) {
                    duArray2[n3++] = duArray[n5];
                }
                ++n5;
            }
            j3.a(duArray2);
            this.x.a(j3);
            ++n2;
        }
    }

    private void N() {
        int n2;
        a a2 = new a(this.x.d());
        int n3 = 0;
        while (n3 < this.x.d()) {
            du[] duArray = (du[])this.x.b(n3);
            a2.a((Object)duArray);
            if (this.y[n3]) {
                duArray = duArray.c();
                n2 = 0;
                while (n2 < duArray.length) {
                    a2.a(duArray[n2]);
                    ++n2;
                }
            }
            ++n3;
        }
        ba ba2 = this.A;
        synchronized (ba2) {
            this.A.q();
            n2 = 0;
            while (n2 < a2.d()) {
                this.A.a(a2.b(n2));
                ++n2;
            }
            return;
        }
    }

    public final j[] B() {
        j[] jArray = new j[this.w.d()];
        int n2 = 0;
        while (n2 < jArray.length) {
            jArray[n2] = this.k(n2);
            ++n2;
        }
        return jArray;
    }

    private j k(int n2) {
        return (j)this.w.b(n2);
    }

    public final void u() {
        this.z.c(true);
    }

    public final void x() {
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2) {
            this.z.c(true);
        }
    }

    public final boolean f(int n2) {
        if (n2 == 97 || n2 == 96) {
            return false;
        }
        if (n2 == 142) {
            Object object = this.A.t();
            String string = null;
            if (object instanceof du) {
                string = ((du)object).f();
            }
            if (!l.b(string)) {
                ap ap2 = ak.b().a(null, string, "\u0110\u00f3ng", 2, 1);
                ap2.a(ak.b());
                ak.b().a(ap2, false);
                return true;
            }
        }
        return this.z.f(n2);
    }

    public final boolean e(int n2, int n3) {
        return this.z.e(n2, n3);
    }

    public final boolean c(int n2, int n3) {
        return this.z.c(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.z.f(n2, n3);
    }

    public final void n() {
        if (this.B > 0) {
            --this.B;
            this.c(true);
        }
        this.z.n();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        boolean bl2 = this.z.k();
        n2 = bl2 ? 1 : 0;
        if (bl2) {
            this.c(true);
            graphics.setColor(z.af);
            graphics.fillRect(this.c(), this.d(), this.e(), this.f() - 20);
            graphics.drawImage(oz.d, this.c() + this.e(), this.d() + this.f() - be.a, 40);
        }
        this.z.a(graphics, this.c(), this.d());
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof du) {
            du du2 = (du)object;
            return new eu(du2, this.A.e());
        }
        int n3 = 0;
        while (n3 < this.x.d()) {
            j j2 = (j)this.x.b(n3);
            --n2;
            if (this.y[n3]) {
                n2 -= j2.c().length;
            }
            if (n2 < 0) {
                n2 = n3;
                break;
            }
            ++n3;
        }
        j j3 = (j)object;
        return new ev(j3, this.y[n2], this.A.e());
    }

    public final void a(au au2) {
        super.a(au2);
        if (au2 != null) {
            if (this.t.a()) {
                this.O();
                this.t.B();
            }
            au2 = this.t.v();
            if (((on)au2).o != null) {
                fc fc2;
                au2 = this;
                if (au2.b != null && !(fc2 = (fc)au2.l()).b(((og)au2).t.v())) {
                    fc2.c(((og)au2).t.v());
                }
            }
        }
    }

    private void O() {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        if (!fc2.b(this.t)) {
            fc2.c(this.t);
        }
    }

    private void a(du du2, boolean bl2, boolean bl3) {
        this.O();
        this.t.a(du2, bl2, bl3);
    }

    private du a(String string, boolean bl2) {
        if (string == null) {
            return null;
        }
        du du2 = this.d(string = string.toLowerCase());
        if (du2 != null) {
            this.a(du2, false, bl2);
        } else {
            du2 = og.e(string);
            this.a(du2, true, bl2);
        }
        return du2;
    }

    public final du c(String string) {
        return this.a(string, true);
    }

    public final void a(String string, long l2) {
        this.h(true);
        this.o.a(string, 0L);
    }

    private du d(String string) {
        int n2 = 0;
        while (n2 < this.w.d()) {
            du[] duArray = this.k(n2);
            if ((duArray = duArray.c()) != null) {
                int n3 = 0;
                while (n3 < duArray.length) {
                    if (duArray[n3].a().equals(string)) {
                        return duArray[n3];
                    }
                    ++n3;
                }
            }
            ++n2;
        }
        return null;
    }

    public final void b(au object, int n2) {
        object = this.A.i(n2);
        if (object instanceof du) {
            boolean bl2;
            boolean bl3;
            String[] stringArray;
            Object object2;
            String[] stringArray2;
            String[] stringArray3 = (String[])this.A.o(n2);
            Object object3 = this;
            this.G = stringArray3;
            bv bv2 = new bv();
            if (stringArray3.i.a().length() < 3) {
                stringArray2 = new String[]{"Chat!"};
                object2 = new int[]{10400};
            } else {
                stringArray2 = new String[]{"Chat!", "Xem ME"};
                object2 = new int[]{10400, 10401};
            }
            int n3 = stringArray3.j.f(3);
            int n4 = stringArray3.j.f(1);
            String[] stringArray4 = null;
            if (n4 >= 0 && (stringArray = lm.a(((lm)stringArray3.j.a((int)n4)).i)) != null) {
                stringArray4 = stringArray;
            }
            if (n3 >= 0 && n4 >= 0 && !l.b(stringArray4)) {
                stringArray = new String[stringArray2.length + 2];
                int[] nArray = new int[((int[])object2).length + 2];
                System.arraycopy(stringArray2, 0, stringArray, 0, stringArray2.length);
                System.arraycopy(object2, 0, nArray, 0, ((int[])object2).length);
                stringArray[stringArray.length - 2] = "Xem tin";
                nArray[nArray.length - 2] = 10420;
                stringArray[stringArray.length - 1] = stringArray4;
                nArray[nArray.length - 1] = 10421;
                stringArray2 = stringArray;
                object2 = nArray;
            } else {
                if (n3 >= 0) {
                    stringArray = new String[stringArray2.length + 1];
                    int[] nArray = new int[((int[])object2).length + 1];
                    System.arraycopy(stringArray2, 0, stringArray, 0, stringArray2.length);
                    System.arraycopy(object2, 0, nArray, 0, ((int[])object2).length);
                    stringArray[stringArray.length - 1] = "Xem tin";
                    nArray[nArray.length - 1] = 10420;
                    stringArray2 = stringArray;
                    object2 = nArray;
                }
                if (n4 >= 0 && !l.b(stringArray4)) {
                    stringArray = new String[stringArray2.length + 1];
                    int[] nArray = new int[((int[])object2).length + 1];
                    System.arraycopy(stringArray2, 0, stringArray, 0, stringArray2.length);
                    System.arraycopy(object2, 0, nArray, 0, ((int[])object2).length);
                    stringArray[stringArray.length - 1] = stringArray4;
                    nArray[nArray.length - 1] = 10421;
                    stringArray2 = stringArray;
                    object2 = nArray;
                }
            }
            if ((bl3 = stringArray3.i.g() != null && stringArray3.i.g().equals("patriot")) && !stringArray3.i.a().equals(gr.e) && ((au)object3).l() instanceof fc && ((fc)((au)object3).l()).i(100)) {
                Object object4 = (oj)((fc)((au)object3).l()).h(100);
                if (object4 != null && ((oj)object4).o != null && ((oj)object4).o.b() == 2) {
                    stringArray3 = new String[stringArray2.length + 1];
                    object4 = new int[((int[])object2).length + 1];
                    System.arraycopy(stringArray2, 1, stringArray3, 2, stringArray2.length - 1);
                    System.arraycopy(object2, 1, object4, 2, ((int[])object2).length - 1);
                    stringArray3[0] = stringArray2[0];
                    object4[0] = object2[0];
                    stringArray3[1] = "Xem th\u00f4ng tin";
                    object4[1] = 10424;
                    stringArray2 = stringArray3;
                    object2 = object4;
                } else {
                    stringArray3 = new String[stringArray2.length + 3];
                    object4 = new int[((int[])object2).length + 3];
                    System.arraycopy(stringArray2, 1, stringArray3, 4, stringArray2.length - 1);
                    System.arraycopy(object2, 1, object4, 4, ((int[])object2).length - 1);
                    stringArray3[0] = stringArray2[0];
                    object4[0] = object2[0];
                    stringArray3[1] = "Khi\u00eau Chi\u1ebfn";
                    object4[1] = 10422;
                    stringArray3[2] = "Giao d\u1ecbch";
                    object4[2] = 10423;
                    stringArray3[3] = "Xem th\u00f4ng tin";
                    object4[3] = 10424;
                    stringArray2 = stringArray3;
                    object2 = object4;
                }
            }
            if (stringArray2.length < 2) {
                bl2 = false;
            } else {
                Object object5 = new bu[stringArray2.length];
                int n5 = 0;
                while (n5 < stringArray2.length) {
                    object5[n5] = new bu(stringArray2[n5], object2[n5]);
                    ++n5;
                }
                bv2.a((bu[])object5);
                n5 = ((og)object3).A.s();
                object5 = ((og)object3).A.o(n5);
                n n6 = ((og)object3).z.r();
                int n7 = (z.r - bv2.e()) / 2;
                int n8 = ((og)object3).z.d() + ((au)object5).d() - n6.b;
                if (n8 + bv2.f() > z.s - be.a) {
                    n8 = z.s - be.a - bv2.f();
                }
                bv2.a_(z.r + bv2.e(), n8);
                bv2.d(n7, n8);
                bv2.a((bk)object3);
                bv2.a(new ga(1, 2));
                bv2.b(new ga(2, 3));
                bv2.c(new gd());
                bv2.a_(1);
                ((fb)object3).a(bv2);
                bl2 = true;
            }
            if (!bl2) {
                boolean bl4 = false;
                object3 = (du)object;
                og og2 = this;
                og2.a((du)object3, false, true);
                return;
            }
        } else {
            int n9 = 0;
            while (n9 < this.x.d()) {
                if (object.equals(this.x.b(n9)) && n9 <= this.y.length) {
                    this.y[n9] = !this.y[n9];
                    this.N();
                    this.A.k(n2);
                    this.P();
                    return;
                }
                ++n9;
            }
        }
    }

    private void P() {
        int n2 = this.z.r().b;
        int n3 = this.A.s();
        if (n3 * 22 - n2 >= this.A.f() - 40 - 22) {
            n2 = n3 * 22 - (this.A.f() - 40 - 22);
        }
        this.z.k(n2);
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final void a(int n2) {
        kq.i += n2;
    }

    public final void b(int n2) {
        kq.i += n2;
    }

    public final void a(j[] jArray) {
        if (jArray != null) {
            j[] jArray2 = jArray;
            int n2 = 0;
            while (n2 < jArray2.length) {
                du[] duArray;
                if (jArray2[n2].a() == 1 && (duArray = jArray2[n2].c()) != null) {
                    int n3 = 0;
                    while (n3 < duArray.length) {
                        duArray[n3].b((short)0);
                        ++n3;
                    }
                }
                ++n2;
            }
            this.a(jArray, true);
        }
    }

    public final void a(j[] object, int n2) {
        if (object == null) {
            return;
        }
        og og2 = this;
        if (og2.w != null) {
            int n3 = og2.w.d() - 1;
            while (n3 >= 0) {
                j j2 = (j)og2.w.b(n3);
                if (j2.a() != 1) {
                    og2.w.a(n3);
                }
                --n3;
            }
        }
        if (gp.h) {
            pa.a(object, n2);
            gr.d = n2;
        }
        j[] jArray = object;
        object = this;
        object.a(jArray, false);
        object = pa.t();
        if (object != null) {
            int n4 = 0;
            while (n4 < ((j[])object).length) {
                this.a((String)((Object)object[n4]), false);
                ++n4;
            }
        }
    }

    public final void C() {
        if (!this.u.o) {
            this.k(false);
        }
        if (!this.o.p) {
            this.h(false);
        }
        if (!this.v.r) {
            this.l(false);
        }
        og og2 = this;
        if (!og2.J && og2.b != null) {
            og2.J = true;
            dv.a().j();
        }
    }

    public static bu[] a(ec[] ecArray) {
        bu[] buArray = new bu[ecArray.length];
        int n2 = 0;
        while (n2 < buArray.length) {
            buArray[n2] = new bu(ecArray[n2].e(), 11399);
            buArray[n2].a(ecArray[n2]);
            ++n2;
        }
        return buArray;
    }

    public final bu[] a(bu[] buArray, int n2) {
        if (buArray == null || this.F == null) {
            return buArray;
        }
        bu[] buArray2 = new bu[buArray.length + 1];
        System.arraycopy(buArray, 0, buArray2, 0, n2);
        buArray2[n2] = this.F;
        System.arraycopy(buArray, n2, buArray2, n2 + 1, buArray.length - n2);
        return buArray2;
    }

    public final boolean j(int n2) {
        if (n2 == 99031) {
            this.i(true);
            return true;
        }
        if (n2 == 99009) {
            this.j(true);
            return true;
        }
        if (n2 == 99017) {
            this.E();
            return true;
        }
        if (n2 == 99012) {
            MGMIDlet.b("1900588883");
            return true;
        }
        if (n2 == 99011) {
            com.mg.sq.a.D();
            return true;
        }
        if (n2 == 99008) {
            this.F();
            return true;
        }
        if (n2 == 99007) {
            com.mg.sq.a.B();
            return true;
        }
        return false;
    }

    public static bu[] b(bu[] buArray, int n2) {
        bu[] buArray2 = new bu[buArray.length + 1];
        bu bu2 = new bu("Gi\u1edbi thi\u1ec7u", 99007);
        if (!com.mg.sq.a.m) {
            System.arraycopy(buArray, 0, buArray2, 0, n2);
            buArray2[n2] = bu2;
            System.arraycopy(buArray, n2, buArray2, n2 + 1, buArray.length - n2);
            return buArray2;
        }
        bu bu3 = new bu("H\u1ed7 tr\u1ee3", 99001);
        bu3.a(new bu[]{bu2, new bu("H\u01b0\u1edbng d\u1eabn", 99008), new bu("Th\u00f4ng Tin #12", 99031), new bu("Th\u1ea3o lu\u1eadn #SQ", 99009), new bu("Rao v\u1eb7t #sqgd", 99017), new bu("H\u1ed7 tr\u1ee3", 99012), new bu("C\u00e0i \u0111\u1eb7t", 99011)});
        System.arraycopy(buArray, 0, buArray2, 0, n2);
        buArray2[n2] = bu3;
        System.arraycopy(buArray, n2, buArray2, n2 + 1, buArray.length - n2);
        return buArray2;
    }

    public final void a(String string, String string2) {
        Object object = this.t.a(string, string2, 0);
        if (object == null) {
            String string3 = string;
            object = this;
            object = ((og)object).a(string3, true);
            ((du)object).d("patriot");
            this.t.a(string, string2, 0);
        }
        dv.a().a(string, string2, null);
    }

    public final void a(eg eg2, short s2) {
        boolean bl2 = false;
        Object object = ak.b().d();
        if (object != null && ((aq)object).h() == 1) {
            String string;
            Object object2;
            object = (ny)object;
            ar ar2 = object;
            if (((ny)object).n != null) {
                object2 = ar2.n;
                string = ((oj)object2).o != null && ((oj)object2).o.b() == 2 ? ((op)((oj)object2).o).s() : "";
            } else {
                string = "";
            }
            if (string.equals(eg2.b())) {
                object2 = eg2.c();
                ar2 = object;
                if (ar2.n != null) {
                    object = object2;
                    oj oj2 = ar2.n;
                    if (oj2.o != null && oj2.o.b() == 2) {
                        ((op)oj2.o).a((String)object);
                    }
                }
                bl2 = true;
            }
        } else if (System.currentTimeMillis() - this.E >= 15000L && eg2.c().equals(gq.a)) {
            this.E = System.currentTimeMillis();
            ak.a().a(50);
            ak.a().b(100);
        }
        this.O();
        object = this.t.b(eg2.b(), eg2.c(), (int)eg2.f());
        if (object == null) {
            boolean bl3;
            object = this.d(eg2.b());
            if (object == null) {
                object = og.e(eg2.b());
                bl3 = true;
            } else {
                bl3 = false;
            }
            object = this.t.a((du)object, eg2.c(), (int)eg2.f(), bl3);
        }
        if (!bl2 && s2 != 2 && object != null) {
            ((dl)object).a = true;
            if (this.b != null) {
                fc fc2 = (fc)this.l();
                if (this.t.v().o != null && this.t.v().o.equals(object) && fc2.q().equals(this.t.v())) {
                    ((dl)object).a = false;
                    return;
                }
            }
            if (((dl)object).a) {
                this.t.B();
            }
        }
    }

    public final void D() {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        fc2.d(this.p);
    }

    private void l(boolean bl2) {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        if (!fc2.b(this.v)) {
            fc2.c(this.v);
        }
        if (bl2) {
            fc2.a(this.v);
        }
    }

    public final void g(boolean bl2) {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        if (!fc2.b(this.p)) {
            fc2.c(this.p);
        }
        fc2.a(this.p);
    }

    public final void h(boolean bl2) {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        if (!fc2.b(this.o)) {
            fc2.c(this.o);
        }
        if (bl2) {
            fc2.a(this.o);
        }
    }

    public final void a(String object, String string, boolean bl2) {
        int n2 = string.toLowerCase().indexOf("rss://");
        if (n2 == 0) {
            this.u.a(string.substring(6));
            return;
        }
        if (bl2) {
            com.mg.sq.a.a("\u0110ang ki\u1ec3m tra \u0111\u01b0\u1eddng d\u1eabn...", null);
            dv.a().c(string, (String)object);
            return;
        }
        try {
            MGMIDlet.f().platformRequest(string);
            return;
        }
        catch (ConnectionNotFoundException connectionNotFoundException) {
            object = connectionNotFoundException;
            connectionNotFoundException.printStackTrace();
            return;
        }
    }

    public final void b(String string, String string2) {
        this.a(string, string2, true);
    }

    public final void E() {
        this.o.a("#sqgd", 0L);
        this.h(true);
    }

    public final void i(boolean bl2) {
        this.o.a("#12", 0L);
        this.h(bl2);
    }

    public final void j(boolean bl2) {
        this.o.a("#sq", 0L);
        this.h(true);
    }

    public final void F() {
        String string = "rss://faq/sq";
        String string2 = null;
        og og2 = this;
        og2.a(string2, string, true);
        this.k(true);
    }

    public final void k(boolean bl2) {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        if (!fc2.b(this.u)) {
            fc2.c(this.u);
        }
        if (bl2) {
            fc2.a(this.u);
        }
    }

    private static du e(String string) {
        if (string == null) {
            return null;
        }
        string = string.toLowerCase();
        du du2 = new du();
        du2.a(string);
        du2.b(string);
        du2.a((short)-13);
        return du2;
    }

    public final void a(du[] duArray) {
        if (this.t.a()) {
            this.O();
        }
        int n2 = 0;
        while (n2 < duArray.length) {
            int n3 = 0;
            while (n3 < this.w.d()) {
                du[] duArray2 = this.k(n3).c();
                if (duArray2 != null) {
                    boolean bl2 = false;
                    int n4 = 0;
                    while (n4 < duArray2.length) {
                        if (duArray2[n4].a().equals(duArray[n2].a())) {
                            if (duArray[n2].e() == 2) {
                                duArray2[n4].c(duArray[n2].f());
                                duArray2[n4].a(duArray[n2].c());
                                duArray2[n4].b(duArray[n2].d());
                            }
                            eg eg2 = null;
                            if (duArray[n2].e() != duArray2[n4].e() && this.t.a(duArray2[n4].a()) >= 0) {
                                eg2 = new eg();
                                eg2.a(System.currentTimeMillis());
                                eg2.a(duArray2[n4].a());
                                String string = duArray2[n4].a();
                                if (!l.b(duArray2[n4].b())) {
                                    string = duArray2[n4].b();
                                }
                                if (duArray[n2].e() == 2) {
                                    eg2.b("[" + string + " \u0111\u00e3 \u0111\u0103ng nh\u1eadp tr\u1edf l\u1ea1i" + "]");
                                } else {
                                    eg2.b("[" + string + " \u0111\u00e3 \u0111\u0103ng xu\u1ea5t" + "]");
                                }
                            }
                            duArray2[n4].d(duArray[n2].g());
                            duArray2[n4].c(duArray[n2].e());
                            this.t.a(duArray2[n4]);
                            if (eg2 != null) {
                                this.a(eg2, (short)2);
                            }
                            bl2 = true;
                            break;
                        }
                        ++n4;
                    }
                    if (bl2) {
                        this.k(n3).d();
                        break;
                    }
                }
                ++n3;
            }
            ++n2;
        }
        n2 = this.A.s();
        this.M();
        this.N();
        this.B = 1;
        if (n2 >= this.A.a()) {
            n2 = this.A.a() - 1;
        }
        this.A.k(n2);
        this.P();
    }

    public final void a(eg[] egArray, short s2) {
        if (egArray != null) {
            Object object;
            this.O();
            if (this.b != null) {
                object = (fc)this.l();
                ((fc)object).e(this.t);
            }
            int n2 = 0;
            while (n2 < egArray.length) {
                object = egArray[n2].c() == null ? "" : egArray[n2].c();
                egArray[n2].b("(" + l.b(egArray[n2].d()) + "): " + (String)object);
                this.a(egArray[n2], s2);
                ++n2;
            }
        }
    }

    public final void G() {
        if (com.mg.sq.a.m && gp.i) {
            pa.b(this.t.w());
        }
        com.mg.sq.a.m = false;
        dv.a().a((dw)null);
        au au2 = this.l();
        if (au2 != null) {
            au2 = (fc)au2;
            ((fc)au2).d(this);
            ((fc)au2).d(this.t);
            ((fc)au2).d(this.u);
            ((fc)au2).d(this.o);
            ((fc)au2).d(this.p);
            ((fc)au2).d(this.v);
            ((fc)au2).d(this.t.v());
        }
        this.t = null;
        this.u = null;
        this.o = null;
        this.p = null;
        this.v = null;
        this.t = null;
        dv.a().c();
        og.f(true);
        com.mg.sq.a.k = null;
        gn.b();
        System.gc();
        cw.b("Logged out OLA");
    }

    public final void a(du[] duArray, String string) {
        j j2;
        Object object;
        og og2;
        j[] jArray;
        block5: {
            jArray = string;
            og2 = this;
            int n2 = 0;
            while (n2 < og2.w.d()) {
                object = og2.k(n2);
                if (((j)object).b().equals(jArray)) {
                    j2 = object;
                    break block5;
                }
                ++n2;
            }
            j2 = null;
        }
        j j3 = j2;
        object = null;
        int n3 = 0;
        while (n3 < duArray.length) {
            object = duArray[n3];
            if (j3 == null) {
                j3 = new j(string);
                og og3 = this;
                jArray = j3;
                og2 = og3;
                jArray = new j[]{jArray};
                og2 = og3;
                og3.a(jArray, false);
                this.a((du)object, string, j3);
            } else {
                this.a((du)object, string, j3);
                this.M();
                this.N();
            }
            ++n3;
        }
    }

    private void a(du du2, String duArray, j j2) {
        du2.b((short)0);
        int n2 = 0;
        while (n2 < this.w.d()) {
            j j3 = this.k(n2);
            if (j3.b().compareTo((String)duArray) == 0) {
                if (j3.c() != null) {
                    duArray = new du[j3.c().length + 1];
                    n2 = 0;
                    while (n2 < j3.c().length) {
                        duArray[n2] = j3.c()[n2];
                        ++n2;
                    }
                    duArray[n2] = du2;
                } else {
                    du[] duArray2 = new du[1];
                    duArray = duArray2;
                    duArray2[0] = du2;
                }
                j2.a(duArray);
                return;
            }
            ++n2;
        }
    }

    public final void H() {
        if (com.mg.sq.a.m) {
            return;
        }
        if (!this.C && System.currentTimeMillis() - q > 120000L) {
            this.C = true;
            this.a(1, null, (short)0);
        }
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        Object object2 = (bu)object;
        this.t();
        switch (n3) {
            case 10400: {
                boolean bl2 = false;
                du du2 = this.G.i;
                object2 = this;
                ((og)object2).a(du2, false, true);
                return;
            }
            case 10401: {
                this.o.a(this.G.i.a(), 0L);
                this.h(true);
                return;
            }
            case 10408: {
                dv.a().b((short)2412);
                com.mg.sq.a.a(null, null);
                return;
            }
            case 10409: {
                object2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n tho\u00e1t h\u1ec7 th\u1ed1ng chat", "C\u00f3", 5, "Kh\u00f4ng", 6, 1);
                ((aq)object2).a(this);
                ak.b().a((ap)object2, false);
                return;
            }
            case 10410: {
                this.h(true);
                return;
            }
            case 10411: {
                this.k(true);
                if (this.b == null) break;
                ((fc)this.b).a(this.u);
                return;
            }
            case 10412: {
                this.g(true);
                return;
            }
            case 10413: {
                this.l(true);
                return;
            }
            case 10414: {
                gp.d = false;
                this.I();
                return;
            }
            case 10415: {
                gp.d = true;
                this.I();
                return;
            }
            case 10416: 
            case 10417: {
                byte[] byArray;
                gp.c = !gp.c;
                this.J();
                object2 = cv.a;
                if (gp.c) {
                    byArray = new byte[1];
                } else {
                    byte[] byArray2 = new byte[1];
                    byArray = byArray2;
                    byArray2[0] = 1;
                }
                ((y)object2).b(133, byArray);
                return;
            }
            case 10418: {
                al al2 = ak.b();
                object2 = this;
                hc hc2 = com.mg.sq.a.a("Nh\u1eadp th\u00f4ng \u0111i\u1ec7p m\u1edbi", null, "\u0110\u1ed5i", 3, "H\u1ee7y", 4);
                object = (ff)hc2.e(1);
                if (object != null) {
                    ((ff)object).e(true);
                }
                hc2.a((bj)object2);
                hc2.b(-9999991);
                al2.a(hc2);
                return;
            }
            case 10419: {
                al al3 = ak.b();
                object2 = this;
                hc hc3 = com.mg.sq.a.a("Nh\u1eadp nick mu\u1ed1n chat", null, "Xong", 7, "H\u1ee7y", 8);
                hc3.a((bj)object2);
                hc3.b(-9999992);
                al3.a(hc3, false);
                return;
            }
            case 0: {
                com.mg.sq.a.A();
                return;
            }
            case 10420: {
                object2 = this.G.j.h();
                if (object2 == null) break;
                object = object2;
                String string = this.G.i.a();
                object2 = this;
                ((og)object2).a(string, (String)object, true);
                return;
            }
            case 10421: {
                if (((bu)object2).b().equals("Xem \u1ea2nh")) {
                    object2 = this.G.j.g();
                    if (object2 == null) break;
                    n3 = 1;
                    this.p.a((String)((object2 = ((String)object2).trim()) == null || ((String)object2).length() <= 2 ? object2 : ((String)object2).substring(2, ((String)object2).length() - 1)));
                    return;
                }
                com.mg.sq.a.A();
                return;
            }
            case 10422: {
                com.mg.sq.a.d(this.G.i.a());
                return;
            }
            case 10423: {
                com.mg.sq.a.p(this.G.i.a());
                return;
            }
            case 10424: {
                com.mg.sq.a.b(this.G.i.a(), this.i);
                return;
            }
            case 11399: {
                this.b(((bu)object2).b());
            }
            default: {
                if (com.mg.sq.a.m(n3)) break;
                this.j(n3);
            }
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1007: {
                pa.f(gr.c);
                ak.b().a(false);
                com.mg.sq.a.a(null, null);
                this.l(true);
                dv.a().n();
                if (this.L < 1L) {
                    return;
                }
                dv.a().b(this.L);
                return;
            }
            case -1006: {
                ak.b().a(false);
                this.a(this.H);
                return;
            }
            case -1002: {
                com.mg.sq.a.q().a(false);
                dv.a().c(null, (short)this.p.i);
                return;
            }
            case -1003: {
                com.mg.sq.a.q().a(false);
                return;
            }
            case -1004: {
                com.mg.sq.a.q().a(false);
                dv.a().k();
                return;
            }
            case -1005: {
                com.mg.sq.a.q().a(false);
                dv.a().l();
                return;
            }
            case -1: {
                og og2 = this;
                bv bv2 = new bv();
                bv2.a(new ga(1, 2));
                bv2.b(new ga(2, 3));
                bu[] buArray = null;
                if (com.mg.sq.a.k != null && com.mg.sq.a.m) {
                    buArray = new bu("N\u1ea1p KEN", 99003);
                    buArray.a(new bu[]{new bu("Nh\u1eafn tin", 99004), new bu("Th\u1ebb c\u00e0o \u0110T", 99005), new bu("DS \u0111i\u1ec7n tho\u1ea1i", 99006)});
                }
                bu bu2 = new bu("Thao t\u00e1c", 10407);
                bu bu3 = new bu("T\u00ednh n\u0103ng", 10406);
                bu bu4 = new bu("\u0110\u00f3ng", 10409);
                bu bu5 = new bu("Mua vip", 10408);
                if (com.mg.sq.a.m) {
                    buArray = new bu[]{bu3, bu2, buArray, bu5, bu4};
                    buArray = og.b(buArray, 3);
                } else {
                    buArray = new bu[]{bu3, bu2, bu4};
                    buArray = og.b(buArray, 2);
                }
                bu3.a(new bu[]{new bu("Ola Me", 10410), new bu("B\u00e1o online", 10411), new bu("Media", 10412), new bu("Kho Game", 10413)});
                bu3 = gp.d ? new bu("\u1ea8n Nick", 10414) : new bu("Hi\u1ec7n Nick", 10415);
                bu4 = gp.c ? new bu("Ch\u1ec9 hi\u1ec7n Online", 10416) : new bu("Hi\u1ec7n Offline", 10417);
                bu5 = new bu("\u0110\u1ed5i th\u00f4ng \u0111i\u1ec7p", 10418);
                bu bu6 = new bu("Chat v\u1edbi", 10419);
                bu bu7 = new bu("Khoe Nh\u00e2n V\u1eadt", 0);
                bu2.a(new bu[]{bu3, bu4, bu5, bu6, bu7});
                buArray = og2.a(buArray, 2);
                bv2.a(buArray);
                bv2.a(og2);
                bv2.a_(-bv2.e(), og2.f());
                bv2.d(0, og2.f() - be.a - bv2.f());
                og2.a(bv2);
                return;
            }
            case -2: {
                if (this.b == null) break;
                ((fc)this.b).a();
                return;
            }
            case -1001: {
                ak.b().a(-10001988, false);
                return;
            }
            case -1000: {
                this.G();
                com.mg.sq.a.q().k();
                return;
            }
            case 1: {
                this.l.f(95);
                return;
            }
            case 2: {
                this.t();
                return;
            }
            case 3: {
                String string = com.mg.sq.a.k(-9999991);
                gp.d = true;
                og.f(string == null ? "" : string);
                ak.b().a(-9999991, false);
                return;
            }
            case 4: {
                ak.b().a(-9999991, false);
                return;
            }
            case 5: {
                ak.b().a(false);
                this.G();
                return;
            }
            case 6: {
                ak.b().a(false);
                return;
            }
            case 7: {
                Object object = com.mg.sq.a.k(-9999992);
                if (!l.b((String)object)) {
                    String string = object;
                    object = this;
                    ((og)object).a(string, true);
                }
                ak.b().a(-9999992, false);
                return;
            }
            case 8: {
                ak.b().a(-9999992, false);
                return;
            }
            case 0: {
                dv.a().m();
                ak.b().e(241231);
            }
        }
    }

    private static void f(String string) {
        if (string == null) {
            string = pa.p();
        } else if (gp.g) {
            pa.a(string);
        }
        if (string == null) {
            string = "\u0110ang ch\u01a1i game 12 S\u1ee9 Qu\u00e2n Online! http://sq.ola.vn";
        } else if (string.length() <= 0) {
            dv.a().a((short)2);
            return;
        }
        dv.a().c(string);
    }

    public final void I() {
        if (gp.d) {
            ob.f();
            og.f(null);
            return;
        }
        ob.f();
        dv.a().a((short)1);
    }

    public final void J() {
        this.M();
        this.N();
        this.B = 1;
    }

    protected final au y() {
        return this;
    }

    public final void a(String object) {
        com.mg.sq.a.t();
        try {
            MGMIDlet.f().platformRequest((String)object);
            return;
        }
        catch (ConnectionNotFoundException connectionNotFoundException) {
            object = connectionNotFoundException;
            connectionNotFoundException.printStackTrace();
            return;
        }
    }

    public final void a(int n2, ds[] dsArray) {
        this.I = new ky(n2, dsArray);
    }

    public final ds K() {
        if (this.I == null) {
            return null;
        }
        return this.I.a();
    }

    public final void a(eb[] ebArray) {
        this.v.a(ebArray);
    }

    public final void a(String string, String string2, ef[] efArray, ec[] ecArray) {
        if (this.b == null) {
            return;
        }
        ak.b().a(new hi(string, string2, efArray, ecArray), false);
        com.mg.sq.a.t();
    }

    public final void a(long l2, eb[] ebArray) {
        eb[] ebArray2 = ebArray;
        long l3 = l2;
        oi oi2 = this.v;
        if (oi2.p == null || ebArray2 == null) {
            com.mg.sq.a.t();
            return;
        }
        int n2 = 0;
        while (n2 < oi2.p.length) {
            if (oi2.p[n2].k == l3) {
                oi2.p[n2].i = true;
                int n3 = 0;
                if (oi2.q[n2] != null) {
                    n3 = oi2.q[n2].length;
                    fa[] faArray = new fa[oi2.q[n2].length + ebArray2.length];
                    System.arraycopy(oi2.q[n2], 0, faArray, 0, oi2.q[n2].length);
                    oi2.q[n2] = faArray;
                } else {
                    oi2.q[n2] = new fa[ebArray2.length];
                }
                int n4 = 0;
                while (n4 < ebArray2.length) {
                    oi2.q[n2][n3 + n4] = new fa(false, ebArray2[n4].c(), ebArray2[n4].a(), "B\u1ea5m v\u00e0o \u0111\u1ec3 xem", 0, oi2.o.e());
                    ++n4;
                }
            }
            ++n2;
        }
        oi2.a();
        com.mg.sq.a.t();
    }

    public final void a(String string, long l2, int n2) {
        this.L = l2;
        this.K = string;
        this.M = n2;
        if (this.b != null) {
            this.L();
        }
    }

    public final void L() {
        if (gr.c < this.M) {
            gr.c = this.M;
            ap ap2 = ak.b().a("Th\u00f4ng tin", "V\u1eeba ra m\u1eaft: " + this.K, "Ch\u01a1i th\u1eed", -1007, "\u0110\u00f3ng", 6, 1);
            ap2.a(this);
            ak.b().a(ap2, false);
        }
    }

    public final void a(String[] stringArray) {
        com.mg.sq.a.t();
        if (stringArray == null) {
            stringArray = new String[]{};
        }
        com.mg.sq.a.e(stringArray);
    }

    public final void a(int n2, String string, short s2) {
        String string2 = string = l.b(string) ? " - L\u1ed7i kh\u00f4ng x\u00e1c \u0111\u1ecbnh." : string;
        if (n2 == 1 || n2 == 7) {
            if (n2 == 7) {
                return;
            }
        } else {
            com.mg.sq.a.t();
            if (this.b == null) {
                return;
            }
            if (n2 == 34 || n2 == 27 || n2 == 26 || n2 == 35) {
                if (n2 == 35 && s2 == (short)this.p.i) {
                    dv.a().l();
                    com.mg.sq.a.q().b().b(null);
                }
                string = "N\u1ed9i dung kh\u00f4ng t\u1ed3n t\u1ea1i ho\u1eb7c \u0111\u00e3 h\u1ebft";
            } else if (n2 == 8) {
                com.mg.sq.a.t();
            } else if (n2 == 24) {
                string = "Kh\u00f4ng \u0111\u01b0\u1ee3c th\u1ef1c hi\u1ec7n c\u00f9ng 1 h\u00e0nh \u0111\u1ed9ng qu\u00e1 nhanh!";
            } else if (n2 == 38) {
                com.mg.sq.a.t("Ch\u1ee9c n\u0103ng c\u1ea7n c\u00f3 VIP. B\u1ea1n c\u00f3 mu\u1ed1n mua VIP kh\u00f4ng?");
                return;
            }
            com.mg.sq.a.a(string);
        }
    }

    private static Item a(ed ed2) {
        cw.a("[BuddyListTab] createItem(...)  " + ed2.a + "  " + ed2.b);
        Object object = null;
        switch (ed2.a) {
            case 4: {
                object = new er(ed2.b, 2, ed2.d, null, ed2.e);
                break;
            }
            case 6: {
                object = new er(ed2.b, 4, ed2.d, null, ed2.e);
                break;
            }
            case 5: {
                object = new er(ed2.b, 1, ed2.d, null, ed2.e);
                break;
            }
            case 3: {
                object = new es(ed2.b, "", 20, 3);
                break;
            }
            case 1: {
                object = new es(ed2.b, "", 20, 0);
                break;
            }
            case 2: {
                object = new es(ed2.b, "", 20, 2);
                break;
            }
            case 10: {
                object = new es(ed2.b, ed2.c, 20, 131072);
                break;
            }
            case 8: {
                object = new DateField(ed2.b, 3);
                break;
            }
            case 7: {
                object = new DateField(ed2.b, 1);
                break;
            }
            case 9: {
                object = new DateField(ed2.b, 2);
            }
        }
        return object;
    }

    public final void a(String[] stringArray, String[] stringArray2) {
        this.s = stringArray2;
        this.r = stringArray;
    }

    public final void a(String string, short s2, eg[] egArray) {
        this.h(false);
        this.o.a(string, s2, egArray);
    }

    public final void a(String string, String string2, String[] stringArray, String[] stringArray2) {
        com.mg.sq.a.t();
        com.mg.sq.a.a(string, string2, stringArray, stringArray2);
    }

    public final void a(ef ef2) {
        com.mg.sq.a.q().b().b(null);
        pa.a(ef2.f, ef2.a());
        this.p.a(ef2);
    }

    public final void a(int n2, int n3) {
        com.mg.sq.a.q().b().b(new lg(n2, n3));
    }

    public final void a(int n2, ep[] epArray, byte[] byArray) {
        if (gr.b >= n2) {
            return;
        }
        pa.b(byArray);
        pa.g(n2);
        pb.a().a(epArray);
        gr.b = n2;
    }

    public final void c_() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "File kh\u00e1c \u0111ang \u0111\u01b0\u1ee3c t\u1ea3i. B\u1ea1n c\u00f3 mu\u1ed1n t\u1ea3i file kh\u00e1c kh\u00f4ng?", "C\u00f3", -1002, "Kh\u00f4ng", -1003, 1);
        ap2.a(this);
        ak.b().a(ap2, false);
    }

    public final void d_() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "File c\u00f3 k\u00edch th\u01b0\u1edbc l\u1edbn. B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n t\u1ea3i v\u1ec1 kh\u00f4ng?", "C\u00f3", -1004, "Kh\u00f4ng", -1005, 1);
        ap2.a(this);
        ak.b().a(ap2, false);
    }

    public final void e_() {
        com.mg.sq.a.q().b().b(null);
        ap ap2 = ak.b().a("Th\u00f4ng tin", "Kh\u00f4ng \u0111\u1ee7 b\u1ed9 nh\u1edb \u0111\u1ec3 t\u1ea3i file", "\u0110\u00f3ng", -1003, 1);
        ap2.a(this);
        ak.b().a(ap2, false);
    }

    public final void a(short s2, long l2, long l3) {
        ap ap2 = ak.b().a("", "B\u1ea1n v\u1eeba mua th\u00e0nh c\u00f4ng VIP " + pb.a().c(s2) + ". V\u1edbi gi\u00e1: " + com.mg.sq.a.b(l3) + ". B\u1ea1n c\u00f2n " + l.a(l2 / 86400000L, ".") + " ng\u00e0y s\u1eed d\u1ee5ng", "\u0110\u00f3ng", -1003, 1);
        ap2.a(this);
        ak.b().a(ap2);
    }

    public final void a(String object, short s2, long l2) {
        object = ak.b().a("", "B\u1ea1n v\u1eeba nh\u1eadn \u0111\u01b0\u01a1c VIP " + pb.a().c(s2) + " v\u1edbi " + l2 + " ng\u00e0y s\u1eed d\u1ee5ng" + " t\u1eeb " + (String)object, "\u0110\u00f3ng", -1003, 1);
        ((aq)object).a(this);
        ak.b().a((ap)object);
    }

    public final void a(String string, ec[] ecArray) {
        this.D = ecArray;
        this.F = new bu(string, 0);
        if (this.D != null) {
            this.F.a(og.a(this.D));
        }
    }

    public final void f_() {
        this.G();
        ap ap2 = ak.b().a("Th\u00f4ng tin", "H\u1ec7 th\u1ed1ng chat Ola c\u1ee7a b\u1ea1n \u0111\u00e3 b\u1ecb \u0111\u0103ng nh\u1eadp b\u1edfi m\u1ed9t ng\u01b0\u1eddi kh\u00e1c ho\u1eb7c thi\u1ebft b\u1ecb kh\u00e1c!", "\u0110\u00f3ng", 2, 1);
        ap2.a(ak.b());
        ak.b().a(ap2, false);
    }

    public final void g_() {
        Object object;
        com.mg.sq.a.m = true;
        pa.b(gr.e);
        Object object2 = pa.u();
        if (object2 != null) {
            object = object2;
            object2 = this;
            object2.a((j[])object, false);
        }
        if ((object2 = pa.t()) != null) {
            int n2 = 0;
            while (n2 < ((Object[])object2).length) {
                this.a((String)object2[n2], false);
                ++n2;
            }
        }
        dv.a().i();
        dv.a().a(gr.d);
        dv.a().s();
        dv.a().q();
        dv.a().r();
        dv.a().b(gr.b);
        if (gp.d) {
            object = pa.p();
            if (object == null) {
                object = "\u0110ang ch\u01a1i game 12 S\u1ee9 Qu\u00e2n Online! http://sq.ola.vn";
            }
            dv.a().c((String)object);
        }
        object = ak.b().d();
        if (this.b != null) {
            this.C();
            return;
        }
        if (object != null && ((aq)object).h() == 1) {
            object2 = (ny)object;
            ny.a(object2.b);
            og.v();
            if (com.mg.sq.a.m) {
                com.mg.sq.a.k.i(false);
            }
        }
    }

    public final void h_() {
        ea ea2 = new ea();
        new ea().b = "vn";
        ea2.e = z.r;
        ea2.f = z.s;
        ea2.g = 0;
        ea2.c = "123456789";
        ea2.d = "patriot";
        String string = System.getProperty("microedition.platform");
        ea2.a = string == null ? " " : string;
        dv.a().a(gr.e, gr.f, gp.d ? (short)2 : 1);
    }

    public final void i_() {
        dv.a().l();
    }

    public final void a(String string, ef ef2) {
        eg eg2 = new eg();
        eg2.b("#\u001b" + ef2.a() + "#");
        eg2.a(string);
        eg2.a(ef2.c);
        this.a(eg2, (short)0);
    }

    public final void b(String string, String[] stringArray, String[] stringArray2) {
        this.k(true);
        this.u.a(string, stringArray, stringArray2);
    }

    public final void c(String string, String[] stringArray, String[] stringArray2) {
        this.k(true);
        this.u.b(string, stringArray, stringArray2);
    }

    public final void a(String string, String[] stringArray, String[] stringArray2) {
        this.k(true);
        this.u.c(string, stringArray, stringArray2);
    }

    public final void a() {
    }

    public final void a(eg eg2, eg[] egArray) {
        this.h(false);
        this.o.a(eg2, egArray);
    }

    public final void a(String string, eg[] egArray) {
        this.k(true);
        this.u.a(string, egArray);
    }

    public final void a(int[] nArray, long[] lArray, short[] sArray) {
        com.mg.sq.a.t();
        com.mg.sq.a.a("Vui l\u00f2ng ch\u1ecdn VIPICON v\u00e0 th\u1eddi gian m\u00e0 b\u1ea1n mu\u1ed1n mua!", nArray, lArray, sArray);
    }
}

