package fpworkshop.datastructures;

import io.vavr.Function1;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

import static fpworkshop.datastructures.ListShould.List.*;
import static io.vavr.API.TODO;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class ListShould {


    @Test
    void madeOfConsAndOneNil(){
        var sentence = cons("Hello", cons("JavaLand", nil()));

        assertThat(sentence.head(), is(equalTo("Hello")));
        assertThat(sentence.tail().head(), is(equalTo("JavaLand")));
        assertThat(sentence.tail().tail(), is(equalTo(nil())));
    }

    @Test
    void beCreatedWithASmartConstructor(){
        var list = listOf("Hello", "JavaLand");

        var expected = cons("Hello", cons("JavaLand", nil()));

        assertThat(list, is(equalTo(expected)));
    }

    // Exercise 1
    // head() throws an exception when it is called on an empty list.
    // This is a side effect.
    // We need a type to represent an optional value.
    // That is what vavrs Option is for.
    // Implement a function headOption() which returns an Option with the head value if this is Cons.
    // Otherwise it returns an empty Option.

    @Test
    void beSomeForHeadOfNonEmptyList(){
        var list = listOf("Hello", "JavaLand");

        var headOption = list.headOption();

        assertThat(headOption.isDefined(), is(true));
        assertThat(headOption.get(), is("Hello"));
    }

    @Test
    void beNoneForHeadOfEmptyList(){
        var list = listOf();

        var headOption = list.headOption();

        assertThat(headOption.isDefined(), is(false));
        assertThat(headOption, is(Option.none()));
    }

    // Exercise 2
    // Mapping a list of values to another list with other values is very common.
    // Please implement the corresponding operation.
    // Remember: Go by the types.
    @Test
    void beMappable(){
        var list = listOf('a', 'b');

        var mappedList = list.map(character -> (int) character);

        assertThat(mappedList, equalTo(listOf(97, 98)));
    }

    @Test
    void emptyListToEmptyList(){
        var list = List.<Character>nil();

        List<Integer> mappedList = list.map(character -> (int) character);

        assertThat(mappedList, equalTo(List.<Integer>nil()));
    }

    // Exercise 3
    // Reimplement the map operator in the list interface



    interface List<E> {

        static <E> List<E> listOf(E... elements){
            var list = List.<E>nil();
            for(var i = elements.length-1; i >= 0; i--){
                list = cons(elements[i], list);
            }
            return list;
        }

        static <E> List<E> cons(E element, List<E> tail){
            return new Cons<>(element, tail);
        }

        static <E> List<E> nil(){
            return Nil.instance();
        }

        default List<E> tail(){
            if(this instanceof Cons){
                return ((Cons<E>) this).tail;
            }
            return Nil.instance();
        }

        default E head(){
            if(this instanceof Cons){
                return ((Cons<E>) this).head;
            }
            throw new NoSuchElementException("head of empty list");
        }

        default Option<E> headOption(){
            if(this instanceof Cons){
                return Option.of(((Cons<E>) this).head);
            }
            return Option.none();
        }

        default <T> List<T> map(Function1<? super E, ? extends T> mapper){
            return TODO("Implement map here");
        };
    }

    final static class Nil<A> implements List<A> {
        private static final Nil<?> instance = new Nil<>();

        private Nil(){}

        @SuppressWarnings("unchecked")
        public static <B> List<B> instance(){
            return (List<B>) instance;
        }

        @Override
        public String toString() {
            return "Nil{}";
        }
    }

    final static class Cons<A> implements List<A> {
        public final A head;
        public final List<A> tail;

        public Cons(A head, List<A> tail){
            this.head = head;
            this.tail = tail;
        }

        @Override
        public String toString() {
            return "Cons{" +
                    "head=" + head +
                    ", tail=" + tail +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cons<?> cons = (Cons<?>) o;
            return Objects.equals(head, cons.head) &&
                    Objects.equals(tail, cons.tail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(head, tail);
        }
    }
}
