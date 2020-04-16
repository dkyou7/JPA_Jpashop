package jpabook.jpashop3;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback
    // 맴버에 저장된 값은 한 트랜잭션 내부에서 아이디값, 유저 이름값이 같다.
    public void testMember(){
        Member member = new Member();
        member.setUsername("memeberA");
        // 객체 저장 후 저장된 아이디 반환!
        Long savedId = memberRepository.save(member);

        // 다시 찾아와!
        Member findMember = memberRepository.find(savedId);

        // 저장된거 아이디가 디비 거쳐서 나온 아이디와 같을 것인가?
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        // 객체도 같을건인가? 한 트랜잭션 내부에서 이루어지므로 같은 객체, 같은 해시값 가진다!
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}