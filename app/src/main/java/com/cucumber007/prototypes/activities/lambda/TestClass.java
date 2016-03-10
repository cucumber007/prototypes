package com.cucumber007.prototypes.activities.lambda;

public class TestClass {

    public static void method(PseudoLambda pseudoLambda) {
        pseudoLambda.test();
    }

    interface PseudoLambda {
        void test();
    }

}
