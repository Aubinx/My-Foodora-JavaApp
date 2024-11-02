package main;
import core.*;


public class Main {
    public static void main(String[] args) {
        MyFoodoraSystem system = new MyFoodoraSystem();
        CommandLineInterface cli = new CommandLineInterface(system);
        cli.start();
    }
}

