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

import com.vam.model.UserDTO;
import com.vam.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {

	 private static final Logger log = LoggerFactory.getLogger(UserController.class);
	 
	 @Autowired
	 private UserService uservice;

	 	/* 유저 목록 화면 */
	    @GetMapping("/list")
	    // => @RequestMapping(value="list", method=RequestMethod.GET)
	    public void userListGET(Model model) {
	    	model.addAttribute("list", uservice.getList());
	        log.info("유저 목록 페이지 진입");
	    }
	    
	    
	    /* 유저 쓰기 화면 */
	    @GetMapping("/join")
	    // => @RequestMapping(value="join", method=RequestMethod.GET)
	    public void userEnrollGET() {
	        log.info("유저 등록 페이지 진입");
	    }
	    
	    /* 유저 쓰기 처리 = 회원가입 */
	    @PostMapping("/join")
	    public String userJoin(UserDTO user, RedirectAttributes rttr) {
	        
	        log.info("UserDTO : " + user);	//화면에서 넘어온 유저 데이터 확인
	        uservice.join(user);			//유저쓰기 처리
	        rttr.addFlashAttribute("result", "join success");	//처리결과 전달
	        return "redirect:/user/list";	//유저쓰기 처리후 유저목록 화면으로 전환
	    }
	    
	    /* 유저 상세 조회 = 로그인 */
	    @GetMapping("/login")
	    public void userLogin(UserDTO user, Model model) {
	        model.addAttribute("pageInfo", uservice.login(user));
	    }
	    
	    /* 수정 페이지 이동 */
	    @GetMapping("/modify")
	    public void userModifyGET(String userId, Model model) {
	        
	        model.addAttribute("pageInfo", uservice.getDetail(userId));
	        
	    }
	    
	    /* 유저 수정 프로세스 처리 */
	    @PostMapping("/modify")
	    public String userModifyPOST(UserDTO user, RedirectAttributes rttr) {
	        uservice.modify(user);								//유저 수정 처리
	        rttr.addFlashAttribute("result", "modify success");	//처리결과 전달
	        return "redirect:/user/list";	//수정후에 리스트 화면으로 전환
	        
	    }
	    
	    /* 유저 삭제 프로세스 처리 */
	    @PostMapping("/delete")
	    public String userDeletePOST(int bno, RedirectAttributes rttr) {
	        uservice.delete(bno);
	        rttr.addFlashAttribute("result", "delete success");
	        return "redirect:/user/list";
	    }
	 
}
