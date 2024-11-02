package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import core.CommandLineInterface;
import core.MyFoodoraSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CommandLineInterfaceTest {

    @Test
    void testStart() {
        // Setup
        MyFoodoraSystem system = new MyFoodoraSystem();
        CommandLineInterface cli = new CommandLineInterface(system);
        
        // Mock input
        String simulatedUserInput = "setup 4 4 4\nlogin ceo 123\nlogout\nexit\n";
        ByteArrayInputStream userInput = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(userInput);
        
        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
        // Run the CLI
        cli.start();
        
        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Login successful."));
        assertTrue(output.contains("Logout successful."));
    }
}
