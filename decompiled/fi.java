/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fi
extends au {
    private String k;
    private String l;
    private Image m;
    public boolean i;
    public int j;
    private int n;
    private int o;
    private String p;
    private Image q = f.d("/staricon");
    private Image r = f.d("/shoppingcarticon");
    private int s = -1;
    private int t = 0;
    private boolean u;
    private int v = -1;
    private d w;
    private d x;
    private long y;

    public fi(String object, int n2, int n3, int n4, long l2, int n5, boolean bl2, Image image, String string, long l3) {
        this((String)object, n2, l2, -1, false, null, -1, -1, ca.d, string, l3);
        this.t = n4;
        this.p = string;
        this.u = true;
        this.y = l3 + System.currentTimeMillis();
        n2 = n3;
        object = this;
        this.v = n2;
    }

    public fi(String string, int n2, long l2, Image image) {
        this.k = string;
        this.l = l.a(l2);
        this.m = image;
        this.j = n2;
        this.x = ca.d;
        this.e(40);
        this.d(z.r);
    }

    public fi(String string, int n2, long l2, int n3, boolean bl2, Image image, int n4, int n5, d d2, String string2, long l3) {
        this.k = string;
        this.l = l.a(l2);
        this.m = image;
        this.i = bl2;
        this.j = n2;
        this.n = n3;
        this.s = n4;
        this.o = n5;
        this.w = d2;
        this.p = string2;
        this.y = l3 + System.currentTimeMillis();
        this.x = lj.a(n3);
        if (string2 == null) {
            this.e(40);
        } else {
            this.e(55);
        }
        this.d(z.r);
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c() + 2;
        if (this.i) {
            if (this.g) {
                oz.a(graphics, n2 - 2, n3, this.e(), this.f(), true, 7470469);
            } else {
                graphics.setColor(7470469);
                graphics.fillRect(n2, n3, this.e(), this.f());
            }
        } else if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
        }
        n3 += 3;
        int n6 = 0;
        if (this.s > 0) {
            oz.b(graphics, n2 + 35, n3 + 1, this.s);
            n6 = 15;
        }
        if (this.x != null) {
            if (this.o > 0) {
                this.x.a(graphics, String.valueOf(this.k) + " +" + this.o, n2 + 35 + n6, n3 + 5, 0);
            } else {
                this.x.a(graphics, this.k, n2 + 35 + n6, n3 + 5, 0);
            }
        }
        String string = "Gi\u00e1: " + this.l + " Ken";
        com.mg.sq.a.h.a(graphics, string, n2 + 35, n3 + 20, 0);
        if (this.y - System.currentTimeMillis() > 0L) {
            long l2 = Math.max(this.y - System.currentTimeMillis(), 0L);
            n5 = z.r - ca.c.a(l.b(l2, "hh:mm:ss")) - 5;
            long l3 = l2;
            int n7 = Math.max((int)(l3 / 3600000L), 0);
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(n7 < 10 ? "0" + n7 : "" + n7)).append(":");
            l3 = l2;
            n7 = (int)Math.max(l3 % 3600000L, 0L);
            n7 = Math.max(n7 / 60000, 0);
            StringBuffer stringBuffer2 = stringBuffer.append(n7 < 10 ? "0" + n7 : "" + n7).append(":");
            l3 = l2;
            n7 = (int)Math.max(l3 % 3600000L, 0L);
            n7 = Math.max(n7 % 60000, 0);
            n4 = Math.max(n7 / 1000, 0);
            com.mg.sq.a.h.a(graphics, stringBuffer2.append(n4 < 10 ? "0" + n4 : "" + n4).toString(), n5, n3 + 20, 0);
        }
        if (this.p != null) {
            com.mg.sq.a.g.a(graphics, "@" + this.p, n2 + 35, n3 + 35, 0);
        }
        if (this.m != null || this.u) {
            int n8;
            n4 = this.m != null ? this.m.getWidth() : 32;
            int n9 = n8 = this.m != null ? this.m.getHeight() : 32;
            if (this.u) {
                if (this.p != null) {
                    oz.g(graphics, this.v, n2, n3 + 10, 0);
                } else {
                    oz.g(graphics, this.v, n2, n3 + 1, 0);
                }
            } else {
                if (this.p != null) {
                    graphics.drawImage(this.m, n2, n3 + 10, 0);
                } else {
                    graphics.drawImage(this.m, n2, n3 + 1, 0);
                }
                if (this.o > 0 && this.w != null) {
                    if (this.p == null) {
                        this.w.a(graphics, "+" + this.o, n2 + n4 - ca.d.a("+" + this.o) - 4, n3 + 32 - ca.d.a(), 0);
                    } else {
                        this.w.a(graphics, "+" + this.o, n2 + n4 - ca.d.a("+" + this.o) - 4, n3 + 9 + 32 - ca.d.a(), 0);
                    }
                }
            }
            if (this.t > 0) {
                if (this.p == null) {
                    ca.c.a(graphics, String.valueOf(this.t), n2 + n4 - 1, n3 + n8 - 15, 2);
                } else {
                    ca.c.a(graphics, String.valueOf(this.t), n2 + n4 - 1, n3 + n8 - 4, 2);
                }
            }
            if (this.i) {
                graphics.drawImage(this.r, n2 + n4 - this.r.getWidth() - 1, n3 + 1 + n8 - this.r.getHeight() - 1, 0);
            }
            if (this.n > 0) {
                n5 = n2 + 35 + com.mg.sq.a.g.a(string) + 5;
                n2 = n3 + 20;
                graphics.drawImage(this.q, n5, n2, 0);
            }
        }
        this.c = false;
    }
}

