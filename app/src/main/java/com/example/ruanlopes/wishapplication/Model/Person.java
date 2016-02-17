package com.example.ruanlopes.wishapplication.Model;

/**
 * Created by ruanlopes on 16-01-12.
 */

public class Person {

    public String name;
    public String age;
    public int photoId;

    public Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }

  /*---------------*/
  /*Get and Setter*/
  /*---------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }



}

