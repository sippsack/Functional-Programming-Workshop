package fpworkshop;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExcercisesPurittyAndReferntialTransparancy {

    // Exercises
    // Are the following expressions pure?
    // If yes, please describe why.
    // If no, extract a functional core.

    @Test
    public void addition() {
        var one = 1;
        var two = 2;

        var three = add(one, two);

        assertThat(three, equalTo(3));
    }

    private int add(int a, int b) {
        return a + b;
    }
}
