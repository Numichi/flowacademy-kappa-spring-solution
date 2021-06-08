package hu.flowacademy.band.database.models;

import hu.flowacademy.band.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Band {

    /**
     * Ő ID-ja most UUID lesz, de máshol "int"-tet használok. Legyen ez az "így is lehet" rész. :)
     * [Source]: https://thorben-janssen.com/generate-uuids-primary-keys-hibernate/
     *
     * Vagy kézzel adsz neki egy UUID-t save-kor. :)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "tinytext")
    private String name;

    @Column(nullable = false)
    private Genre genre;

    /**
     * Album-ban van olyan attribútum, hogy "band". Azzal köti össze!
     */
    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Album> albums;
}
