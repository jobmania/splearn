package tobyspring.splearn.application.provided;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import tobyspring.splearn.application.MemberService;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;
import tobyspring.splearn.domain.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {

    @Test
    void registerTestStub() {
        MemberRegister register = new MemberService(
                new MemberRepositoryStub(),
                new EmailSenderStub(),
                MemberFixture.createPasswordEncoder()
        );

        Member memberRegister = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(memberRegister.getId()).isNotNull();
        assertThat(memberRegister.getStatus()).isEqualTo(MemberStatus.PENDING);

    }


    @Test
    void registerTestMock() {


        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(),
                emailSenderMock,
                MemberFixture.createPasswordEncoder()
        );

        Member memberRegister = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(memberRegister.getId()).isNotNull();
        assertThat(memberRegister.getStatus()).isEqualTo(MemberStatus.PENDING);

//        assertThat(emailSenderMock.tos.size()).isEqualTo(1L);
//        assertThat(emailSenderMock.tos.getFirst()).isEqualTo(memberRegister.getEmail());

        Mockito.verify(emailSenderMock).send(eq(memberRegister.getEmail()) ,any(),any());
    }


    static class MemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member) {

            ReflectionTestUtils.setField(member,"id",1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

    }

    static class EmailSenderStub implements tobyspring.splearn.application.required.EmailSender{

        @Override
        public void send(Email email, String subject, String body) {
        }
    }

    static class EmailSenderMock implements tobyspring.splearn.application.required.EmailSender{

        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }

}