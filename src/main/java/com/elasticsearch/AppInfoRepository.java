package com.elasticsearch;

import com.elasticsearch.pojo.AppInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AppInfoRepository extends ElasticsearchRepository<AppInfo,String> {
    List<AppInfo> getAppInfoByNameLikeAndPackageNameLike(String name,String pack);

    List<AppInfo> getAppInfoByName(String name,String pack);
}
