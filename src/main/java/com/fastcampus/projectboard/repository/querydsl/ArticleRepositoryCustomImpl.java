package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class); // 이건 그냥 외워서 쓰면 됨
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

//        JPQLQuery<String> query = from(article)
//                .distinct()
//                .select(article.hashtag)
//                .where(article.hashtag.isNotNull())
//                .fetch();

        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }

}
