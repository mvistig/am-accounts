package mvi.accounts.data.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@SequenceGenerator(name = "SEQ_ACCOUNT_ID", allocationSize = 1)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCOUNT_ID")
    private Long id;
    private String firstName;
    private String lastName;
    private String phones;
    private String address;
}
