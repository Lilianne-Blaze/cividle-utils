package com.example.cividleutils.lib.utils;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WinConsoleUtils {

    public static void clearScreenCompletely() {
	try {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	} catch (Exception e) {
	}
    }

    public static void scrollToBufferTop() {
	try {

	    WinNT.HANDLE consoleHandle = Kernel32.INSTANCE.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
	    Wincon.CONSOLE_SCREEN_BUFFER_INFO bufferInfo = new Wincon.CONSOLE_SCREEN_BUFFER_INFO();
	    boolean success = Kernel32.INSTANCE.GetConsoleScreenBufferInfo(consoleHandle, bufferInfo);

	    // Get the size of the current console window
	    short windowHeight = (short) (bufferInfo.srWindow.Bottom - bufferInfo.srWindow.Top + 1);

	    // Scroll to the top by setting srWindow.Top to 0 and adjusting Bottom
	    Wincon.SMALL_RECT windowRect = bufferInfo.srWindow;
	    windowRect.Top = 0; // Scroll to the top
	    windowRect.Bottom = (short) (windowHeight - 1); // Adjust Bottom based on window height

	    // Apply the new window position to scroll to the top of the buffer
	    success = WinApisPriv.MyKernel32.INSTANCE.SetConsoleWindowInfo(consoleHandle, true, windowRect);

	    WinApisPriv.COORD coord = new WinApisPriv.COORD();
	    WinApisPriv.MyKernel32.INSTANCE.SetConsoleCursorPosition(consoleHandle, coord);

//	    System.out.print("\033[1;1H");

	} catch (Exception e) {
	}

    }

}
