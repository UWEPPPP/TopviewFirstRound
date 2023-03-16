package com.liujiahui.www.entity.po;

/**
 * 用户
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class User {
   protected   String name;
   protected   int phoneNumber;
   protected   String password;
   protected   String gender;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(int phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }
}
