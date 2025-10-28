package is.hi.hbv501g.eventmanager.Persistence.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String type;

    @Lob
    @Column(name = "picByte", length = 5000)
    private byte[] picByte;

    @OneToOne
    @JoinColumn(name = "events", referencedColumnName = "id")
    private Event event;
}
