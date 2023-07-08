package com.techacademy.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    /** 氏名 */
    @Column(length = 20, nullable = false)
    private String name;

    /** 削除フラグ */
    @Column(nullable = false)
    private Integer delete_flag;

    /** 登録日時 */
    @Column(nullable = false)
    private Date created_at;

    /** 更新日時 */
    @Column(nullable = false)
    private Date updated_at;

    @OneToOne(mappedBy = "employee")
    private Authentication authentication;


    @PreRemove
    @Transactional
    private void preRemove() {
        // 認証エンティティからuserを切り離す
        if (authentication!=null) {
            authentication.setEmployee(null);
        }
    }
}
