/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class au {
    protected int a;
    protected au b;
    protected boolean c = true;
    protected n d = new n();
    protected boolean e = true;
    protected boolean f = true;
    protected boolean g = false;
    protected aj h;

    public au() {
        this.d(this.g);
    }

    public final int b() {
        return this.a;
    }

    public final void a_(int n2) {
        this.a = n2;
    }

    public final void a(aj aj2) {
        this.h = aj2;
    }

    public final int c() {
        return this.d.a;
    }

    public final int d() {
        return this.d.b;
    }

    public final int e() {
        return this.d.c;
    }

    public int f() {
        return this.d.d;
    }

    public final m g() {
        return new m(this.d.c, this.d.d);
    }

    public void a(int n2, int n3, int n4, int n5) {
        this.d.a(n2, n3, n4, n5);
    }

    public final void a(n n2) {
        this.a(n2.a, n2.b, n2.c, n2.d);
    }

    public final n h() {
        return this.d;
    }

    public void a_(int n2, int n3) {
        this.b_(n2);
        this.c(n3);
    }

    public void b(int n2, int n3) {
        this.d(n2);
        this.e(n3);
    }

    public final void b_(int n2) {
        this.d.a = n2;
    }

    public final void c(int n2) {
        this.d.b = n2;
    }

    public void d(int n2) {
        this.d.c = n2;
    }

    public final void e(int n2) {
        this.d.d = n2;
    }

    public final boolean i() {
        return this.e;
    }

    public final void a(boolean bl2) {
        this.e = bl2;
    }

    public final boolean j() {
        return this.f;
    }

    public void b(boolean bl2) {
        this.f = bl2;
    }

    public boolean k() {
        return this.c;
    }

    public void c(boolean bl2) {
        this.c = bl2;
    }

    public final au l() {
        return this.b;
    }

    public void a(au au2) {
        this.b = au2;
    }

    public boolean m() {
        return this.g;
    }

    public void d(boolean bl2) {
        this.g = bl2;
    }

    public void n() {
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.c && this.e) {
            this.c = false;
        }
    }

    public boolean f(int n2) {
        return false;
    }

    public boolean g(int n2) {
        return false;
    }

    public boolean c(int n2, int n3) {
        return false;
    }

    public boolean e(int n2, int n3) {
        return false;
    }

    public boolean f(int n2, int n3) {
        return false;
    }

    public void o() {
    }

    public void p() {
    }

    public String toString() {
        Object object = this;
        object = "Component id = " + ((au)object).a + ";  width=" + this.d.c + ",height=" + this.d.d + ",x=" + this.d.a + ",y=" + this.d.b;
        return object;
    }
}

