package hu.flowacademy.band.database.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "tinytext")
    private String name;

    /**
     * "release" FOGLALT! MySQL alatt, így nevesítjük egy direkt dupla "-al.
     *
     * Exception: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'release datetime(6) not null, band_uuid binary(255), primary key (id)) engine=In' at line 1
     */
    @Column(name = "\"release\"", nullable = false)
    private LocalDate release;

    @ManyToOne
    @JoinColumn
    private Band band;

    /**
     * Track-ben van olyan attribútum, hogy "album". Azzal köti össze!
     */
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Track> trackList;
}
