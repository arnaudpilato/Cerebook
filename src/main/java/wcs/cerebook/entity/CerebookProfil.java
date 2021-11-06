package wcs.cerebook.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CerebookProfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activity;
    private String avatar;
    private boolean isInterestedInWoman;
    private boolean isInterestedInMen;
    private String hometown;
    private String relashionship;
}
