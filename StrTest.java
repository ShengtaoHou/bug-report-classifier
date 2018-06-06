/**
 * Created by 29952 on 2018/4/30.
 */
public class StrTest {
    public static void main(String[] args) throws Exception {
        String str="i installed windows XP sp2 yesterday. but i found some problem in my computer.\n" +
                "whenever i scrolled textpad and push F3 button in Eclipse, it was gone.\n" +
                "\n" +
                "this is error message when eclipse is gone\n" +
                "----------------------------------\n" +
                "Eclipse\n" +
                "JVM terminated. Exit code=1\n" +
                "C:\\j2sdk1.4.2_04\\bin\\javaw.exe\n" +
                "-cp C:\\eclipse\\startup.jar org.eclipse.core.launher.Main\n" +
                "-os win32\n" +
                "-ws win32\n" +
                "-arch x86\n" +
                "-showsplash C:\\eclipse\\eclipse.exe -showsplash 600\n" +
                "-exitdata C:\\eclipse\\eclipse.exe -exitdata 804_84\n" +
                "-VM C:\\j2sdk1.4.2_04\\bin\\javaw.exe\n" +
                "-vmargs\n" +
                "-cp C:\\eclipse\\startup.jar org.eclipse.core.launcher.Main\n" +
                "\n" +
                "\n" +
                "also i found a log file in my desktop.\n" +
                "----------------------------------\n" +
                "Heap at VM Abort:\n" +
                "Heap\n" +
                " def new generation   total 576K, used 400K [0x10020000, 0x100c0000, \n" +
                "0x10780000)\n" +
                "  eden space 512K,  75% used [0x10020000, 0x100812e0, 0x100a0000)\n" +
                "  from space 64K,  17% used [0x100b0000, 0x100b2e08, 0x100c0000)\n" +
                "  to   space 64K,   0% used [0x100a0000, 0x100a0000, 0x100b0000)\n" +
                " tenured generation   total 1408K, used 703K [0x10780000, 0x108e0000, \n" +
                "0x16020000)\n" +
                "   the space 1408K,  49% used [0x10780000, 0x1082ff60, 0x10830000, 0x108e0000)\n" +
                " compacting perm gen  total 5888K, used 5814K [0x16020000, 0x165e0000, \n" +
                "0x1a020000)\n" +
                "   the space 5888K,  98% used [0x16020000, 0x165cdab8, 0x165cdc00, 0x165e0000)\n" +
                "\n" +
                "Local Time = Wed Sep 01 20:12:38 2004\n" +
                "Elapsed Time = 485\n" +
                "#\n" +
                "# The exception above was detected in native code outside the VM\n" +
                "#\n" +
                "# Java VM: Java HotSpot(TM) Client VM (1.4.2_04-b05 mixed mode)";
        str=str.replaceAll("[\r\n]+", " ");
        str=str.replaceAll("[^a-zA-Z\\s]", " ");
        str=str.replaceAll("( )+", " ");
        System.out.println(str);

    }
}
