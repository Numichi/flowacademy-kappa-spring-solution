package hu.flowacademy.band.database.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "tinytext")
    private String name;

    @Column(nullable = false)
    private Integer length;

    /**
     * Ha "null", akkor ingyenes. Tehát legyen ez az alapértelmezett.
     */
    @Column
    private Long price = null;

    @ManyToOne
    @JoinColumn
    private Album album;

    /**
     * [Source]: https://www.baeldung.com/hibernate-many-to-many
     *
     * ManyToMany miatt létrehoz egy másik táblát, "Track_Provider" néven (mint ha ez lenne a class neve)
     * és a két ID típusából (Track.id és a Provider.id) csinál egy kapcsoló táblát.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Track_Provider",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Provider> providerSet;
}
