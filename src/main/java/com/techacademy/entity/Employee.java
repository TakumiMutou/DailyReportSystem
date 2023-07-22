package com.techacademy.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    /** 氏名 */
    @Column(length = 20, nullable = false)
    @NotEmpty
    private String name;

    /** 削除フラグ */
    @Column(nullable = false)
    @NotNull
    private Integer deleteFlag;

    /** 登録日時 */
    @Column(nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    /** 更新日時 */
    @Column(nullable = false)
    @NotNull
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Authentication authentication;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Report> reports;


    @PreRemove
    @Transactional
    private void preRemove() {

        if (authentication!=null) {
            authentication.setEmployee(null);
        }
    }
}
