package com.scratchy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users_hobbies")
@NoArgsConstructor
public class UserHobby {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long hobbyId;

  @Column(name = "user_id")
  private String userId;

  public UserHobby(long hobbyId, String userId) {
    this.userId = userId;
    this.hobbyId = hobbyId;
  }
}
