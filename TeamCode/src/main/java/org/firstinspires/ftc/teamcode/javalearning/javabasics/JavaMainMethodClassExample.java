package org.firstinspires.ftc.teamcode.javalearning.javabasics;

/*In every basic Java program, if you want run the code that you've created, you need to put it in what's called the
* "main method". In Java, the main method is the container in which you put all the code that you want to run when you start your program.
* I'm not saying you have to put ALL the code in your entire project in this method, just the entry-level code that you want to start the program off
* with. Once you put code in the main method, when you press the "play" button, Java will automatically compile and run your code inside the main method.
*
* Here is a basic example of a main method with code in it to run: */

import android.util.Log;

public class JavaMainMethodClassExample {

    public static void main(String[] args) {
        Log.i(JavaMainMethodClassExample.class.getSimpleName(), "This message will print to the Android Studio LogCat.");
    }
}

/*See that 'public static void main(String[] args) {}' thing? That is the main method, and inside of it, there is code that will print a simple
* message when ran. This is the basis of starting any program, and this main method structure is what you're going to see and be using in a lot of basic
* java programs. Now in the context of Android Studio robot programming, that's a little bit different because the driver hub doesn't really use
* a main method to initialize and run code, it uses the FTC SDK's built-in compiler initialization methods to do it, but that is something we will cover
* in a later chapter. For now, we will stick to using main methods to start off our programs.*/
