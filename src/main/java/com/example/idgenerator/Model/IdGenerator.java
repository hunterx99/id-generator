package com.example.idgenerator.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "id_generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "last_unique_id")
//    private Long lastUniqueId;
    private Integer lastUniqueId;

    @Column(name = "time_stamp")
    private Long timeStamp;
}
