package com.lfy.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.service.SolrService;
import com.lfy.blog.utils.SolrPager;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SolrServiceImpl  implements SolrService {


    @Autowired
    private HttpSolrClient solrClient;
    /**
     * 根据文章关键字进行搜索并分页
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public SolrPager<UserContent> findByKeyWords(String keyword, Integer pageNum, Integer pageSize) {
        //创建查询对象
        SolrQuery solrQuery=new SolrQuery();
        //设置查询条件
        solrQuery.setQuery("title:"+keyword+"*");
        //设置高亮
        solrQuery.setHighlight( true );
        solrQuery.addHighlightField( "title" );
        solrQuery.setHighlightSimplePre( "<span style='color:red'>" );
        solrQuery.setHighlightSimplePost( "</span>" );
        //分页
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 4;
        }
        solrQuery.setStart((pageNum-1)*pageSize);
        //一页显示几条数据
        solrQuery.setRows(pageSize);
        //排序根据时间降序
        solrQuery.addSort("rptTime",SolrQuery.ORDER.desc);
        //开始查询
        try{
            QueryResponse response = solrClient.query( solrQuery );
            //获得高亮数据集合
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //获得结果集
            SolrDocumentList resultList = response.getResults();
            //获得总数量
            long totalNum = resultList.getNumFound();
            List<UserContent> list = new ArrayList<UserContent>(  );
            for(SolrDocument solrDocument:resultList){
                //创建文章对象
                UserContent content = new UserContent();
                //文章id
                String id = (String) solrDocument.get( "id" );
                Object content1 = solrDocument.get( "content" );
                Object commentNum = solrDocument.get( "commentNum" );
                Object downvote = solrDocument.get( "downvote" );
                Object upvote = solrDocument.get( "upvote" );
                Object nickName = solrDocument.get( "nickName" );
                Object imgUrl = solrDocument.get( "imgUrl" );
                Object uid = solrDocument.get( "uId" );
                Object rpt_time = solrDocument.get( "rptTime" );
                Object category = solrDocument.get( "category" );
                Object personal = solrDocument.get( "personal" );
                Object title_pic = solrDocument.get("titlePic" );
                //取得高亮数据集合中的文章标题
                Map<String, List<String>> map = highlighting.get( id );
                String title = map.get( "title" ).get( 0 );

                content.setId( Long.parseLong( id ) );
                content.setCommentNum( Integer.parseInt( commentNum.toString() ) );
                content.setDownvote( Integer.parseInt( downvote.toString() ) );
                content.setUpvote( Integer.parseInt( upvote.toString() ) );
                content.setNickName( nickName.toString() );
                content.setImgUrl( imgUrl.toString() );
                content.setUId( Long.parseLong( uid.toString() ) );
                content.setTitle( title );
                content.setPersonal( personal.toString() );
                Date date = (Date)rpt_time;
                content.setRptTime(date);
                List<String> clist = (ArrayList)content1;
                content.setContent(clist.get(0).toString() );
                content.setCategory( category.toString() );
                list.add(content);
            }
            //分页
            SolrPager<UserContent> objectSolrPager = new SolrPager<>();
            //查询出所有数据
            objectSolrPager.setPageNum(pageNum);
            objectSolrPager.setPageSize(pageSize);
            objectSolrPager.setList(list);
            objectSolrPager.setRecordCount(totalNum);
            //计算出总页数
            int pageCount= (int)Math.ceil((double)totalNum/pageSize);
            objectSolrPager.setPageCount(pageCount);
            return objectSolrPager;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addUserContent(UserContent cont) {
        if(cont!=null){
            addDocument(cont);
        }
    }

    @Override
    public void updateUserContent(UserContent cont) {
        if(cont!=null){
            addDocument(cont);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            solrClient.deleteById(id.toString());
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDocument(UserContent cont) {
        try {
            SolrInputDocument inputDocument = new SolrInputDocument();
            inputDocument.addField("comment_num", cont.getCommentNum());
            inputDocument.addField("downvote", cont.getDownvote());
            inputDocument.addField("upvote", cont.getUpvote());
            inputDocument.addField("nick_name", cont.getNickName());
            inputDocument.addField("img_url", cont.getImgUrl());
            inputDocument.addField("rpt_time", cont.getRptTime());
            inputDocument.addField("content", cont.getContent());
            inputDocument.addField("category", cont.getCategory());
            inputDocument.addField("title", cont.getTitle());
            inputDocument.addField("u_id", cont.getUId());
            inputDocument.addField("id", cont.getId());
            inputDocument.addField("personal", cont.getPersonal());
            inputDocument.addField("title_pic",cont.getTitlePic());
            solrClient.add(inputDocument);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
