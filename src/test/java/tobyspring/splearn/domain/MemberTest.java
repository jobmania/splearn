package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void createMember(){
        var member = new Member("abc@splearn.app", "abc", "secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }


    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "abc","abc")
                ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate(){
        var member = new Member("toby", "abc", "secret");
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail(){
        var member = new Member("toby", "abc", "secret");
        member.activate();
        assertThatThrownBy(() ->{
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate(){
        var member = new Member("toby", "abc", "secret");
        member.activate();

        member.deActivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deActivateFail(){
        var member = new Member("toby", "abc", "secret");

        assertThatThrownBy(() ->{
            member.deActivate();
        }).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deActivate();


        assertThatThrownBy(() ->{
            member.deActivate();
        }).isInstanceOf(IllegalStateException.class);

    }


}