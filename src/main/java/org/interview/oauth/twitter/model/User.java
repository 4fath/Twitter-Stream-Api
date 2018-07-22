package org.interview.oauth.twitter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;


@Data
@EqualsAndHashCode(of = {"id"})
public final class User implements Comparable<User>, Serializable {

    private static final long serialVersionUID = 4555920791122320012L;

    private long id;
    private String screen_name;
    private String created_at;

    public String getIdAsString() {
        return String.valueOf(this.id);
    }

    // TODO : build an abstraction level
    @Override
    public int compareTo(User otherUser) {
        try {
            Date thisUserCreateDate = DateUtil.formatDateStr(this.created_at);
            Date otherUserCreateDate = DateUtil.formatDateStr(otherUser.getCreated_at());
            return thisUserCreateDate.compareTo(otherUserCreateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
