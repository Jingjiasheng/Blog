package com.duan.blogos.service.impl.common;

import com.duan.blogos.dao.blog.*;
import com.duan.blogos.dao.blogger.BloggerAccountDao;
import com.duan.blogos.dao.blogger.BloggerPictureDao;
import com.duan.blogos.dao.blogger.BloggerProfileDao;
import com.duan.blogos.dto.blog.BlogStatisticsCountDTO;
import com.duan.blogos.dto.blog.BlogStatisticsDTO;
import com.duan.blogos.dto.blogger.BloggerDTO;
import com.duan.blogos.entity.blog.*;
import com.duan.blogos.entity.blogger.BloggerAccount;
import com.duan.blogos.entity.blogger.BloggerPicture;
import com.duan.blogos.entity.blogger.BloggerProfile;
import com.duan.blogos.enums.BloggerPictureCategoryEnum;
import com.duan.blogos.manager.DataFillingManager;
import com.duan.blogos.manager.StringConstructorManager;
import com.duan.blogos.manager.properties.DbProperties;
import com.duan.blogos.restful.ResultBean;
import com.duan.blogos.service.blogger.BloggerStatisticsService;
import com.duan.blogos.service.common.BlogStatisticsService;
import com.duan.blogos.util.CollectionUtils;
import com.duan.blogos.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created on 2017/12/19.
 *
 * @author j_jiasheng
 */
@Service
public class BlogStatisticsServiceImpl implements BlogStatisticsService {

    @Autowired
    private DbProperties dbProperties;

    @Autowired
    private DataFillingManager dataFillingManager;

    @Autowired
    private BlogCategoryDao categoryDao;

    @Autowired
    private BlogLabelDao labelDao;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogStatisticsDao statisticsDao;

    @Autowired
    private BlogLikeDao likeDao;

    @Autowired
    private BlogCollectDao collectDao;

    @Autowired
    private BlogCommentDao commentDao;

    @Autowired
    private BloggerStatisticsService statisticsService;

    @Override
    public ResultBean<BlogStatisticsDTO> getBlogStatistics(int blogId) {

        Blog blog = blogDao.getBlogById(blogId);
        if (blog == null) return null;

        int bloggerId = blog.getBloggerId();

        // ????????????
        BlogStatistics statistics = statisticsDao.getStatistics(blogId);
        if (statistics == null) return null;

        // ??????
        BlogCategory[] categories = null;
        String sn = dbProperties.getStringFiledSplitCharacterForNumber();
        int[] cids = StringUtils.intStringDistinctToArray(blog.getCategoryIds(), sn);
        if (!CollectionUtils.isEmpty(cids)) {
            categories = categoryDao.listCategoryById(cids).toArray(new BlogCategory[cids.length]);
        }

        // ??????
        BlogLabel[] labels = null;
        int[] lids = StringUtils.intStringDistinctToArray(blog.getLabelIds(), sn);
        if (!CollectionUtils.isEmpty(lids)) {
            labels = labelDao.listLabelById(lids).toArray(new BlogLabel[lids.length]);
        }

        int c = 0;
        // ????????????????????????
        BloggerDTO[] likes = null;
        List<BlogLike> likeList = likeDao.listAllLikeByBlogId(blogId);
        if (!CollectionUtils.isEmpty(likeList)) {
            int[] ids = new int[likeList.size()];
            for (BlogLike like : likeList) {
                ids[c++] = like.getLikerId();
            }
            likes = statisticsService.listBloggerDTO(ids);
        }

        // ???????????????????????????
        BloggerDTO[] collects = null;
        c = 0;
        List<BlogCollect> collectList = collectDao.listAllCollectByBlogId(blogId);
        if (!CollectionUtils.isEmpty(collectList)) {
            int[] ids = new int[collectList.size()];
            for (BlogCollect collect : collectList) {
                ids[c++] = collect.getCollectorId();
            }
            collects = statisticsService.listBloggerDTO(ids);
        }

        // ???????????????????????????
        BloggerDTO[] commenter = null;
        c = 0;
        List<BlogComment> commentList = commentDao.listAllCommentByBlogId(blogId);
        if (!CollectionUtils.isEmpty(commentList)) {
            int[] ids = new int[commentList.size()];
            for (BlogComment comment : commentList) {
                // ???????????????????????????????????????????????????
                Integer id = comment.getSpokesmanId();
                if (id != null)
                    ids[c++] = id;
            }
            // ids ????????????
            commenter = statisticsService.listBloggerDTO(IntStream.of(Arrays.copyOf(ids, c)).distinct().toArray());
        }

        BlogStatisticsDTO dto = dataFillingManager.blogStatisticsToDTO(blog, statistics, categories, labels,
                likes, collects, commenter, dbProperties.getStringFiledSplitCharacterForString());

        return new ResultBean<>(dto);
    }


    @Override
    public ResultBean<BlogStatisticsCountDTO> getBlogStatisticsCount(int blogId) {

        BlogStatistics statistics = statisticsDao.getStatistics(blogId);
        if (statistics == null) return null;

        BlogStatisticsCountDTO dto = dataFillingManager.blogStatisticsCountToDTO(statistics);
        return new ResultBean<>(dto);
    }

    @Override
    public boolean updateBlogViewCountPlus(int blogId) {
        int effect = statisticsDao.updateViewCountPlus(blogId);

        if (effect > 0) return true;
        else return false;
    }
}
