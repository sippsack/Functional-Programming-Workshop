package fpworkshop.datastructures;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Objects;

import static fpworkshop.datastructures.ListShould.List.*;
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
