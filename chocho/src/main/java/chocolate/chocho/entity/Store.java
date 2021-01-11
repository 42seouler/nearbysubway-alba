package chocolate.chocho.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "store_id", columnDefinition = "BINARY(16)")
    private UUID        id;
    private String      name;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Employer_id")
    private Employer    employer;
    private Address     address;

    public Store(String name, Employer employer, Address address) {
        this.name = name;
        this.employer = employer;
        this.address = address;
    }

    public Store(UUID id, String name, Employer employer, Address address) {
        this.id = id;
        this.name = name;
        this.employer = employer;
        this.address = address;
    }
}