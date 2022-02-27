package com.vam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vam.model.BoardVO;
import com.vam.model.Criteria;
import com.vam.model.PageMakerDTO;
import com.vam.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	 private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	 
	 @Autowired
	 private BoardService bservice;

	 	/* 게시글 목록 화면(페이징 적용) */
	    @GetMapping("/list")
	    // => @RequestMapping(value="list", method=RequestMethod.GET)
	    public void boardListGET(Model model, Criteria cri) {
	    	model.addAttribute("list", bservice.getListPaging(cri));
	        log.info("게시판 목록 페이지 진입");
	        
	        int total = bservice.getTotal();
	        PageMakerDTO pageMake = new PageMakerDTO(cri, total);
	        model.addAttribute("pageMaker", pageMake);
	    }
	    
	    
	    /* 게시글 쓰기 화면 */
	    @GetMapping("/enroll")
	    // => @RequestMapping(value="enroll", method=RequestMethod.GET)
	    public void boardEnrollGET() {
	        log.info("게시판 등록 페이지 진입");
	    }
	    
	    /* 게시글 쓰기 처리 */
	    @PostMapping("/enroll")
	    public String boardEnrollPOST(BoardVO board, RedirectAttributes rttr) {
	        
	        log.info("BoardVO : " + board);	//화면에서 넘어온 글 데이터 확인
	        bservice.enroll(board);			//글쓰기 처리
	        rttr.addFlashAttribute("result", "enroll success");	//처리결과 전달
	        return "redirect:/board/list";	//글쓰기 처리후 글목록 화면으로 전환
	    }
	    
	    /* 게시판 상세 조회 */
	    @GetMapping("/get")
	    public void boardGetPageGET(int bno, Model model, Criteria cri) {
	        model.addAttribute("pageInfo", bservice.getPage(bno));
	        model.addAttribute("cri", cri);
	    }
	    
	    /* 수정 페이지 이동 */
	    @GetMapping("/modify")
	    public void boardModifyGET(int bno, Model model, Criteria cri) {
	        
	        model.addAttribute("pageInfo", bservice.getPage(bno));
	        model.addAttribute("cri", cri);
	        
	    }
	    
	    /* 글 수정 프로세스 처리 */
	    @PostMapping("/modify")
	    public String boardModifyPOST(BoardVO board, RedirectAttributes rttr) {
	        bservice.modify(board);								//글 수정 처리
	        rttr.addFlashAttribute("result", "modify success");	//처리결과 전달
	        return "redirect:/board/list";	//수정후에 리스트 화면으로 전환
	        
	    }
	    
	    /* 글 삭제 프로세스 처리 */
	    @PostMapping("/delete")
	    public String boardDeletePOST(int bno, RedirectAttributes rttr) {
	        bservice.delete(bno);
	        rttr.addFlashAttribute("result", "delete success");
	        return "redirect:/board/list";
	    }
	 
}
