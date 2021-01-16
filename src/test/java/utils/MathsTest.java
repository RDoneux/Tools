package utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MathsTest {

    @Test
    public void shouldGetLowestInt(){
        assertThat(Maths.getLow(3,6)).isEqualTo(3);
        assertThat(Maths.getLow(9,2)).isEqualTo(2);
    }

    @Test
    public void shouldGetHighestInt(){
        assertThat(Maths.getHigh(3,6)).isEqualTo(6);
        assertThat(Maths.getHigh(9,2)).isEqualTo(9);
    }
}
