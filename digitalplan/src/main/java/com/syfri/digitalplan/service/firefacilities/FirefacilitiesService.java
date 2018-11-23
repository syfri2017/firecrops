package com.syfri.digitalplan.service.firefacilities;

import com.syfri.baseapi.model.ValueObject;
import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.firefacilities.*;

import java.util.List;
import java.util.Map;

public interface FirefacilitiesService extends BaseService<FirefacilitiesVO> {
    public Map<String, List> doFindlist(FirefacilitiesVO vo);

    public ValueObject doFindXfssDetail(FirefacilitiesVO vo);

    public FirefacilitiesVO doInsertFirefacilities(FirefacilitiesVO firefacilitiesVO);

    public FirefacilitiesVO doUpdateFirefacilities(FirefacilitiesVO firefacilitiesVO);

    public int doDeleteFirefacilities(List<FirefacilitiesVO> facilitiesList);//消防设施删除

    public FirefacilitiesVO doDeleteFirefacilitiesDetails(FirefacilitiesVO firefacilitiesVO);//消防设施从表删除
}