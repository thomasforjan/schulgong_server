package at.schulgong;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "RingTime")
public class RingTime {

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

 /* @Column(name = "addinfo")
  private String addInfo;*/


}
