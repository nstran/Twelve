/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class hd
extends al
implements bf,
bn,
bq {
    private dt[] k;
    private ay l = new ay(0);
    private aw m;
    private boolean[] n;
    private ih o;

    public final void a(ih ih2) {
        this.o = ih2;
    }

    public hd() {
        super(1);
        dt dt2;
        this.l.a(this.a(), this.c(), this.i(), this.j() - 20);
        this.m = new aw();
        this.m.a(this);
        this.m.a(this);
        this.m.e(true);
        this.l.b(this.m);
        hd hd2 = this;
        int n2 = 0;
        dt dt3 = new dt("\u0110\u00e0m \u0110\u1ea1o");
        Object[] objectArray = com.mg.sq.a.m.A().z();
        int n3 = 0;
        int n4 = 0;
        while (n4 < objectArray.length) {
            if (objectArray[n4].a().length() >= 3) {
                ++n3;
            }
            ++n4;
        }
        ds[] dsArray = new ds[n3];
        int n5 = objectArray.length - 1;
        while (n5 >= 0) {
            if (objectArray[n5].a().length() >= 3) {
                dsArray[--n3] = objectArray[n5];
            }
            --n5;
        }
        if (dsArray.length <= 0) {
            dt2 = null;
        } else {
            dt3.a(dsArray);
            dt2 = dt3 = dt3;
        }
        if (dt2 != null) {
            n2 = 1;
        }
        objectArray = com.mg.sq.a.m.B();
        n3 = 0;
        int n6 = 0;
        while (n6 < objectArray.length) {
            if (((dt)objectArray[n6]).a() == 0) {
                ++n3;
            }
            ++n6;
        }
        hd2.k = new dt[n3 + n2];
        if (n2 > 0) {
            hd2.k[0] = dt3;
        }
        n6 = objectArray.length - 1;
        while (n6 >= 0) {
            if (((dt)objectArray[n6]).a() == 0) {
                hd2.k[--n3 + n2] = objectArray[n6];
            }
            --n6;
        }
        this.n = new boolean[this.k.length];
        int n7 = 0;
        while (n7 < this.n.length) {
            this.n[n7] = true;
            ++n7;
        }
        this.t();
        this.a(new ba());
        gb gb2 = new gb(-1, 2);
        hd hd3 = this;
        hd3.a(gb2, true);
        gb2 = new gb(-2, 3);
        hd3 = this;
        hd3.b(gb2, true);
        this.a(this);
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        if (bl2) {
            this.l.c(true);
        }
    }

    public final void c(int n2) {
        this.l.f(n2);
    }

    public final void c(int n2, int n3) {
        this.l.e(n2, n3);
    }

    public final void a(int n2, int n3) {
        this.l.c(n2, n3);
    }

    public final void b(int n2, int n3) {
        this.l.f(n2, n3);
    }

    public final void c(Graphics graphics) {
        boolean bl2 = this.l.k();
        if (bl2) {
            this.e(true);
            graphics.setColor(v.am);
            graphics.fillRect(this.a(), this.c(), this.i(), this.j() - 20);
            graphics.drawImage(pc.d, this.a() + this.i(), this.c() + this.j() - ba.a, 40);
        }
        this.l.a(graphics, this.a(), this.c());
    }

    protected final void g() {
        this.l.n();
    }

    public final aq a(aw object, int n2) {
        if ((object = ((aw)object).i(n2)) instanceof ds) {
            ds ds2 = (ds)object;
            return new eu(ds2, this.m.e());
        }
        int n3 = 0;
        while (n3 < this.k.length) {
            dt dt2 = this.k[n3];
            --n2;
            if (this.n[n3]) {
                n2 -= dt2.c().length;
            }
            if (n2 < 0) {
                n2 = n3;
                break;
            }
            ++n3;
        }
        dt dt3 = (dt)object;
        return new ev(dt3, this.n[n2], this.m.e());
    }

    public final void b(aq object, int n2) {
        object = this.m.i(n2);
        if (object instanceof ds) {
            this.d(0, -1);
            return;
        }
        int n3 = 0;
        while (n3 < this.k.length) {
            if (object.equals(this.k[n3]) && n3 <= this.n.length) {
                this.n[n3] = !this.n[n3];
                this.t();
                this.m.k(n2);
                object = this;
                n2 = ((hd)object).l.r().b;
                n3 = ((hd)object).m.s();
                if (n3 * 22 - n2 >= ((hd)object).m.f() - 40 - 22) {
                    n2 = n3 * 22 - (((hd)object).m.f() - 40 - 22);
                }
                ((hd)object).l.k(n2);
                return;
            }
            ++n3;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void t() {
        Object object;
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.k.length) {
            dt dt2 = this.k[n3];
            object = dt2.c();
            ++n2;
            if (this.n[n3]) {
                n2 += ((ds[])object).length;
            }
            ++n3;
        }
        Object[] objectArray = new Object[n2];
        n2 = 0;
        int n4 = 0;
        while (n4 < this.k.length) {
            object = this.k[n4];
            objectArray[n2++] = object;
            if (this.n[n4]) {
                object = ((dt)object).c();
                int n5 = 0;
                while (n5 < ((Object)object).length) {
                    objectArray[n2++] = object[n5];
                    ++n5;
                }
            }
            ++n4;
        }
        aw aw2 = this.m;
        synchronized (aw2) {
            this.m.q();
            this.m.a(objectArray);
            return;
        }
    }

    public final void a(aq aq2, int n2) {
    }

    public final void a(aq object, int n2, int n3) {
        object = this.m.i(n3);
        if (object instanceof ds) {
            gb gb2 = new gb(-1, 2);
            object = this;
            ((am)object).a(gb2, true);
            return;
        }
        this.n();
    }

    public final void d(int n2, int n3) {
        ag.b().a(false);
        if (n3 == -1 && this.o != null) {
            ds ds2 = (ds)this.m.t();
            this.o.u(ds2.a());
        }
    }
}

