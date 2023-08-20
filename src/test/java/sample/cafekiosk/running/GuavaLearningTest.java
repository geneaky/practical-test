package sample.cafekiosk.running;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class GuavaLearningTest {

    @Test
    void test() throws Exception {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        List<List<Integer>> partition = Lists.partition(integers, 3);

        assertThat(partition).hasSize(2)
            .isEqualTo(List.of(
                List.of(1, 2, 3), List.of(4, 5, 6)
            ));
    }

    @Test
    void test1() throws Exception {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        List<List<Integer>> partition = Lists.partition(integers, 4);

        assertThat(partition).hasSize(2)
            .isEqualTo(List.of(
                List.of(1, 2, 3, 4), List.of(5, 6)
            ));
    }

    @Test
    void test3() throws Exception {
        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        Collection<String> strings = multimap.get("커피");

        assertThat(strings).hasSize(3)
            .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));
    }

    @TestFactory
    Collection<DynamicTest> test4() throws Exception {
        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        return List.of(
            DynamicTest.dynamicTest("1개 value 삭제", () -> {
                multimap.remove("커피", "카푸치노");

                Collection<String> strings = multimap.get("커피");
                assertThat(strings).hasSize(2)
                    .isEqualTo(List.of("아메리카노", "카페라떼"));
            }),
            DynamicTest.dynamicTest("1개 key 삭제", () -> {
                multimap.removeAll("커피");

                Collection<String> strings = multimap.get("커피");
                assertThat(strings).isEmpty();
            })
        );
    }
}
