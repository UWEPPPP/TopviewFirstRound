package com.liujiahui.www.entity.po;

/**
 * 用户
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class User {
   protected   String name;
   protected   String phoneNumber;
   protected   String password;
   protected   String gender;
   protected   String balance;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
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
