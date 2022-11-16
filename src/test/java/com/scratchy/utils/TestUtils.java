package com.scratchy.utils;

import java.util.List;

import com.scratchy.entity.Hobby;

public class TestUtils {

  protected static Hobby firstHobby;
  protected static Hobby secondHobby;

  static {
    firstHobby = new Hobby();
    firstHobby.setName("Swimming");

    secondHobby = new Hobby();
    secondHobby.setName("Hiking");
  }

  public static final List<Hobby> HOBBIES = List.of(firstHobby, secondHobby);

  public static final String USER_ID = "userId";
}
