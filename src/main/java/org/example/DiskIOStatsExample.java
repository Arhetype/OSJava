package org.example;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class DiskIOStatsExample {

    public interface CLibrary extends Library {
        CLibrary INSTANCE = Native.load(Platform.C_LIBRARY_NAME, CLibrary.class);

        int getpid();
        int read(int fd, byte[] buf, int count);
        int write(int fd, byte[] buf, int count);
        int open(String path, int flags);
        int close(int fd);
        int ioctl(int fd, long request, DiskIOStats stats);
    }

    // Structure for disk I/O statistics
    public static class DiskIOStats extends Structure {
        public int rchar;
        public int wchar;
        public int syscr;
        public int syscw;
        public int read_bytes;
        public int write_bytes;
        public int cancelled_write_bytes;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("rchar", "wchar", "syscr", "syscw", "read_bytes", "write_bytes", "cancelled_write_bytes");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int pid = CLibrary.INSTANCE.getpid();

        int fd = CLibrary.INSTANCE.open("/proc/" + pid + "/io", 0);

        DiskIOStats stats = new DiskIOStats();

        while (true) {
            CLibrary.INSTANCE.ioctl(fd, 0x80081272L, stats);

            double avgSpeed = (stats.read_bytes + stats.write_bytes) / 60.0;

            System.out.println("Average disk access speed for the last minute: " + avgSpeed + " bytes/s");

            // Sleep for a minute
            Thread.sleep(60000);
        }
    }
}