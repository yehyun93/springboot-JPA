package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    //transaction이 없으면 entityManage error
    //test에 들어가있으면 데이터를 넣은 다음 롤백 해버림
    @Transactional
    @Rollback(false) //rollback 강제로 안하게 하기 (롤백되면 insert 쿼리도 로그에 안찍힘)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);
        
        //then
        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
        assertEquals(findMember, member);

        //영속성 컨테스트에서 엔티티를 관리하고 있기 때문에 같음
        System.out.println("findMember = " + findMember);
        System.out.println("member = " + member);
    }
}