/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 */
import java.util.Date;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

public final class cw
extends Form
implements CommandListener {
    private static cw a;
    private static boolean b;
    private int c;
    private int d;
    private final long e;
    private Displayable f;
    private Display g;

    static {
        b = true;
    }

    public static void a(boolean bl2, int n2) {
        b = true;
    }

    public static boolean a() {
        return b;
    }

    public static void a(Display object, Displayable displayable) {
        if (a != null) {
            Displayable displayable2 = displayable;
            displayable = object;
            object = a;
            a.f = displayable2;
            object.g = displayable;
            displayable.setCurrent((Displayable)object);
        }
    }

    public final void commandAction(Command object, Displayable displayable) {
        if (object.getLabel().equals("Back")) {
            if (this.f != null && this.g != null) {
                this.g.setCurrent(this.f);
                this.f = null;
                this.g = null;
                return;
            }
        } else if (object.getLabel().equals("Clear")) {
            object = this;
            object.deleteAll();
            object.d = 0;
            object.append("Start at " + new Date(System.currentTimeMillis()).toString());
        }
    }

    public static boolean b() {
        if (a != null) {
            return (cw.a.c & 1) == 1;
        }
        return false;
    }

    public static void a(String object) {
        if (a != null) {
            String string = object;
            object = a;
            if ((((cw)((Object)object)).c & 1) == 1) {
                if (((cw)((Object)object)).d >= 30) {
                    object.delete(0);
                }
                object.append("[" + string + "]");
                System.out.println(string);
                ++((cw)((Object)object)).d;
            }
        }
    }

    public static void a(Throwable object) {
        if (a != null) {
            Throwable throwable = object;
            object = a;
            if ((((cw)((Object)object)).c & 2) == 2) {
                if (((cw)((Object)object)).d >= 30) {
                    object.delete(0);
                }
                throwable.printStackTrace();
                object.append(throwable.toString());
                ++((cw)((Object)object)).d;
            }
        }
    }

    public static boolean c() {
        if (a != null) {
            return (cw.a.c & 4) == 4;
        }
        return false;
    }

    public static void b(String object) {
        if (a != null) {
            String string = object;
            object = a;
            if ((((cw)((Object)object)).c & 4) == 4) {
                if (((cw)((Object)object)).d >= 30) {
                    object.delete(0);
                }
                System.gc();
                Runtime runtime = Runtime.getRuntime();
                long l2 = (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
                string = "[MEM] " + string + " " + l2 + "K/" + ((cw)((Object)object)).e + "K";
                object.append(string);
                System.out.println(string);
            }
        }
    }
}

