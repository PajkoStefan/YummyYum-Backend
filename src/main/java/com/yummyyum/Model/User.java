package com.yummyyum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yummyyum.Model.History.CreditCardHistory;
import com.yummyyum.Model.History.ShippingAddressHistory;
import com.yummyyum.Model.History.SubscriptionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String username;

    private String password;

    @Column(name = "signup_date")
    private Timestamp signUpDate;

    @OneToOne
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private Email email;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<OrderInfo> OrderInfo;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private CreditCard creditCard;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ShippingAddress shippingAddress;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<CreditCardHistory> creditCardHistories;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ShippingAddressHistory> shippingAddressHistories;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<SubscriptionHistory> subscriptionHistories;


    public User() {
    }

    public User(String firstName, String lastName, String username,
                String password, Timestamp signUpDate) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.signUpDate = signUpDate;
    }


    @Override
    public String toString() {
        return "SignUp{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", signUpDate='" + signUpDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}

