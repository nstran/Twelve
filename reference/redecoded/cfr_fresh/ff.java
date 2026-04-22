/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.TextBox
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextBox;

public final class ff
extends aq
implements bf,
CommandListener {
    private static char[][] j = new char[][]{{'a', '\u0103', '\u00e2', 'e', '\u00ea', 'i', 'o', '\u00f4', '\u01a1', 'u', '\u01b0', 'y', 'A', '\u0102', '\u00c2', 'E', '\u00ca', 'I', 'O', '\u00d4', '\u01a0', 'U', '\u01af', 'Y'}, {'\u00e1', '\u1eaf', '\u1ea5', '\u00e9', '\u1ebf', '\u00ed', '\u00f3', '\u1ed1', '\u1edb', '\u00fa', '\u1ee9', '\u00fd', '\u00c1', '\u1eae', '\u1ea4', '\u00c9', '\u1ebe', '\u00cd', '\u00d3', '\u1ed0', '\u1eda', '\u00da', '\u1ee8', '\u00dd'}, {'\u00e0', '\u1eb1', '\u1ea7', '\u00e8', '\u1ec1', '\u00ec', '\u00f2', '\u1ed3', '\u1edd', '\u00f9', '\u1eeb', '\u1ef3', '\u00c0', '\u1eb0', '\u1ea6', '\u00c8', '\u1ec0', '\u00cc', '\u00d2', '\u1ed2', '\u1edc', '\u00d9', '\u1eea', '\u1ef2'}, {'\u1ea3', '\u1eb3', '\u1ea9', '\u1ebb', '\u1ec3', '\u1ec9', '\u1ecf', '\u1ed5', '\u1edf', '\u1ee7', '\u1eed', '\u1ef7', '\u1ea2', '\u1eb2', '\u1ea8', '\u1eba', '\u1ec2', '\u1ec8', '\u1ece', '\u1ed4', '\u1ede', '\u1ee6', '\u1eec', '\u1ef6'}, {'\u00e3', '\u1eb5', '\u1eab', '\u1ebd', '\u1ec5', '\u0129', '\u00f5', '\u1ed7', '\u1ee1', '\u0169', '\u1eef', '\u1ef9', '\u00c3', '\u1eb4', '\u1eaa', '\u1ebc', '\u1ec4', '\u0128', '\u00d5', '\u1ed6', '\u1ee0', '\u0168', '\u1eee', '\u1ef8'}, {'\u1ea1', '\u1eb7', '\u1ead', '\u1eb9', '\u1ec7', '\u1ecb', '\u1ecd', '\u1ed9', '\u1ee3', '\u1ee5', '\u1ef1', '\u1ef5', '\u1ea0', '\u1eb6', '\u1eac', '\u1eb8', '\u1ec6', '\u1eca', '\u1ecc', '\u1ed8', '\u1ee2', '\u1ee4', '\u1ef0', '\u1ef4'}};
    private static final String[] k = new String[]{" 0", ".,?!'-()#@/:*+<=>;_$%&\"1", "ABC2", "DEF3", "GHI4", "JKL5", "MNO6", "PQRS7", "TUV8", "WXYZ9"};
    private static final String[] l = new String[]{" 0", ".,?!'-()#@/:*+<=>;_$%&\"1", "abc2", "def3", "ghi4", "jkl5", "mno6", "pqrs7", "tuv8", "wxyz9"};
    private static final String[] m = new String[]{" 0", ".,?!'-()#@/:*+<=>;_$%&\"1", "A\u0102\u00c2BC2", "D\u0110E\u00caF3", "GHI4", "JKL5", "MNO\u00d4\u01a06", "PQRS7", "TU\u01afV8", "WXYZ9"};
    private static final String[] n = new String[]{" 0", ".,?!'-()#@/:*+<=>;_$%&\"1", "a\u0103\u00e2bc2", "d\u0111e\u00eaf3", "ghi4", "jkl5", "mno\u00f4\u01a16", "pqrs7", "tu\u01b0v8", "wxyz9"};
    private static final String[] o = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] p = new String[]{"abc", "Abc", "ABC", "123"};
    private static final String[] q = new String[]{"a\u0103\u00e2", "A\u0103\u00e2", "A\u0102\u00c2", "123"};
    private String[] r = l;
    private int s = -1;
    private StringBuffer t = new StringBuffer("");
    private int u;
    private d v = bx.d;
    private int w = -1987;
    private int x;
    private int y;
    private String z;
    private String A;
    private boolean B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private String J;
    private TextBox K;
    private String L;
    private int M;
    private int N;
    private int O;
    public static String i;
    private boolean P;
    private hp Q;
    private boolean R;
    private boolean S;
    private int T;
    private int U;
    private int V;
    private int W;
    private int X;
    private fu Y;
    private String Z;
    private be aa;
    private boolean ab;
    private ae ac;

    public final void e(boolean bl2) {
        if (bl2 && v.z) {
            this.Y = pc.a(this, -3, this.c(), this.d() + 1);
        }
    }

    public final void a(String string) {
        this.Z = string;
    }

    public final void g(int n2, int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException("\u0110i\u1ec3m b\u1eaft \u0111\u1ea7u kh\u00f4ng \u0111\u01b0\u1ee3c nh\u1ecf h\u01a1n 0");
        }
        if (n3 <= 0) {
            throw new IllegalArgumentException("Chi\u1ec1u d\u00e0i kh\u00f4ng \u0111\u01b0\u1ee3c nh\u1ecf h\u01a1n 1");
        }
        this.R = true;
        this.S = true;
        this.T = n2;
        this.U = n3;
        this.w();
    }

    public final void a(hp hp2) {
        this.Q = hp2;
        hp2.a(this);
    }

    public final void h(int n2) {
        this.M = n2;
    }

    public final void b(boolean bl2) {
        super.b(bl2);
        this.t();
    }

    public static void a() {
        ff.m[0] = "+0";
        ff.k[0] = "+0";
        ff.n[0] = "+0";
        ff.l[0] = "+0";
    }

    public static void q() {
        ff.m[0] = " ";
        ff.k[0] = " ";
        ff.n[0] = " ";
        ff.l[0] = " ";
    }

    public ff() {
        this("", 255, 2);
    }

    public ff(String string, int n2, int n3) {
        new k();
        this.G = -1;
        this.J = "";
        this.M = -1;
        this.N = 0xFF0000;
        this.O = 0xFFFFFF;
        this.P = false;
        this.Q = null;
        this.R = false;
        this.S = false;
        this.T = 0;
        this.U = 0;
        this.X = 15830413;
        this.Z = "";
        this.aa = null;
        this.ab = false;
        this.ac = new ae();
        this.L = string;
        this.M = n2;
        this.H = n3 << 31 >>> 31;
        this.I = n3 >>> 1 << 1;
        if (this.I == 4) {
            this.i(3);
            this.y = 0;
        } else if (this.I == 2) {
            this.i(1);
            this.y = 0;
        }
        if (this.H == 1) {
            this.i(0);
            this.P = true;
            this.y = 0;
        }
        int n4 = 16354990;
        ff ff2 = this;
        this.N = 16354990;
        int n5 = 12643805;
        ff ff3 = this;
        this.O = 12643805;
    }

    public final String r() {
        return this.t.toString();
    }

    public final void b(String string) {
        if (i.a(string)) {
            return;
        }
        this.t.append(string);
        this.s();
    }

    private void s() {
        if (this.t.length() > this.M) {
            this.t.delete(0, this.t.length() - this.M);
        }
        this.u = this.t.length();
        this.w();
        this.t();
    }

    public final void c(String string) {
        if (string == null) {
            string = "";
        }
        this.t = new StringBuffer(string);
        this.s();
    }

    public final void i(int n2) {
        this.C = n2;
        this.y = 15;
        this.x = 0;
        String string = this.A = v.b ? q[this.C] : p[this.C];
        if (this.C == 0) {
            if (v.b) {
                this.r = n;
                return;
            }
            this.r = l;
            return;
        }
        if (this.C == 1 || this.C == 2) {
            if (v.b) {
                this.r = m;
                return;
            }
            this.r = k;
            return;
        }
        this.r = o;
    }

    public final void commandAction(Command command, Displayable displayable) {
        if (i.a(command.getLabel(), "OK")) {
            this.c(this.K.getString());
            MGMIDlet.d().a((Displayable)ag.a(), true);
            return;
        }
        if (i.a(command.getLabel(), "Tr\u1edf v\u1ec1")) {
            MGMIDlet.d().a((Displayable)ag.a(), true);
            return;
        }
        if (i.a(command.getLabel(), "D\u00e1n") && !i.a(i)) {
            this.K.insert(i, this.K.getCaretPosition());
        }
    }

    public final void f(boolean bl2) {
        this.ab = true;
    }

    public final void a(be be2) {
        this.aa = be2;
    }

    public final void g(boolean bl2) {
        if (!bl2) {
            int n2;
            int n3;
            ag.a().e();
            boolean ff2 = false;
            if (this.I == 4) {
                n3 = 2;
            }
            if (this.H == 1) {
                n2 = n3 | 0x10000;
            }
            this.K = new TextBox(this.L, this.t.toString(), this.M, n2);
            this.K.addCommand(new Command("OK", 4, 1));
            this.K.addCommand(new Command("Tr\u1edf v\u1ec1", 2, 1));
            if (!i.a(i)) {
                this.K.addCommand(new Command("D\u00e1n", 1, 1));
            }
            this.K.setCommandListener((CommandListener)this);
            MGMIDlet.d().a((Displayable)this.K, true);
            return;
        }
        ff ff2 = this;
        bk.b = ff2.aa;
        if (ff2.I == 4) {
            ag.a().a(ff2.d() + ff2.f(), 3);
            return;
        }
        if (ff2.u == 0 && ff2.I != 1) {
            ag.a().a(ff2.d() + ff2.f(), 1);
            return;
        }
        ag.a().a(ff2.d() + ff2.f(), 2);
    }

    private void t() {
        this.u();
        this.v();
    }

    private void u() {
        if (this.w >= 0) {
            v.c[this.w] = 0;
        }
        this.y = 0;
        this.x = 0;
        this.w = -1987;
        this.s = -1;
        this.B = false;
        if (this.H == 1) {
            this.w();
        }
        if (this.b != null) {
            this.b.c(true);
        }
    }

    /*
     * Unable to fully structure code
     */
    private void v() {
        block6: {
            block7: {
                block8: {
                    if (this.C == 3) break block6;
                    if (this.P) break block7;
                    if (this.u <= 0) break block8;
                    var1_1 = this;
                    if (var1_1.u <= 1) ** GOTO lbl-1000
                    var2_2 = var1_1.t.charAt(var1_1.u - 2);
                    if (var1_1.t.charAt(var1_1.u - 1) == ' ' && (var2_2 == '.' || var2_2 == '!' || var2_2 == '?')) {
                        v0 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v0 = false;
                    }
                    if (!v0) break block7;
                }
                this.i(1);
                return;
            }
            if (this.C == 1) {
                this.i(0);
            }
        }
    }

    public final void d(boolean bl2) {
        if (!this.f) {
            return;
        }
        if (this.g != bl2) {
            if (bl2 && this.R) {
                this.S = true;
                this.w();
            }
            super.d(bl2);
            this.i(this.C);
            this.u();
        }
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.w();
    }

    private void l(int n2) {
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > this.t.length()) {
            n2 = this.t.length();
        }
        this.u = n2;
        this.w();
    }

    private void h(int n2, int n3) {
        if (this.t.length() <= 0) {
            return;
        }
        if (n2 >= this.t.length()) {
            n2 = this.t.length() - 1;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        if (n2 + n3 > this.t.length()) {
            n3 = this.t.length() - n2;
        }
        this.t.delete(n2, n2 + n3);
        this.u = n2;
        this.w();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final boolean f(int var1_1) {
        if (!this.f) {
            return false;
        }
        block0 : switch (var1_1) {
            case 95: {
                this.g(false);
                break;
            }
            case 92: 
            case 93: {
                if (this.S) {
                    this.h(this.T, this.U);
                    this.S = false;
                    return true;
                }
                if (var1_1 == 93 && v.b()) {
                    return false;
                }
                if (this.u <= 0) break;
                --this.u;
                this.t.deleteCharAt(this.u);
                this.t();
                this.w();
                break;
            }
            case 97: {
                if (this.S) {
                    this.l(this.T);
                    this.S = false;
                    return true;
                }
                if (this.u > 0) {
                    --this.u;
                    if (this.u < 0) {
                        this.u = 0;
                    }
                    this.t();
                    this.w();
                    break;
                }
                if (this.w != -1987) {
                    this.t();
                    return true;
                }
                return false;
            }
            case 96: {
                if (this.S) {
                    this.l(this.T + this.U);
                    this.S = false;
                    return true;
                }
                if (this.u < this.t.length()) {
                    ++this.u;
                    if (this.u > this.t.length()) {
                        this.u = this.t.length();
                    }
                    this.t();
                    this.w();
                    break;
                }
                if (this.w != -1987) {
                    this.t();
                    return true;
                }
                if (this.Q != null) {
                    ag.b().a(this.Q, false);
                    return true;
                }
                return false;
            }
            case 94: 
            case 98: 
            case 99: {
                return false;
            }
            default: {
                if (this.S) {
                    this.h(this.T, this.U);
                    this.S = false;
                }
                if (v.a() && var1_1 == 135 || !v.a() && var1_1 == 142) {
                    if (this.I == 4) {
                        return true;
                    }
                    if (this.B) {
                        if (v.a) {
                            v.b = v.b == false;
                        }
                        this.t();
                        this.i(this.D);
                        return true;
                    }
                    this.B = true;
                    this.w = var1_1;
                    if (!v.ai) {
                        this.D = this.C++;
                        if (this.C >= 4) {
                            this.C = 0;
                        }
                        this.i(this.C);
                        return true;
                    }
                }
                if (!v.ai) ** GOTO lbl410
                var2_5 = var1_1;
                var1_2 = this;
                var3_7 = ae.a(var2_5);
                if (var1_2.C == 3 && (var3_7 < 48 || var3_7 > 57)) break;
                if (!v.b || var1_2.u <= 0) ** GOTO lbl408
                var5_10 = var3_7;
                var4_13 = var1_2;
                var1_3 = var4_13.t.charAt(var4_13.u - 1);
                var6_15 = -1;
                block7 : switch (var5_10) {
                    case 68: 
                    case 100: {
                        switch (var1_3) {
                            case 'd': {
                                var4_13.c('\u0111');
                                break block0;
                            }
                            case '\u0111': {
                                var4_13.c('d');
                                break block7;
                            }
                            case 'D': {
                                var4_13.c('\u0110');
                                break block0;
                            }
                            case '\u0110': {
                                var4_13.c('D');
                                break block7;
                            }
                        }
                        ** GOTO lbl386
                    }
                    case 65: 
                    case 97: {
                        switch (var1_3) {
                            case 'a': 
                            case '\u0103': {
                                var4_13.c('\u00e2');
                                break block0;
                            }
                            case '\u00e1': 
                            case '\u1eaf': {
                                var4_13.c('\u1ea5');
                                break block0;
                            }
                            case '\u00e0': 
                            case '\u1eb1': {
                                var4_13.c('\u1ea7');
                                break block0;
                            }
                            case '\u1ea3': 
                            case '\u1eb3': {
                                var4_13.c('\u1ea9');
                                break block0;
                            }
                            case '\u00e3': 
                            case '\u1eb5': {
                                var4_13.c('\u1eab');
                                break block0;
                            }
                            case '\u1ea1': 
                            case '\u1eb7': {
                                var4_13.c('\u1ead');
                                break block0;
                            }
                            case '\u00e2': 
                            case '\u1ea5': 
                            case '\u1ea7': 
                            case '\u1ea9': 
                            case '\u1eab': 
                            case '\u1ead': {
                                var4_13.c('a');
                                break block7;
                            }
                            case 'A': 
                            case '\u0102': {
                                var4_13.c('\u00c2');
                                break block0;
                            }
                            case '\u00c1': 
                            case '\u1eae': {
                                var4_13.c('\u1ea4');
                                break block0;
                            }
                            case '\u00c0': 
                            case '\u1eb0': {
                                var4_13.c('\u1ea6');
                                break block0;
                            }
                            case '\u1ea2': 
                            case '\u1eb2': {
                                var4_13.c('\u1ea8');
                                break block0;
                            }
                            case '\u00c3': 
                            case '\u1eb4': {
                                var4_13.c('\u1eb4');
                                break block0;
                            }
                            case '\u1ea0': 
                            case '\u1eb6': {
                                var4_13.c('\u1eac');
                                break block0;
                            }
                            case '\u00c2': 
                            case '\u1ea4': 
                            case '\u1ea6': 
                            case '\u1ea8': 
                            case '\u1eaa': 
                            case '\u1eac': {
                                var4_13.c('A');
                                break block7;
                            }
                        }
                        ** GOTO lbl386
                    }
                    case 69: 
                    case 101: {
                        switch (var1_3) {
                            case 'e': {
                                var4_13.c('\u00ea');
                                break block0;
                            }
                            case '\u00e9': {
                                var4_13.c('\u1ebf');
                                break block0;
                            }
                            case '\u00e8': {
                                var4_13.c('\u1ec1');
                                break block0;
                            }
                            case '\u1ebb': {
                                var4_13.c('\u1ec3');
                                break block0;
                            }
                            case '\u1ebd': {
                                var4_13.c('\u1ec5');
                                break block0;
                            }
                            case '\u1eb9': {
                                var4_13.c('\u1ec7');
                                break block0;
                            }
                            case '\u00ea': 
                            case '\u1ebf': 
                            case '\u1ec1': 
                            case '\u1ec3': 
                            case '\u1ec5': 
                            case '\u1ec7': {
                                var4_13.c('e');
                                break block7;
                            }
                            case 'E': {
                                var4_13.c('\u1ebf');
                                break block0;
                            }
                            case '\u00c9': {
                                var4_13.c('\u1ec1');
                                break block0;
                            }
                            case '\u00c8': {
                                var4_13.c('\u1ec3');
                                break block0;
                            }
                            case '\u1eba': {
                                var4_13.c('\u1ec5');
                                break block0;
                            }
                            case '\u1ebc': {
                                var4_13.c('\u1ec7');
                                break block0;
                            }
                            case '\u1eb8': {
                                var4_13.c('\u1ec7');
                                break block0;
                            }
                            case '\u00ca': 
                            case '\u1ebe': 
                            case '\u1ec0': 
                            case '\u1ec2': 
                            case '\u1ec4': 
                            case '\u1ec6': {
                                var4_13.c('E');
                                break block7;
                            }
                        }
                        ** GOTO lbl386
                    }
                    case 79: 
                    case 111: {
                        switch (var1_3) {
                            case 'o': 
                            case '\u01a1': {
                                var4_13.c('\u00f4');
                                break block0;
                            }
                            case '\u00f3': 
                            case '\u1edb': {
                                var4_13.c('\u1ed1');
                                break block0;
                            }
                            case '\u00f2': 
                            case '\u1edd': {
                                var4_13.c('\u1ed3');
                                break block0;
                            }
                            case '\u1ecf': 
                            case '\u1edf': {
                                var4_13.c('\u1ed5');
                                break block0;
                            }
                            case '\u00f5': 
                            case '\u1ee1': {
                                var4_13.c('\u1ed7');
                                break block0;
                            }
                            case '\u1ecd': 
                            case '\u1ee3': {
                                var4_13.c('\u1ed9');
                                break block0;
                            }
                            case '\u00f4': 
                            case '\u1ed1': 
                            case '\u1ed3': 
                            case '\u1ed5': 
                            case '\u1ed7': 
                            case '\u1ed9': {
                                var4_13.c('o');
                                break block7;
                            }
                            case 'O': 
                            case '\u01a0': {
                                var4_13.c('\u00d4');
                                break block0;
                            }
                            case '\u00d3': 
                            case '\u1eda': {
                                var4_13.c('\u1ed0');
                                break block0;
                            }
                            case '\u00d2': 
                            case '\u1edc': {
                                var4_13.c('\u1ed2');
                                break block0;
                            }
                            case '\u1ece': 
                            case '\u1ede': {
                                var4_13.c('\u1ed4');
                                break block0;
                            }
                            case '\u00d5': 
                            case '\u1ee0': {
                                var4_13.c('\u1ed6');
                                break block0;
                            }
                            case '\u1ecc': 
                            case '\u1ee2': {
                                var4_13.c('\u1ed8');
                                break block0;
                            }
                            case '\u00d4': 
                            case '\u1ed0': 
                            case '\u1ed2': 
                            case '\u1ed4': 
                            case '\u1ed6': 
                            case '\u1ed8': {
                                var4_13.c('O');
                                break block7;
                            }
                        }
                        ** GOTO lbl386
                    }
                    case 87: 
                    case 119: {
                        switch (var1_3) {
                            case 'o': 
                            case '\u00f4': {
                                var4_13.c('\u01a1');
                                break block0;
                            }
                            case '\u00f3': 
                            case '\u1ed1': {
                                var4_13.c('\u1edb');
                                break block0;
                            }
                            case '\u00f2': 
                            case '\u1ed3': {
                                var4_13.c('\u1edd');
                                break block0;
                            }
                            case '\u1ecf': 
                            case '\u1ed5': {
                                var4_13.c('\u1edf');
                                break block0;
                            }
                            case '\u00f5': 
                            case '\u1ed7': {
                                var4_13.c('\u1ee1');
                                break block0;
                            }
                            case '\u1ecd': 
                            case '\u1ed9': {
                                var4_13.c('\u1ee3');
                                break block0;
                            }
                            case '\u01a1': 
                            case '\u1edb': 
                            case '\u1edd': 
                            case '\u1edf': 
                            case '\u1ee1': 
                            case '\u1ee3': {
                                var4_13.c('o');
                                break block7;
                            }
                            case 'O': 
                            case '\u00d4': {
                                var4_13.c('\u01a0');
                                break block0;
                            }
                            case '\u00d3': 
                            case '\u1ed0': {
                                var4_13.c('\u1eda');
                                break block0;
                            }
                            case '\u00d2': 
                            case '\u1ed2': {
                                var4_13.c('\u1edc');
                                break block0;
                            }
                            case '\u1ece': 
                            case '\u1ed4': {
                                var4_13.c('\u1ede');
                                break block0;
                            }
                            case '\u00d5': 
                            case '\u1ed6': {
                                var4_13.c('\u1ee0');
                                break block0;
                            }
                            case '\u1ecc': 
                            case '\u1ed8': {
                                var4_13.c('\u1ee2');
                                break block0;
                            }
                            case '\u01a0': 
                            case '\u1eda': 
                            case '\u1edc': 
                            case '\u1ede': 
                            case '\u1ee0': 
                            case '\u1ee2': {
                                var4_13.c('O');
                                break block7;
                            }
                            case 'u': {
                                var4_13.c('\u01b0');
                                break block0;
                            }
                            case '\u00fa': {
                                var4_13.c('\u1ee9');
                                break block0;
                            }
                            case '\u00f9': {
                                var4_13.c('\u1eeb');
                                break block0;
                            }
                            case '\u1ee7': {
                                var4_13.c('\u1eed');
                                break block0;
                            }
                            case '\u0169': {
                                var4_13.c('\u1eef');
                                break block0;
                            }
                            case '\u1ee5': {
                                var4_13.c('\u1ef1');
                                break block0;
                            }
                            case '\u01b0': 
                            case '\u1ee9': 
                            case '\u1eeb': 
                            case '\u1eed': 
                            case '\u1eef': 
                            case '\u1ef1': {
                                var4_13.c('u');
                                break block7;
                            }
                            case 'U': {
                                var4_13.c('\u01af');
                                break block0;
                            }
                            case '\u00da': {
                                var4_13.c('\u1ee8');
                                break block0;
                            }
                            case '\u00d9': {
                                var4_13.c('\u1eea');
                                break block0;
                            }
                            case '\u1ee6': {
                                var4_13.c('\u1eec');
                                break block0;
                            }
                            case '\u0168': {
                                var4_13.c('\u1eee');
                                break block0;
                            }
                            case '\u1ee4': {
                                var4_13.c('\u1ef0');
                                break block0;
                            }
                            case '\u01af': 
                            case '\u1ee8': 
                            case '\u1eea': 
                            case '\u1eec': 
                            case '\u1eee': 
                            case '\u1ef0': {
                                var4_13.c('U');
                                break block7;
                            }
                            case 'a': 
                            case '\u00e2': {
                                var4_13.c('\u0103');
                                break block0;
                            }
                            case '\u00e1': 
                            case '\u1ea5': {
                                var4_13.c('\u1eaf');
                                break block0;
                            }
                            case '\u00e0': 
                            case '\u1ea7': {
                                var4_13.c('\u1eb1');
                                break block0;
                            }
                            case '\u1ea3': 
                            case '\u1ea9': {
                                var4_13.c('\u1eb3');
                                break block0;
                            }
                            case '\u00e3': 
                            case '\u1eab': {
                                var4_13.c('\u1eb5');
                                break block0;
                            }
                            case '\u1ea1': 
                            case '\u1ead': {
                                var4_13.c('\u1eb7');
                                break block0;
                            }
                            case '\u0103': 
                            case '\u1eaf': 
                            case '\u1eb1': 
                            case '\u1eb3': 
                            case '\u1eb5': 
                            case '\u1eb7': {
                                var4_13.c('a');
                                break block7;
                            }
                            case 'A': 
                            case '\u00c2': {
                                var4_13.c('\u0102');
                                break block0;
                            }
                            case '\u00c1': 
                            case '\u1ea4': {
                                var4_13.c('\u1eae');
                                break block0;
                            }
                            case '\u00c0': 
                            case '\u1ea6': {
                                var4_13.c('\u1eb0');
                                break block0;
                            }
                            case '\u1ea2': 
                            case '\u1ea8': {
                                var4_13.c('\u1eb2');
                                break block0;
                            }
                            case '\u00c3': 
                            case '\u1eaa': {
                                var4_13.c('\u1eb4');
                                break block0;
                            }
                            case '\u1ea0': 
                            case '\u1eac': {
                                var4_13.c('\u1eb6');
                                break block0;
                            }
                            case '\u0102': 
                            case '\u1eae': 
                            case '\u1eb0': 
                            case '\u1eb2': 
                            case '\u1eb4': 
                            case '\u1eb6': {
                                var4_13.c('A');
                                break block7;
                            }
                        }
                        ** GOTO lbl386
                    }
                    case 90: 
                    case 122: {
                        var6_15 = 0;
                        ** GOTO lbl386
                    }
                    case 83: 
                    case 115: {
                        var6_15 = 1;
                        ** GOTO lbl386
                    }
                    case 70: 
                    case 102: {
                        var6_15 = 2;
                        ** GOTO lbl386
                    }
                    case 82: 
                    case 114: {
                        var6_15 = 3;
                        ** GOTO lbl386
                    }
                    case 88: 
                    case 120: {
                        var6_15 = 4;
                        ** GOTO lbl386
                    }
                    case 74: 
                    case 106: {
                        var6_15 = 5;
                    }
lbl386:
                    // 12 sources

                    default: {
                        if (var6_15 < 0) break;
                        var2_5 = 5;
                        while (var2_5 >= 0) {
                            var3_7 = ff.j[0].length - 1;
                            while (var3_7 >= 0) {
                                if (var1_3 == ff.j[var2_5][var3_7]) {
                                    if (var2_5 == var6_15) {
                                        var1_3 = ff.j[0][var3_7];
                                        var4_13.c(var1_3);
                                        var4_13.d((char)var5_10);
                                        break block0;
                                    }
                                    var1_3 = ff.j[var6_15][var3_7];
                                    var4_13.c(var1_3);
                                    break block0;
                                }
                                --var3_7;
                            }
                            --var2_5;
                        }
                        break block7;
                    }
                }
                var4_13.d((char)var5_10);
                break;
lbl408:
                // 1 sources

                var1_2.d((char)var3_7);
                break;
lbl410:
                // 1 sources

                var2_6 = var1_1;
                var1_4 = this;
                switch (var2_6) {
                    case 135: 
                    case 142: 
                    case 148: 
                    case 149: 
                    case 150: 
                    case 151: 
                    case 152: 
                    case 153: 
                    case 154: 
                    case 155: 
                    case 156: 
                    case 157: {
                        if (!v.a()) ** GOTO lbl434
                        if (var2_6 != 148 || var1_4.C == 3) ** GOTO lbl424
                        if (var1_4.B) {
                            var1_4.c('0');
                            var1_4.t();
                            var1_4.w();
                            break block0;
                        }
                        var1_4.d(' ');
                        var1_4.w = var2_6;
                        ** GOTO lbl554
lbl424:
                        // 1 sources

                        if (var2_6 == 142 && !v.b) {
                            if (var1_4.C == 3 || var1_4.B) {
                                var1_4.d('+');
                                var1_4.t();
                                var1_4.w();
                                break block0;
                            }
                            var1_4.w = var2_6;
                            var1_4.B = true;
                            break block0;
                        }
                        ** GOTO lbl441
lbl434:
                        // 1 sources

                        if (var2_6 == 135) {
                            var5_11 = var1_4;
                            var5_11.d(' ');
                            var5_11.w = -1987;
                            var5_11.B = false;
                            var5_11.v();
                            break block0;
                        }
lbl441:
                        // 3 sources

                        if (!v.b || (!v.a() || var2_6 != 142) && (v.a() || var2_6 != 148) || var1_4.u <= 0) ** GOTO lbl-1000
                        var3_8 = var1_4.t.charAt(var1_4.u - 1);
                        if (!var1_4.B) ** GOTO lbl528
                        var5_12 = var3_8;
                        var6_16 = var5_12;
                        switch (var5_12) {
                            case 'a': 
                            case '\u00e0': 
                            case '\u00e1': 
                            case '\u00e3': 
                            case '\u1ea1': 
                            case '\u1ea3': {
                                var6_16 = 97;
                                break;
                            }
                            case '\u0103': 
                            case '\u1eaf': 
                            case '\u1eb1': 
                            case '\u1eb3': 
                            case '\u1eb5': 
                            case '\u1eb7': {
                                var6_16 = 259;
                                break;
                            }
                            case '\u00e2': 
                            case '\u1ea5': 
                            case '\u1ea7': 
                            case '\u1ea9': 
                            case '\u1eab': 
                            case '\u1ead': {
                                var6_16 = 226;
                                break;
                            }
                            case 'o': 
                            case '\u00f2': 
                            case '\u00f3': 
                            case '\u00f5': 
                            case '\u1ecd': 
                            case '\u1ecf': {
                                var6_16 = 111;
                                break;
                            }
                            case '\u01a1': 
                            case '\u1edb': 
                            case '\u1edd': 
                            case '\u1edf': 
                            case '\u1ee1': 
                            case '\u1ee3': {
                                var6_16 = 417;
                                break;
                            }
                            case '\u00f4': 
                            case '\u1ed1': 
                            case '\u1ed3': 
                            case '\u1ed5': 
                            case '\u1ed7': 
                            case '\u1ed9': {
                                var6_16 = 244;
                                break;
                            }
                            case 'e': 
                            case '\u00e8': 
                            case '\u00e9': 
                            case '\u1eb9': 
                            case '\u1ebb': 
                            case '\u1ebd': {
                                var6_16 = 101;
                                break;
                            }
                            case '\u00ea': 
                            case '\u1ebf': 
                            case '\u1ec1': 
                            case '\u1ec3': 
                            case '\u1ec5': 
                            case '\u1ec7': {
                                var6_16 = 234;
                                break;
                            }
                            case 'u': 
                            case '\u00f9': 
                            case '\u00fa': 
                            case '\u0169': 
                            case '\u1ee5': 
                            case '\u1ee7': {
                                var6_16 = 117;
                                break;
                            }
                            case '\u01b0': 
                            case '\u1ee9': 
                            case '\u1eeb': 
                            case '\u1eed': 
                            case '\u1eef': 
                            case '\u1ef1': {
                                var6_16 = 432;
                                break;
                            }
                            case 'i': 
                            case '\u00ec': 
                            case '\u00ed': 
                            case '\u0129': 
                            case '\u1ec9': 
                            case '\u1ecb': {
                                var6_16 = 105;
                                break;
                            }
                            case 'y': 
                            case '\u00fd': 
                            case '\u1ef3': 
                            case '\u1ef5': 
                            case '\u1ef7': 
                            case '\u1ef9': {
                                var6_16 = 121;
                                break;
                            }
                            case 'd': 
                            case '\u0111': {
                                var6_16 = 100;
                                break;
                            }
                            case 'A': 
                            case '\u00c0': 
                            case '\u00c1': 
                            case '\u00c3': 
                            case '\u1ea0': 
                            case '\u1ea2': {
                                var6_16 = 65;
                                break;
                            }
                            case '\u0102': 
                            case '\u1eae': 
                            case '\u1eb0': 
                            case '\u1eb2': 
                            case '\u1eb4': 
                            case '\u1eb6': {
                                var6_16 = 258;
                                break;
                            }
                            case '\u00c2': 
                            case '\u1ea4': 
                            case '\u1ea6': 
                            case '\u1ea8': 
                            case '\u1eaa': 
                            case '\u1eac': {
                                var6_16 = 194;
                                break;
                            }
                            case 'O': 
                            case '\u00d2': 
                            case '\u00d3': 
                            case '\u00d5': 
                            case '\u1ecc': 
                            case '\u1ece': {
                                var6_16 = 79;
                                break;
                            }
                            case '\u01a0': 
                            case '\u1eda': 
                            case '\u1edc': 
                            case '\u1ede': 
                            case '\u1ee0': 
                            case '\u1ee2': {
                                var6_16 = 416;
                                break;
                            }
                            case '\u00d4': 
                            case '\u1ed0': 
                            case '\u1ed2': 
                            case '\u1ed4': 
                            case '\u1ed6': 
                            case '\u1ed8': {
                                var6_16 = 212;
                                break;
                            }
                            case 'E': 
                            case '\u00c8': 
                            case '\u00c9': 
                            case '\u1eb8': 
                            case '\u1eba': 
                            case '\u1ebc': {
                                var6_16 = 69;
                                break;
                            }
                            case '\u00ca': 
                            case '\u1ebe': 
                            case '\u1ec0': 
                            case '\u1ec2': 
                            case '\u1ec4': 
                            case '\u1ec6': {
                                var6_16 = 202;
                                break;
                            }
                            case 'U': 
                            case '\u00d9': 
                            case '\u00da': 
                            case '\u0168': 
                            case '\u1ee4': 
                            case '\u1ee6': {
                                var6_16 = 85;
                                break;
                            }
                            case '\u01af': 
                            case '\u1ee8': 
                            case '\u1eea': 
                            case '\u1eec': 
                            case '\u1eee': 
                            case '\u1ef0': {
                                var6_16 = 431;
                                break;
                            }
                            case 'I': 
                            case '\u00cc': 
                            case '\u00cd': 
                            case '\u0128': 
                            case '\u1ec8': 
                            case '\u1eca': {
                                var6_16 = 73;
                                break;
                            }
                            case 'Y': 
                            case '\u00dd': 
                            case '\u1ef2': 
                            case '\u1ef4': 
                            case '\u1ef6': 
                            case '\u1ef8': {
                                var6_16 = 89;
                                break;
                            }
                            case 'D': 
                            case '\u0110': {
                                var6_16 = 68;
                            }
                        }
                        var4_14 = var6_16;
                        if (var4_14 != var3_8) {
                            var1_4.c(var4_14);
                        }
                        ** GOTO lbl-1000
lbl528:
                        // 1 sources

                        var4_14 = ff.a(var3_8);
                        if (var4_14 != '\u001a') {
                            var1_4.c(var4_14);
                            var1_4.t();
                        } else lbl-1000:
                        // 3 sources

                        {
                            if (var1_4.B && var2_6 != var1_4.w) {
                                v.c[var2_6] = 0;
                                break block0;
                            }
                            if (var2_6 != var1_4.w) {
                                var1_4.s = -1;
                                if (var1_4.w != -1987 && v.a() && var1_4.w != 135 || !v.a() && var1_4.w != 142) {
                                    var1_4.t();
                                }
                            }
                            if (var2_6 < 148 || var2_6 > 157) break block0;
                            var1_4.x = 25;
                            var1_4.z = var3_9 = var1_4.r[var2_6 - 148];
                            var1_4.s = var1_4.B != false ? var3_9.length() - 1 : (var1_4.s + 1) % var3_9.length();
                            var4_14 = var3_9.charAt(var1_4.s);
                            if (var2_6 != var1_4.w) {
                                var1_4.d(var4_14);
                                var1_4.w = var2_6;
                            } else {
                                var1_4.c(var4_14);
                            }
                            if (var1_4.B || var3_9.length() < 2) {
                                var1_4.t();
                                var1_4.w();
                                break block0;
                            }
                        }
lbl554:
                        // 4 sources

                        var1_4.B = true;
                    }
                }
            }
        }
        return true;
    }

    public final boolean c(int n2, int n3) {
        int n4 = 0;
        if (this.Y != null) {
            this.Y.c(n2, n3);
            n4 = 20;
        }
        if (this.m()) {
            this.S = false;
        }
        if (!this.f) {
            return false;
        }
        if (n2 > this.c() + n4 && n2 < this.c() + this.e() + n4 && n3 > this.d() && n3 < this.d() + this.f()) {
            if (this.g) {
                ag.a();
                this.g(!ag.f());
            } else {
                this.g(true);
            }
            return true;
        }
        return false;
    }

    public final boolean g(int n2) {
        if (!this.g) {
            return false;
        }
        this.B = false;
        return true;
    }

    private static char a(char c2) {
        char c3 = '\u001a';
        switch (c2) {
            case 'a': {
                c3 = '\u00e1';
                break;
            }
            case '\u00e1': {
                c3 = '\u00e0';
                break;
            }
            case '\u00e0': {
                c3 = '\u1ea3';
                break;
            }
            case '\u1ea3': {
                c3 = '\u00e3';
                break;
            }
            case '\u00e3': {
                c3 = '\u1ea1';
                break;
            }
            case '\u1ea1': {
                c3 = 'a';
                break;
            }
            case '\u0103': {
                c3 = '\u1eaf';
                break;
            }
            case '\u1eaf': {
                c3 = '\u1eb1';
                break;
            }
            case '\u1eb1': {
                c3 = '\u1eb3';
                break;
            }
            case '\u1eb3': {
                c3 = '\u1eb5';
                break;
            }
            case '\u1eb5': {
                c3 = '\u1eb7';
                break;
            }
            case '\u1eb7': {
                c3 = '\u0103';
                break;
            }
            case '\u00e2': {
                c3 = '\u1ea5';
                break;
            }
            case '\u1ea5': {
                c3 = '\u1ea7';
                break;
            }
            case '\u1ea7': {
                c3 = '\u1ea9';
                break;
            }
            case '\u1ea9': {
                c3 = '\u1eab';
                break;
            }
            case '\u1eab': {
                c3 = '\u1ead';
                break;
            }
            case '\u1ead': {
                c3 = '\u00e2';
                break;
            }
            case 'o': {
                c3 = '\u00f3';
                break;
            }
            case '\u00f3': {
                c3 = '\u00f2';
                break;
            }
            case '\u00f2': {
                c3 = '\u1ecf';
                break;
            }
            case '\u1ecf': {
                c3 = '\u00f5';
                break;
            }
            case '\u00f5': {
                c3 = '\u1ecd';
                break;
            }
            case '\u1ecd': {
                c3 = 'o';
                break;
            }
            case '\u01a1': {
                c3 = '\u1edb';
                break;
            }
            case '\u1edb': {
                c3 = '\u1edd';
                break;
            }
            case '\u1edd': {
                c3 = '\u1edf';
                break;
            }
            case '\u1edf': {
                c3 = '\u1ee1';
                break;
            }
            case '\u1ee1': {
                c3 = '\u1ee3';
                break;
            }
            case '\u1ee3': {
                c3 = '\u01a1';
                break;
            }
            case '\u00f4': {
                c3 = '\u1ed1';
                break;
            }
            case '\u1ed1': {
                c3 = '\u1ed3';
                break;
            }
            case '\u1ed3': {
                c3 = '\u1ed5';
                break;
            }
            case '\u1ed5': {
                c3 = '\u1ed7';
                break;
            }
            case '\u1ed7': {
                c3 = '\u1ed9';
                break;
            }
            case '\u1ed9': {
                c3 = '\u00f4';
                break;
            }
            case 'e': {
                c3 = '\u00e9';
                break;
            }
            case '\u00e9': {
                c3 = '\u00e8';
                break;
            }
            case '\u00e8': {
                c3 = '\u1ebb';
                break;
            }
            case '\u1ebb': {
                c3 = '\u1ebd';
                break;
            }
            case '\u1ebd': {
                c3 = '\u1eb9';
                break;
            }
            case '\u1eb9': {
                c3 = 'e';
                break;
            }
            case '\u00ea': {
                c3 = '\u1ebf';
                break;
            }
            case '\u1ebf': {
                c3 = '\u1ec1';
                break;
            }
            case '\u1ec1': {
                c3 = '\u1ec3';
                break;
            }
            case '\u1ec3': {
                c3 = '\u1ec5';
                break;
            }
            case '\u1ec5': {
                c3 = '\u1ec7';
                break;
            }
            case '\u1ec7': {
                c3 = '\u00ea';
                break;
            }
            case 'u': {
                c3 = '\u00fa';
                break;
            }
            case '\u00fa': {
                c3 = '\u00f9';
                break;
            }
            case '\u00f9': {
                c3 = '\u1ee7';
                break;
            }
            case '\u1ee7': {
                c3 = '\u0169';
                break;
            }
            case '\u0169': {
                c3 = '\u1ee5';
                break;
            }
            case '\u1ee5': {
                c3 = 'u';
                break;
            }
            case '\u01b0': {
                c3 = '\u1ee9';
                break;
            }
            case '\u1ee9': {
                c3 = '\u1eeb';
                break;
            }
            case '\u1eeb': {
                c3 = '\u1eed';
                break;
            }
            case '\u1eed': {
                c3 = '\u1eef';
                break;
            }
            case '\u1eef': {
                c3 = '\u1ef1';
                break;
            }
            case '\u1ef1': {
                c3 = '\u01b0';
                break;
            }
            case 'i': {
                c3 = '\u00ed';
                break;
            }
            case '\u00ed': {
                c3 = '\u00ec';
                break;
            }
            case '\u00ec': {
                c3 = '\u1ec9';
                break;
            }
            case '\u1ec9': {
                c3 = '\u0129';
                break;
            }
            case '\u0129': {
                c3 = '\u1ecb';
                break;
            }
            case '\u1ecb': {
                c3 = 'i';
                break;
            }
            case 'y': {
                c3 = '\u00fd';
                break;
            }
            case '\u00fd': {
                c3 = '\u1ef3';
                break;
            }
            case '\u1ef3': {
                c3 = '\u1ef7';
                break;
            }
            case '\u1ef7': {
                c3 = '\u1ef9';
                break;
            }
            case '\u1ef9': {
                c3 = '\u1ef5';
                break;
            }
            case '\u1ef5': {
                c3 = 'y';
                break;
            }
            case 'd': {
                c3 = '\u0111';
                break;
            }
            case '\u0111': {
                c3 = 'd';
                break;
            }
            case 'A': {
                c3 = '\u00c1';
                break;
            }
            case '\u00c1': {
                c3 = '\u00c0';
                break;
            }
            case '\u00c0': {
                c3 = '\u1ea2';
                break;
            }
            case '\u1ea2': {
                c3 = '\u00c3';
                break;
            }
            case '\u00c3': {
                c3 = '\u1ea0';
                break;
            }
            case '\u1ea0': {
                c3 = 'A';
                break;
            }
            case '\u0102': {
                c3 = '\u1eae';
                break;
            }
            case '\u1eae': {
                c3 = '\u1eb0';
                break;
            }
            case '\u1eb0': {
                c3 = '\u1eb2';
                break;
            }
            case '\u1eb2': {
                c3 = '\u1eb4';
                break;
            }
            case '\u1eb4': {
                c3 = '\u1eb6';
                break;
            }
            case '\u1eb6': {
                c3 = '\u0102';
                break;
            }
            case '\u00c2': {
                c3 = '\u1ea4';
                break;
            }
            case '\u1ea4': {
                c3 = '\u1ea6';
                break;
            }
            case '\u1ea6': {
                c3 = '\u1ea8';
                break;
            }
            case '\u1ea8': {
                c3 = '\u1eaa';
                break;
            }
            case '\u1eaa': {
                c3 = '\u1eac';
                break;
            }
            case '\u1eac': {
                c3 = '\u00c2';
                break;
            }
            case 'O': {
                c3 = '\u00d3';
                break;
            }
            case '\u00d3': {
                c3 = '\u00d2';
                break;
            }
            case '\u00d2': {
                c3 = '\u1ece';
                break;
            }
            case '\u1ece': {
                c3 = '\u00d5';
                break;
            }
            case '\u00d5': {
                c3 = '\u1ecc';
                break;
            }
            case '\u1ecc': {
                c3 = 'O';
                break;
            }
            case '\u01a0': {
                c3 = '\u1eda';
                break;
            }
            case '\u1eda': {
                c3 = '\u1edc';
                break;
            }
            case '\u1edc': {
                c3 = '\u1ede';
                break;
            }
            case '\u1ede': {
                c3 = '\u1ee0';
                break;
            }
            case '\u1ee0': {
                c3 = '\u1ee2';
                break;
            }
            case '\u1ee2': {
                c3 = '\u01a0';
                break;
            }
            case '\u00d4': {
                c3 = '\u1ed0';
                break;
            }
            case '\u1ed0': {
                c3 = '\u1ed2';
                break;
            }
            case '\u1ed2': {
                c3 = '\u1ed4';
                break;
            }
            case '\u1ed4': {
                c3 = '\u1ed6';
                break;
            }
            case '\u1ed6': {
                c3 = '\u1ed8';
                break;
            }
            case '\u1ed8': {
                c3 = '\u00d4';
                break;
            }
            case 'E': {
                c3 = '\u00c9';
                break;
            }
            case '\u00c9': {
                c3 = '\u00c8';
                break;
            }
            case '\u00c8': {
                c3 = '\u1eba';
                break;
            }
            case '\u1eba': {
                c3 = '\u1ebc';
                break;
            }
            case '\u1ebc': {
                c3 = '\u1eb8';
                break;
            }
            case '\u1eb8': {
                c3 = 'E';
                break;
            }
            case '\u00ca': {
                c3 = '\u1ebe';
                break;
            }
            case '\u1ebe': {
                c3 = '\u1ec0';
                break;
            }
            case '\u1ec0': {
                c3 = '\u1ec2';
                break;
            }
            case '\u1ec2': {
                c3 = '\u1ec4';
                break;
            }
            case '\u1ec4': {
                c3 = '\u1ec6';
                break;
            }
            case '\u1ec6': {
                c3 = '\u00ca';
                break;
            }
            case 'U': {
                c3 = '\u00da';
                break;
            }
            case '\u00da': {
                c3 = '\u00d9';
                break;
            }
            case '\u00d9': {
                c3 = '\u1ee6';
                break;
            }
            case '\u1ee6': {
                c3 = '\u0168';
                break;
            }
            case '\u0168': {
                c3 = '\u1ee4';
                break;
            }
            case '\u1ee4': {
                c3 = 'U';
                break;
            }
            case '\u01af': {
                c3 = '\u1ee8';
                break;
            }
            case '\u1ee8': {
                c3 = '\u1eea';
                break;
            }
            case '\u1eea': {
                c3 = '\u1eec';
                break;
            }
            case '\u1eec': {
                c3 = '\u1eee';
                break;
            }
            case '\u1eee': {
                c3 = '\u1ef0';
                break;
            }
            case '\u1ef0': {
                c3 = '\u01af';
                break;
            }
            case 'I': {
                c3 = '\u00cd';
                break;
            }
            case '\u00cd': {
                c3 = '\u00cc';
                break;
            }
            case '\u00cc': {
                c3 = '\u1ec8';
                break;
            }
            case '\u1ec8': {
                c3 = '\u0128';
                break;
            }
            case '\u0128': {
                c3 = '\u1eca';
                break;
            }
            case '\u1eca': {
                c3 = 'I';
                break;
            }
            case 'Y': {
                c3 = '\u00dd';
                break;
            }
            case '\u00dd': {
                c3 = '\u1ef2';
                break;
            }
            case '\u1ef2': {
                c3 = '\u1ef6';
                break;
            }
            case '\u1ef6': {
                c3 = '\u1ef8';
                break;
            }
            case '\u1ef8': {
                c3 = '\u1ef4';
                break;
            }
            case '\u1ef4': {
                c3 = 'Y';
                break;
            }
            case 'D': {
                c3 = '\u0110';
                break;
            }
            case '\u0110': {
                c3 = 'D';
            }
        }
        return c3;
    }

    private char b(char c2) {
        if (this.h == null) {
            return c2;
        }
        this.ac.a(c2);
        this.h.a(this.ac);
        return this.ac.a();
    }

    private void c(char c2) {
        if (this.t.length() <= 0 || this.u <= 0) {
            return;
        }
        if ((c2 = this.b(c2)) != '\u001a') {
            this.t.setCharAt(this.u - 1, c2);
            this.w();
        }
    }

    private void d(char c2) {
        if ((c2 = this.b(c2)) != '\u001a') {
            if (this.t.length() + 1 > this.M) {
                this.t.delete(this.M - 1, this.t.length());
                if (this.u > this.t.length()) {
                    this.u = this.t.length();
                }
            }
            if (this.u <= this.t.length()) {
                this.t.insert(this.u, c2);
            } else {
                this.t.append(c2);
            }
            ++this.u;
            this.w();
        }
    }

    private void w() {
        int n2 = this.e() - (this.Y != null ? 20 : 0);
        this.J = "";
        if (this.H == 1) {
            int n3 = this.u - 2;
            while (n3 >= 0) {
                this.J = String.valueOf(this.J) + "*";
                --n3;
            }
            if (this.u > 0) {
                this.J = this.x > 0 ? String.valueOf(this.J) + this.t.charAt(this.u - 1) : String.valueOf(this.J) + "*";
            }
            n3 = this.t.length() - 1;
            while (n3 >= this.u) {
                this.J = String.valueOf(this.J) + "*";
                --n3;
            }
        } else {
            this.J = this.t.toString();
        }
        String string = this.J;
        if (string == null) {
            string = "";
        }
        int n4 = this.v.a(string);
        if (this.E < 0 && this.v.a(string) + this.E < n2 - 4 - 3) {
            this.E = n2 - n4;
        }
        this.G = this.v.a(string.substring(0, this.u));
        if (this.E + this.G <= 0) {
            this.E = -this.G;
            this.E += 8;
        } else if (this.E + this.G >= n2 - 4 - 3) {
            this.E = n2 - this.G - 8;
        }
        if (this.E > 0) {
            this.E = 0;
        }
        if (this.G <= 0) {
            this.G = -1;
        }
        if (this.S) {
            this.W = 0;
            this.V = 0;
            if (this.t.length() <= 0 || this.T >= this.t.length()) {
                return;
            }
            int n5 = this.T;
            int n6 = this.U;
            if (n5 < 0) {
                n5 = 0;
            }
            if (n5 + n6 > this.t.length()) {
                n6 = this.t.length() - n5;
            }
            this.V = n5 == 0 ? 0 : this.v.a(string.substring(0, n5));
            this.W = n5 + n6 >= string.length() ? n4 : this.V + this.v.a(string.substring(n5, n5 + n6));
            this.V += this.E;
            this.W += this.E;
            n2 -= 8;
            if (this.V < 0) {
                this.V = 0;
            }
            if (this.W > n2) {
                this.W = n2;
            }
        }
    }

    public final void n() {
        if (this.x > 0) {
            --this.x;
            if (this.x == 0) {
                this.t();
            }
        }
        if (this.y > 0) {
            --this.y;
            if (this.x == 0 && this.b != null) {
                this.b.c(true);
            }
        }
        if (!this.g) {
            return;
        }
        if (this.F < 12) {
            ++this.F;
            return;
        }
        this.F = 0;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        if (!this.e) {
            return;
        }
        int n8 = this.Y != null ? 20 : 0;
        int n9 = this.c() + n2 + n8;
        int n10 = n7 = this.d() + n3;
        int n11 = n9;
        Graphics graphics2 = graphics;
        ff ff2 = this;
        pc.a(graphics2, n11, n10, ff2.e() - (ff2.Y != null ? 20 : 0), ff2.f(), ff2.g && ff2.f);
        n10 = n7;
        n11 = n9;
        graphics2 = graphics;
        ff2 = this;
        if (ff2.W > ff2.V && ff2.S && ff2.m()) {
            n6 = n11 + 4 + ff2.V;
            n5 = n10 + (ff2.d.d - ff2.v.a() >> 1);
            graphics2.setColor(ff2.X);
            graphics2.fillRect(n6, n5, ff2.W - ff2.V, ff2.v.a());
        }
        cw.a(graphics);
        cw.a(graphics, n9 + 4, n7, this.e() - 4 - 4 - n8, this.f());
        n10 = n7;
        n11 = n9;
        graphics2 = graphics;
        ff2 = this;
        n6 = n11 + 4 + ff2.E;
        n5 = n10 + (ff2.d.d - ff2.v.a() >> 1);
        if (ff2.J == null || ff2.J.equals("")) {
            if (ff2.Z.length() > 0) {
                bx.d.a(true);
                bx.d.a(graphics2, ff2.Z, n6, n5, 0);
                bx.d.a(false);
            }
        } else if (ff2.H == 1) {
            if (ff2.x <= 0) {
                ff2.v.a(graphics2, ff2.J, n6, n5 + 3, 0);
            } else if (ff2.J.length() > 0) {
                String string = ff2.J.substring(0, ff2.J.length() - 1);
                if (!i.b(string)) {
                    ff2.v.a(graphics2, string, n6, n5 + 3, 0);
                }
                n4 = ff2.v.a(string);
                ff2.v.a(graphics2, String.valueOf(ff2.J.charAt(ff2.J.length() - 1)), n6 + n4, n5, 0);
            }
        } else {
            ff2.v.a(graphics2, ff2.J, n6, n5, 0);
        }
        cw.b(graphics);
        n10 = n7;
        n11 = n9;
        graphics2 = graphics;
        ff2 = this;
        if (ff2.L != null && ff2.L.length() > 0) {
            ff2.v.c(false);
            ff2.v.a(graphics2, ff2.L, n11 + 2, n10 - ff2.v.a() - 4, 0);
            ff2.v.c(false);
        }
        n10 = n7;
        n11 = n9;
        graphics2 = graphics;
        ff2 = this;
        if ((!ff2.S || ff2.W <= ff2.V) && ff2.m()) {
            n6 = n11 + 4 + ff2.E;
            n5 = n10 + (ff2.d.d - ff2.v.a() >> 1);
            n8 = ff2.e() - (ff2.Y != null ? 20 : 0);
            if (ff2.g) {
                if (ff2.F < 6) {
                    graphics2.setColor(0xFF0000);
                    graphics2.drawLine(n6 += ff2.G, n5, n6, n5 + ff2.v.a() - 2);
                }
                if (ff2.x > 0) {
                    n4 = ff2.v.a(ff2.z) + 6;
                    n9 = ff2.v.a() + 6;
                    n7 = n11 + n8 - n4;
                    n6 = n10 - n9;
                    cw.b(graphics2, ff2.O, n7, n6, n4, n9 - 2);
                    graphics2.setColor(ff2.O);
                    graphics2.fillRect(n7 + 1, n6 + 1, n4 - 2, n9 - 4);
                    graphics2.setColor(0);
                    cw.b(graphics2, 0, n11 + n8 - n4, n6, n4, n9 - 2);
                    graphics2.setColor(ff2.N);
                    n10 = ff2.v.a(ff2.z.substring(0, ff2.s));
                    graphics2.fillRect(n7 + 3 + n10, n6 + 2, ff2.v.a(ff2.z.charAt(ff2.s)) - 1, n9 - 6);
                    ff2.v.a(graphics2, ff2.z, n7 + 3, n6 + 2, 0);
                } else if (ff2.y > 0) {
                    String string = ff2.A;
                    n9 = ff2.v.a(string) + 6;
                    n7 = ff2.v.a() + 6;
                    n6 = n11 + n8 - n9;
                    cw.b(graphics2, ff2.O, n6, n10 -= n7, n9, n7 - 2);
                    graphics2.setColor(ff2.O);
                    graphics2.fillRect(n6 + 1, n10 + 1, n9 - 2, n7 - 4);
                    cw.b(graphics2, 0, n11 + n8 - n9, n10, n9, n7 - 2);
                    ff2.v.a(graphics2, string, n6 + 3, n10 + 2, 0);
                } else if (ff2.ab) {
                    ff ff3 = ff2;
                    String string = String.valueOf(ff3.M - ff3.t.length());
                    int n12 = ff2.v.a(string);
                    n7 = ff2.v.a();
                    n6 = n11 + n8 - n12;
                    ff2.v.a(graphics2, string, n6, n10 -= n7, 0);
                }
            }
        }
        if (this.Y != null) {
            this.Y.a(graphics, n2, n3);
        }
    }

    public final void j(int n2) {
        this.I = n2;
        if (n2 == 4) {
            this.i(3);
            this.y = 0;
        } else if (n2 == 2) {
            this.i(1);
            this.y = 0;
        }
        if (this.H == 1) {
            this.i(0);
            this.P = true;
            this.y = 0;
        }
    }

    public final void k(int n2) {
        this.H = n2;
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -8882: {
                ag.b().e(241224);
                return;
            }
            case -8881: {
                if (this.Q == null) break;
                this.b(this.Q.t());
                ag.b().e(241224);
                return;
            }
            case -3: {
                if (this.Q == null) {
                    this.Q = new hp(1);
                    this.Q.a(this);
                }
                this.Y.d(false);
                ag.b().a(this.Q, false);
            }
        }
    }
}

