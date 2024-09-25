package com.example.cividleutils.lib.utils;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon.CONSOLE_SCREEN_BUFFER_INFO;
import com.sun.jna.win32.W32APIOptions;

public class WinApisPriv {

    // Kernel32 interface for Windows API calls
    public interface MyKernel32 extends Kernel32 {
	MyKernel32 INSTANCE = Native.load("kernel32", MyKernel32.class, W32APIOptions.DEFAULT_OPTIONS);

	// Get the console screen buffer info
	boolean GetConsoleScreenBufferInfo(WinNT.HANDLE hConsoleOutput,
		CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

	// Set the console window info
	boolean SetConsoleWindowInfo(WinNT.HANDLE hConsoleOutput, boolean absolute, SMALL_RECT rect);

	boolean SetConsoleCursorPosition(WinNT.HANDLE hConsoleOutput, WinApisPriv.COORD coord);

    }

    // Coordinate structure for cursor position
    public static class COORD extends Structure {
	public short X;
	public short Y;

	@Override
	protected java.util.List<String> getFieldOrder() {
	    return java.util.Arrays.asList("X", "Y");
	}

	public COORD() {
	    this((short) 0, (short) 0);
	}

	public COORD(short x, short y) {
	    this.X = x;
	    this.Y = y;
	}
    }

    // Structure to store console screen buffer information
    public static class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
	public COORD dwSize;
	public COORD dwCursorPosition;
	public short wAttributes;
	public SMALL_RECT srWindow;
	public COORD dwMaximumWindowSize;

	@Override
	protected java.util.List<String> getFieldOrder() {
	    return java.util.Arrays.asList("dwSize", "dwCursorPosition", "wAttributes", "srWindow",
		    "dwMaximumWindowSize");
	}
    }

    // Small rectangle structure used in console screen buffer info
    public static class SMALL_RECT extends Structure {
	public short Left;
	public short Top;
	public short Right;
	public short Bottom;

	@Override
	protected java.util.List<String> getFieldOrder() {
	    return java.util.Arrays.asList("Left", "Top", "Right", "Bottom");
	}
    }

}
