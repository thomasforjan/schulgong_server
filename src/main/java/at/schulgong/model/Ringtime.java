package at.schulgong.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Model of ringtime
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
@Getter
@Setter
@Entity
@Table(name = "Ringtime")
public class Ringtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ringtone_ID")
    private Ringtone ringtone;

    @Column(name = "startdate", columnDefinition = "DATE")
    private Date startDate;

    @Column(name = "enddate", columnDefinition = "DATE")
    private Date endDate;

    @Column(name = "monday")
    private boolean monday;

    @Column(name = "tuesday")
    private boolean tuesday;

    @Column(name = "wednesday")
    private boolean wednesday;

    @Column(name = "thursday")
    private boolean thursday;

    @Column(name = "friday")
    private boolean friday;

    @Column(name = "saturday")
    private boolean saturday;

    @Column(name = "sunday")
    private boolean sunday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hour_ID")
    private Hour hour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "minute_ID")
    private Minute minute;
}
