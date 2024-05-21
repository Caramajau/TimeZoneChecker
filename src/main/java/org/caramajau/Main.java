package org.caramajau;

import org.caramajau.view.App;
import org.caramajau.view.IApp;

public class Main {
    public static void main(String[] args) {
        IApp application = new App();
        application.startView(args);
    }
}