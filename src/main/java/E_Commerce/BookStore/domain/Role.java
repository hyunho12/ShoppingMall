package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @Column(length = 150, nullable = false)
    private String description;

    public Role(){}

    public Role(Long id){this.id = id;}

    public Role(String name){
        this.name = name;
    }

    public Role(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
