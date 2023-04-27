package dev.jeremidigitallab.wimpychatty.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AccountFriend {

    @Id
    private String connectedId;

    // who initiated
    private String sourceId;

    private String targetId;

    private Date createdDate;

    private Date lastModified;

}
