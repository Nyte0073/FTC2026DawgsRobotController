package org.firstinspires.ftc.teamcode.javalearning.variables.nonprimitivevariables;

/*Integers are a type of variable in Java that hold whole numbers as their value. This is important because these variables cannot hold numbers that
* have decimals, because if you try to initialize an integer variable with a decimal number as its value, Java will give a compiler error because
* an integer is not the same thing as a decimal and cannot be casted up or down to a decimal.
*
* Here is an example of me creating an integer variable named "myInteger", with me assigning it the value 27.*/
public class JavaInteger {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        int myInteger = 27;
    }
}

 /*In the example above, I have created an integer variable. You can see that I put the keyword 'int' at the front of the declaration
 * to specify that I am creating an integer variable. This is the same structure with every variable in Java, before you give the variable a name,
 * you must use a specific keyword to tell Java what type of variable you are making, so that it knows what values can be given to that variable in order
 * for it to compile and run properly.*/

/*Now that you've seen how an integer variable is created in Java, let's talk about some of the ways Integers can be used in Java, both in general and
* in terms of our robotics course.*/

/*1. Integers can be used with Java Operators to perform mathematical calculations in your code.
* The operators that can be used with integers or any other number variable are: '=' which is used for assigning variables values, '+'
* which can be used to add two number variables together, '-' which can be used to subtract to number variables, '/' which is used to divide two number
* variables (you have to be careful about using this with integers, because integers don't have decimals, so when the result of you dividing two integers
* results in a number less than 0, Java will automatically truncate the value of that result to the closest integer value, which is 0.), '%' which can be
* used to find the resulting remainder of dividing two number variables, and '<', '>', '<=', '==' and '>=' signs that can be used to compare the values of
* two number variables.
*
I will show two examples for every operator, one for in-general Java use and one related to robotics usage, to demonstrate how these operators are used in real
* world situations.
* */

/*1. '==' SIGN
*
* This is called the equal sign and in Java, this sign is used to compare the values of two variables. Be mindful though that this sign is typically
* used for comparing the values of any variable types other than Strings (variables that hold text as their value), because while you can use this
* sign to compare two Strings, we typically use the equals() method to compare Strings instead because they are text, so equals() can provide a more
* detailed comparison other than just comparing the Strings' LITERAL text value.
*
* Here is an example of me using the equal sign to compare the number values of two integers:*/

class IntegerComparison {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        int firstInteger = 10;
        int secondInteger = 9;
        System.out.println(firstInteger == secondInteger); /*This outputs false, because we are now comparing values, not just stating them.*/
    }
}

/*So you can see in the example above that I have created two integer variables, one with a value of 10 and one with the value of 9.
* The reason that the output statement would return 'false' instead of a number when having the result of the comparison outputted to the console
* is because when you compare two things, you're basically stating that the variables are a certain way in proportionality to each other.
*
* For example, if I said "three is less than two", that's a statement, and that is false, because we know that 3 is bigger than 2, not less.
* That's exactly what's happening here. When you compared the values of the two integers using the equal sign, you're basically stating to Java
* "9 is equal to 10". But that's obviously not true, because 9 is less than 10, not equal to it, which is why Java outputs/returns 'false'.
*
*
* 2. '+' SIGN
*
* This is called the addition sign, and in Java, this sign that be used to either add numbers together or combine Strings (which we will talk about later on).
* The way you would use this sign in Java is exactly the way you would use this sign in Math in real life. To add number variables together and also t*/