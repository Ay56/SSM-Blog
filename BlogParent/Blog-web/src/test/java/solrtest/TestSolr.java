package solrtest;

import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.service.UserContentService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.IOException;
import java.util.List;

@ContextConfiguration(locations = {"classpath:applicationContext-redis.xml","classpath:spring-mybatis.xml","classpath:applicationContext-activemq.xml","classpath:applicationContext-solr.xml"})
public class TestSolr extends AbstractJUnit4SpringContextTests {


    @Autowired
    private SolrClient solrServer;

    @Autowired
    private UserContentService userContentService;

    @Test
    public void testSaveAll() throws IOException, SolrServerException {
        List<UserContent> list =userContentService.selectAll();
        if(list!=null && list.size()>0){
            for (UserContent cont : list){
                SolrInputDocument inputDocument = new SolrInputDocument();
                inputDocument.addField( "comment_num", cont.getCommentNum() );
                inputDocument.addField( "downvote", cont.getDownvote() );
                inputDocument.addField( "upvote", cont.getUpvote() );
                inputDocument.addField( "nick_name", cont.getNickName());
                inputDocument.addField( "img_url", cont.getImgUrl() );
                inputDocument.addField( "rpt_time", cont.getRptTime() );
                inputDocument.addField( "content", cont.getContent() );
                inputDocument.addField( "category", cont.getCategory());
                inputDocument.addField( "title", cont.getTitle() );
                inputDocument.addField( "u_id", cont.getUId());
                inputDocument.addField( "id", cont.getId());
                inputDocument.addField( "personal", cont.getPersonal());
                inputDocument.addField( "title_pic", cont.getTitlePic());
                solrServer.add( inputDocument );
            }
        }

        solrServer.commit();
    }
}
