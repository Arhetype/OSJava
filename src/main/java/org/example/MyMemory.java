package org.example;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

interface CLibrary extends Library {
    int PTRACE_ATTACH = 16;
    int PTRACE_DETACH = 17;
    int PTRACE_PEEKDATA = 2;
    int PTRACE_POKEDATA = 5;

    int ptrace(int request, int pid, Pointer addr, int data);
}

public class MyMemory {
    private final Pointer address;
    private final int pid;
    private final CLibrary libc;

    public MyMemory(int pid, String adrStr) {
        this.pid = pid;
        this.address = new Pointer(Long.parseLong(adrStr, 16));
        this.libc = (CLibrary) Native.loadLibrary("c", CLibrary.class);
    }

    public int read() {
        libc.ptrace(CLibrary.PTRACE_ATTACH, pid, null, 0);
        int addressValue = libc.ptrace(CLibrary.PTRACE_PEEKDATA, pid, address, 0);
        libc.ptrace(CLibrary.PTRACE_DETACH, pid, null, 0);
        return addressValue;
    }

    public void write(int value) {
        libc.ptrace(CLibrary.PTRACE_ATTACH, pid, null, 0);
        libc.ptrace(CLibrary.PTRACE_POKEDATA, pid, address, value);
        libc.ptrace(CLibrary.PTRACE_DETACH, pid, null, 0);
    }

    public int getPid() {
        return pid;
    }

    public String getAddressStr() {
        return address.toString();
    }
}