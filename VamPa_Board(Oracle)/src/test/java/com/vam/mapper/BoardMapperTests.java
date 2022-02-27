package com.vam.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vam.model.BoardVO;
import com.vam.model.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {
 
     private static final Logger log = LoggerFactory.getLogger(BoardMapperTests.class);
     
     @Autowired
     private BoardMapper mapper;
 
     @Test
     public void testEnroll() {
         
    	 /* 1. 테스트를 위한 데이터 준비 */
         BoardVO vo = new BoardVO();
         
         vo.setTitle("mapper test");
         vo.setContent("mapper test");
         vo.setWriter("mapper test");
         
         /* 2. 테스트 실행  */
         mapper.enroll(vo);
         
         /* 3. 테스트 aeert함수 결과값 뽑기 */
         
     }
     
     
     /* 게시판 목록 테스트 */
     @Test
     public void testGetList() {
         List list = mapper.getList();	//mapper로 글 리스트 가져오기.
         
        
        /* foreach문(향상된 for문) */
         for(Object a : list) {
			 log.info("" + a);
         }
        
     }
     
     
     /* 게시판 상세 조회 */
     @Test
    public void testGetPage() {
        
        /* 실제 존재하는 페이지 */
        int bno = 8;
        
        log.info("" + mapper.getPage(bno));
        
    }
     
     /* 게시판 수정 */
     @Test
     public void testModify() {
         BoardVO board = new BoardVO();
         board.setBno(8);
         board.setTitle("수정 제목");
         board.setContent("수정 내용");
         
         int result = mapper.modify(board);
         log.info("result : " +result);
         
     }
     
     /* 게시판 삭제 */
     @Test
     public void testDelete() {
         int result = mapper.delete(23);
         log.info("result : " + result);
         
     }
     
     
     /* 게시판 목록(페이징 적용)테스트 */
	  @Test
	  public void testGetListPaging() {
	      Criteria cri = new Criteria();
	      cri.setPageNum(2);
	      List list = mapper.getListPaging(cri);
	      list.forEach(board -> log.info("" + board));
	  }
     
 
}