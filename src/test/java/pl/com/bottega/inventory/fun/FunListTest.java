package pl.com.bottega.inventory.fun;

import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.in;

public class FunListTest {

    @Test
    public void addElementEmptyList() {
        FunList list = FunList.empty();
        FunList newList = list.add("element");
        assertThat(newList.size()).isEqualTo(1);
    }

    @Test
    public void addElementsEmptyList() {
        FunList list = FunList.empty();
        FunList newList = list.add(1).add(2).add(3);
        assertThat(newList.size()).isEqualTo(3);
    }

    @Test
    public void removeElementEmptyList() {
        FunList list = FunList.empty();
        FunList newList = list.add(1).add(2).add(3).remove(2);
        assertThat(newList.size()).isEqualTo(2);
    }

    @Test
    public void canHoldNullValues() {
        FunList<Integer> newList = FunList.empty(Integer.class).add(1).add(2).add(null).add(3).remove(null);
        assertThat(newList.size()).isEqualTo(3);
    }

    @Test
    public void isGeneric() {
        FunList<Integer> integerFunList = FunList.empty(Integer.class).add(1);
    }

    @Test
    public void iteratesOverEachElement() {
        FunList<String> stringFunList = FunList.empty(String.class).add("1").add("2").add("3");

        //obywatel pierwszej kategorii
        StringBuilder stringBuilder = new StringBuilder();
        stringFunList.each(stringBuilder::append);
        assertThat(stringBuilder.toString()).isEqualTo("123");
    }

    @Test
    public void mapsLists() {
        FunList<String> smallString = FunList.empty(String.class).add("a").add("b").add("c");
        FunList<String> upperString = smallString.map(String::toUpperCase);
        assertThat(upperString).isEqualTo(FunList.empty(String.class).add("A").add("B").add("C"));
    }

    @Test
    public void foldsLeft() {
        FunList<String> smallString = FunList.empty(String.class).add("a").add("b").add("c");
        String fold = smallString.foldLeft("", (acc, string) -> acc + string);
        assertThat(fold).isEqualTo("abc");
    }

    @Test
    public void filtersElements() {
        FunList<Integer> ints = FunList.empty(Integer.class).add(1).add(2).add(3).add(5);
        FunList<Integer> odds = ints.filter(n -> n % 2 == 1);
        assertThat(odds).isEqualTo(FunList.empty(Integer.class).add(1).add(3).add(5));
    }
}