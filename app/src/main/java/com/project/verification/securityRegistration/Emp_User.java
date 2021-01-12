package com.project.verification.securityRegistration;

public class Emp_User {

        public  String emp_id,name,address,state,city,pin,phone,email;

        public Emp_User()
        {

        }

        public Emp_User(String emp_id, String name,String address,String state,String city,String pin,String phone,String email)
        {
            this.emp_id=emp_id;
            this.name=name;
            this.address=address;
            this.state=state;
            this.city=city;
            this.pin=pin;
            this.phone=phone;
            this.email=email;
        }
    }

