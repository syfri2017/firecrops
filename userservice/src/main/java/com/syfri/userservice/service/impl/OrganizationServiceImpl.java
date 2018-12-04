package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.OrganizationTree;
import com.syfri.userservice.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.OrganizationDAO;
import com.syfri.userservice.model.OrganizationVO;
import com.syfri.userservice.service.OrganizationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("organizationService")
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationVO> implements OrganizationService {

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private RedisService redisService;

    @Override
    public OrganizationDAO getBaseDAO() {
        return organizationDAO;
    }

    /*根据用户ID查询其组织机构.*/
    @Override
    public OrganizationVO doFindOrganizationByUserid(String userid) {
        return organizationDAO.doFindOrganizationByUserid(userid);
    }

    /*获取所有总队*/
    public List<OrganizationVO> getZongdui() {
        return organizationDAO.getZongdui();
    }

    /*--根据重点单位id获取重点单位详情--*/
    @Override
    public OrganizationVO doFindDetailById(String uuid) {
        return organizationDAO.doFindDetailById(uuid);
    }

    @Override
    public List<OrganizationTree> doFindAllOrganization() {
        // 目的树
        List<OrganizationTree> organizationTrees = new ArrayList<>();
        if (redisService.exists("organization")) {
            organizationTrees = (List<OrganizationTree>) redisService.get("organization");
        } else {
            // 源树
            List<OrganizationVO> organizationVOs = organizationDAO.doFindAllOrganization();

            if (organizationVOs != null && organizationVOs.size() > 0) {
                for (OrganizationVO organizationVO : organizationVOs) {
                    // 选出部局
                    if (organizationVO.getJgid().equals("01000000")) {
                        OrganizationTree tree = new OrganizationTree();
                        tree.setUuid(organizationVO.getUuid());
                        tree.setJgjc(organizationVO.getJgjc());
                        tree.setJgid(organizationVO.getJgid());
                        List<OrganizationTree> children = new ArrayList();
                        for (OrganizationVO organizationVO2 : organizationVOs) {
                            // 选出总队
                            if (organizationVO.getUuid().equals(organizationVO2.getSjjgid())) {
                                OrganizationTree tree2 = new OrganizationTree();
                                tree2.setUuid(organizationVO2.getUuid());
                                tree2.setJgjc(organizationVO2.getJgjc());
                                tree2.setJgid(organizationVO2.getJgid());
                                List<OrganizationTree> children2 = new ArrayList();
                                for (OrganizationVO organizationVO3 : organizationVOs) {
                                    // 选出支队
                                    if (organizationVO2.getUuid().equals(organizationVO3.getSjjgid())) {
                                        OrganizationTree tree3 = new OrganizationTree();
                                        tree3.setUuid(organizationVO3.getUuid());
                                        tree3.setJgjc(organizationVO3.getJgjc());
                                        tree3.setJgid(organizationVO3.getJgid());
                                        List<OrganizationTree> children3 = new ArrayList();
                                        for (OrganizationVO organizationVO4 : organizationVOs) {
                                            // 选出大队
                                            if (organizationVO3.getUuid().equals(organizationVO4.getSjjgid())) {
                                                OrganizationTree tree4 = new OrganizationTree();
                                                tree4.setUuid(organizationVO4.getUuid());
                                                tree4.setJgjc(organizationVO4.getJgjc());
                                                tree4.setJgid(organizationVO4.getJgid());
                                                List<OrganizationTree> children4 = new ArrayList();
                                                for (OrganizationVO organizationVO5 : organizationVOs) {
                                                    // 选出中队
                                                    if (organizationVO4.getUuid().equals(organizationVO5.getSjjgid())) {
                                                        OrganizationTree tree5 = new OrganizationTree();
                                                        tree5.setUuid(organizationVO5.getUuid());
                                                        tree5.setJgjc(organizationVO5.getJgjc());
                                                        tree5.setJgid(organizationVO5.getJgid());
                                                        children4.add(tree5);
                                                    }
                                                }
                                                if (!children4.isEmpty()) {
                                                    tree4.setChildren(children4);
                                                }
                                                children3.add(tree4);
                                            }
                                        }
                                        if (!children3.isEmpty()) {
                                            tree3.setChildren(children3);
                                        }
                                        children2.add(tree3);
                                    }
                                }
                                if (!children2.isEmpty()) {
                                    tree2.setChildren(children2);
                                }
                                children.add(tree2);
                            }
                        }
                        if (!children.isEmpty()) {
                            tree.setChildren(children);
                        }
                        organizationTrees.add(tree);
                    }
                }
            }
            redisService.set("organization", organizationTrees);
        }
        return organizationTrees;
    }

    /*--根据用户获取组织机构树.--*/
    @Override
    public List<OrganizationTree> doFindJgTreeByUser(OrganizationVO organizationVO) {
        List<OrganizationTree> tree1s = new ArrayList<>();
        if (redisService.exists("organizationTreeByUser" + organizationVO.getUuid())) {
            tree1s = (List<OrganizationTree>) redisService.get("organizationTreeByUser" + organizationVO.getUuid());
        } else {
            OrganizationTree tree1 = new OrganizationTree(organizationVO.getUuid(), organizationVO.getJgjc(), organizationVO.getJgid());
            //获取组织机构下的总队
            List<OrganizationVO> zongduiList = organizationDAO.getZongdui();
            List<OrganizationTree> tree2s = new ArrayList<>();
            for (OrganizationVO zongdui : zongduiList) {
                tree2s.add(new OrganizationTree(zongdui.getUuid(), zongdui.getJgjc(), zongdui.getJgid()));
            }
            List<OrganizationTree> tree2Children = new ArrayList<>();
            for (OrganizationTree tree2 : tree2s) {
                List<OrganizationTree> tree3s = organizationDAO.doFindJgBySjjgid(tree2.getUuid());
                List<OrganizationTree> tree3Children = new ArrayList<>();
                for (OrganizationTree tree3 : tree3s) {
                    List<OrganizationTree> tree4s = organizationDAO.doFindJgBySjjgid(tree3.getUuid());
                    List<OrganizationTree> tree4Children = new ArrayList<>();
                    for (OrganizationTree tree4 : tree4s) {
                        List<OrganizationTree> tree5s = organizationDAO.doFindJgBySjjgid(tree4.getUuid());
                        if (!tree5s.isEmpty()) {
                            tree4.setChildren(tree5s);
                        }
                        tree4Children.add(tree4);
                    }
                    if (!tree4s.isEmpty()) {
                        tree3.setChildren(tree4s);
                    }
                    tree3Children.add(tree3);
                }
                if (!tree3Children.isEmpty()) {
                    tree2.setChildren(tree3Children);
                }
                tree2Children.add(tree2);
            }
            if (!tree2Children.isEmpty()) {
                tree1.setChildren(tree2Children);
            }
            tree1s.add(tree1);
            redisService.set("organizationTreeByUser" + organizationVO.getUuid(), tree1s);
        }
        return tree1s;
    }

    @Override
    public int doInsertByVO(OrganizationVO organizationVO) {
        int count = 0;
        count = organizationDAO.doInsertByVO(organizationVO);
        if (count > 0) {
            redisService.remove("organization");
        }
        return count;
    }
}