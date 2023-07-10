package com.ware.group.approval;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ware.group.SSEController;
import com.ware.group.alim.AllimDAO;
import com.ware.group.alim.AllimVO;
import com.ware.group.annual.LeaveRecordVO;

import com.ware.group.approval3.DocumentFilesVO;
import com.ware.group.department.DepartmentVO;
import com.ware.group.member.JobVO;
import com.ware.group.member.MemberVO;
import com.ware.group.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor=Exception.class)
@Slf4j
public class ApprovalService {
	
	@Autowired
	private ApprovalDAO approvalDAO;
	@Autowired
	private AllimDAO allimDAO;
	
	
	public List<ApprovalCategoryVO> getUnderCategory(ApprovalCategoryVO approvalCategoryVO) throws Exception{;
		return approvalDAO.getUnderCategory(approvalCategoryVO);
	}
	
	public int updateApproverDepth(ApproverVO approverVO) throws Exception{
		return approvalDAO.updateApproverDepth(approverVO);
	}
	public int addUpperFormFile(ApprovalFormFileVO approvalFormFileVO) throws Exception{
		return approvalDAO.addUpperFormFile(approvalFormFileVO);
	};
	public int addUnderCategory(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.addUnderCategory(approvalCategoryVO);
	}
	
	public int deleteUnderFormFile(ApprovalFormFileVO approvalFormFileVO) throws Exception{
		return approvalDAO.deleteUnderFormFile(approvalFormFileVO);
	}
	
	public int deleteUnderApprover(ApproverVO approverVO) throws Exception{
		return approvalDAO.deleteUnderApprover(approverVO);
	}
	
	public int deleteUnderCategory(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.deleteUnderCategory(approvalCategoryVO);
	}
	
	public List<ApprovalCategoryVO> checkUpperCategory() throws Exception{
		return approvalDAO.checkUpperCategory();
	}
	
	public long underCategoryCount(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.underCategoryCount(approvalCategoryVO);
	}
	
	public int deleteUpperOptionApprover(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.deleteUpperOptionApprover(approvalCategoryVO);
	}
	
	public int deleteUpperOptionFormFile(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.deleteUpperOptionFormFile(approvalCategoryVO);
	}
	
	public int deleteApprover(ApproverVO approverVO) throws Exception{
		return approvalDAO.deleteApprover(approverVO);
	}
	
	public int updateFormFile(ApprovalFormFileVO approvalFormFileVO) throws Exception{
		return approvalDAO.updateFormFile(approvalFormFileVO);
	}
	public int updateCategoryName(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.updateCategoryName(approvalCategoryVO);
	}
	public int updateApprover(ApproverVO approverVO) throws Exception{
		return approvalDAO.updateApprover(approverVO);
	}
	
	public List<ApprovalFormFileVO> getListFormFile() throws Exception{
		return approvalDAO.getListFormFile();
	};
	public List<ApproverVO> getListApprover() throws Exception{
		return approvalDAO.getListApprover();
	}
	
	public List<ApprovalCategoryVO> getListCategoryRef0() throws Exception{
		return approvalDAO.getListCategoryRef0();
	};
	
	public List<ApprovalCategoryVO> getListCategoryRef1() throws Exception{
		return approvalDAO.getListCategoryRef1();
	};
	public int addCategory(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.addCategory(approvalCategoryVO);
	}
	public int addApprover(ApproverVO approverVO) throws Exception{
		approverVO.setDepth(approverVO.getDepth() + 1L);
		return approvalDAO.addApprover(approverVO);
	}
	public int addApprover1(ApproverVO approverVO) throws Exception{
		return approvalDAO.addApprover(approverVO);
	}
	public int addApprovalFormFile(ApprovalFormFileVO approvalFormFileVO) throws Exception{
		return approvalDAO.addApprovalFormFile(approvalFormFileVO);
	}
	public List<ApprovalCategoryVO> getCategoryOption() throws Exception{
		return approvalDAO.getCategoryOption();
	};
	public int deleteCategory(ApprovalCategoryVO categoryVO) throws Exception{
		return approvalDAO.deleteCategory(categoryVO);
	};
	
	public ApprovalVO test() throws Exception{
		return approvalDAO.test();
	};
	
	public List<DepartmentVO> getDepartmentList() throws Exception{
		return approvalDAO.getDepartmentList();
	};
	public List<JobVO> getJobList(DepartmentVO departmentVO) throws Exception{
		return approvalDAO.getJobList(departmentVO);
	};
	
	public int addFormFile(DocumentFilesVO documentFilesVO) throws Exception{
		return approvalDAO.addFormFile(documentFilesVO);
	};
	
	public long getFileId(String fileName) throws Exception{
		return approvalDAO.getFileId(fileName);
	}
	
	public List<String> getCategoryDuplication(String[] name) throws Exception{
		List<String> dup = new ArrayList<String>();
		for(String str : name) {
			List<String> temp = approvalDAO.getCategoryDuplication(str);
			if(temp.size() != 0) {
				dup.add(str);
			}
		}
		return dup;
	}
	
	public List<String> getFileDuplication(String[] formFileName) throws Exception{
		List<String> dup = new ArrayList<String>();
		for(String str : formFileName) {
			List<String> temp = approvalDAO.getFileDuplication(str);
			if(temp.size() != 0) {
				dup.add(str);
			}
		}
		
		return dup;
	}
	
	//
	public List<ApprovalCategoryVO> getListCategory() throws Exception{
		return approvalDAO.getListCategory();
	}
	
	public List<Integer> setApprovalApplication(ApprovalVO approvalVO,String fileName,LeaveRecordVO leaveRecordVO) throws Exception{
		  System.out.println("=========================4=====================");
		Long allimId=0L;
		List<Integer> al = new ArrayList<>();
		int result = approvalDAO.setApprovalApplication(approvalVO);
		
		if(result == 1) {
			ApprovalUploadFileVO approvalUploadFileVO = new ApprovalUploadFileVO();
			//결재 승인 번호
			//대기 하나 승인중 하나 씩 history 저장
			approvalUploadFileVO.setApprovalId(approvalVO.getId());
			approvalUploadFileVO.setName(fileName);
			result = approvalDAO.setApprovalApplicationFileUpload(approvalUploadFileVO);
			if(result == 1) {
				ApprovalHistoryVO approvalHistoryVO = new ApprovalHistoryVO();
				approvalHistoryVO.setMemberId(approvalVO.getMemberId());
				approvalHistoryVO.setApprovalId(approvalVO.getId());
				approvalHistoryVO.setCheck(ApprovalStatus.PENDING);
				
				result=approvalDAO.setApprovalApplicationHistory(approvalHistoryVO);
				if(result == 1) {
					//연차 기록에 결재 번호 입력
					
					if(leaveRecordVO.getCount() != null) {
						leaveRecordVO.setApprovalId(approvalVO.getId());
						leaveRecordVO.setMemberId(approvalVO.getMemberId());
						leaveRecordVO.setType(ApprovalStatus.PENDING);
						result = approvalDAO.setLeaverCode(leaveRecordVO);
					}
					ApprovalInfoVO approvalInfoVO = new ApprovalInfoVO();
					approvalInfoVO.setApprovalId(approvalVO.getId());
					approvalInfoVO.setDepth(0);
					approvalInfoVO.setCheck(ApprovalStatus.APPROVALING);
					MemberVO memberVO = approvalDAO.memberDepart(approvalVO);
					DepartmentVO departmentVO = approvalDAO.departManager(memberVO);
					approvalInfoVO.setMemberId(departmentVO.getManager());
					allimId=approvalInfoVO.getMemberId();
					result = approvalDAO.setApprovalInfo(approvalInfoVO);
					
					List<ApproverVO> ar = approvalDAO.getApprover(approvalVO);
					for(ApproverVO approverVO : ar) {
						
						//결재 승인 번호
						
						memberVO = approvalDAO.getApprovalInfo(approverVO);
						//결재자 id
						approvalInfoVO.setMemberId(memberVO.getId());
						
						approvalInfoVO.setDepth(Long.valueOf(approverVO.getDepth()).intValue());
						
						if(approvalInfoVO.getCheck().equals(ApprovalStatus.APPROVALING)) {
							approvalInfoVO.setCheck(ApprovalStatus.PENDING);
						}
						
						result = approvalDAO.setApprovalInfo(approvalInfoVO);
						
						
						}
					
				}
			}
		}
		AllimVO allimVO = new AllimVO();
		allimVO.setMemberId(allimId);
		allimVO.setType(1);
		allimVO.setQnaId(null);
		log.error(" qna{}",allimVO.getQnaId());
		result = allimDAO.setAllim(allimVO);
		al.add(result);
		al.add(allimId.intValue());
		return al;
	}
	
	public List<ApprovalVO> getApprovalList(Pager pager) throws Exception{
		pager.makeStartRow();
		pager.makeNum(approvalDAO.getTotalCount(pager));
		if(approvalDAO.getTotalCount(pager)<1) {
			pager.setLastNum(1L);
			}
		log.error("++++++++++++++++++++++++++{}++++++++++++",pager.getStartRow());
		List<ApprovalVO> ar = approvalDAO.getApprovalList(pager);
		
		return ar;
	}
	public ApprovalUploadFileVO getApprovalFile(ApprovalVO approvalVO) throws Exception{
		return approvalDAO.getApprovalFile(approvalVO);
	}
	public List<Integer> setApprovalApproval(MemberVO memberVO,ApprovalVO approvalVO,int approval) throws Exception{
		int result=0;
		Long allimId;
		List<Integer> al = new ArrayList<>();
		AllimVO allimVO = new AllimVO();
		ApprovalHistoryVO approvalHistoryVO = new ApprovalHistoryVO();
		approvalHistoryVO.setMemberId(memberVO.getId());
		approvalHistoryVO.setApprovalId(approvalVO.getId());
		
		ApprovalInfoVO approvalInfoVO = new ApprovalInfoVO();
		if(approval ==1) {
		approvalHistoryVO.setCheck(ApprovalStatus.APPROVAL);
		result=approvalDAO.setApprovalApplicationHistory(approvalHistoryVO);
		//대기/승인 대입
				approvalInfoVO.setCheck(ApprovalStatus.APPROVAL);
				//결재 번호 대입
				approvalInfoVO.setApprovalId(approvalVO.getId());
				//회원 id  대입
				approvalInfoVO.setMemberId(memberVO.getId());
				result = approvalDAO.setInfoUpdate(approvalInfoVO);
				approvalInfoVO=approvalDAO.getInfoDetail(approvalInfoVO);
				approvalInfoVO.setDepth(approvalInfoVO.getDepth()+1);
				approvalInfoVO=approvalDAO.getInfoList(approvalInfoVO);
				
				if(approvalInfoVO != null) {
					approvalInfoVO.setCheck(ApprovalStatus.APPROVALING);
					result = approvalDAO.setInfoUpdate(approvalInfoVO);
					log.error("제발 ::{}",approvalVO.getId());
					log.error("제발2 ::{}",approvalVO.getMemberId());
					allimId=approvalInfoVO.getMemberId();
					allimVO.setMemberId(approvalInfoVO.getMemberId());
					allimVO.setType(1);
					allimVO.setQnaId(null);
					allimDAO.setAllim(allimVO);
					
				}else {
					approvalVO.setConfirm(ApprovalStatus.APPROVAL);
					result = approvalDAO.setApprovalUpdate(approvalVO);
					approvalVO = approvalDAO.getApprovalId(approvalVO);
					allimVO.setMemberId(approvalVO.getMemberId());
					
					LeaveRecordVO leaveRecordVO = new LeaveRecordVO();
					leaveRecordVO.setApprovalId(approvalVO.getId());
					leaveRecordVO = approvalDAO.getLeave(leaveRecordVO);
					if(leaveRecordVO !=null) {
					log.error("{}::::::::::::::::::::::::",approvalVO.getId());
					approvalVO = approvalDAO.getApprovalId(approvalVO);
					leaveRecordVO.setMemberId(approvalVO.getMemberId());
					leaveRecordVO.setType(ApprovalStatus.APPROVAL);
					result = approvalDAO.setLeaverCode(leaveRecordVO);
					if(leaveRecordVO.getAnnualType().equals("년차")) {
						result = approvalDAO.setAnnual(leaveRecordVO);		
					}
					}			
					
					
					log.error("제발 ::{}",approvalVO.getId());
					log.error("제발2 ::{}",approvalVO.getMemberId());
					allimId=approvalVO.getMemberId();
					allimVO.setType(2);
					allimVO.setQnaId(null);
					allimDAO.setAllim(allimVO);
					}
		}else {
			approvalHistoryVO.setCheck(ApprovalStatus.REFUSE);
			approvalInfoVO.setCheck(ApprovalStatus.REFUSE);
			approvalInfoVO.setApprovalId(approvalVO.getId());
			approvalInfoVO.setMemberId(memberVO.getId());
			result = approvalDAO.setInfoUpdate(approvalInfoVO);
			result=approvalDAO.setApprovalApplicationHistory(approvalHistoryVO);
			
			LeaveRecordVO leaveRecordVO = new LeaveRecordVO();
			leaveRecordVO.setApprovalId(approvalVO.getId());
			leaveRecordVO = approvalDAO.getLeave(leaveRecordVO);
			if(leaveRecordVO !=null) {
			approvalVO = approvalDAO.getApprovalId(approvalVO);
			leaveRecordVO.setMemberId(approvalVO.getMemberId());
			leaveRecordVO.setType(ApprovalStatus.REFUSE);
			result = approvalDAO.setLeaverCode(leaveRecordVO);
			}
			approvalVO.setConfirm(ApprovalStatus.REFUSE);
			
			result = approvalDAO.setApprovalUpdate(approvalVO);
			approvalVO = approvalDAO.getApprovalId(approvalVO);
			allimId=approvalVO.getMemberId();
			log.error("제발 ::{}",approvalVO.getId());
			log.error("제발2 ::{}",approvalVO.getMemberId());
			allimVO.setMemberId(approvalVO.getMemberId());
			allimVO.setType(2);
			allimVO.setQnaId(null);
			allimDAO.setAllim(allimVO);
		}
		
		
		al.add(result);
		al.add(allimId.intValue());
		return al;
	}
	public List<ApprovalVO> getMyApproval(Pager pager) throws Exception{
		
		pager.makeStartRow();
		pager.makeNum(approvalDAO.getMyTotal(pager));
		return approvalDAO.getMyApproval(pager);
	}
	
	public ApprovalFormFileVO getFormFile(ApprovalCategoryVO approvalCategoryVO) throws Exception{
		return approvalDAO.getFormFile(approvalCategoryVO);
		}
	public int setApprovalDelete(Long id1,MemberVO memberVO) throws Exception{
		
		ApprovalHistoryVO approvalHistoryVO = new ApprovalHistoryVO();
		approvalHistoryVO.setMemberId(memberVO.getId());
		approvalHistoryVO.setApprovalId(id1);
		approvalHistoryVO.setCheck(ApprovalStatus.CANCEL);
		approvalDAO.setApprovalApplicationHistory(approvalHistoryVO);
		LeaveRecordVO leaveRecordVO = new LeaveRecordVO();
		leaveRecordVO.setApprovalId(id1);
		leaveRecordVO = approvalDAO.getLeave(leaveRecordVO);
		if(leaveRecordVO !=null) {
			leaveRecordVO.setMemberId(memberVO.getId());
			leaveRecordVO.setType(ApprovalStatus.CANCEL);
			approvalDAO.setAnnual(leaveRecordVO);
		
		}else {
			leaveRecordVO = new LeaveRecordVO();
			leaveRecordVO.setApprovalId(id1);
			List<LeaveRecordVO> ar=approvalDAO.getLeaverCode(leaveRecordVO);
			if(ar.size() < 0){
				leaveRecordVO.setMemberId(memberVO.getId());
				leaveRecordVO.setType(ApprovalStatus.CANCEL);
				approvalDAO.setAnnual(leaveRecordVO);
			}
		}
		return approvalDAO.setApprovalDelete(id1);
	}
	
	public int setApprovalFileDelete(Long id1) throws Exception{
		return approvalDAO.setApprovalFileDelete(id1);
	}
	public int setApprovalInfoDelete(Long id1) throws Exception{
		return approvalDAO.setApprovalInfoDelete(id1);
	}
	
}
