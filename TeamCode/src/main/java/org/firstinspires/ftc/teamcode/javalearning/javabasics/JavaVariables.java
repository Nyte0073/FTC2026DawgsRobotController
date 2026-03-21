package org.firstinspires.ftc.teamcode.javalearning.javabasics;

/*In Java, we use things called 'variables' to store certain values OR instances of something. For example, if I wanted to have the value 3 stored in
* Java's memory to be used in later code, I would put in a variable that it stores that value.
*
* Here is an example of that below:*/

class StoringIntegerExample {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        int integer = 3;
    }
}

/*In this example, I have created an integer variable with the value of 3, so whenever I want to use 3 in code I make later on, I can simple
* call the name of this variable to retrieve its value to use. And all Java programs, data memory works exactly that way, where information is
* stored within variables (or class instances, but we will get to that later on).
*
* Variables are useful because they can be used to store values of something that you want to reuse or want to include in a specific part of your program.
* For example, if you created an integer variable, you can set the value of that to be the result of some kind of function that you set up in your code,
* and then you can simply call the name of that variable to call upon whatever value the code you made set to that variable. This is the basis of what
* variables are used for in Java, they are used data transmission and safeguarding for specific purposes around your program.
*
*
*Now that you know a little bit about how variables are used and what their general purpose is, let's talk about how to create a variable.
* The rules we use to create variables are simple. First we type the "variable keyword", which tells Java what kind of variable you're making,
* then we type the name of the variable, which is the name that you will reference when you want to call that variable's value. Then we use the "="
* sign plus whatever value you want to give to the variable to assign a value to the variable.
*
* A complete example is shown here to demonstrate all the steps:*/

class VariableCreationExample {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        int integerVariable = 10;
        System.out.println(integerVariable);

        /*You can see here that the I put the keyword "int" to tell Java that I am creating an Integer variable. And then I named the variable
        * "integerVariable", which is the name I am referencing in the System.out.println() method to be able to output the variable's vaue to the
        * coding editor's console. And then, I put the equal sign and gave the variable a value of 10. Done! And believe it or not, this rule structure
        * is how all variables and objects in Java are declared or created, so once you learn it, it is applicable everywhere.*/
    }
}

