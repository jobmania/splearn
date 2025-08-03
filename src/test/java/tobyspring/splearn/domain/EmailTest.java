package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void eqaulity(){
        var email1 = new Email("abc@abc.com");
        var email2 = new Email("abc@abc.com");

        assertThat(email1).isEqualTo(email2);
    }

}