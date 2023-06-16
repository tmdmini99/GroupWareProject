package com.ware.group.member;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ware.group.common.ScheduleService;
import com.ware.group.department.DepartmentService;
import com.ware.group.department.DepartmentVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/*")
@Slf4j
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ScheduleService employeeService;
	@Autowired
	private DepartmentService departmentService;


	@GetMapping("memberList")
	public ModelAndView getMemeberList(ModelAndView mv)throws Exception{
		List<MemberVO> ar = memberService.getMemberList();
		mv.addObject("memberVOs", ar);
		return mv;
	}
	
	@GetMapping("join")
	public ModelAndView setMemberJoin(@ModelAttribute MemberVO memberVO, ModelAndView mv)throws Exception{
		
		List<DepartmentVO> departmentVOs =   departmentService.getDepartmentList();
		List<JobVO> jobVOs = memberService.getJobList();
		mv.addObject("jobVOs", jobVOs);
		mv.addObject("departmentVOs", departmentVOs);
		
		mv.setViewName("member/join");
		return mv;
		
	}
	@PostMapping("join")
	public ModelAndView setMemberJoin(ModelAndView mv, @Valid MemberVO memberVO,WorkTimeVO workTimeVO, BindingResult bindingResult)throws Exception{

		
		int result = memberService.setMemeberJoin(memberVO, workTimeVO);
		
		
		mv.setViewName("redirect:/");
		return mv;
	}
//	
	@GetMapping("login")
	public ModelAndView getLogin(ModelAndView mv, HttpSession session)throws Exception{
		
		mv.setViewName("member/login");
		return mv;
		
	}
	@GetMapping("update")
	public ModelAndView setMemberUpdate(MemberVO memberVO)throws Exception{
		ModelAndView mv = new ModelAndView();
		memberVO =memberService.getMemberDetail(memberVO);
		List<DepartmentVO> departmentVOs =   departmentService.getDepartmentList();
		List<JobVO> jobVOs = memberService.getJobList();
//		근태받기
		List<EmployeeStatusVO> employeeStatusVOs =  memberService.getEmployeeStatusList(memberVO);

		mv.addObject("employeeStatusVOs", employeeStatusVOs);
		mv.addObject("jobVOs", jobVOs);
		mv.addObject("departmentVOs", departmentVOs);
		mv.addObject("memberVO", memberVO);
		mv.setViewName("member/update");
		return mv;
	}
	@PostMapping("update")
	public ModelAndView setMemberUpdate(ModelAndView mv, MemberVO memberVO)throws Exception{
		int result = memberService.setMemberUpdate(memberVO);
		
		mv.setViewName("redirect:/member/logout");
		return mv;
	}
	
	@GetMapping("profile")
	public ModelAndView getProfile(@ModelAttribute MemberVO memberVO, ModelAndView mv,HttpSession session)throws Exception{

		memberVO = memberService.getMemberProfile(memberVO, session);
		
		mv.addObject("memberVO", memberVO);
		mv.setViewName("member/profile");
		return mv;
		
	}
	//ajax용
	@GetMapping("detail")
	public ModelAndView getMemberDetail(@ModelAttribute MemberVO memberVO,ModelAndView mv)throws Exception{
		
		memberVO = memberService.getMemberDetail(memberVO);
		mv.addObject("memberVO", memberVO);
		mv.setViewName("member/memberDetailResult");
		return mv;
	}
	
	
	@GetMapping("security")
	public ModelAndView getSecurity(@ModelAttribute MemberVO memberVO, ModelAndView mv)throws Exception{
		
		mv.setViewName("member/security");
		return mv;
	}
	
	@PostMapping("security")
	public ModelAndView setPasswordUpdate(ModelAndView mv, MemberVO memberVO,HttpSession session, BindingResult bindingResult)throws Exception{
		
//		Object obj =session.getAttribute("SPRING_SECURITY_CONTEXT");
//		SecurityContextImpl contextImpl = (SecurityContextImpl)obj;
//		MemberVO memberVO2 =(MemberVO)contextImpl.getAuthentication().getPrincipal(); 
//		memberVO.setId(memberVO2.getId());
		memberService.setPasswordUpdate(memberVO,session, bindingResult);
		mv.setViewName("redirect:/member/logout");
		return mv;
	}
	
//	-------------검증------------------------------------------
	@GetMapping("idDuplicateCheck")
	@ResponseBody
	public boolean idDuplicateCheck(MemberVO memberVO)throws Exception{
		boolean check;
		check = memberService.idDuplicateCheck(memberVO);

		return check;
		
	}

	//----------------근태------------------------------------

	@PostMapping("statusUpdate")
	public ModelAndView employeeStatusUpdate(ModelAndView mv,MemberVO memberVO,EmployeeStatusVO employeeStatusVO,HttpSession session,String timeStatus,WorkTimeVO workTimeVO)throws Exception{
		employeeStatusVO.setStatus(timeStatus);
		int result =  memberService.setStatusUpdate(memberVO, employeeStatusVO,workTimeVO, session);
		
		mv.setViewName("redirect:/");
		return mv;
	}
	@PostMapping("testStatusUp")
	public void testStatusUp(ModelAndView mv,MemberVO memberVO, HttpSession session, EmployeeStatusVO employeeStatusVO) throws Exception{
		int result = employeeService.testTimeStempInsert(memberVO, employeeStatusVO, session);
		
	}
	@GetMapping("statusList")
	public ModelAndView getStatusList(ModelAndView mv,MemberVO memberVO, HttpSession session, EmployeeStatusVO employeeStatusVO,WorkTimeVO workTimeVO)throws Exception{
		
		//필요한 파라미터
		//1.내정보
		memberVO = memberService.getMemberProfile(memberVO, session);
		mv.addObject("memberVO",memberVO);
		//2. 지금 현재 근무상태
		employeeStatusVO =  memberService.getEmployeeStatus(session);
		mv.addObject("employeeVO", employeeStatusVO);
		//  2-1 근무상태 버튼
		List<String> ar = memberService.getEmployeeStatusBtn(employeeStatusVO, session);
		if(ar!=null&&ar.size()>0) {
			
			mv.addObject("btns",ar);
		}
		//3. 총 근무 내역
		List<EmployeeStatusVO> employeeStatusVOs = memberService.getEmployeeStatusList(employeeStatusVO, session);
//		mv.addObject("employeeStatusVOs", employeeStatusVOs);   4번의 list에 있음 사실상 3번은 4번에 추가하여 없어도됨
		//  3-1 근무 내역이 있는 년도
		List<String> years = memberService.getEmployeeStatusYears(employeeStatusVOs);
		mv.addObject("years",years);
		
		//4. 근무 내역의 총 시간 및 필요한 시간, 
		List<WorkTimeStatusVO> workTimeStatusVOs =  memberService.getWorkTimeStatusTotal(workTimeVO,employeeStatusVO, session);
		mv.addObject("workTimeStatusVOs",workTimeStatusVOs);
		
		mv.setViewName("member/statusList");
		return mv;
	}
	
}
