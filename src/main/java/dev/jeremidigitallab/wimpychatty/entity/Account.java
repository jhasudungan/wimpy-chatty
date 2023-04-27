package dev.jeremidigitallab.wimpychatty.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private String accountId;

    private String accountName;

    private String accountDisplayName;

    private String accountLoginPassword;
    
    private Date createdDate;

    private Date lastModified;

}
