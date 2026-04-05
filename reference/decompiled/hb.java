/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class hb
extends ap
implements bj,
bq,
bt {
    private j[] k;
    private bc l = new bc(0);
    private ba m;
    private boolean[] n;
    private ig o;

    public final void a(ig ig2) {
        this.o = ig2;
    }

    public hb() {
        super(1);
        j j2;
        this.l.a(this.a(), this.c(), this.i(), this.j() - 20);
        this.m = new ba();
        this.m.a(this);
        this.m.a(this);
        this.m.e(true);
        this.l.b(this.m);
        hb hb2 = this;
        int n2 = 0;
        j j3 = new j("\u0110\u00e0m \u0110\u1ea1o");
        Object[] objectArray = com.mg.sq.a.k.A().A();
        int n3 = 0;
        int n4 = 0;
        while (n4 < objectArray.length) {
            if (objectArray[n4].a().length() >= 3) {
                ++n3;
            }
            ++n4;
        }
        du[] duArray = new du[n3];
        int n5 = objectArray.length - 1;
        while (n5 >= 0) {
            if (objectArray[n5].a().length() >= 3) {
                duArray[--n3] = objectArray[n5];
            }
            --n5;
        }
        if (duArray.length <= 0) {
            j2 = null;
        } else {
            j3.a(duArray);
            j2 = j3 = j3;
        }
        if (j2 != null) {
            n2 = 1;
        }
        objectArray = com.mg.sq.a.k.B();
        n3 = 0;
        int n6 = 0;
        while (n6 < objectArray.length) {
            if (((j)objectArray[n6]).a() == 0) {
                ++n3;
            }
            ++n6;
        }
        hb2.k = new j[n3 + n2];
        if (n2 > 0) {
            hb2.k[0] = j3;
        }
        n6 = objectArray.length - 1;
        while (n6 >= 0) {
            if (((j)objectArray[n6]).a() == 0) {
                hb2.k[--n3 + n2] = objectArray[n6];
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
        this.a(new be());
        ga ga2 = new ga(-1, 2);
        hb hb3 = this;
        hb3.a(ga2, true);
        ga2 = new ga(-2, 3);
        hb3 = this;
        hb3.b(ga2, true);
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
            graphics.setColor(z.af);
            graphics.fillRect(this.a(), this.c(), this.i(), this.j() - 20);
            graphics.drawImage(oz.d, this.a() + this.i(), this.c() + this.j() - be.a, 40);
        }
        this.l.a(graphics, this.a(), this.c());
    }

    protected final void g() {
        this.l.n();
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof du) {
            du du2 = (du)object;
            return new eu(du2, this.m.e());
        }
        int n3 = 0;
        while (n3 < this.k.length) {
            j j2 = this.k[n3];
            --n2;
            if (this.n[n3]) {
                n2 -= j2.c().length;
            }
            if (n2 < 0) {
                n2 = n3;
                break;
            }
            ++n3;
        }
        j j3 = (j)object;
        return new ev(j3, this.n[n2], this.m.e());
    }

    public final void b(au object, int n2) {
        object = this.m.i(n2);
        if (object instanceof du) {
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
                n2 = ((hb)object).l.r().b;
                n3 = ((hb)object).m.s();
                if (n3 * 22 - n2 >= ((hb)object).m.f() - 40 - 22) {
                    n2 = n3 * 22 - (((hb)object).m.f() - 40 - 22);
                }
                ((hb)object).l.k(n2);
                return;
            }
            ++n3;
        }
    }

    private void t() {
        Object object;
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.k.length) {
            j j2 = this.k[n3];
            object = j2.c();
            ++n2;
            if (this.n[n3]) {
                n2 += ((du[])object).length;
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
                object = ((j)object).c();
                int n5 = 0;
                while (n5 < ((Object)object).length) {
                    objectArray[n2++] = object[n5];
                    ++n5;
                }
            }
            ++n4;
        }
        ba ba2 = this.m;
        synchronized (ba2) {
            this.m.q();
            this.m.a(objectArray);
            return;
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au object, int n2, int n3) {
        object = this.m.i(n3);
        if (object instanceof du) {
            ga ga2 = new ga(-1, 2);
            object = this;
            ((aq)object).a(ga2, true);
            return;
        }
        this.n();
    }

    public final void d(int n2, int n3) {
        ak.b().a(false);
        if (n3 == -1 && this.o != null) {
            du du2 = (du)this.m.t();
            this.o.v(du2.a());
        }
    }
}

