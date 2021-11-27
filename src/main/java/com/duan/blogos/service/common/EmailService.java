package com.duan.blogos.service.common;

/**
 * Created on 2018/4/7.
 *
 * @author j_jiasheng
 */
public interface EmailService {

    /**
     * 发送反馈邮件
     * @param bloggerId 发送者博主 id
     * @param content 邮件内容
     * @param subject 主题
     * @param contact 发送者留的联系方式，如果有的话
     * @return 邮件发送成功返回 true
     */
    boolean sendFeedback(int bloggerId,String subject, String content, String contact);

}
