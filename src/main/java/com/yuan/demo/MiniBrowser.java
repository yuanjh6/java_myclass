package com.yuan.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MiniBrowser{

    /**
     * @param args
     */
    public static void main(String[] args) {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);
        shell.setText("NEW3W");
        Browser browser = new Browser(shell, SWT.NONE);
        
        shell.setLayout(new FillLayout());
        shell.open();
        browser.setUrl("http://list.lufax.com/list/listing");

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
