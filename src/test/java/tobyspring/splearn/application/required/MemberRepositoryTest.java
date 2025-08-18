package tobyspring.splearn.application.required;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;

import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest // DATA JPA를 위한 최소한 빈들만 구성
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Autowired
    EntityManager entityManager;

    @Test
    void createMember(){
        Member member = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());
        Assertions.assertThat(member.getId()).isNull();

        memberRepository.save(member);
        Assertions.assertThat(member.getId()).isNotNull();
        entityManager.flush();
    }

    @Test
    void duplicateEmailFail(){
        Member member = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());

        memberRepository.save(member);

        Member member2 = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());

        Assertions.assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}