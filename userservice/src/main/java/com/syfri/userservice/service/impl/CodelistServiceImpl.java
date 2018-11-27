package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.*;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.CodelistDAO;
import com.syfri.userservice.service.CodelistService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("codelistService")
public class CodelistServiceImpl extends BaseServiceImpl<CodelistVO> implements CodelistService {

	@Autowired
	private CodelistDAO codelistDAO;

	@Override
	public CodelistDAO getBaseDAO() {
		return codelistDAO;
	}

	/*--根据代码类型获取代码集--*/
	@Override
	public List<CodelistDetailVO> doFindDetail(String codeid) {
		return codelistDAO.doFindDetail(codeid);
	}

	/*--查询：代码集.--*/
	@Override
	public List<CodelistVO> doFindCodelist(CodelistVO codelistVO){
		if(codelistVO.getCodetype()!=null && !"".equals(codelistVO.getCodetype())){
			codelistVO.setCodetype(codelistVO.getCodetype().toUpperCase());
		}
		return codelistDAO.doSearchByVO(codelistVO);
	}

	/*--新增：代码集.--*/
	@Override
	public CodelistVO doInsertCodelist(CodelistVO codelistVO){
		codelistVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		codelistVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		if(codelistVO.getLanguage() == null || codelistVO.getLanguage() == ""){
			codelistVO.setLanguage("zh_CN");
		}
		codelistDAO.doInsertByVO(codelistVO);
		return codelistVO;
	}

	/*--修改：代码集.--*/
	@Override
	public CodelistVO doUpdateCodelist(CodelistVO codelistVO){
		codelistVO.setAlterId(CurrentUserUtil.getCurrentUserId());
		codelistVO.setAlterName(CurrentUserUtil.getCurrentUserName());
		codelistDAO.doUpdateByVO(codelistVO);
		return codelistVO;
	}

	/*--删除：代码集.--*/
	@Override
	public int doDeleteCodelist(List<CodelistVO> list){
		int num = 0;
		for(CodelistVO vo : list){
			String codeid = vo.getCodeid();
			codelistDAO.doDeleteById(codeid);
			codelistDAO.doDeleteCodelistDetailBatch(codeid);
			num++;
		}
		return num;
	}

	/*--查询从表数量 by li.xue 2018/11/26.--*/
	@Override
	public int doFindByCodelistDetailNum(CodelistDetailVO codelistDetailVO){
		return codelistDAO.doFindByCodelistDetailNum(codelistDetailVO);
	}

	/*--查询：根据代码集详情对象查询.--*/
	@Override
	public List<CodelistDetailVO> doFindCodelistDetail(CodelistDetailVO codelistDetailVO){
		return codelistDAO.doFindCodelistDetail(codelistDetailVO);
	}

	/*--新增从表：.--*/
	@Override
	public CodelistDetailVO doInsertCodelistDetail(CodelistDetailVO codelistDetailVO){
		codelistDetailVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		codelistDetailVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		CodelistVO codelistVO = codelistDAO.doFindById(codelistDetailVO.getCodeid());
		codelistDetailVO.setCodetype(codelistVO.getCodetype());
		codelistDetailVO.setCodetypeName(codelistVO.getCodetypeName());
		codelistDAO.doInsertCodelistDetail(codelistDetailVO);
		return codelistDetailVO;
	}

	/*--修改从表：.--*/
	@Override
	public CodelistDetailVO doUpdateCodelistDetail(CodelistDetailVO codelistDetailVO){
		codelistDetailVO.setAlterId(CurrentUserUtil.getCurrentUserId());
		codelistDetailVO.setAlterName(CurrentUserUtil.getCurrentUserName());
		codelistDAO.doUpdateCodelistDetail(codelistDetailVO);
		return codelistDetailVO;
	}

	/*--删除从表：根据主键山粗.--*/
	@Override
	public int doDeleteCodelistDetail(List<CodelistDetailVO> list){
		int num = 0;
		for(CodelistDetailVO vo: list){
			codelistDAO.doDeleteCodelistDetail(vo.getPkid());
			num++;
		}
		return num;
	}

	/*--根据代码类型查询代码集.--*/
	@Override
	public List<CodelistDetailVO> doFindCodelistByType(String codetype){
		return codelistDAO.doFindCodelistByType(codetype);
	}

	/*--根据代码类型查询代码集，按数字大小排序.--*/
	@Override
	public List<CodelistDetailVO> doFindCodelistByTypeOrderByNum(String codetype){
		return codelistDAO.doFindCodelistByTypeOrderByNum(codetype);
	}

	/*--根据代码类型获取树状资源.--*/
	@Override
	public List<CodelistTree> doFindCodelistTreeByType(CodelistParams codelistParams) {
		// 代码类型
		String codetype=codelistParams.getCodetype();
		// 每级占位数
		List<Integer> digits = codelistParams.getList();
//		List<Integer> digits = new ArrayList();
//		digits.add(2);
//		digits.add(4);
		// 目的树
		List<CodelistTree> codelistTrees = new ArrayList<>();
		// 源数据
		List<CodelistDetailVO> codelistDetailVOs = codelistDAO.doFindCodelistByType(codetype);

		if (codelistDetailVOs != null && codelistDetailVOs.size() > 0
				&& digits.size() > 0 && digits.get(0) > 0) {
			for (CodelistDetailVO codelistDetailVO : codelistDetailVOs) {
				// 选出第一级类别
				if (codelistDetailVO.getCodeValue().length() == digits.get(0)) {
					CodelistTree tree = new CodelistTree();
					tree.setCodeName(codelistDetailVO.getCodeName());
					tree.setCodeValue(codelistDetailVO.getCodeValue());
					if (digits.size() > 1 && digits.get(1) > 0) {
						List<CodelistTree> children = new ArrayList();
						for (CodelistDetailVO codelistDetailVO2 : codelistDetailVOs) {
							// 选出第二级类别
							if (codelistDetailVO2.getCodeValue().length() == digits.get(1)
									// 所属父类别下
									&& codelistDetailVO2.getCodeValue().substring(0, digits.get(0)).equals(codelistDetailVO.getCodeValue())) {
								CodelistTree tree2 = new CodelistTree();
								tree2.setCodeName(codelistDetailVO2.getCodeName());
								tree2.setCodeValue(codelistDetailVO2.getCodeValue());
								children.add(tree2);
							}
						}
						tree.setChildren(children);
					}
					codelistTrees.add(tree);
				}
			}
		}
		return codelistTrees;
	}

	/*--根据代码类型获取树状资源2.--*/
	@Override
	public List<CodelistTree> doFindCodelistTreeByType2(CodelistParams codelistParams) {
		// 代码类型
		String codetype=codelistParams.getCodetype();
		// 每级占位数
		List<Integer> digits = codelistParams.getList();
//		List<Integer> digits = new ArrayList();
//		digits.add(1);
//		digits.add(2);
//		digits.add(4);
//		digits.add(6);
//		digits.add(8);
		// 目的树
		List<CodelistTree> codelistTrees = new ArrayList<>();
		// 源数据
		List<CodelistDetailVO> codelistDetailVOs = codelistDAO.doFindCodelistByType(codetype);

		if (codelistDetailVOs != null && codelistDetailVOs.size() > 0
				&& digits.size() > 0 && digits.get(0) > 0) {
			for (CodelistDetailVO codelistDetailVO : codelistDetailVOs) {
				// 选出第一级类别
				if (Integer.valueOf(codelistDetailVO.getCodeValue().substring(digits.get(0), codelistDetailVO.getCodeValue().length())) == 0) {
					CodelistTree tree = new CodelistTree();
					tree.setCodeName(codelistDetailVO.getCodeName());
					tree.setCodeValue(codelistDetailVO.getCodeValue());
					if (digits.size() > 1 && digits.get(1) > 0) {
						List<CodelistTree> children = new ArrayList();
						for (CodelistDetailVO codelistDetailVO2 : codelistDetailVOs) {
							// 选出所属第二级类别
							// 所有第二级类别
							if (Integer.valueOf(codelistDetailVO2.getCodeValue().substring(digits.get(1), codelistDetailVO2.getCodeValue().length())) == 0
									// 所属父类别下
									&& codelistDetailVO2.getCodeValue().substring(0, digits.get(0)).equals(codelistDetailVO.getCodeValue().substring(0, digits.get(0)))
									// 除去父类别
									&& !codelistDetailVO2.getCodeValue().equals(codelistDetailVO.getCodeValue())) {
								CodelistTree tree2 = new CodelistTree();
								tree2.setCodeName(codelistDetailVO2.getCodeName());
								tree2.setCodeValue(codelistDetailVO2.getCodeValue());
								if (digits.size() > 2 && digits.get(2) > 0) {
									List<CodelistTree> children2 = new ArrayList();
									for (CodelistDetailVO codelistDetailVO3 : codelistDetailVOs) {
										// 选出所属第三级类别
										// 所有第三级类别
										if (Integer.valueOf(codelistDetailVO3.getCodeValue().substring(digits.get(2), codelistDetailVO3.getCodeValue().length())) == 0
												// 所属父类别下
												&& codelistDetailVO3.getCodeValue().substring(0, digits.get(1)).equals(codelistDetailVO2.getCodeValue().substring(0, digits.get(1)))
												// 除去父类别
												&& !codelistDetailVO3.getCodeValue().equals(codelistDetailVO2.getCodeValue())) {
											CodelistTree tree3 = new CodelistTree();
											tree3.setCodeName(codelistDetailVO3.getCodeName());
											tree3.setCodeValue(codelistDetailVO3.getCodeValue());
											if (digits.size() > 3 && digits.get(3) > 0) {
												List<CodelistTree> children3 = new ArrayList();
												for (CodelistDetailVO codelistDetailVO4 : codelistDetailVOs) {
													// 选出所属第四级类别
													// 所有第四级类别
													if (Integer.valueOf(codelistDetailVO4.getCodeValue().substring(digits.get(3), codelistDetailVO4.getCodeValue().length())) == 0
															// 所属父类别下
															&& codelistDetailVO4.getCodeValue().substring(0, digits.get(2)).equals(codelistDetailVO3.getCodeValue().substring(0, digits.get(2)))
															// 除去父类别
															&& !codelistDetailVO4.getCodeValue().equals(codelistDetailVO3.getCodeValue())) {
														CodelistTree tree4 = new CodelistTree();
														tree4.setCodeName(codelistDetailVO4.getCodeName());
														tree4.setCodeValue(codelistDetailVO4.getCodeValue());
														if (digits.size() > 4 && digits.get(4) > 0) {
															List<CodelistTree> children4 = new ArrayList();
															for (CodelistDetailVO codelistDetailVO5 : codelistDetailVOs) {
																// 选出第五级类别
																// 所属父类别下
																if (codelistDetailVO5.getCodeValue().substring(0, digits.get(3)).equals(codelistDetailVO4.getCodeValue().substring(0, digits.get(3)))
																		// 除去父类别
																		&& !codelistDetailVO5.getCodeValue().equals(codelistDetailVO4.getCodeValue())) {
																	CodelistTree tree5 = new CodelistTree();
																	tree5.setCodeName(codelistDetailVO5.getCodeName());
																	tree5.setCodeValue(codelistDetailVO5.getCodeValue());
																	children4.add(tree5);
																}
															}
															if(!children4.isEmpty()){
																tree4.setChildren(children4);
															}
														}
														children3.add(tree4);
													}
												}
												if(!children3.isEmpty()){
													tree3.setChildren(children3);
												}
											}
											children2.add(tree3);
										}
									}
									if(!children2.isEmpty()){
										tree2.setChildren(children2);
									}
								}
								children.add(tree2);
							}
						}
						if(!children.isEmpty()){
							tree.setChildren(children);
						}
					}
					codelistTrees.add(tree);
				}
			}
		}
		return codelistTrees;
	}

	/*--查询队站类型树状资源
	* 只加载其他消防队伍的子级，其余只保留父级
	* -- by yushch -- */
	@Override
	public List<CodelistTree> doFindDzlxCodelisttree(String codetype) {
		// 目的树
		List<CodelistTree> codelistTrees = new ArrayList<>();
		// 源数据
		List<CodelistDetailVO> codelistDetailVOs = codelistDAO.doFindCodelistByType(codetype);
		if (codelistDetailVOs != null && codelistDetailVOs.size() > 0) {
			for (CodelistDetailVO codelistDetailVO : codelistDetailVOs) {
				// 选出第一级类别
				if (codelistDetailVO.getCodeValue().endsWith("00")) {
					CodelistTree tree = new CodelistTree();
					tree.setCodeName(codelistDetailVO.getCodeName());
					tree.setCodeValue(codelistDetailVO.getCodeValue());
					List<CodelistTree> children = new ArrayList();
					//第二级别
					for (CodelistDetailVO codelistDetailVO2 : codelistDetailVOs) {
						if (codelistDetailVO2.getCodeValue().startsWith(codelistDetailVO.getCodeValue().substring(0,2))&&!codelistDetailVO2.equals(codelistDetailVO)){
							CodelistTree tree2 = new CodelistTree();
							tree2.setCodeName(codelistDetailVO2.getCodeName());
							tree2.setCodeValue(codelistDetailVO2.getCodeValue());
							children.add(tree2);
						}
					}
					if(!children.isEmpty() )
						tree.setChildren(children);
					codelistTrees.add(tree);
				}
			}
		}
		return codelistTrees;
	}

	/*--查询行政区划
	* 只保留31个省
	* -- by yushch -- */
	public List<CodelistDetailVO> doFindXzqhCodelist(String codetype){
		return codelistDAO.doFindXzqhCodelist(codetype);
	}

	/*--查询燃烧物质树状资源
	* 数据结构为（1，2）
	* -- by liurui -- */
	@Override
	public List<CodelistTree> doFindRswzCodelisttree(String codetype) {
		// 目的树
		List<CodelistTree> codelistTrees = new ArrayList<>();
		// 源数据
		List<CodelistDetailVO> codelistDetailVOs = codelistDAO.doFindCodelistByType(codetype);
		if (codelistDetailVOs != null && codelistDetailVOs.size() > 0) {
			for (CodelistDetailVO codelistDetailVO : codelistDetailVOs) {
				// 选出第一级类别
				if (codelistDetailVO.getCodeValue().endsWith("00")) {
					CodelistTree tree = new CodelistTree();
					tree.setCodeName(codelistDetailVO.getCodeName());
					tree.setCodeValue(codelistDetailVO.getCodeValue());
					List<CodelistTree> children = new ArrayList();
					//第二级别
					for (CodelistDetailVO codelistDetailVO2 : codelistDetailVOs) {
						if (codelistDetailVO2.getCodeValue().startsWith(codelistDetailVO.getCodeValue().substring(0,1))&&!codelistDetailVO2.equals(codelistDetailVO)){
							CodelistTree tree2 = new CodelistTree();
							tree2.setCodeName(codelistDetailVO2.getCodeName());
							tree2.setCodeValue(codelistDetailVO2.getCodeValue());
							children.add(tree2);
						}
					}
					if(!children.isEmpty() )
						tree.setChildren(children);
					codelistTrees.add(tree);
				}
			}
		}
		return codelistTrees;
	}

    /*--查询药剂类型树状资源
    * 去掉第一级40000000，数据结构为（2，2，2）
    * -- by liurui -- */
    public List<CodelistTree> doFindYjlxCodelisttree(String codetype) {
        // 目的树
        List<CodelistTree> codelistTrees = new ArrayList<>();
        // 源数据
        List<CodelistDetailVO> codelistDetailVOs = codelistDAO.doFindCodelistByType(codetype);
        if (codelistDetailVOs != null && codelistDetailVOs.size() > 0) {
            for (CodelistDetailVO codelistDetailVO : codelistDetailVOs) {
                // 选出第一级类别
                if ((!codelistDetailVO.getCodeValue().endsWith("0000000")) && codelistDetailVO.getCodeValue().endsWith("000000")) {
                    CodelistTree tree = new CodelistTree();
                    tree.setCodeName(codelistDetailVO.getCodeName());
                    tree.setCodeValue(codelistDetailVO.getCodeValue());
                    List<CodelistTree> children = new ArrayList();
                    //第二级别
                    for (CodelistDetailVO codelistDetailVO2 : codelistDetailVOs) {
                        if (codelistDetailVO2.getCodeValue().startsWith(codelistDetailVO.getCodeValue().substring(0, 2)) &&
                                codelistDetailVO2.getCodeValue().endsWith("0000") && !codelistDetailVO2.equals(codelistDetailVO)) {
                            CodelistTree tree2 = new CodelistTree();
                            tree2.setCodeName(codelistDetailVO2.getCodeName());
                            tree2.setCodeValue(codelistDetailVO2.getCodeValue());
                            //第三级别
                            List<CodelistTree> children2 = new ArrayList();
                            for (CodelistDetailVO codelistDetailVO3 : codelistDetailVOs) {
                                if (codelistDetailVO3.getCodeValue().startsWith(codelistDetailVO2.getCodeValue().substring(0, 4)) && !codelistDetailVO3.equals(codelistDetailVO2)) {
                                    CodelistTree tree3 = new CodelistTree();
                                    tree3.setCodeName(codelistDetailVO3.getCodeName());
                                    tree3.setCodeValue(codelistDetailVO3.getCodeValue());
                                    children2.add(tree3);
                                }
                            }
                            if (!children2.isEmpty())
                                tree2.setChildren(children2);
                            children.add(tree2);
                        }
                    }
                    if (!children.isEmpty())
                        tree.setChildren(children);
                    codelistTrees.add(tree);
                }
            }
        }
        return codelistTrees;
    }
	/*--查询行政区划树状结构 by li.xue.--*/
	@Override
	public List<CodelistTree> getXzqhTreeByUser(OrganizationVO organizationVO){
		List<CodelistTree> provinceTrees = new ArrayList<>();
		List<CodelistDetailVO> provinceList;
		String xzqh = organizationVO.getXzqh();
		//部局
		if(organizationVO!=null && "01000000".equals(organizationVO.getJgid())){
			//31个省
			provinceList = ((CodelistService) AopContext.currentProxy()).doFindXzqhCodelist("XZQH");
		}else{
			//总队
			CodelistDetailVO codelistDetailVO = new CodelistDetailVO();
			codelistDetailVO.setCodeValue(xzqh.substring(0,2)+"0000");
			codelistDetailVO.setCodetype("XZQH");
			provinceList = codelistDAO.doFindCodelistDetail(codelistDetailVO);
		}
		for(CodelistDetailVO vo : provinceList){
			CodelistTree provinceTree = new CodelistTree(vo.getCodeName(), vo.getCodeValue());
			CodelistDetailVO codelistDetailVO = new CodelistDetailVO();
			if(organizationVO.getJgid()!=null && "000000".equals(organizationVO.getJgid().substring(2,8))){
				codelistDetailVO.setCodeValue(vo.getCodeValue().substring(0,2));
			}else{
				codelistDetailVO.setCodeValue(xzqh.substring(0,4));
			}
			codelistDetailVO.setRemark("second");
			List<CodelistDetailVO> cityList = codelistDAO.doFindXzqhByDm(codelistDetailVO);
			List<CodelistTree> cityTrees = new ArrayList<>();
			for(CodelistDetailVO cityVo : cityList){
				CodelistTree cityTree = new CodelistTree(cityVo.getCodeName(), cityVo.getCodeValue());
				List<CodelistDetailVO> regionList = codelistDAO.doFindXzqhByDm(new CodelistDetailVO(cityVo.getCodeValue().substring(0,4), "third"));
				List<CodelistTree> regionTrees = new ArrayList<>();
				for(CodelistDetailVO regionVo : regionList) {
					CodelistTree regionTree = new CodelistTree(regionVo.getCodeName(), regionVo.getCodeValue());
					regionTrees.add(regionTree);
				}
				if(!regionTrees.isEmpty()){
					cityTree.setChildren(regionTrees);
				}
				cityTrees.add(cityTree);
			}
			if(!cityTrees.isEmpty()){
				provinceTree.setChildren(cityTrees);
			}
			provinceTrees.add(provinceTree);
		}
		return provinceTrees;
	}

	/*--查询泡沫液类型树状资源
	* -- by liurui -- */
	@Override
	public List<CodelistTree> doFindPmylxlisttree(String codetype) {
		// 目的树
		List<CodelistTree> codelistTrees = new ArrayList<>();
		// 源数据
		List<CodelistDetailVO> codelistDetailVOs = codelistDAO.doFindCodelistByType(codetype);
		if (codelistDetailVOs != null && codelistDetailVOs.size() > 0) {
			for (CodelistDetailVO codelistDetailVO : codelistDetailVOs) {
				// 选出第一级类别
				if (codelistDetailVO.getCodeValue().endsWith("0000")) {
					CodelistTree tree = new CodelistTree();
					tree.setCodeName(codelistDetailVO.getCodeName());
					tree.setCodeValue(codelistDetailVO.getCodeValue());
					List<CodelistTree> children = new ArrayList();
					//第二级别
					for (CodelistDetailVO codelistDetailVO2 : codelistDetailVOs) {
						if (codelistDetailVO2.getCodeValue().startsWith(codelistDetailVO.getCodeValue().substring(0,4))&&!codelistDetailVO2.equals(codelistDetailVO)){
							CodelistTree tree2 = new CodelistTree();
							tree2.setCodeName(codelistDetailVO2.getCodeName());
							tree2.setCodeValue(codelistDetailVO2.getCodeValue());
							children.add(tree2);
						}
					}
					if(!children.isEmpty() )
						tree.setChildren(children);
					codelistTrees.add(tree);
				}
			}
		}
		return codelistTrees;
	}
}