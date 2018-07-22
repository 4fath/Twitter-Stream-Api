package org.interview.oauth.twitter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

// NOTE:  could be use full_text for +140 character tweets

@Data
@EqualsAndHashCode(of = {"id"})
public final class Tweet implements Comparable<Tweet>, Serializable {

    private static final long serialVersionUID = 1842856285525442244L;

    private long id;
    private String created_at;
    private String text;
    private User user;

    public String getIdAsString() {
        return String.valueOf(this.id);
    }

    // TODO : build an abstraction level too
    @Override
    public int compareTo(Tweet otherTweet) {
        try {
            Date thisTweetCreateDate = DateUtil.formatDateStr(this.created_at);
            Date otherTweetCreateDate = DateUtil.formatDateStr(otherTweet.getCreated_at());
            return thisTweetCreateDate.compareTo(otherTweetCreateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
