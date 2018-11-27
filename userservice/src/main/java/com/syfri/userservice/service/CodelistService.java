package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.*;

import java.util.List;

public interface CodelistService  extends BaseService<CodelistVO>{

	/*--根据代码类型获取代码集--*/
	List<CodelistDetailVO> doFindDetail(String codeid);

	/*--查询：代码集.--*/
	List<CodelistVO> doFindCodelist(CodelistVO codelistVO);

	/*--新增：代码集.--*/
	CodelistVO doInsertCodelist(CodelistVO codelistVO);

	/*--修改：代码集.--*/
	CodelistVO doUpdateCodelist(CodelistVO codelistVO);

	/*--删除：代码集.--*/
	int doDeleteCodelist(List<CodelistVO> list);

	/*--查询从表数量 by li.xue 2018/11/26.--*/
	int doFindByCodelistDetailNum(CodelistDetailVO codelistDetailVO);

	/*--查询从表：根据代码集详情对象查询.--*/
	List<CodelistDetailVO> doFindCodelistDetail(CodelistDetailVO codelistDetailVO);

	/*--新增从表：.--*/
	CodelistDetailVO doInsertCodelistDetail(CodelistDetailVO codelistDetailVO);

	/*--修改从表：.--*/
	CodelistDetailVO doUpdateCodelistDetail(CodelistDetailVO codelistDetailVO);

	/*--删除从表：根据主键删除.--*/
	int doDeleteCodelistDetail(List<CodelistDetailVO> list);

	/*--根据代码类型查询代码集.--*/
	List<CodelistDetailVO> doFindCodelistByType(String codetype);

	/*--根据代码类型查询代码集，按数字大小排序.--*/
	List<CodelistDetailVO> doFindCodelistByTypeOrderByNum(String codetype);

	/*--根据代码类型获取树状资源.--*/
	List<CodelistTree> doFindCodelistTreeByType(CodelistParams codelistParams);

	/*--根据代码类型获取树状资源.--*/
	List<CodelistTree> doFindCodelistTreeByType2(CodelistParams codelistParams);

	/*--查询队站类型树状资源-- by yushch*/
	List<CodelistTree> doFindDzlxCodelisttree(String codetype);

	/*--查询行政区划，只保留31个省-- by yushch*/
	List<CodelistDetailVO> doFindXzqhCodelist(String codetype);

	/*--查询燃烧物质树状资源-- by liurui*/
	List<CodelistTree> doFindRswzCodelisttree(String codetype);

	/*--查询药剂类型树状资源-- by liurui*/
	List<CodelistTree> doFindYjlxCodelisttree(String codetype);

	/*--查询行政区划树状结构 by li.xue.--*/
	List<CodelistTree> getXzqhTreeByUser(OrganizationVO organizationVO);

	/*--查询泡沫液类型树状结构 by liurui.--*/
	List<CodelistTree> doFindPmylxlisttree(String codetype);
}