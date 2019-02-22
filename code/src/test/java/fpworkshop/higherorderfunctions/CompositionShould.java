package fpworkshop.higherorderfunctions;

import org.junit.jupiter.api.Test;

import static io.vavr.API.Function;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositionShould {

    @Test
    void compose_two_functions() {
        var f = Function((Integer a) -> a * 2);
        var g = Function((Integer b) -> b + 2);
        var h = Function((Integer c) -> f.apply(g.apply(c)));
        var i = f.compose(g);

        assertEquals(6, h.apply(1).intValue());
        assertEquals(6, i.apply(1).intValue());
        assertEquals(h.apply(1).intValue(), i.apply(1).intValue());
    }
}
