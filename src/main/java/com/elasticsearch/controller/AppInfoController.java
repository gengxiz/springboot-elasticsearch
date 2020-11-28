package com.elasticsearch.controller;

import com.elasticsearch.AppInfoRepository;
import com.elasticsearch.pojo.AppInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class AppInfoController {

    @Autowired
    private AppInfoRepository appInfoRepository;


//    @Autowired
//    private ElasticsearchTemplate template;

    @GetMapping("/")
    public String index(){
        Iterable<AppInfo> iterable = appInfoRepository.findAll();
        Iterator<AppInfo> iterator = iterable.iterator();
        while (iterator.hasNext()){
            AppInfo appInfo = iterator.next();

            System.out.println(appInfo.getName());
        }

        return "index";
    }

    @GetMapping("/search")
    public String search(){

//        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria().
//                and(new Criteria("name").fuzzy("百度")));
//        List<AppInfo> infoList = template.queryForList(criteriaQuery, AppInfo.class);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("package","*tieba*");
        QueryBuilder nameQueryBuilder = QueryBuilders.queryStringQuery("贴吧").field("name");
        boolQueryBuilder.must(queryBuilder);
        boolQueryBuilder.must(nameQueryBuilder);
//        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("金融").field("name");

//        QueryBuilder queryBuilder= QueryBuilders.
//                multiMatchQuery("name", "百度", "package", "tieba");
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();


        System.out.println(searchQuery.getQuery());
        Page<AppInfo> appInfos = appInfoRepository.search(searchQuery);

        List<AppInfo> infoList = appInfos.getContent();

        for (AppInfo appInfo : infoList) {
            System.out.println(appInfo.getName());
        }

        return "search";
    }
}
