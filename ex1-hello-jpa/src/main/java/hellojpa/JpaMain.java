package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //실제론 스프링이 나머지 코드를 다 실행해주어 persist만 하면됨
            //엔티티 대상으로 쿼리를 날리게 코드 작성(DB, 방언에 종속적이지 않음)

            Member member = em.find(Member.class, 150L);    //find로 찾은 것은 영속 상태이다
            member.setName("ZZZZ");
            
            //em.clear() 영속성 컨텍스트 완전히 초기화
            //em.persist(member1); //1차 캐시에 저장(버퍼링)
            //em.detach(member); 분리, 준영속 상태로 jpa가 관리하지 않는다(커밋해도 아무일 X) remove: 삭제
            //객체를 컬렉션처럼 다루기에 데이터를 변경할때 persist할 필요가 없다....
            tx.commit();    //이 시점에 진짜 DB에 쿼리가 날라가고 데이터 저장
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}