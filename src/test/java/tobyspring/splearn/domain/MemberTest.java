package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    Member member; // 이 멤버 변수는 테스트 메소드 실행마다 매번 초기화 !
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        member = Member.create(new MemberCreateRequest("tobay@abc.com","Toby","secret"), passwordEncoder);

    }


    @Test
    void createMember(){
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }



    @Test
    void activate(){
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail(){
        member.activate();
        assertThatThrownBy(() ->{
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate(){
        member.activate();
        member.deActivate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deActivateFail(){
        assertThatThrownBy(() ->{
            member.deActivate();
        }).isInstanceOf(IllegalStateException.class);
        member.activate();
        member.deActivate();

        assertThatThrownBy(() ->{
            member.deActivate();
        }).isInstanceOf(IllegalStateException.class);
    }



    @Test
    void verifyPassword(){
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("secret1", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname(){
        assertThat(member.getNickname()).isEqualTo("Toby");
        member.changeNickname("charlie");
        assertThat(member.getNickname()).isEqualTo("charlie");
    }

    @Test
    void changePassword(){
        member.changePassword("verySecret", passwordEncoder);
        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue();
    }

    @Test
    void shouldBeActive(){
        assertThat(member.isActive()).isFalse();
        member.activate();
        assertThat(member.isActive()).isTrue();
        member.deActivate();
        assertThat(member.isActive()).isFalse();

    }

    @Test
    void inValidEmail(){
        assertThatThrownBy(() ->{
            Member.create(new MemberCreateRequest("ionavlindEmailkk", "Toby", "secret"), passwordEncoder);
        }).isInstanceOf(IllegalArgumentException.class);

        Member.create(new MemberCreateRequest("abc@abc.com", "Toby", "secret"), passwordEncoder);

    }
}